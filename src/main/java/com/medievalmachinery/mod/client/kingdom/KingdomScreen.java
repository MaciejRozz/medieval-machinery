/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_342
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.kingdom;

import com.medievalmachinery.mod.client.command.CommandOverlayScreen;
import com.medievalmachinery.mod.client.network.ClientKingdomNetworking;
import com.medievalmachinery.mod.npc.organization.OrderMaterials;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class KingdomScreen
extends class_437 {
    private static final OrderStyle[] ORDERS = (OrderStyle[])Arrays.stream(OrderStyle.values()).filter(s -> s != OrderStyle.NONE).toArray(OrderStyle[]::new);
    private final ClientKingdomNetworking.Snapshot snapshot;
    private class_342 nameField;
    private class_342 inviteField;
    private OrderStyle selectedOrder;
    private class_4185 orderButton;

    public KingdomScreen(ClientKingdomNetworking.Snapshot snapshot) {
        super((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.title"));
        this.snapshot = snapshot;
        this.selectedOrder = snapshot.hasKingdom() ? snapshot.mainOrder() : OrderStyle.GOLD;
    }

    protected void method_25426() {
        int w = 320;
        int left = (this.field_22789 - w) / 2;
        int top = Math.max(10, (this.field_22790 - 300) / 2);
        this.nameField = new class_342(this.field_22793, left + 20, top + 58, w - 40, 20, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.name"));
        this.nameField.method_1880(48);
        this.nameField.method_1852(this.snapshot.hasKingdom() ? this.snapshot.name() : "");
        this.method_37063((class_364)this.nameField);
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"<"), b -> this.cycle(-1)).method_46434(left + 20, top + 90, 28, 20).method_46431());
        this.orderButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)this.orderText(), b -> this.cycle(1)).method_46434(left + 52, top + 90, w - 104, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)">"), b -> this.cycle(1)).method_46434(left + w - 48, top + 90, 28, 20).method_46431());
        if (!this.snapshot.hasKingdom()) {
            if (this.snapshot.hasInvite()) {
                this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.accept_invite"), b -> ClientKingdomNetworking.acceptInvite()).method_46434(left + 20, top + 124, 125, 20).method_46431());
                this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.decline_invite"), b -> ClientKingdomNetworking.declineInvite()).method_46434(left + 155, top + 124, 125, 20).method_46431());
            }
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.create"), b -> {
                String n = this.nameField.method_1882().trim();
                if (!n.isEmpty()) {
                    ClientKingdomNetworking.createKingdom(n, this.selectedOrder);
                }
            }).method_46434(left + 20, top + 158, w - 40, 20).method_46431());
        } else {
            if (this.snapshot.king()) {
                this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.rename"), b -> {
                    String n = this.nameField.method_1882().trim();
                    if (!n.isEmpty()) {
                        ClientKingdomNetworking.rename(n);
                    }
                }).method_46434(left + 20, top + 124, 125, 20).method_46431());
                this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.change_order"), b -> ClientKingdomNetworking.changeOrder(this.selectedOrder)).method_46434(left + 155, top + 124, 125, 20).method_46431());
            } else {
                this.nameField.method_1888(false);
            }
            if (this.snapshot.canManageMembers()) {
                this.inviteField = new class_342(this.field_22793, left + 20, top + 158, 180, 20, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.player_name"));
                this.inviteField.method_1880(16);
                this.method_37063((class_364)this.inviteField);
                this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.invite"), b -> this.withTarget(ClientKingdomNetworking::invite)).method_46434(left + 208, top + 158, 92, 20).method_46431());
                if (this.snapshot.king()) {
                    this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.promote"), b -> this.withTarget(ClientKingdomNetworking::promote)).method_46434(left + 20, top + 184, 88, 20).method_46431());
                    this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.demote"), b -> this.withTarget(ClientKingdomNetworking::demote)).method_46434(left + 116, top + 184, 88, 20).method_46431());
                    this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.kick"), b -> this.withTarget(ClientKingdomNetworking::kick)).method_46434(left + 212, top + 184, 88, 20).method_46431());
                } else {
                    this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.kick"), b -> this.withTarget(ClientKingdomNetworking::kick)).method_46434(left + 208, top + 184, 92, 20).method_46431());
                }
            }
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.diplomacy"), b -> ClientKingdomNetworking.requestDiplomacy()).method_46434(left + 170, top + 216, 130, 20).method_46431());
            if (!this.snapshot.king()) {
                this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.leave"), b -> ClientKingdomNetworking.leave()).method_46434(left + 20, top + 216, 130, 20).method_46431());
            }
        }
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.done"), b -> this.method_25419()).method_46434(left + 110, top + 252, 100, 20).method_46431());
    }

    private void withTarget(Consumer<String> action) {
        if (this.inviteField == null) {
            return;
        }
        String n = this.inviteField.method_1882().trim();
        if (!n.isEmpty()) {
            action.accept(n);
        }
    }

    private void cycle(int d) {
        int i = 0;
        for (int n = 0; n < ORDERS.length; ++n) {
            if (ORDERS[n] != this.selectedOrder) continue;
            i = n;
        }
        this.selectedOrder = ORDERS[Math.floorMod(i + d, ORDERS.length)];
        if (this.orderButton != null) {
            this.orderButton.method_25355(this.orderText());
        }
    }

    private class_2561 orderText() {
        return class_2561.method_43471((String)("order.medieval-machinery." + this.selectedOrder.method_15434()));
    }

    private class_2561 roleText() {
        return class_2561.method_43471((String)("kingdom.role.medieval-machinery." + this.snapshot.role().toLowerCase(Locale.ROOT)));
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        context.method_25294(0, 0, this.field_22789, this.field_22790, -2013265920);
        int w = 320;
        int left = (this.field_22789 - w) / 2;
        int top = Math.max(10, (this.field_22790 - 300) / 2);
        context.method_51439(this.field_22793, this.field_22785, left + (w - this.field_22793.method_27525((class_5348)this.field_22785)) / 2, top + 14, 0xFFFFFF, false);
        if (!this.snapshot.hasKingdom()) {
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.not_created"), left + 20, top + 36, 0xE0E0E0, false);
            class_1792 material = OrderMaterials.materialFor(this.selectedOrder);
            if (material != null) {
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.kingdom.foundation_cost", (Object[])new Object[]{new class_1799((class_1935)material).method_7964()}), left + 20, top + 114, 0xD0D0D0, false);
            }
            if (this.snapshot.hasInvite()) {
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.kingdom.invite_from", (Object[])new Object[]{this.snapshot.inviteKingdom()}), left + 20, top + 188, 11067520, false);
            }
        } else {
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.kingdom.info_line", (Object[])new Object[]{this.roleText(), this.snapshot.memberCount(), this.snapshot.kingdomId().toString().substring(0, 8)}), left + 20, top + 36, 0xE0E0E0, false);
            if (this.snapshot.canManageMembers()) {
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.member_hint"), left + 20, top + 238, 0xD0D0D0, false);
            }
        }
        super.method_25394(context, mouseX, mouseY, delta);
    }

    public void method_25419() {
        if (this.field_22787 != null) {
            this.field_22787.method_1507((class_437)new CommandOverlayScreen());
        }
    }

    public boolean method_25421() {
        return false;
    }
}

