/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.machine.core;

import java.util.Objects;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public final class MachineSeat {
    @Nullable
    private UUID occupantUuid;
    private boolean locked;

    public boolean isOccupied() {
        return this.occupantUuid != null;
    }

    public boolean isOccupiedBy(UUID entityUuid) {
        return Objects.equals(this.occupantUuid, entityUuid);
    }

    @Nullable
    public UUID getOccupantUuid() {
        return this.occupantUuid;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public boolean occupy(UUID entityUuid) {
        Objects.requireNonNull(entityUuid, "entityUuid");
        if (this.isOccupied()) {
            return false;
        }
        this.occupantUuid = entityUuid;
        this.locked = false;
        return true;
    }

    public void lock() {
        if (this.isOccupied()) {
            this.locked = true;
        }
    }

    public void unlock() {
        this.locked = false;
    }

    public boolean release() {
        if (!this.isOccupied()) {
            return false;
        }
        this.occupantUuid = null;
        this.locked = false;
        return true;
    }

    public void restore(@Nullable UUID entityUuid, boolean locked) {
        this.occupantUuid = entityUuid;
        this.locked = entityUuid != null && locked;
    }
}

