/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 *  net.minecraft.class_1852
 *  net.minecraft.class_1865
 *  net.minecraft.class_1937
 *  net.minecraft.class_2960
 *  net.minecraft.class_5455
 *  net.minecraft.class_7710
 *  net.minecraft.class_8566
 */
package com.medievalmachinery.mod.recipe;

import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.npc.armor.ArmorStyle;
import com.medievalmachinery.mod.recipe.ModRecipes;
import net.minecraft.class_1799;
import net.minecraft.class_1852;
import net.minecraft.class_1865;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_5455;
import net.minecraft.class_7710;
import net.minecraft.class_8566;

public final class ArmorStyleCycleRecipe
extends class_1852 {
    private static final ArmorStyle[] PLAYER_STYLES = new ArmorStyle[]{ArmorStyle.ARCHER, ArmorStyle.LIGHT_BRIGANDINE, ArmorStyle.HEAVY_BRIGANDINE, ArmorStyle.ENGINEER, ArmorStyle.PLATE, ArmorStyle.CAPTAIN};

    public ArmorStyleCycleRecipe(class_2960 id, class_7710 category) {
        super(id, category);
    }

    private class_1799 findSingleArmor(class_8566 inventory) {
        class_1799 found = class_1799.field_8037;
        for (int i = 0; i < inventory.method_5439(); ++i) {
            class_1799 stack = inventory.method_5438(i);
            if (stack.method_7960()) continue;
            if (!(stack.method_7909() instanceof MedievalArmorItem)) {
                return class_1799.field_8037;
            }
            if (!found.method_7960()) {
                return class_1799.field_8037;
            }
            found = stack;
        }
        return found;
    }

    public boolean matches(class_8566 inventory, class_1937 world) {
        class_1799 stack = this.findSingleArmor(inventory);
        if (stack.method_7960()) {
            return false;
        }
        return !MedievalArmorItem.getTier(stack).isGeneralFamily();
    }

    public class_1799 craft(class_8566 inventory, class_5455 registryManager) {
        class_1799 input = this.findSingleArmor(inventory);
        if (input.method_7960() || MedievalArmorItem.getTier(input).isGeneralFamily()) {
            return class_1799.field_8037;
        }
        class_1799 result = input.method_7972();
        result.method_7939(1);
        ArmorStyle current = MedievalArmorItem.getArmorStyle(result);
        int index = 0;
        for (int i = 0; i < PLAYER_STYLES.length; ++i) {
            if (PLAYER_STYLES[i] != current) continue;
            index = (i + 1) % PLAYER_STYLES.length;
            MedievalArmorItem.setArmorStyle(result, PLAYER_STYLES[index]);
            return result;
        }
        MedievalArmorItem.setArmorStyle(result, ArmorStyle.LIGHT_BRIGANDINE);
        return result;
    }

    public boolean method_8118() {
        return false;
    }

    public boolean method_8113(int width, int height) {
        return width * height >= 1;
    }

    public class_1799 method_8110(class_5455 registryManager) {
        return class_1799.field_8037;
    }

    public class_1865<?> method_8119() {
        return ModRecipes.ARMOR_STYLE_CYCLE;
    }
}

