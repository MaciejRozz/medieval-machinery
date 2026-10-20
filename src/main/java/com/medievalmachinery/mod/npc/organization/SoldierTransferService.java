/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineAssignmentService;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.SoldierControlPolicy;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class SoldierTransferService {
    private SoldierTransferService() {
    }

    public static boolean transfer(class_3218 world, MedievalSoldierEntity soldier, class_3222 currentOwner, class_3222 newOwner) {
        if (world == null || soldier == null || currentOwner == null || newOwner == null) {
            return false;
        }
        if (!soldier.method_5805() || currentOwner.method_5667().equals(newOwner.method_5667())) {
            return false;
        }
        if (!SoldierControlPolicy.canPersonallyControl((class_1657)currentOwner, soldier)) {
            return false;
        }
        UUID soldierId = soldier.method_5667();
        CommandGroupManager.get(world).removeSoldierEverywhere(soldierId);
        soldier.getNpcIdentity().setSquadId(null);
        soldier.getNpcIdentity().setCaptainId(null);
        soldier.getNpcIdentity().setCastleId(null);
        soldier.setFormationSlot(-1);
        soldier.setHomePosition(null);
        soldier.applyCastleDisplayName("-");
        SoldierTransferService.releaseLoadedMachineClaim(world, soldier, soldierId);
        NpcMachineAssignmentService.clear(soldier);
        soldier.getNpcIdentity().setOwnerId(newOwner.method_5667());
        KingdomData newOwnerKingdom = KingdomManager.get(world).findByMember(newOwner.method_5667());
        soldier.getNpcIdentity().setKingdomId(newOwnerKingdom == null ? null : newOwnerKingdom.getId());
        soldier.markOrganizationDirty();
        SoldierRegistryManager.get(world).update(soldier);
        return true;
    }

    public static void releaseLoadedMachineClaim(class_3218 world, MedievalSoldierEntity soldier, UUID soldierId) {
        for (class_1297 entity : world.method_8335(null, soldier.method_5829().method_1014(64.0))) {
            MedievalMachineryOperable machine;
            if (!(entity instanceof MedievalMachineryOperable) || !(machine = (MedievalMachineryOperable)entity).medievalMachinery$isOperatedBy(soldierId)) continue;
            machine.medievalMachinery$release(soldierId);
        }
    }
}

