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
import com.medievalmachinery.mod.blockentity.machine.cannon.StationaryCannonBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class StationaryCannonModel
extends GeoModel<StationaryCannonBlockEntity> {
    private static final class_2960 MODEL = MedievalMachinery.id("geo/stationary_cannon.geo.json");
    private static final class_2960 TEXTURE = MedievalMachinery.id("textures/block/stationary_cannon.png");
    private static final class_2960 ANIMATION = MedievalMachinery.id("animations/stationary_cannon.animation.json");

    public class_2960 getModelResource(StationaryCannonBlockEntity animatable) {
        return MODEL;
    }

    public class_2960 getTextureResource(StationaryCannonBlockEntity animatable) {
        return TEXTURE;
    }

    public class_2960 getAnimationResource(StationaryCannonBlockEntity animatable) {
        return ANIMATION;
    }

    public void setCustomAnimations(StationaryCannonBlockEntity animatable, long instanceId, AnimationState<StationaryCannonBlockEntity> animationState) {
        CoreGeoBone barrel;
        CoreGeoBone elevationTop;
        super.setCustomAnimations((GeoAnimatable)animatable, instanceId, animationState);
        float tickDelta = animationState.getPartialTick();
        float yawDegrees = animatable.getYawOffset(tickDelta);
        float pitchDegrees = animatable.getPitch(tickDelta);
        float recoil = animatable.getRecoilOffset(tickDelta);
        float yawRadians = (float)Math.toRadians(-yawDegrees);
        CoreGeoBone carriage = this.getAnimationProcessor().getBone("carriage");
        if (carriage != null) {
            carriage.setRotY(yawRadians);
            carriage.setPosX((float)(-Math.sin(yawRadians)) * recoil);
            carriage.setPosZ((float)Math.cos(yawRadians) * recoil);
        }
        float wheelRotation = -recoil / 4.5f;
        this.setWheelRotation("wheel_front_left", wheelRotation);
        this.setWheelRotation("wheel_front_right", wheelRotation);
        this.setWheelRotation("wheel_back_left", wheelRotation);
        this.setWheelRotation("wheel_back_right", wheelRotation);
        float normalizedPitch = Math.max(0.0f, Math.min(1.0f, (pitchDegrees + 5.0f) / 50.0f));
        float supportExtension = normalizedPitch * 10.825f;
        CoreGeoBone elevationStem = this.getAnimationProcessor().getBone("elevation_support_stem");
        if (elevationStem != null) {
            elevationStem.setScaleY(1.0f + supportExtension / 4.5f);
        }
        if ((elevationTop = this.getAnimationProcessor().getBone("elevation_support_top")) != null) {
            elevationTop.setPosY(supportExtension);
        }
        if ((barrel = this.getAnimationProcessor().getBone("barrel")) != null) {
            barrel.setRotX((float)Math.toRadians(pitchDegrees));
        }
    }

    private void setWheelRotation(String boneName, float rotation) {
        CoreGeoBone wheel = this.getAnimationProcessor().getBone(boneName);
        if (wheel != null) {
            wheel.setRotX(rotation);
        }
    }
}

