/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1308
 *  net.minecraft.class_1309
 *  net.minecraft.class_1311
 *  net.minecraft.class_1548
 *  net.minecraft.class_1560
 *  net.minecraft.class_1571
 *  net.minecraft.class_1590
 */
package com.medievalmachinery.mod.npc.core;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1311;
import net.minecraft.class_1548;
import net.minecraft.class_1560;
import net.minecraft.class_1571;
import net.minecraft.class_1590;

public final class NpcTargetPolicy {
    private NpcTargetPolicy() {
    }

    public static boolean isProactiveAggressiveTarget(MedievalSoldierEntity soldier, class_1309 target) {
        if (soldier == null || target == null || target == soldier || !target.method_5805()) {
            return false;
        }
        if (target instanceof MedievalSoldierEntity) {
            return false;
        }
        return NpcTargetPolicy.isMonsterMob(target) && !NpcTargetPolicy.isStandardNeutralBlacklist(target);
    }

    public static boolean isMonsterMob(class_1309 entity) {
        return entity instanceof class_1308 && entity.method_5864().method_5891() == class_1311.field_6302;
    }

    public static boolean isStandardNeutralBlacklist(class_1309 entity) {
        return entity instanceof class_1548 || entity instanceof class_1571 || entity instanceof class_1560 || entity instanceof class_1590;
    }

    public static boolean shouldMonsterMobTargetSoldiers(class_1308 mob) {
        return mob != null && mob.method_5864().method_5891() == class_1311.field_6302 && !NpcTargetPolicy.isStandardNeutralBlacklist((class_1309)mob);
    }

    public static boolean shouldCreeperRetaliate(class_1309 attacker) {
        return false;
    }
}

