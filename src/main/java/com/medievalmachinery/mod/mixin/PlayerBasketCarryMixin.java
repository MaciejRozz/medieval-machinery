/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.storage.WickerBasketCarrier;
import com.medievalmachinery.mod.storage.WickerBasketCarryData;
import net.minecraft.class_1657;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1657.class})
public abstract class PlayerBasketCarryMixin
implements WickerBasketCarrier {
    @Unique
    private static final String CARRY_NBT_KEY = "MedievalMachineryCarriedBasket";
    @Unique
    @Nullable
    private WickerBasketCarryData medievalMachinery$carriedBasket;

    @Override
    public WickerBasketCarryData medievalMachinery$getCarriedBasket() {
        return this.medievalMachinery$carriedBasket;
    }

    @Override
    public void medievalMachinery$setCarriedBasket(@Nullable WickerBasketCarryData data) {
        this.medievalMachinery$carriedBasket = data;
    }

    @Inject(method={"method_5652"}, at={@At(value="TAIL")})
    private void medievalMachinery$writeCarriedBasket(class_2487 nbt, CallbackInfo ci) {
        if (this.medievalMachinery$carriedBasket != null) {
            nbt.method_10566(CARRY_NBT_KEY, (class_2520)this.medievalMachinery$carriedBasket.toNbt());
        }
    }

    @Inject(method={"method_5749"}, at={@At(value="TAIL")})
    private void medievalMachinery$readCarriedBasket(class_2487 nbt, CallbackInfo ci) {
        this.medievalMachinery$carriedBasket = nbt.method_10573(CARRY_NBT_KEY, 10) ? WickerBasketCarryData.fromNbt(nbt.method_10562(CARRY_NBT_KEY)) : null;
    }
}

