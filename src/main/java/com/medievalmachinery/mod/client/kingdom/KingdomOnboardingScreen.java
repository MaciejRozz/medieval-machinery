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
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 *  net.minecraft.class_5481
 */
package com.medievalmachinery.mod.client.kingdom;

import com.medievalmachinery.mod.client.command.CommandOverlayScreen;
import com.medievalmachinery.mod.client.network.ClientKingdomNetworking;
import com.medievalmachinery.mod.npc.organization.OrderMaterials;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
import net.minecraft.class_5250;
import net.minecraft.class_5348;
import net.minecraft.class_5481;

@Environment(value=EnvType.CLIENT)
public final class KingdomOnboardingScreen
extends class_437 {
    private static final int VISIBLE_KINGDOMS = 5;
    private static final int VISIBLE_ORDERS = 6;
    private static final OrderStyle[] ORDERS = (OrderStyle[])Arrays.stream(OrderStyle.values()).filter(style -> style != OrderStyle.NONE).toArray(OrderStyle[]::new);
    private ClientKingdomNetworking.Snapshot snapshot;
    private final boolean fromWatchpost;
    private int kingdomOffset;
    private OrderStyle selectedOrder = OrderStyle.GOLD;
    private class_342 nameField;
    private class_4185 orderButton;
    private boolean orderDropdownOpen;
    private int orderOffset;

    public KingdomOnboardingScreen(ClientKingdomNetworking.Snapshot snapshot, boolean fromWatchpost) {
        super((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.onboarding_title"));
        this.snapshot = snapshot;
        this.fromWatchpost = fromWatchpost;
    }

    protected void method_25426() {
        this.method_37067();
        if (this.snapshot.hasKingdom()) {
            this.finishAfterMembership();
            return;
        }
        int panelW = 380;
        int left = (this.field_22789 - panelW) / 2;
        int top = Math.max(12, (this.field_22790 - 330) / 2);
        List<ClientKingdomNetworking.KingdomEntry> kingdoms = this.snapshot.joinableKingdoms();
        int y = top + 78;
        int end = Math.min(kingdoms.size(), this.kingdomOffset + 5);
        for (int i = this.kingdomOffset; i < end; ++i) {
            ClientKingdomNetworking.KingdomEntry entry = kingdoms.get(i);
            class_5250 label = class_2561.method_43469((String)"gui.medieval-machinery.kingdom.join_entry", (Object[])new Object[]{entry.name(), entry.memberCount()});
            this.method_37063((class_364)class_4185.method_46430((class_2561)label, b -> ClientKingdomNetworking.joinKingdom(entry.kingdomId())).method_46434(left + 24, y, panelW - 48, 20).method_46431());
            y += 23;
        }
        if (kingdoms.size() > 5) {
            class_4185 up = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u25b2"), b -> this.scrollKingdoms(-1)).method_46434(left + panelW - 20, top + 78, 16, 20).method_46431());
            class_4185 down = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u25bc"), b -> this.scrollKingdoms(1)).method_46434(left + panelW - 20, top + 170, 16, 20).method_46431());
            up.field_22763 = this.kingdomOffset > 0;
            down.field_22763 = this.kingdomOffset + 5 < kingdoms.size();
        }
        int createY = top + 206;
        this.nameField = new class_342(this.field_22793, left + 24, createY, 176, 20, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.name"));
        this.nameField.method_1880(48);
        this.method_37063((class_364)this.nameField);
        this.orderButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)this.orderText(), b -> {
            this.orderDropdownOpen = !this.orderDropdownOpen;
        }).method_46434(left + 210, createY, 146, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.create"), b -> this.createKingdom()).method_46434(left + 24, createY + 50, panelW - 48, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.cancel"), b -> this.method_25419()).method_46434(left + 130, createY + 78, 120, 20).method_46431());
    }

    public void acceptSnapshot(ClientKingdomNetworking.Snapshot updated) {
        this.snapshot = updated;
        if (updated.hasKingdom()) {
            this.finishAfterMembership();
        } else {
            this.method_25426();
        }
    }

    private void scrollKingdoms(int direction) {
        int max = Math.max(0, this.snapshot.joinableKingdoms().size() - 5);
        this.kingdomOffset = Math.max(0, Math.min(max, this.kingdomOffset + direction));
        this.method_25426();
    }

    private void selectOrder(OrderStyle order) {
        this.selectedOrder = order;
        this.orderDropdownOpen = false;
        if (this.orderButton != null) {
            this.orderButton.method_25355(this.orderText());
        }
    }

    private void scrollOrders(int direction) {
        int max = Math.max(0, ORDERS.length - 6);
        this.orderOffset = Math.max(0, Math.min(max, this.orderOffset + direction));
    }

    private class_2561 orderText() {
        return class_2561.method_43470((String)"").method_10852((class_2561)class_2561.method_43471((String)("order.medieval-machinery." + this.selectedOrder.method_15434()))).method_27693("  \u25bc");
    }

    private void createKingdom() {
        if (this.nameField == null) {
            return;
        }
        String name = this.nameField.method_1882().trim();
        if (!name.isEmpty()) {
            ClientKingdomNetworking.createKingdom(name, this.selectedOrder);
        }
    }

    private void finishAfterMembership() {
        if (this.field_22787 == null) {
            return;
        }
        this.field_22787.method_1507((class_437)(this.fromWatchpost ? null : new CommandOverlayScreen()));
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        context.method_25294(0, 0, this.field_22789, this.field_22790, -1728053248);
        int panelW = 380;
        int left = (this.field_22789 - panelW) / 2;
        int top = Math.max(12, (this.field_22790 - 330) / 2);
        context.method_25294(left, top, left + panelW, top + 318, -535292398);
        context.method_49601(left, top, panelW, 318, -7702692);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, top + 14, 0xFFFFFF);
        class_5250 onboardingMessage = class_2561.method_43471((String)"gui.medieval-machinery.kingdom.onboarding_message");
        List messageLines = this.field_22793.method_1728((class_5348)onboardingMessage, panelW - 48);
        int messageY = top + 34;
        for (class_5481 line : messageLines) {
            context.method_35719(this.field_22793, line, this.field_22789 / 2, messageY, 15130838);
            Objects.requireNonNull(this.field_22793);
            messageY += 9 + 2;
        }
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.existing"), left + 24, top + 60, 14206886);
        if (this.snapshot.joinableKingdoms().isEmpty()) {
            context.method_27535(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.none_available"), left + 24, top + 84, 0xAFAFAF);
        }
        int createY = top + 206;
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.create_section"), left + 24, createY - 16, 14206886);
        class_1792 material = OrderMaterials.materialFor(this.selectedOrder);
        if (material != null) {
            context.method_27535(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.kingdom.foundation_cost", (Object[])new Object[]{new class_1799((class_1935)material).method_7964()}), left + 24, createY + 26, 0xD0D0D0);
        }
        super.method_25394(context, mouseX, mouseY, delta);
        if (this.orderDropdownOpen) {
            context.method_51448().method_22903();
            context.method_51448().method_46416(0.0f, 0.0f, 300.0f);
            this.renderOrderDropdown(context, mouseX, mouseY, left + 210, createY - Math.min(6, ORDERS.length) * 20 - 1, 146);
            context.method_51448().method_22909();
        }
    }

    private void renderOrderDropdown(class_332 context, int mouseX, int mouseY, int x, int y, int width) {
        int visible = Math.min(6, ORDERS.length);
        int rowHeight = 20;
        int listHeight = visible * rowHeight;
        int scrollWidth = ORDERS.length > 6 ? 10 : 0;
        context.method_25294(x, y, x + width, y + listHeight, -15198702);
        context.method_49601(x, y, width, listHeight, -7702692);
        int end = Math.min(ORDERS.length, this.orderOffset + visible);
        for (int i = this.orderOffset; i < end; ++i) {
            boolean hovered;
            int row = i - this.orderOffset;
            int rowY = y + row * rowHeight;
            boolean bl = hovered = mouseX >= x && mouseX < x + width - scrollWidth && mouseY >= rowY && mouseY < rowY + rowHeight;
            if (hovered) {
                context.method_25294(x + 1, rowY + 1, x + width - scrollWidth - 1, rowY + rowHeight - 1, -11909827);
            }
            class_5250 label = class_2561.method_43471((String)("order.medieval-machinery." + ORDERS[i].method_15434()));
            context.method_27535(this.field_22793, (class_2561)label, x + 4, rowY + 6, 0xFFFFFF);
        }
        if (scrollWidth > 0) {
            int trackX = x + width - scrollWidth;
            context.method_25294(trackX, y, x + width, y + listHeight, -14409443);
            int maxOffset = ORDERS.length - 6;
            int thumbHeight = Math.max(18, listHeight * 6 / ORDERS.length);
            int travel = listHeight - thumbHeight;
            int thumbY = y + (maxOffset == 0 ? 0 : travel * this.orderOffset / maxOffset);
            context.method_25294(trackX + 2, thumbY + 1, x + width - 2, thumbY + thumbHeight - 1, -6647420);
        }
    }

    public boolean method_25402(double mouseX, double mouseY, int button) {
        if (this.orderDropdownOpen && button == 0) {
            int scrollWidth;
            int panelW = 380;
            int left = (this.field_22789 - panelW) / 2;
            int top = Math.max(12, (this.field_22790 - 330) / 2);
            int createY = top + 206;
            int x = left + 210;
            int visible = Math.min(6, ORDERS.length);
            int y = createY - visible * 20 - 1;
            int w = 146;
            int h = visible * 20;
            int n = scrollWidth = ORDERS.length > 6 ? 10 : 0;
            if (mouseX >= (double)x && mouseX < (double)(x + w - scrollWidth) && mouseY >= (double)y && mouseY < (double)(y + h)) {
                int row = (int)((mouseY - (double)y) / 20.0);
                int index = this.orderOffset + row;
                if (index >= 0 && index < ORDERS.length) {
                    this.selectOrder(ORDERS[index]);
                }
                return true;
            }
            if (scrollWidth > 0 && mouseX >= (double)(x + w - scrollWidth) && mouseX < (double)(x + w) && mouseY >= (double)y && mouseY < (double)(y + h)) {
                int maxOffset = ORDERS.length - 6;
                double relative = (mouseY - (double)y) / (double)h;
                this.orderOffset = Math.max(0, Math.min(maxOffset, (int)Math.round(relative * (double)maxOffset)));
                return true;
            }
            if (!(mouseX >= (double)x && mouseX < (double)(x + w) && mouseY >= (double)createY && mouseY < (double)(createY + 20))) {
                this.orderDropdownOpen = false;
            }
        }
        return super.method_25402(mouseX, mouseY, button);
    }

    public boolean method_25401(double mouseX, double mouseY, double amount) {
        if (this.orderDropdownOpen) {
            this.scrollOrders(amount > 0.0 ? -1 : 1);
            return true;
        }
        return super.method_25401(mouseX, mouseY, amount);
    }

    public boolean method_25421() {
        return false;
    }
}

