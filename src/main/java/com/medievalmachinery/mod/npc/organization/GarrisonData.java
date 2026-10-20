/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.CastleAreaService;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import org.jetbrains.annotations.Nullable;

public final class GarrisonData {
    private final UUID id;
    private UUID ownerId;
    @Nullable
    private UUID kingdomId;
    private String name;
    private OrderStyle orderStyle = OrderStyle.NONE;
    @Nullable
    private class_2338 center;
    @Nullable
    private String centerDimension;
    @Nullable
    private class_2338 rallyPoint;
    private final Set<UUID> soldierIds = new LinkedHashSet<UUID>();
    private final Set<class_2338> supplyChestPositions = new LinkedHashSet<class_2338>();
    private final Set<class_2338> taxChestPositions = new LinkedHashSet<class_2338>();
    private long storedTaxEmeralds;
    private long lastTaxDay = Long.MIN_VALUE;
    private long lastWatchpostLoadedTaxDay = Long.MIN_VALUE;
    private long taxActiveTicks;
    private long taxPayoutCount;
    private long taxLastGameTime = Long.MIN_VALUE;
    private int lastKnownAdultVillagerCount;
    private final List<class_2338> boundaryPoints = new ArrayList<class_2338>();
    private final Map<Long, Integer> taxVillagersByChunk = new LinkedHashMap<Long, Integer>();
    private boolean taxChunkCensusAuthoritative;

