/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_332
 *  net.minecraft.class_342
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.network.SoldierNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class SoldierRenameScreen
extends class_437 {
    private final int soldierEntityId;
    private final String originalName;
    private class_342 nameField;
    private boolean finishing;

    public SoldierRenameScreen(int soldierEntityId, String originalName) {
        super((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier.rename_title"));
        this.soldierEntityId = soldierEntityId;
        this.originalName = originalName == null ? "" : originalName;
    }

    protected void method_25426() {
        int panelWidth = 260;
        int left = (this.field_22789 - panelWidth) / 2;
        int top = (this.field_22790 - 116) / 2;
        this.nameField = new class_342(this.field_22793, left + 20, top + 40, panelWidth - 40, 20, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier.name"));
        this.nameField.method_1880(32);
        this.nameField.method_1852(this.originalName);
        this.method_37063((class_364)this.nameField);
        this.method_25395((class_364)this.nameField);
        this.nameField.method_25365(true);
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.ok"), b -> this.finish(true)).method_46434(left + 20, top + 72, 104, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.cancel"), b -> this.finish(false)).method_46434(left + panelWidth - 124, top + 72, 104, 20).method_46431());
    }

    private void finish(boolean save) {
        if (this.finishing) {
            return;
        }
        this.finishing = true;
        if (save) {
            class_2540 rename = PacketByteBufs.create();
            rename.method_10804(this.soldierEntityId);
            rename.method_10788(this.nameField == null ? "" : this.nameField.method_1882(), 32);
            ClientPlayNetworking.send((class_2960)SoldierNetworking.RENAME, (class_2540)rename);
        }
        class_2540 reopen = PacketByteBufs.create();
        reopen.method_10804(this.soldierEntityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.OPEN_INVENTORY, (class_2540)reopen);
        if (this.field_22787 != null) {
            this.field_22787.method_1507(null);
        }
    }

    public boolean method_25404(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 335) {
            this.finish(true);
            return true;
        }
        if (keyCode == 256) {
            this.finish(false);
            return true;
        }
        return super.method_25404(keyCode, scanCode, modifiers);
    }

    public void method_25419() {
        this.finish(false);
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        int panelWidth = 260;
        int left = (this.field_22789 - panelWidth) / 2;
        int top = (this.field_22790 - 116) / 2;
        context.method_25294(left, top, left + panelWidth, top + 112, -3750202);
        context.method_25294(left, top, left + panelWidth, top + 2, -1);
        context.method_25294(left, top, left + 2, top + 112, -1);
        context.method_25294(left, top + 110, left + panelWidth, top + 112, -11184811);
        context.method_25294(left + panelWidth - 2, top, left + panelWidth, top + 112, -11184811);
        super.method_25394(context, mouseX, mouseY, delta);
        int titleX = this.field_22789 / 2 - this.field_22793.method_27525((class_5348)this.field_22785) / 2;
        context.method_51439(this.field_22793, this.field_22785, titleX, top + 14, 0x303030, false);
    }

    public boolean method_25421() {
        return false;
    }
}

