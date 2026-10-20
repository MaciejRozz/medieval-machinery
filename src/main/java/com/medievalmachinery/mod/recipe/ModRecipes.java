/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1865
 *  net.minecraft.class_1866
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.recipe;

import com.medievalmachinery.mod.MedievalMachineryCore;
import com.medievalmachinery.mod.recipe.ArmorStyleCycleRecipe;
import com.medievalmachinery.mod.recipe.MedievalArmorSmithingRecipe;
import com.medievalmachinery.mod.recipe.OrderArmorCraftingRecipe;
import com.medievalmachinery.mod.recipe.OrderBannerCraftingRecipe;
import com.medievalmachinery.mod.recipe.OrderShieldCraftingRecipe;
import com.medievalmachinery.mod.recipe.PotionPayloadFillingRecipe;
import com.medievalmachinery.mod.recipe.RoyalSmelterCraftingRecipe;
import com.medievalmachinery.mod.recipe.workshop.MachineWorkshopRecipe;
import net.minecraft.class_1865;
import net.minecraft.class_1866;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public final class ModRecipes {
    public static final class_1865<ArmorStyleCycleRecipe> ARMOR_STYLE_CYCLE = (class_1865)class_2378.method_10230((class_2378)class_7923.field_41189, (class_2960)MedievalMachineryCore.id("armor_style_cycle"), (Object)new class_1866(ArmorStyleCycleRecipe::new));
    public static final class_1865<MedievalArmorSmithingRecipe> MEDIEVAL_ARMOR_SMITHING = (class_1865)class_2378.method_10230((class_2378)class_7923.field_41189, (class_2960)MedievalMachineryCore.id("medieval_armor_smithing"), (Object)MedievalArmorSmithingRecipe.Serializer.INSTANCE);
    public static final class_1865<MachineWorkshopRecipe> MACHINE_WORKSHOP_SHAPED = (class_1865)class_2378.method_10230((class_2378)class_7923.field_41189, (class_2960)MedievalMachineryCore.id("machine_workshop_shaped"), (Object)new MachineWorkshopRecipe.Serializer());
    public static final class_1865<PotionPayloadFillingRecipe> POTION_PAYLOAD_FILLING = (class_1865)class_2378.method_10230((class_2378)class_7923.field_41189, (class_2960)MedievalMachineryCore.id("potion_payload_filling"), (Object)new class_1866(PotionPayloadFillingRecipe::new));
    public static final class_1865<RoyalSmelterCraftingRecipe> ROYAL_SMELTER_CRAFTING = (class_1865)class_2378.method_10230((class_2378)class_7923.field_41189, (class_2960)MedievalMachineryCore.id("royal_smelter_crafting"), (Object)new class_1866(RoyalSmelterCraftingRecipe::new));
    public static final class_1865<OrderArmorCraftingRecipe> ORDER_ARMOR_CRAFTING = (class_1865)class_2378.method_10230((class_2378)class_7923.field_41189, (class_2960)MedievalMachineryCore.id("order_armor_crafting"), (Object)new class_1866(OrderArmorCraftingRecipe::new));
    public static final class_1865<OrderBannerCraftingRecipe> ORDER_BANNER_CRAFTING = (class_1865)class_2378.method_10230((class_2378)class_7923.field_41189, (class_2960)MedievalMachineryCore.id("order_banner_crafting"), (Object)new class_1866(OrderBannerCraftingRecipe::new));
    public static final class_1865<OrderShieldCraftingRecipe> ORDER_SHIELD_CRAFTING = (class_1865)class_2378.method_10230((class_2378)class_7923.field_41189, (class_2960)MedievalMachineryCore.id("order_shield_crafting"), (Object)new class_1866(OrderShieldCraftingRecipe::new));

    private ModRecipes() {
    }

    public static void register() {
        MedievalMachineryCore.LOGGER.info("Registering recipes for Medieval Machinery");
    }
}

