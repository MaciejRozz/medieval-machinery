/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_1792
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.item.spear.SpearItem;
import com.medievalmachinery.mod.npc.core.NpcWeaponProfileResolver;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1309;
import net.minecraft.class_1792;

final class MedievalSoldierMeleeTimingPolicy {
    private MedievalSoldierMeleeTimingPolicy() {
    }

    static int windupTicks(MedievalSoldierEntity soldier) {
        return soldier != null && NpcWeaponProfileResolver.resolve(soldier.method_6047()).isSpearLike() ? 4 : 3;
    }

    static double attackReach(MedievalSoldierEntity soldier, class_1309 target) {
        class_1792 class_17922;
        if (soldier != null && (class_17922 = soldier.method_6047().method_7909()) instanceof SpearItem) {
            SpearItem spear = (SpearItem)class_17922;
            return spear.getReach();
        }
        if (soldier != null && NpcWeaponProfileResolver.resolve(soldier.method_6047()).isSpearLike()) {
            return 3.15;
        }
        if (soldier == null) {
            return 2.0;
        }
        double ownWidth = soldier.method_17681();
        double targetWidth = target == null ? 0.6 : (double)target.method_17681();
        return Math.max(2.0, ownWidth * 2.0 + targetWidth * 0.55);
    }

    static double attackReachSq(MedievalSoldierEntity soldier, class_1309 target) {
        double reach = MedievalSoldierMeleeTimingPolicy.attackReach(soldier, target);
        return reach * reach;
    }

    static double preferredStopDistance(MedievalSoldierEntity soldier, class_1309 target) {
        double reach = MedievalSoldierMeleeTimingPolicy.attackReach(soldier, target);
        if (soldier != null && NpcWeaponProfileResolver.resolve(soldier.method_6047()).isSpearLike()) {
            return Math.max(1.85, reach - 0.55);
        }
        return Math.max(1.45, reach * 0.72);
    }
}

