/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryData;
import java.util.UUID;
import net.minecraft.class_1657;
import net.minecraft.class_3222;

public final class SoldierControlPolicy {
    private SoldierControlPolicy() {
    }

    public static boolean canPersonallyControl(class_1657 player, MedievalSoldierEntity soldier) {
        if (player == null || soldier == null || !soldier.method_5805()) {
            return false;
        }
        UUID ownerId = soldier.getNpcIdentity().getOwnerId();
        return ownerId != null && ownerId.equals(player.method_5667());
    }

    public static boolean ownsRegistryRecord(class_3222 player, SoldierRegistryData record) {
        return player != null && record != null && player.method_5667().equals(record.getOwnerId());
    }
}

