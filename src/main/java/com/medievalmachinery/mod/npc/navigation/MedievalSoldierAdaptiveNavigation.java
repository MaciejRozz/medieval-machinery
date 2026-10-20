/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2323
 *  net.minecraft.class_2338
 *  net.minecraft.class_2349
 *  net.minecraft.class_2374
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_3218
 *  net.minecraft.class_3481
 *  net.minecraft.class_3532
 *  net.minecraft.class_5321
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.navigation;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierCorridorEvaluator;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierCrowdCoordinator;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierLadderDebug;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierLadderRouteSelector;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierLadderTopology;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierLadderTraversalController;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierNavigation;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierPassageController;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierPathMemory;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierPathNodeEvaluator;
import com.medievalmachinery.mod.registry.ModBlocks;
import java.util.Locale;
import net.minecraft.class_1297;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2323;
import net.minecraft.class_2338;
import net.minecraft.class_2349;
import net.minecraft.class_2374;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3481;
import net.minecraft.class_3532;
import net.minecraft.class_5321;
import org.jetbrains.annotations.Nullable;

public final class MedievalSoldierAdaptiveNavigation {
    private final MedievalSoldierEntity soldier;
    private final MedievalSoldierNavigation route;
    private final MedievalSoldierPathNodeEvaluator nodeEvaluator;
    private final MedievalSoldierPathMemory pathMemory;
    private final MedievalSoldierPassageController passageController;
    private final MedievalSoldierCorridorEvaluator corridorEvaluator;
    private final MedievalSoldierLadderTopology ladderTopology;
    private final MedievalSoldierLadderDebug ladderDebug;
    private final MedievalSoldierLadderTraversalController ladderTraversal;
    private final MedievalSoldierLadderRouteSelector ladderRouteSelector;
    private int repathCooldown;
    private int stuckTicks;
    private int collisionTicks;
    private int detourTicks;
    private int crowdDetourCooldown;
    private int passageSearchCooldown;
    @Nullable
    private class_2338 passageWaypoint;
    private int climbSearchCooldown;
    @Nullable
    private class_2338 climbWaypoint;
    @Nullable
    private class_2338 climbExitWaypoint;
    private int climbExitTicks;
    private int climbReentryCooldown;
    private int rescueContinuationTicks;
    private double lastX;
    private double lastZ;
    private double detourX;
    private double detourY;
    private double detourZ;

    public MedievalSoldierAdaptiveNavigation(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
        this.route = new MedievalSoldierNavigation(soldier);
        this.nodeEvaluator = new MedievalSoldierPathNodeEvaluator();
        this.pathMemory = new MedievalSoldierPathMemory();
        this.passageController = new MedievalSoldierPassageController(soldier, this.nodeEvaluator);
        this.corridorEvaluator = new MedievalSoldierCorridorEvaluator(soldier, this.nodeEvaluator);
        this.ladderTopology = new MedievalSoldierLadderTopology(soldier, this.nodeEvaluator);
        this.ladderDebug = new MedievalSoldierLadderDebug(soldier);
        this.ladderTraversal = new MedievalSoldierLadderTraversalController(soldier, this.ladderTopology, this.ladderDebug);
        this.ladderRouteSelector = new MedievalSoldierLadderRouteSelector(soldier, this.ladderTopology);
    }

    private void trySelectLadderRoute(double targetX, double targetY, double targetZ) {
        if (!this.ladderRouteSelector.shouldSearch(targetY, this.climbSearchCooldown, this.climbReentryCooldown, this.stuckTicks)) {
            return;
        }
        this.climbSearchCooldown = 18 + Math.floorMod(this.soldier.method_5628(), 7);
        this.climbWaypoint = this.ladderRouteSelector.findUsefulNearbyClimbable(targetX, targetY, targetZ);
        if (this.climbWaypoint == null) {
            this.ladderDebug.state("NO_LADDER_ROUTE", null, null, "target=" + String.valueOf(class_2338.method_49637((double)targetX, (double)targetY, (double)targetZ)));
            return;
        }
        MedievalSoldierLadderTraversalController.Direction direction = this.ladderDirectionFor(targetY, this.climbWaypoint);
        this.ladderTraversal.beginApproach(this.climbWaypoint, this.ladderRouteSelector.selectedExitRung(), this.ladderRouteSelector.selectedExitFeet(), direction, targetX, targetY, targetZ);
        this.ladderDebug.event("ROUTE_LADDER_FOUND", this.ladderTopology.climbColumnBottom(this.climbWaypoint), this.climbWaypoint, "target=" + String.valueOf(class_2338.method_49637((double)targetX, (double)targetY, (double)targetZ)));
    }

    private MedievalSoldierLadderTraversalController.Direction ladderDirectionFor(double targetY, class_2338 entryRung) {
        return targetY >= (double)entryRung.method_10264() ? MedievalSoldierLadderTraversalController.Direction.UP : MedievalSoldierLadderTraversalController.Direction.DOWN;
    }

    public void reset() {
        this.route.stop();
        this.repathCooldown = Math.floorMod(this.soldier.method_5628(), 4);
        this.clearTransientRouteState();
        this.crowdDetourCooldown = 0;
        this.passageSearchCooldown = Math.floorMod(this.soldier.method_5628(), 3);
        this.climbSearchCooldown = Math.floorMod(this.soldier.method_5628(), 7);
        this.lastX = this.soldier.method_23317();
        this.lastZ = this.soldier.method_23321();
    }

    public void stop() {
        this.clearTransientRouteState();
        this.soldier.cleanupRecoveryBlocksForNavigation();
        this.route.stop();
    }

