/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1496
 *  net.minecraft.class_1657
 *  net.minecraft.class_1688
 *  net.minecraft.class_1690
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1496;
import net.minecraft.class_1657;
import net.minecraft.class_1688;
import net.minecraft.class_1690;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

final class MedievalSoldierRideablePolicy {
    private static final String SMALL_SHIPS_NAMESPACE = "smallships";

    private MedievalSoldierRideablePolicy() {
    }

    static boolean isSupportedCandidate(MedievalSoldierEntity soldier, class_1297 candidate) {
        if (soldier == null || candidate == null || !candidate.method_5805()) {
            return false;
        }
        if (candidate == soldier || candidate instanceof MedievalSoldierEntity || candidate instanceof class_1657) {
            return false;
        }
        boolean assignedHorse = MedievalSoldierRideablePolicy.isAssignedServiceMount(soldier, candidate);
        if (assignedHorse) {
            return !candidate.method_5782();
        }
        if (candidate.method_5752().contains("medieval_machinery_mount") && MedievalSoldierRideablePolicy.isAssignedToOtherSoldier(soldier, candidate)) {
            return false;
        }
        if (candidate instanceof class_1496) {
            return !candidate.method_5782();
        }
        if (candidate instanceof class_1688) {
            return !candidate.method_5782();
        }
        if (candidate instanceof class_1690) {
            return true;
        }
        return MedievalSoldierRideablePolicy.isSmallShipsEntity(candidate);
    }

    static boolean isSmallShipsEntity(class_1297 entity) {
        if (entity == null) {
            return false;
        }
        class_2960 id = class_7923.field_41177.method_10221((Object)entity.method_5864());
        return id != null && SMALL_SHIPS_NAMESPACE.equals(id.method_12836());
    }

    static boolean isAssignedServiceMount(MedievalSoldierEntity soldier, class_1297 entity) {
        return entity != null && soldier != null && entity.method_5752().contains("medieval_machinery_mount_" + String.valueOf(soldier.method_5667()));
    }

    static boolean isAssignedToOtherSoldier(MedievalSoldierEntity soldier, class_1297 entity) {
        String ownTag = "medieval_machinery_mount_" + String.valueOf(soldier.method_5667());
        return entity.method_5752().stream().anyMatch(tag -> tag.startsWith("medieval_machinery_mount_") && !tag.equals(ownTag));
    }

    static double selectionScore(MedievalSoldierEntity soldier, class_1297 candidate) {
        double score = soldier.method_5858(candidate);
        if (MedievalSoldierRideablePolicy.isAssignedServiceMount(soldier, candidate)) {
            score -= 1000.0;
        }
        if (MedievalSoldierRideablePolicy.isSmallShipsEntity(candidate)) {
            score += 2.0;
        }
        score += (double)candidate.method_5685().size() * 3.0;
        return score += soldier.mountReservationPenalty(candidate.method_5667());
    }
}

