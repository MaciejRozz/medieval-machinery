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
 *  net.minecraft.class_811
 */
package com.medievalmachinery.mod.client.fieldcannon;

import com.medievalmachinery.mod.blockentity.machine.cannon.DecorativeFieldCannonBlockEntity;
import com.medievalmachinery.mod.client.fieldcannon.DecorativeFieldCannonRenderer;
import com.medievalmachinery.mod.client.render.ItemTransformUtil;
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
import net.minecraft.class_811;

@Environment(value=EnvType.CLIENT)
public final class DecorativeFieldCannonBuiltinItemRenderer {
    private static final DecorativeFieldCannonRenderer RENDERER = new DecorativeFieldCannonRenderer();
    private static final DecorativeFieldCannonBlockEntity PREVIEW = new DecorativeFieldCannonBlockEntity(class_2338.field_10980, ModBlocks.DECORATIVE_FIELD_CANNON.method_9564());

    private DecorativeFieldCannonBuiltinItemRenderer() {
    }

    public static void register() {
        BuiltinItemRendererRegistry.INSTANCE.register((class_1935)ModBlocks.DECORATIVE_FIELD_CANNON.method_8389(), DecorativeFieldCannonBuiltinItemRenderer::render);
    }

    private static void render(class_1799 stack, class_811 mode, class_4587 matrices, class_4597 consumers, int light, int overlay) {
        matrices.method_22903();
        ItemTransformUtil.apply(matrices, ItemTransformUtil.select(mode, RenderTransforms.Target.DECO_FIELD_CANNON_GUI, RenderTransforms.Target.DECO_FIELD_CANNON_FIRST_PERSON, RenderTransforms.Target.DECO_FIELD_CANNON_THIRD_PERSON, RenderTransforms.Target.DECO_FIELD_CANNON_FIXED, RenderTransforms.Target.DECO_FIELD_CANNON_GROUND), mode, true);
        RENDERER.method_3569(PREVIEW, 0.0f, matrices, consumers, light, overlay);
        matrices.method_22909();
    }
}

