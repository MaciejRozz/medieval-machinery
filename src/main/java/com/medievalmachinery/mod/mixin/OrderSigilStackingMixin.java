/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.item.OrderSigilItem;
import net.minecraft.class_1799;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1799.class})
public abstract class OrderSigilStackingMixin {
    @Inject(method={"method_31577"}, at={@At(value="HEAD")}, cancellable=true)
    private static void medievalMachinery$allowStampedSigilStacking(class_1799 first, class_1799 second, CallbackInfoReturnable<Boolean> cir) {
        if (OrderSigilItem.canStackStampedTogether(first, second)) {
            cir.setReturnValue((Object)true);
        }
    }
}

