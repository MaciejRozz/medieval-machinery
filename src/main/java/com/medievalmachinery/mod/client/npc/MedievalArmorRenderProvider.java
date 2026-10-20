/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1799
 *  net.minecraft.class_572
 *  software.bernie.geckolib.animatable.client.RenderProvider
 *  software.bernie.geckolib.renderer.GeoArmorRenderer
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.npc.MedievalArmorGeoContext;
import com.medievalmachinery.mod.client.npc.MedievalArmorGeoRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_572;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

@Environment(value=EnvType.CLIENT)
public final class MedievalArmorRenderProvider
implements RenderProvider {
    private GeoArmorRenderer<?> renderer;

    public class_572<class_1309> getHumanoidArmorModel(class_1309 livingEntity, class_1799 itemStack, class_1304 equipmentSlot, class_572<class_1309> original) {
        MedievalArmorGeoContext.prepare(livingEntity, itemStack);
        if (this.renderer == null) {
            this.renderer = new MedievalArmorGeoRenderer();
        }
        this.renderer.prepForRender((class_1297)livingEntity, itemStack, equipmentSlot, original);
        return this.renderer;
    }
}

