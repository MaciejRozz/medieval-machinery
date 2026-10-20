/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
 *  net.minecraft.class_1761
 *  net.minecraft.class_1761$class_7704
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_2378
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.registry;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.recipe.OrderBannerCraftingRecipe;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.registry.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.class_1761;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_2378;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public final class ModItemGroups {
    public static final class_1761 MEDIEVAL_MACHINERY = (class_1761)class_2378.method_10230((class_2378)class_7923.field_44687, (class_2960)MedievalMachinery.id("medieval_machinery"), (Object)FabricItemGroup.builder().method_47321((class_2561)class_2561.method_43471((String)"itemGroup.medieval-machinery.medieval_machinery")).method_47320(() -> new class_1799((class_1935)ModBlocks.GUILLOTINE)).method_47317((displayContext, entries) -> {
        entries.method_45421((class_1935)ModBlocks.GUILLOTINE);
        entries.method_45421((class_1935)ModBlocks.ROYAL_GUILLOTINE);
        entries.method_45421((class_1935)ModBlocks.MACHINE_WORKSHOP);
        entries.method_45421((class_1935)ModItems.ROYAL_SMELTER);
        entries.method_45421((class_1935)ModBlocks.WATCHPOST);
        entries.method_45421((class_1935)ModBlocks.STONE_BRICK_QUADER);
        entries.method_45421((class_1935)ModItems.STATIONARY_CANNON);
        entries.method_45421((class_1935)ModBlocks.DECORATIVE_FORTRESS_CANNON);
        entries.method_45421((class_1935)ModBlocks.DECORATIVE_FIELD_CANNON);
        entries.method_45421((class_1935)ModItems.FIELD_CANNON);
        entries.method_45421((class_1935)ModItems.CATAPULT);
        entries.method_45421((class_1935)ModItems.MACHINE_SPANNER);
        entries.method_45421((class_1935)ModItems.WOOD_WINGED_SPEAR);
        entries.method_45421((class_1935)ModItems.STONE_WINGED_SPEAR);
        entries.method_45421((class_1935)ModItems.COPPER_WINGED_SPEAR);
        entries.method_45421((class_1935)ModItems.IRON_WINGED_SPEAR);
        entries.method_45421((class_1935)ModItems.DIAMOND_WINGED_SPEAR);
        entries.method_45421((class_1935)ModItems.NETHERITE_WINGED_SPEAR);
        entries.method_45421((class_1935)ModItems.WOOD_WAR_SPEAR);
        entries.method_45421((class_1935)ModItems.STONE_WAR_SPEAR);
        entries.method_45421((class_1935)ModItems.COPPER_WAR_SPEAR);
        entries.method_45421((class_1935)ModItems.IRON_WAR_SPEAR);
        entries.method_45421((class_1935)ModItems.DIAMOND_WAR_SPEAR);
        entries.method_45421((class_1935)ModItems.NETHERITE_WAR_SPEAR);
        entries.method_45421((class_1935)ModItems.WOOD_GUARD_SPEAR);
        entries.method_45421((class_1935)ModItems.STONE_GUARD_SPEAR);
        entries.method_45421((class_1935)ModItems.COPPER_GUARD_SPEAR);
        entries.method_45421((class_1935)ModItems.IRON_GUARD_SPEAR);
        entries.method_45421((class_1935)ModItems.DIAMOND_GUARD_SPEAR);
        entries.method_45421((class_1935)ModItems.NETHERITE_GUARD_SPEAR);
        entries.method_45421((class_1935)ModItems.WOOD_LONG_SPEAR);
        entries.method_45421((class_1935)ModItems.STONE_LONG_SPEAR);
        entries.method_45421((class_1935)ModItems.COPPER_LONG_SPEAR);
        entries.method_45421((class_1935)ModItems.IRON_LONG_SPEAR);
        entries.method_45421((class_1935)ModItems.DIAMOND_LONG_SPEAR);
        entries.method_45421((class_1935)ModItems.NETHERITE_LONG_SPEAR);
        ModItemGroups.addOrderArmorVariants(entries, false);
        ModItemGroups.addOrderArmorVariants(entries, true);
        ModItemGroups.addGeneralArmorVariants(entries, false);
        ModItemGroups.addGeneralArmorVariants(entries, true);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_GOLD);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_COPPER);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_IRON);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_DIAMOND);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_REDSTONE);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_AMETHYST);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_LAPIS);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_EMERALD);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_QUARTZ);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_COAL);
        entries.method_45421((class_1935)ModItems.ORDER_SIGIL_NETHERITE);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_GOLD);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_COPPER);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_IRON);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_DIAMOND);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_REDSTONE);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_AMETHYST);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_LAPIS);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_EMERALD);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_QUARTZ);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_COAL);
        entries.method_45421((class_1935)ModItems.ORDER_SHIELD_NETHERITE);
        for (OrderStyle style : OrderStyle.values()) {
            if (style == OrderStyle.NONE) continue;
            entries.method_45420(OrderBannerCraftingRecipe.createBanner(style));
        }
        entries.method_45421((class_1935)ModItems.POTION_PAYLOAD);
        entries.method_45421((class_1935)ModItems.COBBLESTONE_BUNDLE);
        entries.method_45421((class_1935)ModItems.EXPLOSIVE_PAYLOAD);
        entries.method_45421((class_1935)ModItems.FIRE_PAYLOAD);
        entries.method_45421((class_1935)ModBlocks.WHEEL);
        entries.method_45421((class_1935)ModBlocks.WICKER_BASKET);
        entries.method_45421((class_1935)ModItems.STONE_CANNONBALL);
        entries.method_45421((class_1935)ModItems.IRON_CANNONBALL);
        entries.method_45421((class_1935)ModItems.HEAVY_IRON_CANNONBALL);
        entries.method_45421((class_1935)ModItems.TNT_CANNONBALL);
    }).method_47324());

    private static void addOrderArmorVariants(class_1761.class_7704 entries, boolean netheriteUpgrade) {
        class_1792[] sigils = new class_1792[]{ModItems.ORDER_SIGIL_GOLD, ModItems.ORDER_SIGIL_COPPER, ModItems.ORDER_SIGIL_IRON, ModItems.ORDER_SIGIL_DIAMOND, ModItems.ORDER_SIGIL_REDSTONE, ModItems.ORDER_SIGIL_AMETHYST, ModItems.ORDER_SIGIL_LAPIS, ModItems.ORDER_SIGIL_EMERALD, ModItems.ORDER_SIGIL_QUARTZ, ModItems.ORDER_SIGIL_COAL, ModItems.ORDER_SIGIL_NETHERITE};
        MedievalArmorItem[] armor = new MedievalArmorItem[]{ModItems.ORDER_HELMET, ModItems.ORDER_CHESTPLATE, ModItems.ORDER_LEGGINGS, ModItems.ORDER_BOOTS};
        for (class_1792 sigilItem : sigils) {
            class_1799 sigil = new class_1799((class_1935)sigilItem);
            for (MedievalArmorItem armorItem : armor) {
                class_1799 stack = MedievalArmorItem.createFromSigil(armorItem, sigil);
                if (netheriteUpgrade) {
                    stack = MedievalArmorItem.upgradeToNetherite(stack);
                }
                if (stack.method_7960()) continue;
                entries.method_45420(stack);
            }
        }
    }

    private static void addGeneralArmorVariants(class_1761.class_7704 entries, boolean netheriteUpgrade) {
        class_1792[] sigils = new class_1792[]{ModItems.ORDER_SIGIL_GOLD, ModItems.ORDER_SIGIL_COPPER, ModItems.ORDER_SIGIL_IRON, ModItems.ORDER_SIGIL_DIAMOND, ModItems.ORDER_SIGIL_REDSTONE, ModItems.ORDER_SIGIL_AMETHYST, ModItems.ORDER_SIGIL_LAPIS, ModItems.ORDER_SIGIL_EMERALD, ModItems.ORDER_SIGIL_QUARTZ, ModItems.ORDER_SIGIL_COAL, ModItems.ORDER_SIGIL_NETHERITE};
        MedievalArmorItem[] armor = new MedievalArmorItem[]{ModItems.GENERAL_HELMET, ModItems.GENERAL_CHESTPLATE, ModItems.GENERAL_LEGGINGS, ModItems.GENERAL_BOOTS};
        for (class_1792 sigilItem : sigils) {
            class_1799 sigil = new class_1799((class_1935)sigilItem);
            for (MedievalArmorItem armorItem : armor) {
                class_1799 stack = MedievalArmorItem.createFromSigil(armorItem, sigil);
                if (netheriteUpgrade) {
                    stack = MedievalArmorItem.upgradeToNetherite(stack);
                }
                if (stack.method_7960()) continue;
                entries.method_45420(stack);
            }
        }
    }

    private ModItemGroups() {
    }

    public static void register() {
        MedievalMachinery.LOGGER.info("Registering creative item group for Medieval Machinery");
    }
}

