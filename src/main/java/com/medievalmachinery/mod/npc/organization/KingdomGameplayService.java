/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_2561
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.OrderMaterials;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_2561;
import net.minecraft.class_3222;

public final class KingdomGameplayService {
    private KingdomGameplayService() {
    }

    public static boolean createKingdom(class_3222 player, String name, OrderStyle order) {
        KingdomManager manager = KingdomManager.get(player.method_51469());
        if (manager.findByMember(player.method_5667()) != null) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.already_member"), false);
            return false;
        }
        class_1792 material = OrderMaterials.materialFor(order);
        if (order == OrderStyle.NONE || material == null) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.invalid_order"), false);
            return false;
        }
        if (!player.method_31549().field_7477 && !KingdomGameplayService.consumeOne(player, material)) {
            player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.missing_foundation_material", (Object[])new Object[]{new class_1799((class_1935)material).method_7964()}), false);
            return false;
        }
        KingdomData kingdom = manager.create(player.method_5667(), name, order);
        if (kingdom == null) {
            return false;
        }
        class_1799 sigil = ((OrderSigilItem)ModItems.getOrderSigil(order)).stamp(kingdom.getId(), kingdom.getName());
        KingdomGameplayService.give(player, sigil);
        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.created", (Object[])new Object[]{kingdom.getName(), order.method_15434()}), false);
        return true;
    }

    public static boolean rename(class_3222 player, String name) {
        KingdomManager manager = KingdomManager.get(player.method_51469());
        KingdomData kingdom = manager.findByMember(player.method_5667());
        if (kingdom == null || !manager.rename(kingdom.getId(), player.method_5667(), name)) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.king_only"), false);
            return false;
        }
        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.renamed", (Object[])new Object[]{kingdom.getName()}), false);
        return true;
    }

    public static boolean changeMainOrder(class_3222 player, OrderStyle order) {
        KingdomManager manager = KingdomManager.get(player.method_51469());
        KingdomData kingdom = manager.findByMember(player.method_5667());
        if (kingdom == null || order == OrderStyle.NONE || !manager.setMainOrder(kingdom.getId(), player.method_5667(), order)) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.king_only"), false);
            return false;
        }
        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.main_order_changed", (Object[])new Object[]{order.method_15434()}), false);
        return true;
    }

    private static boolean consumeOne(class_3222 player, class_1792 item) {
        for (int i = 0; i < player.method_31548().method_5439(); ++i) {
            class_1799 stack = player.method_31548().method_5438(i);
            if (!stack.method_31574(item)) continue;
            stack.method_7934(1);
            player.method_31548().method_5431();
            return true;
        }
        return false;
    }

    private static void give(class_3222 player, class_1799 stack) {
        if (!player.method_31548().method_7394(stack)) {
            player.method_7328(stack, false);
        }
    }
}

