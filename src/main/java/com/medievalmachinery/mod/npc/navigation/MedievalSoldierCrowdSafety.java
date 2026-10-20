/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2350$class_2353
 */
package com.medievalmachinery.mod.npc.navigation;

import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;

public final class MedievalSoldierCrowdSafety {
    private MedievalSoldierCrowdSafety() {
    }

    public static boolean isDangerousCrowdEdge(class_1937 world, class_2338 feet) {
        if (world == null || feet == null) {
            return false;
        }
        for (class_2350 direction : class_2350.class_2353.field_11062) {
            class_2338 sideFloor = feet.method_10093(direction).method_10074();
            if (!world.method_8320(sideFloor).method_26220((class_1922)world, sideFloor).method_1110()) continue;
            return true;
        }
        return false;
    }
}

