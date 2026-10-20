/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1657
 *  net.minecraft.class_3883
 *  net.minecraft.class_3887
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_591
 *  net.minecraft.class_742
 *  net.minecraft.class_811
 */
package com.medievalmachinery.mod.client.storage;

import com.medievalmachinery.mod.client.storage.ClientWickerBasketCarryState;
import com.medievalmachinery.mod.client.storage.WickerBasketBuiltinItemRenderer;
import com.medievalmachinery.mod.storage.WickerBasketCarryData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_591;
import net.minecraft.class_742;
import net.minecraft.class_811;

@Environment(value=EnvType.CLIENT)
public final class WickerBasketCarryFeatureRenderer
extends class_3887<class_742, class_591<class_742>> {
    public WickerBasketCarryFeatureRenderer(class_3883<class_742, class_591<class_742>> context) {
        super(context);
    }

    public void render(class_4587 matrices, class_4597 vertexConsumers, int light, class_742 player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        WickerBasketCarryData data = ClientWickerBasketCarryState.get((class_1657)player);
        if (data == null || player.method_5767()) {
            return;
        }
        matrices.method_22903();
        ((class_591)this.method_17165()).field_3401.method_22703(matrices);
        matrices.method_22904(-0.0625, 0.125, -0.625);
        WickerBasketBuiltinItemRenderer.renderCarried(data, class_811.field_4320, matrices, vertexConsumers, light, class_4608.field_21444);
        matrices.method_22909();
    }
}

