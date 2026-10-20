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
package com.medievalmachinery.mod.client.cannon;

import com.medievalmachinery.mod.client.render.ItemTransformUtil;
import com.medievalmachinery.mod.client.render.RenderTransforms;
import com.medievalmachinery.mod.entity.cannon.StationaryCannonEntity;
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
public final class StationaryCannonBuiltinItemRenderer {
    private static StationaryCannonEntity preview;
    private static class_1937 previewWorld;

    private StationaryCannonBuiltinItemRenderer() {
    }

    public static void register() {
        BuiltinItemRendererRegistry.INSTANCE.register((class_1935)ModItems.STATIONARY_CANNON, StationaryCannonBuiltinItemRenderer::render);
    }

    private static void render(class_1799 stack, class_811 mode, class_4587 matrices, class_4597 vertexConsumers, int light, int overlay) {
        class_310 client = class_310.method_1551();
        class_638 world = client.field_1687;
        if (world == null) {
            return;
        }
        if (preview == null || previewWorld != world) {
            preview = (StationaryCannonEntity)ModEntities.STATIONARY_CANNON.method_5883((class_1937)world);
            previewWorld = world;
            if (preview == null) {
                return;
            }
        }
        preview.method_36456(0.0f);
        StationaryCannonBuiltinItemRenderer.preview.field_5982 = 0.0f;
        preview.setBarrelPitch(0.0f);
        matrices.method_22903();
        ItemTransformUtil.apply(matrices, ItemTransformUtil.select(mode, RenderTransforms.Target.FORTRESS_CANNON_GUI, RenderTransforms.Target.FORTRESS_CANNON_FIRST_PERSON, RenderTransforms.Target.FORTRESS_CANNON_THIRD_PERSON, RenderTransforms.Target.FORTRESS_CANNON_FIXED, RenderTransforms.Target.FORTRESS_CANNON_GROUND), mode, false);
        client.method_1561().method_3954((class_1297)preview, 0.0, 0.0, 0.0, 0.0f, client.method_1488(), matrices, vertexConsumers, light);
        matrices.method_22909();
    }
}

