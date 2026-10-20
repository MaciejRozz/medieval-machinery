/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2487
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.core;

import java.util.UUID;
import net.minecraft.class_2487;
import org.jetbrains.annotations.Nullable;

public final class NpcIdentity {
    @Nullable
    private UUID ownerId;
    @Nullable
    private UUID kingdomId;
    @Nullable
    private UUID castleId;
    @Nullable
    private UUID captainId;
    @Nullable
    private UUID squadId;

    @Nullable
    public UUID getOwnerId() {
        return this.ownerId;
    }

    @Nullable
    public UUID getKingdomId() {
        return this.kingdomId;
    }

    @Nullable
    public UUID getCastleId() {
        return this.castleId;
    }

    @Nullable
    public UUID getCaptainId() {
        return this.captainId;
    }

    @Nullable
    public UUID getSquadId() {
        return this.squadId;
    }

    public void setOwnerId(@Nullable UUID value) {
        this.ownerId = value;
    }

    public void setKingdomId(@Nullable UUID value) {
        this.kingdomId = value;
    }

    public void setCastleId(@Nullable UUID value) {
        this.castleId = value;
    }

    public void setCaptainId(@Nullable UUID value) {
        this.captainId = value;
    }

    public void setSquadId(@Nullable UUID value) {
        this.squadId = value;
    }

    public void writeNbt(class_2487 nbt) {
        NpcIdentity.putUuid(nbt, "Owner", this.ownerId);
        NpcIdentity.putUuid(nbt, "KingdomId", this.kingdomId);
        NpcIdentity.putUuid(nbt, "CastleId", this.castleId);
        NpcIdentity.putUuid(nbt, "Captain", this.captainId);
        NpcIdentity.putUuid(nbt, "Squad", this.squadId);
    }

    public void readNbt(class_2487 nbt) {
        this.ownerId = NpcIdentity.getUuid(nbt, "Owner");
        this.kingdomId = NpcIdentity.getUuid(nbt, "KingdomId");
        this.castleId = NpcIdentity.getUuid(nbt, "CastleId");
        this.captainId = NpcIdentity.getUuid(nbt, "Captain");
        this.squadId = NpcIdentity.getUuid(nbt, "Squad");
    }

    private static void putUuid(class_2487 nbt, String key, @Nullable UUID value) {
        if (value != null) {
            nbt.method_25927(key, value);
        } else {
            nbt.method_10551(key);
        }
    }

    @Nullable
    private static UUID getUuid(class_2487 nbt, String key) {
        return nbt.method_25928(key) ? nbt.method_25926(key) : null;
    }
}

