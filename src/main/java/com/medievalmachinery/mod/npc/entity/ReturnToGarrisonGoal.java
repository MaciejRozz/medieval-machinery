/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_243
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.EnumSet;
import net.minecraft.class_1352;
import net.minecraft.class_243;

final class ReturnToGarrisonGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;
    private class_243 resolvedHome;

    ReturnToGarrisonGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        double dz;
        if (this.soldier.isGeneralBodyguard() || this.soldier.hasSupplyMovementOverride() || this.soldier.isInventoryGuiOpen() || this.soldier.getOrderType() != NpcOrderType.RETURN_TO_GARRISON) {
            return false;
        }
        this.resolvedHome = this.soldier.resolveReturnToGarrisonDestination();
        if (this.resolvedHome == null) {
            return false;
        }
        double dx = this.soldier.method_23317() - this.resolvedHome.field_1352;
        return dx * dx + (dz = this.soldier.method_23321() - this.resolvedHome.field_1350) * dz > 4.0;
    }

    public boolean method_6266() {
        double dz;
        if (this.soldier.hasSupplyMovementOverride() || this.soldier.isInventoryGuiOpen() || this.soldier.getOrderType() != NpcOrderType.RETURN_TO_GARRISON || this.resolvedHome == null) {
            return false;
        }
        double dx = this.soldier.method_23317() - this.resolvedHome.field_1352;
        return dx * dx + (dz = this.soldier.method_23321() - this.resolvedHome.field_1350) * dz > 2.25;
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
    }

    public void method_6268() {
        double dz;
        if (this.resolvedHome == null) {
            return;
        }
        double dx = this.soldier.method_23317() - this.resolvedHome.field_1352;
        if (dx * dx + (dz = this.soldier.method_23321() - this.resolvedHome.field_1350) * dz <= 2.25) {
            this.soldier.adaptiveNavigation().stop();
            this.soldier.setOrderType(NpcOrderType.HOLD_POSITION);
            return;
        }
        this.soldier.adaptiveNavigation().tick(this.resolvedHome.field_1352, this.resolvedHome.field_1351, this.resolvedHome.field_1350, null, this.speed);
    }

    public void method_6270() {
        this.soldier.adaptiveNavigation().stop();
        this.resolvedHome = null;
    }
}

