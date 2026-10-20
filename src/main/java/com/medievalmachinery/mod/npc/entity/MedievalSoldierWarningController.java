/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1304
 *  net.minecraft.class_1799
 *  net.minecraft.class_1937
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.core.NpcEquipmentProfile;
import com.medievalmachinery.mod.npc.core.NpcHungerState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.UUID;
import net.minecraft.class_1263;
import net.minecraft.class_1304;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

final class MedievalSoldierWarningController {
    private static final int CHECK_INTERVAL_TICKS = 20;
    private static final int REPEAT_INTERVAL_TICKS = 12000;
    private boolean noWeaponActive;
    private int noWeaponTicks;
    private boolean noFoodActive;
    private int noFoodTicks;

    MedievalSoldierWarningController() {
    }

    void tickWeaponShortage(MedievalSoldierEntity soldier) {
        boolean missing;
        if (!soldier.isStaggeredAiTick(20) || soldier.method_37908().field_9236) {
            return;
        }
        boolean bl = missing = !MedievalSoldierWarningController.hasClassSuitableFieldWeapon(soldier);
        if (MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled && missing && (soldier.method_6047().method_7960() || NpcEquipmentProfile.weaponPreference(soldier.getNpcRole(), soldier.method_6047()) < 85)) {
            boolean bl2 = missing = !MedievalSoldierWarningController.equipBestCarriedFieldWeapon(soldier);
        }
        if (!missing) {
            this.noWeaponActive = false;
            this.noWeaponTicks = 0;
            return;
        }
        if (!this.noWeaponActive) {
            this.noWeaponActive = true;
            this.noWeaponTicks = 0;
            MedievalSoldierWarningController.notifyMissingWeapon(soldier);
            return;
        }
        this.noWeaponTicks += 20;
        if (this.noWeaponTicks >= 12000) {
            this.noWeaponTicks = 0;
            MedievalSoldierWarningController.notifyMissingWeapon(soldier);
        }
    }

    void tickFoodShortage(MedievalSoldierEntity soldier) {
        boolean missing;
        if (!soldier.isStaggeredAiTick(20) || soldier.method_37908().field_9236) {
            return;
        }
        NpcHungerState state = soldier.getHungerState();
        boolean hungry = state == NpcHungerState.HUNGRY || state == NpcHungerState.VERY_HUNGRY;
        boolean bl = missing = hungry && !soldier.hasCarriedFoodForWarning();
        if (!missing) {
            this.noFoodActive = false;
            this.noFoodTicks = 0;
            return;
        }
        if (!this.noFoodActive) {
            this.noFoodActive = true;
            this.noFoodTicks = 0;
            MedievalSoldierWarningController.notifyMissingFood(soldier);
            return;
        }
        this.noFoodTicks += 20;
        if (this.noFoodTicks >= 12000) {
            this.noFoodTicks = 0;
            MedievalSoldierWarningController.notifyMissingFood(soldier);
        }
    }

    private static boolean hasClassSuitableFieldWeapon(MedievalSoldierEntity soldier) {
        if (NpcEquipmentProfile.weaponPreference(soldier.getNpcRole(), soldier.method_6047()) >= 85) {
            return true;
        }
        class_1263 inventory = soldier.getPersonalInventory();
        for (int slot = 0; slot < Math.min(9, inventory.method_5439()); ++slot) {
            if (NpcEquipmentProfile.weaponPreference(soldier.getNpcRole(), inventory.method_5438(slot)) < 85) continue;
            return true;
        }
        return false;
    }

    private static boolean equipBestCarriedFieldWeapon(MedievalSoldierEntity soldier) {
        class_1263 inventory = soldier.getPersonalInventory();
        int bestSlot = -1;
        long bestScore = Long.MIN_VALUE;
        for (int slot = 0; slot < Math.min(9, inventory.method_5439()); ++slot) {
            long score;
            class_1799 candidate = inventory.method_5438(slot);
            if (NpcEquipmentProfile.weaponPreference(soldier.getNpcRole(), candidate) < 85 || (score = soldier.getWeaponScore(candidate)) <= bestScore) continue;
            bestScore = score;
            bestSlot = slot;
        }
        if (bestSlot < 0) {
            return false;
        }
        class_1799 previous = soldier.method_6047().method_7972();
        class_1799 replacement = inventory.method_5441(bestSlot);
        soldier.method_5673(class_1304.field_6173, replacement);
        if (!previous.method_7960()) {
            inventory.method_5447(bestSlot, previous);
        }
        inventory.method_5431();
        soldier.onEquipmentInventoryChanged();
        return true;
    }

    private static void notifyMissingWeapon(MedievalSoldierEntity soldier) {
        class_3222 owner = MedievalSoldierWarningController.onlineOwner(soldier);
        if (owner == null) {
            return;
        }
        owner.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.npc.no_suitable_weapon", (Object[])new Object[]{soldier.method_5476(), class_2561.method_43471((String)("npc.medieval-machinery.role." + soldier.getNpcRole().serializedName()))}), false);
    }

    private static void notifyMissingFood(MedievalSoldierEntity soldier) {
        class_3222 owner = MedievalSoldierWarningController.onlineOwner(soldier);
        if (owner == null) {
            return;
        }
        owner.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.npc.no_food", (Object[])new Object[]{soldier.method_5476(), class_2561.method_43471((String)("npc.medieval-machinery.hunger." + soldier.getHungerState().serializedName()))}), false);
    }

    private static class_3222 onlineOwner(MedievalSoldierEntity soldier) {
        class_1937 class_19372 = soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return null;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        UUID ownerId = soldier.getNpcIdentity().getOwnerId();
        return ownerId == null ? null : serverWorld.method_8503().method_3760().method_14602(ownerId);
    }
}

