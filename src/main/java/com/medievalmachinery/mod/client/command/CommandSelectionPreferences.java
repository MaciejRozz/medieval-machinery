/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.loader.api.FabricLoader
 */
package com.medievalmachinery.mod.client.command;

import com.medievalmachinery.mod.network.SoldierCommandNetworking;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Properties;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(value=EnvType.CLIENT)
public final class CommandSelectionPreferences {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("medieval-machinery-command-selection.properties");
    private static final Properties VALUES = CommandSelectionPreferences.load();
    private static int selectedScope = CommandSelectionPreferences.parseScope();
    private static UUID selectedCastle = CommandSelectionPreferences.parseCastle();

    private CommandSelectionPreferences() {
    }

    public static int getSelectedScope() {
        return selectedScope;
    }

    public static void setSelectedScope(int scope) {
        selectedScope = CommandSelectionPreferences.isValid(scope) ? scope : SoldierCommandNetworking.scopeForGroupSlot(1);
        VALUES.setProperty("scope", Integer.toString(selectedScope));
        CommandSelectionPreferences.save();
    }

    public static UUID getSelectedCastle() {
        return selectedCastle;
    }

    public static void setSelectedCastle(UUID castleId) {
        selectedCastle = castleId;
        if (castleId == null) {
            VALUES.remove("castle");
        } else {
            VALUES.setProperty("castle", castleId.toString());
        }
        CommandSelectionPreferences.save();
    }

    private static int parseScope() {
        try {
            int value = Integer.parseInt(VALUES.getProperty("scope", ""));
            if (CommandSelectionPreferences.isValid(value)) {
                return value;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return SoldierCommandNetworking.scopeForGroupSlot(1);
    }

    private static UUID parseCastle() {
        try {
            String value = VALUES.getProperty("castle", "").trim();
            return value.isEmpty() ? null : UUID.fromString(value);
        }
        catch (Exception ignored) {
            return null;
        }
    }

    private static Properties load() {
        Properties result;
        block8: {
            result = new Properties();
            try {
                if (!Files.isRegularFile(FILE, new LinkOption[0])) break block8;
                try (BufferedReader reader = Files.newBufferedReader(FILE, StandardCharsets.UTF_8);){
                    result.load(reader);
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return result;
    }

    private static void save() {
        try {
            Files.createDirectories(FILE.getParent(), new FileAttribute[0]);
            try (BufferedWriter writer = Files.newBufferedWriter(FILE, StandardCharsets.UTF_8, new OpenOption[0]);){
                VALUES.store(writer, "Medieval Machinery command menu selection");
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private static boolean isValid(int scope) {
        if (scope == 0 || scope == 1 || scope == 6 || scope == 7) {
            return true;
        }
        return scope >= 1001 && scope <= 1512;
    }
}

