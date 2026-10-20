/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_2960
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.item.spear.SpearItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_3222;

public final class SpearCombatNetworking {
    public static final class_2960 LONG_REACH_ATTACK = MedievalMachinery.id("spear_long_reach_attack");

    private SpearCombatNetworking() {
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver((class_2960)LONG_REACH_ATTACK, (server, player, handler, buf, responseSender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> SpearCombatNetworking.attack(player, entityId));
        });
    }

    private static void attack(class_3222 player, int entityId) {
        class_1799 held = player.method_6047();
        class_1792 class_17922 = held.method_7909();
        if (!(class_17922 instanceof SpearItem)) {
            return;
        }
        SpearItem spear = (SpearItem)class_17922;
        class_1297 target = player.method_37908().method_8469(entityId);
        if (target == null || target == player || !target.method_5805() || target.method_7325()) {
            return;
        }
        double allowed = spear.getReach() + Math.max(0.35, (double)target.method_17681() * 0.5);
        if (player.method_5858(target) > allowed * allowed) {
            return;
        }
        if (!player.method_6057(target)) {
            return;
        }
        player.method_7324(target);
        player.method_23667(class_1268.field_5808, true);
    }
}

