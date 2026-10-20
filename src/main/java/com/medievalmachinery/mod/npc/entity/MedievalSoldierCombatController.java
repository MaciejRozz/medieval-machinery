/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1819
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_5134
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierGroupCombatCoordinator;
import com.medievalmachinery.mod.npc.organization.CastleLocalAggressionService;
import com.medievalmachinery.mod.npc.organization.LocalAggressionManager;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1819;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_5134;

final class MedievalSoldierCombatController {
    MedievalSoldierCombatController() {
    }

    int getMeleeAttackCooldown(MedievalSoldierEntity soldier) {
        double attackSpeed = Math.max(0.25, soldier.method_26825(class_5134.field_23723));
        return Math.max(8, (int)Math.round(20.0 / attackSpeed) + 7);
    }

    boolean isFacingCombatTarget(MedievalSoldierEntity soldier, class_1309 target) {
        class_243 toTarget = target.method_33571().method_1020(soldier.method_33571());
        if (toTarget.method_1027() < 1.0E-4) {
            return true;
        }
        class_243 look = soldier.method_5828(1.0f);
        return look.method_1029().method_1026(toTarget.method_1029()) >= 0.35;
    }

    boolean hasUsableShield(MedievalSoldierEntity soldier) {
        return soldier.method_6079().method_7909() instanceof class_1819 && !soldier.method_6079().method_7960();
    }

    float getLevelRangedInaccuracy(MedievalSoldierEntity soldier) {
        return Math.max(1.1f, 2.0f - (float)Math.max(0, Math.min(10, soldier.getNpcLevel()) - 1) * 0.1f);
    }

    void tickServerAwareness(MedievalSoldierEntity soldier) {
        class_1309 finishedCombatTarget;
        class_1309 activeCombatTarget;
        if (soldier.hasSupplyMovementOverride()) {
            if (soldier.method_5968() != null && !soldier.isCommandedCombatTarget(soldier.method_5968())) {
                soldier.method_5980(null);
                soldier.combatTargetStateForController().resetSight();
            }
            return;
        }
        if (soldier.machineAssignmentOwnsPersonalCombat()) {
            if (soldier.method_5968() != null) {
                soldier.method_5980(null);
            }
            soldier.combatStateForController().clearCommandedTarget();
            soldier.combatTargetStateForController().resetSight();
            return;
        }
        if (soldier.isStaggeredAiTick(5)) {
            class_1309 defensiveThreat = null;
            if (!soldier.isCommandedCombatTarget(soldier.method_5968()) && soldier.getCombatStance() != NpcCombatStance.PASSIVE) {
                defensiveThreat = soldier.nearbyOwnerThreat(24.0);
                if (defensiveThreat == null) {
                    defensiveThreat = soldier.nearbyLeaderThreat(20.0);
                }
                if (defensiveThreat == null && soldier.goalCombatMovementState().mayRebalanceTarget()) {
                    defensiveThreat = soldier.nearbyEndangeredAllyThreat(14.0);
                }
            }
            if (defensiveThreat != null && soldier.method_5968() != defensiveThreat) {
                class_1309 coordinated = MedievalSoldierGroupCombatCoordinator.coordinatedTarget(soldier, defensiveThreat, true);
                soldier.method_5980(coordinated != null ? coordinated : defensiveThreat);
                soldier.stopAdaptiveNavigation();
                soldier.goalCombatMovementState().markTargetRebalanced(30);
            }
        }
        if (soldier.isStaggeredAiTick(10)) {
            soldier.reconcileDiplomacyAndLocalAggressionTarget();
        }
        if ((activeCombatTarget = soldier.method_5968()) instanceof class_3222) {
            class_3222 localAggressor = (class_3222)activeCombatTarget;
            class_1937 class_19372 = soldier.method_37908();
            if (class_19372 instanceof class_3218) {
                class_3218 serverWorld = (class_3218)class_19372;
                if (soldier.method_5985().method_6369((class_1297)localAggressor)) {
                    LocalAggressionManager.refreshIfActive(serverWorld, localAggressor.method_5667(), soldier.getNpcIdentity().getCastleId(), soldier.getNpcIdentity().getKingdomId());
                    if (soldier.isStaggeredAiTick(20)) {
                        CastleLocalAggressionService.propagate(serverWorld, soldier, localAggressor, 12.0);
                    }
                }
            }
        }
        if (activeCombatTarget != null && activeCombatTarget.method_5805() && !soldier.isCommandedCombatTarget(activeCombatTarget) && soldier.goalCombatMovementState().mayRebalanceTarget() && soldier.isStaggeredAiTick(5)) {
            class_1309 rebalanced = MedievalSoldierGroupCombatCoordinator.rebalanceTarget(soldier, activeCombatTarget);
            if (rebalanced != null && rebalanced != activeCombatTarget) {
                soldier.method_5980(rebalanced);
                soldier.goalCombatMovementState().markTargetRebalanced(30 + soldier.method_6051().method_43048(21));
                soldier.stopAdaptiveNavigation();
            } else {
                soldier.goalCombatMovementState().markTargetRebalanced(20);
            }
        }
        if ((finishedCombatTarget = soldier.method_5968()) != null && (!finishedCombatTarget.method_5805() || finishedCombatTarget.method_31481())) {
            boolean wasCommanded = soldier.isCommandedCombatTarget(finishedCombatTarget);
            soldier.method_5980(null);
            soldier.combatStateForController().clearCommandedTarget();
            soldier.combatTargetStateForController().resetSight();
            class_1309 next = null;
            if (!wasCommanded) {
                class_1309 defensive = soldier.immediateDefensiveThreat();
                class_1309 class_13092 = next = defensive != null ? MedievalSoldierGroupCombatCoordinator.coordinatedTarget(soldier, defensive, true) : MedievalSoldierGroupCombatCoordinator.coordinatedTarget(soldier, finishedCombatTarget, true);
            }
            if (next != null && next.method_5805() && next != finishedCombatTarget) {
                soldier.method_5980(next);
                soldier.goalCombatMovementState().markTargetRebalanced(12 + soldier.method_6051().method_43048(9));
            } else {
                soldier.formationCombatFinished();
                soldier.resumeSuspendedOrderIfPossible();
                soldier.goalCombatMovementState().markTargetRebalanced(10);
            }
        }
    }

    void tickTargetLifecycle(MedievalSoldierEntity soldier) {
        double allowedDistanceSq;
        class_1309 combatTarget = soldier.method_5968();
        if (combatTarget == null) {
            soldier.combatTargetStateForController().resetSight();
            return;
        }
        double followRange = soldier.method_26825(class_5134.field_23717);
        double maxTargetDistanceSq = followRange * followRange * 2.25;
        boolean patrolLeashOk = soldier.getOrderType() != NpcOrderType.PATROL || soldier.isWithinPatrolCombatLeash(combatTarget);
        boolean commanded = soldier.isCommandedCombatTarget(combatTarget);
        int lostSightGrace = commanded ? 100 : 30;
        double d = allowedDistanceSq = commanded ? maxTargetDistanceSq * 1.5 : maxTargetDistanceSq;
        if (soldier.combatTargetStateForController().shouldForget(combatTarget, soldier.method_5985().method_6369((class_1297)combatTarget), soldier.method_5858((class_1297)combatTarget), allowedDistanceSq, patrolLeashOk, lostSightGrace)) {
            if (commanded) {
                soldier.combatStateForController().clearCommandedTarget();
            }
            soldier.method_5980(null);
            soldier.combatTargetStateForController().resetSight();
            soldier.formationCombatFinished();
            soldier.resumeSuspendedOrderIfPossible();
        }
    }
}

