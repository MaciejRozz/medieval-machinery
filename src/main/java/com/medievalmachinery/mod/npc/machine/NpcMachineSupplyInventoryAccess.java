/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1258
 *  net.minecraft.class_1263
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2350$class_2353
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_2741
 *  net.minecraft.class_2745
 *  net.minecraft.class_2769
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.machine;

import net.minecraft.class_1258;
import net.minecraft.class_1263;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2745;
import net.minecraft.class_2769;
import net.minecraft.class_3218;

public final class NpcMachineSupplyInventoryAccess {
    private NpcMachineSupplyInventoryAccess() {
    }

    public static class_1263 resolve(class_3218 world, class_2338 pos) {
        if (world == null || pos == null || !world.method_22340(pos)) {
            return null;
        }
        class_2586 firstBe = world.method_8321(pos);
        if (!(firstBe instanceof class_1263)) {
            return null;
        }
        class_1263 first = (class_1263)firstBe;
        class_2680 state = world.method_8320(pos);
        if (!state.method_28498((class_2769)class_2741.field_12506) || state.method_11654((class_2769)class_2741.field_12506) == class_2745.field_12569) {
            return first;
        }
        for (class_2350 direction : class_2350.class_2353.field_11062) {
            class_2586 otherBe;
            class_2680 otherState;
            class_2338 otherPos = pos.method_10093(direction);
            if (!world.method_22340(otherPos) || (otherState = world.method_8320(otherPos)).method_26204() != state.method_26204() || !otherState.method_28498((class_2769)class_2741.field_12506) || otherState.method_11654((class_2769)class_2741.field_12506) == class_2745.field_12569 || !((otherBe = world.method_8321(otherPos)) instanceof class_1263)) continue;
            class_1263 second = (class_1263)otherBe;
            return new class_1258(first, second);
        }
        return first;
    }
}

