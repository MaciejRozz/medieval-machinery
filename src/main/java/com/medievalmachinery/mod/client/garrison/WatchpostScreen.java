/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_124
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1935
 *  net.minecraft.class_2338
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_332
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_465
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.garrison;

import com.medievalmachinery.mod.client.garrison.WatchpostPromotionScreen;
import com.medievalmachinery.mod.client.garrison.WatchpostRenameScreen;
import com.medievalmachinery.mod.network.WatchpostNetworking;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.screen.garrison.WatchpostScreenHandler;
import java.util.List;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_124;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_465;
import net.minecraft.class_5250;
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class WatchpostScreen
extends class_465<WatchpostScreenHandler> {
    private static final int GROUP_VISIBLE_ROWS = 6;
    private static final int ROLE_VISIBLE_ROWS = 6;
    private static final NpcRole[] RECRUITABLE_ROLES = new NpcRole[]{NpcRole.SWORDSMAN, NpcRole.AXEMAN, NpcRole.ARCHER, NpcRole.CROSSBOWMAN, NpcRole.SPEARMAN, NpcRole.CANNONEER, NpcRole.KNIGHT, NpcRole.QUARTERMASTER, NpcRole.SCOUT, NpcRole.MOUNTED_ARCHER, NpcRole.MOUNTED_KNIGHT};
    private boolean roleDropdownOpen;
    private int roleScrollOffset;
    private boolean draggingRoleScrollbar;
    private NpcRole selectedRecruitRole = NpcRole.SWORDSMAN;
    private boolean groupDropdownOpen;
    private int groupScrollOffset;
    private boolean draggingGroupScrollbar;
    private UUID selectedRecruitGroupId;
    private String selectedRecruitGroupName = "Standard";
    private class_4185 recruitButton;

    public WatchpostScreen(WatchpostScreenHandler handler, class_1661 inventory, class_2561 title) {
        super((class_1703)handler, inventory, title);
        this.field_2792 = 420;
        this.field_2779 = 382;
        this.field_25269 = 130;
        this.field_25270 = 270;
    }

    protected void method_25426() {
        super.method_25426();
        if (((WatchpostScreenHandler)this.field_2797).isCaptureMode()) {
            this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.capture_button"), b -> {
                if (this.field_22787 != null && this.field_22787.field_1761 != null) {
                    this.field_22787.field_1761.method_2900(((WatchpostScreenHandler)this.field_2797).field_7763, 100);
                }
            }).method_46434(this.field_2776 + 230, this.field_2800 + 92, 170, 20).method_46431());
            return;
        }
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.rename"), b -> this.openRenameScreen()).method_46434(this.field_2776 + 236, this.field_2800 + 28, 78, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.manage_supply"), b -> this.beginSupplyAssignment()).method_46434(this.field_2776 + 232, this.field_2800 + 130, 168, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.manage_castle_area"), b -> this.beginCastleAreaAssignment()).method_46434(this.field_2776 + 232, this.field_2800 + 154, 168, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.manage_tax"), b -> this.beginTaxAssignment()).method_46434(this.field_2776 + 232, this.field_2800 + 202, 168, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.promote"), b -> this.openPromotionScreen()).method_46434(this.field_2776 + 232, this.field_2800 + 242, 168, 20).method_46431());
        this.selectedRecruitRole = NpcRole.SWORDSMAN;
        this.roleDropdownOpen = false;
        this.roleScrollOffset = 0;
        this.selectedRecruitGroupId = null;
        this.selectedRecruitGroupName = "Standard";
        this.groupDropdownOpen = false;
        this.groupScrollOffset = 0;
        this.recruitButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)this.recruitButtonLabel(), b -> this.sendRecruit(this.selectedRecruitRole)).method_46434(this.field_2776 + 20, this.field_2800 + 218, 184, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.manage_rally"), b -> this.beginRallyAssignment()).method_46434(this.field_2776 + 20, this.field_2800 + 246, 184, 20).method_46431());
    }

    private class_2561 roleLabel(NpcRole role) {
        return class_2561.method_43471((String)("npc.medieval-machinery.role." + (switch (role) {
            case NpcRole.SWORDSMAN -> "swordsman";
            case NpcRole.AXEMAN -> "axeman";
            case NpcRole.ARCHER -> "archer";
            case NpcRole.CROSSBOWMAN -> "crossbowman";
            case NpcRole.SPEARMAN -> "spearman";
            case NpcRole.CANNONEER -> "cannoneer";
            case NpcRole.KNIGHT -> "knight";
            case NpcRole.QUARTERMASTER -> "quartermaster";
            case NpcRole.SCOUT -> "scout";
            case NpcRole.ENGINEER -> "engineer";
            case NpcRole.MOUNTED_ARCHER -> "mounted_archer";
            case NpcRole.MOUNTED_KNIGHT -> "mounted_knight";
            default -> "swordsman";
        })));
    }

    private class_2561 recruitButtonLabel() {
        int cost = ((WatchpostScreenHandler)this.field_2797).getRecruitmentEmeraldCost(this.selectedRecruitRole);
        if (!((WatchpostScreenHandler)this.field_2797).areRecruitmentCostsEnabled() || cost <= 0) {
            return class_2561.method_43471((String)"gui.medieval-machinery.watchpost.recruit_button");
        }
        return class_2561.method_43469((String)"gui.medieval-machinery.watchpost.recruit_button_cost", (Object[])new Object[]{cost});
    }

    private void sendRecruit(NpcRole role) {
        if (this.field_22787 == null || this.field_22787.field_1724 == null || role == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(((WatchpostScreenHandler)this.field_2797).getWatchpostPos());
        buf.method_10804(role.ordinal());
        buf.writeBoolean(this.selectedRecruitGroupId != null);
        if (this.selectedRecruitGroupId != null) {
            buf.method_10797(this.selectedRecruitGroupId);
        }
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.RECRUIT, (class_2540)buf);
    }

    private void beginRallyAssignment() {
        if (this.field_22787 == null || this.field_22787.field_1724 == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(((WatchpostScreenHandler)this.field_2797).getWatchpostPos());
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.ASSIGN_RALLY, (class_2540)buf);
    }

    private void beginCastleAreaAssignment() {
        if (this.field_22787 == null || this.field_22787.field_1724 == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(((WatchpostScreenHandler)this.field_2797).getWatchpostPos());
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.ASSIGN_CASTLE_AREA, (class_2540)buf);
    }

    private void beginSupplyAssignment() {
        if (this.field_22787 == null || this.field_22787.field_1724 == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(((WatchpostScreenHandler)this.field_2797).getWatchpostPos());
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.ASSIGN_SUPPLY, (class_2540)buf);
    }

    private void beginTaxAssignment() {
        if (this.field_22787 == null || this.field_22787.field_1724 == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(((WatchpostScreenHandler)this.field_2797).getWatchpostPos());
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.ASSIGN_TAX, (class_2540)buf);
    }

    private void openPromotionScreen() {
        if (this.field_22787 == null || this.field_22787.field_1724 == null) {
            return;
        }
        class_2338 pos = ((WatchpostScreenHandler)this.field_2797).getWatchpostPos();
        List<WatchpostScreenHandler.PromotionOption> options = ((WatchpostScreenHandler)this.field_2797).getPromotionOptions();
        this.field_22787.field_1724.method_7346();
        this.field_22787.method_1507((class_437)new WatchpostPromotionScreen(pos, options));
    }

    private void openRenameScreen() {
        if (this.field_22787 == null || this.field_22787.field_1724 == null) {
            return;
        }
        class_2338 pos = ((WatchpostScreenHandler)this.field_2797).getWatchpostPos();
        String currentName = ((WatchpostScreenHandler)this.field_2797).getCastleName();
        this.field_22787.field_1724.method_7346();
        this.field_22787.method_1507((class_437)new WatchpostRenameScreen(pos, currentName));
    }

    protected void method_2389(class_332 context, float delta, int mouseX, int mouseY) {
        context.method_25294(this.field_2776, this.field_2800, this.field_2776 + this.field_2792, this.field_2800 + this.field_2779, -3750202);
        context.method_25294(this.field_2776, this.field_2800, this.field_2776 + this.field_2792, this.field_2800 + 2, -1);
        context.method_25294(this.field_2776, this.field_2800, this.field_2776 + 2, this.field_2800 + this.field_2779, -1);
        context.method_25294(this.field_2776, this.field_2800 + this.field_2779 - 2, this.field_2776 + this.field_2792, this.field_2800 + this.field_2779, -11184811);
        context.method_25294(this.field_2776 + this.field_2792 - 2, this.field_2800, this.field_2776 + this.field_2792, this.field_2800 + this.field_2779, -11184811);
        if (((WatchpostScreenHandler)this.field_2797).isCaptureMode()) {
            WatchpostScreen.drawVanillaSlot(context, this.field_2776 + 366, this.field_2800 + 48);
            for (int row = 0; row < 3; ++row) {
                for (int col = 0; col < 9; ++col) {
                    WatchpostScreen.drawVanillaSlot(context, this.field_2776 + 129 + col * 18, this.field_2800 + 284 + row * 18);
                }
            }
            for (int col = 0; col < 9; ++col) {
                WatchpostScreen.drawVanillaSlot(context, this.field_2776 + 129 + col * 18, this.field_2800 + 342);
            }
            return;
        }
        context.method_25294(this.field_2776 + 214, this.field_2800 + 104, this.field_2776 + 216, this.field_2800 + 280, -6645094);
        context.method_25294(this.field_2776 + 12, this.field_2800 + 276, this.field_2776 + this.field_2792 - 12, this.field_2800 + 278, -8947849);
        context.method_25294(this.field_2776 + 12, this.field_2800 + 278, this.field_2776 + this.field_2792 - 12, this.field_2800 + 279, -1);
        WatchpostScreen.drawVanillaSlot(context, this.field_2776 + 366, this.field_2800 + 48);
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                WatchpostScreen.drawVanillaSlot(context, this.field_2776 + 129 + col * 18, this.field_2800 + 284 + row * 18);
            }
        }
        for (int col = 0; col < 9; ++col) {
            WatchpostScreen.drawVanillaSlot(context, this.field_2776 + 129 + col * 18, this.field_2800 + 342);
        }
    }

    private static void drawVanillaSlot(class_332 context, int slotX, int slotY) {
        class_2960 vanilla = new class_2960("minecraft", "textures/gui/container/generic_54.png");
        context.method_25290(vanilla, slotX, slotY, 7.0f, 17.0f, 18, 18, 256, 256);
    }

    protected void method_2388(class_332 context, int mouseX, int mouseY) {
        int dark = 0x404040;
        context.method_51439(this.field_22793, this.field_22785, 16, 14, dark, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)((WatchpostScreenHandler)this.field_2797).getCastleName()), 16, 36, dark, false);
        if (((WatchpostScreenHandler)this.field_2797).isCaptureMode()) {
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.capture_sigil"), 320, 34, dark, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.capture_hint"), 20, 72, dark, false);
            context.method_51439(this.field_22793, this.field_29347, this.field_25269, this.field_25270, dark, false);
            return;
        }
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.order_sigil"), 330, 34, dark, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.watchpost.villagers", (Object[])new Object[]{((WatchpostScreenHandler)this.field_2797).getTotalVillagerCount(), ((WatchpostScreenHandler)this.field_2797).getMinimumVillagers()}), 16, 70, dark, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.watchpost.employed", (Object[])new Object[]{((WatchpostScreenHandler)this.field_2797).getEmployedVillagerCount()}), 126, 70, dark, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.watchpost.available", (Object[])new Object[]{((WatchpostScreenHandler)this.field_2797).getAvailableVillagerCount()}), 226, 70, 0xA06000, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.watchpost.soldiers", (Object[])new Object[]{((WatchpostScreenHandler)this.field_2797).getSoldierCount()}), 330, 70, dark, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.recruit_heading").method_27692(class_124.field_1067), 20, 106, dark, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.recruit_role"), 20, 122, dark, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.recruit_group"), 20, 170, dark, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.management_heading"), 232, 106, dark, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.watchpost.tax_account", (Object[])new Object[]{((WatchpostScreenHandler)this.field_2797).getTaxBalance()}), 232, 180, 1538614, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.watchpost.tax_villagers", (Object[])new Object[]{((WatchpostScreenHandler)this.field_2797).getTaxVillagerCount()}), 232, 192, 0x606060, false);
        context.method_51439(this.field_22793, this.field_29347, this.field_25269, this.field_25270, dark, false);
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        if (this.recruitButton != null) {
            this.recruitButton.method_25355(this.recruitButtonLabel());
        }
        super.method_25394(context, mouseX, mouseY, delta);
        this.drawDropdownField(context, this.field_2776 + 20, this.field_2800 + 134, 184, this.roleLabel(this.selectedRecruitRole), this.roleDropdownOpen);
        this.drawSelectedRoleCost(context, this.field_2776 + 20, this.field_2800 + 134, 184);
        this.drawDropdownField(context, this.field_2776 + 20, this.field_2800 + 182, 184, (class_2561)class_2561.method_43470((String)this.selectedRecruitGroupName), this.groupDropdownOpen);
        this.drawRoleDropdown(context, mouseX, mouseY);
        this.drawGroupDropdown(context, mouseX, mouseY);
        this.method_2380(context, mouseX, mouseY);
    }

    private List<WatchpostScreenHandler.RecruitmentGroupOption> recruitmentGroups() {
        return ((WatchpostScreenHandler)this.field_2797).getRecruitmentGroups();
    }

    private int groupPopupX() {
        return this.field_2776 + 20;
    }

    private int groupPopupY() {
        return this.field_2800 + 202;
    }

    private int maxGroupScroll() {
        int total = this.recruitmentGroups().size() + 1;
        return Math.max(0, total - 6);
    }

    private void selectGroupRow(int visibleRow) {
        int index = this.groupScrollOffset + visibleRow;
        if (index == 0) {
            this.selectedRecruitGroupId = null;
            this.selectedRecruitGroupName = "Standard";
        } else {
            List<WatchpostScreenHandler.RecruitmentGroupOption> groups = this.recruitmentGroups();
            int groupIndex = index - 1;
            if (groupIndex < 0 || groupIndex >= groups.size()) {
                return;
            }
            WatchpostScreenHandler.RecruitmentGroupOption option = groups.get(groupIndex);
            this.selectedRecruitGroupId = option.id();
            this.selectedRecruitGroupName = option.name();
        }
        this.groupDropdownOpen = false;
    }

    private void drawDropdownField(class_332 context, int fx, int fy, int width, class_2561 label, boolean open) {
        context.method_25294(fx, fy, fx + width, fy + 20, -11908534);
        context.method_25294(fx + 1, fy + 1, fx + width - 1, fy + 19, open ? -8947849 : -10329502);
        context.method_25294(fx + width - 20, fy + 1, fx + width - 1, fy + 19, -11184811);
        context.method_51439(this.field_22793, label, fx + 6, fy + 6, -1, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43470((String)(open ? "\u25b2" : "\u25bc")), fx + width - 15, fy + 6, -1, false);
    }

    private int rolePopupX() {
        return this.field_2776 + 20;
    }

    private int rolePopupY() {
        return this.field_2800 + 154;
    }

    private int maxRoleScroll() {
        return Math.max(0, RECRUITABLE_ROLES.length - 6);
    }

    private void drawRoleDropdown(class_332 context, int mouseX, int mouseY) {
        if (!this.roleDropdownOpen) {
            return;
        }
        int px = this.rolePopupX();
        int py = this.rolePopupY();
        int width = 184;
        int visible = Math.min(6, RECRUITABLE_ROLES.length - this.roleScrollOffset);
        int height = visible * 20 + 4;
        context.method_51448().method_22903();
        context.method_51448().method_46416(0.0f, 0.0f, 520.0f);
        context.method_25294(px - 3, py - 3, px + width + 3, py + height + 1, -15724528);
        context.method_25294(px - 1, py - 1, px + width + 1, py + height - 1, -986896);
        for (int row = 0; row < visible; ++row) {
            boolean selected;
            int index = this.roleScrollOffset + row;
            int ry = py + row * 20;
            NpcRole role = RECRUITABLE_ROLES[index];
            int contentRight = this.maxRoleScroll() > 0 ? px + width - 10 : px + width;
            boolean hovered = mouseX >= px && mouseX < contentRight && mouseY >= ry && mouseY < ry + 20;
            boolean bl = selected = role == this.selectedRecruitRole;
            context.method_25294(px, ry, contentRight, ry + 19, selected ? -10855846 : (hovered ? -8026747 : -9934744));
            context.method_51439(this.field_22793, this.roleLabel(role), px + 5, ry + 6, -1, false);
            int cost = ((WatchpostScreenHandler)this.field_2797).getRecruitmentEmeraldCost(role);
            if (!((WatchpostScreenHandler)this.field_2797).areRecruitmentCostsEnabled() || cost <= 0) continue;
            class_1799 emerald = new class_1799((class_1935)class_1802.field_8687);
            int iconX = contentRight - 20;
            class_5250 costText = class_2561.method_43470((String)Integer.toString(cost));
            int numberX = iconX - this.field_22793.method_27525((class_5348)costText) - 2;
            context.method_51439(this.field_22793, (class_2561)costText, numberX, ry + 6, -8329088, false);
            context.method_51427(emerald, iconX, ry + 2);
        }
        this.drawScrollbar(context, px + width - 9, py, visible * 20, this.roleScrollOffset, this.maxRoleScroll(), RECRUITABLE_ROLES.length, 6);
        context.method_51448().method_22909();
    }

    private void drawSelectedRoleCost(class_332 context, int fx, int fy, int width) {
        int cost = ((WatchpostScreenHandler)this.field_2797).getRecruitmentEmeraldCost(this.selectedRecruitRole);
        if (!((WatchpostScreenHandler)this.field_2797).areRecruitmentCostsEnabled() || cost <= 0) {
            return;
        }
        class_1799 emerald = new class_1799((class_1935)class_1802.field_8687);
        int iconX = fx + width - 42;
        context.method_25294(fx + width - 70, fy + 1, fx + width - 20, fy + 19, -10329502);
        class_5250 costText = class_2561.method_43470((String)Integer.toString(cost));
        int numberX = iconX - this.field_22793.method_27525((class_5348)costText) - 2;
        context.method_51439(this.field_22793, (class_2561)costText, numberX, fy + 6, -8329088, false);
        context.method_51427(emerald, iconX, fy + 2);
    }

    private void drawGroupDropdown(class_332 context, int mouseX, int mouseY) {
        if (!this.groupDropdownOpen) {
            return;
        }
        int px = this.groupPopupX();
        int py = this.groupPopupY();
        int width = 184;
        int total = this.recruitmentGroups().size() + 1;
        int visible = Math.min(6, Math.max(0, total - this.groupScrollOffset));
        int height = visible * 20 + 4;
        context.method_51448().method_22903();
        context.method_51448().method_46416(0.0f, 0.0f, 500.0f);
        context.method_25294(px - 3, py - 3, px + width + 3, py + height + 1, -15724528);
        context.method_25294(px - 1, py - 1, px + width + 1, py + height - 1, -986896);
        context.method_25294(px, py, px + width, py + height - 2, -3750202);
        for (int row = 0; row < visible; ++row) {
            boolean selected;
            class_5250 label;
            boolean hovered;
            int index = this.groupScrollOffset + row;
            int ry = py + row * 20;
            int contentRight = this.maxGroupScroll() > 0 ? px + width - 10 : px + width;
            boolean bl = hovered = mouseX >= px && mouseX < contentRight && mouseY >= ry && mouseY < ry + 20;
            if (index == 0) {
                label = class_2561.method_43470((String)"Standard");
                selected = this.selectedRecruitGroupId == null;
            } else {
                WatchpostScreenHandler.RecruitmentGroupOption option = this.recruitmentGroups().get(index - 1);
                label = class_2561.method_43470((String)option.name());
                selected = option.id().equals(this.selectedRecruitGroupId);
            }
            int bg = selected ? -9408400 : (hovered ? -6645094 : -3750202);
            context.method_25294(px, ry, contentRight, ry + 19, bg);
            int color = selected ? -1 : -13619152;
            context.method_51439(this.field_22793, (class_2561)label, px + 5, ry + 6, color, false);
        }
        this.drawScrollbar(context, px + width - 9, py, visible * 20, this.groupScrollOffset, this.maxGroupScroll(), total, 6);
        context.method_51448().method_22909();
    }

    private void drawScrollbar(class_332 context, int sx, int sy, int height, int offset, int maxScroll, int totalRows, int visibleRows) {
        if (maxScroll <= 0 || height <= 0 || totalRows <= visibleRows) {
            return;
        }
        context.method_25294(sx, sy, sx + 7, sy + height - 1, -12763843);
        int thumbHeight = Math.max(14, height * visibleRows / totalRows);
        int travel = Math.max(1, height - thumbHeight);
        int thumbY = sy + (int)Math.round((double)offset * (double)travel / (double)maxScroll);
        context.method_25294(sx + 1, thumbY, sx + 6, thumbY + thumbHeight, -4671304);
        context.method_25294(sx + 2, thumbY + 1, sx + 5, thumbY + thumbHeight - 1, -3092272);
    }

    private int scrollOffsetFromMouse(double mouseY, int sy, int height, int maxScroll, int totalRows, int visibleRows) {
        if (maxScroll <= 0 || totalRows <= visibleRows) {
            return 0;
        }
        int thumbHeight = Math.max(14, height * visibleRows / totalRows);
        int travel = Math.max(1, height - thumbHeight);
        double relative = mouseY - (double)sy - (double)thumbHeight / 2.0;
        int offset = (int)Math.round(relative * (double)maxScroll / (double)travel);
        return Math.max(0, Math.min(maxScroll, offset));
    }

    public boolean method_25402(double mouseX, double mouseY, int button) {
        if (this.roleDropdownOpen && button == 0) {
            int px = this.rolePopupX();
            int py = this.rolePopupY();
            int visible = Math.min(6, RECRUITABLE_ROLES.length - this.roleScrollOffset);
            int height = visible * 20;
            if (this.maxRoleScroll() > 0 && mouseX >= (double)(px + 175) && mouseX < (double)(px + 184) && mouseY >= (double)py && mouseY < (double)(py + height)) {
                this.roleScrollOffset = this.scrollOffsetFromMouse(mouseY, py, height, this.maxRoleScroll(), RECRUITABLE_ROLES.length, 6);
                this.draggingRoleScrollbar = true;
                return true;
            }
            if (mouseX >= (double)px) {
                int n = this.maxRoleScroll() > 0 ? 174 : 184;
                if (mouseX < (double)(px + n) && mouseY >= (double)py && mouseY < (double)(py + height)) {
                    int row = (int)((mouseY - (double)py) / 20.0);
                    this.selectedRecruitRole = RECRUITABLE_ROLES[this.roleScrollOffset + row];
                    this.roleDropdownOpen = false;
                    return true;
                }
            }
            this.roleDropdownOpen = false;
            return true;
        }
        if (this.groupDropdownOpen && button == 0) {
            int px = this.groupPopupX();
            int py = this.groupPopupY();
            int total = this.recruitmentGroups().size() + 1;
            int visible = Math.min(6, Math.max(0, total - this.groupScrollOffset));
            int height = visible * 20;
            if (this.maxGroupScroll() > 0 && mouseX >= (double)(px + 175) && mouseX < (double)(px + 184) && mouseY >= (double)py && mouseY < (double)(py + height)) {
                this.groupScrollOffset = this.scrollOffsetFromMouse(mouseY, py, height, this.maxGroupScroll(), total, 6);
                this.draggingGroupScrollbar = true;
                return true;
            }
            if (mouseX >= (double)px) {
                int n = this.maxGroupScroll() > 0 ? 174 : 184;
                if (mouseX < (double)(px + n) && mouseY >= (double)py && mouseY < (double)(py + height)) {
                    int row = (int)((mouseY - (double)py) / 20.0);
                    this.selectGroupRow(row);
                    return true;
                }
            }
            this.groupDropdownOpen = false;
            return true;
        }
        if (button == 0 && mouseX >= (double)(this.field_2776 + 20) && mouseX < (double)(this.field_2776 + 204) && mouseY >= (double)(this.field_2800 + 134) && mouseY < (double)(this.field_2800 + 154)) {
            this.roleDropdownOpen = !this.roleDropdownOpen;
            this.groupDropdownOpen = false;
            return true;
        }
        if (button == 0 && mouseX >= (double)(this.field_2776 + 20) && mouseX < (double)(this.field_2776 + 204) && mouseY >= (double)(this.field_2800 + 182) && mouseY < (double)(this.field_2800 + 202)) {
            this.groupDropdownOpen = !this.groupDropdownOpen;
            this.roleDropdownOpen = false;
            return true;
        }
        return super.method_25402(mouseX, mouseY, button);
    }

    public boolean method_25403(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && this.draggingRoleScrollbar && this.roleDropdownOpen) {
            int visible = Math.min(6, RECRUITABLE_ROLES.length - this.roleScrollOffset);
            this.roleScrollOffset = this.scrollOffsetFromMouse(mouseY, this.rolePopupY(), visible * 20, this.maxRoleScroll(), RECRUITABLE_ROLES.length, 6);
            return true;
        }
        if (button == 0 && this.draggingGroupScrollbar && this.groupDropdownOpen) {
            int total = this.recruitmentGroups().size() + 1;
            int visible = Math.min(6, Math.max(0, total - this.groupScrollOffset));
            this.groupScrollOffset = this.scrollOffsetFromMouse(mouseY, this.groupPopupY(), visible * 20, this.maxGroupScroll(), total, 6);
            return true;
        }
        return super.method_25403(mouseX, mouseY, button, deltaX, deltaY);
    }

    public boolean method_25406(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.draggingRoleScrollbar = false;
            this.draggingGroupScrollbar = false;
        }
        return super.method_25406(mouseX, mouseY, button);
    }

    public boolean method_25401(double mouseX, double mouseY, double amount) {
        if (this.roleDropdownOpen) {
            if (amount < 0.0) {
                ++this.roleScrollOffset;
            }
            if (amount > 0.0) {
                --this.roleScrollOffset;
            }
            this.roleScrollOffset = Math.max(0, Math.min(this.roleScrollOffset, this.maxRoleScroll()));
            return true;
        }
        if (this.groupDropdownOpen) {
            if (amount < 0.0) {
                ++this.groupScrollOffset;
            }
            if (amount > 0.0) {
                --this.groupScrollOffset;
            }
            this.groupScrollOffset = Math.max(0, Math.min(this.groupScrollOffset, this.maxGroupScroll()));
            return true;
        }
        return super.method_25401(mouseX, mouseY, amount);
    }

    public void method_25419() {
        this.selectedRecruitRole = NpcRole.SWORDSMAN;
        this.roleDropdownOpen = false;
        this.draggingRoleScrollbar = false;
        this.selectedRecruitGroupId = null;
        this.selectedRecruitGroupName = "Standard";
        this.groupDropdownOpen = false;
        this.draggingGroupScrollbar = false;
        super.method_25419();
    }
}

