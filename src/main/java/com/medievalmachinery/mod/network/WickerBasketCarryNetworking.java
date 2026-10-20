/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.fabricmc.fabric.api.networking.v1.PlayerLookup
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_1657
 *  net.minecraft.class_2540
 *  net.minecraft.class_2960
 *  net.minecraft.class_3222
 *  net.minecraft.server.MinecraftServer
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.storage.WickerBasketCarryData;
import com.medievalmachinery.mod.storage.WickerBasketCarryManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1657;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_3222;
import net.minecraft.server.MinecraftServer;

public final class WickerBasketCarryNetworking {
    public static final class_2960 SYNC = MedievalMachinery.id("wicker_basket/carry_sync");

    private WickerBasketCarryNetworking() {
    }

    public static void registerServer() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            class_3222 joined = handler.field_14140;
            for (class_3222 player : PlayerLookup.all((MinecraftServer)server)) {
                WickerBasketCarryNetworking.send(joined, player);
            }
            WickerBasketCarryNetworking.sync(joined);
        });
    }

    public static void sync(class_3222 carriedBy) {
        for (class_3222 recipient : PlayerLookup.all((MinecraftServer)carriedBy.method_5682())) {
            WickerBasketCarryNetworking.send(recipient, carriedBy);
        }
    }

    private static void send(class_3222 recipient, class_3222 carriedBy) {
        WickerBasketCarryData data = WickerBasketCarryManager.get((class_1657)carriedBy);
        class_2540 buffer = PacketByteBufs.create();
        buffer.method_10797(carriedBy.method_5667());
        buffer.writeBoolean(data != null);
        if (data != null) {
            buffer.method_10794(data.toNbt());
        }
        ServerPlayNetworking.send((class_3222)recipient, (class_2960)SYNC, (class_2540)buffer);
    }
}

