/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1297
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

import com.medievalmachinery.mod.client.npc.MedievalArmorGeoModel;
import com.medievalmachinery.mod.client.npc.MedievalArmorPoseSync;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import java.util.Map;
import java.util.WeakHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
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
public final class MedievalArmorGeoRenderer
extends GeoArmorRenderer<MedievalArmorItem> {
    private GeoBone cape;
    private GeoBone rightArmArmor;
    private GeoBone leftArmArmor;
    private GeoBone belt;
    private GeoBone capeMid;
    private GeoBone capeLow;
    private GeoBone leftLegCloth;
    private GeoBone rightLegCloth;
    private GeoBone frontCape;
    private GeoBone chestEmblem;
    private GeoBone capeEmblem;
    private final Map<class_1297, CapeMotionState> capeMotionStates = new WeakHashMap<class_1297, CapeMotionState>();
    private class_630 posedRightArm;
    private class_630 posedLeftArm;

    public MedievalArmorGeoRenderer() {
        super((GeoModel)new MedievalArmorGeoModel());
    }

    protected void grabRelevantBones(BakedGeoModel bakedModel) {
        super.grabRelevantBones(bakedModel);
        GeoModel model = this.getGeoModel();
        this.cape = model.getBone("armorCape").orElse(null);
        this.rightArmArmor = model.getBone("armorRightArm").orElse(null);
        this.leftArmArmor = model.getBone("armorLeftArm").orElse(null);
        this.belt = model.getBone("armorBelt").orElse(null);
        this.capeMid = model.getBone("capeMid").orElse(null);
        this.capeLow = model.getBone("capeLow").orElse(null);
        this.frontCape = model.getBone("armorFrontCape").orElse(null);
        this.leftLegCloth = model.getBone("armorLeftLegCloth").orElse(null);
        this.rightLegCloth = model.getBone("armorRightLegCloth").orElse(null);
        this.chestEmblem = model.getBone("orderEmblemChest").orElse(null);
        this.capeEmblem = model.getBone("orderEmblemCape").orElse(null);
    }

    protected void applyBoneVisibilityBySlot(class_1304 slot) {
        super.applyBoneVisibilityBySlot(slot);
        boolean chest = slot == class_1304.field_6174;
        boolean legs = slot == class_1304.field_6172;
        this.setBoneVisible(this.belt, legs);
        this.setBoneVisible(this.cape, chest);
        this.setBoneVisible(this.capeMid, chest);
        this.setBoneVisible(this.capeLow, chest);
        this.setBoneVisible(this.frontCape, chest);
        this.setBoneVisible(this.leftLegCloth, chest);
        this.setBoneVisible(this.rightLegCloth, chest);
        this.setBoneVisible(this.chestEmblem, chest);
        this.setBoneVisible(this.capeEmblem, chest);
    }

    protected void applyBaseTransformations(class_572<?> baseModel) {
        super.applyBaseTransformations(baseModel);
        this.posedRightArm = baseModel.field_3401;
        this.posedLeftArm = baseModel.field_27433;
        MedievalArmorPoseSync.matchArm(baseModel.field_3401, this.rightArmArmor, -5.0f);
        MedievalArmorPoseSync.matchArm(baseModel.field_27433, this.leftArmArmor, 5.0f);
        MedievalArmorGeoRenderer.matchLegCloth(baseModel.field_3397, this.leftLegCloth, 2.0f);
        MedievalArmorGeoRenderer.matchLegCloth(baseModel.field_3392, this.rightLegCloth, -2.0f);
        if (this.cape != null) {
            class_630 body = baseModel.field_3391;
            this.cape.updatePosition(body.field_3657, body.field_3656, body.field_3655);
        }
    }

    private static void matchLegCloth(class_630 leg, GeoBone cloth, float xOffset) {
        if (cloth == null) {
            return;
        }
        RenderUtils.matchModelPartRot((class_630)leg, (CoreGeoBone)cloth);
        cloth.updatePosition(leg.field_3657 - xOffset, 12.0f - leg.field_3656, leg.field_3655);
    }

    public void preRender(class_4587 poseStack, MedievalArmorItem animatable, BakedGeoModel model, class_4597 bufferSource, class_4588 buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, (class_1792)animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if (this.posedRightArm != null) {
            MedievalArmorPoseSync.matchArm(this.posedRightArm, this.rightArmArmor, -5.0f);
        }
        if (this.posedLeftArm != null) {
            MedievalArmorPoseSync.matchArm(this.posedLeftArm, this.leftArmArmor, 5.0f);
        }
        boolean mounted = this.currentEntity != null && this.currentEntity.method_5765();
        this.setBoneVisible(this.frontCape, !mounted);
        this.setBoneVisible(this.leftLegCloth, !mounted);
        this.setBoneVisible(this.rightLegCloth, !mounted);
        if (!mounted && this.frontCape != null && this.getLeftLegBone() != null && this.getRightLegBone() != null) {
            float forward = Math.min(this.getLeftLegBone().getRotX(), this.getRightLegBone().getRotX());
            this.frontCape.setRotX(Math.max(0.0f, -forward * 1.15f));
        }
        if (this.cape != null && this.currentEntity != null) {
            class_1297 movementSource = mounted && this.currentEntity.method_5854() != null ? this.currentEntity.method_5854() : this.currentEntity;
            double vx = movementSource.method_18798().field_1352;
            double vz = movementSource.method_18798().field_1350;
            float rawSpeed = (float)Math.min(1.0, Math.sqrt(vx * vx + vz * vz) * 5.5);
            CapeMotionState state = this.capeMotionStates.computeIfAbsent(this.currentEntity, ignored -> new CapeMotionState());
            state.speed += (rawSpeed - state.speed) * 0.13f;
            float phase = ((float)this.currentEntity.field_6012 + partialTick) * 0.62f;
            float walkWave = (float)Math.sin(phase) * state.speed;
            float targetUpper = (float)Math.toRadians(-7.0 - (double)state.speed * 32.0 - (double)walkWave * 6.5);
            float targetMiddle = (float)Math.toRadians(-9.0 - (double)state.speed * 18.0 + (double)walkWave * 5.2);
            float targetLower = (float)Math.toRadians(-13.0 - (double)state.speed * 25.0 - (double)walkWave * 7.0);
            state.upper += (targetUpper - state.upper) * 0.22f;
            state.middle += (targetMiddle - state.middle) * 0.17f;
            state.lower += (targetLower - state.lower) * 0.11f;
            this.cape.setRotX(state.upper);
            if (this.capeMid != null) {
                this.capeMid.setRotX(state.middle);
            }
            if (this.capeLow != null) {
                this.capeLow.setRotX(state.lower);
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    private static final class CapeMotionState {
        float speed;
        float upper = (float)Math.toRadians(-5.0);
        float middle = (float)Math.toRadians(-5.0);
        float lower = (float)Math.toRadians(-7.5);

        private CapeMotionState() {
        }
    }
}

