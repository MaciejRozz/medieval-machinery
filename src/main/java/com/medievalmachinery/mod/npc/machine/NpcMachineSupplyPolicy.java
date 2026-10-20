/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_3218;

public final class NpcMachineSupplyPolicy {
    private NpcMachineSupplyPolicy() {
    }

    public static boolean setPreferredSupplyChest(MedievalSoldierEntity soldier, class_2338 chestPos) {
        class_3218 world;
        block6: {
            block5: {
                class_1937 class_19372 = soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block5;
                world = (class_3218)class_19372;
                if (chestPos != null) break block6;
            }
            return false;
        }
        UUID castleId = soldier.getNpcIdentity().getCastleId();
        if (castleId == null) {
            return false;
        }
        GarrisonData garrison = GarrisonManager.get(world).get(castleId);
        if (garrison == null || !garrison.getSupplyChestPositions().contains(chestPos)) {
            return false;
        }
        soldier.medievalMachinery$setPreferredMachineSupplyChest(chestPos);
        return true;
    }

    public static void clearPreferredSupplyChest(MedievalSoldierEntity soldier) {
        soldier.medievalMachinery$setPreferredMachineSupplyChest(null);
    }

    public static List<class_2338> orderedSupplyChests(MedievalSoldierEntity soldier) {
        class_1937 class_19372 = soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return List.of();
        }
        class_3218 world = (class_3218)class_19372;
        UUID castleId = soldier.getNpcIdentity().getCastleId();
        if (castleId == null) {
            return List.of();
        }
        GarrisonData garrison = GarrisonManager.get(world).get(castleId);
        if (garrison == null || garrison.getSupplyChestPositions().isEmpty()) {
            return List.of();
        }
        ArrayList<class_2338> result = new ArrayList<class_2338>(garrison.getSupplyChestPositions());
        class_2338 preferred = soldier.medievalMachinery$getPreferredMachineSupplyChest();
        if (preferred != null && !result.contains(preferred)) {
            NpcMachineSupplyPolicy.clearPreferredSupplyChest(soldier);
            preferred = null;
        }
        class_2338 preferredFinal = preferred;
        result.sort(Comparator.comparingInt(pos -> preferredFinal != null && preferredFinal.equals(pos) ? 0 : 1).thenComparingDouble(pos -> soldier.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5)));
        return List.copyOf(result);
    }
}

