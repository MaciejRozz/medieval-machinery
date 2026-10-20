/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

import java.util.Locale;

public enum NpcCombatStance {
    PASSIVE,
    NEUTRAL,
    AGGRESSIVE,
    RAID;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static NpcCombatStance fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return NEUTRAL;
        }
        for (NpcCombatStance stance : NpcCombatStance.values()) {
            if (!stance.serializedName().equals(value)) continue;
            return stance;
        }
        return NEUTRAL;
    }
}

