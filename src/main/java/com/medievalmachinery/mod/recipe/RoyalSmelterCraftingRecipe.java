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
 *  net.minecraft.class_2960
 *  net.minecraft.class_5455
 *  net.minecraft.class_7710
 *  net.minecraft.class_8566
 */
package com.medievalmachinery.mod.recipe;

import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.item.workshop.RoyalSmelterBlockItem;
import com.medievalmachinery.mod.recipe.ModRecipes;
import com.medievalmachinery.mod.registry.ModBlocks;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1852;
import net.minecraft.class_1865;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_5455;
import net.minecraft.class_7710;
import net.minecraft.class_8566;

public final class RoyalSmelterCraftingRecipe
extends class_1852 {
    public RoyalSmelterCraftingRecipe(class_2960 id, class_7710 category) {
        super(id, category);
    }

    public boolean matches(class_8566 inventory, class_1937 world) {
        boolean furnace = false;
        boolean sigil = false;
        for (int i = 0; i < inventory.method_5439(); ++i) {
            class_1799 stack = inventory.method_5438(i);
            if (stack.method_7960()) continue;
            if (!furnace && stack.method_31574(class_1802.field_16306)) {
                furnace = true;
                continue;
            }
            if (!sigil && stack.method_7909() instanceof OrderSigilItem && OrderSigilItem.isStamped(stack)) {
                sigil = true;
                continue;
            }
            return false;
        }
        return furnace && sigil;
    }

    public class_1799 craft(class_8566 inventory, class_5455 registryManager) {
        for (int i = 0; i < inventory.method_5439(); ++i) {
            class_1799 stack = inventory.method_5438(i);
            if (!(stack.method_7909() instanceof OrderSigilItem) || !OrderSigilItem.isStamped(stack)) continue;
            return RoyalSmelterBlockItem.createFromSigil(ModBlocks.ROYAL_SMELTER, stack);
        }
        return class_1799.field_8037;
    }

    public boolean method_8113(int width, int height) {
        return width * height >= 2;
    }

    public boolean method_8118() {
        return false;
    }

    public class_1799 method_8110(class_5455 registryManager) {
        return new class_1799((class_1935)ModBlocks.ROYAL_SMELTER);
    }

    public class_1865<?> method_8119() {
        return ModRecipes.ROYAL_SMELTER_CRAFTING;
    }
}

