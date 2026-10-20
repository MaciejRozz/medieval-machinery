/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_5614$class_5615
 *  net.minecraft.class_827
 */
package com.medievalmachinery.mod.client.ammunition;

import com.medievalmachinery.mod.block.ammunition.SpecialPayloadBlockEntity;
import com.medievalmachinery.mod.client.ammunition.DecoratedPayloadVisualRenderer;
import com.medievalmachinery.mod.registry.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_5614;
import net.minecraft.class_827;

@Environment(value=EnvType.CLIENT)
public final class SpecialPayloadBlockEntityRenderer
implements class_827<SpecialPayloadBlockEntity> {
    private final DecoratedPayloadVisualRenderer visual = new DecoratedPayloadVisualRenderer();

    public SpecialPayloadBlockEntityRenderer(class_5614.class_5615 context) {
    }

    public void render(SpecialPayloadBlockEntity blockEntity, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light, int overlay) {
        boolean explosive = blockEntity.method_11010().method_27852(ModBlocks.EXPLOSIVE_PAYLOAD);
        this.visual.render(explosive, matrices, vertexConsumers, light, overlay);
    }
}

