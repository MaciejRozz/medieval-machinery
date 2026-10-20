/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_1314
 *  net.minecraft.class_1399
 *  net.minecraft.class_1657
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1309;
import net.minecraft.class_1314;
import net.minecraft.class_1399;
import net.minecraft.class_1657;

final class StanceAwareRevengeGoal
extends class_1399 {
    private final MedievalSoldierEntity soldier;

    StanceAwareRevengeGoal(MedievalSoldierEntity soldier) {
        super((class_1314)soldier, new Class[0]);
        this.soldier = soldier;
    }

    public boolean method_6264() {
        class_1657 player;
        class_1309 attacker = this.soldier.method_6065();
        if (attacker instanceof class_1657 && this.soldier.isOwnedBy(player = (class_1657)attacker)) {
            return false;
        }
        return this.soldier.mayAcquireCombatTarget() && (this.soldier.getCombatStance() != NpcCombatStance.PASSIVE || this.soldier.isCommandedCombatTarget(this.soldier.method_5968())) && super.method_6264();
    }

    public boolean method_6266() {
        return this.soldier.getCombatStance() != NpcCombatStance.PASSIVE && this.soldier.isWithinPatrolCombatLeash(this.soldier.method_5968()) && super.method_6266();
    }
}

