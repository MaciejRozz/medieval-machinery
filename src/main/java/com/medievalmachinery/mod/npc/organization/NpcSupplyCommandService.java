/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.core.NpcSupplyRequest;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.class_1263;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_3218;

public final class NpcSupplyCommandService {
    private NpcSupplyCommandService() {
    }

    public static boolean requestSupply(MedievalSoldierEntity soldier) {
        class_1937 class_19372;
        if (soldier == null || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return false;
        }
        class_3218 world = (class_3218)class_19372;
        UUID castleId = soldier.getNpcIdentity().getCastleId();
        if (castleId == null) {
            return false;
        }
        GarrisonData garrison = GarrisonManager.get(world).get(castleId);
        if (garrison == null || garrison.getSupplyChestPositions().isEmpty()) {
            return false;
        }
        ArrayList<class_2338> usable = new ArrayList<class_2338>();
        for (class_2338 chest : garrison.getSupplyChestPositions()) {
            if (!(world.method_8321(chest) instanceof class_1263)) continue;
            usable.add(chest);
        }
        if (usable.isEmpty()) {
            return false;
        }
        soldier.forgetCurrentTarget();
        soldier.requestSupplyRun(usable, NpcSupplyRequest.SUPPLY);
        return true;
    }
}

