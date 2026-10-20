/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFormationCombatPolicy;
import net.minecraft.class_1297;
import net.minecraft.class_1309;

final class MedievalSoldierMovementPriorityPolicy {
    private MedievalSoldierMovementPriorityPolicy() {
    }

    static boolean mayRunFootOrder(MedievalSoldierEntity soldier, NpcOrderType order) {
        if (soldier == null || order == null) {
            return false;
        }
        if (soldier.isGeneralBodyguard()) {
            return false;
        }
        if (soldier.isInventoryGuiOpen()) {
            return false;
        }
        if (soldier.hasSupplyMovementOverride()) {
            return false;
        }
        if (soldier.hasPendingMountCommand()) {
            return false;
        }
        if (soldier.method_5765()) {
            return false;
        }
        if (soldier.getOrderType() != order) {
            return false;
        }
        return !soldier.medievalMachinery$hasMachineAssignment();
    }

    static void tickJumpAssist(MedievalSoldierEntity soldier) {
        if (soldier != null && soldier.field_5976 && soldier.method_24828() && !soldier.isOnClimbableSurface() && !soldier.method_5942().method_6357()) {
            soldier.method_5993().method_6233();
        }
    }

    static boolean combatBlocksHoldReturn(MedievalSoldierEntity soldier) {
        if (soldier == null) {
            return false;
        }
        class_1309 target = soldier.method_5968();
        return target != null && target.method_5805() && MedievalSoldierFormationCombatPolicy.mayMeleeAdvance(soldier, target);
    }

    static boolean combatBlocksFollow(MedievalSoldierEntity soldier) {
        if (soldier == null) {
            return false;
        }
        class_1309 target = soldier.method_5968();
        return target != null && target.method_5805();
    }

    static boolean followOrderOwnsMovement(MedievalSoldierEntity soldier, class_1309 target) {
        if (soldier == null || soldier.getOrderType() != NpcOrderType.FOLLOW) {
            return false;
        }
        if (target == null || !target.method_5805()) {
            return true;
        }
        if (soldier.isCommandedCombatTarget(target)) {
            return false;
        }
        return soldier.method_5858((class_1297)target) > 12.25;
    }
}

