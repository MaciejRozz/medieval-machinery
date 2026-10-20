/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_2378
 *  net.minecraft.class_3218
 *  net.minecraft.class_4153
 *  net.minecraft.class_6880
 *  net.minecraft.class_7477
 *  net.minecraft.class_7924
 */
package com.medievalmachinery.mod.npc.entity;

import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2378;
import net.minecraft.class_3218;
import net.minecraft.class_4153;
import net.minecraft.class_6880;
import net.minecraft.class_7477;
import net.minecraft.class_7924;

final class QuartermasterComposterPoiService {
    private QuartermasterComposterPoiService() {
    }

    static void suppress(class_3218 world, class_2338 pos) {
        if (world == null || pos == null || !world.method_22340(pos)) {
            return;
        }
        class_4153 poi = world.method_19494();
        if (poi.method_26339(class_7477.field_39282, pos)) {
            poi.method_19112(pos);
        }
    }

    static void restore(class_3218 world, class_2338 pos) {
        if (world == null || pos == null || !world.method_22340(pos) || !world.method_8320(pos).method_27852(class_2246.field_17563)) {
            return;
        }
        class_4153 poi = world.method_19494();
        if (poi.method_26339(class_7477.field_39282, pos)) {
            return;
        }
        class_2378 registry = world.method_30349().method_30530(class_7924.field_41212);
        poi.method_19115(pos, (class_6880)registry.method_40290(class_7477.field_39282));
    }
}

