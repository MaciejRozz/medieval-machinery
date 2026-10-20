/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_332
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_465
 *  net.minecraft.class_490
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.client.command.ClientCommandGroupStatus;
import com.medievalmachinery.mod.client.network.ClientSoldierCommandNetworking;
import com.medievalmachinery.mod.client.network.ClientSoldierNetworking;
import com.medievalmachinery.mod.client.npc.GeneralBodyguardManageScreen;
import com.medievalmachinery.mod.client.npc.QuartermasterFarmManageScreen;
import com.medievalmachinery.mod.client.npc.ScoutObservationEditorClient;
import com.medievalmachinery.mod.client.npc.SoldierRenameScreen;
import com.medievalmachinery.mod.client.npc.SoldierTransferSelectScreen;
import com.medievalmachinery.mod.item.OrderArmorSetBonusService;
import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcPreferredMeleeWeapon;
import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.core.NpcTargetPriority;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.screen.npc.SoldierInventoryScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_465;
import net.minecraft.class_490;
import net.minecraft.class_5250;
import net.minecraft.class_5348;

@Environment(value=EnvType.CLIENT)
public final class SoldierInventoryScreen
extends class_465<SoldierInventoryScreenHandler> {
    private static final class_2960 VANILLA_CONTAINER = new class_2960("minecraft", "textures/gui/container/generic_54.png");
    private static final class_2960 EMPTY_HELMET = new class_2960("minecraft", "textures/item/empty_armor_slot_helmet.png");
    private static final class_2960 EMPTY_CHEST = new class_2960("minecraft", "textures/item/empty_armor_slot_chestplate.png");
    private static final class_2960 EMPTY_LEGS = new class_2960("minecraft", "textures/item/empty_armor_slot_leggings.png");
    private static final class_2960 EMPTY_BOOTS = new class_2960("minecraft", "textures/item/empty_armor_slot_boots.png");
    private static final class_2960 EMPTY_SHIELD = new class_2960("minecraft", "textures/item/empty_armor_slot_shield.png");
    private static final class_2960 SWORD_HINT = new class_2960("minecraft", "textures/item/iron_sword.png");
    private static final class_2960 AXE_HINT = new class_2960("minecraft", "textures/item/iron_axe.png");
    private static final class_2960 BOW_HINT = new class_2960("minecraft", "textures/item/bow.png");
    private static final class_2960 CROSSBOW_HINT = new class_2960("minecraft", "textures/item/crossbow_standby.png");
    private static final class_2960 PICKAXE_HINT = new class_2960("minecraft", "textures/item/iron_pickaxe.png");
    private static final class_2960 CANNONBALL_HINT = new class_2960("medieval-machinery", "textures/item/stone_cannonball.png");
    private static final class_2960 WHEAT_HINT = new class_2960("minecraft", "textures/item/wheat.png");
    private static final class_2960[] ARMOR_HINTS = new class_2960[]{EMPTY_HELMET, EMPTY_CHEST, EMPTY_LEGS, EMPTY_BOOTS};
    private class_4185 renameButton;
    private class_4185 groupButton;
    private final List<class_4185> stanceButtons = new ArrayList<class_4185>();
    private final List<class_4185> orderButtons = new ArrayList<class_4185>();
    private class_4185 patrolOrderButton;
    private class_4185 idleOrderButton;
    private class_4185 patrolEditButton;
    private class_4185 targetPriorityButton;
    private class_4185 holdFireButton;
    private class_4185 fireAtWillButton;
    private class_4185 shieldAutoButton;
    private class_4185 shieldRaisedButton;
    private class_4185 preferredWeaponButton;
    private class_4185 machineAssignmentButton;
    private class_4185 machineClearButton;
    private class_4185 preferredSupplyButton;
    private class_4185 mountButton;
    private class_4185 dismountButton;
    private class_4185 clearPreferredSupplyButton;
    private class_4185 bodyguardManageButton;
    private class_4185 observationPointButton;
    private class_4185 quartermasterFarmButton;
    private class_4185 quartermasterWorkButton;
    private NpcOrderType pendingOrder;
    private NpcCombatStance pendingStance;
    private boolean pendingMoveToPlayer;
    private boolean orderSelectionDirty;
    private boolean stanceSelectionDirty;
    private boolean pendingHoldFire;
    private NpcShieldMode pendingShieldMode;
    private boolean pendingCommandsApplied;
    private int groupContextRefreshTicks;
    private boolean groupDropdownOpen;
    private boolean draggingGroupScrollbar;
    private int groupScrollOffset;
    private static final int GROUP_VISIBLE_ROWS = 5;
    private static final int GROUP_POPUP_WIDTH = 125;
    private static final int GROUP_ROW_HEIGHT = 20;

    public SoldierInventoryScreen(SoldierInventoryScreenHandler handler, class_1661 inventory, class_2561 title) {
        super((class_1703)handler, inventory, title);
        this.field_2792 = 300;
        this.field_2779 = 258;
        this.field_25269 = 69;
        this.field_25270 = 164;
    }

    protected void method_25426() {
        super.method_25426();
        this.renameButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier.rename"), button -> this.openRenameScreen()).method_46434(this.field_2776 + 120, this.field_2800 + 39, 104, 18).method_46431());
        this.groupButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"- \u25bc"), button -> this.toggleGroupDropdown()).method_46434(this.field_2776 + 165, this.field_2800 + 143, 125, 18).method_46431());
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier.supply"), b -> {
            this.applyPendingCommands();
            this.pendingCommandsApplied = false;
            this.orderSelectionDirty = false;
            this.stanceSelectionDirty = false;
            this.pendingMoveToPlayer = false;
            int currentSoldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
            if (currentSoldierId > 0) {
                ClientSoldierNetworking.requestSupplyFromGui(currentSoldierId);
            }
        }).method_46434(this.field_2776 + (this.field_2792 - 94) / 2, this.field_2800 - 26, 94, 20).method_46431());
        this.pendingOrder = ((SoldierInventoryScreenHandler)this.field_2797).getNpcOrderType();
        this.pendingStance = ((SoldierInventoryScreenHandler)this.field_2797).getNpcCombatStance();
        this.pendingHoldFire = ((SoldierInventoryScreenHandler)this.field_2797).isHoldFire();
        this.pendingMoveToPlayer = false;
        this.orderSelectionDirty = false;
        this.stanceSelectionDirty = false;
        this.pendingCommandsApplied = false;
        this.groupContextRefreshTicks = 1;
        int leftX = this.field_2776 - 102;
        int sideTop = this.field_2800 - 7;
        this.stanceButtons.clear();
        this.stanceButtons.add((class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.passive", leftX, sideTop, () -> this.selectStance(NpcCombatStance.PASSIVE))));
        this.stanceButtons.add((class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.neutral", leftX, sideTop + 24, () -> this.selectStance(NpcCombatStance.NEUTRAL))));
        this.stanceButtons.add((class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.aggressive", leftX, sideTop + 48, () -> this.selectStance(NpcCombatStance.AGGRESSIVE))));
        this.stanceButtons.add((class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.raid", leftX, sideTop + 72, () -> this.selectStance(NpcCombatStance.RAID))));
        this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.forget_target", leftX, sideTop + 106, this::forgetCurrentTarget));
        this.holdFireButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.hold_fire", leftX, sideTop + 130, () -> this.setHoldFire(true)));
        this.fireAtWillButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.fire_at_will", leftX, sideTop + 154, () -> this.setHoldFire(false)));
        this.shieldAutoButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.shield.auto", leftX, sideTop + 188, () -> this.setShieldMode(NpcShieldMode.AUTO)));
        this.shieldRaisedButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.shield.raised", leftX, sideTop + 212, () -> this.setShieldMode(NpcShieldMode.RAISED)));
        this.targetPriorityButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43469((String)"screen.medieval-machinery.command.target_priority", (Object[])new Object[]{this.targetPriorityText(this.currentTargetPriority())}), b -> this.cycleTargetPriority()).method_46434(leftX, sideTop + 246, 94, 20).method_46431());
        NpcRole screenRole = ((SoldierInventoryScreenHandler)this.field_2797).getNpcRole();
        int rightX = this.field_2776 + this.field_2792 + 8;
        int rightTop = sideTop - 12;
        int machineSectionTop = rightTop + 128;
        this.orderButtons.clear();
        this.orderButtons.add((class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.my_position", rightX, rightTop, this::selectMoveToPlayer)));
        this.orderButtons.add((class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.follow", rightX, rightTop + 24, () -> this.selectOrder(NpcOrderType.FOLLOW))));
        this.orderButtons.add((class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.hold", rightX, rightTop + 48, () -> this.selectOrder(NpcOrderType.HOLD_POSITION))));
        this.orderButtons.add((class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.return", rightX, rightTop + 72, () -> this.selectOrder(NpcOrderType.RETURN_TO_GARRISON))));
        this.idleOrderButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.idle", rightX, rightTop + 96, () -> this.selectOrder(NpcOrderType.IDLE)));
        this.orderButtons.add(this.idleOrderButton);
        this.quartermasterWorkButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.work", rightX, rightTop + 120, () -> this.selectOrder(NpcOrderType.WORK)));
        this.patrolOrderButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.patrol", rightX, rightTop + 144, () -> this.selectOrder(NpcOrderType.PATROL)));
        this.orderButtons.add(this.patrolOrderButton);
        this.patrolEditButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.patrol.manage"), b -> this.openPatrolManagement()).method_46434(rightX, rightTop + 168, 94, 20).method_46431());
        this.observationPointButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.scout.observation_button"), b -> {
            int currentId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
            if (currentId > 0) {
                ScoutObservationEditorClient.start(currentId);
                this.method_25419();
            }
        }).method_46434(rightX, rightTop + 236, 94, 20).method_46431());
        this.quartermasterFarmButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.quartermaster.manage"), b -> {
            int currentId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
            if (this.field_22787 != null && currentId > 0) {
                this.field_22787.method_1507((class_437)new QuartermasterFarmManageScreen((class_437)this, currentId));
            }
        }).method_46434(rightX, rightTop + 236, 94, 20).method_46431());
        this.mountButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.mount", rightX, rightTop + 188, () -> ClientSoldierNetworking.mountFromGui(((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId())));
        this.dismountButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.command.dismount", rightX, rightTop + 212, () -> ClientSoldierNetworking.dismountFromGui(((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId())));
        this.preferredWeaponButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43469((String)"screen.medieval-machinery.command.preferred_weapon", (Object[])new Object[]{this.preferredWeaponText(this.currentPreferredWeapon())}), b -> this.cyclePreferredWeapon()).method_46434(rightX, rightTop + 236, 94, 20).method_46431());
        this.machineAssignmentButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.machine_assignment.assign", rightX, machineSectionTop, () -> {
            int currentSoldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
            if (currentSoldierId <= 0) {
                return;
            }
            ClientSoldierNetworking.startMachineAssignment(currentSoldierId);
            this.method_25419();
        }));
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier_transfer.button"), b -> this.openTransferScreen()).method_46434(this.field_2776 + (this.field_2792 - 140) / 2, this.field_2800 + this.field_2779 + 8, 140, 20).method_46431());
        MedievalSoldierEntity openedSoldier = this.getSoldier();
        this.bodyguardManageButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.general_bodyguard.manage", rightX, sideTop + 236, this::openBodyguardManagement));
        this.bodyguardManageButton.field_22764 = false;
        this.machineClearButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.machine_assignment.clear", rightX, machineSectionTop + 24, () -> ClientSoldierNetworking.clearMachineAssignment(((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId())));
        this.preferredSupplyButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.machine_supply.preferred", rightX, machineSectionTop + 56, () -> {
            int currentId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
            if (currentId > 0) {
                ClientSoldierNetworking.startPreferredSupplySelection(currentId);
            }
            this.method_25419();
        }));
        this.clearPreferredSupplyButton = (class_4185)this.method_37063((class_364)this.sideButton("screen.medieval-machinery.machine_supply.clear", rightX, machineSectionTop + 80, () -> ClientSoldierNetworking.clearPreferredSupplySelection(((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId())));
        this.clearPreferredSupplyButton.field_22764 = false;
        this.preferredSupplyButton.field_22764 = false;
        this.machineClearButton.field_22764 = false;
        this.updateCurrentCommandButtons();
        this.updateGroupButton();
        this.updateRoleSpecificControls();
        ClientCommandGroupStatus.requestEntityContext(soldierId);
    }

    private void openBodyguardManagement() {
        if (this.field_22787 == null) {
            return;
        }
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        if (soldierId <= 0) {
            return;
        }
        this.field_22787.method_1507((class_437)new GeneralBodyguardManageScreen((class_437)this, soldierId));
    }

    private void openTransferScreen() {
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        MedievalSoldierEntity soldier = this.getSoldier();
        if (this.field_22787 == null || soldierId <= 0 || soldier == null) {
            return;
        }
        this.field_22787.method_1507((class_437)new SoldierTransferSelectScreen((class_437)this, soldierId, soldier.method_5476().getString(), soldier.getNpcRank() == NpcRank.CAPTAIN && soldier.getNpcIdentity().getSquadId() != null));
    }

    private void selectOrder(NpcOrderType order) {
        this.pendingOrder = order == null ? NpcOrderType.IDLE : order;
        this.pendingMoveToPlayer = false;
        this.orderSelectionDirty = true;
        this.updateCurrentCommandButtons();
    }

    private void selectMoveToPlayer() {
        this.pendingOrder = NpcOrderType.MOVE_TO_POSITION;
        this.pendingMoveToPlayer = true;
        this.orderSelectionDirty = true;
        this.updateCurrentCommandButtons();
    }

    private void selectStance(NpcCombatStance stance) {
        this.pendingStance = stance == null ? NpcCombatStance.NEUTRAL : stance;
        this.stanceSelectionDirty = true;
        this.updateCurrentCommandButtons();
    }

    private void forgetCurrentTarget() {
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        if (soldierId > 0) {
            ClientSoldierCommandNetworking.forgetEntityTarget(soldierId);
        }
    }

    private void setHoldFire(boolean hold) {
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        if (soldierId > 0) {
            this.pendingHoldFire = hold;
            ClientSoldierCommandNetworking.setEntityHoldFire(soldierId, hold);
            this.updateCurrentCommandButtons();
        }
    }

    private void setShieldMode(NpcShieldMode mode) {
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        if (soldierId > 0 && mode != null) {
            this.pendingShieldMode = mode;
            ClientSoldierCommandNetworking.setEntityShieldMode(soldierId, mode.ordinal());
            this.updateCurrentCommandButtons();
        }
    }

    private NpcTargetPriority currentTargetPriority() {
        class_1297 entity;
        if (this.field_22787 != null && this.field_22787.field_1687 != null && (entity = this.field_22787.field_1687.method_8469(((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId())) instanceof MedievalSoldierEntity) {
            MedievalSoldierEntity soldier = (MedievalSoldierEntity)entity;
            return soldier.getTargetPriority();
        }
        return NpcTargetPriority.CLOSEST;
    }

    private class_2561 targetPriorityText(NpcTargetPriority priority) {
        NpcTargetPriority resolved = priority == null ? NpcTargetPriority.CLOSEST : priority;
        return class_2561.method_43471((String)("screen.medieval-machinery.command.target_priority." + resolved.serializedName()));
    }

    private void updateTargetPriorityButton() {
        if (this.targetPriorityButton != null) {
            this.targetPriorityButton.method_25355((class_2561)class_2561.method_43469((String)"screen.medieval-machinery.command.target_priority", (Object[])new Object[]{this.targetPriorityText(this.currentTargetPriority())}));
        }
    }

    private void cycleTargetPriority() {
        NpcTargetPriority[] values = NpcTargetPriority.values();
        NpcTargetPriority current = this.currentTargetPriority();
        NpcTargetPriority next = values[(current.ordinal() + 1) % values.length];
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        if (soldierId > 0) {
            ClientSoldierCommandNetworking.setEntityTargetPriority(soldierId, next.ordinal());
        }
    }

    private NpcPreferredMeleeWeapon currentPreferredWeapon() {
        MedievalSoldierEntity soldier = this.getSoldier();
        return soldier == null ? NpcPreferredMeleeWeapon.SWORD : soldier.getPreferredMeleeWeapon();
    }

    private class_2561 preferredWeaponText(NpcPreferredMeleeWeapon weapon) {
        NpcPreferredMeleeWeapon resolved = weapon == null ? NpcPreferredMeleeWeapon.SWORD : weapon;
        return class_2561.method_43471((String)("screen.medieval-machinery.command.preferred_weapon." + resolved.serializedName()));
    }

    private void updatePreferredWeaponButton() {
        if (this.preferredWeaponButton != null) {
            this.preferredWeaponButton.method_25355((class_2561)class_2561.method_43469((String)"screen.medieval-machinery.command.preferred_weapon", (Object[])new Object[]{this.preferredWeaponText(this.currentPreferredWeapon())}));
        }
    }

    private void cyclePreferredWeapon() {
        NpcPreferredMeleeWeapon[] values = NpcPreferredMeleeWeapon.values();
        NpcPreferredMeleeWeapon current = this.currentPreferredWeapon();
        NpcPreferredMeleeWeapon next = values[(current.ordinal() + 1) % values.length];
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        if (soldierId > 0) {
            ClientSoldierCommandNetworking.setEntityPreferredMeleeWeapon(soldierId, next.ordinal());
        }
    }

    private void applyPendingCommands() {
        if (this.pendingCommandsApplied) {
            return;
        }
        this.pendingCommandsApplied = true;
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        if (this.orderSelectionDirty) {
            if (this.pendingMoveToPlayer) {
                ClientSoldierCommandNetworking.moveEntityToPlayer(soldierId);
            } else if (this.pendingOrder != null) {
                ClientSoldierCommandNetworking.setEntityOrder(soldierId, this.pendingOrder.ordinal());
            }
        }
        if (this.stanceSelectionDirty && this.pendingStance != null) {
            ClientSoldierCommandNetworking.setEntityStance(soldierId, this.pendingStance.ordinal());
        }
    }

    private void openPatrolManagement() {
        this.applyPendingCommands();
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        if (this.field_22787 != null && this.field_22787.field_1724 != null) {
            this.field_22787.field_1724.method_7346();
        }
        ClientSoldierNetworking.requestPatrolMenu(soldierId);
    }

    public void method_25419() {
        this.applyPendingCommands();
        super.method_25419();
    }

    public void method_37432() {
        int soldierId;
        super.method_37432();
        if (!this.orderSelectionDirty) {
            this.pendingOrder = ((SoldierInventoryScreenHandler)this.field_2797).getNpcOrderType();
            this.pendingMoveToPlayer = false;
        }
        if (!this.stanceSelectionDirty) {
            this.pendingStance = ((SoldierInventoryScreenHandler)this.field_2797).getNpcCombatStance();
        }
        if ((soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId()) > 0 && --this.groupContextRefreshTicks <= 0) {
            ClientCommandGroupStatus.requestEntityContext(soldierId);
            this.groupContextRefreshTicks = 40;
        }
        this.updateCurrentCommandButtons();
        this.updateGroupButton();
        this.updateTargetPriorityButton();
        this.updatePreferredWeaponButton();
        this.updateRoleSpecificControls();
    }

    private void updateRoleSpecificControls() {
        boolean general;
        boolean machine;
        NpcRole role = ((SoldierInventoryScreenHandler)this.field_2797).getNpcRole();
        boolean knight = role == NpcRole.KNIGHT || role == NpcRole.MOUNTED_KNIGHT;
        boolean cannoneer = role == NpcRole.CANNONEER;
        boolean bl = machine = cannoneer || role == NpcRole.ENGINEER;
        if (this.mountButton != null) {
            this.mountButton.method_46419(this.field_2800 - 31 + (machine ? 240 : 268));
        }
        if (this.dismountButton != null) {
            this.dismountButton.method_46419(this.field_2800 - 31 + (machine ? 264 : 292));
        }
        boolean rangedFireControl = role == NpcRole.ARCHER || role == NpcRole.CROSSBOWMAN || role == NpcRole.MOUNTED_ARCHER || role == NpcRole.CANNONEER;
        MedievalSoldierEntity soldier = this.getSoldier();
        boolean bl2 = general = soldier != null && soldier.getNpcRank() == NpcRank.GENERAL;
        if (this.bodyguardManageButton != null) {
            this.bodyguardManageButton.field_22764 = general;
        }
        if (this.holdFireButton != null) {
            this.holdFireButton.field_22764 = rangedFireControl;
        }
        if (this.fireAtWillButton != null) {
            this.fireAtWillButton.field_22764 = rangedFireControl;
        }
        if (this.patrolOrderButton != null) {
            this.patrolOrderButton.field_22764 = !cannoneer;
            boolean bl3 = this.patrolOrderButton.field_22763 = this.patrolOrderButton.field_22764 && this.pendingOrder != NpcOrderType.PATROL;
        }
        if (this.patrolEditButton != null) {
            this.patrolEditButton.field_22763 = this.patrolEditButton.field_22764 = !cannoneer;
        }
        if (this.preferredWeaponButton != null) {
            this.preferredWeaponButton.field_22764 = knight;
            this.preferredWeaponButton.method_46419(general ? this.field_2800 - 19 + 260 : this.field_2800 - 19 + 236);
        }
        if (this.machineAssignmentButton != null) {
            this.machineAssignmentButton.field_22764 = machine;
            this.machineAssignmentButton.method_46419(this.field_2800 + 109);
        }
        boolean hasMachineAssignment = ((SoldierInventoryScreenHandler)this.field_2797).hasMachineAssignment();
        if (this.idleOrderButton != null) {
            this.idleOrderButton.field_22764 = !cannoneer || !hasMachineAssignment;
            boolean bl4 = this.idleOrderButton.field_22763 = this.idleOrderButton.field_22764 && this.pendingOrder != NpcOrderType.IDLE;
        }
        if (this.machineClearButton != null) {
            this.machineClearButton.field_22763 = this.machineClearButton.field_22764 = machine && hasMachineAssignment;
        }
        if (this.preferredSupplyButton != null) {
            this.preferredSupplyButton.field_22763 = this.preferredSupplyButton.field_22764 = machine && hasMachineAssignment;
        }
        if (this.clearPreferredSupplyButton != null) {
            this.clearPreferredSupplyButton.field_22763 = this.clearPreferredSupplyButton.field_22764 = machine && hasMachineAssignment;
        }
        if (this.quartermasterWorkButton != null) {
            this.quartermasterWorkButton.field_22764 = role == NpcRole.QUARTERMASTER;
            boolean bl5 = this.quartermasterWorkButton.field_22763 = this.quartermasterWorkButton.field_22764 && this.pendingOrder != NpcOrderType.WORK;
        }
        if (this.quartermasterFarmButton != null) {
            this.quartermasterFarmButton.field_22763 = this.quartermasterFarmButton.field_22764 = role == NpcRole.QUARTERMASTER;
        }
        if (this.observationPointButton != null) {
            this.observationPointButton.field_22763 = this.observationPointButton.field_22764 = role == NpcRole.SCOUT;
        }
    }

    private class_4185 sideButton(String key, int bx, int by, Runnable action) {
        return class_4185.method_46430((class_2561)class_2561.method_43471((String)key), b -> action.run()).method_46434(bx, by, 94, 20).method_46431();
    }

    private void updateCurrentCommandButtons() {
        if (this.stanceButtons.size() >= 4 && this.pendingStance != null) {
            this.stanceButtons.get((int)0).field_22763 = this.pendingStance != NpcCombatStance.PASSIVE;
            this.stanceButtons.get((int)1).field_22763 = this.pendingStance != NpcCombatStance.NEUTRAL;
            this.stanceButtons.get((int)2).field_22763 = this.pendingStance != NpcCombatStance.AGGRESSIVE;
            this.stanceButtons.get((int)3).field_22763 = this.pendingStance != NpcCombatStance.RAID;
        }
        boolean serverHoldFire = ((SoldierInventoryScreenHandler)this.field_2797).isHoldFire();
        if (this.holdFireButton != null) {
            boolean bl = this.holdFireButton.field_22763 = !serverHoldFire;
        }
        if (this.fireAtWillButton != null) {
            this.fireAtWillButton.field_22763 = serverHoldFire;
        }
        NpcShieldMode shownShieldMode = this.pendingShieldMode;
        MedievalSoldierEntity shownSoldier = this.getSoldier();
        if (shownShieldMode == null && shownSoldier != null) {
            shownShieldMode = shownSoldier.getShieldMode();
        }
        if (this.shieldAutoButton != null && shownShieldMode != null) {
            boolean bl = this.shieldAutoButton.field_22763 = shownShieldMode != NpcShieldMode.AUTO;
        }
        if (this.shieldRaisedButton != null && shownShieldMode != null) {
            boolean bl = this.shieldRaisedButton.field_22763 = shownShieldMode != NpcShieldMode.RAISED;
        }
        if (this.orderButtons.size() >= 6 && this.pendingOrder != null) {
            MedievalSoldierEntity soldier = this.getSoldier();
            boolean bodyguardDuty = soldier != null && soldier.isGeneralBodyguard();
            this.orderButtons.get((int)0).field_22763 = !bodyguardDuty && this.pendingOrder != NpcOrderType.MOVE_TO_POSITION;
            this.orderButtons.get((int)1).field_22763 = !bodyguardDuty && this.pendingOrder != NpcOrderType.FOLLOW;
            this.orderButtons.get((int)2).field_22763 = !bodyguardDuty && this.pendingOrder != NpcOrderType.HOLD_POSITION;
            this.orderButtons.get((int)3).field_22763 = !bodyguardDuty && this.pendingOrder != NpcOrderType.RETURN_TO_GARRISON;
            this.orderButtons.get((int)4).field_22763 = !bodyguardDuty && this.pendingOrder != NpcOrderType.IDLE;
            this.orderButtons.get((int)5).field_22763 = !bodyguardDuty && this.pendingOrder != NpcOrderType.PATROL;
        }
    }

    public int getSoldierEntityIdForContext() {
        return ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
    }

    public void refreshGroupContext() {
        this.clampGroupScroll();
        this.updateGroupButton();
    }

    private void updateGroupButton() {
        if (this.groupButton == null) {
            return;
        }
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        int group = ClientCommandGroupStatus.hasEntityContext(soldierId) ? ClientCommandGroupStatus.entityCurrentGroup(soldierId) : 0;
        class_5250 baseGroupName = ClientCommandGroupStatus.hasEntityContext(soldierId) ? ClientCommandGroupStatus.entityGroupDisplayName(soldierId, group) : class_2561.method_43470((String)"-");
        this.groupButton.method_25355((class_2561)baseGroupName.method_27661().method_27693(this.groupDropdownOpen ? " \u25b2" : " \u25bc"));
        this.groupButton.field_22763 = true;
    }

    private void toggleGroupDropdown() {
        this.groupDropdownOpen = !this.groupDropdownOpen;
        this.draggingGroupScrollbar = false;
        this.clampGroupScroll();
    }

    private List<Integer> availableGroupIds() {
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        if (ClientCommandGroupStatus.hasEntityContext(soldierId)) {
            return ClientCommandGroupStatus.entityAvailableGroups(soldierId);
        }
        ArrayList<Integer> fallback = new ArrayList<Integer>();
        fallback.add(0);
        for (ClientCommandGroupStatus.Group group : ClientCommandGroupStatus.groups()) {
            fallback.add(group.slot());
        }
        return fallback;
    }

    private int groupPopupX() {
        return this.field_2776 + 165;
    }

    private int groupPopupY() {
        return this.field_2800 + 148;
    }

    private int maxGroupScroll() {
        return Math.max(0, this.availableGroupIds().size() - 5);
    }

    private void clampGroupScroll() {
        this.groupScrollOffset = Math.max(0, Math.min(this.groupScrollOffset, this.maxGroupScroll()));
    }

    private void selectGroupAtRow(int row) {
        List<Integer> groups = this.availableGroupIds();
        int index = this.groupScrollOffset + row;
        if (index < 0 || index >= groups.size()) {
            return;
        }
        int soldierId = ((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId();
        int group = groups.get(index);
        ClientSoldierCommandNetworking.assignGroup(soldierId, group);
        if (this.groupButton != null) {
            class_5250 baseGroupName = ClientCommandGroupStatus.hasEntityContext(soldierId) ? ClientCommandGroupStatus.entityGroupDisplayName(soldierId, group) : class_2561.method_43470((String)"-");
            this.groupButton.method_25355((class_2561)baseGroupName.method_27661().method_27693(" \u25bc"));
        }
        this.groupDropdownOpen = false;
        this.draggingGroupScrollbar = false;
        ClientCommandGroupStatus.requestEntityContext(soldierId);
    }

    private void setScrollFromMouse(double mouseY) {
        int max = this.maxGroupScroll();
        if (max <= 0) {
            this.groupScrollOffset = 0;
            return;
        }
        int top = this.groupPopupY() + 4;
        int trackHeight = 92;
        int thumbHeight = Math.max(18, trackHeight * 5 / this.availableGroupIds().size());
        double usable = Math.max(1.0, (double)(trackHeight - thumbHeight));
        double relative = Math.max(0.0, Math.min(usable, mouseY - (double)top - (double)thumbHeight / 2.0));
        this.groupScrollOffset = (int)Math.round(relative / usable * (double)max);
        this.clampGroupScroll();
    }

    public boolean method_25402(double mouseX, double mouseY, int button) {
        if (this.groupDropdownOpen && button == 0) {
            int px = this.groupPopupX();
            int py = this.groupPopupY();
            int listWidth = 111;
            int listHeight = 100;
            if (mouseX >= (double)(px + 4) && mouseX < (double)(px + 4 + listWidth) && mouseY >= (double)(py + 4) && mouseY < (double)(py + 4 + listHeight)) {
                int row = (int)((mouseY - (double)(py + 4)) / 20.0);
                this.selectGroupAtRow(row);
                return true;
            }
            if (mouseX >= (double)(px + 125 - 10) && mouseX <= (double)(px + 125 - 3) && mouseY >= (double)(py + 4) && mouseY <= (double)(py + 4 + listHeight)) {
                this.draggingGroupScrollbar = true;
                this.setScrollFromMouse(mouseY);
                return true;
            }
            if (!(mouseX >= (double)px && mouseX <= (double)(px + 125) && mouseY >= (double)py && mouseY <= (double)(py + listHeight + 8))) {
                this.groupDropdownOpen = false;
                this.draggingGroupScrollbar = false;
            }
        }
        return super.method_25402(mouseX, mouseY, button);
    }

    public boolean method_25403(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.groupDropdownOpen && this.draggingGroupScrollbar && button == 0) {
            this.setScrollFromMouse(mouseY);
            return true;
        }
        return super.method_25403(mouseX, mouseY, button, deltaX, deltaY);
    }

    public boolean method_25406(double mouseX, double mouseY, int button) {
        if (this.draggingGroupScrollbar && button == 0) {
            this.draggingGroupScrollbar = false;
            return true;
        }
        return super.method_25406(mouseX, mouseY, button);
    }

    public boolean method_25401(double mouseX, double mouseY, double amount) {
        if (this.groupDropdownOpen) {
            if (amount < 0.0) {
                ++this.groupScrollOffset;
            }
            if (amount > 0.0) {
                --this.groupScrollOffset;
            }
            this.clampGroupScroll();
            return true;
        }
        return super.method_25401(mouseX, mouseY, amount);
    }

    private void drawGroupDropdown(class_332 context, int mouseX, int mouseY) {
        if (!this.groupDropdownOpen) {
            return;
        }
        context.method_51448().method_22903();
        context.method_51448().method_46416(0.0f, 0.0f, 500.0f);
        RenderSystem.disableDepthTest();
        int px = this.groupPopupX();
        int py = this.groupPopupY();
        int listWidth = 111;
        int listHeight = 100;
        context.method_25294(px, py, px + 125, py + listHeight + 8, -14737633);
        context.method_25294(px + 2, py + 2, px + 125 - 2, py + listHeight + 6, -3750202);
        List<Integer> groups = this.availableGroupIds();
        MedievalSoldierEntity soldier = this.getSoldier();
        int currentGroup = ClientCommandGroupStatus.hasEntityContext(((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId()) ? ClientCommandGroupStatus.entityCurrentGroup(((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId()) : 0;
        int visible = Math.min(5, Math.max(0, groups.size() - this.groupScrollOffset));
        for (int row = 0; row < visible; ++row) {
            boolean selected;
            int group = groups.get(this.groupScrollOffset + row);
            int ry = py + 4 + row * 20;
            boolean hovered = mouseX >= px + 4 && mouseX < px + 4 + listWidth && mouseY >= ry && mouseY < ry + 20 - 1;
            boolean bl = selected = group == currentGroup;
            int background = selected ? -11184811 : (hovered ? -7697782 : -4737097);
            int textColor = selected ? -1 : -13619152;
            context.method_25294(px + 4, ry, px + 4 + listWidth, ry + 20 - 1, background);
            class_2561 groupName = ClientCommandGroupStatus.entityGroupDisplayName(((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId(), group);
            int groupTextX = px + 4 + listWidth / 2 - this.field_22793.method_27525((class_5348)groupName) / 2;
            context.method_51439(this.field_22793, groupName, groupTextX, ry + 6, textColor, false);
        }
        int trackX = px + 125 - 10;
        int trackY = py + 4;
        int trackHeight = listHeight;
        context.method_25294(trackX, trackY, trackX + 7, trackY + trackHeight, -11119018);
        int max = this.maxGroupScroll();
        int thumbHeight = max <= 0 ? trackHeight : Math.max(18, trackHeight * 5 / groups.size());
        int thumbY = max <= 0 ? trackY : trackY + (int)((double)(trackHeight - thumbHeight) * ((double)this.groupScrollOffset / (double)max));
        context.method_25294(trackX + 1, thumbY, trackX + 6, thumbY + thumbHeight, -3750202);
        context.method_25294(trackX + 2, thumbY + 1, trackX + 5, thumbY + thumbHeight - 1, -7631989);
        RenderSystem.enableDepthTest();
        context.method_51448().method_22909();
    }

    private void openRenameScreen() {
        MedievalSoldierEntity soldier = this.getSoldier();
        if (soldier == null || this.field_22787 == null || this.field_22787.field_1724 == null) {
            return;
        }
        int entityId = soldier.method_5628();
        String currentName = soldier.method_16914() ? soldier.method_5797().getString() : "";
        this.field_22787.field_1724.method_7346();
        this.field_22787.method_1507((class_437)new SoldierRenameScreen(entityId, currentName));
    }

    protected void method_2389(class_332 context, float delta, int mouseX, int mouseY) {
        int col;
        int row;
        context.method_25294(this.field_2776, this.field_2800, this.field_2776 + this.field_2792, this.field_2800 + this.field_2779, -3750202);
        context.method_25294(this.field_2776, this.field_2800, this.field_2776 + this.field_2792, this.field_2800 + 2, -1);
        context.method_25294(this.field_2776, this.field_2800, this.field_2776 + 2, this.field_2800 + this.field_2779, -1);
        context.method_25294(this.field_2776, this.field_2800 + this.field_2779 - 2, this.field_2776 + this.field_2792, this.field_2800 + this.field_2779, -11184811);
        context.method_25294(this.field_2776 + this.field_2792 - 2, this.field_2800, this.field_2776 + this.field_2792, this.field_2800 + this.field_2779, -11184811);
        context.method_25294(this.field_2776 + 48, this.field_2800 + 44, this.field_2776 + 112, this.field_2800 + 116, -11184811);
        context.method_25294(this.field_2776 + 49, this.field_2800 + 45, this.field_2776 + 112, this.field_2800 + 116, -1);
        context.method_25294(this.field_2776 + 50, this.field_2800 + 46, this.field_2776 + 111, this.field_2800 + 115, -7631989);
        for (int i = 0; i < 4; ++i) {
            this.drawVanillaSlot(context, this.field_2776 + 17, this.field_2800 + 47 + i * 18);
        }
        this.drawVanillaSlot(context, this.field_2776 + 35, this.field_2800 + 119);
        this.drawVanillaSlot(context, this.field_2776 + 53, this.field_2800 + 119);
        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 3; ++col) {
                this.drawVanillaSlot(context, this.field_2776 + 239 + col * 18, this.field_2800 + 71 + row * 18);
            }
        }
        if (((SoldierInventoryScreenHandler)this.field_2797).getNpcRole() == NpcRole.CANNONEER || ((SoldierInventoryScreenHandler)this.field_2797).getNpcRole() == NpcRole.QUARTERMASTER) {
            this.drawVanillaSlot(context, this.field_2776 + 71, this.field_2800 + 119);
        }
        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.drawVanillaSlot(context, this.field_2776 + 68 + col * 18, this.field_2800 + 175 + row * 18);
            }
        }
        for (int col2 = 0; col2 < 9; ++col2) {
            this.drawVanillaSlot(context, this.field_2776 + 68 + col2 * 18, this.field_2800 + 233);
        }
        MedievalSoldierEntity soldier = this.getSoldier();
        if (soldier != null) {
            for (int i = 0; i < 4; ++i) {
                if (!((SoldierInventoryScreenHandler)this.field_2797).method_7611(i).method_7677().method_7960()) continue;
                this.drawHint(context, ARMOR_HINTS[i], this.field_2776 + 18, this.field_2800 + 48 + i * 18, 1.0f);
            }
            if (((SoldierInventoryScreenHandler)this.field_2797).method_7611(5).method_7677().method_7960()) {
                this.drawHint(context, EMPTY_SHIELD, this.field_2776 + 36, this.field_2800 + 120, 1.0f);
            }
            if (((SoldierInventoryScreenHandler)this.field_2797).method_7611(4).method_7677().method_7960()) {
                this.drawHint(context, this.getMainHandHint(soldier.getNpcRole()), this.field_2776 + 54, this.field_2800 + 120, 0.55f);
            }
            if (soldier.getNpcRole() == NpcRole.CANNONEER && ((SoldierInventoryScreenHandler)this.field_2797).method_7611(15).method_7677().method_7960()) {
                this.drawHint(context, CANNONBALL_HINT, this.field_2776 + 72, this.field_2800 + 120, 0.12f);
            }
            if (soldier.getNpcRole() == NpcRole.QUARTERMASTER && ((SoldierInventoryScreenHandler)this.field_2797).method_7611(15).method_7677().method_7960()) {
                this.drawGrayHint(context, WHEAT_HINT, this.field_2776 + 72, this.field_2800 + 120);
            }
            float lookX = this.field_2776 + 80 - mouseX;
            float lookY = this.field_2800 + 92 - mouseY;
            class_2561 oldName = soldier.method_5797();
            boolean oldVisible = soldier.method_5807();
            soldier.method_5665(null);
            soldier.method_5880(false);
            class_490.method_2486((class_332)context, (int)(this.field_2776 + 80), (int)(this.field_2800 + 113), (int)31, (float)lookX, (float)lookY, (class_1309)soldier);
            soldier.method_5665(oldName);
            soldier.method_5880(oldVisible);
        }
    }

    private void drawVanillaSlot(class_332 context, int sx, int sy) {
        context.method_25290(VANILLA_CONTAINER, sx, sy, 7.0f, 17.0f, 18, 18, 256, 256);
    }

    private void drawHint(class_332 context, class_2960 texture, int sx, int sy, float alpha) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)alpha);
        context.method_25290(texture, sx, sy, 0.0f, 0.0f, 16, 16, 16, 16);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
    }

    private void drawGrayHint(class_332 context, class_2960 texture, int sx, int sy) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor((float)0.42f, (float)0.42f, (float)0.42f, (float)0.62f);
        context.method_25290(texture, sx, sy, 0.0f, 0.0f, 16, 16, 16, 16);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
    }

    private MedievalSoldierEntity getSoldier() {
        MedievalSoldierEntity soldier;
        if (this.field_22787 == null || this.field_22787.field_1687 == null) {
            return null;
        }
        class_1297 entity = this.field_22787.field_1687.method_8469(((SoldierInventoryScreenHandler)this.field_2797).getSoldierEntityId());
        return entity instanceof MedievalSoldierEntity ? (soldier = (MedievalSoldierEntity)entity) : null;
    }

    protected void method_2388(class_332 context, int mouseX, int mouseY) {
        class_2561 heading;
        MedievalSoldierEntity soldier = this.getSoldier();
        if (soldier == null) {
            heading = this.field_22785;
        } else {
            class_5250 roleText = class_2561.method_43471((String)("npc.medieval-machinery.role." + soldier.getNpcRole().serializedName()));
            heading = soldier.method_16914() && soldier.method_5797() != null && !soldier.method_5797().getString().isBlank() ? class_2561.method_43469((String)"screen.medieval-machinery.soldier_inventory_named", (Object[])new Object[]{roleText, soldier.method_5797()}) : class_2561.method_43469((String)"screen.medieval-machinery.soldier_inventory", (Object[])new Object[]{roleText});
        }
        context.method_51439(this.field_22793, heading, 8, 8, 0x404040, false);
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier_storage"), 237, 39, 0x404040, false);
        context.method_51439(this.field_22793, this.field_29347, this.field_25269, this.field_25270, 0x404040, false);
        if (soldier != null) {
            int infoX = 120;
            int infoY = 63;
            NpcRank rank = soldier.getNpcRank();
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.soldier.castle", (Object[])new Object[]{soldier.getCastleDisplayName()}), infoX, infoY, 0x404040, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.soldier.rank", (Object[])new Object[]{class_2561.method_43471((String)("npc.medieval-machinery.rank." + rank.serializedName()))}), infoX, infoY + 12, 0x404040, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.soldier.level", (Object[])new Object[]{soldier.getNpcLevel(), soldier.getNpcExperience(), soldier.getExperienceForNextLevel()}), infoX, infoY + 24, 0x404040, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.soldier.health", (Object[])new Object[]{Math.round(soldier.method_6032()), Math.round(soldier.method_6063())}), infoX, infoY + 36, 0x404040, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.soldier.hunger_short", (Object[])new Object[]{soldier.getHungerLevel()}), infoX, infoY + 48, 0x404040, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.soldier.morale", (Object[])new Object[]{Math.round(soldier.getMorale())}), infoX, infoY + 60, 0x404040, false);
            NpcOrderType displayedOrder = this.pendingOrder == null ? ((SoldierInventoryScreenHandler)this.field_2797).getNpcOrderType() : this.pendingOrder;
            class_5250 displayedOrderText = soldier.isGeneralBodyguard() ? class_2561.method_43471((String)"npc.order.medieval-machinery.bodyguard") : class_2561.method_43471((String)("npc.order.medieval-machinery." + displayedOrder.serializedName()));
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"screen.medieval-machinery.soldier.order", (Object[])new Object[]{displayedOrderText}), infoX, infoY + 72, 0x404040, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier.group"), infoX, infoY + 90, 0x404040, false);
            OrderStyle activeOrder = OrderArmorSetBonusService.getCompleteSetOrder((class_1309)soldier);
            if (activeOrder != OrderStyle.NONE) {
                String style = activeOrder.method_15434();
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)("screen.medieval-machinery.soldier.order_bonus_name_" + style)), 120, 18, 0x404040, false);
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)("screen.medieval-machinery.soldier.order_bonus_detail_" + style)), 120, 28, 0x404040, false);
            }
            if (soldier.getNpcRole() == NpcRole.CANNONEER) {
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.soldier.used_ammunition"), 17, 142, 0x404040, false);
            } else if (soldier.getNpcRole() == NpcRole.QUARTERMASTER) {
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"screen.medieval-machinery.quartermaster.crop_selector"), 17, 142, 0x404040, false);
            }
        }
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.updateCurrentCommandButtons();
        this.updateGroupButton();
        this.method_25420(context);
        super.method_25394(context, mouseX, mouseY, delta);
        this.drawGroupDropdown(context, mouseX, mouseY);
        if (!this.groupDropdownOpen) {
            this.method_2380(context, mouseX, mouseY);
        }
    }

    private class_2960 getMainHandHint(NpcRole role) {
        return switch (role) {
            default -> throw new IncompatibleClassChangeError();
            case NpcRole.AXEMAN -> AXE_HINT;
            case NpcRole.ARCHER, NpcRole.MOUNTED_ARCHER -> BOW_HINT;
            case NpcRole.CROSSBOWMAN -> CROSSBOW_HINT;
            case NpcRole.ENGINEER -> PICKAXE_HINT;
            case NpcRole.CANNONEER -> SWORD_HINT;
            case NpcRole.SWORDSMAN, NpcRole.KNIGHT, NpcRole.MOUNTED_KNIGHT, NpcRole.SPEARMAN, NpcRole.RECRUIT, NpcRole.QUARTERMASTER, NpcRole.SCOUT -> SWORD_HINT;
        };
    }
}

