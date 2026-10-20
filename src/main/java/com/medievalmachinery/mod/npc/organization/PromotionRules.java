/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.organization.GeneralCooldownManager;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryData;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_3218;

public final class PromotionRules {
    private PromotionRules() {
    }

    public static int knightCount(class_3218 world, UUID castleId) {
        if (castleId == null) {
            return 0;
        }
        int count = 0;
        for (SoldierRegistryData data : SoldierRegistryManager.get(world).getFor(castleId, null)) {
            if (!PromotionRules.isKnightRole(data.getRole()) || !(data.getHealth() > 0.0f)) continue;
            ++count;
        }
        return count;
    }

    public static int soldierCount(class_3218 world, UUID castleId) {
        if (castleId == null) {
            return 0;
        }
        int count = 0;
        for (SoldierRegistryData data : SoldierRegistryManager.get(world).getFor(castleId, null)) {
            if (!(data.getHealth() > 0.0f)) continue;
            ++count;
        }
        return count;
    }

    public static boolean canBecomeCaptain(class_3218 world, UUID castleId, SoldierRegistryData candidate) {
        if (candidate == null || castleId == null || !castleId.equals(candidate.getCastleId())) {
            return false;
        }
        MedievalMachineryConfig cfg = MedievalMachineryConfig.get();
        return candidate.getHealth() > 0.0f && PromotionRules.isKnightRole(candidate.getRole()) && "none".equals(candidate.getRank()) && candidate.getLevel() >= cfg.captainMinimumLevel && PromotionRules.knightCount(world, castleId) >= cfg.captainRequiredKnights;
    }

    public static boolean canBecomeGeneral(class_3218 world, UUID castleId, SoldierRegistryData candidate) {
        if (candidate == null || castleId == null || !castleId.equals(candidate.getCastleId())) {
            return false;
        }
        MedievalMachineryConfig cfg = MedievalMachineryConfig.get();
        return candidate.getHealth() > 0.0f && "captain".equals(candidate.getRank()) && candidate.getLevel() >= cfg.generalMinimumLevel && PromotionRules.knightCount(world, castleId) >= cfg.generalRequiredKnights && PromotionRules.soldierCount(world, castleId) >= cfg.generalRequiredSoldiers && !SoldierRegistryManager.get(world).hasLivingGeneral(castleId) && !GeneralCooldownManager.get(world).isActive(world, castleId);
    }

    public static List<SoldierRegistryData> eligibleCandidates(class_3218 world, UUID castleId) {
        return SoldierRegistryManager.get(world).getFor(castleId, null).stream().filter(data -> PromotionRules.canBecomeCaptain(world, castleId, data) || PromotionRules.canBecomeGeneral(world, castleId, data)).toList();
    }

    public static NpcRank targetRank(class_3218 world, UUID castleId, SoldierRegistryData candidate) {
        if (PromotionRules.canBecomeGeneral(world, castleId, candidate)) {
            return NpcRank.GENERAL;
        }
        if (PromotionRules.canBecomeCaptain(world, castleId, candidate)) {
            return NpcRank.CAPTAIN;
        }
        return NpcRank.NONE;
    }

    public static boolean isKnightRole(String role) {
        return "knight".equals(role) || "mounted_knight".equals(role);
    }
}

