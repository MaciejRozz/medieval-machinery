/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_5617$class_5618
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 */
package com.medievalmachinery.mod.client.fieldcannon;

import com.medievalmachinery.mod.client.fieldcannon.FieldCannonModel;
import com.medievalmachinery.mod.entity.cannon.FieldCannonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5617;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(value=EnvType.CLIENT)
public final class FieldCannonRenderer
extends GeoEntityRenderer<FieldCannonEntity> {
    public FieldCannonRenderer(class_5617.class_5618 context) {
        super(context, (GeoModel)new FieldCannonModel());
        this.field_4673 = 1.2f;
    }
}

