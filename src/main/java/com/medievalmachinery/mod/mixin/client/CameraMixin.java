/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1297
 *  net.minecraft.class_1922
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_243
 *  net.minecraft.class_2586
 *  net.minecraft.class_2769
 *  net.minecraft.class_4184
 *  net.minecraft.class_742
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin.client;

import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlock;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2586;
import net.minecraft.class_2769;
import net.minecraft.class_4184;
import net.minecraft.class_742;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={class_4184.class})
public abstract class CameraMixin {
    private static final int SEARCH_RADIUS = 3;
    private static final double LIE_CAMERA_Y_OFFSET = -0.8125;
    private static final double LIE_CAMERA_FORWARD_OFFSET = 0.3125;

    @Shadow
    public abstract class_243 method_19326();

    @Shadow
    protected abstract void method_19327(double var1, double var3, double var5);

    @Inject(method={"method_19321"}, at={@At(value="TAIL")})
    private void medievalMachinery$lowerGuillotineCamera(class_1922 area, class_1297 focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (thirdPerson || !(focusedEntity instanceof class_742)) {
            return;
        }
        class_742 player = (class_742)focusedEntity;
        GuillotineBlockEntity guillotine = CameraMixin.findOccupiedGuillotine(player);
        if (guillotine == null) {
            return;
        }
        class_243 current = this.method_19326();
        class_2350 facing = (class_2350)guillotine.method_11010().method_11654((class_2769)GuillotineBlock.FACING);
        this.method_19327(current.field_1352 + (double)facing.method_10148() * 0.3125, current.field_1351 + -0.8125, current.field_1350 + (double)facing.method_10165() * 0.3125);
    }

    private static GuillotineBlockEntity findOccupiedGuillotine(class_742 player) {
        class_2338 center = player.method_24515();
        for (class_2338 checkPos : class_2338.method_10097((class_2338)center.method_10069(-3, -2, -3), (class_2338)center.method_10069(3, 2, 3))) {
            GuillotineBlockEntity guillotine;
            class_2586 class_25862 = player.method_37908().method_8321(checkPos);
            if (!(class_25862 instanceof GuillotineBlockEntity) || !(guillotine = (GuillotineBlockEntity)class_25862).isOccupiedBy(player.method_5667())) continue;
            return guillotine;
        }
        return null;
    }
}

