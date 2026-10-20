/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3542
 */
package com.medievalmachinery.mod.npc.organization;

import java.util.Locale;
import net.minecraft.class_3542;

public enum OrderStyle implements class_3542
{
    NONE,
    GOLD,
    COPPER,
    IRON,
    DIAMOND,
    REDSTONE,
    AMETHYST,
    LAPIS,
    EMERALD,
    QUARTZ,
    COAL,
    NETHERITE;


    public String method_15434() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static OrderStyle fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return NONE;
        }
        for (OrderStyle style : OrderStyle.values()) {
            if (!style.method_15434().equals(value)) continue;
            return style;
        }
        return NONE;
    }
}

