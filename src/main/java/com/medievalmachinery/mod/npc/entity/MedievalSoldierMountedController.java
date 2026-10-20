/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1309
 *  net.minecraft.class_1322
 *  net.minecraft.class_1322$class_1323
 *  net.minecraft.class_1324
 *  net.minecraft.class_1498
 *  net.minecraft.class_1657
 *  net.minecraft.class_1764
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3532
 *  net.minecraft.class_3730
 *  net.minecraft.class_5134
 *  net.minecraft.class_5425
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRangedVisualState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFollowPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFormationCombatPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMountedPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMountedState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierRangedFirePolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierRideablePolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierSupplyState;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierMountedNavigation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1322;
import net.minecraft.class_1324;
import net.minecraft.class_1498;
import net.minecraft.class_1657;
import net.minecraft.class_1764;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3532;
import net.minecraft.class_3730;
import net.minecraft.class_5134;
import net.minecraft.class_5425;

final class MedievalSoldierMountedController {
    private static final double ARCHER_MIN_RANGE = 8.0;
    private static final double ARCHER_FIRE_RANGE = 22.0;
    private static final double KNIGHT_HIT_RANGE = 3.1;
    private MedievalSoldierMountedNavigation activeNavigation;
    private UUID activeHorseId;
    private static final UUID MOUNTED_SPEED_BOOST_ID = UUID.fromString("0f977918-0e88-4fd6-bf72-736bf0d90ea1");
    private static final double MOUNTED_SPEED_BOOST = 0.225;
    private class_1498 speedBoostHorse;

    MedievalSoldierMountedController() {
    }

    boolean isMountedRole(MedievalSoldierEntity soldier) {
        return soldier != null && MedievalSoldierMountedPolicy.isMountedRole(soldier.getNpcRole());
    }

    boolean isActivelyMounted(MedievalSoldierEntity soldier) {
        return MedievalSoldierMountedPolicy.horse(soldier) != null;
    }

    void createRecruitMount(MedievalSoldierEntity soldier) {
        class_3218 world;
        block6: {
            block5: {
                class_1937 class_19372;
                if (soldier == null || !((class_19372 = soldier.method_37908()) instanceof class_3218)) break block5;
                world = (class_3218)class_19372;
                if (this.isMountedRole(soldier) && !soldier.method_5765()) break block6;
            }
            return;
        }
        class_1498 horse = (class_1498)class_1299.field_6139.method_5883((class_1937)world);
        if (horse == null) {
            return;
        }
        horse.method_5808(soldier.method_23317() + 1.0, soldier.method_23318(), soldier.method_23321(), soldier.method_36454(), 0.0f);
        horse.method_5943((class_5425)world, world.method_8404(horse.method_24515()), class_3730.field_16471, null, null);
        horse.method_5665((class_2561)class_2561.method_43470((String)(soldier.method_5476().getString() + " - Pferd")));
        horse.method_6766(true);
        horse.method_6732(soldier.getNpcIdentity().getOwnerId());
        horse.method_6576(null);
        horse.method_5780("medieval_machinery_mount");
        horse.method_5780("medieval_machinery_mount_" + soldier.method_5667().toString());
        if (world.method_8649((class_1297)horse)) {
            soldier.method_5873((class_1297)horse, true);
        }
    }

    boolean commandMountNearby(MedievalSoldierEntity soldier) {
        if (soldier == null || soldier.method_37908().field_9236 || soldier.method_5765()) {
            return false;
        }
        class_1297 candidate = this.selectMountCandidate(soldier, null);
        if (candidate == null) {
            return false;
        }
        soldier.stopAdaptiveNavigation();
        if (soldier.method_5858(candidate) <= 6.760000000000001 && soldier.method_5873(candidate, false)) {
            soldier.mountCommandState().clear();
            return true;
        }
        soldier.mountCommandState().begin(candidate.method_5667());
        return true;
    }

    private class_1297 selectMountCandidate(MedievalSoldierEntity soldier, UUID excluded) {
        class_238 area = soldier.method_5829().method_1009(32.0, 8.0, 32.0);
        ArrayList<class_1297> candidates = new ArrayList<class_1297>();
        for (class_1297 candidate2 : soldier.method_37908().method_8333((class_1297)soldier, area, class_1297::method_5805)) {
            if (excluded != null && excluded.equals(candidate2.method_5667()) || !MedievalSoldierRideablePolicy.isSupportedCandidate(soldier, candidate2)) continue;
            candidates.add(candidate2);
        }
        candidates.sort(Comparator.comparingDouble(candidate -> MedievalSoldierRideablePolicy.selectionScore(soldier, candidate)));
        return candidates.isEmpty() ? null : (class_1297)candidates.get(0);
    }

