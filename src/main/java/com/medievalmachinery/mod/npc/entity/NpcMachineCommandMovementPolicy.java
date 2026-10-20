/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  net.minecraft.class_3532
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.UUID;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import net.minecraft.class_3532;
import org.jetbrains.annotations.Nullable;

final class NpcMachineCommandMovementPolicy {
    private static final double MOVE_ARRIVAL_DISTANCE = 2.5;
    private static final double FOLLOW_ARRIVAL_DISTANCE = 6.0;

    private NpcMachineCommandMovementPolicy() {
    }

    static Command resolve(MedievalSoldierEntity soldier, MedievalMachineryOperable machine) {
        double arrival;
        if (soldier == null || machine == null) {
            return Command.NONE;
        }
        NpcOrderType order = soldier.getOrderType();
        if (order != NpcOrderType.FOLLOW && order != NpcOrderType.MOVE_TO_POSITION) {
            return Command.NONE;
        }
        class_243 target = NpcMachineCommandMovementPolicy.resolveTarget(soldier, order);
        if (target == null) {
            return Command.NONE;
        }
        class_243 machinePos = machine.asEntity().method_19538();
        double dx = target.field_1352 - machinePos.field_1352;
        double dz = target.field_1350 - machinePos.field_1350;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        double d = arrival = order == NpcOrderType.FOLLOW ? 6.0 : 2.5;
        if (horizontal <= arrival) {
            return new Command(true, true, machine.asEntity().method_36454(), 0.0f, 0.0f);
        }
        float desiredYaw = class_3532.method_15393((float)((float)Math.toDegrees(Math.atan2(-dx, dz))));
        float yawError = class_3532.method_15393((float)(desiredYaw - machine.asEntity().method_36454()));
        float sideways = class_3532.method_15363((float)(yawError / 35.0f), (float)-1.0f, (float)1.0f);
        float absError = Math.abs(yawError);
        float semanticForward = absError <= 35.0f ? 1.0f : (absError <= 90.0f ? 0.62f : 0.28f);
        float operatorForward = semanticForward * machine.medievalMachinery$getNpcForwardInputSign();
        return new Command(true, false, desiredYaw, operatorForward, sideways);
    }

    @Nullable
    private static class_243 resolveTarget(MedievalSoldierEntity soldier, NpcOrderType order) {
        if (order == NpcOrderType.MOVE_TO_POSITION) {
            return soldier.goalCommandDestination() == null ? null : class_243.method_24955((class_2382)soldier.goalCommandDestination());
        }
        class_1937 class_19372 = soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return null;
        }
        class_3218 world = (class_3218)class_19372;
        UUID ownerId = soldier.getNpcIdentity().getOwnerId();
        if (ownerId == null) {
            return null;
        }
        class_1657 owner = world.method_18470(ownerId);
        if (owner == null || !owner.method_5805() || owner.method_7325()) {
            return null;
        }
        return owner.method_19538();
    }

    record Command(boolean active, boolean arrived, float yaw, float forward, float sideways) {
        private static final Command NONE = new Command(false, false, 0.0f, 0.0f, 0.0f);
    }
}

