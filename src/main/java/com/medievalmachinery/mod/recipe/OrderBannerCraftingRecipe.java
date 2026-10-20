/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1852
 *  net.minecraft.class_1865
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2561
 *  net.minecraft.class_2582$class_3750
 *  net.minecraft.class_2960
 *  net.minecraft.class_5455
 *  net.minecraft.class_7446
 *  net.minecraft.class_7710
 *  net.minecraft.class_8566
 */
package com.medievalmachinery.mod.recipe;

import com.medievalmachinery.mod.npc.organization.OrderHeraldry;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.recipe.ModRecipes;
import com.medievalmachinery.mod.registry.ModBannerPatterns;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1852;
import net.minecraft.class_1865;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2561;
import net.minecraft.class_2582;
import net.minecraft.class_2960;
import net.minecraft.class_5455;
import net.minecraft.class_7446;
import net.minecraft.class_7710;
import net.minecraft.class_8566;

public final class OrderBannerCraftingRecipe
extends class_1852 {
    private final OrderStyle style;

    public OrderBannerCraftingRecipe(class_2960 id, class_7710 category) {
        super(id, category);
        String path = id.method_12832();
        String prefix = "order_banner_";
        OrderStyle orderStyle = this.style = path.startsWith(prefix) ? OrderStyle.fromSerializedName(path.substring(prefix.length())) : OrderStyle.NONE;
        if (this.style == OrderStyle.NONE) {
            throw new IllegalArgumentException("Unsupported order banner recipe id: " + String.valueOf(id));
        }
    }

    public boolean matches(class_8566 inventory, class_1937 world) {
        boolean banner = false;
        boolean sigil = false;
        for (int i = 0; i < inventory.method_5439(); ++i) {
            class_1799 stack = inventory.method_5438(i);
            if (stack.method_7960()) continue;
            if (!banner && stack.method_31574(class_1802.field_8539)) {
                banner = true;
                continue;
            }
            if (!sigil && stack.method_31574(ModItems.getOrderSigil(this.style))) {
                sigil = true;
                continue;
            }
            return false;
        }
        return banner && sigil;
    }

    public class_1799 craft(class_8566 inventory, class_5455 registryManager) {
        return OrderBannerCraftingRecipe.createBanner(this.style, 16);
    }

    public boolean method_8113(int width, int height) {
        return width * height >= 2;
    }

    public class_1799 method_8110(class_5455 registryManager) {
        return OrderBannerCraftingRecipe.createBanner(this.style, 16);
    }

    public static class_1799 createBanner(OrderStyle style) {
        return OrderBannerCraftingRecipe.createBanner(style, 1);
    }

    private static class_1799 createBanner(OrderStyle style, int count) {
        class_1799 result = new class_1799((class_1935)class_1802.field_8572, count);
        class_2582.class_3750 patterns = new class_2582.class_3750().method_43720(class_7446.field_39140, OrderHeraldry.dyeColor(style)).method_43720(ModBannerPatterns.key(style), OrderHeraldry.dyeColor(style));
        class_2487 blockEntityData = result.method_7911("BlockEntityTag");
        blockEntityData.method_10566("Patterns", (class_2520)patterns.method_16375());
        result.method_7977((class_2561)class_2561.method_43471((String)("item.medieval-machinery.order_banner_" + style.method_15434())));
        return result;
    }

    public boolean method_8118() {
        return false;
    }

    public class_1865<?> method_8119() {
        return ModRecipes.ORDER_BANNER_CRAFTING;
    }
}

