/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_2338
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.machine.core.MachineCrewSupplyNeed;
import com.medievalmachinery.mod.npc.machine.NpcMachineSupplyPlan;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.class_1263;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_3218;

public final class NpcMachineSupplyReservationManager {
    private static final long DEFAULT_TTL = 400L;
    private static final Map<class_3218, Map<class_2338, Map<UUID, Reservation>>> BY_WORLD = new WeakHashMap<class_3218, Map<class_2338, Map<UUID, Reservation>>>();

    private NpcMachineSupplyReservationManager() {
    }

    public static NpcMachineSupplyPlan tryReserve(class_3218 world, class_2338 chestPos, UUID npcId, class_1263 inventory, List<MachineCrewSupplyNeed> needs) {
        if (world == null || chestPos == null || npcId == null || inventory == null || needs == null || needs.isEmpty()) {
            return null;
        }
        NpcMachineSupplyReservationManager.cleanup(world, chestPos);
        Map<class_1792, Integer> inventoryCounts = NpcMachineSupplyReservationManager.countInventory(inventory);
        Map<class_1792, Integer> reservedByOthers = NpcMachineSupplyReservationManager.reservedByOthers(world, chestPos, npcId);
        LinkedHashMap<class_1792, Integer> toReserve = new LinkedHashMap<class_1792, Integer>();
        for (MachineCrewSupplyNeed need : needs) {
            if (need == null || need.desiredCount() <= 0) continue;
            int available = Math.max(0, inventoryCounts.getOrDefault(need.item(), 0) - reservedByOthers.getOrDefault(need.item(), 0));
            if (available < need.minimumCount()) {
                return null;
            }
            int reserve = Math.min(need.desiredCount(), available);
            if (reserve <= 0) continue;
            toReserve.put(need.item(), reserve);
        }
        if (toReserve.isEmpty()) {
            return null;
        }
        Map worldMap = BY_WORLD.computeIfAbsent(world, ignored -> new HashMap());
        Map chestMap = worldMap.computeIfAbsent(chestPos.method_10062(), ignored -> new HashMap());
        chestMap.put(npcId, new Reservation(Map.copyOf(toReserve), world.method_8510() + 400L));
        return new NpcMachineSupplyPlan(chestPos, toReserve);
    }

    public static void refresh(class_3218 world, NpcMachineSupplyPlan plan, UUID npcId) {
        if (world == null || plan == null || npcId == null) {
            return;
        }
        Map<class_2338, Map<UUID, Reservation>> worldMap = BY_WORLD.get(world);
        if (worldMap == null) {
            return;
        }
        Map<UUID, Reservation> chestMap = worldMap.get(plan.chestPos());
        if (chestMap == null) {
            return;
        }
        Reservation current = chestMap.get(npcId);
        if (current != null) {
            chestMap.put(npcId, new Reservation(current.items(), world.method_8510() + 400L));
        }
    }

    public static void release(class_3218 world, NpcMachineSupplyPlan plan, UUID npcId) {
        if (world == null || plan == null || npcId == null) {
            return;
        }
        Map<class_2338, Map<UUID, Reservation>> worldMap = BY_WORLD.get(world);
        if (worldMap == null) {
            return;
        }
        Map<UUID, Reservation> chestMap = worldMap.get(plan.chestPos());
        if (chestMap == null) {
            return;
        }
        chestMap.remove(npcId);
        if (chestMap.isEmpty()) {
            worldMap.remove(plan.chestPos());
        }
        if (worldMap.isEmpty()) {
            BY_WORLD.remove(world);
        }
    }

    private static void cleanup(class_3218 world, class_2338 chestPos) {
        Map<class_2338, Map<UUID, Reservation>> worldMap = BY_WORLD.get(world);
        if (worldMap == null) {
            return;
        }
        Map<UUID, Reservation> chestMap = worldMap.get(chestPos);
        if (chestMap == null) {
            return;
        }
        long now = world.method_8510();
        chestMap.entrySet().removeIf(entry -> ((Reservation)entry.getValue()).expiresAt() <= now);
        if (chestMap.isEmpty()) {
            worldMap.remove(chestPos);
        }
        if (worldMap.isEmpty()) {
            BY_WORLD.remove(world);
        }
    }

    private static Map<class_1792, Integer> reservedByOthers(class_3218 world, class_2338 chestPos, UUID npcId) {
        HashMap<class_1792, Integer> result = new HashMap<class_1792, Integer>();
        Map<class_2338, Map<UUID, Reservation>> worldMap = BY_WORLD.get(world);
        if (worldMap == null) {
            return result;
        }
        Map<UUID, Reservation> chestMap = worldMap.get(chestPos);
        if (chestMap == null) {
            return result;
        }
        for (Map.Entry<UUID, Reservation> entry : chestMap.entrySet()) {
            if (entry.getKey().equals(npcId)) continue;
            for (Map.Entry<class_1792, Integer> item : entry.getValue().items().entrySet()) {
                result.merge(item.getKey(), item.getValue(), Integer::sum);
            }
        }
        return result;
    }

    private static Map<class_1792, Integer> countInventory(class_1263 inventory) {
        HashMap<class_1792, Integer> result = new HashMap<class_1792, Integer>();
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            class_1799 stack = inventory.method_5438(slot);
            if (stack.method_7960()) continue;
            result.merge(stack.method_7909(), stack.method_7947(), Integer::sum);
        }
        return result;
    }

    private record Reservation(Map<class_1792, Integer> items, long expiresAt) {
    }
}

