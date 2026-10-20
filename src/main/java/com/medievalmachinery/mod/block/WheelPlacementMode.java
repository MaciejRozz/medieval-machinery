/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3542
 */
package com.medievalmachinery.mod.block;

import net.minecraft.class_3542;

public enum WheelPlacementMode implements class_3542
{
    FLOOR("floor"),
    WALL("wall");

    private final String name;

    private WheelPlacementMode(String name) {
        this.name = name;
    }

    public String method_15434() {
        return this.name;
    }
}

