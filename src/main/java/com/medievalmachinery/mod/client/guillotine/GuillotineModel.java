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
package com.medievalmachinery.mod.client.guillotine;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class GuillotineModel
extends GeoModel<GuillotineBlockEntity> {
    private static final class_2960 MODEL = MedievalMachinery.id("geo/guillotine.geo.json");
    private static final class_2960 TEXTURE = MedievalMachinery.id("textures/block/guillotine.png");
    private static final class_2960 ANIMATION = MedievalMachinery.id("animations/guillotine.animation.json");
    private static final class_2960 ROYAL_MODEL = MedievalMachinery.id("geo/royal_guillotine.geo.json");
    private static final class_2960 ROYAL_TEXTURE = MedievalMachinery.id("textures/block/royal_guillotine.png");
    private static final class_2960 ROYAL_ANIMATION = MedievalMachinery.id("animations/royal_guillotine.animation.json");
    private static final float RAISED_ROPE_LENGTH_PIXELS = 3.0f;
    private static final float ROYAL_RAISED_ROPE_LENGTH_PIXELS = 6.0f;
    private static final float LEVER_UP_ANGLE = 0.0f;
    private static final float LEVER_DOWN_ANGLE = (float)Math.toRadians(-55.0);

    public class_2960 getModelResource(GuillotineBlockEntity animatable) {
        return animatable.isRoyalGuillotine() ? ROYAL_MODEL : MODEL;
    }

    public class_2960 getTextureResource(GuillotineBlockEntity animatable) {
        return animatable.isRoyalGuillotine() ? ROYAL_TEXTURE : TEXTURE;
    }

    public class_2960 getAnimationResource(GuillotineBlockEntity animatable) {
        return animatable.isRoyalGuillotine() ? ROYAL_ANIMATION : ANIMATION;
    }

    public void setCustomAnimations(GuillotineBlockEntity animatable, long instanceId, AnimationState<GuillotineBlockEntity> animationState) {
        super.setCustomAnimations((GeoAnimatable)animatable, instanceId, animationState);
        this.updateBlade(animatable, animationState);
        this.updateStraps(animatable.isOccupied());
        this.updateLever(animatable);
    }

    private void updateBlade(GuillotineBlockEntity animatable, AnimationState<GuillotineBlockEntity> animationState) {
        CoreGeoBone ropeBone;
        float bladeDrop = animatable.getBladeDropPixels(animationState.getPartialTick());
        CoreGeoBone bladeBone = this.getAnimationProcessor().getBone("blade");
        if (bladeBone != null) {
            bladeBone.setPosY(-bladeDrop);
        }
        if ((ropeBone = this.getAnimationProcessor().getBone("rope_blade")) != null) {
            float raisedLength = animatable.isRoyalGuillotine() ? 6.0f : 3.0f;
            ropeBone.setScaleY((raisedLength + bladeDrop) / raisedLength);
        }
    }

    private void updateLever(GuillotineBlockEntity animatable) {
        CoreGeoBone leverBone = this.getAnimationProcessor().getBone("lever_handle");
        if (leverBone == null) {
            return;
        }
        leverBone.setRotZ(animatable.isSequenceRunning() ? LEVER_DOWN_ANGLE : 0.0f);
    }

    private void updateStraps(boolean occupied) {
        this.setHidden("chest_strap_open_left", occupied);
        this.setHidden("chest_strap_open_right", occupied);
        this.setHidden("leg_strap_open_left", occupied);
        this.setHidden("leg_strap_open_right", occupied);
        this.setHidden("chest_strap_closed", !occupied);
        this.setHidden("leg_strap_closed", !occupied);
    }

    private void setHidden(String boneName, boolean hidden) {
        CoreGeoBone bone = this.getAnimationProcessor().getBone(boneName);
        if (bone != null) {
            bone.setHidden(hidden);
        }
    }
}

