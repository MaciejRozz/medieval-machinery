/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.loader.api.FabricLoader
 */
package com.medievalmachinery.mod.client.compat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(value=EnvType.CLIENT)
public final class CombatModCompat {
    private static final boolean BETTER_COMBAT = FabricLoader.getInstance().isModLoaded("bettercombat");

    private CombatModCompat() {
    }

    public static boolean betterCombatLoaded() {
        return BETTER_COMBAT;
    }
}

