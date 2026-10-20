/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyManager;
import com.medievalmachinery.mod.npc.organization.KingdomRelation;
import java.util.UUID;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class KingdomRelationPolicy {
    private KingdomRelationPolicy() {
    }

    public static KingdomRelation relation(class_3218 world, @Nullable UUID a, @Nullable UUID b) {
        return KingdomDiplomacyManager.get(world).getEffectiveRelation(a, b);
    }

    public static boolean areFriendly(class_3218 world, @Nullable UUID a, @Nullable UUID b) {
        KingdomRelation relation = KingdomRelationPolicy.relation(world, a, b);
        return relation == KingdomRelation.SAME_KINGDOM || relation == KingdomRelation.ALLIED;
    }

    public static boolean areHostile(class_3218 world, @Nullable UUID a, @Nullable UUID b) {
        return KingdomRelationPolicy.relation(world, a, b) == KingdomRelation.WAR;
    }
}

