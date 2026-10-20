/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_18
 *  net.minecraft.class_2487
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.test;

import net.minecraft.class_18;
import net.minecraft.class_2487;
import net.minecraft.class_3218;

public final class SlimeSpawnTestState
extends class_18 {
    private static final String STORAGE_KEY = "medievalmachinery_test_settings";
    private static final String DISABLE_SLIMES_KEY = "DisableSlimes";
    private boolean slimesDisabled;

    public static SlimeSpawnTestState get(class_3218 world) {
        return (SlimeSpawnTestState)world.method_17983().method_17924(SlimeSpawnTestState::fromNbt, SlimeSpawnTestState::new, STORAGE_KEY);
    }

    private static SlimeSpawnTestState fromNbt(class_2487 nbt) {
        SlimeSpawnTestState state = new SlimeSpawnTestState();
        state.slimesDisabled = nbt.method_10577(DISABLE_SLIMES_KEY);
        return state;
    }

    public class_2487 method_75(class_2487 nbt) {
        nbt.method_10556(DISABLE_SLIMES_KEY, this.slimesDisabled);
        return nbt;
    }

    public boolean areSlimesDisabled() {
        return this.slimesDisabled;
    }

    public void setSlimesDisabled(boolean disabled) {
        if (this.slimesDisabled != disabled) {
            this.slimesDisabled = disabled;
            this.method_80();
        }
    }
}

