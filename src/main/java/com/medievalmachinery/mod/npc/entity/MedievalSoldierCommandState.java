/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierCommandState {
    int explicitGraceTicks;
    @Nullable
    NpcOrderType suspendedOrder;
    @Nullable
    class_2338 suspendedDestination;
    @Nullable
    class_243 suspendedHold;
    boolean suspendedByCombat;

    MedievalSoldierCommandState() {
    }

    void tick() {
        if (this.explicitGraceTicks > 0) {
            --this.explicitGraceTicks;
        }
    }

    void beginGrace(int ticks) {
        this.explicitGraceTicks = Math.max(this.explicitGraceTicks, ticks);
    }

    boolean hasExplicitGrace() {
        return this.explicitGraceTicks > 0;
    }

    boolean wasSuspendedByCombat() {
        return this.suspendedByCombat;
    }

    boolean hasSuspendedOrder() {
        return this.suspendedOrder != null;
    }

    void suspend(NpcOrderType order, @Nullable class_2338 destination, @Nullable class_243 hold, boolean combat) {
        this.suspendedOrder = order;
        this.suspendedDestination = destination == null ? null : destination.method_10062();
        this.suspendedHold = hold;
        this.suspendedByCombat = combat;
    }

    void markCombatSuspension() {
        this.suspendedByCombat = true;
    }

    void clearSuspension() {
        this.suspendedOrder = null;
        this.suspendedDestination = null;
        this.suspendedHold = null;
        this.suspendedByCombat = false;
    }

    NpcOrderType suspendedOrderOr(NpcOrderType fallback) {
        return this.suspendedOrder == null ? fallback : this.suspendedOrder;
    }

    boolean temporaryCombatMayInterrupt() {
        return this.explicitGraceTicks <= 0;
    }
}

