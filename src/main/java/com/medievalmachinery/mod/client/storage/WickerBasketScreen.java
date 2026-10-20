/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_332
 *  net.minecraft.class_465
 */
package com.medievalmachinery.mod.client.storage;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.screen.storage.WickerBasketScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_465;

@Environment(value=EnvType.CLIENT)
public final class WickerBasketScreen
extends class_465<WickerBasketScreenHandler> {
    private static final class_2960 TEXTURE = MedievalMachinery.id("textures/gui/wicker_basket.png");

    public WickerBasketScreen(WickerBasketScreenHandler handler, class_1661 inventory, class_2561 title) {
        super((class_1703)handler, inventory, title);
        this.field_2792 = 176;
        this.field_2779 = 166;
        this.field_25270 = 72;
    }

    protected void method_2389(class_332 context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        context.method_25290(TEXTURE, this.field_2776, this.field_2800, 0.0f, 0.0f, this.field_2792, this.field_2779, this.field_2792, this.field_2779);
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        super.method_25394(context, mouseX, mouseY, delta);
        this.method_2380(context, mouseX, mouseY);
    }
}