    void retryOrCancelMountCommand(MedievalSoldierEntity soldier) {
        UUID previous = soldier.mountCommandState().targetVehicleId();
        class_1297 replacement = this.selectMountCandidate(soldier, previous);
        if (replacement == null) {
            soldier.mountCommandState().clear();
            soldier.stopAdaptiveNavigation();
        } else {
            soldier.mountCommandState().begin(replacement.method_5667());
        }
    }

    boolean commandDismount(MedievalSoldierEntity soldier) {
        if (soldier == null || soldier.method_37908().field_9236) {
            return false;
        }
        soldier.mountCommandState().clear();
        soldier.stopAdaptiveNavigation();
        if (!soldier.method_5765()) {
            return false;
        }
        soldier.mountedState().cancelRangedPreparation(soldier);
        soldier.mountedState().resetCombat();
        soldier.method_5848();
        return true;
    }

    void tick(MedievalSoldierEntity soldier) {
        if (soldier == null || soldier.method_37908().field_9236 || !this.isMountedRole(soldier)) {
            return;
        }
        MedievalSoldierMountedState state = soldier.mountedState();
        NpcOrderType previousOrder = state.lastOrder;
        state.tick();
        if (previousOrder != null && previousOrder != soldier.getOrderType() && state.rangedPreparing) {
            state.cancelRangedPreparation(soldier);
        }
        state.onOrder(soldier.getOrderType());
        class_1498 horse = MedievalSoldierMountedPolicy.horse(soldier);
        if (horse == null) {
            this.restoreMountedHorseSpeed();
            return;
        }
        this.applyComparisonMountedHorseSpeed(horse);
        soldier.stopAdaptiveNavigation();
        MedievalSoldierMountedNavigation navigation = this.navigationFor(horse);
        navigation.tick();
        if (soldier.getOrderType() == NpcOrderType.PATROL && soldier.hasPatrolRoute()) {
            class_2338 patrolWaypoint = soldier.goalPatrolState().nextWaypointPosition();
            if (patrolWaypoint != null) {
                if (state.waitingForPatrolChunk) {
                    if (!soldier.isStaggeredAiTick(20) || !soldier.method_37908().method_22340(patrolWaypoint)) {
                        navigation.stop();
                        state.clearMovementRecovery();
                        return;
                    }
                    state.waitingForPatrolChunk = false;
                } else if (!soldier.method_37908().method_22340(patrolWaypoint)) {
                    state.waitingForPatrolChunk = true;
                    navigation.stop();
                    state.clearMovementRecovery();
                    return;
                }
            }
        } else {
            state.waitingForPatrolChunk = false;
        }
        if (MedievalSoldierMountedPolicy.recoveryAllowed(state) && this.tickMountedRecovery(horse, navigation, state)) {
            return;
        }
        if (this.tickMountedSupply(soldier, horse, navigation)) {
            return;
        }
        class_243 assemblyTarget = soldier.goalFormationState().getAssemblyTarget();
        if (assemblyTarget != null && soldier.goalFormationState().getAssemblyTicks() > 0) {
            double assemblyArrival;
            double distanceSq = horse.method_19538().method_1025(assemblyTarget);
            double d = assemblyArrival = soldier.getFormation() == NpcFormation.FREE ? 2.2 : 1.65;
            if (distanceSq <= assemblyArrival * assemblyArrival) {
                navigation.stop();
                soldier.formationFinishAssembly(assemblyTarget);
            } else {
                double distance = Math.sqrt(distanceSq);
                navigation.moveTo(assemblyTarget, MedievalSoldierMountedPolicy.formationApproachSpeed(soldier, distance, MedievalSoldierMountedController.horizontalSpeed(horse)));
            }
            return;
        }
        class_1309 target = soldier.method_5968();
        if (!(target != null && target.method_5805() || state.passThroughTicks <= 0 || state.latchedPassThroughPoint == null)) {
            navigation.moveTo(state.latchedPassThroughPoint, MedievalSoldierMountedPolicy.knightChargeSpeed(Math.sqrt(horse.method_19538().method_1025(state.latchedPassThroughPoint))));
            return;
        }
        this.applyOrientation(soldier, horse, target);
        if (target != null && target.method_5805()) {
            if (!this.mountedCombatMayOwnMovement(soldier, horse, target)) {
                state.cancelRangedPreparation(soldier);
                this.tickMountedOrder(soldier, horse, navigation, state);
                return;
            }
            if (MedievalSoldierMountedPolicy.isMountedArcher(soldier)) {
                this.tickMountedArcher(soldier, horse, target, navigation, state);
            } else {
                this.tickMountedKnight(soldier, horse, target, navigation, state);
            }
            return;
        }
        this.tickMountedOrder(soldier, horse, navigation, state);
    }

