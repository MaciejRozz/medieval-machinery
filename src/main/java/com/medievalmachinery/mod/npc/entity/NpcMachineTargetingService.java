/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1496
 *  net.minecraft.class_1690
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_2960
 *  net.minecraft.class_3218
 *  net.minecraft.class_3532
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.machine.core.MachineAimSolution;
import com.medievalmachinery.mod.machine.core.MachineCombatTarget;
import com.medievalmachinery.mod.machine.core.MachineFortificationTargetRegistry;
import com.medievalmachinery.mod.machine.core.MachineTargetCategory;
import com.medievalmachinery.mod.machine.core.MachineTargetProfile;
import com.medievalmachinery.mod.npc.core.NpcTargetPriority;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineFireDiscipline;
import com.medievalmachinery.mod.npc.machine.NpcMachineTargetSaturationManager;
import com.medievalmachinery.mod.npc.organization.KingdomRelationPolicy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1496;
import net.minecraft.class_1690;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_7923;

final class NpcMachineTargetingService {
    private NpcMachineTargetingService() {
    }

    static StabilizedTarget stabilizeAndLead(class_3218 world, MedievalMachineryOperable machine, MachineCombatTarget target, class_243 previousAimPoint) {
        class_243 accepted;
        class_1297 entity;
        if (target == null || target.aimPoint() == null || machine == null) {
            return new StabilizedTarget(target, previousAimPoint);
        }
        class_243 desired = target.aimPoint();
        if (world != null && target.entityId() != null && (entity = world.method_14190(target.entityId())) != null && !entity.method_31481()) {
            class_243 velocity = entity.method_18798();
            class_243 horizontalVelocity = new class_243(velocity.field_1352, 0.0, velocity.field_1350);
            if (horizontalVelocity.method_1033() > 0.025) {
                double distance = machine.medievalMachinery$getNpcAimOrigin().method_1022(desired);
                double leadTicks = class_3532.method_15350((double)(distance * 0.18), (double)3.0, (double)18.0);
                desired = desired.method_1019(horizontalVelocity.method_1021(leadTicks));
            }
            desired = new class_243(Math.floor(desired.field_1352) + 0.5, desired.field_1351, Math.floor(desired.field_1350) + 0.5);
        }
        class_243 candidate = desired;
        MachineCombatTarget snappedTarget = new MachineCombatTarget(target.entityId(), target.blockPos(), target.category(), candidate, target.stableKey());
        if (!NpcMachineTargetingService.hasUsableAim(machine, snappedTarget)) {
            class_1297 entity2;
            class_243 live = target.aimPoint();
            if (world != null && target.entityId() != null && (entity2 = world.method_14190(target.entityId())) != null && !entity2.method_31481()) {
                class_243 velocity = entity2.method_18798();
                double distance = machine.medievalMachinery$getNpcAimOrigin().method_1022(live);
                double leadTicks = class_3532.method_15350((double)(distance * 0.18), (double)3.0, (double)18.0);
                live = live.method_1031(velocity.field_1352 * leadTicks, 0.0, velocity.field_1350 * leadTicks);
            }
            candidate = live;
        }
        if ((accepted = previousAimPoint) == null || !NpcMachineTargetingService.hasUsableAim(machine, new MachineCombatTarget(target.entityId(), target.blockPos(), target.category(), accepted, target.stableKey()))) {
            accepted = candidate;
        }
        return new StabilizedTarget(new MachineCombatTarget(target.entityId(), target.blockPos(), target.category(), accepted, target.stableKey()), accepted);
    }

