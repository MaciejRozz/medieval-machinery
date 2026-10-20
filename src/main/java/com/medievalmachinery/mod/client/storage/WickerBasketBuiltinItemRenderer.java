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
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_7833
 *  net.minecraft.class_811
 */
package com.medievalmachinery.mod.client.storage;

import com.medievalmachinery.mod.block.storage.WickerBasketBlockEntity;
import com.medievalmachinery.mod.client.render.RenderTransforms;
import com.medievalmachinery.mod.client.storage.WickerBasketRenderer;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.storage.WickerBasketCarryData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_7833;
import net.minecraft.class_811;

@Environment(value=EnvType.CLIENT)
public final class WickerBasketBuiltinItemRenderer {
    private static final WickerBasketRenderer CONTENT_RENDERER = new WickerBasketRenderer();
    private static final WickerBasketBlockEntity PREVIEW = new WickerBasketBlockEntity(class_2338.field_10980, ModBlocks.WICKER_BASKET.method_9564());

    private WickerBasketBuiltinItemRenderer() {
    }

    public static void register() {
        BuiltinItemRendererRegistry.INSTANCE.register((class_1935)ModBlocks.WICKER_BASKET.method_8389(), WickerBasketBuiltinItemRenderer::render);
    }

    private static void render(class_1799 stack, class_811 mode, class_4587 matrices, class_4597 consumers, int light, int overlay) {
        WickerBasketBuiltinItemRenderer.render(stack, mode, matrices, consumers, light, overlay, false);
    }

    public static void renderCarried(WickerBasketCarryData data, class_811 mode, class_4587 matrices, class_4597 consumers, int light, int overlay) {
        class_1799 stack = new class_1799((class_1935)ModBlocks.WICKER_BASKET);
        stack.method_7948().method_10566("BlockEntityTag", (class_2520)data.blockEntityNbt().method_10553());
        WickerBasketBuiltinItemRenderer.render(stack, mode, matrices, consumers, light, overlay, true);
    }

    private static void render(class_1799 stack, class_811 mode, class_4587 matrices, class_4597 consumers, int light, int overlay, boolean carried) {
        PREVIEW.method_5448();
        class_2487 tag = stack.method_7941("BlockEntityTag");
        if (tag != null) {
            PREVIEW.method_11014(tag.method_10553());
        }
        matrices.method_22903();
        WickerBasketBuiltinItemRenderer.applyTransform(mode, matrices, carried);
        class_310.method_1551().method_1541().method_3353(ModBlocks.WICKER_BASKET.method_9564(), matrices, consumers, light, overlay);
        CONTENT_RENDERER.renderContents(PREVIEW, matrices, consumers, light);
        matrices.method_22909();
    }

    private static void applyTransform(class_811 mode, class_4587 matrices, boolean carried) {
        switch (mode) {
            case field_4317: {
                RenderTransforms.Transform transform = RenderTransforms.basketGui();
                matrices.method_22904(transform.translationX, transform.translationY, transform.translationZ);
                WickerBasketBuiltinItemRenderer.applyRotation(transform, matrices);
                matrices.method_22905(transform.scale, transform.scale, transform.scale);
                matrices.method_22904(-0.5, 0.0, -0.5);
                break;
            }
            case field_4318: {
                matrices.method_22904(0.5, 0.18, 0.5);
                matrices.method_22905(0.55f, 0.55f, 0.55f);
                matrices.method_22904(-0.5, 0.0, -0.5);
                break;
            }
            case field_4319: {
                matrices.method_22904(0.5, 0.2, 0.5);
                matrices.method_22907(class_7833.field_40716.rotationDegrees(180.0f));
                matrices.method_22905(0.8f, 0.8f, 0.8f);
                matrices.method_22904(-0.5, 0.0, -0.5);
                break;
            }
            case field_4322: 
            case field_4321: {
                RenderTransforms.Transform transform = carried ? RenderTransforms.basketCarryFirstPerson() : RenderTransforms.basketFirstPerson();
                matrices.method_22904(transform.translationX, transform.translationY, transform.translationZ);
                WickerBasketBuiltinItemRenderer.applyRotation(transform, matrices);
                matrices.method_22905(transform.scale, transform.scale, transform.scale);
                matrices.method_22904(-0.5, -0.5, -0.5);
                break;
            }
            case field_4320: 
            case field_4323: {
                if (carried) {
                    WickerBasketBuiltinItemRenderer.applyCarriedThirdPersonTransform(mode, matrices);
                    break;
                }
                WickerBasketBuiltinItemRenderer.applyNormalThirdPersonTransform(mode, matrices);
                break;
            }
            default: {
                matrices.method_22904(0.5, 0.16, 0.5);
                matrices.method_22905(0.55f, 0.55f, 0.55f);
                matrices.method_22904(-0.5, 0.0, -0.5);
            }
        }
    }

    private static void applyNormalThirdPersonTransform(class_811 mode, class_4587 matrices) {
        RenderTransforms.Transform transform = RenderTransforms.normal();
        double side = mode == class_811.field_4323 ? -1.0 : 1.0;
        matrices.method_22904(transform.translationX * side, transform.translationY, transform.translationZ);
        WickerBasketBuiltinItemRenderer.applyRotation(transform, matrices);
        matrices.method_22905(transform.scale, transform.scale, transform.scale);
        matrices.method_22904(-0.5, -0.69, -0.5);
    }

    private static void applyCarriedThirdPersonTransform(class_811 mode, class_4587 matrices) {
        RenderTransforms.Transform transform = RenderTransforms.carried();
        double side = mode == class_811.field_4323 ? -1.0 : 1.0;
        matrices.method_22904(transform.translationX * side, transform.translationY, transform.translationZ);
        matrices.method_22907(class_7833.field_40714.rotationDegrees(transform.rotationX));
        matrices.method_22907(class_7833.field_40716.rotationDegrees(10.3f * (float)side));
        matrices.method_22907(class_7833.field_40718.rotationDegrees(-2.9f * (float)side));
        matrices.method_22907(class_7833.field_40716.rotationDegrees(transform.rotationY));
        matrices.method_22907(class_7833.field_40718.rotationDegrees(transform.rotationZ));
        matrices.method_22905(transform.scale, transform.scale, transform.scale);
        matrices.method_22904(-0.5, -0.69, -0.5);
    }

    private static void applyRotation(RenderTransforms.Transform transform, class_4587 matrices) {
        matrices.method_22907(class_7833.field_40714.rotationDegrees(transform.rotationX));
        matrices.method_22907(class_7833.field_40716.rotationDegrees(transform.rotationY));
        matrices.method_22907(class_7833.field_40718.rotationDegrees(transform.rotationZ));
    }
}

