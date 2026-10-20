/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.organization;

import java.util.UUID;
import net.minecraft.class_3218;

@FunctionalInterface
public interface KingdomActivityProvider {
    public boolean isKingdomActive(class_3218 var1, UUID var2);
}

