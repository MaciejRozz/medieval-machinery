/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_332
 *  net.minecraft.class_5250
 */
package com.medievalmachinery.mod.client.control;

import com.medievalmachinery.mod.client.control.ClientMachineControlController;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import com.medievalmachinery.mod.entity.machine.AbstractMedievalMachineEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_5250;

@Environment(value=EnvType.CLIENT)
public final class MachineControlHud {
    private static final int MARKER = -1583441;
    private static final int MUTED_TEXT = -4674927;

    private MachineControlHud() {
    }

    public static void register() {
        HudRenderCallback.EVENT.register(MachineControlHud::render);
    }

    private static void render(class_332 context, float tickDelta) {
        class_310 client = class_310.method_1551();
        if (!ClientMachineControlController.isAiming() || client.field_1724 == null || client.field_1687 == null || client.field_1690.field_1842) {
            return;
        }
        int width = client.method_22683().method_4486();
        int height = client.method_22683().method_4502();
        MachineControlHud.drawReticle(context, width / 2, height / 2);
        AbstractMedievalMachineEntity machine = ClientMachineControlController.getActiveMachine(client);
        if (machine != null) {
            class_5250 durability = class_2561.method_43469((String)"hud.medieval-machinery.machine.durability", (Object[])new Object[]{machine.getHealth(), machine.getMaximumHealth()});
            context.method_27535(client.field_1772, (class_2561)durability, 8, height - 40, -4674927);
            class_5250 controls = class_2561.method_43471((String)(machine instanceof CatapultEntity ? "hud.medieval-machinery.catapult.controls" : "hud.medieval-machinery.cannon.controls"));
            context.method_27535(client.field_1772, (class_2561)controls, 8, height - 28, -4674927);
        }
        class_5250 leave = class_2561.method_43471((String)"hud.medieval-machinery.cannon.leave");
        context.method_27535(client.field_1772, (class_2561)leave, 8, height - 16, -4674927);
    }

    private static void drawReticle(class_332 context, int x, int y) {
        context.method_25294(x - 8, y, x - 2, y + 1, -1583441);
        context.method_25294(x + 3, y, x + 9, y + 1, -1583441);
        context.method_25294(x, y - 8, x + 1, y - 2, -1583441);
        context.method_25294(x, y + 3, x + 1, y + 9, -1583441);
        context.method_25294(x - 1, y - 1, x + 2, y + 2, -872415232);
    }
}

