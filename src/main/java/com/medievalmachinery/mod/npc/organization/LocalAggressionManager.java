/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 *  net.minecraft.server.MinecraftServer
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.class_3218;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

public final class LocalAggressionManager {
    public static final int DEFAULT_TIMEOUT_TICKS = 6000;
    private static final Map<MinecraftServer, Map<Key, Long>> ACTIVE = new WeakHashMap<MinecraftServer, Map<Key, Long>>();

    private LocalAggressionManager() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void mark(class_3218 world, UUID playerId, @Nullable UUID castleId, @Nullable UUID kingdomId) {
        if (world == null || playerId == null || castleId == null) {
            return;
        }
        MinecraftServer server = world.method_8503();
        long expires = server.method_3780() + 6000;
        Map<MinecraftServer, Map<Key, Long>> map = ACTIVE;
        synchronized (map) {
            ACTIVE.computeIfAbsent(server, ignored -> new HashMap()).put(new Key(playerId, castleId, kingdomId), expires);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isActive(class_3218 world, UUID playerId, @Nullable UUID castleId, @Nullable UUID kingdomId) {
        if (world == null || playerId == null || castleId == null) {
            return false;
        }
        MinecraftServer server = world.method_8503();
        long now = server.method_3780();
        Map<MinecraftServer, Map<Key, Long>> map = ACTIVE;
        synchronized (map) {
            Map<Key, Long> map2 = ACTIVE.get(server);
            if (map2 == null) {
                return false;
            }
            map2.entrySet().removeIf(e -> (Long)e.getValue() <= now);
            if (map2.isEmpty()) {
                ACTIVE.remove(server);
            }
            return LocalAggressionManager.matchesActive(map2, now, playerId, castleId, kingdomId);
        }
    }

    public static void refreshIfActive(class_3218 world, UUID playerId, @Nullable UUID castleId, @Nullable UUID kingdomId) {
        if (!LocalAggressionManager.isActive(world, playerId, castleId, kingdomId)) {
            return;
        }
        LocalAggressionManager.mark(world, playerId, castleId, kingdomId);
    }

    private static boolean matchesActive(Map<Key, Long> map, long now, UUID playerId, @Nullable UUID castleId, @Nullable UUID kingdomId) {
        if (castleId == null) {
            return false;
        }
        for (Map.Entry<Key, Long> entry : map.entrySet()) {
            if (entry.getValue() <= now) continue;
            Key key = entry.getKey();
            if (!key.playerId.equals(playerId) || !castleId.equals(key.castleId)) continue;
            return true;
        }
        return false;
    }

    private record Key(UUID playerId, @Nullable UUID castleId, @Nullable UUID kingdomId) {
    }
}

