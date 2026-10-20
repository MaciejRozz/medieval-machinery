/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1308
 *  net.minecraft.class_1352
 *  net.minecraft.class_1355
 *  net.minecraft.class_1400
 *  net.minecraft.class_1937
 *  net.minecraft.class_5134
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.item.OrderArmorSetBonusService;
import com.medievalmachinery.mod.npc.core.NpcTargetPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1308;
import net.minecraft.class_1352;
import net.minecraft.class_1355;
import net.minecraft.class_1400;
import net.minecraft.class_1937;
import net.minecraft.class_5134;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1308.class})
public abstract class MobEntitySoldierTargetMixin {
    @Shadow
    @Final
    protected class_1355 field_6185;

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    private void medievalmachinery$targetSoldiers(class_1299<? extends class_1308> type, class_1937 world, CallbackInfo ci) {
        class_1308 self = (class_1308)this;
        if (NpcTargetPolicy.shouldMonsterMobTargetSoldiers(self)) {
            this.field_6185.method_6277(4, (class_1352)new class_1400(self, MedievalSoldierEntity.class, true, target -> self.method_5858((class_1297)target) <= Math.pow(self.method_26825(class_5134.field_23717) * OrderArmorSetBonusService.aiTargetDistanceFactor(target), 2.0)));
        }
    }
}

