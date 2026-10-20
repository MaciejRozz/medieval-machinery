/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1935
 *  net.minecraft.class_2350
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_5614$class_5615
 *  net.minecraft.class_7833
 *  net.minecraft.class_811
 *  net.minecraft.class_827
 */
package com.medievalmachinery.mod.client.garrison;

import com.medievalmachinery.mod.block.garrison.WatchpostBlock;
import com.medievalmachinery.mod.block.garrison.WatchpostBlockEntity;
import com.medievalmachinery.mod.client.garrison.WatchpostOrderEmblemRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5614;
import net.minecraft.class_7833;
import net.minecraft.class_811;
import net.minecraft.class_827;

@Environment(value=EnvType.CLIENT)
public final class WatchpostBlockEntityRenderer
implements class_827<WatchpostBlockEntity> {
    private static final class_1799 SWORD = new class_1799((class_1935)class_1802.field_8371);
    private static final class_1799 BOW = new class_1799((class_1935)class_1802.field_8102);

    public WatchpostBlockEntityRenderer(class_5614.class_5615 context) {
    }

    public void render(WatchpostBlockEntity blockEntity, float tickDelta, class_4587 matrices, class_4597 vertices, int light, int overlay) {
        class_2680 state = blockEntity.method_11010();
        if (!(state.method_26204() instanceof WatchpostBlock)) {
            return;
        }
        class_2350 facing = (class_2350)state.method_11654((class_2769)WatchpostBlock.FACING);
        matrices.method_22903();
        matrices.method_22904(0.5, 0.0, 0.5);
        matrices.method_22907(class_7833.field_40716.rotationDegrees(WatchpostBlockEntityRenderer.rotationFor(facing)));
        matrices.method_22904(-0.5, 0.0, -0.5);
        WatchpostBlockEntityRenderer.renderFlatItem(blockEntity, SWORD, matrices, vertices, light, 0.38, 0.895, 0.5, -28.0f, 0.74f, 11);
        WatchpostBlockEntityRenderer.renderFlatItem(blockEntity, BOW, matrices, vertices, light, 1.62, 0.895, 0.5, 24.0f, 0.78f, 29);
        matrices.method_22909();
        WatchpostOrderEmblemRenderer.render(blockEntity, matrices, vertices, light);
    }

    private static void renderFlatItem(WatchpostBlockEntity blockEntity, class_1799 stack, class_4587 matrices, class_4597 vertices, int light, double x, double y, double z, float yaw, float scale, int seedOffset) {
        matrices.method_22903();
        matrices.method_22904(x, y, z);
        matrices.method_22907(class_7833.field_40716.rotationDegrees(yaw));
        matrices.method_22907(class_7833.field_40714.rotationDegrees(90.0f));
        matrices.method_22905(scale, scale, scale);
        class_310.method_1551().method_1480().method_23178(stack, class_811.field_4318, light, class_4608.field_21444, matrices, vertices, blockEntity.method_10997(), (int)blockEntity.method_11016().method_10063() + seedOffset);
        matrices.method_22909();
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

