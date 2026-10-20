/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 */
package com.medievalmachinery.mod.machine.core;

import net.minecraft.class_1792;
import net.minecraft.class_1799;

public interface MachineCrewResourceAccess {
    default public class_1799 medievalMachinery$getPreferredMachineAmmunition() {
        return class_1799.field_8037;
    }

    public int medievalMachinery$countMachineResource(class_1792 var1);

    default public boolean medievalMachinery$hasMachineResource(class_1792 item, int count) {
        return count <= 0 || this.medievalMachinery$countMachineResource(item) >= count;
    }

    public int medievalMachinery$storeMachineResource(class_1792 var1, int var2);

    public boolean medievalMachinery$consumeMachineResource(class_1792 var1, int var2);
}

