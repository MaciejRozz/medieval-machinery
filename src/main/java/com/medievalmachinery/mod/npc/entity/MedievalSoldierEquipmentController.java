/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1542
 *  net.minecraft.class_1738
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_238
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.npc.core.NpcEquipmentProfile;
import com.medievalmachinery.mod.npc.core.NpcWeaponProfileResolver;
import com.medievalmachinery.mod.npc.core.NpcWeaponRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEquipmentContext;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMaintenanceState;
import net.minecraft.class_1263;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1542;
import net.minecraft.class_1738;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_238;

final class MedievalSoldierEquipmentController {
    private boolean compactingPersonalStorage;

    MedievalSoldierEquipmentController() {
    }

    boolean beginEvaluation(MedievalSoldierEquipmentContext context) {
        if (context.equipmentEvaluationInProgress()) {
            return false;
        }
        context.setEquipmentEvaluationInProgress(true);
        return true;
    }

    void endEvaluation(MedievalSoldierEquipmentContext context) {
        context.setEquipmentEvaluationInProgress(false);
    }

    boolean isProtectedReserveWeapon(MedievalSoldierEntity soldier, class_1799 stack, int slot, int protectedFamilySlot, int protectedSidearmSlot) {
        return slot == protectedFamilySlot || slot == protectedSidearmSlot;
    }

    boolean isCriticallyWornArmor(MedievalSoldierEntity soldier, class_1799 stack) {
        if (stack == null || stack.method_7960() || !(stack.method_7909() instanceof class_1738) || !stack.method_7963()) {
            return false;
        }
        int remaining = stack.method_7936() - stack.method_7919();
        return remaining <= Math.max(1, (int)Math.ceil((double)stack.method_7936() * 0.05));
    }

    long closeCombatWeaponScore(MedievalSoldierEntity soldier, class_1799 stack) {
        if (!soldier.isCloseCombatWeapon(stack)) {
            return Long.MIN_VALUE;
        }
        NpcWeaponRole role = NpcWeaponProfileResolver.resolve(stack);
        long typePriority = switch (role) {
            case NpcWeaponRole.SWORD -> 2000000000L;
            case NpcWeaponRole.AXE, NpcWeaponRole.HEAVY_MELEE -> 1800000000L;
            case NpcWeaponRole.SPEAR, NpcWeaponRole.POLEARM -> 1600000000L;
            default -> 1000000000L;
        };
        long quality = 0L;
        if (stack.method_7963()) {
            quality += Math.min(100000L, (long)stack.method_7936() * 10L);
            quality += (long)Math.max(0, stack.method_7936() - stack.method_7919());
        }
        if (stack.method_7942()) {
            quality += 250000L;
        }
        return typePriority + quality;
    }

    boolean sameWeaponFamily(MedievalSoldierEntity soldier, class_1799 a, class_1799 b) {
        if (a == null || b == null || a.method_7960() || b.method_7960()) {
            return false;
        }
        if (NpcWeaponProfileResolver.sameFamily(a, b)) {
            return true;
        }
        NpcWeaponRole ar = NpcWeaponProfileResolver.resolve(a);
        NpcWeaponRole br = NpcWeaponProfileResolver.resolve(b);
        return ar == NpcWeaponRole.NONE && br == NpcWeaponRole.NONE && a.method_7909() == b.method_7909();
    }

    long storedArmorScore(MedievalSoldierEntity soldier, class_1799 stack) {
        class_1792 class_17922;
        if (stack == null || stack.method_7960() || !((class_17922 = stack.method_7909()) instanceof class_1738)) {
            return Long.MIN_VALUE;
        }
        class_1738 armor = (class_1738)class_17922;
        long score = 0L;
        score += (long)armor.method_7687() * 10000000L;
        if (stack.method_7963()) {
            score += Math.min(500000L, (long)stack.method_7936() * 100L);
            score += (long)Math.max(0, stack.method_7936() - stack.method_7919());
        }
        if (stack.method_7942()) {
            score += 1000000L;
        }
        return score;
    }

