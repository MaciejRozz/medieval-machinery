/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1282
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1309.class})
public abstract class SoldierCombatExperienceMixin {
    @Inject(method={"method_5643"}, at={@At(value="RETURN")})
    private void medievalmachinery$awardSoldierCombatXp(class_1282 source, float amount, CallbackInfoReturnable<Boolean> cir) {
        MedievalSoldierEntity soldier;
        if (!cir.getReturnValueZ()) {
            return;
        }
        class_1309 victim = (class_1309)this;
        class_1297 class_12972 = source.method_5529();
        if (class_12972 instanceof MedievalSoldierEntity && (soldier = (MedievalSoldierEntity)class_12972) != victim) {
            soldier.awardCombatExperience(victim, !victim.method_5805() || victim.method_6032() <= 0.0f);
        }
    }
}

