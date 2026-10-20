/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2680
 *  net.minecraft.class_3218
 *  net.minecraft.class_4970$class_2251
 *  net.minecraft.class_5819
 */
package com.medievalmachinery.mod.block;

import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_4970;
import net.minecraft.class_5819;

public final class TemporaryRescueBlock
extends class_2248 {
    private static final int MAX_LIFETIME_TICKS = 200;

    public TemporaryRescueBlock(class_4970.class_2251 settings) {
        super(settings);
    }

    public void method_9615(class_2680 state, class_1937 world, class_2338 pos, class_2680 oldState, boolean notify) {
        super.method_9615(state, world, pos, oldState, notify);
        if (!world.field_9236) {
            world.method_39279(pos, (class_2248)this, 200);
        }
    }

    public void method_9588(class_2680 state, class_3218 world, class_2338 pos, class_5819 random) {
        if (state.method_27852((class_2248)this)) {
            world.method_8650(pos, false);
        }
    }
}

