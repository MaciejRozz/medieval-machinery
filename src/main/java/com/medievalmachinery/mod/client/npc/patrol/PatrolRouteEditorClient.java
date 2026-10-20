/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.minecraft.class_2338
 *  net.minecraft.class_239
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_2400
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_332
 *  net.minecraft.class_3965
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.npc.patrol;

import com.medievalmachinery.mod.client.network.ClientSoldierNetworking;
import java.util.List;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_2400;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3965;
import net.minecraft.class_5250;
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class PatrolRouteEditorClient {
    private static boolean editing;
    private static int soldierEntityId;
    private static UUID soldierUuid;
    private static int nextWaypointIndex;
    private static List<class_2338> waypoints;
    private static int particleTicker;
    private static int missingSoldierTicks;
    private static boolean removeInputLatched;

    private PatrolRouteEditorClient() {
    }

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> PatrolRouteEditorClient.renderEditorHud(context));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!editing) {
                return;
            }
            if (client.field_1687 == null || client.field_1724 == null) {
                editing = false;
                soldierEntityId = -1;
                soldierUuid = null;
                nextWaypointIndex = 0;
                waypoints = List.of();
                return;
            }
            if (client.field_1687.method_8469(soldierEntityId) == null) {
                if (++missingSoldierTicks > 40) {
                    PatrolRouteEditorClient.stopEditing();
                    return;
                }
            } else {
                missingSoldierTicks = 0;
            }
            if (!client.field_1690.field_1904.method_1434()) {
                removeInputLatched = false;
            }
            if (++particleTicker % 4 != 0) {
                return;
            }
            PatrolRouteEditorClient.renderWaypointParticles(client);
        });
    }

    public static boolean isEditing() {
        return editing;
    }

    public static int getSoldierEntityId() {
        return soldierEntityId;
    }

    public static void acceptSnapshot(boolean editMode, int entityId, UUID uuid, int nextIndex, List<class_2338> points) {
        boolean wasEditing = editing;
        editing = editMode;
        soldierEntityId = editMode ? entityId : -1;
        soldierUuid = editMode ? uuid : null;
        nextWaypointIndex = points.isEmpty() ? 0 : Math.floorMod(nextIndex, points.size());
        waypoints = editMode ? List.copyOf(points) : List.of();
        particleTicker = 0;
        missingSoldierTicks = 0;
        removeInputLatched = false;
        class_310 client = class_310.method_1551();
        if (client.field_1724 != null && editing && !wasEditing) {
            particleTicker = 0;
        }
    }

    public static boolean handleItemUse(class_310 client) {
        if (!editing || client.field_1724 == null || client.field_1687 == null) {
            return false;
        }
        if (client.field_1724.method_5715()) {
            if (!removeInputLatched) {
                ClientSoldierNetworking.removeLastPatrolPoint();
                removeInputLatched = true;
            }
            return true;
        }
        class_239 class_2392 = client.field_1765;
        if (class_2392 instanceof class_3965) {
            class_3965 blockHit = (class_3965)class_2392;
            ClientSoldierNetworking.addPatrolPoint(blockHit.method_17777());
            return true;
        }
        client.field_1724.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.patrol.look_at_block"), true);
        return true;
    }

    public static boolean handleKey(int key, int action) {
        if (!editing) {
            return false;
        }
        if (key == 256 && action == 1) {
            PatrolRouteEditorClient.stopEditing();
            return true;
        }
        return false;
    }

    public static void stopEditing() {
        if (!editing) {
            return;
        }
        int returnEntityId = soldierEntityId;
        ClientSoldierNetworking.stopPatrolEditing();
        editing = false;
        soldierEntityId = -1;
        soldierUuid = null;
        nextWaypointIndex = 0;
        waypoints = List.of();
        missingSoldierTicks = 0;
        removeInputLatched = false;
        if (returnEntityId >= 0) {
            ClientSoldierNetworking.openSoldierInventory(returnEntityId);
        }
    }

    private static void renderEditorHud(class_332 context) {
        if (!editing) {
            return;
        }
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1687 == null) {
            return;
        }
        class_5250 heading = class_2561.method_43471((String)"screen.medieval-machinery.patrol.editor_heading");
        class_5250 instructions = class_2561.method_43471((String)"screen.medieval-machinery.patrol.editor_instructions");
        class_5250 count = class_2561.method_43469((String)"screen.medieval-machinery.patrol.waypoints", (Object[])new Object[]{waypoints.size()});
        int centerX = client.method_22683().method_4486() / 2;
        int y = 18;
        context.method_27535(client.field_1772, (class_2561)heading, centerX - client.field_1772.method_27525((class_5348)heading) / 2, y, 0xFFFFFF);
        context.method_27535(client.field_1772, (class_2561)instructions, centerX - client.field_1772.method_27525((class_5348)instructions) / 2, y + 13, 0xE0E0E0);
        context.method_27535(client.field_1772, (class_2561)count, centerX - client.field_1772.method_27525((class_5348)count) / 2, y + 26, 0xC0C0C0);
    }

    private static void renderWaypointParticles(class_310 client) {
        if (client.field_1687 == null) {
            return;
        }
        for (int i = 0; i < waypoints.size(); ++i) {
            class_2338 pos = waypoints.get(i);
            double x = (double)pos.method_10263() + 0.5;
            double y = (double)pos.method_10264() + 1.08;
            double z = (double)pos.method_10260() + 0.5;
            boolean next = i == nextWaypointIndex;
            class_2400 particle = next ? class_2398.field_11211 : class_2398.field_11207;
            client.field_1687.method_8406((class_2394)particle, x, y + 0.25, z, 0.0, 0.015, 0.0);
            client.field_1687.method_8406((class_2394)particle, x + 0.28, y, z, 0.0, 0.01, 0.0);
            client.field_1687.method_8406((class_2394)particle, x - 0.28, y, z, 0.0, 0.01, 0.0);
            client.field_1687.method_8406((class_2394)particle, x, y, z + 0.28, 0.0, 0.01, 0.0);
            client.field_1687.method_8406((class_2394)particle, x, y, z - 0.28, 0.0, 0.01, 0.0);
        }
    }

    static {
        soldierEntityId = -1;
        waypoints = List.of();
    }
}

