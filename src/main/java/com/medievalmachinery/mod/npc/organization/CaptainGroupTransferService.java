/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.SoldierControlPolicy;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import com.medievalmachinery.mod.npc.organization.SoldierTransferService;
import java.util.LinkedHashSet;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import org.jetbrains.annotations.Nullable;

public final class CaptainGroupTransferService {
    private CaptainGroupTransferService() {
    }

    @Nullable
    public static Preview preview(class_3218 world, MedievalSoldierEntity captain, class_3222 currentOwner) {
        if (world == null || captain == null || currentOwner == null) {
            return null;
        }
        if (!captain.method_5805() || captain.getNpcRank() != NpcRank.CAPTAIN) {
            return null;
        }
        if (!SoldierControlPolicy.canPersonallyControl((class_1657)currentOwner, captain)) {
            return null;
        }
        UUID groupId = captain.getNpcIdentity().getSquadId();
        if (groupId == null) {
            return null;
        }
        CommandGroupData group = CommandGroupManager.get(world).get(groupId);
        if (group == null || !group.getOwnerId().equals(currentOwner.method_5667()) || !group.getSoldierIds().contains(captain.method_5667())) {
            return null;
        }
        return new Preview(group.getId(), group.getName(), group.getSoldierIds().size());
    }

    public static boolean transfer(class_3218 world, MedievalSoldierEntity captain, class_3222 currentOwner, class_3222 newOwner) {
        Preview preview = CaptainGroupTransferService.preview(world, captain, currentOwner);
        if (preview == null || newOwner == null || currentOwner.method_5667().equals(newOwner.method_5667())) {
            return false;
        }
        CommandGroupManager groups = CommandGroupManager.get(world);
        CommandGroupData group = groups.get(preview.groupId());
        if (group == null) {
            return false;
        }
        LinkedHashSet<UUID> members = new LinkedHashSet<UUID>(group.getSoldierIds());
        UUID targetGroupId = groups.transferCaptainGroupDetached(group.getId(), currentOwner.method_5667(), newOwner.method_5667(), captain.method_5667());
        if (targetGroupId == null) {
            return false;
        }
        SoldierRegistryManager registry = SoldierRegistryManager.get(world);
        for (UUID soldierId : members) {
            registry.updateMembership(soldierId, newOwner.method_5667(), null, targetGroupId);
        }
        for (class_3218 serverWorld : world.method_8503().method_3738()) {
            for (UUID soldierId : members) {
                MedievalSoldierEntity soldier;
                class_1297 entity = serverWorld.method_14190(soldierId);
                if (!(entity instanceof MedievalSoldierEntity) || !(soldier = (MedievalSoldierEntity)entity).method_5805()) continue;
                groups.reconcileMembership(soldier);
                soldier.setHomePosition(null);
                soldier.applyCastleDisplayName("-");
                SoldierTransferService.releaseLoadedMachineClaim(serverWorld, soldier, soldierId);
                soldier.markOrganizationDirty();
                registry.update(soldier);
            }
        }
        return true;
    }

    public record Preview(UUID groupId, String groupName, int memberCount) {
    }
}

