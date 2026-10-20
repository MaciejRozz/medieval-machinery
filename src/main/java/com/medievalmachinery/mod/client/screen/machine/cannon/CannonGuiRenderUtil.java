/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1799
 *  net.minecraft.class_332
 */
package com.medievalmachinery.mod.client.screen.machine.cannon;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_332;

@Environment(value=EnvType.CLIENT)
final class CannonGuiRenderUtil {
    private CannonGuiRenderUtil() {
    }

    static void drawGhostItem(class_332 context, class_1799 stack, int x, int y, boolean occupied) {
        if (occupied) {
            return;
        }
        context.method_51448().method_22903();
        context.method_51448().method_22904(0.0, 0.0, 100.0);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)0.62f);
        context.method_51427(stack, x, y);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        context.method_51448().method_22904(0.0, 0.0, 250.0);
        context.method_25294(x, y, x + 16, y + 16, 1490605777);
        RenderSystem.disableBlend();
        context.method_51448().method_22909();
    }
}

