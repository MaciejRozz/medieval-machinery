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

import com.medievalmachinery.mod.npc.organization.KingdomPermission;
import com.medievalmachinery.mod.npc.organization.KingdomRole;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import org.jetbrains.annotations.Nullable;

public final class KingdomData {
    private final UUID id;
    private UUID kingId;
    private String name;
    private OrderStyle mainOrder;
    private final Map<UUID, KingdomRole> members = new LinkedHashMap<UUID, KingdomRole>();

    public KingdomData(UUID id, UUID kingId, String name, OrderStyle mainOrder) {
        this.id = id;
        this.kingId = kingId;
        this.name = KingdomData.sanitizeName(name);
        this.mainOrder = mainOrder == null ? OrderStyle.NONE : mainOrder;
        this.members.put(kingId, KingdomRole.KING);
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getKingId() {
        return this.kingId;
    }

    public String getName() {
        return this.name;
    }

    public OrderStyle getMainOrder() {
        return this.mainOrder;
    }

    public Map<UUID, KingdomRole> getMembers() {
        return Map.copyOf(this.members);
    }

    public void setName(String value) {
        this.name = KingdomData.sanitizeName(value);
    }

    public void setMainOrder(OrderStyle value) {
        this.mainOrder = value == null ? OrderStyle.NONE : value;
    }

    public boolean isMember(UUID playerId) {
        return this.members.containsKey(playerId);
    }

    @Nullable
    public KingdomRole getRole(UUID playerId) {
        return this.members.get(playerId);
    }

    public boolean addMember(UUID playerId) {
        if (this.members.containsKey(playerId)) {
            return false;
        }
        this.members.put(playerId, KingdomRole.MEMBER);
        return true;
    }

    public boolean removeMember(UUID playerId) {
        if (this.kingId.equals(playerId)) {
            return false;
        }
        return this.members.remove(playerId) != null;
    }

    public boolean setRole(UUID playerId, KingdomRole role) {
        if (playerId == null || role == null || this.kingId.equals(playerId) || !this.members.containsKey(playerId)) {
            return false;
        }
        if (role == KingdomRole.KING) {
            return false;
        }
        this.members.put(playerId, role);
        return true;
    }

    public boolean hasPermission(UUID playerId, KingdomPermission permission) {
        KingdomRole role = this.members.get(playerId);
        if (role == null) {
            return false;
        }
        if (role == KingdomRole.KING) {
            return true;
        }
        if (role == KingdomRole.OFFICER) {
            return switch (permission) {
                default -> throw new IncompatibleClassChangeError();
                case KingdomPermission.RENAME_KINGDOM, KingdomPermission.CHANGE_MAIN_ORDER -> false;
                case KingdomPermission.MANAGE_MEMBERS, KingdomPermission.MANAGE_DIPLOMACY, KingdomPermission.MINT_SIGIL, KingdomPermission.ADMINISTER_CASTLE, KingdomPermission.RECRUIT -> true;
            };
        }
        return switch (permission) {
            default -> throw new IncompatibleClassChangeError();
            case KingdomPermission.MINT_SIGIL, KingdomPermission.ADMINISTER_CASTLE, KingdomPermission.RECRUIT -> true;
            case KingdomPermission.RENAME_KINGDOM, KingdomPermission.CHANGE_MAIN_ORDER, KingdomPermission.MANAGE_MEMBERS, KingdomPermission.MANAGE_DIPLOMACY -> false;
        };
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        nbt.method_25927("Id", this.id);
        nbt.method_25927("King", this.kingId);
        nbt.method_10582("Name", this.name);
        nbt.method_10582("MainOrder", this.mainOrder.method_15434());
        class_2499 list = new class_2499();
        for (Map.Entry<UUID, KingdomRole> entry : this.members.entrySet()) {
            class_2487 member = new class_2487();
            member.method_25927("Player", entry.getKey());
            member.method_10582("Role", entry.getValue().name());
            list.add((Object)member);
        }
        nbt.method_10566("Members", (class_2520)list);
        return nbt;
    }

    public static KingdomData fromNbt(class_2487 nbt) {
        UUID id = nbt.method_25926("Id");
        UUID king = nbt.method_25926("King");
        KingdomData data = new KingdomData(id, king, nbt.method_10558("Name"), OrderStyle.fromSerializedName(nbt.method_10558("MainOrder")));
        data.members.clear();
        class_2499 list = nbt.method_10554("Members", 10);
        for (int i = 0; i < list.size(); ++i) {
            KingdomRole role;
            class_2487 member = list.method_10602(i);
            if (!member.method_25928("Player")) continue;
            try {
                role = KingdomRole.valueOf(member.method_10558("Role"));
            }
            catch (IllegalArgumentException ex) {
                role = KingdomRole.MEMBER;
            }
            data.members.put(member.method_25926("Player"), role);
        }
        data.members.put(king, KingdomRole.KING);
        return data;
    }

    private static String sanitizeName(String value) {
        if (value == null) {
            return "K\u00f6nigreich";
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return "K\u00f6nigreich";
        }
        return trimmed.length() > 48 ? trimmed.substring(0, 48) : trimmed;
    }
}

