/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2378
 *  net.minecraft.class_2582
 *  net.minecraft.class_2960
 *  net.minecraft.class_5321
 *  net.minecraft.class_7923
 *  net.minecraft.class_7924
 */
package com.medievalmachinery.mod.registry;

import com.medievalmachinery.mod.MedievalMachineryCore;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.class_2378;
import net.minecraft.class_2582;
import net.minecraft.class_2960;
import net.minecraft.class_5321;
import net.minecraft.class_7923;
import net.minecraft.class_7924;

public final class ModBannerPatterns {
    private static final Map<OrderStyle, class_5321<class_2582>> KEYS = new EnumMap<OrderStyle, class_5321<class_2582>>(OrderStyle.class);

    private ModBannerPatterns() {
    }

    public static class_5321<class_2582> key(OrderStyle style) {
        class_5321<class_2582> key = KEYS.get((Object)style);
        if (key == null) {
            throw new IllegalArgumentException("No order banner pattern for " + String.valueOf((Object)style));
        }
        return key;
    }

    public static void register() {
        MedievalMachineryCore.LOGGER.info("Registering order banner patterns for Medieval Machinery");
    }

    static {
        for (OrderStyle style : OrderStyle.values()) {
            if (style == OrderStyle.NONE) continue;
            String path = "order_emblem_" + style.method_15434();
            class_5321 key = class_5321.method_29179((class_5321)class_7924.field_41252, (class_2960)MedievalMachineryCore.id(path));
            class_2378.method_10230((class_2378)class_7923.field_41165, (class_2960)MedievalMachineryCore.id(path), (Object)new class_2582("mm_" + style.method_15434()));
            KEYS.put(style, (class_5321<class_2582>)key);
        }
    }
}

