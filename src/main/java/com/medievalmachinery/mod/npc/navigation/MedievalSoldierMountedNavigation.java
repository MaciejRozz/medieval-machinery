/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1498
 *  net.minecraft.class_1922
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.navigation;

import net.minecraft.class_1297;
import net.minecraft.class_1498;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import org.jetbrains.annotations.Nullable;

public final class MedievalSoldierMountedNavigation {
    private final class_1498 horse;
    @Nullable
    private class_243 lastTarget;
    private int repathCooldown;
    private double lastSpeed;
    private int jumpCooldown;
    private int descentCooldown;
    private static final int MAX_SAFE_DESCENT_BLOCKS = 3;

    public MedievalSoldierMountedNavigation(class_1498 horse) {
        this.horse = horse;
    }

    public void tick() {
        if (this.repathCooldown > 0) {
            --this.repathCooldown;
        }
        if (this.jumpCooldown > 0) {
            --this.jumpCooldown;
        }
        if (this.descentCooldown > 0) {
            --this.descentCooldown;
        }
    }

    public void moveTo(class_243 target, double speed) {
        boolean needsNewPath;
        if (target == null || this.horse == null || !this.horse.method_5805()) {
            return;
        }
        if (this.trySafeDescent(target, speed)) {
            return;
        }
        this.tryVanillaObstacleJump(target);
        boolean targetChanged = this.lastTarget == null || this.lastTarget.method_1025(target) > 1.44;
        boolean speedChanged = Math.abs(this.lastSpeed - speed) > 0.1;
        boolean bl = needsNewPath = this.horse.method_5942().method_6357() || this.repathCooldown <= 0 && (targetChanged || speedChanged);
        if (needsNewPath) {
            this.horse.method_5942().method_6337(target.field_1352, target.field_1351, target.field_1350, speed);
            this.lastTarget = target;
            this.lastSpeed = speed;
            this.repathCooldown = 6;
        }
    }

    private boolean trySafeDescent(class_243 target, double speed) {
        if (this.descentCooldown > 0 || !this.horse.method_24828() || target == null) {
            return false;
        }
        if (target.field_1351 >= this.horse.method_23318() - 0.55) {
            return false;
        }
        class_243 flat = new class_243(target.field_1352 - this.horse.method_23317(), 0.0, target.field_1350 - this.horse.method_23321());
        if (flat.method_1027() < 0.36) {
            return false;
        }
        class_243 dir = flat.method_1029();
        class_2338 frontFeet = class_2338.method_49637((double)(this.horse.method_23317() + dir.field_1352 * 1.15), (double)this.horse.method_23318(), (double)(this.horse.method_23321() + dir.field_1350 * 1.15));
        if (!this.horse.method_37908().method_8320(frontFeet.method_10074()).method_26220((class_1922)this.horse.method_37908(), frontFeet.method_10074()).method_1110()) {
            return false;
        }
        for (int drop = 1; drop <= 3; ++drop) {
            class_2338 landingFeet = frontFeet.method_10087(drop);
            class_2338 support = landingFeet.method_10074();
            if (this.horse.method_37908().method_8320(support).method_26220((class_1922)this.horse.method_37908(), support).method_1110()) continue;
            double landingX = (double)landingFeet.method_10263() + 0.5;
            double landingY = landingFeet.method_10264();
            double landingZ = (double)landingFeet.method_10260() + 0.5;
            class_238 landingBox = this.horse.method_5829().method_989(landingX - this.horse.method_23317(), landingY - this.horse.method_23318(), landingZ - this.horse.method_23321());
            if (!this.horse.method_37908().method_8587((class_1297)this.horse, landingBox)) continue;
            this.horse.method_5942().method_6340();
            this.horse.method_5962().method_6239(landingX, landingY, landingZ, Math.min(speed, 0.9));
            this.lastTarget = target;
            this.lastSpeed = speed;
            this.descentCooldown = 6;
            return true;
        }
        return false;
    }

    private void tryVanillaObstacleJump(class_243 target) {
        boolean landingSupport;
        if (this.jumpCooldown > 0 || !this.horse.method_24828() || target == null) {
            return;
        }
        class_243 delta = target.method_1020(this.horse.method_19538());
        class_243 flat = new class_243(delta.field_1352, 0.0, delta.field_1350);
        if (flat.method_1027() < 0.25) {
            return;
        }
        class_243 dir = flat.method_1029();
        class_2338 feet = this.horse.method_24515();
        class_2338 front = class_2338.method_49637((double)(this.horse.method_23317() + dir.field_1352 * 1.05), (double)this.horse.method_23318(), (double)(this.horse.method_23321() + dir.field_1350 * 1.05));
        class_2680 obstacle = this.horse.method_37908().method_8320(front);
        if (obstacle.method_26220((class_1922)this.horse.method_37908(), front).method_1110()) {
            return;
        }
        if (!this.horse.method_37908().method_8320(front.method_10084()).method_26220((class_1922)this.horse.method_37908(), front.method_10084()).method_1110()) {
            return;
        }
        class_2338 landing = class_2338.method_49637((double)(this.horse.method_23317() + dir.field_1352 * 2.1), (double)this.horse.method_23318(), (double)(this.horse.method_23321() + dir.field_1350 * 2.1));
        boolean landingSpace = this.horse.method_37908().method_8320(landing).method_26220((class_1922)this.horse.method_37908(), landing).method_1110() && this.horse.method_37908().method_8320(landing.method_10084()).method_26220((class_1922)this.horse.method_37908(), landing.method_10084()).method_1110();
        boolean bl = landingSupport = !this.horse.method_37908().method_8320(landing.method_10074()).method_26220((class_1922)this.horse.method_37908(), landing.method_10074()).method_1110() || !this.horse.method_37908().method_8320(landing).method_26220((class_1922)this.horse.method_37908(), landing).method_1110();
        if (!landingSpace || !landingSupport) {
            return;
        }
        this.horse.method_5993().method_6233();
        this.jumpCooldown = 12;
    }

    public void stop() {
        if (this.horse != null) {
            this.horse.method_5942().method_6340();
        }
        this.lastTarget = null;
        this.repathCooldown = 0;
        this.lastSpeed = 0.0;
        this.descentCooldown = 0;
    }

    public boolean isIdle() {
        return this.horse == null || this.horse.method_5942().method_6357();
    }
}

