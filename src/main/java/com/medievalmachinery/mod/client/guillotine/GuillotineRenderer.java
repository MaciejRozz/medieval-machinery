/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_5614$class_5615
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoBlockRenderer
 */
package com.medievalmachinery.mod.client.guillotine;

import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import com.medievalmachinery.mod.client.guillotine.GuillotineModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5614;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@Environment(value=EnvType.CLIENT)
public final class GuillotineRenderer
extends GeoBlockRenderer<GuillotineBlockEntity> {
    public GuillotineRenderer(class_5614.class_5615 context) {
        this();
    }

    public GuillotineRenderer() {
        super((GeoModel)new GuillotineModel());
    }

    public boolean rendersOutsideBoundingBox(GuillotineBlockEntity blockEntity) {
        return true;
    }
}

