/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.organization.CommandGroupType;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import org.jetbrains.annotations.Nullable;

public final class CommandGroupData {
    private final UUID id;
    @Nullable
    private UUID castleId;
    private UUID ownerId;
    private String name;
    private final CommandGroupType type;
    private NpcFormation formation = NpcFormation.FREE;
    private NpcShieldMode shieldMode = NpcShieldMode.AUTO;
    private NpcCombatStance combatStance = NpcCombatStance.NEUTRAL;
    private NpcOrderType orderType = NpcOrderType.HOLD_POSITION;
    private long orderRevision = 0L;
    private long formationRevision = 0L;
    private long shieldRevision = 0L;
    private long combatStanceRevision = 0L;
    @Nullable
    private UUID captainId;
    @Nullable
    private UUID transferTargetCastleId;
    private long transferStartedGameTime = Long.MIN_VALUE;
    private final Set<UUID> soldierIds = new LinkedHashSet<UUID>();

    public CommandGroupData(UUID id, @Nullable UUID castleId, UUID ownerId, String name, CommandGroupType type) {
        this.id = id;
        this.castleId = castleId;
        this.ownerId = ownerId;
        this.name = CommandGroupData.sanitizeName(name);
        this.type = type == null ? CommandGroupType.CUSTOM : type;
    }

    public UUID getId() {
        return this.id;
    }

    @Nullable
    public UUID getCastleId() {
        return this.castleId;
    }

    public UUID getOwnerId() {
        return this.ownerId;
    }

    public String getName() {
        return this.name;
    }

    public CommandGroupType getType() {
        return this.type;
    }

    public NpcFormation getFormation() {
        return this.formation;
    }

    public NpcShieldMode getShieldMode() {
        return this.shieldMode;
    }

    public NpcCombatStance getCombatStance() {
        return this.combatStance;
    }

    public NpcOrderType getOrderType() {
        return this.orderType;
    }

    public long getOrderRevision() {
        return this.orderRevision;
    }

    public long getFormationRevision() {
        return this.formationRevision;
    }

    public long getShieldRevision() {
        return this.shieldRevision;
    }

    public long getCombatStanceRevision() {
        return this.combatStanceRevision;
    }

    @Nullable
    public UUID getCaptainId() {
        return this.captainId;
    }

    @Nullable
    public UUID getTransferTargetCastleId() {
        return this.transferTargetCastleId;
    }

    public long getTransferStartedGameTime() {
        return this.transferStartedGameTime;
    }

    public boolean isCastleTransferActive() {
        return this.transferTargetCastleId != null;
    }

    public void setFormation(NpcFormation value) {
        this.formation = value == null ? NpcFormation.FREE : value;
        ++this.formationRevision;
    }

    public void setShieldMode(NpcShieldMode value) {
        this.shieldMode = value == null ? NpcShieldMode.AUTO : value;
        ++this.shieldRevision;
    }

    public void setCombatStance(NpcCombatStance value) {
        this.combatStance = value == null ? NpcCombatStance.NEUTRAL : value;
        ++this.combatStanceRevision;
    }

    public void setOrderType(NpcOrderType value) {
        this.orderType = value == null ? NpcOrderType.HOLD_POSITION : value;
        ++this.orderRevision;
    }

    public void setCaptainId(@Nullable UUID value) {
        this.captainId = value;
    }

    public void beginCastleTransfer(UUID targetCastleId, long gameTime) {
        this.transferTargetCastleId = targetCastleId;
        this.transferStartedGameTime = gameTime;
    }

    public void clearCastleTransfer() {
        this.transferTargetCastleId = null;
        this.transferStartedGameTime = Long.MIN_VALUE;
    }

    public Set<UUID> getSoldierIds() {
        return Set.copyOf(this.soldierIds);
    }

    public int memberIndex(UUID soldierId) {
        if (soldierId == null) {
            return 0;
        }
        int index = 0;
        for (UUID id : this.soldierIds) {
            if (soldierId.equals(id)) {
                return index;
            }
            ++index;
        }
        return Math.max(0, index);
    }

    public void setCastleId(@Nullable UUID castleId) {
        this.castleId = castleId;
    }

    public void setOwnerId(UUID ownerId) {
        if (ownerId != null) {
            this.ownerId = ownerId;
        }
    }

