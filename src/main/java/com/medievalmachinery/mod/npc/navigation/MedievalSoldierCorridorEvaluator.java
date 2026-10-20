/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1922
 *  net.minecraft.class_2323
 *  net.minecraft.class_2338
 *  net.minecraft.class_2349
 *  net.minecraft.class_238
 *  net.minecraft.class_2680
 *  net.minecraft.class_3532
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.navigation;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierPathNodeEvaluator;
import net.minecraft.class_1297;
import net.minecraft.class_1922;
import net.minecraft.class_2323;
import net.minecraft.class_2338;
import net.minecraft.class_2349;
import net.minecraft.class_238;
import net.minecraft.class_2680;
import net.minecraft.class_3532;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierCorridorEvaluator {
    private final MedievalSoldierEntity soldier;
    private final MedievalSoldierPathNodeEvaluator nodeEvaluator;

    MedievalSoldierCorridorEvaluator(MedievalSoldierEntity soldier, MedievalSoldierPathNodeEvaluator nodeEvaluator) {
        this.soldier = soldier;
        this.nodeEvaluator = nodeEvaluator;
    }

    @Nullable
    class_2338 projectedPassageCell(double targetX, double targetZ) {
        double dz;
        double dx = targetX - this.soldier.method_23317();
        double len = Math.sqrt(dx * dx + (dz = targetZ - this.soldier.method_23321()) * dz);
        if (len < 0.001) {
            return null;
        }
        dx /= len;
        dz /= len;
        int y = class_3532.method_15357((double)this.soldier.method_23318());
        for (double forward : new double[]{1.15, 1.75, 2.25}) {
            class_2338 feet = new class_2338(class_3532.method_15357((double)(this.soldier.method_23317() + dx * forward)), y, class_3532.method_15357((double)(this.soldier.method_23321() + dz * forward)));
            if (this.nodeEvaluator.isWalkableFeet(this.soldier.method_37908(), feet)) {
                return feet;
            }
            if (!this.nodeEvaluator.isWalkableFeet(this.soldier.method_37908(), feet.method_10084())) continue;
            return feet.method_10084();
        }
        return null;
    }

    boolean isNarrowPassage(double targetX, double targetZ) {
        double dirZ;
        double dirX = targetX - this.soldier.method_23317();
        double length = Math.sqrt(dirX * dirX + (dirZ = targetZ - this.soldier.method_23321()) * dirZ);
        if (length < 0.001) {
            return false;
        }
        double sideX = -(dirZ /= length);
        double sideZ = dirX /= length;
        int baseY = class_3532.method_15357((double)this.soldier.method_23318());
        for (double forward : new double[]{0.65, 1.2}) {
            class_2338 center = new class_2338(class_3532.method_15357((double)(this.soldier.method_23317() + dirX * forward)), baseY, class_3532.method_15357((double)(this.soldier.method_23321() + dirZ * forward)));
            class_2680 state = this.soldier.method_37908().method_8320(center);
            if (!(state.method_26204() instanceof class_2323) && !(state.method_26204() instanceof class_2349)) continue;
            return true;
        }
        return this.passageSideBlocked(sideX, sideZ, 0.85) && this.passageSideBlocked(-sideX, -sideZ, 0.85);
    }

    boolean hasSoldierBlockingAhead(double targetX, double targetZ, @Nullable class_1297 targetEntity) {
        double dirZ;
        double dirX = targetX - this.soldier.method_23317();
        double length = Math.sqrt(dirX * dirX + (dirZ = targetZ - this.soldier.method_23321()) * dirZ);
        if (length < 0.001) {
            return false;
        }
        class_238 ahead = this.soldier.method_5829().method_1012((dirX /= length) * 2.75, 0.0, (dirZ /= length) * 2.75).method_1009(0.55, 0.2, 0.55);
        return !this.soldier.method_37908().method_8390(MedievalSoldierEntity.class, ahead, other -> other != this.soldier && other != targetEntity && other.method_5805()).isEmpty();
    }

    private boolean passageSideBlocked(double sideX, double sideZ, double distance) {
        int x = class_3532.method_15357((double)(this.soldier.method_23317() + sideX * distance));
        int z = class_3532.method_15357((double)(this.soldier.method_23321() + sideZ * distance));
        int y = class_3532.method_15357((double)this.soldier.method_23318());
        class_2338 feet = new class_2338(x, y, z);
        class_2338 head = feet.method_10084();
        return !this.soldier.method_37908().method_8320(feet).method_26220((class_1922)this.soldier.method_37908(), feet).method_1110() || !this.soldier.method_37908().method_8320(head).method_26220((class_1922)this.soldier.method_37908(), head).method_1110();
    }
}

