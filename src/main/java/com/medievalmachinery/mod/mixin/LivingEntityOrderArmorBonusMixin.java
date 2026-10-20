/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1282
 *  net.minecraft.class_1309
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.item.OrderArmorSetBonusService;
import net.minecraft.class_1282;
import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1309.class})
public abstract class LivingEntityOrderArmorBonusMixin {
    @ModifyVariable(method={"method_5643"}, at=@At(value="HEAD"), argsOnly=true, ordinal=0)
    private float medievalmachinery$quartzProjectileReduction(float amount, class_1282 source) {
        return OrderArmorSetBonusService.modifyIncomingDamage((class_1309)this, source, amount);
    }

    @Inject(method={"method_5643"}, at={@At(value="RETURN")})
    private void medievalmachinery$rememberDamage(class_1282 source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            OrderArmorSetBonusService.markDamaged((class_1309)this);
        }
    }
}

