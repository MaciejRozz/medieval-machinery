/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.EnumSet;
import net.minecraft.class_1352;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import org.jetbrains.annotations.Nullable;

final class OrderAwareWanderGoal
extends class_1352 {
    private static final double MAX_IDLE_WANDER_DISTANCE_SQ = 100.0;
    private static final int RANDOM_RADIUS = 8;
    private static final int RALLY_RANDOM_RADIUS = 4;
    private final MedievalSoldierEntity soldier;
    private final double speed;
    @Nullable
    private class_243 destination;

    OrderAwareWanderGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        if (!this.soldier.isStaggeredAiTick(this.soldier.goalUsesIdleRallyAnchor() ? 10 : 20)) {
            return false;
        }
        if (!this.canWander()) {
            return false;
        }
        class_243 anchor = this.soldier.goalIdleWanderAnchor();
        if (anchor == null) {
            anchor = this.soldier.method_19538();
            this.soldier.goalSetIdleWanderAnchor(anchor);
        }
        if (this.soldier.method_19538().method_1025(anchor) > 100.0) {
            return false;
        }
        this.destination = this.chooseDestination(anchor, this.soldier.goalUsesIdleRallyAnchor() ? 4 : 8);
        return this.destination != null;
    }

    private boolean canWander() {
        return !this.soldier.isGeneralBodyguard() && !this.soldier.hasSupplyMovementOverride() && this.soldier.getOrderType() == NpcOrderType.IDLE && this.soldier.method_5968() == null && !this.soldier.method_6115();
    }

    @Nullable
    private class_243 chooseDestination(class_243 anchor, int radius) {
        int attempts = this.soldier.goalUsesIdleRallyAnchor() ? 10 : 6;
        for (int attempt = 0; attempt < attempts; ++attempt) {
            int dz;
            int dx = this.soldier.method_6051().method_43048(radius * 2 + 1) - radius;
            if (dx * dx + (dz = this.soldier.method_6051().method_43048(radius * 2 + 1) - radius) * dz < 4) continue;
            class_2338 probe = class_2338.method_49637((double)(anchor.field_1352 + (double)dx), (double)anchor.field_1351, (double)(anchor.field_1350 + (double)dz));
            class_243 candidate = this.soldier.adaptiveNavigation().findWalkableNear(probe, 2);
            if (candidate == null || candidate.method_1025(anchor) > 100.0 || candidate.method_1025(this.soldier.method_19538()) < 2.25) continue;
            return candidate;
        }
        return null;
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
    }

    public boolean method_6266() {
        class_243 anchor = this.soldier.goalIdleWanderAnchor();
        return this.destination != null && this.canWander() && anchor != null && this.soldier.method_19538().method_1025(anchor) <= 100.0 && this.soldier.method_19538().method_1025(this.destination) > 1.0;
    }

    public void method_6268() {
        if (this.destination != null) {
            this.soldier.adaptiveNavigation().tickToPoint(this.destination, this.speed);
        }
    }

    public void method_6270() {
        this.soldier.adaptiveNavigation().stop();
        this.destination = null;
    }
}

