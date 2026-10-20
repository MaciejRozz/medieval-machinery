/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1306
 *  net.minecraft.class_1657
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_4608
 *  net.minecraft.class_746
 *  net.minecraft.class_759
 *  net.minecraft.class_811
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin.client;

import com.medievalmachinery.mod.client.storage.ClientWickerBasketCarryState;
import com.medievalmachinery.mod.client.storage.WickerBasketBuiltinItemRenderer;
import com.medievalmachinery.mod.storage.WickerBasketCarryData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_746;
import net.minecraft.class_759;
import net.minecraft.class_811;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_759.class})
public abstract class BasketFirstPersonMixin {
    @Shadow
    private void method_3219(class_4587 matrices, class_4597 vertexConsumers, int light, float equipProgress, float swingProgress, class_1306 arm) {
    }

    @Inject(method={"method_22976(FLnet/minecraft/class_4587;Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_746;I)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$renderCarriedBasket(float tickDelta, class_4587 matrices, class_4597.class_4598 vertexConsumers, class_746 player, int light, CallbackInfo ci) {
        WickerBasketCarryData carriedBasket = ClientWickerBasketCarryState.get((class_1657)player);
        if (carriedBasket == null) {
            return;
        }
        matrices.method_22903();
        matrices.method_22904(0.0, -0.28, -0.55);
        this.method_3219(matrices, (class_4597)vertexConsumers, light, 0.0f, 0.0f, class_1306.field_6183);
        matrices.method_22909();
        matrices.method_22903();
        matrices.method_22904(0.0, -0.28, -0.55);
        this.method_3219(matrices, (class_4597)vertexConsumers, light, 0.0f, 0.0f, class_1306.field_6182);
        matrices.method_22909();
        matrices.method_22903();
        WickerBasketBuiltinItemRenderer.renderCarried(carriedBasket, class_811.field_4322, matrices, (class_4597)vertexConsumers, light, class_4608.field_21444);
        matrices.method_22909();
        vertexConsumers.method_22993();
        ci.cancel();
    }
}

