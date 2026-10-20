/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.class_2168
 *  net.minecraft.class_2170
 *  net.minecraft.class_2186
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_2382
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.command;

import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2186;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_2382;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_3222;

public final class ModCommands {
    private static final int DEFAULT_RADIUS = 8;

    private ModCommands() {
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"guillotine").then(class_2170.method_9247((String)"help").executes(context -> ModCommands.showHelp((class_2168)context.getSource())))).then(((LiteralArgumentBuilder)class_2170.method_9247((String)"seat").requires(source -> source.method_9259(2))).then(((RequiredArgumentBuilder)class_2170.method_9244((String)"player", (ArgumentType)class_2186.method_9305()).executes(context -> ModCommands.seatPlayer((class_2168)context.getSource(), class_2186.method_9315((CommandContext)context, (String)"player"), 8))).then(class_2170.method_9244((String)"radius", (ArgumentType)IntegerArgumentType.integer((int)1, (int)32)).executes(context -> ModCommands.seatPlayer((class_2168)context.getSource(), class_2186.method_9315((CommandContext)context, (String)"player"), IntegerArgumentType.getInteger((CommandContext)context, (String)"radius"))))))).then(((LiteralArgumentBuilder)class_2170.method_9247((String)"release").requires(source -> source.method_9259(2))).then(((RequiredArgumentBuilder)class_2170.method_9244((String)"player", (ArgumentType)class_2186.method_9305()).executes(context -> ModCommands.releasePlayer((class_2168)context.getSource(), class_2186.method_9315((CommandContext)context, (String)"player"), 8))).then(class_2170.method_9244((String)"radius", (ArgumentType)IntegerArgumentType.integer((int)1, (int)32)).executes(context -> ModCommands.releasePlayer((class_2168)context.getSource(), class_2186.method_9315((CommandContext)context, (String)"player"), IntegerArgumentType.getInteger((CommandContext)context, (String)"radius"))))))).then(((LiteralArgumentBuilder)class_2170.method_9247((String)"executioner").then(((LiteralArgumentBuilder)class_2170.method_9247((String)"toggle").executes(context -> ModCommands.setExecutionerEnabled((class_2168)context.getSource(), 8, null))).then(class_2170.method_9244((String)"radius", (ArgumentType)IntegerArgumentType.integer((int)1, (int)32)).executes(context -> ModCommands.setExecutionerEnabled((class_2168)context.getSource(), IntegerArgumentType.getInteger((CommandContext)context, (String)"radius"), null))))).then(((RequiredArgumentBuilder)class_2170.method_9244((String)"enabled", (ArgumentType)BoolArgumentType.bool()).executes(context -> ModCommands.setExecutionerEnabled((class_2168)context.getSource(), 8, BoolArgumentType.getBool((CommandContext)context, (String)"enabled")))).then(class_2170.method_9244((String)"radius", (ArgumentType)IntegerArgumentType.integer((int)1, (int)32)).executes(context -> ModCommands.setExecutionerEnabled((class_2168)context.getSource(), IntegerArgumentType.getInteger((CommandContext)context, (String)"radius"), BoolArgumentType.getBool((CommandContext)context, (String)"enabled"))))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"test").requires(source -> source.method_9259(2))).executes(context -> ModCommands.testNearestGuillotine((class_2168)context.getSource(), 8))).then(class_2170.method_9244((String)"radius", (ArgumentType)IntegerArgumentType.integer((int)1, (int)32)).executes(context -> ModCommands.testNearestGuillotine((class_2168)context.getSource(), IntegerArgumentType.getInteger((CommandContext)context, (String)"radius")))))));
    }

    private static int showHelp(class_2168 source) {
        source.method_9226(() -> class_2561.method_43471((String)"command.medievalmachinery.help.title"), false);
        source.method_9226(() -> class_2561.method_43470((String)"Rechtsklick: In eine freie Guillotine legen."), false);
        source.method_9226(() -> class_2561.method_43470((String)"Shift + Rechtsklick: Guillotine ausl\u00f6sen."), false);
        source.method_9226(() -> class_2561.method_43470((String)"OP + Holzschwert + Shift + Rechtsklick: Admin-GUI \u00f6ffnen."), false);
        source.method_9226(() -> class_2561.method_43470((String)"/guillotine seat <Spieler> [Radius] - Spieler gesperrt hineinsetzen."), false);
        source.method_9226(() -> class_2561.method_43470((String)"/guillotine release <Spieler> [Radius] - Spieler durch einen Admin freigeben."), false);
        source.method_9226(() -> class_2561.method_43470((String)"/guillotine test [Radius] - Einstellungen und Animation testen."), false);
        source.method_9226(() -> class_2561.method_43470((String)"Eigene Befehle: %player%, %Player%, %player_uuid%, %world%, %x%, %y%, %z%."), false);
        return 1;
    }

    private static int setExecutionerEnabled(class_2168 source, int radius, Boolean enabled) {
        GuillotineBlockEntity guillotine = ModCommands.findNearestGuillotine(source, radius);
        if (guillotine == null) {
            source.method_9213((class_2561)class_2561.method_43469((String)"command.medievalmachinery.no_guillotine", (Object[])new Object[]{radius}));
            return 0;
        }
        boolean newValue = enabled == null ? guillotine.toggleExecutionerEnabled() : guillotine.setExecutionerEnabled(enabled);
        source.method_9226(() -> class_2561.method_43469((String)"command.medievalmachinery.executioner.changed", (Object[])new Object[]{newValue ? class_2561.method_43471((String)"gui.medievalmachinery.on") : class_2561.method_43471((String)"gui.medievalmachinery.off"), guillotine.getDisplayName()}), false);
        return 1;
    }

    private static int testNearestGuillotine(class_2168 source, int radius) {
        String previewName;
        GuillotineBlockEntity guillotine = ModCommands.findNearestGuillotine(source, radius);
        if (guillotine == null) {
            source.method_9213((class_2561)class_2561.method_43470((String)("Keine Guillotine im Umkreis von " + radius + " Bl\u00f6cken gefunden.")));
            return 0;
        }
        MedievalMachineryConfig config = MedievalMachineryConfig.get();
        try {
            previewName = source.method_9207().method_7334().getName();
        }
        catch (Exception ignored) {
            previewName = "Testspieler";
        }
        boolean darkHeadsLoaded = FabricLoader.getInstance().isModLoaded("darkheads") || FabricLoader.getInstance().isModLoaded("dark_heads") || FabricLoader.getInstance().isModLoaded("rpgplayerheads");
        String previewMessage = config.getExecutionMessage(previewName);
        source.method_9226(() -> class_2561.method_43470((String)"=== Guillotine-Testmodus ==="), false);
        source.method_9226(() -> class_2561.method_43470((String)("\u2713 Guillotine gefunden: " + guillotine.method_11016().method_23854())), false);
        source.method_9226(() -> class_2561.method_43470((String)("\u2713 Bann bei Hinrichtung: " + (guillotine.isBanOnExecution() ? "AN" : "AUS"))), false);
        source.method_9226(() -> class_2561.method_43470((String)("\u2713 Broadcast: " + (config.broadcastExecutionMessage ? "AN" : "AUS"))), false);
        source.method_9226(() -> class_2561.method_43470((String)("\u2713 Todesnachricht: " + previewMessage)), false);
        source.method_9226(() -> class_2561.method_43470((String)("\u2713 Kopf-Drop: " + (darkHeadsLoaded ? "DarkHeads \u00fcbernimmt" : "Vanilla-Fallback \u00fcbernimmt"))), false);
        if (guillotine.isSequenceRunning()) {
            source.method_9226(() -> class_2561.method_43470((String)"\u26a0 Animation nicht gestartet: Guillotine l\u00e4uft bereits."), false);
        } else {
            guillotine.startSequence();
            source.method_9226(() -> class_2561.method_43470((String)"\u2713 Animation: leere Testsequenz gestartet."), false);
        }
        source.method_9226(() -> class_2561.method_43470((String)"\u2713 Sicherer Test: Niemand wird get\u00f6tet oder gebannt."), false);
        source.method_9226(() -> class_2561.method_43470((String)"=== Test erfolgreich abgeschlossen ==="), false);
        return 1;
    }

    private static GuillotineBlockEntity findNearestGuillotine(class_2168 source, int radius) {
        class_2338 center = class_2338.method_49638((class_2374)source.method_9222());
        GuillotineBlockEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (class_2338 checkPos : class_2338.method_10097((class_2338)center.method_10069(-radius, -3, -radius), (class_2338)center.method_10069(radius, 3, radius))) {
            class_2586 class_25862 = source.method_9225().method_8321(checkPos);
            if (!(class_25862 instanceof GuillotineBlockEntity)) continue;
            GuillotineBlockEntity guillotine = (GuillotineBlockEntity)class_25862;
            double distance = checkPos.method_10262((class_2382)center);
            if (!(distance < nearestDistance)) continue;
            nearest = guillotine;
            nearestDistance = distance;
        }
        return nearest;
    }

    private static int releasePlayer(class_2168 source, class_3222 player, int radius) {
        class_2338 center = class_2338.method_49638((class_2374)source.method_9222());
        GuillotineBlockEntity occupiedGuillotine = null;
        double nearestDistance = Double.MAX_VALUE;
        for (class_2338 checkPos : class_2338.method_10097((class_2338)center.method_10069(-radius, -3, -radius), (class_2338)center.method_10069(radius, 3, radius))) {
            double distance;
            GuillotineBlockEntity guillotine;
            class_2586 class_25862 = source.method_9225().method_8321(checkPos);
            if (!(class_25862 instanceof GuillotineBlockEntity) || !(guillotine = (GuillotineBlockEntity)class_25862).isOccupiedBy(player.method_5667()) || !((distance = checkPos.method_10262((class_2382)center)) < nearestDistance)) continue;
            occupiedGuillotine = guillotine;
            nearestDistance = distance;
        }
        if (occupiedGuillotine == null) {
            source.method_9213((class_2561)class_2561.method_43470((String)("Im Umkreis von " + radius + " Bl\u00f6cken wurde keine Guillotine mit diesem Spieler gefunden.")));
            return 0;
        }
        if (!occupiedGuillotine.releaseOccupant()) {
            source.method_9213((class_2561)class_2561.method_43470((String)"Der Spieler konnte nicht freigegeben werden."));
            return 0;
        }
        source.method_9226(() -> class_2561.method_43469((String)"command.medievalmachinery.release.success", (Object[])new Object[]{player.method_7334().getName()}), true);
        player.method_7353((class_2561)class_2561.method_43470((String)"Du wurdest durch einen Admin aus der Guillotine freigegeben."), false);
        return 1;
    }

    private static int seatPlayer(class_2168 source, class_3222 player, int radius) {
        class_2338 center = class_2338.method_49638((class_2374)source.method_9222());
        GuillotineBlockEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (class_2338 checkPos : class_2338.method_10097((class_2338)center.method_10069(-radius, -3, -radius), (class_2338)center.method_10069(radius, 3, radius))) {
            class_2586 class_25862 = source.method_9225().method_8321(checkPos);
            if (!(class_25862 instanceof GuillotineBlockEntity)) continue;
            GuillotineBlockEntity guillotine = (GuillotineBlockEntity)class_25862;
            double distance = checkPos.method_10262((class_2382)center);
            if (!(distance < nearestDistance)) continue;
            nearest = guillotine;
            nearestDistance = distance;
        }
        if (nearest == null) {
            source.method_9213((class_2561)class_2561.method_43470((String)("Keine Guillotine im Umkreis von " + radius + " Bl\u00f6cken gefunden.")));
            return 0;
        }
        if (!nearest.isEmpty()) {
            source.method_9213((class_2561)class_2561.method_43470((String)"Die n\u00e4chste Guillotine ist bereits belegt."));
            return 0;
        }
        if (!nearest.occupy(player.method_5667())) {
            source.method_9213((class_2561)class_2561.method_43470((String)"Der Spieler konnte nicht in die Guillotine gesetzt werden."));
            return 0;
        }
        nearest.lockOccupant();
        source.method_9226(() -> class_2561.method_43469((String)"command.medievalmachinery.seat.success", (Object[])new Object[]{player.method_7334().getName()}), true);
        player.method_7353((class_2561)class_2561.method_43470((String)"Du wurdest durch einen Admin in der Guillotine fixiert."), false);
        return 1;
    }
}

