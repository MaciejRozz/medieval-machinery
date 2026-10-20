/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1308
 *  net.minecraft.class_1309
 *  net.minecraft.class_1603
 *  net.minecraft.class_1657
 *  net.minecraft.class_1743
 *  net.minecraft.class_1753
 *  net.minecraft.class_1764
 *  net.minecraft.class_1799
 *  net.minecraft.class_1810
 *  net.minecraft.class_1829
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1603;
import net.minecraft.class_1657;
import net.minecraft.class_1743;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1799;
import net.minecraft.class_1810;
import net.minecraft.class_1829;

final class MedievalSoldierShieldPolicy {
    private MedievalSoldierShieldPolicy() {
    }

    static boolean shouldRaise(MedievalSoldierEntity soldier, class_1309 target) {
        boolean meleeWeapon;
        boolean rangedThreat;
        class_1657 player;
        class_1308 mob;
        if (soldier == null || target == null || !target.method_5805()) {
            return false;
        }
        double distanceSq = soldier.method_5858((class_1297)target);
        boolean attackingUs = target instanceof class_1308 && (mob = (class_1308)target).method_5968() == soldier || target instanceof class_1657 && (player = (class_1657)target).method_6052() == soldier;
        class_1799 hostileHand = target.method_6047();
        boolean bl = rangedThreat = target instanceof class_1603 || hostileHand.method_7909() instanceof class_1753 || hostileHand.method_7909() instanceof class_1764;
        if (rangedThreat && attackingUs) {
            double rangedRange = soldier.method_5765() ? 8.4 : 11.0;
            return distanceSq <= rangedRange * rangedRange;
        }
        boolean heavyMelee = hostileHand.method_7909() instanceof class_1743 || hostileHand.method_7909() instanceof class_1810;
        boolean bl2 = meleeWeapon = heavyMelee || hostileHand.method_7909() instanceof class_1829;
        double meleeRange = heavyMelee ? 4.0 : (meleeWeapon ? 3.5 : 3.25);
        return attackingUs && distanceSq <= meleeRange * meleeRange;
    }

    static int reRaiseDelayAfterAttack(MedievalSoldierEntity soldier) {
        return 7;
    }

    static int disabledCooldownTicks() {
        return 100;
    }
}

