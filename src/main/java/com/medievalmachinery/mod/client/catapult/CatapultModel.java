/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2960
 *  net.minecraft.class_3532
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.model.CoreGeoBone
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.model.GeoModel
 */
package com.medievalmachinery.mod.client.catapult;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class CatapultModel
extends GeoModel<CatapultEntity> {
    private static final float ARM_PIVOT_Y = 24.0f;
    private static final float ARM_PIVOT_Z = 6.5f;
    private static final float ROPE_ANCHOR_Y = 20.0f;
    private static final float ROPE_ANCHOR_Z = -25.5f;
    private static final float ARM_ATTACH_LOCAL_Y = -3.1883f;
    private static final float ARM_ATTACH_LOCAL_Z = -32.1774f;
    private static final float ROPE_BASE_LENGTH = 36.8f;

    public class_2960 getModelResource(CatapultEntity a) {
        return MedievalMachinery.id("geo/catapult.geo.json");
    }

    public class_2960 getTextureResource(CatapultEntity a) {
        return MedievalMachinery.id("textures/entity/catapult.png");
    }

    public class_2960 getAnimationResource(CatapultEntity a) {
        return MedievalMachinery.id("animations/empty.animation.json");
    }

    public void setCustomAnimations(CatapultEntity entity, long id, AnimationState<CatapultEntity> state) {
        CoreGeoBone brake;
        CoreGeoBone crank;
        CoreGeoBone ammo;
        super.setCustomAnimations((GeoAnimatable)entity, id, state);
        float tickDelta = state.getPartialTick();
        CoreGeoBone root = this.getAnimationProcessor().getBone("root");
        if (root != null) {
            root.setRotY((float)Math.toRadians(-entity.getVisualYaw(tickDelta)));
        }
        float armDegrees = entity.getArmAngleDegrees(tickDelta);
        CoreGeoBone arm = this.getAnimationProcessor().getBone("arm");
        if (arm != null) {
            arm.setRotX((float)Math.toRadians(-armDegrees));
        }
        if ((ammo = this.getAnimationProcessor().getBone("ammo")) != null) {
            ammo.setHidden(true);
        }
        float winchRotation = (float)Math.toRadians(-entity.getWinchRotationDegrees(tickDelta));
        CoreGeoBone winch = this.getAnimationProcessor().getBone("winch");
        if (winch != null) {
            winch.setRotX(winchRotation);
        }
        if ((crank = this.getAnimationProcessor().getBone("winch_crank")) != null) {
            crank.setRotX((float)Math.toRadians(-entity.getCrankAngleDegrees(tickDelta)));
        }
        if ((brake = this.getAnimationProcessor().getBone("winch_brake")) != null) {
            brake.setRotX((float)Math.toRadians(-entity.getBrakeAngleDegrees(tickDelta)));
        }
        this.updateWinchRope(entity.getRuntimeArmRotationDegrees(tickDelta));
        float wheelRotation = (float)(-Math.toRadians(entity.getWheelRotation()));
        this.setWheelRotation("wheel_front_left", wheelRotation);
        this.setWheelRotation("wheel_back_left", wheelRotation);
        this.setWheelRotation("wheel_front_right", wheelRotation);
        this.setWheelRotation("wheel_back_right", wheelRotation);
    }

    private void setWheelRotation(String boneName, float rotation) {
        CoreGeoBone bone = this.getAnimationProcessor().getBone(boneName);
        if (bone != null) {
            bone.setRotX(rotation);
        }
    }

    private void updateWinchRope(float runtimeArmDegrees) {
        CoreGeoBone rope = this.getAnimationProcessor().getBone("winch_rope");
        if (rope == null) {
            return;
        }
        float radians = (float)Math.toRadians(runtimeArmDegrees);
        float attachY = 24.0f + -3.1883f * class_3532.method_15362((float)radians) - -32.1774f * class_3532.method_15374((float)radians);
        float attachZ = 6.5f + -3.1883f * class_3532.method_15374((float)radians) + -32.1774f * class_3532.method_15362((float)radians);
        float deltaY = attachY - 20.0f;
        float deltaZ = attachZ - -25.5f;
        float length = class_3532.method_15355((float)(deltaY * deltaY + deltaZ * deltaZ));
        rope.setRotX((float)Math.atan2(deltaZ, deltaY));
        rope.setScaleY(Math.max(0.001f, length / 36.8f));
    }
}

