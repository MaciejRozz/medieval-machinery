/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.minecraft.class_2338
 *  net.minecraft.class_2960
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.network;

import com.medievalmachinery.mod.client.screen.GuillotineAdminScreen;
import com.medievalmachinery.mod.network.AdminGuiNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.class_2338;
import net.minecraft.class_2960;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class ClientAdminGuiNetworking {
    private ClientAdminGuiNetworking() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)AdminGuiNetworking.OPEN_ADMIN_GUI, (client, handler, buf, responseSender) -> {
            class_2338 pos = buf.method_10811();
            client.execute(() -> client.method_1507((class_437)new GuillotineAdminScreen(pos)));
        });
    }
}

