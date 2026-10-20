/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierMountedState {
    int attackCooldown;
    int repathCooldown;
    int chargeCooldown;
    int passThroughTicks;
    int meleeWindupTicks;
    int rangedPreparationTicks;
    boolean rangedPreparing;
    boolean rangedCrossbow;
    int orbitSign = 1;
    @Nullable
    class_243 cachedFollowPoint;
    @Nullable
    class_243 cachedOwnerFollowAnchor;
    int followRefreshTicks;
    @Nullable
    class_2338 latchedMoveDestination;
    @Nullable
    class_243 latchedMovePoint;
    @Nullable
    NpcOrderType lastOrder;
    @Nullable
    class_243 latchedPassThroughPoint;
    @Nullable
    class_243 latchedOrbitPoint;
    int moveArrivalSettleTicks;
    @Nullable
    class_243 knightChargeTargetAnchor;
    @Nullable
    class_243 knightChargeDirection;
    int knightChargeCommitTicks;
    int patrolArrivalSettleTicks;
    boolean waitingForPatrolChunk;
    int postPassTurnDelayTicks;
    int rangedSightLossTicks;
    @Nullable
    class_243 knightStagingPoint;
    int knightStagingTicks;
    @Nullable
    class_243 mountedProgressAnchor;
    int mountedNoProgressTicks;
    int recoveryTurnSign = 1;
    @Nullable
    class_243 latchedRecoveryPoint;
    int recoveryTicks;

    MedievalSoldierMountedState() {
    }

    void tick() {
        if (this.followRefreshTicks > 0) {
            --this.followRefreshTicks;
        }
        if (this.passThroughTicks > 0) {
            --this.passThroughTicks;
        }
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
        if (this.repathCooldown > 0) {
            --this.repathCooldown;
        }
        if (this.chargeCooldown > 0) {
            --this.chargeCooldown;
        }
        if (this.meleeWindupTicks > 0) {
            --this.meleeWindupTicks;
        }
        if (this.mountedNoProgressTicks > 0) {
            --this.mountedNoProgressTicks;
        }
        if (this.recoveryTicks > 0) {
            --this.recoveryTicks;
        }
        if (this.knightStagingPoint != null) {
            ++this.knightStagingTicks;
        }
        if (this.knightChargeCommitTicks > 0) {
            --this.knightChargeCommitTicks;
        }
        if (this.postPassTurnDelayTicks > 0) {
            --this.postPassTurnDelayTicks;
        }
    }

    void onOrder(NpcOrderType order) {
        if (order == this.lastOrder) {
            return;
        }
        this.lastOrder = order;
        this.cachedFollowPoint = null;
        this.cachedOwnerFollowAnchor = null;
        this.followRefreshTicks = 0;
        this.latchedMoveDestination = null;
        this.latchedMovePoint = null;
        this.passThroughTicks = 0;
        this.latchedPassThroughPoint = null;
        this.latchedOrbitPoint = null;
        this.moveArrivalSettleTicks = 0;
        this.knightChargeTargetAnchor = null;
        this.knightChargeDirection = null;
        this.knightChargeCommitTicks = 0;
        this.patrolArrivalSettleTicks = 0;
        this.waitingForPatrolChunk = false;
        this.postPassTurnDelayTicks = 0;
        this.rangedSightLossTicks = 0;
        this.chargeCooldown = 0;
        this.knightStagingPoint = null;
        this.knightStagingTicks = 0;
        this.mountedProgressAnchor = null;
        this.mountedNoProgressTicks = 0;
        this.latchedRecoveryPoint = null;
        this.recoveryTicks = 0;
    }

    void resetCombat() {
        this.attackCooldown = 0;
        this.chargeCooldown = 0;
        this.meleeWindupTicks = 0;
        this.passThroughTicks = 0;
        this.latchedPassThroughPoint = null;
        this.latchedOrbitPoint = null;
        this.knightChargeTargetAnchor = null;
        this.knightChargeDirection = null;
        this.knightChargeCommitTicks = 0;
        this.postPassTurnDelayTicks = 0;
        this.rangedSightLossTicks = 0;
        this.knightStagingPoint = null;
        this.knightStagingTicks = 0;
        this.mountedProgressAnchor = null;
        this.mountedNoProgressTicks = 0;
        this.latchedRecoveryPoint = null;
        this.recoveryTicks = 0;
        this.rangedPreparationTicks = 0;
        this.rangedPreparing = false;
        this.rangedCrossbow = false;
    }

    void clearMovementRecovery() {
        this.mountedProgressAnchor = null;
        this.mountedNoProgressTicks = 0;
        this.latchedRecoveryPoint = null;
        this.recoveryTicks = 0;
        this.patrolArrivalSettleTicks = 0;
    }

    void cancelRangedPreparation(MedievalSoldierEntity soldier) {
        if (soldier != null) {
            soldier.stopRangedWeaponUse();
            soldier.clearRangedVisual();
        }
        this.finishRangedPreparation();
    }

    void finishRangedPreparation() {
        this.rangedPreparationTicks = 0;
        this.rangedPreparing = false;
        this.rangedCrossbow = false;
    }
}

