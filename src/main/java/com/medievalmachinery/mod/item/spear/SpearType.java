/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.item.spear;

public enum SpearType {
    WINGED("winged", 1.0f, 3.75, 1.15f),
    WAR("war", 1.2f, 4.0, 1.0f),
    GUARD("guard", 1.05f, 4.5, 1.25f),
    LONG("long", 1.1f, 4.25, 1.1f);

    private final String id;
    private final float attackSpeed;
    private final double reach;
    private final float durabilityMultiplier;

    private SpearType(String id, float attackSpeed, double reach, float durabilityMultiplier) {
        this.id = id;
        this.attackSpeed = attackSpeed;
        this.reach = reach;
        this.durabilityMultiplier = durabilityMultiplier;
    }

    public String id() {
        return this.id;
    }

    public float attackSpeed() {
        return this.attackSpeed;
    }

    public double reach() {
        return this.reach;
    }

    public float durabilityMultiplier() {
        return this.durabilityMultiplier;
    }

    public int damageOffset() {
        return this == WINGED ? 1 : (this == GUARD ? -1 : 0);
    }
}

