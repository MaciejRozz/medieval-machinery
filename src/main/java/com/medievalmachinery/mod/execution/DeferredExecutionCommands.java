/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
 *  net.minecraft.class_3222
 *  net.minecraft.server.MinecraftServer
 */
package com.medievalmachinery.mod.execution;

import com.medievalmachinery.mod.MedievalMachinery;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.class_3222;
import net.minecraft.server.MinecraftServer;

public final class DeferredExecutionCommands {
    private static final Map<UUID, List<String>> PENDING_COMMANDS = new ConcurrentHashMap<UUID, List<String>>();

    private DeferredExecutionCommands() {
    }

    public static void register() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> DeferredExecutionCommands.executePendingCommands(newPlayer));
    }

    public static void schedule(class_3222 player, List<String> commands) {
        if (commands == null || commands.isEmpty()) {
            return;
        }
        PENDING_COMMANDS.put(player.method_5667(), List.copyOf(commands));
    }

    private static void executePendingCommands(class_3222 player) {
        List<String> commands = PENDING_COMMANDS.remove(player.method_5667());
        if (commands == null || commands.isEmpty()) {
            return;
        }
        MinecraftServer server = player.method_5682();
        if (server == null) {
            return;
        }
        for (String command : commands) {
            try {
                int result = server.method_3734().method_44252(server.method_3739(), command);
                MedievalMachinery.LOGGER.info("Deferred guillotine command executed for {}: '{}' (result {})", new Object[]{player.method_7334().getName(), command, result});
            }
            catch (Exception exception) {
                MedievalMachinery.LOGGER.error("Failed to execute deferred guillotine command for {}: '{}'", new Object[]{player.method_7334().getName(), command, exception});
            }
        }
    }
}

