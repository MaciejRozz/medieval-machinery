/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

import java.util.Locale;

public enum NpcRank {
    NONE,
    CAPTAIN,
    GENERAL;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static NpcRank fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return NONE;
        }
        for (NpcRank rank : NpcRank.values()) {
            if (!rank.serializedName().equals(value)) continue;
            return rank;
        }
        return NONE;
    }
}

