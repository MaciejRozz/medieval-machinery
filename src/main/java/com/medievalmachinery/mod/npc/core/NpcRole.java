/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

import java.util.Locale;

public enum NpcRole {
    RECRUIT,
    SWORDSMAN,
    AXEMAN,
    ARCHER,
    CROSSBOWMAN,
    SPEARMAN,
    CANNONEER,
    ENGINEER,
    KNIGHT,
    MOUNTED_ARCHER,
    MOUNTED_KNIGHT,
    QUARTERMASTER,
    SCOUT;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static NpcRole fromOrdinal(int ordinal) {
        NpcRole[] roles = NpcRole.values();
        return ordinal >= 0 && ordinal < roles.length ? roles[ordinal] : SWORDSMAN;
    }

    public static NpcRole fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return SWORDSMAN;
        }
        for (NpcRole role : NpcRole.values()) {
            if (!role.serializedName().equals(value)) continue;
            return role;
        }
        return SWORDSMAN;
    }
}

