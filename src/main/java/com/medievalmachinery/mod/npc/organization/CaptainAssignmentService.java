/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryData;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class CaptainAssignmentService {
    private CaptainAssignmentService() {
    }

    public static Result assign(class_3218 world, UUID actorId, UUID groupId, UUID captainId) {
        if (world == null || actorId == null || groupId == null || captainId == null) {
            return Result.INVALID;
        }
        CommandGroupManager groups = CommandGroupManager.get(world);
        CommandGroupData group = groups.get(groupId);
        if (group == null || !actorId.equals(group.getOwnerId())) {
            return Result.NO_PERMISSION;
        }
        if (!group.getSoldierIds().contains(captainId)) {
            return Result.NOT_MEMBER;
        }
        SoldierRegistryData record = SoldierRegistryManager.get(world).get(captainId);
        if (!(record != null && actorId.equals(record.getOwnerId()) && Objects.equals(group.getCastleId(), record.getCastleId()) && "captain".equals(record.getRank()))) {
            return Result.NOT_ELIGIBLE;
        }
        if (!groups.setCaptain(groupId, actorId, captainId)) {
            return Result.INVALID;
        }
        CaptainAssignmentService.synchronizeLoadedMembers(world, group);
        return Result.ASSIGNED;
    }

    public static Result clear(class_3218 world, UUID actorId, UUID groupId) {
        if (world == null || actorId == null || groupId == null) {
            return Result.INVALID;
        }
        CommandGroupManager groups = CommandGroupManager.get(world);
        CommandGroupData group = groups.get(groupId);
        if (group == null || !actorId.equals(group.getOwnerId())) {
            return Result.NO_PERMISSION;
        }
        if (!groups.setCaptain(groupId, actorId, null)) {
            return Result.INVALID;
        }
        CaptainAssignmentService.synchronizeLoadedMembers(world, group);
        return Result.CLEARED;
    }

    public static void assignPromotedCaptainIfVacant(class_3218 world, MedievalSoldierEntity captain) {
        if (world == null || captain == null || captain.getNpcRank() != NpcRank.CAPTAIN) {
            return;
        }
        UUID groupId = captain.getNpcIdentity().getSquadId();
        UUID ownerId = captain.getNpcIdentity().getOwnerId();
        if (groupId == null || ownerId == null) {
            return;
        }
        CommandGroupData group = CommandGroupManager.get(world).get(groupId);
        if (group == null || group.getCaptainId() != null || !group.getSoldierIds().contains(captain.method_5667())) {
            return;
        }
        if (CommandGroupManager.get(world).setCaptain(group.getId(), ownerId, captain.method_5667())) {
            CaptainAssignmentService.synchronizeLoadedMembers(world, group);
        }
    }

    public static void clearOnCaptainDeath(class_3218 world, MedievalSoldierEntity captain) {
        if (world == null || captain == null || captain.getNpcRank() != NpcRank.CAPTAIN) {
            return;
        }
        UUID groupId = captain.getNpcIdentity().getSquadId();
        UUID ownerId = captain.getNpcIdentity().getOwnerId();
        if (groupId == null || ownerId == null) {
            return;
        }
        CommandGroupData group = CommandGroupManager.get(world).get(groupId);
        if (group == null || !Objects.equals(group.getCaptainId(), captain.method_5667())) {
            return;
        }
        if (CommandGroupManager.get(world).setCaptain(group.getId(), ownerId, null)) {
            CaptainAssignmentService.synchronizeLoadedMembers(world, group);
        }
    }

    public static void synchronizeLoadedMembers(class_3218 world, CommandGroupData group) {
        if (world == null || group == null) {
            return;
        }
        @Nullable UUID captainId = group.getCaptainId();
        for (UUID memberId : group.getSoldierIds()) {
            class_1297 entity = world.method_14190(memberId);
            if (!(entity instanceof MedievalSoldierEntity)) continue;
            MedievalSoldierEntity member = (MedievalSoldierEntity)entity;
            member.getNpcIdentity().setCaptainId(Objects.equals(memberId, captainId) ? null : captainId);
            member.markOrganizationDirty();
        }
    }

    public static enum Result {
        ASSIGNED,
        CLEARED,
        NO_PERMISSION,
        NOT_MEMBER,
        NOT_ELIGIBLE,
        INVALID;

    }
}

