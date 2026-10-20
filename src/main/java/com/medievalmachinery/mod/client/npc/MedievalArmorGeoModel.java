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
import com.medievalmachinery.mod.client.npc.MedievalArmorGeoContext;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.npc.armor.ArmorStyle;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class MedievalArmorGeoModel
extends GeoModel<MedievalArmorItem> {
    public class_2960 getModelResource(MedievalArmorItem animatable) {
        MedievalArmorGeoContext.State state = MedievalArmorGeoContext.current();
        return MedievalMachinery.id("geo/armor/" + state.style().serializedName() + ".geo.json");
    }

    public class_2960 getTextureResource(MedievalArmorItem animatable) {
        MedievalArmorGeoContext.State state = MedievalArmorGeoContext.current();
        OrderStyle order = state.order() == OrderStyle.NONE ? OrderStyle.IRON : state.order();
        String suffix = state.tier().isNetheriteUpgraded() ? "_netherite" : "";
        String textureFamily = switch (state.style()) {
            case ArmorStyle.PLATE, ArmorStyle.HEAVY_BRIGANDINE, ArmorStyle.ARCHER, ArmorStyle.LIGHT_BRIGANDINE -> "plate";
            default -> state.style().serializedName();
        };
        return MedievalMachinery.id("textures/entity/npc/armor/gecko/" + textureFamily + "/" + order.method_15434() + suffix + ".png");
    }

    public class_2960 getAnimationResource(MedievalArmorItem animatable) {
        return MedievalMachinery.id("animations/empty.animation.json");
    }
}

