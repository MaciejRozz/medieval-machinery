/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.organization;

import java.util.Locale;

public enum CommandGroupType {
    INFANTRY,
    RANGED,
    KNIGHTS,
    MOUNTED,
    MACHINISTS,
    SCOUTS,
    QUARTERMASTERS,
    CUSTOM;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static CommandGroupType fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return CUSTOM;
        }
        for (CommandGroupType type : CommandGroupType.values()) {
            if (!type.serializedName().equals(value)) continue;
            return type;
        }
        return CUSTOM;
    }
}

