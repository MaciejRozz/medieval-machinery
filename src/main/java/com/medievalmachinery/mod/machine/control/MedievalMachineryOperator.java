/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_2487
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.machine.control;

import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_2487;
import org.jetbrains.annotations.Nullable;

public final class MedievalMachineryOperator {
    private static final String UUID_KEY = "MedievalMachineryOperatorUuid";
    private static final String PLAYER_KEY = "MedievalMachineryOperatorIsPlayer";
    @Nullable
    private UUID uuid;
    private boolean player;

    public boolean tryClaim(class_1297 entity) {
        if (this.uuid != null && !this.uuid.equals(entity.method_5667())) {
            return false;
        }
        this.uuid = entity.method_5667();
        this.player = entity instanceof class_1657;
        return true;
    }

    public boolean tryClaim(UUID candidate, boolean playerOperator) {
        if (this.uuid != null && !this.uuid.equals(candidate)) {
            return false;
        }
        this.uuid = candidate;
        this.player = playerOperator;
        return true;
    }

    @Nullable
    public UUID getOperatorUuid() {
        return this.uuid;
    }

    public void forceRelease() {
        this.clear();
    }

    public boolean isOperatedBy(UUID candidate) {
        return this.uuid != null && this.uuid.equals(candidate);
    }

    public boolean isOccupied() {
        return this.uuid != null;
    }

    public boolean isPlayerOperator() {
        return this.uuid != null && this.player;
    }

    @Nullable
    public UUID getUuid() {
        return this.uuid;
    }

    public boolean release(UUID candidate) {
        if (!this.isOperatedBy(candidate)) {
            return false;
        }
        this.clear();
        return true;
    }

    public void clear() {
        this.uuid = null;
        this.player = false;
    }

    public void writeNbt(class_2487 nbt) {
        if (this.uuid != null) {
            nbt.method_25927(UUID_KEY, this.uuid);
            nbt.method_10556(PLAYER_KEY, this.player);
        }
    }

    public void readNbt(class_2487 nbt) {
        if (nbt.method_25928(UUID_KEY)) {
            this.uuid = nbt.method_25926(UUID_KEY);
            this.player = nbt.method_10577(PLAYER_KEY);
        } else {
            this.clear();
        }
    }
}

