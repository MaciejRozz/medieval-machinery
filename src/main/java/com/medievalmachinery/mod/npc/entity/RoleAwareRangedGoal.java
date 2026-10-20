/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_1764
 *  net.minecraft.class_243
 *  net.minecraft.class_3532
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.MedievalMachineryCore;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcMoraleBehaviorPolicy;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRangedVisualState;
import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierCombatPathPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFormationCombatPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMovementPriorityPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierRangedFirePolicy;
import java.util.EnumSet;
import java.util.Locale;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1352;
import net.minecraft.class_1764;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import org.jetbrains.annotations.Nullable;

final class RoleAwareRangedGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;
    private final float maxRange;
    private int attackCooldown;
    private int unseenTicks;
    private int visibleTicks;
    private int preparationTicks;
    private int aimTicks;
    private boolean preparingShot;
    private boolean crossbowShot;
    @Nullable
    private class_243 lastSeenTargetPos;
    private long nextPatrolDebugTick;

    private double minimumRange() {
        return 5.0;
    }

    RoleAwareRangedGoal(MedievalSoldierEntity soldier, double speed, float maxRange) {
        this.soldier = soldier;
        this.speed = speed;
        this.maxRange = maxRange;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405, class_1352.class_4134.field_18406));
    }

    public boolean method_6264() {
        if (this.soldier.machineAssignmentOwnsPersonalCombat() || this.soldier.isActivelyMountedRole()) {
            return false;
        }
        class_1309 commandGraceTarget = this.soldier.method_5968();
        if (this.soldier.goalCommandState().hasExplicitGrace() && commandGraceTarget != null && this.soldier.method_5858((class_1297)commandGraceTarget) > 36.0) {
            return false;
        }
        class_1309 target = this.soldier.method_5968();
        if (target == null || !target.method_5805()) {
            return false;
        }
        if (this.soldier.getOrderType() == NpcOrderType.PATROL && MedievalMachineryConfig.get().rangedCombatDebugEnabled) {
            this.debugPatrolStartGate(target);
        }
        if (MedievalSoldierMovementPriorityPolicy.followOrderOwnsMovement(this.soldier, target)) {
            return false;
        }
        boolean commandedTarget = this.soldier.isCommandedCombatTarget(target);
        if (!this.soldier.isWithinPatrolCombatLeash(target)) {
            return false;
        }
        return (this.soldier.getCombatStance() != NpcCombatStance.PASSIVE || commandedTarget) && this.soldier.getShieldMode() != NpcShieldMode.RAISED && !this.soldier.isUsingShield() && !this.soldier.isHoldFire() && this.soldier.isHoldingRangedWeapon();
    }

    public boolean method_6266() {
        if (this.soldier.machineAssignmentOwnsPersonalCombat() || this.soldier.isActivelyMountedRole()) {
            return false;
        }
        class_1309 target = this.soldier.method_5968();
        if (MedievalSoldierMovementPriorityPolicy.followOrderOwnsMovement(this.soldier, target)) {
            return false;
        }
        return (this.soldier.getCombatStance() != NpcCombatStance.PASSIVE || this.soldier.isCommandedCombatTarget(target)) && this.soldier.getShieldMode() != NpcShieldMode.RAISED && !this.soldier.isUsingShield() && !this.soldier.isHoldFire() && this.soldier.isHoldingRangedWeapon() && target != null && target.method_5805() && this.soldier.isWithinPatrolCombatLeash(target);
    }

    public void method_6269() {
        class_1309 startTarget;
        this.soldier.adaptiveNavigation().reset();
        this.attackCooldown = 0;
        this.unseenTicks = 0;
        this.visibleTicks = 0;
        if (this.soldier.goalCombatState().rangedCloseCombatWeaponActive && this.soldier.isHoldingRangedWeapon()) {
            this.soldier.goalCombatState().rangedCloseCombatWeaponActive = false;
            this.soldier.goalCombatState().rangedCloseCombatCalmTicks = 0;
        }
        if ((startTarget = this.soldier.method_5968()) != null) {
            this.soldier.goalCombatMovementState().beginEngagement(startTarget.method_5667(), this.soldier.method_19538());
        }
        this.preparationTicks = NpcMoraleBehaviorPolicy.rangedPreparationDelayTicks(this.soldier.getMorale(), this.soldier.hasNearbyMoraleLeader());
        this.aimTicks = 0;
        this.preparingShot = false;
        this.crossbowShot = false;
        this.lastSeenTargetPos = startTarget == null ? null : startTarget.method_19538();
        this.soldier.npcDebug().event("RANGED_START", startTarget == null ? null : startTarget.method_24515(), "target=" + String.valueOf(startTarget == null ? "none" : Integer.valueOf(startTarget.method_5628())));
    }

    public void method_6268() {
        class_1309 target = this.soldier.method_5968();
        if (target == null) {
            return;
        }
        double distanceSq = this.soldier.method_5858((class_1297)target);
        double rangeSq = this.maxRange * this.maxRange;
        boolean canSee = this.soldier.method_5985().method_6369((class_1297)target);
        if (canSee) {
            this.unseenTicks = 0;
            ++this.visibleTicks;
            this.lastSeenTargetPos = target.method_19538();
        } else {
            ++this.unseenTicks;
            this.visibleTicks = 0;
        }
        boolean commandedTarget = this.soldier.isCommandedCombatTarget(target);
        if (!commandedTarget && this.unseenTicks >= MedievalSoldierRangedFirePolicy.automaticLostSightTicks()) {
            this.cancelPreparation();
            this.soldier.abandonAutomaticCombatTarget(10);
            return;
        }
        this.soldier.method_5988().method_6226((class_1297)target, 30.0f, 30.0f);
        if (this.preparingShot) {
            boolean aimingPhase;
            this.soldier.adaptiveNavigation().stop();
            boolean bl = aimingPhase = this.crossbowShot && this.aimTicks > 0;
            if (MedievalSoldierRangedFirePolicy.shouldCancelPreparation(canSee, this.unseenTicks, aimingPhase, commandedTarget)) {
                this.cancelPreparation();
                this.attackCooldown = 8;
                return;
            }
            this.tickPreparation(target);
            return;
        }
        double minimumRange = this.minimumRange();
        boolean closeThreat = this.soldier.isTrueCloseRangedThreat(target, minimumRange);
        if (this.soldier.getOrderType() == NpcOrderType.PATROL && this.soldier.hasPatrolRoute()) {
            this.soldier.method_5728(false);
            this.soldier.adaptiveNavigation().stop();
            if (this.attackCooldown > 0) {
                --this.attackCooldown;
            }
            if (canSee && distanceSq <= rangeSq && this.attackCooldown <= 0) {
                if (closeThreat) {
                    if (distanceSq <= 6.25) {
                        this.soldier.method_6121((class_1297)target);
                    }
                    this.attackCooldown = 12;
                } else if (this.soldier.hasFriendlyInLineOfFire(target)) {
                    this.attackCooldown = MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(this.soldier);
                } else if (MedievalSoldierRangedFirePolicy.mayBeginShot(this.soldier, this.visibleTicks)) {
                    this.beginPreparation();
                }
            }
            return;
        }
        if (closeThreat && this.soldier.switchRangedUnitToCloseCombatWeapon()) {
            this.soldier.method_5728(false);
            this.soldier.adaptiveNavigation().stop();
            this.cancelPreparation();
            return;
        }
        boolean holdFormationPosition = MedievalSoldierFormationCombatPolicy.shouldHoldRangedPosition(this.soldier);
        if (holdFormationPosition) {
            class_243 anchor = MedievalSoldierFormationCombatPolicy.tacticalAnchor(this.soldier);
            this.soldier.method_5728(false);
            if (anchor != null && MedievalSoldierFormationCombatPolicy.rangedNeedsReturn(this.soldier)) {
                this.soldier.adaptiveNavigation().tickToPoint(anchor, this.speed);
            } else {
                this.soldier.adaptiveNavigation().stop();
            }
            if (this.attackCooldown > 0) {
                --this.attackCooldown;
            }
            if (canSee && distanceSq <= rangeSq && this.attackCooldown <= 0) {
                if (closeThreat) {
                    this.soldier.method_5988().method_6226((class_1297)target, 30.0f, 30.0f);
                    if (distanceSq <= 6.25) {
                        this.soldier.method_6121((class_1297)target);
                    }
                    this.attackCooldown = 12;
                } else if (this.soldier.hasFriendlyInLineOfFire(target)) {
                    this.attackCooldown = MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(this.soldier);
                } else if (MedievalSoldierFormationCombatPolicy.rangedIsSettled(this.soldier) && MedievalSoldierRangedFirePolicy.mayBeginShot(this.soldier, this.visibleTicks)) {
                    this.beginPreparation();
                }
            }
            return;
        }
        if (distanceSq > rangeSq) {
            this.soldier.method_5728(false);
            if (!MedievalSoldierCombatPathPolicy.mayPursue(this.soldier, target)) {
                this.soldier.adaptiveNavigation().stop();
                if (!this.soldier.isCommandedCombatTarget(target)) {
                    this.soldier.abandonAutomaticCombatTarget(0);
                }
                return;
            }
            this.soldier.adaptiveNavigation().tickToEntity((class_1297)target, this.speed);
        } else if (closeThreat) {
            if (!this.soldier.retreatForRangedCombat(target, Math.max(1.35, this.speed * 1.2), minimumRange + 1.5)) {
                this.soldier.method_5728(false);
                this.soldier.adaptiveNavigation().stop();
            }
        } else if (this.unseenTicks > 5) {
            this.soldier.method_5728(false);
            if (this.unseenTicks <= 35 && this.lastSeenTargetPos != null && this.soldier.method_19538().method_1025(this.lastSeenTargetPos) > 4.0) {
                this.soldier.adaptiveNavigation().tickToPoint(this.lastSeenTargetPos, this.speed);
            } else {
                this.soldier.sidestepForClearShot(target, this.speed);
            }
        } else {
            this.soldier.method_5728(false);
            this.soldier.adaptiveNavigation().stop();
        }
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
        if (canSee && distanceSq <= rangeSq && this.attackCooldown <= 0) {
            if (closeThreat) {
                boolean escaped = this.soldier.retreatForRangedCombat(target, Math.max(1.35, this.speed * 1.2), minimumRange + 1.5);
                if (escaped) {
                    this.attackCooldown = 4;
                } else if (this.soldier.canShootCloseTargetFromHeight(target)) {
                    this.beginPreparation();
                } else {
                    this.soldier.method_5988().method_6226((class_1297)target, 30.0f, 30.0f);
                    if (this.soldier.method_5858((class_1297)target) <= 6.25) {
                        this.soldier.method_6121((class_1297)target);
                    }
                    this.attackCooldown = 12;
                }
            } else if (this.soldier.hasFriendlyInLineOfFire(target)) {
                this.soldier.sidestepForClearShot(target, this.speed);
                this.attackCooldown = MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(this.soldier);
            } else if (MedievalSoldierRangedFirePolicy.mayBeginShot(this.soldier, this.visibleTicks)) {
                this.beginPreparation();
            }
        }
    }

    private void beginPreparation() {
        this.preparingShot = true;
        this.preparationTicks = NpcMoraleBehaviorPolicy.rangedPreparationDelayTicks(this.soldier.getMorale(), this.soldier.hasNearbyMoraleLeader());
        this.aimTicks = 0;
        this.crossbowShot = this.soldier.method_6047().method_7909() instanceof class_1764;
        if (this.crossbowShot) {
            this.soldier.beginRangedVisual(NpcRangedVisualState.LOAD, MedievalSoldierRangedFirePolicy.crossbowLoadTicks(false) + MedievalSoldierRangedFirePolicy.crossbowChargeTicks(false) + MedievalSoldierRangedFirePolicy.crossbowAimTicks(false) + MedievalSoldierRangedFirePolicy.releaseHoldTicks(true, false));
        } else {
            this.soldier.beginRangedVisual(NpcRangedVisualState.PREPARE, MedievalSoldierRangedFirePolicy.bowDrawTicks(false) + MedievalSoldierRangedFirePolicy.releaseHoldTicks(false, false));
            this.soldier.method_6019(class_1268.field_5808);
        }
    }

    private void tickPreparation(class_1309 target) {
        ++this.preparationTicks;
        if (this.crossbowShot) {
            int loadTicks = MedievalSoldierRangedFirePolicy.crossbowLoadTicks(false);
            int chargeDone = loadTicks + MedievalSoldierRangedFirePolicy.crossbowChargeTicks(false);
            if (this.preparationTicks == loadTicks) {
                this.soldier.markCrossbowBoltInsertedForVisual();
                this.soldier.setRangedVisualState(NpcRangedVisualState.PREPARE);
                this.soldier.method_6019(class_1268.field_5808);
            }
            if (this.preparationTicks < chargeDone) {
                return;
            }
            if (this.soldier.method_6115()) {
                this.soldier.method_6075();
            }
            if (this.aimTicks == 0) {
                this.soldier.setRangedVisualState(NpcRangedVisualState.AIM);
            }
            ++this.aimTicks;
            if (this.aimTicks < MedievalSoldierRangedFirePolicy.crossbowAimTicks(false)) {
                return;
            }
            if (this.soldier.hasFriendlyInLineOfFire(target)) {
                this.cancelPreparation();
                if (!MedievalSoldierFormationCombatPolicy.shouldHoldRangedPosition(this.soldier)) {
                    this.soldier.sidestepForClearShot(target, this.speed);
                }
                this.attackCooldown = MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(this.soldier);
                return;
            }
            this.soldier.holdRangedReleaseVisual(true);
            this.soldier.fireStandardArrowAt(target);
            this.soldier.markCrossbowUnloadedAfterShot();
            this.finishShot(this.soldier.applyLeadershipRangedCooldown(MedievalSoldierRangedFirePolicy.postShotCooldown(true, false)), true);
            return;
        }
        if (this.preparationTicks < MedievalSoldierRangedFirePolicy.bowDrawTicks(false)) {
            return;
        }
        this.soldier.stopRangedWeaponUse();
        if (this.soldier.hasFriendlyInLineOfFire(target)) {
            this.cancelPreparation();
            if (!MedievalSoldierFormationCombatPolicy.shouldHoldRangedPosition(this.soldier)) {
                this.soldier.sidestepForClearShot(target, this.speed);
            }
            this.attackCooldown = MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(this.soldier);
            return;
        }
        this.soldier.holdRangedReleaseVisual(false);
        this.soldier.fireStandardArrowAt(target);
        this.soldier.markCrossbowUnloadedAfterShot();
        double distance = Math.sqrt(this.soldier.method_5858((class_1297)target));
        int rangedCooldown = class_3532.method_15386((float)(class_3532.method_15363((float)((float)(distance / (double)this.maxRange)), (float)0.0f, (float)1.0f) * 10.0f + 10.0f));
        this.finishShot(this.soldier.applyLevelRangedCooldown(rangedCooldown), false);
    }

    private void finishShot(int cooldown, boolean crossbow) {
        this.soldier.stopRangedWeaponUse();
        this.preparingShot = false;
        this.preparationTicks = 0;
        this.aimTicks = 0;
        this.attackCooldown = cooldown;
    }

    private void cancelPreparation() {
        this.soldier.stopRangedWeaponUse();
        this.soldier.clearRangedVisual();
        this.preparingShot = false;
        this.preparationTicks = 0;
        this.aimTicks = 0;
    }

    private void stopWithoutCancellingCompletedRelease() {
        this.soldier.stopRangedWeaponUse();
        if (this.preparingShot) {
            this.soldier.clearRangedVisual();
        }
        this.preparingShot = false;
        this.preparationTicks = 0;
        this.aimTicks = 0;
    }

    private void debugPatrolStartGate(class_1309 target) {
        long now = this.soldier.method_37908().method_8510();
        if (now < this.nextPatrolDebugTick) {
            return;
        }
        this.nextPatrolDebugTick = now + 20L;
        MedievalMachineryCore.LOGGER.info("[RANGED-PATROL] npc={} role={} target={} alive={} stance={} rangedWeapon={} holdFire={} shieldMode={} usingShield={} leash={} machineOwns={} mounted={} followOwnsMove={} distance={}", new Object[]{this.soldier.method_5628(), this.soldier.getNpcRole(), target.method_5628(), target.method_5805(), this.soldier.getCombatStance(), this.soldier.isHoldingRangedWeapon(), this.soldier.isHoldFire(), this.soldier.getShieldMode(), this.soldier.isUsingShield(), this.soldier.isWithinPatrolCombatLeash(target), this.soldier.machineAssignmentOwnsPersonalCombat(), this.soldier.isActivelyMountedRole(), MedievalSoldierMovementPriorityPolicy.followOrderOwnsMovement(this.soldier, target), String.format(Locale.ROOT, "%.2f", Math.sqrt(this.soldier.method_5858((class_1297)target)))});
    }

    public void method_6270() {
        class_1309 debugTarget = this.soldier.method_5968();
        this.soldier.npcDebug().event("RANGED_STOP", debugTarget == null ? null : debugTarget.method_24515(), "target=" + String.valueOf(debugTarget == null ? "none" : Integer.valueOf(debugTarget.method_5628())));
        this.soldier.method_5728(false);
        this.soldier.adaptiveNavigation().stop();
        this.stopWithoutCancellingCompletedRelease();
        this.unseenTicks = 0;
        this.visibleTicks = 0;
        this.lastSeenTargetPos = null;
        this.soldier.goalCombatMovementState().clearRepositioning();
    }
}

