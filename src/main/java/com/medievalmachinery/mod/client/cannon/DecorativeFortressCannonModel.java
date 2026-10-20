/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2960
 *  software.bernie.geckolib.model.GeoModel
 */
package com.medievalmachinery.mod.client.cannon;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.blockentity.machine.cannon.DecorativeFortressCannonBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(value=EnvType.CLIENT)
public final class DecorativeFortressCannonModel
extends GeoModel<DecorativeFortressCannonBlockEntity> {
    private static final class_2960 MODEL = MedievalMachinery.id("geo/decorative_stationary_cannon.geo.json");
    private static final class_2960 TEXTURE = MedievalMachinery.id("textures/block/stationary_cannon.png");
    private static final class_2960 ANIMATION = MedievalMachinery.id("animations/stationary_cannon.animation.json");

    public class_2960 getModelResource(DecorativeFortressCannonBlockEntity a) {
        return MODEL;
    }

    public class_2960 getTextureResource(DecorativeFortressCannonBlockEntity a) {
        return TEXTURE;
    }

    public class_2960 getAnimationResource(DecorativeFortressCannonBlockEntity a) {
        return ANIMATION;
    }
}