    private void applyOrientation(MedievalSoldierEntity soldier, class_1498 horse, class_1309 target) {
        if (target != null && target.method_5805()) {
            soldier.method_5988().method_6226((class_1297)target, 30.0f, 25.0f);
            return;
        }
        float delta = class_3532.method_15393((float)(horse.method_36454() - soldier.method_36454()));
        soldier.method_36456(soldier.method_36454() + class_3532.method_15363((float)delta, (float)-12.0f, (float)12.0f));
        soldier.field_6283 = soldier.method_36454();
        soldier.field_6241 = soldier.method_36454();
    }

    private boolean mountedCombatMayOwnMovement(MedievalSoldierEntity soldier, class_1498 horse, class_1309 target) {
        if (soldier == null || horse == null || target == null) {
            return false;
        }
        if (soldier.isCommandedCombatTarget(target)) {
            return true;
        }
        NpcOrderType order = soldier.getOrderType();
        if (order == NpcOrderType.MOVE_TO_POSITION) {
            return false;
        }
        if (MedievalSoldierFormationCombatPolicy.isHeldFormation(soldier)) {
            if (MedievalSoldierMountedPolicy.isMountedKnight(soldier)) {
                return MedievalSoldierFormationCombatPolicy.mayMeleeAdvance(soldier, target);
            }
            if (MedievalSoldierMountedPolicy.isMountedArcher(soldier)) {
                class_243 hold = soldier.mountedHoldPosition();
                if (hold != null && horse.method_19538().method_1025(hold) > 16.0) {
                    return false;
                }
                double leash = MedievalSoldierMountedPolicy.followCombatLeash();
                return hold == null || hold.method_1025(target.method_19538()) <= leash * leash;
            }
        }
        if (order == NpcOrderType.FOLLOW) {
            class_3218 serverWorld;
            class_1937 class_19372;
            class_243 anchor = soldier.getNpcIdentity().getOwnerId() != null && (class_19372 = soldier.method_37908()) instanceof class_3218 && (serverWorld = (class_3218)class_19372).method_8503().method_3760().method_14602(soldier.getNpcIdentity().getOwnerId()) != null ? serverWorld.method_8503().method_3760().method_14602(soldier.getNpcIdentity().getOwnerId()).method_19538() : horse.method_19538();
            double leash = MedievalSoldierMountedPolicy.followCombatLeash();
            return anchor.method_1025(target.method_19538()) <= leash * leash;
        }
        return true;
    }

