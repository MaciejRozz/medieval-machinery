/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_5601
 *  net.minecraft.class_5603
 *  net.minecraft.class_5605
 *  net.minecraft.class_5606
 *  net.minecraft.class_5607
 *  net.minecraft.class_5609
 *  net.minecraft.class_5610
 *  net.minecraft.class_572
 *  net.minecraft.class_630
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5601;
import net.minecraft.class_5603;
import net.minecraft.class_5605;
import net.minecraft.class_5606;
import net.minecraft.class_5607;
import net.minecraft.class_5609;
import net.minecraft.class_5610;
import net.minecraft.class_572;
import net.minecraft.class_630;

@Environment(value=EnvType.CLIENT)
public final class MedievalSoldierVanillaArmorModel
extends class_572<MedievalSoldierEntity> {
    public static final class_5601 INNER_LAYER = new class_5601(MedievalMachinery.id("soldier_vanilla_armor"), "inner");
    public static final class_5601 OUTER_LAYER = new class_5601(MedievalMachinery.id("soldier_vanilla_armor"), "outer");

    public MedievalSoldierVanillaArmorModel(class_630 root) {
        super(root);
    }

    public static class_5607 createInnerLayer() {
        return MedievalSoldierVanillaArmorModel.createLayer(new class_5605(0.5f), false);
    }

    public static class_5607 createOuterLayer() {
        return MedievalSoldierVanillaArmorModel.createLayer(new class_5605(1.0f), true);
    }

    private static class_5607 createLayer(class_5605 dilation, boolean villagerHelmet) {
        class_5609 data = new class_5609();
        class_5610 root = data.method_32111();
        if (villagerHelmet) {
            root.method_32117("head", class_5606.method_32108().method_32101(0, 0).method_32098(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation), class_5603.method_32090((float)0.0f, (float)1.0f, (float)0.0f));
        } else {
            root.method_32117("head", class_5606.method_32108().method_32101(0, 0).method_32098(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation), class_5603.field_27701);
        }
        root.method_32117("hat", class_5606.method_32108(), class_5603.field_27701);
        root.method_32117("body", class_5606.method_32108().method_32101(16, 16).method_32098(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation), class_5603.field_27701);
        root.method_32117("right_arm", class_5606.method_32108().method_32101(40, 16).method_32098(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), class_5603.method_32090((float)-5.0f, (float)2.0f, (float)0.0f));
        root.method_32117("left_arm", class_5606.method_32108().method_32101(40, 16).method_32096().method_32098(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), class_5603.method_32090((float)5.0f, (float)2.0f, (float)0.0f));
        root.method_32117("right_leg", class_5606.method_32108().method_32101(0, 16).method_32098(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), class_5603.method_32090((float)-1.9f, (float)12.0f, (float)0.0f));
        root.method_32117("left_leg", class_5606.method_32108().method_32101(0, 16).method_32096().method_32098(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), class_5603.method_32090((float)1.9f, (float)12.0f, (float)0.0f));
        return class_5607.method_32110((class_5609)data, (int)64, (int)32);
    }
}

