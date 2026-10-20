/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
 *  net.minecraft.class_1297
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_638
 *  net.minecraft.class_811
 */
package com.medievalmachinery.mod.client.catapult;

import com.medievalmachinery.mod.client.render.ItemTransformUtil;
import com.medievalmachinery.mod.client.render.RenderTransforms;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import com.medievalmachinery.mod.registry.ModEntities;
import com.medievalmachinery.mod.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.class_1297;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_638;
import net.minecraft.class_811;

@Environment(value=EnvType.CLIENT)
public final class CatapultBuiltinItemRenderer {
    private static CatapultEntity preview;
    private static class_1937 previewWorld;

    private CatapultBuiltinItemRenderer() {
    }

    public static void register() {
        BuiltinItemRendererRegistry.INSTANCE.register((class_1935)ModItems.CATAPULT, CatapultBuiltinItemRenderer::render);
    }

    private static void render(class_1799 stack, class_811 mode, class_4587 matrices, class_4597 vertexConsumers, int light, int overlay) {
        class_310 client = class_310.method_1551();
        class_638 world = client.field_1687;
        if (world == null) {
            return;
        }
        if (preview == null || previewWorld != world) {
            preview = (CatapultEntity)ModEntities.CATAPULT.method_5883((class_1937)world);
            previewWorld = world;
            if (preview == null) {
                return;
            }
        }
        matrices.method_22903();
        ItemTransformUtil.apply(matrices, ItemTransformUtil.select(mode, RenderTransforms.Target.CATAPULT_GUI, RenderTransforms.Target.CATAPULT_FIRST_PERSON, RenderTransforms.Target.CATAPULT_THIRD_PERSON, RenderTransforms.Target.CATAPULT_FIXED, RenderTransforms.Target.CATAPULT_GROUND), mode, false);
        client.method_1561().method_3954((class_1297)preview, 0.0, 0.0, 0.0, 0.0f, client.method_1488(), matrices, vertexConsumers, light);
        matrices.method_22909();
    }
}

