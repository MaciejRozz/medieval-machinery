/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.armor;

import com.medievalmachinery.mod.npc.armor.ArmorStyle;
import com.medievalmachinery.mod.npc.armor.ArmorTier;
import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.core.NpcRole;

public final class NpcArmorProfiles {
    private NpcArmorProfiles() {
    }

    public static ArmorStyle defaultStyle(NpcRole role, NpcRank rank) {
        if (rank == NpcRank.GENERAL) {
            return ArmorStyle.GENERAL;
        }
        if (rank == NpcRank.CAPTAIN) {
            return ArmorStyle.CAPTAIN;
        }
        if (role == null) {
            return ArmorStyle.HEAVY_BRIGANDINE;
        }
        return switch (role) {
            default -> throw new IncompatibleClassChangeError();
            case NpcRole.RECRUIT -> ArmorStyle.HEAVY_BRIGANDINE;
            case NpcRole.ARCHER, NpcRole.MOUNTED_ARCHER, NpcRole.SCOUT -> ArmorStyle.ARCHER;
            case NpcRole.CROSSBOWMAN -> ArmorStyle.LIGHT_BRIGANDINE;
            case NpcRole.SPEARMAN, NpcRole.SWORDSMAN, NpcRole.AXEMAN -> ArmorStyle.HEAVY_BRIGANDINE;
            case NpcRole.CANNONEER, NpcRole.ENGINEER -> ArmorStyle.ENGINEER;
            case NpcRole.KNIGHT, NpcRole.MOUNTED_KNIGHT -> ArmorStyle.PLATE;
            case NpcRole.QUARTERMASTER -> ArmorStyle.ARCHER;
        };
    }

    public static ArmorStyle styleForEquippedArmor(NpcRole role, NpcRank rank, ArmorTier tier) {
        if (tier != null && tier.isGeneralFamily()) {
            return rank == NpcRank.GENERAL ? ArmorStyle.GENERAL : NpcArmorProfiles.defaultStyle(role, rank);
        }
        if (rank == NpcRank.GENERAL) {
            return ArmorStyle.CAPTAIN;
        }
        return NpcArmorProfiles.defaultStyle(role, rank);
    }
}

