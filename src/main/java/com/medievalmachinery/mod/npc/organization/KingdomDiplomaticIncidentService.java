/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyManager;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyStance;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.KingdomRelation;
import com.medievalmachinery.mod.npc.organization.LocalAggressionManager;
import java.util.UUID;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class KingdomDiplomaticIncidentService {
    private KingdomDiplomaticIncidentService() {
    }

    public static void onPlayerAttacksSoldier(class_3218 world, class_3222 attacker, MedievalSoldierEntity victim) {
        if (world == null || attacker == null || victim == null) {
            return;
        }
        UUID victimKingdom = victim.getNpcIdentity().getKingdomId();
        if (victimKingdom == null) {
            return;
        }
        KingdomData attackerKingdom = KingdomManager.get(world).findByMember(attacker.method_5667());
        if (attackerKingdom == null || victimKingdom.equals(attackerKingdom.getId())) {
            return;
        }
        if (LocalAggressionManager.isActive(world, attacker.method_5667(), victim.getNpcIdentity().getCastleId(), victimKingdom)) {
            return;
        }
        KingdomDiplomacyManager diplomacy = KingdomDiplomacyManager.get(world);
        if (diplomacy.getEffectiveRelation(victimKingdom, attackerKingdom.getId()) == KingdomRelation.WAR) {
            return;
        }
        diplomacy.setStance(victimKingdom, attackerKingdom.getId(), KingdomDiplomacyStance.WAR);
        KingdomDiplomaticIncidentService.notifyKingdom(world, victimKingdom, (class_2561)class_2561.method_43469((String)"message.medieval-machinery.diplomacy.war_incident_victim", (Object[])new Object[]{attackerKingdom.getName()}));
        KingdomDiplomaticIncidentService.notifyKingdom(world, attackerKingdom.getId(), (class_2561)class_2561.method_43471((String)"message.medieval-machinery.diplomacy.war_incident_attacker"));
    }

    public static void onSoldierAttacksSoldier(class_3218 world, MedievalSoldierEntity attacker, MedievalSoldierEntity victim) {
        if (world == null || attacker == null || victim == null || attacker == victim) {
            return;
        }
        UUID attackerKingdom = attacker.getNpcIdentity().getKingdomId();
        UUID victimKingdom = victim.getNpcIdentity().getKingdomId();
        if (attackerKingdom == null || victimKingdom == null || attackerKingdom.equals(victimKingdom)) {
            return;
        }
        UUID ownerId = attacker.getNpcIdentity().getOwnerId();
        if (ownerId != null && LocalAggressionManager.isActive(world, ownerId, victim.getNpcIdentity().getCastleId(), victimKingdom)) {
            return;
        }
        KingdomDiplomacyManager diplomacy = KingdomDiplomacyManager.get(world);
        if (diplomacy.getEffectiveRelation(victimKingdom, attackerKingdom) == KingdomRelation.WAR) {
            return;
        }
        if (!diplomacy.setStance(victimKingdom, attackerKingdom, KingdomDiplomacyStance.WAR)) {
            return;
        }
        KingdomData attackerData = KingdomManager.get(world).get(attackerKingdom);
        String attackerName = attackerData == null ? "?" : attackerData.getName();
        KingdomDiplomaticIncidentService.notifyKingdom(world, victimKingdom, (class_2561)class_2561.method_43469((String)"message.medieval-machinery.diplomacy.war_incident_victim", (Object[])new Object[]{attackerName}));
        KingdomDiplomaticIncidentService.notifyKingdom(world, attackerKingdom, (class_2561)class_2561.method_43471((String)"message.medieval-machinery.diplomacy.war_incident_soldier_attacker"));
    }

    public static void onSoldierAttacksPlayer(class_3218 world, MedievalSoldierEntity attacker, class_3222 victim) {
        if (world == null || attacker == null || victim == null) {
            return;
        }
        UUID attackerKingdom = attacker.getNpcIdentity().getKingdomId();
        KingdomData victimData = KingdomManager.get(world).findByMember(victim.method_5667());
        if (attackerKingdom == null || victimData == null || attackerKingdom.equals(victimData.getId())) {
            return;
        }
        if (LocalAggressionManager.isActive(world, victim.method_5667(), attacker.getNpcIdentity().getCastleId(), attackerKingdom)) {
            return;
        }
        KingdomDiplomacyManager diplomacy = KingdomDiplomacyManager.get(world);
        if (diplomacy.getEffectiveRelation(victimData.getId(), attackerKingdom) == KingdomRelation.WAR) {
            return;
        }
        if (!diplomacy.setStance(victimData.getId(), attackerKingdom, KingdomDiplomacyStance.WAR)) {
            return;
        }
        KingdomData attackerData = KingdomManager.get(world).get(attackerKingdom);
        String attackerName = attackerData == null ? "?" : attackerData.getName();
        KingdomDiplomaticIncidentService.notifyKingdom(world, victimData.getId(), (class_2561)class_2561.method_43469((String)"message.medieval-machinery.diplomacy.war_incident_victim", (Object[])new Object[]{attackerName}));
        KingdomDiplomaticIncidentService.notifyKingdom(world, attackerKingdom, (class_2561)class_2561.method_43471((String)"message.medieval-machinery.diplomacy.war_incident_soldier_attacker"));
    }

    private static void notifyKingdom(class_3218 world, UUID kingdomId, class_2561 message) {
        KingdomData kingdom = KingdomManager.get(world).get(kingdomId);
        if (kingdom == null) {
            return;
        }
        for (class_3222 online : world.method_8503().method_3760().method_14571()) {
            if (!kingdom.isMember(online.method_5667())) continue;
            online.method_7353(message, false);
        }
    }
}

