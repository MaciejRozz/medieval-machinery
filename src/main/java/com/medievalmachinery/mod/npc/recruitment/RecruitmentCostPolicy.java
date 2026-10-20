/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.recruitment;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.core.NpcRole;

public final class RecruitmentCostPolicy {
    private RecruitmentCostPolicy() {
    }

    public static int emeraldCost(NpcRole role) {
        return RecruitmentCostPolicy.emeraldCost(MedievalMachineryConfig.get(), role);
    }

    public static int emeraldCost(MedievalMachineryConfig cfg, NpcRole role) {
        if (cfg == null || !cfg.recruitmentCostsEnabled || role == null) {
            return 0;
        }
        return switch (role) {
            case NpcRole.SWORDSMAN -> cfg.recruitSwordsmanEmeraldCost;
            case NpcRole.AXEMAN -> cfg.recruitAxemanEmeraldCost;
            case NpcRole.ARCHER -> cfg.recruitArcherEmeraldCost;
            case NpcRole.CROSSBOWMAN -> cfg.recruitCrossbowmanEmeraldCost;
            case NpcRole.SPEARMAN -> cfg.recruitSpearmanEmeraldCost;
            case NpcRole.CANNONEER -> cfg.recruitCannoneerEmeraldCost;
            case NpcRole.KNIGHT -> cfg.recruitKnightEmeraldCost;
            case NpcRole.QUARTERMASTER -> cfg.recruitQuartermasterEmeraldCost;
            case NpcRole.SCOUT -> cfg.recruitScoutEmeraldCost;
            case NpcRole.ENGINEER -> cfg.recruitEngineerEmeraldCost;
            case NpcRole.MOUNTED_ARCHER -> cfg.recruitMountedArcherEmeraldCost;
            case NpcRole.MOUNTED_KNIGHT -> cfg.recruitMountedKnightEmeraldCost;
            default -> 0;
        };
    }
}