    private void tickMountedArcher(MedievalSoldierEntity soldier, class_1498 horse, class_1309 target, MedievalSoldierMountedNavigation navigation, MedievalSoldierMountedState state) {
        double anchorDistanceSq;
        class_243 heldAnchor;
        double distanceSq = horse.method_5858((class_1297)target);
        double minSq = 64.0;
        double fireSq = 484.0;
        boolean heldFormation = MedievalSoldierFormationCombatPolicy.isHeldFormation(soldier);
        class_243 class_2432 = heldAnchor = heldFormation ? MedievalSoldierFormationCombatPolicy.tacticalAnchor(soldier) : null;
        if (heldAnchor != null && (anchorDistanceSq = horse.method_19538().method_1025(heldAnchor)) > 5.0625) {
            state.cancelRangedPreparation(soldier);
            navigation.moveTo(heldAnchor, MedievalSoldierMountedPolicy.formationApproachSpeed(soldier, Math.sqrt(anchorDistanceSq), MedievalSoldierMountedController.horizontalSpeed(horse)));
            return;
        }
        if (distanceSq < 30.25 && soldier.isHoldingRangedWeapon() && soldier.switchRangedUnitToCloseCombatWeapon()) {
            state.cancelRangedPreparation(soldier);
        }
        if (soldier.goalCombatState().rangedCloseCombatWeaponActive) {
            state.cancelRangedPreparation(soldier);
            soldier.method_5988().method_6226((class_1297)target, 35.0f, 35.0f);
            if (distanceSq <= 10.240000000000002 && state.attackCooldown <= 0) {
                soldier.method_6121((class_1297)target);
                state.attackCooldown = 16;
                navigation.moveTo(MedievalSoldierMountedPolicy.retreatPoint(horse, target), 1.05);
            } else if (distanceSq > 10.240000000000002) {
                navigation.moveTo(target.method_19538(), 1.05);
            }
            return;
        }
        if (!soldier.isHoldingRangedWeapon()) {
            state.cancelRangedPreparation(soldier);
            return;
        }
        if (heldFormation && heldAnchor != null) {
            state.latchedOrbitPoint = null;
            navigation.stop();
        } else if (MedievalSoldierMountedPolicy.archerShouldRetreat(distanceSq)) {
            navigation.moveTo(MedievalSoldierMountedPolicy.retreatPoint(horse, target), 1.1);
        } else if (distanceSq > fireSq) {
            navigation.moveTo(target.method_19538(), 1.05);
        } else if (state.repathCooldown <= 0 || state.latchedOrbitPoint == null) {
            state.latchedOrbitPoint = MedievalSoldierMountedPolicy.archerOrbitPoint(horse, target, state.orbitSign);
            navigation.moveTo(state.latchedOrbitPoint, 1.05);
            state.repathCooldown = MedievalSoldierMountedPolicy.archerOrbitRepathTicks();
        } else if (!state.rangedPreparing && horse.method_19538().method_1025(state.latchedOrbitPoint) > 6.25 && navigation.isIdle()) {
            navigation.moveTo(state.latchedOrbitPoint, 1.05);
        }
        soldier.method_5988().method_6226((class_1297)target, 35.0f, 35.0f);
        if (state.rangedPreparing) {
            boolean canSee;
            if (distanceSq >= minSq && distanceSq <= fireSq) {
                navigation.stop();
            }
            state.rangedSightLossTicks = !(canSee = soldier.mountedCanSee(target)) ? ++state.rangedSightLossTicks : 0;
            if (distanceSq > fireSq || state.rangedSightLossTicks > MedievalSoldierMountedPolicy.archerSightLossGraceTicks()) {
                state.cancelRangedPreparation(soldier);
                state.attackCooldown = 6;
                return;
            }
            ++state.rangedPreparationTicks;
            int loadTicks = state.rangedCrossbow ? MedievalSoldierRangedFirePolicy.crossbowLoadTicks(true) : 0;
            int chargeTicks = state.rangedCrossbow ? MedievalSoldierRangedFirePolicy.crossbowChargeTicks(true) : MedievalSoldierRangedFirePolicy.bowDrawTicks(true);
            int aimTicks = state.rangedCrossbow ? MedievalSoldierRangedFirePolicy.crossbowAimTicks(true) : 0;
            int required = loadTicks + chargeTicks + aimTicks;
            if (state.rangedCrossbow && state.rangedPreparationTicks == loadTicks) {
                soldier.markCrossbowBoltInsertedForVisual();
                soldier.setRangedVisualState(NpcRangedVisualState.PREPARE);
                soldier.method_6019(class_1268.field_5808);
            }
            if (state.rangedCrossbow && state.rangedPreparationTicks == loadTicks + chargeTicks) {
                soldier.stopRangedWeaponUse();
                soldier.setRangedVisualState(NpcRangedVisualState.AIM);
            }
            if (state.rangedPreparationTicks < required) {
                return;
            }
            if (soldier.hasFriendlyInLineOfFire(target)) {
                state.cancelRangedPreparation(soldier);
                state.attackCooldown = MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(soldier);
                return;
            }
            soldier.stopRangedWeaponUse();
            soldier.holdRangedReleaseVisual(state.rangedCrossbow, true);
            soldier.fireStandardArrowAt(target);
            if (state.rangedCrossbow) {
                soldier.markCrossbowUnloadedAfterShot();
            }
            state.finishRangedPreparation();
            state.attackCooldown = MedievalSoldierRangedFirePolicy.postShotCooldown(state.rangedCrossbow, true);
            return;
        }
        if (distanceSq <= fireSq && state.attackCooldown <= 0 && soldier.mountedCanSee(target) && !soldier.hasFriendlyInLineOfFire(target)) {
            if (!MedievalSoldierMountedPolicy.archerStableToAim(horse, heldFormation)) {
                navigation.stop();
                return;
            }
            state.rangedPreparing = true;
            state.rangedPreparationTicks = 0;
            state.rangedCrossbow = soldier.method_6047().method_7909() instanceof class_1764;
            int visualTicks = state.rangedCrossbow ? MedievalSoldierRangedFirePolicy.crossbowLoadTicks(true) + MedievalSoldierRangedFirePolicy.crossbowChargeTicks(true) + MedievalSoldierRangedFirePolicy.crossbowAimTicks(true) + 12 : MedievalSoldierRangedFirePolicy.bowDrawTicks(true) + 10;
            soldier.beginRangedVisual(state.rangedCrossbow ? NpcRangedVisualState.LOAD : NpcRangedVisualState.PREPARE, visualTicks);
            if (!state.rangedCrossbow) {
                soldier.method_6019(class_1268.field_5808);
            }
        }
    }

