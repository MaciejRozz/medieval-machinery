/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
 *  net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
 *  net.fabricmc.fabric.api.event.player.UseBlockCallback
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1542
 *  net.minecraft.class_1657
 *  net.minecraft.class_1750
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2520
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3726
 *  net.minecraft.class_3965
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.storage;

import com.medievalmachinery.mod.block.storage.WickerBasketBlockEntity;
import com.medievalmachinery.mod.network.WickerBasketCarryNetworking;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.storage.WickerBasketCarrier;
import com.medievalmachinery.mod.storage.WickerBasketCarryBridge;
import com.medievalmachinery.mod.storage.WickerBasketCarryData;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2520;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3726;
import net.minecraft.class_3965;
import org.jetbrains.annotations.Nullable;

public final class WickerBasketCarryManager {
    private WickerBasketCarryManager() {
    }

    public static void register() {
        WickerBasketCarryBridge.install(WickerBasketCarryManager::pickUpFromBridge);
        UseBlockCallback.EVENT.register(WickerBasketCarryManager::useBlock);
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity instanceof class_3222) {
                class_3222 player = (class_3222)entity;
                WickerBasketCarryManager.dropCarriedBasket(player);
            }
        });
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            WickerBasketCarryData data = WickerBasketCarryManager.get((class_1657)oldPlayer);
            if (data != null) {
                WickerBasketCarryManager.set((class_1657)newPlayer, data);
            }
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> WickerBasketCarryNetworking.sync(newPlayer));
    }

    private static class_1269 pickUpFromBridge(class_3222 player, class_2338 pos, class_2680 state, class_2586 blockEntity) {
        if (!(blockEntity instanceof WickerBasketBlockEntity)) {
            return class_1269.field_5814;
        }
        WickerBasketBlockEntity basket = (WickerBasketBlockEntity)blockEntity;
        return WickerBasketCarryManager.pickUp(player, pos, state, basket);
    }

    public static class_1269 pickUp(class_3222 player, class_2338 pos, class_2680 state, WickerBasketBlockEntity basket) {
        if (WickerBasketCarryManager.get((class_1657)player) != null) {
            player.method_7353((class_2561)class_2561.method_43470((String)"You are already carrying a basket."), true);
            return class_1269.field_5814;
        }
        WickerBasketCarryData data = WickerBasketCarryData.of(state, basket.method_38244());
        basket.suppressNextDrop();
        if (!player.method_37908().method_8650(pos, false)) {
            return class_1269.field_5814;
        }
        WickerBasketCarryManager.set((class_1657)player, data);
        WickerBasketCarryNetworking.sync(player);
        return class_1269.field_21466;
    }

    @Nullable
    public static WickerBasketCarryData get(class_1657 player) {
        return ((WickerBasketCarrier)player).medievalMachinery$getCarriedBasket();
    }

    public static boolean isCarrying(class_1657 player) {
        return WickerBasketCarryManager.get(player) != null;
    }

    private static class_1269 useBlock(class_1657 player, class_1937 world, class_1268 hand, class_3965 hit) {
        if (hand != class_1268.field_5808 || !WickerBasketCarryManager.isCarrying(player)) {
            return class_1269.field_5811;
        }
        if (world.field_9236) {
            return class_1269.field_5812;
        }
        return WickerBasketCarryManager.tryPlace((class_3222)player, hit) ? class_1269.field_21466 : class_1269.field_5814;
    }

    private static boolean tryPlace(class_3222 player, class_3965 hit) {
        class_3218 world = player.method_51469();
        WickerBasketCarryData data = WickerBasketCarryManager.get((class_1657)player);
        if (data == null) {
            return false;
        }
        class_1799 placementStack = new class_1799((class_1935)ModBlocks.WICKER_BASKET);
        class_1750 context = new class_1750((class_1657)player, class_1268.field_5808, placementStack, hit);
        class_2338 targetPos = context.method_8037();
        class_2680 replacedState = world.method_8320(targetPos);
        class_2680 carriedState = data.blockState((class_1937)world);
        if (!(carriedState.method_27852(ModBlocks.WICKER_BASKET) && replacedState.method_26166(context) && world.method_8628(carriedState, targetPos, class_3726.method_16195((class_1297)player)))) {
            player.method_7353((class_2561)class_2561.method_43470((String)"The basket cannot be placed there."), true);
            return false;
        }
        if (!world.method_8652(targetPos, carriedState, 3)) {
            return false;
        }
        class_2586 class_25862 = world.method_8321(targetPos);
        if (!(class_25862 instanceof WickerBasketBlockEntity)) {
            world.method_8652(targetPos, replacedState, 3);
            return false;
        }
        WickerBasketBlockEntity basket = (WickerBasketBlockEntity)class_25862;
        basket.method_11014(data.blockEntityNbt().method_10553());
        basket.method_5431();
        world.method_8413(targetPos, carriedState, carriedState, 3);
        WickerBasketCarryManager.set((class_1657)player, null);
        WickerBasketCarryNetworking.sync(player);
        return true;
    }

    private static void dropCarriedBasket(class_3222 player) {
        class_1937 class_19372;
        WickerBasketCarryData data = WickerBasketCarryManager.get((class_1657)player);
        if (data == null || !((class_19372 = player.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 world = (class_3218)class_19372;
        class_1799 stack = new class_1799((class_1935)ModBlocks.WICKER_BASKET);
        stack.method_7948().method_10566("BlockEntityTag", (class_2520)data.blockEntityNbt().method_10553());
        class_1542 item = new class_1542((class_1937)world, player.method_23317(), player.method_23318() + 0.5, player.method_23321(), stack);
        item.method_6988();
        if (world.method_8649((class_1297)item)) {
            WickerBasketCarryManager.set((class_1657)player, null);
            WickerBasketCarryNetworking.sync(player);
        }
    }

    private static void set(class_1657 player, @Nullable WickerBasketCarryData data) {
        ((WickerBasketCarrier)player).medievalMachinery$setCarriedBasket(data);
    }
}