    private void clearTransientRouteState() {
        this.detourTicks = 0;
        this.passageWaypoint = null;
        this.climbWaypoint = null;
        this.climbExitWaypoint = null;
        this.climbExitTicks = 0;
        this.climbReentryCooldown = 0;
        this.rescueContinuationTicks = 0;
        this.stuckTicks = 0;
        this.collisionTicks = 0;
        this.pathMemory.clear();
        MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667());
        this.ladderTraversal.reset();
    }

    public boolean isLadderRouteActive() {
        return this.ladderTraversal.hasApproach() || this.ladderTraversal.isCommitted() || this.climbWaypoint != null || this.climbExitWaypoint != null;
    }

    public void tickToEntity(class_1297 target, double speed) {
        if (target == null) {
            return;
        }
        this.tick(target.method_23317(), target.method_23318(), target.method_23321(), target, speed);
    }

    public void tickToPoint(class_243 point, double speed) {
        if (point == null) {
            return;
        }
        this.tick(point.field_1352, point.field_1351, point.field_1350, null, speed);
    }

    public void tickToBlock(class_2338 target, boolean targetIsFloorBlock, double speed) {
        if (target == null) {
            return;
        }
        class_243 point = this.resolveBlockTarget(target, targetIsFloorBlock);
        this.tick(point.field_1352, point.field_1351, point.field_1350, null, speed);
    }

    public void tick(double targetX, double targetY, double targetZ, @Nullable class_1297 targetEntity, double speed) {
        double dx = targetX - this.soldier.method_23317();
        double dz = targetZ - this.soldier.method_23321();
        double distanceSq = dx * dx + dz * dz;
        if (Math.floorMod(this.soldier.field_6012 + this.soldier.method_5628(), 2) == 0) {
            this.passageController.tryOpenPassage(targetX, targetZ);
        }
        if (this.passageSearchCooldown > 0) {
            --this.passageSearchCooldown;
        }
        if (this.passageWaypoint == null && this.climbWaypoint == null && this.climbExitWaypoint == null && !this.soldier.isOnClimbableSurface() && this.passageSearchCooldown <= 0 && distanceSq > 4.0 && (this.route.isIdle() || this.stuckTicks >= 4) && this.passageController.shouldSearchForPassage(targetX, targetY, targetZ) && this.passageController.isDirectTravelBlocked(targetX, targetY, targetZ)) {
            class_2338 nearbyPassage = this.passageController.findUsefulNearbyPassage(targetX, targetY, targetZ);
            int n = this.passageSearchCooldown = nearbyPassage == null ? 18 : 8;
            if (nearbyPassage != null) {
                this.passageWaypoint = nearbyPassage;
                this.route.stop();
                this.repathCooldown = 0;
            }
        }
        if (this.passageWaypoint != null) {
            class_2680 passageState = this.soldier.method_37908().method_8320(this.passageWaypoint);
            if (this.passageController.isUsablePassage(passageState) && !MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), this.passageWaypoint, MedievalSoldierCrowdCoordinator.Lane.PASSAGE, this.soldier.method_5667(), this.soldier.method_37908().method_8510(), 18)) {
                this.route.stop();
                this.repathCooldown = 4 + Math.floorMod(this.soldier.method_5628(), 5);
                this.updateStuckState(distanceSq);
                return;
            }
            double pdx = (double)this.passageWaypoint.method_10263() + 0.5 - this.soldier.method_23317();
            double pdz = (double)this.passageWaypoint.method_10260() + 0.5 - this.soldier.method_23321();
            double passageDistanceSq = pdx * pdx + pdz * pdz;
            if (!this.passageController.isUsablePassage(passageState)) {
                this.passageWaypoint = null;
                this.repathCooldown = 0;
            } else {
                if (passageDistanceSq <= 4.0 || this.passageController.isPassageOpen(passageState)) {
                    this.passageController.openPassageAt(this.passageWaypoint);
                    double throughX = targetX - ((double)this.passageWaypoint.method_10263() + 0.5);
                    double throughZ = targetZ - ((double)this.passageWaypoint.method_10260() + 0.5);
                    double throughLen = Math.sqrt(throughX * throughX + throughZ * throughZ);
                    if (throughLen > 0.001) {
                        this.detourX = (double)this.passageWaypoint.method_10263() + 0.5 + (throughX /= throughLen) * 2.15;
                        this.detourY = this.passageWaypoint.method_10264();
                        this.detourZ = (double)this.passageWaypoint.method_10260() + 0.5 + (throughZ /= throughLen) * 2.15;
                        this.detourTicks = 44;
                        this.route.moveTo(this.detourX, this.detourY, this.detourZ, speed);
                    }
                    this.passageWaypoint = null;
                    this.repathCooldown = 6;
                    return;
                }
                if (this.repathCooldown-- <= 0 || this.route.isIdle()) {
                    this.soldier.method_5942().method_6337((double)this.passageWaypoint.method_10263() + 0.5, (double)this.passageWaypoint.method_10264(), (double)this.passageWaypoint.method_10260() + 0.5, speed);
                    this.repathCooldown = 8;
                }
                this.updateStuckState(distanceSq);
                return;
            }
        }
        if (this.climbExitWaypoint != null) {
            MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), this.climbExitWaypoint, MedievalSoldierCrowdCoordinator.Lane.CLIMB_EXIT, this.soldier.method_5667(), this.soldier.method_37908().method_8510(), 24);
            if (--this.climbExitTicks <= 0) {
                MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667(), MedievalSoldierCrowdCoordinator.Lane.CLIMB_EXIT);
                MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667(), MedievalSoldierCrowdCoordinator.Lane.CLIMB);
                this.climbExitWaypoint = null;
            } else {
                double ex = (double)this.climbExitWaypoint.method_10263() + 0.5 - this.soldier.method_23317();
                double ey = (double)this.climbExitWaypoint.method_10264() - this.soldier.method_23318();
                double ez = (double)this.climbExitWaypoint.method_10260() + 0.5 - this.soldier.method_23321();
                if (ex * ex + ez * ez <= 0.36 && Math.abs(ey) <= 0.75) {
                    MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667(), MedievalSoldierCrowdCoordinator.Lane.CLIMB_EXIT);
                    MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667(), MedievalSoldierCrowdCoordinator.Lane.CLIMB);
                    this.climbExitWaypoint = null;
                    this.climbWaypoint = null;
                    this.ladderTraversal.clearQueue();
                    this.ladderTraversal.clearCommitment();
                    this.climbReentryCooldown = Math.max(this.climbReentryCooldown, 8);
                    this.climbSearchCooldown = Math.max(this.climbSearchCooldown, 6);
                    if (targetEntity != null) {
                        this.route.moveTo(targetEntity, speed);
                    } else {
                        this.route.moveTo(targetX, targetY, targetZ, speed);
                    }
                    this.repathCooldown = 8;
                } else {
                    this.route.stop();
                    double horizontal = Math.sqrt(ex * ex + ez * ez);
                    if (horizontal > 0.001) {
                        ex /= horizontal;
                        ez /= horizontal;
                    }
                    class_243 current = this.soldier.method_18798();
                    double targetYVelocity = ey > 0.2 ? Math.max(current.field_1351 * 0.35, 0.1) : (ey < -0.35 ? Math.min(current.field_1351 * 0.35, -0.06) : class_3532.method_15350((double)(current.field_1351 * 0.25), (double)-0.04, (double)0.045));
                    this.soldier.method_18800(current.field_1352 * 0.1 + ex * 0.2, targetYVelocity, current.field_1350 * 0.1 + ez * 0.2);
                    this.soldier.field_6007 = true;
                    this.soldier.field_6017 = 0.0f;
                    this.updateStuckState(distanceSq);
                    return;
                }
            }
        }
        if (this.climbSearchCooldown > 0) {
            --this.climbSearchCooldown;
        }
        if (this.climbReentryCooldown > 0) {
            --this.climbReentryCooldown;
        }
        if (this.ladderTraversal.hasApproach()) {
            if (this.ladderTraversal.approachStillValid(targetX, targetY, targetZ)) {
                this.climbWaypoint = this.ladderTraversal.approachRung();
            } else {
                class_2338 releasedRung = this.ladderTraversal.approachRung();
                this.ladderDebug.event("ENTRY_APPROACH_RELEASE", releasedRung == null ? null : this.ladderTopology.climbColumnBottom(releasedRung), releasedRung, "target changed, direction changed, or ladder invalid");
                this.ladderTraversal.clearApproach();
                this.ladderTraversal.clearQueue();
                this.climbWaypoint = null;
                this.climbSearchCooldown = 0;
            }
        }
        if (this.ladderTraversal.queueColumn() != null && this.climbReentryCooldown <= 0) {
            if (this.ladderTraversal.tickQueue(this.route, speed)) {
                this.stuckTicks = 0;
                this.collisionTicks = 0;
                this.repathCooldown = 2;
                return;
            }
            this.climbSearchCooldown = 0;
        }
        if (this.climbWaypoint == null) {
            this.trySelectLadderRoute(targetX, targetY, targetZ);
        }
        if (this.climbWaypoint != null) {
            class_2680 climbState = this.soldier.method_37908().method_8320(this.climbWaypoint);
            if (!climbState.method_26164(class_3481.field_22414)) {
                this.ladderDebug.event("WAYPOINT_INVALID", this.climbWaypoint, null, "selected rung no longer climbable");
                this.ladderTraversal.clearApproach();
                this.climbWaypoint = null;
            } else {
                class_2338 top;
                class_2338 descentLip;
                double cdx = (double)this.climbWaypoint.method_10263() + 0.5 - this.soldier.method_23317();
                double cdz = (double)this.climbWaypoint.method_10260() + 0.5 - this.soldier.method_23321();
                double cdist = cdx * cdx + cdz * cdz;
                if (targetY > this.soldier.method_23318() + 1.45 && !this.ladderTraversal.isTouchingColumn(this.climbWaypoint)) {
                    class_2338 accessRung = this.ladderTopology.findClimbAccessRungNearY(this.climbWaypoint, this.soldier.method_23318());
                    class_2338 entryLip = this.ladderTopology.findClimbEntryLip(accessRung, targetX, targetZ);
                    class_2338 columnBottom = this.ladderTopology.climbColumnBottom(this.climbWaypoint);
                    if (entryLip != null && accessRung.method_10264() > columnBottom.method_10264() + 1) {
                        this.ladderDebug.state("APPROACH_MID_ENTRY", columnBottom, entryLip, "rung=" + String.valueOf(accessRung));
                        if (this.ladderTraversal.approachEntryLip(this.route, entryLip, accessRung, speed, MedievalSoldierLadderTraversalController.Direction.UP)) {
                            this.stuckTicks = 0;
                            this.collisionTicks = 0;
                            return;
                        }
                        long now = this.soldier.method_37908().method_8510();
                        if (!MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), accessRung, MedievalSoldierCrowdCoordinator.Lane.CLIMB, this.soldier.method_5667(), now, 24)) {
                            this.route.stop();
                            this.stuckTicks = 0;
                            this.collisionTicks = 0;
                            return;
                        }
                        this.ladderTraversal.commit(accessRung, MedievalSoldierLadderTraversalController.Direction.UP);
                        this.climbWaypoint = null;
                        this.route.stop();
                        double tx = (double)accessRung.method_10263() + 0.5 - this.soldier.method_23317();
                        double tz = (double)accessRung.method_10260() + 0.5 - this.soldier.method_23321();
                        double len = Math.sqrt(Math.max(1.0E-4, tx * tx + tz * tz));
                        class_243 current = this.soldier.method_18798();
                        this.soldier.method_18800(current.field_1352 * 0.06 + tx / len * 0.16, Math.max(current.field_1351, 0.12), current.field_1350 * 0.06 + tz / len * 0.16);
                        this.soldier.field_6007 = true;
                        this.soldier.field_6017 = 0.0f;
                        this.repathCooldown = 1;
                        if (this.tryUseClimbable(targetX, targetY, targetZ, speed)) {
                            this.updateStuckState(distanceSq);
                        }
                        return;
                    }
                }
                if (targetY < this.soldier.method_23318() - 1.45 && !this.ladderTraversal.isTouchingColumn(this.climbWaypoint) && (descentLip = this.ladderTopology.findClimbDescentLip(top = this.ladderTopology.findClimbAccessRungNearY(this.climbWaypoint, this.soldier.method_23318()), targetX, targetZ)) != null) {
                    this.ladderDebug.state("APPROACH_TOP_ENTRY", this.ladderTopology.climbColumnBottom(top), descentLip, "rung=" + String.valueOf(top));
                    long now = this.soldier.method_37908().method_8510();
                    boolean ownsTopEntry = MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), top, MedievalSoldierCrowdCoordinator.Lane.CLIMB, this.soldier.method_5667(), now, 24);
                    if (!ownsTopEntry) {
                        this.ladderDebug.state("QUEUE_TOP", this.ladderTopology.climbColumnBottom(top), descentLip, "top mouth reserved by another NPC");
                        this.ladderTraversal.queue(top, MedievalSoldierLadderTraversalController.Direction.DOWN);
                        if (this.ladderTraversal.tickQueue(this.route, speed)) {
                            this.stuckTicks = 0;
                            this.collisionTicks = 0;
                            return;
                        }
                    }
                    if (this.ladderTraversal.approachEntryLip(this.route, descentLip, top, speed, MedievalSoldierLadderTraversalController.Direction.DOWN)) {
                        this.stuckTicks = 0;
                        this.collisionTicks = 0;
                        return;
                    }
                    this.ladderTraversal.commit(top, MedievalSoldierLadderTraversalController.Direction.DOWN);
                    this.climbWaypoint = null;
                    this.route.stop();
                    double tx = (double)top.method_10263() + 0.5 - this.soldier.method_23317();
                    double tz = (double)top.method_10260() + 0.5 - this.soldier.method_23321();
                    double len = Math.sqrt(Math.max(1.0E-4, tx * tx + tz * tz));
                    class_243 current = this.soldier.method_18798();
                    this.soldier.method_18800(current.field_1352 * 0.06 + tx / len * 0.16, Math.min(current.field_1351, -0.085), current.field_1350 * 0.06 + tz / len * 0.16);
                    this.soldier.field_6007 = true;
                    this.soldier.field_6017 = 0.0f;
                    this.repathCooldown = 1;
                    if (this.tryUseClimbable(targetX, targetY, targetZ, speed)) {
                        this.updateStuckState(distanceSq);
                        return;
                    }
                    return;
                }
                boolean touchingColumn = this.ladderTraversal.isTouchingColumn(this.climbWaypoint);
                class_2338 entry = this.climbWaypoint;
                if (!touchingColumn && targetY > this.soldier.method_23318() + 1.45) {
                    class_2338 approach = this.ladderRouteSelector.findReachableEntryApproach(entry, targetX, targetZ);
                    if (approach == null) {
                        this.ladderDebug.event("ENTRY_APPROACH_INVALID", entry, this.climbWaypoint, "selected ladder has no ground-path-reachable entry mouth");
                        this.ladderTraversal.clearApproach();
                        this.climbWaypoint = null;
                        this.climbSearchCooldown = 0;
                        this.route.stop();
                        return;
                    }
                    this.ladderDebug.state("APPROACH_BOTTOM", entry, approach, "targetY=" + String.format(Locale.ROOT, "%.2f", targetY));
                    if (this.ladderTraversal.approachEntryLip(this.route, approach, entry, speed, MedievalSoldierLadderTraversalController.Direction.UP)) {
                        this.updateStuckState(distanceSq);
                        return;
                    }
                    long now = this.soldier.method_37908().method_8510();
                    if (!MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), entry, MedievalSoldierCrowdCoordinator.Lane.CLIMB, this.soldier.method_5667(), now, 20)) {
                        this.ladderTraversal.clearApproach();
                        this.ladderTraversal.queue(entry);
                        this.climbWaypoint = null;
                        class_243 queuePoint = this.ladderTopology.findClimbQueuePoint(entry);
                        if (queuePoint != null) {
                            this.route.moveTo(queuePoint.field_1352, queuePoint.field_1351, queuePoint.field_1350, Math.min(speed, 0.84));
                        } else {
                            this.route.stop();
                        }
                        this.stuckTicks = 0;
                        this.collisionTicks = 0;
                        return;
                    }
                    this.ladderTraversal.commit(entry, MedievalSoldierLadderTraversalController.Direction.UP);
                    this.climbWaypoint = null;
                    this.route.stop();
                    this.repathCooldown = 1;
                    if (entry.method_10264() >= class_3532.method_15357((double)this.soldier.method_23318())) {
                        this.soldier.method_5993().method_6233();
                    }
                    if (this.tryUseClimbable(targetX, targetY, targetZ, speed)) {
                        this.updateStuckState(distanceSq);
                    }
                    return;
                }
                if (!touchingColumn && cdist > 2.25) {
                    this.route.moveTo((double)this.climbWaypoint.method_10263() + 0.5, Math.min(this.soldier.method_23318(), (double)this.climbWaypoint.method_10264()), (double)this.climbWaypoint.method_10260() + 0.5, speed);
                    this.updateStuckState(distanceSq);
                    return;
                }
                if (touchingColumn) {
                    this.ladderTraversal.commit(entry, this.ladderTraversal.directionTowardTarget(targetY));
                    this.climbWaypoint = null;
                    if (this.tryUseClimbable(targetX, targetY, targetZ, speed)) {
                        this.updateStuckState(distanceSq);
                        return;
                    }
                } else {
                    this.route.moveTo((double)this.climbWaypoint.method_10263() + 0.5, this.soldier.method_23318(), (double)this.climbWaypoint.method_10260() + 0.5, speed);
                    this.updateStuckState(distanceSq);
                    return;
                }
            }
        }
        if (this.rescueContinuationTicks > 0) {
            --this.rescueContinuationTicks;
        } else if (!this.soldier.getRecoveryStateForNavigation().getPlacedBlocks().isEmpty() && this.soldier.getRecoveryStateForNavigation().canUseEmergencyBlock(this.soldier) && this.tryEmergencyRescueBlock(speed)) {
            return;
        }
        if (this.climbReentryCooldown <= 0 && this.ladderTraversal.isCommitted() && this.tryUseClimbable(targetX, targetY, targetZ, speed)) {
            this.ladderDebug.state("CLIMBING", this.ladderTraversal.committedColumn(), this.soldier.method_24515(), "target=" + String.valueOf(class_2338.method_49637((double)targetX, (double)targetY, (double)targetZ)));
            this.updateStuckState(distanceSq);
            return;
        }
        this.collisionTicks = this.soldier.field_5976 && this.soldier.method_24828() ? ++this.collisionTicks : Math.max(0, this.collisionTicks - 1);
        if (this.detourTicks > 0) {
            double ddz;
            --this.detourTicks;
            double ddx = this.detourX - this.soldier.method_23317();
            if (ddx * ddx + (ddz = this.detourZ - this.soldier.method_23321()) * ddz <= 1.0) {
                this.detourTicks = 0;
                this.repathCooldown = 0;
            } else if (this.route.isIdle()) {
                this.pathMemory.markFailed(class_2338.method_49637((double)this.detourX, (double)this.detourY, (double)this.detourZ), this.soldier.method_37908().method_8510(), 80);
                this.detourTicks = 0;
                this.repathCooldown = 0;
            } else if (this.repathCooldown-- <= 0) {
                this.route.moveTo(this.detourX, this.detourY, this.detourZ, speed);
                this.repathCooldown = 6;
            }
            this.updateStuckState(distanceSq);
            return;
        }
        if (this.repathCooldown-- <= 0 || this.route.isIdle()) {
            if (targetEntity != null) {
                this.route.moveTo(targetEntity, speed);
            } else {
                this.route.moveTo(targetX, targetY, targetZ, speed);
            }
            this.repathCooldown = 10;
        }
        this.updateStuckState(distanceSq);
        if (this.crowdDetourCooldown > 0) {
            --this.crowdDetourCooldown;
        }
        if (distanceSq > 4.0 && this.crowdDetourCooldown <= 0 && (this.stuckTicks >= 20 || this.collisionTicks >= 8) && this.corridorEvaluator.hasSoldierBlockingAhead(targetX, targetZ, targetEntity)) {
            if (this.corridorEvaluator.isNarrowPassage(targetX, targetZ)) {
                class_2338 queueCell = this.corridorEvaluator.projectedPassageCell(targetX, targetZ);
                long now = this.soldier.method_37908().method_8510();
                if (queueCell != null && MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), queueCell, MedievalSoldierCrowdCoordinator.Lane.CHOKEPOINT, this.soldier.method_5667(), now, 18)) {
                    this.crowdDetourCooldown = 5;
                    this.collisionTicks = Math.max(0, this.collisionTicks - 2);
                    return;
                }
                this.route.stop();
                double vx = targetX - this.soldier.method_23317();
                double vz = targetZ - this.soldier.method_23321();
                double len = Math.sqrt(Math.max(1.0E-4, vx * vx + vz * vz));
                double yieldX = this.soldier.method_23317() - vx / len * 0.35;
                double yieldZ = this.soldier.method_23321() - vz / len * 0.35;
                class_243 yieldPoint = new class_243(yieldX, this.soldier.method_23318(), yieldZ);
                if (this.soldier.isSafeNavigationPosition(yieldPoint)) {
                    this.soldier.method_5962().method_6239(yieldX, this.soldier.method_23318(), yieldZ, 0.55);
                }
                this.crowdDetourCooldown = 7 + Math.floorMod(this.soldier.method_5628(), 7);
                this.repathCooldown = 2 + Math.floorMod(this.soldier.method_5628(), 3);
                this.collisionTicks = Math.max(0, this.collisionTicks - 3);
                return;
            }
            if (this.tryDetour(targetX, targetY, targetZ, speed)) {
                this.crowdDetourCooldown = 12;
                this.collisionTicks = 0;
                this.stuckTicks = 0;
                return;
            }
            this.crowdDetourCooldown = 6;
        }
        if (distanceSq > 6.25 && (this.route.isIdle() || this.stuckTicks >= 35) && (this.collisionTicks >= 6 || this.stuckTicks >= 25)) {
            if (this.tryDetour(targetX, targetY, targetZ, speed)) {
                this.collisionTicks = 0;
                this.stuckTicks = 0;
            } else if (this.stuckTicks >= 60 && this.tryEmergencyRescueBlock(speed)) {
                this.collisionTicks = 0;
                this.stuckTicks = 20;
            } else if (this.stuckTicks >= 50 && this.tryWideRecovery(targetX, targetY, targetZ, speed)) {
                this.collisionTicks = 0;
                this.stuckTicks = 10;
            }
        }
    }

    private boolean tryUseClimbable(double targetX, double targetY, double targetZ, double speed) {
        double yVelocity;
        boolean atIntermediateExit;
        class_2338 climbBlock = this.ladderTraversal.nearestTouchingClimbable();
        if (climbBlock == null) {
            if (!this.ladderTraversal.isCommitted() || this.ladderTraversal.committedColumn() == null) {
                return false;
            }
            class_2338 probe = new class_2338(this.ladderTraversal.committedColumn().method_10263(), class_3532.method_15357((double)this.soldier.method_23318()), this.ladderTraversal.committedColumn().method_10260());
            if (!this.soldier.method_37908().method_8320(probe).method_26164(class_3481.field_22414)) {
                class_2338 below = probe.method_10074();
                class_2338 above = probe.method_10084();
                if (this.soldier.method_37908().method_8320(below).method_26164(class_3481.field_22414)) {
                    probe = below;
                } else if (this.soldier.method_37908().method_8320(above).method_26164(class_3481.field_22414)) {
                    probe = above;
                } else {
                    return false;
                }
            }
            climbBlock = probe;
        }
        if (!this.ladderTraversal.isCommitted()) {
            return false;
        }
        if (this.soldier.isOnClimbableSurface()) {
            this.ladderTraversal.maintainEntryReservation(targetY);
        }
        double verticalDelta = targetY - this.soldier.method_23318();
        class_2338 top = this.ladderTopology.climbColumnTop(climbBlock);
        boolean atTopSection = this.soldier.method_23318() >= (double)top.method_10264() - 0.42;
        boolean wantsUpperOrLevelExit = this.ladderTraversal.isAscending() && verticalDelta > -0.18;
        class_2338 intermediateRung = this.ladderTraversal.plannedExitRung();
        if (intermediateRung == null) {
            intermediateRung = this.ladderTopology.findTargetFloorRung(climbBlock, targetX, targetY, targetZ);
        }
        class_2338 committedEntryRung = this.ladderTraversal.committedEntryRung();
        class_2338 plannedIntermediateFeet = this.ladderTraversal.plannedExitFeet();
        boolean ascendingIntermediateExit = this.ladderTraversal.isAscending() && intermediateRung != null && committedEntryRung != null && intermediateRung.method_10264() > committedEntryRung.method_10264() && intermediateRung.method_10264() < top.method_10264() && (plannedIntermediateFeet == null ? Math.abs(this.soldier.method_23318() - (double)intermediateRung.method_10264()) <= 0.72 : Math.abs(this.soldier.method_23318() - (double)plannedIntermediateFeet.method_10264()) <= 0.82) && verticalDelta > -0.35;
        boolean descendingIntermediateExit = this.ladderTraversal.isDescending() && intermediateRung != null && committedEntryRung != null && intermediateRung.method_10264() < committedEntryRung.method_10264() && intermediateRung.method_10264() > this.ladderTopology.climbColumnBottom(climbBlock).method_10264() && Math.abs(this.soldier.method_23318() - (double)intermediateRung.method_10264()) <= 0.72 && verticalDelta < 0.35;
        boolean bl = atIntermediateExit = ascendingIntermediateExit || descendingIntermediateExit;
        if (atIntermediateExit) {
            class_2338 intermediateExit = this.ladderTraversal.plannedExitFeet();
            if (intermediateExit == null) {
                intermediateExit = this.ladderTopology.findClimbExitFrom(intermediateRung, targetX, targetY, targetZ);
            }
            if (intermediateExit != null) {
                this.ladderDebug.event("EXIT_INTERMEDIATE", this.ladderTraversal.committedColumn(), intermediateExit, "rung=" + String.valueOf(intermediateRung));
                if (this.beginClimbExit(intermediateRung, intermediateExit)) {
                    return true;
                }
            } else {
                this.ladderDebug.state("EXIT_INTERMEDIATE_MISSING", this.ladderTraversal.committedColumn(), intermediateRung, "no usable side cell");
            }
        }
        if (atTopSection && wantsUpperOrLevelExit) {
            class_2338 climbExit;
            class_2338 class_23382 = climbExit = top.equals((Object)this.ladderTraversal.plannedExitRung()) ? this.ladderTraversal.plannedExitFeet() : null;
            if (climbExit == null) {
                climbExit = this.ladderTopology.findClimbExitFrom(top, targetX, targetY, targetZ);
            }
            if (climbExit != null) {
                this.ladderDebug.state("EXIT_TOP", this.ladderTraversal.committedColumn(), climbExit, "top=" + String.valueOf(top));
                this.route.stop();
                if (this.beginClimbExit(top, climbExit)) {
                    return true;
                }
            } else {
                this.ladderDebug.state("EXIT_TOP_MISSING", this.ladderTraversal.committedColumn(), top, "no usable top exit toward target");
            }
        }
        if (this.ladderTraversal.needsDescentEntryAssist(top)) {
            this.route.stop();
            double centerX = (double)top.method_10263() + 0.5 - this.soldier.method_23317();
            double centerZ = (double)top.method_10260() + 0.5 - this.soldier.method_23321();
            class_243 current = this.soldier.method_18798();
            this.soldier.method_18800(class_3532.method_15350((double)(current.field_1352 * 0.04 + centerX * 0.18), (double)-0.09, (double)0.09), Math.min(current.field_1351, -0.16), class_3532.method_15350((double)(current.field_1350 * 0.04 + centerZ * 0.18), (double)-0.09, (double)0.09));
            this.soldier.field_6007 = true;
            this.soldier.field_6017 = 0.0f;
            this.ladderTraversal.tickProgress();
            this.repathCooldown = 2;
            return true;
        }
        if (!this.ladderTraversal.isCommitted()) {
            class_2338 entry = this.ladderTopology.climbColumnBottom(climbBlock);
            long climbNow = this.soldier.method_37908().method_8510();
            boolean entryFree = MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), entry, MedievalSoldierCrowdCoordinator.Lane.CLIMB, this.soldier.method_5667(), climbNow, 14);
            if (!entryFree) {
                this.ladderTraversal.queue(entry);
                class_243 queuePoint = this.ladderTopology.findClimbQueuePoint(entry);
                if (queuePoint != null && this.soldier.method_5707(queuePoint) > 0.7224999999999999) {
                    this.route.moveTo(queuePoint.field_1352, queuePoint.field_1351, queuePoint.field_1350, Math.min(speed, 0.88));
                } else {
                    this.route.stop();
                }
                this.stuckTicks = 0;
                this.collisionTicks = 0;
                return true;
            }
            this.ladderTraversal.commit(entry, this.ladderTraversal.directionTowardTarget(targetY));
        }
        if (Math.abs(verticalDelta) < 0.3 && !this.ladderTraversal.isCommitted()) {
            MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667(), MedievalSoldierCrowdCoordinator.Lane.CLIMB);
            return false;
        }
        this.ladderTraversal.tickProgress();
        double cx = (double)climbBlock.method_10263() + 0.5 - this.soldier.method_23317();
        double cz = (double)climbBlock.method_10260() + 0.5 - this.soldier.method_23321();
        double horizontal = Math.sqrt(cx * cx + cz * cz);
        double dirX = horizontal > 0.001 ? cx / horizontal : 0.0;
        double dirZ = horizontal > 0.001 ? cz / horizontal : 0.0;
        double d = yVelocity = this.ladderTraversal.isDescending() ? -0.095 : 0.205;
        if (this.ladderTraversal.isDescending()) {
            class_2338 bottom = this.ladderTopology.climbColumnBottom(climbBlock);
            if (this.soldier.method_23318() <= (double)bottom.method_10264() + 1.24) {
                double tz;
                class_2338 bottomExit = this.ladderTopology.findClimbBottomExitFrom(bottom, targetX, targetY, targetZ);
                if (bottomExit != null) {
                    this.ladderDebug.state("EXIT_BOTTOM", this.ladderTraversal.committedColumn(), bottomExit, "bottom=" + String.valueOf(bottom));
                    long now = this.soldier.method_37908().method_8510();
                    if (MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), bottomExit, MedievalSoldierCrowdCoordinator.Lane.CLIMB_EXIT, this.soldier.method_5667(), now, 20)) {
                        MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667(), MedievalSoldierCrowdCoordinator.Lane.CLIMB);
                        this.climbWaypoint = null;
                        this.ladderTraversal.clearCommitment();
                        this.climbReentryCooldown = 18;
                        this.climbSearchCooldown = Math.max(this.climbSearchCooldown, 18);
                        this.climbExitWaypoint = bottomExit.method_10062();
                        this.climbExitTicks = 36;
                        this.route.stop();
                        double ex = (double)bottomExit.method_10263() + 0.5 - this.soldier.method_23317();
                        double ez = (double)bottomExit.method_10260() + 0.5 - this.soldier.method_23321();
                        double elen = Math.sqrt(ex * ex + ez * ez);
                        if (elen > 0.001) {
                            ex /= elen;
                            ez /= elen;
                        }
                        class_243 exitVelocity = this.soldier.method_18798();
                        boolean lowerExit = bottomExit.method_10264() < bottom.method_10264();
                        this.soldier.method_18800(exitVelocity.field_1352 * 0.1 + ex * (lowerExit ? 0.13 : 0.18), lowerExit ? Math.min(exitVelocity.field_1351, -0.085) : Math.max(exitVelocity.field_1351, -0.035), exitVelocity.field_1350 * 0.1 + ez * (lowerExit ? 0.13 : 0.18));
                        this.soldier.field_6007 = true;
                        this.soldier.field_6017 = 0.0f;
                        this.repathCooldown = 3;
                        return true;
                    }
                }
                if (bottomExit == null) {
                    this.ladderDebug.state("EXIT_BOTTOM_MISSING", this.ladderTraversal.committedColumn(), bottom, "no usable bottom exit toward target");
                }
                this.climbReentryCooldown = Math.max(this.climbReentryCooldown, 6);
                yVelocity = -0.04;
                double tx = targetX - this.soldier.method_23317();
                double tlen = Math.sqrt(tx * tx + (tz = targetZ - this.soldier.method_23321()) * tz);
                if (tlen > 0.001) {
                    dirX = tx / tlen;
                    dirZ = tz / tlen;
                }
            }
        }
        class_243 current = this.soldier.method_18798();
        this.route.stop();
        this.soldier.method_18800(class_3532.method_15350((double)(current.field_1352 * 0.14 + dirX * 0.09), (double)-0.11, (double)0.11), yVelocity, class_3532.method_15350((double)(current.field_1350 * 0.14 + dirZ * 0.09), (double)-0.11, (double)0.11));
        this.soldier.field_6007 = true;
        this.soldier.field_6017 = 0.0f;
        this.repathCooldown = 3;
        return true;
    }

    private void holdBelowBusyTopExit(class_2338 top) {
        this.route.stop();
        double centerX = (double)top.method_10263() + 0.5 - this.soldier.method_23317();
        double centerZ = (double)top.method_10260() + 0.5 - this.soldier.method_23321();
        double holdY = (double)top.method_10264() - 0.72;
        double dy = holdY - this.soldier.method_23318();
        class_243 current = this.soldier.method_18798();
        this.soldier.method_18800(class_3532.method_15350((double)(current.field_1352 * 0.05 + centerX * 0.14), (double)-0.065, (double)0.065), class_3532.method_15350((double)(dy * 0.16), (double)-0.085, (double)0.055), class_3532.method_15350((double)(current.field_1350 * 0.05 + centerZ * 0.14), (double)-0.065, (double)0.065));
        this.soldier.field_6007 = true;
        this.soldier.field_6017 = 0.0f;
        this.ladderDebug.state("EXIT_TOP_QUEUE", this.ladderTraversal.committedColumn(), top, "exit occupied; holding below rim");
    }

    private boolean beginClimbExit(class_2338 rung, class_2338 climbExit) {
        class_2338 exitMouth = this.ladderTopology.climbExitMouth(rung, climbExit);
        long now = this.soldier.method_37908().method_8510();
        if (!MedievalSoldierCrowdCoordinator.tryReserve((class_5321<class_1937>)this.soldier.method_37908().method_27983(), exitMouth, MedievalSoldierCrowdCoordinator.Lane.CLIMB_EXIT, this.soldier.method_5667(), now, 24)) {
            this.ladderDebug.state("EXIT_RESERVED", this.ladderTraversal.committedColumn(), exitMouth, "waiting for ladder mouth; selectedExit=" + String.valueOf(climbExit));
            this.route.stop();
            this.repathCooldown = 4 + Math.floorMod(this.soldier.method_5628(), 4);
            return true;
        }
        MedievalSoldierCrowdCoordinator.releaseOwned(this.soldier.method_5667(), MedievalSoldierCrowdCoordinator.Lane.CLIMB);
        this.ladderTraversal.clearCommitment();
        this.climbWaypoint = null;
        this.climbExitWaypoint = exitMouth.method_10062();
        this.climbExitTicks = 32;
        double ex = (double)exitMouth.method_10263() + 0.5 - this.soldier.method_23317();
        double ez = (double)exitMouth.method_10260() + 0.5 - this.soldier.method_23321();
        double len = Math.sqrt(ex * ex + ez * ez);
        if (len > 0.001) {
            ex /= len;
            ez /= len;
        }
        class_243 current = this.soldier.method_18798();
        this.route.stop();
        this.soldier.method_18800(current.field_1352 * 0.06 + ex * 0.285, Math.max(0.055, class_3532.method_15350((double)(current.field_1351 * 0.3), (double)-0.01, (double)0.08)), current.field_1350 * 0.06 + ez * 0.285);
        this.soldier.field_6007 = true;
        this.soldier.field_6017 = 0.0f;
        this.repathCooldown = 2;
        return true;
    }

    private boolean tryWideRecovery(double targetX, double targetY, double targetZ, double speed) {
        class_2338 center = this.soldier.method_24515();
        class_243 best = null;
        double bestScore = Double.MAX_VALUE;
        double currentTargetSq = this.soldier.method_5649(targetX, targetY, targetZ);
        for (int ring = 2; ring <= 4; ++ring) {
            for (int dx = -ring; dx <= ring; ++dx) {
                for (int dz = -ring; dz <= ring; ++dz) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) continue;
                    for (int dy : new int[]{0, 1, -1}) {
                        double score;
                        class_243 candidate;
                        double targetSq;
                        class_2338 feet = center.method_10069(dx, dy, dz);
                        if (!this.isWalkableFeet(feet) || (targetSq = (candidate = new class_243((double)feet.method_10263() + 0.5, (double)feet.method_10264(), (double)feet.method_10260() + 0.5)).method_1028(targetX, targetY, targetZ)) > currentTargetSq + 9.0 || !((score = targetSq + this.soldier.method_5707(candidate) * 0.2) < bestScore)) continue;
                        bestScore = score;
                        best = candidate;
                    }
                }
            }
            if (best != null) break;
        }
        if (best == null) {
            return false;
        }
        this.detourX = best.field_1352;
        this.detourY = best.field_1351;
        this.detourZ = best.field_1350;
        this.detourTicks = 35;
        this.route.stop();
        this.route.moveTo(this.detourX, this.detourY, this.detourZ, speed);
        this.repathCooldown = 6;
        return true;
    }

    private boolean tryEmergencyRescueBlock(double speed) {
        double toOriginZ;
        class_1937 class_19372 = this.soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return false;
        }
        class_3218 world = (class_3218)class_19372;
        if (!this.soldier.getRecoveryStateForNavigation().canUseEmergencyBlock(this.soldier)) {
            return false;
        }
        class_2338 origin = this.soldier.getRecoveryStateForNavigation().getAccidentOrigin();
        if (origin == null) {
            return false;
        }
        class_2338 current = this.soldier.method_24515();
        int stepY = current.method_10264();
        if (stepY + 1 > origin.method_10264()) {
            return false;
        }
        double toOriginX = (double)origin.method_10263() + 0.5 - this.soldier.method_23317();
        double originLen = Math.sqrt(toOriginX * toOriginX + (toOriginZ = (double)origin.method_10260() + 0.5 - this.soldier.method_23321()) * toOriginZ);
        if (originLen < 0.001) {
            toOriginX = 1.0;
            toOriginZ = 0.0;
            originLen = 1.0;
        }
        toOriginX /= originLen;
        toOriginZ /= originLen;
        class_2338 best = null;
        double bestScore = Double.MAX_VALUE;
        class_2338 localStep = current;
        if (this.canPlaceEmergencyStep(world, localStep, origin)) {
            best = localStep;
            bestScore = -1.0;
        }
        for (int ox = -1; ox <= 1; ++ox) {
            for (int oz = -1; oz <= 1; ++oz) {
                double dz;
                double dx;
                double score;
                double dot;
                class_2338 place;
                if (ox == 0 && oz == 0 || !this.canPlaceEmergencyStep(world, place = new class_2338(current.method_10263() + ox, stepY, current.method_10260() + oz), origin) || (dot = (double)ox * toOriginX + (double)oz * toOriginZ) <= 0.05 || !((score = (dx = (double)(place.method_10263() - origin.method_10263())) * dx + (dz = (double)(place.method_10260() - origin.method_10260())) * dz - dot * 0.5) < bestScore)) continue;
                bestScore = score;
                best = place;
            }
        }
        if (best == null) {
            return false;
        }
        if (!world.method_8501(best, ModBlocks.NPC_RESCUE_BLOCK.method_9564())) {
            return false;
        }
        this.soldier.getRecoveryStateForNavigation().recordPlacedBlock(best);
        this.rescueContinuationTicks = 14;
        if (best.equals((Object)current)) {
            class_243 velocity = this.soldier.method_18798();
            this.soldier.method_18800(velocity.field_1352, Math.max(velocity.field_1351, 0.42), velocity.field_1350);
            this.soldier.field_6007 = true;
            this.soldier.method_5993().method_6233();
        }
        this.detourX = (double)best.method_10263() + 0.5;
        this.detourY = (double)best.method_10264() + 1.0;
        this.detourZ = (double)best.method_10260() + 0.5;
        this.detourTicks = 30;
        this.route.stop();
        this.route.moveTo(this.detourX, this.detourY, this.detourZ, speed);
        this.repathCooldown = 6;
        return true;
    }

    private boolean canPlaceEmergencyStep(class_3218 world, class_2338 place, class_2338 origin) {
        class_2338 current = this.soldier.method_24515();
        if (Math.abs(place.method_10263() - current.method_10263()) > 1 || Math.abs(place.method_10260() - current.method_10260()) > 1) {
            return false;
        }
        if (place.method_10264() + 1 > origin.method_10264()) {
            return false;
        }
        class_2680 state = world.method_8320(place);
        if (!state.method_26215() && !state.method_45474()) {
            return false;
        }
        class_2338 support = place.method_10074();
        if (world.method_8320(support).method_26220((class_1922)world, support).method_1110()) {
            return false;
        }
        class_2338 feetOnStep = place.method_10084();
        class_2338 headOnStep = feetOnStep.method_10084();
        if (!world.method_8320(feetOnStep).method_26220((class_1922)world, feetOnStep).method_1110()) {
            return false;
        }
        if (!world.method_8320(headOnStep).method_26220((class_1922)world, headOnStep).method_1110()) {
            return false;
        }
        class_2680 feetState = world.method_8320(feetOnStep);
        return !(feetState.method_26204() instanceof class_2323) && !(feetState.method_26204() instanceof class_2349) && !feetState.method_26164(class_3481.field_22414);
    }

    private void updateStuckState(double distanceSq) {
        double movedZ;
        boolean barelyMoved;
        if (!this.soldier.isStaggeredAiTick(5)) {
            return;
        }
        double movedX = this.soldier.method_23317() - this.lastX;
        boolean bl = barelyMoved = movedX * movedX + (movedZ = this.soldier.method_23321() - this.lastZ) * movedZ < 0.025;
        this.stuckTicks = barelyMoved && distanceSq > 6.25 ? (this.stuckTicks += 5) : Math.max(0, this.stuckTicks - 5);
        this.lastX = this.soldier.method_23317();
        this.lastZ = this.soldier.method_23321();
    }

    private boolean tryDetour(double targetX, double targetY, double targetZ, double speed) {
        double dirZ;
        double dirX = targetX - this.soldier.method_23317();
        double length = Math.sqrt(dirX * dirX + (dirZ = targetZ - this.soldier.method_23321()) * dirZ);
        if (length < 0.001) {
            return false;
        }
        double sideX = -(dirZ /= length);
        double sideZ = dirX /= length;
        int preferredSign = (this.soldier.method_5667().hashCode() & 1) == 0 ? 1 : -1;
        double[] sideDistances = new double[]{1.5, 2.25, 3.25};
        double[] forwardDistances = new double[]{2.0, 1.0, 0.25};
        for (double sideDistance : sideDistances) {
            for (double forwardDistance : forwardDistances) {
                for (int pass = 0; pass < 2; ++pass) {
                    long now;
                    class_2338 detourPos;
                    int sign = pass == 0 ? preferredSign : -preferredSign;
                    double x = this.soldier.method_23317() + dirX * forwardDistance + sideX * sideDistance * (double)sign;
                    double z = this.soldier.method_23321() + dirZ * forwardDistance + sideZ * sideDistance * (double)sign;
                    class_243 walkable = this.findNearbyWalkable(x, this.soldier.method_23318(), z);
                    if (walkable == null || this.pathMemory.isTemporarilyBlocked(detourPos = class_2338.method_49638((class_2374)walkable), now = this.soldier.method_37908().method_8510())) continue;
                    this.detourX = walkable.field_1352;
                    this.detourY = walkable.field_1351;
                    this.detourZ = walkable.field_1350;
                    this.detourTicks = 18;
                    this.repathCooldown = 0;
                    this.route.stop();
                    this.route.moveTo(this.detourX, this.detourY, this.detourZ, speed);
                    return true;
                }
            }
        }
        return false;
    }

    @Nullable
    private class_243 findNearbyWalkable(double x, double y, double z) {
        return this.nodeEvaluator.findNearbyWalkable(this.soldier.method_37908(), this.soldier.method_24515(), x, y, z);
    }

    private boolean isWalkableFeet(class_2338 feet) {
        return this.nodeEvaluator.isWalkableFeet(this.soldier.method_37908(), feet);
    }

    private class_243 resolveBlockTarget(class_2338 target, boolean targetIsFloorBlock) {
        return this.nodeEvaluator.resolveBlockTarget(this.soldier.method_37908(), target, targetIsFloorBlock);
    }

    @Nullable
    public class_243 findWalkableNear(class_2338 center, int radius) {
        return this.nodeEvaluator.findWalkableNear(this.soldier.method_37908(), center, radius, this.soldier.method_19538());
    }
}

