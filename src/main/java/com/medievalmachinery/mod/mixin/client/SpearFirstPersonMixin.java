/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1268
 *  net.minecraft.class_1306
 *  net.minecraft.class_1799
 *  net.minecraft.class_3532
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_742
 *  net.minecraft.class_759
 *  net.minecraft.class_7833
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin.client;

import com.medievalmachinery.mod.client.compat.CombatModCompat;
import com.medievalmachinery.mod.item.spear.SpearItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1799;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_759;
import net.minecraft.class_7833;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_759.class})
public abstract class SpearFirstPersonMixin {
    @Unique
    private boolean medievalMachinery$renderingSpear;

    @Inject(method={"method_3228"}, at={@At(value="HEAD")})
    private void medievalMachinery$beginSpearRender(class_742 player, float tickDelta, float pitch, class_1268 hand, float swingProgress, class_1799 item, float equipProgress, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
        this.medievalMachinery$renderingSpear = !CombatModCompat.betterCombatLoaded() && item.method_7909() instanceof SpearItem;
    }

    @Inject(method={"method_3228"}, at={@At(value="RETURN")})
    private void medievalMachinery$endSpearRender(class_742 player, float tickDelta, float pitch, class_1268 hand, float swingProgress, class_1799 item, float equipProgress, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
        this.medievalMachinery$renderingSpear = false;
    }

    @Inject(method={"method_3217"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$applySpearThrust(class_4587 matrices, class_1306 arm, float swingProgress, CallbackInfo ci) {
        float thrust;
        float prepare;
        if (!this.medievalMachinery$renderingSpear) {
            return;
        }
        float p = class_3532.method_15363((float)swingProgress, (float)0.0f, (float)1.0f);
        if (p < 0.16f) {
            prepare = SpearFirstPersonMixin.medievalMachinery$smoothStep(p / 0.16f);
            thrust = 0.0f;
        } else if (p < 0.36f) {
            t = SpearFirstPersonMixin.medievalMachinery$smoothStep((p - 0.16f) / 0.2f);
            prepare = 1.0f - t;
            thrust = t;
        } else {
            t = SpearFirstPersonMixin.medievalMachinery$smoothStep((p - 0.36f) / 0.64f);
            prepare = 0.0f;
            thrust = 1.0f - t;
        }
        double handSide = arm == class_1306.field_6183 ? 1.0 : -1.0;
        matrices.method_22904(0.006 * handSide * (double)thrust, -0.006 * (double)thrust, 0.13 * (double)prepare - 1.02 * (double)thrust);
        matrices.method_22907(class_7833.field_40714.rotationDegrees(-20.0f * thrust + 4.0f * prepare));
        ci.cancel();
    }

    @Unique
    private static float medievalMachinery$smoothStep(float value) {
        float t = class_3532.method_15363((float)value, (float)0.0f, (float)1.0f);
        return t * t * (3.0f - 2.0f * t);
    }
}

