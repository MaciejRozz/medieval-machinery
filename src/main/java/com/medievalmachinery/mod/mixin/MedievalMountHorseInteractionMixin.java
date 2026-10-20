/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1277
 *  net.minecraft.class_1297
 *  net.minecraft.class_1496
 *  net.minecraft.class_1657
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1263;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1277;
import net.minecraft.class_1297;
import net.minecraft.class_1496;
import net.minecraft.class_1657;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1496.class})
abstract class MedievalMountHorseInteractionMixin {
    @Shadow
    private class_1277 field_6962;

    MedievalMountHorseInteractionMixin() {
    }

    @Inject(method={"method_6062"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalmachinery$horseStaysMobileWithSoldier(CallbackInfoReturnable<Boolean> cir) {
        class_1496 horse = (class_1496)this;
        for (class_1297 passenger : horse.method_5685()) {
            if (!(passenger instanceof MedievalSoldierEntity)) continue;
            cir.setReturnValue((Object)false);
            return;
        }
    }

    @Inject(method={"method_5992"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalmachinery$openMountedHorseInventory(class_1657 player, class_1268 hand, CallbackInfoReturnable<class_1269> cir) {
        class_1496 horse = (class_1496)this;
        if (horse.method_37908().field_9236 || !horse.method_5752().contains("medieval_machinery_mount")) {
            return;
        }
        boolean authorised = player.method_5667().equals(horse.method_6139());
        boolean occupiedByOwnedSoldier = false;
        for (class_1297 passenger : horse.method_5685()) {
            MedievalSoldierEntity soldier;
            if (!(passenger instanceof MedievalSoldierEntity) || !(soldier = (MedievalSoldierEntity)passenger).isOwnedBy(player)) continue;
            authorised = true;
            occupiedByOwnedSoldier = true;
            break;
        }
        if (!authorised) {
            return;
        }
        if (occupiedByOwnedSoldier || player.method_5715()) {
            player.method_7291(horse, (class_1263)this.field_6962);
            cir.setReturnValue((Object)class_1269.field_21466);
        }
    }
}

