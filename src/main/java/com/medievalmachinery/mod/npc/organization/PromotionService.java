/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CaptainAssignmentService;
import com.medievalmachinery.mod.npc.organization.CastleAccessPolicy;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.GeneralCooldownManager;
import com.medievalmachinery.mod.npc.organization.PromotionRules;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryData;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class PromotionService {
    private PromotionService() {
    }

    public static Result promote(class_3218 world, class_3222 player, UUID castleId, UUID soldierId) {
        int diamonds;
        MedievalSoldierEntity soldier;
        if (world == null || player == null || castleId == null || soldierId == null) {
            return Result.INVALID;
        }
        GarrisonData garrison = GarrisonManager.get(world).get(castleId);
        if (garrison == null || !PromotionService.canAdminister(world, player, garrison)) {
            return Result.NO_PERMISSION;
        }
        SoldierRegistryData data = SoldierRegistryManager.get(world).get(soldierId);
        if (data == null || !castleId.equals(data.getCastleId())) {
            return Result.NOT_ELIGIBLE;
        }
        NpcRank target = PromotionRules.targetRank(world, castleId, data);
        if (target == NpcRank.NONE) {
            return GeneralCooldownManager.get(world).isActive(world, castleId) ? Result.GENERAL_COOLDOWN : Result.NOT_ELIGIBLE;
        }
        class_1297 entity = world.method_14190(soldierId);
        if (!(entity instanceof MedievalSoldierEntity) || !(soldier = (MedievalSoldierEntity)entity).method_5805()) {
            return Result.CANDIDATE_NOT_LOADED;
        }
        MedievalMachineryConfig cfg = MedievalMachineryConfig.get();
        int emeralds = target == NpcRank.CAPTAIN ? cfg.captainEmeraldCost : cfg.generalEmeraldCost;
        int n = diamonds = target == NpcRank.CAPTAIN ? cfg.captainDiamondCost : cfg.generalDiamondCost;
        if (cfg.promotionCostsEnabled && !player.method_31549().field_7477) {
            if (PromotionService.count(player, class_1802.field_8687) < emeralds || PromotionService.count(player, class_1802.field_8477) < diamonds) {
                return Result.MISSING_COSTS;
            }
            PromotionService.remove(player, class_1802.field_8687, emeralds);
            PromotionService.remove(player, class_1802.field_8477, diamonds);
        }
        soldier.setNpcRank(target);
        SoldierRegistryManager.get(world).update(soldier);
        if (target == NpcRank.CAPTAIN) {
            CaptainAssignmentService.assignPromotedCaptainIfVacant(world, soldier);
        }
        return target == NpcRank.GENERAL ? Result.PROMOTED_GENERAL : Result.PROMOTED_CAPTAIN;
    }

    private static boolean canAdminister(class_3218 world, class_3222 player, GarrisonData garrison) {
        return CastleAccessPolicy.canAdminister(world, player, garrison);
    }

    private static int count(class_3222 player, class_1792 item) {
        int total = 0;
        for (int i = 0; i < player.method_31548().method_5439(); ++i) {
            if (!player.method_31548().method_5438(i).method_31574(item)) continue;
            total += player.method_31548().method_5438(i).method_7947();
        }
        return total;
    }

    private static void remove(class_3222 player, class_1792 item, int amount) {
        int left = amount;
        for (int i = 0; i < player.method_31548().method_5439() && left > 0; ++i) {
            class_1799 stack = player.method_31548().method_5438(i);
            if (!stack.method_31574(item)) continue;
            int take = Math.min(left, stack.method_7947());
            stack.method_7934(take);
            left -= take;
        }
    }

    public static enum Result {
        PROMOTED_CAPTAIN,
        PROMOTED_GENERAL,
        NO_PERMISSION,
        NOT_ELIGIBLE,
        GENERAL_COOLDOWN,
        MISSING_COSTS,
        CANDIDATE_NOT_LOADED,
        INVALID;

    }
}

