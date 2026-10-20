/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcHungerState;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.EnumSet;
import net.minecraft.class_1352;

final class IdleRestGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private int restTicks;

    IdleRestGoal(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        if (!this.soldier.isStaggeredAiTick(20)) {
            return false;
        }
        if (this.soldier.getOrderType() != NpcOrderType.IDLE || this.soldier.hasActiveSupplyRun() || this.soldier.method_5968() != null || this.soldier.method_6115() || this.soldier.method_6032() < this.soldier.method_6063() || this.soldier.goalHungerData().getState() == NpcHungerState.HUNGRY || this.soldier.goalHungerData().getState() == NpcHungerState.VERY_HUNGRY) {
            return false;
        }
        return this.soldier.method_6051().method_43048(90) == 0;
    }

    public void method_6269() {
        this.restTicks = 40 + this.soldier.method_6051().method_43048(61);
        this.soldier.stopAdaptiveNavigation();
    }

    public boolean method_6266() {
        return this.restTicks > 0 && this.soldier.getOrderType() == NpcOrderType.IDLE && !this.soldier.hasActiveSupplyRun() && this.soldier.method_5968() == null;
    }

    public void method_6268() {
        --this.restTicks;
        this.soldier.stopAdaptiveNavigation();
    }
}

