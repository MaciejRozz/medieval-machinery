/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.KingdomRelation;
import com.medievalmachinery.mod.npc.organization.KingdomRelationPolicy;
import java.util.UUID;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class CastleDiplomacyPolicy {
    private CastleDiplomacyPolicy() {
    }

    public static boolean mayUseFriendlyInfrastructure(class_3218 world, @Nullable UUID castleKingdom, @Nullable UUID playerKingdom) {
        return castleKingdom != null && playerKingdom != null && KingdomRelationPolicy.areFriendly(world, castleKingdom, playerKingdom);
    }

    public static boolean mayAttemptConquest(class_3218 world, @Nullable UUID castleKingdom, @Nullable UUID playerKingdom) {
        if (playerKingdom == null) {
            return false;
        }
        if (castleKingdom == null) {
            return true;
        }
        KingdomRelation relation = KingdomRelationPolicy.relation(world, castleKingdom, playerKingdom);
        return relation == KingdomRelation.NEUTRAL || relation == KingdomRelation.WAR;
    }
}

