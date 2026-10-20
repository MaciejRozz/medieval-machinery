/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.network.ClientSoldierCommandNetworking;
import com.medievalmachinery.mod.client.npc.GeneralBodyguardEditorClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5250;
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class GeneralBodyguardManageScreen
extends class_437 {
    private final class_437 parent;
    private final int generalEntityId;

    public GeneralBodyguardManageScreen(class_437 parent, int generalEntityId) {
        super((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.general_bodyguard.manage_title"));
        this.parent = parent;
        this.generalEntityId = generalEntityId;
    }

    protected void method_25426() {
        int panelWidth = 340;
        int left = (this.field_22789 - panelWidth) / 2;
        int top = (this.field_22790 - 154) / 2;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.general_bodyguard.fill_open"), button -> {
            GeneralBodyguardEditorClient.start(this.generalEntityId);
            if (this.field_22787 != null) {
                this.field_22787.method_1507(null);
            }
        }).method_46434(left + 30, top + 64, panelWidth - 60, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.general_bodyguard.clear"), button -> ClientSoldierCommandNetworking.clearGeneralBodyguards(this.generalEntityId)).method_46434(left + 30, top + 90, panelWidth - 60, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.back"), button -> this.method_25419()).method_46434(left + 80, top + 122, 100, 20).method_46431());
    }

    public void method_25419() {
        if (this.field_22787 != null) {
            this.field_22787.method_1507(this.parent);
        }
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        int panelWidth = 340;
        int left = (this.field_22789 - panelWidth) / 2;
        int top = (this.field_22790 - 154) / 2;
        context.method_25294(left, top, left + panelWidth, top + 152, -3750202);
        context.method_25294(left, top, left + panelWidth, top + 2, -1);
        context.method_25294(left, top, left + 2, top + 152, -1);
        context.method_25294(left, top + 150, left + panelWidth, top + 152, -11184811);
        context.method_25294(left + panelWidth - 2, top, left + panelWidth, top + 152, -11184811);
        class_5250 hint = class_2561.method_43471((String)"screen.medieval-machinery.general_bodyguard.manage_hint");
        context.method_51439(this.field_22793, this.field_22785, this.field_22789 / 2 - this.field_22793.method_27525((class_5348)this.field_22785) / 2, top + 14, 0x202020, false);
        context.method_51439(this.field_22793, (class_2561)hint, this.field_22789 / 2 - this.field_22793.method_27525((class_5348)hint) / 2, top + 38, 0x303030, false);
        super.method_25394(context, mouseX, mouseY, delta);
    }

    public boolean method_25421() {
        return false;
    }
}

