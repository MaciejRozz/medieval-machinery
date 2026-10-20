/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

public enum NpcHungerState {
    SATIATED("satiated"),
    NORMAL("normal"),
    HUNGRY("hungry"),
    VERY_HUNGRY("very_hungry");

    private final String serializedName;

    private NpcHungerState(String serializedName) {
        this.serializedName = serializedName;
    }

    public String serializedName() {
        return this.serializedName;
    }
}

