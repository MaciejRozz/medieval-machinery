/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_243
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.EnumSet;
import net.minecraft.class_1352;
import net.minecraft.class_243;

final class FormationAssemblyGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;

    FormationAssemblyGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        if (this.soldier.isGeneralBodyguard()) {
            return false;
        }
        if (this.soldier.goalFormationState().isTemporarilyReleased()) {
            return false;
        }
        return !this.soldier.hasSupplyMovementOverride() && this.soldier.goalFormationState().getAssemblyTarget() != null && this.soldier.goalFormationState().getAssemblyTicks() > 0 && !this.soldier.isInventoryGuiOpen() && this.soldier.method_5707(this.soldier.goalFormationState().getAssemblyTarget()) > 0.48999999999999994;
    }

    public boolean method_6266() {
        if (this.soldier.goalFormationState().isTemporarilyReleased()) {
            return false;
        }
        return this.method_6264();
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
    }

    public void method_6268() {
        class_243 target = this.soldier.goalFormationState().getAssemblyTarget();
        if (target == null) {
            return;
        }
        if (this.soldier.method_5707(target) <= 1.3224999999999998) {
            this.soldier.formationFinishAssembly(target);
            this.soldier.adaptiveNavigation().stop();
            return;
        }
        this.soldier.adaptiveNavigation().tickToPoint(target, this.speed);
    }

    public void method_6270() {
        class_243 target = this.soldier.goalFormationState().getAssemblyTarget();
        if (target != null && this.soldier.method_5707(target) <= 2.1025) {
            this.soldier.formationFinishAssembly(target);
        }
        this.soldier.adaptiveNavigation().stop();
    }
}

