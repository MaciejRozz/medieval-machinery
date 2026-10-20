/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.class_1934
 *  net.minecraft.class_3222
 *  net.minecraft.server.MinecraftServer
 */
package com.medievalmachinery.mod.execution;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medievalmachinery.mod.MedievalMachinery;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1934;
import net.minecraft.class_3222;
import net.minecraft.server.MinecraftServer;

public final class GuillotinePunishmentManager {
    public static final Duration ADVENTURE_DURATION = Duration.ofMinutes(10L);
    public static final Duration TEMP_BAN_DURATION = Duration.ofHours(24L);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path SAVE_PATH = FabricLoader.getInstance().getConfigDir().resolve("medieval-machinery-punishments.json");
    private static final List<AdventurePunishment> ADVENTURE = new ArrayList<AdventurePunishment>();
    private static final List<TemporaryBan> TEMPORARY_BANS = new ArrayList<TemporaryBan>();
    private static MinecraftServer currentServer;
    private static int tickCounter;

    private GuillotinePunishmentManager() {
    }

    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            currentServer = server;
            GuillotinePunishmentManager.load();
            GuillotinePunishmentManager.processExpiredEntries(server);
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            GuillotinePunishmentManager.save();
            currentServer = null;
        });
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (++tickCounter >= 20) {
                tickCounter = 0;
                GuillotinePunishmentManager.processExpiredEntries(server);
            }
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> GuillotinePunishmentManager.applyAdventureAfterRespawn(newPlayer));
    }

    public static void scheduleAdventure(class_3222 player) {
        GuillotinePunishmentManager.removeAdventure(player.method_5667());
        ADVENTURE.add(new AdventurePunishment(player.method_5667().toString(), player.field_13974.method_14257().method_8381(), Instant.now().plus(ADVENTURE_DURATION).toEpochMilli()));
        GuillotinePunishmentManager.save();
    }

    public static void scheduleTemporaryBan(MinecraftServer server, String playerName, String reason) {
        TEMPORARY_BANS.removeIf(entry -> entry.playerName().equalsIgnoreCase(playerName));
        TEMPORARY_BANS.add(new TemporaryBan(playerName, Instant.now().plus(TEMP_BAN_DURATION).toEpochMilli()));
        server.method_3734().method_44252(server.method_3739(), "ban " + playerName + " " + reason);
        GuillotinePunishmentManager.save();
    }

    private static void applyAdventureAfterRespawn(class_3222 player) {
        AdventurePunishment punishment = GuillotinePunishmentManager.findAdventure(player.method_5667());
        if (punishment == null) {
            return;
        }
        if (punishment.expiresAt() <= System.currentTimeMillis()) {
            GuillotinePunishmentManager.restoreOriginalGameMode(player, punishment);
            GuillotinePunishmentManager.removeAdventure(player.method_5667());
            GuillotinePunishmentManager.save();
            return;
        }
        player.method_7336(class_1934.field_9216);
    }

    private static void processExpiredEntries(MinecraftServer server) {
        long now = System.currentTimeMillis();
        boolean changed = false;
        Iterator<AdventurePunishment> adventureIterator = ADVENTURE.iterator();
        while (adventureIterator.hasNext()) {
            UUID uuid;
            AdventurePunishment punishment = adventureIterator.next();
            if (punishment.expiresAt() > now) continue;
            try {
                uuid = UUID.fromString(punishment.playerUuid());
            }
            catch (IllegalArgumentException exception) {
                adventureIterator.remove();
                changed = true;
                continue;
            }
            class_3222 player = server.method_3760().method_14602(uuid);
            if (player != null) {
                GuillotinePunishmentManager.restoreOriginalGameMode(player, punishment);
            }
            adventureIterator.remove();
            changed = true;
        }
        Iterator<TemporaryBan> banIterator = TEMPORARY_BANS.iterator();
        while (banIterator.hasNext()) {
            TemporaryBan temporaryBan = banIterator.next();
            if (temporaryBan.expiresAt() > now) continue;
            server.method_3734().method_44252(server.method_3739(), "pardon " + temporaryBan.playerName());
            banIterator.remove();
            changed = true;
        }
        if (changed) {
            GuillotinePunishmentManager.save();
        }
    }

    private static void restoreOriginalGameMode(class_3222 player, AdventurePunishment punishment) {
        class_1934 original = class_1934.method_8378((String)punishment.previousGameMode(), (class_1934)class_1934.field_9215);
        player.method_7336(original);
    }

    private static AdventurePunishment findAdventure(UUID uuid) {
        String value = uuid.toString();
        for (AdventurePunishment punishment : ADVENTURE) {
            if (!punishment.playerUuid().equals(value)) continue;
            return punishment;
        }
        return null;
    }

    private static void removeAdventure(UUID uuid) {
        String value = uuid.toString();
        ADVENTURE.removeIf(punishment -> punishment.playerUuid().equals(value));
    }

    private static void load() {
        ADVENTURE.clear();
        TEMPORARY_BANS.clear();
        if (!Files.exists(SAVE_PATH, new LinkOption[0])) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(SAVE_PATH);){
            StoredData data = (StoredData)GSON.fromJson((Reader)reader, StoredData.class);
            if (data == null) {
                return;
            }
            if (data.adventure != null) {
                ADVENTURE.addAll(data.adventure);
            }
            if (data.temporaryBans != null) {
                TEMPORARY_BANS.addAll(data.temporaryBans);
            }
        }
        catch (IOException exception) {
            MedievalMachinery.LOGGER.error("Could not load guillotine punishments.", (Throwable)exception);
        }
    }

    private static void save() {
        try {
            Files.createDirectories(SAVE_PATH.getParent(), new FileAttribute[0]);
            StoredData data = new StoredData();
            data.adventure = new ArrayList<AdventurePunishment>(ADVENTURE);
            data.temporaryBans = new ArrayList<TemporaryBan>(TEMPORARY_BANS);
            try (BufferedWriter writer = Files.newBufferedWriter(SAVE_PATH, new OpenOption[0]);){
                GSON.toJson((Object)data, (Appendable)writer);
            }
        }
        catch (IOException exception) {
            MedievalMachinery.LOGGER.error("Could not save guillotine punishments.", (Throwable)exception);
        }
    }

    private record AdventurePunishment(String playerUuid, String previousGameMode, long expiresAt) {
    }

    private record TemporaryBan(String playerName, long expiresAt) {
    }

    private static final class StoredData {
        private List<AdventurePunishment> adventure = new ArrayList<AdventurePunishment>();
        private List<TemporaryBan> temporaryBans = new ArrayList<TemporaryBan>();

        private StoredData() {
        }
    }
}

