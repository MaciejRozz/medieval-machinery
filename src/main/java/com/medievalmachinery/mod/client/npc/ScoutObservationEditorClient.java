/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.minecraft.class_239
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_3965
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.network.ClientSoldierNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.class_239;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_5250;
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class ScoutObservationEditorClient {
    private static boolean editing;
    private static int soldierEntityId;

    private ScoutObservationEditorClient() {
    }

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            if (!editing) {
                return;
            }
            class_310 client = class_310.method_1551();
            if (client.field_1724 == null) {
                return;
            }
            class_5250 heading = class_2561.method_43471((String)"screen.medieval-machinery.scout.observation_heading");
            class_5250 instructions = class_2561.method_43471((String)"screen.medieval-machinery.scout.observation_instructions");
            int cx = client.method_22683().method_4486() / 2;
            context.method_27535(client.field_1772, (class_2561)heading, cx - client.field_1772.method_27525((class_5348)heading) / 2, 18, 0xFFFFFF);
            context.method_27535(client.field_1772, (class_2561)instructions, cx - client.field_1772.method_27525((class_5348)instructions) / 2, 31, 0xE0E0E0);
        });
    }

    public static void start(int entityId) {
        if (entityId <= 0) {
            return;
        }
        soldierEntityId = entityId;
        editing = true;
        ClientSoldierNetworking.startObservationPointEditing(entityId);
    }

    public static boolean handleItemUse(class_310 client) {
        if (!editing || client.field_1724 == null) {
            return false;
        }
        class_239 class_2392 = client.field_1765;
        if (class_2392 instanceof class_3965) {
            class_3965 hit = (class_3965)class_2392;
            ClientSoldierNetworking.setObservationPoint(hit.method_17777());
            editing = false;
            soldierEntityId = -1;
            return true;
        }
        client.field_1724.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.scout.observation_look_at_block"), true);
        return true;
    }

    public static boolean handleKey(int key, int action) {
        if (!editing) {
            return false;
        }
        if (key == 256 && action == 1) {
            ClientSoldierNetworking.stopObservationPointEditing();
            editing = false;
            soldierEntityId = -1;
            return true;
        }
        return false;
    }

    static {
        soldierEntityId = -1;
    }
}

