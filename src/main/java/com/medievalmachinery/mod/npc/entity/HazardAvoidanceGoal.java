/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_1541
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.EnumSet;
import net.minecraft.class_1297;
import net.minecraft.class_1352;
import net.minecraft.class_1541;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import org.jetbrains.annotations.Nullable;

final class HazardAvoidanceGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    @Nullable
    private class_243 fleeTarget;
    private int rescanTicks;
    private int safeTicks;

    HazardAvoidanceGoal(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        if (this.soldier.isInventoryGuiOpen()) {
            return false;
        }
        if (!this.soldier.isStaggeredAiTick(4)) {
            return false;
        }
        this.fleeTarget = this.findFleeTarget();
        return this.fleeTarget != null;
    }

    public boolean method_6266() {
        return this.fleeTarget != null && this.safeTicks < 12;
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
        this.rescanTicks = 0;
        this.safeTicks = 0;
    }

    public void method_6268() {
        if (this.rescanTicks-- <= 0) {
            class_243 updated = this.findFleeTarget();
            this.rescanTicks = 4;
            if (updated != null) {
                this.fleeTarget = updated;
                this.safeTicks = 0;
            } else {
                this.safeTicks += 5;
            }
        }
        if (this.fleeTarget != null) {
            this.soldier.adaptiveNavigation().tickToPoint(this.fleeTarget, 1.25);
            this.soldier.method_5728(true);
        }
    }

    public void method_6270() {
        this.soldier.method_5728(false);
        this.soldier.adaptiveNavigation().stop();
        this.fleeTarget = null;
        this.safeTicks = 0;
    }

    @Nullable
    private class_243 findFleeTarget() {
        class_1297 tnt = this.nearestPrimedTnt();
        class_2338 fire = this.nearestFireOrLava();
        class_243 dangerPos = null;
        double urgency = Double.MAX_VALUE;
        boolean selectedTnt = false;
        if (tnt != null) {
            dangerPos = tnt.method_19538();
            urgency = this.soldier.method_5858(tnt);
            selectedTnt = true;
        }
        if (fire != null) {
            double fireSq = this.soldier.method_5649((double)fire.method_10263() + 0.5, (double)fire.method_10264() + 0.5, (double)fire.method_10260() + 0.5);
            if (dangerPos == null || fireSq < urgency) {
                dangerPos = class_243.method_24953((class_2382)fire);
                selectedTnt = false;
            }
        }
        if (dangerPos == null) {
            return null;
        }
        class_243 away = this.soldier.method_19538().method_1020(dangerPos);
        if (away.method_1027() < 0.01) {
            away = new class_243(1.0, 0.0, 0.0);
        }
        away = new class_243(away.field_1352, 0.0, away.field_1350).method_1029();
        double fleeDistance = selectedTnt ? 8.0 : 4.0;
        for (int i = 0; i < 5; ++i) {
            double angle = switch (i) {
                case 1 -> Math.toRadians(25.0);
                case 2 -> Math.toRadians(-25.0);
                case 3 -> Math.toRadians(50.0);
                case 4 -> Math.toRadians(-50.0);
                default -> 0.0;
            };
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double rx = away.field_1352 * cos - away.field_1350 * sin;
            double rz = away.field_1352 * sin + away.field_1350 * cos;
            class_243 candidate = new class_243(this.soldier.method_23317() + rx * fleeDistance, this.soldier.method_23318(), this.soldier.method_23321() + rz * fleeDistance);
            if (!this.soldier.isSafeCombatPosition(candidate)) continue;
            return candidate;
        }
        return null;
    }

    @Nullable
    private class_1297 nearestPrimedTnt() {
        class_1541 best = null;
        double bestSq = 100.0;
        class_238 area = this.soldier.method_5829().method_1014(10.0);
        for (class_1541 tnt : this.soldier.method_37908().method_8390(class_1541.class, area, class_1297::method_5805)) {
            double sq = this.soldier.method_5858((class_1297)tnt);
            if (!(sq < bestSq)) continue;
            bestSq = sq;
            best = tnt;
        }
        return best;
    }

    @Nullable
    private class_2338 nearestFireOrLava() {
        class_2338 origin = this.soldier.method_24515();
        class_2338 best = null;
        double bestSq = Double.MAX_VALUE;
        for (int dx = -3; dx <= 3; ++dx) {
            for (int dy = -1; dy <= 2; ++dy) {
                for (int dz = -3; dz <= 3; ++dz) {
                    double sq;
                    boolean dangerous;
                    class_2338 pos = origin.method_10069(dx, dy, dz);
                    class_2680 state = this.soldier.method_37908().method_8320(pos);
                    boolean bl = dangerous = state.method_27852(class_2246.field_10164) || state.method_27852(class_2246.field_10036) || state.method_27852(class_2246.field_22089);
                    if (!dangerous || !((sq = pos.method_10262((class_2382)origin)) < bestSq)) continue;
                    bestSq = sq;
                    best = pos.method_10062();
                }
            }
        }
        return best;
    }
}

