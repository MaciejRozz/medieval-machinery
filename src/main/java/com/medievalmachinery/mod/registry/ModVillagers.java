/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper
 *  net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper
 *  net.minecraft.class_1297
 *  net.minecraft.class_1646
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1914
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2378
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2960
 *  net.minecraft.class_3218
 *  net.minecraft.class_3417
 *  net.minecraft.class_3852
 *  net.minecraft.class_3853
 *  net.minecraft.class_3853$class_1652
 *  net.minecraft.class_4140
 *  net.minecraft.class_4158
 *  net.minecraft.class_4208
 *  net.minecraft.class_5321
 *  net.minecraft.class_6880
 *  net.minecraft.class_7923
 *  net.minecraft.class_7924
 */
package com.medievalmachinery.mod.registry;

import com.google.common.collect.ImmutableSet;
import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.npc.organization.RoyalSmelterBindingState;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.registry.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Optional;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.class_1297;
import net.minecraft.class_1646;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1914;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2378;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_3417;
import net.minecraft.class_3852;
import net.minecraft.class_3853;
import net.minecraft.class_4140;
import net.minecraft.class_4158;
import net.minecraft.class_4208;
import net.minecraft.class_5321;
import net.minecraft.class_6880;
import net.minecraft.class_7923;
import net.minecraft.class_7924;

public final class ModVillagers {
    public static final class_2960 EXECUTIONER_ID = MedievalMachinery.id("executioner");
    public static final class_2960 MACHINE_ENGINEER_ID = MedievalMachinery.id("machine_engineer");
    public static final class_2960 ROYAL_ARMORER_ID = MedievalMachinery.id("royal_armorer");
    public static final class_5321<class_4158> GUILLOTINE_POI_KEY = class_5321.method_29179((class_5321)class_7924.field_41212, (class_2960)MedievalMachinery.id("guillotine"));
    public static final class_5321<class_4158> MACHINE_WORKSHOP_POI_KEY = class_5321.method_29179((class_5321)class_7924.field_41212, (class_2960)MedievalMachinery.id("machine_workshop"));
    public static final class_5321<class_4158> ROYAL_SMELTER_POI_KEY = class_5321.method_29179((class_5321)class_7924.field_41212, (class_2960)MedievalMachinery.id("royal_smelter"));
    public static class_4158 GUILLOTINE_POI;
    public static class_4158 MACHINE_WORKSHOP_POI;
    public static class_4158 ROYAL_SMELTER_POI;
    public static class_3852 EXECUTIONER;
    public static class_3852 MACHINE_ENGINEER;
    public static class_3852 ROYAL_ARMORER;
    private static boolean registered;

    private ModVillagers() {
    }

    public static void register() {
        if (registered) {
            return;
        }
        registered = true;
        MedievalMachinery.LOGGER.info("Registering Medieval Machinery villager POIs, professions and trades");
        GUILLOTINE_POI = PointOfInterestHelper.register((class_2960)MedievalMachinery.id("guillotine"), (int)1, (int)1, (Iterable)ModBlocks.GUILLOTINE.method_9595().method_11662());
        EXECUTIONER = (class_3852)class_2378.method_10230((class_2378)class_7923.field_41195, (class_2960)EXECUTIONER_ID, (Object)new class_3852("executioner", ModVillagers::isGuillotinePoi, ModVillagers::isGuillotinePoi, ImmutableSet.of(), ImmutableSet.of(), class_3417.field_24063));
        RegisteredContent.bindExecutionerProfession(EXECUTIONER);
        MACHINE_WORKSHOP_POI = PointOfInterestHelper.register((class_2960)MedievalMachinery.id("machine_workshop"), (int)1, (int)1, (Iterable)ModBlocks.MACHINE_WORKSHOP.method_9595().method_11662());
        MACHINE_ENGINEER = (class_3852)class_2378.method_10230((class_2378)class_7923.field_41195, (class_2960)MACHINE_ENGINEER_ID, (Object)new class_3852("machine_engineer", ModVillagers::isMachineWorkshopPoi, ModVillagers::isMachineWorkshopPoi, ImmutableSet.of(), ImmutableSet.of(), class_3417.field_14559));
        ROYAL_SMELTER_POI = PointOfInterestHelper.register((class_2960)MedievalMachinery.id("royal_smelter"), (int)1, (int)1, (Iterable)ModBlocks.ROYAL_SMELTER.method_9595().method_11662());
        ROYAL_ARMORER = (class_3852)class_2378.method_10230((class_2378)class_7923.field_41195, (class_2960)ROYAL_ARMORER_ID, (Object)new class_3852("royal_armorer", ModVillagers::isRoyalSmelterPoi, ModVillagers::isRoyalSmelterPoi, ImmutableSet.of(), ImmutableSet.of(), class_3417.field_17605));
        ModVillagers.registerTrades();
    }

