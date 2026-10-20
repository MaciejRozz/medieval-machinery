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

import com.medievalmachinery.mod.client.garrison.CastleAreaEditorClient;
import com.medievalmachinery.mod.client.garrison.RallyPointEditorClient;
import com.medievalmachinery.mod.client.garrison.SupplyChestEditorClient;
import com.medievalmachinery.mod.client.garrison.TaxChestEditorClient;
import com.medievalmachinery.mod.client.npc.QuartermasterFarmEditorClient;
import com.medievalmachinery.mod.client.npc.ScoutObservationEditorClient;
import com.medievalmachinery.mod.client.npc.patrol.PatrolRouteEditorClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_310.class})
public abstract class PatrolEditUseMixin {
    @Inject(method={"method_1583"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$patrolRouteWorldInput(CallbackInfo ci) {
        class_310 client = (class_310)this;
        if (QuartermasterFarmEditorClient.handleItemUse(client) || ScoutObservationEditorClient.handleItemUse(client) || CastleAreaEditorClient.handleItemUse(client) || RallyPointEditorClient.handleItemUse(client) || TaxChestEditorClient.handleItemUse(client) || SupplyChestEditorClient.handleItemUse(client) || PatrolRouteEditorClient.handleItemUse(client)) {
            ci.cancel();
        }
    }
}

