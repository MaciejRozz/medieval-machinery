/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_18
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.core.NpcEquipmentProfile;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.class_18;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class SoldierRegistryManager
extends class_18 {
    private static final String SAVE_ID = "medieval-machinery_soldier_registry";
    private final Map<UUID, SoldierRegistryData> soldiers = new LinkedHashMap<UUID, SoldierRegistryData>();

    public static SoldierRegistryManager get(class_3218 world) {
        class_3218 overworld = world.method_8503().method_30002();
        return (SoldierRegistryManager)overworld.method_17983().method_17924(SoldierRegistryManager::fromNbt, SoldierRegistryManager::new, SAVE_ID);
    }

    @Nullable
    public SoldierRegistryData get(UUID soldierId) {
        return soldierId == null ? null : this.soldiers.get(soldierId);
    }

    public List<SoldierRegistryData> getFor(UUID castleId, UUID ownerId) {
        ArrayList<SoldierRegistryData> result = new ArrayList<SoldierRegistryData>();
        for (SoldierRegistryData data : this.soldiers.values()) {
            if (ownerId != null && !ownerId.equals(data.getOwnerId()) || castleId != null && !castleId.equals(data.getCastleId())) continue;
            result.add(data);
        }
        result.sort(Comparator.comparing(SoldierRegistryData::getName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    public void update(MedievalSoldierEntity soldier) {
        boolean changed;
        SoldierRegistryData data = this.soldiers.get(soldier.method_5667());
        boolean created = false;
        if (data == null) {
            data = new SoldierRegistryData(soldier.method_5667());
            this.soldiers.put(soldier.method_5667(), data);
            created = true;
        }
        UUID ownerId = soldier.getNpcIdentity().getOwnerId();
        UUID castleId = soldier.getNpcIdentity().getCastleId();
        UUID groupId = soldier.getNpcIdentity().getSquadId();
        String name = soldier.method_16914() && soldier.method_5797() != null ? soldier.method_5797().getString() : "Soldat";
        String role = soldier.getNpcRole().serializedName();
        String rank = soldier.getNpcRank().serializedName();
        int level = soldier.getNpcLevel();
        float health = soldier.method_6032();
        float maxHealth = soldier.method_6063();
        boolean hasFood = soldier.hasFoodCached();
        boolean hasWeapon = NpcEquipmentProfile.isCombatReady(soldier.getNpcRole(), soldier.method_6047());
        boolean bl = changed = created || !Objects.equals(data.getOwnerId(), ownerId) || !Objects.equals(data.getCastleId(), castleId) || !Objects.equals(data.getGroupId(), groupId) || !Objects.equals(data.getName(), name) || !Objects.equals(data.getRole(), role) || !Objects.equals(data.getRank(), rank) || data.getLevel() != level || Float.compare(data.getHealth(), health) != 0 || Float.compare(data.getMaxHealth(), maxHealth) != 0 || data.hasFood() != hasFood || data.hasWeapon() != hasWeapon;
        if (!changed) {
            return;
        }
        data.setOwnerId(ownerId);
        data.setCastleId(castleId);
        data.setGroupId(groupId);
        data.setName(name);
        data.setRole(role);
        data.setRank(rank);
        data.setLevel(level);
        data.setHealth(health);
        data.setMaxHealth(maxHealth);
        data.setHasFood(hasFood);
        data.setHasWeapon(hasWeapon);
        this.method_80();
    }

    public void updateMembership(UUID soldierId, UUID ownerId, @Nullable UUID castleId, @Nullable UUID groupId) {
        boolean changed;
        SoldierRegistryData data = this.soldiers.get(soldierId);
        boolean created = false;
        if (data == null) {
            data = new SoldierRegistryData(soldierId);
            this.soldiers.put(soldierId, data);
            created = true;
        }
        UUID effectiveOwner = ownerId != null ? ownerId : data.getOwnerId();
        boolean bl = changed = created || !Objects.equals(data.getOwnerId(), effectiveOwner) || !Objects.equals(data.getCastleId(), castleId) || !Objects.equals(data.getGroupId(), groupId);
        if (!changed) {
            return;
        }
        if (effectiveOwner != null) {
            data.setOwnerId(effectiveOwner);
        }
        data.setCastleId(castleId);
        data.setGroupId(groupId);
        this.method_80();
    }

    public Set<UUID> clearCastle(UUID castleId) {
        if (castleId == null) {
            return Set.of();
        }
        LinkedHashSet<UUID> affected = new LinkedHashSet<UUID>();
        for (SoldierRegistryData data : this.soldiers.values()) {
            if (!castleId.equals(data.getCastleId())) continue;
            data.setCastleId(null);
            affected.add(data.getSoldierId());
        }
        if (!affected.isEmpty()) {
            this.method_80();
        }
        return affected;
    }

    public boolean hasLivingGeneral(UUID castleId) {
        if (castleId == null) {
            return false;
        }
        for (SoldierRegistryData data : this.soldiers.values()) {
            if (!castleId.equals(data.getCastleId()) || data.getHealth() <= 0.0f || !"general".equals(data.getRank())) continue;
            return true;
        }
        return false;
    }

    public List<SoldierRegistryData> getUnassignedFor(UUID ownerId) {
        ArrayList<SoldierRegistryData> result = new ArrayList<SoldierRegistryData>();
        for (SoldierRegistryData data : this.soldiers.values()) {
            if (ownerId != null && !ownerId.equals(data.getOwnerId()) || data.getCastleId() != null) continue;
            result.add(data);
        }
        result.sort(Comparator.comparing(SoldierRegistryData::getName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    public void remove(UUID soldierId) {
        if (soldierId != null && this.soldiers.remove(soldierId) != null) {
            this.method_80();
        }
    }

    public class_2487 method_75(class_2487 nbt) {
        class_2499 list = new class_2499();
        for (SoldierRegistryData data : this.soldiers.values()) {
            list.add((Object)data.toNbt());
        }
        nbt.method_10566("Soldiers", (class_2520)list);
        return nbt;
    }

    private static SoldierRegistryManager fromNbt(class_2487 nbt) {
        SoldierRegistryManager manager = new SoldierRegistryManager();
        class_2499 list = nbt.method_10554("Soldiers", 10);
        for (int i = 0; i < list.size(); ++i) {
            SoldierRegistryData data = SoldierRegistryData.fromNbt(list.method_10602(i));
            manager.soldiers.put(data.getSoldierId(), data);
        }
        return manager;
    }
}

