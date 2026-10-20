/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcFormationLayout;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.GroupCastleTransferService;
import com.medievalmachinery.mod.npc.transfer.CastleTransferController;
import com.medievalmachinery.mod.npc.transfer.CastleTransferData;
import com.medievalmachinery.mod.npc.transfer.CastleTransferState;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class GroupCastleTransferMarchCoordinator {
    private GroupCastleTransferMarchCoordinator() {
    }

    public static boolean tick(class_3218 world, @Nullable UUID groupId, CastleTransferState state) {
        GarrisonData target;
        if (world == null || groupId == null || state == null) {
            return false;
        }
        CommandGroupData group = CommandGroupManager.get(world).get(groupId);
        if (group == null || !group.isCastleTransferActive()) {
            return false;
        }
        List<MedievalSoldierEntity> members = GroupCastleTransferMarchCoordinator.orderedLoadedMembers(world, group);
        if (members.size() != group.getSoldierIds().size()) {
            return true;
        }
        if (members.stream().anyMatch(s -> s.medievalMachinery$getCastleTransferData().getState() != state)) {
            return true;
        }
        GarrisonManager garrisons = GarrisonManager.get(world);
        GarrisonData source = group.getCastleId() == null ? null : garrisons.get(group.getCastleId());
        GarrisonData garrisonData = target = group.getTransferTargetCastleId() == null ? null : garrisons.get(group.getTransferTargetCastleId());
        if (source == null || target == null) {
            return true;
        }
        return switch (state) {
            case CastleTransferState.GATHERING -> GroupCastleTransferMarchCoordinator.tickGathering(world, group, members, source);
            case CastleTransferState.DEPARTING -> GroupCastleTransferMarchCoordinator.tickDeparting(world, group, members, source, target);
            case CastleTransferState.ARRIVING -> GroupCastleTransferMarchCoordinator.tickArriving(world, group, members, target);
            default -> false;
        };
    }

    private static boolean tickGathering(class_3218 world, CommandGroupData group, List<MedievalSoldierEntity> members, GarrisonData source) {
        class_2338 gather;
        class_2338 class_23382 = gather = source.getRallyPoint() != null ? source.getRallyPoint() : source.getCenter();
        if (gather == null) {
            return true;
        }
        GroupCastleTransferMarchCoordinator.moveColumn(world, members, class_243.method_24955((class_2382)gather), false);
        if (!GroupCastleTransferMarchCoordinator.allNearMarchSlots(members, class_243.method_24955((class_2382)gather), 3.0)) {
            return true;
        }
        class_2338 exit = CastleTransferController.planDepartureTarget(world, source, gather);
        if (exit == null) {
            for (MedievalSoldierEntity soldier : members) {
                soldier.stopAdaptiveNavigation();
                soldier.setOrderType(NpcOrderType.IDLE);
                soldier.medievalMachinery$getCastleTransferData().setState(CastleTransferState.WAITING_FOR_EXIT);
            }
            return true;
        }
        for (MedievalSoldierEntity soldier : members) {
            CastleTransferData data = soldier.medievalMachinery$getCastleTransferData();
            data.setDepartureTarget(exit);
            data.setState(CastleTransferState.DEPARTING);
        }
        GroupCastleTransferMarchCoordinator.moveColumn(world, members, class_243.method_24955((class_2382)exit), false);
        return true;
    }

    private static boolean tickDeparting(class_3218 world, CommandGroupData group, List<MedievalSoldierEntity> members, GarrisonData source, GarrisonData target) {
        class_2338 exit = members.get(0).medievalMachinery$getCastleTransferData().getDepartureTarget();
        if (exit == null) {
            return true;
        }
        class_243 destination = class_243.method_24955((class_2382)exit);
        GroupCastleTransferMarchCoordinator.moveColumn(world, members, destination, true);
        if (!GroupCastleTransferMarchCoordinator.allNearMarchSlots(members, destination, 4.5)) {
            return true;
        }
        for (MedievalSoldierEntity soldier : members) {
            soldier.stopAdaptiveNavigation();
            soldier.clearCommandDestination();
            soldier.setOrderType(NpcOrderType.IDLE);
            soldier.medievalMachinery$getCastleTransferData().setState(CastleTransferState.WAITING_GROUP_DEPARTURE);
        }
        GroupCastleTransferService.tryBeginTravel(world, group.getId());
        return true;
    }

    private static boolean tickArriving(class_3218 world, CommandGroupData group, List<MedievalSoldierEntity> members, GarrisonData target) {
        class_2338 rally;
        class_2338 class_23382 = rally = target.getRallyPoint() != null ? target.getRallyPoint() : target.getCenter();
        if (rally == null) {
            return true;
        }
        class_243 destination = class_243.method_24955((class_2382)rally);
        GroupCastleTransferMarchCoordinator.moveColumn(world, members, destination, true);
        if (!GroupCastleTransferMarchCoordinator.allNearMarchSlots(members, destination, 4.0)) {
            return true;
        }
        for (MedievalSoldierEntity soldier : members) {
            soldier.stopAdaptiveNavigation();
            soldier.clearCommandDestination();
            soldier.setOrderType(NpcOrderType.IDLE);
            soldier.medievalMachinery$getCastleTransferData().setState(CastleTransferState.WAITING_GROUP_COMMIT);
        }
        GroupCastleTransferService.tryCommit(world, group.getId());
        return true;
    }

    private static void moveColumn(class_3218 world, List<MedievalSoldierEntity> members, class_243 phaseTarget, boolean holdLeaderForStragglers) {
        double forwardZ;
        if (members.isEmpty()) {
            return;
        }
        MedievalSoldierEntity leader = members.get(0);
        class_243 leaderPos = leader.method_19538();
        double dx = phaseTarget.field_1352 - leaderPos.field_1352;
        double dz = phaseTarget.field_1350 - leaderPos.field_1350;
        double len = Math.sqrt(dx * dx + dz * dz);
        double forwardX = len > 0.05 ? dx / len : -Math.sin(Math.toRadians(leader.method_36454()));
        double rightX = forwardZ = len > 0.05 ? dz / len : Math.cos(Math.toRadians(leader.method_36454()));
        double rightZ = -forwardX;
        boolean straggler = holdLeaderForStragglers && members.stream().skip(1L).anyMatch(s -> s.method_5858((class_1297)leader) > 100.0);
        class_2338 leaderTarget = class_2338.method_49638((class_2374)(straggler ? leaderPos : phaseTarget));
        GroupCastleTransferMarchCoordinator.setDestinationIfChanged(leader, leaderTarget);
        double spacing = 2.0;
        for (int i = 1; i < members.size(); ++i) {
            MedievalSoldierEntity soldier = members.get(i);
            NpcFormationLayout.Offset off = NpcFormationLayout.offset(NpcFormation.MOVEMENT, i, members.size(), spacing);
            class_243 slot = new class_243(leaderPos.field_1352 - forwardX * off.back() + rightX * off.lateral(), leaderPos.field_1351, leaderPos.field_1350 - forwardZ * off.back() + rightZ * off.lateral());
            GroupCastleTransferMarchCoordinator.setDestinationIfChanged(soldier, class_2338.method_49638((class_2374)slot));
        }
    }

    private static boolean allNearMarchSlots(List<MedievalSoldierEntity> members, class_243 anchor, double tolerance) {
        double forwardZ;
        if (members.isEmpty()) {
            return true;
        }
        MedievalSoldierEntity leader = members.get(0);
        class_243 leaderPos = leader.method_19538();
        double dx = anchor.field_1352 - leaderPos.field_1352;
        double dz = anchor.field_1350 - leaderPos.field_1350;
        double len = Math.sqrt(dx * dx + dz * dz);
        double forwardX = len > 0.05 ? dx / len : -Math.sin(Math.toRadians(leader.method_36454()));
        double rightX = forwardZ = len > 0.05 ? dz / len : Math.cos(Math.toRadians(leader.method_36454()));
        double rightZ = -forwardX;
        double toleranceSq = tolerance * tolerance;
        if (leader.method_19538().method_1025(anchor) > toleranceSq) {
            return false;
        }
        for (int i = 1; i < members.size(); ++i) {
            NpcFormationLayout.Offset off = NpcFormationLayout.offset(NpcFormation.MOVEMENT, i, members.size(), 2.0);
            class_243 expected = new class_243(anchor.field_1352 - forwardX * off.back() + rightX * off.lateral(), anchor.field_1351, anchor.field_1350 - forwardZ * off.back() + rightZ * off.lateral());
            if (!(members.get(i).method_19538().method_1025(expected) > toleranceSq)) continue;
            return false;
        }
        return true;
    }

    private static void setDestinationIfChanged(MedievalSoldierEntity soldier, class_2338 target) {
        class_2338 current = soldier.getCommandDestination();
        if (current == null || current.method_10262((class_2382)target) > 2.25) {
            soldier.setCommandDestination(target);
        }
    }

    private static List<MedievalSoldierEntity> orderedLoadedMembers(class_3218 world, CommandGroupData group) {
        ArrayList<MedievalSoldierEntity> result = new ArrayList<MedievalSoldierEntity>();
        for (UUID id : group.getSoldierIds()) {
            class_1297 entity = world.method_14190(id);
            if (!(entity instanceof MedievalSoldierEntity)) continue;
            MedievalSoldierEntity soldier = (MedievalSoldierEntity)entity;
            result.add(soldier);
        }
        UUID captainId = group.getCaptainId();
        result.sort(Comparator.comparing(s -> captainId != null && captainId.equals(s.method_5667()) ? 0 : 1).thenComparing(s -> s.method_5667().toString()));
        return result;
    }
}

