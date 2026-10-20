/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3222
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyManager;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyStance;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.KingdomPermission;
import com.medievalmachinery.mod.npc.organization.KingdomRelation;
import java.util.UUID;
import net.minecraft.class_3222;
import org.jetbrains.annotations.Nullable;

public final class KingdomDiplomacyService {
    private KingdomDiplomacyService() {
    }

    public static boolean setStance(class_3222 actor, UUID targetKingdomId, KingdomDiplomacyStance stance) {
        if (actor == null || targetKingdomId == null || stance == null) {
            return false;
        }
        KingdomManager kingdoms = KingdomManager.get(actor.method_51469());
        KingdomData source = kingdoms.findByMember(actor.method_5667());
        KingdomData target = kingdoms.get(targetKingdomId);
        if (source == null || target == null || source.getId().equals(target.getId())) {
            return false;
        }
        if (!source.hasPermission(actor.method_5667(), KingdomPermission.MANAGE_DIPLOMACY)) {
            return false;
        }
        return KingdomDiplomacyManager.get(actor.method_51469()).setStance(source.getId(), target.getId(), stance);
    }

    @Nullable
    public static KingdomDiplomacyStance getOwnStance(class_3222 actor, UUID targetKingdomId) {
        if (actor == null || targetKingdomId == null) {
            return null;
        }
        KingdomData source = KingdomManager.get(actor.method_51469()).findByMember(actor.method_5667());
        if (source == null) {
            return null;
        }
        return KingdomDiplomacyManager.get(actor.method_51469()).getStance(source.getId(), targetKingdomId);
    }

    public static KingdomRelation getEffectiveRelation(class_3222 actor, UUID targetKingdomId) {
        if (actor == null || targetKingdomId == null) {
            return KingdomRelation.NEUTRAL;
        }
        KingdomData source = KingdomManager.get(actor.method_51469()).findByMember(actor.method_5667());
        if (source == null) {
            return KingdomRelation.NEUTRAL;
        }
        return KingdomDiplomacyManager.get(actor.method_51469()).getEffectiveRelation(source.getId(), targetKingdomId);
    }
}

