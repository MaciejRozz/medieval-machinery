/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.armor;

import java.util.Locale;

public enum ArmorTier {
    ORDER,
    NETHERITE_ORDER,
    GENERAL,
    NETHERITE_GENERAL;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public boolean isNetheriteUpgraded() {
        return this == NETHERITE_ORDER || this == NETHERITE_GENERAL;
    }

    public boolean isGeneralFamily() {
        return this == GENERAL || this == NETHERITE_GENERAL;
    }

    public static ArmorTier fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return ORDER;
        }
        for (ArmorTier tier : ArmorTier.values()) {
            if (!tier.serializedName().equals(value)) continue;
            return tier;
        }
        return ORDER;
    }
}