    private void tickMountedKnight(MedievalSoldierEntity soldier, class_1498 horse, class_1309 target, MedievalSoldierMountedNavigation navigation, MedievalSoldierMountedState state) {
        class_243 hold;
        double distanceSq = horse.method_5858((class_1297)target);
        soldier.method_5988().method_6226((class_1297)target, 35.0f, 35.0f);
        if (state.passThroughTicks > 0) {
            double throughArrival;
            double throughDistanceSq;
            if (state.latchedPassThroughPoint == null) {
                state.latchedPassThroughPoint = MedievalSoldierMountedPolicy.knightPassThroughPoint(horse, target);
            }
            if ((throughDistanceSq = horse.method_19538().method_1025(state.latchedPassThroughPoint)) <= (throughArrival = MedievalSoldierMountedPolicy.passThroughArrivalRadius()) * throughArrival) {
                navigation.stop();
                state.passThroughTicks = 0;
                state.latchedPassThroughPoint = null;
                state.postPassTurnDelayTicks = MedievalSoldierMountedPolicy.postPassTurnDelayTicks();
            } else {
                navigation.moveTo(state.latchedPassThroughPoint, MedievalSoldierMountedPolicy.knightChargeSpeed(Math.sqrt(throughDistanceSq)));
            }
            return;
        }
        state.latchedPassThroughPoint = null;
        if (state.postPassTurnDelayTicks > 0) {
            navigation.stop();
            horse.method_18799(horse.method_18798().method_18805(0.82, 1.0, 0.82));
            return;
        }
        if (soldier.getOrderType() == NpcOrderType.HOLD_POSITION && soldier.getFormation() != NpcFormation.FREE && (hold = soldier.mountedHoldPosition()) != null && horse.method_19538().method_1025(hold) > 56.25 && distanceSq > 9.610000000000001) {
            state.knightStagingPoint = null;
            state.knightStagingTicks = 0;
            navigation.moveTo(hold, MedievalSoldierMountedPolicy.formationApproachSpeed(soldier, Math.sqrt(horse.method_19538().method_1025(hold)), MedievalSoldierMountedController.horizontalSpeed(horse)));
            return;
        }
        if (distanceSq > MedievalSoldierMountedPolicy.knightReengageDistance() * MedievalSoldierMountedPolicy.knightReengageDistance() && state.chargeCooldown <= 0) {
            double stagingArrival;
            double stagingDistanceSq;
            if (state.knightStagingPoint == null || state.knightStagingTicks > MedievalSoldierMountedPolicy.knightStagingTimeoutTicks()) {
                class_243 candidate;
                if (state.knightStagingTicks > MedievalSoldierMountedPolicy.knightStagingTimeoutTicks()) {
                    state.recoveryTurnSign *= -1;
                }
                state.knightStagingPoint = MedievalSoldierMountedPolicy.stagingPointIsUsable(horse, candidate = MedievalSoldierMountedPolicy.knightStagingPoint(horse, target, state.recoveryTurnSign)) ? candidate : MedievalSoldierMountedPolicy.knightStagingPoint(horse, target, -state.recoveryTurnSign);
                state.knightStagingTicks = 0;
            }
            if (state.knightStagingPoint != null && (stagingDistanceSq = horse.method_19538().method_1025(state.knightStagingPoint)) > (stagingArrival = MedievalSoldierMountedPolicy.knightStagingArrivalRadius()) * stagingArrival) {
                navigation.moveTo(state.knightStagingPoint, MedievalSoldierMountedPolicy.knightStagingSpeed(Math.sqrt(stagingDistanceSq)));
                return;
            }
            state.knightStagingPoint = null;
            state.knightStagingTicks = 0;
            state.knightChargeTargetAnchor = target.method_19538();
            state.knightChargeDirection = MedievalSoldierMountedPolicy.chargeDirection(horse, target);
            state.knightChargeCommitTicks = MedievalSoldierMountedPolicy.knightChargeCommitTicks();
        } else if (distanceSq <= MedievalSoldierMountedPolicy.knightReengageDistance() * MedievalSoldierMountedPolicy.knightReengageDistance()) {
            state.knightStagingPoint = null;
            state.knightStagingTicks = 0;
        }
        if (state.knightChargeCommitTicks > 0 && !MedievalSoldierMountedPolicy.chargeCorridorStillValid(horse, target, state.knightChargeTargetAnchor, state.knightChargeDirection)) {
            state.knightChargeCommitTicks = 0;
            state.knightChargeTargetAnchor = null;
            state.knightChargeDirection = null;
            state.knightStagingPoint = MedievalSoldierMountedPolicy.knightStagingPoint(horse, target, -state.recoveryTurnSign);
            state.recoveryTurnSign *= -1;
            state.knightStagingTicks = 0;
            if (MedievalSoldierMountedPolicy.stagingPointIsUsable(horse, state.knightStagingPoint)) {
                navigation.moveTo(state.knightStagingPoint, 1.05);
                return;
            }
            state.knightStagingPoint = null;
        }
        if (distanceSq <= 42.25 && state.attackCooldown <= 0 && state.meleeWindupTicks <= 0) {
            soldier.beginMountedSpearWindupVisual();
            state.meleeWindupTicks = 10;
        }
        if (distanceSq <= 9.610000000000001 && state.attackCooldown <= 0 && MedievalSoldierMountedPolicy.knightHasMeaningfulChargeSpeed(horse) && MedievalSoldierMountedPolicy.knightChargeAligned(horse, target)) {
            soldier.method_6121((class_1297)target);
            state.attackCooldown = 18;
            state.chargeCooldown = 28;
            state.passThroughTicks = 12;
            state.knightStagingPoint = null;
            state.knightStagingTicks = 0;
            state.knightChargeTargetAnchor = null;
            state.knightChargeDirection = null;
            state.knightChargeCommitTicks = 0;
            state.latchedPassThroughPoint = MedievalSoldierMountedPolicy.knightPassThroughPoint(horse, target);
            navigation.moveTo(state.latchedPassThroughPoint, MedievalSoldierMountedPolicy.knightChargeSpeed(Math.sqrt(horse.method_19538().method_1025(state.latchedPassThroughPoint))));
            return;
        }
        if (state.chargeCooldown > 0 && distanceSq < MedievalSoldierMountedPolicy.knightReengageDistance() * MedievalSoldierMountedPolicy.knightReengageDistance()) {
            navigation.moveTo(MedievalSoldierMountedPolicy.retreatPoint(horse, target), 1.05);
            return;
        }
        navigation.moveTo(MedievalSoldierMountedPolicy.chargeApproachPoint(horse, target), MedievalSoldierMountedPolicy.knightChargeSpeed(Math.sqrt(distanceSq)));
    }