    static MachineCombatTarget acquire(class_3218 world, MedievalSoldierEntity operator, MedievalMachineryOperable machine, MachineCombatTarget preferred) {
        MachineCombatTarget selected;
        MachineCombatTarget target2;
        class_243 aim;
        MachineTargetCategory category;
        MachineCombatTarget selected2;
        MachineTargetCategory category2;
        MachineCombatTarget selected3;
        if (world == null || operator == null || machine == null) {
            return null;
        }
        MachineTargetProfile profile = machine.medievalMachinery$getTargetProfile();
        if (profile == null || profile.categories().isEmpty()) {
            return null;
        }
        double range = Math.max(4.0, machine.medievalMachinery$getNpcTargetingRange());
        class_1309 commanded = operator.method_5968();
        if (operator.isCommandedCombatTarget(commanded) && commanded != null && NpcMachineTargetingService.isLivingCandidate(operator, machine, profile, commanded, range) && NpcMachineTargetingService.hasUsableAim(machine, selected3 = MachineCombatTarget.entity((class_1297)commanded, category2 = NpcMachineTargetingService.classifyLiving(commanded), commanded.method_19538().method_1031(0.0, (double)commanded.method_17682() * 0.55, 0.0)))) {
            NpcMachineTargetSaturationManager.note(world, machine.asEntity().method_5667(), selected3);
            return selected3;
        }
        class_1309 current = operator.method_5968();
        if (current != null && NpcMachineTargetingService.isLivingCandidate(operator, machine, profile, current, range) && NpcMachineTargetingService.hasUsableAim(machine, selected2 = MachineCombatTarget.entity((class_1297)current, category = NpcMachineTargetingService.classifyLiving(current), current.method_19538().method_1031(0.0, (double)current.method_17682() * 0.55, 0.0)))) {
            NpcMachineTargetSaturationManager.note(world, machine.asEntity().method_5667(), selected2);
            return selected2;
        }
        if (preferred != null && NpcMachineTargetingService.isStillValid(world, operator, machine, profile, preferred, range) && NpcMachineTargetingService.hasUsableAim(machine, selected3 = NpcMachineTargetingService.refresh(world, preferred))) {
            NpcMachineTargetSaturationManager.note(world, machine.asEntity().method_5667(), selected3);
            return selected3;
        }
        ArrayList<MachineCombatTarget> candidates = new ArrayList<MachineCombatTarget>();
        class_1297 machineEntity = machine.asEntity();
        class_238 search = machineEntity.method_5829().method_1014(range);
        for (class_1309 living : world.method_8390(class_1309.class, search, target -> NpcMachineTargetingService.isLivingCandidate(operator, machine, profile, target, range))) {
            MachineCombatTarget candidate = MachineCombatTarget.entity((class_1297)living, NpcMachineTargetingService.classifyLiving(living), living.method_19538().method_1031(0.0, (double)living.method_17682() * 0.55, 0.0));
            if (!NpcMachineTargetingService.hasUsableAim(machine, candidate)) continue;
            candidates.add(candidate);
        }
        if (profile.accepts(MachineTargetCategory.SIEGE_WEAPONS)) {
            for (class_1297 entity2 : world.method_8333(machineEntity, search, entity -> entity instanceof MedievalMachineryOperable && entity != machineEntity)) {
                MedievalMachineryOperable other = (MedievalMachineryOperable)entity2;
                if (!NpcMachineTargetingService.isHostileMachine(operator, machine, other) || !NpcMachineTargetingService.isWithinEngagementEnvelope(machine, aim = entity2.method_5829().method_1005(), range) || !NpcMachineTargetingService.hasUsableAim(machine, target2 = MachineCombatTarget.entity(entity2, MachineTargetCategory.SIEGE_WEAPONS, aim)) || !NpcMachineTargetingService.hasClearLine(world, machine, target2)) continue;
                candidates.add(target2);
            }
        }
        if (profile.accepts(MachineTargetCategory.FORTIFICATIONS)) {
            class_2338 center = machineEntity.method_24515();
            for (MachineFortificationTargetRegistry.Entry entry : MachineFortificationTargetRegistry.loadedWithin(world, center, range)) {
                if (!NpcMachineTargetingService.isHostileFortification(world, operator, entry) || !NpcMachineTargetingService.isWithinEngagementEnvelope(machine, aim = class_243.method_24953((class_2382)entry.pos()), range) || !NpcMachineTargetingService.hasUsableAim(machine, target2 = MachineCombatTarget.block(entry.pos(), MachineTargetCategory.FORTIFICATIONS, aim)) || !NpcMachineTargetingService.hasClearLine(world, machine, target2)) continue;
                candidates.add(target2);
            }
        }
        if ((selected = (MachineCombatTarget)candidates.stream().min(Comparator.comparingDouble(target -> NpcMachineTargetingService.score(world, operator, machine, target))).orElse(null)) != null) {
            NpcMachineTargetSaturationManager.note(world, machine.asEntity().method_5667(), selected);
        }
        return selected;
    }

    static boolean hasClearLine(class_3218 world, MedievalMachineryOperable machine, MachineCombatTarget target) {
        if (world == null || machine == null || target == null || target.aimPoint() == null) {
            return false;
        }
        if (!machine.medievalMachinery$requiresDirectNpcLineOfSight()) {
            return true;
        }
        class_243 start = machine.medievalMachinery$getNpcAimOrigin();
        class_243 end = target.aimPoint();
        class_3965 hit = world.method_17742(new class_3959(start, end, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, machine.asEntity()));
        double tolerance = target.isBlock() ? 2.25 : 1.5;
        return hit.method_17783() == class_239.class_240.field_1333 || hit.method_17784().method_1025(end) <= tolerance * tolerance;
    }

