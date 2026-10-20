/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1750
 *  net.minecraft.class_2248
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2758
 *  net.minecraft.class_2769
 *  net.minecraft.class_3710
 *  net.minecraft.class_4970$class_2251
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.workshop;

import com.medievalmachinery.mod.item.workshop.RoyalSmelterBlockItem;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import net.minecraft.class_1750;
import net.minecraft.class_2248;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2758;
import net.minecraft.class_2769;
import net.minecraft.class_3710;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public final class RoyalSmelterBlock
extends class_3710 {
    public static final class_2758 ORDER = class_2758.method_11867((String)"order", (int)0, (int)(OrderStyle.values().length - 1));

    public RoyalSmelterBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)this.method_9564().method_11657((class_2769)ORDER, (Comparable)Integer.valueOf(OrderStyle.NONE.ordinal())));
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        super.method_9515(builder);
        builder.method_11667(new class_2769[]{ORDER});
    }

    @Nullable
    public class_2680 method_9605(class_1750 ctx) {
        class_2680 state = super.method_9605(ctx);
        if (state == null) {
            return null;
        }
        OrderStyle order = RoyalSmelterBlockItem.getBoundOrder(ctx.method_8041());
        return (class_2680)state.method_11657((class_2769)ORDER, (Comparable)Integer.valueOf(order.ordinal()));
    }
}

