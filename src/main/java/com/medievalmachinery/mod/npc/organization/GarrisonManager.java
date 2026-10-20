/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_18
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.CastleAreaService;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.class_18;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class GarrisonManager
extends class_18 {
    private static final String SAVE_ID = "medieval-machinery_garrisons";
    private final Map<UUID, GarrisonData> garrisons = new LinkedHashMap<UUID, GarrisonData>();
    private boolean pruned = false;

    public static GarrisonManager get(class_3218 world) {
        class_3218 overworld = world.method_8503().method_30002();
        GarrisonManager manager = (GarrisonManager)overworld.method_17983().method_17924(GarrisonManager::fromNbt, GarrisonManager::new, SAVE_ID);
        if (!manager.pruned) {
            manager.pruned = true;
            manager.pruneDeadSoldiers(SoldierRegistryManager.get(overworld));
        }
        return manager;
    }

    public GarrisonData create(UUID ownerId, @Nullable UUID kingdomId, String name) {
        return this.create(ownerId, kingdomId, name, null);
    }

    public GarrisonData create(UUID ownerId, @Nullable UUID kingdomId, String name, @Nullable class_2338 center) {
        GarrisonData garrison = new GarrisonData(UUID.randomUUID(), ownerId, kingdomId, name);
        garrison.setCenter(center);
        this.garrisons.put(garrison.getId(), garrison);
        this.method_80();
        return garrison;
    }

    @Nullable
    public GarrisonData findFirstByOwner(UUID ownerId) {
        for (GarrisonData garrison : this.garrisons.values()) {
            if (!garrison.getOwnerId().equals(ownerId)) continue;
            return garrison;
        }
        return null;
    }

    public GarrisonData getOrCreateDefault(UUID ownerId, @Nullable UUID kingdomId, String playerName) {
        GarrisonData existing = this.findFirstByOwner(ownerId);
        if (existing != null) {
            if (existing.getKingdomId() == null && kingdomId != null) {
                existing.setKingdomId(kingdomId);
                this.method_80();
            }
            return existing;
        }
        return this.create(ownerId, kingdomId, "Garnison " + playerName);
    }

    public boolean rename(UUID castleId, String name) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null) {
            return false;
        }
        garrison.setName(name);
        this.method_80();
        return true;
    }

    public void clearKingdom(UUID kingdomId) {
        boolean changed = false;
        for (GarrisonData garrison : this.garrisons.values()) {
            if (!kingdomId.equals(garrison.getKingdomId())) continue;
            garrison.setKingdomId(null);
            garrison.setOrderStyle(OrderStyle.NONE);
            changed = true;
        }
        if (changed) {
            this.method_80();
        }
    }

    public void transferOwnership(UUID castleId, UUID newOwnerId, @Nullable UUID newKingdomId, OrderStyle style) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null || newOwnerId == null) {
            return;
        }
        garrison.setOwnerId(newOwnerId);
        garrison.setKingdomId(newKingdomId);
        garrison.setOrderStyle(style);
        this.method_80();
    }

    public void setCastleContext(UUID castleId, @Nullable UUID kingdomId, OrderStyle style) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null) {
            return;
        }
        garrison.setKingdomId(kingdomId);
        garrison.setOrderStyle(style);
        this.method_80();
    }

    public void setOrderStyle(UUID garrisonId, OrderStyle style) {
        GarrisonData garrison = this.garrisons.get(garrisonId);
        if (garrison != null && garrison.getOrderStyle() != style) {
            garrison.setOrderStyle(style);
            this.method_80();
        }
    }

    public void registerSoldier(UUID garrisonId, UUID soldierId) {
        GarrisonData garrison = this.garrisons.get(garrisonId);
        if (garrison != null && garrison.addSoldier(soldierId)) {
            this.method_80();
        }
    }

    public void unregisterSoldier(UUID garrisonId, UUID soldierId) {
        GarrisonData garrison = this.garrisons.get(garrisonId);
        if (garrison != null && garrison.removeSoldier(soldierId)) {
            this.method_80();
        }
    }

    public boolean removeSoldierEverywhere(UUID soldierId) {
        if (soldierId == null) {
            return false;
        }
        boolean changed = false;
        for (GarrisonData garrison : this.garrisons.values()) {
            changed |= garrison.removeSoldier(soldierId);
        }
        if (changed) {
            this.method_80();
        }
        return changed;
    }

    public void pruneDeadSoldiers(SoldierRegistryManager registry) {
        if (registry == null) {
            return;
        }
        boolean changed = false;
        for (GarrisonData garrison : this.garrisons.values()) {
            List<UUID> toRemove = new ArrayList<UUID>();
            for (UUID soldierId : garrison.getSoldierIds()) {
                if (registry.get(soldierId) == null) {
                    toRemove.add(soldierId);
                }
            }
            for (UUID deadId : toRemove) {
                changed |= garrison.removeSoldier(deadId);
            }
        }
        if (changed) {
            this.method_80();
        }
    }

    public int getSoldierCount(UUID garrisonId) {
        GarrisonData garrison = this.garrisons.get(garrisonId);
        return garrison == null ? 0 : garrison.getSoldierCount();
    }

    public boolean addSupplyChest(UUID castleId, class_2338 pos) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison != null && garrison.addSupplyChest(pos)) {
            this.method_80();
            return true;
        }
        return false;
    }

    public boolean removeSupplyChest(UUID castleId, class_2338 pos) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison != null && garrison.removeSupplyChest(pos)) {
            this.method_80();
            return true;
        }
        return false;
    }

    public boolean removeSupplyChestEverywhere(class_2338 pos) {
        if (pos == null) {
            return false;
        }
        boolean changed = false;
        for (GarrisonData garrison : this.garrisons.values()) {
            changed |= garrison.removeSupplyChest(pos);
        }
        if (changed) {
            this.method_80();
        }
        return changed;
    }

    @Nullable
    public GarrisonData findBySupplyChest(class_2338 pos, @Nullable String dimensionId) {
        if (pos == null) {
            return null;
        }
        for (GarrisonData garrison : this.garrisons.values()) {
            if (dimensionId != null && garrison.getCenterDimension() != null && !dimensionId.equals(garrison.getCenterDimension()) || !garrison.getSupplyChestPositions().contains(pos)) continue;
            return garrison;
        }
        return null;
    }

    public boolean addTaxChest(UUID castleId, class_2338 pos) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison != null && garrison.addTaxChest(pos)) {
            this.method_80();
            return true;
        }
        return false;
    }

    public boolean clearTaxChests(UUID castleId) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null || garrison.getTaxChestPositions().isEmpty()) {
            return false;
        }
        boolean changed = false;
        for (class_2338 pos : garrison.getTaxChestPositions()) {
            changed |= garrison.removeTaxChest(pos);
        }
        if (changed) {
            this.method_80();
        }
        return changed;
    }

    public boolean removeTaxChest(UUID castleId, class_2338 pos) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison != null && garrison.removeTaxChest(pos)) {
            this.method_80();
            return true;
        }
        return false;
    }

    public boolean removeTaxChestEverywhere(class_2338 pos) {
        if (pos == null) {
            return false;
        }
        boolean changed = false;
        for (GarrisonData garrison : this.garrisons.values()) {
            changed |= garrison.removeTaxChest(pos);
        }
        if (changed) {
            this.method_80();
        }
        return changed;
    }

    public boolean removeTaxChestEverywhere(class_2338 pos, @Nullable String dimensionId) {
        if (pos == null) {
            return false;
        }
        boolean changed = false;
        for (GarrisonData garrison : this.garrisons.values()) {
            if (dimensionId != null && garrison.getCenterDimension() != null && !dimensionId.equals(garrison.getCenterDimension())) continue;
            changed |= garrison.removeTaxChest(pos);
        }
        if (changed) {
            this.method_80();
        }
        return changed;
    }

    @Nullable
    public GarrisonData findByTaxChest(class_2338 pos, @Nullable String dimensionId) {
        if (pos == null) {
            return null;
        }
        for (GarrisonData garrison : this.garrisons.values()) {
            if (dimensionId != null && garrison.getCenterDimension() != null && !dimensionId.equals(garrison.getCenterDimension()) || !garrison.getTaxChestPositions().contains(pos)) continue;
            return garrison;
        }
        return null;
    }

    public void markTaxDataDirty() {
        this.method_80();
    }

    @Nullable
    public GarrisonData get(UUID id) {
        return this.garrisons.get(id);
    }

    public Collection<GarrisonData> getAll() {
        return List.copyOf(this.garrisons.values());
    }

    public boolean reassignSoldier(UUID soldierId, @Nullable UUID sourceCastleId, UUID targetCastleId) {
        GarrisonData source;
        if (soldierId == null || targetCastleId == null) {
            return false;
        }
        GarrisonData target = this.garrisons.get(targetCastleId);
        if (target == null) {
            return false;
        }
        boolean changed = false;
        if (sourceCastleId != null && (source = this.garrisons.get(sourceCastleId)) != null && !sourceCastleId.equals(targetCastleId)) {
            changed |= source.removeSoldier(soldierId);
        }
        if (changed |= target.addSoldier(soldierId)) {
            this.method_80();
        }
        return changed;
    }

    public void setCenter(UUID castleId, @Nullable class_2338 center) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null) {
            return;
        }
        garrison.setCenter(center);
        this.method_80();
    }

    public boolean addBoundaryPoint(UUID castleId, class_2338 point) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null || !CastleAreaService.canAddBoundaryPoint(garrison, point)) {
            return false;
        }
        ArrayList<class_2338> points = new ArrayList<class_2338>(garrison.getBoundaryPoints());
        class_2338 immutable = point.method_10062();
        for (class_2338 existing : points) {
            if (existing.method_10263() != immutable.method_10263() || existing.method_10260() != immutable.method_10260()) continue;
            return false;
        }
        points.add(immutable);
        garrison.setBoundaryPoints(points);
        garrison.clearTaxChunkCensus();
        this.method_80();
        return true;
    }

    public boolean removeBoundaryPointNear(UUID castleId, class_2338 point, int maxHorizontalDistance) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null || point == null || garrison.getBoundaryPoints().isEmpty()) {
            return false;
        }
        ArrayList<class_2338> points = new ArrayList<class_2338>(garrison.getBoundaryPoints());
        int bestIndex = -1;
        long bestDistanceSq = (long)maxHorizontalDistance * (long)maxHorizontalDistance + 1L;
        for (int i = 0; i < points.size(); ++i) {
            long dz;
            class_2338 candidate = (class_2338)points.get(i);
            long dx = (long)candidate.method_10263() - (long)point.method_10263();
            long distanceSq = dx * dx + (dz = (long)candidate.method_10260() - (long)point.method_10260()) * dz;
            if (distanceSq >= bestDistanceSq) continue;
            bestDistanceSq = distanceSq;
            bestIndex = i;
        }
        if (bestIndex < 0 || bestDistanceSq > (long)maxHorizontalDistance * (long)maxHorizontalDistance) {
            return false;
        }
        points.remove(bestIndex);
        garrison.setBoundaryPoints(points);
        garrison.clearTaxChunkCensus();
        this.method_80();
        return true;
    }

    public boolean clearBoundary(UUID castleId) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null || garrison.getBoundaryPoints().isEmpty()) {
            return false;
        }
        garrison.setBoundaryPoints(List.of());
        garrison.clearTaxChunkCensus();
        this.method_80();
        return true;
    }

    public boolean setRallyPoint(UUID castleId, @Nullable class_2338 rallyPoint) {
        class_2338 next;
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null) {
            return false;
        }
        class_2338 old = garrison.getRallyPoint();
        class_2338 class_23382 = next = rallyPoint == null ? null : rallyPoint.method_10062();
        if (Objects.equals(old, next)) {
            return false;
        }
        garrison.setRallyPoint(next);
        this.method_80();
        return true;
    }

    public void setCenter(UUID castleId, @Nullable class_2338 center, @Nullable String dimensionId) {
        GarrisonData garrison = this.garrisons.get(castleId);
        if (garrison == null) {
            return;
        }
        garrison.setCenter(center);
        garrison.setCenterDimension(dimensionId);
        this.method_80();
    }

    public boolean remove(UUID id) {
        if (this.garrisons.remove(id) != null) {
            this.method_80();
            return true;
        }
        return false;
    }

    public class_2487 method_75(class_2487 nbt) {
        class_2499 list = new class_2499();
        for (GarrisonData garrison : this.garrisons.values()) {
            list.add(garrison.toNbt());
        }
        nbt.method_10566("Garrisons", (class_2520)list);
        return nbt;
    }

    private static GarrisonManager fromNbt(class_2487 nbt) {
        GarrisonManager manager = new GarrisonManager();
        class_2499 list = nbt.method_10554("Garrisons", 10);
        for (int i = 0; i < list.size(); ++i) {
            GarrisonData garrison = GarrisonData.fromNbt(list.method_10602(i));
            manager.garrisons.put(garrison.getId(), garrison);
        }
        return manager;
    }
}

