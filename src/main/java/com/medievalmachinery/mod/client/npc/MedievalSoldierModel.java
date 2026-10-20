/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1268
 *  net.minecraft.class_1306
 *  net.minecraft.class_1309
 *  net.minecraft.class_1743
 *  net.minecraft.class_1753
 *  net.minecraft.class_1764
 *  net.minecraft.class_1829
 *  net.minecraft.class_1839
 *  net.minecraft.class_3532
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
import com.medievalmachinery.mod.item.spear.SpearItem;
import com.medievalmachinery.mod.npc.core.NpcRangedVisualState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1743;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1829;
import net.minecraft.class_1839;
import net.minecraft.class_3532;
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
public final class MedievalSoldierModel
extends class_572<MedievalSoldierEntity> {
    public static final class_5601 LAYER = new class_5601(MedievalMachinery.id("soldier"), "main");

    public MedievalSoldierModel(class_630 root) {
        super(root);
    }

    public void setAngles(MedievalSoldierEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        super.method_17087((class_1309)entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        if (entity.method_6047().method_7909() instanceof SpearItem) {
            this.applySpearThrust(entity, animationProgress);
        } else {
            float melee = entity.getMeleeSwingAnimationProgress(animationProgress);
            if (melee >= 0.0f) {
                this.applyMeleeSwing(entity, melee);
            }
        }
        if (entity.method_5765()) {
            this.applyMountedPose(entity);
        } else if (entity.isRangedAttackAnimating() && entity.method_6047().method_7909() instanceof class_1764) {
            this.applyCrossbowPose(entity.getRangedVisualState());
        }
        if (!entity.method_6115()) {
            return;
        }
        class_1839 action = entity.method_6030().method_7976();
        if (action != class_1839.field_8950 && action != class_1839.field_8946) {
            return;
        }
        float bob = class_3532.method_15374((float)(animationProgress * 1.65f)) * 0.08f;
        if (entity.method_6058() == class_1268.field_5810) {
            this.field_27433.field_3654 = -1.35f + bob;
            this.field_27433.field_3675 = 0.18f;
            this.field_27433.field_3674 = -0.08f;
        } else {
            this.field_3401.field_3654 = -1.35f + bob;
            this.field_3401.field_3675 = -0.18f;
            this.field_3401.field_3674 = 0.08f;
        }
    }

    private void applyMeleeSwing(MedievalSoldierEntity entity, float progress) {
        float strike;
        float windup;
        class_630 arm;
        boolean right = entity.method_6068() == class_1306.field_6183;
        class_630 class_6302 = arm = right ? this.field_3401 : this.field_27433;
        if (progress < 0.32f) {
            windup = progress / 0.32f;
            strike = 0.0f;
        } else {
            windup = 1.0f;
            strike = class_3532.method_15374((float)((progress - 0.32f) / 0.68f * (float)Math.PI));
        }
        boolean axe = entity.method_6047().method_7909() instanceof class_1743;
        boolean sword = entity.method_6047().method_7909() instanceof class_1829;
        if (axe) {
            arm.field_3654 = -1.55f - 0.55f * windup + 2.05f * strike;
            arm.field_3675 = right ? -0.18f : 0.18f;
            arm.field_3674 = right ? -0.14f : 0.14f;
        } else if (sword) {
            arm.field_3654 = -0.72f - 0.35f * windup + 1.55f * strike;
            arm.field_3675 = right ? -0.42f + 0.7f * strike : 0.42f - 0.7f * strike;
            arm.field_3674 = right ? 0.12f : -0.12f;
        } else {
            arm.field_3654 = -0.85f + 1.45f * strike;
            arm.field_3675 = right ? -0.25f : 0.25f;
        }
    }

    private void applySpearThrust(MedievalSoldierEntity entity, float animationProgress) {
        float side;
        float p = entity.getSpearThrustAnimationProgress(animationProgress);
        if (p < 0.0f) {
            p = entity.field_6252 ? class_3532.method_15363((float)entity.field_6251, (float)0.0f, (float)1.0f) : 0.0f;
        }
        p = class_3532.method_15363((float)p, (float)0.0f, (float)1.0f);
        this.field_3391.field_3675 = 0.0f;
        this.field_3401.field_3657 = -5.0f;
        this.field_3401.field_3656 = 2.0f;
        this.field_3401.field_3655 = 0.0f;
        this.field_27433.field_3657 = 5.0f;
        this.field_27433.field_3656 = 2.0f;
        this.field_27433.field_3655 = 0.0f;
        float prepare = 0.0f;
        float thrust = 0.0f;
        if (p < 0.16f) {
            prepare = MedievalSoldierModel.smoothStep(p / 0.16f);
        } else if (p < 0.36f) {
            t = MedievalSoldierModel.smoothStep((p - 0.16f) / 0.2f);
            prepare = 1.0f - t;
            thrust = t;
        } else {
            t = MedievalSoldierModel.smoothStep((p - 0.36f) / 0.64f);
            thrust = 1.0f - t;
        }
        boolean right = entity.method_6068() == class_1306.field_6183;
        class_630 attackArm = right ? this.field_3401 : this.field_27433;
        class_630 supportArm = right ? this.field_27433 : this.field_3401;
        float f = side = right ? 1.0f : -1.0f;
        if (entity.method_5765()) {
            attackArm.field_3654 = -1.5f - 0.05f * prepare + 0.02f * thrust;
            attackArm.field_3675 = -0.07f * side;
            attackArm.field_3674 = 0.025f * side;
            attackArm.field_3655 = 0.55f * prepare - 4.15f * thrust;
            attackArm.field_3656 = 2.0f + 0.22f * thrust;
            supportArm.field_3654 = -0.86f - 0.08f * prepare + 0.05f * thrust;
            supportArm.field_3675 = 0.28f * side;
            supportArm.field_3674 = -0.035f * side;
            supportArm.field_3655 = -0.55f * thrust;
            return;
        }
        attackArm.field_3654 = -1.5f - 0.06f * prepare + 0.02f * thrust;
        attackArm.field_3675 = -0.045f * side;
        attackArm.field_3674 = 0.01f * side;
        attackArm.field_3655 = 0.65f * prepare - 4.65f * thrust;
        attackArm.field_3656 = 2.0f + 0.34f * thrust;
        supportArm.field_3654 = -0.93f - 0.1f * prepare + 0.07f * thrust;
        supportArm.field_3675 = 0.19f * side;
        supportArm.field_3674 = 0.0f;
        supportArm.field_3655 = 0.2f * prepare - 1.15f * thrust;
    }

    private static float smoothStep(float value) {
        float t = class_3532.method_15363((float)value, (float)0.0f, (float)1.0f);
        return t * t * (3.0f - 2.0f * t);
    }

    private void applyCrossbowPose(NpcRangedVisualState phase) {
        boolean aimed;
        boolean bl = aimed = phase == NpcRangedVisualState.AIM || phase == NpcRangedVisualState.RELEASE;
        if (aimed) {
            this.field_3401.field_3654 = -1.5f + this.field_3398.field_3654 * 0.32f;
            this.field_3401.field_3675 = this.field_3398.field_3675 - 0.16f;
            this.field_3401.field_3674 = 0.0f;
            this.field_27433.field_3654 = -1.44f + this.field_3398.field_3654 * 0.32f;
            this.field_27433.field_3675 = this.field_3398.field_3675 + 0.31f;
            this.field_27433.field_3674 = 0.0f;
        } else {
            this.field_3401.field_3654 = -1.18f + this.field_3398.field_3654 * 0.25f;
            this.field_3401.field_3675 = this.field_3398.field_3675 - 0.34f;
            this.field_3401.field_3674 = 0.0f;
            this.field_27433.field_3654 = -1.1f + this.field_3398.field_3654 * 0.25f;
            this.field_27433.field_3675 = this.field_3398.field_3675 + 0.44f;
            this.field_27433.field_3674 = 0.0f;
        }
    }

    private void applyMountedPose(MedievalSoldierEntity entity) {
        this.field_3392.field_3654 = -1.18f;
        this.field_3397.field_3654 = -1.18f;
        this.field_3392.field_3675 = 0.32f;
        this.field_3397.field_3675 = -0.32f;
        this.field_3392.field_3674 = 0.06f;
        this.field_3397.field_3674 = -0.06f;
        if (!entity.isRangedAttackAnimating()) {
            return;
        }
        float headPitch = this.field_3398.field_3654;
        float headYaw = this.field_3398.field_3675;
        if (entity.method_6047().method_7909() instanceof class_1753) {
            boolean released = entity.getRangedVisualState() == NpcRangedVisualState.RELEASE;
            float drawPitch = released ? -1.54f : -1.49f;
            this.field_3401.field_3654 = drawPitch + headPitch * 0.55f;
            this.field_3401.field_3675 = headYaw - 0.12f;
            this.field_3401.field_3674 = 0.0f;
            this.field_27433.field_3654 = drawPitch + 0.04f + headPitch * 0.55f;
            this.field_27433.field_3675 = headYaw + 0.52f;
            this.field_27433.field_3674 = 0.0f;
        } else if (entity.method_6047().method_7909() instanceof class_1764) {
            boolean aimed = entity.getRangedVisualState() == NpcRangedVisualState.AIM || entity.getRangedVisualState() == NpcRangedVisualState.RELEASE;
            this.field_3401.field_3654 = (aimed ? -1.46f : -1.18f) + headPitch * 0.34f;
            this.field_3401.field_3675 = headYaw + (aimed ? -0.16f : -0.3f);
            this.field_3401.field_3674 = 0.0f;
            this.field_27433.field_3654 = (aimed ? -1.4f : -1.1f) + headPitch * 0.34f;
            this.field_27433.field_3675 = headYaw + (aimed ? 0.3f : 0.42f);
            this.field_27433.field_3674 = 0.0f;
        }
    }

    public static class_5607 getTexturedModelData() {
        class_5609 data = new class_5609();
        class_5610 root = data.method_32111();
        class_5610 head = root.method_32117("head", class_5606.method_32108().method_32101(0, 0).method_32097(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), class_5603.field_27701);
        head.method_32117("nose", class_5606.method_32108().method_32101(24, 0).method_32097(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), class_5603.method_32090((float)0.0f, (float)-2.0f, (float)0.0f));
        root.method_32117("hat", class_5606.method_32108(), class_5603.field_27701);
        root.method_32117("body", class_5606.method_32108().method_32101(16, 20).method_32098(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, class_5605.field_27715), class_5603.field_27701);
        root.method_32117("right_arm", class_5606.method_32108().method_32101(44, 22).method_32098(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, class_5605.field_27715), class_5603.method_32090((float)-5.0f, (float)2.0f, (float)0.0f));
        root.method_32117("left_arm", class_5606.method_32108().method_32101(44, 22).method_32096().method_32098(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, class_5605.field_27715), class_5603.method_32090((float)5.0f, (float)2.0f, (float)0.0f));
        root.method_32117("right_leg", class_5606.method_32108().method_32101(0, 22).method_32098(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, class_5605.field_27715), class_5603.method_32090((float)-1.9f, (float)12.0f, (float)0.0f));
        root.method_32117("left_leg", class_5606.method_32108().method_32101(0, 22).method_32096().method_32098(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, class_5605.field_27715), class_5603.method_32090((float)1.9f, (float)12.0f, (float)0.0f));
        return class_5607.method_32110((class_5609)data, (int)64, (int)64);
    }
}

