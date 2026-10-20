/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1738$class_8051
 *  net.minecraft.class_1747
 *  net.minecraft.class_1792
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.registry;

import com.medievalmachinery.mod.MedievalMachineryCore;
import com.medievalmachinery.mod.item.CannonballBlockItem;
import com.medievalmachinery.mod.item.CatapultItem;
import com.medievalmachinery.mod.item.CatapultSpecialPayloadItem;
import com.medievalmachinery.mod.item.FieldCannonItem;
import com.medievalmachinery.mod.item.MachineSpannerItem;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.item.MedievalArmorMaterials;
import com.medievalmachinery.mod.item.OrderShieldItem;
import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.item.PotionPayloadItem;
import com.medievalmachinery.mod.item.StationaryCannonItem;
import com.medievalmachinery.mod.item.spear.SpearItem;
import com.medievalmachinery.mod.item.spear.SpearMaterial;
import com.medievalmachinery.mod.item.spear.SpearType;
import com.medievalmachinery.mod.item.workshop.RoyalSmelterBlockItem;
import com.medievalmachinery.mod.npc.armor.ArmorTier;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.registry.ModBlocks;
import net.minecraft.class_1738;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public final class ModItems {
    public static final class_1792 STONE_CANNONBALL = ModItems.register("stone_cannonball", (class_1792)new CannonballBlockItem(ModBlocks.STONE_CANNONBALL_PILE, new class_1792.class_1793().method_7889(64), "item.medieval-machinery.stone_cannonball"));
    public static final class_1792 IRON_CANNONBALL = ModItems.register("iron_cannonball", (class_1792)new CannonballBlockItem(ModBlocks.IRON_CANNONBALL_PILE, new class_1792.class_1793().method_7889(64), "item.medieval-machinery.iron_cannonball"));
    public static final class_1792 TNT_CANNONBALL = ModItems.register("tnt_cannonball", (class_1792)new CannonballBlockItem(ModBlocks.TNT_CANNONBALL_PILE, new class_1792.class_1793().method_7889(64), "item.medieval-machinery.tnt_cannonball"));
    public static final class_1792 HEAVY_IRON_CANNONBALL = ModItems.register("heavy_iron_cannonball", (class_1792)new CannonballBlockItem(ModBlocks.HEAVY_IRON_CANNONBALL_PILE, new class_1792.class_1793().method_7889(64), "item.medieval-machinery.heavy_iron_cannonball"));
    public static final class_1792 STATIONARY_CANNON = ModItems.register("stationary_cannon", new StationaryCannonItem(new class_1792.class_1793().method_7889(16)));
    public static final class_1792 FIELD_CANNON = ModItems.register("field_cannon", new FieldCannonItem(new class_1792.class_1793().method_7889(16)));
    public static final class_1792 COBBLESTONE_BUNDLE = ModItems.register("cobblestone_bundle", (class_1792)new class_1747(ModBlocks.COBBLESTONE_BUNDLE, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 POTION_PAYLOAD = ModItems.register("potion_payload", (class_1792)new PotionPayloadItem(new class_1792.class_1793().method_7889(16)));
    public static final class_1792 EXPLOSIVE_PAYLOAD = ModItems.register("explosive_payload", (class_1792)new CatapultSpecialPayloadItem(ModBlocks.EXPLOSIVE_PAYLOAD, new class_1792.class_1793().method_7889(64), "tooltip.medieval-machinery.explosive_payload"));
    public static final class_1792 FIRE_PAYLOAD = ModItems.register("fire_payload", (class_1792)new CatapultSpecialPayloadItem(ModBlocks.FIRE_PAYLOAD, new class_1792.class_1793().method_7889(64), "tooltip.medieval-machinery.fire_payload"));
    public static final class_1792 ROYAL_SMELTER = ModItems.register("royal_smelter", (class_1792)new RoyalSmelterBlockItem(ModBlocks.ROYAL_SMELTER, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 MACHINE_SPANNER = ModItems.register("machine_spanner", new MachineSpannerItem(new class_1792.class_1793().method_7895(64)));
    public static final class_1792 CATAPULT = ModItems.register("catapult", new CatapultItem(new class_1792.class_1793().method_7889(16)));
    public static final SpearItem WOOD_WINGED_SPEAR = ModItems.registerSpear(SpearType.WINGED, SpearMaterial.WOOD);
    public static final SpearItem STONE_WINGED_SPEAR = ModItems.registerSpear(SpearType.WINGED, SpearMaterial.STONE);
    public static final SpearItem COPPER_WINGED_SPEAR = ModItems.registerSpear(SpearType.WINGED, SpearMaterial.COPPER);
    public static final SpearItem IRON_WINGED_SPEAR = ModItems.registerSpear(SpearType.WINGED, SpearMaterial.IRON);
    public static final SpearItem DIAMOND_WINGED_SPEAR = ModItems.registerSpear(SpearType.WINGED, SpearMaterial.DIAMOND);
    public static final SpearItem NETHERITE_WINGED_SPEAR = ModItems.registerSpear(SpearType.WINGED, SpearMaterial.NETHERITE);
    public static final SpearItem WOOD_WAR_SPEAR = ModItems.registerSpear(SpearType.WAR, SpearMaterial.WOOD);
    public static final SpearItem STONE_WAR_SPEAR = ModItems.registerSpear(SpearType.WAR, SpearMaterial.STONE);
    public static final SpearItem COPPER_WAR_SPEAR = ModItems.registerSpear(SpearType.WAR, SpearMaterial.COPPER);
    public static final SpearItem IRON_WAR_SPEAR = ModItems.registerSpear(SpearType.WAR, SpearMaterial.IRON);
    public static final SpearItem DIAMOND_WAR_SPEAR = ModItems.registerSpear(SpearType.WAR, SpearMaterial.DIAMOND);
    public static final SpearItem NETHERITE_WAR_SPEAR = ModItems.registerSpear(SpearType.WAR, SpearMaterial.NETHERITE);
    public static final SpearItem WOOD_GUARD_SPEAR = ModItems.registerSpear(SpearType.GUARD, SpearMaterial.WOOD);
    public static final SpearItem STONE_GUARD_SPEAR = ModItems.registerSpear(SpearType.GUARD, SpearMaterial.STONE);
    public static final SpearItem COPPER_GUARD_SPEAR = ModItems.registerSpear(SpearType.GUARD, SpearMaterial.COPPER);
    public static final SpearItem IRON_GUARD_SPEAR = ModItems.registerSpear(SpearType.GUARD, SpearMaterial.IRON);
    public static final SpearItem DIAMOND_GUARD_SPEAR = ModItems.registerSpear(SpearType.GUARD, SpearMaterial.DIAMOND);
    public static final SpearItem NETHERITE_GUARD_SPEAR = ModItems.registerSpear(SpearType.GUARD, SpearMaterial.NETHERITE);
    public static final SpearItem WOOD_LONG_SPEAR = ModItems.registerSpear(SpearType.LONG, SpearMaterial.WOOD);
    public static final SpearItem STONE_LONG_SPEAR = ModItems.registerSpear(SpearType.LONG, SpearMaterial.STONE);
    public static final SpearItem COPPER_LONG_SPEAR = ModItems.registerSpear(SpearType.LONG, SpearMaterial.COPPER);
    public static final SpearItem IRON_LONG_SPEAR = ModItems.registerSpear(SpearType.LONG, SpearMaterial.IRON);
    public static final SpearItem DIAMOND_LONG_SPEAR = ModItems.registerSpear(SpearType.LONG, SpearMaterial.DIAMOND);
    public static final SpearItem NETHERITE_LONG_SPEAR = ModItems.registerSpear(SpearType.LONG, SpearMaterial.NETHERITE);
    public static final MedievalArmorItem ORDER_HELMET = (MedievalArmorItem)ModItems.register("order_helmet", (class_1792)new MedievalArmorItem(MedievalArmorMaterials.ORDER, class_1738.class_8051.field_41934, ArmorTier.ORDER, new class_1792.class_1793()));
    public static final MedievalArmorItem ORDER_CHESTPLATE = (MedievalArmorItem)ModItems.register("order_chestplate", (class_1792)new MedievalArmorItem(MedievalArmorMaterials.ORDER, class_1738.class_8051.field_41935, ArmorTier.ORDER, new class_1792.class_1793()));
    public static final MedievalArmorItem ORDER_LEGGINGS = (MedievalArmorItem)ModItems.register("order_leggings", (class_1792)new MedievalArmorItem(MedievalArmorMaterials.ORDER, class_1738.class_8051.field_41936, ArmorTier.ORDER, new class_1792.class_1793()));
    public static final MedievalArmorItem ORDER_BOOTS = (MedievalArmorItem)ModItems.register("order_boots", (class_1792)new MedievalArmorItem(MedievalArmorMaterials.ORDER, class_1738.class_8051.field_41937, ArmorTier.ORDER, new class_1792.class_1793()));
    public static final MedievalArmorItem GENERAL_HELMET = (MedievalArmorItem)ModItems.register("general_helmet", (class_1792)new MedievalArmorItem(MedievalArmorMaterials.GENERAL, class_1738.class_8051.field_41934, ArmorTier.GENERAL, new class_1792.class_1793()));
    public static final MedievalArmorItem GENERAL_CHESTPLATE = (MedievalArmorItem)ModItems.register("general_chestplate", (class_1792)new MedievalArmorItem(MedievalArmorMaterials.GENERAL, class_1738.class_8051.field_41935, ArmorTier.GENERAL, new class_1792.class_1793()));
    public static final MedievalArmorItem GENERAL_LEGGINGS = (MedievalArmorItem)ModItems.register("general_leggings", (class_1792)new MedievalArmorItem(MedievalArmorMaterials.GENERAL, class_1738.class_8051.field_41936, ArmorTier.GENERAL, new class_1792.class_1793()));
    public static final MedievalArmorItem GENERAL_BOOTS = (MedievalArmorItem)ModItems.register("general_boots", (class_1792)new MedievalArmorItem(MedievalArmorMaterials.GENERAL, class_1738.class_8051.field_41937, ArmorTier.GENERAL, new class_1792.class_1793()));
    public static final class_1792 ORDER_SIGIL_GOLD = ModItems.register("order_sigil_gold", new OrderSigilItem(OrderStyle.GOLD, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_COPPER = ModItems.register("order_sigil_copper", new OrderSigilItem(OrderStyle.COPPER, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_IRON = ModItems.register("order_sigil_iron", new OrderSigilItem(OrderStyle.IRON, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_DIAMOND = ModItems.register("order_sigil_diamond", new OrderSigilItem(OrderStyle.DIAMOND, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_REDSTONE = ModItems.register("order_sigil_redstone", new OrderSigilItem(OrderStyle.REDSTONE, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_AMETHYST = ModItems.register("order_sigil_amethyst", new OrderSigilItem(OrderStyle.AMETHYST, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_LAPIS = ModItems.register("order_sigil_lapis", new OrderSigilItem(OrderStyle.LAPIS, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_EMERALD = ModItems.register("order_sigil_emerald", new OrderSigilItem(OrderStyle.EMERALD, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_QUARTZ = ModItems.register("order_sigil_quartz", new OrderSigilItem(OrderStyle.QUARTZ, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_COAL = ModItems.register("order_sigil_coal", new OrderSigilItem(OrderStyle.COAL, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SIGIL_NETHERITE = ModItems.register("order_sigil_netherite", new OrderSigilItem(OrderStyle.NETHERITE, new class_1792.class_1793().method_7889(64)));
    public static final class_1792 ORDER_SHIELD_GOLD = ModItems.register("order_shield_gold", (class_1792)new OrderShieldItem(OrderStyle.GOLD, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_COPPER = ModItems.register("order_shield_copper", (class_1792)new OrderShieldItem(OrderStyle.COPPER, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_IRON = ModItems.register("order_shield_iron", (class_1792)new OrderShieldItem(OrderStyle.IRON, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_DIAMOND = ModItems.register("order_shield_diamond", (class_1792)new OrderShieldItem(OrderStyle.DIAMOND, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_REDSTONE = ModItems.register("order_shield_redstone", (class_1792)new OrderShieldItem(OrderStyle.REDSTONE, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_AMETHYST = ModItems.register("order_shield_amethyst", (class_1792)new OrderShieldItem(OrderStyle.AMETHYST, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_LAPIS = ModItems.register("order_shield_lapis", (class_1792)new OrderShieldItem(OrderStyle.LAPIS, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_EMERALD = ModItems.register("order_shield_emerald", (class_1792)new OrderShieldItem(OrderStyle.EMERALD, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_QUARTZ = ModItems.register("order_shield_quartz", (class_1792)new OrderShieldItem(OrderStyle.QUARTZ, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_COAL = ModItems.register("order_shield_coal", (class_1792)new OrderShieldItem(OrderStyle.COAL, new class_1792.class_1793().method_7895(500)));
    public static final class_1792 ORDER_SHIELD_NETHERITE = ModItems.register("order_shield_netherite", (class_1792)new OrderShieldItem(OrderStyle.NETHERITE, new class_1792.class_1793().method_7895(500)));

    private static SpearItem registerSpear(SpearType type, SpearMaterial material) {
        String path = material.id() + "_" + type.id() + "_spear";
        return (SpearItem)ModItems.register(path, (class_1792)new SpearItem(type, material, new class_1792.class_1793()));
    }

    public static boolean isSpear(class_1799 stack) {
        return stack != null && stack.method_7909() instanceof SpearItem;
    }

    public static class_1792 getOrderSigil(OrderStyle style) {
        return switch (style) {
            case OrderStyle.GOLD -> ORDER_SIGIL_GOLD;
            case OrderStyle.COPPER -> ORDER_SIGIL_COPPER;
            case OrderStyle.IRON -> ORDER_SIGIL_IRON;
            case OrderStyle.DIAMOND -> ORDER_SIGIL_DIAMOND;
            case OrderStyle.REDSTONE -> ORDER_SIGIL_REDSTONE;
            case OrderStyle.AMETHYST -> ORDER_SIGIL_AMETHYST;
            case OrderStyle.LAPIS -> ORDER_SIGIL_LAPIS;
            case OrderStyle.EMERALD -> ORDER_SIGIL_EMERALD;
            case OrderStyle.QUARTZ -> ORDER_SIGIL_QUARTZ;
            case OrderStyle.COAL -> ORDER_SIGIL_COAL;
            case OrderStyle.NETHERITE -> ORDER_SIGIL_NETHERITE;
            default -> throw new IllegalArgumentException("NONE has no order sigil item");
        };
    }

    private ModItems() {
    }

    private static class_1792 register(String path, class_1792 item) {
        return (class_1792)class_2378.method_10230((class_2378)class_7923.field_41178, (class_2960)MedievalMachineryCore.id(path), (Object)item);
    }

    public static boolean isCannonball(class_1792 item) {
        return item == STONE_CANNONBALL || item == IRON_CANNONBALL || item == HEAVY_IRON_CANNONBALL || item == TNT_CANNONBALL;
    }

    public static void register() {
        MedievalMachineryCore.LOGGER.info("Registering items for Medieval Machinery");
    }

    public static class_1792 getOrderShield(OrderStyle style) {
        return switch (style) {
            default -> throw new IncompatibleClassChangeError();
            case OrderStyle.GOLD -> ORDER_SHIELD_GOLD;
            case OrderStyle.COPPER -> ORDER_SHIELD_COPPER;
            case OrderStyle.IRON -> ORDER_SHIELD_IRON;
            case OrderStyle.DIAMOND -> ORDER_SHIELD_DIAMOND;
            case OrderStyle.REDSTONE -> ORDER_SHIELD_REDSTONE;
            case OrderStyle.AMETHYST -> ORDER_SHIELD_AMETHYST;
            case OrderStyle.LAPIS -> ORDER_SHIELD_LAPIS;
            case OrderStyle.EMERALD -> ORDER_SHIELD_EMERALD;
            case OrderStyle.QUARTZ -> ORDER_SHIELD_QUARTZ;
            case OrderStyle.COAL -> ORDER_SHIELD_COAL;
            case OrderStyle.NETHERITE -> ORDER_SHIELD_NETHERITE;
            case OrderStyle.NONE -> throw new IllegalArgumentException("NONE has no order shield item");
        };
    }
}

