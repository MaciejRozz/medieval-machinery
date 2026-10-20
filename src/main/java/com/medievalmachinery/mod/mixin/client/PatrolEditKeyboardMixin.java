/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_309
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
import com.medievalmachinery.mod.client.npc.GeneralBodyguardEditorClient;
import com.medievalmachinery.mod.client.npc.MachineAssignmentEditorClient;
import com.medievalmachinery.mod.client.npc.QuartermasterFarmEditorClient;
import com.medievalmachinery.mod.client.npc.ScoutObservationEditorClient;
import com.medievalmachinery.mod.client.npc.patrol.PatrolRouteEditorClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_309.class})
public abstract class PatrolEditKeyboardMixin {
    @Inject(method={"method_1466"}, at={@At(value="HEAD")}, cancellable=true)
    private void medievalMachinery$finishPatrolEditOnEscape(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (QuartermasterFarmEditorClient.handleKey(key, action) || ScoutObservationEditorClient.handleKey(key, action) || GeneralBodyguardEditorClient.handleKey(key, action) || MachineAssignmentEditorClient.handleKey(key, action) || CastleAreaEditorClient.handleKey(key, action) || RallyPointEditorClient.handleKey(key, action) || TaxChestEditorClient.handleKey(key, action) || SupplyChestEditorClient.handleKey(key, action) || PatrolRouteEditorClient.handleKey(key, action)) {
            ci.cancel();
        }
    }
}

