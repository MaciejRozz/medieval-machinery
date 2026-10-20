/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_243
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.EnumSet;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1352;
import net.minecraft.class_243;

final class UnarmedRangedFallbackGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;
    private int recalcTicks;

    UnarmedRangedFallbackGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        class_1309 target = this.soldier.method_5968();
        return this.soldier.isRangedRole() && target != null && target.method_5805() && !this.soldier.isHoldingRangedWeapon() && !this.soldier.hasCarriedRangedWeapon() && !this.soldier.isCloseCombatWeapon(this.soldier.method_6047()) && !this.soldier.hasSupplyMovementOverride();
    }

    public boolean method_6266() {
        return this.method_6264();
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
        this.recalcTicks = 0;
    }

    public void method_6268() {
        class_1309 target = this.soldier.method_5968();
        if (target == null) {
            return;
        }
        this.soldier.method_5988().method_6226((class_1297)target, 30.0f, 30.0f);
        if (this.recalcTicks-- > 0) {
            return;
        }
        this.recalcTicks = 8;
        class_243 away = this.soldier.method_19538().method_1020(target.method_19538());
        away = new class_243(away.field_1352, 0.0, away.field_1350);
        if (away.method_1027() < 0.01) {
            away = new class_243(1.0, 0.0, 0.0);
        }
        away = away.method_1029();
        class_243 best = null;
        for (double angle : new double[]{0.0, 0.45, -0.45, 0.8, -0.8}) {
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double x = away.field_1352 * cos - away.field_1350 * sin;
            double z = away.field_1352 * sin + away.field_1350 * cos;
            class_243 candidate = new class_243(this.soldier.method_23317() + x * 5.0, this.soldier.method_23318(), this.soldier.method_23321() + z * 5.0);
            if (!this.soldier.isSafeCombatPosition(candidate)) continue;
            best = candidate;
            break;
        }
        if (best != null) {
            this.soldier.adaptiveNavigation().tickToPoint(best, this.speed);
            this.soldier.method_5728(true);
        } else {
            this.soldier.adaptiveNavigation().stop();
        }
    }

    public void method_6270() {
        this.soldier.method_5728(false);
        this.soldier.adaptiveNavigation().stop();
    }
}

