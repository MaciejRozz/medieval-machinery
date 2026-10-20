/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2960
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.model.CoreGeoBone
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.model.GeoModel
 */
package com.medievalmachinery.mod.client.fieldcannon;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.blockentity.machine.cannon.DecorativeFieldCannonBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class DecorativeFieldCannonModel
extends GeoModel<DecorativeFieldCannonBlockEntity> {
    private static final class_2960 MODEL = MedievalMachinery.id("geo/decorative_field_cannon.geo.json");
    private static final class_2960 TEXTURE = MedievalMachinery.id("textures/entity/field_cannon.png");
    private static final class_2960 ANIMATION = MedievalMachinery.id("animations/field_cannon.animation.json");
    private static final float CARRIAGE_TILT_DEGREES = 15.5f;

    public class_2960 getModelResource(DecorativeFieldCannonBlockEntity animatable) {
        return MODEL;
    }

    public class_2960 getTextureResource(DecorativeFieldCannonBlockEntity animatable) {
        return TEXTURE;
    }

    public class_2960 getAnimationResource(DecorativeFieldCannonBlockEntity animatable) {
        return ANIMATION;
    }

    public void setCustomAnimations(DecorativeFieldCannonBlockEntity animatable, long instanceId, AnimationState<DecorativeFieldCannonBlockEntity> animationState) {
        CoreGeoBone fuse;
        CoreGeoBone barrel;
        super.setCustomAnimations((GeoAnimatable)animatable, instanceId, animationState);
        CoreGeoBone root = this.getAnimationProcessor().getBone("root");
        if (root != null) {
            root.setRotX((float)Math.toRadians(15.5));
            root.setRotY(0.0f);
            root.setRotZ(0.0f);
        }
        if ((barrel = this.getAnimationProcessor().getBone("barrel")) != null) {
            barrel.setRotX(0.0f);
        }
        if ((fuse = this.getAnimationProcessor().getBone("fuse")) != null) {
            fuse.setRotX(0.0f);
        }
    }
}

