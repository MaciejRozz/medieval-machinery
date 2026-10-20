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
package com.medievalmachinery.mod.client.cannon;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.entity.cannon.StationaryCannonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class StationaryCannonEntityModel
extends GeoModel<StationaryCannonEntity> {
    private static final class_2960 MODEL = MedievalMachinery.id("geo/stationary_cannon.geo.json");
    private static final class_2960 TEXTURE = MedievalMachinery.id("textures/block/stationary_cannon.png");
    private static final class_2960 ANIMATION = MedievalMachinery.id("animations/stationary_cannon.animation.json");

    public class_2960 getModelResource(StationaryCannonEntity animatable) {
        return MODEL;
    }

    public class_2960 getTextureResource(StationaryCannonEntity animatable) {
        return TEXTURE;
    }

    public class_2960 getAnimationResource(StationaryCannonEntity animatable) {
        return ANIMATION;
    }

    public void setCustomAnimations(StationaryCannonEntity animatable, long instanceId, AnimationState<StationaryCannonEntity> animationState) {
        CoreGeoBone barrel;
        CoreGeoBone top;
        CoreGeoBone carriage;
        super.setCustomAnimations((GeoAnimatable)animatable, instanceId, animationState);
        float tickDelta = animationState.getPartialTick();
        float smoothYaw = animatable.getVisualYaw(tickDelta);
        float recoil = animatable.getRecoilOffset(tickDelta);
        CoreGeoBone root = this.getAnimationProcessor().getBone("root");
        if (root != null) {
            root.setRotY((float)Math.toRadians(-smoothYaw));
        }
        if ((carriage = this.getAnimationProcessor().getBone("carriage")) != null) {
            carriage.setPosZ(recoil);
        }
        float pitchDegrees = animatable.getBarrelPitch();
        float normalizedPitch = Math.max(0.0f, Math.min(1.0f, pitchDegrees / 40.0f));
        float supportExtension = normalizedPitch * 10.825f;
        CoreGeoBone stem = this.getAnimationProcessor().getBone("elevation_support_stem");
        if (stem != null) {
            stem.setScaleY(1.0f + supportExtension / 4.5f);
        }
        if ((top = this.getAnimationProcessor().getBone("elevation_support_top")) != null) {
            top.setPosY(supportExtension);
        }
        if ((barrel = this.getAnimationProcessor().getBone("barrel")) != null) {
            barrel.setRotX((float)Math.toRadians(pitchDegrees));
        }
    }
}