    public GarrisonData(UUID id, UUID ownerId, @Nullable UUID kingdomId, String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.kingdomId = kingdomId;
        this.name = name;
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getCastleId() {
        return this.id;
    }

    public UUID getOwnerId() {
        return this.ownerId;
    }

    @Nullable
    public UUID getKingdomId() {
        return this.kingdomId;
    }

    public String getName() {
        return this.name;
    }

    public Set<UUID> getSoldierIds() {
        return Set.copyOf(this.soldierIds);
    }

    public int getSoldierCount() {
        return this.soldierIds.size();
    }

    public OrderStyle getOrderStyle() {
        return this.orderStyle;
    }

    @Nullable
    public class_2338 getCenter() {
        return this.center;
    }

    @Nullable
    public String getCenterDimension() {
        return this.centerDimension;
    }

    @Nullable
    public class_2338 getRallyPoint() {
        return this.rallyPoint;
    }

    public boolean addSoldier(UUID id) {
        return this.soldierIds.add(id);
    }

    public boolean removeSoldier(UUID id) {
        return this.soldierIds.remove(id);
    }

    public Set<class_2338> getSupplyChestPositions() {
        return Set.copyOf(this.supplyChestPositions);
    }

    public boolean addSupplyChest(class_2338 pos) {
        return pos != null && this.supplyChestPositions.add(pos.method_10062());
    }

    public boolean removeSupplyChest(class_2338 pos) {
        return pos != null && this.supplyChestPositions.remove(pos);
    }

    public Set<class_2338> getTaxChestPositions() {
        return Set.copyOf(this.taxChestPositions);
    }

    public boolean addTaxChest(class_2338 pos) {
        return pos != null && this.taxChestPositions.add(pos.method_10062());
    }

    public boolean removeTaxChest(class_2338 pos) {
        return pos != null && this.taxChestPositions.remove(pos);
    }

    public long getStoredTaxEmeralds() {
        return this.storedTaxEmeralds;
    }

    public long getLastTaxDay() {
        return this.lastTaxDay;
    }

    public long getLastWatchpostLoadedTaxDay() {
        return this.lastWatchpostLoadedTaxDay;
    }

    public long getTaxActiveTicks() {
        return this.taxActiveTicks;
    }

    public long getTaxPayoutCount() {
        return this.taxPayoutCount;
    }

    public long getTaxLastGameTime() {
        return this.taxLastGameTime;
    }

    public int getLastKnownAdultVillagerCount() {
        return this.lastKnownAdultVillagerCount;
    }

    public void setLastKnownAdultVillagerCount(int value) {
        this.lastKnownAdultVillagerCount = Math.max(0, value);
    }

    public List<class_2338> getBoundaryPoints() {
        return List.copyOf(this.boundaryPoints);
    }

    void setBoundaryPoints(List<class_2338> points) {
        this.boundaryPoints.clear();
        if (points != null) {
            for (class_2338 point : points) {
                if (point == null) continue;
                this.boundaryPoints.add(point.method_10062());
            }
        }
    }

    public Map<Long, Integer> getTaxVillagersByChunk() {
        return Map.copyOf(this.taxVillagersByChunk);
    }

    public Integer getTaxChunkVillagerCount(long chunkKey) {
        return this.taxVillagersByChunk.get(chunkKey);
    }

    public int getTaxChunkCensusSize() {
        return this.taxVillagersByChunk.size();
    }

    public boolean hasTaxChunkCensusForAll(Set<Long> chunkKeys) {
        return this.taxVillagersByChunk.keySet().containsAll(chunkKeys);
    }

    public void setTaxChunkVillagerCount(long chunkKey, int value) {
        this.taxVillagersByChunk.put(chunkKey, Math.max(0, value));
    }

    public boolean retainTaxChunkCensus(Set<Long> chunkKeys) {
        int before = this.taxVillagersByChunk.size();
        this.taxVillagersByChunk.keySet().retainAll(chunkKeys);
        return this.taxVillagersByChunk.size() != before;
    }

    public void clearTaxChunkCensus() {
        this.taxVillagersByChunk.clear();
        this.taxChunkCensusAuthoritative = false;
        this.lastKnownAdultVillagerCount = 0;
    }

    public boolean isTaxChunkCensusAuthoritative() {
        return this.taxChunkCensusAuthoritative;
    }

    public void setTaxChunkCensusAuthoritative(boolean value) {
        this.taxChunkCensusAuthoritative = value;
    }

    public int getTaxChunkCensusTotal() {
        int total = 0;
        for (int value : this.taxVillagersByChunk.values()) {
            total += Math.max(0, value);
        }
        return total;
    }

    public void setLastTaxDay(long value) {
        this.lastTaxDay = value;
    }

    public void setLastWatchpostLoadedTaxDay(long value) {
        this.lastWatchpostLoadedTaxDay = value;
    }

    public void setTaxLastGameTime(long value) {
        this.taxLastGameTime = value;
    }

    public void setTaxPayoutCount(long value) {
        this.taxPayoutCount = Math.max(0L, value);
    }

    public void addTaxActiveTicks(long amount) {
        if (amount <= 0L) {
            return;
        }
        this.taxActiveTicks = Math.max(0L, this.taxActiveTicks + amount);
    }

    public void addStoredTaxEmeralds(long amount) {
        if (amount <= 0L) {
            return;
        }
        this.storedTaxEmeralds = Math.min(Integer.MAX_VALUE, this.storedTaxEmeralds + amount);
    }

    public long withdrawStoredTaxEmeralds() {
        long amount = this.storedTaxEmeralds;
        this.storedTaxEmeralds = 0L;
        return amount;
    }

    public long removeStoredTaxEmeralds(long amount) {
        if (amount <= 0L || this.storedTaxEmeralds <= 0L) {
            return 0L;
        }
        long removed = Math.min(amount, this.storedTaxEmeralds);
        this.storedTaxEmeralds -= removed;
        return removed;
    }

    public void setOwnerId(UUID value) {
        if (value != null) {
            this.ownerId = value;
        }
    }

    public void setKingdomId(@Nullable UUID value) {
        this.kingdomId = value;
    }

    public void setName(String name) {
        if (name == null) {
            return;
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return;
        }
        this.name = trimmed.length() > 48 ? trimmed.substring(0, 48) : trimmed;
    }

    public void setOrderStyle(OrderStyle style) {
        this.orderStyle = style == null ? OrderStyle.NONE : style;
    }

    public void setCenter(@Nullable class_2338 value) {
        this.center = value == null ? null : value.method_10062();
    }

    public void setCenterDimension(@Nullable String value) {
        this.centerDimension = value == null || value.isBlank() ? null : value;
    }

    public void setRallyPoint(@Nullable class_2338 value) {
        this.rallyPoint = value == null ? null : value.method_10062();
    }

    /*
     * WARNING - void declaration
     */
    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        nbt.method_25927("CastleId", this.id);
        nbt.method_25927("Owner", this.ownerId);
        if (this.kingdomId != null) {
            nbt.method_25927("KingdomId", this.kingdomId);
        }
        nbt.method_10582("Name", this.name);
        nbt.method_10582("OrderStyle", this.orderStyle.method_15434());
        if (this.center != null) {
            nbt.method_10569("CenterX", this.center.method_10263());
            nbt.method_10569("CenterY", this.center.method_10264());
            nbt.method_10569("CenterZ", this.center.method_10260());
        }
        if (this.centerDimension != null) {
            nbt.method_10582("CenterDimension", this.centerDimension);
        }
        if (this.rallyPoint != null) {
            nbt.method_10569("RallyX", this.rallyPoint.method_10263());
            nbt.method_10569("RallyY", this.rallyPoint.method_10264());
            nbt.method_10569("RallyZ", this.rallyPoint.method_10260());
        }
        int soldierIndex = 0;
        for (UUID uUID : this.soldierIds) {
            nbt.method_25927("Soldier" + soldierIndex++, uUID);
        }
        nbt.method_10569("SoldierCount", soldierIndex);
        int supplyIndex = 0;
        for (class_2338 class_23382 : this.supplyChestPositions) {
            nbt.method_10569("SupplyX" + supplyIndex, class_23382.method_10263());
            nbt.method_10569("SupplyY" + supplyIndex, class_23382.method_10264());
            nbt.method_10569("SupplyZ" + supplyIndex, class_23382.method_10260());
            ++supplyIndex;
        }
        nbt.method_10569("SupplyCount", supplyIndex);
        int taxIndex = 0;
        for (class_2338 taxPos : this.taxChestPositions) {
            nbt.method_10569("TaxX" + taxIndex, taxPos.method_10263());
            nbt.method_10569("TaxY" + taxIndex, taxPos.method_10264());
            nbt.method_10569("TaxZ" + taxIndex, taxPos.method_10260());
            ++taxIndex;
        }
        nbt.method_10569("TaxCount", taxIndex);
        nbt.method_10544("StoredTaxEmeralds", this.storedTaxEmeralds);
        nbt.method_10544("LastTaxDay", this.lastTaxDay);
        nbt.method_10544("LastWatchpostLoadedTaxDay", this.lastWatchpostLoadedTaxDay);
        nbt.method_10544("TaxActiveTicks", this.taxActiveTicks);
        nbt.method_10544("TaxPayoutCount", this.taxPayoutCount);
        nbt.method_10544("TaxLastGameTime", this.taxLastGameTime);
        nbt.method_10569("LastKnownAdultVillagerCount", this.lastKnownAdultVillagerCount);
        nbt.method_10569("BoundaryPointCount", this.boundaryPoints.size());
        for (int boundaryIndex = 0; boundaryIndex < this.boundaryPoints.size(); ++boundaryIndex) {
            class_2338 point = this.boundaryPoints.get(boundaryIndex);
            nbt.method_10569("BoundaryX" + boundaryIndex, point.method_10263());
            nbt.method_10569("BoundaryY" + boundaryIndex, point.method_10264());
            nbt.method_10569("BoundaryZ" + boundaryIndex, point.method_10260());
        }
        class_2487 class_24872 = new class_2487();
        int censusIndex = 0;
        for (Map.Entry<Long, Integer> entry : this.taxVillagersByChunk.entrySet()) {
            class_24872.method_10544("Chunk" + censusIndex, entry.getKey().longValue());
            class_24872.method_10569("Count" + censusIndex, entry.getValue().intValue());
            ++censusIndex;
        }
        class_24872.method_10569("Size", censusIndex);
        class_24872.method_10556("Authoritative", this.taxChunkCensusAuthoritative);
        nbt.method_10566("TaxChunkCensus", (class_2520)class_24872);
        return nbt;
    }

