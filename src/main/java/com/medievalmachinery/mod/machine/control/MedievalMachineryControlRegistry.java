/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_3222
 *  net.minecraft.class_5321
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.machine.control;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_1937;
import net.minecraft.class_3222;
import net.minecraft.class_5321;
import org.jetbrains.annotations.Nullable;

public final class MedievalMachineryControlRegistry {
    private static final Map<UUID, Session> SESSIONS = new HashMap<UUID, Session>();

    private MedievalMachineryControlRegistry() {
    }

    public static void start(class_3222 player, int entityId) {
        SESSIONS.put(player.method_5667(), new Session((class_5321<class_1937>)player.method_37908().method_27983(), entityId));
    }

    public static void stop(UUID player) {
        SESSIONS.remove(player);
    }

    @Nullable
    public static Session get(UUID player) {
        return SESSIONS.get(player);
    }

    public static boolean isControlling(UUID player) {
        return SESSIONS.containsKey(player);
    }

    public static void clearAll() {
        SESSIONS.clear();
    }

    public record Session(class_5321<class_1937> world, int entityId) {
    }
}

