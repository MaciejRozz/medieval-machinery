/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierFormationCombatPolicy {
    private static final double HELD_RANGED_RETURN_RADIUS = 7.0;
    private static final double HELD_RANGED_SETTLE_RADIUS = 1.35;

    private MedievalSoldierFormationCombatPolicy() {
    }

    static boolean isHeldFormation(MedievalSoldierEntity soldier) {
        if (soldier == null || soldier.getFormation() == NpcFormation.FREE) {
            return false;
        }
        NpcOrderType order = soldier.getOrderType();
        return order == NpcOrderType.HOLD_POSITION || order == NpcOrderType.MOVE_TO_POSITION;
    }

    static boolean mayMeleeAdvance(MedievalSoldierEntity soldier, @Nullable class_1309 target) {
        double targetDistanceSq;
        if (soldier == null || target == null || soldier.shouldUseLooseFormationMovement()) {
            return true;
        }
        if (soldier.getOrderType() == NpcOrderType.MOVE_TO_POSITION && !soldier.isCommandedCombatTarget(target)) {
            return false;
        }
        if (!MedievalSoldierFormationCombatPolicy.isHeldFormation(soldier)) {
            return true;
        }
        double reach = soldier.formationCombatAttackReach(target);
        double releaseDistance = Math.max(2.25, reach * 1.5);
        class_243 anchor = MedievalSoldierFormationCombatPolicy.tacticalAnchor(soldier);
        double d = targetDistanceSq = anchor != null ? anchor.method_1025(target.method_19538()) : soldier.method_5858((class_1297)target);
        if (targetDistanceSq > releaseDistance * releaseDistance) {
            return false;
        }
        return MedievalSoldierFormationCombatPolicy.hasMeleeReleaseLane(soldier, target);
    }

    static boolean mayJoinGroupDefense(MedievalSoldierEntity soldier, @Nullable class_1309 attacker) {
        if (soldier == null || attacker == null) {
            return false;
        }
        if (soldier.getOrderType() == NpcOrderType.MOVE_TO_POSITION && !soldier.isCommandedCombatTarget(attacker)) {
            return false;
        }
        if (!MedievalSoldierFormationCombatPolicy.isHeldFormation(soldier)) {
            return true;
        }
        double reach = soldier.formationCombatAttackReach(attacker);
        double releaseDistance = Math.max(2.25, reach * 1.5);
        class_243 anchor = MedievalSoldierFormationCombatPolicy.tacticalAnchor(soldier);
        double attackerDistanceSq = anchor != null ? anchor.method_1025(attacker.method_19538()) : soldier.method_5858((class_1297)attacker);
        return soldier.isHoldingRangedWeapon() || attackerDistanceSq <= releaseDistance * releaseDistance && MedievalSoldierFormationCombatPolicy.hasMeleeReleaseLane(soldier, attacker);
    }

    static boolean hasMeleeReleaseLane(MedievalSoldierEntity soldier, @Nullable class_1309 target) {
        if (soldier == null || target == null || !MedievalSoldierFormationCombatPolicy.isHeldFormation(soldier)) {
            return true;
        }
        class_243 anchor = MedievalSoldierFormationCombatPolicy.tacticalAnchor(soldier);
        if (anchor == null) {
            return true;
        }
        class_243 toTarget = target.method_19538().method_1020(anchor);
        double length = Math.sqrt(toTarget.field_1352 * toTarget.field_1352 + toTarget.field_1350 * toTarget.field_1350);
        if (length < 0.5) {
            return true;
        }
        double nx = toTarget.field_1352 / length;
        double nz = toTarget.field_1350 / length;
        for (MedievalSoldierEntity ally : soldier.method_37908().method_8390(MedievalSoldierEntity.class, soldier.method_5829().method_1009(5.0, 2.0, 5.0), other -> other != soldier && other.method_5805() && soldier.isFriendlyCombatEntity((class_1309)other) && soldier.isSameCommandGroup((MedievalSoldierEntity)other) && other.getFormation() == soldier.getFormation() && other.usesMeleeCombat())) {
            class_243 rel = ally.method_19538().method_1020(anchor);
            double forward = rel.field_1352 * nx + rel.field_1350 * nz;
            double lateral = Math.abs(rel.field_1352 * -nz + rel.field_1350 * nx);
            if (!(forward > 0.65) || !(forward < 4.5) || !(lateral < 1.25) || ally.method_5968() == null || !ally.method_5968().method_5805()) continue;
            return false;
        }
        return true;
    }

    static boolean shouldHoldRangedPosition(MedievalSoldierEntity soldier) {
        return MedievalSoldierFormationCombatPolicy.isHeldFormation(soldier);
    }

    @Nullable
    static class_243 tacticalAnchor(MedievalSoldierEntity soldier) {
        if (soldier == null) {
            return null;
        }
        class_243 hold = soldier.goalHoldPosition();
        if (hold != null) {
            return hold;
        }
        if (soldier.goalFormationState().getAssemblyTarget() != null) {
            return soldier.goalFormationState().getAssemblyTarget();
        }
        if (soldier.goalCommandDestination() != null) {
            return class_243.method_24955((class_2382)soldier.goalCommandDestination());
        }
        return null;
    }

    static boolean rangedNeedsReturn(MedievalSoldierEntity soldier) {
        class_243 anchor = MedievalSoldierFormationCombatPolicy.tacticalAnchor(soldier);
        return anchor != null && soldier.method_19538().method_1025(anchor) > 49.0;
    }

    static boolean rangedIsSettled(MedievalSoldierEntity soldier) {
        class_243 anchor = MedievalSoldierFormationCombatPolicy.tacticalAnchor(soldier);
        return anchor == null || soldier.method_19538().method_1025(anchor) <= 1.8225000000000002;
    }
}

