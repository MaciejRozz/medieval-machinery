/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

public enum NpcSupplyRequest {
    NONE,
    SUPPLY,
    AUTO_FOOD,
    AUTO_WEAPON;


    public boolean isRun() {
        return this != NONE;
    }

    public boolean isAutomatic() {
        return this == AUTO_FOOD || this == AUTO_WEAPON;
    }
}

