/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1819
 */
package com.medievalmachinery.mod.item;

import com.medievalmachinery.mod.npc.organization.OrderStyle;
import net.minecraft.class_1792;
import net.minecraft.class_1819;

public final class OrderShieldItem
extends class_1819 {
    private final OrderStyle style;

    public OrderShieldItem(OrderStyle style, class_1792.class_1793 settings) {
        super(settings);
        this.style = style == null ? OrderStyle.NONE : style;
    }

    public OrderStyle getOrderStyle() {
        return this.style;
    }
}

