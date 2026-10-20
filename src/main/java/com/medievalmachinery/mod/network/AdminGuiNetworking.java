/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_2338
 *  net.minecraft.class_2586
 *  net.minecraft.class_2960
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import com.medievalmachinery.mod.block.machine.guillotine.PunishmentMode;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2960;

public final class AdminGuiNetworking {
    public static final class_2960 OPEN_ADMIN_GUI = MedievalMachinery.id("open_admin_gui");
    public static final class_2960 ADMIN_GUI_ACTION = MedievalMachinery.id("admin_gui_action");
    public static final int ACTION_MODE_NONE = 0;
    public static final int ACTION_MODE_ADVENTURE = 1;
    public static final int ACTION_MODE_BAN_24H = 2;
    public static final int ACTION_MODE_PERMANENT = 3;
    public static final int ACTION_MODE_CUSTOM = 4;
    public static final int ACTION_TOGGLE_BROADCAST = 10;
    public static final int ACTION_SAVE_NAME = 20;
    public static final int ACTION_SAVE_CUSTOM_COMMAND = 21;
    public static final int ACTION_TOGGLE_HEAD_DROP = 22;
    public static final int ACTION_TOGGLE_EXECUTIONER = 24;

    private AdminGuiNetworking() {
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver((class_2960)ADMIN_GUI_ACTION, (server, player, handler, buf, responseSender) -> {
            class_2338 pos = buf.method_10811();
            int action = buf.method_10816();
            String textValue = buf.readableBytes() > 0 ? buf.method_10800(512) : "";
            server.execute(() -> {
                if (!player.method_5687(2)) {
                    return;
                }
                class_2586 patt0$temp = player.method_37908().method_8321(pos);
                if (!(patt0$temp instanceof GuillotineBlockEntity)) {
                    return;
                }
                GuillotineBlockEntity guillotine = (GuillotineBlockEntity)patt0$temp;
                if (player.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5) > 64.0) {
                    return;
                }
                switch (action) {
                    case 0: {
                        guillotine.setPunishmentMode(PunishmentMode.NONE);
                        break;
                    }
                    case 1: {
                        guillotine.setPunishmentMode(PunishmentMode.ADVENTURE_10_MINUTES);
                        break;
                    }
                    case 2: {
                        guillotine.setPunishmentMode(PunishmentMode.BAN_24_HOURS);
                        break;
                    }
                    case 3: {
                        guillotine.setPunishmentMode(PunishmentMode.PERMANENT_BAN);
                        break;
                    }
                    case 4: {
                        guillotine.setPunishmentMode(PunishmentMode.CUSTOM_COMMAND);
                        break;
                    }
                    case 10: {
                        guillotine.toggleBroadcastExecution();
                        break;
                    }
                    case 20: {
                        guillotine.setDisplayName(textValue);
                        break;
                    }
                    case 21: {
                        guillotine.setCustomCommand(textValue);
                        break;
                    }
                    case 22: {
                        guillotine.toggleHeadDrop();
                        break;
                    }
                    case 24: {
                        guillotine.toggleExecutionerEnabled();
                        break;
                    }
                }
            });
        });
    }
}

