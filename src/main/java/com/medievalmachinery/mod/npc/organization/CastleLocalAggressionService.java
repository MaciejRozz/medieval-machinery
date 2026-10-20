/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.KingdomRelationPolicy;
import com.medievalmachinery.mod.npc.organization.LocalAggressionManager;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3959;
import net.minecraft.class_3965;

public final class CastleLocalAggressionService {
    public static final double INITIAL_ALARM_RADIUS = 25.0;

    private CastleLocalAggressionService() {
    }

    public static boolean onSupplyChestOpened(class_3218 world, class_3222 player, class_2338 pos) {
        if (world == null || player == null || pos == null) {
            return false;
        }
        GarrisonData garrison = GarrisonManager.get(world).findBySupplyChest(pos, world.method_27983().method_29177().toString());
        if (garrison == null || garrison.getKingdomId() == null) {
            return false;
        }
        UUID castleKingdom = garrison.getKingdomId();
        KingdomData playerKingdom = KingdomManager.get(world).findByMember(player.method_5667());
        if (playerKingdom != null && KingdomRelationPolicy.areFriendly(world, castleKingdom, playerKingdom.getId())) {
            return false;
        }
        LocalAggressionManager.mark(world, player.method_5667(), garrison.getCastleId(), castleKingdom);
        class_238 alarmBox = new class_238(pos).method_1014(25.0);
        for (MedievalSoldierEntity soldier : world.method_8390(MedievalSoldierEntity.class, alarmBox, class_1309::method_5805)) {
            UUID soldierKingdom = soldier.getNpcIdentity().getKingdomId();
            UUID soldierCastle = soldier.getNpcIdentity().getCastleId();
            if (soldierKingdom == null || !soldierKingdom.equals(castleKingdom) || garrison.getCastleId() != null && !garrison.getCastleId().equals(soldierCastle)) continue;
            soldier.respondToLocalAggression(player);
        }
        return true;
    }

    public static void propagate(class_3218 world, MedievalSoldierEntity source, class_3222 player, double radius) {
        if (world == null || source == null || player == null || !player.method_5805()) {
            return;
        }
        UUID castleId = source.getNpcIdentity().getCastleId();
        UUID kingdomId = source.getNpcIdentity().getKingdomId();
        if (!LocalAggressionManager.isActive(world, player.method_5667(), castleId, kingdomId)) {
            return;
        }
        class_238 area = source.method_5829().method_1009(Math.max(4.0, radius), 6.0, Math.max(4.0, radius));
        for (MedievalSoldierEntity ally : world.method_8390(MedievalSoldierEntity.class, area, e -> e != source && e.method_5805())) {
            if (kingdomId != null && !kingdomId.equals(ally.getNpcIdentity().getKingdomId()) || castleId != null && !castleId.equals(ally.getNpcIdentity().getCastleId()) || !CastleLocalAggressionService.hasLineOfSight(world, source, ally)) continue;
            ally.respondToLocalAggression(player);
        }
    }

    private static boolean hasLineOfSight(class_3218 world, MedievalSoldierEntity source, MedievalSoldierEntity target) {
        class_3965 hit = world.method_17742(new class_3959(source.method_33571(), target.method_33571(), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)source));
        return hit.method_17783() == class_239.class_240.field_1333 || source.method_33571().method_1025(hit.method_17784()) + 0.25 >= source.method_33571().method_1025(target.method_33571());
    }
}

