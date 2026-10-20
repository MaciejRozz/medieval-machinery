/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.entity;

import java.util.UUID;

final class MedievalSoldierMountCommandState {
    private UUID targetVehicleId;
    private int remainingTicks;

    MedievalSoldierMountCommandState() {
    }

    boolean active() {
        return this.targetVehicleId != null && this.remainingTicks > 0;
    }

    UUID targetVehicleId() {
        return this.targetVehicleId;
    }

    void begin(UUID vehicleId) {
        this.targetVehicleId = vehicleId;
        this.remainingTicks = 200;
    }

    void tick() {
        if (this.remainingTicks > 0) {
            --this.remainingTicks;
        }
        if (this.remainingTicks <= 0) {
            this.clear();
        }
    }

    void clear() {
        this.targetVehicleId = null;
        this.remainingTicks = 0;
    }
}

