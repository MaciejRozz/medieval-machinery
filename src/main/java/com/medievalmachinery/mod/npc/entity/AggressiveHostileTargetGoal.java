/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1308
 *  net.minecraft.class_1309
 *  net.minecraft.class_1400
 *  net.minecraft.class_5134
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.item.OrderArmorSetBonusService;
import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcMoraleBehaviorPolicy;
import com.medievalmachinery.mod.npc.core.NpcTargetPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierGroupCombatCoordinator;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1400;
import net.minecraft.class_5134;

final class AggressiveHostileTargetGoal
extends class_1400<class_1308> {
    private final MedievalSoldierEntity soldier;

    AggressiveHostileTargetGoal(MedievalSoldierEntity soldier) {
        super((class_1308)soldier, class_1308.class, true, target -> NpcTargetPolicy.isProactiveAggressiveTarget(soldier, target) && soldier.isWithinPatrolCombatLeash((class_1309)target) && soldier.method_5858((class_1297)target) <= Math.pow(soldier.method_26825(class_5134.field_23717) * OrderArmorSetBonusService.aiTargetDistanceFactor(target), 2.0));
        this.soldier = soldier;
    }

    public boolean method_6264() {
        return this.soldier.mayAcquireCombatTarget() && NpcMoraleBehaviorPolicy.mayAcquireProactiveTarget(this.soldier.getMorale()) && this.soldier.getCombatStance() == NpcCombatStance.AGGRESSIVE && super.method_6264();
    }

    public void method_6269() {
        super.method_6269();
        class_1309 acquired = this.soldier.method_5968();
        if (acquired == null || !acquired.method_5805()) {
            return;
        }
        class_1309 coordinated = MedievalSoldierGroupCombatCoordinator.coordinatedTarget(this.soldier, acquired, true);
        if (coordinated != null && coordinated != acquired) {
            this.soldier.method_5980(coordinated);
        }
    }

    public boolean method_6266() {
        return this.soldier.getCombatStance() == NpcCombatStance.AGGRESSIVE && this.soldier.isWithinPatrolCombatLeash(this.soldier.method_5968()) && super.method_6266();
    }
}

