/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.EnumSet;
import net.minecraft.class_1352;

final class IdleReturnGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;

    IdleReturnGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        return !this.soldier.isGeneralBodyguard() && !this.soldier.hasSupplyMovementOverride() && this.soldier.getOrderType() == NpcOrderType.IDLE && this.soldier.method_5968() == null && this.soldier.goalIdleWanderAnchor() != null && this.soldier.method_19538().method_1025(this.soldier.goalIdleWanderAnchor()) > 36.0;
    }

    public boolean method_6266() {
        return this.method_6264() && this.soldier.method_19538().method_1025(this.soldier.goalIdleWanderAnchor()) > 12.25;
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
    }

    public void method_6268() {
        if (this.soldier.goalIdleWanderAnchor() != null) {
            this.soldier.adaptiveNavigation().tickToPoint(this.soldier.goalIdleWanderAnchor(), this.speed);
        }
    }

    public void method_6270() {
        this.soldier.adaptiveNavigation().stop();
    }
}

