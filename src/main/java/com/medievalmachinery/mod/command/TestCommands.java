/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
 *  net.minecraft.class_2168
 *  net.minecraft.class_2170
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.command;

import com.medievalmachinery.mod.test.SlimeSpawnTestState;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2561;
import net.minecraft.class_3218;

public final class TestCommands {
    private TestCommands() {
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"medievalmachinery").requires(source -> source.method_9259(2))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247((String)"test").then(class_2170.method_9247((String)"disableSlimes").executes(context -> TestCommands.setSlimes((class_2168)context.getSource(), true)))).then(class_2170.method_9247((String)"enableSlimes").executes(context -> TestCommands.setSlimes((class_2168)context.getSource(), false)))).then(class_2170.method_9247((String)"slimeStatus").executes(context -> TestCommands.showStatus((class_2168)context.getSource()))))));
    }

    private static int setSlimes(class_2168 source, boolean disabled) {
        class_3218 world = source.method_9225();
        SlimeSpawnTestState.get(world).setSlimesDisabled(disabled);
        source.method_9226(() -> class_2561.method_43470((String)(disabled ? "Slimes sind f\u00fcr diese Dimension deaktiviert." : "Slimes sind f\u00fcr diese Dimension wieder aktiviert.")), true);
        return 1;
    }

    private static int showStatus(class_2168 source) {
        boolean disabled = SlimeSpawnTestState.get(source.method_9225()).areSlimesDisabled();
        source.method_9226(() -> class_2561.method_43470((String)("Slime-Sperre: " + (disabled ? "AKTIV" : "AUS"))), false);
        return 1;
    }
}

