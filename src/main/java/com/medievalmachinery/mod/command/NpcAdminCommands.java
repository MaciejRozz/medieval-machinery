/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
 *  net.minecraft.class_1297
 *  net.minecraft.class_2168
 *  net.minecraft.class_2170
 *  net.minecraft.class_2186
 *  net.minecraft.class_2561
 */
package com.medievalmachinery.mod.command;

import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Locale;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.class_1297;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2186;
import net.minecraft.class_2561;

public final class NpcAdminCommands {
    private NpcAdminCommands() {
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"medievalmachinery").requires(source -> source.method_9259(2))).then(class_2170.method_9247((String)"npc").then(class_2170.method_9247((String)"rank").then(class_2170.method_9244((String)"target", (ArgumentType)class_2186.method_9309()).then(class_2170.method_9244((String)"rank", (ArgumentType)StringArgumentType.word()).suggests((context, builder) -> {
            builder.suggest("none");
            builder.suggest("captain");
            builder.suggest("general");
            builder.suggest("hauptmann");
            builder.suggest("keiner");
            return builder.buildFuture();
        }).executes(context -> NpcAdminCommands.setRank((class_2168)context.getSource(), class_2186.method_9313((CommandContext)context, (String)"target"), StringArgumentType.getString((CommandContext)context, (String)"rank")))))))));
    }

    private static int setRank(class_2168 source, class_1297 target, String rankName) {
        if (!(target instanceof MedievalSoldierEntity)) {
            source.method_9213((class_2561)class_2561.method_43470((String)"Das Ziel ist kein Medieval-Machinery-Soldat."));
            return 0;
        }
        MedievalSoldierEntity soldier = (MedievalSoldierEntity)target;
        NpcRank rank = NpcAdminCommands.parseRank(rankName);
        if (rank == null) {
            source.method_9213((class_2561)class_2561.method_43470((String)"Ung\u00fcltiger Rang. Erlaubt: none/keiner, captain/hauptmann, general."));
            return 0;
        }
        soldier.setNpcRank(rank);
        source.method_9226(() -> class_2561.method_43470((String)("NPC-Rang gesetzt: " + soldier.method_5477().getString() + " -> " + NpcAdminCommands.displayName(rank))), true);
        return 1;
    }

    private static NpcRank parseRank(String value) {
        if (value == null) {
            return null;
        }
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "none", "keiner", "kein", "normal" -> NpcRank.NONE;
            case "captain", "hauptmann" -> NpcRank.CAPTAIN;
            case "general" -> NpcRank.GENERAL;
            default -> null;
        };
    }

    private static String displayName(NpcRank rank) {
        return switch (rank) {
            default -> throw new IncompatibleClassChangeError();
            case NpcRank.NONE -> "Kein Rang";
            case NpcRank.CAPTAIN -> "Hauptmann";
            case NpcRank.GENERAL -> "General";
        };
    }
}

