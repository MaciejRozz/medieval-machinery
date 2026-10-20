/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1921
 *  net.minecraft.class_2960
 *  net.minecraft.class_3883
 *  net.minecraft.class_3887
 *  net.minecraft.class_4587
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_5602
 *  net.minecraft.class_5617$class_5618
 *  net.minecraft.class_620
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.npc.MedievalSoldierModel;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5602;
import net.minecraft.class_5617;
import net.minecraft.class_620;

@Environment(value=EnvType.CLIENT)
final class QuartermasterFarmerHatFeatureRenderer
extends class_3887<MedievalSoldierEntity, MedievalSoldierModel> {
    private static final class_2960 FARMER = new class_2960("minecraft", "textures/entity/villager/profession/farmer.png");
    private final class_620<MedievalSoldierEntity> farmerModel;

    QuartermasterFarmerHatFeatureRenderer(class_3883<MedievalSoldierEntity, MedievalSoldierModel> context, class_5617.class_5618 rendererContext) {
        super(context);
        this.farmerModel = new class_620(rendererContext.method_32167(class_5602.field_27675));
    }

    public void render(class_4587 matrices, class_4597 vertices, int light, MedievalSoldierEntity soldier, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (soldier.getNpcRole() != NpcRole.QUARTERMASTER) {
            return;
        }
        this.farmerModel.method_2838().method_17138(((MedievalSoldierModel)this.method_17165()).field_3398);
        class_4588 consumer = vertices.getBuffer(class_1921.method_23578((class_2960)FARMER));
        this.farmerModel.method_2838().method_22699(matrices, consumer, light, class_4608.field_21444, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

