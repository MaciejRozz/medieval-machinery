/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1734
 *  net.minecraft.class_1735
 *  net.minecraft.class_1799
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.item.OrderSigilCrafting;
import com.medievalmachinery.mod.item.WickerBasketItemData;
import com.medievalmachinery.mod.mixin.CraftingResultSlotAccessor;
import net.minecraft.class_1263;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1734;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1735.class})
public abstract class SlotMixin {
    @Shadow
    @Final
    public class_1263 field_7871;
    @Shadow
    @Final
    private int field_7875;

    @Inject(method={"method_7680"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$denyFilledBasketInContainers(class_1799 stack, CallbackInfoReturnable<Boolean> cir) {
        if (!(this.field_7871 instanceof class_1661) && WickerBasketItemData.isFilledBasket(stack)) {
            cir.setReturnValue((Object)false);
        }
    }

    @Inject(method={"method_7674"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$restrictOrderSigilCrafting(class_1657 player, CallbackInfoReturnable<Boolean> cir) {
        if (!(this instanceof class_1734)) {
            return;
        }
        class_1799 result = this.field_7871.method_5438(this.field_7875);
        if (OrderSigilCrafting.isCraftedSigil(result) && !OrderSigilCrafting.canPlayerCraft(player, result)) {
            OrderSigilCrafting.notifyMissingKingdom(player);
            cir.setReturnValue((Object)false);
        }
    }

    @Inject(method={"method_7677"}, at={@At(value="RETURN")})
    private void medievalMachinery$stampCraftedOrderSigil(CallbackInfoReturnable<class_1799> cir) {
        SlotMixin slotMixin = this;
        if (!(slotMixin instanceof CraftingResultSlotAccessor)) {
            return;
        }
        CraftingResultSlotAccessor accessor = (CraftingResultSlotAccessor)((Object)slotMixin);
        class_1799 result = (class_1799)cir.getReturnValue();
        OrderSigilCrafting.stampForPlayer(accessor.medievalMachinery$getCraftingPlayer(), result);
    }
}

