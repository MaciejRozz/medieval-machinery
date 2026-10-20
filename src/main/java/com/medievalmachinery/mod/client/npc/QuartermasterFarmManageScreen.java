/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1297
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.network.ClientSoldierNetworking;
import com.medievalmachinery.mod.client.npc.QuartermasterFarmEditorClient;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
final class QuartermasterFarmManageScreen
extends class_437 {
    private final class_437 parent;
    private final int soldierId;
    private final class_4185[] fieldButtons = new class_4185[3];
    private class_4185 composterButton;
    private int optimisticFieldCount = -1;
    private int optimisticFieldTicks;

    QuartermasterFarmManageScreen(class_437 parent, int soldierId) {
        super((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.quartermaster.manage"));
        this.parent = parent;
        this.soldierId = soldierId;
    }

    protected void method_25426() {
        int cx = this.field_22789 / 2;
        int top = this.field_22790 / 2 - 82;
        this.composterButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43473(), b -> {
            MedievalSoldierEntity soldier = this.soldier();
            if (soldier != null && soldier.hasQuartermasterComposter()) {
                ClientSoldierNetworking.clearQuartermasterComposter(this.soldierId);
            } else {
                QuartermasterFarmEditorClient.startComposter(this.soldierId);
                this.closeToWorld();
            }
        }).method_46434(cx - 95, top, 190, 20).method_46431());
        for (int i = 0; i < 3; ++i) {
            int index = i;
            this.fieldButtons[i] = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43473(), b -> {
                int count;
                MedievalSoldierEntity soldier = this.soldier();
                int n = count = soldier == null ? 0 : soldier.getQuartermasterFieldCount();
                if (index < count) {
                    ClientSoldierNetworking.removeQuartermasterField(this.soldierId, index);
                    this.optimisticFieldCount = Math.max(0, count - 1);
                    this.optimisticFieldTicks = 20;
                    this.refreshButtons();
                } else if (index == count && count < 3) {
                    QuartermasterFarmEditorClient.startField(this.soldierId, count);
                    this.closeToWorld();
                }
            }).method_46434(cx - 95, top + 32 + i * 26, 190, 20).method_46431());
        }
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.back"), b -> this.method_25419()).method_46434(cx - 50, top + 118, 100, 20).method_46431());
        this.refreshButtons();
    }

    public void method_25393() {
        super.method_25393();
        if (this.optimisticFieldTicks > 0) {
            --this.optimisticFieldTicks;
        }
        this.refreshButtons();
    }

    private void refreshButtons() {
        boolean hasComposter;
        int trackedCount;
        MedievalSoldierEntity soldier = this.soldier();
        int n = trackedCount = soldier == null ? 0 : soldier.getQuartermasterFieldCount();
        if (this.optimisticFieldCount >= 0 && trackedCount == this.optimisticFieldCount) {
            this.optimisticFieldCount = -1;
            this.optimisticFieldTicks = 0;
        }
        if (this.optimisticFieldTicks <= 0) {
            this.optimisticFieldCount = -1;
        }
        int count = this.optimisticFieldCount >= 0 ? this.optimisticFieldCount : trackedCount;
        boolean bl = hasComposter = soldier != null && soldier.hasQuartermasterComposter();
        if (this.composterButton != null) {
            this.composterButton.method_25355((class_2561)class_2561.method_43471((String)(hasComposter ? "screen.medieval-machinery.quartermaster.composter_remove" : "screen.medieval-machinery.quartermaster.assign_composter")));
        }
        for (int i = 0; i < this.fieldButtons.length; ++i) {
            class_4185 button = this.fieldButtons[i];
            if (button == null) continue;
            if (i < count) {
                button.field_22763 = true;
                button.method_25355((class_2561)class_2561.method_43469((String)"screen.medieval-machinery.quartermaster.field_remove", (Object[])new Object[]{i + 1}));
                continue;
            }
            if (i == count && count < 3) {
                button.field_22763 = true;
                button.method_25355((class_2561)class_2561.method_43469((String)"screen.medieval-machinery.quartermaster.field_assign", (Object[])new Object[]{i + 1}));
                continue;
            }
            button.field_22763 = false;
            button.method_25355((class_2561)class_2561.method_43469((String)"screen.medieval-machinery.quartermaster.field_free", (Object[])new Object[]{i + 1}));
        }
    }

    private MedievalSoldierEntity soldier() {
        class_1297 class_12972;
        if (this.field_22787 != null && this.field_22787.field_1687 != null && (class_12972 = this.field_22787.field_1687.method_8469(this.soldierId)) instanceof MedievalSoldierEntity) {
            MedievalSoldierEntity s = (MedievalSoldierEntity)class_12972;
            return s;
        }
        return null;
    }

    private void closeToWorld() {
        if (this.field_22787 != null) {
            this.field_22787.method_1507(null);
        }
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        super.method_25394(context, mouseX, mouseY, delta);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, this.field_22790 / 2 - 108, 0xFFFFFF);
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.quartermaster.field_help"), this.field_22789 / 2, this.field_22790 / 2 + 68, 0xD0D0D0);
    }

    public void method_25419() {
        if (this.field_22787 != null) {
            this.field_22787.method_1507(this.parent);
        }
    }
}

