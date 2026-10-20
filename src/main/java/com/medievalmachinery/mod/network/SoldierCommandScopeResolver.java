/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.block.garrison.WatchpostBlockEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CastleAccessPolicy;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.SoldierControlPolicy;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryData;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

final class SoldierCommandScopeResolver {
    static final UUID UNASSIGNED_SCOPE_ID = UUID.fromString("00000000-0000-0000-0000-00000000a551");
    private static final Map<UUID, UUID> SELECTED_CASTLE = new ConcurrentHashMap<UUID, UUID>();

    private SoldierCommandScopeResolver() {
    }

    static boolean matchesCastle(MedievalSoldierEntity soldier, UUID castleId) {
        if (soldier == null || castleId == null) {
            return false;
        }
        UUID soldierCastle = soldier.getNpcIdentity().getCastleId();
        return UNASSIGNED_SCOPE_ID.equals(castleId) ? soldierCastle == null : castleId.equals(soldierCastle);
    }

    static boolean matchesCastle(CommandGroupData group, UUID castleId) {
        if (group == null || castleId == null) {
            return false;
        }
        return UNASSIGNED_SCOPE_ID.equals(castleId) ? group.getCastleId() == null : castleId.equals(group.getCastleId());
    }

    static class_2561 labelForScope(class_3222 player, int scope) {
        CommandGroupData group;
        if (scope == 0) {
            return class_2561.method_43471((String)"message.medieval-machinery.command.scope.nearby");
        }
        if (scope == 1) {
            return class_2561.method_43471((String)"message.medieval-machinery.command.scope.castle");
        }
        if (scope == 7) {
            return class_2561.method_43471((String)"message.medieval-machinery.command.scope.everyone");
        }
        int slot = SoldierCommandScopeResolver.groupSlotForScope(scope);
        UUID castleId = SoldierCommandScopeResolver.selectedCastle(player);
        if (slot > 0 && castleId != null && (group = SoldierCommandScopeResolver.groupForSlot(player.method_51469(), player, castleId, slot)) != null) {
            return class_2561.method_43470((String)group.getName());
        }
        return class_2561.method_43471((String)"message.medieval-machinery.command.scope.nearby");
    }

    static void sendCommandFeedback(class_3222 player, int scope, String translationKey, Object ... args) {
        Object[] translatedArgs = new Object[args.length + 1];
        translatedArgs[0] = SoldierCommandScopeResolver.labelForScope(player, scope);
        System.arraycopy(args, 0, translatedArgs, 1, args.length);
        player.method_7353((class_2561)class_2561.method_43469((String)translationKey, (Object[])translatedArgs), false);
    }

    static int groupSlotForScope(int scope) {
        if (scope < 1000) {
            return 0;
        }
        int slot = scope - 1000;
        return slot > 0 && slot <= 512 ? slot : 0;
    }

    static SoldierRegistryData ownedSoldierInCastle(class_3218 world, class_3222 player, UUID castleId, UUID soldierId) {
        SoldierRegistryData record = SoldierRegistryManager.get(world).get(soldierId);
        return record != null && SoldierControlPolicy.ownsRegistryRecord(player, record) && castleId.equals(record.getCastleId()) ? record : null;
    }

    static CommandGroupData groupForSlot(class_3218 world, class_3222 player, UUID castleId, int slot) {
        if (castleId == null || slot <= 0) {
            return null;
        }
        List<CommandGroupData> groups = UNASSIGNED_SCOPE_ID.equals(castleId) ? CommandGroupManager.get(world).getUnassignedGroups(player.method_5667()) : CommandGroupManager.get(world).getUiGroups(castleId, player.method_5667());
        int index = slot - 1;
        return index >= 0 && index < groups.size() ? groups.get(index) : null;
    }

