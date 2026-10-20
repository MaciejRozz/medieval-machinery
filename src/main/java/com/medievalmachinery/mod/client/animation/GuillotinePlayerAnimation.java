/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  dev.kosmx.playerAnim.api.layered.IAnimation
 *  dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer
 *  dev.kosmx.playerAnim.api.layered.ModifierLayer
 *  dev.kosmx.playerAnim.core.data.KeyframeAnimation
 *  dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess
 *  dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory
 *  dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2586
 *  net.minecraft.class_2769
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_742
 */
package com.medievalmachinery.mod.client.animation;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlock;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2586;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_742;

@Environment(value=EnvType.CLIENT)
public final class GuillotinePlayerAnimation {
    private static final class_2960 LAYER_ID = MedievalMachinery.id("guillotine_player_layer");
    private static final class_2960 ANIMATION_ID = MedievalMachinery.id("guillotine_lie");
    private static final int LAYER_PRIORITY = 800;
    private static final int SEARCH_RADIUS = 3;

    private GuillotinePlayerAnimation() {
    }

    public static void register() {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(LAYER_ID, 800, player -> new ModifierLayer());
        ClientTickEvents.END_CLIENT_TICK.register(GuillotinePlayerAnimation::tick);
    }

    private static void tick(class_310 client) {
        if (client.field_1687 == null) {
            return;
        }
        for (class_742 player : client.field_1687.method_18456()) {
            boolean shouldLie;
            ModifierLayer<IAnimation> layer = GuillotinePlayerAnimation.getLayer(player);
            if (layer == null) continue;
            GuillotineBlockEntity guillotine = GuillotinePlayerAnimation.findOccupiedGuillotine(player);
            boolean bl = shouldLie = guillotine != null;
            if (guillotine != null) {
                GuillotinePlayerAnimation.lockBodyRotation(player, guillotine);
            }
            if (shouldLie && layer.getAnimation() == null) {
                KeyframeAnimation animation = PlayerAnimationRegistry.getAnimation((class_2960)ANIMATION_ID);
                if (animation == null) continue;
                layer.setAnimation((IAnimation)new KeyframeAnimationPlayer(animation));
                continue;
            }
            if (shouldLie || layer.getAnimation() == null) continue;
            layer.setAnimation(null);
        }
    }

    private static ModifierLayer<IAnimation> getLayer(class_742 player) {
        return (ModifierLayer)PlayerAnimationAccess.getPlayerAssociatedData((class_742)player).get(LAYER_ID);
    }

    private static GuillotineBlockEntity findOccupiedGuillotine(class_742 player) {
        class_2338 center = player.method_24515();
        for (class_2338 checkPos : class_2338.method_10097((class_2338)center.method_10069(-3, -2, -3), (class_2338)center.method_10069(3, 2, 3))) {
            GuillotineBlockEntity guillotine;
            class_2586 class_25862 = player.method_37908().method_8321(checkPos);
            if (!(class_25862 instanceof GuillotineBlockEntity) || !(guillotine = (GuillotineBlockEntity)class_25862).isOccupiedBy(player.method_5667())) continue;
            return guillotine;
        }
        return null;
    }

    private static void lockBodyRotation(class_742 player, GuillotineBlockEntity guillotine) {
        float bodyYaw = ((class_2350)guillotine.method_11010().method_11654((class_2769)GuillotineBlock.FACING)).method_10144();
        player.method_5636(bodyYaw);
        player.field_6220 = bodyYaw;
    }
}

