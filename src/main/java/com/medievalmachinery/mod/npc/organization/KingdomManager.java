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

import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomPermission;
import com.medievalmachinery.mod.npc.organization.KingdomRole;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_18;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class KingdomManager
extends class_18 {
    private static final String SAVE_ID = "medieval-machinery_kingdoms";
    private final Map<UUID, KingdomData> kingdoms = new LinkedHashMap<UUID, KingdomData>();
    private final Map<UUID, UUID> pendingInvites = new LinkedHashMap<UUID, UUID>();

    public static KingdomManager get(class_3218 world) {
        class_3218 overworld = world.method_8503().method_30002();
        return (KingdomManager)overworld.method_17983().method_17924(KingdomManager::fromNbt, KingdomManager::new, SAVE_ID);
    }

    public KingdomData createSystemKingdom(UUID kingdomId, UUID leaderId, String name, OrderStyle mainOrder) {
        KingdomData existing = this.kingdoms.get(kingdomId);
        if (existing != null) {
            return existing;
        }
        KingdomData kingdom = new KingdomData(kingdomId, leaderId, name, mainOrder);
        this.kingdoms.put(kingdomId, kingdom);
        this.method_80();
        return kingdom;
    }

    @Nullable
    public KingdomData create(UUID kingId, String name, OrderStyle mainOrder) {
        if (this.findByMember(kingId) != null || mainOrder == OrderStyle.NONE) {
            return null;
        }
        KingdomData kingdom = new KingdomData(UUID.randomUUID(), kingId, name, mainOrder);
        this.kingdoms.put(kingdom.getId(), kingdom);
        this.method_80();
        return kingdom;
    }

    @Nullable
    public KingdomData get(UUID kingdomId) {
        return this.kingdoms.get(kingdomId);
    }

    public Collection<KingdomData> getKingdoms() {
        return List.copyOf(this.kingdoms.values());
    }

    @Nullable
    public KingdomData findByMember(UUID playerId) {
        for (KingdomData kingdom : this.kingdoms.values()) {
            if (!kingdom.isMember(playerId)) continue;
            return kingdom;
        }
        return null;
    }

    public boolean addMember(UUID kingdomId, UUID playerId) {
        if (this.findByMember(playerId) != null) {
            return false;
        }
        KingdomData kingdom = this.kingdoms.get(kingdomId);
        if (kingdom == null || !kingdom.addMember(playerId)) {
            return false;
        }
        this.method_80();
        return true;
    }

    public boolean removeMember(UUID kingdomId, UUID playerId) {
        KingdomData kingdom = this.kingdoms.get(kingdomId);
        if (kingdom == null || !kingdom.removeMember(playerId)) {
            return false;
        }
        this.method_80();
        return true;
    }

    public boolean removeMember(UUID kingdomId, UUID actorId, UUID playerId) {
        KingdomData kingdom = this.kingdoms.get(kingdomId);
        if (kingdom == null || actorId.equals(playerId) || !kingdom.hasPermission(actorId, KingdomPermission.MANAGE_MEMBERS)) {
            return false;
        }
        if (kingdom.getRole(playerId) == KingdomRole.OFFICER && !kingdom.getKingId().equals(actorId)) {
            return false;
        }
        if (!kingdom.removeMember(playerId)) {
            return false;
        }
        this.pendingInvites.remove(playerId);
        this.method_80();
        return true;
    }

    public boolean setMemberRole(UUID kingdomId, UUID actorId, UUID playerId, KingdomRole role) {
        KingdomData kingdom = this.kingdoms.get(kingdomId);
        if (kingdom == null || !kingdom.getKingId().equals(actorId)) {
            return false;
        }
        if (!kingdom.setRole(playerId, role)) {
            return false;
        }
        this.method_80();
        return true;
    }

    public boolean leave(UUID playerId) {
        KingdomData kingdom = this.findByMember(playerId);
        if (kingdom == null || kingdom.getKingId().equals(playerId)) {
            return false;
        }
        if (!kingdom.removeMember(playerId)) {
            return false;
        }
        this.pendingInvites.remove(playerId);
        this.method_80();
        return true;
    }

    public boolean rename(UUID kingdomId, UUID actor, String name) {
        KingdomData kingdom = this.kingdoms.get(kingdomId);
        if (kingdom == null || !kingdom.hasPermission(actor, KingdomPermission.RENAME_KINGDOM)) {
            return false;
        }
        kingdom.setName(name);
        this.method_80();
        return true;
    }

    public boolean setMainOrder(UUID kingdomId, UUID actor, OrderStyle order) {
        KingdomData kingdom = this.kingdoms.get(kingdomId);
        if (kingdom == null || order == OrderStyle.NONE || !kingdom.hasPermission(actor, KingdomPermission.CHANGE_MAIN_ORDER)) {
            return false;
        }
        kingdom.setMainOrder(order);
        this.method_80();
        return true;
    }

    public boolean invite(UUID kingdomId, UUID actorId, UUID targetId) {
        KingdomData kingdom = this.kingdoms.get(kingdomId);
        if (kingdom == null || kingdom.isMember(targetId) || !kingdom.hasPermission(actorId, KingdomPermission.MANAGE_MEMBERS) || this.findByMember(targetId) != null) {
            return false;
        }
        this.pendingInvites.put(targetId, kingdomId);
        this.method_80();
        return true;
    }

    @Nullable
    public KingdomData getPendingInvite(UUID playerId) {
        UUID kingdomId = this.pendingInvites.get(playerId);
        return kingdomId == null ? null : this.kingdoms.get(kingdomId);
    }

    public boolean acceptInvite(UUID playerId) {
        UUID kingdomId = this.pendingInvites.remove(playerId);
        if (kingdomId == null || this.findByMember(playerId) != null) {
            return false;
        }
        KingdomData kingdom = this.kingdoms.get(kingdomId);
        if (kingdom == null || !kingdom.addMember(playerId)) {
            return false;
        }
        this.method_80();
        return true;
    }

    public boolean declineInvite(UUID playerId) {
        if (this.pendingInvites.remove(playerId) == null) {
            return false;
        }
        this.method_80();
        return true;
    }

    public boolean deleteKingdom(UUID kingdomId, UUID actorId) {
        KingdomData kingdom = this.kingdoms.get(kingdomId);
        if (kingdom == null || !kingdom.getKingId().equals(actorId)) {
            return false;
        }
        this.kingdoms.remove(kingdomId);
        this.pendingInvites.entrySet().removeIf(e -> kingdomId.equals(e.getValue()));
        this.method_80();
        return true;
    }

    public class_2487 method_75(class_2487 nbt) {
        class_2499 list = new class_2499();
        for (KingdomData kingdom : this.kingdoms.values()) {
            list.add((Object)kingdom.toNbt());
        }
        nbt.method_10566("Kingdoms", (class_2520)list);
        class_2499 invites = new class_2499();
        for (Map.Entry<UUID, UUID> entry : this.pendingInvites.entrySet()) {
            class_2487 invite = new class_2487();
            invite.method_25927("Player", entry.getKey());
            invite.method_25927("Kingdom", entry.getValue());
            invites.add((Object)invite);
        }
        nbt.method_10566("Invites", (class_2520)invites);
        return nbt;
    }

    private static KingdomManager fromNbt(class_2487 nbt) {
        KingdomManager manager = new KingdomManager();
        class_2499 list = nbt.method_10554("Kingdoms", 10);
        for (int i = 0; i < list.size(); ++i) {
            KingdomData kingdom = KingdomData.fromNbt(list.method_10602(i));
            manager.kingdoms.put(kingdom.getId(), kingdom);
        }
        class_2499 invites = nbt.method_10554("Invites", 10);
        for (int i = 0; i < invites.size(); ++i) {
            class_2487 invite = invites.method_10602(i);
            if (!invite.method_25928("Player") || !invite.method_25928("Kingdom")) continue;
            manager.pendingInvites.put(invite.method_25926("Player"), invite.method_25926("Kingdom"));
        }
        return manager;
    }
}

