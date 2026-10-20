/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3542
 */
package com.medievalmachinery.mod.block.garrison;

import net.minecraft.class_3542;

public enum WatchpostPart implements class_3542
{
    MAIN("main"),
    EXTENSION("extension");

    private final String name;

    private WatchpostPart(String name) {
        this.name = name;
    }

    public String method_15434() {
        return this.name;
    }
}

