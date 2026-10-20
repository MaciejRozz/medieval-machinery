/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1269
 *  net.minecraft.class_2338
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.storage;

import java.util.Objects;
import net.minecraft.class_1269;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_3222;

public final class WickerBasketCarryBridge {
    private static PickupHandler pickupHandler;

    private WickerBasketCarryBridge() {
    }

    public static void install(PickupHandler handler) {
        if (pickupHandler != null) {
            throw new IllegalStateException("Basket carry bridge already installed");
        }
        pickupHandler = Objects.requireNonNull(handler, "handler");
    }

    public static class_1269 pickUp(class_3222 player, class_2338 pos, class_2680 state, class_2586 basket) {
        if (pickupHandler == null) {
            throw new IllegalStateException("Basket carry bridge not installed");
        }
        return pickupHandler.pickUp(player, pos, state, basket);
    }

    @FunctionalInterface
    public static interface PickupHandler {
        public class_1269 pickUp(class_3222 var1, class_2338 var2, class_2680 var3, class_2586 var4);
    }
}

