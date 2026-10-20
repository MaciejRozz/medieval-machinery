/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2323
 *  net.minecraft.class_2338
 *  net.minecraft.class_2349
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_2741
 *  net.minecraft.class_2756
 *  net.minecraft.class_2769
 *  net.minecraft.class_3481
 *  net.minecraft.class_3532
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.navigation;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierPathNodeEvaluator;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2323;
import net.minecraft.class_2338;
import net.minecraft.class_2349;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2756;
import net.minecraft.class_2769;
import net.minecraft.class_3481;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierPassageController {
    private final MedievalSoldierEntity soldier;
    private final MedievalSoldierPathNodeEvaluator nodeEvaluator;

    MedievalSoldierPassageController(MedievalSoldierEntity soldier, MedievalSoldierPathNodeEvaluator nodeEvaluator) {
        this.soldier = soldier;
        this.nodeEvaluator = nodeEvaluator;
    }

    boolean shouldSearchForPassage(double targetX, double targetY, double targetZ) {
        double dx = targetX - this.soldier.method_23317();
        double dz = targetZ - this.soldier.method_23321();
        return this.nodeEvaluator.shouldSearchForPassage(dx * dx + dz * dz, Math.abs(targetY - this.soldier.method_23318()));
    }

    boolean isDirectTravelBlocked(double targetX, double targetY, double targetZ) {
        class_243 to;
        class_243 from = new class_243(this.soldier.method_23317(), this.soldier.method_23323(0.45), this.soldier.method_23321());
        if (from.method_1025(to = new class_243(targetX, targetY + 0.8, targetZ)) < 4.0) {
            return false;
        }
        class_3965 hit = this.soldier.method_37908().method_17742(new class_3959(from, to, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.soldier));
        return hit.method_17783() == class_239.class_240.field_1332 && hit.method_17784().method_1025(from) + 0.5 < to.method_1025(from);
    }

    boolean isUsablePassage(class_2680 state) {
        return this.nodeEvaluator.isUsablePassage(state);
    }

    boolean isPassageOpen(class_2680 state) {
        return this.nodeEvaluator.isPassageOpen(state);
    }

    class_2338 normalizePassagePos(class_2338 pos) {
        if (pos == null) {
            return null;
        }
        class_2680 state = this.soldier.method_37908().method_8320(pos);
        if (state.method_26204() instanceof class_2323 && state.method_28498((class_2769)class_2741.field_12533) && state.method_11654((class_2769)class_2741.field_12533) == class_2756.field_12609) {
            return pos.method_10074().method_10062();
        }
        return pos.method_10062();
    }

    /*
     * Enabled aggressive block sorting
     */
    void openPassageAt(class_2338 pos) {
        if (pos == null) {
            return;
        }
        class_1937 world = this.soldier.method_37908();
        class_2680 state = world.method_8320(pos = this.normalizePassagePos(pos));
        if (!this.isUsablePassage(state)) return;
        if (this.isPassageOpen(state)) {
            return;
        }
        class_2248 class_22482 = state.method_26204();
        if (class_22482 instanceof class_2323) {
            class_2323 door = (class_2323)class_22482;
            if (state.method_26164(class_3481.field_15494)) {
                door.method_10033((class_1297)this.soldier, world, state, pos, true);
                this.soldier.getNavigationInteractionStateForNavigation().recordOpenedPassage(pos);
                return;
            }
        }
        if (!(state.method_26204() instanceof class_2349)) return;
        world.method_8501(pos, (class_2680)state.method_11657((class_2769)class_2741.field_12537, (Comparable)Boolean.valueOf(true)));
        this.soldier.getNavigationInteractionStateForNavigation().recordOpenedPassage(pos);
    }

    @Nullable
    class_2338 findUsefulNearbyPassage(double targetX, double targetY, double targetZ) {
        class_2338 center = this.soldier.method_24515();
        class_2338 best = null;
        double bestScore = Double.MAX_VALUE;
        for (int radius = 2; radius <= 16; ++radius) {
            for (int dx = -radius; dx <= radius; ++dx) {
                for (int dz = -radius; dz <= radius; ++dz) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != radius) continue;
                    for (int dy = -1; dy <= 2; ++dy) {
                        double score;
                        double routeLen;
                        double maxLateral;
                        double nearestZ;
                        double nearestX;
                        double lateralSq;
                        double pz;
                        double px;
                        double projection;
                        double sz;
                        double routeZ;
                        double sx;
                        double routeX;
                        double routeLenSq;
                        class_2338 pos = center.method_10069(dx, dy, dz);
                        class_2680 state = this.soldier.method_37908().method_8320(pos);
                        if (!this.isUsablePassage(state) || (routeLenSq = (routeX = targetX - (sx = this.soldier.method_23317())) * routeX + (routeZ = targetZ - (sz = this.soldier.method_23321())) * routeZ) < 1.0 || (projection = ((px = (double)pos.method_10263() + 0.5 - sx) * routeX + (pz = (double)pos.method_10260() + 0.5 - sz) * routeZ) / routeLenSq) < -0.05 || projection > 1.2 || (lateralSq = (px - (nearestX = routeX * projection)) * (px - nearestX) + (pz - (nearestZ = routeZ * projection)) * (pz - nearestZ)) > (maxLateral = Math.max(1.15, Math.min(2.25, (routeLen = Math.sqrt(routeLenSq)) * 0.2))) * maxLateral) continue;
                        double fromSoldierSq = px * px + pz * pz;
                        double tx = targetX - ((double)pos.method_10263() + 0.5);
                        double ty = targetY - (double)pos.method_10264();
                        double tz = targetZ - ((double)pos.method_10260() + 0.5);
                        double toTargetSq = tx * tx + tz * tz + ty * ty * 0.2;
                        if (toTargetSq > routeLenSq * 1.08 && projection < 0.35 || !((score = fromSoldierSq * 0.55 + toTargetSq + lateralSq * 2.25) < bestScore)) continue;
                        bestScore = score;
                        best = this.normalizePassagePos(pos);
                    }
                }
            }
            if (best != null && radius >= 5) break;
        }
        return best;
    }

    void tryOpenPassage(double targetX, double targetZ) {
        double dirZ;
        double dirX = targetX - this.soldier.method_23317();
        double length = Math.sqrt(dirX * dirX + (dirZ = targetZ - this.soldier.method_23321()) * dirZ);
        if (length < 0.001) {
            return;
        }
        dirX /= length;
        dirZ /= length;
        class_1937 world = this.soldier.method_37908();
        for (double forward : new double[]{0.55, 1.05, 1.55}) {
            int x = class_3532.method_15357((double)(this.soldier.method_23317() + dirX * forward));
            int z = class_3532.method_15357((double)(this.soldier.method_23321() + dirZ * forward));
            for (int yOffset = 0; yOffset <= 1; ++yOffset) {
                class_2338 managed;
                class_2680 managedState;
                class_2248 class_22482;
                class_2338 pos = new class_2338(x, class_3532.method_15357((double)this.soldier.method_23318()) + yOffset, z);
                class_2680 state = world.method_8320(pos);
                if (!state.method_28498((class_2769)class_2741.field_12537) || ((Boolean)state.method_11654((class_2769)class_2741.field_12537)).booleanValue()) continue;
                if (state.method_26204() instanceof class_2323 && state.method_26164(class_3481.field_15494) && (class_22482 = (managedState = world.method_8320(managed = this.normalizePassagePos(pos))).method_26204()) instanceof class_2323) {
                    class_2323 door = (class_2323)class_22482;
                    door.method_10033((class_1297)this.soldier, world, managedState, managed, true);
                    this.soldier.getNavigationInteractionStateForNavigation().recordOpenedPassage(managed);
                    return;
                }
                if (!(state.method_26204() instanceof class_2349)) continue;
                world.method_8501(pos, (class_2680)state.method_11657((class_2769)class_2741.field_12537, (Comparable)Boolean.valueOf(true)));
                this.soldier.getNavigationInteractionStateForNavigation().recordOpenedPassage(pos);
                return;
            }
        }
    }
}

