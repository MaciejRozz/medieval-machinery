/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_2338
 *  net.minecraft.class_239
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3965
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.garrison;

import com.medievalmachinery.mod.network.WatchpostNetworking;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_5250;
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class RallyPointEditorClient {
    private static boolean editing;
    private static UUID castleId;
    private static class_2338 rallyPoint;
    private static int particleTicks;
    private static class_2338 lastActionPos;
    private static boolean lastActionRemove;
    private static int lastActionPlayerAge;

    private RallyPointEditorClient() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)WatchpostNetworking.RALLY_EDIT_SNAPSHOT, (client, handler, buf, sender) -> {
            UUID id = buf.method_10790();
            class_2338 point = buf.readBoolean() ? buf.method_10811() : null;
            client.execute(() -> {
                editing = true;
                castleId = id;
                rallyPoint = point == null ? null : point.method_10062();
                particleTicks = 0;
            });
        });
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            if (!editing) {
                return;
            }
            class_310 client = class_310.method_1551();
            if (client.field_1724 == null) {
                return;
            }
            class_5250 heading = class_2561.method_43471((String)"screen.medieval-machinery.rally.editor_heading");
            class_5250 instructions = class_2561.method_43471((String)"screen.medieval-machinery.rally.editor_instructions");
            int cx = client.method_22683().method_4486() / 2;
            context.method_27535(client.field_1772, (class_2561)heading, cx - client.field_1772.method_27525((class_5348)heading) / 2, 18, 0xFFFFFF);
            context.method_27535(client.field_1772, (class_2561)instructions, cx - client.field_1772.method_27525((class_5348)instructions) / 2, 31, 0xE0E0E0);
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!editing || client.field_1687 == null || client.field_1724 == null || rallyPoint == null) {
                return;
            }
            if (++particleTicks % 4 != 0) {
                return;
            }
            double x = (double)rallyPoint.method_10263() + 0.5;
            double y = (double)rallyPoint.method_10264() + 1.05;
            double z = (double)rallyPoint.method_10260() + 0.5;
            client.field_1687.method_8406((class_2394)class_2398.field_11211, x, y + 0.65, z, 0.0, 0.01, 0.0);
            client.field_1687.method_8406((class_2394)class_2398.field_11207, x + 0.48, y, z + 0.48, 0.0, 0.01, 0.0);
            client.field_1687.method_8406((class_2394)class_2398.field_11207, x - 0.48, y, z + 0.48, 0.0, 0.01, 0.0);
            client.field_1687.method_8406((class_2394)class_2398.field_11207, x + 0.48, y, z - 0.48, 0.0, 0.01, 0.0);
            client.field_1687.method_8406((class_2394)class_2398.field_11207, x - 0.48, y, z - 0.48, 0.0, 0.01, 0.0);
        });
    }

    public static boolean handleItemUse(class_310 client) {
        if (!editing || client.field_1724 == null) {
            return false;
        }
        class_239 class_2392 = client.field_1765;
        if (!(class_2392 instanceof class_3965)) {
            client.field_1724.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.rally.look_at_block"), true);
            return true;
        }
        class_3965 hit = (class_3965)class_2392;
        class_2338 clicked = hit.method_17777();
        boolean remove = client.field_1724.method_5715();
        if (clicked.equals((Object)lastActionPos) && remove == lastActionRemove && client.field_1724.field_6012 - lastActionPlayerAge <= 5) {
            return true;
        }
        lastActionPos = clicked.method_10062();
        lastActionRemove = remove;
        lastActionPlayerAge = client.field_1724.field_6012;
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(clicked);
        buf.writeBoolean(remove);
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.RALLY_EDIT_ACTION, (class_2540)buf);
        return true;
    }

    public static boolean handleKey(int key, int action) {
        if (!editing) {
            return false;
        }
        if (key == 256 && action == 1) {
            RallyPointEditorClient.stop();
            return true;
        }
        return false;
    }

    public static void stop() {
        if (!editing) {
            return;
        }
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.RALLY_EDIT_STOP, (class_2540)PacketByteBufs.create());
        editing = false;
        castleId = null;
        rallyPoint = null;
        lastActionPos = null;
        lastActionPlayerAge = Integer.MIN_VALUE;
    }

    static {
        lastActionPlayerAge = Integer.MIN_VALUE;
    }
}

