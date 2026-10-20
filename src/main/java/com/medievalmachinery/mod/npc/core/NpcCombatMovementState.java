/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 */
package com.medievalmachinery.mod.npc.core;

import java.util.UUID;
import net.minecraft.class_243;

public final class NpcCombatMovementState {
    private int repositionCooldown;
    private int targetRebalanceCooldown;
    private int preferredSide;
    private class_243 engagementAnchor;
    private UUID engagementTargetId;
    private class_243 firingLane;
    private UUID firingLaneTargetId;
    private class_243 patrolCombatAnchor;
    private int patrolCombatIdleTicks;

    public void tick() {
        if (this.repositionCooldown > 0) {
            --this.repositionCooldown;
        }
        if (this.targetRebalanceCooldown > 0) {
            --this.targetRebalanceCooldown;
        }
    }

    public class_243 patrolCombatAnchor(class_243 currentPosition) {
        if (this.patrolCombatAnchor == null && currentPosition != null) {
            this.patrolCombatAnchor = currentPosition;
        }
        this.patrolCombatIdleTicks = 0;
        return this.patrolCombatAnchor;
    }

    public void tickPatrolCombatAnchor(boolean patrolActive, boolean hasLiveTarget) {
        if (!patrolActive) {
            this.clearPatrolCombatAnchor();
            return;
        }
        if (hasLiveTarget) {
            this.patrolCombatIdleTicks = 0;
        } else if (this.patrolCombatAnchor != null && ++this.patrolCombatIdleTicks > 20) {
            this.clearPatrolCombatAnchor();
        }
    }

    public void clearPatrolCombatAnchor() {
        this.patrolCombatAnchor = null;
        this.patrolCombatIdleTicks = 0;
    }

    public boolean mayRebalanceTarget() {
        return this.targetRebalanceCooldown <= 0;
    }

    public void markTargetRebalanced(int cooldownTicks) {
        this.targetRebalanceCooldown = Math.max(this.targetRebalanceCooldown, Math.max(0, cooldownTicks));
    }

    public boolean mayReposition() {
        return this.repositionCooldown <= 0;
    }

    public void beginEngagement(UUID targetId, class_243 position) {
        if (targetId == null || position == null) {
            return;
        }
        if (!targetId.equals(this.engagementTargetId) || this.engagementAnchor == null) {
            this.engagementTargetId = targetId;
            this.engagementAnchor = position;
        }
    }

    public class_243 engagementAnchor() {
        return this.engagementAnchor;
    }

    public boolean withinEngagementLeash(class_243 candidate, double maxDistance) {
        return this.engagementAnchor == null || candidate == null || this.engagementAnchor.method_1025(candidate) <= maxDistance * maxDistance;
    }

    public void markRepositioned(int cooldownTicks) {
        this.repositionCooldown = Math.max(this.repositionCooldown, Math.max(0, cooldownTicks));
    }

    public class_243 firingLane(UUID targetId) {
        return targetId != null && targetId.equals(this.firingLaneTargetId) ? this.firingLane : null;
    }

    public void rememberFiringLane(UUID targetId, class_243 lane) {
        this.firingLaneTargetId = targetId;
        this.firingLane = lane;
    }

    public void clearFiringLane() {
        this.firingLaneTargetId = null;
        this.firingLane = null;
    }

    public int preferredSide(int stableHash) {
        if (this.preferredSide == 0) {
            this.preferredSide = (stableHash & 1) == 0 ? 1 : -1;
        }
        return this.preferredSide;
    }

    public void clearRepositioning() {
        this.repositionCooldown = 0;
        this.clearFiringLane();
    }

    public void clear() {
        this.repositionCooldown = 0;
        this.targetRebalanceCooldown = 0;
        this.engagementAnchor = null;
        this.engagementTargetId = null;
        this.clearFiringLane();
    }
}

