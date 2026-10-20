/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1922
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2902$class_2903
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.transfer;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineAssignmentService;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.GroupCastleTransferMarchCoordinator;
import com.medievalmachinery.mod.npc.organization.GroupCastleTransferService;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import com.medievalmachinery.mod.npc.transfer.CastleTransferData;
import com.medievalmachinery.mod.npc.transfer.CastleTransferPolicy;
import com.medievalmachinery.mod.npc.transfer.CastleTransferState;
import java.util.UUID;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2902;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class CastleTransferController {
    private CastleTransferController() {
    }

    public static boolean request(class_3218 world, MedievalSoldierEntity soldier, UUID targetCastleId) {
        return CastleTransferController.requestInternal(world, soldier, targetCastleId, null);
    }

    public static boolean requestGroupMember(class_3218 world, MedievalSoldierEntity soldier, UUID targetCastleId, UUID groupId) {
        if (groupId == null || !groupId.equals(soldier.getNpcIdentity().getSquadId())) {
            return false;
        }
        return CastleTransferController.requestInternal(world, soldier, targetCastleId, groupId);
    }

    private static boolean requestInternal(class_3218 world, MedievalSoldierEntity soldier, UUID targetCastleId, @Nullable UUID transferGroupId) {
        GarrisonData target;
        if (world == null || soldier == null || targetCastleId == null || !soldier.method_5805()) {
            return false;
        }
        CastleTransferData data = soldier.medievalMachinery$getCastleTransferData();
        if (data.isActive()) {
            return false;
        }
        UUID sourceId = soldier.getNpcIdentity().getCastleId();
        if (sourceId == null || sourceId.equals(targetCastleId)) {
            return false;
        }
        GarrisonManager manager = GarrisonManager.get(world);
        GarrisonData source = manager.get(sourceId);
        if (!CastleTransferController.isValidTargetForSoldier(soldier, source, target = manager.get(targetCastleId)) || !CastleTransferController.sameWorld(world, source, target)) {
            return false;
        }
        CastleTransferState initial = CastleTransferController.hasActiveCombat(soldier) ? CastleTransferState.BLOCKED_IN_COMBAT : CastleTransferState.GATHERING;
        data.begin(sourceId, targetCastleId, world.method_8510(), initial, transferGroupId);
        if (initial == CastleTransferState.GATHERING) {
            CastleTransferController.prepareForDeparture(world, soldier, transferGroupId != null);
        }
        return true;
    }

    public static void cancel(MedievalSoldierEntity soldier) {
        if (soldier == null) {
            return;
        }
        CastleTransferData data = soldier.medievalMachinery$getCastleTransferData();
        if (data.getState() == CastleTransferState.TRAVELLING) {
            CastleTransferController.restoreAfterStrategicTravel(soldier);
        }
        data.clear();
        soldier.stopAdaptiveNavigation();
        if (soldier.getOrderType() == NpcOrderType.MOVE_TO_POSITION) {
            soldier.setOrderType(NpcOrderType.IDLE);
        }
    }

    public static void tick(class_3218 world, MedievalSoldierEntity soldier) {
        GarrisonData target;
        CastleTransferData data = soldier.medievalMachinery$getCastleTransferData();
        if (!data.isActive()) {
            return;
        }
        GarrisonManager manager = GarrisonManager.get(world);
        GarrisonData source = data.getSourceCastleId() == null ? null : manager.get(data.getSourceCastleId());
        GarrisonData garrisonData = target = data.getTargetCastleId() == null ? null : manager.get(data.getTargetCastleId());
        if (!CastleTransferController.isValidTargetForSoldier(soldier, source, target) || !CastleTransferController.sameWorld(world, source, target)) {
            if (data.getState() == CastleTransferState.TRAVELLING) {
                CastleTransferController.restoreAfterStrategicTravel(soldier);
            }
            data.setState(CastleTransferState.NEEDS_DESTINATION);
            soldier.stopAdaptiveNavigation();
            if (soldier.getOrderType() == NpcOrderType.MOVE_TO_POSITION) {
                soldier.setOrderType(NpcOrderType.IDLE);
            }
            return;
        }
        if (data.getTransferGroupId() != null && GroupCastleTransferMarchCoordinator.tick(world, data.getTransferGroupId(), data.getState())) {
            return;
        }
        switch (data.getState()) {
            case BLOCKED_IN_COMBAT: {
                CastleTransferController.tickBlockedInCombat(world, soldier, data);
                break;
            }
            case GATHERING: {
                CastleTransferController.tickGathering(world, soldier, source, data);
                break;
            }
            case WAITING_FOR_EXIT: {
                CastleTransferController.tickWaitingForExit(world, soldier, source, data);
                break;
            }
            case DEPARTING: {
                CastleTransferController.tickDeparting(world, soldier, source, target, data);
                break;
            }
            case WAITING_GROUP_DEPARTURE: {
                GroupCastleTransferService.tryBeginTravel(world, data.getTransferGroupId());
                break;
            }
            case TRAVELLING: {
                CastleTransferController.tickTravelling(world, soldier, source, target, data);
                break;
            }
            case ARRIVING: {
                CastleTransferController.tickArriving(world, soldier, source, target, data);
                break;
            }
            case WAITING_GROUP_COMMIT: {
                GroupCastleTransferService.tryCommit(world, data.getTransferGroupId());
                break;
            }
            case NEEDS_DESTINATION: {
                soldier.stopAdaptiveNavigation();
                break;
            }
        }
    }

    private static void tickBlockedInCombat(class_3218 world, MedievalSoldierEntity soldier, CastleTransferData data) {
        if (CastleTransferController.hasActiveCombat(soldier)) {
            return;
        }
        CastleTransferController.prepareForDeparture(world, soldier, data.getTransferGroupId() != null);
        data.setState(CastleTransferState.GATHERING);
    }

    private static void tickGathering(class_3218 world, MedievalSoldierEntity soldier, GarrisonData source, CastleTransferData data) {
        class_2338 gather;
        class_2338 class_23382 = gather = source.getRallyPoint() != null ? source.getRallyPoint() : source.getCenter();
        if (gather == null) {
            data.setState(CastleTransferState.WAITING_FOR_EXIT);
            return;
        }
        if (soldier.method_19538().method_1025(class_243.method_24953((class_2382)gather)) > 25.0) {
            if (!gather.equals((Object)soldier.getCommandDestination())) {
                soldier.setCommandDestination(gather);
            }
            return;
        }
        class_2338 exit = CastleTransferController.findExteriorTarget(world, source, soldier.method_24515(), 20, 28);
        if (exit == null) {
            soldier.stopAdaptiveNavigation();
            soldier.setOrderType(NpcOrderType.IDLE);
            data.setState(CastleTransferState.WAITING_FOR_EXIT);
            return;
        }
        data.setDepartureTarget(exit);
        data.setState(CastleTransferState.DEPARTING);
        soldier.setCommandDestination(exit);
    }

    private static void tickWaitingForExit(class_3218 world, MedievalSoldierEntity soldier, GarrisonData source, CastleTransferData data) {
        data.incrementBlockedTicks();
        if (data.getBlockedTicks() < 40) {
            return;
        }
        class_2338 exit = CastleTransferController.findExteriorTarget(world, source, soldier.method_24515(), 20, 28);
        if (exit == null) {
            return;
        }
        data.setDepartureTarget(exit);
        data.setState(CastleTransferState.DEPARTING);
        soldier.setCommandDestination(exit);
    }

    private static void tickDeparting(class_3218 world, MedievalSoldierEntity soldier, GarrisonData source, GarrisonData target, CastleTransferData data) {
        class_2338 exit = data.getDepartureTarget();
        if (exit == null) {
            data.setState(CastleTransferState.WAITING_FOR_EXIT);
            return;
        }
        if (!exit.equals((Object)soldier.getCommandDestination())) {
            soldier.setCommandDestination(exit);
        }
        double reachedSq = 9.0;
        if (soldier.method_19538().method_1025(class_243.method_24953((class_2382)exit)) <= reachedSq) {
            soldier.stopAdaptiveNavigation();
            soldier.setOrderType(NpcOrderType.IDLE);
            if (data.getTransferGroupId() != null) {
                data.setState(CastleTransferState.WAITING_GROUP_DEPARTURE);
                GroupCastleTransferService.tryBeginTravel(world, data.getTransferGroupId());
            } else if (!CastleTransferController.beginStrategicTravel(world, soldier, source, target, data)) {
                data.setState(CastleTransferState.NEEDS_DESTINATION);
            }
        }
    }

    private static void tickTravelling(class_3218 world, MedievalSoldierEntity soldier, GarrisonData source, GarrisonData target, CastleTransferData data) {
        class_2338 destination;
        CastleTransferController.parkForStrategicTravel(soldier);
        if (world.method_8510() < data.getArrivalGameTime()) {
            return;
        }
        class_2338 arrival = data.getArrivalTarget();
        if (arrival == null) {
            CastleTransferController.restoreAfterStrategicTravel(soldier);
            data.setState(CastleTransferState.NEEDS_DESTINATION);
            return;
        }
        CastleTransferController.restoreAfterStrategicTravel(soldier);
        soldier.method_5808((double)arrival.method_10263() + 0.5, arrival.method_10264(), (double)arrival.method_10260() + 0.5, soldier.method_36454(), soldier.method_36455());
        data.setState(CastleTransferState.ARRIVING);
        class_2338 class_23382 = destination = target.getRallyPoint() != null ? target.getRallyPoint() : target.getCenter();
        if (destination != null) {
            soldier.setCommandDestination(destination);
        }
    }

    private static void tickArriving(class_3218 world, MedievalSoldierEntity soldier, GarrisonData source, GarrisonData target, CastleTransferData data) {
        class_2338 destination;
        class_2338 class_23382 = destination = target.getRallyPoint() != null ? target.getRallyPoint() : target.getCenter();
        if (destination == null) {
            data.setState(CastleTransferState.NEEDS_DESTINATION);
            soldier.stopAdaptiveNavigation();
            return;
        }
        if (!destination.equals((Object)soldier.getCommandDestination())) {
            soldier.setCommandDestination(destination);
        }
        double reachedSq = 16.0;
        if (soldier.method_19538().method_1025(class_243.method_24953((class_2382)destination)) > reachedSq) {
            return;
        }
        if (data.getTransferGroupId() != null) {
            soldier.stopAdaptiveNavigation();
            soldier.clearCommandDestination();
            soldier.setOrderType(NpcOrderType.IDLE);
            data.setState(CastleTransferState.WAITING_GROUP_COMMIT);
            GroupCastleTransferService.tryCommit(world, data.getTransferGroupId());
            return;
        }
        UUID sourceId = data.getSourceCastleId();
        UUID targetId = data.getTargetCastleId();
        if (!(targetId != null && GarrisonManager.get(world).reassignSoldier(soldier.method_5667(), sourceId, targetId) || GarrisonManager.get(world).get(targetId) != null)) {
            data.setState(CastleTransferState.NEEDS_DESTINATION);
            return;
        }
        soldier.getNpcIdentity().setCastleId(targetId);
        soldier.setHomePosition(target.getCenter());
        soldier.applyCastleDisplayName(target.getName());
        soldier.clearCommandDestination();
        soldier.setOrderType(NpcOrderType.IDLE);
        soldier.markOrganizationDirty();
        SoldierRegistryManager.get(world).update(soldier);
        data.setState(CastleTransferState.COMPLETE);
        data.clear();
    }

    public static boolean beginStrategicTravel(class_3218 world, MedievalSoldierEntity soldier, GarrisonData source, GarrisonData target, CastleTransferData data, long departureTime, long arrivalTime, int arrivalSlot) {
        if (world == null || soldier == null || source == null || target == null || data == null) {
            return false;
        }
        class_2338 arrival = data.getArrivalTarget();
        if (arrival == null) {
            arrival = CastleTransferController.planArrivalTarget(world, source, target, arrivalSlot);
        }
        if (arrival == null) {
            return false;
        }
        soldier.stopAdaptiveNavigation();
        soldier.clearCommandDestination();
        soldier.setOrderType(NpcOrderType.IDLE);
        soldier.forgetCurrentTarget();
        data.setArrivalTarget(arrival);
        data.markTravelling(departureTime, arrivalTime);
        soldier.method_5808((double)arrival.method_10263() + 0.5, arrival.method_10264(), (double)arrival.method_10260() + 0.5, soldier.method_36454(), soldier.method_36455());
        CastleTransferController.parkForStrategicTravel(soldier);
        return true;
    }

    private static boolean beginStrategicTravel(class_3218 world, MedievalSoldierEntity soldier, GarrisonData source, GarrisonData target, CastleTransferData data) {
        long departure = world.method_8510();
        long arrival = departure + CastleTransferController.travelDurationTicks(source, target);
        return CastleTransferController.beginStrategicTravel(world, soldier, source, target, data, departure, arrival, 0);
    }

    @Nullable
    public static class_2338 planDepartureTarget(class_3218 world, GarrisonData source, class_2338 preferredFrom) {
        return CastleTransferController.findExteriorTarget(world, source, preferredFrom, 20, 28);
    }

    @Nullable
    public static class_2338 planArrivalTarget(class_3218 world, GarrisonData source, GarrisonData target, int slot) {
        class_2338 preferredFrom;
        class_2338 class_23382 = preferredFrom = source.getCenter() != null ? source.getCenter() : target.getCenter();
        if (preferredFrom == null) {
            return null;
        }
        class_2338 base = CastleTransferController.findExteriorTarget(world, target, preferredFrom, 20, 28);
        if (base == null || slot <= 0) {
            return base;
        }
        int desired = Math.max(1, slot);
        for (int radius = 1; radius <= 6; ++radius) {
            int[][] offsets = new int[][]{{radius, 0}, {-radius, 0}, {0, radius}, {0, -radius}, {radius, radius}, {radius, -radius}, {-radius, radius}, {-radius, -radius}};
            for (int i = 0; i < offsets.length; ++i) {
                if (--desired > 0) continue;
                class_2338 candidate = base.method_10069(offsets[i][0], 0, offsets[i][1]);
                class_2338 surface = world.method_8598(class_2902.class_2903.field_13203, candidate);
                if (CastleTransferController.isStandingSpace(world, surface)) {
                    return surface.method_10062();
                }
                if (CastleTransferController.isStandingSpace(world, surface.method_10074())) {
                    return surface.method_10074().method_10062();
                }
                desired = 1;
            }
        }
        return base;
    }

    public static long travelDurationTicks(GarrisonData source, GarrisonData target) {
        class_2338 targetCenter;
        class_2338 sourceCenter = source == null ? null : source.getCenter();
        class_2338 class_23382 = targetCenter = target == null ? null : target.getCenter();
        if (sourceCenter == null || targetCenter == null) {
            return 200L;
        }
        double dx = targetCenter.method_10263() - sourceCenter.method_10263();
        double dz = targetCenter.method_10260() - sourceCenter.method_10260();
        return CastleTransferPolicy.travelTicks(Math.sqrt(dx * dx + dz * dz));
    }

    private static void parkForStrategicTravel(MedievalSoldierEntity soldier) {
        soldier.stopAdaptiveNavigation();
        soldier.clearCommandDestination();
        soldier.method_18799(class_243.field_1353);
        soldier.method_5648(true);
        soldier.method_5684(true);
        soldier.method_5875(true);
        soldier.method_5977(true);
    }

    private static void restoreAfterStrategicTravel(MedievalSoldierEntity soldier) {
        soldier.method_5648(false);
        soldier.method_5684(false);
        soldier.method_5875(false);
        soldier.method_5977(false);
        soldier.method_18799(class_243.field_1353);
    }

    private static void prepareForDeparture(class_3218 world, MedievalSoldierEntity soldier, boolean preserveGroup) {
        if (!preserveGroup) {
            CommandGroupManager.get(world).removeSoldierEverywhere(soldier.method_5667());
            soldier.getNpcIdentity().setSquadId(null);
            soldier.getNpcIdentity().setCaptainId(null);
            soldier.setFormationSlot(-1);
        }
        NpcMachineAssignmentService.clear(soldier);
        soldier.stopStrategicAreaFire();
        soldier.forgetCurrentTarget();
        soldier.clearCommandDestination();
        soldier.setOrderType(NpcOrderType.IDLE);
        soldier.stopAdaptiveNavigation();
    }

    private static boolean hasActiveCombat(MedievalSoldierEntity soldier) {
        return soldier.method_5968() != null && soldier.method_5968().method_5805();
    }

    private static boolean isValidTargetForSoldier(MedievalSoldierEntity soldier, @Nullable GarrisonData source, @Nullable GarrisonData target) {
        if (source == null || target == null) {
            return false;
        }
        UUID soldierKingdom = soldier.getNpcIdentity().getKingdomId();
        UUID sourceKingdom = source.getKingdomId();
        UUID targetKingdom = target.getKingdomId();
        if (soldierKingdom != null) {
            return soldierKingdom.equals(sourceKingdom) && soldierKingdom.equals(targetKingdom);
        }
        return sourceKingdom == null && targetKingdom == null && source.getOwnerId().equals(target.getOwnerId()) && source.getOwnerId().equals(soldier.getNpcIdentity().getOwnerId());
    }

    private static boolean sameWorld(class_3218 world, GarrisonData source, GarrisonData target) {
        String dimension = world.method_27983().method_29177().toString();
        return !(source.getCenterDimension() != null && !source.getCenterDimension().equals(dimension) || target.getCenterDimension() != null && !target.getCenterDimension().equals(dimension));
    }

    @Nullable
    private static class_2338 findExteriorTarget(class_3218 world, GarrisonData castle, class_2338 preferredFrom, int ringMin, int ringMax) {
        class_2338 center;
        class_2338 class_23382 = center = castle.getCenter() != null ? castle.getCenter() : castle.getRallyPoint();
        if (center == null) {
            return null;
        }
        if (castle.getCenterDimension() != null && !castle.getCenterDimension().equals(world.method_27983().method_29177().toString())) {
            return null;
        }
        class_2350 preferred = CastleTransferController.horizontalDirection(center, preferredFrom);
        class_2350[] directions = new class_2350[]{preferred, preferred.method_10170(), preferred.method_10160(), preferred.method_10153()};
        for (int distance = ringMin; distance <= ringMax; distance += 4) {
            for (class_2350 direction : directions) {
                class_2338 horizontal = center.method_10079(direction, distance);
                class_2338 surface = world.method_8598(class_2902.class_2903.field_13203, horizontal);
                class_2338 feet = surface;
                if (CastleTransferController.isStandingSpace(world, feet)) {
                    return feet.method_10062();
                }
                if (!CastleTransferController.isStandingSpace(world, feet.method_10074())) continue;
                return feet.method_10074().method_10062();
            }
        }
        return null;
    }

    private static class_2350 horizontalDirection(class_2338 center, class_2338 other) {
        int dx = other.method_10263() - center.method_10263();
        int dz = other.method_10260() - center.method_10260();
        if (Math.abs(dx) >= Math.abs(dz)) {
            return dx >= 0 ? class_2350.field_11034 : class_2350.field_11039;
        }
        return dz >= 0 ? class_2350.field_11035 : class_2350.field_11043;
    }

    private static boolean isStandingSpace(class_3218 world, class_2338 feet) {
        if (!world.method_8320(feet).method_26220((class_1922)world, feet).method_1110()) {
            return false;
        }
        if (!world.method_8320(feet.method_10084()).method_26220((class_1922)world, feet.method_10084()).method_1110()) {
            return false;
        }
        class_2338 floor = feet.method_10074();
        return !world.method_8320(floor).method_26220((class_1922)world, floor).method_1110();
    }
}

