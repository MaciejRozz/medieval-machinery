/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2680
 *  net.minecraft.class_3908
 */
package com.medievalmachinery.mod.blockentity.machine.cannon;

import net.minecraft.class_1263;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_3908;

public interface LegacyStationaryCannonAccess
extends class_1263,
class_3908 {
    public static final int GUNPOWDER_SLOT = 0;
    public static final int CANNONBALL_SLOT = 1;

    public float getShapeYawOffset();

    public boolean isIgnited();

    public boolean isReadyToFire();

    public boolean tryIgnite();

    public void tickServer(class_1937 var1, class_2338 var2, class_2680 var3);
}

