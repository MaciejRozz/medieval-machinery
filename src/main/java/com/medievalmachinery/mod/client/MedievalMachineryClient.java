/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
 *  net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
 *  net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
 *  net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
 *  net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
 *  net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
 *  net.minecraft.class_1007
 *  net.minecraft.class_1299
 *  net.minecraft.class_1646
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1844
 *  net.minecraft.class_1921
 *  net.minecraft.class_1935
 *  net.minecraft.class_2960
 *  net.minecraft.class_3883
 *  net.minecraft.class_3887
 *  net.minecraft.class_3929
 *  net.minecraft.class_5272
 *  net.minecraft.class_5601
 *  net.minecraft.class_591
 *  net.minecraft.class_620
 *  net.minecraft.class_742
 *  net.minecraft.class_953
 *  net.minecraft.class_963
 */
package com.medievalmachinery.mod.client;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.client.ammunition.PotionPayloadBlockEntityRenderer;
import com.medievalmachinery.mod.client.ammunition.SpecialPayloadBlockEntityRenderer;
import com.medievalmachinery.mod.client.ammunition.SpecialPayloadBuiltinItemRenderer;
import com.medievalmachinery.mod.client.animation.GuillotinePlayerAnimation;
import com.medievalmachinery.mod.client.animation.SpearPlayerAnimation;
import com.medievalmachinery.mod.client.cannon.DecorativeFortressCannonBuiltinItemRenderer;
import com.medievalmachinery.mod.client.cannon.DecorativeFortressCannonRenderer;
import com.medievalmachinery.mod.client.cannon.StationaryCannonBuiltinItemRenderer;
import com.medievalmachinery.mod.client.cannon.StationaryCannonEntityRenderer;
import com.medievalmachinery.mod.client.cannon.StationaryCannonRenderer;
import com.medievalmachinery.mod.client.catapult.CatapultBuiltinItemRenderer;
import com.medievalmachinery.mod.client.catapult.CatapultRenderer;
import com.medievalmachinery.mod.client.command.ClientCommandGroupStatus;
import com.medievalmachinery.mod.client.command.CommandClientController;
import com.medievalmachinery.mod.client.control.ClientMachineControlController;
import com.medievalmachinery.mod.client.control.MachineControlHud;
import com.medievalmachinery.mod.client.fieldcannon.DecorativeFieldCannonBuiltinItemRenderer;
import com.medievalmachinery.mod.client.fieldcannon.DecorativeFieldCannonRenderer;
import com.medievalmachinery.mod.client.fieldcannon.FieldCannonBuiltinItemRenderer;
import com.medievalmachinery.mod.client.fieldcannon.FieldCannonRenderer;
import com.medievalmachinery.mod.client.garrison.CastleAreaEditorClient;
import com.medievalmachinery.mod.client.garrison.RallyPointEditorClient;
import com.medievalmachinery.mod.client.garrison.SupplyChestEditorClient;
import com.medievalmachinery.mod.client.garrison.TaxChestEditorClient;
import com.medievalmachinery.mod.client.garrison.TaxChestEmeraldRenderer;
import com.medievalmachinery.mod.client.garrison.WatchpostBlockEntityRenderer;
import com.medievalmachinery.mod.client.garrison.WatchpostBreakClient;
import com.medievalmachinery.mod.client.garrison.WatchpostScreen;
import com.medievalmachinery.mod.client.guillotine.GuillotineRenderer;
import com.medievalmachinery.mod.client.guillotine.RoyalGuillotineBuiltinItemRenderer;
import com.medievalmachinery.mod.client.item.OrderShieldBuiltinItemRenderer;
import com.medievalmachinery.mod.client.network.ClientAdminGuiNetworking;
import com.medievalmachinery.mod.client.network.ClientKingdomNetworking;
import com.medievalmachinery.mod.client.network.ClientSoldierCommandNetworking;
import com.medievalmachinery.mod.client.network.ClientSoldierNetworking;
import com.medievalmachinery.mod.client.npc.GeneralBodyguardEditorClient;
import com.medievalmachinery.mod.client.npc.MachineAssignmentEditorClient;
import com.medievalmachinery.mod.client.npc.MedievalSoldierModel;
import com.medievalmachinery.mod.client.npc.MedievalSoldierRenderer;
import com.medievalmachinery.mod.client.npc.MedievalSoldierVanillaArmorModel;
import com.medievalmachinery.mod.client.npc.QuartermasterFarmEditorClient;
import com.medievalmachinery.mod.client.npc.RoyalArmorerEmblemFeatureRenderer;
import com.medievalmachinery.mod.client.npc.ScoutObservationEditorClient;
import com.medievalmachinery.mod.client.npc.SoldierInventoryScreen;
import com.medievalmachinery.mod.client.npc.patrol.PatrolRouteEditorClient;
import com.medievalmachinery.mod.client.screen.machine.cannon.FieldCannonScreen;
import com.medievalmachinery.mod.client.screen.machine.cannon.StationaryCannonScreen;
import com.medievalmachinery.mod.client.storage.ClientWickerBasketCarryState;
import com.medievalmachinery.mod.client.storage.WickerBasketBuiltinItemRenderer;
import com.medievalmachinery.mod.client.storage.WickerBasketCarryFeatureRenderer;
import com.medievalmachinery.mod.client.storage.WickerBasketRenderer;
import com.medievalmachinery.mod.client.storage.WickerBasketScreen;
import com.medievalmachinery.mod.client.wheel.WheelBuiltinItemRenderer;
import com.medievalmachinery.mod.client.wheel.WheelRenderer;
import com.medievalmachinery.mod.client.workshop.MachineWorkshopScreen;
import com.medievalmachinery.mod.item.PotionPayloadItem;
import com.medievalmachinery.mod.item.workshop.RoyalSmelterBlockItem;
import com.medievalmachinery.mod.registry.ModBlockEntities;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.registry.ModEntities;
import com.medievalmachinery.mod.registry.ModItems;
import com.medievalmachinery.mod.registry.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.class_1007;
import net.minecraft.class_1299;
import net.minecraft.class_1646;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1844;
import net.minecraft.class_1921;
import net.minecraft.class_1935;
import net.minecraft.class_2960;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_3929;
import net.minecraft.class_5272;
import net.minecraft.class_5601;
import net.minecraft.class_591;
import net.minecraft.class_620;
import net.minecraft.class_742;
import net.minecraft.class_953;
import net.minecraft.class_963;

