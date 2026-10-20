/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1304
 *  net.minecraft.class_1792
 *  net.minecraft.class_4587
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597
 *  net.minecraft.class_572
 *  net.minecraft.class_630
 *  software.bernie.geckolib.cache.object.BakedGeoModel
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.core.animatable.model.CoreGeoBone
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoArmorRenderer
 *  software.bernie.geckolib.util.RenderUtils
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.npc.MedievalArmorPoseSync;
import com.medievalmachinery.mod.client.npc.MedievalGarmentGeoContext;
import com.medievalmachinery.mod.client.npc.MedievalGarmentGeoModel;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1304;
import net.minecraft.class_1792;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_572;
import net.minecraft.class_630;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.RenderUtils;

@Environment(value=EnvType.CLIENT)
public final class MedievalGarmentGeoRenderer
extends GeoArmorRenderer<MedievalArmorItem> {
    private GeoBone torso;
    private GeoBone belt;
    private GeoBone buckle;
    private GeoBone rightSleeve;
    private GeoBone leftSleeve;
    private GeoBone leftLeg;
    private GeoBone rightLeg;
    private GeoBone leftBoot;
    private GeoBone rightBoot;
    private GeoBone leftCloth;
    private GeoBone rightCloth;
    private GeoBone leftClothLow;
    private GeoBone rightClothLow;
    private GeoBone sideLeft;
    private GeoBone sideRight;
    private GeoBone sideLeftMid;
    private GeoBone sideRightMid;
    private GeoBone sideLeftLow;
    private GeoBone sideRightLow;
    private GeoBone backCloth;
    private GeoBone chestEmblem;
    private GeoBone backEmblem;
    private float leftLegPitch;
    private float rightLegPitch;
    private class_630 posedRightArm;
    private class_630 posedLeftArm;

    public MedievalGarmentGeoRenderer() {
        super((GeoModel)new MedievalGarmentGeoModel());
    }

    protected void grabRelevantBones(BakedGeoModel bakedModel) {
        super.grabRelevantBones(bakedModel);
        GeoModel model = this.getGeoModel();
        this.torso = model.getBone("armorBody").orElse(null);
        this.belt = model.getBone("garmentBelt").orElse(null);
        this.buckle = model.getBone("garmentBuckle").orElse(null);
        this.rightSleeve = model.getBone("armorRightArm").orElse(null);
        this.leftSleeve = model.getBone("armorLeftArm").orElse(null);
        this.leftLeg = model.getBone("armorLeftLeg").orElse(null);
        this.rightLeg = model.getBone("armorRightLeg").orElse(null);
        this.leftBoot = model.getBone("armorLeftBoot").orElse(null);
        this.rightBoot = model.getBone("armorRightBoot").orElse(null);
        this.leftCloth = model.getBone("armorLeftLegCloth").orElse(null);
        this.rightCloth = model.getBone("armorRightLegCloth").orElse(null);
        this.leftClothLow = model.getBone("leftClothLow").orElse(null);
        this.rightClothLow = model.getBone("rightClothLow").orElse(null);
        this.sideLeft = model.getBone("garmentSideLeft").orElse(null);
        this.sideRight = model.getBone("garmentSideRight").orElse(null);
        this.sideLeftMid = model.getBone("garmentSideLeftMid").orElse(null);
        this.sideRightMid = model.getBone("garmentSideRightMid").orElse(null);
        this.sideLeftLow = model.getBone("garmentSideLeftLow").orElse(null);
        this.sideRightLow = model.getBone("garmentSideRightLow").orElse(null);
        this.backCloth = model.getBone("armorCape").orElse(null);
        this.chestEmblem = model.getBone("orderEmblemChest").orElse(null);
        this.backEmblem = model.getBone("orderEmblemBack").orElse(null);
    }

    protected void applyBoneVisibilityBySlot(class_1304 slot) {
        super.applyBoneVisibilityBySlot(slot);
        MedievalGarmentGeoContext.State state = MedievalGarmentGeoContext.current();
        this.setBoneVisible(this.torso, state.torso());
        this.setBoneVisible(this.belt, state.torso());
        this.setBoneVisible(this.buckle, state.torso());
        this.setBoneVisible(this.chestEmblem, state.torso());
        this.setBoneVisible(this.backEmblem, state.torso());
        this.setBoneVisible(this.rightSleeve, state.torso());
        this.setBoneVisible(this.leftSleeve, state.torso());
        this.setBoneVisible(this.leftLeg, state.legs());
        this.setBoneVisible(this.rightLeg, state.legs());
        this.setBoneVisible(this.leftCloth, state.legs());
        this.setBoneVisible(this.rightCloth, state.legs());
        this.setBoneVisible(this.leftClothLow, state.legs());
        this.setBoneVisible(this.rightClothLow, state.legs());
        this.setBoneVisible(this.sideLeft, state.legs());
        this.setBoneVisible(this.sideRight, state.legs());
        this.setBoneVisible(this.sideLeftMid, state.legs());
        this.setBoneVisible(this.sideRightMid, state.legs());
        this.setBoneVisible(this.sideLeftLow, state.legs());
        this.setBoneVisible(this.sideRightLow, state.legs());
        this.setBoneVisible(this.backCloth, state.legs());
        this.setBoneVisible(this.leftBoot, state.feet());
        this.setBoneVisible(this.rightBoot, state.feet());
    }

    protected void applyBaseTransformations(class_572<?> baseModel) {
        super.applyBaseTransformations(baseModel);
        this.posedRightArm = baseModel.field_3401;
        this.posedLeftArm = baseModel.field_27433;
        MedievalArmorPoseSync.matchArm(baseModel.field_3401, this.rightSleeve, -5.0f);
        MedievalArmorPoseSync.matchArm(baseModel.field_27433, this.leftSleeve, 5.0f);
        MedievalGarmentGeoRenderer.matchLeg(baseModel.field_3397, this.leftLeg, 1.9f);
        MedievalGarmentGeoRenderer.matchLeg(baseModel.field_3392, this.rightLeg, -1.9f);
        MedievalGarmentGeoRenderer.matchLeg(baseModel.field_3397, this.leftBoot, 1.9f);
        MedievalGarmentGeoRenderer.matchLeg(baseModel.field_3392, this.rightBoot, -1.9f);
        this.leftLegPitch = baseModel.field_3397.field_3654;
        this.rightLegPitch = baseModel.field_3392.field_3654;
    }

    private static void matchLeg(class_630 leg, GeoBone bone, float xOffset) {
        if (bone == null) {
            return;
        }
        RenderUtils.matchModelPartRot((class_630)leg, (CoreGeoBone)bone);
        bone.updatePosition(leg.field_3657 - xOffset, 12.0f - leg.field_3656, leg.field_3655);
    }

    private static float coatTailPitch(float legPitch) {
        return legPitch < 0.0f ? legPitch * 0.98f : legPitch * 0.82f;
    }

    public void preRender(class_4587 poseStack, MedievalArmorItem animatable, BakedGeoModel model, class_4597 bufferSource, class_4588 buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, (class_1792)animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if (this.posedRightArm != null) {
            MedievalArmorPoseSync.matchArm(this.posedRightArm, this.rightSleeve, -5.0f);
        }
        if (this.posedLeftArm != null) {
            MedievalArmorPoseSync.matchArm(this.posedLeftArm, this.leftSleeve, 5.0f);
        }
        float leftPitch = MedievalGarmentGeoRenderer.coatTailPitch(this.leftLegPitch);
        float rightPitch = MedievalGarmentGeoRenderer.coatTailPitch(this.rightLegPitch);
        boolean mounted = this.currentEntity != null && this.currentEntity.method_5765();
        MedievalGarmentGeoContext.State garmentState = MedievalGarmentGeoContext.current();
        this.setBoneVisible(this.leftCloth, garmentState.legs() && !mounted);
        this.setBoneVisible(this.rightCloth, garmentState.legs() && !mounted);
        this.setBoneVisible(this.leftClothLow, garmentState.legs() && !mounted);
        this.setBoneVisible(this.rightClothLow, garmentState.legs() && !mounted);
        this.setBoneVisible(this.sideLeft, garmentState.legs());
        this.setBoneVisible(this.sideRight, garmentState.legs());
        this.setBoneVisible(this.sideLeftMid, garmentState.legs());
        this.setBoneVisible(this.sideRightMid, garmentState.legs());
        this.setBoneVisible(this.sideLeftLow, garmentState.legs());
        this.setBoneVisible(this.sideRightLow, garmentState.legs());
        this.setBoneVisible(this.backCloth, garmentState.legs());
        if (mounted) {
            if (this.sideLeft != null) {
                this.sideLeft.setRotZ(-0.055f);
            }
            if (this.sideRight != null) {
                this.sideRight.setRotZ(0.055f);
            }
            if (this.sideLeftMid != null) {
                this.sideLeftMid.setScaleZ(1.18f);
            }
            if (this.sideRightMid != null) {
                this.sideRightMid.setScaleZ(1.18f);
            }
            if (this.sideLeftLow != null) {
                this.sideLeftLow.setScaleZ(1.28f);
            }
            if (this.sideRightLow != null) {
                this.sideRightLow.setScaleZ(1.28f);
            }
            if (this.backCloth != null) {
                this.backCloth.setRotX(-0.1f);
            }
            return;
        }
        if (this.leftCloth != null) {
            this.leftCloth.setRotX(leftPitch);
            this.leftCloth.setRotZ(-0.018f);
        }
        if (this.rightCloth != null) {
            this.rightCloth.setRotX(rightPitch);
            this.rightCloth.setRotZ(0.018f);
        }
        if (this.leftClothLow != null) {
            this.leftClothLow.setRotX(-0.035f + leftPitch * 0.18f);
            this.leftClothLow.setRotZ(-0.05f);
        }
        if (this.rightClothLow != null) {
            this.rightClothLow.setRotX(-0.035f + rightPitch * 0.18f);
            this.rightClothLow.setRotZ(0.05f);
        }
        float leftOpen = Math.min(1.0f, Math.abs(leftPitch));
        float rightOpen = Math.min(1.0f, Math.abs(rightPitch));
        if (this.sideLeft != null) {
            this.sideLeft.setRotX(0.0f);
            this.sideLeft.setScaleZ(1.0f);
        }
        if (this.sideRight != null) {
            this.sideRight.setRotX(0.0f);
            this.sideRight.setScaleZ(1.0f);
        }
        if (this.sideLeftMid != null) {
            this.sideLeftMid.setRotX(0.0f);
            this.sideLeftMid.setScaleZ(1.0f + leftOpen * 0.22f);
        }
        if (this.sideRightMid != null) {
            this.sideRightMid.setRotX(0.0f);
            this.sideRightMid.setScaleZ(1.0f + rightOpen * 0.22f);
        }
        if (this.sideLeftLow != null) {
            this.sideLeftLow.setRotX(0.0f);
            this.sideLeftLow.setScaleZ(1.0f + leftOpen * 0.48f);
        }
        if (this.sideRightLow != null) {
            this.sideRightLow.setRotX(0.0f);
            this.sideRightLow.setScaleZ(1.0f + rightOpen * 0.48f);
        }
        if (this.backCloth != null) {
            float current;
            float backward = Math.max(0.0f, Math.max(this.leftLegPitch, this.rightLegPitch));
            float target = -backward * 1.34f;
            float factor = target < (current = this.backCloth.getRotX()) ? 0.96f : 0.2f;
            this.backCloth.setRotX(current + (target - current) * factor);
        }
    }
}

