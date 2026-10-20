/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

import java.util.Locale;

public enum NpcFormation {
    FREE,
    LINE,
    SQUARE,
    TRIANGLE,
    HALF_CIRCLE,
    HOLLOW_SQUARE,
    V_FORM,
    CIRCLE,
    MOVEMENT;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static NpcFormation fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return FREE;
        }
        for (NpcFormation formation : NpcFormation.values()) {
            if (!formation.serializedName().equals(value)) continue;
            return formation;
        }
        return FREE;
    }
}

