/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_638
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin.client;

import com.medievalmachinery.mod.block.garrison.WatchpostBlock;
import com.medievalmachinery.mod.block.garrison.WatchpostPart;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotinePartBlock;
import com.medievalmachinery.mod.registry.ModBlocks;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_638;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_638.class})
public abstract class ClientWorldMixin {
    private static boolean medievalMachinery$mirroringWatchpostBreaking;
    private static final Map<Integer, class_2338> medievalMachinery$watchpostMirrorByBreaker;

    private static int medievalMachinery$watchpostMirrorEntityId(int entityId) {
        return entityId ^ 0x40000000;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Inject(method={"method_8517"}, at={@At(value="TAIL")})
    private void medievalMachinery$showRoyalGuillotineBreakingAnimation(int entityId, class_2338 pos, int progress, CallbackInfo ci) {
        class_2338 otherPos;
        class_2338 previousMirror;
        class_638 world = (class_638)this;
        if (!medievalMachinery$mirroringWatchpostBreaking && progress < 0 && (previousMirror = medievalMachinery$watchpostMirrorByBreaker.remove(entityId)) != null) {
            medievalMachinery$mirroringWatchpostBreaking = true;
            try {
                world.method_8517(ClientWorldMixin.medievalMachinery$watchpostMirrorEntityId(entityId), previousMirror, progress);
            }
            finally {
                medievalMachinery$mirroringWatchpostBreaking = false;
            }
        }
        class_2680 state = world.method_8320(pos);
        if (!medievalMachinery$mirroringWatchpostBreaking && state.method_26204() instanceof WatchpostBlock && world.method_8320(otherPos = state.method_11654(WatchpostBlock.PART) == WatchpostPart.MAIN ? pos.method_10093(WatchpostBlock.getExtensionDirection((class_2350)state.method_11654((class_2769)WatchpostBlock.FACING))) : WatchpostBlock.getMainPos(state, pos)).method_26204() instanceof WatchpostBlock) {
            medievalMachinery$watchpostMirrorByBreaker.put(entityId, otherPos.method_10062());
            medievalMachinery$mirroringWatchpostBreaking = true;
            try {
                world.method_8517(ClientWorldMixin.medievalMachinery$watchpostMirrorEntityId(entityId), otherPos, progress);
            }
            finally {
                medievalMachinery$mirroringWatchpostBreaking = false;
            }
        }
        if (!state.method_27852(ModBlocks.GUILLOTINE_PART)) {
            return;
        }
        class_2338 controllerPos = GuillotinePartBlock.getControllerPos(state, pos);
        if (!world.method_8320(controllerPos).method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
            return;
        }
        world.method_8517(entityId, controllerPos, progress);
    }

    static {
        medievalMachinery$watchpostMirrorByBreaker = new HashMap<Integer, class_2338>();
    }
}

