/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_2338
 *  net.minecraft.class_2540
 *  net.minecraft.class_2960
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.garrison;

import com.medievalmachinery.mod.client.garrison.WatchpostBreakScreen;
import com.medievalmachinery.mod.network.WatchpostNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class WatchpostBreakClient {
    private WatchpostBreakClient() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)WatchpostNetworking.BREAK_PROMPT, (client, handler, buf, sender) -> {
            class_2338 pos = buf.method_10811();
            String castleName = buf.method_10800(48);
            client.execute(() -> client.method_1507((class_437)new WatchpostBreakScreen(pos, castleName)));
        });
    }

    public static void send(class_2338 pos, int action) {
        class_2540 out = PacketByteBufs.create();
        out.method_10807(pos);
        out.method_10804(action);
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.BREAK_ACTION, (class_2540)out);
    }
}

