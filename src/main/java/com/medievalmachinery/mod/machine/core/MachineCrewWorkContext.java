/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.machine.core;

public record MachineCrewWorkContext(double cycleTimeMultiplier, float aimSpreadMultiplier, boolean mayUseVirtualStandardResources) {
    public static final MachineCrewWorkContext DEFAULT = new MachineCrewWorkContext(1.0, 1.0f, false);

    public MachineCrewWorkContext {
        cycleTimeMultiplier = Math.max(0.25, Math.min(2.0, cycleTimeMultiplier));
        aimSpreadMultiplier = Math.max(0.25f, Math.min(2.0f, aimSpreadMultiplier));
    }
}

