/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.minecraft.class_1657
 *  net.minecraft.class_2487
 *  net.minecraft.class_2960
 */
package com.medievalmachinery.mod.client.storage;

import com.medievalmachinery.mod.network.WickerBasketCarryNetworking;
import com.medievalmachinery.mod.storage.WickerBasketCarryData;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.class_1657;
import net.minecraft.class_2487;
import net.minecraft.class_2960;

@Environment(value=EnvType.CLIENT)
public final class ClientWickerBasketCarryState {
    private static final Map<UUID, WickerBasketCarryData> CARRIED = new HashMap<UUID, WickerBasketCarryData>();

    private ClientWickerBasketCarryState() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)WickerBasketCarryNetworking.SYNC, (client, handler, buffer, sender) -> {
            UUID playerUuid = buffer.method_10790();
            boolean carrying = buffer.readBoolean();
            class_2487 nbt = carrying ? buffer.method_10798() : null;
            client.execute(() -> {
                if (nbt == null) {
                    CARRIED.remove(playerUuid);
                } else {
                    WickerBasketCarryData data = WickerBasketCarryData.fromNbt(nbt);
                    if (data != null) {
                        CARRIED.put(playerUuid, data);
                    }
                }
            });
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> CARRIED.clear());
    }

    public static WickerBasketCarryData get(class_1657 player) {
        return CARRIED.get(player.method_5667());
    }

    public static boolean isCarrying(class_1657 player) {
        return ClientWickerBasketCarryState.get(player) != null;
    }
}