    public void setName(String value) {
        this.name = CommandGroupData.sanitizeName(value);
    }

    public boolean addSoldier(UUID id) {
        return id != null && this.soldierIds.add(id);
    }

    public boolean removeSoldier(UUID id) {
        return id != null && this.soldierIds.remove(id);
    }

    public void addSoldiers(Set<UUID> ids) {
        if (ids != null) {
            this.soldierIds.addAll(ids);
        }
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        nbt.method_25927("Id", this.id);
        if (this.castleId != null) {
            nbt.method_25927("CastleId", this.castleId);
        }
        nbt.method_25927("OwnerId", this.ownerId);
        nbt.method_10582("Name", this.name);
        nbt.method_10582("Type", this.type.serializedName());
        nbt.method_10582("Formation", this.formation.serializedName());
        nbt.method_10582("ShieldMode", this.shieldMode.serializedName());
        nbt.method_10582("CombatStance", this.combatStance.serializedName());
        nbt.method_10582("OrderType", this.orderType.serializedName());
        nbt.method_10544("OrderRevision", this.orderRevision);
        nbt.method_10544("FormationRevision", this.formationRevision);
        nbt.method_10544("ShieldRevision", this.shieldRevision);
        nbt.method_10544("CombatStanceRevision", this.combatStanceRevision);
        if (this.captainId != null) {
            nbt.method_25927("CaptainId", this.captainId);
        }
        if (this.transferTargetCastleId != null) {
            nbt.method_25927("TransferTargetCastleId", this.transferTargetCastleId);
        }
        nbt.method_10544("TransferStartedGameTime", this.transferStartedGameTime);
        class_2499 members = new class_2499();
        for (UUID soldierId : this.soldierIds) {
            class_2487 member = new class_2487();
            member.method_25927("Id", soldierId);
            members.add((Object)member);
        }
        nbt.method_10566("Soldiers", (class_2520)members);
        return nbt;
    }

    public static CommandGroupData fromNbt(class_2487 nbt) {
        CommandGroupData data = new CommandGroupData(nbt.method_25926("Id"), nbt.method_25928("CastleId") ? nbt.method_25926("CastleId") : null, nbt.method_25926("OwnerId"), nbt.method_10558("Name"), CommandGroupType.fromSerializedName(nbt.method_10558("Type")));
        data.formation = NpcFormation.fromSerializedName(nbt.method_10558("Formation"));
        data.shieldMode = NpcShieldMode.fromSerializedName(nbt.method_10558("ShieldMode"));
        data.combatStance = nbt.method_10545("CombatStance") ? NpcCombatStance.fromSerializedName(nbt.method_10558("CombatStance")) : NpcCombatStance.NEUTRAL;
        data.orderType = nbt.method_10545("OrderType") ? NpcOrderType.fromSerializedName(nbt.method_10558("OrderType")) : NpcOrderType.HOLD_POSITION;
        data.orderRevision = nbt.method_10545("OrderRevision") ? nbt.method_10537("OrderRevision") : 0L;
        data.formationRevision = nbt.method_10545("FormationRevision") ? nbt.method_10537("FormationRevision") : -1L;
        data.shieldRevision = nbt.method_10545("ShieldRevision") ? nbt.method_10537("ShieldRevision") : -1L;
        data.combatStanceRevision = nbt.method_10545("CombatStanceRevision") ? nbt.method_10537("CombatStanceRevision") : -1L;
        data.captainId = nbt.method_25928("CaptainId") ? nbt.method_25926("CaptainId") : null;
        data.transferTargetCastleId = nbt.method_25928("TransferTargetCastleId") ? nbt.method_25926("TransferTargetCastleId") : null;
        data.transferStartedGameTime = nbt.method_10545("TransferStartedGameTime") ? nbt.method_10537("TransferStartedGameTime") : Long.MIN_VALUE;
        class_2499 members = nbt.method_10554("Soldiers", 10);
        for (int i = 0; i < members.size(); ++i) {
            class_2487 member = members.method_10602(i);
            if (!member.method_25928("Id")) continue;
            data.soldierIds.add(member.method_25926("Id"));
        }
        return data;
    }

    private static String sanitizeName(String value) {
        if (value == null) {
            return "Gruppe";
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return "Gruppe";
        }
        return trimmed.length() > 24 ? trimmed.substring(0, 24) : trimmed;
    }
}

