/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.core.NpcSupplyRequest;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CastleAreaService;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_3218;

final class MedievalSoldierAutonomousSupplyPolicy {
    private static final int CHECK_INTERVAL_TICKS = 40;

    MedievalSoldierAutonomousSupplyPolicy() {
    }

    void tick(MedievalSoldierEntity soldier) {
        class_3218 world;
        block12: {
            block11: {
                class_1937 class_19372 = soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block11;
                world = (class_3218)class_19372;
                if (!soldier.hasSupplyMovementOverride() && !soldier.method_6115() && soldier.isStaggeredAiTick(40) && (soldier.method_5968() == null || !soldier.method_5968().method_5805())) break block12;
            }
            return;
        }
        UUID castleId = soldier.getNpcIdentity().getCastleId();
        if (castleId == null) {
            return;
        }
        GarrisonData garrison = GarrisonManager.get(world).get(castleId);
        if (garrison == null || garrison.getSupplyChestPositions().isEmpty()) {
            return;
        }
        if (garrison.getCenterDimension() != null && !garrison.getCenterDimension().equals(world.method_27983().method_29177().toString())) {
            return;
        }
        int fallbackRadius = MedievalMachineryConfig.get().taxVillagerRadius;
        if (!CastleAreaService.contains(garrison, soldier.method_24515(), fallbackRadius)) {
            return;
        }
        NpcSupplyRequest request = NpcSupplyRequest.NONE;
        if (MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled && soldier.equipmentMaintenanceState().automaticWeaponReplacementNeeded && soldier.method_6047().method_7960()) {
            request = NpcSupplyRequest.AUTO_WEAPON;
        } else if (!soldier.hasCarriedFoodForWarning()) {
            request = NpcSupplyRequest.AUTO_FOOD;
        }
        if (request == NpcSupplyRequest.NONE) {
            return;
        }
        ArrayList<class_2338> chests = new ArrayList<class_2338>(garrison.getSupplyChestPositions());
        soldier.requestSupplyRun(chests, request);
    }
}