@Environment(value=EnvType.CLIENT)
public final class MedievalMachineryClient
implements ClientModInitializer {
    public void onInitializeClient() {
        class_5272.method_27879((class_1792)ModItems.ROYAL_SMELTER, (class_2960)MedievalMachinery.id("royal_order"), (stack, world, entity, seed) -> RoyalSmelterBlockItem.getBoundOrder(stack).ordinal());
        class_2960 blockingPredicate = new class_2960("blocking");
        for (class_1792 shield : new class_1792[]{ModItems.ORDER_SHIELD_GOLD, ModItems.ORDER_SHIELD_COPPER, ModItems.ORDER_SHIELD_IRON, ModItems.ORDER_SHIELD_DIAMOND, ModItems.ORDER_SHIELD_REDSTONE, ModItems.ORDER_SHIELD_AMETHYST, ModItems.ORDER_SHIELD_LAPIS, ModItems.ORDER_SHIELD_EMERALD, ModItems.ORDER_SHIELD_QUARTZ, ModItems.ORDER_SHIELD_COAL, ModItems.ORDER_SHIELD_NETHERITE}) {
            class_5272.method_27879((class_1792)shield, (class_2960)blockingPredicate, (stack, world, entity, seed) -> entity != null && entity.method_6115() && entity.method_6030() == stack ? 1.0f : 0.0f);
        }
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex != 0) {
                return 0xFFFFFF;
            }
            class_1799 storedPotion = PotionPayloadItem.getStoredPotion(stack);
            return storedPotion.method_7960() ? 15661306 : class_1844.method_8064((class_1799)storedPotion);
        }, (Object[])new class_1935[]{ModItems.POTION_PAYLOAD});
        EntityRendererRegistry.register(ModEntities.CANNONBALL, class_953::new);
        EntityRendererRegistry.register(ModEntities.FIELD_CANNON, FieldCannonRenderer::new);
        EntityRendererRegistry.register(ModEntities.CATAPULT_STONE, class_953::new);
        EntityRendererRegistry.register(ModEntities.CATAPULT, CatapultRenderer::new);
        EntityRendererRegistry.register(ModEntities.STATIONARY_CANNON, StationaryCannonEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer((class_5601)MedievalSoldierModel.LAYER, MedievalSoldierModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer((class_5601)MedievalSoldierVanillaArmorModel.INNER_LAYER, MedievalSoldierVanillaArmorModel::createInnerLayer);
        EntityModelLayerRegistry.registerModelLayer((class_5601)MedievalSoldierVanillaArmorModel.OUTER_LAYER, MedievalSoldierVanillaArmorModel::createOuterLayer);
        EntityRendererRegistry.register(ModEntities.SOLDIER, MedievalSoldierRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.POTION_PAYLOAD, PotionPayloadBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.SPECIAL_PAYLOAD, SpecialPayloadBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.GUILLOTINE, GuillotineRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.STATIONARY_CANNON, StationaryCannonRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.DECORATIVE_FORTRESS_CANNON, DecorativeFortressCannonRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.DECORATIVE_FIELD_CANNON, DecorativeFieldCannonRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.WICKER_BASKET, WickerBasketRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.WHEEL, WheelRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.WATCHPOST, WatchpostBlockEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROYAL_SMELTER, class_1921.method_23581());
        RoyalGuillotineBuiltinItemRenderer.register();
        DecorativeFortressCannonBuiltinItemRenderer.register();
        DecorativeFieldCannonBuiltinItemRenderer.register();
        FieldCannonBuiltinItemRenderer.register();
        StationaryCannonBuiltinItemRenderer.register();
        CatapultBuiltinItemRenderer.register();
        WickerBasketBuiltinItemRenderer.register();
        WheelBuiltinItemRenderer.register();
        SpecialPayloadBuiltinItemRenderer.register();
        OrderShieldBuiltinItemRenderer.register();
        ClientWickerBasketCarryState.register();
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, renderer, helper, context) -> {
            if (entityType == class_1299.field_6097 && renderer instanceof class_1007) {
                class_1007 playerRenderer = (class_1007)renderer;
                helper.register((class_3887)new WickerBasketCarryFeatureRenderer((class_3883<class_742, class_591<class_742>>)playerRenderer));
            }
            if (entityType == class_1299.field_6077 && renderer instanceof class_963) {
                class_963 villagerRenderer = (class_963)renderer;
                helper.register((class_3887)new RoyalArmorerEmblemFeatureRenderer((class_3883<class_1646, class_620<class_1646>>)villagerRenderer));
            }
        });
        GuillotinePlayerAnimation.register();
        SpearPlayerAnimation.register();
        ClientAdminGuiNetworking.register();
        ClientKingdomNetworking.register();
        ClientSoldierNetworking.register();
        MachineAssignmentEditorClient.register();
        GeneralBodyguardEditorClient.register();
        ClientSoldierCommandNetworking.register();
        PatrolRouteEditorClient.register();
        ScoutObservationEditorClient.register();
        QuartermasterFarmEditorClient.register();
        SupplyChestEditorClient.register();
        CastleAreaEditorClient.register();
        RallyPointEditorClient.register();
        TaxChestEditorClient.register();
        WatchpostBreakClient.register();
        TaxChestEmeraldRenderer.register();
        CommandClientController.register();
        ClientCommandGroupStatus.register();
        ClientMachineControlController.register();
        MachineControlHud.register();
        class_3929.method_17542(ModScreenHandlers.STATIONARY_CANNON, StationaryCannonScreen::new);
        class_3929.method_17542(ModScreenHandlers.FIELD_CANNON, FieldCannonScreen::new);
        class_3929.method_17542(ModScreenHandlers.WICKER_BASKET, WickerBasketScreen::new);
        class_3929.method_17542(ModScreenHandlers.MACHINE_WORKSHOP, MachineWorkshopScreen::new);
        class_3929.method_17542(ModScreenHandlers.WATCHPOST, WatchpostScreen::new);
        class_3929.method_17542(ModScreenHandlers.SOLDIER_INVENTORY, SoldierInventoryScreen::new);
    }
}

