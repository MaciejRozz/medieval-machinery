/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.KingdomPermission;
import com.medievalmachinery.mod.npc.organization.KingdomRelationPolicy;
import java.util.UUID;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class CastleAccessPolicy {
    private CastleAccessPolicy() {
    }

    public static boolean isKingdomMember(class_3218 world, class_3222 player, GarrisonData garrison) {
        if (world == null || player == null || garrison == null) {
            return false;
        }
        UUID kingdomId = garrison.getKingdomId();
        if (kingdomId == null) {
            return garrison.getOwnerId().equals(player.method_5667());
        }
        KingdomData kingdom = KingdomManager.get(world).get(kingdomId);
        return kingdom != null && kingdom.isMember(player.method_5667());
    }

    public static boolean canAdminister(class_3218 world, class_3222 player, GarrisonData garrison) {
        if (world == null || player == null || garrison == null) {
            return false;
        }
        UUID kingdomId = garrison.getKingdomId();
        if (kingdomId == null) {
            return garrison.getOwnerId().equals(player.method_5667());
        }
        KingdomData kingdom = KingdomManager.get(world).get(kingdomId);
        return kingdom != null && kingdom.hasPermission(player.method_5667(), KingdomPermission.ADMINISTER_CASTLE);
    }

    public static boolean canRecruit(class_3218 world, class_3222 player, GarrisonData garrison) {
        if (world == null || player == null || garrison == null) {
            return false;
        }
        UUID kingdomId = garrison.getKingdomId();
        if (kingdomId == null) {
            return garrison.getOwnerId().equals(player.method_5667());
        }
        KingdomData kingdom = KingdomManager.get(world).get(kingdomId);
        return kingdom != null && kingdom.hasPermission(player.method_5667(), KingdomPermission.RECRUIT);
    }

    public static boolean isDiplomaticallyFriendly(class_3218 world, UUID castleKingdomId, UUID otherKingdomId) {
        if (world == null || castleKingdomId == null || otherKingdomId == null) {
            return false;
        }
        return KingdomRelationPolicy.areFriendly(world, castleKingdomId, otherKingdomId);
    }
}

