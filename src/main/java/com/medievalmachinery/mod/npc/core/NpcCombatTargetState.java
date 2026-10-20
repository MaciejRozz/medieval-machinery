/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 */
package com.medievalmachinery.mod.npc.core;

import net.minecraft.class_1309;

public final class NpcCombatTargetState {
    private int lostSightTicks;
    private int manualForgetTicks;

    public void tickManualForget() {
        if (this.manualForgetTicks > 0) {
            --this.manualForgetTicks;
        }
    }

    public boolean mayAcquireTarget() {
        return this.manualForgetTicks <= 0;
    }

    public void suppressTargetAcquisition(int ticks) {
        this.manualForgetTicks = Math.max(this.manualForgetTicks, ticks);
    }

    public void acceptCommandedTarget() {
        this.manualForgetTicks = 0;
        this.lostSightTicks = 0;
    }

    public void resetSight() {
        this.lostSightTicks = 0;
    }

    public int incrementLostSight() {
        return ++this.lostSightTicks;
    }

    public boolean shouldForget(class_1309 target, boolean canSee, double distanceSq, double maxDistanceSq, boolean patrolLeashOk, int graceTicks) {
        if (target == null || !target.method_5805() || target.method_31481()) {
            return true;
        }
        if (distanceSq > maxDistanceSq || !patrolLeashOk) {
            return true;
        }
        if (canSee) {
            this.lostSightTicks = 0;
            return false;
        }
        return ++this.lostSightTicks >= graceTicks;
    }
}

