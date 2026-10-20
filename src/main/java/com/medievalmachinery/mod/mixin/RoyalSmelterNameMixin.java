/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_3720
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.registry.ModBlocks;
import net.minecraft.class_2561;
import net.minecraft.class_3720;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_3720.class})
public abstract class RoyalSmelterNameMixin {
    @Inject(method={"method_17823"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$royalSmelterContainerName(CallbackInfoReturnable<class_2561> cir) {
        class_3720 self = (class_3720)this;
        if (self.method_11010().method_27852(ModBlocks.ROYAL_SMELTER)) {
            cir.setReturnValue((Object)class_2561.method_43471((String)"block.medieval-machinery.royal_smelter"));
        }
    }
}

