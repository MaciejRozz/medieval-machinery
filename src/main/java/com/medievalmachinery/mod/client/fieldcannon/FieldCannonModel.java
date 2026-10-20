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
import com.medievalmachinery.mod.entity.cannon.FieldCannonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class FieldCannonModel
extends GeoModel<FieldCannonEntity> {
    private static final class_2960 MODEL = MedievalMachinery.id("geo/field_cannon.geo.json");
    private static final class_2960 TEXTURE = MedievalMachinery.id("textures/entity/field_cannon.png");
    private static final class_2960 ANIMATION = MedievalMachinery.id("animations/field_cannon.animation.json");

    public class_2960 getModelResource(FieldCannonEntity animatable) {
        return MODEL;
    }

    public class_2960 getTextureResource(FieldCannonEntity animatable) {
        return TEXTURE;
    }

    public class_2960 getAnimationResource(FieldCannonEntity animatable) {
        return ANIMATION;
    }

    public void setCustomAnimations(FieldCannonEntity animatable, long instanceId, AnimationState<FieldCannonEntity> animationState) {
        CoreGeoBone fuse;
        CoreGeoBone barrel;
        CoreGeoBone supportTop;
        CoreGeoBone support;
        CoreGeoBone rightWheel;
        CoreGeoBone carriage;
        super.setCustomAnimations((GeoAnimatable)animatable, instanceId, animationState);
        float recoil = animatable.getRecoilOffset(animationState.getPartialTick());
        CoreGeoBone root = this.getAnimationProcessor().getBone("root");
        if (root != null) {
            root.setRotX((float)Math.toRadians(19.0));
            root.setRotY((float)Math.toRadians(-animatable.getVisualYaw(animationState.getPartialTick())));
        }
        if ((carriage = this.getAnimationProcessor().getBone("carriage")) != null) {
            carriage.setPosZ(recoil);
        }
        float wheelRadians = (float)(-Math.toRadians(animatable.getWheelRotation() - recoil * 7.5f));
        CoreGeoBone leftWheel = this.getAnimationProcessor().getBone("wheel_left");
        if (leftWheel != null) {
            leftWheel.setRotX(wheelRadians);
        }
        if ((rightWheel = this.getAnimationProcessor().getBone("wheel_right")) != null) {
            rightWheel.setRotX(wheelRadians);
        }
        if ((support = this.getAnimationProcessor().getBone("elevation_support")) != null) {
            support.setScaleY(1.0f);
        }
        float pitchOffset = animatable.getBarrelPitch() - 12.0f;
        CoreGeoBone supportStem = this.getAnimationProcessor().getBone("elevation_support_stem");
        if (supportStem != null) {
            supportStem.setScaleY(1.0f + pitchOffset / 23.0f);
        }
        if ((supportTop = this.getAnimationProcessor().getBone("elevation_support_top")) != null) {
            supportTop.setPosY(pitchOffset * 0.245f);
        }
        if ((barrel = this.getAnimationProcessor().getBone("barrel")) != null) {
            barrel.setRotX((float)Math.toRadians(animatable.getBarrelPitch() - 12.0f));
        }
        if ((fuse = this.getAnimationProcessor().getBone("fuse")) != null) {
            fuse.setRotX((float)Math.toRadians(animatable.getBarrelPitch() - 12.0f));
        }
    }
}

