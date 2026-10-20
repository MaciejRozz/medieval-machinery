/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_989
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin.client;

import com.medievalmachinery.mod.client.storage.ClientWickerBasketCarryState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_989;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_989.class})
public abstract class BasketHeldItemFeatureMixin {
    @Inject(method={"method_17162"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$hideHeldItems(class_4587 matrices, class_4597 vertexConsumers, int light, class_1309 entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        class_1657 player;
        if (entity instanceof class_1657 && ClientWickerBasketCarryState.isCarrying(player = (class_1657)entity)) {
            ci.cancel();
        }
    }
}

