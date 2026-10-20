/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMovementPriorityPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierRideablePolicy;
import java.util.EnumSet;
import net.minecraft.class_1297;
import net.minecraft.class_1352;

final class MountCommandGoal
extends class_1352 {
    private static final double BOARD_DISTANCE_SQ = 6.760000000000001;
    private final MedievalSoldierEntity soldier;
    private final double speed;
    private double lastDistanceSq = Double.MAX_VALUE;
    private int stalledTicks;

    MountCommandGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405, class_1352.class_4134.field_18406));
    }

    public boolean method_6264() {
        return !this.soldier.isGeneralBodyguard() && !this.soldier.method_5765() && this.soldier.hasPendingMountCommand() && this.soldier.pendingMountTarget() != null;
    }

    public boolean method_6266() {
        return this.method_6264();
    }

    public void method_6269() {
        class_1297 target = this.soldier.pendingMountTarget();
        this.lastDistanceSq = target == null ? Double.MAX_VALUE : this.soldier.method_5858(target);
        this.stalledTicks = 0;
        this.soldier.adaptiveNavigation().reset();
    }

    public void method_6268() {
        class_1297 target = this.soldier.pendingMountTarget();
        if (target == null || !MedievalSoldierRideablePolicy.isSupportedCandidate(this.soldier, target)) {
            this.soldier.retryOrCancelMountCommand();
            return;
        }
        this.soldier.method_5988().method_6226(target, 30.0f, 30.0f);
        if (this.soldier.method_5858(target) <= 6.760000000000001) {
            if (this.soldier.tryBoardPendingMount(target)) {
                return;
            }
            this.soldier.retryOrCancelMountCommand();
            return;
        }
        double distanceSq = this.soldier.method_5858(target);
        if (distanceSq + 0.2 < this.lastDistanceSq) {
            this.lastDistanceSq = distanceSq;
            this.stalledTicks = 0;
        } else {
            ++this.stalledTicks;
        }
        MedievalSoldierMovementPriorityPolicy.tickJumpAssist(this.soldier);
        if (this.stalledTicks >= 40) {
            this.soldier.retryOrCancelMountCommand();
            this.stalledTicks = 0;
            this.lastDistanceSq = Double.MAX_VALUE;
            return;
        }
        this.soldier.adaptiveNavigation().tickToEntity(target, this.speed);
    }

    public void method_6270() {
        this.soldier.adaptiveNavigation().stop();
    }
}

