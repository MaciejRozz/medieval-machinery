/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_243
 */
package com.medievalmachinery.mod.catapult;

import net.minecraft.class_1297;
import net.minecraft.class_243;

public final class CatapultAnchors {
    public static final double MODEL_Y_OFFSET = -0.020833333333333332;
    public static final class_243 LAUNCH_LOCAL = new class_243(0.0, 3.9166666666666665, -0.2375);

    private CatapultAnchors() {
    }

    public static class_243 rootAnchorToWorld(class_1297 entity, class_243 local) {
        double yaw = Math.toRadians(entity.method_36454());
        double sin = Math.sin(yaw);
        double cos = Math.cos(yaw);
        double worldX = entity.method_23317() + local.field_1352 * cos + local.field_1350 * sin;
        double worldZ = entity.method_23321() + local.field_1352 * sin - local.field_1350 * cos;
        return new class_243(worldX, entity.method_23318() + local.field_1351, worldZ);
    }

    public static class_243 launchWorld(class_1297 entity) {
        return CatapultAnchors.rootAnchorToWorld(entity, LAUNCH_LOCAL);
    }
}

