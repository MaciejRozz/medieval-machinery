/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_5250
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_5250;

final class MedievalSoldierScoutController {
    private static final double REPORT_RADIUS = 64.0;
    private static final int SCAN_INTERVAL = 40;
    private static final int SAME_CONTACT_COOLDOWN = 600;
    private long nextSameContactReportTick;
    private String lastSignature = "";

    MedievalSoldierScoutController() {
    }

    void tick(MedievalSoldierEntity scout) {
        class_1937 class_19372;
        if (scout.getNpcRole() != NpcRole.SCOUT || !((class_19372 = scout.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 world = (class_3218)class_19372;
        if (scout.getOrderType() != NpcOrderType.PATROL && scout.getOrderType() != NpcOrderType.HOLD_POSITION && scout.getOrderType() != NpcOrderType.OBSERVATION_POINT) {
            return;
        }
        if (!scout.isStaggeredAiTick(40)) {
            return;
        }
        UUID ownKingdom = scout.getNpcIdentity().getKingdomId();
        if (ownKingdom == null) {
            return;
        }
        class_238 scanBox = scout.method_5829().method_1009(64.0, 24.0, 64.0);
        List enemies = world.method_8390(class_1309.class, scanBox, target -> target != scout && target.method_5805() && (target instanceof MedievalSoldierEntity || target instanceof class_1657) && scout.isDiplomaticWarTarget((class_1309)target) && scout.method_5985().method_6369((class_1297)target));
        if (enemies.isEmpty()) {
            this.lastSignature = "";
            return;
        }
        int players = 0;
        EnumMap<NpcRole, Integer> soldiersByRole = new EnumMap<NpcRole, Integer>(NpcRole.class);
        double sumX = 0.0;
        double sumZ = 0.0;
        for (class_1309 enemy : enemies) {
            sumX += enemy.method_23317();
            sumZ += enemy.method_23321();
            if (enemy instanceof MedievalSoldierEntity) {
                MedievalSoldierEntity soldier = (MedievalSoldierEntity)enemy;
                soldiersByRole.merge(soldier.getNpcRole(), 1, Integer::sum);
                continue;
            }
            if (!(enemy instanceof class_1657)) continue;
            ++players;
        }
        class_2338 origin = scout.getHomePosition();
        double ox = origin != null ? (double)origin.method_10263() + 0.5 : scout.method_23317();
        double oz = origin != null ? (double)origin.method_10260() + 0.5 : scout.method_23321();
        String directionKey = MedievalSoldierScoutController.directionKey(sumX / (double)enemies.size() - ox, sumZ / (double)enemies.size() - oz);
        String signature = directionKey + "|" + players + "|" + String.valueOf(soldiersByRole);
        long now = world.method_8510();
        if (signature.equals(this.lastSignature) && now < this.nextSameContactReportTick) {
            return;
        }
        this.lastSignature = signature;
        this.nextSameContactReportTick = now + 600L;
        MedievalSoldierScoutController.sendReport(world, scout, ownKingdom, directionKey, soldiersByRole, players);
    }

    private static void sendReport(class_3218 world, MedievalSoldierEntity scout, UUID kingdomId, String directionKey, Map<NpcRole, Integer> soldiersByRole, int players) {
        KingdomData kingdom = KingdomManager.get(world).get(kingdomId);
        if (kingdom == null) {
            return;
        }
        int soldierCount = soldiersByRole.values().stream().mapToInt(Integer::intValue).sum();
        class_5250 details = class_2561.method_43473();
        if (soldierCount > 0) {
            details.method_10852((class_2561)class_2561.method_43469((String)"message.medieval-machinery.scout.soldiers", (Object[])new Object[]{soldierCount}));
            boolean firstRole = true;
            class_5250 roles = class_2561.method_43473();
            for (Map.Entry<NpcRole, Integer> entry : soldiersByRole.entrySet()) {
                if (!firstRole) {
                    roles.method_10852((class_2561)class_2561.method_43470((String)", "));
                }
                roles.method_10852((class_2561)class_2561.method_43470((String)(String.valueOf(entry.getValue()) + " "))).method_10852((class_2561)class_2561.method_43471((String)("npc.medieval-machinery.role." + entry.getKey().serializedName())));
                firstRole = false;
            }
            if (!firstRole) {
                details.method_10852((class_2561)class_2561.method_43470((String)" (")).method_10852((class_2561)roles).method_10852((class_2561)class_2561.method_43470((String)")"));
            }
        }
        if (players > 0) {
            if (soldierCount > 0) {
                details.method_10852((class_2561)class_2561.method_43470((String)", "));
            }
            details.method_10852((class_2561)class_2561.method_43469((String)"message.medieval-machinery.scout.players", (Object[])new Object[]{players}));
        }
        class_5250 report = class_2561.method_43469((String)"message.medieval-machinery.scout.report", (Object[])new Object[]{class_2561.method_43471((String)directionKey), details});
        for (UUID memberId : kingdom.getMembers().keySet()) {
            class_3222 player = world.method_8503().method_3760().method_14602(memberId);
            if (player == null) continue;
            player.method_7353((class_2561)report, false);
        }
    }

    private static String directionKey(double dx, double dz) {
        double az;
        double ax = Math.abs(dx);
        if (ax < (az = Math.abs(dz)) * 0.5) {
            return dz < 0.0 ? "message.medieval-machinery.scout.direction.north" : "message.medieval-machinery.scout.direction.south";
        }
        if (az < ax * 0.5) {
            return dx > 0.0 ? "message.medieval-machinery.scout.direction.east" : "message.medieval-machinery.scout.direction.west";
        }
        if (dx > 0.0 && dz < 0.0) {
            return "message.medieval-machinery.scout.direction.northeast";
        }
        if (dx < 0.0 && dz < 0.0) {
            return "message.medieval-machinery.scout.direction.northwest";
        }
        if (dx > 0.0) {
            return "message.medieval-machinery.scout.direction.southeast";
        }
        return "message.medieval-machinery.scout.direction.southwest";
    }
}

