/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import net.minecraft.class_1937;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class NpcOrganizationService {
    private NpcOrganizationService() {
    }

    public static void assignToPlayerDefaultContext(MedievalSoldierEntity npc, class_3222 player) {
        class_1937 class_19372 = player.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 world = (class_3218)class_19372;
        KingdomData kingdom = KingdomManager.get(world).findByMember(player.method_5667());
        npc.getNpcIdentity().setOwnerId(player.method_5667());
        npc.getNpcIdentity().setKingdomId(kingdom == null ? null : kingdom.getId());
        npc.getNpcIdentity().setCastleId(null);
    }
}

