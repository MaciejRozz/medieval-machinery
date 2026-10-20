/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_2338
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.QuartermasterFarmingController;
import java.util.EnumSet;
import net.minecraft.class_1268;
import net.minecraft.class_1352;
import net.minecraft.class_2338;

final class QuartermasterFarmingGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private QuartermasterFarmingController.Work work;
    private int interactionTicks;
    private boolean workVisualStarted;
    private class_2338 openChestPos;
    private int compostStepTicks;

    QuartermasterFarmingGoal(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405, class_1352.class_4134.field_18406));
    }

    public boolean method_6264() {
        if (this.soldier.getNpcRole() != NpcRole.QUARTERMASTER || this.soldier.isInventoryGuiOpen()) {
            return false;
        }
        if (this.soldier.method_5968() != null || this.soldier.machineAssignmentOwnsPersonalCombat()) {
            return false;
        }
        if (this.soldier.supplySupplyState().ownsMovement()) {
            return false;
        }
        if (this.soldier.getOrderType() != NpcOrderType.WORK) {
            return false;
        }
        this.work = this.soldier.quartermasterFarmingController().plan(this.soldier);
        this.interactionTicks = 0;
        this.workVisualStarted = false;
        this.openChestPos = null;
        this.compostStepTicks = 0;
        return this.work != null;
    }

    public boolean method_6266() {
        return this.work != null && this.soldier.getOrderType() == NpcOrderType.WORK && this.soldier.method_5968() == null && !this.soldier.supplySupplyState().ownsMovement();
    }

    public void method_6268() {
        boolean chestAction;
        if (this.work == null) {
            return;
        }
        class_2338 pos = this.work.pos();
        double dist = this.soldier.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5);
        if (dist > 4.0) {
            boolean targetIsFloorBlock = this.work.action() == QuartermasterFarmingController.Action.WATER || this.work.action() == QuartermasterFarmingController.Action.TILL || this.work.action() == QuartermasterFarmingController.Action.INSPECT;
            this.soldier.adaptiveNavigation().tickToBlock(pos, targetIsFloorBlock, 0.9);
            this.soldier.method_5988().method_20248((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5);
            return;
        }
        this.soldier.stopAdaptiveNavigation();
        this.soldier.method_5988().method_20248((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5);
        boolean bl = chestAction = this.work.action() == QuartermasterFarmingController.Action.DEPOSIT_BREAD || this.work.action() == QuartermasterFarmingController.Action.DEPOSIT_PRODUCE || this.work.action() == QuartermasterFarmingController.Action.FETCH_HOE;
        if (chestAction) {
            if (this.interactionTicks <= 0 && this.openChestPos == null) {
                this.soldier.openWorkChestVisual(pos);
                this.soldier.method_6104(class_1268.field_5808);
                this.openChestPos = pos.method_10062();
                this.interactionTicks = 18;
                return;
            }
            if (--this.interactionTicks > 0) {
                return;
            }
        } else {
            if (this.work.action() == QuartermasterFarmingController.Action.COMPOST) {
                if (!this.workVisualStarted) {
                    this.workVisualStarted = true;
                    this.soldier.setQuartermasterCompostingVisual(true);
                    this.soldier.beginQuartermasterWorkVisual(this.work.action());
                }
                if (!this.soldier.quartermasterFarmingController().continueCompostBatch(this.soldier, this.work)) {
                    this.soldier.setQuartermasterCompostingVisual(false);
                    this.soldier.quartermasterFarmingController().clearWork();
                    this.work = null;
                    return;
                }
                if (this.compostStepTicks-- <= 0) {
                    this.soldier.method_6104(class_1268.field_5808);
                    this.soldier.quartermasterFarmingController().performCompostBatchStep(this.soldier, this.work);
                    this.compostStepTicks = 4;
                }
                return;
            }
            if (QuartermasterFarmingGoal.isVanillaHandWork(this.work.action()) && !this.workVisualStarted) {
                this.workVisualStarted = true;
                this.soldier.beginQuartermasterWorkVisual(this.work.action());
                this.soldier.method_6104(class_1268.field_5808);
            }
        }
        this.soldier.quartermasterFarmingController().perform(this.soldier, this.work);
        this.closeWorkChest();
        this.work = null;
    }

    public void method_6270() {
        this.soldier.stopAdaptiveNavigation();
        this.closeWorkChest();
        this.interactionTicks = 0;
        this.workVisualStarted = false;
        this.soldier.setQuartermasterCompostingVisual(false);
        this.work = null;
        this.soldier.quartermasterFarmingController().clearWork();
    }

    private static boolean isVanillaHandWork(QuartermasterFarmingController.Action action) {
        return action == QuartermasterFarmingController.Action.TILL || action == QuartermasterFarmingController.Action.PLANT || action == QuartermasterFarmingController.Action.HARVEST || action == QuartermasterFarmingController.Action.FERTILIZE || action == QuartermasterFarmingController.Action.COLLECT_BONEMEAL;
    }

    private void closeWorkChest() {
        if (this.openChestPos != null) {
            this.soldier.closeWorkChestVisual(this.openChestPos);
        }
        this.openChestPos = null;
    }
}

