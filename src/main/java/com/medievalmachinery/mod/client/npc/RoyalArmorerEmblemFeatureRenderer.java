/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1646
 *  net.minecraft.class_1921
 *  net.minecraft.class_2960
 *  net.minecraft.class_3883
 *  net.minecraft.class_3887
 *  net.minecraft.class_4587
 *  net.minecraft.class_4587$class_4665
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_620
 *  net.minecraft.class_630
 *  org.joml.Matrix3f
 *  org.joml.Matrix4f
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.npc.organization.RoyalArmorerOrderCarrier;
import com.medievalmachinery.mod.registry.ModVillagers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1646;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_620;
import net.minecraft.class_630;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(value=EnvType.CLIENT)
public final class RoyalArmorerEmblemFeatureRenderer
extends class_3887<class_1646, class_620<class_1646>> {
    private static final class_2960 VANILLA_ARMORER_TEXTURE = new class_2960("minecraft", "textures/entity/villager/profession/armorer.png");

    public RoyalArmorerEmblemFeatureRenderer(class_3883<class_1646, class_620<class_1646>> context) {
        super(context);
    }

    public void render(class_4587 matrices, class_4597 vertices, int light, class_1646 villager, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (villager.method_7231().method_16924() != ModVillagers.ROYAL_ARMORER) {
            return;
        }
        class_4588 armorer = vertices.getBuffer(class_1921.method_23578((class_2960)VANILLA_ARMORER_TEXTURE));
        ((class_620)this.method_17165()).method_2828(matrices, armorer, light, class_4608.field_21444, 1.0f, 1.0f, 1.0f, 1.0f);
        if (!(villager instanceof RoyalArmorerOrderCarrier)) {
            return;
        }
        RoyalArmorerOrderCarrier carrier = (RoyalArmorerOrderCarrier)villager;
        int ordinal = carrier.medievalMachinery$getRoyalArmorerOrderOrdinal();
        OrderStyle[] values = OrderStyle.values();
        if (ordinal <= 0 || ordinal >= values.length) {
            return;
        }
        OrderStyle style = values[ordinal];
        class_4588 vc = vertices.getBuffer(class_1921.method_23578((class_2960)MedievalMachinery.id("textures/order/master/" + style.method_15434() + ".png")));
        matrices.method_22903();
        class_630 body = ((class_620)this.method_17165()).method_32008().method_32086("body");
        body.method_22703(matrices);
        class_4587.class_4665 entry = matrices.method_23760();
        Matrix4f pos = entry.method_23761();
        Matrix3f normal = entry.method_23762();
        float x0 = -0.196875f;
        float x1 = 0.196875f;
        float y0 = 0.021875f;
        float y1 = 0.446875f;
        float z = 0.234375f;
        RoyalArmorerEmblemFeatureRenderer.vertex(vc, pos, normal, x0, y1, z, 0.0f, 1.0f, light);
        RoyalArmorerEmblemFeatureRenderer.vertex(vc, pos, normal, x0, y0, z, 0.0f, 0.0f, light);
        RoyalArmorerEmblemFeatureRenderer.vertex(vc, pos, normal, x1, y0, z, 1.0f, 0.0f, light);
        RoyalArmorerEmblemFeatureRenderer.vertex(vc, pos, normal, x1, y1, z, 1.0f, 1.0f, light);
        matrices.method_22909();
    }

    private static void vertex(class_4588 vc, Matrix4f pos, Matrix3f normal, float x, float y, float z, float u, float v, int light) {
        vc.method_22918(pos, x, y, z).method_1336(255, 255, 255, 255).method_22913(u, v).method_22922(class_4608.field_21444).method_22916(light).method_23763(normal, 0.0f, 0.0f, 1.0f).method_1344();
    }
}

