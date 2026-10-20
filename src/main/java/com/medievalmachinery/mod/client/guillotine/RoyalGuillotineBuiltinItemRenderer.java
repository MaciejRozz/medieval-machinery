/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_2338
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_7833
 *  net.minecraft.class_811
 */
package com.medievalmachinery.mod.client.guillotine;

import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import com.medievalmachinery.mod.client.guillotine.GuillotineRenderer;
import com.medievalmachinery.mod.client.render.RenderTransforms;
import com.medievalmachinery.mod.registry.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_2338;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_7833;
import net.minecraft.class_811;

@Environment(value=EnvType.CLIENT)
public final class RoyalGuillotineBuiltinItemRenderer {
    private static final GuillotineRenderer RENDERER = new GuillotineRenderer();
    private static final GuillotineBlockEntity PREVIEW = new GuillotineBlockEntity(class_2338.field_10980, ModBlocks.ROYAL_GUILLOTINE.method_9564());

    private RoyalGuillotineBuiltinItemRenderer() {
    }

    public static void register() {
        BuiltinItemRendererRegistry.INSTANCE.register((class_1935)ModBlocks.ROYAL_GUILLOTINE.method_8389(), RoyalGuillotineBuiltinItemRenderer::render);
    }

    private static void render(class_1799 stack, class_811 mode, class_4587 matrices, class_4597 vertexConsumers, int light, int overlay) {
        matrices.method_22903();
        switch (mode) {
            case field_4317: {
                RoyalGuillotineBuiltinItemRenderer.applyRenderTransform(RenderTransforms.royalGuillotineGui(), matrices);
                break;
            }
            case field_4318: {
                matrices.method_22904(0.5, 0.3, 0.5);
                matrices.method_22905(0.255f, 0.255f, 0.255f);
                matrices.method_22904(-0.5, 0.0, -0.5);
                break;
            }
            case field_4319: {
                matrices.method_22904(0.5, 0.15, 0.5);
                matrices.method_22905(0.245f, 0.245f, 0.245f);
                matrices.method_22904(-0.5, 0.0, -0.5);
                break;
            }
            case field_4322: 
            case field_4321: {
                RoyalGuillotineBuiltinItemRenderer.applyRenderTransform(RenderTransforms.royalGuillotineFirstPerson(), matrices);
                break;
            }
            case field_4320: 
            case field_4323: {
                RoyalGuillotineBuiltinItemRenderer.applyRenderTransform(RenderTransforms.royalGuillotineThirdPerson(), matrices);
                break;
            }
            default: {
                matrices.method_22904(0.5, 0.06, 0.5);
                matrices.method_22905(0.195f, 0.195f, 0.195f);
                matrices.method_22904(-0.5, 0.0, -0.5);
            }
        }
        RENDERER.method_3569(PREVIEW, 0.0f, matrices, vertexConsumers, light, overlay);
        matrices.method_22909();
    }

    private static void applyRenderTransform(RenderTransforms.Transform transform, class_4587 matrices) {
        matrices.method_22904(transform.translationX, transform.translationY, transform.translationZ);
        matrices.method_22907(class_7833.field_40714.rotationDegrees(transform.rotationX));
        matrices.method_22907(class_7833.field_40716.rotationDegrees(transform.rotationY));
        matrices.method_22907(class_7833.field_40718.rotationDegrees(transform.rotationZ));
        matrices.method_22905(transform.scale, transform.scale, transform.scale);
        matrices.method_22904(-0.5, 0.0, -0.5);
    }
}

