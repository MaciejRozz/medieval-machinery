/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_243
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1309;
import net.minecraft.class_243;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierPatrolCombatPolicy {
    private static final double COMBAT_RADIUS = 8.0;

    private MedievalSoldierPatrolCombatPolicy() {
    }

    static boolean allowsTarget(MedievalSoldierEntity soldier, @Nullable class_1309 target) {
        if (target == null || soldier.getOrderType() != NpcOrderType.PATROL || !soldier.hasPatrolRoute()) {
            return true;
        }
        class_243 anchor = soldier.goalCombatMovementState().patrolCombatAnchor(soldier.method_19538());
        if (anchor == null) {
            return true;
        }
        if (soldier.isRangedRole()) {
            return true;
        }
        return anchor.method_1025(target.method_19538()) <= 64.0;
    }

    static void tick(MedievalSoldierEntity soldier) {
        class_1309 target = soldier.method_5968();
        soldier.goalCombatMovementState().tickPatrolCombatAnchor(soldier.getOrderType() == NpcOrderType.PATROL && soldier.hasPatrolRoute(), target != null && target.method_5805());
    }
}

