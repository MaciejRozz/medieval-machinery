/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1496
 *  net.minecraft.class_1690
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.core.NpcTargetPriority;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1496;
import net.minecraft.class_1690;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

final class MedievalSoldierTargetPriorityPolicy {
    private MedievalSoldierTargetPriorityPolicy() {
    }

    static NpcTargetPriority defaultPriorityForRole(NpcRole role) {
        if (role == null) {
            return NpcTargetPriority.CLOSEST;
        }
        return switch (role) {
            case NpcRole.SPEARMAN, NpcRole.MOUNTED_KNIGHT -> NpcTargetPriority.CAVALRY;
            case NpcRole.ARCHER, NpcRole.CROSSBOWMAN, NpcRole.MOUNTED_ARCHER -> NpcTargetPriority.INFANTRY;
            case NpcRole.CANNONEER -> NpcTargetPriority.SIEGE_WEAPONS;
            default -> NpcTargetPriority.CLOSEST;
        };
    }

    static NpcTargetPriority classify(class_1309 target) {
        if (target == null) {
            return NpcTargetPriority.CLOSEST;
        }
        class_1297 vehicle = target.method_5854();
        if (vehicle != null) {
            if (MedievalSoldierTargetPriorityPolicy.isShip(vehicle)) {
                return NpcTargetPriority.SHIPS;
            }
            if (vehicle instanceof class_1496) {
                return NpcTargetPriority.CAVALRY;
            }
        }
        if (MedievalSoldierTargetPriorityPolicy.isShip((class_1297)target)) {
            return NpcTargetPriority.SHIPS;
        }
        if (MedievalSoldierTargetPriorityPolicy.looksLikeSiegeEntity((class_1297)target)) {
            return NpcTargetPriority.SIEGE_WEAPONS;
        }
        return NpcTargetPriority.INFANTRY;
    }

    static double scoreBias(MedievalSoldierEntity soldier, class_1309 target) {
        if (soldier == null || target == null) {
            return 0.0;
        }
        NpcTargetPriority desired = soldier.getTargetPriority();
        if (desired == NpcTargetPriority.CLOSEST) {
            return 0.0;
        }
        return MedievalSoldierTargetPriorityPolicy.classify(target) == desired ? -26.0 : 5.0;
    }

    static boolean materiallyPreferred(MedievalSoldierEntity soldier, class_1309 candidate, class_1309 current) {
        if (soldier == null || candidate == null || current == null) {
            return false;
        }
        NpcTargetPriority desired = soldier.getTargetPriority();
        if (desired == NpcTargetPriority.CLOSEST) {
            return false;
        }
        return MedievalSoldierTargetPriorityPolicy.classify(candidate) == desired && MedievalSoldierTargetPriorityPolicy.classify(current) != desired;
    }

    private static boolean isShip(class_1297 entity) {
        if (entity instanceof class_1690) {
            return true;
        }
        class_2960 id = class_7923.field_41177.method_10221((Object)entity.method_5864());
        return id != null && "smallships".equals(id.method_12836());
    }

    private static boolean looksLikeSiegeEntity(class_1297 entity) {
        class_2960 id = class_7923.field_41177.method_10221((Object)entity.method_5864());
        if (id == null) {
            return false;
        }
        String path = id.method_12832();
        return path.contains("cannon") || path.contains("ballista") || path.contains("catapult") || path.contains("trebuchet") || path.contains("ram");
    }
}

