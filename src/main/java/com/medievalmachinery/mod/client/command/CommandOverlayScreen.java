/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_2338
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_342
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_640
 */
package com.medievalmachinery.mod.client.command;

import com.medievalmachinery.mod.client.command.ClientCommandGroupStatus;
import com.medievalmachinery.mod.client.command.CommandGroupPreferences;
import com.medievalmachinery.mod.client.command.CommandGroupRenameScreen;
import com.medievalmachinery.mod.client.command.CommandSelectionPreferences;
import com.medievalmachinery.mod.client.network.ClientKingdomNetworking;
import com.medievalmachinery.mod.client.network.ClientSoldierCommandNetworking;
import com.medievalmachinery.mod.network.SoldierCommandNetworking;
import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.core.NpcSupplyRequest;
import com.medievalmachinery.mod.npc.core.NpcTargetPriority;
import com.medievalmachinery.mod.npc.organization.OrderMaterials;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_640;

@Environment(value=EnvType.CLIENT)
public final class CommandOverlayScreen
extends class_437 {
    private static class_2338 lastLookedAtBlock;
    private static int lastLookedAtEntityId;
    private final List<class_4185> castleWidgets = new ArrayList<class_4185>();
    private final List<class_4185> groupTiles = new ArrayList<class_4185>();
    private final List<class_4185> groupAuxWidgets = new ArrayList<class_4185>();
    private int groupPage;
    private static final int GROUPS_PER_PAGE = 6;
    private final List<class_4185> panelWidgets = new ArrayList<class_4185>();
    private final List<class_4185> transferDropdownWidgets = new ArrayList<class_4185>();
    private final List<class_4185> categoryButtons = new ArrayList<class_4185>();
    private final List<class_342> panelFields = new ArrayList<class_342>();
    private final Map<NpcCombatStance, class_4185> stanceCommandButtons = new EnumMap<NpcCombatStance, class_4185>(NpcCombatStance.class);
    private final Map<NpcOrderType, class_4185> orderCommandButtons = new EnumMap<NpcOrderType, class_4185>(NpcOrderType.class);
    private final Map<NpcFormation, class_4185> formationCommandButtons = new EnumMap<NpcFormation, class_4185>(NpcFormation.class);
    private final Map<NpcShieldMode, class_4185> shieldCommandButtons = new EnumMap<NpcShieldMode, class_4185>(NpcShieldMode.class);
    private class_4185 holdFireButton;
    private class_4185 fireAtWillButton;
    private class_4185 areaFireButton;
    private boolean managementUngrouped;
    private int managementMemberScroll;
    private static final OrderStyle[] KINGDOM_ORDERS;
    private ClientKingdomNetworking.Snapshot kingdomSnapshot;
    private class_342 kingdomNameField;
    private class_342 kingdomInviteField;
    private boolean transferPlayerDropdownOpen;
    private boolean transferCastleDropdownOpen;
    private OrderStyle selectedKingdomOrder = OrderStyle.GOLD;
    private class_4185 kingdomOrderButton;
    private class_4185 targetPriorityButton;
    private NpcTargetPriority selectedTargetPriority = NpcTargetPriority.CLOSEST;
    private int selectedScope;
    private Panel panel = Panel.COMBAT;

    public CommandOverlayScreen() {
        super((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.title"));
        this.selectedScope = CommandSelectionPreferences.getSelectedScope();
    }

    public static void setLastLookedAtBlock(class_2338 blockPos) {
        lastLookedAtBlock = blockPos == null ? null : blockPos.method_10062();
    }

    public static void setLastLookedAtEntity(int entityId) {
        lastLookedAtEntityId = entityId;
    }

    protected void method_25426() {
        if (!(this.selectedScope == 7 || this.groupForScope(this.selectedScope) > 0 && this.visibleGroups().contains(this.groupForScope(this.selectedScope)))) {
            this.selectedScope = SoldierCommandNetworking.scopeForGroupSlot(1);
            CommandSelectionPreferences.setSelectedScope(this.selectedScope);
        }
        this.ensureSelectedGroupPage();
        this.buildCastleSelector();
        this.buildGroupTiles();
        this.buildCategoryButtons();
        int bottomY = Math.min(this.field_22790 - 30, this.field_22790 / 2 + 152);
        int navWidth = 150;
        int navGap = 10;
        int navLeft = this.field_22789 / 2 - (navWidth * 3 + navGap * 2) / 2;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.groups"), b -> this.setPanel(Panel.GROUPS)).method_46434(navLeft, bottomY, navWidth, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.kingdom"), b -> this.setPanel(Panel.KINGDOM)).method_46434(navLeft + navWidth + navGap, bottomY, navWidth, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.diplomacy"), b -> ClientKingdomNetworking.requestDiplomacy()).method_46434(navLeft + (navWidth + navGap) * 2, bottomY, navWidth, 20).method_46431());
        this.rebuildPanel();
        ClientCommandGroupStatus.requestAuto();
    }

    private void buildCastleSelector() {
        for (class_4185 widget : this.castleWidgets) {
            this.method_37066((class_364)widget);
        }
        this.castleWidgets.clear();
        int cy = 26;
        int cx = this.field_22789 / 2;
        List<ClientCommandGroupStatus.Castle> castles = ClientCommandGroupStatus.castles();
        class_4185 previous = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"<"), b -> this.cycleCastle(-1)).method_46434(cx - 170, cy, 28, 20).method_46431());
        previous.field_22763 = castles.size() > 1;
        this.castleWidgets.add(previous);
        class_4185 name = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)ClientCommandGroupStatus.selectedCastleName()), b -> {}).method_46434(cx - 136, cy, 272, 20).method_46431());
        name.field_22763 = false;
        this.castleWidgets.add(name);
        class_4185 next = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)">"), b -> this.cycleCastle(1)).method_46434(cx + 142, cy, 28, 20).method_46431());
        next.field_22763 = castles.size() > 1;
        this.castleWidgets.add(next);
    }

    private void cycleCastle(int direction) {
        List<ClientCommandGroupStatus.Castle> castles = ClientCommandGroupStatus.castles();
        if (castles.isEmpty()) {
            return;
        }
        int index = 0;
        UUID selected = ClientCommandGroupStatus.selectedCastleId();
        for (int i = 0; i < castles.size(); ++i) {
            if (!castles.get(i).id().equals(selected)) continue;
            index = i;
            break;
        }
        int next = Math.floorMod(index + direction, castles.size());
        this.managementUngrouped = false;
        this.managementMemberScroll = 0;
        this.selectedScope = SoldierCommandNetworking.scopeForGroupSlot(1);
        this.groupPage = 0;
        CommandSelectionPreferences.setSelectedScope(this.selectedScope);
        ClientCommandGroupStatus.selectCastle(castles.get(next).id());
    }

    private void clearGroupWidgets() {
        for (class_4185 tile : this.groupTiles) {
            this.method_37066((class_364)tile);
        }
        for (class_4185 widget : this.groupAuxWidgets) {
            this.method_37066((class_364)widget);
        }
        this.groupTiles.clear();
        this.groupAuxWidgets.clear();
    }

    private void buildGroupTiles() {
        this.groupTiles.clear();
        this.groupAuxWidgets.clear();
        List<Integer> groups = this.visibleGroups();
        int pageCount = Math.max(1, (groups.size() + 6 - 1) / 6);
        this.groupPage = Math.max(0, Math.min(this.groupPage, pageCount - 1));
        int from = this.groupPage * 6;
        int to = Math.min(groups.size(), from + 6);
        List<Integer> pageGroups = groups.subList(from, to);
        int tileWidth = 82;
        int gap = 4;
        int navWidth = 24;
        int plusWidth = 36;
        int allWidth = 92;
        int contentWidth = pageGroups.size() * tileWidth + Math.max(0, pageGroups.size() - 1) * gap;
        int totalWidth = allWidth + gap + navWidth + gap + contentWidth + gap + navWidth + gap + plusWidth;
        int x = this.field_22789 / 2 - totalWidth / 2;
        int y = 62;
        class_4185 allGroups = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.all_groups"), b -> {
            this.selectedScope = 7;
            this.managementUngrouped = false;
            this.managementMemberScroll = 0;
            CommandSelectionPreferences.setSelectedScope(this.selectedScope);
            this.rebuildCustomGroupTiles();
            this.updateCurrentCommandButtons();
            if (this.panel == Panel.GROUPS) {
                this.rebuildPanel();
            }
        }).method_46434(x, y, allWidth, 32).method_46431());
        allGroups.field_22763 = this.selectedScope != 7;
        this.groupAuxWidgets.add(allGroups);
        class_4185 previous = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"<"), b -> {
            if (this.groupPage > 0) {
                --this.groupPage;
                this.rebuildCustomGroupTiles();
            }
        }).method_46434(x += allWidth + gap, y, navWidth, 32).method_46431());
        previous.field_22763 = this.groupPage > 0;
        this.groupAuxWidgets.add(previous);
        x += navWidth + gap;
        Iterator<Integer> iterator = pageGroups.iterator();
        while (iterator.hasNext()) {
            int group;
            int value = group = iterator.next().intValue();
            int scope = this.scopeForGroup(group);
            class_4185 tile = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43473(), b -> {
                this.selectedScope = scope;
                this.managementUngrouped = false;
                this.managementMemberScroll = 0;
                CommandSelectionPreferences.setSelectedScope(scope);
                this.rebuildCustomGroupTiles();
                this.updateCurrentCommandButtons();
                if (this.panel == Panel.GROUPS) {
                    this.rebuildPanel();
                }
            }).method_46434(x, y, tileWidth, 32).method_46431());
            this.groupTiles.add(tile);
            x += tileWidth + gap;
        }
        class_4185 next = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)">"), b -> {
            if (this.groupPage + 1 < pageCount) {
                ++this.groupPage;
                this.rebuildCustomGroupTiles();
            }
        }).method_46434(x, y, navWidth, 32).method_46431());
        next.field_22763 = this.groupPage + 1 < pageCount;
        this.groupAuxWidgets.add(next);
        int freeCustom = CommandGroupPreferences.firstFreeCustomGroup();
        class_4185 plus = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"+"), b -> {
            int group = CommandGroupPreferences.firstFreeCustomGroup();
            if (group >= 0 && this.field_22787 != null) {
                this.field_22787.method_1507((class_437)new CommandGroupRenameScreen(this, group));
            }
        }).method_46434(x += navWidth + gap, y, plusWidth, 32).method_46431());
        plus.field_22763 = freeCustom > 0;
        this.groupAuxWidgets.add(plus);
        this.refreshGroupTiles();
    }

    private List<Integer> visibleGroups() {
        ArrayList<Integer> groups = new ArrayList<Integer>();
        for (ClientCommandGroupStatus.Group group : ClientCommandGroupStatus.groups()) {
            groups.add(group.slot());
        }
        return groups;
    }

    private int scopeForGroup(int group) {
        return group == -1 ? 7 : SoldierCommandNetworking.scopeForGroupSlot(group);
    }

    private int groupForScope(int scope) {
        if (scope == 7) {
            return -1;
        }
        if (scope >= 1000) {
            int slot = scope - 1000;
            return slot > 0 ? slot : 0;
        }
        if (scope >= 2 && scope <= 5) {
            return scope - 1;
        }
        if (scope >= 7 && scope <= 12) {
            return scope - 2;
        }
        return 0;
    }

    private void ensureSelectedGroupPage() {
        if (this.selectedScope == 7) {
            this.groupPage = Math.max(0, this.groupPage);
            return;
        }
        int group = this.groupForScope(this.selectedScope);
        List<Integer> groups = this.visibleGroups();
        int index = groups.indexOf(group);
        this.groupPage = index < 0 ? 0 : index / 6;
    }

    private class_2561 groupTileText(int group) {
        return group == -1 ? class_2561.method_43471((String)"screen.medieval-machinery.command.all_groups") : CommandGroupPreferences.displayName(group);
    }

    public void refreshGroupTiles() {
        List<Integer> groups = this.visibleGroups();
        int from = Math.min(groups.size(), this.groupPage * 6);
        int to = Math.min(groups.size(), from + 6);
        List<Integer> pageGroups = groups.subList(from, to);
        for (int i = 0; i < pageGroups.size() && i < this.groupTiles.size(); ++i) {
            int group = pageGroups.get(i);
            class_4185 tile = this.groupTiles.get(i);
            tile.method_25355((class_2561)class_2561.method_43473());
            tile.field_22763 = this.scopeForGroup(group) != this.selectedScope;
        }
    }

    public void refreshFromServerSnapshot() {
        this.buildCastleSelector();
        this.ensureSelectedGroupPage();
        this.rebuildCustomGroupTiles();
        this.updateCurrentCommandButtons();
        if (this.panel == Panel.GROUPS) {
            this.rebuildPanel();
        }
    }

    public void rebuildCustomGroupTiles() {
        this.clearGroupWidgets();
        int selectedGroup = this.groupForScope(this.selectedScope);
        if (selectedGroup > 0 && ClientCommandGroupStatus.group(selectedGroup) == null) {
            this.selectedScope = SoldierCommandNetworking.scopeForGroupSlot(1);
            this.groupPage = 0;
            CommandSelectionPreferences.setSelectedScope(this.selectedScope);
        }
        this.buildGroupTiles();
        if (this.panel == Panel.GROUPS) {
            this.rebuildPanel();
        }
    }

    private void buildCategoryButtons() {
        int y = 104;
        int w = 112;
        int gap = 8;
        int start = this.field_22789 / 2 - (w * 3 + gap * 2) / 2;
        this.categoryButtons.add((class_4185)this.method_37063((class_364)this.categoryButton("screen.medieval-machinery.command.category.combat", Panel.COMBAT, start, y, w)));
        this.categoryButtons.add((class_4185)this.method_37063((class_364)this.categoryButton("screen.medieval-machinery.command.category.movement", Panel.MOVEMENT, start + w + gap, y, w)));
        this.categoryButtons.add((class_4185)this.method_37063((class_364)this.categoryButton("screen.medieval-machinery.command.category.organization", Panel.ORGANIZATION, start + (w + gap) * 2, y, w)));
        this.refreshCategoryButtons();
    }

    private class_4185 categoryButton(String key, Panel target, int x, int y, int width) {
        return class_4185.method_46430((class_2561)class_2561.method_43471((String)key), b -> this.setPanel(target)).method_46434(x, y, width, 20).method_46431();
    }

    private class_2561 targetPriorityText(NpcTargetPriority priority) {
        NpcTargetPriority resolved = priority == null ? NpcTargetPriority.CLOSEST : priority;
        return class_2561.method_43471((String)("screen.medieval-machinery.command.target_priority." + resolved.serializedName()));
    }

    private void updateTargetPriorityButton() {
        if (this.targetPriorityButton != null) {
            this.targetPriorityButton.method_25355((class_2561)class_2561.method_43469((String)"screen.medieval-machinery.command.target_priority.short", (Object[])new Object[]{this.targetPriorityText(this.selectedTargetPriority)}));
        }
    }

    private void cycleTargetPriority() {
        NpcTargetPriority[] values = NpcTargetPriority.values();
        this.selectedTargetPriority = values[(this.selectedTargetPriority.ordinal() + 1) % values.length];
        ClientSoldierCommandNetworking.setTargetPriority(this.selectedScope, this.selectedTargetPriority.ordinal());
        this.updateTargetPriorityButton();
    }

    private void setPanel(Panel target) {
        this.panel = target;
        this.refreshCategoryButtons();
        this.rebuildPanel();
        if (target == Panel.KINGDOM) {
            ClientKingdomNetworking.requestEmbedded();
        }
    }

    public void acceptKingdomSnapshot(ClientKingdomNetworking.Snapshot snapshot) {
        this.kingdomSnapshot = snapshot;
        OrderStyle orderStyle = this.selectedKingdomOrder = snapshot != null && snapshot.hasKingdom() ? snapshot.mainOrder() : OrderStyle.GOLD;
        if (this.panel != Panel.KINGDOM) {
            this.panel = Panel.KINGDOM;
        }
        this.refreshCategoryButtons();
        this.rebuildPanel();
    }

    private void refreshCategoryButtons() {
        if (this.categoryButtons.size() >= 3) {
            this.categoryButtons.get((int)0).field_22763 = this.panel != Panel.COMBAT;
            this.categoryButtons.get((int)1).field_22763 = this.panel != Panel.MOVEMENT;
            this.categoryButtons.get((int)2).field_22763 = this.panel != Panel.ORGANIZATION;
        }
    }

    private void clearPanelWidgets() {
        for (class_4185 widget : this.panelWidgets) {
            this.method_37066((class_364)widget);
        }
        this.panelWidgets.clear();
        this.transferDropdownWidgets.clear();
        for (class_342 field : this.panelFields) {
            this.method_37066((class_364)field);
        }
        this.panelFields.clear();
        this.kingdomNameField = null;
        this.kingdomInviteField = null;
        this.kingdomOrderButton = null;
        this.targetPriorityButton = null;
        this.stanceCommandButtons.clear();
        this.orderCommandButtons.clear();
        this.formationCommandButtons.clear();
        this.shieldCommandButtons.clear();
        this.holdFireButton = null;
        this.areaFireButton = null;
        this.fireAtWillButton = null;
    }

    private void rebuildPanel() {
        this.clearPanelWidgets();
        int cx = this.field_22789 / 2;
        int top = 142;
        switch (this.panel.ordinal()) {
            case 0: {
                int colW = 108;
                int colGap = 8;
                int totalW = colW * 4 + colGap * 3;
                int left = cx - totalW / 2;
                int row = 24;
                this.stanceCommandButtons.put(NpcCombatStance.PASSIVE, this.addPanelButton("screen.medieval-machinery.command.passive", left, top, colW, () -> this.stance(NpcCombatStance.PASSIVE)));
                this.stanceCommandButtons.put(NpcCombatStance.NEUTRAL, this.addPanelButton("screen.medieval-machinery.command.neutral", left, top + row, colW, () -> this.stance(NpcCombatStance.NEUTRAL)));
                this.stanceCommandButtons.put(NpcCombatStance.AGGRESSIVE, this.addPanelButton("screen.medieval-machinery.command.aggressive", left, top + row * 2, colW, () -> this.stance(NpcCombatStance.AGGRESSIVE)));
                this.stanceCommandButtons.put(NpcCombatStance.RAID, this.addPanelButton("screen.medieval-machinery.command.raid", left, top + row * 3, colW, () -> this.stance(NpcCombatStance.RAID)));
                int col2 = left + colW + colGap;
                this.addPanelButton("screen.medieval-machinery.command.forget_targets", col2, top, colW, () -> ClientSoldierCommandNetworking.forgetTargets(this.selectedScope));
                class_4185 attackTarget = this.addPanelButton("screen.medieval-machinery.command.attack_target", col2, top + row, colW, () -> ClientSoldierCommandNetworking.attackTarget(this.selectedScope, lastLookedAtEntityId));
                attackTarget.field_22763 = lastLookedAtEntityId >= 0;
                this.targetPriorityButton = this.addPanelButton("screen.medieval-machinery.command.target_priority.short", col2, top + row * 2, colW, this::cycleTargetPriority);
                this.updateTargetPriorityButton();
                int col3 = col2 + colW + colGap;
                this.holdFireButton = this.addPanelButton("screen.medieval-machinery.command.hold_fire", col3, top, colW, () -> ClientSoldierCommandNetworking.setHoldFire(this.selectedScope, true));
                this.fireAtWillButton = this.addPanelButton("screen.medieval-machinery.command.fire_at_will", col3, top + row, colW, () -> ClientSoldierCommandNetworking.setHoldFire(this.selectedScope, false));
                this.areaFireButton = this.addPanelButton("screen.medieval-machinery.command.area_volley", col3, top + row * 2, colW, () -> ClientSoldierCommandNetworking.areaVolley(this.selectedScope, lastLookedAtBlock));
                this.areaFireButton.field_22763 = lastLookedAtBlock != null;
                int col4 = col3 + colW + colGap;
                this.shieldCommandButtons.put(NpcShieldMode.AUTO, this.addPanelButton("screen.medieval-machinery.command.shield.auto", col4, top, colW, () -> ClientSoldierCommandNetworking.setShieldMode(this.selectedScope, NpcShieldMode.AUTO.ordinal())));
                this.shieldCommandButtons.put(NpcShieldMode.RAISED, this.addPanelButton("screen.medieval-machinery.command.shield.raised", col4, top + row, colW, () -> ClientSoldierCommandNetworking.setShieldMode(this.selectedScope, NpcShieldMode.RAISED.ordinal())));
                int formationY = top + 104;
                int tile = 28;
                int gap = 4;
                int total = tile * NpcFormation.values().length + gap * (NpcFormation.values().length - 1);
                int x = cx - total / 2;
                for (NpcFormation formation : NpcFormation.values()) {
                    class_4185 button = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43473(), b -> ClientSoldierCommandNetworking.setFormation(this.selectedScope, formation.ordinal())).method_46434(x, formationY, tile, tile).method_46431());
                    this.panelWidgets.add(button);
                    this.formationCommandButtons.put(formation, button);
                    x += tile + gap;
                }
                int formationControlY = formationY + 34;
                this.addPanelButton("screen.medieval-machinery.command.formation_tighter", cx - 166, formationControlY, 104, () -> ClientSoldierCommandNetworking.adjustFormationSpacing(this.selectedScope, -1));
                this.addPanelButton("screen.medieval-machinery.command.align_formation", cx - 52, formationControlY, 104, () -> ClientSoldierCommandNetworking.alignFormation(this.selectedScope));
                this.addPanelButton("screen.medieval-machinery.command.formation_wider", cx + 62, formationControlY, 104, () -> ClientSoldierCommandNetworking.adjustFormationSpacing(this.selectedScope, 1));
                break;
            }
            case 1: {
                this.orderCommandButtons.put(NpcOrderType.FOLLOW, this.addPanelButton("screen.medieval-machinery.command.follow", cx - 166, top + 10, 104, () -> this.order(NpcOrderType.FOLLOW)));
                this.orderCommandButtons.put(NpcOrderType.IDLE, this.addPanelButton("screen.medieval-machinery.command.idle", cx - 52, top + 10, 104, () -> this.order(NpcOrderType.IDLE)));
                this.orderCommandButtons.put(NpcOrderType.HOLD_POSITION, this.addPanelButton("screen.medieval-machinery.command.hold", cx + 62, top + 10, 104, () -> this.order(NpcOrderType.HOLD_POSITION)));
                this.orderCommandButtons.put(NpcOrderType.PATROL, this.addPanelButton("screen.medieval-machinery.command.patrol", cx - 166, top + 40, 104, () -> this.order(NpcOrderType.PATROL)));
                class_4185 move = this.addPanelButton("screen.medieval-machinery.command.move_to", cx - 52, top + 40, 104, this::moveToLookedAtBlock);
                this.addPanelButton("screen.medieval-machinery.command.my_position", cx + 62, top + 40, 104, this::moveToPlayerPosition);
                this.orderCommandButtons.put(NpcOrderType.MOVE_TO_POSITION, move);
                if (lastLookedAtBlock != null) break;
                move.field_22763 = false;
                break;
            }
            case 2: {
                this.addPanelButton("screen.medieval-machinery.command.supply", cx - 52, top + 10, 104, () -> ClientSoldierCommandNetworking.requestSupply(this.selectedScope, NpcSupplyRequest.SUPPLY.ordinal()));
                this.addPanelButton("screen.medieval-machinery.command.mount", cx - 108, top + 40, 104, () -> ClientSoldierCommandNetworking.mount(this.selectedScope));
                this.addPanelButton("screen.medieval-machinery.command.dismount", cx + 4, top + 40, 104, () -> ClientSoldierCommandNetworking.dismount(this.selectedScope));
                break;
            }
            case 3: {
                this.buildGroupManagement(top);
                break;
            }
            case 4: {
                this.buildKingdomPanel(top);
            }
        }
        this.updateCurrentCommandButtons();
    }

    private void updateCurrentCommandButtons() {
        int group = this.groupForScope(this.selectedScope);
        if (group <= 0) {
            return;
        }
        int currentStance = ClientCommandGroupStatus.stance(group);
        for (Map.Entry<NpcCombatStance, class_4185> entry : this.stanceCommandButtons.entrySet()) {
            entry.getValue().field_22763 = currentStance != ((NpcCombatStance)((Object)entry.getKey())).ordinal();
        }
        int currentOrder = ClientCommandGroupStatus.order(group);
        for (Map.Entry entry : this.orderCommandButtons.entrySet()) {
            class_4185 class_41852 = (class_4185)entry.getValue();
            if (entry.getKey() == NpcOrderType.MOVE_TO_POSITION && lastLookedAtBlock == null) {
                class_41852.field_22763 = false;
                continue;
            }
            class_41852.field_22763 = currentOrder != ((NpcOrderType)((Object)entry.getKey())).ordinal();
        }
        int currentFormation = ClientCommandGroupStatus.formation(group);
        for (Map.Entry<NpcFormation, class_4185> entry : this.formationCommandButtons.entrySet()) {
            entry.getValue().field_22763 = currentFormation != entry.getKey().ordinal();
        }
        int n = ClientCommandGroupStatus.holdFire(group);
        if (this.holdFireButton != null) {
            boolean bl = this.holdFireButton.field_22763 = n != 1;
        }
        if (this.fireAtWillButton != null) {
            this.fireAtWillButton.field_22763 = n != 0;
        }
        int n2 = ClientCommandGroupStatus.areaFire(group);
        if (this.areaFireButton != null) {
            this.areaFireButton.field_22763 = n2 != 1 && lastLookedAtBlock != null;
        }
        int currentShield = ClientCommandGroupStatus.shieldMode(group);
        for (Map.Entry<NpcShieldMode, class_4185> entry : this.shieldCommandButtons.entrySet()) {
            entry.getValue().field_22763 = currentShield != entry.getKey().ordinal();
        }
    }

    private class_4185 addPanelButton(String key, int x, int y, int width, Runnable action) {
        class_4185 button = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)key), b -> action.run()).method_46434(x, y, width, 20).method_46431());
        this.panelWidgets.add(button);
        return button;
    }

    private void buildGroupManagement(int top) {
        int cx = this.field_22789 / 2;
        int selectedGroup = Math.max(1, this.groupForScope(this.selectedScope));
        int shownGroup = this.managementUngrouped ? 0 : selectedGroup;
        class_4185 noGroup = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.ungrouped"), b -> {
            this.managementUngrouped = !this.managementUngrouped;
            this.managementMemberScroll = 0;
            this.rebuildPanel();
        }).method_46434(cx + 85, top + 4, 96, 20).method_46431());
        noGroup.field_22763 = !this.managementUngrouped;
        this.panelWidgets.add(noGroup);
        if (!this.managementUngrouped && selectedGroup >= 6) {
            class_4185 rename = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.rename_short"), b -> {
                if (this.field_22787 != null) {
                    this.field_22787.method_1507((class_437)new CommandGroupRenameScreen(this, selectedGroup));
                }
            }).method_46434(cx - 181, top + 4, 86, 20).method_46431());
            this.panelWidgets.add(rename);
            class_4185 delete = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.delete"), b -> this.deleteSelectedCustomGroup(selectedGroup)).method_46434(cx - 89, top + 4, 86, 20).method_46431());
            this.panelWidgets.add(delete);
        }
        List<ClientCommandGroupStatus.Member> members = ClientCommandGroupStatus.membersForGroup(shownGroup);
        boolean showTransfer = !this.managementUngrouped && ClientCommandGroupStatus.group(selectedGroup) != null;
        int bottomNavigationY = Math.min(this.field_22790 - 30, this.field_22790 / 2 + 152);
        int transferY = bottomNavigationY - 26;
        int memberStartY = top + 34;
        int availableMemberHeight = showTransfer ? Math.max(23, transferY - memberStartY - 4) : 138;
        int visibleRows = Math.max(1, Math.min(5, availableMemberHeight / 23));
        int maxScroll = Math.max(0, members.size() - visibleRows);
        this.managementMemberScroll = Math.max(0, Math.min(this.managementMemberScroll, maxScroll));
        int y = memberStartY;
        int rows = Math.min(visibleRows, members.size() - this.managementMemberScroll);
        for (int row = 0; row < rows; ++row) {
            ClientCommandGroupStatus.Member member = members.get(this.managementMemberScroll + row);
            String status = member.name() + "   " + Math.round(member.health()) + "/" + Math.round(member.maxHealth()) + (String)(member.loaded() ? "" : "  [" + class_2561.method_43471((String)"screen.medieval-machinery.command.group.unloaded").getString() + "]");
            class_4185 info = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)status), b -> {}).method_46434(cx - 231, y, 320, 20).method_46431());
            info.field_22763 = false;
            this.panelWidgets.add(info);
            if (this.managementUngrouped) {
                class_4185 add = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.add"), b -> {
                    ClientSoldierCommandNetworking.assignGroupPersistent(member.soldierId(), selectedGroup);
                    ClientCommandGroupStatus.request();
                }).method_46434(cx + 95, y, 86, 20).method_46431());
                this.panelWidgets.add(add);
            } else {
                class_4185 remove = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.remove"), b -> {
                    ClientSoldierCommandNetworking.assignGroupPersistent(member.soldierId(), 0);
                    ClientCommandGroupStatus.request();
                }).method_46434(cx + 95, y, 86, 20).method_46431());
                this.panelWidgets.add(remove);
            }
            y += 23;
        }
        if (members.isEmpty()) {
            class_4185 empty = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.no_members"), b -> {}).method_46434(cx - 160, top + 62, 260, 20).method_46431());
            empty.field_22763 = false;
            this.panelWidgets.add(empty);
        }
        if (showTransfer) {
            int transferWidth = 206;
            int gap = 8;
            int total = transferWidth * 2 + gap;
            int left = cx - total / 2;
            class_4185 playerTransfer = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Gruppe an Spieler \u00fcbergeben"), b -> {
                this.transferPlayerDropdownOpen = !this.transferPlayerDropdownOpen;
                this.transferCastleDropdownOpen = false;
                this.rebuildPanel();
            }).method_46434(left, transferY, transferWidth, 20).method_46431());
            this.panelWidgets.add(playerTransfer);
            class_4185 castleTransfer = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Gruppe an Burg \u00fcbergeben"), b -> {
                this.transferCastleDropdownOpen = !this.transferCastleDropdownOpen;
                this.transferPlayerDropdownOpen = false;
                this.rebuildPanel();
            }).method_46434(left + transferWidth + gap, transferY, transferWidth, 20).method_46431());
            this.panelWidgets.add(castleTransfer);
            if (this.transferPlayerDropdownOpen) {
                playerTransfer.field_22764 = false;
                this.buildPlayerTransferDropdown(selectedGroup, left, transferY - 2);
            } else if (this.transferCastleDropdownOpen) {
                castleTransfer.field_22764 = false;
                this.buildCastleTransferDropdown(selectedGroup, left + transferWidth + gap, transferY - 2);
            }
        }
    }

    private void buildPlayerTransferDropdown(int group, int x, int anchorY) {
        if (this.field_22787 == null || this.field_22787.method_1562() == null) {
            return;
        }
        ArrayList<class_640> entries = new ArrayList<class_640>(this.field_22787.method_1562().method_2880());
        entries.removeIf(entry -> this.field_22787.field_1724 != null && entry.method_2966().getId().equals(this.field_22787.field_1724.method_5667()));
        entries.sort((a, b) -> {
            boolean bSameTeam;
            boolean aSameTeam = this.field_22787.field_1724 != null && this.field_22787.field_1687 != null && this.field_22787.field_1687.method_18470(a.method_2966().getId()) != null && Objects.equals(this.field_22787.field_1687.method_18470(a.method_2966().getId()).method_5781(), this.field_22787.field_1724.method_5781());
            boolean bl = bSameTeam = this.field_22787.field_1724 != null && this.field_22787.field_1687 != null && this.field_22787.field_1687.method_18470(b.method_2966().getId()) != null && Objects.equals(this.field_22787.field_1687.method_18470(b.method_2966().getId()).method_5781(), this.field_22787.field_1724.method_5781());
            if (aSameTeam != bSameTeam) {
                return aSameTeam ? -1 : 1;
            }
            return a.method_2966().getName().compareToIgnoreCase(b.method_2966().getName());
        });
        int visible = Math.min(6, entries.size());
        int y = anchorY - visible * 20;
        for (int i = 0; i < visible; ++i) {
            String playerName = ((class_640)entries.get(i)).method_2966().getName();
            class_4185 option = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)playerName), b -> {
                ClientSoldierCommandNetworking.transferGroup(group, playerName);
                this.transferPlayerDropdownOpen = false;
                ClientCommandGroupStatus.request();
                this.rebuildPanel();
            }).method_46434(x, y + i * 20, 206, 20).method_46431());
            this.panelWidgets.add(option);
            this.transferDropdownWidgets.add(option);
        }
    }

    private void buildCastleTransferDropdown(int group, int x, int anchorY) {
        ArrayList<ClientCommandGroupStatus.Castle> castles = new ArrayList<ClientCommandGroupStatus.Castle>(ClientCommandGroupStatus.castles());
        castles.removeIf(castle -> "Ohne Burg".equalsIgnoreCase(castle.name()));
        castles.sort(Comparator.comparing(ClientCommandGroupStatus.Castle::name, String.CASE_INSENSITIVE_ORDER));
        int visible = Math.min(6, castles.size() + 1);
        int y = anchorY - visible * 20;
        class_4185 none = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"Ohne Burg"), b -> {
            ClientSoldierCommandNetworking.transferGroupToCastle(group, null);
            this.transferCastleDropdownOpen = false;
            ClientCommandGroupStatus.request();
            this.rebuildPanel();
        }).method_46434(x, y, 206, 20).method_46431());
        this.panelWidgets.add(none);
        this.transferDropdownWidgets.add(none);
        for (int i = 0; i < Math.min(5, castles.size()); ++i) {
            ClientCommandGroupStatus.Castle castle2 = (ClientCommandGroupStatus.Castle)castles.get(i);
            class_4185 option = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)castle2.name()), b -> {
                ClientSoldierCommandNetworking.transferGroupToCastle(group, castle2.id());
                this.transferCastleDropdownOpen = false;
                ClientCommandGroupStatus.request();
                this.rebuildPanel();
            }).method_46434(x, y + (i + 1) * 20, 206, 20).method_46431());
            this.panelWidgets.add(option);
            this.transferDropdownWidgets.add(option);
        }
    }

    private void deleteSelectedCustomGroup(int group) {
        if (!CommandGroupPreferences.isCustomEnabled(group)) {
            return;
        }
        ClientSoldierCommandNetworking.deleteGroup(group);
        this.selectedScope = SoldierCommandNetworking.scopeForGroupSlot(1);
        this.managementUngrouped = false;
        this.managementMemberScroll = 0;
        CommandSelectionPreferences.setSelectedScope(this.selectedScope);
        this.rebuildCustomGroupTiles();
        ClientCommandGroupStatus.request();
    }

    private void buildKingdomPanel(int top) {
        int cx = this.field_22789 / 2;
        int left = cx - 170;
        int panelWidth = 340;
        if (this.kingdomSnapshot == null) {
            class_4185 loading = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.kingdom.loading"), b -> ClientKingdomNetworking.requestEmbedded()).method_46434(cx - 100, top + 30, 200, 20).method_46431());
            loading.field_22763 = false;
            this.panelWidgets.add(loading);
            return;
        }
        this.kingdomNameField = new class_342(this.field_22793, left + 12, top + 24, panelWidth - 24, 20, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.name"));
        this.kingdomNameField.method_1880(48);
        this.kingdomNameField.method_1852(this.kingdomSnapshot.hasKingdom() ? this.kingdomSnapshot.name() : "");
        if (this.kingdomSnapshot.hasKingdom() && !this.kingdomSnapshot.king()) {
            this.kingdomNameField.method_1888(false);
        }
        this.method_37063((class_364)this.kingdomNameField);
        this.panelFields.add(this.kingdomNameField);
        class_4185 previous = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"<"), b -> this.cycleKingdomOrder(-1)).method_46434(left + 12, top + 50, 28, 20).method_46431());
        this.panelWidgets.add(previous);
        this.kingdomOrderButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)this.kingdomOrderText(), b -> this.cycleKingdomOrder(1)).method_46434(left + 44, top + 50, panelWidth - 88, 20).method_46431());
        this.panelWidgets.add(this.kingdomOrderButton);
        class_4185 next = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)">"), b -> this.cycleKingdomOrder(1)).method_46434(left + panelWidth - 40, top + 50, 28, 20).method_46431());
        this.panelWidgets.add(next);
        if (!this.kingdomSnapshot.hasKingdom()) {
            if (this.kingdomSnapshot.hasInvite()) {
                class_4185 accept = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.accept_invite"), b -> ClientKingdomNetworking.acceptInvite()).method_46434(left + 12, top + 82, 150, 20).method_46431());
                this.panelWidgets.add(accept);
                class_4185 decline = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.decline_invite"), b -> ClientKingdomNetworking.declineInvite()).method_46434(left + 178, top + 82, 150, 20).method_46431());
                this.panelWidgets.add(decline);
            }
            class_4185 create = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.create"), b -> {
                String name;
                String string = name = this.kingdomNameField == null ? "" : this.kingdomNameField.method_1882().trim();
                if (!name.isEmpty()) {
                    ClientKingdomNetworking.createKingdom(name, this.selectedKingdomOrder);
                }
            }).method_46434(left + 12, top + 112, panelWidth - 24, 20).method_46431());
            this.panelWidgets.add(create);
            return;
        }
        previous.field_22763 = this.kingdomSnapshot.king();
        next.field_22763 = this.kingdomSnapshot.king();
        this.kingdomOrderButton.field_22763 = this.kingdomSnapshot.king();
        if (this.kingdomSnapshot.king()) {
            class_4185 rename = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.rename"), b -> {
                String name;
                String string = name = this.kingdomNameField == null ? "" : this.kingdomNameField.method_1882().trim();
                if (!name.isEmpty()) {
                    ClientKingdomNetworking.rename(name);
                }
            }).method_46434(left + 12, top + 82, 150, 20).method_46431());
            this.panelWidgets.add(rename);
            class_4185 changeOrder = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.change_order"), b -> ClientKingdomNetworking.changeOrder(this.selectedKingdomOrder)).method_46434(left + 178, top + 82, 150, 20).method_46431());
            this.panelWidgets.add(changeOrder);
        }
        if (this.kingdomSnapshot.canManageMembers()) {
            this.kingdomInviteField = new class_342(this.field_22793, left + 12, top + 112, 198, 20, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.player_name"));
            this.kingdomInviteField.method_1880(16);
            this.method_37063((class_364)this.kingdomInviteField);
            this.panelFields.add(this.kingdomInviteField);
            class_4185 invite = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.invite"), b -> this.withKingdomTarget(ClientKingdomNetworking::invite)).method_46434(left + 218, top + 112, 110, 20).method_46431());
            this.panelWidgets.add(invite);
            if (this.kingdomSnapshot.king()) {
                class_4185 promote = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.promote"), b -> this.withKingdomTarget(ClientKingdomNetworking::promote)).method_46434(left + 12, top + 140, 100, 20).method_46431());
                this.panelWidgets.add(promote);
                class_4185 demote = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.demote"), b -> this.withKingdomTarget(ClientKingdomNetworking::demote)).method_46434(left + 120, top + 140, 100, 20).method_46431());
                this.panelWidgets.add(demote);
                class_4185 kick = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.kick"), b -> this.withKingdomTarget(ClientKingdomNetworking::kick)).method_46434(left + 228, top + 140, 100, 20).method_46431());
                this.panelWidgets.add(kick);
            }
        }
        if (!this.kingdomSnapshot.king()) {
            class_4185 leave = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.leave"), b -> ClientKingdomNetworking.leave()).method_46434(left + 12, top + 140, 150, 20).method_46431());
            this.panelWidgets.add(leave);
        }
    }

    private void renderFormationTiles(class_332 context, int mouseX, int mouseY, int panelTop) {
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.formation.heading"), this.field_22789 / 2, panelTop + 91, 0xD9D9D9);
        for (Map.Entry<NpcFormation, class_4185> entry : this.formationCommandButtons.entrySet()) {
            class_4185 button = entry.getValue();
            int x = button.method_46426();
            int y = button.method_46427();
            int w = button.method_25368();
            int h = button.method_25364();
            context.method_25294(x + 2, y + 2, x + w - 2, y + h - 2, button.field_22763 ? 0x66000000 : -1442840576);
            this.drawFormationGlyph(context, entry.getKey(), x + w / 2, y + h / 2, button.field_22763 ? -15658735 : -855310);
            if (mouseX < x || mouseX >= x + w || mouseY < y || mouseY >= y + h) continue;
            context.method_51438(this.field_22793, this.formationName(entry.getKey()), mouseX, mouseY);
        }
    }

    private class_2561 formationName(NpcFormation formation) {
        String key = switch (formation) {
            default -> throw new IncompatibleClassChangeError();
            case NpcFormation.FREE -> "free";
            case NpcFormation.LINE -> "line";
            case NpcFormation.SQUARE -> "square";
            case NpcFormation.TRIANGLE -> "triangle";
            case NpcFormation.HALF_CIRCLE -> "half_circle";
            case NpcFormation.HOLLOW_SQUARE -> "hollow_square";
            case NpcFormation.V_FORM -> "v_form";
            case NpcFormation.CIRCLE -> "circle";
            case NpcFormation.MOVEMENT -> "movement";
        };
        return class_2561.method_43471((String)("screen.medieval-machinery.command.formation." + key));
    }

    private void drawFormationGlyph(class_332 context, NpcFormation formation, int cx, int cy, int c) {
        switch (formation) {
            case FREE: {
                this.drawPixelLine(context, cx - 8, cy - 8, cx + 8, cy + 8, c);
                this.drawPixelLine(context, cx + 8, cy - 8, cx - 8, cy + 8, c);
                break;
            }
            case LINE: {
                context.method_25294(cx - 10, cy - 1, cx + 11, cy + 2, c);
                break;
            }
            case SQUARE: {
                context.method_25294(cx - 8, cy - 8, cx + 9, cy + 9, c);
                break;
            }
            case TRIANGLE: {
                this.drawPixelLine(context, cx, cy - 10, cx - 10, cy + 8, c);
                this.drawPixelLine(context, cx - 10, cy + 8, cx + 10, cy + 8, c);
                this.drawPixelLine(context, cx + 10, cy + 8, cx, cy - 10, c);
                break;
            }
            case HALF_CIRCLE: {
                this.drawPixelLine(context, cx - 10, cy + 6, cx - 8, cy - 1, c);
                this.drawPixelLine(context, cx - 8, cy - 1, cx - 4, cy - 6, c);
                this.drawPixelLine(context, cx - 4, cy - 6, cx + 4, cy - 6, c);
                this.drawPixelLine(context, cx + 4, cy - 6, cx + 8, cy - 1, c);
                this.drawPixelLine(context, cx + 8, cy - 1, cx + 10, cy + 6, c);
                break;
            }
            case HOLLOW_SQUARE: {
                context.method_25294(cx - 9, cy - 9, cx + 10, cy - 6, c);
                context.method_25294(cx - 9, cy + 7, cx + 10, cy + 10, c);
                context.method_25294(cx - 9, cy - 6, cx - 6, cy + 7, c);
                context.method_25294(cx + 7, cy - 6, cx + 10, cy + 7, c);
                break;
            }
            case V_FORM: {
                this.drawPixelLine(context, cx - 10, cy - 8, cx, cy + 9, c);
                this.drawPixelLine(context, cx + 10, cy - 8, cx, cy + 9, c);
                break;
            }
            case CIRCLE: {
                context.method_25294(cx - 5, cy - 10, cx + 6, cy - 7, c);
                context.method_25294(cx - 9, cy - 7, cx - 5, cy - 3, c);
                context.method_25294(cx + 6, cy - 7, cx + 10, cy - 3, c);
                context.method_25294(cx - 11, cy - 3, cx - 8, cy + 5, c);
                context.method_25294(cx + 8, cy - 3, cx + 11, cy + 5, c);
                context.method_25294(cx - 9, cy + 5, cx - 5, cy + 8, c);
                context.method_25294(cx + 6, cy + 5, cx + 10, cy + 8, c);
                context.method_25294(cx - 5, cy + 8, cx + 6, cy + 11, c);
                break;
            }
            case MOVEMENT: {
                context.method_25294(cx - 5, cy - 9, cx - 2, cy + 7, c);
                context.method_25294(cx + 2, cy - 9, cx + 5, cy + 7, c);
                this.drawPixelLine(context, cx - 7, cy + 3, cx, cy + 10, c);
                this.drawPixelLine(context, cx + 7, cy + 3, cx, cy + 10, c);
            }
        }
    }

    private void drawPixelLine(class_332 context, int x0, int y0, int x1, int y1, int color) {
        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;
        int err = dx + dy;
        while (true) {
            context.method_25294(x0 - 1, y0 - 1, x0 + 2, y0 + 2, color);
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 >= dy) {
                err += dy;
                x0 += sx;
            }
            if (e2 > dx) continue;
            err += dx;
            y0 += sy;
        }
    }

    private void withKingdomTarget(Consumer<String> action) {
        if (this.kingdomInviteField == null) {
            return;
        }
        String target = this.kingdomInviteField.method_1882().trim();
        if (!target.isEmpty()) {
            action.accept(target);
        }
    }

    private void cycleKingdomOrder(int direction) {
        int index = 0;
        for (int i = 0; i < KINGDOM_ORDERS.length; ++i) {
            if (KINGDOM_ORDERS[i] != this.selectedKingdomOrder) continue;
            index = i;
            break;
        }
        this.selectedKingdomOrder = KINGDOM_ORDERS[Math.floorMod(index + direction, KINGDOM_ORDERS.length)];
        if (this.kingdomOrderButton != null) {
            this.kingdomOrderButton.method_25355(this.kingdomOrderText());
        }
    }

    private class_2561 kingdomOrderText() {
        return class_2561.method_43471((String)("order.medieval-machinery." + this.selectedKingdomOrder.method_15434()));
    }

    private class_2561 kingdomRoleText() {
        if (this.kingdomSnapshot == null || !this.kingdomSnapshot.hasKingdom()) {
            return class_2561.method_43470((String)"-");
        }
        return class_2561.method_43471((String)("kingdom.role.medieval-machinery." + this.kingdomSnapshot.role().toLowerCase(Locale.ROOT)));
    }

    private void order(NpcOrderType order) {
        ClientSoldierCommandNetworking.setOrder(this.selectedScope, order.ordinal());
        ClientCommandGroupStatus.request();
    }

    private void stance(NpcCombatStance stance) {
        ClientSoldierCommandNetworking.setStance(this.selectedScope, stance.ordinal());
        ClientCommandGroupStatus.request();
    }

    private void moveToPlayerPosition() {
        if (this.field_22787 == null || this.field_22787.field_1724 == null) {
            return;
        }
        ClientSoldierCommandNetworking.moveTo(this.selectedScope, this.field_22787.field_1724.method_24515());
    }

    private void moveToLookedAtBlock() {
        if (lastLookedAtBlock != null) {
            ClientSoldierCommandNetworking.moveTo(this.selectedScope, lastLookedAtBlock);
        }
    }

    private int groupManagementVisibleRows(int selectedGroup) {
        boolean showTransfer;
        boolean bl = showTransfer = !this.managementUngrouped && ClientCommandGroupStatus.group(selectedGroup) != null;
        if (!showTransfer) {
            return 5;
        }
        int top = 146;
        int memberStartY = top + 34;
        int bottomNavigationY = Math.min(this.field_22790 - 30, this.field_22790 / 2 + 152);
        int transferY = bottomNavigationY - 26;
        int availableMemberHeight = Math.max(23, transferY - memberStartY - 4);
        return Math.max(1, Math.min(5, availableMemberHeight / 23));
    }

    public boolean method_25401(double mouseX, double mouseY, double amount) {
        if (this.panel == Panel.GROUPS) {
            int selectedGroup = Math.max(1, this.groupForScope(this.selectedScope));
            int shownGroup = this.managementUngrouped ? 0 : selectedGroup;
            int visibleRows = this.groupManagementVisibleRows(selectedGroup);
            int maxScroll = Math.max(0, ClientCommandGroupStatus.membersForGroup(shownGroup).size() - visibleRows);
            if (amount < 0.0) {
                this.managementMemberScroll = Math.min(maxScroll, this.managementMemberScroll + 1);
            }
            if (amount > 0.0) {
                this.managementMemberScroll = Math.max(0, this.managementMemberScroll - 1);
            }
            this.rebuildPanel();
            return true;
        }
        return super.method_25401(mouseX, mouseY, amount);
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        context.method_25294(0, 0, this.field_22789, this.field_22790, -2013265920);
        int groupY = 62;
        int categoryY = 104;
        int panelTop = 142;
        int panelBottom = Math.min(this.field_22790 - 42, this.field_22790 / 2 + 137);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, 8, 0xFFFFFF);
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.groups_heading"), this.field_22789 / 2, 46, 0xD9D9D9);
        List<Integer> groups = this.visibleGroups();
        int from = Math.min(groups.size(), this.groupPage * 6);
        int to = Math.min(groups.size(), from + 6);
        List<Integer> renderedGroups = groups.subList(from, to);
        for (int i = 0; i < renderedGroups.size() && i < this.groupTiles.size(); ++i) {
            int group = renderedGroups.get(i);
            class_4185 tile = this.groupTiles.get(i);
            String count = ClientCommandGroupStatus.inRange(group) + "/" + ClientCommandGroupStatus.total(group);
            context.method_27534(this.field_22793, this.groupTileText(group), tile.method_46426() + tile.method_25368() / 2, tile.method_46427() + 4, tile.field_22763 ? 0xFFFFFF : 0xFFF2C2);
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43470((String)count), tile.method_46426() + tile.method_25368() / 2, tile.method_46427() + 18, tile.field_22763 ? 0xC7C7C7 : 0xFFFFFF);
        }
        context.method_27534(this.field_22793, this.panelTitle(), this.field_22789 / 2, panelTop - 13, 14862202);
        if (this.panel == Panel.MOVEMENT && lastLookedAtBlock != null) {
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.command.look_target", (Object[])new Object[]{lastLookedAtBlock.method_10263(), lastLookedAtBlock.method_10264(), lastLookedAtBlock.method_10260()}), this.field_22789 / 2, panelBottom - 15, 0xB8D8B8);
        }
        if (this.panel == Panel.KINGDOM && this.kingdomSnapshot != null) {
            if (this.kingdomSnapshot.hasKingdom()) {
                context.method_27534(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.kingdom.info_line", (Object[])new Object[]{this.kingdomRoleText(), this.kingdomSnapshot.memberCount(), this.kingdomSnapshot.kingdomId().toString().substring(0, 8)}), this.field_22789 / 2, panelTop + 170, 0xD0D0D0);
            } else {
                context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.kingdom.not_created"), this.field_22789 / 2, panelTop + 150, 0xD0D0D0);
                class_1792 material = OrderMaterials.materialFor(this.selectedKingdomOrder);
                if (material != null) {
                    context.method_27534(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.kingdom.foundation_cost", (Object[])new Object[]{new class_1799((class_1935)material).method_7964()}), this.field_22789 / 2, panelTop + 164, 0xC8C8C8);
                }
            }
        }
        super.method_25394(context, mouseX, mouseY, delta);
        if (this.panel == Panel.COMBAT) {
            this.renderFormationTiles(context, mouseX, mouseY, panelTop);
        }
        if (this.panel == Panel.GROUPS) {
            int selectedGroup = Math.max(1, this.groupForScope(this.selectedScope));
            int shownGroup = this.managementUngrouped ? 0 : selectedGroup;
            List<ClientCommandGroupStatus.Member> members = ClientCommandGroupStatus.membersForGroup(shownGroup);
            int visibleRows = this.groupManagementVisibleRows(selectedGroup);
            int rows = Math.min(visibleRows, Math.max(0, members.size() - this.managementMemberScroll));
            if (members.size() > 5) {
                int maxScroll = Math.max(1, members.size() - visibleRows);
                int trackX = this.field_22789 / 2 - 243;
                int trackY = panelTop + 34;
                int trackHeight = visibleRows * 23 - 3;
                int thumbHeight = Math.max(14, Math.round((float)trackHeight * ((float)visibleRows / (float)members.size())));
                int thumbTravel = Math.max(0, trackHeight - thumbHeight);
                int thumbY = trackY + Math.round((float)thumbTravel * ((float)this.managementMemberScroll / (float)maxScroll));
                context.method_25294(trackX, trackY, trackX + 8, trackY + trackHeight, -14671840);
                context.method_25294(trackX + 1, trackY + 1, trackX + 7, trackY + trackHeight - 1, -11908534);
                context.method_25294(trackX + 1, thumbY, trackX + 7, thumbY + thumbHeight, -3750202);
                context.method_25294(trackX + 2, thumbY + 1, trackX + 6, thumbY + thumbHeight - 1, -7631989);
            }
            int rowY = panelTop + 34;
            for (int row = 0; row < rows; ++row) {
                ClientCommandGroupStatus.Member member = members.get(this.managementMemberScroll + row);
                if (!member.hasFood()) {
                    context.method_27535(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.no_food"), this.field_22789 / 2 - 32, rowY + 6, -43691);
                }
                if (!member.hasWeapon()) {
                    context.method_27535(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.command.group.no_weapon"), this.field_22789 / 2 + 28, rowY + 6, -43691);
                }
                rowY += 23;
            }
        }
        if (this.panel == Panel.GROUPS && (this.transferPlayerDropdownOpen || this.transferCastleDropdownOpen) && !this.transferDropdownWidgets.isEmpty()) {
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (class_4185 widget : this.transferDropdownWidgets) {
                minX = Math.min(minX, widget.method_46426());
                minY = Math.min(minY, widget.method_46427());
                maxX = Math.max(maxX, widget.method_46426() + widget.method_25368());
                maxY = Math.max(maxY, widget.method_46427() + widget.method_25364());
            }
            context.method_25294(minX - 5, minY - 5, maxX + 5, maxY + 5, -14737633);
            context.method_25294(minX - 3, minY - 3, maxX + 3, maxY + 3, -3750202);
            context.method_25294(minX - 1, minY - 1, maxX + 1, maxY + 1, -11184811);
            for (class_4185 widget : this.transferDropdownWidgets) {
                widget.method_25394(context, mouseX, mouseY, delta);
            }
        }
    }

    private class_2561 panelTitle() {
        return switch (this.panel.ordinal()) {
            default -> throw new IncompatibleClassChangeError();
            case 0 -> class_2561.method_43471((String)"screen.medieval-machinery.command.category.combat");
            case 1 -> class_2561.method_43471((String)"screen.medieval-machinery.command.category.movement");
            case 2 -> class_2561.method_43471((String)"screen.medieval-machinery.command.category.organization");
            case 3 -> class_2561.method_43471((String)"screen.medieval-machinery.command.groups");
            case 4 -> class_2561.method_43471((String)"screen.medieval-machinery.command.kingdom");
        };
    }

    public boolean method_25421() {
        return false;
    }

    static {
        lastLookedAtEntityId = -1;
        KINGDOM_ORDERS = (OrderStyle[])Arrays.stream(OrderStyle.values()).filter(style -> style != OrderStyle.NONE).toArray(OrderStyle[]::new);
    }

    @Environment(value=EnvType.CLIENT)
    private static enum Panel {
        COMBAT,
        MOVEMENT,
        ORGANIZATION,
        GROUPS,
        KINGDOM;

    }
}

