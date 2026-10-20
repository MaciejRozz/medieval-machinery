/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1308
 *  net.minecraft.class_1309
 *  net.minecraft.class_1548
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1548;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1308.class})
public abstract class CreeperSoldierRetaliationMixin {
    @Inject(method={"method_5980"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalmachinery$neverTargetSoldiers(class_1309 target, CallbackInfo ci) {
        class_1308 self = (class_1308)this;
        if (self instanceof class_1548 && target instanceof MedievalSoldierEntity) {
            ci.cancel();
        }
    }
}

