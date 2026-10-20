/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyManager;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyService;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyStance;
import com.medievalmachinery.mod.npc.organization.KingdomGameplayService;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.KingdomPermission;
import com.medievalmachinery.mod.npc.organization.KingdomRole;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.npc.organization.SystemKingdoms;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_3222;

public final class KingdomNetworking {
    public static final class_2960 REQUEST = MedievalMachinery.id("kingdom/request");
    public static final class_2960 ACTION = MedievalMachinery.id("kingdom/action");
    public static final class_2960 SNAPSHOT = MedievalMachinery.id("kingdom/snapshot");
    public static final class_2960 ONBOARDING_SNAPSHOT = MedievalMachinery.id("kingdom/onboarding_snapshot");
    public static final class_2960 DIPLOMACY_REQUEST = MedievalMachinery.id("kingdom/diplomacy_request");
    public static final class_2960 DIPLOMACY_ACTION = MedievalMachinery.id("kingdom/diplomacy_action");
    public static final class_2960 DIPLOMACY_SNAPSHOT = MedievalMachinery.id("kingdom/diplomacy_snapshot");
    public static final int ACTION_CREATE = 0;
    public static final int ACTION_RENAME = 1;
    public static final int ACTION_CHANGE_ORDER = 2;
    public static final int ACTION_INVITE = 3;
    public static final int ACTION_ACCEPT_INVITE = 4;
    public static final int ACTION_DECLINE_INVITE = 5;
    public static final int ACTION_PROMOTE = 6;
    public static final int ACTION_DEMOTE = 7;
    public static final int ACTION_KICK = 8;
    public static final int ACTION_LEAVE = 9;
    public static final int ACTION_JOIN = 10;

