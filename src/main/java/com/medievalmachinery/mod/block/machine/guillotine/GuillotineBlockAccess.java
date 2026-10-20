/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.block.machine.guillotine;

import java.util.UUID;

public interface GuillotineBlockAccess {
    public boolean toggleBanOnExecution();

    public boolean isSequenceRunning();

    public void startSequence();

    public boolean isOccupiedBy(UUID var1);

    public boolean isLocked();

    public boolean releaseOccupant();

    public boolean isEmpty();

    public boolean occupy(UUID var1);

    public boolean updateRedstonePower(boolean var1);
}

