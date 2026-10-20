/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2487
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import java.util.UUID;
import net.minecraft.class_2487;
import org.jetbrains.annotations.Nullable;

public final class SoldierRegistryData {
    private final UUID soldierId;
    private UUID ownerId;
    @Nullable
    private UUID castleId;
    @Nullable
    private UUID groupId;
    private String name = "Soldat";
    private String role = "recruit";
    private String rank = "none";
    private int level = 1;
    private float health = 20.0f;
    private float maxHealth = 20.0f;
    private boolean hasFood;
    private boolean hasWeapon;

    public SoldierRegistryData(UUID soldierId) {
        this.soldierId = soldierId;
    }

    public UUID getSoldierId() {
        return this.soldierId;
    }

    public UUID getOwnerId() {
        return this.ownerId;
    }

    @Nullable
    public UUID getCastleId() {
        return this.castleId;
    }

    @Nullable
    public UUID getGroupId() {
        return this.groupId;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public String getRank() {
        return this.rank;
    }

    public int getLevel() {
        return Math.max(1, this.level);
    }

    public float getHealth() {
        return this.health;
    }

    public float getMaxHealth() {
        return this.maxHealth;
    }

    public boolean hasFood() {
        return this.hasFood;
    }

    public boolean hasWeapon() {
        return this.hasWeapon;
    }

    public void setOwnerId(UUID value) {
        this.ownerId = value;
    }

    public void setCastleId(@Nullable UUID value) {
        this.castleId = value;
    }

    public void setGroupId(@Nullable UUID value) {
        this.groupId = value;
    }

    public void setName(String value) {
        this.name = value == null || value.isBlank() ? "Soldat" : value;
    }

    public void setRole(String value) {
        this.role = value == null || value.isBlank() ? "recruit" : value;
    }

    public void setRank(String value) {
        this.rank = value == null || value.isBlank() ? "none" : value;
    }

    public void setLevel(int value) {
        this.level = Math.max(1, value);
    }

    public void setHealth(float value) {
        this.health = Math.max(0.0f, value);
    }

    public void setMaxHealth(float value) {
        this.maxHealth = Math.max(1.0f, value);
    }

    public void setHasFood(boolean value) {
        this.hasFood = value;
    }

    public void setHasWeapon(boolean value) {
        this.hasWeapon = value;
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        nbt.method_25927("SoldierId", this.soldierId);
        if (this.ownerId != null) {
            nbt.method_25927("OwnerId", this.ownerId);
        }
        if (this.castleId != null) {
            nbt.method_25927("CastleId", this.castleId);
        }
        if (this.groupId != null) {
            nbt.method_25927("GroupId", this.groupId);
        }
        nbt.method_10582("Name", this.name);
        nbt.method_10582("Role", this.role);
        nbt.method_10582("Rank", this.rank);
        nbt.method_10569("Level", this.getLevel());
        nbt.method_10548("Health", this.health);
        nbt.method_10548("MaxHealth", this.maxHealth);
        nbt.method_10556("HasFood", this.hasFood);
        nbt.method_10556("HasWeapon", this.hasWeapon);
        return nbt;
    }

    public static SoldierRegistryData fromNbt(class_2487 nbt) {
        SoldierRegistryData data = new SoldierRegistryData(nbt.method_25926("SoldierId"));
        if (nbt.method_25928("OwnerId")) {
            data.ownerId = nbt.method_25926("OwnerId");
        }
        if (nbt.method_25928("CastleId")) {
            data.castleId = nbt.method_25926("CastleId");
        }
        if (nbt.method_25928("GroupId")) {
            data.groupId = nbt.method_25926("GroupId");
        }
        data.name = nbt.method_10558("Name");
        data.role = nbt.method_10558("Role");
        data.rank = nbt.method_10545("Rank") ? nbt.method_10558("Rank") : "none";
        data.level = nbt.method_10545("Level") ? Math.max(1, nbt.method_10550("Level")) : 1;
        data.health = Math.max(0.0f, nbt.method_10583("Health"));
        data.maxHealth = Math.max(1.0f, nbt.method_10583("MaxHealth"));
        data.hasFood = nbt.method_10577("HasFood");
        data.hasWeapon = nbt.method_10577("HasWeapon");
        return data;
    }
}

