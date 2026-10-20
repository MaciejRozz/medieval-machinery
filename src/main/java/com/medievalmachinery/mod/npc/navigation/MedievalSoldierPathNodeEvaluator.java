/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2323
 *  net.minecraft.class_2338
 *  net.minecraft.class_2349
 *  net.minecraft.class_2350
 *  net.minecraft.class_2350$class_2353
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_2741
 *  net.minecraft.class_2769
 *  net.minecraft.class_3481
 *  net.minecraft.class_3532
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.navigation;

import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2323;
import net.minecraft.class_2338;
import net.minecraft.class_2349;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2769;
import net.minecraft.class_3481;
import net.minecraft.class_3532;
import org.jetbrains.annotations.Nullable;

public final class MedievalSoldierPathNodeEvaluator {
    public boolean isWalkableFeet(class_1937 world, class_2338 feet) {
        if (world == null || feet == null) {
            return false;
        }
        class_2338 head = feet.method_10084();
        class_2338 below = feet.method_10074();
        if (!world.method_8320(feet).method_26220((class_1922)world, feet).method_1110()) {
            return false;
        }
        if (!world.method_8320(head).method_26220((class_1922)world, head).method_1110()) {
            return false;
        }
        if (world.method_8320(below).method_26220((class_1922)world, below).method_1110()) {
            return false;
        }
        return !this.hasDangerousEdge(world, feet);
    }

    public boolean hasDangerousEdge(class_1937 world, class_2338 feet) {
        for (class_2350 direction : class_2350.class_2353.field_11062) {
            class_2338 side = feet.method_10093(direction);
            if (!world.method_8320(side).method_26220((class_1922)world, side).method_1110()) continue;
            boolean emptyOne = world.method_8320(side.method_10074()).method_26220((class_1922)world, side.method_10074()).method_1110();
            boolean emptyTwo = world.method_8320(side.method_10087(2)).method_26220((class_1922)world, side.method_10087(2)).method_1110();
            if (!emptyOne || !emptyTwo) continue;
            return true;
        }
        return false;
    }

    public boolean isUsablePassage(class_2680 state) {
        return state != null && (state.method_26204() instanceof class_2323 && state.method_26164(class_3481.field_15494) || state.method_26204() instanceof class_2349);
    }

    public boolean isPassageOpen(class_2680 state) {
        return state != null && state.method_28498((class_2769)class_2741.field_12537) && (Boolean)state.method_11654((class_2769)class_2741.field_12537) != false;
    }

    public boolean shouldSearchForPassage(double horizontalDistanceSq, double verticalDistance) {
        return horizontalDistanceSq > 36.0 || verticalDistance > 1.65;
    }

    public boolean isClimbExitFeet(class_1937 world, class_2338 feet, class_2338 ladderTop) {
        if (world == null || feet == null || ladderTop == null) {
            return false;
        }
        if (!this.isBodyClear(world, feet)) {
            return false;
        }
        class_2338 below = feet.method_10074();
        if (world.method_8320(below).method_26220((class_1922)world, below).method_1110()) {
            return false;
        }
        for (class_2350 direction : class_2350.class_2353.field_11062) {
            boolean ladderSide;
            class_2338 side = feet.method_10093(direction);
            if (!world.method_8320(side).method_26220((class_1922)world, side).method_1110()) continue;
            boolean emptyOne = world.method_8320(side.method_10074()).method_26220((class_1922)world, side.method_10074()).method_1110();
            boolean emptyTwo = world.method_8320(side.method_10087(2)).method_26220((class_1922)world, side.method_10087(2)).method_1110();
            if (!emptyOne || !emptyTwo || (ladderSide = side.method_10263() == ladderTop.method_10263() && side.method_10260() == ladderTop.method_10260())) continue;
            return false;
        }
        return true;
    }

    public boolean isBodyClear(class_1937 world, class_2338 feet) {
        if (world == null || feet == null) {
            return false;
        }
        return world.method_8320(feet).method_26220((class_1922)world, feet).method_1110() && world.method_8320(feet.method_10084()).method_26220((class_1922)world, feet.method_10084()).method_1110();
    }

