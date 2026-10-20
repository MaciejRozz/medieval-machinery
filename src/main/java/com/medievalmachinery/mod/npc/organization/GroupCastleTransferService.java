/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_2338
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import com.medievalmachinery.mod.npc.transfer.CastleTransferController;
import com.medievalmachinery.mod.npc.transfer.CastleTransferData;
import com.medievalmachinery.mod.npc.transfer.CastleTransferState;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class GroupCastleTransferService {
    private GroupCastleTransferService() {
    }

    public static StartResult request(class_3218 world, CommandGroupData group, UUID actorId, UUID targetCastleId) {
        if (world == null || group == null || actorId == null || targetCastleId == null) {
            return StartResult.INVALID;
        }
        if (!actorId.equals(group.getOwnerId()) || group.getCastleId() == null || group.getCastleId().equals(targetCastleId)) {
            return StartResult.INVALID;
        }
        if (group.isCastleTransferActive()) {
            return StartResult.ALREADY_ACTIVE;
        }
        GarrisonManager garrisons = GarrisonManager.get(world);
        GarrisonData source = garrisons.get(group.getCastleId());
        GarrisonData target = garrisons.get(targetCastleId);
        if (source == null || target == null || !GroupCastleTransferService.sameWorld(world, source, target)) {
            return StartResult.INVALID;
        }
        if (!Objects.equals(source.getKingdomId(), target.getKingdomId())) {
            return StartResult.INVALID;
        }
        if (source.getKingdomId() == null && !source.getOwnerId().equals(target.getOwnerId())) {
            return StartResult.INVALID;
        }
        List<MedievalSoldierEntity> members = GroupCastleTransferService.loadedMembers(world, group);
        if (members.size() != group.getSoldierIds().size()) {
            return StartResult.MEMBERS_NOT_LOADED;
        }
        for (MedievalSoldierEntity soldier : members) {
            if (soldier.method_5805() && group.getId().equals(soldier.getNpcIdentity().getSquadId()) && group.getCastleId().equals(soldier.getNpcIdentity().getCastleId()) && !soldier.medievalMachinery$getCastleTransferData().isActive()) continue;
            return StartResult.INVALID;
        }
        CommandGroupManager groups = CommandGroupManager.get(world);
        if (!groups.beginGroupCastleTransfer(group.getId(), actorId, targetCastleId, world.method_8510())) {
            return StartResult.INVALID;
        }
        ArrayList<MedievalSoldierEntity> started = new ArrayList<MedievalSoldierEntity>();
        for (MedievalSoldierEntity soldier : members) {
            if (!CastleTransferController.requestGroupMember(world, soldier, targetCastleId, group.getId())) {
                for (MedievalSoldierEntity rollback : started) {
                    CastleTransferController.cancel(rollback);
                }
                groups.cancelGroupCastleTransfer(group.getId(), actorId);
                return StartResult.INVALID;
            }
            started.add(soldier);
        }
        return StartResult.STARTED;
    }

    public static boolean tryBeginTravel(class_3218 world, @Nullable UUID groupId) {
        int i;
        if (world == null || groupId == null) {
            return false;
        }
        CommandGroupManager groups = CommandGroupManager.get(world);
        CommandGroupData group = groups.get(groupId);
        if (group == null || !group.isCastleTransferActive()) {
            return false;
        }
        UUID sourceCastleId = group.getCastleId();
        UUID targetCastleId = group.getTransferTargetCastleId();
        if (sourceCastleId == null || targetCastleId == null) {
            return false;
        }
        GarrisonManager garrisons = GarrisonManager.get(world);
        GarrisonData source = garrisons.get(sourceCastleId);
        GarrisonData target = garrisons.get(targetCastleId);
        if (source == null || target == null) {
            return false;
        }
        List<MedievalSoldierEntity> members = GroupCastleTransferService.loadedMembers(world, group);
        if (members.size() != group.getSoldierIds().size()) {
            return false;
        }
        for (MedievalSoldierEntity soldier : members) {
            CastleTransferData data = soldier.medievalMachinery$getCastleTransferData();
            if (data.getState() == CastleTransferState.WAITING_GROUP_DEPARTURE && groupId.equals(data.getTransferGroupId()) && targetCastleId.equals(data.getTargetCastleId())) continue;
            return false;
        }
        long departure = world.method_8510();
        long arrival = departure + CastleTransferController.travelDurationTicks(source, target);
        ArrayList<class_2338> arrivalTargets = new ArrayList<class_2338>();
        for (i = 0; i < members.size(); ++i) {
            class_2338 planned = CastleTransferController.planArrivalTarget(world, source, target, i);
            if (planned == null) {
                return false;
            }
            arrivalTargets.add(planned);
        }
        for (i = 0; i < members.size(); ++i) {
            MedievalSoldierEntity soldier = members.get(i);
            CastleTransferData data = soldier.medievalMachinery$getCastleTransferData();
            data.setArrivalTarget((class_2338)arrivalTargets.get(i));
            if (CastleTransferController.beginStrategicTravel(world, soldier, source, target, data, departure, arrival, i)) continue;
            return false;
        }
        return true;
    }

    public static boolean tryCommit(class_3218 world, @Nullable UUID groupId) {
        if (world == null || groupId == null) {
            return false;
        }
        CommandGroupManager groups = CommandGroupManager.get(world);
        CommandGroupData group = groups.get(groupId);
        if (group == null || !group.isCastleTransferActive()) {
            return false;
        }
        UUID sourceCastleId = group.getCastleId();
        UUID targetCastleId = group.getTransferTargetCastleId();
        if (sourceCastleId == null || targetCastleId == null) {
            return false;
        }
        GarrisonManager garrisons = GarrisonManager.get(world);
        GarrisonData target = garrisons.get(targetCastleId);
        if (target == null) {
            return false;
        }
        List<MedievalSoldierEntity> members = GroupCastleTransferService.loadedMembers(world, group);
        if (members.size() != group.getSoldierIds().size()) {
            return false;
        }
        for (MedievalSoldierEntity soldier : members) {
            CastleTransferData data = soldier.medievalMachinery$getCastleTransferData();
            if (data.getState() == CastleTransferState.WAITING_GROUP_COMMIT && groupId.equals(data.getTransferGroupId()) && targetCastleId.equals(data.getTargetCastleId())) continue;
            return false;
        }
        if (!groups.completeGroupCastleTransfer(groupId, group.getOwnerId())) {
            return false;
        }
        SoldierRegistryManager registry = SoldierRegistryManager.get(world);
        for (MedievalSoldierEntity soldier : members) {
            UUID soldierId = soldier.method_5667();
            garrisons.reassignSoldier(soldierId, sourceCastleId, targetCastleId);
            soldier.getNpcIdentity().setCastleId(targetCastleId);
            soldier.getNpcIdentity().setSquadId(groupId);
            soldier.setHomePosition(target.getCenter());
            soldier.applyCastleDisplayName(target.getName());
            soldier.clearCommandDestination();
            soldier.setOrderType(NpcOrderType.IDLE);
            soldier.stopAdaptiveNavigation();
            soldier.markOrganizationDirty();
            registry.updateMembership(soldierId, group.getOwnerId(), targetCastleId, groupId);
            registry.update(soldier);
            soldier.medievalMachinery$getCastleTransferData().clear();
        }
        return true;
    }

    private static List<MedievalSoldierEntity> loadedMembers(class_3218 world, CommandGroupData group) {
        ArrayList<MedievalSoldierEntity> result = new ArrayList<MedievalSoldierEntity>();
        for (UUID soldierId : group.getSoldierIds()) {
            class_1297 entity = world.method_14190(soldierId);
            if (!(entity instanceof MedievalSoldierEntity)) continue;
            MedievalSoldierEntity soldier = (MedievalSoldierEntity)entity;
            result.add(soldier);
        }
        return result;
    }

    private static boolean sameWorld(class_3218 world, GarrisonData source, GarrisonData target) {
        String dimension = world.method_27983().method_29177().toString();
        return !(source.getCenterDimension() != null && !source.getCenterDimension().equals(dimension) || target.getCenterDimension() != null && !target.getCenterDimension().equals(dimension));
    }

    public static enum StartResult {
        STARTED,
        INVALID,
        ALREADY_ACTIVE,
        MEMBERS_NOT_LOADED;

    }
}

