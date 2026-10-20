/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 */
package com.medievalmachinery.mod.machine.core;

import net.minecraft.class_1792;

public record MachineCrewSupplyNeed(class_1792 item, int minimumCount, int desiredCount) {
    public MachineCrewSupplyNeed {
        if (item == null) {
            throw new IllegalArgumentException("item");
        }
        minimumCount = Math.max(0, minimumCount);
        desiredCount = Math.max(minimumCount, desiredCount);
    }
}