    boolean isBetterArmorPiece(MedievalSoldierEntity soldier, class_1799 candidate, class_1799 current) {
        float currentToughness;
        class_1792 class_17922 = candidate.method_7909();
        if (!(class_17922 instanceof class_1738)) {
            return false;
        }
        class_1738 candidateArmor = (class_1738)class_17922;
        if (current == null || current.method_7960()) {
            return true;
        }
        class_1792 class_17923 = current.method_7909();
        if (!(class_17923 instanceof class_1738)) {
            return true;
        }
        class_1738 currentArmor = (class_1738)class_17923;
        int candidateProtection = candidateArmor.method_7687();
        int currentProtection = currentArmor.method_7687();
        if (candidateProtection != currentProtection) {
            return candidateProtection > currentProtection;
        }
        float candidateToughness = candidateArmor.method_26353();
        if (Float.compare(candidateToughness, currentToughness = currentArmor.method_26353()) != 0) {
            return candidateToughness > currentToughness;
        }
        int candidateRemaining = candidate.method_7963() ? candidate.method_7936() - candidate.method_7919() : Integer.MAX_VALUE;
        int currentRemaining = current.method_7963() ? current.method_7936() - current.method_7919() : Integer.MAX_VALUE;
        return candidateRemaining > currentRemaining;
    }

    int countStoredWeaponFamily(MedievalSoldierEntity soldier, class_1799 reference) {
        int count = 0;
        for (int i = 0; i < Math.min(9, soldier.equipmentPersonalInventory().method_5439()); ++i) {
            class_1799 stack = soldier.equipmentPersonalInventory().method_5438(i);
            if (!soldier.sameWeaponFamily(reference, stack)) continue;
            count += stack.method_7947();
        }
        return count;
    }

