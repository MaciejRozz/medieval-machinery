/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ModInitializer
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
 *  net.minecraft.class_1309
 *  net.minecraft.class_2960
 *  net.minecraft.class_3222
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.medievalmachinery.mod;

import com.medievalmachinery.mod.command.KingdomCommands;
import com.medievalmachinery.mod.command.ModCommands;
import com.medievalmachinery.mod.command.NpcAdminCommands;
import com.medievalmachinery.mod.command.TestCommands;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.integration.CastleCaptureIntegration;
import com.medievalmachinery.mod.integration.TaxSystemIntegration;
import com.medievalmachinery.mod.integration.TransientRuntimeStateIntegration;
import com.medievalmachinery.mod.interaction.ModInteractions;
import com.medievalmachinery.mod.item.OrderArmorSetBonusService;
import com.medievalmachinery.mod.network.AdminGuiNetworking;
import com.medievalmachinery.mod.network.KingdomNetworking;
import com.medievalmachinery.mod.network.MachineControlNetworking;
import com.medievalmachinery.mod.network.SoldierCommandNetworking;
import com.medievalmachinery.mod.network.SoldierNetworking;
import com.medievalmachinery.mod.network.SpearCombatNetworking;
import com.medievalmachinery.mod.network.WatchpostNetworking;
import com.medievalmachinery.mod.network.WickerBasketCarryNetworking;
import com.medievalmachinery.mod.npc.organization.SystemKingdoms;
import com.medievalmachinery.mod.platform.FabricLoaderHooks;
import com.medievalmachinery.mod.recipe.ModRecipes;
import com.medievalmachinery.mod.registry.ModBannerPatterns;
import com.medievalmachinery.mod.registry.ModBlockEntities;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.registry.ModEntities;
import com.medievalmachinery.mod.registry.ModItemGroups;
import com.medievalmachinery.mod.registry.ModItems;
import com.medievalmachinery.mod.registry.ModScreenHandlers;
import com.medievalmachinery.mod.registry.ModStatusEffects;
import com.medievalmachinery.mod.registry.ModVillagers;
import com.medievalmachinery.mod.sound.ModSounds;
import com.medievalmachinery.mod.storage.WickerBasketCarryManager;
import com.medievalmachinery.mod.test.TestWorldSpawnControl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.class_1309;
import net.minecraft.class_2960;
import net.minecraft.class_3222;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MedievalMachinery
implements ModInitializer {
    public static final String MOD_ID = "medieval-machinery";
    public static final Logger LOGGER = LoggerFactory.getLogger((String)"medieval-machinery");

    public void onInitialize() {
        LOGGER.info("Initializing Medieval Machinery");
        FabricLoaderHooks.install();
        MedievalMachineryConfig.register();
        ModBlocks.register();
        ModItems.register();
        ModStatusEffects.register();
        ModEntities.register();
        ModBannerPatterns.register();
        ModRecipes.register();
        ModItemGroups.register();
        ModBlockEntities.register();
        ModScreenHandlers.register();
        ModVillagers.register();
        ModSounds.register();
        ModCommands.register();
        KingdomCommands.register();
        NpcAdminCommands.register();
        TestCommands.register();
        TestWorldSpawnControl.register();
        ServerLifecycleEvents.SERVER_STARTED.register(server -> SystemKingdoms.ensureDragonKnights(server.method_30002()));
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (class_3222 player : server.method_3760().method_14571()) {
                OrderArmorSetBonusService.tickServer((class_1309)player);
            }
        });
        ModInteractions.register();
        AdminGuiNetworking.registerServer();
        MachineControlNetworking.registerServer();
        KingdomNetworking.registerServer();
        WatchpostNetworking.registerServer();
        TaxSystemIntegration.register();
        TransientRuntimeStateIntegration.register();
        CastleCaptureIntegration.register();
        SoldierNetworking.registerServer();
        SoldierCommandNetworking.registerServer();
        WickerBasketCarryNetworking.registerServer();
        SpearCombatNetworking.registerServer();
        WickerBasketCarryManager.register();
    }

    public static class_2960 id(String path) {
        return new class_2960(MOD_ID, path);
    }
}

