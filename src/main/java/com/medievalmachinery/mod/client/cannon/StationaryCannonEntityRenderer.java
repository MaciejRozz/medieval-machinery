/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1297
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_5617$class_5618
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 */
package com.medievalmachinery.mod.client.cannon;

import com.medievalmachinery.mod.client.cannon.StationaryCannonEntityModel;
import com.medievalmachinery.mod.entity.cannon.StationaryCannonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_5617;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(value=EnvType.CLIENT)
public final class StationaryCannonEntityRenderer
extends GeoEntityRenderer<StationaryCannonEntity> {
    public StationaryCannonEntityRenderer(class_5617.class_5618 context) {
        super(context, (GeoModel)new StationaryCannonEntityModel());
        this.field_4673 = 1.25f;
    }

    public void render(StationaryCannonEntity entity, float entityYaw, float partialTick, class_4587 matrices, class_4597 vertexConsumers, int light) {
        matrices.method_22903();
        matrices.method_22904(0.0, -0.0625, 0.0);
        super.method_3936((class_1297)entity, entityYaw, partialTick, matrices, vertexConsumers, light);
        matrices.method_22909();
    }
}

