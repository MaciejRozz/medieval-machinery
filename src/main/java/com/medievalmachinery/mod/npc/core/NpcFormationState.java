/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_243
 *  net.minecraft.class_3532
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.core;

import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import org.jetbrains.annotations.Nullable;

public final class NpcFormationState {
    private static final double HEADING_UPDATE_DISTANCE_SQ = 6.25;
    private int slot = -1;
    @Nullable
    private class_243 assemblyTarget;
    private int assemblyTicks;
    private boolean headingInitialized;
    private double forwardX;
    private double forwardZ = 1.0;
    private double headingAnchorX;
    private double headingAnchorZ;
    private boolean temporarilyReleased;
    private int reassembleDelay;
    private double spacingMultiplier = 1.0;
    private float facingYaw = Float.NaN;

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = Math.max(-1, slot);
    }

    @Nullable
    public class_243 getAssemblyTarget() {
        return this.assemblyTarget;
    }

    public int getAssemblyTicks() {
        return this.assemblyTicks;
    }

    public void assembleAt(class_243 target, int ticks) {
        this.assemblyTarget = target;
        this.assemblyTicks = Math.max(0, ticks);
    }

    public void clearAssembly() {
        this.assemblyTarget = null;
        this.assemblyTicks = 0;
    }

    public void tick() {
        if (this.assemblyTicks > 0) {
            --this.assemblyTicks;
        } else {
            this.assemblyTarget = null;
        }
        if (this.reassembleDelay > 0) {
            --this.reassembleDelay;
        }
    }

    public boolean isTemporarilyReleased() {
        return this.temporarilyReleased;
    }

    public int getReassembleDelay() {
        return this.reassembleDelay;
    }

    public void releaseTemporarily(int ticks) {
        this.temporarilyReleased = true;
        this.reassembleDelay = Math.max(0, ticks);
    }

    public void clearTemporaryRelease() {
        this.temporarilyReleased = false;
        this.reassembleDelay = 0;
    }

    public double getSpacingMultiplier() {
        return class_3532.method_15350((double)this.spacingMultiplier, (double)0.65, (double)1.45);
    }

    public void setSpacingMultiplier(double value) {
        this.spacingMultiplier = class_3532.method_15350((double)value, (double)0.7, (double)1.4);
    }

    public float resolveFacingYaw(float fallbackYaw) {
        return Float.isNaN(this.facingYaw) ? fallbackYaw : this.facingYaw;
    }

    public void setFacingYaw(float yaw) {
        this.facingYaw = class_3532.method_15393((float)yaw);
    }

    public boolean hasFacingYaw() {
        return !Float.isNaN(this.facingYaw);
    }

    public float getFacingYaw() {
        return this.facingYaw;
    }

    public void resetHeading() {
        this.headingInitialized = false;
    }

    public void updateHeading(class_1657 owner) {
        double dz;
        if (owner == null) {
            return;
        }
        if (!this.headingInitialized) {
            class_243 velocity = owner.method_18798();
            double speedSq = velocity.field_1352 * velocity.field_1352 + velocity.field_1350 * velocity.field_1350;
            if (speedSq > 0.01) {
                double len = Math.sqrt(speedSq);
                this.forwardX = velocity.field_1352 / len;
                this.forwardZ = velocity.field_1350 / len;
            } else {
                float yawRad = owner.method_36454() * ((float)Math.PI / 180);
                this.forwardX = -class_3532.method_15374((float)yawRad);
                this.forwardZ = class_3532.method_15362((float)yawRad);
            }
            this.headingAnchorX = owner.method_23317();
            this.headingAnchorZ = owner.method_23321();
            this.headingInitialized = true;
            return;
        }
        double dx = owner.method_23317() - this.headingAnchorX;
        double travelledSq = dx * dx + (dz = owner.method_23321() - this.headingAnchorZ) * dz;
        if (travelledSq >= 6.25) {
            double len = Math.sqrt(travelledSq);
            this.forwardX = dx / len;
            this.forwardZ = dz / len;
            this.headingAnchorX = owner.method_23317();
            this.headingAnchorZ = owner.method_23321();
        }
    }

    public double forwardX() {
        return this.forwardX;
    }

    public double forwardZ() {
        return this.forwardZ;
    }
}

