/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
 *  net.minecraft.class_1259$class_1260
 *  net.minecraft.class_1259$class_1261
 *  net.minecraft.class_2338
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2960
 *  net.minecraft.class_3213
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_5321
 *  net.minecraft.class_7924
 *  net.minecraft.server.MinecraftServer
 */
package com.medievalmachinery.mod.integration;

import com.medievalmachinery.mod.block.garrison.WatchpostBlockEntity;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.organization.CastleCaptureStateManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.class_1259;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2960;
import net.minecraft.class_3213;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_5321;
import net.minecraft.class_7924;
import net.minecraft.server.MinecraftServer;

public final class CastleCaptureIntegration {
    private static final Map<UUID, class_3213> BARS = new HashMap<UUID, class_3213>();

    private CastleCaptureIntegration() {
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(CastleCaptureIntegration::tick);
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> CastleCaptureIntegration.abortCapturesForPlayer(server, handler.field_14140));
    }

    private static void tick(MinecraftServer server) {
        class_3218 overworld = server.method_30002();
        CastleCaptureStateManager manager = CastleCaptureStateManager.get(overworld);
        long now = overworld.method_8510();
        HashSet<UUID> live = new HashSet<UUID>();
        for (CastleCaptureStateManager.CaptureState state : manager.all()) {
            WatchpostBlockEntity watchpost;
            live.add(state.castleId());
            class_3218 world = CastleCaptureIntegration.resolveWorld(server, state.dimension());
            class_3222 player = server.method_3760().method_14602(state.playerId());
            class_3213 bar = BARS.computeIfAbsent(state.castleId(), id -> new class_3213((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.capture_progress"), class_1259.class_1260.field_5785, class_1259.class_1261.field_5795));
            if (world == null || player == null) {
                bar.method_14094();
                manager.cancel(state.castleId());
                continue;
            }
            class_2586 be = world.method_8321(state.pos());
            if (!(be instanceof WatchpostBlockEntity) || (watchpost = (WatchpostBlockEntity)be).getCastleId() == null || !state.castleId().equals(watchpost.getCastleId())) {
                CastleCaptureIntegration.abort(manager, state, player, null, bar, false);
                continue;
            }
            if (!watchpost.canContinueCapture(player, state)) {
                CastleCaptureIntegration.abort(manager, state, player, watchpost, bar, true);
                continue;
            }
            if (CastleCaptureIntegration.mustCancel(player, world, state.pos())) {
                CastleCaptureIntegration.abort(manager, state, player, watchpost, bar, player.method_5805());
                continue;
            }
            CastleCaptureIntegration.updateViewers(server, world, state.pos(), bar);
            bar.method_5408(state.progress(now));
            if (!state.complete(now)) continue;
            if (watchpost.completeActiveCapture(player)) {
                bar.method_5408(1.0f);
                bar.method_5413((class_2561)class_2561.method_43471((String)"message.medieval-machinery.watchpost.conquered"));
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.watchpost.conquered"), false);
            } else {
                watchpost.returnCaptureSigil(player);
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.watchpost.capture_aborted"), false);
            }
            manager.finish(state.castleId());
            bar.method_14094();
            BARS.remove(state.castleId());
        }
        BARS.keySet().removeIf(id -> {
            if (live.contains(id)) {
                return false;
            }
            class_3213 bar = BARS.get(id);
            if (bar != null) {
                bar.method_14094();
            }
            return true;
        });
    }

    private static void abortCapturesForPlayer(MinecraftServer server, class_3222 player) {
        if (server == null || player == null) {
            return;
        }
        CastleCaptureStateManager manager = CastleCaptureStateManager.get(server.method_30002());
        for (CastleCaptureStateManager.CaptureState state : manager.all()) {
            class_2586 be;
            if (!player.method_5667().equals(state.playerId())) continue;
            class_3218 world = CastleCaptureIntegration.resolveWorld(server, state.dimension());
            WatchpostBlockEntity watchpost = null;
            if (world != null && (be = world.method_8321(state.pos())) instanceof WatchpostBlockEntity) {
                WatchpostBlockEntity candidate = (WatchpostBlockEntity)be;
                if (state.castleId().equals(candidate.getCastleId())) {
                    watchpost = candidate;
                }
            }
            class_3213 bar = BARS.get(state.castleId());
            CastleCaptureIntegration.abort(manager, state, player, watchpost, bar, watchpost != null);
        }
    }

    private static void abort(CastleCaptureStateManager manager, CastleCaptureStateManager.CaptureState state, class_3222 player, WatchpostBlockEntity watchpost, class_3213 bar, boolean returnSigil) {
        if (returnSigil && watchpost != null && player != null) {
            watchpost.returnCaptureSigil(player);
        }
        if (player != null) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.watchpost.capture_aborted"), false);
        }
        if (bar != null) {
            bar.method_14094();
        }
        manager.cancel(state.castleId());
        BARS.remove(state.castleId());
    }

    private static boolean mustCancel(class_3222 player, class_3218 world, class_2338 pos) {
        if (!player.method_5805() || player.method_51469() != world) {
            return true;
        }
        double max = Math.max(2, MedievalMachineryConfig.get().castleCaptureMaxDistance);
        if (player.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5) > max * max) {
            return true;
        }
        return player.method_6065() != null && player.field_6012 - player.method_6117() <= 2;
    }

    private static void updateViewers(MinecraftServer server, class_3218 world, class_2338 pos, class_3213 bar) {
        double radius = Math.max(16, MedievalMachineryConfig.get().castleCaptureBossbarRadius);
        double r2 = radius * radius;
        HashSet<UUID> wanted = new HashSet<UUID>();
        for (class_3222 online : server.method_3760().method_14571()) {
            if (online.method_51469() != world || !(online.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5) <= r2)) continue;
            wanted.add(online.method_5667());
            if (bar.method_14092().contains(online)) continue;
            bar.method_14088(online);
        }
        for (class_3222 shown : Set.copyOf(bar.method_14092())) {
            if (wanted.contains(shown.method_5667())) continue;
            bar.method_14089(shown);
        }
    }

    private static class_3218 resolveWorld(MinecraftServer server, String id) {
        try {
            class_2960 identifier = new class_2960(id);
            return server.method_3847(class_5321.method_29179((class_5321)class_7924.field_41223, (class_2960)identifier));
        }
        catch (Exception ignored) {
            return null;
        }
    }
}

