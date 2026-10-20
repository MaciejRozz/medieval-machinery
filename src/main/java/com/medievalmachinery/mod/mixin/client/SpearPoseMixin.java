/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1306
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_3532
 *  net.minecraft.class_572
 *  net.minecraft.class_630
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin.client;

import com.medievalmachinery.mod.item.spear.SpearItem;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_3532;
import net.minecraft.class_572;
import net.minecraft.class_630;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_572.class})
public abstract class SpearPoseMixin<T extends class_1309> {
    @Inject(method={"method_17087"}, at={@At(value="TAIL")})
    private void medievalMachinery$spearThrust(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof class_1657 || entity instanceof MedievalSoldierEntity) {
            return;
        }
        if (!(entity.method_6047().method_7909() instanceof SpearItem)) {
            return;
        }
        class_572 model = (class_572)this;
        float p = ((class_1309)entity).field_6252 ? class_3532.method_15363((float)((class_1309)entity).field_6251, (float)0.0f, (float)1.0f) : 0.0f;
        float thrust = p <= 0.5f ? class_3532.method_15374((float)(p / 0.5f * (float)Math.PI * 0.5f)) : class_3532.method_15362((float)((p - 0.5f) / 0.5f * (float)Math.PI * 0.5f));
        float draw = p < 0.18f ? class_3532.method_15374((float)(p / 0.18f * (float)Math.PI)) : 0.0f;
        boolean right = entity.method_6068() == class_1306.field_6183;
        class_630 arm = right ? model.field_3401 : model.field_27433;
        arm.field_3654 = -1.48f + draw * 0.14f;
        arm.field_3675 = right ? -0.04f : 0.04f;
        arm.field_3674 = right ? 0.015f : -0.015f;
        arm.field_3655 = -3.2f * thrust + 0.7f * draw;
        class_630 off = right ? model.field_27433 : model.field_3401;
        off.field_3654 = -0.72f;
        off.field_3675 = right ? 0.26f : -0.26f;
        off.field_3674 = 0.0f;
        off.field_3655 = -1.0f * thrust;
    }
}