    static boolean isFireCorridorClear(class_3218 world, MedievalSoldierEntity operator, MedievalMachineryOperable machine, MachineCombatTarget target, MachineAimSolution solution) {
        if (world == null || operator == null || machine == null || target == null || solution == null || !solution.valid()) {
            return false;
        }
        List<class_243> path = machine.medievalMachinery$getNpcProjectilePath(target, solution);
        if (path == null || path.size() < 2) {
            return false;
        }
        UUID ownKingdom = operator.getNpcIdentity().getKingdomId();
        for (int index = 1; index < path.size(); ++index) {
            class_243 start = path.get(index - 1);
            class_243 end = path.get(index);
            if (start == null || end == null) continue;
            class_238 corridor = new class_238(start, end).method_1014(0.95);
            for (class_1309 living2 : world.method_8390(class_1309.class, corridor, living -> living.method_5805() && living != operator)) {
                if (target.entityId() != null && target.entityId().equals(living2.method_5667()) || !NpcMachineTargetingService.isNearSegment(living2.method_5829().method_1005(), start, end, 1.1) || !operator.isFriendlyCombatEntity(living2)) continue;
                return false;
            }
            for (class_1297 entity2 : world.method_8333(machine.asEntity(), corridor, entity -> entity instanceof MedievalMachineryOperable && entity != machine.asEntity())) {
                if (target.entityId() != null && target.entityId().equals(entity2.method_5667()) || !NpcMachineTargetingService.isNearSegment(entity2.method_5829().method_1005(), start, end, 1.3)) continue;
                MedievalMachineryOperable other = (MedievalMachineryOperable)entity2;
                UUID otherKingdom = other.medievalMachinery$getKingdomId();
                if (ownKingdom == null || otherKingdom == null || !KingdomRelationPolicy.areFriendly(world, ownKingdom, otherKingdom)) continue;
                return false;
            }
        }
        double impactRadius = Math.max(0.0, machine.medievalMachinery$getNpcImpactSafetyRadius(target));
        if (impactRadius > 0.0 && target.aimPoint() != null) {
            class_238 impact = new class_238(target.aimPoint(), target.aimPoint()).method_1014(impactRadius);
            for (class_1309 living3 : world.method_8390(class_1309.class, impact, living -> living.method_5805() && living != operator)) {
                if (target.entityId() != null && target.entityId().equals(living3.method_5667()) || !operator.isFriendlyCombatEntity(living3)) continue;
                return false;
            }
            for (class_1297 entity3 : world.method_8333(machine.asEntity(), impact, entity -> entity instanceof MedievalMachineryOperable && entity != machine.asEntity())) {
                if (target.entityId() != null && target.entityId().equals(entity3.method_5667())) continue;
                MedievalMachineryOperable other = (MedievalMachineryOperable)entity3;
                UUID otherKingdom = other.medievalMachinery$getKingdomId();
                if (ownKingdom == null || otherKingdom == null || !KingdomRelationPolicy.areFriendly(world, ownKingdom, otherKingdom)) continue;
                return false;
            }
        }
        return true;
    }

    static boolean isProjectilePathTerrainClear(class_3218 world, MedievalMachineryOperable machine, MachineCombatTarget target, MachineAimSolution solution) {
        if (world == null || machine == null || target == null || solution == null || !solution.valid()) {
            return false;
        }
        List<class_243> path = machine.medievalMachinery$getNpcProjectilePath(target, solution);
        if (path == null || path.size() < 2) {
            return false;
        }
        class_243 targetPoint = target.aimPoint();
        for (int index = 1; index < path.size(); ++index) {
            class_243 end;
            class_243 start = path.get(index - 1);
            class_3965 hit = world.method_17742(new class_3959(start, end = path.get(index), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, machine.asEntity()));
            if (hit.method_17783() == class_239.class_240.field_1333) continue;
            return (target.isBlock() || target.isPoint()) && targetPoint != null && hit.method_17784().method_1025(targetPoint) <= 5.0625;
        }
        return true;
    }

