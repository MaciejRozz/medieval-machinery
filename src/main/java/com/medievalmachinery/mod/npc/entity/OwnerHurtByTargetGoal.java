/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierGroupCombatCoordinator;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.class_1309;
import net.minecraft.class_1352;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_3218;

final class OwnerHurtByTargetGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private class_1309 assistTarget;
    private int lastHandledOwnerHurtTime = Integer.MIN_VALUE;

    OwnerHurtByTargetGoal(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18408));
    }

    public boolean method_6264() {
        class_1657 owner;
        class_1937 class_19372;
        if (!this.soldier.mayAcquireCombatTarget() || this.soldier.getCombatStance() == NpcCombatStance.PASSIVE || !((class_19372 = this.soldier.method_37908()) instanceof class_3218)) {
            return false;
        }
        class_3218 world = (class_3218)class_19372;
        UUID ownerId = this.soldier.getNpcIdentity().getOwnerId();
        class_1657 class_16572 = owner = ownerId == null ? null : world.method_18470(ownerId);
        if (owner == null || !owner.method_5805()) {
            return false;
        }
        class_1309 attacker = owner.method_6065();
        int ownerHurtTime = owner.method_6117();
        if (ownerHurtTime == this.lastHandledOwnerHurtTime) {
            return false;
        }
        if (!this.soldier.canAssistOwnerAgainst(attacker)) {
            return false;
        }
        if (this.soldier.method_5968() == attacker) {
            return false;
        }
        this.assistTarget = attacker;
        return true;
    }

    public void method_6269() {
        if (this.assistTarget != null) {
            class_1309 coordinated = MedievalSoldierGroupCombatCoordinator.coordinatedTarget(this.soldier, this.assistTarget, true);
            this.soldier.method_5980(coordinated != null ? coordinated : this.assistTarget);
            this.soldier.alertNearbyFriendlySoldiers(this.assistTarget);
            class_1937 class_19372 = this.soldier.method_37908();
            if (class_19372 instanceof class_3218) {
                class_1657 owner;
                class_3218 world = (class_3218)class_19372;
                UUID ownerId = this.soldier.getNpcIdentity().getOwnerId();
                class_1657 class_16572 = owner = ownerId == null ? null : world.method_18470(ownerId);
                if (owner != null) {
                    this.lastHandledOwnerHurtTime = owner.method_6117();
                }
            }
        }
    }

    public boolean method_6266() {
        return false;
    }

    public void method_6270() {
        this.assistTarget = null;
    }
}

