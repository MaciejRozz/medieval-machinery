/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_630
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.core.animatable.model.CoreGeoBone
 *  software.bernie.geckolib.util.RenderUtils
 */
package com.medievalmachinery.mod.client.npc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_630;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.util.RenderUtils;

@Environment(value=EnvType.CLIENT)
final class MedievalArmorPoseSync {
    private MedievalArmorPoseSync() {
    }

    static void matchArm(class_630 arm, GeoBone armorBone, float neutralPivotX) {
        if (armorBone == null) {
            return;
        }
        RenderUtils.matchModelPartRot((class_630)arm, (CoreGeoBone)armorBone);
    }
}

