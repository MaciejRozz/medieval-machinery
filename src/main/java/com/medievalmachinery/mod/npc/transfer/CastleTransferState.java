/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.transfer;

import java.util.Locale;

public enum CastleTransferState {
    NONE,
    BLOCKED_IN_COMBAT,
    GATHERING,
    WAITING_FOR_EXIT,
    DEPARTING,
    WAITING_GROUP_DEPARTURE,
    TRAVELLING,
    ARRIVING,
    WAITING_GROUP_COMMIT,
    COMPLETE,
    NEEDS_DESTINATION;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static CastleTransferState fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return NONE;
        }
        for (CastleTransferState state : CastleTransferState.values()) {
            if (!state.serializedName().equals(value)) continue;
            return state;
        }
        return NONE;
    }

    public boolean isActive() {
        return this != NONE && this != COMPLETE;
    }
}

