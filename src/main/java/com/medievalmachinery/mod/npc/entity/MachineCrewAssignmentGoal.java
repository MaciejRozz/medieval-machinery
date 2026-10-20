/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_3218
 *  net.minecraft.class_3532
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.machine.core.MachineAimSolution;
import com.medievalmachinery.mod.machine.core.MachineCombatTarget;
import com.medievalmachinery.mod.machine.core.MachineCrewAction;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.machine.core.MachineCrewSupplyNeed;
import com.medievalmachinery.mod.machine.core.MachineCrewWorkContext;
import com.medievalmachinery.mod.npc.core.NpcLeadershipPolicy;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.NpcMachineCommandMovementPolicy;
import com.medievalmachinery.mod.npc.entity.NpcMachineTargetingService;
import com.medievalmachinery.mod.npc.machine.NpcMachineAssignmentService;
import com.medievalmachinery.mod.npc.machine.NpcMachineCrewCoordinator;
import com.medievalmachinery.mod.npc.machine.NpcMachineCrewReadinessPolicy;
import com.medievalmachinery.mod.npc.machine.NpcMachineRolePolicy;
import com.medievalmachinery.mod.npc.machine.NpcMachineSupplyInventoryAccess;
import com.medievalmachinery.mod.npc.machine.NpcMachineSupplyPlan;
import com.medievalmachinery.mod.npc.machine.NpcMachineSupplyPolicy;
import com.medievalmachinery.mod.npc.machine.NpcMachineSupplyReservationManager;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.class_1263;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1352;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3532;

