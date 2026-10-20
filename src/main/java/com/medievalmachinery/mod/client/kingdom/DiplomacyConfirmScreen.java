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
package com.medievalmachinery.mod.client.kingdom;

import com.medievalmachinery.mod.client.kingdom.DiplomacyScreen;
import com.medievalmachinery.mod.client.network.ClientKingdomNetworking;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyStance;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
final class DiplomacyConfirmScreen
extends class_437 {
    private final DiplomacyScreen parent;
    private final ClientKingdomNetworking.DiplomacyEntry entry;
    private final KingdomDiplomacyStance stance;
    private final class_2561 message;

    DiplomacyConfirmScreen(DiplomacyScreen parent, ClientKingdomNetworking.DiplomacyEntry entry, KingdomDiplomacyStance stance, class_2561 title, class_2561 message) {
        super(title);
        this.parent = parent;
        this.entry = entry;
        this.stance = stance;
        this.message = message;
    }

    protected void method_25426() {
        int left = this.field_22789 / 2 - 110;
        int y = this.field_22790 / 2 + 34;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.confirm"), b -> this.parent.apply(this.entry, this.stance)).method_46434(left, y, 105, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.cancel"), b -> {
            if (this.field_22787 != null) {
                this.field_22787.method_1507((class_437)this.parent);
            }
        }).method_46434(left + 115, y, 105, 20).method_46431());
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        context.method_25294(0, 0, this.field_22789, this.field_22790, -1442840576);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, this.field_22790 / 2 - 36, 0xFFFFFF);
        context.method_27534(this.field_22793, this.message, this.field_22789 / 2, this.field_22790 / 2 - 10, 0xE0E0E0);
        super.method_25394(context, mouseX, mouseY, delta);
    }

    public void method_25419() {
        if (this.field_22787 != null) {
            this.field_22787.method_1507((class_437)this.parent);
        }
    }

    public boolean method_25421() {
        return false;
    }
}

