/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1937
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineAssignmentAudit;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_3218;

public final class NpcMachineAssignmentService {
    private NpcMachineAssignmentService() {
    }

    public static NpcMachineAssignmentAudit.Result validate(MedievalSoldierEntity soldier, MedievalMachineryOperable machine, MachineCrewRole crewRole) {
        if (soldier == null || machine == null || crewRole == null || soldier.method_31481() || !soldier.method_5805()) {
            return NpcMachineAssignmentAudit.Result.INVALID_REFERENCE;
        }
        class_1297 machineEntity = machine.asEntity();
        if (machineEntity == null || machineEntity.method_31481() || machineEntity.method_37908() != soldier.method_37908()) {
            return NpcMachineAssignmentAudit.Result.INVALID_REFERENCE;
        }
        return NpcMachineAssignmentAudit.validate(soldier, machine, crewRole);
    }

    public static boolean canAssign(MedievalSoldierEntity soldier, MedievalMachineryOperable machine, MachineCrewRole crewRole) {
        return NpcMachineAssignmentService.validate(soldier, machine, crewRole) == NpcMachineAssignmentAudit.Result.OK;
    }

    public static boolean assign(MedievalSoldierEntity soldier, MedievalMachineryOperable machine, MachineCrewRole crewRole) {
        if (!NpcMachineAssignmentService.canAssign(soldier, machine, crewRole)) {
            return false;
        }
        soldier.medievalMachinery$assignMachine(machine.asEntity().method_5667(), crewRole);
        return true;
    }

    public static boolean isAssignedToLoadedStationaryMachine(MedievalSoldierEntity soldier) {
        MedievalMachineryOperable operable;
        class_1937 class_19372;
        if (soldier == null || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return false;
        }
        class_3218 world = (class_3218)class_19372;
        UUID machineId = soldier.medievalMachinery$getAssignedMachineId();
        if (machineId == null) {
            return false;
        }
        class_1297 entity = world.method_14190(machineId);
        return entity instanceof MedievalMachineryOperable && !(operable = (MedievalMachineryOperable)entity).medievalMachinery$isMobile();
    }

    public static void releaseOperatorClaim(MedievalSoldierEntity soldier) {
        MedievalMachineryOperable operable;
        class_1937 class_19372;
        if (soldier == null) {
            return;
        }
        UUID machineId = soldier.medievalMachinery$getAssignedMachineId();
        MachineCrewRole role = soldier.medievalMachinery$getAssignedMachineCrewRole();
        if (machineId == null || role != MachineCrewRole.OPERATOR || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 world = (class_3218)class_19372;
        class_1297 entity = world.method_14190(machineId);
        if (entity instanceof MedievalMachineryOperable && (operable = (MedievalMachineryOperable)entity).medievalMachinery$isOperatedBy(soldier.method_5667())) {
            operable.medievalMachinery$release(soldier.method_5667());
        }
    }

    public static void clear(MedievalSoldierEntity soldier) {
        class_3218 world;
        class_1297 entity;
        class_1937 class_19372;
        if (soldier == null) {
            return;
        }
        UUID machineId = soldier.medievalMachinery$getAssignedMachineId();
        if (machineId != null && (class_19372 = soldier.method_37908()) instanceof class_3218 && (entity = (world = (class_3218)class_19372).method_14190(machineId)) instanceof MedievalMachineryOperable) {
            MedievalMachineryOperable operable = (MedievalMachineryOperable)entity;
            operable.medievalMachinery$onNpcAssignmentCleared((class_1297)soldier);
        }
        NpcMachineAssignmentService.releaseOperatorClaim(soldier);
        soldier.medievalMachinery$clearMachineAssignment();
    }

    public static boolean tryClaimOperator(MedievalSoldierEntity soldier, MedievalMachineryOperable machine) {
        if (!NpcMachineAssignmentService.canAssign(soldier, machine, MachineCrewRole.OPERATOR)) {
            return false;
        }
        if (!soldier.medievalMachinery$isAssignedTo(machine.asEntity().method_5667(), MachineCrewRole.OPERATOR)) {
            return false;
        }
        return machine.medievalMachinery$isOperatedBy(soldier.method_5667()) || machine.medievalMachinery$claim((class_1297)soldier);
    }
}

