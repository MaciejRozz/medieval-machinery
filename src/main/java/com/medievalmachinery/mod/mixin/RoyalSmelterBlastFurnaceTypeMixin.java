/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2591
 *  net.minecraft.class_2680
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.registry.ModBlocks;
import net.minecraft.class_2591;
import net.minecraft.class_2680;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_2591.class})
public abstract class RoyalSmelterBlastFurnaceTypeMixin {
    @Inject(method={"method_20526"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$royalSmelterSupports(class_2680 state, CallbackInfoReturnable<Boolean> cir) {
        if (this == class_2591.field_16415 && state.method_27852(ModBlocks.ROYAL_SMELTER)) {
            cir.setReturnValue((Object)true);
        }
    }
}