    private boolean tickMountedRecovery(class_1498 horse, MedievalSoldierMountedNavigation navigation, MedievalSoldierMountedState state) {
        if (state.recoveryTicks > 0 && state.latchedRecoveryPoint != null) {
            navigation.moveTo(state.latchedRecoveryPoint, 0.78);
            if (horse.method_19538().method_1025(state.latchedRecoveryPoint) <= 4.0) {
                state.recoveryTicks = 0;
                state.latchedRecoveryPoint = null;
                navigation.stop();
            }
            return true;
        }
        if (state.mountedNoProgressTicks <= 0) {
            double dz;
            double dx;
            class_243 now = horse.method_19538();
            if (state.mountedProgressAnchor != null && !navigation.isIdle() && (dx = now.field_1352 - state.mountedProgressAnchor.field_1352) * dx + (dz = now.field_1350 - state.mountedProgressAnchor.field_1350) * dz < 0.0484) {
                navigation.stop();
                state.recoveryTurnSign *= -1;
                class_243 candidate = MedievalSoldierMountedPolicy.mountedRecoveryPoint(horse, state.recoveryTurnSign);
                if (MedievalSoldierMountedPolicy.stagingPointIsUsable(horse, candidate)) {
                    state.latchedRecoveryPoint = candidate;
                    state.recoveryTicks = 14;
                    navigation.moveTo(candidate, 0.78);
                    state.mountedProgressAnchor = now;
                    state.mountedNoProgressTicks = 10;
                    return true;
                }
            }
            state.mountedProgressAnchor = now;
            state.mountedNoProgressTicks = 10;
        }
        return false;
    }

    private boolean tickMountedSupply(MedievalSoldierEntity soldier, class_1498 horse, MedievalSoldierMountedNavigation navigation) {
        MedievalSoldierSupplyState supply = soldier.goalSupplyState();
        if (!supply.ownsMovement()) {
            return false;
        }
        if (supply.request.isRun() && supply.chest != null) {
            if (supply.startDelay <= 0) {
                class_243 approach = soldier.findSupplyInteractionPoint(supply.chest);
                double distanceSq = horse.method_19538().method_1025(approach);
                if (distanceSq <= 2.7224999999999997) {
                    navigation.stop();
                } else {
                    navigation.moveTo(approach, MedievalSoldierMountedPolicy.supplyApproachSpeed(Math.sqrt(distanceSq)));
                }
            } else {
                navigation.stop();
            }
            return true;
        }
        if (supply.isReturningToOrigin() && supply.originPosition != null) {
            double distanceSq = horse.method_19538().method_1025(supply.originPosition);
            if (distanceSq <= 4.0) {
                navigation.stop();
            } else {
                navigation.moveTo(supply.originPosition, MedievalSoldierMountedPolicy.supplyApproachSpeed(Math.sqrt(distanceSq)));
            }
            return true;
        }
        return false;
    }

