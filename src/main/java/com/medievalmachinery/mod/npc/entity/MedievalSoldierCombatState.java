/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.entity;

import java.util.UUID;

final class MedievalSoldierCombatState {
    int shieldReRaiseTicks;
    int shieldBlockCooldownTicks;
    int meleeAttackCooldownTicks;
    int meleeWindupTicks;
    UUID pendingMeleeTargetId;
    boolean rangedCloseCombatWeaponActive;
    int rangedCloseCombatCalmTicks;
    boolean holdFire;
    UUID commandedTargetId;

    MedievalSoldierCombatState() {
    }

    void tickCooldowns() {
        if (this.shieldReRaiseTicks > 0) {
            --this.shieldReRaiseTicks;
        }
        if (this.shieldBlockCooldownTicks > 0) {
            --this.shieldBlockCooldownTicks;
        }
        if (this.meleeAttackCooldownTicks > 0) {
            --this.meleeAttackCooldownTicks;
        }
        if (this.meleeWindupTicks > 0) {
            --this.meleeWindupTicks;
        }
    }

    boolean isHoldFire() {
        return this.holdFire;
    }

    boolean mayUseRangedWeapon() {
        return !this.holdFire;
    }

    void setHoldFire(boolean value) {
        this.holdFire = value;
    }

    boolean hasCommandedTarget() {
        return this.commandedTargetId != null;
    }

    void setCommandedTarget(UUID targetId) {
        this.commandedTargetId = targetId;
    }

    boolean isCommandedTarget(UUID targetId) {
        return targetId != null && this.commandedTargetId != null && this.commandedTargetId.equals(targetId);
    }

    void clearCommandedTarget() {
        this.commandedTargetId = null;
    }

    boolean hasPendingMeleeAttack() {
        return this.pendingMeleeTargetId != null && this.meleeWindupTicks > 0;
    }

    void beginMeleeWindup(UUID targetId, int ticks) {
        this.pendingMeleeTargetId = targetId;
        this.meleeWindupTicks = Math.max(1, ticks);
    }

    boolean meleeWindupReady() {
        return this.pendingMeleeTargetId != null && this.meleeWindupTicks <= 0;
    }

    UUID consumePendingMeleeTarget() {
        UUID target = this.pendingMeleeTargetId;
        this.pendingMeleeTargetId = null;
        this.meleeWindupTicks = 0;
        return target;
    }

    void cancelPendingMeleeAttack() {
        this.pendingMeleeTargetId = null;
        this.meleeWindupTicks = 0;
    }
}

