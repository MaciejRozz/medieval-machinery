/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 */
package com.medievalmachinery.mod.npc.entity;

import net.minecraft.class_1799;

final class MedievalSoldierEatingState {
    int sourceSlot = -1;
    int ticksRemaining;
    class_1799 previousOffhand = class_1799.field_8037;
    float pendingHeal;
    int pendingHunger;
    float pendingSaturationModifier;

    MedievalSoldierEatingState() {
    }

    boolean isEating() {
        return this.sourceSlot >= 0;
    }

    void begin(int slot, int ticks, class_1799 previous, float heal, int hunger, float saturationModifier) {
        this.sourceSlot = slot;
        this.ticksRemaining = ticks;
        this.previousOffhand = previous.method_7972();
        this.pendingHeal = heal;
        this.pendingHunger = hunger;
        this.pendingSaturationModifier = saturationModifier;
    }

    void clear() {
        this.sourceSlot = -1;
        this.ticksRemaining = 0;
        this.previousOffhand = class_1799.field_8037;
        this.pendingHeal = 0.0f;
        this.pendingHunger = 0;
        this.pendingSaturationModifier = 0.0f;
    }
}