    boolean ensureReplacementWeaponFromSupply(MedievalSoldierEntity soldier, class_1263 inventory) {
        class_1799 main = soldier.method_6047();
        if (main.method_7960() || !NpcEquipmentProfile.isSupportedWeapon(main)) {
            return false;
        }
        if (this.countStoredWeaponFamily(soldier, main) >= 1) {
            return false;
        }
        int bestSlot = -1;
        long bestScore = Long.MIN_VALUE;
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            long score;
            class_1799 candidate = inventory.method_5438(slot);
            if (!soldier.sameWeaponFamily(main, candidate) || (score = NpcEquipmentProfile.weaponScore(soldier.getNpcRole(), candidate)) <= bestScore) continue;
            bestScore = score;
            bestSlot = slot;
        }
        if (bestSlot < 0) {
            return false;
        }
        class_1799 source = inventory.method_5438(bestSlot);
        class_1799 one = source.method_7972();
        one.method_7939(1);
        int inserted = soldier.insertIntoPersonalStorageAndCount(one);
        if (inserted <= 0) {
            return false;
        }
        source.method_7934(inserted);
        return true;
    }

    boolean insertIntoSupplyInventory(MedievalSoldierEntity soldier, class_1263 inventory, class_1799 stack) {
        int slot;
        if (stack == null || stack.method_7960()) {
            return true;
        }
        class_1799 remaining = stack.method_7972();
        for (slot = 0; slot < inventory.method_5439() && !remaining.method_7960(); ++slot) {
            class_1799 current = inventory.method_5438(slot);
            if (current.method_7960() || !class_1799.method_31577((class_1799)current, (class_1799)remaining) || current.method_7947() >= current.method_7914()) continue;
            int moved = Math.min(current.method_7914() - current.method_7947(), remaining.method_7947());
            current.method_7933(moved);
            remaining.method_7934(moved);
        }
        for (slot = 0; slot < inventory.method_5439() && !remaining.method_7960(); ++slot) {
            if (!inventory.method_5438(slot).method_7960()) continue;
            int moved = Math.min(remaining.method_7947(), remaining.method_7914());
            class_1799 inserted = remaining.method_7972();
            inserted.method_7939(moved);
            inventory.method_5447(slot, inserted);
            remaining.method_7934(moved);
        }
        return remaining.method_7960();
    }

    int findBestSupplyArmorSlot(MedievalSoldierEntity soldier, class_1263 inventory, class_1304 equipSlot) {
        int bestSlot = -1;
        class_1799 best = soldier.method_6118(equipSlot);
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            class_1738 armor;
            class_1799 candidate = inventory.method_5438(slot);
            class_1792 class_17922 = candidate.method_7909();
            if (!(class_17922 instanceof class_1738) || (armor = (class_1738)class_17922).method_7685() != equipSlot || !MedievalArmorItem.canNpcEquip(soldier, candidate) || !soldier.isBetterArmorPiece(candidate, best)) continue;
            best = candidate;
            bestSlot = slot;
        }
        return bestSlot;
    }

    boolean hasCarriedCloseCombatSidearm(MedievalSoldierEntity soldier) {
        if (soldier.isCloseCombatWeapon(soldier.method_6047())) {
            return true;
        }
        for (int i = 0; i < Math.min(9, soldier.equipmentPersonalInventory().method_5439()); ++i) {
            if (!soldier.isCloseCombatWeapon(soldier.equipmentPersonalInventory().method_5438(i))) continue;
            return true;
        }
        return false;
    }

    int chooseReserveSidearmSlot(MedievalSoldierEntity soldier, class_1263 inventory) {
        int bestSlot = -1;
        long bestScore = Long.MIN_VALUE;
        int secondSlot = -1;
        long secondScore = Long.MIN_VALUE;
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            class_1799 candidate = inventory.method_5438(slot);
            long score = soldier.closeCombatWeaponScore(candidate);
            if (score == Long.MIN_VALUE) continue;
            if (score > bestScore) {
                secondScore = bestScore;
                secondSlot = bestSlot;
                bestScore = score;
                bestSlot = slot;
                continue;
            }
            if (score <= secondScore) continue;
            secondScore = score;
            secondSlot = slot;
        }
        return secondSlot >= 0 ? secondSlot : bestSlot;
    }

    boolean hasStorageCapacityFor(MedievalSoldierEntity soldier, class_1799 stack) {
        for (int slot = 0; slot < Math.min(9, soldier.equipmentPersonalInventory().method_5439()); ++slot) {
            class_1799 current = soldier.equipmentPersonalInventory().method_5438(slot);
            if (current.method_7960()) {
                return true;
            }
            if (!class_1799.method_31577((class_1799)current, (class_1799)stack) || current.method_7947() >= current.method_7914()) continue;
            return true;
        }
        return false;
    }

    boolean isPersonalStorageFull(MedievalSoldierEntity soldier) {
        for (int slot = 0; slot < Math.min(9, soldier.equipmentPersonalInventory().method_5439()); ++slot) {
            if (!soldier.equipmentPersonalInventory().method_5438(slot).method_7960()) continue;
            return false;
        }
        return true;
    }

    int insertIntoPersonalStorageAndCount(MedievalSoldierEntity soldier, class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return 0;
        }
        class_1799 remaining = stack.method_7972();
        int requested = remaining.method_7947();
        MedievalSoldierEquipmentController.mergeIntoExistingPersonalStacks(soldier, remaining);
        MedievalSoldierEquipmentController.fillEmptyPersonalSlots(soldier, remaining);
        int inserted = requested - remaining.method_7947();
        if (inserted > 0) {
            soldier.equipmentPersonalInventory().method_5431();
        }
        return inserted;
    }

    boolean insertIntoPersonalStorage(MedievalSoldierEntity soldier, class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return true;
        }
        class_1799 remaining = stack.method_7972();
        MedievalSoldierEquipmentController.mergeIntoExistingPersonalStacks(soldier, remaining);
        MedievalSoldierEquipmentController.fillEmptyPersonalSlots(soldier, remaining);
        soldier.equipmentPersonalInventory().method_5431();
        return remaining.method_7960();
    }

    private static void mergeIntoExistingPersonalStacks(MedievalSoldierEntity soldier, class_1799 remaining) {
        for (int slot = 0; slot < Math.min(9, soldier.equipmentPersonalInventory().method_5439()) && !remaining.method_7960(); ++slot) {
            class_1799 current = soldier.equipmentPersonalInventory().method_5438(slot);
            if (current.method_7960() || !class_1799.method_31577((class_1799)current, (class_1799)remaining) || current.method_7947() >= current.method_7914()) continue;
            int moved = Math.min(current.method_7914() - current.method_7947(), remaining.method_7947());
            current.method_7933(moved);
            remaining.method_7934(moved);
        }
    }

    private static void fillEmptyPersonalSlots(MedievalSoldierEntity soldier, class_1799 remaining) {
        for (int slot = 0; slot < Math.min(9, soldier.equipmentPersonalInventory().method_5439()) && !remaining.method_7960(); ++slot) {
            if (!soldier.equipmentPersonalInventory().method_5438(slot).method_7960()) continue;
            int moved = Math.min(remaining.method_7947(), remaining.method_7914());
            class_1799 inserted = remaining.method_7972();
            inserted.method_7939(moved);
            soldier.equipmentPersonalInventory().method_5447(slot, inserted);
            remaining.method_7934(moved);
        }
    }

    int findWorstStoredWeaponSlot(MedievalSoldierEntity soldier) {
        int worstSlot = -1;
        long worstScore = Long.MAX_VALUE;
        for (int slot = 0; slot < Math.min(9, soldier.equipmentPersonalInventory().method_5439()); ++slot) {
            long score;
            class_1799 stack = soldier.equipmentPersonalInventory().method_5438(slot);
            if (!NpcEquipmentProfile.isSupportedWeapon(stack) || !NpcEquipmentProfile.acceptsMainHand(soldier.getNpcRole(), stack) || (score = NpcEquipmentProfile.weaponScore(soldier.getNpcRole(), stack)) >= worstScore) continue;
            worstScore = score;
            worstSlot = slot;
        }
        return worstSlot;
    }

    int findBestStoredArmorReplacementSlot(MedievalSoldierEntity soldier, class_1304 equipmentSlot) {
        int bestSlot = -1;
        long bestScore = Long.MIN_VALUE;
        for (int slot = 0; slot < Math.min(9, soldier.equipmentPersonalInventory().method_5439()); ++slot) {
            long score;
            class_1738 armor;
            class_1799 candidate = soldier.equipmentPersonalInventory().method_5438(slot);
            class_1792 class_17922 = candidate.method_7909();
            if (!(class_17922 instanceof class_1738) || (armor = (class_1738)class_17922).method_7685() != equipmentSlot || (score = soldier.storedArmorScore(candidate)) <= bestScore) continue;
            bestScore = score;
            bestSlot = slot;
        }
        return bestSlot;
    }

    void maintainFieldWeaponReserve(MedievalSoldierEntity soldier) {
        int reserveRemaining;
        if (!MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled) {
            return;
        }
        class_1799 main = soldier.method_6047();
        if (!this.isCriticallyWornArmor(soldier, main)) {
            return;
        }
        int reserveSlot = this.findBestStoredReplacementSlot(soldier, main, true);
        if (reserveSlot < 0) {
            return;
        }
        class_1799 reserve = soldier.equipmentPersonalInventory().method_5438(reserveSlot);
        int mainRemaining = main.method_7936() - main.method_7919();
        int n = reserveRemaining = reserve.method_7963() ? reserve.method_7936() - reserve.method_7919() : Integer.MAX_VALUE;
        if (reserveRemaining <= mainRemaining) {
            return;
        }
        class_1799 old = main.method_7972();
        class_1799 replacement = soldier.equipmentPersonalInventory().method_5441(reserveSlot);
        soldier.method_5673(class_1304.field_6173, replacement);
        soldier.equipmentPersonalInventory().method_5447(reserveSlot, old);
        soldier.equipmentPersonalInventory().method_5431();
        soldier.equipmentMaintenanceState().registryDirty = true;
        soldier.equipmentMaintenanceState().equipmentDirty = false;
        soldier.equipmentMaintenanceState().lastObservedMainHandItem = soldier.method_6047().method_7909();
        soldier.updateWeaponClassDamageModifier();
    }

    void equipBestFieldReplacement(MedievalSoldierEntity soldier, class_1799 brokenWeapon) {
        if (!MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled) {
            soldier.equipmentMaintenanceState().equipmentDirty = false;
            return;
        }
        int slot = this.findBestStoredReplacementSlot(soldier, brokenWeapon, false);
        if (slot < 0) {
            soldier.equipmentMaintenanceState().equipmentDirty = true;
            return;
        }
        class_1799 replacement = soldier.equipmentPersonalInventory().method_5441(slot);
        if (replacement.method_7960()) {
            soldier.equipmentMaintenanceState().equipmentDirty = true;
            return;
        }
        soldier.method_5673(class_1304.field_6173, replacement);
        soldier.equipmentPersonalInventory().method_5431();
        soldier.equipmentMaintenanceState().registryDirty = true;
        soldier.equipmentMaintenanceState().equipmentDirty = false;
        soldier.equipmentMaintenanceState().lastObservedMainHandItem = soldier.method_6047().method_7909();
        soldier.updateWeaponClassDamageModifier();
    }

    void maintainFieldArmorReserve(MedievalSoldierEntity soldier) {
        if (!MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled) {
            return;
        }
        for (class_1304 equipmentSlot : new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166}) {
            class_1799 reserve;
            int reserveSlot;
            class_1799 current = soldier.method_6118(equipmentSlot);
            if (!soldier.isCriticallyWornArmor(current) || (reserveSlot = soldier.findBestStoredArmorReplacementSlot(equipmentSlot)) < 0 || soldier.storedArmorScore(reserve = soldier.equipmentPersonalInventory().method_5438(reserveSlot)) <= soldier.storedArmorScore(current)) continue;
            class_1799 worn = current.method_7972();
            class_1799 replacement = soldier.equipmentPersonalInventory().method_5441(reserveSlot);
            soldier.method_5673(equipmentSlot, replacement);
            soldier.equipmentPersonalInventory().method_5447(reserveSlot, worn);
            soldier.equipmentPersonalInventory().method_5431();
            soldier.equipmentMaintenanceState().registryDirty = true;
            soldier.equipmentMaintenanceState().equipmentDirty = true;
        }
    }

    void equipStoredArmorAfterBreak(MedievalSoldierEntity soldier, class_1304 equipmentSlot) {
        if (!MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled) {
            return;
        }
        int reserveSlot = soldier.findBestStoredArmorReplacementSlot(equipmentSlot);
        if (reserveSlot < 0) {
            soldier.equipmentMaintenanceState().equipmentDirty = true;
            return;
        }
        class_1799 replacement = soldier.equipmentPersonalInventory().method_5441(reserveSlot);
        if (replacement.method_7960()) {
            return;
        }
        soldier.method_5673(equipmentSlot, replacement);
        soldier.equipmentPersonalInventory().method_5431();
        soldier.equipmentMaintenanceState().registryDirty = true;
        soldier.equipmentMaintenanceState().equipmentDirty = true;
    }

    void damageMainHandWeapon(MedievalSoldierEntity soldier, int amount) {
        if (amount <= 0) {
            return;
        }
        class_1799 weapon = soldier.method_6047();
        if (weapon.method_7960() || !weapon.method_7963()) {
            return;
        }
        class_1799 before = weapon.method_7972();
        weapon.method_7956(amount, (class_1309)soldier, entity -> entity.method_20235(class_1304.field_6173));
        soldier.onEquipmentInventoryChanged();
        if (soldier.method_6047().method_7960()) {
            if (MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled) {
                this.equipBestFieldReplacement(soldier, before);
                if (soldier.method_6047().method_7960() && NpcEquipmentProfile.isSupportedWeapon(before)) {
                    soldier.equipmentMaintenanceState().automaticWeaponReplacementNeeded = true;
                }
            } else {
                soldier.equipmentMaintenanceState().automaticWeaponReplacementNeeded = false;
            }
        } else {
            this.maintainFieldWeaponReserve(soldier);
        }
    }

    void onPersonalInventoryChanged(MedievalSoldierEntity soldier) {
        if (!this.compactingPersonalStorage) {
            this.compactingPersonalStorage = true;
            try {
                MedievalSoldierEquipmentController.mergeDuplicatePersonalStacks(soldier);
            }
            finally {
                this.compactingPersonalStorage = false;
            }
        }
        if (!soldier.equipmentMaintenanceState().applyingEquipmentEvaluation) {
            soldier.equipmentMaintenanceState().equipmentDirty = true;
        }
        soldier.equipmentMaintenanceState().inventoryCacheDirty = true;
        soldier.equipmentMaintenanceState().registryDirty = true;
    }

    private static void mergeDuplicatePersonalStacks(MedievalSoldierEntity soldier) {
        int size = Math.min(9, soldier.equipmentPersonalInventory().method_5439());
        for (int sourceSlot = 1; sourceSlot < size; ++sourceSlot) {
            class_1799 source = soldier.equipmentPersonalInventory().method_5438(sourceSlot);
            if (source.method_7960()) continue;
            for (int targetSlot = 0; targetSlot < sourceSlot && !source.method_7960(); ++targetSlot) {
                class_1799 target = soldier.equipmentPersonalInventory().method_5438(targetSlot);
                if (target.method_7960() || !class_1799.method_31577((class_1799)target, (class_1799)source) || target.method_7947() >= target.method_7914()) continue;
                int moved = Math.min(target.method_7914() - target.method_7947(), source.method_7947());
                target.method_7933(moved);
                source.method_7934(moved);
            }
            if (!source.method_7960()) continue;
            soldier.equipmentPersonalInventory().method_5447(sourceSlot, class_1799.field_8037);
        }
    }

    int findBestStoredReplacementSlot(MedievalSoldierEntity soldier, class_1799 reference, boolean requireSameFamily) {
        int bestSlot = -1;
        long bestScore = Long.MIN_VALUE;
        for (int slot = 0; slot < Math.min(9, soldier.formationPersonalInventory().method_5439()); ++slot) {
            long familyBonus;
            long score;
            class_1799 candidate = soldier.formationPersonalInventory().method_5438(slot);
            if (candidate.method_7960() || !NpcEquipmentProfile.isSupportedWeapon(candidate) || requireSameFamily && !soldier.sameWeaponFamily(reference, candidate) || (score = (familyBonus = soldier.sameWeaponFamily(reference, candidate) ? 10000000000L : 0L) + NpcEquipmentProfile.weaponScore(soldier.getNpcRole(), candidate)) <= bestScore) continue;
            bestScore = score;
            bestSlot = slot;
        }
        return bestSlot;
    }

    boolean pickupWeapon(MedievalSoldierEntity soldier, class_1542 item) {
        class_1799 ground = item.method_6983();
        if (ground.method_7960()) {
            return false;
        }
        class_1799 candidate = ground.method_7972();
        candidate.method_7939(1);
        if (!MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled) {
            if (!soldier.insertIntoPersonalStorage(candidate.method_7972())) {
                return false;
            }
            ground.method_7934(1);
            soldier.method_6103((class_1297)item, 1);
            if (ground.method_7960()) {
                item.method_31472();
            } else {
                item.method_6979(ground);
            }
            return true;
        }
        class_1799 current = soldier.method_6047().method_7972();
        boolean handUpgrade = soldier.isBetterWeapon(candidate, current);
        if (handUpgrade) {
            boolean oldHandStored = current.method_7960();
            if (!current.method_7960()) {
                int worstStored;
                oldHandStored = soldier.insertIntoPersonalStorage(current.method_7972());
                if (!oldHandStored && soldier.isPersonalStorageFull() && (worstStored = soldier.findWorstStoredWeaponSlot()) >= 0) {
                    long oldHandScore;
                    class_1799 worst = soldier.equipmentPersonalInventory().method_5438(worstStored);
                    long worstScore = NpcEquipmentProfile.weaponScore(soldier.getNpcRole(), worst);
                    if (worstScore < (oldHandScore = NpcEquipmentProfile.weaponScore(soldier.getNpcRole(), current))) {
                        class_1799 evicted = soldier.equipmentPersonalInventory().method_5441(worstStored);
                        if (!evicted.method_7960()) {
                            soldier.method_5775(evicted);
                        }
                        oldHandStored = soldier.insertIntoPersonalStorage(current.method_7972());
                    }
                }
                if (!oldHandStored) {
                    soldier.method_5775(current.method_7972());
                }
            }
            soldier.method_5673(class_1304.field_6173, candidate);
            soldier.onEquipmentInventoryChanged();
            soldier.updateWeaponClassDamageModifier();
            ground.method_7934(1);
            soldier.method_6103((class_1297)item, 1);
            if (ground.method_7960()) {
                item.method_31472();
            } else {
                item.method_6979(ground);
            }
            return true;
        }
        if (soldier.insertIntoPersonalStorage(candidate.method_7972())) {
            ground.method_7934(1);
            soldier.method_6103((class_1297)item, 1);
            if (ground.method_7960()) {
                item.method_31472();
            } else {
                item.method_6979(ground);
            }
            return true;
        }
        int worstStored = soldier.findWorstStoredWeaponSlot();
        if (worstStored >= 0 && NpcEquipmentProfile.weaponScore(soldier.getNpcRole(), candidate) > NpcEquipmentProfile.weaponScore(soldier.getNpcRole(), soldier.equipmentPersonalInventory().method_5438(worstStored))) {
            class_1799 evicted = soldier.equipmentPersonalInventory().method_5441(worstStored);
            if (!evicted.method_7960()) {
                soldier.method_5775(evicted);
            }
            if (soldier.insertIntoPersonalStorage(candidate.method_7972())) {
                ground.method_7934(1);
                soldier.method_6103((class_1297)item, 1);
                if (ground.method_7960()) {
                    item.method_31472();
                } else {
                    item.method_6979(ground);
                }
                return true;
            }
        }
        return false;
    }

    void tickOpportunisticNearbyPickup(MedievalSoldierEntity soldier) {
        if (!soldier.isStaggeredAiTick(3) || soldier.isInventoryGuiOpen()) {
            return;
        }
        class_238 pickupBox = soldier.method_5829().method_1009(1.05, 0.65, 1.05);
        int pickedEntities = 0;
        for (class_1542 item : soldier.method_37908().method_8390(class_1542.class, pickupBox, candidate -> candidate.method_5805() && !candidate.method_6983().method_7960() && soldier.method_5939(candidate.method_6983()))) {
            soldier.method_5949(item);
            if (++pickedEntities < 4) continue;
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void tickEventDrivenMaintenance(MedievalSoldierEntity soldier) {
        MedievalSoldierMaintenanceState maintenance = soldier.equipmentMaintenanceState();
        class_1792 observedMainHand = soldier.method_6047().method_7909();
        if (observedMainHand != maintenance.lastObservedMainHandItem) {
            maintenance.lastObservedMainHandItem = observedMainHand;
            maintenance.equipmentDirty = true;
            maintenance.registryDirty = true;
        }
        if (!maintenance.equipmentDirty || soldier.goalEatingState().sourceSlot >= 0) {
            return;
        }
        if (!MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled) {
            maintenance.equipmentDirty = false;
            maintenance.lastObservedMainHandItem = soldier.method_6047().method_7909();
            soldier.updateWeaponClassDamageModifier();
            return;
        }
        if (soldier.combatStateForController().rangedCloseCombatWeaponActive) {
            maintenance.equipmentDirty = false;
            maintenance.lastObservedMainHandItem = soldier.method_6047().method_7909();
            return;
        }
        maintenance.equipmentDirty = false;
        maintenance.applyingEquipmentEvaluation = true;
        try {
            soldier.equipBestWeaponFromInventory();
            soldier.updateWeaponClassDamageModifier();
        }
        finally {
            maintenance.applyingEquipmentEvaluation = false;
            maintenance.lastObservedMainHandItem = soldier.method_6047().method_7909();
        }
    }
}

