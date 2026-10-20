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
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.network.ClientSoldierNetworking;
import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
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
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class MachineAssignmentEditorClient {
    private static boolean editing;
    private static int soldierEntityId;

    private MachineAssignmentEditorClient() {
    }

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> MachineAssignmentEditorClient.renderHud(context));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!editing) {
                return;
            }
            if (client.field_1687 == null || client.field_1724 == null) {
                MachineAssignmentEditorClient.resetLocal();
            }
        });
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!(world.field_9236 && editing && entity instanceof MedievalMachineryOperable)) {
                return class_1269.field_5811;
            }
            ClientSoldierNetworking.selectMachineAssignment(soldierEntityId, entity.method_5628());
            return class_1269.field_5812;
        });
    }

    public static void start(int entityId) {
        editing = true;
        soldierEntityId = entityId;
    }

    public static void assignmentSucceeded() {
        MachineAssignmentEditorClient.resetLocal();
    }

    public static boolean isEditing() {
        return editing;
    }

    public static boolean handleKey(int key, int action) {
        if (!editing) {
            return false;
        }
        if (key == 256 && action == 1) {
            ClientSoldierNetworking.stopMachineAssignment();
            MachineAssignmentEditorClient.resetLocal();
            return true;
        }
        return false;
    }

    private static void resetLocal() {
        editing = false;
        soldierEntityId = -1;
    }

    private static void renderHud(class_332 context) {
        if (!editing) {
            return;
        }
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1687 == null) {
            return;
        }
        class_5250 heading = class_2561.method_43471((String)"screen.medieval-machinery.machine_assignment.editor_heading");
        class_5250 instructions = class_2561.method_43471((String)"screen.medieval-machinery.machine_assignment.editor_instructions");
        class_5250 cancel = class_2561.method_43471((String)"screen.medieval-machinery.machine_assignment.editor_cancel");
        int centerX = client.method_22683().method_4486() / 2;
        int y = 18;
        context.method_27535(client.field_1772, (class_2561)heading, centerX - client.field_1772.method_27525((class_5348)heading) / 2, y, 0xFFFFFF);
        context.method_27535(client.field_1772, (class_2561)instructions, centerX - client.field_1772.method_27525((class_5348)instructions) / 2, y + 13, 0xE0E0E0);
        context.method_27535(client.field_1772, (class_2561)cancel, centerX - client.field_1772.method_27525((class_5348)cancel) / 2, y + 26, 0xC0C0C0);
    }

    static {
        soldierEntityId = -1;
    }
}

