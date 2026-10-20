/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFollowPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMovementPriorityPolicy;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.class_1352;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3218;

final class FollowOwnerGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;
    private final float stopDistance;
    private final float startDistance;
    private class_1657 owner;
    private class_243 cachedFollowPoint;
    private int followPointRefreshTicks;

    FollowOwnerGoal(MedievalSoldierEntity soldier, double speed, float stopDistance, float startDistance) {
        this.soldier = soldier;
        this.speed = speed;
        this.stopDistance = stopDistance;
        this.startDistance = startDistance;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405, class_1352.class_4134.field_18406));
    }

    public boolean method_6264() {
        class_1937 class_19372;
        if (!MedievalSoldierMovementPriorityPolicy.mayRunFootOrder(this.soldier, NpcOrderType.FOLLOW) || !((class_19372 = this.soldier.method_37908()) instanceof class_3218)) {
            return false;
        }
        class_3218 world = (class_3218)class_19372;
        UUID ownerId = this.soldier.getNpcIdentity().getOwnerId();
        if (ownerId == null) {
            return false;
        }
        this.owner = world.method_18470(ownerId);
        if (this.owner == null || !this.owner.method_5805() || this.owner.method_7325()) {
            return false;
        }
        if (!MedievalSoldierMovementPriorityPolicy.followOrderOwnsMovement(this.soldier, this.soldier.method_5968())) {
            return false;
        }
        class_243 desired = MedievalSoldierFollowPolicy.desiredPoint(this.soldier, this.owner);
        double threshold = MedievalSoldierFollowPolicy.startDistance(this.soldier);
        double followDistance = MedievalSoldierFollowPolicy.hysteresisDistance(this.soldier, this.owner, desired);
        return followDistance > threshold;
    }

    public boolean method_6266() {
        if (!MedievalSoldierMovementPriorityPolicy.mayRunFootOrder(this.soldier, NpcOrderType.FOLLOW) || !MedievalSoldierMovementPriorityPolicy.followOrderOwnsMovement(this.soldier, this.soldier.method_5968()) || this.owner == null || !this.owner.method_5805() || this.owner.method_7325()) {
            return false;
        }
        if (this.soldier.adaptiveNavigation().isLadderRouteActive()) {
            return true;
        }
        return MedievalSoldierFollowPolicy.hysteresisDistance(this.soldier, this.owner, MedievalSoldierFollowPolicy.desiredPoint(this.soldier, this.owner)) > MedievalSoldierFollowPolicy.stopDistance(this.soldier);
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
        this.cachedFollowPoint = this.owner == null ? null : MedievalSoldierFollowPolicy.desiredPoint(this.soldier, this.owner);
        int cadence = Math.max(1, MedievalSoldierFollowPolicy.repathTicks(this.soldier));
        this.followPointRefreshTicks = Math.floorMod(this.soldier.method_5667().hashCode(), cadence);
    }

    public void method_6268() {
        if (this.owner == null) {
            return;
        }
        MedievalSoldierFollowPolicy.updateLook(this.soldier, this.owner);
        if (this.cachedFollowPoint == null || this.followPointRefreshTicks-- <= 0 || this.soldier.method_5942().method_6357() && this.soldier.method_19538().method_1025(this.cachedFollowPoint) > MedievalSoldierFollowPolicy.stopDistance(this.soldier) * MedievalSoldierFollowPolicy.stopDistance(this.soldier)) {
            this.cachedFollowPoint = MedievalSoldierFollowPolicy.desiredPoint(this.soldier, this.owner);
            this.followPointRefreshTicks = MedievalSoldierFollowPolicy.repathTicks(this.soldier);
        }
        double followDistance = MedievalSoldierFollowPolicy.hysteresisDistance(this.soldier, this.owner, this.cachedFollowPoint);
        if (!this.soldier.adaptiveNavigation().isLadderRouteActive() && MedievalSoldierFollowPolicy.shouldHardStop(this.soldier, followDistance)) {
            this.soldier.adaptiveNavigation().stop();
            return;
        }
        this.soldier.adaptiveNavigation().tickToPoint(this.cachedFollowPoint, this.speed);
        MedievalSoldierMovementPriorityPolicy.tickJumpAssist(this.soldier);
    }

    public void method_6270() {
        this.owner = null;
        this.cachedFollowPoint = null;
        this.followPointRefreshTicks = 0;
        this.soldier.adaptiveNavigation().stop();
    }
}

