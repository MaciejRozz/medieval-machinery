/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  software.bernie.geckolib.animatable.GeoBlockEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.medievalmachinery.mod.block;

import com.medievalmachinery.mod.platform.RegisteredContent;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class WheelBlockEntity
extends class_2586
implements GeoBlockEntity {
    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public WheelBlockEntity(class_2338 pos, class_2680 state) {
        super(RegisteredContent.wheel(), pos, state);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animationCache;
    }
}

