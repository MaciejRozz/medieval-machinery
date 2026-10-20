/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 */
package com.medievalmachinery.mod.machine.cannon;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1792;

public final class CannonBallistics {
    private static final float CANNONBALL_RANGE_BONUS = 24.0f;

    private CannonBallistics() {
    }

    public static float maximumRange(class_1792 ammunition) {
        MedievalMachineryConfig config = MedievalMachineryConfig.get();
        if (ammunition == ModItems.TNT_CANNONBALL) {
            return (float)config.tnt.range + 24.0f;
        }
        if (ammunition == ModItems.HEAVY_IRON_CANNONBALL) {
            return (float)config.heavyIron.range + 24.0f;
        }
        if (ammunition == ModItems.IRON_CANNONBALL) {
            return (float)config.iron.range + 24.0f;
        }
        return (float)config.stone.range + 24.0f;
    }

    public static float launchSpeed(class_1792 ammunition) {
        double desiredRange = CannonBallistics.maximumRange(ammunition);
        double gravity = 0.05;
        double sinTwoTheta = Math.sin(Math.toRadians(80.0));
        return (float)Math.sqrt(desiredRange * gravity / sinTwoTheta);
    }
}

