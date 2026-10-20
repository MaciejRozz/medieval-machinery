/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_2168
 *  net.minecraft.class_2170
 *  net.minecraft.class_238
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.command;

import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyManager;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyStance;
import com.medievalmachinery.mod.npc.organization.KingdomGameplayService;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.OrderMaterials;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.npc.organization.SystemKingdoms;
import com.medievalmachinery.mod.registry.ModItems;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_238;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class KingdomCommands {
    private KingdomCommands() {
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"kingdom").then(class_2170.method_9247((String)"info").executes(ctx -> KingdomCommands.info(((class_2168)ctx.getSource()).method_9207())))).then(class_2170.method_9247((String)"create").then(class_2170.method_9244((String)"order", (ArgumentType)StringArgumentType.word()).then(class_2170.method_9244((String)"name", (ArgumentType)StringArgumentType.greedyString()).executes(ctx -> KingdomCommands.create(((class_2168)ctx.getSource()).method_9207(), StringArgumentType.getString((CommandContext)ctx, (String)"order"), StringArgumentType.getString((CommandContext)ctx, (String)"name"))))))).then(((LiteralArgumentBuilder)class_2170.method_9247((String)"sigil").requires(source -> source.method_9259(2))).then(class_2170.method_9244((String)"order", (ArgumentType)StringArgumentType.word()).executes(ctx -> KingdomCommands.mintSigilForTesting(((class_2168)ctx.getSource()).method_9207(), StringArgumentType.getString((CommandContext)ctx, (String)"order")))))).then(class_2170.method_9247((String)"rename").then(class_2170.method_9244((String)"name", (ArgumentType)StringArgumentType.greedyString()).executes(ctx -> KingdomCommands.rename(((class_2168)ctx.getSource()).method_9207(), StringArgumentType.getString((CommandContext)ctx, (String)"name")))))).then(class_2170.method_9247((String)"delete").then(class_2170.method_9247((String)"confirm").executes(ctx -> KingdomCommands.deleteKingdom(((class_2168)ctx.getSource()).method_9207()))))).then(class_2170.method_9247((String)"order").then(class_2170.method_9244((String)"order", (ArgumentType)StringArgumentType.word()).executes(ctx -> KingdomCommands.changeMainOrder(((class_2168)ctx.getSource()).method_9207(), StringArgumentType.getString((CommandContext)ctx, (String)"order")))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"diplomacy").requires(source -> source.method_9259(2))).then(class_2170.method_9247((String)"list").executes(ctx -> KingdomCommands.diplomacyList(((class_2168)ctx.getSource()).method_9207())))).then(((LiteralArgumentBuilder)class_2170.method_9247((String)"dragon_knights").then(class_2170.method_9244((String)"stance", (ArgumentType)StringArgumentType.word()).executes(ctx -> KingdomCommands.setDragonDiplomacy(((class_2168)ctx.getSource()).method_9207(), StringArgumentType.getString((CommandContext)ctx, (String)"stance"), false)))).then(class_2170.method_9247((String)"both").then(class_2170.method_9244((String)"stance", (ArgumentType)StringArgumentType.word()).executes(ctx -> KingdomCommands.setDragonDiplomacy(((class_2168)ctx.getSource()).method_9207(), StringArgumentType.getString((CommandContext)ctx, (String)"stance"), true))))))));
    }

    private static int create(class_3222 player, String orderName, String name) {
        OrderStyle order = KingdomCommands.parseOrder(orderName);
        return KingdomGameplayService.createKingdom(player, name, order) ? 1 : 0;
    }

    private static int mintSigilForTesting(class_3222 player, String orderName) {
        KingdomManager manager = KingdomManager.get(player.method_51469());
        KingdomData kingdom = manager.findByMember(player.method_5667());
        if (kingdom == null) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.not_member"), false);
            return 0;
        }
        OrderStyle order = KingdomCommands.parseOrder(orderName);
        if (order == OrderStyle.NONE || OrderMaterials.materialFor(order) == null) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.invalid_order"), false);
            return 0;
        }
        class_1799 sigil = ((OrderSigilItem)ModItems.getOrderSigil(order)).stamp(kingdom.getId(), kingdom.getName());
        KingdomCommands.give(player, sigil);
        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.sigil_minted"), false);
        return 1;
    }

    private static int rename(class_3222 player, String name) {
        return KingdomGameplayService.rename(player, name) ? 1 : 0;
    }

    private static int changeMainOrder(class_3222 player, String orderName) {
        return KingdomGameplayService.changeMainOrder(player, KingdomCommands.parseOrder(orderName)) ? 1 : 0;
    }

    private static int deleteKingdom(class_3222 player) {
        KingdomManager manager = KingdomManager.get(player.method_51469());
        KingdomData kingdom = manager.findByMember(player.method_5667());
        if (kingdom == null || !kingdom.getKingId().equals(player.method_5667())) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.delete_king_only"), false);
            return 0;
        }
        UUID kingdomId = kingdom.getId();
        if (!manager.deleteKingdom(kingdomId, player.method_5667())) {
            return 0;
        }
        KingdomDiplomacyManager.get(player.method_51469()).removeKingdom(kingdomId);
        GarrisonManager.get(player.method_51469()).clearKingdom(kingdomId);
        for (class_3218 world : player.method_5682().method_3738()) {
            for (MedievalSoldierEntity soldier : world.method_8390(MedievalSoldierEntity.class, new class_238(-3.0E7, (double)world.method_31607(), -3.0E7, 3.0E7, (double)world.method_31600(), 3.0E7), e -> kingdomId.equals(e.getNpcIdentity().getKingdomId()))) {
                soldier.getNpcIdentity().setKingdomId(null);
            }
        }
        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.deleted"), false);
        return 1;
    }

    private static int info(class_3222 player) {
        KingdomData kingdom = KingdomManager.get(player.method_51469()).findByMember(player.method_5667());
        if (kingdom == null) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.not_member"), false);
            return 0;
        }
        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.info", (Object[])new Object[]{kingdom.getName(), kingdom.getMainOrder().method_15434(), kingdom.getRole(player.method_5667()).name(), kingdom.getId().toString().substring(0, 8)}), false);
        return 1;
    }

    private static int diplomacyList(class_3222 player) {
        KingdomData own = KingdomManager.get(player.method_51469()).findByMember(player.method_5667());
        if (own == null) {
            player.method_7353((class_2561)class_2561.method_43470((String)"Du bist in keinem K\u00f6nigreich."), false);
            return 0;
        }
        SystemKingdoms.ensureDragonKnights(player.method_51469());
        KingdomDiplomacyManager diplomacy = KingdomDiplomacyManager.get(player.method_51469());
        for (KingdomData kingdom : KingdomManager.get(player.method_51469()).getKingdoms()) {
            if (kingdom.getId().equals(own.getId())) continue;
            player.method_7353((class_2561)class_2561.method_43470((String)(kingdom.getName() + " [" + kingdom.getId().toString().substring(0, 8) + "] -> " + diplomacy.getEffectiveRelation(own.getId(), kingdom.getId()).name() + " (wir=" + diplomacy.getStance(own.getId(), kingdom.getId()).name() + ", sie=" + diplomacy.getStance(kingdom.getId(), own.getId()).name() + ")")), false);
        }
        return 1;
    }

    private static int setDragonDiplomacy(class_3222 player, String value, boolean bothDirections) {
        KingdomData own = KingdomManager.get(player.method_51469()).findByMember(player.method_5667());
        if (own == null) {
            player.method_7353((class_2561)class_2561.method_43470((String)"Du bist in keinem K\u00f6nigreich."), false);
            return 0;
        }
        KingdomData dragon = SystemKingdoms.ensureDragonKnights(player.method_51469());
        KingdomDiplomacyStance stance = KingdomDiplomacyStance.fromName(value);
        KingdomDiplomacyManager diplomacy = KingdomDiplomacyManager.get(player.method_51469());
        diplomacy.setStance(own.getId(), dragon.getId(), stance);
        if (bothDirections) {
            diplomacy.setStance(dragon.getId(), own.getId(), stance);
        }
        player.method_7353((class_2561)class_2561.method_43470((String)("Diplomatie zu Drachenritter: " + diplomacy.getEffectiveRelation(own.getId(), dragon.getId()).name())), false);
        return 1;
    }

    private static OrderStyle parseOrder(String value) {
        String key;
        return switch (key = value.toLowerCase(Locale.ROOT)) {
            case "gold" -> OrderStyle.GOLD;
            case "copper", "kupfer" -> OrderStyle.COPPER;
            case "iron", "eisen" -> OrderStyle.IRON;
            case "diamond", "diamant" -> OrderStyle.DIAMOND;
            case "redstone" -> OrderStyle.REDSTONE;
            case "amethyst" -> OrderStyle.AMETHYST;
            case "lapis" -> OrderStyle.LAPIS;
            case "emerald", "smaragd" -> OrderStyle.EMERALD;
            case "quartz", "quarz" -> OrderStyle.QUARTZ;
            case "coal", "kohle" -> OrderStyle.COAL;
            case "netherite", "netherit" -> OrderStyle.NETHERITE;
            default -> OrderStyle.NONE;
        };
    }

    private static boolean consume(class_3222 player, Map<class_1792, Integer> requirements) {
        class_1799 stack;
        int i;
        for (Map.Entry<class_1792, Integer> entry : requirements.entrySet()) {
            int count = 0;
            for (i = 0; i < player.method_31548().method_5439(); ++i) {
                stack = player.method_31548().method_5438(i);
                if (!stack.method_31574(entry.getKey())) continue;
                count += stack.method_7947();
            }
            if (count >= entry.getValue()) continue;
            return false;
        }
        for (Map.Entry<class_1792, Integer> entry : requirements.entrySet()) {
            int remaining = entry.getValue();
            for (i = 0; i < player.method_31548().method_5439() && remaining > 0; ++i) {
                stack = player.method_31548().method_5438(i);
                if (!stack.method_31574(entry.getKey())) continue;
                int take = Math.min(remaining, stack.method_7947());
                stack.method_7934(take);
                remaining -= take;
            }
        }
        player.method_31548().method_5431();
        return true;
    }

    private static void give(class_3222 player, class_1799 stack) {
        if (!player.method_31548().method_7394(stack)) {
            player.method_7328(stack, false);
        }
    }
}

