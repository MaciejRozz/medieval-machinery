/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1799
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_5614$class_5615
 *  net.minecraft.class_7833
 *  net.minecraft.class_811
 *  net.minecraft.class_827
 */
package com.medievalmachinery.mod.client.ammunition;

import com.medievalmachinery.mod.block.ammunition.PotionPayloadBlockEntity;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5614;
import net.minecraft.class_7833;
import net.minecraft.class_811;
import net.minecraft.class_827;

@Environment(value=EnvType.CLIENT)
public final class PotionPayloadBlockEntityRenderer
implements class_827<PotionPayloadBlockEntity> {
    public PotionPayloadBlockEntityRenderer(class_5614.class_5615 context) {
    }

    public void render(PotionPayloadBlockEntity blockEntity, float tickDelta, class_4587 matrices, class_4597 vertices, int light, int overlay) {
        List<class_1799> stacks = blockEntity.copyStacks();
        double[][] offsets = new double[][]{{0.5, 0.0825, 0.34}, {0.34, 0.0825, 0.62}, {0.66, 0.0825, 0.62}};
        for (int i = 0; i < stacks.size() && i < 3; ++i) {
            matrices.method_22903();
            matrices.method_22904(offsets[i][0], offsets[i][1], offsets[i][2]);
            matrices.method_22907(class_7833.field_40716.rotationDegrees(i == 0 ? 0.0f : (i == 1 ? -18.0f : 18.0f)));
            matrices.method_22905(1.0f, 1.0f, 1.0f);
            class_310.method_1551().method_1480().method_23178(stacks.get(i), class_811.field_4318, light, class_4608.field_21444, matrices, vertices, blockEntity.method_10997(), (int)blockEntity.method_11016().method_10063() + i);
            matrices.method_22909();
        }
    }
}

