/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_1937
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.GeneralBodyguardService;
import java.util.EnumSet;
import net.minecraft.class_1352;
import net.minecraft.class_1937;
import net.minecraft.class_3218;

final class BodyguardEscortGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private GeneralBodyguardService.EscortDirective directive;

    BodyguardEscortGoal(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        this.directive = this.resolveDirective();
        return this.directive != null && !GeneralBodyguardService.isAtEscortSlot(this.soldier, this.directive);
    }

    public boolean method_6266() {
        this.directive = this.resolveDirective();
        return this.directive != null && !GeneralBodyguardService.isAtEscortSlot(this.soldier, this.directive);
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
    }

    public void method_6268() {
        this.directive = this.resolveDirective();
        if (this.directive != null) {
            this.soldier.adaptiveNavigation().tickToPoint(this.directive.target(), this.directive.speed());
        }
    }

    public void method_6270() {
        this.soldier.adaptiveNavigation().stop();
        this.directive = null;
    }

    private GeneralBodyguardService.EscortDirective resolveDirective() {
        if (this.soldier.hasSupplyMovementOverride()) {
            return null;
        }
        class_1937 class_19372 = this.soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return null;
        }
        class_3218 world = (class_3218)class_19372;
        return GeneralBodyguardService.escortDirective(world, this.soldier);
    }
}