    private void tickMountedOrder(MedievalSoldierEntity soldier, class_1498 horse, MedievalSoldierMountedNavigation navigation, MedievalSoldierMountedState state) {
        NpcOrderType order = soldier.getOrderType();
        if (order == NpcOrderType.IDLE && soldier.getIdleRallyAnchor() != null) {
            class_243 rally = soldier.getIdleRallyAnchor();
            double rallyDistanceSq = horse.method_19538().method_1025(rally);
            double start = MedievalSoldierMountedPolicy.idleRallyStartRadius();
            double stop = MedievalSoldierMountedPolicy.idleRallyStopRadius();
            if (rallyDistanceSq > start * start) {
                navigation.moveTo(rally, MedievalSoldierMountedPolicy.idleRallyApproachSpeed(Math.sqrt(rallyDistanceSq)));
            } else if (rallyDistanceSq <= stop * stop) {
                navigation.stop();
            }
            return;
        }
        if (order == NpcOrderType.FOLLOW) {
            class_1937 class_19372;
            UUID ownerId = soldier.getNpcIdentity().getOwnerId();
            class_3222 owner = null;
            if (ownerId != null && (class_19372 = soldier.method_37908()) instanceof class_3218) {
                class_3218 serverWorld = (class_3218)class_19372;
                owner = serverWorld.method_8503().method_3760().method_14602(ownerId);
            }
            if (owner != null) {
                boolean ownerMovedEnough;
                MedievalSoldierFollowPolicy.updateLook(soldier, owner);
                double followRefreshDistance = MedievalSoldierMountedPolicy.followRefreshDistance(owner);
                boolean bl = ownerMovedEnough = state.cachedOwnerFollowAnchor == null || state.cachedOwnerFollowAnchor.method_1025(owner.method_19538()) > followRefreshDistance * followRefreshDistance;
                if (state.cachedFollowPoint == null || state.followRefreshTicks <= 0 || ownerMovedEnough) {
                    class_243 requestedFollowPoint = MedievalSoldierMountedPolicy.mountedFollowLookAhead(soldier, owner, MedievalSoldierFollowPolicy.desiredPoint(soldier, (class_1657)owner));
                    state.cachedFollowPoint = MedievalSoldierMountedPolicy.smoothFollowPoint(state.cachedFollowPoint, requestedFollowPoint);
                    state.cachedOwnerFollowAnchor = owner.method_19538();
                    state.followRefreshTicks = MedievalSoldierMountedPolicy.followRefreshTicks(owner);
                }
                class_243 followPoint = state.cachedFollowPoint;
                double pointDistance = horse.method_19538().method_1022(followPoint);
                double followDistance = soldier.getFormation() == NpcFormation.FREE ? horse.method_19538().method_1022(owner.method_19538()) : pointDistance;
                double start = MedievalSoldierFollowPolicy.startDistance(soldier);
                double stop = MedievalSoldierFollowPolicy.stopDistance(soldier);
                if (followDistance > start) {
                    navigation.moveTo(followPoint, MedievalSoldierMountedPolicy.mountedFollowSpeed(soldier, pointDistance, MedievalSoldierMountedController.horizontalSpeed(horse)));
                } else if (followDistance <= stop) {
                    navigation.stop();
                } else if (navigation.isIdle()) {
                    navigation.stop();
                }
            } else {
                navigation.stop();
            }
            return;
        }
        if (order == NpcOrderType.MOVE_TO_POSITION) {
            class_2338 destination = soldier.getCommandDestination();
            if (destination == null) {
                navigation.stop();
                return;
            }
            if (state.latchedMoveDestination == null || !state.latchedMoveDestination.equals((Object)destination) || state.latchedMovePoint == null) {
                state.latchedMoveDestination = destination.method_10062();
                state.latchedMovePoint = class_243.method_24955((class_2382)destination.method_10084());
            }
            class_243 target = state.latchedMovePoint;
            double arrivalRadius = MedievalSoldierMountedPolicy.moveArrivalRadius(soldier);
            double targetDistanceSq = horse.method_19538().method_1025(target);
            if (targetDistanceSq <= arrivalRadius * arrivalRadius) {
                navigation.stop();
                ++state.moveArrivalSettleTicks;
                if (state.moveArrivalSettleTicks >= MedievalSoldierMountedPolicy.mountedMoveSettleTicks(soldier)) {
                    soldier.goalCompleteMoveAt(destination.method_10084());
                    state.latchedMoveDestination = null;
                    state.latchedMovePoint = null;
                    state.moveArrivalSettleTicks = 0;
                }
            } else {
                state.moveArrivalSettleTicks = 0;
                navigation.moveTo(target, MedievalSoldierMountedPolicy.formationApproachSpeed(soldier, Math.sqrt(targetDistanceSq), MedievalSoldierMountedController.horizontalSpeed(horse)));
            }
            return;
        }
        if (order == NpcOrderType.HOLD_POSITION) {
            class_243 hold = soldier.mountedHoldPosition();
            double holdRadius = MedievalSoldierMountedPolicy.holdRadius(soldier);
            if (hold != null && horse.method_19538().method_1025(hold) > holdRadius * holdRadius) {
                navigation.moveTo(hold, MedievalSoldierMountedPolicy.formationApproachSpeed(soldier, Math.sqrt(horse.method_19538().method_1025(hold)), MedievalSoldierMountedController.horizontalSpeed(horse)));
            } else {
                navigation.stop();
            }
            return;
        }
        if (order == NpcOrderType.PATROL && soldier.hasPatrolRoute()) {
            class_2338 waypoint = soldier.goalPatrolState().nextWaypointPosition();
            if (waypoint == null) {
                navigation.stop();
                return;
            }
            class_243 target = class_243.method_24955((class_2382)waypoint.method_10084());
            double patrolDistanceSq = horse.method_19538().method_1025(target);
            if (patrolDistanceSq <= 9.0) {
                navigation.stop();
                ++state.patrolArrivalSettleTicks;
                if (state.patrolArrivalSettleTicks >= MedievalSoldierMountedPolicy.patrolSettleTicks()) {
                    soldier.goalPatrolState().markWaypointReached();
                    soldier.goalMarkRegistryDirty();
                    state.repathCooldown = 0;
                    state.patrolArrivalSettleTicks = 0;
                }
            } else if (state.repathCooldown <= 0) {
                state.patrolArrivalSettleTicks = 0;
                navigation.moveTo(target, MedievalSoldierMountedPolicy.patrolSpeed(Math.sqrt(patrolDistanceSq)));
                state.repathCooldown = MedievalSoldierMountedPolicy.patrolRepathTicks();
            }
            return;
        }
        if (order == NpcOrderType.RETURN_TO_GARRISON) {
            class_243 home = soldier.resolveReturnToGarrisonDestination();
            if (home == null) {
                navigation.stop();
                return;
            }
            double homeDistanceSq = horse.method_19538().method_1025(home);
            if (homeDistanceSq > 9.0) {
                navigation.moveTo(home, MedievalSoldierMountedPolicy.formationApproachSpeed(soldier, Math.sqrt(homeDistanceSq), MedievalSoldierMountedController.horizontalSpeed(horse)));
            } else {
                navigation.stop();
                soldier.setOrderType(NpcOrderType.HOLD_POSITION);
            }
        }
    }

