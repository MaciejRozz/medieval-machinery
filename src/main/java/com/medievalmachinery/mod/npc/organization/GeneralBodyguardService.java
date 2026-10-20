/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.GeneralBodyguardManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_243;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class GeneralBodyguardService {
    private static final double ASSIGN_RADIUS = 32.0;
    private static final double FOLLOW_DISTANCE_SQ = 0.81;
    private static final double CATCHUP_DISTANCE_SQ = 196.0;
    private static final double SLOT_BACK = 0.5;
    private static final double SLOT_SIDE = 1.25;

    private GeneralBodyguardService() {
    }

    public static int assignNearest(class_3218 world, MedievalSoldierEntity general) {
        if (!GeneralBodyguardService.validGeneral(general)) {
            return 0;
        }
        GeneralBodyguardManager manager = GeneralBodyguardManager.get(world);
        Set<UUID> current = manager.getGuards(general.method_5667());
        int free = Math.max(0, 2 - current.size());
        if (free == 0) {
            return 0;
        }
        ArrayList<MedievalSoldierEntity> candidates = new ArrayList<MedievalSoldierEntity>(world.method_8390(MedievalSoldierEntity.class, general.method_5829().method_1014(32.0), guard -> GeneralBodyguardService.eligibleGuard(general, guard) && manager.getGeneralForGuard(guard.method_5667()) == null));
        candidates.sort(Comparator.comparingDouble(arg_0 -> ((MedievalSoldierEntity)general).method_5858(arg_0)));
        int assigned = 0;
        for (MedievalSoldierEntity guard2 : candidates) {
            if (assigned >= free) break;
            if (!manager.assign(general.method_5667(), guard2.method_5667())) continue;
            guard2.setGeneralBodyguardDuty(true);
            guard2.setOrderType(NpcOrderType.IDLE);
            guard2.forgetCurrentTarget();
            guard2.stopAdaptiveNavigation();
            ++assigned;
        }
        return assigned;
    }

    public static boolean assignSelected(class_3218 world, MedievalSoldierEntity general, MedievalSoldierEntity guard) {
        if (!GeneralBodyguardService.validGeneral(general) || !GeneralBodyguardService.eligibleGuard(general, guard)) {
            return false;
        }
        GeneralBodyguardManager manager = GeneralBodyguardManager.get(world);
        UUID existingGeneral = manager.getGeneralForGuard(guard.method_5667());
        if (general.method_5667().equals(existingGeneral)) {
            return true;
        }
        if (manager.getGuards(general.method_5667()).size() >= 2) {
            return false;
        }
        if (!manager.assign(general.method_5667(), guard.method_5667())) {
            return false;
        }
        guard.setGeneralBodyguardDuty(true);
        guard.setOrderType(NpcOrderType.IDLE);
        guard.forgetCurrentTarget();
        guard.stopAdaptiveNavigation();
        return true;
    }

    public static boolean removeSelected(class_3218 world, MedievalSoldierEntity general, MedievalSoldierEntity guard) {
        if (world == null || general == null || guard == null) {
            return false;
        }
        GeneralBodyguardManager manager = GeneralBodyguardManager.get(world);
        UUID assignedGeneral = manager.getGeneralForGuard(guard.method_5667());
        if (!general.method_5667().equals(assignedGeneral)) {
            return false;
        }
        boolean removed = manager.removeGuard(guard.method_5667());
        if (removed) {
            guard.setGeneralBodyguardDuty(false);
            guard.setOrderType(NpcOrderType.IDLE);
            guard.stopAdaptiveNavigation();
        }
        return removed;
    }

    public static int clear(class_3218 world, MedievalSoldierEntity general) {
        if (general == null) {
            return 0;
        }
        GeneralBodyguardManager manager = GeneralBodyguardManager.get(world);
        Set<UUID> guardIds = manager.getGuards(general.method_5667());
        int count = guardIds.size();
        manager.clearGeneral(general.method_5667());
        for (UUID guardId : guardIds) {
            class_1297 entity = world.method_14190(guardId);
            if (!(entity instanceof MedievalSoldierEntity)) continue;
            MedievalSoldierEntity guard = (MedievalSoldierEntity)entity;
            guard.setGeneralBodyguardDuty(false);
            guard.setOrderType(NpcOrderType.IDLE);
            guard.stopAdaptiveNavigation();
        }
        return count;
    }

    public static void onSoldierDeath(class_3218 world, MedievalSoldierEntity soldier) {
        GeneralBodyguardManager manager = GeneralBodyguardManager.get(world);
        if (soldier.getNpcRank() == NpcRank.GENERAL) {
            GeneralBodyguardService.clear(world, soldier);
        }
        manager.removeGuard(soldier.method_5667());
        soldier.setGeneralBodyguardDuty(false);
    }

    public static void tickGuard(class_3218 world, MedievalSoldierEntity guard) {
        MedievalSoldierEntity general;
        if (!guard.isStaggeredAiTick(10)) {
            return;
        }
        GeneralBodyguardManager manager = GeneralBodyguardManager.get(world);
        UUID generalId = manager.getGeneralForGuard(guard.method_5667());
        if (generalId == null) {
            guard.setGeneralBodyguardDuty(false);
            return;
        }
        guard.setGeneralBodyguardDuty(true);
        class_1297 entity = world.method_14190(generalId);
        if (!(entity instanceof MedievalSoldierEntity && GeneralBodyguardService.validGeneral(general = (MedievalSoldierEntity)entity) && GeneralBodyguardService.eligibleGuard(general, guard))) {
            manager.removeGuard(guard.method_5667());
            guard.setGeneralBodyguardDuty(false);
            return;
        }
        if (!guard.hasCommandedCombatTarget()) {
            class_1309 threat = general.method_6065();
            if (threat == null || !threat.method_5805()) {
                threat = general.method_5968();
            }
            if (threat != null && threat.method_5805() && threat != guard && guard.canBodyguardTarget(threat)) {
                guard.method_5980(threat);
            }
        }
    }

    @Nullable
    public static EscortDirective escortDirective(class_3218 world, MedievalSoldierEntity guard) {
        double forwardZ;
        MedievalSoldierEntity general;
        GeneralBodyguardManager manager = GeneralBodyguardManager.get(world);
        UUID generalId = manager.getGeneralForGuard(guard.method_5667());
        if (generalId == null) {
            return null;
        }
        class_1297 entity = world.method_14190(generalId);
        if (!(entity instanceof MedievalSoldierEntity && GeneralBodyguardService.validGeneral(general = (MedievalSoldierEntity)entity) && GeneralBodyguardService.eligibleGuard(general, guard))) {
            manager.removeGuard(guard.method_5667());
            return null;
        }
        if (guard.method_5968() != null && guard.method_5968().method_5805()) {
            return null;
        }
        int slot = manager.getGuardSlot(generalId, guard.method_5667());
        if (slot < 0 || slot > 1) {
            manager.removeGuard(guard.method_5667());
            return null;
        }
        double yaw = Math.toRadians(general.method_36454());
        double forwardX = -Math.sin(yaw);
        double rightX = forwardZ = Math.cos(yaw);
        double rightZ = -forwardX;
        double escortBack = general.getOrderType() == NpcOrderType.FOLLOW ? 1.75 : 0.5;
        double escortSide = general.getOrderType() == NpcOrderType.FOLLOW ? 1.4 : 1.25;
        double side = slot == 0 ? -escortSide : escortSide;
        class_243 target = new class_243(general.method_23317() - forwardX * escortBack + rightX * side, general.method_23318(), general.method_23321() - forwardZ * escortBack + rightZ * side);
        double speed = guard.method_5858((class_1297)general) > 196.0 ? 1.18 : 1.05;
        return new EscortDirective(target, speed);
    }

    public static boolean isAtEscortSlot(MedievalSoldierEntity guard, EscortDirective directive) {
        return directive != null && guard.method_19538().method_1025(directive.target()) <= 0.81;
    }

    private static boolean validGeneral(MedievalSoldierEntity general) {
        return general != null && general.method_5805() && general.getNpcRank() == NpcRank.GENERAL && general.getNpcIdentity().getCastleId() != null;
    }

    private static boolean eligibleGuard(MedievalSoldierEntity general, MedievalSoldierEntity guard) {
        if (guard == null || !guard.method_5805() || guard == general || guard.getNpcRole() != NpcRole.SWORDSMAN) {
            return false;
        }
        if (guard.getNpcRank() != NpcRank.NONE) {
            return false;
        }
        return Objects.equals(general.getNpcIdentity().getOwnerId(), guard.getNpcIdentity().getOwnerId()) && Objects.equals(general.getNpcIdentity().getKingdomId(), guard.getNpcIdentity().getKingdomId()) && Objects.equals(general.getNpcIdentity().getCastleId(), guard.getNpcIdentity().getCastleId());
    }

    public record EscortDirective(class_243 target, double speed) {
    }
}

