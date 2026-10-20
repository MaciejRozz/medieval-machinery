/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1291
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.registry;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.item.OrderSetDisplayEffect;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.platform.RegisteredContent;
import java.util.EnumMap;
import net.minecraft.class_1291;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public final class ModStatusEffects {
    private ModStatusEffects() {
    }

    public static void register() {
        EnumMap<OrderStyle, class_1291> effects = new EnumMap<OrderStyle, class_1291>(OrderStyle.class);
        for (OrderStyle style : OrderStyle.values()) {
            if (style == OrderStyle.NONE) continue;
            class_1291 effect = (class_1291)class_2378.method_10230((class_2378)class_7923.field_41174, (class_2960)MedievalMachinery.id("order_set_" + style.method_15434()), (Object)((Object)new OrderSetDisplayEffect(ModStatusEffects.color(style))));
            effects.put(style, effect);
        }
        RegisteredContent.bindOrderSetEffects(effects);
    }

    private static int color(OrderStyle style) {
        return switch (style) {
            default -> throw new IncompatibleClassChangeError();
            case OrderStyle.GOLD -> 14199080;
            case OrderStyle.COPPER -> 12086077;
            case OrderStyle.IRON -> 0xC8C8C8;
            case OrderStyle.DIAMOND -> 6477288;
            case OrderStyle.REDSTONE -> 11546150;
            case OrderStyle.AMETHYST -> 9395394;
            case OrderStyle.LAPIS -> 3432131;
            case OrderStyle.EMERALD -> 3843669;
            case OrderStyle.QUARTZ -> 15656660;
            case OrderStyle.COAL -> 0x444444;
            case OrderStyle.NETHERITE -> 7028038;
            case OrderStyle.NONE -> 0xFFFFFF;
        };
    }
}

