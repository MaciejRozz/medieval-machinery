/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_2540
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.network;

import com.medievalmachinery.mod.client.command.CommandOverlayScreen;
import com.medievalmachinery.mod.client.kingdom.DiplomacyScreen;
import com.medievalmachinery.mod.client.kingdom.KingdomOnboardingScreen;
import com.medievalmachinery.mod.client.kingdom.KingdomScreen;
import com.medievalmachinery.mod.network.KingdomNetworking;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyStance;
import com.medievalmachinery.mod.npc.organization.KingdomRelation;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class ClientKingdomNetworking {
    private static boolean embedNextSnapshot;
    private static UUID preferredDiplomacyTarget;
    private static boolean commandGatePending;

    private ClientKingdomNetworking() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)KingdomNetworking.SNAPSHOT, (client, handler, buf, sender) -> {
            boolean hasInvite;
            boolean has = buf.readBoolean();
            String name = "";
            String role = "";
            OrderStyle order = OrderStyle.NONE;
            UUID id = new UUID(0L, 0L);
            int members = 0;
            boolean king = false;
            boolean manage = false;
            if (has) {
                name = buf.method_10800(48);
                order = OrderStyle.fromSerializedName(buf.method_10800(32));
                role = buf.method_10800(24);
                id = buf.method_10790();
                members = buf.method_10816();
                king = buf.readBoolean();
                manage = buf.readBoolean();
            }
            String invite = (hasInvite = buf.readBoolean()) ? buf.method_10800(48) : "";
            List<KingdomEntry> joinable = ClientKingdomNetworking.readKingdomEntries(buf);
            Snapshot snapshot = new Snapshot(has, name, order, role, id, members, king, manage, hasInvite, invite, joinable);
            client.execute(() -> {
                class_437 patt1$temp;
                class_437 patt0$temp = client.field_1755;
                if (patt0$temp instanceof KingdomOnboardingScreen) {
                    KingdomOnboardingScreen onboarding = (KingdomOnboardingScreen)patt0$temp;
                    embedNextSnapshot = false;
                    commandGatePending = false;
                    onboarding.acceptSnapshot(snapshot);
                    return;
                }
                if (commandGatePending) {
                    commandGatePending = false;
                    embedNextSnapshot = false;
                    client.method_1507(snapshot.hasKingdom() ? new CommandOverlayScreen() : new KingdomOnboardingScreen(snapshot, false));
                } else if (embedNextSnapshot && (patt1$temp = client.field_1755) instanceof CommandOverlayScreen) {
                    CommandOverlayScreen commandScreen = (CommandOverlayScreen)patt1$temp;
                    embedNextSnapshot = false;
                    commandScreen.acceptKingdomSnapshot(snapshot);
                } else {
                    embedNextSnapshot = false;
                    client.method_1507((class_437)new KingdomScreen(snapshot));
                }
            });
        });
        ClientPlayNetworking.registerGlobalReceiver((class_2960)KingdomNetworking.ONBOARDING_SNAPSHOT, (client, handler, buf, sender) -> {
            boolean fromWatchpost = buf.readBoolean();
            Snapshot snapshot = ClientKingdomNetworking.readSnapshot(buf);
            client.execute(() -> client.method_1507((class_437)new KingdomOnboardingScreen(snapshot, fromWatchpost)));
        });
        ClientPlayNetworking.registerGlobalReceiver((class_2960)KingdomNetworking.DIPLOMACY_SNAPSHOT, (client, handler, buf, sender) -> {
            boolean has = buf.readBoolean();
            if (!has) {
                client.execute(() -> client.method_1507((class_437)new DiplomacyScreen(DiplomacySnapshot.none())));
                return;
            }
            UUID ownId = buf.method_10790();
            boolean canManage = buf.readBoolean();
            int count = buf.method_10816();
            ArrayList<DiplomacyEntry> entries = new ArrayList<DiplomacyEntry>(count);
            for (int i = 0; i < count; ++i) {
                KingdomRelation relation;
                UUID id = buf.method_10790();
                String name = buf.method_10800(48);
                OrderStyle order = OrderStyle.fromSerializedName(buf.method_10800(32));
                KingdomDiplomacyStance own = KingdomDiplomacyStance.fromName(buf.method_10800(16));
                KingdomDiplomacyStance other = KingdomDiplomacyStance.fromName(buf.method_10800(16));
                try {
                    relation = KingdomRelation.valueOf(buf.method_10800(24));
                }
                catch (IllegalArgumentException ex) {
                    relation = KingdomRelation.NEUTRAL;
                }
                entries.add(new DiplomacyEntry(id, name, order, own, other, relation));
            }
            DiplomacySnapshot snapshot = new DiplomacySnapshot(true, ownId, canManage, List.copyOf(entries));
            UUID preferred = preferredDiplomacyTarget;
            client.execute(() -> client.method_1507((class_437)new DiplomacyScreen(snapshot, preferred)));
        });
    }

    public static void requestOpen() {
        ClientPlayNetworking.send((class_2960)KingdomNetworking.REQUEST, (class_2540)PacketByteBufs.create());
    }

    public static void requestCommandGate() {
        commandGatePending = true;
        ClientPlayNetworking.send((class_2960)KingdomNetworking.REQUEST, (class_2540)PacketByteBufs.create());
    }

    public static void requestEmbedded() {
        embedNextSnapshot = true;
        ClientPlayNetworking.send((class_2960)KingdomNetworking.REQUEST, (class_2540)PacketByteBufs.create());
    }

    public static void requestDiplomacy() {
        ClientKingdomNetworking.requestDiplomacy(null);
    }

    public static void requestDiplomacy(UUID preferredTarget) {
        preferredDiplomacyTarget = preferredTarget;
        ClientPlayNetworking.send((class_2960)KingdomNetworking.DIPLOMACY_REQUEST, (class_2540)PacketByteBufs.create());
    }

    public static void rememberDiplomacyTarget(UUID target) {
        preferredDiplomacyTarget = target;
    }

    public static void setDiplomacy(UUID target, KingdomDiplomacyStance stance) {
        preferredDiplomacyTarget = target;
        class_2540 b = PacketByteBufs.create();
        b.method_10797(target);
        b.method_10788(stance.name(), 16);
        ClientPlayNetworking.send((class_2960)KingdomNetworking.DIPLOMACY_ACTION, (class_2540)b);
    }

    public static void createKingdom(String name, OrderStyle order) {
        ClientKingdomNetworking.send(0, name, order);
    }

    public static void rename(String name) {
        ClientKingdomNetworking.send(1, name, OrderStyle.NONE);
    }

    public static void changeOrder(OrderStyle order) {
        ClientKingdomNetworking.send(2, "", order);
    }

    public static void invite(String playerName) {
        ClientKingdomNetworking.send(3, playerName, OrderStyle.NONE);
    }

    public static void acceptInvite() {
        ClientKingdomNetworking.send(4, "", OrderStyle.NONE);
    }

    public static void declineInvite() {
        ClientKingdomNetworking.send(5, "", OrderStyle.NONE);
    }

    public static void promote(String playerName) {
        ClientKingdomNetworking.send(6, playerName, OrderStyle.NONE);
    }

    public static void demote(String playerName) {
        ClientKingdomNetworking.send(7, playerName, OrderStyle.NONE);
    }

    public static void kick(String playerName) {
        ClientKingdomNetworking.send(8, playerName, OrderStyle.NONE);
    }

    public static void leave() {
        ClientKingdomNetworking.send(9, "", OrderStyle.NONE);
    }

    public static void joinKingdom(UUID id) {
        ClientKingdomNetworking.send(10, id == null ? "" : id.toString(), OrderStyle.NONE);
    }

    private static Snapshot readSnapshot(class_2540 buf) {
        boolean hasInvite;
        boolean has = buf.readBoolean();
        String name = "";
        String role = "";
        OrderStyle order = OrderStyle.NONE;
        UUID id = new UUID(0L, 0L);
        int members = 0;
        boolean king = false;
        boolean manage = false;
        if (has) {
            name = buf.method_10800(48);
            order = OrderStyle.fromSerializedName(buf.method_10800(32));
            role = buf.method_10800(24);
            id = buf.method_10790();
            members = buf.method_10816();
            king = buf.readBoolean();
            manage = buf.readBoolean();
        }
        String invite = (hasInvite = buf.readBoolean()) ? buf.method_10800(48) : "";
        return new Snapshot(has, name, order, role, id, members, king, manage, hasInvite, invite, ClientKingdomNetworking.readKingdomEntries(buf));
    }

    private static List<KingdomEntry> readKingdomEntries(class_2540 buf) {
        int count = buf.method_10816();
        ArrayList<KingdomEntry> entries = new ArrayList<KingdomEntry>(count);
        for (int i = 0; i < count; ++i) {
            entries.add(new KingdomEntry(buf.method_10790(), buf.method_10800(48), OrderStyle.fromSerializedName(buf.method_10800(32)), buf.method_10816()));
        }
        return List.copyOf(entries);
    }

    private static void send(int action, String value, OrderStyle order) {
        class_310 client = class_310.method_1551();
        if (client.field_1755 instanceof CommandOverlayScreen) {
            embedNextSnapshot = true;
        }
        class_2540 b = PacketByteBufs.create();
        b.method_10804(action);
        b.method_10788(value == null ? "" : value, 48);
        b.method_10788(order == null ? OrderStyle.NONE.method_15434() : order.method_15434(), 32);
        ClientPlayNetworking.send((class_2960)KingdomNetworking.ACTION, (class_2540)b);
    }

    @Environment(value=EnvType.CLIENT)
    public record Snapshot(boolean hasKingdom, String name, OrderStyle mainOrder, String role, UUID kingdomId, int memberCount, boolean king, boolean canManageMembers, boolean hasInvite, String inviteKingdom, List<KingdomEntry> joinableKingdoms) {
        public static Snapshot none(boolean hasInvite, String inviteKingdom) {
            return new Snapshot(false, "", OrderStyle.NONE, "", new UUID(0L, 0L), 0, false, false, hasInvite, inviteKingdom, List.of());
        }
    }

    @Environment(value=EnvType.CLIENT)
    public record KingdomEntry(UUID kingdomId, String name, OrderStyle mainOrder, int memberCount) {
    }

    @Environment(value=EnvType.CLIENT)
    public record DiplomacyEntry(UUID kingdomId, String name, OrderStyle mainOrder, KingdomDiplomacyStance ownStance, KingdomDiplomacyStance otherStance, KingdomRelation relation) {
    }

    @Environment(value=EnvType.CLIENT)
    public record DiplomacySnapshot(boolean hasKingdom, UUID ownKingdomId, boolean canManage, List<DiplomacyEntry> kingdoms) {
        public static DiplomacySnapshot none() {
            return new DiplomacySnapshot(false, new UUID(0L, 0L), false, List.of());
        }
    }
}

