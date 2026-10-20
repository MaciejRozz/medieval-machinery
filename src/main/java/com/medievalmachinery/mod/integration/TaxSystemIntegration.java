/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
 *  net.fabricmc.fabric.api.event.player.UseBlockCallback
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
 *  net.minecraft.class_1269
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_2595
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.integration;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.network.WatchpostNetworking;
import com.medievalmachinery.mod.npc.organization.CastleTaxService;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.class_1269;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2595;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class TaxSystemIntegration {
    private static final Map<UUID, ActivityState> ACTIVITY = new HashMap<UUID, ActivityState>();

    private TaxSystemIntegration() {
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            long now = server.method_30002().method_8510();
            for (class_3222 player : server.method_3760().method_14571()) {
                ActivityState state = ACTIVITY.computeIfAbsent(player.method_5667(), id -> ActivityState.from(player, now));
                if (!state.hasActivity(player)) continue;
                state.capture(player, now);
            }
            ACTIVITY.keySet().removeIf(id -> server.method_3760().method_14602(id) == null);
        });
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            CastleTaxService.tickPayouts(world);
            CastleTaxService.tick(world, TaxSystemIntegration::isKingdomActive);
        });
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            class_3222 serverPlayer;
            block5: {
                block4: {
                    if (world.field_9236 || !(world instanceof class_3218) || !(player instanceof class_3222)) break block4;
                    serverPlayer = (class_3222)player;
                    if (world.method_8321(hit.method_17777()) instanceof class_2595) break block5;
                }
                return class_1269.field_5811;
            }
            int payout = CastleTaxService.collectFromTaxChest(serverPlayer, hit.method_17777());
            if (payout > 0) {
                serverPlayer.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.tax.paid_out", (Object[])new Object[]{payout}), true);
            }
            return class_1269.field_5811;
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> server.execute(() -> {
            long now = server.method_30002().method_8510();
            ACTIVITY.put(handler.field_14140.method_5667(), ActivityState.from(handler.field_14140, now));
            WatchpostNetworking.sendTaxChestSync(handler.field_14140);
        }));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> ACTIVITY.remove(handler.field_14140.method_5667()));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> WatchpostNetworking.sendTaxChestSync(player));
    }

    private static boolean isKingdomActive(class_3218 world, UUID kingdomId) {
        KingdomData kingdom = KingdomManager.get(world).get(kingdomId);
        if (kingdom == null) {
            return false;
        }
        long now = world.method_8503().method_30002().method_8510();
        long timeout = (long)MedievalMachineryConfig.get().taxAfkTimeoutMinutes * 60L * 20L;
        for (class_3222 player : world.method_8503().method_3760().method_14571()) {
            ActivityState state;
            if (!kingdom.isMember(player.method_5667()) || (state = ACTIVITY.get(player.method_5667())) == null || now - state.lastActiveTick > timeout) continue;
            return true;
        }
        return false;
    }

    private static final class ActivityState {
        private class_243 pos;
        private float yaw;
        private float pitch;
        private int selectedSlot;
        private long lastActiveTick;

        private ActivityState() {
        }

        private static ActivityState from(class_3222 player, long now) {
            ActivityState state = new ActivityState();
            state.pos = player.method_19538();
            state.yaw = player.method_36454();
            state.pitch = player.method_36455();
            state.selectedSlot = player.method_31548().field_7545;
            state.lastActiveTick = now;
            return state;
        }

        private boolean hasActivity(class_3222 player) {
            class_243 current = player.method_19538();
            return current.method_1025(this.pos) > 0.0025 || Math.abs(player.method_36454() - this.yaw) > 0.5f || Math.abs(player.method_36455() - this.pitch) > 0.5f || player.method_31548().field_7545 != this.selectedSlot || player.method_6115();
        }

        private void capture(class_3222 player, long now) {
            this.pos = player.method_19538();
            this.yaw = player.method_36454();
            this.pitch = player.method_36455();
            this.selectedSlot = player.method_31548().field_7545;
            this.lastActiveTick = now;
        }
    }
}