    public boolean isSafeTransition(class_1937 world, class_2338 fromFeet, class_2338 toFeet) {
        if (!this.isWalkableFeet(world, toFeet)) {
            return false;
        }
        int dy = toFeet.method_10264() - fromFeet.method_10264();
        if (dy > 1 || dy < -1) {
            return false;
        }
        int dx = Math.abs(toFeet.method_10263() - fromFeet.method_10263());
        int dz = Math.abs(toFeet.method_10260() - fromFeet.method_10260());
        if (dx == 1 && dz == 1) {
            class_2338 shoulderX = new class_2338(toFeet.method_10263(), fromFeet.method_10264(), fromFeet.method_10260());
            class_2338 shoulderZ = new class_2338(fromFeet.method_10263(), fromFeet.method_10264(), toFeet.method_10260());
            if (!this.isBodyClear(world, shoulderX) || !this.isBodyClear(world, shoulderZ)) {
                return false;
            }
        }
        return true;
    }

    public double localNodeCost(class_1937 world, class_2338 fromFeet, class_2338 feet, double targetX, double targetY, double targetZ) {
        if (!this.isSafeTransition(world, fromFeet, feet)) {
            return Double.POSITIVE_INFINITY;
        }
        double dx = (double)feet.method_10263() + 0.5 - targetX;
        double dy = (double)feet.method_10264() - targetY;
        double dz = (double)feet.method_10260() + 0.5 - targetZ;
        double cost = dx * dx + dz * dz + Math.abs(dy) * 1.5;
        cost += (double)Math.abs(feet.method_10264() - fromFeet.method_10264()) * 0.75;
        int blockedShoulders = 0;
        for (class_2350 direction : class_2350.class_2353.field_11062) {
            if (this.isBodyClear(world, feet.method_10093(direction))) continue;
            ++blockedShoulders;
        }
        return cost + (double)blockedShoulders * 0.35;
    }

    public double localNodeCost(class_1937 world, class_2338 feet, double targetX, double targetY, double targetZ) {
        return this.localNodeCost(world, feet, feet, targetX, targetY, targetZ);
    }

    @Nullable
    public class_243 findNearbyWalkable(class_1937 world, class_2338 from, double x, double y, double z) {
        int baseX = class_3532.method_15357((double)x);
        int baseY = class_3532.method_15357((double)y);
        int baseZ = class_3532.method_15357((double)z);
        for (int yOffset : new int[]{0, 1, -1}) {
            class_2338 feet = new class_2338(baseX, baseY + yOffset, baseZ);
            if (!this.isSafeTransition(world, from, feet)) continue;
            return new class_243((double)feet.method_10263() + 0.5, (double)feet.method_10264(), (double)feet.method_10260() + 0.5);
        }
        return null;
    }

    public class_243 resolveBlockTarget(class_1937 world, class_2338 target, boolean targetIsFloorBlock) {
        if (targetIsFloorBlock) {
            return new class_243((double)target.method_10263() + 0.5, (double)target.method_10264() + 1.0, (double)target.method_10260() + 0.5);
        }
        class_2338 resolved = target;
        if (world.method_8320(resolved).method_26212((class_1922)world, resolved)) {
            for (int i = 0; i < 4 && world.method_8320(resolved = resolved.method_10084()).method_26212((class_1922)world, resolved); ++i) {
            }
        }
        return new class_243((double)resolved.method_10263() + 0.5, (double)resolved.method_10264(), (double)resolved.method_10260() + 0.5);
    }

    @Nullable
    public class_243 findWalkableNear(class_1937 world, class_2338 center, int radius, class_243 origin) {
        class_243 best = null;
        double bestDistanceSq = Double.MAX_VALUE;
        for (int ring = 1; ring <= radius; ++ring) {
            for (int dx = -ring; dx <= ring; ++dx) {
                for (int dz = -ring; dz <= ring; ++dz) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) continue;
                    class_2338 candidate = center.method_10069(dx, 0, dz);
                    for (int yOffset : new int[]{0, 1, -1}) {
                        double z;
                        double y;
                        double x;
                        double distanceSq;
                        class_2338 feet = candidate.method_10069(0, yOffset, 0);
                        if (!this.isWalkableFeet(world, feet) || !((distanceSq = (x = (double)feet.method_10263() + 0.5 - origin.field_1352) * x + (y = (double)feet.method_10264() - origin.field_1351) * y + (z = (double)feet.method_10260() + 0.5 - origin.field_1350) * z) < bestDistanceSq)) continue;
                        bestDistanceSq = distanceSq;
                        best = new class_243((double)feet.method_10263() + 0.5, (double)feet.method_10264(), (double)feet.method_10260() + 0.5);
                    }
                }
            }
            if (best == null) continue;
            return best;
        }
        return best;
    }
}

