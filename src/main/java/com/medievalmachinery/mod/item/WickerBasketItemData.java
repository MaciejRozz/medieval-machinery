/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 *  net.minecraft.class_2487
 */
package com.medievalmachinery.mod.item;

import com.medievalmachinery.mod.registry.ModBlocks;
import net.minecraft.class_1799;
import net.minecraft.class_2487;

public final class WickerBasketItemData {
    private WickerBasketItemData() {
    }

    public static boolean isFilledBasket(class_1799 stack) {
        if (!stack.method_31574(ModBlocks.WICKER_BASKET.method_8389())) {
            return false;
        }
        class_2487 blockEntityTag = stack.method_7941("BlockEntityTag");
        return blockEntityTag != null && blockEntityTag.method_10573("Items", 9) && !blockEntityTag.method_10554("Items", 10).isEmpty();
    }
}

