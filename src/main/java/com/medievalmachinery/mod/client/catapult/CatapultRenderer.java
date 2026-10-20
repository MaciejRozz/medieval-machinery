/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1297
 *  net.minecraft.class_1799
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_5617$class_5618
 *  net.minecraft.class_7833
 *  net.minecraft.class_811
 *  software.bernie.geckolib.core.animatable.model.CoreGeoBone
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 */
package com.medievalmachinery.mod.client.catapult;

import com.medievalmachinery.mod.client.catapult.CatapultModel;
import com.medievalmachinery.mod.client.catapult.PayloadModelMetrics;
import com.medievalmachinery.mod.client.render.GeoBoneAnchorResolver;
import com.medievalmachinery.mod.client.render.RenderTransforms;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1799;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5617;
import net.minecraft.class_7833;
import net.minecraft.class_811;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(value=EnvType.CLIENT)
public final class CatapultRenderer
extends GeoEntityRenderer<CatapultEntity> {
    private static final float PAYLOAD_BASE_SCALE = 1.0f;

    public CatapultRenderer(class_5617.class_5618 context) {
        super(context, (GeoModel)new CatapultModel());
        this.field_4673 = 1.4f;
    }

    public void render(CatapultEntity entity, float entityYaw, float partialTick, class_4587 matrices, class_4597 vertexConsumers, int light) {
        class_243 anchorWorld;
        matrices.method_22903();
        matrices.method_22904(0.0, -0.020833333333333332, 0.0);
        super.method_3936((class_1297)entity, entityYaw, partialTick, matrices, vertexConsumers, light);
        matrices.method_22909();
        class_1799 payload = entity.getLoadedAmmunition();
        if (payload.method_7960()) {
            return;
        }
        CoreGeoBone payloadAnchor = this.getGeoModel().getAnimationProcessor().getBone("payload_anchor");
        class_243 class_2432 = anchorWorld = payloadAnchor == null ? null : GeoBoneAnchorResolver.resolveWorldPosition(payloadAnchor);
        if (anchorWorld == null) {
            return;
        }
        matrices.method_22903();
        matrices.method_22904(anchorWorld.field_1352 - entity.method_23317(), anchorWorld.field_1351 - entity.method_23318(), anchorWorld.field_1350 - entity.method_23321());
        RenderTransforms.Transform tuning = RenderTransforms.catapultPayload();
        matrices.method_22907(class_7833.field_40716.rotationDegrees(-entity.getVisualYaw(partialTick)));
        float payloadArmRotation = entity.getArmAngleDegrees(partialTick);
        matrices.method_22907(class_7833.field_40714.rotationDegrees(payloadArmRotation));
        matrices.method_22907(class_7833.field_40716.rotationDegrees(180.0f));
        matrices.method_22904(tuning.translationX, tuning.translationY, tuning.translationZ);
        float scale = 1.0f * tuning.scale;
        matrices.method_22905(scale, scale, scale);
        PayloadModelMetrics.Bounds bounds = PayloadModelMetrics.bounds(payload);
        matrices.method_46416(-bounds.bottomCenterX(), -bounds.minY(), -bounds.bottomCenterZ());
        class_310.method_1551().method_1480().method_23178(payload, class_811.field_4315, light, class_4608.field_21444, matrices, vertexConsumers, entity.method_37908(), entity.method_5628());
        matrices.method_22909();
    }
}

