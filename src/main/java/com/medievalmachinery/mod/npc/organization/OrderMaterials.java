/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1802
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.OrderStyle;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import org.jetbrains.annotations.Nullable;

public final class OrderMaterials {
    private OrderMaterials() {
    }

    @Nullable
    public static class_1792 materialFor(OrderStyle style) {
        return switch (style) {
            case OrderStyle.GOLD -> class_1802.field_8695;
            case OrderStyle.COPPER -> class_1802.field_27022;
            case OrderStyle.IRON -> class_1802.field_8620;
            case OrderStyle.DIAMOND -> class_1802.field_8477;
            case OrderStyle.REDSTONE -> class_1802.field_8725;
            case OrderStyle.AMETHYST -> class_1802.field_27063;
            case OrderStyle.LAPIS -> class_1802.field_8759;
            case OrderStyle.EMERALD -> class_1802.field_8687;
            case OrderStyle.QUARTZ -> class_1802.field_8155;
            case OrderStyle.COAL -> class_1802.field_8713;
            case OrderStyle.NETHERITE -> class_1802.field_22020;
            default -> null;
        };
    }
}

