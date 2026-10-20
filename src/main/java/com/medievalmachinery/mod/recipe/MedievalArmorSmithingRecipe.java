/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.class_1263
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1865
 *  net.minecraft.class_1937
 *  net.minecraft.class_2540
 *  net.minecraft.class_2960
 *  net.minecraft.class_5455
 *  net.minecraft.class_8059
 */
package com.medievalmachinery.mod.recipe;

import com.google.gson.JsonObject;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.recipe.ModRecipes;
import net.minecraft.class_1263;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1865;
import net.minecraft.class_1937;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_5455;
import net.minecraft.class_8059;

public final class MedievalArmorSmithingRecipe
implements class_8059 {
    private final class_2960 id;

    public MedievalArmorSmithingRecipe(class_2960 id) {
        this.id = id;
    }

    public boolean method_48453(class_1799 stack) {
        return stack.method_31574(class_1802.field_41946);
    }

    public boolean method_48454(class_1799 stack) {
        return MedievalArmorItem.isMedievalArmor(stack) && !MedievalArmorItem.getTier(stack).isNetheriteUpgraded();
    }

    public boolean method_30029(class_1799 stack) {
        return stack.method_31574(class_1802.field_22020);
    }

    public boolean method_8115(class_1263 inventory, class_1937 world) {
        if (inventory.method_5439() < 3) {
            return false;
        }
        return this.method_48453(inventory.method_5438(0)) && this.method_48454(inventory.method_5438(1)) && this.method_30029(inventory.method_5438(2));
    }

    public class_1799 method_8116(class_1263 inventory, class_5455 registryManager) {
        if (!(inventory.method_5439() >= 3 && this.method_48453(inventory.method_5438(0)) && this.method_48454(inventory.method_5438(1)) && this.method_30029(inventory.method_5438(2)))) {
            return class_1799.field_8037;
        }
        return MedievalArmorItem.upgradeToNetherite(inventory.method_5438(1));
    }

    public class_1799 method_8110(class_5455 registryManager) {
        return class_1799.field_8037;
    }

    public class_2960 method_8114() {
        return this.id;
    }

    public class_1865<?> method_8119() {
        return ModRecipes.MEDIEVAL_ARMOR_SMITHING;
    }

    public static final class Serializer
    implements class_1865<MedievalArmorSmithingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        public MedievalArmorSmithingRecipe read(class_2960 id, JsonObject json) {
            return new MedievalArmorSmithingRecipe(id);
        }

        public MedievalArmorSmithingRecipe read(class_2960 id, class_2540 buf) {
            return new MedievalArmorSmithingRecipe(id);
        }

        public void write(class_2540 buf, MedievalArmorSmithingRecipe recipe) {
        }
    }
}

