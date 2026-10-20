/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierCommandMovementPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMovementPriorityPolicy;
import java.util.EnumSet;
import java.util.Objects;
import net.minecraft.class_1352;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import org.jetbrains.annotations.Nullable;

final class MoveToCommandGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;
    @Nullable
    private class_2338 latchedArrival;
    private class_243 lastProgressPos;
    private int stalledTicks;

    MoveToCommandGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        return !this.soldier.isGeneralBodyguard() && !this.soldier.hasSupplyMovementOverride() && !this.soldier.isInventoryGuiOpen() && this.soldier.getOrderType() == NpcOrderType.MOVE_TO_POSITION && this.soldier.goalCommandDestination() != null;
    }

    public boolean method_6266() {
        return MedievalSoldierMovementPriorityPolicy.mayRunFootOrder(this.soldier, NpcOrderType.MOVE_TO_POSITION) && this.soldier.goalCommandDestination() != null;
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
        this.lastProgressPos = this.soldier.method_19538();
        this.stalledTicks = 0;
        this.latchedArrival = this.soldier.goalCommandDestination() == null ? null : (this.soldier.getFormation() == NpcFormation.FREE ? this.findArrivalPosition(this.soldier.goalCommandDestination()) : this.findFormationArrivalPosition(this.soldier.goalCommandDestination()));
    }

    public void method_6268() {
        class_2338 target = this.soldier.goalCommandDestination();
        if (target == null) {
            return;
        }
        if (this.latchedArrival == null || !this.isArrivalStillUsable(this.latchedArrival)) {
            this.latchedArrival = this.soldier.getFormation() == NpcFormation.FREE ? this.findArrivalPosition(target) : this.findFormationArrivalPosition(target);
        }
        class_2338 arrival = this.latchedArrival;
        if (this.lastProgressPos == null || this.soldier.method_19538().method_1025(this.lastProgressPos) > 0.16) {
            this.lastProgressPos = this.soldier.method_19538();
            this.stalledTicks = 0;
        } else if (++this.stalledTicks >= 40) {
            arrival = this.latchedArrival = this.soldier.getFormation() == NpcFormation.FREE ? this.findArrivalPosition(target) : this.findFormationArrivalPosition(target);
            this.stalledTicks = 0;
        }
        double dx = this.soldier.method_23317() - ((double)arrival.method_10263() + 0.5);
        double dz = this.soldier.method_23321() - ((double)arrival.method_10260() + 0.5);
        double dy = this.soldier.method_23318() - (double)arrival.method_10264();
        double arrivalRadiusSq = MedievalSoldierCommandMovementPolicy.moveArrivalRadiusSq(this.soldier);
        if (dx * dx + dz * dz <= arrivalRadiusSq && Math.abs(dy) <= MedievalSoldierCommandMovementPolicy.moveArrivalVerticalTolerance(this.soldier) && this.soldier.method_24828()) {
            this.soldier.goalCompleteMoveAt(arrival);
            this.soldier.adaptiveNavigation().stop();
            return;
        }
        this.soldier.adaptiveNavigation().tickToPoint(new class_243((double)arrival.method_10263() + 0.5, (double)arrival.method_10264(), (double)arrival.method_10260() + 0.5), this.speed);
        MedievalSoldierMovementPriorityPolicy.tickJumpAssist(this.soldier);
    }

    private boolean isArrivalStillUsable(class_2338 feet) {
        boolean terrainFree;
        if (feet == null) {
            return false;
        }
        class_1937 world = this.soldier.method_37908();
        boolean bl = terrainFree = world.method_8320(feet).method_26220((class_1922)world, feet).method_1110() && world.method_8320(feet.method_10084()).method_26220((class_1922)world, feet.method_10084()).method_1110() && !world.method_8320(feet.method_10074()).method_26220((class_1922)world, feet.method_10074()).method_1110();
        if (!terrainFree) {
            return false;
        }
        class_238 cell = new class_238(feet).method_1009(0.08, 0.0, 0.08);
        return world.method_8390(MedievalSoldierEntity.class, cell, other -> other != this.soldier && Objects.equals(other.getNpcIdentity().getOwnerId(), this.soldier.getNpcIdentity().getOwnerId()) && Objects.equals(other.getNpcIdentity().getCastleId(), this.soldier.getNpcIdentity().getCastleId())).isEmpty();
    }

    private class_2338 findFormationArrivalPosition(class_2338 clickedFloor) {
        int[][] offsets;
        int feetY = clickedFloor.method_10264() + 1;
        class_2338 exact = new class_2338(clickedFloor.method_10263(), feetY, clickedFloor.method_10260());
        if (this.isArrivalStillUsable(exact)) {
            return exact;
        }
        for (int[] offset : offsets = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}}) {
            class_2338 candidate = new class_2338(clickedFloor.method_10263() + offset[0], feetY, clickedFloor.method_10260() + offset[1]);
            if (!this.isArrivalStillUsable(candidate)) continue;
            return candidate;
        }
        return exact;
    }

    private class_2338 findArrivalPosition(class_2338 clickedFloor) {
        int feetY = clickedFloor.method_10264() + 1;
        int[][] offsets = new int[][]{{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {2, 0}, {-2, 0}, {0, 2}, {0, -2}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int start = Math.floorMod(this.soldier.method_5667().hashCode(), offsets.length);
        class_2338 fallback = clickedFloor.method_10084();
        for (int i = 0; i < offsets.length; ++i) {
            int[] offset = offsets[(start + i) % offsets.length];
            class_2338 feet = new class_2338(clickedFloor.method_10263() + offset[0], feetY, clickedFloor.method_10260() + offset[1]);
            class_2338 floor = feet.method_10074();
            if (!this.soldier.method_37908().method_8320(feet).method_26220((class_1922)this.soldier.method_37908(), feet).method_1110() || !this.soldier.method_37908().method_8320(feet.method_10084()).method_26220((class_1922)this.soldier.method_37908(), feet.method_10084()).method_1110() || this.soldier.method_37908().method_8320(floor).method_26220((class_1922)this.soldier.method_37908(), floor).method_1110()) continue;
            return feet;
        }
        return fallback;
    }

    public void method_6270() {
        this.lastProgressPos = null;
        this.stalledTicks = 0;
        this.soldier.adaptiveNavigation().stop();
        this.latchedArrival = null;
    }
}

