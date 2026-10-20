/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_310
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin.client;

import com.medievalmachinery.mod.client.control.ClientMachineControlController;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_310.class})
public abstract class MachineUseBlockMixin {
    @Inject(method={"method_1583"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$cancelItemUseWhileFiring(CallbackInfo ci) {
        class_310 client = (class_310)this;
        if (ClientMachineControlController.isAiming() && ClientMachineControlController.getActiveMachine(client) instanceof CatapultEntity) {
            ci.cancel();
        }
    }
}

