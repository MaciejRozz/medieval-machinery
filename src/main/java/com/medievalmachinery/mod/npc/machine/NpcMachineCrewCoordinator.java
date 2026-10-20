/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_238
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.machine.core.MachineCrewAction;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineCrewReadinessPolicy;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.class_238;
import net.minecraft.class_3218;

public final class NpcMachineCrewCoordinator {
    private static final long LEASE_TICKS = 40L;
    private static final Map<class_3218, Map<UUID, EnumMap<MachineCrewAction, WorkLease>>> LEASES = new WeakHashMap<class_3218, Map<UUID, EnumMap<MachineCrewAction, WorkLease>>>();

    private NpcMachineCrewCoordinator() {
    }

    public static boolean shouldDeferToDedicatedCrew(class_3218 world, MedievalSoldierEntity soldier, UUID machineId, MachineCrewRole role, MachineCrewAction action) {
        if (world == null || soldier == null || machineId == null || role == null || action == null) {
            return false;
        }
        if (action != MachineCrewAction.LOAD || role != MachineCrewRole.OPERATOR) {
            return false;
        }
        class_238 scan = soldier.method_5829().method_1014(48.0);
        return !world.method_8390(MedievalSoldierEntity.class, scan, other -> other != soldier && other.method_5805() && NpcMachineCrewReadinessPolicy.canWork(other, MachineCrewRole.LOADER) && other.medievalMachinery$isAssignedTo(machineId, MachineCrewRole.LOADER)).isEmpty();
    }

    public static boolean tryLease(class_3218 world, UUID machineId, UUID npcId, MachineCrewAction action) {
        if (world == null || machineId == null || npcId == null || action == null || action == MachineCrewAction.IDLE) {
            return false;
        }
        NpcMachineCrewCoordinator.cleanup(world);
        Map machines = LEASES.computeIfAbsent(world, ignored -> new HashMap());
        EnumMap actions = machines.computeIfAbsent(machineId, ignored -> new EnumMap(MachineCrewAction.class));
        WorkLease current = (WorkLease)actions.get((Object)action);
        long now = world.method_8510();
        if (current != null && current.expiresAt > now && !current.npcId.equals(npcId)) {
            return false;
        }
        actions.put(action, new WorkLease(npcId, now + 40L));
        return true;
    }

    public static void refresh(class_3218 world, UUID machineId, UUID npcId) {
        Map<UUID, EnumMap<MachineCrewAction, WorkLease>> machines = LEASES.get(world);
        if (machines == null) {
            return;
        }
        EnumMap<MachineCrewAction, WorkLease> actions = machines.get(machineId);
        if (actions == null) {
            return;
        }
        long expires = world.method_8510() + 40L;
        actions.replaceAll((action, lease) -> lease.npcId.equals(npcId) ? new WorkLease(npcId, expires) : lease);
    }

    public static void release(class_3218 world, UUID machineId, UUID npcId) {
        Map<UUID, EnumMap<MachineCrewAction, WorkLease>> machines = LEASES.get(world);
        if (machines == null) {
            return;
        }
        EnumMap<MachineCrewAction, WorkLease> actions = machines.get(machineId);
        if (actions == null) {
            return;
        }
        actions.entrySet().removeIf(e -> ((WorkLease)e.getValue()).npcId.equals(npcId));
        if (actions.isEmpty()) {
            machines.remove(machineId);
        }
        if (machines.isEmpty()) {
            LEASES.remove(world);
        }
    }

    private static void cleanup(class_3218 world) {
        Map<UUID, EnumMap<MachineCrewAction, WorkLease>> machines = LEASES.get(world);
        if (machines == null) {
            return;
        }
        long now = world.method_8510();
        machines.values().forEach(actions -> actions.entrySet().removeIf(e -> ((WorkLease)e.getValue()).expiresAt <= now));
        machines.entrySet().removeIf(e -> ((EnumMap)e.getValue()).isEmpty());
        if (machines.isEmpty()) {
            LEASES.remove(world);
        }
    }

    private record WorkLease(UUID npcId, long expiresAt) {
    }
}

