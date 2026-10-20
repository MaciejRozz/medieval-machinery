/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1665
 *  net.minecraft.class_3966
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.item.OrderArmorSetBonusService;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1665;
import net.minecraft.class_3966;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1665.class})
public abstract class PersistentProjectileOrderArmorBonusMixin {
    @Unique
    private double medievalmachinery$originalDamage = Double.NaN;

    @Inject(method={"method_7454"}, at={@At(value="HEAD")})
    private void medievalmachinery$lapisRangedBonusStart(class_3966 hit, CallbackInfo ci) {
        class_1665 self = (class_1665)this;
        class_1297 owner = self.method_24921();
        if (!(owner instanceof class_1309)) {
            return;
        }
        class_1309 living = (class_1309)owner;
        double multiplier = OrderArmorSetBonusService.rangedDamageMultiplier(living);
        if (multiplier == 1.0) {
            return;
        }
        this.medievalmachinery$originalDamage = self.method_7448();
        self.method_7438(this.medievalmachinery$originalDamage * multiplier);
    }

    @Inject(method={"method_7454"}, at={@At(value="RETURN")})
    private void medievalmachinery$lapisRangedBonusEnd(class_3966 hit, CallbackInfo ci) {
        if (Double.isNaN(this.medievalmachinery$originalDamage)) {
            return;
        }
        class_1665 self = (class_1665)this;
        self.method_7438(this.medievalmachinery$originalDamage);
        this.medievalmachinery$originalDamage = Double.NaN;
    }
}