    private void applyComparisonMountedHorseSpeed(class_1498 horse) {
        if (horse == null) {
            return;
        }
        if (this.speedBoostHorse != horse) {
            this.restoreMountedHorseSpeed();
            this.speedBoostHorse = horse;
            class_1324 speed = horse.method_5996(class_5134.field_23719);
            if (speed != null) {
                double base;
                for (base = speed.method_6201(); base > 0.337501; base -= 0.225) {
                }
                if (Math.abs(base - speed.method_6201()) > 1.0E-6) {
                    speed.method_6192(Math.max(0.1125, base));
                }
                if (speed.method_6199(MOUNTED_SPEED_BOOST_ID) == null) {
                    speed.method_26835(new class_1322(MOUNTED_SPEED_BOOST_ID, "Medieval Machinery mounted horse speed", 0.225, class_1322.class_1323.field_6328));
                }
            }
        }
    }

    private void restoreMountedHorseSpeed() {
        class_1324 speed;
        if (this.speedBoostHorse != null && (speed = this.speedBoostHorse.method_5996(class_5134.field_23719)) != null && speed.method_6199(MOUNTED_SPEED_BOOST_ID) != null) {
            speed.method_6200(MOUNTED_SPEED_BOOST_ID);
        }
        this.speedBoostHorse = null;
    }

    private MedievalSoldierMountedNavigation navigationFor(class_1498 horse) {
        if (this.activeNavigation == null || this.activeHorseId == null || !this.activeHorseId.equals(horse.method_5667())) {
            this.activeHorseId = horse.method_5667();
            this.activeNavigation = new MedievalSoldierMountedNavigation(horse);
        }
        return this.activeNavigation;
    }

    private static boolean sameFriendlyMountedElement(MedievalSoldierEntity a, MedievalSoldierEntity b) {
        if (a == null || b == null) {
            return false;
        }
        UUID castleA = a.getNpcIdentity().getCastleId();
        UUID castleB = b.getNpcIdentity().getCastleId();
        if (castleA != null && castleA.equals(castleB)) {
            return true;
        }
        UUID kingdomA = a.getNpcIdentity().getKingdomId();
        UUID kingdomB = b.getNpcIdentity().getKingdomId();
        if (kingdomA != null && kingdomA.equals(kingdomB)) {
            return true;
        }
        UUID ownerA = a.getNpcIdentity().getOwnerId();
        return ownerA != null && ownerA.equals(b.getNpcIdentity().getOwnerId());
    }

    private static boolean isPeriodicPhase(class_1498 horse, int interval) {
        if (horse == null || interval <= 1) {
            return true;
        }
        int phase = Math.floorMod(horse.method_5667().hashCode(), interval);
        return Math.floorMod(horse.field_6012, interval) == phase;
    }

    private static double horizontalSpeed(class_1498 horse) {
        if (horse == null) {
            return 0.0;
        }
        class_243 v = horse.method_18798();
        return Math.sqrt(v.field_1352 * v.field_1352 + v.field_1350 * v.field_1350);
    }
}

