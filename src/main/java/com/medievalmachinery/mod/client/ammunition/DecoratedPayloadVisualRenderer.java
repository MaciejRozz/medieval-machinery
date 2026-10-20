/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1792
 *  net.minecraft.class_1802
 *  net.minecraft.class_1921
 *  net.minecraft.class_2960
 *  net.minecraft.class_4587
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597
 *  net.minecraft.class_4722
 *  net.minecraft.class_4730
 *  net.minecraft.class_5321
 *  net.minecraft.class_630
 *  net.minecraft.class_8173
 *  net.minecraft.class_8188
 */
package com.medievalmachinery.mod.client.ammunition;

import com.medievalmachinery.mod.MedievalMachinery;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4722;
import net.minecraft.class_4730;
import net.minecraft.class_5321;
import net.minecraft.class_630;
import net.minecraft.class_8173;
import net.minecraft.class_8188;

@Environment(value=EnvType.CLIENT)
final class DecoratedPayloadVisualRenderer {
    private static final class_4730 BASE_TEXTURE = class_4722.method_49341((class_5321)class_8173.field_42787);
    private static final class_4730 SIDE_TEXTURE = DecoratedPayloadVisualRenderer.createVanillaBrickSideTexture();
    private static final class_2960 TNT_TEXTURE = new class_2960("minecraft", "textures/block/tnt_side.png");
    private static final class_2960 FIRE_TEXTURE = MedievalMachinery.id("textures/item/fire_payload_emblem.png");
    private final class_630 neck;
    private final class_630 top;
    private final class_630 bottom;
    private final class_630 front;
    private final class_630 back;
    private final class_630 left;
    private final class_630 right;

    private static class_4730 createVanillaBrickSideTexture() {
        class_5321 key = class_8173.method_49206((class_1792)class_1802.field_8621);
        return key == null ? null : class_4722.method_49341((class_5321)key);
    }

    DecoratedPayloadVisualRenderer() {
        class_630 base = class_8188.method_49346().method_32109();
        class_630 sides = class_8188.method_49347().method_32109();
        this.neck = base.method_32086("neck");
        this.top = base.method_32086("top");
        this.bottom = base.method_32086("bottom");
        this.front = sides.method_32086("front");
        this.back = sides.method_32086("back");
        this.left = sides.method_32086("left");
        this.right = sides.method_32086("right");
    }

    void render(boolean explosive, class_4587 matrices, class_4597 consumers, int light, int overlay) {
        if (BASE_TEXTURE == null) {
            return;
        }
        class_4588 baseConsumer = BASE_TEXTURE.method_24145(consumers, class_1921::method_23572);
        this.neck.method_22698(matrices, baseConsumer, light, overlay);
        this.top.method_22698(matrices, baseConsumer, light, overlay);
        this.bottom.method_22698(matrices, baseConsumer, light, overlay);
        if (SIDE_TEXTURE != null) {
            class_4588 sideConsumer = SIDE_TEXTURE.method_24145(consumers, class_1921::method_23572);
            this.front.method_22698(matrices, sideConsumer, light, overlay);
            this.back.method_22698(matrices, sideConsumer, light, overlay);
            this.left.method_22698(matrices, sideConsumer, light, overlay);
            this.right.method_22698(matrices, sideConsumer, light, overlay);
        }
        this.renderFourSideEmblems(explosive ? TNT_TEXTURE : FIRE_TEXTURE, matrices, consumers, light, overlay);
    }

    private void renderFourSideEmblems(class_2960 texture, class_4587 matrices, class_4597 consumers, int light, int overlay) {
        class_4588 emblemConsumer = consumers.getBuffer(class_1921.method_23578((class_2960)texture));
        float emblemScale = 0.6f;
        float surfaceOffsetScale = 1.004f;
        DecoratedPayloadVisualRenderer.renderScaledSide(this.front, emblemConsumer, matrices, light, overlay, 0.6f, 0.6f, 1.004f);
        DecoratedPayloadVisualRenderer.renderScaledSide(this.back, emblemConsumer, matrices, light, overlay, 0.6f, 0.6f, 1.004f);
        DecoratedPayloadVisualRenderer.renderScaledSide(this.left, emblemConsumer, matrices, light, overlay, 1.004f, 0.6f, 0.6f);
        DecoratedPayloadVisualRenderer.renderScaledSide(this.right, emblemConsumer, matrices, light, overlay, 1.004f, 0.6f, 0.6f);
    }

    private static void renderScaledSide(class_630 side, class_4588 consumer, class_4587 matrices, int light, int overlay, float scaleX, float scaleY, float scaleZ) {
        matrices.method_22903();
        matrices.method_22904(0.5, 0.5, 0.5);
        matrices.method_22905(scaleX, scaleY, scaleZ);
        matrices.method_22904(-0.5, -0.5, -0.5);
        side.method_22698(matrices, consumer, light, overlay);
        matrices.method_22909();
    }
}

