/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1921
 *  net.minecraft.class_2350
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_2960
 *  net.minecraft.class_4587
 *  net.minecraft.class_4587$class_4665
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_7833
 *  org.joml.Matrix3f
 *  org.joml.Matrix4f
 */
package com.medievalmachinery.mod.client.garrison;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.block.garrison.WatchpostBlock;
import com.medievalmachinery.mod.block.garrison.WatchpostBlockEntity;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1921;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_7833;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(value=EnvType.CLIENT)
public final class WatchpostOrderEmblemRenderer {
    private WatchpostOrderEmblemRenderer() {
    }

    public static void render(WatchpostBlockEntity blockEntity, class_4587 matrices, class_4597 vertices, int light) {
        class_2680 state = blockEntity.method_11010();
        if (!(state.method_26204() instanceof WatchpostBlock)) {
            return;
        }
        OrderStyle style = (OrderStyle)((Object)state.method_11654(WatchpostBlock.ORDER));
        String styleName = style.method_15434();
        class_2960 clothTexture = MedievalMachinery.id("textures/block/watchpost_banner_" + styleName + ".png");
        matrices.method_22903();
        matrices.method_22904(0.5, 0.0, 0.5);
        matrices.method_22907(class_7833.field_40716.rotationDegrees(WatchpostOrderEmblemRenderer.rotationFor((class_2350)state.method_11654((class_2769)WatchpostBlock.FACING))));
        matrices.method_22904(-0.5, 0.0, -0.5);
        float clothTop = 0.759375f;
        float clothVTop = 0.041666668f;
        WatchpostOrderEmblemRenderer.renderQuad(matrices, vertices, clothTexture, light, 0.705f, 0.19f, 1.295f, 0.759375f, -0.031f, false, 0.041666668f, 1.0f);
        WatchpostOrderEmblemRenderer.renderQuad(matrices, vertices, clothTexture, light, 1.295f, 0.19f, 0.705f, 0.759375f, 0.018f, true, 0.041666668f, 1.0f);
        if (style != OrderStyle.NONE) {
            class_2960 emblem = MedievalMachinery.id("textures/order/master/" + styleName + ".png");
            WatchpostOrderEmblemRenderer.renderQuad(matrices, vertices, emblem, light, 0.845f, 0.41625f, 1.155f, 0.70625f, -0.036f, false);
        }
        matrices.method_22909();
    }

    private static void renderQuad(class_4587 matrices, class_4597 vertices, class_2960 texture, int light, float x0, float y0, float x1, float y1, float z, boolean reverse) {
        WatchpostOrderEmblemRenderer.renderQuad(matrices, vertices, texture, light, x0, y0, x1, y1, z, reverse, 0.0f, 1.0f);
    }

    private static void renderQuad(class_4587 matrices, class_4597 vertices, class_2960 texture, int light, float x0, float y0, float x1, float y1, float z, boolean reverse, float vTop, float vBottom) {
        class_4588 vc = vertices.getBuffer(class_1921.method_23578((class_2960)texture));
        class_4587.class_4665 entry = matrices.method_23760();
        Matrix4f pos = entry.method_23761();
        Matrix3f normal = entry.method_23762();
        if (!reverse) {
            WatchpostOrderEmblemRenderer.vertex(vc, pos, normal, x0, y1, z, 0.0f, vTop, light, 0.0f, 0.0f, -1.0f);
            WatchpostOrderEmblemRenderer.vertex(vc, pos, normal, x0, y0, z, 0.0f, vBottom, light, 0.0f, 0.0f, -1.0f);
            WatchpostOrderEmblemRenderer.vertex(vc, pos, normal, x1, y0, z, 1.0f, vBottom, light, 0.0f, 0.0f, -1.0f);
            WatchpostOrderEmblemRenderer.vertex(vc, pos, normal, x1, y1, z, 1.0f, vTop, light, 0.0f, 0.0f, -1.0f);
        } else {
            WatchpostOrderEmblemRenderer.vertex(vc, pos, normal, x0, y1, z, 0.0f, vTop, light, 0.0f, 0.0f, 1.0f);
            WatchpostOrderEmblemRenderer.vertex(vc, pos, normal, x0, y0, z, 0.0f, vBottom, light, 0.0f, 0.0f, 1.0f);
            WatchpostOrderEmblemRenderer.vertex(vc, pos, normal, x1, y0, z, 1.0f, vBottom, light, 0.0f, 0.0f, 1.0f);
            WatchpostOrderEmblemRenderer.vertex(vc, pos, normal, x1, y1, z, 1.0f, vTop, light, 0.0f, 0.0f, 1.0f);
        }
    }

    private static void vertex(class_4588 vc, Matrix4f pos, Matrix3f normal, float x, float y, float z, float u, float v, int light, float nx, float ny, float nz) {
        vc.method_22918(pos, x, y, z).method_1336(255, 255, 255, 255).method_22913(u, v).method_22922(class_4608.field_21444).method_22916(light).method_23763(normal, nx, ny, nz).method_1344();
    }

    private static float rotationFor(class_2350 facing) {
        return switch (facing) {
            case class_2350.field_11034 -> -90.0f;
            case class_2350.field_11035 -> 180.0f;
            case class_2350.field_11039 -> 90.0f;
            default -> 0.0f;
        };
    }
}

