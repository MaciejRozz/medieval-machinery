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
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.network.ClientSoldierNetworking;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class SoldierTransferConfirmScreen
extends class_437 {
    private final UUID requestId;
    private final String sourceName;
    private final String soldierName;
    private final boolean withTroop;
    private final String groupName;
    private final int memberCount;
    private boolean responded;

    public SoldierTransferConfirmScreen(UUID requestId, String sourceName, String soldierName, boolean withTroop, String groupName, int memberCount) {
        super((class_2561)class_2561.method_43471((String)(withTroop ? "screen.medieval-machinery.soldier_transfer.troop_title" : "screen.medieval-machinery.soldier_transfer.title")));
        this.requestId = requestId;
        this.sourceName = sourceName;
        this.soldierName = soldierName;
        this.withTroop = withTroop;
        this.groupName = groupName == null ? "" : groupName;
        this.memberCount = Math.max(0, memberCount);
    }

    protected void method_25426() {
        int y = this.field_22790 / 2 + 30;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.accept"), button -> this.respond(true)).method_46434(this.field_22789 / 2 - 104, y, 100, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.cancel"), button -> this.respond(false)).method_46434(this.field_22789 / 2 + 4, y, 100, 20).method_46431());
    }

    private void respond(boolean accepted) {
        if (this.responded) {
            return;
        }
        this.responded = true;
        ClientSoldierNetworking.respondSoldierTransfer(this.requestId, accepted);
        if (this.field_22787 != null) {
            this.field_22787.method_1507(null);
        }
    }

    public void method_25419() {
        if (!this.responded) {
            this.respond(false);
        } else {
            super.method_25419();
        }
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, this.field_22790 / 2 - 54, 0xFFFFFF);
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43469((String)(this.withTroop ? "screen.medieval-machinery.soldier_transfer.troop_question" : "screen.medieval-machinery.soldier_transfer.question"), (Object[])new Object[]{this.sourceName, this.soldierName}), this.field_22789 / 2, this.field_22790 / 2 - 22, 0xDDDDDD);
        if (this.withTroop) {
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.soldier_transfer.troop_members", (Object[])new Object[]{this.groupName, this.memberCount}), this.field_22789 / 2, this.field_22790 / 2 - 8, 0xAAAAAA);
        }
        super.method_25394(context, mouseX, mouseY, delta);
    }
}

