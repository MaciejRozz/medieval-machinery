/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1322
 *  net.minecraft.class_1322$class_1323
 *  net.minecraft.class_1324
 *  net.minecraft.class_1657
 *  net.minecraft.class_5134
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.item.spear.SpearItem;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1322;
import net.minecraft.class_1324;
import net.minecraft.class_1657;
import net.minecraft.class_5134;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1657.class})
public abstract class PlayerSpearDamageMixin {
    @Unique
    private static final UUID MEDIEVAL_MACHINERY$CLOSE_SPEAR_PENALTY = UUID.fromString("92536cfa-fbc9-46e2-ac42-a17924f97d61");

    @Inject(method={"method_7324"}, at={@At(value="HEAD")})
    private void medievalMachinery$applyCloseSpearPenalty(class_1297 target, CallbackInfo ci) {
        class_1657 player = (class_1657)this;
        if (!(player.method_6047().method_7909() instanceof SpearItem)) {
            return;
        }
        if (player.method_5858(target) >= 2.25) {
            return;
        }
        class_1324 attribute = player.method_5996(class_5134.field_23721);
        if (attribute == null || attribute.method_6199(MEDIEVAL_MACHINERY$CLOSE_SPEAR_PENALTY) != null) {
            return;
        }
        attribute.method_26835(new class_1322(MEDIEVAL_MACHINERY$CLOSE_SPEAR_PENALTY, "Medieval Machinery spear close-range penalty", -0.25, class_1322.class_1323.field_6331));
    }

    @Inject(method={"method_7324"}, at={@At(value="RETURN")})
    private void medievalMachinery$removeCloseSpearPenalty(class_1297 target, CallbackInfo ci) {
        class_1657 player = (class_1657)this;
        class_1324 attribute = player.method_5996(class_5134.field_23721);
        if (attribute != null) {
            attribute.method_6200(MEDIEVAL_MACHINERY$CLOSE_SPEAR_PENALTY);
        }
    }

    @Redirect(method={"method_7324"}, at=@At(value="INVOKE", target="Lnet/minecraft/class_1657;method_7263()V"))
    private void medievalMachinery$suppressSpearSweepParticles(class_1657 player) {
        if (!(player.method_6047().method_7909() instanceof SpearItem)) {
            player.method_7263();
        }
    }
}

