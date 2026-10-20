/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_5134
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.item.spear.SpearItem;
import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcMoraleBehaviorPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierCombatPathPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFormationCombatPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMeleeTimingPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMovementPriorityPolicy;
import java.util.EnumSet;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1352;
import net.minecraft.class_5134;

final class RoleAwareMeleeGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;
    private int parityUnseenTicks;

    RoleAwareMeleeGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405, class_1352.class_4134.field_18406));
    }

    public boolean method_6264() {
        if (this.soldier.machineAssignmentOwnsPersonalCombat() || this.soldier.isActivelyMountedRole()) {
            return false;
        }
        class_1309 target = this.soldier.method_5968();
        if (target == null || !target.method_5805()) {
            return false;
        }
        if (MedievalSoldierMovementPriorityPolicy.followOrderOwnsMovement(this.soldier, target)) {
            return false;
        }
        if (this.soldier.goalCommandState().hasExplicitGrace() && this.soldier.method_5858((class_1297)target) > 12.25) {
            return false;
        }
        boolean commanded = this.soldier.isCommandedCombatTarget(target);
        if (!commanded && !this.soldier.method_5985().method_6369((class_1297)target)) {
            return false;
        }
        return this.soldier.usesMeleeCombat() && !this.soldier.isHoldingRangedWeapon() && this.formationAllowsAdvance() && (this.soldier.getCombatStance() != NpcCombatStance.PASSIVE || commanded) && this.soldier.isWithinPatrolCombatLeash(target);
    }

    public void method_6269() {
        this.parityUnseenTicks = 0;
        this.soldier.adaptiveNavigation().reset();
        class_1309 target = this.soldier.method_5968();
        if (target != null) {
            this.soldier.goalCombatMovementState().beginEngagement(target.method_5667(), this.soldier.method_19538());
        }
    }

    public boolean method_6266() {
        if (this.soldier.machineAssignmentOwnsPersonalCombat() || this.soldier.isActivelyMountedRole()) {
            return false;
        }
        class_1309 target = this.soldier.method_5968();
        return target != null && target.method_5805() && !MedievalSoldierMovementPriorityPolicy.followOrderOwnsMovement(this.soldier, target) && this.soldier.usesMeleeCombat() && !this.soldier.isHoldingRangedWeapon() && this.formationAllowsAdvance() && (this.soldier.getCombatStance() != NpcCombatStance.PASSIVE || this.soldier.isCommandedCombatTarget(target)) && this.soldier.isWithinPatrolCombatLeash(target);
    }

    private boolean formationAllowsAdvance() {
        return MedievalSoldierFormationCombatPolicy.mayMeleeAdvance(this.soldier, this.soldier.method_5968());
    }

    private boolean updatePursuitState(class_1309 target) {
        this.parityUnseenTicks = this.soldier.method_5985().method_6369((class_1297)target) ? 0 : ++this.parityUnseenTicks;
        if (!this.shouldStopAutomaticPursuit(target, this.parityUnseenTicks, Math.sqrt(this.soldier.method_5858((class_1297)target)))) {
            return true;
        }
        this.soldier.abandonAutomaticCombatTarget(10);
        return false;
    }

    private boolean shouldStopAutomaticPursuit(class_1309 target, int unseenTicks, double distance) {
        if (target == null || !target.method_5805()) {
            return true;
        }
        if (this.soldier.isCommandedCombatTarget(target)) {
            return false;
        }
        double follow = this.soldier.method_26825(class_5134.field_23717);
        return unseenTicks > 8 || distance > follow * 1.35;
    }

    public void method_6268() {
        double holdDistance;
        class_1309 target = this.soldier.method_5968();
        if (target == null || !this.updatePursuitState(target)) {
            return;
        }
        this.soldier.method_5988().method_6226((class_1297)target, 30.0f, 30.0f);
        double distanceSq = this.soldier.method_5858((class_1297)target);
        double attackReachSq = MedievalSoldierMeleeTimingPolicy.attackReachSq(this.soldier, target);
        double preferredStop = MedievalSoldierMeleeTimingPolicy.preferredStopDistance(this.soldier, target);
        boolean canSee = this.soldier.method_5985().method_6369((class_1297)target);
        if (distanceSq > preferredStop * preferredStop && !MedievalSoldierFormationCombatPolicy.isHeldFormation(this.soldier)) {
            if (!MedievalSoldierCombatPathPolicy.mayPursue(this.soldier, target)) {
                this.soldier.adaptiveNavigation().stop();
                if (!this.soldier.isCommandedCombatTarget(target)) {
                    this.soldier.abandonAutomaticCombatTarget(0);
                }
                return;
            }
            double pursuitSpeed = this.speed * NpcMoraleBehaviorPolicy.pursuitSpeedMultiplier(this.soldier.getMorale(), this.soldier.hasNearbyMoraleLeader());
            this.soldier.adaptiveNavigation().tickToEntity((class_1297)target, pursuitSpeed);
        } else if (MedievalSoldierFormationCombatPolicy.isHeldFormation(this.soldier) || !this.soldier.tryMeleeCombatReposition(target, this.speed)) {
            this.soldier.adaptiveNavigation().stop();
        }
        if (canSee && distanceSq <= attackReachSq) {
            this.soldier.method_6121((class_1297)target);
        }
        if (this.soldier.method_6047().method_7909() instanceof SpearItem && distanceSq <= (holdDistance = MedievalSoldierMeleeTimingPolicy.preferredStopDistance(this.soldier, target)) * holdDistance && distanceSq >= 2.25) {
            this.soldier.adaptiveNavigation().stop();
            this.soldier.method_5988().method_6226((class_1297)target, 30.0f, 30.0f);
        }
    }

    public void method_6270() {
        class_1309 target;
        this.soldier.adaptiveNavigation().stop();
        this.soldier.goalCombatMovementState().clear();
        if (this.soldier.goalCombatState().rangedCloseCombatWeaponActive && ((target = this.soldier.method_5968()) == null || !target.method_5805())) {
            this.soldier.goalCombatState().rangedCloseCombatCalmTicks = Math.max(this.soldier.goalCombatState().rangedCloseCombatCalmTicks, 10);
        }
    }
}

