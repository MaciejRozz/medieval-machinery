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
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierGroupCombatCoordinator;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1400;
import net.minecraft.class_5134;

final class DiplomaticHostileTargetGoal
extends class_1400<class_1309> {
    private final MedievalSoldierEntity soldier;

    DiplomaticHostileTargetGoal(MedievalSoldierEntity soldier) {
        super((class_1308)soldier, class_1309.class, true, target -> target != null && target.method_5805() && target != soldier && soldier.isDiplomaticWarTarget((class_1309)target) && !soldier.isFriendlyCombatEntity((class_1309)target) && soldier.method_5858((class_1297)target) <= Math.pow(soldier.method_26825(class_5134.field_23717) * OrderArmorSetBonusService.aiTargetDistanceFactor(target), 2.0));
        this.soldier = soldier;
    }

    public boolean method_6264() {
        if (!this.soldier.mayAcquireCombatTarget() || !NpcMoraleBehaviorPolicy.mayAcquireProactiveTarget(this.soldier.getMorale())) {
            return false;
        }
        NpcCombatStance stance = this.soldier.getCombatStance();
        return (stance == NpcCombatStance.AGGRESSIVE || stance == NpcCombatStance.RAID) && super.method_6264();
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
        class_1309 target = this.soldier.method_5968();
        return target != null && this.soldier.isDiplomaticWarTarget(target) && this.soldier.isWithinPatrolCombatLeash(target) && super.method_6266();
    }
}