    static void selectCastle(class_3222 player, UUID requestedCastle, boolean autoSelectCurrent) {
        UUID existing;
        Object current;
        List<GarrisonData> accessible = SoldierCommandScopeResolver.accessibleCastles(player);
        if (accessible.isEmpty()) {
            SELECTED_CASTLE.remove(player.method_5667());
            return;
        }
        if (autoSelectCurrent && (current = SoldierCommandScopeResolver.nearestCastleInRadius(player, accessible, 50.0)) != null) {
            SELECTED_CASTLE.put(player.method_5667(), ((GarrisonData)current).getId());
            return;
        }
        if (requestedCastle != null) {
            for (GarrisonData castle : accessible) {
                if (!castle.getId().equals(requestedCastle)) continue;
                SELECTED_CASTLE.put(player.method_5667(), requestedCastle);
                return;
            }
        }
        if ((existing = SELECTED_CASTLE.get(player.method_5667())) != null) {
            for (GarrisonData castle : accessible) {
                if (!castle.getId().equals(existing)) continue;
                return;
            }
        }
        accessible.sort(Comparator.comparing(GarrisonData::getName, String.CASE_INSENSITIVE_ORDER));
        SELECTED_CASTLE.put(player.method_5667(), accessible.get(0).getId());
    }

    static UUID selectedCastle(class_3222 player) {
        UUID selected = SELECTED_CASTLE.get(player.method_5667());
        if (selected == null) {
            SoldierCommandScopeResolver.selectCastle(player, null, true);
            selected = SELECTED_CASTLE.get(player.method_5667());
        }
        return selected;
    }

    static boolean isAccessibleCastle(UUID castleId, List<GarrisonData> accessible) {
        if (castleId == null || accessible == null) {
            return false;
        }
        for (GarrisonData castle : accessible) {
            if (!castleId.equals(castle.getId())) continue;
            return true;
        }
        return false;
    }

    static GarrisonData selectedCastleData(class_3222 player, UUID castleId) {
        if (castleId == null) {
            return null;
        }
        if (UNASSIGNED_SCOPE_ID.equals(castleId)) {
            return new GarrisonData(UNASSIGNED_SCOPE_ID, player.method_5667(), null, "Ohne Burg");
        }
        return GarrisonManager.get(player.method_51469()).get(castleId);
    }

    static List<GarrisonData> accessibleCastles(class_3222 player) {
        class_3218 world = player.method_51469();
        GarrisonManager garrisons = GarrisonManager.get(world);
        CommandGroupManager groups = CommandGroupManager.get(world);
        LinkedHashMap<UUID, GarrisonData> result = new LinkedHashMap<UUID, GarrisonData>();
        if (!groups.getUnassignedGroups(player.method_5667()).isEmpty() || !SoldierRegistryManager.get(world).getUnassignedFor(player.method_5667()).isEmpty()) {
            GarrisonData unassigned = new GarrisonData(UNASSIGNED_SCOPE_ID, player.method_5667(), null, "Ohne Burg");
            result.put(UNASSIGNED_SCOPE_ID, unassigned);
        }
        ArrayList<UUID> staleLoadedCastles = new ArrayList<UUID>();
        SoldierRegistryManager soldierRegistry = SoldierRegistryManager.get(world);
        for (GarrisonData castle : garrisons.getAll()) {
            class_2338 center = castle.getCenter();
            if (center == null) {
                boolean hasRegisteredSoldiers = castle.getSoldierCount() > 0 || !soldierRegistry.getFor(castle.getId(), null).isEmpty();
                if (hasRegisteredSoldiers) continue;
                staleLoadedCastles.add(castle.getId());
                continue;
            }
            if (world.method_22340(center) && !(world.method_8321(center) instanceof WatchpostBlockEntity)) {
                staleLoadedCastles.add(castle.getId());
                continue;
            }
            if (!CastleAccessPolicy.isKingdomMember(world, player, castle) && groups.getGroups(castle.getId(), player.method_5667()).size() <= 0) continue;
            result.put(castle.getId(), castle);
        }
        for (UUID staleId : staleLoadedCastles) {
            garrisons.remove(staleId);
        }
        return new ArrayList<GarrisonData>(result.values());
    }

    private static GarrisonData nearestCastleInRadius(class_3222 player, List<GarrisonData> castles, double radius) {
        GarrisonData best = null;
        double bestSq = radius * radius;
        for (GarrisonData castle : castles) {
            double dz;
            double dy;
            double dx;
            double distanceSq;
            class_2338 center = castle.getCenter();
            if (center == null || !((distanceSq = (dx = player.method_23317() - ((double)center.method_10263() + 0.5)) * dx + (dy = player.method_23318() - ((double)center.method_10264() + 0.5)) * dy + (dz = player.method_23321() - ((double)center.method_10260() + 0.5)) * dz) <= bestSq)) continue;
            bestSq = distanceSq;
            best = castle;
        }
        return best;
    }
}

