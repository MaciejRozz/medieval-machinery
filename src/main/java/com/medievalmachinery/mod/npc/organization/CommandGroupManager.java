/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_18
 *  net.minecraft.class_1937
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupType;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.class_18;
import net.minecraft.class_1937;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class CommandGroupManager
extends class_18 {
    private static final String SAVE_ID = "medieval-machinery_command_groups";
    private final Map<UUID, CommandGroupData> groups = new LinkedHashMap<UUID, CommandGroupData>();
    private final Map<UUID, UUID> aliases = new LinkedHashMap<UUID, UUID>();
    private final Map<UUID, PendingTransfer> pendingTransfers = new LinkedHashMap<UUID, PendingTransfer>();

    public static CommandGroupManager get(class_3218 world) {
        class_3218 overworld = world.method_8503().method_30002();
        return (CommandGroupManager)overworld.method_17983().method_17924(CommandGroupManager::fromNbt, CommandGroupManager::new, SAVE_ID);
    }

    @Nullable
    public CommandGroupData get(UUID id) {
        if (id == null) {
            return null;
        }
        UUID resolved = this.resolveAlias(id);
        return this.groups.get(resolved);
    }

    public UUID resolveAlias(UUID id) {
        UUID current = id;
        HashSet<UUID> seen = new HashSet<UUID>();
        while (current != null && this.aliases.containsKey(current) && seen.add(current)) {
            current = this.aliases.get(current);
        }
        return current == null ? id : current;
    }

    public List<CommandGroupData> getGroups(UUID castleId, UUID ownerId) {
        ArrayList<CommandGroupData> result = new ArrayList<CommandGroupData>();
        for (CommandGroupData group : this.groups.values()) {
            if (!Objects.equals(group.getCastleId(), castleId) || !group.getOwnerId().equals(ownerId)) continue;
            result.add(group);
        }
        result.sort(Comparator.comparing(g -> CommandGroupManager.fixedOrder(g.getType())).thenComparing(CommandGroupData::getName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    public List<CommandGroupData> getGroupsByOwner(UUID ownerId) {
        ArrayList<CommandGroupData> result = new ArrayList<CommandGroupData>();
        for (CommandGroupData group : this.groups.values()) {
            if (!group.getOwnerId().equals(ownerId)) continue;
            result.add(group);
        }
        return result;
    }

    public List<CommandGroupData> getUiGroups(UUID castleId, UUID ownerId) {
        ArrayList<CommandGroupData> result = new ArrayList<CommandGroupData>();
        for (CommandGroupType type : new CommandGroupType[]{CommandGroupType.INFANTRY, CommandGroupType.RANGED, CommandGroupType.KNIGHTS, CommandGroupType.MOUNTED, CommandGroupType.MACHINISTS}) {
            result.add(this.ensureFixedGroup(castleId, ownerId, type));
        }
        block1: for (CommandGroupType special : new CommandGroupType[]{CommandGroupType.SCOUTS, CommandGroupType.QUARTERMASTERS}) {
            for (CommandGroupData group : this.groups.values()) {
                if (!Objects.equals(group.getCastleId(), castleId) || !group.getOwnerId().equals(ownerId) || group.getType() != special) continue;
                result.add(group);
                continue block1;
            }
        }
        ArrayList<CommandGroupData> custom = new ArrayList<CommandGroupData>();
        for (CommandGroupData group : this.groups.values()) {
            if (!Objects.equals(group.getCastleId(), castleId) || !group.getOwnerId().equals(ownerId) || group.getType() != CommandGroupType.CUSTOM) continue;
            custom.add(group);
        }
        custom.sort(Comparator.comparing(CommandGroupData::getName, String.CASE_INSENSITIVE_ORDER));
        result.addAll(custom);
        return result;
    }

    @Nullable
    public CommandGroupData findByName(UUID castleId, UUID ownerId, String name) {
        if (ownerId == null || name == null) {
            return null;
        }
        String wanted = name.trim();
        for (CommandGroupData group : this.groups.values()) {
            if (!Objects.equals(group.getCastleId(), castleId) || !group.getOwnerId().equals(ownerId) || !group.getName().equalsIgnoreCase(wanted)) continue;
            return group;
        }
        return null;
    }

    public CommandGroupData ensureFixedGroup(UUID castleId, UUID ownerId, CommandGroupType type) {
        if (type == null || type == CommandGroupType.CUSTOM) {
            throw new IllegalArgumentException("fixed group type required");
        }
        for (CommandGroupData group : this.groups.values()) {
            if (!Objects.equals(group.getCastleId(), castleId) || !group.getOwnerId().equals(ownerId) || group.getType() != type) continue;
            return group;
        }
        CommandGroupData created = new CommandGroupData(UUID.randomUUID(), castleId, ownerId, CommandGroupManager.defaultName(type), type);
        this.groups.put(created.getId(), created);
        this.method_80();
        return created;
    }

    public CommandGroupData createCustomGroup(UUID castleId, UUID ownerId, String name) {
        CommandGroupData sameName = this.findByName(castleId, ownerId, name);
        if (sameName != null) {
            return sameName;
        }
        CommandGroupData created = new CommandGroupData(UUID.randomUUID(), castleId, ownerId, name, CommandGroupType.CUSTOM);
        this.groups.put(created.getId(), created);
        this.method_80();
        return created;
    }

    public boolean rename(UUID groupId, UUID actorId, String name) {
        CommandGroupData group = this.get(groupId);
        if (group == null || !group.getOwnerId().equals(actorId)) {
            return false;
        }
        CommandGroupData sameName = this.findByName(group.getCastleId(), actorId, name);
        if (sameName != null && !sameName.getId().equals(group.getId())) {
            return false;
        }
        group.setName(name);
        this.method_80();
        return true;
    }

    public void assignRecruit(MedievalSoldierEntity soldier, @Nullable UUID groupId) {
        class_1937 class_19372 = soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 world = (class_3218)class_19372;
        UUID soldierId = soldier.method_5667();
        if (groupId == null) {
            this.removeSoldierFromOtherGroups(soldierId, null);
            soldier.getNpcIdentity().setSquadId(null);
            soldier.getNpcIdentity().setCaptainId(null);
            soldier.setFormationSlot(-1);
            soldier.setOrderType(NpcOrderType.IDLE);
            soldier.markOrganizationDirty();
            this.method_80();
            return;
        }
        CommandGroupData group = this.get(groupId);
        if (group == null) {
            return;
        }
        if (!Objects.equals(group.getCastleId(), soldier.getNpcIdentity().getCastleId())) {
            return;
        }
        if (!group.getOwnerId().equals(soldier.getNpcIdentity().getOwnerId())) {
            return;
        }
        this.removeSoldierFromOtherGroups(soldierId, group.getId());
        group.addSoldier(soldierId);
        soldier.getNpcIdentity().setSquadId(group.getId());
        soldier.getNpcIdentity().setCaptainId(Objects.equals(group.getCaptainId(), soldierId) ? null : group.getCaptainId());
        CommandGroupManager.applyJoinTacticalSettings(soldier, group);
        soldier.setFormationSlot(group.memberIndex(soldierId));
        soldier.setOrderType(NpcOrderType.IDLE);
        soldier.markGroupOrderRevisionApplied(group.getOrderRevision());
        soldier.markOrganizationDirty();
        this.method_80();
    }

    public void assignSoldier(MedievalSoldierEntity soldier, @Nullable UUID groupId) {
        class_1937 class_19372 = soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 world = (class_3218)class_19372;
        UUID soldierId = soldier.method_5667();
        if (groupId == null) {
            this.removeSoldierFromOtherGroups(soldierId, null);
            soldier.getNpcIdentity().setSquadId(null);
            soldier.getNpcIdentity().setCaptainId(null);
            soldier.setFormationSlot(-1);
            soldier.markOrganizationDirty();
            this.method_80();
            return;
        }
        CommandGroupData group = this.get(groupId);
        if (group == null) {
            return;
        }
        if (!Objects.equals(group.getCastleId(), soldier.getNpcIdentity().getCastleId())) {
            return;
        }
        if (!group.getOwnerId().equals(soldier.getNpcIdentity().getOwnerId())) {
            return;
        }
        this.removeSoldierFromOtherGroups(soldierId, group.getId());
        group.addSoldier(soldierId);
        soldier.getNpcIdentity().setSquadId(group.getId());
        soldier.getNpcIdentity().setCaptainId(Objects.equals(group.getCaptainId(), soldierId) ? null : group.getCaptainId());
        CommandGroupManager.applyJoinSettings(soldier, group);
        soldier.setFormationSlot(group.memberIndex(soldierId));
        soldier.markOrganizationDirty();
        this.method_80();
    }

    public boolean setCaptain(UUID groupId, UUID actorId, @Nullable UUID captainId) {
        CommandGroupData group = this.get(groupId);
        if (group == null || actorId == null || !group.getOwnerId().equals(actorId)) {
            return false;
        }
        if (captainId != null && !group.getSoldierIds().contains(captainId)) {
            return false;
        }
        if (Objects.equals(group.getCaptainId(), captainId)) {
            return true;
        }
        group.setCaptainId(captainId);
        this.method_80();
        return true;
    }

    @Nullable
    public UUID getCaptain(UUID groupId) {
        CommandGroupData group = this.get(groupId);
        return group == null ? null : group.getCaptainId();
    }

    public boolean setFormation(UUID groupId, UUID actorId, NpcFormation formation) {
        CommandGroupData group = this.get(groupId);
        if (group == null || actorId == null || !group.getOwnerId().equals(actorId)) {
            return false;
        }
        group.setFormation(formation);
        this.method_80();
        return true;
    }

    public boolean setShieldMode(UUID groupId, UUID actorId, NpcShieldMode mode) {
        CommandGroupData group = this.get(groupId);
        if (group == null || actorId == null || !group.getOwnerId().equals(actorId)) {
            return false;
        }
        group.setShieldMode(mode);
        this.method_80();
        return true;
    }

    public boolean setCombatStance(UUID groupId, UUID actorId, NpcCombatStance stance) {
        CommandGroupData group = this.get(groupId);
        if (group == null || actorId == null || !group.getOwnerId().equals(actorId)) {
            return false;
        }
        group.setCombatStance(stance);
        this.method_80();
        return true;
    }

    public boolean setOrderType(UUID groupId, UUID actorId, NpcOrderType order) {
        CommandGroupData group = this.get(groupId);
        if (group == null || actorId == null || !group.getOwnerId().equals(actorId)) {
            return false;
        }
        group.setOrderType(order);
        this.method_80();
        return true;
    }

    private static void applyJoinTacticalSettings(MedievalSoldierEntity soldier, CommandGroupData group) {
        soldier.setFormation(group.getFormation());
        soldier.setShieldMode(group.getShieldMode());
        soldier.setCombatStance(group.getCombatStance());
        soldier.markGroupFormationRevisionApplied(group.getFormationRevision());
        soldier.markGroupShieldRevisionApplied(group.getShieldRevision());
        soldier.markGroupCombatStanceRevisionApplied(group.getCombatStanceRevision());
    }

    private static void applyJoinSettings(MedievalSoldierEntity soldier, CommandGroupData group) {
        CommandGroupManager.applyJoinTacticalSettings(soldier, group);
        NpcOrderType order = group.getOrderType();
        if (order == NpcOrderType.PATROL && !soldier.hasPatrolRoute()) {
            order = NpcOrderType.HOLD_POSITION;
        }
        if (order == NpcOrderType.MOVE_TO_POSITION) {
            order = NpcOrderType.HOLD_POSITION;
        }
        soldier.setOrderType(order);
        soldier.markGroupOrderRevisionApplied(group.getOrderRevision());
    }

    private boolean removeSoldierFromOtherGroups(UUID soldierId, @Nullable UUID keepGroupId) {
        boolean changed = false;
        UUID keep = keepGroupId == null ? null : this.resolveAlias(keepGroupId);
        for (CommandGroupData candidate : this.groups.values()) {
            if (keep != null && candidate.getId().equals(keep)) continue;
            changed |= candidate.removeSoldier(soldierId);
            if (!soldierId.equals(candidate.getCaptainId())) continue;
            candidate.setCaptainId(null);
            changed = true;
        }
        return changed;
    }

    public void reconcileMembership(MedievalSoldierEntity soldier) {
        UUID resolved;
        CommandGroupData existing;
        class_1937 class_19372 = soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 world = (class_3218)class_19372;
        PendingTransfer pending = this.pendingTransfers.remove(soldier.method_5667());
        if (pending != null) {
            boolean membershipChanged = this.removeSoldierFromOtherGroups(soldier.method_5667(), pending.groupId());
            soldier.getNpcIdentity().setOwnerId(pending.ownerId());
            soldier.getNpcIdentity().setCaptainId(pending.captainId());
            KingdomData pendingOwnerKingdom = KingdomManager.get(world).findByMember(pending.ownerId());
            soldier.getNpcIdentity().setKingdomId(pendingOwnerKingdom == null ? null : pendingOwnerKingdom.getId());
            if (pending.groupId() == null) {
                soldier.getNpcIdentity().setSquadId(null);
                soldier.setFormationSlot(-1);
            } else {
                UUID resolved2 = this.resolveAlias(pending.groupId());
                soldier.getNpcIdentity().setSquadId(resolved2);
                CommandGroupData target = this.get(resolved2);
                if (target != null) {
                    soldier.getNpcIdentity().setCastleId(target.getCastleId());
                    target.addSoldier(soldier.method_5667());
                    soldier.getNpcIdentity().setCaptainId(Objects.equals(target.getCaptainId(), soldier.method_5667()) ? null : target.getCaptainId());
                    CommandGroupManager.applyJoinSettings(soldier, target);
                    soldier.setFormationSlot(target.memberIndex(soldier.method_5667()));
                }
            }
            this.method_80();
            return;
        }
        UUID existingId = soldier.getNpcIdentity().getSquadId();
        if (existingId != null && (existing = this.groups.get(resolved = this.resolveAlias(existingId))) != null) {
            soldier.getNpcIdentity().setCastleId(existing.getCastleId());
            boolean changed = this.removeSoldierFromOtherGroups(soldier.method_5667(), resolved);
            if (!resolved.equals(existingId)) {
                soldier.getNpcIdentity().setSquadId(resolved);
            }
            soldier.getNpcIdentity().setCaptainId(Objects.equals(existing.getCaptainId(), soldier.method_5667()) ? null : existing.getCaptainId());
            if (changed |= existing.addSoldier(soldier.method_5667())) {
                this.method_80();
            }
            soldier.setFormationSlot(existing.memberIndex(soldier.method_5667()));
            if (existing.getFormationRevision() > soldier.getLastAppliedGroupFormationRevision()) {
                soldier.setFormation(existing.getFormation());
                soldier.markGroupFormationRevisionApplied(existing.getFormationRevision());
            }
            if (existing.getShieldRevision() > soldier.getLastAppliedGroupShieldRevision()) {
                soldier.setShieldMode(existing.getShieldMode());
                soldier.markGroupShieldRevisionApplied(existing.getShieldRevision());
            }
            if (existing.getCombatStanceRevision() > soldier.getLastAppliedGroupCombatStanceRevision()) {
                soldier.setCombatStance(existing.getCombatStance());
                soldier.markGroupCombatStanceRevisionApplied(existing.getCombatStanceRevision());
            }
            if (existing.getOrderRevision() > soldier.getLastAppliedGroupOrderRevision()) {
                if (!soldier.isGeneralBodyguard()) {
                    NpcOrderType persistedOrder = existing.getOrderType();
                    if (persistedOrder == NpcOrderType.PATROL && !soldier.hasPatrolRoute()) {
                        persistedOrder = NpcOrderType.HOLD_POSITION;
                    }
                    if (persistedOrder == NpcOrderType.MOVE_TO_POSITION) {
                        persistedOrder = NpcOrderType.HOLD_POSITION;
                    }
                    soldier.setOrderType(persistedOrder);
                }
                soldier.markGroupOrderRevisionApplied(existing.getOrderRevision());
            }
            return;
        }
    }

    @Nullable
    public UUID deleteGroup(UUID groupId, UUID actorId) {
        CommandGroupData group = this.get(groupId);
        if (group == null || !group.getOwnerId().equals(actorId) || group.getType() != CommandGroupType.CUSTOM) {
            return null;
        }
        this.groups.remove(group.getId());
        for (UUID soldierId : group.getSoldierIds()) {
            this.pendingTransfers.put(soldierId, new PendingTransfer(actorId, null, null));
        }
        this.method_80();
        return group.getId();
    }

    @Nullable
    public UUID transferGroup(UUID groupId, UUID actorId, UUID targetOwnerId) {
        CommandGroupData source = this.get(groupId);
        if (source == null || !source.getOwnerId().equals(actorId) || targetOwnerId == null || actorId.equals(targetOwnerId)) {
            return null;
        }
        CommandGroupData target = this.findByName(source.getCastleId(), targetOwnerId, source.getName());
        if (target == null && source.getType() != CommandGroupType.CUSTOM) {
            for (CommandGroupData candidate : this.getGroups(source.getCastleId(), targetOwnerId)) {
                if (candidate.getType() != source.getType()) continue;
                target = candidate;
                break;
            }
        }
        if (target == null) {
            source.setOwnerId(targetOwnerId);
            target = source;
        } else if (!target.getId().equals(source.getId())) {
            target.addSoldiers(source.getSoldierIds());
            this.aliases.put(source.getId(), target.getId());
            this.groups.remove(source.getId());
        }
        for (UUID soldierId : source.getSoldierIds()) {
            this.pendingTransfers.put(soldierId, new PendingTransfer(targetOwnerId, target.getId(), Objects.equals(target.getCaptainId(), soldierId) ? null : target.getCaptainId()));
        }
        this.method_80();
        return target.getId();
    }

    @Nullable
    public UUID transferCaptainGroupDetached(UUID groupId, UUID actorId, UUID targetOwnerId, UUID captainId) {
        CommandGroupData source = this.get(groupId);
        if (source == null || actorId == null || targetOwnerId == null || captainId == null || actorId.equals(targetOwnerId) || !source.getOwnerId().equals(actorId) || !source.getSoldierIds().contains(captainId)) {
            return null;
        }
        source.setOwnerId(targetOwnerId);
        source.setCastleId(null);
        source.setCaptainId(captainId);
        for (UUID soldierId : source.getSoldierIds()) {
            this.pendingTransfers.put(soldierId, new PendingTransfer(targetOwnerId, source.getId(), soldierId.equals(captainId) ? null : captainId));
        }
        this.method_80();
        return source.getId();
    }

    public void queueAssignment(UUID soldierId, UUID ownerId, UUID castleId, @Nullable UUID groupId) {
        CommandGroupData target;
        if (soldierId == null || ownerId == null) {
            return;
        }
        for (CommandGroupData group : this.groups.values()) {
            group.removeSoldier(soldierId);
            if (!soldierId.equals(group.getCaptainId())) continue;
            group.setCaptainId(null);
        }
        UUID resolved = groupId == null ? null : this.resolveAlias(groupId);
        CommandGroupData commandGroupData = target = resolved == null ? null : this.groups.get(resolved);
        if (target != null && ownerId.equals(target.getOwnerId()) && castleId != null && castleId.equals(target.getCastleId())) {
            target.addSoldier(soldierId);
        } else {
            resolved = null;
        }
        this.pendingTransfers.put(soldierId, new PendingTransfer(ownerId, resolved, null));
        this.method_80();
    }

    public Set<UUID> detachCastle(UUID castleId) {
        if (castleId == null) {
            return Set.of();
        }
        LinkedHashSet<UUID> affectedSoldiers = new LinkedHashSet<UUID>();
        boolean changed = false;
        Iterator<Map.Entry<UUID, CommandGroupData>> iterator = this.groups.entrySet().iterator();
        while (iterator.hasNext()) {
            CommandGroupData group = iterator.next().getValue();
            if (!castleId.equals(group.getCastleId())) continue;
            if (group.getSoldierIds().isEmpty() && group.getType() != CommandGroupType.CUSTOM) {
                iterator.remove();
                changed = true;
                continue;
            }
            group.setCastleId(null);
            affectedSoldiers.addAll(group.getSoldierIds());
            changed = true;
        }
        if (changed) {
            this.method_80();
        }
        return affectedSoldiers;
    }

    public List<CommandGroupData> getUnassignedGroups(UUID ownerId) {
        ArrayList<CommandGroupData> result = new ArrayList<CommandGroupData>();
        for (CommandGroupData group : this.groups.values()) {
            if (group.getCastleId() != null || !group.getOwnerId().equals(ownerId)) continue;
            result.add(group);
        }
        result.sort(Comparator.comparing(g -> CommandGroupManager.fixedOrder(g.getType())).thenComparing(CommandGroupData::getName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    public boolean beginGroupCastleTransfer(UUID groupId, UUID actorId, UUID targetCastleId, long gameTime) {
        CommandGroupData group = this.get(groupId);
        if (group == null || actorId == null || targetCastleId == null || !actorId.equals(group.getOwnerId())) {
            return false;
        }
        if (group.getCastleId() == null || group.getCastleId().equals(targetCastleId) || group.isCastleTransferActive()) {
            return false;
        }
        group.beginCastleTransfer(targetCastleId, gameTime);
        this.method_80();
        return true;
    }

    public boolean completeGroupCastleTransfer(UUID groupId, UUID actorId) {
        CommandGroupData group = this.get(groupId);
        if (group == null || actorId == null || !actorId.equals(group.getOwnerId()) || !group.isCastleTransferActive()) {
            return false;
        }
        UUID target = group.getTransferTargetCastleId();
        if (target == null) {
            return false;
        }
        group.setCastleId(target);
        group.clearCastleTransfer();
        this.method_80();
        return true;
    }

    public void cancelGroupCastleTransfer(UUID groupId, UUID actorId) {
        CommandGroupData group = this.get(groupId);
        if (group == null || actorId == null || !actorId.equals(group.getOwnerId())) {
            return;
        }
        if (!group.isCastleTransferActive()) {
            return;
        }
        group.clearCastleTransfer();
        this.method_80();
    }

    public boolean assignGroupToCastle(UUID groupId, UUID actorId, @Nullable UUID castleId) {
        CommandGroupData group = this.get(groupId);
        if (group == null || actorId == null || !actorId.equals(group.getOwnerId())) {
            return false;
        }
        if (Objects.equals(group.getCastleId(), castleId)) {
            return true;
        }
        if (group.isCastleTransferActive()) {
            return false;
        }
        group.setCastleId(castleId);
        this.method_80();
        return true;
    }

    public void removeSoldierEverywhere(UUID soldierId) {
        if (soldierId == null) {
            return;
        }
        boolean changed = false;
        for (CommandGroupData group : this.groups.values()) {
            changed |= group.removeSoldier(soldierId);
            if (!soldierId.equals(group.getCaptainId())) continue;
            group.setCaptainId(null);
            changed = true;
        }
        if (changed |= this.pendingTransfers.remove(soldierId) != null) {
            this.method_80();
        }
    }

    public static String defaultName(CommandGroupType type) {
        return switch (type) {
            default -> throw new IncompatibleClassChangeError();
            case CommandGroupType.INFANTRY -> "Infanterie";
            case CommandGroupType.RANGED -> "Sch\u00fctzen";
            case CommandGroupType.KNIGHTS -> "Ritter";
            case CommandGroupType.MOUNTED -> "Berittene";
            case CommandGroupType.MACHINISTS -> "Maschinisten";
            case CommandGroupType.SCOUTS -> "Sp\u00e4her";
            case CommandGroupType.QUARTERMASTERS -> "Versorger";
            case CommandGroupType.CUSTOM -> "Gruppe";
        };
    }

    private static int fixedOrder(CommandGroupType type) {
        return switch (type) {
            default -> throw new IncompatibleClassChangeError();
            case CommandGroupType.INFANTRY -> 1;
            case CommandGroupType.RANGED -> 2;
            case CommandGroupType.KNIGHTS -> 3;
            case CommandGroupType.MOUNTED -> 4;
            case CommandGroupType.MACHINISTS -> 5;
            case CommandGroupType.SCOUTS -> 6;
            case CommandGroupType.QUARTERMASTERS -> 7;
            case CommandGroupType.CUSTOM -> 8;
        };
    }

    public class_2487 method_75(class_2487 nbt) {
        class_2499 groupList = new class_2499();
        for (CommandGroupData commandGroupData : this.groups.values()) {
            groupList.add((Object)commandGroupData.toNbt());
        }
        nbt.method_10566("Groups", (class_2520)groupList);
        class_2499 aliasList = new class_2499();
        for (Map.Entry<UUID, UUID> entry : this.aliases.entrySet()) {
            class_2487 alias = new class_2487();
            alias.method_25927("From", entry.getKey());
            alias.method_25927("To", entry.getValue());
            aliasList.add((Object)alias);
        }
        nbt.method_10566("Aliases", (class_2520)aliasList);
        class_2499 class_24992 = new class_2499();
        for (Map.Entry<UUID, PendingTransfer> entry : this.pendingTransfers.entrySet()) {
            class_2487 transfer = new class_2487();
            transfer.method_25927("Soldier", entry.getKey());
            transfer.method_25927("Owner", entry.getValue().ownerId());
            if (entry.getValue().groupId() != null) {
                transfer.method_25927("Group", entry.getValue().groupId());
            }
            if (entry.getValue().captainId() != null) {
                transfer.method_25927("Captain", entry.getValue().captainId());
            }
            class_24992.add((Object)transfer);
        }
        nbt.method_10566("PendingTransfers", (class_2520)class_24992);
        return nbt;
    }

    private static CommandGroupManager fromNbt(class_2487 nbt) {
        CommandGroupManager manager = new CommandGroupManager();
        class_2499 groupList = nbt.method_10554("Groups", 10);
        for (int i = 0; i < groupList.size(); ++i) {
            CommandGroupData group = CommandGroupData.fromNbt(groupList.method_10602(i));
            manager.groups.put(group.getId(), group);
        }
        class_2499 aliasList = nbt.method_10554("Aliases", 10);
        for (int i = 0; i < aliasList.size(); ++i) {
            class_2487 alias = aliasList.method_10602(i);
            if (!alias.method_25928("From") || !alias.method_25928("To")) continue;
            manager.aliases.put(alias.method_25926("From"), alias.method_25926("To"));
        }
        class_2499 transferList = nbt.method_10554("PendingTransfers", 10);
        for (int i = 0; i < transferList.size(); ++i) {
            class_2487 transfer = transferList.method_10602(i);
            if (!transfer.method_25928("Soldier") || !transfer.method_25928("Owner")) continue;
            UUID group = transfer.method_25928("Group") ? transfer.method_25926("Group") : null;
            UUID captain = transfer.method_25928("Captain") ? transfer.method_25926("Captain") : null;
            manager.pendingTransfers.put(transfer.method_25926("Soldier"), new PendingTransfer(transfer.method_25926("Owner"), group, captain));
        }
        return manager;
    }

    public record PendingTransfer(UUID ownerId, UUID groupId, @Nullable UUID captainId) {
    }
}

