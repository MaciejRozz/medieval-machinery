/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1675
 *  net.minecraft.class_1792
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_2540
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3966
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.medievalmachinery.mod.mixin.client;

import com.medievalmachinery.mod.client.compat.CombatModCompat;
import com.medievalmachinery.mod.item.spear.SpearItem;
import com.medievalmachinery.mod.network.SpearCombatNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1675;
import net.minecraft.class_1792;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3966;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_310.class})
public abstract class SpearAttackMixin {
    @Inject(method={"method_1536"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$extendedSpearAttack(CallbackInfoReturnable<Boolean> cir) {
        if (CombatModCompat.betterCombatLoaded()) {
            return;
        }
        class_310 client = (class_310)this;
        if (client.field_1724 == null || client.field_1687 == null) {
            return;
        }
        class_1792 class_17922 = client.field_1724.method_6047().method_7909();
        if (!(class_17922 instanceof SpearItem)) {
            return;
        }
        SpearItem spear = (SpearItem)class_17922;
        double reach = spear.getReach();
        class_243 start = client.field_1724.method_5836(1.0f);
        class_243 look = client.field_1724.method_5828(1.0f);
        class_243 end = start.method_1019(look.method_1021(reach));
        class_238 box = client.field_1724.method_5829().method_18804(look.method_1021(reach)).method_1014(1.0);
        class_3966 hit = class_1675.method_18075((class_1297)client.field_1724, (class_243)start, (class_243)end, (class_238)box, entity -> !entity.method_7325() && entity.method_5863(), (double)(reach * reach));
        if (hit == null) {
            return;
        }
        class_1297 target = hit.method_17782();
        if (client.field_1724.method_5858(target) <= 9.0) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(target.method_5628());
        ClientPlayNetworking.send((class_2960)SpearCombatNetworking.LONG_REACH_ATTACK, (class_2540)buf);
        client.field_1724.method_6104(class_1268.field_5808);
        cir.setReturnValue((Object)true);
    }
}

