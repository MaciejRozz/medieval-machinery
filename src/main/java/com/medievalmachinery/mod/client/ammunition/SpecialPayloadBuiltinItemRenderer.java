/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_7833
 *  net.minecraft.class_811
 */
package com.medievalmachinery.mod.client.ammunition;

import com.medievalmachinery.mod.client.ammunition.DecoratedPayloadVisualRenderer;
import com.medievalmachinery.mod.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_7833;
import net.minecraft.class_811;

@Environment(value=EnvType.CLIENT)
public final class SpecialPayloadBuiltinItemRenderer {
    private static final DecoratedPayloadVisualRenderer VISUAL = new DecoratedPayloadVisualRenderer();

    private SpecialPayloadBuiltinItemRenderer() {
    }

    public static void register() {
        BuiltinItemRendererRegistry.INSTANCE.register((class_1935)ModItems.EXPLOSIVE_PAYLOAD, SpecialPayloadBuiltinItemRenderer::render);
        BuiltinItemRendererRegistry.INSTANCE.register((class_1935)ModItems.FIRE_PAYLOAD, SpecialPayloadBuiltinItemRenderer::render);
    }

    private static void render(class_1799 stack, class_811 mode, class_4587 matrices, class_4597 consumers, int light, int overlay) {
        matrices.method_22903();
        SpecialPayloadBuiltinItemRenderer.applyTransform(mode, matrices);
        VISUAL.render(stack.method_31574(ModItems.EXPLOSIVE_PAYLOAD), matrices, consumers, light, overlay);
        matrices.method_22909();
    }

    private static void applyTransform(class_811 mode, class_4587 matrices) {
        if (mode == class_811.field_4315) {
            return;
        }
        switch (mode) {
            case field_4317: {
                matrices.method_22904(0.5, 0.5, 0.5);
                matrices.method_22907(class_7833.field_40714.rotationDegrees(30.0f));
                matrices.method_22907(class_7833.field_40716.rotationDegrees(225.0f));
                matrices.method_22905(0.625f, 0.625f, 0.625f);
                matrices.method_22904(-0.5, -0.5, -0.5);
                break;
            }
            case field_4321: 
            case field_4322: {
                matrices.method_22904(0.5, 0.5, 0.5);
                matrices.method_22907(class_7833.field_40716.rotationDegrees(45.0f));
                matrices.method_22905(0.4f, 0.4f, 0.4f);
                matrices.method_22904(-0.5, -0.5, -0.5);
                break;
            }
            case field_4323: 
            case field_4320: {
                matrices.method_22904(0.5, 0.5, 0.5);
                matrices.method_22907(class_7833.field_40714.rotationDegrees(75.0f));
                matrices.method_22907(class_7833.field_40716.rotationDegrees(45.0f));
                matrices.method_22905(0.375f, 0.375f, 0.375f);
                matrices.method_22904(-0.5, -0.5, -0.5);
                break;
            }
            case field_4318: {
                matrices.method_22904(0.5, 0.25, 0.5);
                matrices.method_22905(0.25f, 0.25f, 0.25f);
                matrices.method_22904(-0.5, 0.0, -0.5);
                break;
            }
            case field_4319: {
                matrices.method_22904(0.5, 0.5, 0.5);
                matrices.method_22905(0.5f, 0.5f, 0.5f);
                matrices.method_22904(-0.5, -0.5, -0.5);
                break;
            }
            case field_4316: {
                matrices.method_22904(0.5, 0.5, 0.5);
                matrices.method_22905(0.625f, 0.625f, 0.625f);
                matrices.method_22904(-0.5, -0.5, -0.5);
                break;
            }
            default: {
                matrices.method_22904(0.5, 0.5, 0.5);
                matrices.method_22905(0.5f, 0.5f, 0.5f);
                matrices.method_22904(-0.5, -0.5, -0.5);
            }
        }
    }
}

