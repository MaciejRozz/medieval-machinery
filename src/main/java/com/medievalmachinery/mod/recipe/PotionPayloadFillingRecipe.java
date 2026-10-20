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

import com.medievalmachinery.mod.item.PotionPayloadItem;
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

public final class PotionPayloadFillingRecipe
extends class_1852 {
    public PotionPayloadFillingRecipe(class_2960 id, class_7710 category) {
        super(id, category);
    }

    public boolean matches(class_8566 inventory, class_1937 world) {
        boolean foundContainer = false;
        boolean foundPotion = false;
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            class_1799 stack = inventory.method_5438(slot);
            if (stack.method_7960()) continue;
            if (stack.method_31574(ModItems.POTION_PAYLOAD) && !PotionPayloadItem.isFilled(stack) && !foundContainer) {
                foundContainer = true;
                continue;
            }
            if (stack.method_31574(class_1802.field_8574) && !foundPotion) {
                foundPotion = true;
                continue;
            }
            return false;
        }
        return foundContainer && foundPotion;
    }

    public class_1799 craft(class_8566 inventory, class_5455 registryManager) {
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            class_1799 stack = inventory.method_5438(slot);
            if (!stack.method_31574(class_1802.field_8574)) continue;
            return PotionPayloadItem.createFilled(stack);
        }
        return class_1799.field_8037;
    }

    public class_2371<class_1799> getRemainder(class_8566 inventory) {
        class_2371 remainder = class_2371.method_10213((int)inventory.method_5439(), (Object)class_1799.field_8037);
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            class_1799 stack = inventory.method_5438(slot);
            if (stack.method_31574(class_1802.field_8574)) {
                remainder.set(slot, (Object)new class_1799((class_1935)class_1802.field_8469));
                continue;
            }
            if (!stack.method_7909().method_7857()) continue;
            remainder.set(slot, (Object)new class_1799((class_1935)stack.method_7909().method_7858()));
        }
        return remainder;
    }

    public boolean method_8113(int width, int height) {
        return width * height >= 2;
    }

    public class_1865<?> method_8119() {
        return ModRecipes.POTION_PAYLOAD_FILLING;
    }
}

