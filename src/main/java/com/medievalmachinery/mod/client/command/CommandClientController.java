/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1675
 *  net.minecraft.class_238
 *  net.minecraft.class_239
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_304
 *  net.minecraft.class_3675$class_307
 *  net.minecraft.class_3965
 *  net.minecraft.class_3966
 *  net.minecraft.class_746
 */
package com.medievalmachinery.mod.client.command;

import com.medievalmachinery.mod.client.command.CommandOverlayScreen;
import com.medievalmachinery.mod.client.network.ClientKingdomNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1675;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_304;
import net.minecraft.class_3675;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_746;

@Environment(value=EnvType.CLIENT)
public final class CommandClientController {
    private static final class_304 OPEN_COMMANDS = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.medieval-machinery.command", class_3675.class_307.field_1668, 75, "key.categories.medieval-machinery"));

    private CommandClientController() {
    }

    private static class_3966 findAttackTarget(class_746 player) {
        double reach = 100.0;
        class_243 start = player.method_5836(1.0f);
        class_243 look = player.method_5828(1.0f);
        class_243 end = start.method_1019(look.method_1021(100.0));
        class_239 blockHit = player.method_5745(100.0, 1.0f, false);
        if (blockHit != null && blockHit.method_17783() == class_239.class_240.field_1332) {
            end = blockHit.method_17784();
        }
        double maxDistanceSq = start.method_1025(end);
        class_238 box = player.method_5829().method_18804(end.method_1020(start)).method_1014(1.0);
        return class_1675.method_18075((class_1297)player, (class_243)start, (class_243)end, (class_238)box, entity -> entity instanceof class_1309 && !entity.method_7325() && entity.method_5863(), (double)maxDistanceSq);
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_COMMANDS.method_1436()) {
                if (client.field_1724 == null || client.field_1755 != null) continue;
                class_239 tacticalHit = client.field_1724.method_5745(100.0, 1.0f, false);
                if (tacticalHit instanceof class_3965) {
                    class_3965 blockHit = (class_3965)tacticalHit;
                    CommandOverlayScreen.setLastLookedAtBlock(blockHit.method_17777());
                } else {
                    CommandOverlayScreen.setLastLookedAtBlock(null);
                }
                class_3966 attackTarget = CommandClientController.findAttackTarget(client.field_1724);
                CommandOverlayScreen.setLastLookedAtEntity(attackTarget != null && attackTarget.method_17782() instanceof class_1309 ? attackTarget.method_17782().method_5628() : -1);
                ClientKingdomNetworking.requestCommandGate();
            }
        });
    }
}

