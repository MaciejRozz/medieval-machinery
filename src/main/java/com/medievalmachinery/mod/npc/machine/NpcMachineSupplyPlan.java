/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_2338
 */
package com.medievalmachinery.mod.npc.machine;

import java.util.Map;
import net.minecraft.class_1792;
import net.minecraft.class_2338;

public record NpcMachineSupplyPlan(class_2338 chestPos, Map<class_1792, Integer> reservedItems) {
    public NpcMachineSupplyPlan {
        chestPos = chestPos.method_10062();
        reservedItems = Map.copyOf(reservedItems);
    }
}

