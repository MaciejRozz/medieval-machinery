/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1542
 *  net.minecraft.class_1646
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1923
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_2595
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3417
 *  net.minecraft.class_3419
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.organization.CastleAccessPolicy;
import com.medievalmachinery.mod.npc.organization.CastleAreaService;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.KingdomActivityProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1646;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1923;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2595;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3417;
import net.minecraft.class_3419;

public final class CastleTaxService {
    private static final long TAX_DAY_TICKS = 24000L;
    private static final long TAX_NOON_PHASE = 6000L;
    private static final long CENSUS_REFRESH_INTERVAL_TICKS = 200L;
    private static final int PAYOUT_STACK_INTERVAL_TICKS = 1;
    private static final Map<class_3218, Map<UUID, PayoutState>> PAYOUTS = new WeakHashMap<class_3218, Map<UUID, PayoutState>>();

    private CastleTaxService() {
    }

    public static void tick(class_3218 world, KingdomActivityProvider activityProvider) {
        MedievalMachineryConfig config = MedievalMachineryConfig.get();
        GarrisonManager manager = GarrisonManager.get(world);
        long taxClock = world.method_8503().method_30002().method_8510();
        long currentMinecraftDay = Math.floorDiv(taxClock, 24000L);
        CastleTaxService.markLoadedWatchpostsForDay(world, manager, currentMinecraftDay);
        if (Math.floorMod(taxClock, 200L) == 0L) {
            CastleTaxService.refreshLoadedCensus(world, manager, config.taxVillagerRadius);
        }
        if (Math.floorMod(taxClock, 24000L) != 6000L) {
            return;
        }
        long taxDay = Math.floorDiv(taxClock - 6000L, 24000L);
        String dimensionId = world.method_27983().method_29177().toString();
        boolean taxesEnabled = config.taxesEnabled && config.taxEmeraldsPerAdultVillager > 0;
        HashMap<UUID, Boolean> kingdomActiveAtNoon = new HashMap<UUID, Boolean>();
        boolean dirty = false;
        CastleTaxService.refreshLoadedCensus(world, manager, config.taxVillagerRadius);
        for (GarrisonData garrison : manager.getAll()) {
            int adultVillagers;
            UUID kingdomId;
            boolean active;
            if (garrison.getCenter() == null || garrison.getKingdomId() == null || garrison.getCenterDimension() != null && !dimensionId.equals(garrison.getCenterDimension()) || garrison.getLastTaxDay() == taxDay) continue;
            garrison.setLastTaxDay(taxDay);
            dirty = true;
            if (!taxesEnabled || activityProvider == null || garrison.getLastWatchpostLoadedTaxDay() != currentMinecraftDay || !(active = kingdomActiveAtNoon.computeIfAbsent(kingdomId = garrison.getKingdomId(), id -> activityProvider.isKingdomActive(world, (UUID)id)).booleanValue()) || (adultVillagers = garrison.getLastKnownAdultVillagerCount()) <= 0) continue;
            long amount = (long)adultVillagers * (long)config.taxEmeraldsPerAdultVillager;
            garrison.addStoredTaxEmeralds(amount);
        }
        if (dirty) {
            manager.markTaxDataDirty();
        }
    }

    private static void markLoadedWatchpostsForDay(class_3218 world, GarrisonManager manager, long currentMinecraftDay) {
        String dimensionId = world.method_27983().method_29177().toString();
        boolean changed = false;
        for (GarrisonData garrison : manager.getAll()) {
            class_2338 center;
            if (garrison.getLastWatchpostLoadedTaxDay() == currentMinecraftDay || (center = garrison.getCenter()) == null || garrison.getCenterDimension() != null && !dimensionId.equals(garrison.getCenterDimension()) || !world.method_14178().method_12123(center.method_10263() >> 4, center.method_10260() >> 4)) continue;
            garrison.setLastWatchpostLoadedTaxDay(currentMinecraftDay);
            changed = true;
        }
        if (changed) {
            manager.markTaxDataDirty();
        }
    }

