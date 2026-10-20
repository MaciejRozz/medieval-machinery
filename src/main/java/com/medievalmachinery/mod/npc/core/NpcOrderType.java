/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

import java.util.Locale;

public enum NpcOrderType {
    IDLE,
    FOLLOW,
    HOLD_POSITION,
    PATROL,
    RETURN_TO_GARRISON,
    MOVE_TO_POSITION,
    OBSERVATION_POINT,
    WORK;


    public String serializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static NpcOrderType fromSerializedName(String value) {
        if (value == null || value.isBlank()) {
            return IDLE;
        }
        for (NpcOrderType order : NpcOrderType.values()) {
            if (!order.serializedName().equals(value)) continue;
            return order;
        }
        return IDLE;
    }
}

