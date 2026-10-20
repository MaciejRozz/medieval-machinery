/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1937
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFormationCombatPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierTargetPriorityPolicy;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierGroupCombatCoordinator {
    private static final double LOCAL_TARGET_RADIUS = 18.0;
    private static final double ALLY_COUNT_RADIUS = 32.0;

    private MedievalSoldierGroupCombatCoordinator() {
    }

    @Nullable
    static class_1309 coordinatedTarget(MedievalSoldierEntity soldier, class_1309 preferred, boolean allowAlternativeTargets) {
        class_3218 world;
        block6: {
            block5: {
                class_1937 class_19372;
                if (soldier == null || preferred == null || !((class_19372 = soldier.method_37908()) instanceof class_3218)) break block5;
                world = (class_3218)class_19372;
                if (allowAlternativeTargets) break block6;
            }
            return preferred;
        }
        UUID groupId = soldier.getNpcIdentity().getSquadId();
        if (groupId == null) {
            return preferred;
        }
        UUID resolved = CommandGroupManager.get(world).resolveAlias(groupId);
        if (resolved == null) {
            return preferred;
        }
        List<MedievalSoldierEntity> allies = MedievalSoldierGroupCombatCoordinator.loadGroupAllies(world, soldier, resolved);
        return MedievalSoldierGroupCombatCoordinator.selectCoordinatedTarget(world, soldier, preferred, allies);
    }

    @Nullable
    private static class_1309 selectCoordinatedTarget(class_3218 world, MedievalSoldierEntity soldier, class_1309 preferred, List<MedievalSoldierEntity> allies) {
        class_238 targetArea = (preferred.method_5805() ? preferred.method_5829() : soldier.method_5829()).method_1009(18.0, 8.0, 18.0);
        List candidates = world.method_8390(class_1309.class, targetArea, target -> target != soldier && target.method_5805() && !soldier.isFriendlyCombatEntity((class_1309)target) && soldier.isLegalAutomaticCombatTarget((class_1309)target) && MedievalSoldierFormationCombatPolicy.mayJoinGroupDefense(soldier, target));
        if (preferred.method_5805() && !candidates.contains(preferred)) {
            candidates.add(preferred);
        }
        if (candidates.isEmpty()) {
            return null;
        }
        if (candidates.size() == 1) {
            return (class_1309)candidates.get(0);
        }
        return candidates.stream().min(Comparator.comparingDouble(target -> MedievalSoldierGroupCombatCoordinator.scoreTarget(soldier, target, preferred, allies))).orElse(preferred);
    }

    private static List<MedievalSoldierEntity> loadGroupAllies(class_3218 world, MedievalSoldierEntity soldier, UUID resolvedGroupId) {
        class_238 allyArea = soldier.method_5829().method_1009(32.0, 12.0, 32.0);
        return world.method_8390(MedievalSoldierEntity.class, allyArea, ally -> ally.method_5805() && resolvedGroupId.equals(CommandGroupManager.get(world).resolveAlias(ally.getNpcIdentity().getSquadId())));
    }

    private static double scoreTarget(MedievalSoldierEntity soldier, class_1309 target, class_1309 preferred, List<MedievalSoldierEntity> allies) {
        long assigned = allies.stream().filter(ally -> ally.method_5968() == target).count();
        class_243 tacticalAnchor = MedievalSoldierFormationCombatPolicy.tacticalAnchor(soldier);
        double distance = MedievalSoldierFormationCombatPolicy.isHeldFormation(soldier) && tacticalAnchor != null ? tacticalAnchor.method_1025(target.method_19538()) : soldier.method_5858((class_1297)target);
        int desiredAttackers = MedievalSoldierGroupCombatCoordinator.desiredAttackers(soldier, target);
        long excess = Math.max(0L, assigned - (long)desiredAttackers + 1L);
        double overloadPenalty = (double)(excess * excess) * 34.0 + (double)assigned * 4.0;
        double preferredBias = target == preferred ? -5.0 : 0.0;
        double commitmentBias = soldier.method_5968() == target ? -12.0 : 0.0;
        double retaliationBias = soldier.method_6065() == target ? -8.0 : 0.0;
        double roleBias = 0.0;
        if ((soldier.getNpcRole() == NpcRole.SPEARMAN || soldier.getNpcRole() == NpcRole.MOUNTED_KNIGHT) && target.method_5765()) {
            roleBias -= 8.0;
        }
        double targetPriorityBias = MedievalSoldierTargetPriorityPolicy.scoreBias(soldier, target);
        return distance + overloadPenalty + preferredBias + commitmentBias + retaliationBias + roleBias + targetPriorityBias;
    }

    private static int desiredAttackers(MedievalSoldierEntity soldier, class_1309 target) {
        int capacity;
        if (soldier == null || target == null) {
            return 2;
        }
        int n = capacity = soldier.isHoldingRangedWeapon() ? 4 : 2;
        if (target.method_17681() >= 1.4f) {
            ++capacity;
        }
        if (target.method_5765()) {
            ++capacity;
        }
        return capacity;
    }

    @Nullable
    static class_1309 rebalanceTarget(MedievalSoldierEntity soldier, @Nullable class_1309 current) {
        int alternativeCapacity;
        int currentCapacity;
        class_1937 class_19372;
        if (soldier == null || current == null || !current.method_5805() || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return current;
        }
        class_3218 world = (class_3218)class_19372;
        UUID groupId = soldier.getNpcIdentity().getSquadId();
        if (groupId == null) {
            return current;
        }
        UUID resolved = CommandGroupManager.get(world).resolveAlias(groupId);
        if (resolved == null) {
            return current;
        }
        List<MedievalSoldierEntity> allies = MedievalSoldierGroupCombatCoordinator.loadGroupAllies(world, soldier, resolved);
        long currentAssigned = allies.stream().filter(ally -> ally.method_5968() == current).count();
        if (currentAssigned <= (long)(currentCapacity = MedievalSoldierGroupCombatCoordinator.desiredAttackers(soldier, current)) + 1L) {
            return current;
        }
        class_1309 alternative = MedievalSoldierGroupCombatCoordinator.selectCoordinatedTarget(world, soldier, current, allies);
        if (alternative == null || alternative == current) {
            return current;
        }
        long alternativeAssigned = allies.stream().filter(ally -> ally.method_5968() == alternative).count();
        boolean alternativeHasRoom = alternativeAssigned < (long)(alternativeCapacity = MedievalSoldierGroupCombatCoordinator.desiredAttackers(soldier, alternative));
        long occupancyGain = currentAssigned - alternativeAssigned;
        boolean priorityUpgrade = MedievalSoldierTargetPriorityPolicy.materiallyPreferred(soldier, alternative, current);
        return alternativeHasRoom && (occupancyGain >= 3L || priorityUpgrade && occupancyGain >= 1L) ? alternative : current;
    }
}

