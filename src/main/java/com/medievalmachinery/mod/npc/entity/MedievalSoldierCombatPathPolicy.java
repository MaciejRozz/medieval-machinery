/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_11
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_11;
import net.minecraft.class_1297;
import net.minecraft.class_1309;

final class MedievalSoldierCombatPathPolicy {
    private MedievalSoldierCombatPathPolicy() {
    }

    static boolean mayPursue(MedievalSoldierEntity soldier, class_1309 target) {
        if (target == null || !target.method_5805()) {
            return false;
        }
        if (Math.abs(target.method_23318() - soldier.method_23318()) <= 1.25) {
            return true;
        }
        class_11 path = soldier.method_5942().method_6349((class_1297)target, 0);
        return path != null && path.method_21655();
    }
}

