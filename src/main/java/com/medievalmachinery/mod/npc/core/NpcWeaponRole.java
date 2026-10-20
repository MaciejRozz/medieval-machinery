/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

public enum NpcWeaponRole {
    NONE,
    SWORD,
    AXE,
    SPEAR,
    POLEARM,
    HEAVY_MELEE,
    BOW,
    CROSSBOW,
    PICKAXE,
    HOE;


    public boolean isRanged() {
        return this == BOW || this == CROSSBOW;
    }

    public boolean isSpearLike() {
        return this == SPEAR || this == POLEARM;
    }

    public boolean isCloseCombat() {
        return this == SWORD || this == AXE || this.isSpearLike() || this == HEAVY_MELEE;
    }
}

