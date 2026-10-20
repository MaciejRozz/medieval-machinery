/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_342
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.command;

import com.medievalmachinery.mod.client.command.ClientCommandGroupStatus;
import com.medievalmachinery.mod.client.command.CommandGroupPreferences;
import com.medievalmachinery.mod.client.network.ClientSoldierCommandNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class CommandGroupRenameScreen
extends class_437 {
    private final class_437 parent;
    private final int group;
    private class_342 field;

    public CommandGroupRenameScreen(class_437 parent, int group) {
        super((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.rename"));
        this.parent = parent;
        this.group = group;
    }

    protected void method_25426() {
        int panelWidth = 250;
        int left = (this.field_22789 - panelWidth) / 2;
        int top = (this.field_22790 - 108) / 2;
        this.field = new class_342(this.field_22793, left + 18, top + 36, panelWidth - 36, 20, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.name"));
        this.field.method_1880(24);
        this.field.method_1852(CommandGroupPreferences.customName(this.group));
        this.method_37063((class_364)this.field);
        this.method_25395((class_364)this.field);
        this.field.method_25365(true);
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.ok"), b -> this.saveAndClose()).method_46434(left + 18, top + 70, 100, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.cancel"), b -> this.method_25419()).method_46434(left + panelWidth - 118, top + 70, 100, 20).method_46431());
    }

    private void saveAndClose() {
        String name;
        String string = name = this.field == null ? "" : this.field.method_1882().trim();
        if (!name.isEmpty()) {
            if (ClientCommandGroupStatus.group(this.group) == null) {
                ClientSoldierCommandNetworking.createGroup(this.group, name);
            } else {
                ClientSoldierCommandNetworking.renameGroup(this.group, name);
            }
        }
        this.method_25419();
    }

    public boolean method_25404(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 335) {
            this.saveAndClose();
            return true;
        }
        return super.method_25404(keyCode, scanCode, modifiers);
    }

    public void method_25419() {
        if (this.field_22787 != null) {
            this.field_22787.method_1507(this.parent);
            ClientCommandGroupStatus.request();
        }
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        int panelWidth = 250;
        int left = (this.field_22789 - panelWidth) / 2;
        int top = (this.field_22790 - 108) / 2;
        context.method_25294(left, top, left + panelWidth, top + 104, -574175546);
        context.method_51439(this.field_22793, this.field_22785, this.field_22789 / 2 - this.field_22793.method_27525((class_5348)this.field_22785) / 2, top + 14, 0x404040, false);
        super.method_25394(context, mouseX, mouseY, delta);
    }

    public boolean method_25421() {
        return false;
    }
}

