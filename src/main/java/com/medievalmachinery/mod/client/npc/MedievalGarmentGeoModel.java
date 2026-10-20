/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2960
 *  software.bernie.geckolib.model.GeoModel
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.client.npc.MedievalGarmentGeoContext;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class MedievalGarmentGeoModel
extends GeoModel<MedievalArmorItem> {
    public class_2960 getModelResource(MedievalArmorItem animatable) {
        return MedievalMachinery.id("geo/armor/order_garment.geo.json");
    }

    public class_2960 getTextureResource(MedievalArmorItem animatable) {
        return MedievalMachinery.id("textures/entity/npc/garment/" + MedievalGarmentGeoContext.current().order().method_15434() + ".png");
    }

    public class_2960 getAnimationResource(MedievalArmorItem animatable) {
        return MedievalMachinery.id("animations/empty.animation.json");
    }
}

