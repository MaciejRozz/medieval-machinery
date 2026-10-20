/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

import com.medievalmachinery.mod.npc.core.NpcHungerState;

public final class NpcMoraleData {
    public static final float MIN = 0.0f;
    public static final float MAX = 100.0f;
    public static final float DEFAULT = 50.0f;
    public static final float NORMAL_RECOVERY_CEILING = 65.0f;
    private float morale = 50.0f;

    public float get() {
        return this.morale;
    }

    public void set(float value) {
        this.morale = NpcMoraleData.clamp(value);
    }

    public void add(float amount) {
        this.set(this.morale + amount);
    }

    public void update(NpcHungerState hunger, float healthFraction) {
        float next = this.morale;
        if (hunger == NpcHungerState.VERY_HUNGRY && next > 0.0f) {
            next -= 2.0f;
        } else if (hunger == NpcHungerState.HUNGRY && next > 35.0f) {
            next -= 1.0f;
        }
        if ((hunger == NpcHungerState.SATIATED || healthFraction >= 0.85f) && next < 65.0f) {
            next += 2.0f;
        }
        this.set(next);
    }

    public static float clamp(float value) {
        return Math.max(0.0f, Math.min(100.0f, value));
    }
}

