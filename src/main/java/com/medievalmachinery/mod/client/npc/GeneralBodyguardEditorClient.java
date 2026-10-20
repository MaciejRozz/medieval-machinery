/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.fabricmc.fabric.api.event.player.UseEntityCallback
 *  net.minecraft.class_1269
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_332
 *  net.minecraft.class_5250
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.network.ClientSoldierCommandNetworking;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.class_1269;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_5250;

@Environment(value=EnvType.CLIENT)
public final class GeneralBodyguardEditorClient {
    private static boolean editing;

    private GeneralBodyguardEditorClient() {
    }

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> GeneralBodyguardEditorClient.renderHud(context));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (editing && (client.field_1687 == null || client.field_1724 == null)) {
                GeneralBodyguardEditorClient.resetLocal();
            }
        });
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!(world.field_9236 && editing && entity instanceof MedievalSoldierEntity)) {
                return class_1269.field_5811;
            }
            ClientSoldierCommandNetworking.selectBodyguard(entity.method_5628(), player.method_5715());
            return class_1269.field_5812;
        });
    }

    public static void start(int generalEntityId) {
        if (generalEntityId <= 0) {
            return;
        }
        editing = true;
        ClientSoldierCommandNetworking.startBodyguardEdit(generalEntityId);
    }

    public static boolean handleKey(int key, int action) {
        if (!editing) {
            return false;
        }
        if (key == 256 && action == 1) {
            ClientSoldierCommandNetworking.stopBodyguardEdit();
            GeneralBodyguardEditorClient.resetLocal();
            return true;
        }
        return false;
    }

    private static void resetLocal() {
        editing = false;
    }

    private static void renderHud(class_332 context) {
        if (!editing) {
            return;
        }
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1687 == null) {
            return;
        }
        class_5250 heading = class_2561.method_43471((String)"screen.medieval-machinery.general_bodyguard.editor_heading");
        class_5250 assign = class_2561.method_43471((String)"screen.medieval-machinery.general_bodyguard.editor_assign");
        class_5250 remove = class_2561.method_43471((String)"screen.medieval-machinery.general_bodyguard.editor_remove");
        class_5250 cancel = class_2561.method_43471((String)"screen.medieval-machinery.general_bodyguard.editor_cancel");
        int centerX = client.method_22683().method_4486() / 2;
        int y = 18;
        context.method_27534(client.field_1772, (class_2561)heading, centerX, y, 0xFFFFFF);
        context.method_27534(client.field_1772, (class_2561)assign, centerX, y + 13, 0xE0E0E0);
        context.method_27534(client.field_1772, (class_2561)remove, centerX, y + 26, 0xE0E0E0);
        context.method_27534(client.field_1772, (class_2561)cancel, centerX, y + 39, 0xC0C0C0);
    }
}