    private KingdomNetworking() {
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver((class_2960)REQUEST, (server, player, handler, buf, sender) -> server.execute(() -> KingdomNetworking.sendSnapshot(player)));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)ACTION, (server, player, handler, buf, sender) -> {
            int action = buf.method_10816();
            String value = buf.method_10800(48);
            String orderName = buf.method_10800(32);
            server.execute(() -> {
                KingdomManager manager = KingdomManager.get(player.method_51469());
                OrderStyle order = OrderStyle.fromSerializedName(orderName);
                switch (action) {
                    case 0: {
                        KingdomGameplayService.createKingdom(player, value, order);
                        break;
                    }
                    case 1: {
                        KingdomGameplayService.rename(player, value);
                        break;
                    }
                    case 2: {
                        KingdomGameplayService.changeMainOrder(player, order);
                        break;
                    }
                    case 3: {
                        KingdomNetworking.invite(player, value, manager);
                        break;
                    }
                    case 4: {
                        if (!manager.acceptInvite(player.method_5667())) break;
                        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.invite_accepted"), false);
                        break;
                    }
                    case 5: {
                        manager.declineInvite(player.method_5667());
                        break;
                    }
                    case 6: {
                        KingdomNetworking.changeMemberRole(player, value, manager, KingdomRole.OFFICER);
                        break;
                    }
                    case 7: {
                        KingdomNetworking.changeMemberRole(player, value, manager, KingdomRole.MEMBER);
                        break;
                    }
                    case 8: {
                        KingdomNetworking.kickMember(player, value, manager);
                        break;
                    }
                    case 10: {
                        KingdomNetworking.joinExisting(player, value, manager);
                        break;
                    }
                    case 9: {
                        if (manager.leave(player.method_5667())) {
                            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.left"), false);
                            break;
                        }
                        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.leave_failed"), false);
                        break;
                    }
                }
                KingdomNetworking.sendSnapshot(player);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)DIPLOMACY_REQUEST, (server, player, handler, buf, sender) -> server.execute(() -> KingdomNetworking.sendDiplomacySnapshot(player)));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)DIPLOMACY_ACTION, (server, player, handler, buf, sender) -> {
            UUID target = buf.method_10790();
            KingdomDiplomacyStance stance = KingdomDiplomacyStance.fromName(buf.method_10800(16));
            server.execute(() -> {
                boolean changed;
                KingdomManager manager = KingdomManager.get(player.method_51469());
                KingdomData source = manager.findByMember(player.method_5667());
                KingdomData targetKingdom = manager.get(target);
                KingdomDiplomacyStance previous = source == null ? KingdomDiplomacyStance.NEUTRAL : KingdomDiplomacyManager.get(player.method_51469()).getStance(source.getId(), target);
                boolean unchanged = source != null && previous == stance;
                boolean bl = changed = !unchanged && KingdomDiplomacyService.setStance(player, target, stance);
                if ((changed || unchanged) && source != null && targetKingdom != null) {
                    if (changed) {
                        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.diplomacy.changed", (Object[])new Object[]{targetKingdom.getName(), KingdomNetworking.stanceText(stance)}), false);
                        KingdomNetworking.notifyTargetKingdom(player, source, targetKingdom, stance);
                        KingdomNetworking.sendDiplomacySnapshotToKingdom(player, source);
                        KingdomNetworking.sendDiplomacySnapshotToKingdom(player, targetKingdom);
                    } else {
                        KingdomNetworking.sendDiplomacySnapshot(player);
                    }
                } else {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.diplomacy.change_failed"), false);
                    KingdomNetworking.sendDiplomacySnapshot(player);
                }
            });
        });
    }

    private static class_2561 stanceText(KingdomDiplomacyStance stance) {
        return class_2561.method_43471((String)("diplomacy.stance.medieval-machinery." + stance.name().toLowerCase(Locale.ROOT)));
    }

    private static void notifyTargetKingdom(class_3222 actor, KingdomData source, KingdomData target, KingdomDiplomacyStance stance) {
        String key = switch (stance) {
            default -> throw new IncompatibleClassChangeError();
            case KingdomDiplomacyStance.ALLY -> "message.medieval-machinery.diplomacy.alliance_offer";
            case KingdomDiplomacyStance.WAR -> "message.medieval-machinery.diplomacy.war_declared";
            case KingdomDiplomacyStance.NEUTRAL -> "message.medieval-machinery.diplomacy.neutral_notice";
        };
        for (UUID memberId : target.getMembers().keySet()) {
            class_3222 member = actor.method_5682().method_3760().method_14602(memberId);
            if (member == null) continue;
            member.method_7353((class_2561)class_2561.method_43469((String)key, (Object[])new Object[]{source.getName()}), false);
        }
    }

    private static void sendDiplomacySnapshotToKingdom(class_3222 actor, KingdomData kingdom) {
        if (actor == null || kingdom == null || actor.method_5682() == null) {
            return;
        }
        for (UUID memberId : kingdom.getMembers().keySet()) {
            class_3222 member = actor.method_5682().method_3760().method_14602(memberId);
            if (member == null) continue;
            KingdomNetworking.sendDiplomacySnapshot(member);
        }
    }

    public static void sendDiplomacySnapshot(class_3222 player) {
        KingdomManager kingdoms = KingdomManager.get(player.method_51469());
        KingdomData own = kingdoms.findByMember(player.method_5667());
        class_2540 out = PacketByteBufs.create();
        out.writeBoolean(own != null);
        if (own != null) {
            KingdomDiplomacyManager diplomacy = KingdomDiplomacyManager.get(player.method_51469());
            out.method_10797(own.getId());
            out.writeBoolean(own.hasPermission(player.method_5667(), KingdomPermission.MANAGE_DIPLOMACY));
            List<KingdomData> others = kingdoms.getKingdoms().stream().filter(k -> !k.getId().equals(own.getId())).sorted(Comparator.comparing(KingdomData::getName, String.CASE_INSENSITIVE_ORDER)).toList();
            out.method_10804(others.size());
            for (KingdomData other : others) {
                out.method_10797(other.getId());
                out.method_10788(other.getName(), 48);
                out.method_10788(other.getMainOrder().method_15434(), 32);
                out.method_10788(diplomacy.getStance(own.getId(), other.getId()).name(), 16);
                out.method_10788(diplomacy.getStance(other.getId(), own.getId()).name(), 16);
                out.method_10788(diplomacy.getEffectiveRelation(own.getId(), other.getId()).name(), 24);
            }
        }
        ServerPlayNetworking.send((class_3222)player, (class_2960)DIPLOMACY_SNAPSHOT, (class_2540)out);
    }

    private static void invite(class_3222 player, String targetName, KingdomManager manager) {
        KingdomData kingdom = manager.findByMember(player.method_5667());
        class_3222 target = player.method_5682().method_3760().method_14566(targetName);
        if (kingdom == null || target == null || !manager.invite(kingdom.getId(), player.method_5667(), target.method_5667())) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.invite_failed"), false);
            return;
        }
        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.invite_sent", (Object[])new Object[]{target.method_7334().getName()}), false);
        target.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.invited", (Object[])new Object[]{kingdom.getName()}), false);
    }

    private static void changeMemberRole(class_3222 player, String targetName, KingdomManager manager, KingdomRole role) {
        KingdomData kingdom = manager.findByMember(player.method_5667());
        class_3222 target = player.method_5682().method_3760().method_14566(targetName);
        if (kingdom == null || target == null || !manager.setMemberRole(kingdom.getId(), player.method_5667(), target.method_5667(), role)) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.member_change_failed"), false);
            return;
        }
        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.member_role_changed", (Object[])new Object[]{target.method_7334().getName(), class_2561.method_43471((String)("kingdom.role.medieval-machinery." + role.name().toLowerCase(Locale.ROOT)))}), false);
        KingdomNetworking.sendSnapshot(target);
    }

    private static void kickMember(class_3222 player, String targetName, KingdomManager manager) {
        KingdomData kingdom = manager.findByMember(player.method_5667());
        class_3222 target = player.method_5682().method_3760().method_14566(targetName);
        if (kingdom == null || target == null || !manager.removeMember(kingdom.getId(), player.method_5667(), target.method_5667())) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.kingdom.member_change_failed"), false);
            return;
        }
        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.member_kicked", (Object[])new Object[]{target.method_7334().getName()}), false);
        target.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.kicked", (Object[])new Object[]{kingdom.getName()}), false);
        KingdomNetworking.sendSnapshot(target);
    }

    private static void joinExisting(class_3222 player, String kingdomId, KingdomManager manager) {
        UUID id;
        if (manager.findByMember(player.method_5667()) != null) {
            return;
        }
        try {
            id = UUID.fromString(kingdomId);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        if (SystemKingdoms.DRAGON_KNIGHTS_ID.equals(id)) {
            return;
        }
        KingdomData kingdom = manager.get(id);
        if (kingdom == null || !manager.addMember(id, player.method_5667())) {
            return;
        }
        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.kingdom.joined", (Object[])new Object[]{kingdom.getName()}), false);
    }

    public static void sendOnboardingSnapshot(class_3222 player, boolean fromWatchpost) {
        KingdomManager manager = KingdomManager.get(player.method_51469());
        class_2540 out = PacketByteBufs.create();
        out.writeBoolean(fromWatchpost);
        KingdomNetworking.writeSnapshotPayload(player, manager, out, true);
        ServerPlayNetworking.send((class_3222)player, (class_2960)ONBOARDING_SNAPSHOT, (class_2540)out);
    }

    public static void sendSnapshot(class_3222 player) {
        KingdomManager manager = KingdomManager.get(player.method_51469());
        class_2540 out = PacketByteBufs.create();
        KingdomNetworking.writeSnapshotPayload(player, manager, out, true);
        ServerPlayNetworking.send((class_3222)player, (class_2960)SNAPSHOT, (class_2540)out);
    }

    private static void writeSnapshotPayload(class_3222 player, KingdomManager manager, class_2540 out, boolean includeKingdomList) {
        KingdomData kingdom = manager.findByMember(player.method_5667());
        KingdomData invite = manager.getPendingInvite(player.method_5667());
        out.writeBoolean(kingdom != null);
        if (kingdom != null) {
            out.method_10788(kingdom.getName(), 48);
            out.method_10788(kingdom.getMainOrder().method_15434(), 32);
            out.method_10788(kingdom.getRole(player.method_5667()).name(), 24);
            out.method_10797(kingdom.getId());
            out.method_10804(kingdom.getMembers().size());
            out.writeBoolean(kingdom.getKingId().equals(player.method_5667()));
            out.writeBoolean(kingdom.hasPermission(player.method_5667(), KingdomPermission.MANAGE_MEMBERS));
        }
        out.writeBoolean(invite != null);
        if (invite != null) {
            out.method_10788(invite.getName(), 48);
        }
        List<KingdomData> joinable = includeKingdomList ? manager.getKingdoms().stream().filter(k -> !SystemKingdoms.DRAGON_KNIGHTS_ID.equals(k.getId())).sorted(Comparator.comparing(KingdomData::getName, String.CASE_INSENSITIVE_ORDER)).toList() : List.of();
        out.method_10804(joinable.size());
        for (KingdomData existing : joinable) {
            out.method_10797(existing.getId());
            out.method_10788(existing.getName(), 48);
            out.method_10788(existing.getMainOrder().method_15434(), 32);
            out.method_10804(existing.getMembers().size());
        }
    }
}

