/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_572
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
import net.minecraft.class_572;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_572.class})
public abstract class BasketCarryPoseMixin<T extends class_1309> {
    @Inject(method={"method_17087"}, at={@At(value="TAIL")})
    private void medievalMachinery$poseBasketCarry(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        class_1657 player;
        if (!(entity instanceof class_1657) || !ClientWickerBasketCarryState.isCarrying(player = (class_1657)entity)) {
            return;
        }
        class_572 model = (class_572)this;
        model.field_3401.field_3654 = -0.92f;
        model.field_3401.field_3675 = -0.18f;
        model.field_3401.field_3674 = 0.05f;
        model.field_27433.field_3654 = -0.92f;
        model.field_27433.field_3675 = 0.18f;
        model.field_27433.field_3674 = -0.05f;
    }
}

