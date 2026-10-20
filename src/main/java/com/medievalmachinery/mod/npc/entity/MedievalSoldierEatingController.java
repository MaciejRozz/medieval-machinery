/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1799
 *  net.minecraft.class_1937
 *  net.minecraft.class_2392
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEatingState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2392;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_3218;

final class MedievalSoldierEatingController {
    MedievalSoldierEatingController() {
    }

    void tick(MedievalSoldierEntity soldier) {
        if (soldier == null || soldier.method_37908().field_9236) {
            return;
        }
        MedievalSoldierEatingState state = soldier.goalEatingState();
        if (state.isEating()) {
            if (soldier.isBusyWithCombatForEating()) {
                this.finishEating(soldier, false);
                return;
            }
            this.tickEatingUse(soldier);
            return;
        }
        if (!soldier.isStaggeredAiTick(20)) {
            return;
        }
        if (!soldier.goalHungerData().shouldEat(soldier) || soldier.isBusyWithCombatForEating() || soldier.method_6115() || !soldier.hasFoodInStorage()) {
            return;
        }
        soldier.beginEatingBestFood();
    }

    void tickEatingUse(MedievalSoldierEntity soldier) {
        MedievalSoldierEatingState state = soldier.goalEatingState();
        if (state.sourceSlot < 0) {
            return;
        }
        state.ticksRemaining = Math.max(0, state.ticksRemaining - 1);
        class_1937 class_19372 = soldier.method_37908();
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            if (soldier.isStaggeredAiTick(4) && !soldier.method_6079().method_7960()) {
                serverWorld.method_14199((class_2394)new class_2392(class_2398.field_11218, soldier.method_6079()), soldier.method_23317(), soldier.method_23320() - 0.18, soldier.method_23321(), 3, 0.12, 0.08, 0.12, 0.02);
            }
        }
        if (state.ticksRemaining <= 0) {
            this.finishEating(soldier, true);
        }
    }

    void finishEating(MedievalSoldierEntity soldier, boolean completed) {
        class_1799 leftover;
        MedievalSoldierEatingState state = soldier.goalEatingState();
        if (state.sourceSlot < 0) {
            return;
        }
        class_1799 remainder = soldier.method_6079().method_7972();
        if (completed) {
            if (!remainder.method_7960() && remainder.method_19267()) {
                remainder = remainder.method_7910(soldier.method_37908(), (class_1309)soldier);
            }
            if (state.pendingHunger > 0) {
                soldier.goalHungerData().eat(state.pendingHunger, state.pendingSaturationModifier);
                soldier.syncTrackedHunger();
            }
            if (state.pendingHeal > 0.0f && soldier.method_6032() < soldier.method_6063()) {
                soldier.method_6025(state.pendingHeal);
            }
        }
        if (!remainder.method_7960() && !(leftover = soldier.supplyPersonalInventory().method_5491(remainder)).method_7960()) {
            soldier.method_5775(leftover);
        }
        soldier.method_6075();
        soldier.method_5673(class_1304.field_6171, state.previousOffhand);
        state.previousOffhand = class_1799.field_8037;
        state.pendingHeal = 0.0f;
        state.pendingHunger = 0;
        state.pendingSaturationModifier = 0.0f;
        state.ticksRemaining = 0;
        state.sourceSlot = -1;
        soldier.supplyPersonalInventory().method_5431();
    }
}

