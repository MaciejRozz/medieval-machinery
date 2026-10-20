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

import com.medievalmachinery.mod.client.network.ClientSoldierNetworking;
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
public final class SoldierPatrolMenuScreen
extends class_437 {
    private final int soldierEntityId;
    private final int currentOrderOrdinal;
    private int waypointCount;

    public SoldierPatrolMenuScreen(int soldierEntityId, int currentOrderOrdinal, int waypointCount) {
        super((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.patrol.title"));
        this.soldierEntityId = soldierEntityId;
        this.currentOrderOrdinal = currentOrderOrdinal;
        this.waypointCount = Math.max(0, waypointCount);
    }

    protected void method_25426() {
        this.rebuildButtons();
    }

    private void rebuildButtons() {
        this.method_37067();
        int cx = this.field_22789 / 2;
        int top = this.field_22790 / 2 - 48;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.patrol.edit"), b -> {
            ClientSoldierNetworking.startPatrolEditing(this.soldierEntityId);
            if (this.field_22787 != null) {
                this.field_22787.method_1507(null);
            }
        }).method_46434(cx - 104, top, 208, 20).method_46431());
        class_4185 delete = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.patrol.delete"), b -> {
            ClientSoldierNetworking.clearPatrolRoute(this.soldierEntityId);
            this.waypointCount = 0;
            this.rebuildButtons();
        }).method_46434(cx - 104, top + 26, 208, 20).method_46431());
        delete.field_22763 = this.waypointCount > 0;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.back"), b -> this.method_25419()).method_46434(cx - 104, top + 52, 208, 20).method_46431());
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        int cx = this.field_22789 / 2;
        int titleY = this.field_22790 / 2 - 82;
        context.method_27535(this.field_22793, this.field_22785, cx - this.field_22793.method_27525((class_5348)this.field_22785) / 2, titleY, 0xFFFFFF);
        class_5250 count = class_2561.method_43469((String)"screen.medieval-machinery.patrol.waypoints", (Object[])new Object[]{this.waypointCount});
        context.method_27535(this.field_22793, (class_2561)count, cx - this.field_22793.method_27525((class_5348)count) / 2, titleY + 14, 0xD0D0D0);
        super.method_25394(context, mouseX, mouseY, delta);
    }

    public boolean method_25421() {
        return false;
    }
}

