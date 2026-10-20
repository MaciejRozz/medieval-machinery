/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_2338
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
package com.medievalmachinery.mod.client.garrison;

import com.medievalmachinery.mod.network.WatchpostNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2338;
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
public final class WatchpostRenameScreen
extends class_437 {
    private final class_2338 watchpostPos;
    private final String currentName;
    private class_342 field;

    public WatchpostRenameScreen(class_2338 watchpostPos, String currentName) {
        super((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.rename_title"));
        this.watchpostPos = watchpostPos.method_10062();
        this.currentName = currentName == null ? "" : currentName;
    }

    protected void method_25426() {
        int panelWidth = 280;
        int left = (this.field_22789 - panelWidth) / 2;
        int top = (this.field_22790 - 116) / 2;
        this.field = new class_342(this.field_22793, left + 18, top + 38, panelWidth - 36, 20, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.castle_name"));
        this.field.method_1880(48);
        this.field.method_1852(this.currentName);
        this.method_37063((class_364)this.field);
        this.method_25395((class_364)this.field);
        this.field.method_25365(true);
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.ok"), b -> this.save()).method_46434(left + 18, top + 74, 112, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.cancel"), b -> this.method_25419()).method_46434(left + panelWidth - 130, top + 74, 112, 20).method_46431());
    }

    private void save() {
        String name;
        String string = name = this.field == null ? "" : this.field.method_1882().trim();
        if (!name.isEmpty()) {
            class_2540 buf = PacketByteBufs.create();
            buf.method_10807(this.watchpostPos);
            buf.method_10788(name, 48);
            ClientPlayNetworking.send((class_2960)WatchpostNetworking.RENAME, (class_2540)buf);
        }
        this.method_25419();
    }

    public boolean method_25404(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 335) {
            this.save();
            return true;
        }
        return super.method_25404(keyCode, scanCode, modifiers);
    }

    public void method_25419() {
        if (this.field_22787 != null) {
            this.field_22787.method_1507(null);
        }
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        int panelWidth = 280;
        int left = (this.field_22789 - panelWidth) / 2;
        int top = (this.field_22790 - 116) / 2;
        context.method_25294(left, top, left + panelWidth, top + 110, -3750202);
        context.method_25294(left, top, left + panelWidth, top + 2, -1);
        context.method_25294(left, top, left + 2, top + 110, -1);
        context.method_25294(left, top + 108, left + panelWidth, top + 110, -11184811);
        context.method_25294(left + panelWidth - 2, top, left + panelWidth, top + 110, -11184811);
        context.method_51439(this.field_22793, this.field_22785, this.field_22789 / 2 - this.field_22793.method_27525((class_5348)this.field_22785) / 2, top + 14, 0x404040, false);
        super.method_25394(context, mouseX, mouseY, delta);
    }

    public boolean method_25421() {
        return false;
    }
}

