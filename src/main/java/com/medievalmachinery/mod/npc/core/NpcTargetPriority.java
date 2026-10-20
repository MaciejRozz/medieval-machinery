/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

public enum NpcTargetPriority {
    CLOSEST("closest"),
    INFANTRY("infantry"),
    CAVALRY("cavalry"),
    SIEGE_WEAPONS("siege_weapons"),
    SHIPS("ships");

    private final String serializedName;

    private NpcTargetPriority(String serializedName) {
        this.serializedName = serializedName;
    }

    public String serializedName() {
        return this.serializedName;
    }

    public static NpcTargetPriority fromSerializedName(String value) {
        if (value != null) {
            for (NpcTargetPriority priority : NpcTargetPriority.values()) {
                if (!priority.serializedName.equalsIgnoreCase(value)) continue;
                return priority;
            }
        }
        return CLOSEST;
    }
}

