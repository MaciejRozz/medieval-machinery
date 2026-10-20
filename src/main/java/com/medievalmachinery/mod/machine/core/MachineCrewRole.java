/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.machine.core;

import java.util.Locale;

public enum MachineCrewRole {
    OPERATOR,
    LOADER,
    MAINTENANCE;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static MachineCrewRole fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return OPERATOR;
        }
        for (MachineCrewRole role : MachineCrewRole.values()) {
            if (!role.serializedName().equals(value)) continue;
            return role;
        }
        return OPERATOR;
    }
}

