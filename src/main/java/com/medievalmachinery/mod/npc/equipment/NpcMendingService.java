/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1799
 *  net.minecraft.class_1887
 *  net.minecraft.class_1890
 *  net.minecraft.class_1893
 */
package com.medievalmachinery.mod.npc.equipment;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_1893;

public final class NpcMendingService {
    private NpcMendingService() {
    }

    public static int repairEquippedItems(class_1309 entity, int experience) {
        class_1799 stack;
        List<class_1799> candidates;
        int remaining;
        int repair;
        if (entity == null || experience <= 0) {
            return Math.max(0, experience);
        }
        for (remaining = experience; remaining > 0 && !(candidates = NpcMendingService.damagedMendingEquipment(entity)).isEmpty() && (repair = Math.min((stack = candidates.get(entity.method_6051().method_43048(candidates.size()))).method_7919(), remaining * 2)) > 0; remaining -= (repair + 1) / 2) {
            stack.method_7974(stack.method_7919() - repair);
        }
        return remaining;
    }

    private static List<class_1799> damagedMendingEquipment(class_1309 entity) {
        ArrayList<class_1799> result = new ArrayList<class_1799>(6);
        for (class_1304 slot : class_1304.values()) {
            class_1799 stack = entity.method_6118(slot);
            if (stack.method_7960() || !stack.method_7986() || class_1890.method_8225((class_1887)class_1893.field_9101, (class_1799)stack) <= 0) continue;
            result.add(stack);
        }
        return result;
    }
}

