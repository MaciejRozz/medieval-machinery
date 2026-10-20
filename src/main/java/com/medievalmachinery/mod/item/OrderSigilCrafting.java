/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1799
 *  net.minecraft.class_2561
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.item;

import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_3222;

public final class OrderSigilCrafting {
    private static final Map<UUID, Long> LAST_WARNING_TICK = new HashMap<UUID, Long>();

    private OrderSigilCrafting() {
    }

    public static boolean isCraftedSigil(class_1799 stack) {
        return stack.method_7909() instanceof OrderSigilItem;
    }

    public static boolean canPlayerCraft(class_1657 player, class_1799 stack) {
        if (!OrderSigilCrafting.isCraftedSigil(stack)) {
            return true;
        }
        if (player.method_37908().field_9236) {
            return true;
        }
        if (!(player instanceof class_3222)) {
            return false;
        }
        class_3222 serverPlayer = (class_3222)player;
        return OrderSigilCrafting.kingdomFor(serverPlayer) != null;
    }

    public static void notifyMissingKingdom(class_1657 player) {
        if (!(player instanceof class_3222)) {
            return;
        }
        class_3222 serverPlayer = (class_3222)player;
        long now = serverPlayer.method_51469().method_8510();
        Long last = LAST_WARNING_TICK.get(serverPlayer.method_5667());
        if (last != null && now >= last && now - last < 20L) {
            return;
        }
        LAST_WARNING_TICK.put(serverPlayer.method_5667(), now);
        serverPlayer.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.create_first"), true);
    }

    private static KingdomData kingdomFor(class_3222 player) {
        return KingdomManager.get(player.method_51469()).findByMember(player.method_5667());
    }

    public static void clearWarning(UUID player) {
        LAST_WARNING_TICK.remove(player);
    }

    public static void clearWarnings() {
        LAST_WARNING_TICK.clear();
    }

    public static void stampForPlayer(class_1657 player, class_1799 stack) {
        if (!OrderSigilCrafting.isCraftedSigil(stack) || OrderSigilItem.isStamped(stack)) {
            return;
        }
        if (!(player instanceof class_3222)) {
            return;
        }
        class_3222 serverPlayer = (class_3222)player;
        KingdomData kingdom = OrderSigilCrafting.kingdomFor(serverPlayer);
        if (kingdom == null) {
            return;
        }
        OrderSigilItem item = (OrderSigilItem)stack.method_7909();
        OrderSigilItem.stamp(stack, kingdom.getId(), item.getOrderStyle(), kingdom.getName());
    }
}

