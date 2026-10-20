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

import com.medievalmachinery.mod.blockentity.machine.cannon.DecorativeFortressCannonBlockEntity;
import com.medievalmachinery.mod.client.cannon.DecorativeFortressCannonModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5614;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@Environment(value=EnvType.CLIENT)
public final class DecorativeFortressCannonRenderer
extends GeoBlockRenderer<DecorativeFortressCannonBlockEntity> {
    public DecorativeFortressCannonRenderer() {
        super((GeoModel)new DecorativeFortressCannonModel());
    }

    public DecorativeFortressCannonRenderer(class_5614.class_5615 context) {
        this();
    }

    public boolean rendersOutsideBoundingBox(DecorativeFortressCannonBlockEntity be) {
        return true;
    }
}