final class MachineCrewAssignmentGoal
extends class_1352 {
    private static final double CREW_ARRIVAL_SQ = 4.840000000000001;
    private static final double OPERATOR_ARRIVAL_SQ = 3.4225000000000003;
    private static final double OPERATOR_DRIVE_MACHINE_TETHER_SQ = 30.25;
    private static final double OPERATOR_WORK_MACHINE_TETHER_SQ = 14.0625;
    private final MedievalSoldierEntity soldier;
    private final double speed;
    private class_1297 machineEntity;
    private MedievalMachineryOperable machine;
    private MachineCrewRole crewRole;
    private MachineCrewAction activeAction = MachineCrewAction.IDLE;
    private int workTicks;
    private NpcMachineSupplyPlan supplyPlan;
    private List<MachineCrewSupplyNeed> activeSupplyNeeds = List.of();
    private final Set<class_2338> failedSupplyChests = new HashSet<class_2338>();
    private class_243 supplyLastPosition;
    private int supplyStalledTicks;
    private int supplyInteractionTicks;
    private int supplyRetryCooldown;
    private MachineCombatTarget machineTarget;
    private int targetRefreshTicks;
    private int targetLostTicks;
    private MachineAimSolution currentAim = MachineAimSolution.INVALID;
    private class_243 trackedAimPoint = null;
    private MachineCombatTarget committedFireTarget;
    private MachineAimSolution committedFireAim = MachineAimSolution.INVALID;
    private NpcOrderType lastObservedOrder;
    private boolean operatorWorkStationActive;

    MachineCrewAssignmentGoal(MedievalSoldierEntity soldier, double speed) {
        this.soldier = soldier;
        this.speed = speed;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405, class_1352.class_4134.field_18406));
    }

    public boolean method_6264() {
        class_1297 found;
        class_3218 world;
        block7: {
            block6: {
                class_1937 class_19372 = this.soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block6;
                world = (class_3218)class_19372;
                if (this.soldier.medievalMachinery$hasMachineAssignment() && !this.soldier.isInventoryGuiOpen() && !this.soldier.hasSupplyMovementOverride() && !this.soldier.medievalMachinery$isTemporarilyDefendingFromMachine()) break block7;
            }
            return false;
        }
        UUID machineId = this.soldier.medievalMachinery$getAssignedMachineId();
        class_1297 class_12972 = found = machineId == null ? null : world.method_14190(machineId);
        if (!(found instanceof MedievalMachineryOperable)) {
            return false;
        }
        MedievalMachineryOperable operable = (MedievalMachineryOperable)found;
        MachineCrewRole role = this.soldier.medievalMachinery$getAssignedMachineCrewRole();
        if (!NpcMachineCrewReadinessPolicy.canWork(this.soldier, role)) {
            return false;
        }
        if (!NpcMachineAssignmentService.canAssign(this.soldier, operable, role)) {
            return false;
        }
        this.machineEntity = found;
        this.machine = operable;
        this.crewRole = role;
        return true;
    }

    public boolean method_6266() {
        return this.machineEntity != null && this.machine != null && !this.machineEntity.method_31481() && this.machineEntity.method_37908() == this.soldier.method_37908() && this.soldier.medievalMachinery$isAssignedTo(this.machineEntity.method_5667(), this.crewRole) && NpcMachineCrewReadinessPolicy.canWork(this.soldier, this.crewRole) && !this.soldier.hasSupplyMovementOverride() && !this.soldier.medievalMachinery$isTemporarilyDefendingFromMachine();
    }

    public void method_6269() {
        this.soldier.setMachineHeldVisual(this.machine == null ? class_1799.field_8037 : this.machine.medievalMachinery$getNpcHeldVisual((class_1297)this.soldier));
        this.soldier.adaptiveNavigation().reset();
        this.activeAction = MachineCrewAction.IDLE;
        this.workTicks = 0;
        this.failedSupplyChests.clear();
        this.supplyPlan = null;
        this.activeSupplyNeeds = List.of();
        this.supplyLastPosition = null;
        this.supplyStalledTicks = 0;
        this.supplyInteractionTicks = 0;
        this.supplyRetryCooldown = 0;
        this.machineTarget = null;
        this.targetRefreshTicks = 0;
        this.targetLostTicks = 0;
        this.currentAim = MachineAimSolution.INVALID;
        this.committedFireTarget = null;
        this.committedFireAim = MachineAimSolution.INVALID;
        this.lastObservedOrder = this.soldier.getOrderType();
        this.operatorWorkStationActive = false;
    }

    public void method_6270() {
        this.soldier.setMachineHeldVisual(class_1799.field_8037);
        this.soldier.adaptiveNavigation().stop();
        if (this.machine != null && this.crewRole == MachineCrewRole.OPERATOR) {
            this.machine.medievalMachinery$release(this.soldier.method_5667());
        }
        this.machineEntity = null;
        this.machine = null;
        this.crewRole = null;
        this.activeAction = MachineCrewAction.IDLE;
        this.workTicks = 0;
        this.releaseSupplyReservation();
        this.releaseWorkLease();
        this.failedSupplyChests.clear();
        this.machineTarget = null;
        this.currentAim = MachineAimSolution.INVALID;
        this.committedFireTarget = null;
        this.committedFireAim = MachineAimSolution.INVALID;
        this.operatorWorkStationActive = false;
    }

    public void method_6268() {
        boolean latchedOperatorReady;
        NpcMachineCommandMovementPolicy.Command driveCommand;
        if (this.machine == null || this.machineEntity == null || this.crewRole == null) {
            return;
        }
        this.soldier.setMachineHeldVisual(this.machine.medievalMachinery$getNpcHeldVisual((class_1297)this.soldier));
        if (this.lastObservedOrder != this.soldier.getOrderType()) {
            boolean leftDriveOrder = this.lastObservedOrder == NpcOrderType.FOLLOW || this.lastObservedOrder == NpcOrderType.MOVE_TO_POSITION;
            this.lastObservedOrder = this.soldier.getOrderType();
            if (leftDriveOrder) {
                this.releaseWorkLease();
                this.activeAction = MachineCrewAction.IDLE;
                this.workTicks = 0;
                this.machineTarget = null;
                this.currentAim = MachineAimSolution.INVALID;
                this.committedFireTarget = null;
                this.committedFireAim = MachineAimSolution.INVALID;
                this.targetRefreshTicks = 0;
                this.targetLostTicks = 0;
            }
        }
        if (this.supplyRetryCooldown > 0) {
            --this.supplyRetryCooldown;
        }
        if (this.supplyPlan != null) {
            this.tickMachineSupplyRun();
            return;
        }
        class_243 station = this.machine.medievalMachinery$getCrewPosition(this.crewRole, this.soldier.method_5667());
        class_243 navigationStation = this.resolveNavigationStation(station);
        double distanceSq = this.soldier.method_5649(navigationStation.field_1352, navigationStation.field_1351, navigationStation.field_1350);
        if (this.crewRole == MachineCrewRole.OPERATOR && this.machine.medievalMachinery$isMobile() && (driveCommand = NpcMachineCommandMovementPolicy.resolve(this.soldier, this.machine)).active()) {
            double machineDistanceSq = this.soldier.method_5858(this.machineEntity);
            if (machineDistanceSq <= 30.25 && NpcMachineAssignmentService.tryClaimOperator(this.soldier, this.machine)) {
                if (this.tickMobileMachineCommand(navigationStation, driveCommand)) {
                    return;
                }
            } else {
                this.machine.medievalMachinery$npcTick((class_1297)this.soldier, this.machineEntity.method_36454(), 0.0f, 0.0f, 0.0f);
                this.soldier.adaptiveNavigation().tickToPoint(navigationStation, this.speed);
                return;
            }
        }
        double arrivalSq = this.crewRole == MachineCrewRole.OPERATOR ? 3.4225000000000003 : 4.840000000000001;
        boolean bl = latchedOperatorReady = this.crewRole == MachineCrewRole.OPERATOR && !this.machine.medievalMachinery$isMobile() && this.operatorWorkStationActive && this.soldier.method_5858(this.machineEntity) <= 14.0625;
        if ((distanceSq <= arrivalSq || latchedOperatorReady) && !this.soldier.adaptiveNavigation().isLadderRouteActive()) {
            if (this.crewRole == MachineCrewRole.OPERATOR) {
                if (!NpcMachineAssignmentService.tryClaimOperator(this.soldier, this.machine)) {
                    return;
                }
                if (!this.operatorWorkStationActive) {
                    this.operatorWorkStationActive = true;
                    this.soldier.npcDebug().event("MACHINE_WORK_READY", this.machineEntity.method_24515(), "operator station reached; target acquisition owns next state");
                    this.targetRefreshTicks = 0;
                }
                this.soldier.adaptiveNavigation().stop();
                this.updateMachineTargetAndAim();
                MachineAimSolution steeringAim = this.activeAction == MachineCrewAction.IGNITE && this.committedFireAim.valid() ? this.committedFireAim : this.currentAim;
                float yaw = steeringAim.valid() ? steeringAim.yaw() : class_3532.method_15393((float)this.machine.medievalMachinery$operatorLookYaw());
                float pitch = steeringAim.valid() ? steeringAim.pitch() : this.soldier.method_36455();
                this.machine.medievalMachinery$npcTick((class_1297)this.soldier, yaw, pitch, 0.0f, 0.0f);
                this.faceFiringDirection(this.machine.medievalMachinery$getNpcFiringLookYaw(steeringAim));
            } else {
                this.soldier.adaptiveNavigation().stop();
                this.soldier.method_5988().method_6226(this.machineEntity, 30.0f, 30.0f);
            }
            this.tickCrewWork();
            return;
        }
        this.operatorWorkStationActive = false;
        this.soldier.adaptiveNavigation().tickToPoint(navigationStation, this.speed);
    }

    private class_243 resolveNavigationStation(class_243 station) {
        class_2338 base = class_2338.method_49638((class_2374)station);
        class_2338 best = null;
        double bestSq = Double.MAX_VALUE;
        for (int dy : new int[]{0, 1, -1}) {
            for (int dx = -1; dx <= 1; ++dx) {
                for (int dz = -1; dz <= 1; ++dz) {
                    class_243 candidate;
                    double sq;
                    class_2338 feet = base.method_10069(dx, dy, dz);
                    class_2680 feetState = this.soldier.method_37908().method_8320(feet);
                    class_2680 headState = this.soldier.method_37908().method_8320(feet.method_10084());
                    class_2338 below = feet.method_10074();
                    class_2680 belowState = this.soldier.method_37908().method_8320(below);
                    if (!feetState.method_26220((class_1922)this.soldier.method_37908(), feet).method_1110() || !headState.method_26220((class_1922)this.soldier.method_37908(), feet.method_10084()).method_1110() || belowState.method_26220((class_1922)this.soldier.method_37908(), below).method_1110() || !((sq = (candidate = class_243.method_24955((class_2382)feet)).method_1025(station)) <= 2.8899999999999997) || !(sq < bestSq)) continue;
                    bestSq = sq;
                    best = feet.method_10062();
                }
            }
        }
        return best == null ? station : class_243.method_24955(best);
    }

    private boolean tickMobileMachineCommand(class_243 station, NpcMachineCommandMovementPolicy.Command driveCommand) {
        if (driveCommand.arrived()) {
            this.machine.medievalMachinery$npcTick((class_1297)this.soldier, driveCommand.yaw(), 0.0f, 0.0f, 0.0f);
            this.soldier.adaptiveNavigation().stop();
            if (this.soldier.getOrderType() == NpcOrderType.MOVE_TO_POSITION) {
                this.releaseWorkLease();
                this.activeAction = MachineCrewAction.IDLE;
                this.workTicks = 0;
                this.committedFireTarget = null;
                this.committedFireAim = MachineAimSolution.INVALID;
                this.soldier.goalCompleteMoveAt(this.machineEntity.method_24515());
                return true;
            }
            return false;
        }
        this.releaseWorkLease();
        this.activeAction = MachineCrewAction.IDLE;
        this.workTicks = 0;
        this.machineTarget = null;
        this.currentAim = MachineAimSolution.INVALID;
        this.committedFireTarget = null;
        this.committedFireAim = MachineAimSolution.INVALID;
        this.machine.medievalMachinery$npcTick((class_1297)this.soldier, driveCommand.yaw(), 0.0f, driveCommand.forward(), driveCommand.sideways());
        class_243 walkTarget = station;
        class_243 machineVelocity = this.machineEntity.method_18798();
        class_243 horizontalVelocity = new class_243(machineVelocity.field_1352, 0.0, machineVelocity.field_1350);
        if (horizontalVelocity.method_1027() > 1.0E-5) {
            walkTarget = station.method_1019(horizontalVelocity.method_1029().method_1021(1.15));
        }
        this.soldier.adaptiveNavigation().tickToPoint(walkTarget, Math.max(this.speed, 1.12));
        this.soldier.method_5988().method_6230(this.machineEntity.method_23317(), this.soldier.method_23320(), this.machineEntity.method_23321(), 30.0f, 30.0f);
        return true;
    }

    private void faceFiringDirection(float yaw) {
        double rad = Math.toRadians(yaw);
        double lookX = this.soldier.method_23317() - Math.sin(rad) * 12.0;
        double lookZ = this.soldier.method_23321() + Math.cos(rad) * 12.0;
        this.soldier.method_5988().method_6230(lookX, this.soldier.method_33571().field_1351, lookZ, 60.0f, 30.0f);
    }

    private void tickCrewWork() {
        class_3218 world;
        class_1937 class_19372;
        MachineCrewAction requested;
        MachineCrewWorkContext context;
        block27: {
            block28: {
                List<MachineCrewSupplyNeed> supplyNeeds;
                block24: {
                    class_3218 world2;
                    block26: {
                        block25: {
                            if (this.machine == null || this.crewRole == null) {
                                return;
                            }
                            NpcLeadershipPolicy.Strength leadership = this.soldier.getNearbyLeadershipStrength();
                            context = new MachineCrewWorkContext(NpcLeadershipPolicy.machineCycleTimeMultiplier(leadership), NpcLeadershipPolicy.machineAimSpreadMultiplier(leadership), NpcMachineRolePolicy.mayUseVirtualStandardMachineResources(this.soldier.getNpcRole(), this.crewRole));
                            if (this.crewRole != MachineCrewRole.OPERATOR || this.activeAction != MachineCrewAction.IGNITE || this.workTicks <= 0 || this.committedFireTarget == null || !this.committedFireAim.valid()) break block24;
                            class_1937 class_19373 = this.soldier.method_37908();
                            if (!(class_19373 instanceof class_3218)) break block25;
                            world2 = (class_3218)class_19373;
                            if (!this.soldier.isHoldFire() && NpcMachineTargetingService.isStillValid(world2, this.soldier, this.machine, this.machine.medievalMachinery$getTargetProfile(), this.committedFireTarget, Math.max(4.0, this.machine.medievalMachinery$getNpcTargetingRange())) && NpcMachineTargetingService.isFireCorridorClear(world2, this.soldier, this.machine, this.committedFireTarget, this.committedFireAim) && NpcMachineTargetingService.isProjectilePathTerrainClear(world2, this.machine, this.committedFireTarget, this.committedFireAim)) break block26;
                        }
                        this.releaseWorkLease();
                        this.activeAction = MachineCrewAction.IDLE;
                        this.workTicks = 0;
                        this.committedFireTarget = null;
                        this.committedFireAim = MachineAimSolution.INVALID;
                        return;
                    }
                    NpcMachineCrewCoordinator.refresh(world2, this.machineEntity.method_5667(), this.soldier.method_5667());
                    if (--this.workTicks > 0) {
                        return;
                    }
                    this.soldier.method_6104(class_1268.field_5808);
                    this.machine.medievalMachinery$performNpcCrewAction((class_1297)this.soldier, this.crewRole, MachineCrewAction.IGNITE, context);
                    this.releaseWorkLease();
                    this.activeAction = MachineCrewAction.IDLE;
                    this.workTicks = 0;
                    this.committedFireTarget = null;
                    this.committedFireAim = MachineAimSolution.INVALID;
                    this.trackedAimPoint = null;
                    return;
                }
                if (this.supplyRetryCooldown <= 0 && (supplyNeeds = this.machine.medievalMachinery$getNpcCrewSupplyNeeds((class_1297)this.soldier, this.crewRole, context)) != null && !supplyNeeds.isEmpty()) {
                    if (this.tryBeginMachineSupplyRun(supplyNeeds)) {
                        return;
                    }
                    this.supplyRetryCooldown = 200;
                }
                if ((requested = this.machine.medievalMachinery$getNpcCrewAction((class_1297)this.soldier, this.crewRole, context)) == null) {
                    requested = MachineCrewAction.IDLE;
                }
                if (this.crewRole == MachineCrewRole.OPERATOR && this.soldier.isHoldFire() && requested == MachineCrewAction.IGNITE) {
                    this.releaseWorkLease();
                    this.activeAction = MachineCrewAction.IDLE;
                    this.workTicks = 0;
                    return;
                }
                if (requested != MachineCrewAction.IGNITE || this.crewRole != MachineCrewRole.OPERATOR) break block27;
                class_19372 = this.soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block28;
                world = (class_3218)class_19372;
                if (this.machineTarget != null && NpcMachineTargetingService.isStillValid(world, this.soldier, this.machine, this.machine.medievalMachinery$getTargetProfile(), this.machineTarget, Math.max(4.0, this.machine.medievalMachinery$getNpcTargetingRange())) && NpcMachineTargetingService.isFireCorridorClear(world, this.soldier, this.machine, this.machineTarget, this.currentAim) && NpcMachineTargetingService.isProjectilePathTerrainClear(world, this.machine, this.machineTarget, this.currentAim) && !this.soldier.isHoldFire() && this.currentAim.valid() && this.machine.medievalMachinery$isNpcAimAligned(this.currentAim)) break block27;
            }
            this.releaseWorkLease();
            this.activeAction = MachineCrewAction.IDLE;
            this.workTicks = 0;
            return;
        }
        if (requested == MachineCrewAction.IDLE) {
            this.releaseWorkLease();
            this.activeAction = MachineCrewAction.IDLE;
            this.workTicks = 0;
            this.committedFireTarget = null;
            this.committedFireAim = MachineAimSolution.INVALID;
            return;
        }
        class_19372 = this.soldier.method_37908();
        if (class_19372 instanceof class_3218 && NpcMachineCrewCoordinator.shouldDeferToDedicatedCrew(world = (class_3218)class_19372, this.soldier, this.machineEntity.method_5667(), this.crewRole, requested)) {
            this.releaseWorkLease();
            this.activeAction = MachineCrewAction.IDLE;
            this.workTicks = 0;
            return;
        }
        if (requested != this.activeAction) {
            class_3218 world3;
            this.releaseWorkLease();
            class_1937 class_19374 = this.soldier.method_37908();
            if (!(class_19374 instanceof class_3218) || !NpcMachineCrewCoordinator.tryLease(world3 = (class_3218)class_19374, this.machineEntity.method_5667(), this.soldier.method_5667(), requested)) {
                this.activeAction = MachineCrewAction.IDLE;
                this.workTicks = 0;
                return;
            }
            this.activeAction = requested;
            if (this.activeAction == MachineCrewAction.IGNITE && this.crewRole == MachineCrewRole.OPERATOR) {
                this.committedFireTarget = this.machineTarget;
                this.committedFireAim = this.currentAim;
            } else {
                this.committedFireTarget = null;
                this.committedFireAim = MachineAimSolution.INVALID;
            }
            int baseTicks = Math.max(1, this.machine.medievalMachinery$getNpcCrewActionTicks(this.activeAction, this.crewRole));
            this.workTicks = Math.max(1, (int)Math.ceil((double)baseTicks * context.cycleTimeMultiplier()));
        } else {
            class_19372 = this.soldier.method_37908();
            if (class_19372 instanceof class_3218) {
                world = (class_3218)class_19372;
                NpcMachineCrewCoordinator.refresh(world, this.machineEntity.method_5667(), this.soldier.method_5667());
            }
        }
        if (--this.workTicks > 0) {
            return;
        }
        if (this.activeAction == MachineCrewAction.IGNITE && this.crewRole == MachineCrewRole.OPERATOR) {
            this.soldier.method_6104(class_1268.field_5808);
        }
        MachineCrewAction completedAction = this.activeAction;
        this.machine.medievalMachinery$performNpcCrewAction((class_1297)this.soldier, this.crewRole, this.activeAction, context);
        this.releaseWorkLease();
        this.activeAction = MachineCrewAction.IDLE;
        this.workTicks = 0;
        this.committedFireTarget = null;
        this.committedFireAim = MachineAimSolution.INVALID;
        if (completedAction == MachineCrewAction.IGNITE) {
            this.trackedAimPoint = null;
            if (this.soldier.isStrategicAreaFireActive()) {
                this.machineTarget = null;
            }
        }
    }

    private void updateMachineTargetAndAim() {
        MachineCombatTarget refreshed;
        class_3218 world;
        block18: {
            block17: {
                class_1937 class_19372 = this.soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block17;
                world = (class_3218)class_19372;
                if (this.machine != null && this.crewRole == MachineCrewRole.OPERATOR) break block18;
            }
            this.machineTarget = null;
            this.currentAim = MachineAimSolution.INVALID;
            return;
        }
        if (this.soldier.isStrategicAreaFireActive()) {
            if (this.machineTarget == null || !this.machineTarget.isPoint()) {
                this.machineTarget = MachineCombatTarget.point(this.soldier.chooseStrategicAreaAimPoint());
                this.targetLostTicks = 0;
                this.trackedAimPoint = null;
            }
        } else if (--this.targetRefreshTicks <= 0) {
            if (this.machineTarget != null && this.machineTarget.isPoint()) {
                this.machineTarget = null;
                this.trackedAimPoint = null;
                this.currentAim = MachineAimSolution.INVALID;
            }
            this.targetRefreshTicks = 1;
            MachineCombatTarget resolved = NpcMachineTargetingService.acquire(world, this.soldier, this.machine, this.machineTarget);
            if (resolved != null) {
                this.machineTarget = resolved;
                this.targetLostTicks = 0;
            } else if (++this.targetLostTicks >= 3) {
                this.machineTarget = null;
                this.targetLostTicks = 0;
            }
        } else if (this.machineTarget != null && (refreshed = NpcMachineTargetingService.refresh(world, this.machineTarget)) != null) {
            this.machineTarget = refreshed;
        }
        if (this.machineTarget != null) {
            MachineCombatTarget aimingTarget = this.stabilizeAndLeadMachineTarget(world, this.machineTarget);
            NpcLeadershipPolicy.Strength leadership = this.soldier.getNearbyLeadershipStrength();
            this.currentAim = this.machine.medievalMachinery$solveNpcAim(aimingTarget, NpcLeadershipPolicy.machineAimSpreadMultiplier(leadership));
            if (this.soldier.isStrategicAreaFireActive() && this.machineTarget.isPoint() && (!this.currentAim.valid() || !NpcMachineTargetingService.isStillValid(world, this.soldier, this.machine, this.machine.medievalMachinery$getTargetProfile(), this.machineTarget, Math.max(4.0, this.machine.medievalMachinery$getNpcTargetingRange())))) {
                this.machineTarget = null;
                this.trackedAimPoint = null;
                this.currentAim = MachineAimSolution.INVALID;
            }
        } else {
            this.trackedAimPoint = null;
            this.currentAim = MachineAimSolution.INVALID;
        }
    }

    private MachineCombatTarget stabilizeAndLeadMachineTarget(class_3218 world, MachineCombatTarget target) {
        NpcMachineTargetingService.StabilizedTarget stabilized = NpcMachineTargetingService.stabilizeAndLead(world, this.machine, target, this.trackedAimPoint);
        this.trackedAimPoint = stabilized.trackedAimPoint();
        return stabilized.target();
    }

    private boolean tryBeginMachineSupplyRun(List<MachineCrewSupplyNeed> needs) {
        class_1937 class_19372 = this.soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return false;
        }
        class_3218 world = (class_3218)class_19372;
        for (class_2338 chest : NpcMachineSupplyPolicy.orderedSupplyChests(this.soldier)) {
            NpcMachineSupplyPlan reserved;
            class_1263 inventory;
            if (this.failedSupplyChests.contains(chest) || (inventory = NpcMachineSupplyInventoryAccess.resolve(world, chest)) == null || (reserved = NpcMachineSupplyReservationManager.tryReserve(world, chest, this.soldier.method_5667(), inventory, needs)) == null) continue;
            this.supplyPlan = reserved;
            this.activeSupplyNeeds = List.copyOf(needs);
            this.supplyLastPosition = this.soldier.method_19538();
            this.supplyStalledTicks = 0;
            this.supplyInteractionTicks = 0;
            this.releaseWorkLease();
            this.activeAction = MachineCrewAction.IDLE;
            this.workTicks = 0;
            this.soldier.adaptiveNavigation().stop();
            return true;
        }
        this.failedSupplyChests.clear();
        return false;
    }

    private void tickMachineSupplyRun() {
        class_3218 world;
        block10: {
            block9: {
                class_1937 class_19372 = this.soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block9;
                world = (class_3218)class_19372;
                if (this.supplyPlan != null) break block10;
            }
            this.releaseSupplyReservation();
            return;
        }
        NpcMachineSupplyReservationManager.refresh(world, this.supplyPlan, this.soldier.method_5667());
        class_2338 chest = this.supplyPlan.chestPos();
        class_243 target = class_243.method_24953((class_2382)chest);
        this.soldier.method_5988().method_6230(target.field_1352, target.field_1351, target.field_1350, 30.0f, 30.0f);
        double distanceSq = this.soldier.method_5649(target.field_1352, target.field_1351, target.field_1350);
        if (distanceSq <= 6.002500000000001) {
            this.soldier.adaptiveNavigation().stop();
            if (++this.supplyInteractionTicks < 12) {
                return;
            }
            if (this.collectReservedMachineResources(world, this.supplyPlan)) {
                this.releaseSupplyReservation();
                this.activeSupplyNeeds = List.of();
                this.failedSupplyChests.clear();
                this.supplyRetryCooldown = 20;
                return;
            }
            this.failCurrentSupplyChest();
            return;
        }
        this.soldier.adaptiveNavigation().tickToPoint(target, this.speed);
        class_243 now = this.soldier.method_19538();
        if (this.supplyLastPosition != null && now.method_1025(this.supplyLastPosition) < 0.0144) {
            ++this.supplyStalledTicks;
        } else {
            this.supplyStalledTicks = 0;
            this.supplyLastPosition = now;
        }
        if (this.supplyStalledTicks >= 50) {
            this.failCurrentSupplyChest();
        }
    }

    private boolean collectReservedMachineResources(class_3218 world, NpcMachineSupplyPlan plan) {
        MedievalSoldierEntity resources = this.soldier;
        class_1263 inventory = NpcMachineSupplyInventoryAccess.resolve(world, plan.chestPos());
        if (inventory == null) {
            return false;
        }
        for (Map.Entry<class_1792, Integer> entry : plan.reservedItems().entrySet()) {
            if (MachineCrewAssignmentGoal.countInventoryItem(inventory, entry.getKey()) >= entry.getValue()) continue;
            return false;
        }
        boolean movedAny = false;
        for (Map.Entry<class_1792, Integer> entry : plan.reservedItems().entrySet()) {
            int requested = entry.getValue();
            int accepted = resources.medievalMachinery$storeMachineResource(entry.getKey(), requested);
            if (accepted <= 0) continue;
            MachineCrewAssignmentGoal.removeInventoryItem(inventory, entry.getKey(), accepted);
            movedAny = true;
        }
        inventory.method_5431();
        return movedAny;
    }

    private static int countInventoryItem(class_1263 inventory, class_1792 item) {
        int count = 0;
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            class_1799 stack = inventory.method_5438(slot);
            if (stack.method_7960() || !stack.method_31574(item)) continue;
            count += stack.method_7947();
        }
        return count;
    }

    private static void removeInventoryItem(class_1263 inventory, class_1792 item, int count) {
        int remaining = count;
        for (int slot = 0; slot < inventory.method_5439() && remaining > 0; ++slot) {
            class_1799 stack = inventory.method_5438(slot);
            if (stack.method_7960() || !stack.method_31574(item)) continue;
            int take = Math.min(remaining, stack.method_7947());
            stack.method_7934(take);
            remaining -= take;
            if (!stack.method_7960()) continue;
            inventory.method_5447(slot, class_1799.field_8037);
        }
    }

    private void failCurrentSupplyChest() {
        List<MachineCrewSupplyNeed> retryNeeds = this.activeSupplyNeeds;
        if (this.supplyPlan != null) {
            this.failedSupplyChests.add(this.supplyPlan.chestPos());
        }
        this.releaseSupplyReservation();
        if (!retryNeeds.isEmpty() && this.tryBeginMachineSupplyRun(retryNeeds)) {
            return;
        }
        this.activeSupplyNeeds = List.of();
        this.failedSupplyChests.clear();
        this.supplyRetryCooldown = 200;
    }

    private void releaseSupplyReservation() {
        class_1937 class_19372;
        if (this.supplyPlan != null && (class_19372 = this.soldier.method_37908()) instanceof class_3218) {
            class_3218 world = (class_3218)class_19372;
            NpcMachineSupplyReservationManager.release(world, this.supplyPlan, this.soldier.method_5667());
        }
        this.supplyPlan = null;
        this.supplyLastPosition = null;
        this.supplyStalledTicks = 0;
        this.supplyInteractionTicks = 0;
    }

    private void releaseWorkLease() {
        class_1937 class_19372;
        if (this.machineEntity != null && (class_19372 = this.soldier.method_37908()) instanceof class_3218) {
            class_3218 world = (class_3218)class_19372;
            NpcMachineCrewCoordinator.release(world, this.machineEntity.method_5667(), this.soldier.method_5667());
        }
    }
}

