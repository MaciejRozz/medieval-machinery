/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
 *  net.minecraft.class_1297
 *  net.minecraft.class_1621
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.test;

import com.medievalmachinery.mod.test.SlimeSpawnTestState;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.class_1297;
import net.minecraft.class_1621;
import net.minecraft.class_3218;

public final class TestWorldSpawnControl {
    private TestWorldSpawnControl() {
    }

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register(TestWorldSpawnControl::onEntityLoad);
    }

    private static void onEntityLoad(class_1297 entity, class_3218 world) {
        if (entity instanceof class_1621 && SlimeSpawnTestState.get(world).areSlimesDisabled()) {
            entity.method_31472();
        }
    }
}