    public static GarrisonData fromNbt(class_2487 nbt) {
        UUID id = nbt.method_25926("CastleId");
        UUID kingdom = nbt.method_25928("KingdomId") ? nbt.method_25926("KingdomId") : null;
        GarrisonData data = new GarrisonData(id, nbt.method_25926("Owner"), kingdom, nbt.method_10558("Name"));
        data.orderStyle = OrderStyle.fromSerializedName(nbt.method_10558("OrderStyle"));
        if (nbt.method_10573("CenterX", 3) && nbt.method_10573("CenterY", 3) && nbt.method_10573("CenterZ", 3)) {
            data.center = new class_2338(nbt.method_10550("CenterX"), nbt.method_10550("CenterY"), nbt.method_10550("CenterZ"));
        }
        if (nbt.method_10573("CenterDimension", 8)) {
            data.centerDimension = nbt.method_10558("CenterDimension");
        }
        if (nbt.method_10573("RallyX", 3) && nbt.method_10573("RallyY", 3) && nbt.method_10573("RallyZ", 3)) {
            data.rallyPoint = new class_2338(nbt.method_10550("RallyX"), nbt.method_10550("RallyY"), nbt.method_10550("RallyZ"));
        }
        int count = Math.max(0, nbt.method_10550("SoldierCount"));
        for (int i = 0; i < count; ++i) {
            String key = "Soldier" + i;
            if (!nbt.method_25928(key)) continue;
            data.soldierIds.add(nbt.method_25926(key));
        }
        int supplyCount = Math.max(0, nbt.method_10550("SupplyCount"));
        for (int i = 0; i < supplyCount; ++i) {
            String x = "SupplyX" + i;
            String y = "SupplyY" + i;
            String z = "SupplyZ" + i;
            if (!nbt.method_10573(x, 3) || !nbt.method_10573(y, 3) || !nbt.method_10573(z, 3)) continue;
            data.supplyChestPositions.add(new class_2338(nbt.method_10550(x), nbt.method_10550(y), nbt.method_10550(z)));
        }
        int taxCount = Math.max(0, nbt.method_10550("TaxCount"));
        for (int i = 0; i < taxCount; ++i) {
            String x = "TaxX" + i;
            String y = "TaxY" + i;
            String z = "TaxZ" + i;
            if (!nbt.method_10573(x, 3) || !nbt.method_10573(y, 3) || !nbt.method_10573(z, 3)) continue;
            data.taxChestPositions.add(new class_2338(nbt.method_10550(x), nbt.method_10550(y), nbt.method_10550(z)));
        }
        data.storedTaxEmeralds = Math.max(0L, nbt.method_10537("StoredTaxEmeralds"));
        data.lastTaxDay = nbt.method_10573("LastTaxDay", 4) ? nbt.method_10537("LastTaxDay") : Long.MIN_VALUE;
        data.lastWatchpostLoadedTaxDay = nbt.method_10573("LastWatchpostLoadedTaxDay", 4) ? nbt.method_10537("LastWatchpostLoadedTaxDay") : Long.MIN_VALUE;
        data.taxActiveTicks = nbt.method_10573("TaxActiveTicks", 4) ? Math.max(0L, nbt.method_10537("TaxActiveTicks")) : 0L;
        data.taxPayoutCount = nbt.method_10573("TaxPayoutCount", 4) ? Math.max(0L, nbt.method_10537("TaxPayoutCount")) : 0L;
        data.taxLastGameTime = nbt.method_10573("TaxLastGameTime", 4) ? nbt.method_10537("TaxLastGameTime") : Long.MIN_VALUE;
        data.lastKnownAdultVillagerCount = Math.max(0, nbt.method_10550("LastKnownAdultVillagerCount"));
        int boundaryCount = Math.max(0, Math.min(24, nbt.method_10550("BoundaryPointCount")));
        for (int i = 0; i < boundaryCount; ++i) {
            String x = "BoundaryX" + i;
            String y = "BoundaryY" + i;
            String z = "BoundaryZ" + i;
            if (!nbt.method_10573(x, 3) || !nbt.method_10573(y, 3) || !nbt.method_10573(z, 3)) continue;
            data.boundaryPoints.add(new class_2338(nbt.method_10550(x), nbt.method_10550(y), nbt.method_10550(z)));
        }
        if (!data.boundaryPoints.isEmpty()) {
            data.setBoundaryPoints(CastleAreaService.sanitizeBoundary(data, new ArrayList<class_2338>(data.boundaryPoints)));
        }
        if (nbt.method_10573("TaxChunkCensus", 10)) {
            class_2487 census = nbt.method_10562("TaxChunkCensus");
            int censusSize = Math.max(0, census.method_10550("Size"));
            for (int i = 0; i < censusSize; ++i) {
                String chunk = "Chunk" + i;
                String countKey = "Count" + i;
                if (!census.method_10573(chunk, 4) || !census.method_10573(countKey, 3)) continue;
                data.taxVillagersByChunk.put(census.method_10537(chunk), Math.max(0, census.method_10550(countKey)));
            }
            data.taxChunkCensusAuthoritative = census.method_10577("Authoritative");
        }
        return data;
    }
}