    static boolean isStillValid(class_3218 world, MedievalSoldierEntity operator, MedievalMachineryOperable machine, MachineTargetProfile profile, MachineCombatTarget target, double range) {
        MachineCombatTarget refreshed = NpcMachineTargetingService.refresh(world, target);
        if (refreshed == null) {
            return false;
        }
        if (!NpcMachineTargetingService.isWithinEngagementEnvelope(machine, refreshed.aimPoint(), range)) {
            return false;
        }
        if (refreshed.isPoint()) {
            return NpcMachineTargetingService.hasUsableAim(machine, refreshed);
        }
        if (refreshed.isEntity()) {
            class_1297 entity = world.method_14190(refreshed.entityId());
            if (entity instanceof class_1309) {
                class_1309 living = (class_1309)entity;
                return NpcMachineTargetingService.isLivingCandidate(operator, machine, profile, living, range);
            }
            if (entity instanceof MedievalMachineryOperable) {
                MedievalMachineryOperable other = (MedievalMachineryOperable)entity;
                return profile.accepts(MachineTargetCategory.SIEGE_WEAPONS) && NpcMachineTargetingService.isHostileMachine(operator, machine, other) && NpcMachineTargetingService.hasClearLine(world, machine, refreshed);
            }
            return false;
        }
        if (refreshed.isBlock()) {
            if (!profile.accepts(MachineTargetCategory.FORTIFICATIONS)) {
                return false;
            }
            MachineFortificationTargetRegistry.Entry match = MachineFortificationTargetRegistry.loadedWithin(world, refreshed.blockPos(), 0.5).stream().filter(entry -> entry.pos().equals((Object)refreshed.blockPos())).findFirst().orElse(null);
            return match != null && NpcMachineTargetingService.isHostileFortification(world, operator, match) && NpcMachineTargetingService.hasClearLine(world, machine, refreshed);
        }
        return false;
    }

    static MachineCombatTarget refresh(class_3218 world, MachineCombatTarget target) {
        if (world == null || target == null) {
            return null;
        }
        if (target.entityId() != null) {
            class_243 class_2432;
            class_1297 entity = world.method_14190(target.entityId());
            if (entity == null || entity.method_31481()) {
                return null;
            }
            if (entity instanceof class_1309) {
                class_1309 living = (class_1309)entity;
                class_2432 = living.method_19538().method_1031(0.0, (double)living.method_17682() * 0.55, 0.0);
            } else {
                class_2432 = entity.method_5829().method_1005();
            }
            class_243 aim = class_2432;
            return new MachineCombatTarget(target.entityId(), null, target.category(), aim, target.stableKey());
        }
        if (target.blockPos() != null && world.method_22340(target.blockPos())) {
            return MachineCombatTarget.block(target.blockPos(), target.category(), class_243.method_24953((class_2382)target.blockPos()));
        }
        if (target.isPoint()) {
            return target;
        }
        return null;
    }

    private static boolean isLivingCandidate(MedievalSoldierEntity operator, MedievalMachineryOperable machine, MachineTargetProfile profile, class_1309 target, double range) {
        if (target == null || target == operator || !target.method_5805() || target.method_7325()) {
            return false;
        }
        if (!operator.isLegalAutomaticCombatTarget(target)) {
            return false;
        }
        MachineTargetCategory category = NpcMachineTargetingService.classifyLiving(target);
        if (!(profile.accepts(category) || category == MachineTargetCategory.LARGE_TARGETS && profile.accepts(MachineTargetCategory.INFANTRY))) {
            return false;
        }
        if (!NpcMachineTargetingService.isWithinEngagementEnvelope(machine, target.method_19538(), range)) {
            return false;
        }
        return NpcMachineTargetingService.hasClearLine((class_3218)operator.method_37908(), machine, MachineCombatTarget.entity((class_1297)target, category, target.method_19538().method_1031(0.0, (double)target.method_17682() * 0.55, 0.0)));
    }

    private static boolean isHostileMachine(MedievalSoldierEntity operator, MedievalMachineryOperable own, MedievalMachineryOperable target) {
        class_3218 world;
        block5: {
            block4: {
                class_1937 class_19372 = operator.method_37908();
                if (!(class_19372 instanceof class_3218)) break block4;
                world = (class_3218)class_19372;
                if (target != null && target != own) break block5;
            }
            return false;
        }
        UUID ownKingdom = operator.getNpcIdentity().getKingdomId();
        UUID targetKingdom = target.medievalMachinery$getKingdomId();
        if (ownKingdom == null || targetKingdom == null) {
            return false;
        }
        return KingdomRelationPolicy.areHostile(world, ownKingdom, targetKingdom);
    }

