/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_11
 *  net.minecraft.class_2338
 *  net.minecraft.class_3481
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.navigation;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierLadderTopology;
import java.util.HashSet;
import java.util.PriorityQueue;
import net.minecraft.class_11;
import net.minecraft.class_2338;
import net.minecraft.class_3481;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierLadderRouteSelector {
    private static final int MIN_SEARCH_RADIUS = 20;
    private static final int MAX_SEARCH_RADIUS = 40;
    private static final int EXIT_GROUND_SEARCH_RADIUS = 24;
    private static final int EXIT_GROUND_SEARCH_NODES = 768;
    private static final int[][] CARDINAL = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private final MedievalSoldierEntity soldier;
    private final MedievalSoldierLadderTopology topology;
    @Nullable
    private class_2338 selectedExitRung;
    @Nullable
    private class_2338 selectedExitFeet;
    @Nullable
    private class_2338 selectedEntryApproach;

    MedievalSoldierLadderRouteSelector(MedievalSoldierEntity soldier, MedievalSoldierLadderTopology topology) {
        this.soldier = soldier;
        this.topology = topology;
    }

    boolean shouldSearch(double targetY, int searchCooldown, int reentryCooldown, int stuckTicks) {
        if (Math.abs(targetY - this.soldier.method_23318()) <= 1.45 || searchCooldown > 0 || reentryCooldown > 0) {
            return false;
        }
        return targetY < this.soldier.method_23318() - 1.45 || targetY > this.soldier.method_23318() + 1.45 || stuckTicks >= 10;
    }

    @Nullable
    class_2338 findUsefulNearbyClimbable(double targetX, double targetY, double targetZ) {
        this.selectedExitRung = null;
        this.selectedExitFeet = null;
        this.selectedEntryApproach = null;
        double verticalDelta = targetY - this.soldier.method_23318();
        if (Math.abs(verticalDelta) < 0.75) {
            return null;
        }
        boolean goingUp = verticalDelta > 0.0;
        class_2338 center = this.soldier.method_24515();
        class_2338 bestRung = null;
        class_2338 bestExitRung = null;
        class_2338 bestExitFeet = null;
        class_2338 bestEntryApproach = null;
        double bestScore = Double.MAX_VALUE;
        HashSet<class_2338> scoredColumns = new HashSet<class_2338>();
        double horizontalTargetDistance = Math.hypot(targetX - this.soldier.method_23317(), targetZ - this.soldier.method_23321());
        int searchRadius = Math.min(40, Math.max(20, (int)Math.ceil(horizontalTargetDistance) + 12));
        for (int radius = 1; radius <= searchRadius; ++radius) {
            for (int dx = -radius; dx <= radius; ++dx) {
                for (int dz = -radius; dz <= radius; ++dz) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != radius) continue;
                    int minY = Math.min(center.method_10264(), (int)Math.floor(targetY)) - 4;
                    int maxY = Math.max(center.method_10264(), (int)Math.ceil(targetY)) + 4;
                    for (int y = minY; y <= maxY; ++y) {
                        int groundSteps;
                        ExitCandidate exit;
                        class_2338 bottom;
                        class_2338 rung = new class_2338(center.method_10263() + dx, y, center.method_10260() + dz);
                        if (!this.soldier.method_37908().method_8320(rung).method_26164(class_3481.field_22414) || !scoredColumns.add(bottom = this.topology.climbColumnBottom(rung).method_10062())) continue;
                        class_2338 top = this.topology.climbColumnTop(rung);
                        class_2338 entryRung = this.topology.findClimbAccessRungNearY(rung, this.soldier.method_23318());
                        EntryCandidate entry = this.findReachableEntry(entryRung, targetX, targetZ, goingUp);
                        if (entry == null) continue;
                        ExitCandidate exitCandidate = exit = goingUp ? this.usefulUpperExit(rung, entryRung, top, targetX, targetY, targetZ) : this.usefulLowerExit(entryRung, bottom, targetX, targetY, targetZ);
                        if (exit == null || (groundSteps = this.groundStepsToTarget(exit.exit(), targetX, targetY, targetZ)) < 0) continue;
                        double climbSteps = Math.abs(exit.rung().method_10264() - entryRung.method_10264());
                        double score = entry.pathLength() + climbSteps * 0.85 + (double)groundSteps;
                        if (!(score < bestScore)) continue;
                        bestScore = score;
                        bestRung = entryRung.method_10062();
                        bestEntryApproach = entry.approach().method_10062();
                        bestExitRung = exit.rung().method_10062();
                        bestExitFeet = exit.exit().method_10062();
                    }
                }
            }
            if (bestRung != null && radius >= 6) break;
        }
        this.selectedEntryApproach = bestEntryApproach;
        this.selectedExitRung = bestExitRung;
        this.selectedExitFeet = bestExitFeet;
        return bestRung;
    }

    @Nullable
    class_2338 selectedExitRung() {
        return this.selectedExitRung;
    }

    @Nullable
    class_2338 selectedExitFeet() {
        return this.selectedExitFeet;
    }

    @Nullable
    class_2338 selectedEntryApproach() {
        return this.selectedEntryApproach;
    }

    @Nullable
    class_2338 findReachableGroundApproach(class_2338 rung) {
        EntryCandidate entry = this.findReachableGroundEntry(rung);
        return entry == null ? null : entry.approach();
    }

    @Nullable
    class_2338 findReachableEntryApproach(class_2338 rung, double targetX, double targetZ) {
        if (this.selectedEntryApproach != null) {
            return this.selectedEntryApproach;
        }
        EntryCandidate entry = this.findReachableEntry(rung, targetX, targetZ, true);
        return entry == null ? null : entry.approach();
    }

    @Nullable
    private EntryCandidate findReachableEntry(class_2338 rung, double targetX, double targetZ, boolean goingUp) {
        class_11 path;
        if (!goingUp) {
            class_2338 lip = this.topology.findClimbDescentLip(rung, targetX, targetZ);
            if (lip == null) {
                return null;
            }
            class_11 path2 = this.soldier.method_5942().method_6348(lip, 0);
            return path2 != null && path2.method_21655() ? new EntryCandidate(lip, path2.method_38()) : null;
        }
        class_2338 preferred = this.topology.findClimbEntryLip(rung, targetX, targetZ);
        if (preferred != null && (path = this.soldier.method_5942().method_6348(preferred, 0)) != null && path.method_21655()) {
            return new EntryCandidate(preferred, path.method_38());
        }
        return this.findReachableGroundEntry(rung);
    }

    @Nullable
    private EntryCandidate findReachableGroundEntry(class_2338 rung) {
        class_2338 best = null;
        double bestLength = Double.MAX_VALUE;
        for (class_2338 candidate : this.topology.findClimbGroundApproaches(rung)) {
            double length;
            class_11 path = this.soldier.method_5942().method_6348(candidate, 0);
            if (path == null || !path.method_21655() || !((length = (double)path.method_38()) < bestLength)) continue;
            bestLength = length;
            best = candidate;
        }
        return best == null ? null : new EntryCandidate(best, bestLength);
    }

    @Nullable
    private ExitCandidate usefulUpperExit(class_2338 anyRung, class_2338 entryRung, class_2338 top, double targetX, double targetY, double targetZ) {
        if (top.method_10264() <= entryRung.method_10264()) {
            return null;
        }
        ExitCandidate best = null;
        double bestScore = Double.MAX_VALUE;
        for (int y = entryRung.method_10264() + 1; y <= top.method_10264(); ++y) {
            class_2338 candidateRung = new class_2338(anyRung.method_10263(), y, anyRung.method_10260());
            for (class_2338 exit : this.topology.findClimbExits(candidateRung)) {
                double score;
                int ground;
                if (!this.makesVerticalProgress(exit, targetY, true) || (ground = this.groundStepsToTarget(exit, targetX, targetY, targetZ)) < 0 || !((score = Math.abs((double)exit.method_10264() - targetY) * 2.0 + (double)ground) < bestScore)) continue;
                bestScore = score;
                best = new ExitCandidate(candidateRung.method_10062(), exit.method_10062());
            }
        }
        return best;
    }

    @Nullable
    private ExitCandidate usefulLowerExit(class_2338 entryRung, class_2338 bottom, double targetX, double targetY, double targetZ) {
        if (bottom.method_10264() >= entryRung.method_10264()) {
            return null;
        }
        ExitCandidate best = null;
        double bestScore = Double.MAX_VALUE;
        for (int y = entryRung.method_10264() - 1; y >= bottom.method_10264(); --y) {
            class_2338 candidateRung = new class_2338(entryRung.method_10263(), y, entryRung.method_10260());
            for (class_2338 exit : this.topology.findClimbExits(candidateRung)) {
                double score;
                int ground;
                if (!this.makesVerticalProgress(exit, targetY, false) || (ground = this.groundStepsToTarget(exit, targetX, targetY, targetZ)) < 0 || !((score = Math.abs((double)exit.method_10264() - targetY) * 2.0 + (double)ground) < bestScore)) continue;
                bestScore = score;
                best = new ExitCandidate(candidateRung.method_10062(), exit.method_10062());
            }
        }
        return best;
    }

    private int groundStepsToTarget(class_2338 start, double targetX, double targetY, double targetZ) {
        class_2338 target = class_2338.method_49637((double)targetX, (double)targetY, (double)targetZ);
        PriorityQueue<GroundNode> open = new PriorityQueue<GroundNode>((a, b) -> Integer.compare(a.score(), b.score()));
        HashSet<class_2338> visited = new HashSet<class_2338>();
        open.add(new GroundNode(start.method_10062(), 0, this.groundHeuristic(start, target)));
        visited.add(start.method_10062());
        int expanded = 0;
        while (!open.isEmpty() && expanded++ < 768) {
            int dzTarget;
            GroundNode node = (GroundNode)open.remove();
            class_2338 pos = node.pos();
            int dxTarget = pos.method_10263() - target.method_10263();
            if (dxTarget * dxTarget + (dzTarget = pos.method_10260() - target.method_10260()) * dzTarget <= 2 && Math.abs((double)pos.method_10264() - targetY) <= 1.25) {
                return node.steps();
            }
            if (Math.abs(pos.method_10263() - start.method_10263()) > 24 || Math.abs(pos.method_10260() - start.method_10260()) > 24) continue;
            block1: for (int[] d : CARDINAL) {
                for (int dy : new int[]{0, 1, -1}) {
                    class_2338 next = pos.method_10069(d[0], dy, d[1]);
                    if (visited.contains(next) || !this.topology.isConnectorGroundFeet(next)) continue;
                    class_2338 immutable = next.method_10062();
                    visited.add(immutable);
                    int steps = node.steps() + 1;
                    open.add(new GroundNode(immutable, steps, steps + this.groundHeuristic(immutable, target)));
                    continue block1;
                }
            }
        }
        return -1;
    }

    private int groundHeuristic(class_2338 pos, class_2338 target) {
        return Math.abs(pos.method_10263() - target.method_10263()) + Math.abs(pos.method_10260() - target.method_10260()) + Math.abs(pos.method_10264() - target.method_10264()) * 2;
    }

    private boolean makesVerticalProgress(@Nullable class_2338 exit, double targetY, boolean goingUp) {
        if (exit == null) {
            return false;
        }
        double before = Math.abs(targetY - this.soldier.method_23318());
        double after = Math.abs(targetY - (double)exit.method_10264());
        if (after >= before - 0.35) {
            return false;
        }
        return goingUp ? (double)exit.method_10264() > this.soldier.method_23318() + 0.45 : (double)exit.method_10264() < this.soldier.method_23318() - 0.45;
    }

    private record EntryCandidate(class_2338 approach, double pathLength) {
    }

    private record ExitCandidate(class_2338 rung, class_2338 exit) {
    }

    private record GroundNode(class_2338 pos, int steps, int score) {
    }
}

