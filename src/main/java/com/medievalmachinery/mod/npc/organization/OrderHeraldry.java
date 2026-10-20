/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1767
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.OrderStyle;
import net.minecraft.class_1767;

public final class OrderHeraldry {
    private OrderHeraldry() {
    }

    public static class_1767 dyeColor(OrderStyle style) {
        return switch (style) {
            case OrderStyle.GOLD -> class_1767.field_7947;
            case OrderStyle.COPPER -> class_1767.field_7946;
            case OrderStyle.IRON -> class_1767.field_7967;
            case OrderStyle.DIAMOND -> class_1767.field_7951;
            case OrderStyle.REDSTONE -> class_1767.field_7964;
            case OrderStyle.AMETHYST -> class_1767.field_7945;
            case OrderStyle.LAPIS -> class_1767.field_7966;
            case OrderStyle.EMERALD -> class_1767.field_7942;
            case OrderStyle.QUARTZ -> class_1767.field_7952;
            case OrderStyle.COAL -> class_1767.field_7944;
            case OrderStyle.NETHERITE -> class_1767.field_7944;
            default -> class_1767.field_7952;
        };
    }
}

