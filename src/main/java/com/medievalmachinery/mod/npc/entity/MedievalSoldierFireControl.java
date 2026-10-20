/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1799
 *  net.minecraft.class_1937
 *  net.minecraft.class_2382
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcEquipmentProfile;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcWeaponProfileResolver;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import net.minecraft.class_3959;

final class MedievalSoldierFireControl {
    MedievalSoldierFireControl() {
    }

    boolean hasClearBlockLineOfFire(MedievalSoldierEntity soldier, class_243 shooterPos, class_1309 target) {
        class_243 start = shooterPos.method_1031(0.0, (double)soldier.method_5751() - 0.1, 0.0);
        class_243 end = new class_243(target.method_23317(), target.method_23323(0.58), target.method_23321());
        return soldier.method_37908().method_17742(new class_3959(start, end, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)soldier)).method_17783() == class_239.class_240.field_1333;
    }

    boolean withinRangedRetreatLeash(MedievalSoldierEntity soldier, class_243 candidate) {
        class_1937 class_19372;
        if (candidate == null) {
            return false;
        }
        if (soldier.getOrderType() == NpcOrderType.FOLLOW && (class_19372 = soldier.method_37908()) instanceof class_3218) {
            class_1657 owner;
            class_3218 world = (class_3218)class_19372;
            UUID ownerId = soldier.getNpcIdentity().getOwnerId();
            class_1657 class_16572 = owner = ownerId == null ? null : world.method_18470(ownerId);
            if (owner != null) {
                class_243 formationAnchor = soldier.formationPointBehind(owner);
                return formationAnchor.method_1025(candidate) <= soldier.fireControlRANGED_RETREAT_LEASH() * soldier.fireControlRANGED_RETREAT_LEASH();
            }
        }
        if (soldier.getOrderType() == NpcOrderType.MOVE_TO_POSITION && soldier.fireControlCommandDestination() != null) {
            class_243 orderAnchor = class_243.method_24955((class_2382)soldier.fireControlCommandDestination());
            return orderAnchor.method_1025(candidate) <= soldier.fireControlRANGED_RETREAT_LEASH() * soldier.fireControlRANGED_RETREAT_LEASH();
        }
        return soldier.fireControlCombatMovementState().withinEngagementLeash(candidate, soldier.fireControlRANGED_RETREAT_LEASH());
    }

    void tickRangedCloseCombatWeaponState(MedievalSoldierEntity soldier) {
        class_1309 target = soldier.method_5968();
        if (!soldier.fireControlCombatState().rangedCloseCombatWeaponActive) {
            if (soldier.isRangedRole() && soldier.isHoldingRangedWeapon() && target != null && target.method_5805() && soldier.isTrueCloseRangedThreat(target, 5.0)) {
                soldier.switchRangedUnitToCloseCombatWeapon();
            }
            return;
        }
        if (target == null || !target.method_5805()) {
            if (++soldier.fireControlCombatState().rangedCloseCombatCalmTicks >= 10) {
                soldier.restoreRangedWeaponAfterCloseCombat();
            }
            return;
        }
        double dx = target.method_23317() - soldier.method_23317();
        double dz = target.method_23321() - soldier.method_23321();
        double horizontalSq = dx * dx + dz * dz;
        double vertical = Math.abs(target.method_23323(0.5) - soldier.method_23323(0.5));
        if (horizontalSq <= 64.0 && vertical < 2.25) {
            soldier.fireControlCombatState().rangedCloseCombatCalmTicks = 0;
        } else if (++soldier.fireControlCombatState().rangedCloseCombatCalmTicks >= 10) {
            soldier.restoreRangedWeaponAfterCloseCombat();
        }
    }

    boolean restoreRangedWeaponAfterCloseCombat(MedievalSoldierEntity soldier) {
        if (!soldier.fireControlCombatState().rangedCloseCombatWeaponActive || !soldier.isRangedRole()) {
            return false;
        }
        int bestSlot = -1;
        long bestScore = Long.MIN_VALUE;
        for (int slot = 0; slot < Math.min(9, soldier.fireControlPersonalInventory().method_5439()); ++slot) {
            long score;
            class_1799 candidate = soldier.fireControlPersonalInventory().method_5438(slot);
            if (!NpcWeaponProfileResolver.resolve(candidate).isRanged() || (score = NpcEquipmentProfile.weaponScore(soldier.getNpcRole(), candidate)) <= bestScore) continue;
            bestScore = score;
            bestSlot = slot;
        }
        if (bestSlot < 0) {
            soldier.fireControlCombatState().rangedCloseCombatWeaponActive = false;
            soldier.fireControlCombatState().rangedCloseCombatCalmTicks = 0;
            soldier.fireControlMaintenanceState().equipmentDirty = true;
            return false;
        }
        class_1799 rangedWeapon = soldier.fireControlPersonalInventory().method_5441(bestSlot);
        class_1799 sidearm = soldier.method_6047().method_7972();
        soldier.method_5673(class_1304.field_6173, rangedWeapon);
        if (!sidearm.method_7960()) {
            soldier.fireControlPersonalInventory().method_5447(bestSlot, sidearm);
        }
        soldier.fireControlPersonalInventory().method_5431();
        soldier.fireControlCombatState().rangedCloseCombatWeaponActive = false;
        soldier.fireControlCombatState().rangedCloseCombatCalmTicks = 0;
        soldier.fireControlMaintenanceState().equipmentDirty = false;
        soldier.fireControlMaintenanceState().lastObservedMainHandItem = soldier.method_6047().method_7909();
        soldier.fireControlMaintenanceState().registryDirty = true;
        soldier.updateWeaponClassDamageModifier();
        return true;
    }
}

