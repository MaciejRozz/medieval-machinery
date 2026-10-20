/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.command;

import com.medievalmachinery.mod.client.command.CommandOverlayScreen;
import com.medievalmachinery.mod.client.command.CommandSelectionPreferences;
import com.medievalmachinery.mod.client.npc.SoldierInventoryScreen;
import com.medievalmachinery.mod.network.SoldierCommandNetworking;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.ToIntFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class ClientCommandGroupStatus {
    private static UUID selectedCastleId;
    private static String selectedCastleName;
    private static List<Castle> CASTLES;
    private static List<Group> GROUPS;
    private static List<Member> MEMBERS;
    private static int entityContextId;
    private static int entityCurrentGroup;
    private static List<Group> ENTITY_GROUPS;

    private ClientCommandGroupStatus() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)SoldierCommandNetworking.GROUP_STATUS_SNAPSHOT, (client, handler, buf, sender) -> {
            UUID selectedId = null;
            String selectedName = "-";
            if (buf.readBoolean()) {
                selectedId = buf.method_10790();
                selectedName = buf.method_10800(48);
            }
            int castleCount = buf.method_10816();
            ArrayList<Castle> castles = new ArrayList<Castle>(castleCount);
            for (int i = 0; i < castleCount; ++i) {
                castles.add(new Castle(buf.method_10790(), buf.method_10800(48)));
            }
            int slotCount = Math.max(0, Math.min(512, buf.method_10816()));
            ArrayList<Group> groups = new ArrayList<Group>(slotCount);
            for (int slot = 1; slot <= slotCount; ++slot) {
                if (!buf.readBoolean()) continue;
                groups.add(new Group(slot, buf.method_10790(), buf.method_10800(24), buf.method_10800(16), buf.method_10816(), buf.method_10816(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt()));
            }
            int memberCount = buf.method_10816();
            ArrayList<Member> members = new ArrayList<Member>(memberCount);
            for (int i = 0; i < memberCount; ++i) {
                members.add(new Member(buf.method_10790(), buf.method_10816(), buf.readBoolean(), buf.method_10816(), buf.method_10800(48), buf.method_10800(24), buf.readFloat(), buf.readFloat(), buf.readBoolean(), buf.readBoolean()));
            }
            UUID finalSelectedId = selectedId;
            String finalSelectedName = selectedName;
            client.execute(() -> {
                selectedCastleId = finalSelectedId;
                selectedCastleName = finalSelectedName;
                CommandSelectionPreferences.setSelectedCastle(finalSelectedId);
                CASTLES = List.copyOf(castles);
                GROUPS = List.copyOf(groups);
                MEMBERS = List.copyOf(members);
                class_437 patt0$temp = client.field_1755;
                if (patt0$temp instanceof CommandOverlayScreen) {
                    CommandOverlayScreen commandScreen = (CommandOverlayScreen)patt0$temp;
                    commandScreen.refreshFromServerSnapshot();
                }
            });
        });
        ClientPlayNetworking.registerGlobalReceiver((class_2960)SoldierCommandNetworking.ENTITY_GROUP_CONTEXT_SNAPSHOT, (client, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            int currentSlot = buf.method_10816();
            int count = buf.method_10816();
            ArrayList<Group> groups = new ArrayList<Group>(Math.max(0, Math.min(512, count)));
            for (int i = 0; i < count; ++i) {
                int slot = buf.method_10816();
                UUID id = buf.method_10790();
                String name = buf.method_10800(24);
                String type = buf.method_10800(16);
                if (slot < 1 || slot > 512) continue;
                groups.add(new Group(slot, id, name, type, 0, 0, -2, -2, -2, -2, -2, -2));
            }
            client.execute(() -> {
                SoldierInventoryScreen soldierScreen;
                entityContextId = entityId;
                entityCurrentGroup = currentSlot;
                ENTITY_GROUPS = List.copyOf(groups);
                class_437 patt0$temp = client.field_1755;
                if (patt0$temp instanceof SoldierInventoryScreen && (soldierScreen = (SoldierInventoryScreen)patt0$temp).getSoldierEntityIdForContext() == entityId) {
                    soldierScreen.refreshGroupContext();
                }
            });
        });
    }

    public static void requestEntityContext(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.ENTITY_GROUP_CONTEXT_REQUEST, (class_2540)buf);
    }

    public static boolean hasEntityContext(int entityId) {
        return entityContextId == entityId;
    }

    public static int entityCurrentGroup(int entityId) {
        return entityContextId == entityId ? entityCurrentGroup : 0;
    }

    public static Group entityGroup(int entityId, int slot) {
        if (entityContextId != entityId || slot <= 0) {
            return null;
        }
        for (Group group : ENTITY_GROUPS) {
            if (group.slot() != slot) continue;
            return group;
        }
        return null;
    }

    public static List<Integer> entityAvailableGroups(int entityId) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(0);
        if (entityContextId != entityId) {
            return result;
        }
        for (Group group : ENTITY_GROUPS) {
            result.add(group.slot());
        }
        return result;
    }

    public static class_2561 entityGroupDisplayName(int entityId, int slot) {
        if (slot == 0) {
            return class_2561.method_43471((String)"screen.medieval-machinery.command.group.none");
        }
        Group group = ClientCommandGroupStatus.entityGroup(entityId, slot);
        if (group == null) {
            return class_2561.method_43470((String)"-");
        }
        return switch (group.type()) {
            case "infantry" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.infantry");
            case "ranged" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.shooters");
            case "knights" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.knights");
            case "mounted" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.mounted");
            case "machinists" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.machinists");
            default -> class_2561.method_43470((String)group.name());
        };
    }

    public static void requestAuto() {
        ClientCommandGroupStatus.request(CommandSelectionPreferences.getSelectedCastle(), true);
    }

    public static void request() {
        ClientCommandGroupStatus.request(selectedCastleId, false);
    }

    public static void selectCastle(UUID castleId) {
        ClientCommandGroupStatus.request(castleId, false);
    }

    private static void request(UUID castleId, boolean auto) {
        class_2540 buf = PacketByteBufs.create();
        buf.writeBoolean(auto);
        buf.writeBoolean(castleId != null);
        if (castleId != null) {
            buf.method_10797(castleId);
        }
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.GROUP_STATUS_REQUEST, (class_2540)buf);
    }

    public static UUID selectedCastleId() {
        return selectedCastleId;
    }

    public static String selectedCastleName() {
        return selectedCastleName;
    }

    public static List<Castle> castles() {
        return CASTLES;
    }

    public static Group group(int slot) {
        if (slot <= 0) {
            return null;
        }
        for (Group group : GROUPS) {
            if (group.slot() != slot) continue;
            return group;
        }
        return null;
    }

    public static List<Group> groups() {
        return GROUPS;
    }

    public static String groupName(int slot) {
        Group group = ClientCommandGroupStatus.group(slot);
        return group == null ? "-" : group.name();
    }

    public static int inRange(int slot) {
        if (slot == -1) {
            int sum = 0;
            for (Group group : GROUPS) {
                sum += group.inRange();
            }
            return sum;
        }
        Group group = ClientCommandGroupStatus.group(slot);
        return group == null ? 0 : group.inRange();
    }

    public static int total(int slot) {
        if (slot == -1) {
            int sum = 0;
            for (Group group : GROUPS) {
                sum += group.total();
            }
            return sum;
        }
        Group group = ClientCommandGroupStatus.group(slot);
        return group == null ? 0 : group.total();
    }

    private static int commonValue(int slot, ToIntFunction<Group> getter) {
        if (slot != -1) {
            Group group = ClientCommandGroupStatus.group(slot);
            return group == null ? -2 : getter.applyAsInt(group);
        }
        if (GROUPS.isEmpty()) {
            return -2;
        }
        int common = getter.applyAsInt(GROUPS.get(0));
        for (int i = 1; i < GROUPS.size(); ++i) {
            if (getter.applyAsInt(GROUPS.get(i)) == common) continue;
            return -1;
        }
        return common;
    }

    public static int formation(int slot) {
        return ClientCommandGroupStatus.commonValue(slot, Group::formation);
    }

    public static int shieldMode(int slot) {
        return ClientCommandGroupStatus.commonValue(slot, Group::shieldMode);
    }

    public static int order(int slot) {
        return ClientCommandGroupStatus.commonValue(slot, Group::order);
    }

    public static int stance(int slot) {
        return ClientCommandGroupStatus.commonValue(slot, Group::stance);
    }

    public static int holdFire(int slot) {
        return ClientCommandGroupStatus.commonValue(slot, Group::holdFire);
    }

    public static int areaFire(int slot) {
        return ClientCommandGroupStatus.commonValue(slot, Group::areaFire);
    }

    public static List<Member> members() {
        return MEMBERS;
    }

    public static List<Member> membersForGroup(int slot) {
        ArrayList<Member> result = new ArrayList<Member>();
        for (Member member : MEMBERS) {
            if (member.group() != slot) continue;
            result.add(member);
        }
        return result;
    }

    static {
        selectedCastleName = "-";
        CASTLES = List.of();
        GROUPS = List.of();
        MEMBERS = List.of();
        entityContextId = -1;
        ENTITY_GROUPS = List.of();
    }

    @Environment(value=EnvType.CLIENT)
    public record Group(int slot, UUID id, String name, String type, int inRange, int total, int order, int stance, int holdFire, int areaFire, int formation, int shieldMode) {
    }

    @Environment(value=EnvType.CLIENT)
    public record Member(UUID soldierId, int entityId, boolean loaded, int group, String name, String role, float health, float maxHealth, boolean hasFood, boolean hasWeapon) {
    }

    @Environment(value=EnvType.CLIENT)
    public record Castle(UUID id, String name) {
    }
}

