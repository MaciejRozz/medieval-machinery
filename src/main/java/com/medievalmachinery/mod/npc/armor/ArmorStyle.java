/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.armor;

import java.util.Locale;

public enum ArmorStyle {
    ARCHER,
    LIGHT_BRIGANDINE,
    HEAVY_BRIGANDINE,
    ENGINEER,
    PLATE,
    CAPTAIN,
    GENERAL;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static ArmorStyle fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return HEAVY_BRIGANDINE;
        }
        if ("recruit".equals(value) || "brigandine".equals(value)) {
            return HEAVY_BRIGANDINE;
        }
        for (ArmorStyle style : ArmorStyle.values()) {
            if (!style.serializedName().equals(value)) continue;
            return style;
        }
        return HEAVY_BRIGANDINE;
    }
}

