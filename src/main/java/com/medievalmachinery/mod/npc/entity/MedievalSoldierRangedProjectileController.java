/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1667
 *  net.minecraft.class_1764
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3417
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.MedievalMachineryCore;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.core.NpcEquipmentProfile;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomaticIncidentService;
import java.util.Locale;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1667;
import net.minecraft.class_1764;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3417;

final class MedievalSoldierRangedProjectileController {
    private static final float PROJECTILE_SPEED = 2.25f;
    private static final double PROJECTILE_DRAG = 0.99;
    private static final double PROJECTILE_GRAVITY = 0.05;

    MedievalSoldierRangedProjectileController() {
    }

    void fireAt(MedievalSoldierEntity soldier, class_1309 target) {
        class_1937 class_19372;
        if (target == null || !target.method_5805() || soldier.method_37908().field_9236) {
            return;
        }
        class_243 origin = soldier.rangedShotOrigin();
        AimSolution solution = this.solveMovingTarget(origin, target);
        class_1667 arrow = this.createArrow(soldier, origin);
        arrow.method_7485(solution.dx(), solution.dy(), solution.dz(), 2.25f, soldier.getLevelRangedInaccuracy());
        if (soldier.isCommandedCombatTarget(target) && (class_19372 = soldier.method_37908()) instanceof class_3218) {
            class_3218 world = (class_3218)class_19372;
            if (target instanceof class_3222) {
                class_3222 playerTarget = (class_3222)target;
                KingdomDiplomaticIncidentService.onSoldierAttacksPlayer(world, soldier, playerTarget);
            } else if (target instanceof MedievalSoldierEntity) {
                MedievalSoldierEntity soldierTarget = (MedievalSoldierEntity)target;
                KingdomDiplomaticIncidentService.onSoldierAttacksSoldier(world, soldier, soldierTarget);
            }
        }
        soldier.method_37908().method_8649((class_1297)arrow);
        this.logShot(soldier, target, origin, solution);
        this.finishShot(soldier);
    }

    void fireAtPoint(MedievalSoldierEntity soldier, class_243 aimPoint) {
        if (aimPoint == null || soldier.method_37908().field_9236) {
            return;
        }
        class_243 origin = soldier.rangedShotOrigin();
        AimSolution solution = this.solveStaticPoint(origin, aimPoint);
        class_1667 arrow = this.createArrow(soldier, origin);
        arrow.method_7485(solution.dx(), solution.dy(), solution.dz(), 2.25f, soldier.getLevelRangedInaccuracy());
        soldier.method_37908().method_8649((class_1297)arrow);
        this.finishShot(soldier);
    }

    private class_1667 createArrow(MedievalSoldierEntity soldier, class_243 origin) {
        class_1667 arrow = new class_1667(soldier.method_37908(), (class_1309)soldier);
        arrow.method_5814(origin.field_1352, origin.field_1351, origin.field_1350);
        float efficiency = (float)NpcEquipmentProfile.damageMultiplier(soldier.getNpcRole(), soldier.method_6047());
        arrow.method_7438(2.0 * Math.max(0.25, (double)efficiency));
        return arrow;
    }

    private void finishShot(MedievalSoldierEntity soldier) {
        soldier.method_5783(soldier.method_6047().method_7909() instanceof class_1764 ? class_3417.field_15187 : class_3417.field_14600, 1.0f, 1.0f / (soldier.method_6051().method_43057() * 0.4f + 0.8f));
        soldier.damageMainHandWeapon(1);
    }

    private AimSolution solveMovingTarget(class_243 origin, class_1309 target) {
        class_243 velocity = target.method_18798();
        class_243 currentAim = new class_243(target.method_23317(), target.method_23323(0.55), target.method_23321());
        AimSolution first = this.solveStaticPoint(origin, currentAim);
        double leadTicks = Math.min(30.0, first.flightTicks());
        class_243 ledAim = currentAim.method_1019(velocity.method_1021(leadTicks));
        return this.solveStaticPoint(origin, ledAim);
    }

    private AimSolution solveStaticPoint(class_243 origin, class_243 aimPoint) {
        double dx = aimPoint.field_1352 - origin.field_1352;
        double dz = aimPoint.field_1350 - origin.field_1350;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 0.001) {
            return new AimSolution(dx, aimPoint.field_1351 - origin.field_1351, dz, 0.0, aimPoint);
        }
        double targetDy = aimPoint.field_1351 - origin.field_1351;
        double low = -1.5;
        double high = 1.5;
        FlightSample sample = null;
        for (int i = 0; i < 28; ++i) {
            double slope = (low + high) * 0.5;
            sample = this.simulateToHorizontal(horizontal, slope);
            if (sample.yAtTarget() < targetDy) {
                low = slope;
                continue;
            }
            high = slope;
        }
        double slope = (low + high) * 0.5;
        sample = this.simulateToHorizontal(horizontal, slope);
        return new AimSolution(dx, slope * horizontal, dz, sample.flightTicks(), aimPoint);
    }

    private FlightSample simulateToHorizontal(double targetHorizontal, double slope) {
        double normalization = Math.sqrt(1.0 + slope * slope);
        double horizontalVelocity = 2.25 / normalization;
        double verticalVelocity = 2.25 * slope / normalization;
        double horizontal = 0.0;
        double vertical = 0.0;
        for (int tick = 1; tick <= 80; ++tick) {
            double previousHorizontal = horizontal;
            double previousVertical = vertical;
            horizontal += horizontalVelocity;
            vertical += verticalVelocity;
            if (horizontal >= targetHorizontal) {
                double segment = horizontal - previousHorizontal;
                double fraction = segment <= 1.0E-6 ? 1.0 : (targetHorizontal - previousHorizontal) / segment;
                double yAtTarget = previousVertical + (vertical - previousVertical) * fraction;
                return new FlightSample(yAtTarget, (double)(tick - 1) + fraction);
            }
            horizontalVelocity *= 0.99;
            verticalVelocity = verticalVelocity * 0.99 - 0.05;
        }
        return new FlightSample(vertical, 80.0);
    }

    private void logShot(MedievalSoldierEntity soldier, class_1309 target, class_243 origin, AimSolution solution) {
        if (!MedievalMachineryConfig.get().rangedCombatDebugEnabled) {
            return;
        }
        MedievalMachineryCore.LOGGER.info("[RANGED-SHOT] npc={} target={} distance={} origin=({},{},{}) aim=({},{},{}) flightTicks={} inaccuracy={}", new Object[]{soldier.method_5628(), target.method_5628(), MedievalSoldierRangedProjectileController.format(Math.sqrt(soldier.method_5858((class_1297)target))), MedievalSoldierRangedProjectileController.format(origin.field_1352), MedievalSoldierRangedProjectileController.format(origin.field_1351), MedievalSoldierRangedProjectileController.format(origin.field_1350), MedievalSoldierRangedProjectileController.format(solution.aimPoint().field_1352), MedievalSoldierRangedProjectileController.format(solution.aimPoint().field_1351), MedievalSoldierRangedProjectileController.format(solution.aimPoint().field_1350), MedievalSoldierRangedProjectileController.format(solution.flightTicks()), MedievalSoldierRangedProjectileController.format(soldier.getLevelRangedInaccuracy())});
    }

    private static String format(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }

    private record AimSolution(double dx, double dy, double dz, double flightTicks, class_243 aimPoint) {
    }

    private record FlightSample(double yAtTarget, double flightTicks) {
    }
}

