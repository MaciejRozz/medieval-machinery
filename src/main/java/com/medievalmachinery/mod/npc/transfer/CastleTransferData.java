/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.transfer;

import com.medievalmachinery.mod.npc.transfer.CastleTransferState;
import java.util.UUID;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import org.jetbrains.annotations.Nullable;

public final class CastleTransferData {
    private CastleTransferState state = CastleTransferState.NONE;
    @Nullable
    private UUID sourceCastleId;
    @Nullable
    private UUID targetCastleId;
    @Nullable
    private class_2338 departureTarget;
    @Nullable
    private class_2338 arrivalTarget;
    @Nullable
    private UUID transferGroupId;
    private long requestedGameTime = Long.MIN_VALUE;
    private long departureGameTime = Long.MIN_VALUE;
    private long arrivalGameTime = Long.MIN_VALUE;
    private int blockedTicks;

    public CastleTransferState getState() {
        return this.state;
    }

    @Nullable
    public UUID getSourceCastleId() {
        return this.sourceCastleId;
    }

    @Nullable
    public UUID getTargetCastleId() {
        return this.targetCastleId;
    }

    @Nullable
    public class_2338 getDepartureTarget() {
        return this.departureTarget;
    }

    @Nullable
    public class_2338 getArrivalTarget() {
        return this.arrivalTarget;
    }

    @Nullable
    public UUID getTransferGroupId() {
        return this.transferGroupId;
    }

    public long getRequestedGameTime() {
        return this.requestedGameTime;
    }

    public long getDepartureGameTime() {
        return this.departureGameTime;
    }

    public long getArrivalGameTime() {
        return this.arrivalGameTime;
    }

    public int getBlockedTicks() {
        return this.blockedTicks;
    }

    public boolean isActive() {
        return this.state.isActive();
    }

    public void begin(UUID sourceCastleId, UUID targetCastleId, long gameTime, CastleTransferState initialState) {
        this.begin(sourceCastleId, targetCastleId, gameTime, initialState, null);
    }

    public void begin(UUID sourceCastleId, UUID targetCastleId, long gameTime, CastleTransferState initialState, @Nullable UUID transferGroupId) {
        this.sourceCastleId = sourceCastleId;
        this.targetCastleId = targetCastleId;
        this.requestedGameTime = gameTime;
        this.transferGroupId = transferGroupId;
        this.departureGameTime = Long.MIN_VALUE;
        this.arrivalGameTime = Long.MIN_VALUE;
        this.departureTarget = null;
        this.arrivalTarget = null;
        this.blockedTicks = 0;
        this.state = initialState == null ? CastleTransferState.GATHERING : initialState;
    }

    public void setState(CastleTransferState value) {
        CastleTransferState castleTransferState = this.state = value == null ? CastleTransferState.NONE : value;
        if (this.state != CastleTransferState.WAITING_FOR_EXIT) {
            this.blockedTicks = 0;
        }
    }

    public void setDepartureTarget(@Nullable class_2338 value) {
        this.departureTarget = value == null ? null : value.method_10062();
    }

    public void setArrivalTarget(@Nullable class_2338 value) {
        this.arrivalTarget = value == null ? null : value.method_10062();
    }

    public void incrementBlockedTicks() {
        this.blockedTicks = Math.min(Integer.MAX_VALUE, this.blockedTicks + 1);
    }

    public void markTravelling(long departureTime, long arrivalTime) {
        this.departureGameTime = departureTime;
        this.arrivalGameTime = Math.max(departureTime, arrivalTime);
        this.state = CastleTransferState.TRAVELLING;
        this.blockedTicks = 0;
    }

    public void clear() {
        this.state = CastleTransferState.NONE;
        this.sourceCastleId = null;
        this.targetCastleId = null;
        this.departureTarget = null;
        this.arrivalTarget = null;
        this.transferGroupId = null;
        this.requestedGameTime = Long.MIN_VALUE;
        this.departureGameTime = Long.MIN_VALUE;
        this.arrivalGameTime = Long.MIN_VALUE;
        this.blockedTicks = 0;
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        nbt.method_10582("State", this.state.serializedName());
        if (this.sourceCastleId != null) {
            nbt.method_25927("SourceCastleId", this.sourceCastleId);
        }
        if (this.targetCastleId != null) {
            nbt.method_25927("TargetCastleId", this.targetCastleId);
        }
        if (this.transferGroupId != null) {
            nbt.method_25927("TransferGroupId", this.transferGroupId);
        }
        if (this.departureTarget != null) {
            nbt.method_10569("DepartureX", this.departureTarget.method_10263());
            nbt.method_10569("DepartureY", this.departureTarget.method_10264());
            nbt.method_10569("DepartureZ", this.departureTarget.method_10260());
        }
        if (this.arrivalTarget != null) {
            nbt.method_10569("ArrivalX", this.arrivalTarget.method_10263());
            nbt.method_10569("ArrivalY", this.arrivalTarget.method_10264());
            nbt.method_10569("ArrivalZ", this.arrivalTarget.method_10260());
        }
        nbt.method_10544("RequestedGameTime", this.requestedGameTime);
        nbt.method_10544("DepartureGameTime", this.departureGameTime);
        nbt.method_10544("ArrivalGameTime", this.arrivalGameTime);
        nbt.method_10569("BlockedTicks", this.blockedTicks);
        return nbt;
    }

    public void readNbt(class_2487 nbt) {
        this.state = CastleTransferState.fromSerializedName(nbt.method_10558("State"));
        this.sourceCastleId = nbt.method_25928("SourceCastleId") ? nbt.method_25926("SourceCastleId") : null;
        this.targetCastleId = nbt.method_25928("TargetCastleId") ? nbt.method_25926("TargetCastleId") : null;
        this.transferGroupId = nbt.method_25928("TransferGroupId") ? nbt.method_25926("TransferGroupId") : null;
        this.departureTarget = nbt.method_10573("DepartureX", 3) && nbt.method_10573("DepartureY", 3) && nbt.method_10573("DepartureZ", 3) ? new class_2338(nbt.method_10550("DepartureX"), nbt.method_10550("DepartureY"), nbt.method_10550("DepartureZ")) : null;
        this.arrivalTarget = nbt.method_10573("ArrivalX", 3) && nbt.method_10573("ArrivalY", 3) && nbt.method_10573("ArrivalZ", 3) ? new class_2338(nbt.method_10550("ArrivalX"), nbt.method_10550("ArrivalY"), nbt.method_10550("ArrivalZ")) : null;
        this.requestedGameTime = nbt.method_10573("RequestedGameTime", 4) ? nbt.method_10537("RequestedGameTime") : Long.MIN_VALUE;
        this.departureGameTime = nbt.method_10573("DepartureGameTime", 4) ? nbt.method_10537("DepartureGameTime") : Long.MIN_VALUE;
        this.arrivalGameTime = nbt.method_10573("ArrivalGameTime", 4) ? nbt.method_10537("ArrivalGameTime") : Long.MIN_VALUE;
        int n = this.blockedTicks = nbt.method_10573("BlockedTicks", 3) ? Math.max(0, nbt.method_10550("BlockedTicks")) : 0;
        if (this.sourceCastleId == null || this.targetCastleId == null) {
            this.clear();
        }
    }
}

