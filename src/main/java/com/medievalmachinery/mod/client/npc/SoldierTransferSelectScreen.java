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
 *  net.minecraft.class_640
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.command.ClientCommandGroupStatus;
import com.medievalmachinery.mod.client.network.ClientSoldierNetworking;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_640;

@Environment(value=EnvType.CLIENT)
public final class SoldierTransferSelectScreen
extends class_437 {
    private final class_437 parent;
    private final int soldierEntityId;
    private final String soldierName;
    private final boolean canTransferTroop;
    private boolean transferTroop;
    private final List<class_640> candidates = new ArrayList<class_640>();

    public SoldierTransferSelectScreen(class_437 parent, int soldierEntityId, String soldierName, boolean canTransferTroop) {
        super((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier_transfer.select_title"));
        this.parent = parent;
        this.soldierEntityId = soldierEntityId;
        this.soldierName = soldierName;
        this.canTransferTroop = canTransferTroop;
    }

    protected void method_25426() {
        this.candidates.clear();
        if (this.field_22787 != null && this.field_22787.method_1562() != null && this.field_22787.field_1724 != null) {
            for (class_640 entry2 : this.field_22787.method_1562().method_2880()) {
                if (entry2.method_2966().getId().equals(this.field_22787.field_1724.method_5667())) continue;
                this.candidates.add(entry2);
            }
        }
        this.candidates.sort(Comparator.comparing(entry -> entry.method_2966().getName(), String.CASE_INSENSITIVE_ORDER));
        int center = this.field_22789 / 2;
        int columnWidth = 150;
        int leftX = center - columnWidth - 14;
        int rightX = center + 14;
        int startY = 72;
        if (this.canTransferTroop) {
            this.method_37063((class_364)class_4185.method_46430((class_2561)this.transferModeText(), button -> {
                this.transferTroop = !this.transferTroop;
                button.method_25355(this.transferModeText());
            }).method_46434(leftX, 46, columnWidth, 20).method_46431());
        }
        int shownPlayers = Math.min(this.candidates.size(), 7);
        for (int i = 0; i < shownPlayers; ++i) {
            class_640 entry3 = this.candidates.get(i);
            int y = startY + i * 22;
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)entry3.method_2966().getName()), button -> {
                ClientSoldierNetworking.requestSoldierTransfer(this.soldierEntityId, entry3.method_2966().getId(), this.transferTroop);
                if (this.field_22787 != null) {
                    this.field_22787.method_1507(this.parent);
                }
            }).method_46434(leftX, y, columnWidth, 20).method_46431());
        }
        ArrayList<ClientCommandGroupStatus.Castle> castles = new ArrayList<ClientCommandGroupStatus.Castle>(ClientCommandGroupStatus.castles());
        castles.sort(Comparator.comparing(ClientCommandGroupStatus.Castle::name, String.CASE_INSENSITIVE_ORDER));
        int shownCastles = Math.min(castles.size(), 7);
        for (int i = 0; i < shownCastles; ++i) {
            ClientCommandGroupStatus.Castle castle = (ClientCommandGroupStatus.Castle)castles.get(i);
            int y = startY + i * 22;
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)castle.name()), button -> {
                ClientSoldierNetworking.requestCastleTransfer(this.soldierEntityId, castle.id());
                if (this.field_22787 != null) {
                    this.field_22787.method_1507(this.parent);
                }
            }).method_46434(rightX, y, columnWidth, 20).method_46431());
        }
        int bottom = startY + Math.max(shownPlayers, shownCastles) * 22 + 12;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.back"), button -> this.method_25419()).method_46434(center - 50, bottom, 100, 20).method_46431());
    }

    private class_2561 transferModeText() {
        return class_2561.method_43471((String)(this.transferTroop ? "screen.medieval-machinery.soldier_transfer.mode_troop" : "screen.medieval-machinery.soldier_transfer.mode_single"));
    }

    public void method_25419() {
        if (this.field_22787 != null) {
            this.field_22787.method_1507(this.parent);
        }
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        int center = this.field_22789 / 2;
        context.method_27534(this.field_22793, this.field_22785, center, 14, 0xFFFFFF);
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.soldier_transfer.select_for", (Object[])new Object[]{this.soldierName}), center, 28, 0xCCCCCC);
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier_transfer.players"), center - 89, 58, 0xFFFFFF);
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier_transfer.castles"), center + 89, 58, 0xFFFFFF);
        context.method_25294(center - 1, 50, center + 1, Math.min(this.field_22790 - 42, 238), -8947849);
        if (this.candidates.isEmpty()) {
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier_transfer.no_players"), center - 89, 86, 0xAAAAAA);
        }
        if (ClientCommandGroupStatus.castles().isEmpty()) {
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier_transfer.no_castles"), center + 89, 86, 0xAAAAAA);
        }
        super.method_25394(context, mouseX, mouseY, delta);
    }
}