    private static void refreshLoadedCensus(class_3218 world, GarrisonManager manager, int fallbackRadius) {
        String dimensionId = world.method_27983().method_29177().toString();
        LinkedHashMap<UUID, Set<Long>> expectedChunks = new LinkedHashMap<UUID, Set<Long>>();
        LinkedHashMap<Long, List> loadedChunkCastles = new LinkedHashMap<Long, List>();
        boolean changed = false;
        for (GarrisonData garrisonData : manager.getAll()) {
            if (garrisonData.getCenter() == null || garrisonData.getCenterDimension() != null && !dimensionId.equals(garrisonData.getCenterDimension())) continue;
            Set<Long> expected = CastleAreaService.candidateChunkKeys(garrisonData, fallbackRadius);
            expectedChunks.put(garrisonData.getId(), expected);
            if (garrisonData.retainTaxChunkCensus(expected)) {
                changed = true;
            }
            for (long chunkKey : expected) {
                class_1923 chunkPos = new class_1923(chunkKey);
                int chunkX = chunkPos.field_9181;
                int chunkZ = chunkPos.field_9180;
                if (!world.method_14178().method_12123(chunkX, chunkZ)) continue;
                loadedChunkCastles.computeIfAbsent(chunkKey, ignored -> new ArrayList()).add(garrisonData);
            }
        }
        for (Map.Entry entry : loadedChunkCastles.entrySet()) {
            long chunkKey = (Long)entry.getKey();
            class_1923 chunkPos = new class_1923(chunkKey);
            int chunkX = chunkPos.field_9181;
            int chunkZ = chunkPos.field_9180;
            int minX = chunkX << 4;
            int minZ = chunkZ << 4;
            class_238 chunkBox = new class_238((double)minX, (double)world.method_31607(), (double)minZ, (double)(minX + 16), (double)world.method_31600(), (double)(minZ + 16));
            HashMap<UUID, Integer> counts = new HashMap<UUID, Integer>();
            for (class_1646 villager : world.method_8390(class_1646.class, chunkBox, CastleTaxService::isTaxPayingVillager)) {
                GarrisonData owner = CastleTaxService.nearestCastleForVillager(villager, (List)entry.getValue(), fallbackRadius);
                if (owner == null) continue;
                counts.merge(owner.getId(), 1, Integer::sum);
            }
            for (GarrisonData garrison : (List)entry.getValue()) {
                int next = counts.getOrDefault(garrison.getId(), 0);
                Integer old = garrison.getTaxChunkVillagerCount(chunkKey);
                if (old != null && old == next) continue;
                garrison.setTaxChunkVillagerCount(chunkKey, next);
                changed = true;
            }
        }
        for (GarrisonData garrisonData : manager.getAll()) {
            Set expected = (Set)expectedChunks.get(garrisonData.getId());
            if (expected == null || expected.isEmpty()) continue;
            boolean completeSurvey = garrisonData.hasTaxChunkCensusForAll(expected);
            if (completeSurvey && !garrisonData.isTaxChunkCensusAuthoritative()) {
                garrisonData.setTaxChunkCensusAuthoritative(true);
                changed = true;
            }
            if (!garrisonData.isTaxChunkCensusAuthoritative() && garrisonData.getLastKnownAdultVillagerCount() != 0) continue;
            int total = garrisonData.getTaxChunkCensusTotal();
            if (garrisonData.getLastKnownAdultVillagerCount() == total) continue;
            garrisonData.setLastKnownAdultVillagerCount(total);
            changed = true;
        }
        if (changed) {
            manager.markTaxDataDirty();
        }
    }

    private static GarrisonData nearestCastleForVillager(class_1646 villager, List<GarrisonData> candidates, int fallbackRadius) {
        GarrisonData best = null;
        double bestDistanceSq = Double.MAX_VALUE;
        class_2338 villagerPos = villager.method_24515();
        for (GarrisonData garrison : candidates) {
            double dz;
            double dx;
            double distanceSq;
            class_2338 center;
            if (!CastleAreaService.contains(garrison, villagerPos, fallbackRadius) || (center = garrison.getCenter()) == null || !((distanceSq = (dx = villager.method_23317() - ((double)center.method_10263() + 0.5)) * dx + (dz = villager.method_23321() - ((double)center.method_10260() + 0.5)) * dz) < bestDistanceSq)) continue;
            bestDistanceSq = distanceSq;
            best = garrison;
        }
        return best;
    }

