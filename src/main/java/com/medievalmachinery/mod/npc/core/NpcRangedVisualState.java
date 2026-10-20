/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

public enum NpcRangedVisualState {
    IDLE,
    LOAD,
    PREPARE,
    AIM,
    RELEASE;


    public static NpcRangedVisualState fromOrdinal(int value) {
        NpcRangedVisualState[] values = NpcRangedVisualState.values();
        return value >= 0 && value < values.length ? values[value] : IDLE;
    }
}

