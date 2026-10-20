/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
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
public final class CastleAreaEditorClient {
    private static boolean editing;
    private static UUID castleId;
    private static int defaultRadius;
    private static class_2338 center;
    private static List<class_2338> points;
    private static int particleTicks;
    private static class_2338 lastActionPos;
    private static boolean lastActionRemove;
    private static int lastActionPlayerAge;

    private CastleAreaEditorClient() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)WatchpostNetworking.CASTLE_AREA_EDIT_SNAPSHOT, (client, handler, buf, sender) -> {
            UUID id = buf.method_10790();
            int radius = Math.max(1, buf.method_10816());
            class_2338 nextCenter = buf.method_10811();
            int count = Math.max(0, Math.min(24, buf.method_10816()));
            ArrayList<class_2338> next = new ArrayList<class_2338>(count);
            for (int i = 0; i < count; ++i) {
                next.add(buf.method_10811());
            }
            client.execute(() -> {
                editing = true;
                castleId = id;
                defaultRadius = radius;
                center = nextCenter.method_10062();
                points = List.copyOf(next);
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
            class_5250 heading = class_2561.method_43471((String)"screen.medieval-machinery.castle_area.editor_heading");
            class_5250 instructions = class_2561.method_43471((String)"screen.medieval-machinery.castle_area.editor_instructions");
            class_5250 state = points.size() >= 3 ? class_2561.method_43469((String)"screen.medieval-machinery.castle_area.custom", (Object[])new Object[]{points.size()}) : class_2561.method_43469((String)"screen.medieval-machinery.castle_area.default", (Object[])new Object[]{defaultRadius, points.size()});
            int cx = client.method_22683().method_4486() / 2;
            context.method_27535(client.field_1772, (class_2561)heading, cx - client.field_1772.method_27525((class_5348)heading) / 2, 18, 0xFFFFFF);
            context.method_27535(client.field_1772, (class_2561)instructions, cx - client.field_1772.method_27525((class_5348)instructions) / 2, 31, 0xE0E0E0);
            context.method_27535(client.field_1772, (class_2561)state, cx - client.field_1772.method_27525((class_5348)state) / 2, 44, 11069091);
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!editing || client.field_1687 == null || client.field_1724 == null) {
                return;
            }
            if (++particleTicks % 4 != 0) {
                return;
            }
            CastleAreaEditorClient.drawBoundaryParticles(client);
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> CastleAreaEditorClient.resetLocal());
    }

    private static void drawBoundaryParticles(class_310 client) {
        int i;
        if (points.size() < 3 && center != null) {
            int samples = 48;
            for (i = 0; i < samples; ++i) {
                double angle = Math.PI * 2 * (double)i / (double)samples;
                double x = (double)center.method_10263() + 0.5 + Math.cos(angle) * (double)defaultRadius;
                double z = (double)center.method_10260() + 0.5 + Math.sin(angle) * (double)defaultRadius;
                client.field_1687.method_8406((class_2394)class_2398.field_11207, x, (double)center.method_10264() + 1.05, z, 0.0, 0.0, 0.0);
            }
        }
        for (class_2338 point : points) {
            client.field_1687.method_8406((class_2394)class_2398.field_11211, (double)point.method_10263() + 0.5, (double)point.method_10264() + 1.15, (double)point.method_10260() + 0.5, 0.0, 0.01, 0.0);
        }
        if (points.size() < 2) {
            return;
        }
        int edgeCount = points.size() >= 3 ? points.size() : points.size() - 1;
        for (i = 0; i < edgeCount; ++i) {
            class_2338 a = points.get(i);
            class_2338 b = points.get((i + 1) % points.size());
            double dx = b.method_10263() - a.method_10263();
            double dz = b.method_10260() - a.method_10260();
            double length = Math.sqrt(dx * dx + dz * dz);
            int samples = Math.max(1, (int)Math.ceil(length / 3.0));
            for (int sample = 0; sample <= samples; ++sample) {
                double t = (double)sample / (double)samples;
                double x = (double)a.method_10263() + 0.5 + dx * t;
                double z = (double)a.method_10260() + 0.5 + dz * t;
                double y = (double)a.method_10264() + 1.05 + (double)(b.method_10264() - a.method_10264()) * t;
                client.field_1687.method_8406((class_2394)class_2398.field_11207, x, y, z, 0.0, 0.0, 0.0);
            }
        }
    }

    public static boolean handleItemUse(class_310 client) {
        if (!editing || client.field_1724 == null) {
            return false;
        }
        class_239 class_2392 = client.field_1765;
        if (!(class_2392 instanceof class_3965)) {
            client.field_1724.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.castle_area.look_at_block"), true);
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
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.CASTLE_AREA_EDIT_ACTION, (class_2540)buf);
        return true;
    }

    public static boolean handleKey(int key, int action) {
        if (!editing) {
            return false;
        }
        if (action != 1) {
            return false;
        }
        if (key == 256) {
            CastleAreaEditorClient.stop();
            return true;
        }
        if (key == 76) {
            ClientPlayNetworking.send((class_2960)WatchpostNetworking.CASTLE_AREA_EDIT_CLEAR, (class_2540)PacketByteBufs.create());
            return true;
        }
        return false;
    }

    public static void stop() {
        if (!editing) {
            return;
        }
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.CASTLE_AREA_EDIT_STOP, (class_2540)PacketByteBufs.create());
        CastleAreaEditorClient.resetLocal();
    }

    private static void resetLocal() {
        editing = false;
        castleId = null;
        center = null;
        points = List.of();
        lastActionPos = null;
        lastActionPlayerAge = Integer.MIN_VALUE;
    }

    static {
        defaultRadius = 50;
        points = List.of();
        lastActionPlayerAge = Integer.MIN_VALUE;
    }
}

