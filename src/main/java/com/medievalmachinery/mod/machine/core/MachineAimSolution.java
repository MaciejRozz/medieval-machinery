/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.machine.core;

public record MachineAimSolution(float yaw, float pitch, boolean valid) {
    public static final MachineAimSolution INVALID = new MachineAimSolution(0.0f, 0.0f, false);
}

