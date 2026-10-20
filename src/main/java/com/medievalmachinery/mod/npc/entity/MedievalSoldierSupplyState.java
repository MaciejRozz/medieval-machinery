/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcSupplyRequest;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierAdaptiveNavigation;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_243;

final class MedievalSoldierSupplyState {
    NpcSupplyRequest request = NpcSupplyRequest.NONE;
    List<class_2338> chests = List.of();
    int chestIndex;
    class_2338 chest;
    NpcOrderType orderBefore = NpcOrderType.IDLE;
    class_2338 commandDestinationBefore;
    class_243 holdPositionBefore;
    class_243 originPosition;
    boolean returningToOrigin;
    int startDelay;
    int interactionTicks;
    int approachTicks;
    boolean chestVisualOpen;
    final MedievalSoldierAdaptiveNavigation movement;

    MedievalSoldierSupplyState(MedievalSoldierEntity soldier) {
        this.movement = soldier.adaptiveNavigation();
    }

    boolean isRunning() {
        return this.request.isRun() && this.chest != null;
    }

    boolean hasChest() {
        return this.chest != null;
    }

    boolean isReturningToOrigin() {
        return this.returningToOrigin;
    }

    void begin(List<class_2338> usableChests, NpcSupplyRequest requested, NpcOrderType currentOrder, class_2338 destination, class_243 hold, class_243 currentPosition, int delay) {
        this.orderBefore = currentOrder;
        this.commandDestinationBefore = destination == null ? null : destination.method_10062();
        this.holdPositionBefore = hold;
        this.originPosition = (currentOrder == NpcOrderType.HOLD_POSITION || currentOrder == NpcOrderType.OBSERVATION_POINT) && hold != null ? hold : currentPosition;
        this.returningToOrigin = false;
        this.request = requested;
        this.chests = List.copyOf(usableChests);
        this.chestIndex = 0;
        this.chest = this.chests.get(0);
        this.interactionTicks = 0;
        this.approachTicks = 0;
        this.startDelay = Math.max(0, delay);
        this.chestVisualOpen = false;
        this.movement.reset();
    }

    boolean advanceChest() {
        ++this.chestIndex;
        this.interactionTicks = 0;
        this.approachTicks = 0;
        if (this.chestIndex >= this.chests.size()) {
            return false;
        }
        this.chest = this.chests.get(this.chestIndex);
        return true;
    }

    void beginReturn() {
        this.returningToOrigin = true;
        this.movement.reset();
    }

    void cancelAll() {
        this.clearRun();
        this.clearRestoreContext();
    }

    boolean ownsMovement() {
        return this.request.isRun() || this.returningToOrigin;
    }

    void clearRun() {
        this.request = NpcSupplyRequest.NONE;
        this.chests = List.of();
        this.chestIndex = 0;
        this.chest = null;
        this.interactionTicks = 0;
        this.approachTicks = 0;
        this.startDelay = 0;
        this.chestVisualOpen = false;
        this.movement.stop();
    }

    void clearRestoreContext() {
        this.commandDestinationBefore = null;
        this.holdPositionBefore = null;
        this.originPosition = null;
        this.returningToOrigin = false;
    }
}

