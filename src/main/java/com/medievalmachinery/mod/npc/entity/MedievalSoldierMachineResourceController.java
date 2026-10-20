/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1277
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1277;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1935;

final class MedievalSoldierMachineResourceController {
    private static final int REAL_STORAGE_SLOTS = 9;
    private static final int SELECTOR_SLOT = 9;

    MedievalSoldierMachineResourceController() {
    }

    class_1799 selectedAmmunition(MedievalSoldierEntity soldier) {
        return soldier.supplyPersonalInventory().method_5438(9);
    }

    class_1799 preferredAmmunition(MedievalSoldierEntity soldier) {
        class_1799 selected = this.selectedAmmunition(soldier);
        if (soldier.getNpcRole() == NpcRole.CANNONEER && selected.method_7960()) {
            return new class_1799((class_1935)ModItems.STONE_CANNONBALL);
        }
        return selected.method_7972();
    }

    int count(MedievalSoldierEntity soldier, class_1792 item) {
        if (item == null) {
            return 0;
        }
        int found = 0;
        class_1277 inventory = soldier.supplyPersonalInventory();
        for (int slot = 0; slot < Math.min(9, inventory.method_5439()); ++slot) {
            class_1799 stack = inventory.method_5438(slot);
            if (stack.method_7960() || !stack.method_31574(item)) continue;
            found += stack.method_7947();
        }
        return found;
    }

    int store(MedievalSoldierEntity soldier, class_1792 item, int count) {
        int slot;
        if (item == null || count <= 0) {
            return 0;
        }
        class_1277 inventory = soldier.supplyPersonalInventory();
        int remaining = count;
        for (slot = 0; slot < Math.min(9, inventory.method_5439()) && remaining > 0; ++slot) {
            int add;
            class_1799 stack = inventory.method_5438(slot);
            if (stack.method_7960() || !stack.method_31574(item) || (add = Math.min(Math.max(0, stack.method_7914() - stack.method_7947()), remaining)) <= 0) continue;
            stack.method_7933(add);
            remaining -= add;
        }
        for (slot = 0; slot < Math.min(9, inventory.method_5439()) && remaining > 0; ++slot) {
            if (!inventory.method_5438(slot).method_7960()) continue;
            class_1799 created = new class_1799((class_1935)item, Math.min(item.method_7882(), remaining));
            inventory.method_5447(slot, created);
            remaining -= created.method_7947();
        }
        if (remaining != count) {
            inventory.method_5431();
            soldier.onPersonalInventoryChanged();
        }
        return count - remaining;
    }

    boolean consume(MedievalSoldierEntity soldier, class_1792 item, int count) {
        if (item == null || count <= 0) {
            return true;
        }
        if (this.count(soldier, item) < count) {
            return false;
        }
        class_1277 inventory = soldier.supplyPersonalInventory();
        int remaining = count;
        for (int slot = 0; slot < Math.min(9, inventory.method_5439()) && remaining > 0; ++slot) {
            class_1799 stack = inventory.method_5438(slot);
            if (stack.method_7960() || !stack.method_31574(item)) continue;
            int take = Math.min(remaining, stack.method_7947());
            stack.method_7934(take);
            remaining -= take;
            if (!stack.method_7960()) continue;
            inventory.method_5447(slot, class_1799.field_8037);
        }
        inventory.method_5431();
        soldier.onPersonalInventoryChanged();
        return remaining == 0;
    }

    boolean matchesSelected(MedievalSoldierEntity soldier, class_1799 ammunition) {
        class_1799 selected = this.selectedAmmunition(soldier);
        return !selected.method_7960() && ammunition != null && !ammunition.method_7960() && class_1799.method_31577((class_1799)selected, (class_1799)ammunition);
    }
}

