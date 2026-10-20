/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1498
 *  net.minecraft.class_1922
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_243
 *  net.minecraft.class_3222
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMountedState;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1498;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_243;
import net.minecraft.class_3222;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierMountedPolicy {
    private MedievalSoldierMountedPolicy() {
    }

    static boolean isMountedRole(NpcRole role) {
        return role == NpcRole.MOUNTED_ARCHER || role == NpcRole.MOUNTED_KNIGHT;
    }

    static double formationFootprint(MedievalSoldierEntity soldier) {
        return soldier != null && MedievalSoldierMountedPolicy.isMountedRole(soldier.getNpcRole()) ? 1.45 : 1.0;
    }

    static boolean isMountedArcher(MedievalSoldierEntity soldier) {
        return soldier != null && soldier.getNpcRole() == NpcRole.MOUNTED_ARCHER;
    }

    static boolean isMountedKnight(MedievalSoldierEntity soldier) {
        return soldier != null && soldier.getNpcRole() == NpcRole.MOUNTED_KNIGHT;
    }

    @Nullable
    static class_1498 horse(MedievalSoldierEntity soldier) {
        class_1498 horse;
        class_1297 class_12972;
        return soldier != null && (class_12972 = soldier.method_5854()) instanceof class_1498 && (horse = (class_1498)class_12972).method_5805() ? horse : null;
    }

    static class_243 archerOrbitPoint(class_1498 horse, class_1309 target, int sign) {
        class_243 away = horse.method_19538().method_1020(target.method_19538());
        class_243 horizontal = new class_243(away.field_1352, 0.0, away.field_1350);
        if (horizontal.method_1027() < 0.001) {
            horizontal = new class_243(1.0, 0.0, 0.0);
        }
        horizontal = horizontal.method_1029();
        class_243 tangent = new class_243(-horizontal.field_1350 * (double)sign, 0.0, horizontal.field_1352 * (double)sign);
        return target.method_19538().method_1019(horizontal.method_1021(MedievalSoldierMountedPolicy.archerOrbitRadius(target))).method_1019(tangent.method_1021(5.0));
    }

    static class_243 retreatPoint(class_1498 horse, class_1309 target) {
        class_243 away = horse.method_19538().method_1020(target.method_19538());
        class_243 horizontal = new class_243(away.field_1352, 0.0, away.field_1350);
        if (horizontal.method_1027() < 0.001) {
            horizontal = new class_243(1.0, 0.0, 0.0);
        }
        return horse.method_19538().method_1019(horizontal.method_1029().method_1021(8.0));
    }

    static class_243 knightStagingPoint(class_1498 horse, class_1309 target, int turnSign) {
        class_243 fromTarget = horse.method_19538().method_1020(target.method_19538());
        class_243 horizontal = new class_243(fromTarget.field_1352, 0.0, fromTarget.field_1350);
        if (horizontal.method_1027() < 0.001) {
            horizontal = new class_243(1.0, 0.0, 0.0);
        }
        horizontal = horizontal.method_1029();
        class_243 tangent = new class_243(-horizontal.field_1350 * (double)turnSign, 0.0, horizontal.field_1352 * (double)turnSign);
        return target.method_19538().method_1019(horizontal.method_1021(9.5)).method_1019(tangent.method_1021(2.5));
    }

    static boolean stagingPointIsUsable(class_1498 horse, class_243 point) {
        if (horse == null || point == null) {
            return false;
        }
        class_2338 feet = class_2338.method_49638((class_2374)point);
        return !horse.method_37908().method_8320(feet.method_10074()).method_26220((class_1922)horse.method_37908(), feet.method_10074()).method_1110() && horse.method_37908().method_8320(feet).method_26220((class_1922)horse.method_37908(), feet).method_1110() && horse.method_37908().method_8320(feet.method_10084()).method_26220((class_1922)horse.method_37908(), feet.method_10084()).method_1110();
    }

    static class_243 chargeApproachPoint(class_1498 horse, class_1309 target) {
        class_243 velocity = target.method_18798();
        class_243 horizontalVelocity = new class_243(velocity.field_1352, 0.0, velocity.field_1350);
        horizontalVelocity = horizontalVelocity.method_1027() > 0.04000000000000001 ? horizontalVelocity.method_1029().method_1021(0.9) : horizontalVelocity.method_1021(4.5);
        class_243 predicted = target.method_19538().method_1019(horizontalVelocity);
        class_243 toTarget = predicted.method_1020(horse.method_19538());
        class_243 horizontal = new class_243(toTarget.field_1352, 0.0, toTarget.field_1350);
        if (horizontal.method_1027() < 0.001) {
            return predicted;
        }
        return predicted.method_1020(horizontal.method_1029().method_1021(1.2));
    }

    static double mountedFollowSpeed(MedievalSoldierEntity soldier, double distance) {
        if (distance >= 20.0) {
            return 1.12;
        }
        if (distance >= 12.0) {
            return 1.08;
        }
        return 1.05;
    }

    static double moveArrivalRadius(MedievalSoldierEntity soldier) {
        return soldier != null && soldier.getFormation() != NpcFormation.FREE ? 1.8 : 3.2;
    }

    static double holdRadius(MedievalSoldierEntity soldier) {
        return soldier != null && soldier.getFormation() != NpcFormation.FREE ? 1.7 : 2.8;
    }

    static boolean archerShouldRetreat(double distanceSq) {
        return distanceSq < 56.25;
    }

    static int archerOrbitRepathTicks() {
        return 30;
    }

    static class_243 mountedRecoveryPoint(class_1498 horse, int turnSign) {
        class_243 velocity = horse.method_18798();
        class_243 forward = new class_243(velocity.field_1352, 0.0, velocity.field_1350);
        if (forward.method_1027() < 0.02) {
            double yaw = Math.toRadians(horse.method_36454());
            forward = new class_243(-Math.sin(yaw), 0.0, Math.cos(yaw));
        } else {
            forward = forward.method_1029();
        }
        class_243 side = new class_243(-forward.field_1350 * (double)turnSign, 0.0, forward.field_1352 * (double)turnSign);
        return horse.method_19538().method_1019(forward.method_1021(3.5)).method_1019(side.method_1021(3.0));
    }

    static class_243 knightPassThroughPoint(class_1498 horse, class_1309 target) {
        class_243 through = target.method_19538().method_1020(horse.method_19538());
        class_243 horizontal = new class_243(through.field_1352, 0.0, through.field_1350);
        if (horizontal.method_1027() < 0.001) {
            return target.method_19538();
        }
        return target.method_19538().method_1019(horizontal.method_1029().method_1021(8.5));
    }

    static double followCombatLeash() {
        return 14.0;
    }

    static double formationApproachSpeed(MedievalSoldierEntity soldier, double distance) {
        if (soldier == null || soldier.getFormation() == NpcFormation.FREE) {
            return 1.05;
        }
        if (distance > 9.0) {
            return 1.05;
        }
        if (distance > 4.5) {
            return 0.98;
        }
        return 0.88;
    }

    static int mountedMoveSettleTicks(MedievalSoldierEntity soldier) {
        return 1;
    }

    static double followRefreshDistance(class_3222 owner) {
        if (owner == null) {
            return 1.5;
        }
        class_243 velocity = owner.method_18798();
        double horizontalSpeed = Math.sqrt(velocity.field_1352 * velocity.field_1352 + velocity.field_1350 * velocity.field_1350);
        return horizontalSpeed > 0.12 ? 0.85 : 1.5;
    }

    static class_243 smoothFollowPoint(class_243 previous, class_243 requested) {
        if (requested == null) {
            return previous;
        }
        if (previous == null) {
            return requested;
        }
        double distanceSq = previous.method_1025(requested);
        if (distanceSq < 0.12249999999999998) {
            return previous;
        }
        double blend = distanceSq > 36.0 ? 0.6 : 0.38;
        return previous.method_35590(requested, blend);
    }

    static int followRefreshTicks(class_3222 owner) {
        if (owner == null) {
            return 6;
        }
        class_243 velocity = owner.method_18798();
        double horizontalSpeed = Math.sqrt(velocity.field_1352 * velocity.field_1352 + velocity.field_1350 * velocity.field_1350);
        return horizontalSpeed > 0.12 ? 4 : 7;
    }

    static class_243 mountedFollowLookAhead(MedievalSoldierEntity soldier, class_3222 owner, class_243 basePoint) {
        if (soldier == null || owner == null || basePoint == null) {
            return basePoint;
        }
        class_243 velocity = owner.method_18798();
        class_243 horizontal = new class_243(velocity.field_1352, 0.0, velocity.field_1350);
        if (horizontal.method_1027() < 0.015) {
            return basePoint;
        }
        double lead = MedievalSoldierMountedPolicy.followLeadDistance(owner, soldier.getFormation() != NpcFormation.FREE);
        return basePoint.method_1019(horizontal.method_1029().method_1021(lead));
    }

    static boolean chargeCorridorStillValid(class_1498 horse, class_1309 target, class_243 chargeAnchor, class_243 chargeDirection) {
        if (horse == null || target == null || chargeAnchor == null || chargeDirection == null) {
            return true;
        }
        class_243 delta = target.method_19538().method_1020(chargeAnchor);
        class_243 horizontal = new class_243(delta.field_1352, 0.0, delta.field_1350);
        class_243 dir = new class_243(chargeDirection.field_1352, 0.0, chargeDirection.field_1350);
        if (dir.method_1027() < 0.001) {
            return true;
        }
        dir = dir.method_1029();
        class_243 lateralAxis = new class_243(-dir.field_1350, 0.0, dir.field_1352);
        double lateral = Math.abs(horizontal.method_1026(lateralAxis));
        return lateral <= 4.25;
    }

    static class_243 chargeDirection(class_1498 horse, class_1309 target) {
        class_243 delta = target.method_19538().method_1020(horse.method_19538());
        class_243 horizontal = new class_243(delta.field_1352, 0.0, delta.field_1350);
        return horizontal.method_1027() < 0.001 ? new class_243(0.0, 0.0, 1.0) : horizontal.method_1029();
    }

    static int knightChargeCommitTicks() {
        return 24;
    }

    static double formationApproachSpeed(MedievalSoldierEntity soldier, double distance, double horizontalSpeed) {
        double base = MedievalSoldierMountedPolicy.formationApproachSpeed(soldier, distance);
        if (soldier == null || soldier.getFormation() == NpcFormation.FREE) {
            return base;
        }
        if (distance < 5.5 && horizontalSpeed > 0.22) {
            return Math.min(base, 0.8);
        }
        if (distance < 3.0 && horizontalSpeed > 0.13) {
            return Math.min(base, 0.74);
        }
        return base;
    }

    static double followLeadDistance(class_3222 owner, boolean formation) {
        if (owner == null) {
            return 0.0;
        }
        class_243 v = owner.method_18798();
        double speed = Math.sqrt(v.field_1352 * v.field_1352 + v.field_1350 * v.field_1350);
        if (speed < 0.06) {
            return 0.0;
        }
        double lead = formation ? 1.0 + speed * 3.5 : 0.65 + speed * 2.5;
        return Math.min(formation ? 2.25 : 1.55, lead);
    }

    static double passThroughArrivalRadius() {
        return 2.15;
    }

    static double knightReengageDistance() {
        return 7.5;
    }

    static boolean recoveryAllowed(MedievalSoldierMountedState state) {
        return state != null && state.knightChargeCommitTicks <= 0 && state.passThroughTicks <= 0;
    }

    static double settledSeparationStrength(boolean settled) {
        return settled ? 0.003 : 0.01;
    }

    static int patrolSettleTicks() {
        return 2;
    }

    static int patrolRepathTicks() {
        return 12;
    }

    static double knightChargeSpeed(double distance) {
        if (distance > 12.0) {
            return 1.12;
        }
        if (distance > 6.0) {
            return 1.15;
        }
        if (distance > 3.0) {
            return 1.1;
        }
        return 1.04;
    }

    static boolean knightHasMeaningfulChargeSpeed(class_1498 horse) {
        if (horse == null) {
            return false;
        }
        class_243 v = horse.method_18798();
        return v.field_1352 * v.field_1352 + v.field_1350 * v.field_1350 >= 0.011024999999999998;
    }

    static boolean archerStableToAim(class_1498 horse, boolean heldFormation) {
        if (horse == null) {
            return false;
        }
        class_243 v = horse.method_18798();
        double speedSq = v.field_1352 * v.field_1352 + v.field_1350 * v.field_1350;
        return speedSq <= (heldFormation ? 0.016900000000000002 : 0.010000000000000002);
    }

    static double patrolSpeed(double distance) {
        return distance > 4.0 ? 1.05 : 0.98;
    }

    static boolean knightChargeAligned(class_1498 horse, class_1309 target) {
        if (horse == null || target == null) {
            return false;
        }
        class_243 velocity = new class_243(horse.method_18798().field_1352, 0.0, horse.method_18798().field_1350);
        class_243 toTarget = new class_243(target.method_23317() - horse.method_23317(), 0.0, target.method_23321() - horse.method_23321());
        if (velocity.method_1027() < 0.001 || toTarget.method_1027() < 0.001) {
            return false;
        }
        return velocity.method_1029().method_1026(toTarget.method_1029()) >= 0.28;
    }

    static int knightStagingTimeoutTicks() {
        return 58;
    }

    static double knightStagingArrivalRadius() {
        return 2.45;
    }

    static double knightStagingSpeed(double distance) {
        if (distance > 7.0) {
            return 1.08;
        }
        if (distance > 3.5) {
            return 1.0;
        }
        return 0.9;
    }

    static int postPassTurnDelayTicks() {
        return 5;
    }

    static double mountedFollowSpeed(MedievalSoldierEntity soldier, double distance, double horizontalSpeed) {
        double base = MedievalSoldierMountedPolicy.mountedFollowSpeed(soldier, distance);
        if (distance < 8.0 && horizontalSpeed > 0.22) {
            return Math.min(base, 0.9);
        }
        if (distance < 6.0 && horizontalSpeed > 0.14) {
            return Math.min(base, 0.84);
        }
        return base;
    }

    static double idleRallyStartRadius() {
        return 7.0;
    }

    static double idleRallyStopRadius() {
        return 4.5;
    }

    static double idleRallyApproachSpeed(double distance) {
        if (distance > 18.0) {
            return 0.94;
        }
        if (distance > 9.0) {
            return 0.86;
        }
        return 0.74;
    }

    static double supplyApproachSpeed(double distance) {
        if (distance > 14.0) {
            return 0.9;
        }
        if (distance > 6.0) {
            return 0.8;
        }
        return 0.68;
    }

    static double archerOrbitRadius(class_1309 target) {
        if (target == null) {
            return 12.0;
        }
        class_243 v = target.method_18798();
        double speed = Math.sqrt(v.field_1352 * v.field_1352 + v.field_1350 * v.field_1350);
        return Math.min(14.0, 12.0 + speed * 5.0);
    }

    static int archerSightLossGraceTicks() {
        return 6;
    }
}

