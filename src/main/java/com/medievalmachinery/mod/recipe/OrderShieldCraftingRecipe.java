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
 *  net.minecraft.class_2371
 *  net.minecraft.class_2960
 *  net.minecraft.class_5455
 *  net.minecraft.class_7710
 *  net.minecraft.class_8566
 */
package com.medievalmachinery.mod.recipe;

import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.recipe.ModRecipes;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1852;
import net.minecraft.class_1865;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2371;
import net.minecraft.class_2960;
import net.minecraft.class_5455;
import net.minecraft.class_7710;
import net.minecraft.class_8566;

public final class OrderShieldCraftingRecipe
extends class_1852 {
    private static final int INPUT_SHIELD_COUNT = 1;
    private static final int OUTPUT_SHIELD_COUNT = 1;
    private final OrderStyle style;

    public OrderShieldCraftingRecipe(class_2960 id, class_7710 category) {
        super(id, category);
        String prefix = "order_shield_";
        String path = id.method_12832();
        OrderStyle orderStyle = this.style = path.startsWith(prefix) ? OrderStyle.fromSerializedName(path.substring(prefix.length())) : OrderStyle.NONE;
        if (this.style == OrderStyle.NONE) {
            throw new IllegalArgumentException("Unsupported order shield recipe id: " + String.valueOf(id));
        }
    }

    public boolean matches(class_8566 inventory, class_1937 world) {
        int shields = 0;
        boolean sigil = false;
        for (int i = 0; i < inventory.method_5439(); ++i) {
            class_1799 stack = inventory.method_5438(i);
            if (stack.method_7960()) continue;
            if (stack.method_31574(class_1802.field_8255)) {
                ++shields;
                continue;
            }
            if (!sigil && stack.method_31574(ModItems.getOrderSigil(this.style))) {
                sigil = true;
                continue;
            }
            return false;
        }
        return shields == 1 && sigil;
    }

    public class_1799 craft(class_8566 inventory, class_5455 registryManager) {
        return new class_1799((class_1935)ModItems.getOrderShield(this.style), 1);
    }

    public class_2371<class_1799> getRemainder(class_8566 inventory) {
        return class_2371.method_10213((int)inventory.method_5439(), (Object)class_1799.field_8037);
    }

    public boolean method_8113(int width, int height) {
        return width * height >= 2;
    }

    public class_1799 method_8110(class_5455 registryManager) {
        return new class_1799((class_1935)ModItems.getOrderShield(this.style), 1);
    }

    public boolean method_8118() {
        return false;
    }

    public class_1865<?> method_8119() {
        return ModRecipes.ORDER_SHIELD_CRAFTING;
    }
}

