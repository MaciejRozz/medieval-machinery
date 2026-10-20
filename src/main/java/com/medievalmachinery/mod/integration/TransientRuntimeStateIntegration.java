/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
 */
package com.medievalmachinery.mod.integration;

import com.medievalmachinery.mod.item.OrderSigilCrafting;
import com.medievalmachinery.mod.network.WatchpostNetworking;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierCrowdCoordinator;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public final class TransientRuntimeStateIntegration {
    private TransientRuntimeStateIntegration() {
    }

    public static void register() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            OrderSigilCrafting.clearWarning(handler.field_14140.method_5667());
            WatchpostNetworking.clearEditorState(handler.field_14140.method_5667());
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            OrderSigilCrafting.clearWarnings();
            MedievalSoldierCrowdCoordinator.clearAll();
            WatchpostNetworking.clearAllTransientState();
        });
    }
}

