/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.organization;

import java.util.Locale;

public enum KingdomDiplomacyStance {
    NEUTRAL,
    ALLY,
    WAR;


    public static KingdomDiplomacyStance fromName(String value) {
        if (value == null || value.isBlank()) {
            return NEUTRAL;
        }
        try {
            return KingdomDiplomacyStance.valueOf(value.trim().toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException ignored) {
            return NEUTRAL;
        }
    }
}

