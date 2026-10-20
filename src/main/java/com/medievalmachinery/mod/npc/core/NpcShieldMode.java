/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

import java.util.Locale;

public enum NpcShieldMode {
    AUTO,
    RAISED;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static NpcShieldMode fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return AUTO;
        }
        for (NpcShieldMode mode : NpcShieldMode.values()) {
            if (!mode.serializedName().equals(value)) continue;
            return mode;
        }
        return AUTO;
    }
}