    private static boolean isGuillotinePoi(class_6880<class_4158> entry) {
        return entry.method_40225(GUILLOTINE_POI_KEY);
    }

    private static boolean isMachineWorkshopPoi(class_6880<class_4158> entry) {
        return entry.method_40225(MACHINE_WORKSHOP_POI_KEY);
    }

    private static boolean isRoyalSmelterPoi(class_6880<class_4158> entry) {
        return entry != null && entry.method_40225(ROYAL_SMELTER_POI_KEY);
    }

    private static void registerTrades() {
        ModVillagers.registerRoyalArmorerTrades();
        TradeOfferHelper.registerVillagerOffers((class_3852)EXECUTIONER, (int)1, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8713, 12), new class_1799((class_1935)class_1802.field_8687, 1), 16, 2, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 2), new class_1799((class_1935)class_1802.field_23983, 4), 12, 2, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers((class_3852)EXECUTIONER, (int)2, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8745, 8), new class_1799((class_1935)class_1802.field_8687, 1), 12, 10, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 4), new class_1799((class_1935)class_1802.field_16539, 2), 8, 10, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers((class_3852)EXECUTIONER, (int)3, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8620, 6), new class_1799((class_1935)class_1802.field_8687, 1), 10, 20, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 5), new class_1799((class_1935)class_1802.field_8076, 8), 8, 20, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers((class_3852)EXECUTIONER, (int)4, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 7), new class_1799((class_1935)class_1802.field_8448, 1), 6, 30, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 6), new class_1799((class_1935)class_1802.field_8529, 4), 8, 30, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers((class_3852)EXECUTIONER, (int)5, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 12), new class_1799((class_1935)class_1802.field_16315, 1), 4, 40, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 10), new class_1799((class_1935)class_1802.field_8557, 1), 4, 40, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers((class_3852)MACHINE_ENGINEER, (int)1, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8583, 16), new class_1799((class_1935)class_1802.field_8687, 1), 16, 2, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8684, 16), new class_1799((class_1935)class_1802.field_8687, 1), 16, 2, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8170, 16), new class_1799((class_1935)class_1802.field_8687, 1), 16, 2, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 1), new class_1799((class_1935)class_1802.field_8792, 8), 16, 2, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 1), new class_1799((class_1935)class_1802.field_8701, 8), 16, 2, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers((class_3852)MACHINE_ENGINEER, (int)2, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_33400, 4), new class_1799((class_1935)class_1802.field_8687, 1), 12, 10, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 2), new class_1799((class_1935)class_1802.field_8620, 4), 12, 10, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers((class_3852)MACHINE_ENGINEER, (int)3, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 5), new class_1799((class_1935)class_1802.field_8773, 1), 8, 20, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 1), new class_1799((class_1935)class_1802.field_8320, 12), 12, 20, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers((class_3852)MACHINE_ENGINEER, (int)4, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 2), new class_1799((class_1935)class_1802.field_8745, 4), 10, 30, 0.05f));
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 4), new class_1799((class_1935)class_1802.field_8719, 2), 8, 30, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers((class_3852)MACHINE_ENGINEER, (int)5, factories -> {
            factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 24), new class_1799((class_1935)ModItems.MACHINE_SPANNER, 1), 4, 30, 0.05f));
            factories.add((entity, random) -> switch (random.method_43048(4)) {
                case 0 -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 8), new class_1799((class_1935)class_1802.field_8249, 4), 6, 30, 0.05f);
                case 1 -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 7), new class_1799((class_1935)class_1802.field_23983, 8), 6, 30, 0.05f);
                case 2 -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 10), new class_1799((class_1935)class_1802.field_8782, 1), 4, 30, 0.05f);
                default -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 6), new class_1799((class_1935)class_1802.field_8725, 16), 8, 30, 0.05f);
            });
        });
    }

    private static void registerRoyalArmorerTrades() {
        Int2ObjectMap vanilla = (Int2ObjectMap)class_3853.field_17067.get(class_3852.field_17052);
        if (vanilla == null) {
            return;
        }
        for (int level = 1; level <= 5; ++level) {
            class_3853.class_1652[] delegates = (class_3853.class_1652[])vanilla.get(level);
            if (delegates == null || delegates.length == 0) continue;
            int tradeLevel = level;
            TradeOfferHelper.registerVillagerOffers((class_3852)ROYAL_ARMORER, (int)tradeLevel, factories -> {
                for (class_3853.class_1652 delegate : delegates) {
                    factories.add((entity, random) -> ModVillagers.replaceDiamondArmorOffer(entity, delegate.method_7246(entity, random)));
                }
                if (tradeLevel == 5) {
                    factories.add((entity, random) -> new class_1914(new class_1799((class_1935)class_1802.field_8687, 16), new class_1799((class_1935)class_1802.field_8477, 1), 4, 30, 0.05f));
                }
            });
        }
    }

    private static class_1914 replaceDiamondArmorOffer(class_1297 merchant, class_1914 vanilla) {
        if (vanilla == null) {
            return null;
        }
        class_1799 sold = vanilla.method_8250();
        MedievalArmorItem target = ModVillagers.orderArmorForVanillaDiamond(sold);
        if (target == null) {
            return vanilla;
        }
        RoyalSmelterBindingState.Binding binding = ModVillagers.bindingForMerchant(merchant);
        if (binding == null) {
            return vanilla;
        }
        class_1799 sigil = new class_1799((class_1935)ModItems.getOrderSigil(binding.orderStyle()));
        OrderSigilItem.stamp(sigil, binding.kingdomId(), binding.orderStyle(), binding.kingdomName());
        class_1799 replacement = MedievalArmorItem.createFromSigil(target, sigil);
        if (replacement.method_7960()) {
            return vanilla;
        }
        if (sold.method_7985()) {
            class_2487 sourceNbt = sold.method_7969();
            class_2487 targetNbt = replacement.method_7948();
            if (sourceNbt.method_10545("Enchantments")) {
                targetNbt.method_10566("Enchantments", (class_2520)sourceNbt.method_10554("Enchantments", 10).method_10612());
            }
            if (sourceNbt.method_10545("RepairCost")) {
                targetNbt.method_10569("RepairCost", sourceNbt.method_10550("RepairCost"));
            }
        }
        return new class_1914(vanilla.method_8246().method_7972(), vanilla.method_8247().method_7972(), replacement, vanilla.method_8248(), vanilla.method_19279(), vanilla.method_19278());
    }

    private static MedievalArmorItem orderArmorForVanillaDiamond(class_1799 stack) {
        if (stack.method_31574(class_1802.field_8805)) {
            return ModItems.ORDER_HELMET;
        }
        if (stack.method_31574(class_1802.field_8058)) {
            return ModItems.ORDER_CHESTPLATE;
        }
        if (stack.method_31574(class_1802.field_8348)) {
            return ModItems.ORDER_LEGGINGS;
        }
        if (stack.method_31574(class_1802.field_8285)) {
            return ModItems.ORDER_BOOTS;
        }
        return null;
    }

    private static RoyalSmelterBindingState.Binding bindingForMerchant(class_1297 merchant) {
        class_1646 villager;
        class_1937 class_19372;
        if (!(merchant instanceof class_1646) || !((class_19372 = (villager = (class_1646)merchant).method_37908()) instanceof class_3218)) {
            return null;
        }
        class_3218 world = (class_3218)class_19372;
        Optional jobSite = villager.method_18868().method_46873(class_4140.field_18439);
        if (jobSite.isEmpty()) {
            return null;
        }
        class_4208 global = (class_4208)jobSite.get();
        if (!global.method_19442().equals(world.method_27983())) {
            return null;
        }
        if (!world.method_8320(global.method_19446()).method_27852(ModBlocks.ROYAL_SMELTER)) {
            return null;
        }
        return RoyalSmelterBindingState.get(world).get(global.method_19446());
    }
}

