/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_3481
 *  net.minecraft.class_3532
 *  net.minecraft.class_5321
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.navigation;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierCrowdCoordinator;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierPathNodeEvaluator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_3481;
import net.minecraft.class_3532;
import net.minecraft.class_5321;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierLadderTopology {
    private static final int MAX_COLUMN_SCAN = 32;
    private static final int[][] CARDINAL = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private final MedievalSoldierEntity soldier;
    private final MedievalSoldierPathNodeEvaluator nodeEvaluator;

    MedievalSoldierLadderTopology(MedievalSoldierEntity soldier, MedievalSoldierPathNodeEvaluator nodeEvaluator) {
        this.soldier = soldier;
        this.nodeEvaluator = nodeEvaluator;
    }

    class_2338 climbColumnTop(class_2338 climbBlock) {
        class_2338 top = climbBlock;
        for (int i = 0; i < 32; ++i) {
            class_2338 above = top.method_10084();
            if (!this.soldier.method_37908().method_8320(above).method_26164(class_3481.field_22414)) break;
            top = above;
        }
        return top;
    }

    class_2338 climbColumnBottom(class_2338 climbBlock) {
        class_2338 bottom = climbBlock;
        for (int i = 0; i < 32; ++i) {
            class_2338 below = bottom.method_10074();
            if (!this.soldier.method_37908().method_8320(below).method_26164(class_3481.field_22414)) break;
            bottom = below;
        }
        return bottom;
    }

    List<class_2338> findClimbGroundApproaches(class_2338 rung) {
        ArrayList<class_2338> approaches = new ArrayList<class_2338>();
        for (int[] offset : CARDINAL) {
            for (int yOffset : new int[]{0, -1, 1}) {
                class_2338 candidate = new class_2338(rung.method_10263() + offset[0], rung.method_10264() + yOffset, rung.method_10260() + offset[1]);
                if (!this.nodeEvaluator.isBodyClear(this.soldier.method_37908(), candidate) || this.soldier.method_37908().method_8320(candidate.method_10074()).method_26220((class_1922)this.soldier.method_37908(), candidate.method_10074()).method_1110()) continue;
                approaches.add(candidate.method_10062());
            }
        }
        return approaches;
    }

    @Nullable
    class_2338 findClimbGroundApproach(class_2338 rung) {
        class_2338 best = null;
        double bestScore = Double.MAX_VALUE;
        for (class_2338 candidate : this.findClimbGroundApproaches(rung)) {
            double dx = (double)candidate.method_10263() + 0.5 - this.soldier.method_23317();
            double dy = (double)candidate.method_10264() - this.soldier.method_23318();
            double dz = (double)candidate.method_10260() + 0.5 - this.soldier.method_23321();
            double score = dx * dx + dz * dz + Math.abs(dy) * 0.75;
            if (!(score < bestScore)) continue;
            bestScore = score;
            best = candidate;
        }
        return best;
    }

    @Nullable
    class_243 findClimbTopQueuePoint(class_2338 top) {
        int start = Math.floorMod(this.soldier.method_5667().hashCode(), 12);
        class_243 fallback = null;
        for (int step = 0; step < 12; ++step) {
            int index = (start + step) % 12;
            int radius = 2 + index / 4;
            int[] dir = CARDINAL[index % 4];
            for (int yOffset : new int[]{1, 0}) {
                class_2338 c = new class_2338(top.method_10263() + dir[0] * radius, top.method_10264() + yOffset, top.method_10260() + dir[1] * radius);
                if (!this.nodeEvaluator.isWalkableFeet(this.soldier.method_37908(), c)) continue;
                class_243 point = class_243.method_24955((class_2382)c);
                if (fallback == null) {
                    fallback = point;
                }
                class_238 cell = new class_238(c).method_1009(0.18, 0.1, 0.18);
                if (!this.soldier.method_37908().method_8390(MedievalSoldierEntity.class, cell, o -> o != this.soldier && o.method_5805()).isEmpty()) continue;
                return point;
            }
        }
        return fallback;
    }

    @Nullable
    class_2338 findClimbEntryLip(class_2338 accessRung, double targetX, double targetZ) {
        class_2338 best = null;
        double bestScore = Double.MAX_VALUE;
        for (int yOffset : new int[]{1, 0}) {
            for (int[] offset : CARDINAL) {
                double tz;
                double tx;
                double sz;
                double sx;
                double score;
                class_2338 candidate = new class_2338(accessRung.method_10263() + offset[0], accessRung.method_10264() + yOffset, accessRung.method_10260() + offset[1]);
                if (!this.nodeEvaluator.isClimbExitFeet(this.soldier.method_37908(), candidate, accessRung) || !((score = (sx = (double)candidate.method_10263() + 0.5 - this.soldier.method_23317()) * sx + (sz = (double)candidate.method_10260() + 0.5 - this.soldier.method_23321()) * sz + ((tx = (double)candidate.method_10263() + 0.5 - targetX) * tx + (tz = (double)candidate.method_10260() + 0.5 - targetZ) * tz) * 0.08) < bestScore)) continue;
                bestScore = score;
                best = candidate;
            }
        }
        return best;
    }

    boolean isAtEntryLip(class_2338 lip) {
        double dz;
        double dx = (double)lip.method_10263() + 0.5 - this.soldier.method_23317();
        return dx * dx + (dz = (double)lip.method_10260() + 0.5 - this.soldier.method_23321()) * dz <= 0.3364 && Math.abs((double)lip.method_10264() - this.soldier.method_23318()) <= 0.85;
    }

    @Nullable
    class_2338 findClimbDescentLip(class_2338 accessRung, double targetX, double targetZ) {
        return this.findClimbEntryLip(accessRung, targetX, targetZ);
    }

    class_2338 findClimbAccessRungNearY(class_2338 anyRung, double requestedY) {
        class_2338 top;
        class_2338 bottom = this.climbColumnBottom(anyRung);
        class_2338 best = top = this.climbColumnTop(anyRung);
        double bestDistance = Double.MAX_VALUE;
        int preferredY = class_3532.method_15357((double)requestedY);
        for (int y = bottom.method_10264(); y <= top.method_10264(); ++y) {
            class_2338 rung = new class_2338(anyRung.method_10263(), y, anyRung.method_10260());
            double distance = Math.abs(y - preferredY);
            if (distance > bestDistance + 1.0 || this.findClimbExitFrom(rung, this.soldier.method_23317(), requestedY, this.soldier.method_23321()) == null || !(distance < bestDistance)) continue;
            bestDistance = distance;
            best = rung;
        }
        return best;
    }

    @Nullable
    class_2338 findTargetFloorRung(class_2338 anyRung, double targetX, double targetY, double targetZ) {
        class_2338 rung = this.findClimbAccessRungNearY(anyRung, targetY);
        return this.findClimbExitFrom(rung, targetX, targetY, targetZ) == null ? null : rung;
    }

    boolean isWalkableFeet(class_2338 feet) {
        return this.nodeEvaluator.isWalkableFeet(this.soldier.method_37908(), feet);
    }

    boolean isConnectorGroundFeet(class_2338 feet) {
        if (!this.nodeEvaluator.isBodyClear(this.soldier.method_37908(), feet)) {
            return false;
        }
        class_2338 below = feet.method_10074();
        return !this.soldier.method_37908().method_8320(below).method_26220((class_1922)this.soldier.method_37908(), below).method_1110();
    }

    List<class_2338> findClimbExits(class_2338 rung) {
        ArrayList<class_2338> exits = new ArrayList<class_2338>();
        for (int yOffset : new int[]{1, 0, 2}) {
            block1: for (int[] offset : CARDINAL) {
                int feetY = rung.method_10264() + yOffset;
                class_2338 lip = new class_2338(rung.method_10263() + offset[0], feetY, rung.method_10260() + offset[1]);
                if (!this.nodeEvaluator.isClimbExitFeet(this.soldier.method_37908(), lip, rung)) continue;
                for (int distance = 1; distance <= 4; ++distance) {
                    class_2338 candidate = new class_2338(rung.method_10263() + offset[0] * distance, feetY, rung.method_10260() + offset[1] * distance);
                    if (distance > 1 && !this.nodeEvaluator.isWalkableFeet(this.soldier.method_37908(), candidate)) continue block1;
                    exits.add(candidate.method_10062());
                }
            }
        }
        return exits;
    }

    @Nullable
    class_2338 findClimbExitFrom(class_2338 rung, double targetX, double targetY, double targetZ) {
        class_2338 best = null;
        double bestScore = Double.MAX_VALUE;
        for (int yOffset : new int[]{1, 0, 2}) {
            block1: for (int[] offset : CARDINAL) {
                int feetY = rung.method_10264() + yOffset;
                class_2338 lip = new class_2338(rung.method_10263() + offset[0], feetY, rung.method_10260() + offset[1]);
                if (!this.nodeEvaluator.isClimbExitFeet(this.soldier.method_37908(), lip, rung)) continue;
                for (int distance = 1; distance <= 4; ++distance) {
                    class_2338 c = new class_2338(rung.method_10263() + offset[0] * distance, feetY, rung.method_10260() + offset[1] * distance);
                    if (distance > 1 && !this.nodeEvaluator.isWalkableFeet(this.soldier.method_37908(), c)) continue block1;
                    long now = this.soldier.method_37908().method_8510();
                    if (MedievalSoldierCrowdCoordinator.isReservedByOther((class_5321<class_1937>)this.soldier.method_37908().method_27983(), c, MedievalSoldierCrowdCoordinator.Lane.CLIMB_EXIT, this.soldier.method_5667(), now) || this.isClimbExitOccupied(c)) continue;
                    double dx = (double)c.method_10263() + 0.5 - targetX;
                    double dy = (double)c.method_10264() - targetY;
                    double dz = (double)c.method_10260() + 0.5 - targetZ;
                    double score = dx * dx + dz * dz + Math.abs(dy) * 1.25 - (double)Math.min(distance - 1, 2) * 0.22;
                    if (!(score < bestScore)) continue;
                    bestScore = score;
                    best = c;
                }
            }
        }
        return best;
    }

    class_2338 climbExitMouth(class_2338 rung, class_2338 selectedExit) {
        int dx = Integer.compare(selectedExit.method_10263(), rung.method_10263());
        int dz = Integer.compare(selectedExit.method_10260(), rung.method_10260());
        return new class_2338(rung.method_10263() + dx, selectedExit.method_10264(), rung.method_10260() + dz);
    }

    boolean isClimbExitOccupied(class_2338 feet) {
        class_238 cell = new class_238(feet).method_1009(0.1, 0.15, 0.1);
        return !this.soldier.method_37908().method_8390(MedievalSoldierEntity.class, cell, o -> o != this.soldier && o.method_5805()).isEmpty();
    }

    @Nullable
    class_2338 findClimbBottomExitFrom(class_2338 bottom, double targetX, double targetY, double targetZ) {
        int[] ys;
        int[] nArray;
        class_2338 best = null;
        double bestScore = Double.MAX_VALUE;
        boolean gap = this.soldier.method_37908().method_8320(bottom.method_10074()).method_26215();
        if (gap) {
            int[] nArray2 = new int[3];
            nArray2[0] = -1;
            nArray2[1] = 0;
            nArray = nArray2;
            nArray2[2] = 1;
        } else {
            int[] nArray3 = new int[3];
            nArray3[0] = 0;
            nArray3[1] = -1;
            nArray = nArray3;
            nArray3[2] = 1;
        }
        for (int yOffset : ys = nArray) {
            for (int[] offset : CARDINAL) {
                class_2338 c = new class_2338(bottom.method_10263() + offset[0], bottom.method_10264() + yOffset, bottom.method_10260() + offset[1]);
                if (!this.nodeEvaluator.isWalkableFeet(this.soldier.method_37908(), c)) continue;
                double dx = (double)c.method_10263() + 0.5 - targetX;
                double dy = (double)c.method_10264() - targetY;
                double dz = (double)c.method_10260() + 0.5 - targetZ;
                double score = dx * dx + dz * dz + Math.abs(dy) * 1.15;
                if (!(score < bestScore)) continue;
                bestScore = score;
                best = c;
            }
        }
        return best;
    }

    @Nullable
    class_243 findClimbQueuePoint(class_2338 climbBlock) {
        class_2338 bottom = this.climbColumnBottom(climbBlock);
        int floorY = class_3532.method_15357((double)this.soldier.method_23318());
        int start = Math.floorMod(this.soldier.method_5667().hashCode(), 12);
        class_243 fallback = null;
        for (int step = 0; step < 12; ++step) {
            int index = (start + step) % 12;
            int radius = 2 + index / 4;
            int[] offset = CARDINAL[index % 4];
            class_2338 c = new class_2338(bottom.method_10263() + offset[0] * radius, floorY, bottom.method_10260() + offset[1] * radius);
            if (!this.nodeEvaluator.isWalkableFeet(this.soldier.method_37908(), c)) continue;
            class_243 point = class_243.method_24955((class_2382)c);
            if (fallback == null) {
                fallback = point;
            }
            class_238 cell = new class_238(c).method_1009(0.2, 0.1, 0.2);
            if (!this.soldier.method_37908().method_8390(MedievalSoldierEntity.class, cell, o -> o != this.soldier && o.method_5805()).isEmpty()) continue;
            return point;
        }
        return fallback;
    }

    boolean isClimbableLaneOccupied(class_2338 lane, double verticalDelta) {
        class_238 laneBox = new class_238(lane).method_1009(0.16, 1.35, 0.16);
        return !this.soldier.method_37908().method_8390(MedievalSoldierEntity.class, laneBox, o -> {
            if (o == this.soldier || !o.method_5805()) {
                return false;
            }
            if (verticalDelta > 0.0) {
                return o.method_23318() > this.soldier.method_23318() + 0.3;
            }
            if (verticalDelta < 0.0) {
                return o.method_23318() < this.soldier.method_23318() - 0.3;
            }
            return false;
        }).isEmpty();
    }
}

