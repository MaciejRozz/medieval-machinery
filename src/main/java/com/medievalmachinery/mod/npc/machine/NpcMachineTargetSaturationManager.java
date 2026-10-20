/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.machine.core.MachineCombatTarget;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.class_3218;

public final class NpcMachineTargetSaturationManager {
    private static final long INTENT_TICKS = 30L;
    private static final Map<class_3218, Map<UUID, Intent>> INTENTS = new WeakHashMap<class_3218, Map<UUID, Intent>>();

    private NpcMachineTargetSaturationManager() {
    }

    public static void note(class_3218 world, UUID machineId, MachineCombatTarget target) {
        if (world == null || machineId == null || target == null) {
            return;
        }
        NpcMachineTargetSaturationManager.cleanup(world);
        INTENTS.computeIfAbsent(world, ignored -> new HashMap()).put(machineId, new Intent(target.stableKey(), world.method_8510() + 30L));
    }

    public static int countOthers(class_3218 world, UUID machineId, MachineCombatTarget target) {
        if (world == null || machineId == null || target == null) {
            return 0;
        }
        NpcMachineTargetSaturationManager.cleanup(world);
        Map<UUID, Intent> map = INTENTS.get(world);
        if (map == null) {
            return 0;
        }
        int count = 0;
        for (Map.Entry<UUID, Intent> entry : map.entrySet()) {
            if (machineId.equals(entry.getKey()) || entry.getValue().targetKey != target.stableKey()) continue;
            ++count;
        }
        return count;
    }

    public static void clear(class_3218 world, UUID machineId) {
        Map<UUID, Intent> map = INTENTS.get(world);
        if (map != null) {
            map.remove(machineId);
        }
    }

    private static void cleanup(class_3218 world) {
        Map<UUID, Intent> map = INTENTS.get(world);
        if (map == null) {
            return;
        }
        long now = world.method_8510();
        map.entrySet().removeIf(e -> ((Intent)e.getValue()).expiresAt <= now);
        if (map.isEmpty()) {
            INTENTS.remove(world);
        }
    }

    private record Intent(long targetKey, long expiresAt) {
    }
}

