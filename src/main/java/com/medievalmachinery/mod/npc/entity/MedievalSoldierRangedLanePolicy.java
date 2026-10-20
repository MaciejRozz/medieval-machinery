/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_243
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1309;
import net.minecraft.class_243;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierRangedLanePolicy {
    private MedievalSoldierRangedLanePolicy() {
    }

    @Nullable
    static class_243 chooseLane(MedievalSoldierEntity soldier, class_1309 target) {
        double dz;
        double dx;
        double length;
        if (soldier == null || target == null) {
            return null;
        }
        class_243 remembered = soldier.goalCombatMovementState().firingLane(target.method_5667());
        if (remembered != null) {
            if (soldier.combatIsSafePosition(remembered) && soldier.rangedHasClearLineFrom(remembered, target) && soldier.method_19538().method_1025(remembered) <= 16.0) {
                return remembered;
            }
            soldier.goalCombatMovementState().clearFiringLane();
        }
        if ((length = Math.sqrt((dx = target.method_23317() - soldier.method_23317()) * dx + (dz = target.method_23321() - soldier.method_23321()) * dz)) < 0.001) {
            return null;
        }
        double rightX = -dz / length;
        double rightZ = dx / length;
        double forwardX = dx / length;
        double forwardZ = dz / length;
        int preferred = soldier.goalCombatMovementState().preferredSide(soldier.method_5667().hashCode());
        double[] lateral = new double[]{1.75 * (double)preferred, 2.75 * (double)preferred, -1.75 * (double)preferred};
        double[] forward = new double[]{0.0, -0.75, 0.0};
        for (int i = 0; i < lateral.length; ++i) {
            class_243 candidate = new class_243(soldier.method_23317() + rightX * lateral[i] + forwardX * forward[i], soldier.method_23318(), soldier.method_23321() + rightZ * lateral[i] + forwardZ * forward[i]);
            if (!soldier.combatIsSafePosition(candidate) || !soldier.rangedHasClearLineFrom(candidate, target)) continue;
            soldier.goalCombatMovementState().rememberFiringLane(target.method_5667(), candidate);
            return candidate;
        }
        return null;
    }
}

