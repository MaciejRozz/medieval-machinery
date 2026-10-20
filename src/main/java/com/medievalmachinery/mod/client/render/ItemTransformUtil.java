/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_4587
 *  net.minecraft.class_7833
 *  net.minecraft.class_811
 */
package com.medievalmachinery.mod.client.render;

import com.medievalmachinery.mod.client.render.RenderTransforms;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_811;

@Environment(value=EnvType.CLIENT)
public final class ItemTransformUtil {
    private ItemTransformUtil() {
    }

    public static RenderTransforms.Transform select(class_811 mode, RenderTransforms.Target gui, RenderTransforms.Target firstPerson, RenderTransforms.Target thirdPerson, RenderTransforms.Target fixed, RenderTransforms.Target ground) {
        return switch (mode) {
            case class_811.field_4317 -> RenderTransforms.get(gui);
            case class_811.field_4321, class_811.field_4322 -> RenderTransforms.get(firstPerson);
            case class_811.field_4323, class_811.field_4320 -> RenderTransforms.get(thirdPerson);
            case class_811.field_4319 -> RenderTransforms.get(fixed);
            case class_811.field_4318 -> RenderTransforms.get(ground);
            default -> RenderTransforms.get(gui);
        };
    }

    public static void apply(class_4587 matrices, RenderTransforms.Transform transform, class_811 mode, boolean centerForBlockRenderer) {
        matrices.method_22904(transform.translationX, transform.translationY, transform.translationZ);
        if (mode == class_811.field_4319) {
            matrices.method_22904(0.5, 0.5, 0.5);
        }
        matrices.method_22907(class_7833.field_40714.rotationDegrees(transform.rotationX));
        matrices.method_22907(class_7833.field_40716.rotationDegrees(transform.rotationY));
        matrices.method_22907(class_7833.field_40718.rotationDegrees(transform.rotationZ));
        if (mode == class_811.field_4319) {
            matrices.method_22904(-0.5, -0.5, -0.5);
        }
        matrices.method_22905(transform.scale, transform.scale, transform.scale);
        if (centerForBlockRenderer) {
            matrices.method_22904(-0.5, 0.0, -0.5);
        }
    }
}

