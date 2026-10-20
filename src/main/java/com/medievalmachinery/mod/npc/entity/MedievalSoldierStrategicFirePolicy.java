/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 *  net.minecraft.class_5819
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_243;
import net.minecraft.class_5819;

final class MedievalSoldierStrategicFirePolicy {
    private MedievalSoldierStrategicFirePolicy() {
    }

    static boolean canAccept(MedievalSoldierEntity soldier) {
        return soldier != null && !soldier.method_37908().field_9236 && (soldier.isHoldingRangedWeapon() || soldier.machineAssignmentOwnsPersonalCombat());
    }

    static class_243 chooseAimPoint(class_5819 random, class_243 center, double radius, class_243 fallback) {
        if (center == null) {
            return fallback;
        }
        double angle = random.method_43058() * Math.PI * 2.0;
        double r = Math.sqrt(random.method_43058()) * Math.max(0.5, radius);
        return new class_243(center.field_1352 + Math.cos(angle) * r, center.field_1351 + 0.7, center.field_1350 + Math.sin(angle) * r);
    }
}

