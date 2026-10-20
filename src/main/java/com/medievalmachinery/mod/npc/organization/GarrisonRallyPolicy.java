/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1922
 *  net.minecraft.class_2338
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import java.util.UUID;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_3218;

public final class GarrisonRallyPolicy {
    public static final double INFLUENCE_RADIUS = 18.0;
    private static final double[] RADII = new double[]{2.0, 2.8, 3.6, 4.4};

    private GarrisonRallyPolicy() {
    }

    public static boolean isWithinInfluence(class_243 position, class_2338 marker) {
        return position != null && marker != null && position.method_1025(class_243.method_24953((class_2382)marker)) <= 324.0;
    }

    public static class_243 nearbyAnchor(class_3218 world, class_2338 marker, UUID soldierId) {
        if (marker == null) {
            return class_243.field_1353;
        }
        int seed = soldierId == null ? marker.hashCode() : soldierId.hashCode();
        int start = Math.floorMod(seed, 16);
        for (int ring = 0; ring < RADII.length; ++ring) {
            double radius = RADII[ring];
            for (int step = 0; step < 16; ++step) {
                int idx = start + step * 5 + ring * 3 & 0xF;
                double angle = (double)idx * 0.39269908169872414;
                int x = marker.method_10263() + (int)Math.round(Math.cos(angle) * radius);
                int z = marker.method_10260() + (int)Math.round(Math.sin(angle) * radius);
                class_2338 feet = GarrisonRallyPolicy.findStandableFeet(world, new class_2338(x, marker.method_10264() + 1, z));
                if (feet == null) continue;
                return class_243.method_24955((class_2382)feet);
            }
        }
        return class_243.method_24955((class_2382)marker.method_10084());
    }

    public static IdleAnchor resolveIdleAnchor(class_3218 world, UUID castleId, UUID soldierId, class_243 currentPosition) {
        GarrisonData garrison;
        if (world != null && castleId != null && (garrison = GarrisonManager.get(world).get(castleId)) != null && garrison.getRallyPoint() != null && GarrisonRallyPolicy.isWithinInfluence(currentPosition, garrison.getRallyPoint())) {
            return new IdleAnchor(GarrisonRallyPolicy.nearbyAnchor(world, garrison.getRallyPoint(), soldierId), true);
        }
        return new IdleAnchor(currentPosition, false);
    }

    public static class_243 resolveReturnDestination(class_3218 world, UUID castleId, UUID soldierId, class_2338 homePosition) {
        GarrisonData garrison;
        if (world != null && castleId != null && (garrison = GarrisonManager.get(world).get(castleId)) != null && garrison.getRallyPoint() != null) {
            return GarrisonRallyPolicy.nearbyAnchor(world, garrison.getRallyPoint(), soldierId);
        }
        return homePosition == null ? null : class_243.method_24955((class_2382)homePosition.method_10084());
    }

    private static class_2338 findStandableFeet(class_3218 world, class_2338 guess) {
        for (int dy = 2; dy >= -3; --dy) {
            class_2338 floor;
            class_2338 feet = guess.method_10069(0, dy, 0);
            if (!world.method_8320(feet).method_26220((class_1922)world, feet).method_1110() || !world.method_8320(feet.method_10084()).method_26220((class_1922)world, feet.method_10084()).method_1110() || world.method_8320(floor = feet.method_10074()).method_26220((class_1922)world, floor).method_1110()) continue;
            return feet.method_10062();
        }
        return null;
    }

    public record IdleAnchor(class_243 position, boolean rallyBound) {
    }
}

