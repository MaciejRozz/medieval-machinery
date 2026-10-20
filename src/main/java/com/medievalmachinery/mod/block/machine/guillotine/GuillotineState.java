/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.block.machine.guillotine;

import java.util.Locale;

public enum GuillotineState {
    EMPTY,
    OCCUPIED,
    LOCKED,
    TRIGGERED,
    RESETTING;


    public String asString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static GuillotineState fromString(String value) {
        if (value == null || value.isBlank()) {
            return EMPTY;
        }
        try {
            return GuillotineState.valueOf(value.toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException exception) {
            return EMPTY;
        }
    }
}

