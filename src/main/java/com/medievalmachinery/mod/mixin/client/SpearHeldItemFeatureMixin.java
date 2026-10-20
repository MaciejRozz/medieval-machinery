/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1306
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1799
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_7833
 *  net.minecraft.class_811
 *  net.minecraft.class_989
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin.client;

import com.medievalmachinery.mod.client.compat.CombatModCompat;
import com.medievalmachinery.mod.item.spear.SpearItem;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_7833;
import net.minecraft.class_811;
import net.minecraft.class_989;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_989.class})
public abstract class SpearHeldItemFeatureMixin {
    @Inject(method={"method_4192"}, at={@At(value="INVOKE", target="Lnet/minecraft/class_759;method_3233(Lnet/minecraft/class_1309;Lnet/minecraft/class_1799;Lnet/minecraft/class_811;ZLnet/minecraft/class_4587;Lnet/minecraft/class_4597;I)V")})
    private void medievalMachinery$tipSpearDuringThrust(class_1309 entity, class_1799 stack, class_811 transformationMode, class_1306 arm, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
        MedievalSoldierEntity soldier;
        float p;
        if (!(stack.method_7909() instanceof SpearItem)) {
            return;
        }
        if (transformationMode != class_811.field_4320 && transformationMode != class_811.field_4323) {
            return;
        }
        if (entity instanceof MedievalSoldierEntity) {
            MedievalSoldierEntity soldier2 = (MedievalSoldierEntity)entity;
            float tickDelta = class_310.method_1551().method_1488();
            p = soldier2.getSpearThrustAnimationProgress((float)soldier2.field_6012 + tickDelta);
            if (p < 0.0f) {
                p = soldier2.field_6252 ? class_3532.method_15363((float)soldier2.field_6251, (float)0.0f, (float)1.0f) : 0.0f;
            }
        } else if (entity instanceof class_1657) {
            class_1657 player = (class_1657)entity;
            if (CombatModCompat.betterCombatLoaded()) {
                return;
            }
            p = player.field_6252 ? class_3532.method_15363((float)player.field_6251, (float)0.0f, (float)1.0f) : 0.0f;
        } else {
            p = entity.field_6252 ? class_3532.method_15363((float)entity.field_6251, (float)0.0f, (float)1.0f) : 0.0f;
        }
        float thrust = SpearHeldItemFeatureMixin.medievalMachinery$thrustAmount(p);
        if (thrust <= 0.001f) {
            return;
        }
        if (entity instanceof MedievalSoldierEntity && (soldier = (MedievalSoldierEntity)entity).method_5765()) {
            matrices.method_22904(0.0, 0.02 * (double)thrust, -0.78 * (double)thrust);
            matrices.method_22907(class_7833.field_40714.rotationDegrees(-40.0f * thrust));
        } else {
            matrices.method_22904(0.0, 0.018 * (double)thrust, -0.76 * (double)thrust);
            matrices.method_22907(class_7833.field_40714.rotationDegrees(-42.0f * thrust));
        }
    }

    private static float medievalMachinery$thrustAmount(float p) {
        if ((p = class_3532.method_15363((float)p, (float)0.0f, (float)1.0f)) < 0.16f) {
            return 0.0f;
        }
        if (p < 0.36f) {
            float t = class_3532.method_15363((float)((p - 0.16f) / 0.2f), (float)0.0f, (float)1.0f);
            return t * t * (3.0f - 2.0f * t);
        }
        float t = class_3532.method_15363((float)((p - 0.36f) / 0.64f), (float)0.0f, (float)1.0f);
        t = t * t * (3.0f - 2.0f * t);
        return 1.0f - t;
    }
}

