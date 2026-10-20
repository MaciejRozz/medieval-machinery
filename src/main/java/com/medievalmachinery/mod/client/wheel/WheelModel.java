/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2350
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_2960
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.model.CoreGeoBone
 *  software.bernie.geckolib.core.animation.AnimationState
 *  software.bernie.geckolib.model.GeoModel
 */
package com.medievalmachinery.mod.client.wheel;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.block.WheelBlock;
import com.medievalmachinery.mod.block.WheelBlockEntity;
import com.medievalmachinery.mod.block.WheelPlacementMode;
import com.medievalmachinery.mod.client.render.RenderTransforms;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class WheelModel
extends GeoModel<WheelBlockEntity> {
    private static final class_2960 MODEL = MedievalMachinery.id("geo/wheel.geo.json");
    private static final class_2960 TEXTURE = MedievalMachinery.id("textures/block/wheel.png");
    private static final class_2960 ANIMATION = MedievalMachinery.id("animations/empty.animation.json");

    public class_2960 getModelResource(WheelBlockEntity animatable) {
        return MODEL;
    }

    public class_2960 getTextureResource(WheelBlockEntity animatable) {
        return TEXTURE;
    }

    public class_2960 getAnimationResource(WheelBlockEntity animatable) {
        return ANIMATION;
    }

    public void setCustomAnimations(WheelBlockEntity animatable, long instanceId, AnimationState<WheelBlockEntity> animationState) {
        super.setCustomAnimations((GeoAnimatable)animatable, instanceId, animationState);
        CoreGeoBone wheelBone = this.getAnimationProcessor().getBone("wheel");
        if (wheelBone == null) {
            return;
        }
        class_2680 state = animatable.method_11010();
        if (state.method_11654(WheelBlock.PLACEMENT_MODE) != WheelPlacementMode.WALL) {
            wheelBone.setRotY(0.0f);
            return;
        }
        class_2350 facing = (class_2350)state.method_11654((class_2769)WheelBlock.FACING);
        if (facing == class_2350.field_11034 || facing == class_2350.field_11039) {
            RenderTransforms.Transform transform = facing == class_2350.field_11034 ? RenderTransforms.wheelWallEast() : RenderTransforms.wheelWallWest();
            wheelBone.setRotY((float)Math.toRadians(transform.rotationY));
        } else {
            wheelBone.setRotY(0.0f);
        }
    }
}

