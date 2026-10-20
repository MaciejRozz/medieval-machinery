/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierCommandMovementPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFormationCombatPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMovementPriorityPolicy;
import java.util.EnumSet;
import net.minecraft.class_1352;

final class HoldPositionGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;

    HoldPositionGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        double dz;
        if (this.soldier.isGeneralBodyguard() || this.soldier.isInventoryGuiOpen() || this.soldier.getOrderType() != NpcOrderType.HOLD_POSITION && this.soldier.getOrderType() != NpcOrderType.OBSERVATION_POINT || this.soldier.goalHoldPosition() == null || this.soldier.hasActiveSupplyRun() || this.soldier.goalSupplyState().isReturningToOrigin() || this.soldier.method_5968() != null && MedievalSoldierFormationCombatPolicy.mayMeleeAdvance(this.soldier, this.soldier.method_5968())) {
            return false;
        }
        double dx = this.soldier.method_23317() - this.soldier.goalHoldPosition().field_1352;
        return dx * dx + (dz = this.soldier.method_23321() - this.soldier.goalHoldPosition().field_1350) * dz > MedievalSoldierCommandMovementPolicy.holdStartRadiusSq() || Math.abs(this.soldier.method_23318() - this.soldier.goalHoldPosition().field_1351) > 0.75;
    }

    public boolean method_6266() {
        double dz;
        NpcOrderType order = this.soldier.getOrderType();
        if (order != NpcOrderType.HOLD_POSITION && order != NpcOrderType.OBSERVATION_POINT || !MedievalSoldierMovementPriorityPolicy.mayRunFootOrder(this.soldier, order) || this.soldier.goalHoldPosition() == null || MedievalSoldierMovementPriorityPolicy.combatBlocksHoldReturn(this.soldier)) {
            return false;
        }
        double dx = this.soldier.method_23317() - this.soldier.goalHoldPosition().field_1352;
        return dx * dx + (dz = this.soldier.method_23321() - this.soldier.goalHoldPosition().field_1350) * dz > MedievalSoldierCommandMovementPolicy.holdStopRadiusSq() || Math.abs(this.soldier.method_23318() - this.soldier.goalHoldPosition().field_1351) > 0.55;
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
    }

    public void method_6268() {
        if (this.soldier.goalHoldPosition() == null) {
            return;
        }
        this.soldier.adaptiveNavigation().tickToPoint(this.soldier.goalHoldPosition(), this.speed);
        MedievalSoldierMovementPriorityPolicy.tickJumpAssist(this.soldier);
    }

    public void method_6270() {
        this.soldier.adaptiveNavigation().stop();
        this.soldier.goalSetHoldDisplacedTicks(0);
    }
}

