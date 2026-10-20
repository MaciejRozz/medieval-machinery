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
package com.medievalmachinery.mod.client.cannon;

import com.medievalmachinery.mod.blockentity.machine.cannon.StationaryCannonBlockEntity;
import com.medievalmachinery.mod.client.cannon.StationaryCannonModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5614;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@Environment(value=EnvType.CLIENT)
public final class StationaryCannonRenderer
extends GeoBlockRenderer<StationaryCannonBlockEntity> {
    public StationaryCannonRenderer() {
        super((GeoModel)new StationaryCannonModel());
    }

    public StationaryCannonRenderer(class_5614.class_5615 context) {
        this();
    }

    public boolean rendersOutsideBoundingBox(StationaryCannonBlockEntity blockEntity) {
        return true;
    }
}

