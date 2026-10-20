/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1921
 *  net.minecraft.class_1935
 *  net.minecraft.class_2960
 *  net.minecraft.class_3883
 *  net.minecraft.class_3887
 *  net.minecraft.class_4587
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_572
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.npc.MedievalGarmentGeoContext;
import com.medievalmachinery.mod.client.npc.MedievalGarmentGeoRenderer;
import com.medievalmachinery.mod.client.npc.MedievalSoldierModel;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1921;
import net.minecraft.class_1935;
import net.minecraft.class_2960;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_572;
import software.bernie.geckolib.core.animatable.GeoAnimatable;

@Environment(value=EnvType.CLIENT)
public final class MedievalSoldierOrderLayer
extends class_3887<MedievalSoldierEntity, MedievalSoldierModel> {
    private static final MedievalArmorItem GARMENT_ITEM = ModItems.ORDER_CHESTPLATE;
    private static final class_1799 GARMENT_STACK = new class_1799((class_1935)GARMENT_ITEM);
    private final MedievalGarmentGeoRenderer garmentRenderer = new MedievalGarmentGeoRenderer();

    public MedievalSoldierOrderLayer(class_3883<MedievalSoldierEntity, MedievalSoldierModel> context) {
        super(context);
    }

    public void render(class_4587 matrices, class_4597 vertices, int light, MedievalSoldierEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        boolean showFeet;
        class_1799 chest = entity.method_6118(class_1304.field_6174);
        class_1799 legs = entity.method_6118(class_1304.field_6172);
        class_1799 feet = entity.method_6118(class_1304.field_6166);
        boolean showTorso = chest.method_7960() || MedievalSoldierOrderLayer.isChainmail(chest);
        boolean showLegs = legs.method_7960() || MedievalSoldierOrderLayer.isChainmail(legs);
        boolean bl = showFeet = feet.method_7960() || MedievalSoldierOrderLayer.isChainmail(feet);
        if (!(showTorso || showLegs || showFeet)) {
            return;
        }
        OrderStyle order = MedievalSoldierOrderLayer.resolveVisibleOrder(entity, chest, legs, feet);
        MedievalGarmentGeoContext.prepare(order, showTorso, showLegs, showFeet);
        this.garmentRenderer.prepForRender((class_1297)entity, GARMENT_STACK, class_1304.field_6174, (class_572)this.method_17165());
        class_4588 consumer = vertices.getBuffer(class_1921.method_23578((class_2960)this.garmentRenderer.getGeoModel().getTextureResource((GeoAnimatable)GARMENT_ITEM)));
        this.garmentRenderer.method_2828(matrices, consumer, light, class_4608.field_21444, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    private static OrderStyle resolveVisibleOrder(MedievalSoldierEntity entity, class_1799 chest, class_1799 legs, class_1799 feet) {
        for (class_1799 stack : new class_1799[]{chest, legs, feet}) {
            OrderStyle order;
            if (!MedievalArmorItem.isMedievalArmor(stack) || (order = MedievalArmorItem.getOrderStyle(stack)) == OrderStyle.NONE) continue;
            return order;
        }
        return entity.getOrderStyle();
    }

    private static boolean isChainmail(class_1799 stack) {
        return stack.method_31574(class_1802.field_8283) || stack.method_31574(class_1802.field_8873) || stack.method_31574(class_1802.field_8218) || stack.method_31574(class_1802.field_8313);
    }
}