    private static boolean isTaxPayingVillager(class_1646 villager) {
        return villager != null && villager.method_5805() && !villager.method_6109();
    }

    public static int collectFromTaxChest(class_3222 player, class_2338 chestPos) {
        class_1937 class_19372;
        if (player == null || chestPos == null || !((class_19372 = player.method_37908()) instanceof class_3218)) {
            return 0;
        }
        class_3218 world = (class_3218)class_19372;
        GarrisonManager manager = GarrisonManager.get(world);
        GarrisonData garrison = manager.findByTaxChest(chestPos, world.method_27983().method_29177().toString());
        if (garrison == null || !CastleTaxService.canCollect(player, world, garrison)) {
            return 0;
        }
        long stored = garrison.getStoredTaxEmeralds();
        if (stored <= 0L) {
            return 0;
        }
        Map worldPayouts = PAYOUTS.computeIfAbsent(world, ignored -> new HashMap());
        if (worldPayouts.containsKey(garrison.getId())) {
            return 0;
        }
        long scheduled = Math.min(Integer.MAX_VALUE, stored);
        worldPayouts.put(garrison.getId(), new PayoutState(garrison.getId(), chestPos.method_10062(), scheduled, world.method_8510()));
        return (int)scheduled;
    }

    public static void tickPayouts(class_3218 world) {
        Map<UUID, PayoutState> worldPayouts = PAYOUTS.get(world);
        if (worldPayouts == null || worldPayouts.isEmpty()) {
            return;
        }
        GarrisonManager manager = GarrisonManager.get(world);
        long now = world.method_8510();
        Iterator<Map.Entry<UUID, PayoutState>> iterator = worldPayouts.entrySet().iterator();
        while (iterator.hasNext()) {
            int stackSize;
            PayoutState state = iterator.next().getValue();
            if (now < state.nextDropTick) continue;
            GarrisonData garrison = manager.get(state.castleId);
            if (garrison == null || state.remaining <= 0L || garrison.getStoredTaxEmeralds() <= 0L) {
                iterator.remove();
                continue;
            }
            if (!(world.method_8321(state.chestPos) instanceof class_2595)) {
                iterator.remove();
                continue;
            }
            int n = stackSize = state.remaining > 0L && garrison.getStoredTaxEmeralds() > 0L ? 1 : 0;
            if (stackSize <= 0) {
                iterator.remove();
                continue;
            }
            class_1542 drop = new class_1542((class_1937)world, (double)state.chestPos.method_10263() + 0.5, (double)state.chestPos.method_10264() + 1.05, (double)state.chestPos.method_10260() + 0.5, new class_1799((class_1935)class_1802.field_8687, 1));
            drop.method_6988();
            drop.method_18800((world.field_9229.method_43058() - 0.5) * 0.08, 0.12 + world.field_9229.method_43058() * 0.04, (world.field_9229.method_43058() - 0.5) * 0.08);
            world.method_8649((class_1297)drop);
            world.method_8396(null, state.chestPos, class_3417.field_14611, class_3419.field_15245, 0.75f, 0.95f + world.field_9229.method_43057() * 0.1f);
            garrison.removeStoredTaxEmeralds(stackSize);
            state.remaining -= (long)stackSize;
            state.nextDropTick = now + 1L;
            manager.markTaxDataDirty();
            if (state.remaining > 0L && garrison.getStoredTaxEmeralds() > 0L) continue;
            iterator.remove();
        }
    }

    private static boolean canCollect(class_3222 player, class_3218 world, GarrisonData garrison) {
        return CastleAccessPolicy.isKingdomMember(world, player, garrison);
    }

    private static final class PayoutState {
        private final UUID castleId;
        private final class_2338 chestPos;
        private long remaining;
        private long nextDropTick;

        private PayoutState(UUID castleId, class_2338 chestPos, long remaining, long nextDropTick) {
            this.castleId = castleId;
            this.chestPos = chestPos;
            this.remaining = remaining;
            this.nextDropTick = nextDropTick;
        }
    }
}

