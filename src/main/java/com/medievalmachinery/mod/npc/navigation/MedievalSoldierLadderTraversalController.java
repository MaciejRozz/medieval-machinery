/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_3481
 *  net.minecraft.class_5321
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.navigation;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierCrowdCoordinator;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierLadderDebug;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierLadderTopology;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierNavigation;
import java.util.Locale;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_3481;
import net.minecraft.class_5321;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierLadderTraversalController {
    private final MedievalSoldierEntity soldier;
    private final MedievalSoldierLadderTopology topology;
    private final MedievalSoldierLadderDebug debug;
    @Nullable
    private class_2338 queueColumn;
    @Nullable
    private Direction queueDirection;
    private int queueClaimTicks;
    private int queueYieldTicks;
    private boolean committed;
    @Nullable
    private class_2338 committedColumn;
    @Nullable
    private class_2338 committedEntryRung;
    @Nullable
    private Direction committedDirection;
    @Nullable
    private class_2338 approachRung;
    @Nullable
    private Direction approachDirection;
    @Nullable
    private class_2338 plannedExitRung;
    @Nullable
    private class_2338 plannedExitFeet;
    @Nullable
    private class_2338 approachTarget;
    private int noProgressTicks;
    private double lastY = Double.NaN;

    MedievalSoldierLadderTraversalController(MedievalSoldierEntity soldier, MedievalSoldierLadderTopology topology, MedievalSoldierLadderDebug debug) {
        this.soldier = soldier;
        this.topology = topology;
        this.debug = debug;
    }

    void reset() {
        this.queueColumn = null;
        this.queueDirection = null;
        this.queueClaimTicks = 0;
        this.queueYieldTicks = 0;
        this.committed = false;
        this.committedColumn = null;
        this.committedEntryRung = null;
        this.committedDirection = null;
        this.plannedExitRung = null;
        this.plannedExitFeet = null;
        this.clearApproach();
        this.noProgressTicks = 0;
        this.lastY = Double.NaN;
        this.debug.clearState();
    }

    boolean hasApproach() {
        return this.approachRung != null && this.approachDirection != null;
    }

    @Nullable
    class_2338 approachRung() {
        return this.approachRung;
    }

    void beginApproach(class_2338 rung, @Nullable class_2338 exitRung, @Nullable class_2338 exitFeet, Direction direction, double targetX, double targetY, double targetZ) {
        this.approachRung = rung.method_10062();
        this.plannedExitRung = exitRung == null ? null : exitRung.method_10062();
        this.plannedExitFeet = exitFeet == null ? null : exitFeet.method_10062();
        this.approachDirection = direction;
        this.approachTarget = class_2338.method_49637((double)targetX, (double)targetY, (double)targetZ);
        this.debug.event("ENTRY_APPROACH_LOCK", this.topology.climbColumnBottom(rung), this.approachRung, "direction=" + String.valueOf((Object)direction) + " exitRung=" + String.valueOf(this.plannedExitRung) + " exitFeet=" + String.valueOf(this.plannedExitFeet) + " target=" + String.valueOf(this.approachTarget));
    }

    boolean approachStillValid(double targetX, double targetY, double targetZ) {
        if (!this.hasApproach() || this.approachRung == null) {
            return false;
        }
        if (!this.soldier.method_37908().method_8320(this.approachRung).method_26164(class_3481.field_22414)) {
            return false;
        }
        return this.approachDirection == Direction.UP ? targetY > this.soldier.method_23318() + 0.75 : targetY < this.soldier.method_23318() - 0.75;
    }

    void clearApproach() {
        this.approachRung = null;
        this.approachDirection = null;
        this.approachTarget = null;
    }

    boolean isCommitted() {
        return this.committed;
    }

    @Nullable
    class_2338 committedColumn() {
        return this.committedColumn;
    }

    @Nullable
    class_2338 committedEntryRung() {
        return this.committedEntryRung;
    }

    @Nullable
    class_2338 plannedExitRung() {
        return this.plannedExitRung;
    }

    @Nullable
    class_2338 plannedExitFeet() {
        return this.plannedExitFeet;
    }

    @Nullable
    Direction committedDirection() {
        return this.committedDirection;
    }

    boolean isAscending() {
        return this.isCommitted(Direction.UP);
    }

    boolean isDescending() {
        return this.isCommitted(Direction.DOWN);
    }

    Direction directionTowardTarget(double targetY) {
        double deltaY = targetY - this.soldier.method_23318();
        if (deltaY < -0.05) {
            return Direction.DOWN;
        }
        if (deltaY > 0.05) {
            return Direction.UP;
        }
        return this.committedDirection != null ? this.committedDirection : Direction.UP;
    }

    private boolean isCommitted(Direction direction) {
        return this.committed && this.committedDirection == direction;
    }

    boolean needsDescentEntryAssist(class_2338 topRung) {
        return this.isDescending() && this.soldier.method_23318() > (double)topRung.method_10264() + 0.55;
    }

    @Nullable
    class_2338 queueColumn() {
        return this.queueColumn;
    }

    void queue(class_2338 rung) {
        this.queue(rung, Direction.UP);
    }

    void queue(class_2338 rung, Direction direction) {
        class_2338 queueRung;
        class_2338 class_23382 = queueRung = direction == Direction.DOWN ? rung.method_10062() : this.topology.climbColumnBottom(rung).method_10062();
        if (queueRung.equals((Object)this.queueColumn) && direction == this.queueDirection) {
            return;
        }
        this.queueColumn = queueRung;
        this.queueDirection = direction;
        this.queueClaimTicks = 0;
        this.queueYieldTicks = 0;
        this.debug.event("QUEUE_ENTER", this.topology.climbColumnBottom(rung), queueRung, "direction=" + String.valueOf((Object)direction) + " waiting for ladder mouth");
    }

    void clearQueue() {
        this.queueColumn = null;
        this.queueDirection = null;
        this.queueClaimTicks = 0;
        this.queueYieldTicks = 0;
    }

    boolean approachEntryLip(MedievalSoldierNavigation route, class_2338 lip, class_2338 rung, double speed, Direction direction) {
        double ldx = (double)lip.method_10263() + 0.5 - this.soldier.method_23317();
        double ldz = (double)lip.method_10260() + 0.5 - this.soldier.method_23321();
        double lipDistSq = ldx * ldx + ldz * ldz;
        if (this.topology.isAtEntryLip(lip)) {
            return false;
        }
        if (!route.isIdle() || lipDistSq > 3.0625) {
            route.moveTo((double)lip.method_10263() + 0.5, lip.method_10264(), (double)lip.method_10260() + 0.5, Math.min(speed, 0.92));
            return true;
        }
        route.stop();
        this.soldier.method_5962().method_6239((double)lip.method_10263() + 0.5, (double)lip.method_10264(), (double)lip.method_10260() + 0.5, Math.min(speed, 0.82));
        this.debug.state("ENTRY_HANDOFF", this.topology.climbColumnBottom(rung), lip, "rung=" + String.valueOf(rung) + " direction=" + String.valueOf((Object)direction));
        return true;
    }

    void commit(class_2338 rung, Direction direction) {
        this.clearApproach();
        this.committed = true;
        this.committedColumn = this.topology.climbColumnBottom(rung).method_10062();
        this.committedEntryRung = rung.method_10062();
        this.committedDirection = direction;
        this.debug.event("COMMIT", this.committedColumn, this.committedEntryRung, "ladder traversal owns movement direction=" + String.valueOf((Object)direction));
        this.clearQueue();
    }

    void clearCommitment() {
        this.committed = false;
        class_2338 old = this.committedColumn;
        this.committedColumn = null;
        this.committedEntryRung = null;
        this.committedDirection = null;
        this.plannedExitRung = null;
        this.plannedExitFeet = null;
        this.clearApproach();
        this.debug.event("COMMIT_CLEAR", old, null, "leaving ladder traversal");
        this.resetProgress();
    }

    void resetProgress() {
        this.noProgressTicks = 0;
        this.lastY = Double.NaN;
    }

    void tickProgress() {
        if (Double.isNaN(this.lastY) || Math.abs(this.soldier.method_23318() - this.lastY) > 0.075) {
            this.lastY = this.soldier.method_23318();
            this.noProgressTicks = 0;
        } else if (++this.noProgressTicks >= 30) {
            this.debug.event("CLIMB_STALLED", this.committedColumn, this.soldier.method_24515(), "no vertical progress for 30 ticks");
            this.noProgressTicks = 0;
            this.lastY = this.soldier.method_23318();
        }
        if (this.committed) {
            this.debug.snapshot(this.committedColumn, this.soldier.method_24515(), "committed=true y=" + String.format(Locale.ROOT, "%.2f", this.soldier.method_23318()));
        }
    }

    void maintainEntryReservation(double targetY) {
        boolean clearOfBottomMouth;
        if (!this.committed || this.committedColumn == null || !this.soldier.isOnClimbableSurface()) {
            return;
        }
        boolean bl = clearOfBottomMouth = this.soldier.method_23318() >= (double)this.committedColumn.method_10264() + 2.75;
        if (!this.isAscending() || clearOfBottomMouth) {
            MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667(), MedievalSoldierCrowdCoordinator.Lane.CLIMB);
        } else {
            MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), this.committedColumn, MedievalSoldierCrowdCoordinator.Lane.CLIMB, this.soldier.method_5667(), this.soldier.method_37908().method_8510(), 14);
        }
    }

    boolean isTouchingColumn(class_2338 column) {
        if (column == null) {
            return false;
        }
        class_238 body = this.soldier.method_5829().method_1009(0.18, 0.08, 0.18);
        for (int dy = -1; dy <= 2; ++dy) {
            class_2338 pos = new class_2338(column.method_10263(), column.method_10264() + dy, column.method_10260());
            if (!this.soldier.method_37908().method_8320(pos).method_26164(class_3481.field_22414) || !body.method_994(new class_238(pos))) continue;
            return true;
        }
        return false;
    }

    @Nullable
    class_2338 nearestTouchingClimbable() {
        class_2338 base = this.soldier.method_24515();
        class_2338 best = null;
        double bestSq = Double.MAX_VALUE;
        class_238 body = this.soldier.method_5829().method_1009(0.24, 0.12, 0.24);
        for (int dy = -1; dy <= 2; ++dy) {
            for (int dx = -1; dx <= 1; ++dx) {
                for (int dz = -1; dz <= 1; ++dz) {
                    double pz;
                    double px;
                    double sq;
                    class_2338 pos = base.method_10069(dx, dy, dz);
                    if (!this.soldier.method_37908().method_8320(pos).method_26164(class_3481.field_22414) || !body.method_994(new class_238(pos)) || !((sq = (px = (double)pos.method_10263() + 0.5 - this.soldier.method_23317()) * px + (pz = (double)pos.method_10260() + 0.5 - this.soldier.method_23321()) * pz) < bestSq)) continue;
                    bestSq = sq;
                    best = pos.method_10062();
                }
            }
        }
        return best;
    }

    boolean tickQueue(MedievalSoldierNavigation route, double speed) {
        Direction direction;
        if (this.queueColumn == null) {
            return false;
        }
        class_2338 queued = this.queueColumn;
        Direction direction2 = direction = this.queueDirection == null ? Direction.UP : this.queueDirection;
        if (!this.soldier.method_37908().method_8320(queued).method_26164(class_3481.field_22414)) {
            this.debug.event("QUEUE_INVALID", queued, null, "queued ladder block no longer climbable");
            this.clearQueue();
            return false;
        }
        if (this.queueYieldTicks > 0) {
            --this.queueYieldTicks;
            this.moveToQueuePoint(route, queued, direction, speed, 0.82);
            return true;
        }
        long now = this.soldier.method_37908().method_8510();
        boolean reserved = MedievalSoldierCrowdCoordinator.isReservedByOther((class_5321<class_1937>)this.soldier.method_37908().method_27983(), queued, MedievalSoldierCrowdCoordinator.Lane.CLIMB, this.soldier.method_5667(), now);
        if (!reserved && MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), queued, MedievalSoldierCrowdCoordinator.Lane.CLIMB, this.soldier.method_5667(), now, 18)) {
            if (direction == Direction.DOWN) {
                this.debug.event("QUEUE_ADVANCE", this.topology.climbColumnBottom(queued), queued, "direction=DOWN top mouth acquired");
                this.clearQueue();
                return false;
            }
            class_2338 bottom = this.topology.climbColumnBottom(queued);
            if (this.soldier.isOnClimbableSurface()) {
                this.commit(bottom, Direction.UP);
                return false;
            }
            double bx = (double)bottom.method_10263() + 0.5 - this.soldier.method_23317();
            double bz = (double)bottom.method_10260() + 0.5 - this.soldier.method_23321();
            double horizontalSq = bx * bx + bz * bz;
            if (++this.queueClaimTicks > 40) {
                MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667(), MedievalSoldierCrowdCoordinator.Lane.CLIMB);
                this.queueClaimTicks = 0;
                this.queueYieldTicks = 18 + Math.floorMod(this.soldier.method_5628(), 12);
                this.debug.event("QUEUE_YIELD", queued, this.topology.findClimbQueuePoint(queued) == null ? null : class_2338.method_49638((class_2374)this.topology.findClimbQueuePoint(queued)), "entry claim timed out");
                this.moveToQueuePoint(route, queued, direction, speed, 0.8);
                return true;
            }
            route.moveTo((double)bottom.method_10263() + 0.5, (double)bottom.method_10264() + 0.15, (double)bottom.method_10260() + 0.5, Math.min(speed, 0.95));
            if (horizontalSq <= 1.2100000000000002) {
                double len = Math.sqrt(Math.max(1.0E-4, horizontalSq));
                class_243 current = this.soldier.method_18798();
                double y = current.field_1351;
                if (bottom.method_10264() > (int)Math.floor(this.soldier.method_23318())) {
                    this.soldier.method_5993().method_6233();
                    y = Math.max(y, 0.34);
                }
                this.soldier.method_18800(bx / len * 0.11, y, bz / len * 0.11);
                this.soldier.field_6007 = true;
            }
            return true;
        }
        this.queueClaimTicks = 0;
        this.moveToQueuePoint(route, queued, direction, speed, 0.85);
        return true;
    }

    private void moveToQueuePoint(MedievalSoldierNavigation route, class_2338 column, Direction direction, double speed, double cap) {
        class_243 point;
        class_243 class_2432 = point = direction == Direction.DOWN ? this.topology.findClimbTopQueuePoint(column) : this.topology.findClimbQueuePoint(column);
        if (point != null && this.soldier.method_5707(point) > 0.7224999999999999) {
            route.moveTo(point.field_1352, point.field_1351, point.field_1350, Math.min(speed, cap));
        } else {
            route.stop();
        }
    }

    static enum Direction {
        UP,
        DOWN;

    }
}

