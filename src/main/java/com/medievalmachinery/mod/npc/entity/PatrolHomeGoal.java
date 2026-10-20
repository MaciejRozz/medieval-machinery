/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_2338
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.EnumSet;
import net.minecraft.class_1352;
import net.minecraft.class_2338;

final class PatrolHomeGoal
extends class_1352 {
    private static final double PATROL_WALK_SPEED = 0.9;
    private final MedievalSoldierEntity soldier;
    private class_2338 target;
    private boolean waitingForWaypointChunk;
    private int scoutDwellTicks;
    private float scoutLookBaseYaw;

    PatrolHomeGoal(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405, class_1352.class_4134.field_18406));
    }

    public boolean method_6264() {
        return !this.soldier.isGeneralBodyguard() && !this.soldier.hasSupplyMovementOverride() && !this.soldier.isInventoryGuiOpen() && this.soldier.getOrderType() == NpcOrderType.PATROL && this.soldier.hasPatrolRoute() && this.soldier.method_5968() == null && this.soldier.goalPatrolState().consumeRepathReady();
    }

    public boolean method_6266() {
        return !this.soldier.hasSupplyMovementOverride() && !this.soldier.isInventoryGuiOpen() && this.soldier.getOrderType() == NpcOrderType.PATROL && this.soldier.hasPatrolRoute() && this.soldier.method_5968() == null && this.target != null;
    }

    public void method_6269() {
        this.target = this.soldier.goalPatrolState().nextWaypointPosition();
        this.waitingForWaypointChunk = false;
        this.scoutDwellTicks = 0;
        this.scoutLookBaseYaw = this.soldier.method_36454();
        this.soldier.adaptiveNavigation().reset();
        this.soldier.npcDebug().event("PATROL_START", this.target, "resume waypoint");
    }

    public void method_6268() {
        boolean navigationArrived;
        if (this.target == null) {
            return;
        }
        if (this.waitingForWaypointChunk) {
            if (!this.soldier.isStaggeredAiTick(20) || !this.soldier.method_37908().method_22340(this.target)) {
                return;
            }
            this.waitingForWaypointChunk = false;
            this.soldier.adaptiveNavigation().reset();
        } else if (!this.soldier.method_37908().method_22340(this.target)) {
            this.waitingForWaypointChunk = true;
            this.soldier.npcDebug().event("PATROL_WAIT_CHUNK", this.target, "waypoint chunk unloaded");
            this.soldier.adaptiveNavigation().stop();
            return;
        }
        if (this.scoutDwellTicks > 0) {
            this.soldier.adaptiveNavigation().stop();
            --this.scoutDwellTicks;
            int phase = (40 - this.scoutDwellTicks) / 10;
            float yaw = switch (phase & 3) {
                case 0 -> this.scoutLookBaseYaw - 70.0f;
                case 1 -> this.scoutLookBaseYaw;
                case 2 -> this.scoutLookBaseYaw + 70.0f;
                default -> this.scoutLookBaseYaw + 180.0f;
            };
            this.soldier.method_36456(yaw);
            this.soldier.method_5847(yaw);
            this.soldier.field_6283 = yaw;
            if (this.scoutDwellTicks > 0) {
                return;
            }
            this.soldier.npcDebug().event("PATROL_WAYPOINT_REACHED", this.target, "scout dwell complete");
            this.soldier.goalPatrolState().markWaypointReached();
            this.soldier.goalMarkRegistryDirty();
            this.target = this.soldier.goalPatrolState().nextWaypointPosition();
            this.waitingForWaypointChunk = false;
            if (this.target == null) {
                return;
            }
            if (!this.soldier.method_37908().method_22340(this.target)) {
                this.waitingForWaypointChunk = true;
                return;
            }
            this.soldier.adaptiveNavigation().tickToBlock(this.target, true, 0.9);
            return;
        }
        double targetX = (double)this.target.method_10263() + 0.5;
        double targetZ = (double)this.target.method_10260() + 0.5;
        double dx = this.soldier.method_23317() - targetX;
        double dy = this.soldier.method_23318() - (double)this.target.method_10264();
        double dz = this.soldier.method_23321() - targetZ;
        double horizontalDistanceSq = dx * dx + dz * dz;
        double verticalDistance = Math.abs(dy);
        boolean correctWaypointLevel = verticalDistance <= 1.75;
        boolean bl = navigationArrived = this.soldier.method_5942().method_6357() && horizontalDistanceSq <= 3.24 && correctWaypointLevel;
        if (horizontalDistanceSq <= 2.25 && correctWaypointLevel || navigationArrived) {
            if (this.soldier.getNpcRole() == NpcRole.SCOUT) {
                this.scoutDwellTicks = 40;
                this.scoutLookBaseYaw = this.soldier.method_36454();
                this.soldier.adaptiveNavigation().stop();
                return;
            }
            this.soldier.npcDebug().event("PATROL_WAYPOINT_REACHED", this.target, "level and distance valid");
            this.soldier.goalPatrolState().markWaypointReached();
            this.soldier.goalMarkRegistryDirty();
            this.target = this.soldier.goalPatrolState().nextWaypointPosition();
            this.waitingForWaypointChunk = false;
            this.soldier.adaptiveNavigation().stop();
            if (this.target == null) {
                return;
            }
            if (!this.soldier.method_37908().method_22340(this.target)) {
                this.waitingForWaypointChunk = true;
                return;
            }
            this.soldier.adaptiveNavigation().tickToBlock(this.target, true, 0.9);
            return;
        }
        this.soldier.adaptiveNavigation().tickToBlock(this.target, true, 0.9);
    }

    public void method_6270() {
        this.soldier.npcDebug().event("PATROL_STOP", this.target, (String)(this.soldier.method_5968() == null ? "interrupted/owner changed" : "combat target=" + this.soldier.method_5968().method_5628()));
        this.target = null;
        this.waitingForWaypointChunk = false;
        this.scoutDwellTicks = 0;
        this.soldier.adaptiveNavigation().stop();
        this.soldier.goalPatrolState().scheduleRepath(this.soldier.method_5968() == null && this.soldier.getOrderType() == NpcOrderType.PATROL ? 0 : 4 + this.soldier.goalNextRandomInt(8));
    }
}

