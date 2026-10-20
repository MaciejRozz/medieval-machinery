/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_332
 *  net.minecraft.class_465
 */
package com.medievalmachinery.mod.client.screen.machine.cannon;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.client.screen.machine.cannon.CannonGuiRenderUtil;
import com.medievalmachinery.mod.registry.ModItems;
import com.medievalmachinery.mod.screen.machine.cannon.StationaryCannonScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_465;

@Environment(value=EnvType.CLIENT)
public final class StationaryCannonScreen
extends class_465<StationaryCannonScreenHandler> {
    private static final class_2960 BACKGROUND = MedievalMachinery.id("textures/gui/stationary_cannon_inventory.png");
    private static final class_2960 SLOT_FRAME = MedievalMachinery.id("textures/gui/cannon_machine_slot.png");

    public StationaryCannonScreen(StationaryCannonScreenHandler handler, class_1661 inventory, class_2561 title) {
        super((class_1703)handler, inventory, title);
        this.field_2792 = 176;
        this.field_2779 = 190;
        this.field_25267 = 8;
        this.field_25268 = 6;
        this.field_25269 = 8;
        this.field_25270 = 86;
    }

    protected void method_2389(class_332 context, float delta, int mouseX, int mouseY) {
        int x = (this.field_22789 - this.field_2792) / 2;
        int y = (this.field_22790 - this.field_2779) / 2;
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        context.method_25290(BACKGROUND, x, y, 0.0f, 0.0f, this.field_2792, this.field_2779, 256, 256);
        StationaryCannonScreen.drawScaledMachineItem(context, ModItems.STATIONARY_CANNON.method_7854(), x + 48, y + 12, 5.15f);
        context.method_25290(SLOT_FRAME, x + 14, y + 28, 0.0f, 0.0f, 18, 18, 18, 18);
        context.method_25290(SLOT_FRAME, x + 144, y + 56, 0.0f, 0.0f, 18, 18, 18, 18);
        CannonGuiRenderUtil.drawGhostItem(context, ModItems.STONE_CANNONBALL.method_7854(), x + 15, y + 29, ((StationaryCannonScreenHandler)this.field_2797).method_7611(1).method_7681());
        CannonGuiRenderUtil.drawGhostItem(context, class_1802.field_8054.method_7854(), x + 145, y + 57, ((StationaryCannonScreenHandler)this.field_2797).method_7611(0).method_7681());
    }

    private static void drawScaledMachineItem(class_332 context, class_1799 stack, int x, int y, float scale) {
        context.method_51448().method_22903();
        context.method_51448().method_22904((double)x, (double)y, 40.0);
        context.method_51448().method_22905(scale, scale, 1.0f);
        context.method_51427(stack, 0, 0);
        context.method_51448().method_22909();
    }

    protected void method_2388(class_332 context, int mouseX, int mouseY) {
        context.method_51439(this.field_22793, this.field_22785, this.field_25267, this.field_25268, 0x404040, false);
        context.method_51439(this.field_22793, this.field_29347, this.field_25269, this.field_25270, 0x404040, false);
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        super.method_25394(context, mouseX, mouseY, delta);
        this.method_2380(context, mouseX, mouseY);
    }
}

