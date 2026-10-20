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

import com.medievalmachinery.mod.npc.core.NpcMoraleBehaviorPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.EnumSet;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1352;
import net.minecraft.class_243;

final class MoraleFallbackGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private int ticks;

    MoraleFallbackGoal(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405, class_1352.class_4134.field_18406));
    }

    public boolean method_6264() {
        class_1309 target = this.soldier.method_5968();
        return target != null && target.method_5805() && NpcMoraleBehaviorPolicy.shouldFallback(this.soldier.getMorale(), this.soldier.isCommandedCombatTarget(target), this.soldier.hasNearbyMoraleLeader());
    }

    public boolean method_6266() {
        class_1309 target = this.soldier.method_5968();
        return this.ticks < 40 && target != null && target.method_5805() && !NpcMoraleBehaviorPolicy.hasRallied(this.soldier.getMorale()) && NpcMoraleBehaviorPolicy.shouldFallback(this.soldier.getMorale(), this.soldier.isCommandedCombatTarget(target), this.soldier.hasNearbyMoraleLeader());
    }

    public void method_6269() {
        this.ticks = 0;
        this.soldier.adaptiveNavigation().reset();
        this.soldier.method_5728(true);
    }

    public void method_6268() {
        ++this.ticks;
        class_1309 target = this.soldier.method_5968();
        if (target == null) {
            return;
        }
        this.soldier.method_5988().method_6226((class_1297)target, 30.0f, 30.0f);
        class_243 away = this.soldier.method_19538().method_1020(target.method_19538());
        double horizontal = Math.sqrt(away.field_1352 * away.field_1352 + away.field_1350 * away.field_1350);
        if (horizontal < 0.001) {
            double angle = this.soldier.method_6051().method_43058() * Math.PI * 2.0;
            away = new class_243(Math.cos(angle), 0.0, Math.sin(angle));
            horizontal = 1.0;
        }
        double nx = away.field_1352 / horizontal;
        double nz = away.field_1350 / horizontal;
        double retreat = 5.0;
        this.soldier.adaptiveNavigation().tickToPoint(new class_243(this.soldier.method_23317() + nx * retreat, this.soldier.method_23318(), this.soldier.method_23321() + nz * retreat), 1.2);
    }

    public void method_6270() {
        this.soldier.method_5728(false);
        this.soldier.adaptiveNavigation().stop();
        class_1309 target = this.soldier.method_5968();
        if (target != null && !this.soldier.isCommandedCombatTarget(target)) {
            this.soldier.abandonAutomaticCombatTarget(20);
        }
        this.soldier.applyLeadershipRallyAfterFallback();
    }
}

