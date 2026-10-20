/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_1542
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.QuartermasterCropPolicy;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1352;
import net.minecraft.class_1542;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import org.jetbrains.annotations.Nullable;

final class WantedItemPickupGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private final double speed;
    @Nullable
    private class_1542 wantedItem;
    @Nullable
    private UUID temporarilyUnreachableItem;
    private int unreachableItemUntilAge;
    private double bestTargetDistanceSq = Double.MAX_VALUE;
    private int noPickupProgressTicks;

    WantedItemPickupGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405));
    }

    public boolean method_6264() {
        boolean committedHarvestPickup = this.isCommittedHarvestPickup();
        if (!committedHarvestPickup) {
            int scanPeriod;
            int n = scanPeriod = this.soldier.getNpcRole() == NpcRole.QUARTERMASTER && this.soldier.getOrderType() == NpcOrderType.WORK ? 2 : 10;
            if (!this.soldier.isStaggeredAiTick(scanPeriod)) {
                return false;
            }
        }
        if (this.soldier.hasSupplyMovementOverride() || this.soldier.isInventoryGuiOpen() || this.soldier.method_6115()) {
            return false;
        }
        if (!committedHarvestPickup && this.soldier.method_5968() != null) {
            return false;
        }
        if (this.soldier.getNpcRole() == NpcRole.QUARTERMASTER && this.soldier.getOrderType() == NpcOrderType.WORK && this.soldier.quartermasterFarmingController().isCompostingBatch()) {
            return false;
        }
        NpcOrderType order = this.soldier.getOrderType();
        if (order != NpcOrderType.IDLE && order != NpcOrderType.HOLD_POSITION && (order != NpcOrderType.WORK || this.soldier.getNpcRole() != NpcRole.QUARTERMASTER)) {
            return false;
        }
        double radius = order == NpcOrderType.HOLD_POSITION ? 4.0 : 16.0;
        this.wantedItem = this.findWantedItem(radius);
        return this.wantedItem != null;
    }

    public boolean method_6266() {
        return this.wantedItem != null && this.wantedItem.method_5805() && (this.isCommittedHarvestPickup() || this.soldier.method_5968() == null) && !this.soldier.hasSupplyMovementOverride() && this.soldier.method_5939(this.wantedItem.method_6983()) && this.soldier.method_5858((class_1297)this.wantedItem) <= 256.0;
    }

    public void method_6269() {
        this.soldier.adaptiveNavigation().reset();
        this.bestTargetDistanceSq = this.wantedItem == null ? Double.MAX_VALUE : this.soldier.method_5858((class_1297)this.wantedItem);
        this.noPickupProgressTicks = 0;
    }

    public void method_6268() {
        if (this.wantedItem == null || !this.wantedItem.method_5805()) {
            return;
        }
        double distanceSq = this.soldier.method_5858((class_1297)this.wantedItem);
        if (distanceSq <= 3.4225000000000003) {
            this.soldier.adaptiveNavigation().stop();
            this.soldier.method_5988().method_6226((class_1297)this.wantedItem, 30.0f, 30.0f);
            this.soldier.method_5949(this.wantedItem);
            if (!this.wantedItem.method_5805() || this.wantedItem.method_6983().method_7960()) {
                this.wantedItem = null;
            }
            this.noPickupProgressTicks = 0;
            return;
        }
        if (distanceSq + 0.2 < this.bestTargetDistanceSq) {
            this.bestTargetDistanceSq = distanceSq;
            this.noPickupProgressTicks = 0;
        } else if (++this.noPickupProgressTicks >= 60) {
            this.temporarilyUnreachableItem = this.wantedItem.method_5667();
            this.unreachableItemUntilAge = this.soldier.field_6012 + 120;
            this.wantedItem = null;
            this.soldier.adaptiveNavigation().stop();
            return;
        }
        class_243 pickupPoint = this.wantedItem.method_19538();
        this.soldier.adaptiveNavigation().tickToPoint(pickupPoint, this.speed);
        this.soldier.method_5988().method_6226((class_1297)this.wantedItem, 30.0f, 30.0f);
        if (!this.wantedItem.method_5805() || this.wantedItem.method_6983().method_7960()) {
            this.wantedItem = null;
        }
    }

    public void method_6270() {
        this.soldier.adaptiveNavigation().stop();
        this.wantedItem = null;
    }

    private boolean isCommittedHarvestPickup() {
        return this.soldier.getNpcRole() == NpcRole.QUARTERMASTER && this.soldier.getOrderType() == NpcOrderType.WORK && this.soldier.quartermasterFarmingController().pendingHarvestPickupAnchor() != null;
    }

    @Nullable
    private class_1542 findWantedItem(double radius) {
        class_1542 harvestDrop;
        class_2338 harvestAnchor;
        class_2338 class_23382 = harvestAnchor = this.soldier.getNpcRole() == NpcRole.QUARTERMASTER && this.soldier.getOrderType() == NpcOrderType.WORK ? this.soldier.quartermasterFarmingController().pendingHarvestPickupAnchor() : null;
        if (harvestAnchor != null && (harvestDrop = this.findNearestIn(new class_238(harvestAnchor).method_1009(4.5, 2.5, 4.5), true)) != null) {
            return harvestDrop;
        }
        class_238 area = this.soldier.method_5829().method_1009(radius, 2.5, radius);
        return this.findNearestIn(area, false);
    }

    @Nullable
    private class_1542 findNearestIn(class_238 area, boolean farmDropsOnly) {
        class_1542 best = null;
        double bestSq = Double.MAX_VALUE;
        for (class_1542 item : this.soldier.method_37908().method_8390(class_1542.class, area, candidate -> !(!candidate.method_5805() || candidate.method_6983().method_7960() || farmDropsOnly && !QuartermasterCropPolicy.isFarmPickup(candidate.method_6983()) || this.temporarilyUnreachableItem != null && this.soldier.field_6012 < this.unreachableItemUntilAge && this.temporarilyUnreachableItem.equals(candidate.method_5667()) || !this.soldier.method_5939(candidate.method_6983())))) {
            double sq = this.soldier.method_5858((class_1297)item);
            if (!(sq < bestSq)) continue;
            bestSq = sq;
            best = item;
        }
        return best;
    }
}

