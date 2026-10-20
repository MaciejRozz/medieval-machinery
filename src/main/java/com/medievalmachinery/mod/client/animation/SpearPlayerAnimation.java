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
 *  net.minecraft.class_1306
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_742
 */
package com.medievalmachinery.mod.client.animation;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.client.compat.CombatModCompat;
import com.medievalmachinery.mod.item.spear.SpearItem;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.class_1306;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_742;

@Environment(value=EnvType.CLIENT)
public final class SpearPlayerAnimation {
    private static final class_2960 LAYER_ID = MedievalMachinery.id("spear_player_layer");
    private static final class_2960 RIGHT = MedievalMachinery.id("spear_thrust_right");
    private static final class_2960 LEFT = MedievalMachinery.id("spear_thrust_left");
    private static final int LAYER_PRIORITY = 350;
    private static final Map<UUID, Boolean> WAS_SWINGING = new HashMap<UUID, Boolean>();

    private SpearPlayerAnimation() {
    }

    public static void register() {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(LAYER_ID, 350, player -> new ModifierLayer());
        ClientTickEvents.END_CLIENT_TICK.register(SpearPlayerAnimation::tick);
    }

    private static void tick(class_310 client) {
        if (client.field_1687 == null) {
            WAS_SWINGING.clear();
            return;
        }
        for (class_742 player : client.field_1687.method_18456()) {
            ModifierLayer<IAnimation> layer = SpearPlayerAnimation.getLayer(player);
            if (layer == null) continue;
            if (CombatModCompat.betterCombatLoaded()) {
                if (layer.getAnimation() != null) {
                    layer.setAnimation(null);
                }
                WAS_SWINGING.put(player.method_5667(), player.field_6252);
                continue;
            }
            boolean spear = player.method_6047().method_7909() instanceof SpearItem;
            boolean swinging = player.field_6252;
            boolean wasSwinging = WAS_SWINGING.getOrDefault(player.method_5667(), false);
            if (spear && swinging && !wasSwinging) {
                class_2960 id = player.method_6068() == class_1306.field_6182 ? LEFT : RIGHT;
                KeyframeAnimation animation = PlayerAnimationRegistry.getAnimation((class_2960)id);
                if (animation != null) {
                    layer.setAnimation((IAnimation)new KeyframeAnimationPlayer(animation));
                }
            } else if (!spear && layer.getAnimation() != null) {
                layer.setAnimation(null);
            }
            WAS_SWINGING.put(player.method_5667(), swinging);
        }
    }

    private static ModifierLayer<IAnimation> getLayer(class_742 player) {
        return (ModifierLayer)PlayerAnimationAccess.getPlayerAssociatedData((class_742)player).get(LAYER_ID);
    }
}