    private static boolean isHostileFortification(class_3218 world, MedievalSoldierEntity operator, MachineFortificationTargetRegistry.Entry entry) {
        UUID ownKingdom = operator.getNpcIdentity().getKingdomId();
        return ownKingdom != null && entry.kingdomId() != null && KingdomRelationPolicy.areHostile(world, ownKingdom, entry.kingdomId());
    }

    private static double score(class_3218 world, MedievalSoldierEntity operator, MedievalMachineryOperable machine, MachineCombatTarget target) {
        double distance = machine.asEntity().method_19538().method_1022(target.aimPoint());
        double categoryBias = switch (target.category()) {
            case MachineTargetCategory.FORTIFICATIONS -> -8.0;
            case MachineTargetCategory.SIEGE_WEAPONS -> -6.0;
            case MachineTargetCategory.LARGE_TARGETS -> -4.0;
            default -> 0.0;
        };
        double playerBias = switch (operator.getTargetPriority()) {
            case NpcTargetPriority.INFANTRY -> {
                if (target.category() == MachineTargetCategory.INFANTRY) {
                    yield -18.0;
                }
                yield 0.0;
            }
            case NpcTargetPriority.CAVALRY -> {
                if (target.category() == MachineTargetCategory.CAVALRY) {
                    yield -18.0;
                }
                yield 0.0;
            }
            case NpcTargetPriority.SHIPS -> {
                if (target.category() == MachineTargetCategory.SHIPS) {
                    yield -18.0;
                }
                yield 0.0;
            }
            case NpcTargetPriority.SIEGE_WEAPONS -> {
                if (target.category() == MachineTargetCategory.SIEGE_WEAPONS) {
                    yield -24.0;
                }
                yield 0.0;
            }
            default -> 0.0;
        };
        double machineBias = machine.medievalMachinery$getNpcTargetCategoryBias(target.category());
        int saturation = NpcMachineTargetSaturationManager.countOthers(world, machine.asEntity().method_5667(), target);
        double saturationPenalty = NpcMachineFireDiscipline.saturationPenalty(target, saturation);
        return distance + categoryBias + playerBias + machineBias + saturationPenalty;
    }

    private static boolean isWithinEngagementEnvelope(MedievalMachineryOperable machine, class_243 point, double maxRange) {
        if (machine == null || point == null) {
            return false;
        }
        double distanceSq = machine.asEntity().method_19538().method_1025(point);
        if (distanceSq > maxRange * maxRange) {
            return false;
        }
        double minRange = Math.max(0.0, machine.medievalMachinery$getNpcMinimumTargetingRange());
        return distanceSq >= minRange * minRange;
    }

    private static boolean hasUsableAim(MedievalMachineryOperable machine, MachineCombatTarget target) {
        if (machine == null || target == null) {
            return false;
        }
        MachineAimSolution solution = machine.medievalMachinery$solveNpcAim(target, 1.0f);
        return solution != null && solution.valid();
    }

    private static MachineTargetCategory classifyLiving(class_1309 target) {
        class_1297 vehicle = target.method_5854();
        if (vehicle != null) {
            if (NpcMachineTargetingService.isShip(vehicle)) {
                return MachineTargetCategory.SHIPS;
            }
            if (vehicle instanceof class_1496) {
                return MachineTargetCategory.CAVALRY;
            }
        }
        if (NpcMachineTargetingService.isShip((class_1297)target)) {
            return MachineTargetCategory.SHIPS;
        }
        if (target.method_17681() >= 1.8f || target.method_17682() >= 3.0f) {
            return MachineTargetCategory.LARGE_TARGETS;
        }
        return MachineTargetCategory.INFANTRY;
    }

    private static boolean isShip(class_1297 entity) {
        if (entity instanceof class_1690) {
            return true;
        }
        class_2960 id = class_7923.field_41177.method_10221((Object)entity.method_5864());
        return id != null && "smallships".equals(id.method_12836());
    }

    private static boolean isNearSegment(class_243 point, class_243 start, class_243 end, double radius) {
        class_243 segment = end.method_1020(start);
        double lengthSq = segment.method_1027();
        if (lengthSq < 1.0E-6) {
            return point.method_1025(start) <= radius * radius;
        }
        double t = Math.max(0.0, Math.min(1.0, point.method_1020(start).method_1026(segment) / lengthSq));
        class_243 closest = start.method_1019(segment.method_1021(t));
        return point.method_1025(closest) <= radius * radius;
    }

    record StabilizedTarget(MachineCombatTarget target, class_243 trackedAimPoint) {
    }
}

