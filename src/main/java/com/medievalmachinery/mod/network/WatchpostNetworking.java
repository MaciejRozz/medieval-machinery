/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.event.player.AttackBlockCallback
 *  net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
 *  net.fabricmc.fabric.api.event.player.UseBlockCallback
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1542
 *  net.minecraft.class_1657
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2281
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2350$class_2353
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2595
 *  net.minecraft.class_2680
 *  net.minecraft.class_2741
 *  net.minecraft.class_2745
 *  net.minecraft.class_2769
 *  net.minecraft.class_2960
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_5321
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.block.garrison.WatchpostBlock;
import com.medievalmachinery.mod.block.garrison.WatchpostBlockEntity;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CastleAccessPolicy;
import com.medievalmachinery.mod.npc.organization.CastleAreaService;
import com.medievalmachinery.mod.npc.organization.CastleLocalAggressionService;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.GarrisonRallyPolicy;
import com.medievalmachinery.mod.npc.organization.PromotionService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2281;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2595;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2745;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_5321;

public final class WatchpostNetworking {
    private static final Map<UUID, UUID> ACTIVE_SUPPLY_EDIT = new ConcurrentHashMap<UUID, UUID>();
    private static final Map<UUID, UUID> ACTIVE_TAX_EDIT = new ConcurrentHashMap<UUID, UUID>();
    private static final Map<UUID, UUID> ACTIVE_RALLY_EDIT = new ConcurrentHashMap<UUID, UUID>();
    private static final Map<UUID, UUID> ACTIVE_CASTLE_AREA_EDIT = new ConcurrentHashMap<UUID, UUID>();
    private static final Map<UUID, PendingWatchpostBreak> PENDING_WATCHPOST_BREAKS = new ConcurrentHashMap<UUID, PendingWatchpostBreak>();
    public static final class_2960 RENAME = MedievalMachinery.id("watchpost/rename");
    public static final class_2960 RECRUIT = MedievalMachinery.id("watchpost/recruit");
    public static final class_2960 ASSIGN_GROUP_CASTLE = MedievalMachinery.id("watchpost/assign_group_castle");
    public static final class_2960 DETACH_GROUP_CASTLE = MedievalMachinery.id("watchpost/detach_group_castle");
    public static final class_2960 ASSIGN_SUPPLY = MedievalMachinery.id("watchpost/edit_supply");
    public static final class_2960 SUPPLY_EDIT_ACTION = MedievalMachinery.id("watchpost/supply_edit_action");
    public static final class_2960 SUPPLY_EDIT_STOP = MedievalMachinery.id("watchpost/supply_edit_stop");
    public static final class_2960 SUPPLY_EDIT_SNAPSHOT = MedievalMachinery.id("watchpost/supply_edit_snapshot");
    public static final class_2960 ASSIGN_TAX = MedievalMachinery.id("watchpost/edit_tax");
    public static final class_2960 TAX_EDIT_ACTION = MedievalMachinery.id("watchpost/tax_edit_action");
    public static final class_2960 TAX_EDIT_STOP = MedievalMachinery.id("watchpost/tax_edit_stop");
    public static final class_2960 TAX_EDIT_SNAPSHOT = MedievalMachinery.id("watchpost/tax_edit_snapshot");
    public static final class_2960 TAX_CHEST_SYNC = MedievalMachinery.id("watchpost/tax_chest_sync");
    public static final class_2960 PROMOTE = MedievalMachinery.id("watchpost/promote");
    public static final class_2960 ASSIGN_RALLY = MedievalMachinery.id("watchpost/edit_rally");
    public static final class_2960 RALLY_EDIT_ACTION = MedievalMachinery.id("watchpost/rally_edit_action");
    public static final class_2960 RALLY_EDIT_STOP = MedievalMachinery.id("watchpost/rally_edit_stop");
    public static final class_2960 RALLY_EDIT_SNAPSHOT = MedievalMachinery.id("watchpost/rally_edit_snapshot");
    public static final class_2960 ASSIGN_CASTLE_AREA = MedievalMachinery.id("watchpost/edit_castle_area");
    public static final class_2960 CASTLE_AREA_EDIT_ACTION = MedievalMachinery.id("watchpost/castle_area_edit_action");
    public static final class_2960 CASTLE_AREA_EDIT_CLEAR = MedievalMachinery.id("watchpost/castle_area_edit_clear");
    public static final class_2960 CASTLE_AREA_EDIT_STOP = MedievalMachinery.id("watchpost/castle_area_edit_stop");
    public static final class_2960 CASTLE_AREA_EDIT_SNAPSHOT = MedievalMachinery.id("watchpost/castle_area_edit_snapshot");
    public static final class_2960 BREAK_PROMPT = MedievalMachinery.id("watchpost/break_prompt");
    public static final class_2960 BREAK_ACTION = MedievalMachinery.id("watchpost/break_action");
    public static final int BREAK_MOVE = 0;
    public static final int BREAK_DISSOLVE = 1;

    private WatchpostNetworking() {
    }

    public static void registerServer() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            class_3218 serverWorld;
            block7: {
                block6: {
                    if (!(world instanceof class_3218)) break block6;
                    serverWorld = (class_3218)world;
                    if (player instanceof class_3222) break block7;
                }
                return class_1269.field_5811;
            }
            class_3222 serverPlayer = (class_3222)player;
            class_2680 state = serverWorld.method_8320(pos);
            if (!(state.method_26204() instanceof WatchpostBlock)) {
                return class_1269.field_5811;
            }
            class_2338 mainPos = WatchpostBlock.getMainPos(state, pos);
            class_2586 mainEntity = serverWorld.method_8321(mainPos);
            if (!(mainEntity instanceof WatchpostBlockEntity)) {
                return class_1269.field_5811;
            }
            WatchpostBlockEntity watchpost = (WatchpostBlockEntity)mainEntity;
            if (watchpost.blocksForeignBreak((class_1657)serverPlayer)) {
                serverPlayer.method_7353((class_2561)class_2561.method_43471((String)(watchpost.hasGeneralProtection() ? "message.medieval-machinery.watchpost.general_protected" : "message.medieval-machinery.watchpost.foreign_break_blocked")), true);
                return class_1269.field_5814;
            }
            return class_1269.field_5811;
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            class_3218 serverWorld;
            block5: {
                block4: {
                    if (!(world instanceof class_3218)) break block4;
                    serverWorld = (class_3218)world;
                    if (player instanceof class_3222) break block5;
                }
                return class_1269.field_5811;
            }
            class_3222 serverPlayer = (class_3222)player;
            class_2338 clicked = hitResult.method_17777();
            if (!(serverWorld.method_8321(clicked) instanceof class_2595)) {
                return class_1269.field_5811;
            }
            CastleLocalAggressionService.onSupplyChestOpened(serverWorld, serverPlayer, clicked);
            return class_1269.field_5811;
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)RENAME, (server, player, handler, buf, sender) -> {
            class_2338 pos = buf.method_10811();
            String name = buf.method_10800(48);
            server.execute(() -> {
                WatchpostBlockEntity watchpost;
                class_2586 be = player.method_51469().method_8321(pos);
                if (be instanceof WatchpostBlockEntity && (watchpost = (WatchpostBlockEntity)be).renameCastle(player, name)) {
                    player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.watchpost.renamed", (Object[])new Object[]{watchpost.getCastleName()}), false);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)RECRUIT, (server, player, handler, buf, sender) -> {
            class_2338 pos = buf.method_10811();
            int roleOrdinal = buf.method_10816();
            UUID groupId = buf.readBoolean() ? buf.method_10790() : null;
            server.execute(() -> {
                if (roleOrdinal < 0 || roleOrdinal >= NpcRole.values().length) {
                    return;
                }
                if (player.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5) > 64.0) {
                    return;
                }
                class_2586 be = player.method_51469().method_8321(pos);
                if (!(be instanceof WatchpostBlockEntity)) {
                    return;
                }
                WatchpostBlockEntity watchpost = (WatchpostBlockEntity)be;
                NpcRole role = NpcRole.values()[roleOrdinal];
                boolean couldAfford = watchpost.canAffordRecruitment(player, role);
                int emeraldCost = watchpost.getRecruitmentEmeraldCost(role);
                boolean success = watchpost.recruit(player, role, groupId);
                if (!success) {
                    if (!watchpost.hasValidStampedSigil()) {
                        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.watchpost.requires_stamped_sigil"), false);
                    } else if (!couldAfford) {
                        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.watchpost.not_enough_emeralds", (Object[])new Object[]{emeraldCost}), false);
                    } else {
                        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.watchpost.not_enough_villagers", (Object[])new Object[]{10, watchpost.getTotalVillagerCount(), watchpost.getAvailableVillagerCount()}), false);
                    }
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)PROMOTE, (server, player, handler, buf, sender) -> {
            class_2338 pos = buf.method_10811();
            UUID soldierId = buf.method_10790();
            server.execute(() -> {
                WatchpostBlockEntity watchpost;
                if (player.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5) > 64.0) {
                    return;
                }
                class_2586 be = player.method_51469().method_8321(pos);
                if (!(be instanceof WatchpostBlockEntity) || (watchpost = (WatchpostBlockEntity)be).getCastleId() == null) {
                    return;
                }
                PromotionService.Result result = PromotionService.promote(player.method_51469(), player, watchpost.getCastleId(), soldierId);
                String key = switch (result) {
                    case PromotionService.Result.PROMOTED_CAPTAIN -> "message.medieval-machinery.promotion.captain";
                    case PromotionService.Result.PROMOTED_GENERAL -> "message.medieval-machinery.promotion.general";
                    case PromotionService.Result.MISSING_COSTS -> "message.medieval-machinery.promotion.missing_costs";
                    case PromotionService.Result.GENERAL_COOLDOWN -> "message.medieval-machinery.promotion.cooldown";
                    case PromotionService.Result.CANDIDATE_NOT_LOADED -> "message.medieval-machinery.promotion.not_loaded";
                    case PromotionService.Result.NO_PERMISSION -> "message.medieval-machinery.promotion.no_permission";
                    default -> "message.medieval-machinery.promotion.not_eligible";
                };
                player.method_7353((class_2561)class_2561.method_43471((String)key), false);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)ASSIGN_GROUP_CASTLE, (server, player, handler, buf, sender) -> {
            class_2338 pos = buf.method_10811();
            UUID groupId = buf.method_10790();
            server.execute(() -> {
                WatchpostBlockEntity watchpost;
                class_2586 be = player.method_51469().method_8321(pos);
                if (be instanceof WatchpostBlockEntity && (watchpost = (WatchpostBlockEntity)be).assignGroupToThisCastle(player, groupId)) {
                    player.method_7353((class_2561)class_2561.method_43470((String)"Gruppe dieser Burg zugewiesen."), false);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)DETACH_GROUP_CASTLE, (server, player, handler, buf, sender) -> {
            class_2338 pos = buf.method_10811();
            UUID groupId = buf.method_10790();
            server.execute(() -> {
                WatchpostBlockEntity watchpost;
                class_2586 be = player.method_51469().method_8321(pos);
                if (be instanceof WatchpostBlockEntity && (watchpost = (WatchpostBlockEntity)be).detachGroupFromCastle(player, groupId)) {
                    player.method_7353((class_2561)class_2561.method_43470((String)"Gruppe aus Burg gel\u00f6st."), false);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)ASSIGN_RALLY, (server, player, handler, buf, sender) -> {
            class_2338 watchpostPos = buf.method_10811();
            server.execute(() -> {
                WatchpostBlockEntity watchpost;
                class_2586 be = player.method_51469().method_8321(watchpostPos);
                if (be instanceof WatchpostBlockEntity && (watchpost = (WatchpostBlockEntity)be).canPlayerAdminister((class_1657)player) && watchpost.getCastleId() != null) {
                    ACTIVE_RALLY_EDIT.put(player.method_5667(), watchpost.getCastleId());
                    player.method_7346();
                    WatchpostNetworking.sendRallyEditSnapshot(player, watchpost.getCastleId());
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)RALLY_EDIT_ACTION, (server, player, handler, buf, sender) -> {
            class_2338 clicked = buf.method_10811();
            boolean remove = buf.readBoolean();
            server.execute(() -> {
                UUID castleId = ACTIVE_RALLY_EDIT.get(player.method_5667());
                if (castleId == null) {
                    return;
                }
                GarrisonManager manager = GarrisonManager.get(player.method_51469());
                GarrisonData garrison = manager.get(castleId);
                if (garrison == null || !WatchpostNetworking.canAdministerGarrison(player, garrison)) {
                    ACTIVE_RALLY_EDIT.remove(player.method_5667());
                    return;
                }
                if (garrison.getCenter() != null && garrison.getCenter().method_10262((class_2382)clicked) > 2500.0) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.rally.too_far"), true);
                    return;
                }
                class_2338 oldRally = garrison.getRallyPoint();
                boolean changed = manager.setRallyPoint(castleId, remove ? null : clicked);
                if (changed) {
                    WatchpostNetworking.refreshIdleRallyAnchors(player, garrison, oldRally);
                }
                player.method_7353((class_2561)class_2561.method_43471((String)(remove ? (changed ? "message.medieval-machinery.rally.removed" : "message.medieval-machinery.rally.not_set") : (changed ? "message.medieval-machinery.rally.assigned" : "message.medieval-machinery.rally.already_assigned"))), true);
                WatchpostNetworking.sendRallyEditSnapshot(player, castleId);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)ASSIGN_CASTLE_AREA, (server, player, handler, buf, sender) -> {
            class_2338 watchpostPos = buf.method_10811();
            server.execute(() -> {
                WatchpostBlockEntity watchpost;
                class_2586 be = player.method_51469().method_8321(watchpostPos);
                if (be instanceof WatchpostBlockEntity && (watchpost = (WatchpostBlockEntity)be).canPlayerAdminister((class_1657)player) && watchpost.getCastleId() != null) {
                    ACTIVE_CASTLE_AREA_EDIT.put(player.method_5667(), watchpost.getCastleId());
                    player.method_7346();
                    WatchpostNetworking.sendCastleAreaEditSnapshot(player, watchpost.getCastleId());
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)CASTLE_AREA_EDIT_ACTION, (server, player, handler, buf, sender) -> {
            class_2338 clicked = buf.method_10811();
            boolean remove = buf.readBoolean();
            server.execute(() -> {
                boolean changed;
                UUID castleId = ACTIVE_CASTLE_AREA_EDIT.get(player.method_5667());
                if (castleId == null) {
                    return;
                }
                GarrisonManager manager = GarrisonManager.get(player.method_51469());
                GarrisonData garrison = manager.get(castleId);
                if (garrison == null || !WatchpostNetworking.canAdministerGarrison(player, garrison)) {
                    ACTIVE_CASTLE_AREA_EDIT.remove(player.method_5667());
                    return;
                }
                if (remove) {
                    changed = manager.removeBoundaryPointNear(castleId, clicked, 8);
                    player.method_7353((class_2561)class_2561.method_43471((String)(changed ? "message.medieval-machinery.castle_area.point_removed" : "message.medieval-machinery.castle_area.no_point_nearby")), true);
                } else {
                    if (!CastleAreaService.canAddBoundaryPoint(garrison, clicked)) {
                        player.method_7353((class_2561)class_2561.method_43471((String)(garrison.getBoundaryPoints().size() >= 24 ? "message.medieval-machinery.castle_area.too_many_points" : "message.medieval-machinery.castle_area.too_far")), true);
                        return;
                    }
                    changed = manager.addBoundaryPoint(castleId, clicked);
                    player.method_7353((class_2561)class_2561.method_43471((String)(changed ? "message.medieval-machinery.castle_area.point_added" : "message.medieval-machinery.castle_area.point_exists")), true);
                }
                if (changed) {
                    WatchpostNetworking.sendCastleAreaEditSnapshot(player, castleId);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)CASTLE_AREA_EDIT_CLEAR, (server, player, handler, buf, sender) -> server.execute(() -> {
            UUID castleId = ACTIVE_CASTLE_AREA_EDIT.get(player.method_5667());
            if (castleId == null) {
                return;
            }
            GarrisonManager manager = GarrisonManager.get(player.method_51469());
            GarrisonData garrison = manager.get(castleId);
            if (garrison == null || !WatchpostNetworking.canAdministerGarrison(player, garrison)) {
                ACTIVE_CASTLE_AREA_EDIT.remove(player.method_5667());
                return;
            }
            boolean changed = manager.clearBoundary(castleId);
            player.method_7353((class_2561)class_2561.method_43469((String)(changed ? "message.medieval-machinery.castle_area.cleared" : "message.medieval-machinery.castle_area.already_default"), (Object[])new Object[]{MedievalMachineryConfig.get().taxVillagerRadius}), true);
            WatchpostNetworking.sendCastleAreaEditSnapshot(player, castleId);
        }));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)ASSIGN_SUPPLY, (server, player, handler, buf, sender) -> {
            class_2338 watchpostPos = buf.method_10811();
            server.execute(() -> {
                WatchpostBlockEntity watchpost;
                class_2586 be = player.method_51469().method_8321(watchpostPos);
                if (be instanceof WatchpostBlockEntity && (watchpost = (WatchpostBlockEntity)be).canPlayerAdminister((class_1657)player) && watchpost.getCastleId() != null) {
                    ACTIVE_SUPPLY_EDIT.put(player.method_5667(), watchpost.getCastleId());
                    player.method_7346();
                    WatchpostNetworking.sendSupplyEditSnapshot(player, watchpost.getCastleId());
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)SUPPLY_EDIT_ACTION, (server, player, handler, buf, sender) -> {
            class_2338 clicked = buf.method_10811();
            boolean remove = buf.readBoolean();
            server.execute(() -> {
                UUID castleId = ACTIVE_SUPPLY_EDIT.get(player.method_5667());
                if (castleId == null) {
                    return;
                }
                GarrisonData garrison = GarrisonManager.get(player.method_51469()).get(castleId);
                if (garrison == null || !CastleAccessPolicy.canAdminister(player.method_51469(), player, garrison)) {
                    ACTIVE_SUPPLY_EDIT.remove(player.method_5667());
                    return;
                }
                if (!CastleAreaService.contains(garrison, clicked, MedievalMachineryConfig.get().taxVillagerRadius)) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.supply.outside_castle_area"), true);
                    return;
                }
                List<class_2338> physicalChest = WatchpostNetworking.linkedChestPositions(player.method_51469(), clicked);
                if (physicalChest.isEmpty()) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.supply.not_chest"), true);
                    return;
                }
                boolean changed = false;
                if (remove) {
                    for (class_2338 pos : physicalChest) {
                        changed |= GarrisonManager.get(player.method_51469()).removeSupplyChest(castleId, pos);
                    }
                    player.method_7353((class_2561)class_2561.method_43471((String)(changed ? "message.medieval-machinery.supply.removed" : "message.medieval-machinery.supply.not_assigned")), true);
                } else {
                    for (class_2338 pos : physicalChest) {
                        changed |= GarrisonManager.get(player.method_51469()).addSupplyChest(castleId, pos);
                    }
                    player.method_7353((class_2561)class_2561.method_43471((String)(changed ? "message.medieval-machinery.supply.assigned" : "message.medieval-machinery.supply.already_assigned")), true);
                }
                WatchpostNetworking.sendSupplyEditSnapshot(player, castleId);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)ASSIGN_TAX, (server, player, handler, buf, sender) -> {
            class_2338 watchpostPos = buf.method_10811();
            server.execute(() -> {
                WatchpostBlockEntity watchpost;
                class_2586 be = player.method_51469().method_8321(watchpostPos);
                if (be instanceof WatchpostBlockEntity && (watchpost = (WatchpostBlockEntity)be).canPlayerAdminister((class_1657)player) && watchpost.getCastleId() != null) {
                    ACTIVE_TAX_EDIT.put(player.method_5667(), watchpost.getCastleId());
                    player.method_7346();
                    WatchpostNetworking.sendTaxEditSnapshot(player, watchpost.getCastleId());
                    WatchpostNetworking.sendTaxChestSync(player);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)TAX_EDIT_ACTION, (server, player, handler, buf, sender) -> {
            class_2338 clicked = buf.method_10811();
            boolean remove = buf.readBoolean();
            server.execute(() -> {
                UUID castleId = ACTIVE_TAX_EDIT.get(player.method_5667());
                if (castleId == null) {
                    return;
                }
                GarrisonData garrison = GarrisonManager.get(player.method_51469()).get(castleId);
                if (garrison == null || !WatchpostNetworking.canAdministerGarrison(player, garrison)) {
                    ACTIVE_TAX_EDIT.remove(player.method_5667());
                    return;
                }
                if (!CastleAreaService.contains(garrison, clicked, MedievalMachineryConfig.get().taxVillagerRadius)) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.tax.outside_castle_area"), true);
                    return;
                }
                List<class_2338> physicalChest = WatchpostNetworking.linkedChestPositions(player.method_51469(), clicked);
                if (physicalChest.isEmpty()) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.tax.not_chest"), true);
                    return;
                }
                boolean changed = false;
                if (remove) {
                    for (class_2338 pos : physicalChest) {
                        changed |= GarrisonManager.get(player.method_51469()).removeTaxChest(castleId, pos);
                    }
                    player.method_7353((class_2561)class_2561.method_43471((String)(changed ? "message.medieval-machinery.tax.removed" : "message.medieval-machinery.tax.not_assigned")), true);
                } else {
                    GarrisonManager manager = GarrisonManager.get(player.method_51469());
                    for (class_2338 pos : physicalChest) {
                        manager.removeTaxChestEverywhere(pos, player.method_51469().method_27983().method_29177().toString());
                    }
                    manager.clearTaxChests(castleId);
                    for (class_2338 pos : physicalChest) {
                        changed |= manager.addTaxChest(castleId, pos);
                    }
                    player.method_7353((class_2561)class_2561.method_43471((String)(changed ? "message.medieval-machinery.tax.assigned" : "message.medieval-machinery.tax.already_assigned")), true);
                }
                WatchpostNetworking.sendTaxEditSnapshot(player, castleId);
                for (class_3222 online : player.method_5682().method_3760().method_14571()) {
                    WatchpostNetworking.sendTaxChestSync(online);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)BREAK_ACTION, (server, player, handler, buf, sender) -> {
            class_2338 requestedPos = buf.method_10811();
            int action = buf.method_10816();
            server.execute(() -> WatchpostNetworking.handleWatchpostBreakAction(player, requestedPos, action));
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)TAX_EDIT_STOP, (server, player, handler, buf, sender) -> server.execute(() -> ACTIVE_TAX_EDIT.remove(player.method_5667())));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)RALLY_EDIT_STOP, (server, player, handler, buf, sender) -> server.execute(() -> ACTIVE_RALLY_EDIT.remove(player.method_5667())));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)SUPPLY_EDIT_STOP, (server, player, handler, buf, sender) -> server.execute(() -> ACTIVE_SUPPLY_EDIT.remove(player.method_5667())));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)CASTLE_AREA_EDIT_STOP, (server, player, handler, buf, sender) -> server.execute(() -> ACTIVE_CASTLE_AREA_EDIT.remove(player.method_5667())));
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            class_3218 serverWorld;
            if (world instanceof class_3218) {
                serverWorld = (class_3218)world;
                if (player instanceof class_3222) {
                    class_3222 serverPlayer = (class_3222)player;
                    if (state.method_26204() instanceof WatchpostBlock) {
                        WatchpostBlockEntity watchpost;
                        class_2338 mainPos = WatchpostBlock.getMainPos(state, pos);
                        class_2680 mainState = serverWorld.method_8320(mainPos);
                        class_2586 mainEntity = serverWorld.method_8321(mainPos);
                        if (mainState.method_26204() instanceof WatchpostBlock && mainEntity instanceof WatchpostBlockEntity && (watchpost = (WatchpostBlockEntity)mainEntity).getCastleId() != null && watchpost.canPlayerAdminister((class_1657)serverPlayer)) {
                            if (watchpost.isCaptureActive() || watchpost.hasStagedCaptureSigil()) {
                                serverPlayer.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.watchpost.break_capture_blocked"), true);
                                return false;
                            }
                            class_1799 relocation = watchpost.createRelocationStack();
                            class_1799 sigil = watchpost.peekActiveSigilForDissolve();
                            PendingWatchpostBreak pending = new PendingWatchpostBreak(serverPlayer.method_5667(), (class_5321<class_1937>)serverWorld.method_27983(), mainPos.method_10062(), watchpost.getCastleId(), watchpost.getCastleName(), relocation, sigil, mainState);
                            PENDING_WATCHPOST_BREAKS.put(serverPlayer.method_5667(), pending);
                            WatchpostNetworking.removeWatchpostBlocks(serverWorld, mainPos, mainState);
                            WatchpostNetworking.sendWatchpostBreakPrompt(serverPlayer, mainPos, watchpost.getCastleName());
                            return false;
                        }
                    }
                }
            }
            if (world instanceof class_3218) {
                serverWorld = (class_3218)world;
                List<class_2338> physical = WatchpostNetworking.linkedChestPositions(serverWorld, pos);
                if (physical.isEmpty()) {
                    GarrisonManager.get(serverWorld).removeSupplyChestEverywhere(pos);
                    GarrisonManager.get(serverWorld).removeTaxChestEverywhere(pos, serverWorld.method_27983().method_29177().toString());
                } else {
                    for (class_2338 chestPos : physical) {
                        GarrisonManager.get(serverWorld).removeSupplyChestEverywhere(chestPos);
                        GarrisonManager.get(serverWorld).removeTaxChestEverywhere(chestPos, serverWorld.method_27983().method_29177().toString());
                    }
                }
                for (class_3222 online : serverWorld.method_8503().method_3760().method_14571()) {
                    WatchpostNetworking.sendTaxChestSync(online);
                }
            }
            return true;
        });
    }

    private static void sendWatchpostBreakPrompt(class_3222 player, class_2338 mainPos, String castleName) {
        class_2540 out = PacketByteBufs.create();
        out.method_10807(mainPos);
        out.method_10788(castleName == null ? "" : castleName, 48);
        ServerPlayNetworking.send((class_3222)player, (class_2960)BREAK_PROMPT, (class_2540)out);
    }

    private static void handleWatchpostBreakAction(class_3222 player, class_2338 requestedPos, int action) {
        if (player == null || requestedPos == null) {
            return;
        }
        PendingWatchpostBreak pending = PENDING_WATCHPOST_BREAKS.get(player.method_5667());
        if (pending == null || !pending.dropPos().equals((Object)requestedPos) || !pending.worldKey().equals((Object)player.method_51469().method_27983())) {
            return;
        }
        if (action != 0 && action != 1) {
            return;
        }
        class_3218 world = player.method_51469();
        PENDING_WATCHPOST_BREAKS.remove(player.method_5667());
        if (action == 0) {
            GarrisonManager.get(world).setCenter(pending.castleId(), null, null);
            WatchpostNetworking.dropAt(world, pending.dropPos(), pending.relocationStack());
            player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.watchpost.moving", (Object[])new Object[]{pending.castleName()}), false);
            return;
        }
        if (action == 1) {
            WatchpostBlockEntity.removeCastleAssignment(world, pending.castleId(), pending.dropPos());
            WatchpostNetworking.dropAt(world, pending.dropPos(), new class_1799((class_1935)pending.mainState().method_26204().method_8389()));
            WatchpostNetworking.dropAt(world, pending.dropPos(), pending.activeSigil());
            player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.watchpost.dissolved", (Object[])new Object[]{pending.castleName()}), false);
        }
    }

    private static void removeWatchpostBlocks(class_3218 world, class_2338 mainPos, class_2680 mainState) {
        class_2338 extensionPos = mainPos.method_10093(WatchpostBlock.getExtensionDirection((class_2350)mainState.method_11654((class_2769)WatchpostBlock.FACING)));
        class_2680 extensionState = world.method_8320(extensionPos);
        if (extensionState.method_26204() instanceof WatchpostBlock) {
            world.method_8650(extensionPos, false);
        }
        world.method_8650(mainPos, false);
    }

    private static void dropAt(class_3218 world, class_2338 pos, class_1799 stack) {
        if (world == null || pos == null || stack == null || stack.method_7960()) {
            return;
        }
        class_1799 dropped = stack.method_7972();
        world.method_8649((class_1297)new class_1542((class_1937)world, (double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.35, (double)pos.method_10260() + 0.5, dropped));
    }

    private static void sendRallyEditSnapshot(class_3222 player, UUID castleId) {
        GarrisonData garrison = GarrisonManager.get(player.method_51469()).get(castleId);
        if (garrison == null) {
            return;
        }
        class_2540 out = PacketByteBufs.create();
        out.method_10797(castleId);
        out.writeBoolean(garrison.getRallyPoint() != null);
        if (garrison.getRallyPoint() != null) {
            out.method_10807(garrison.getRallyPoint());
        }
        ServerPlayNetworking.send((class_3222)player, (class_2960)RALLY_EDIT_SNAPSHOT, (class_2540)out);
    }

    private static void refreshIdleRallyAnchors(class_3222 player, GarrisonData garrison, class_2338 oldRally) {
        class_2338 scanCenter;
        if (player == null || garrison == null) {
            return;
        }
        class_3218 world = player.method_51469();
        class_2338 marker = garrison.getRallyPoint();
        class_2338 class_23382 = marker != null ? marker : (scanCenter = oldRally != null ? oldRally : garrison.getCenter());
        if (scanCenter == null) {
            return;
        }
        double radius = 18.0;
        for (MedievalSoldierEntity soldier : world.method_8390(MedievalSoldierEntity.class, new class_238(scanCenter).method_1014(radius), npc -> garrison.getCastleId().equals(npc.getNpcIdentity().getCastleId()) && npc.getOrderType() == NpcOrderType.IDLE)) {
            if (marker == null) {
                soldier.setIdleFreeAnchor(soldier.method_19538());
                continue;
            }
            soldier.setIdleRallyAnchor(GarrisonRallyPolicy.nearbyAnchor(world, marker, soldier.method_5667()));
        }
    }

    private static void sendCastleAreaEditSnapshot(class_3222 player, UUID castleId) {
        GarrisonData garrison = GarrisonManager.get(player.method_51469()).get(castleId);
        if (garrison == null) {
            return;
        }
        class_2540 out = PacketByteBufs.create();
        out.method_10797(castleId);
        out.method_10804(MedievalMachineryConfig.get().taxVillagerRadius);
        out.method_10807(garrison.getCenter() == null ? player.method_24515() : garrison.getCenter());
        List<class_2338> points = garrison.getBoundaryPoints();
        out.method_10804(points.size());
        for (class_2338 point : points) {
            out.method_10807(point);
        }
        ServerPlayNetworking.send((class_3222)player, (class_2960)CASTLE_AREA_EDIT_SNAPSHOT, (class_2540)out);
    }

    private static void sendSupplyEditSnapshot(class_3222 player, UUID castleId) {
        GarrisonData garrison = GarrisonManager.get(player.method_51469()).get(castleId);
        if (garrison == null) {
            return;
        }
        class_2540 out = PacketByteBufs.create();
        LinkedHashSet<class_2338> marked = new LinkedHashSet<class_2338>();
        for (class_2338 pos : garrison.getSupplyChestPositions()) {
            List<class_2338> physical = WatchpostNetworking.linkedChestPositions(player.method_51469(), pos);
            if (physical.isEmpty()) {
                marked.add(pos);
                continue;
            }
            marked.addAll(physical);
        }
        out.method_10797(castleId);
        out.method_10804(marked.size());
        for (class_2338 pos : marked) {
            out.method_10807(pos);
        }
        ServerPlayNetworking.send((class_3222)player, (class_2960)SUPPLY_EDIT_SNAPSHOT, (class_2540)out);
    }

    private static boolean canAdministerGarrison(class_3222 player, GarrisonData garrison) {
        return CastleAccessPolicy.canAdminister(player.method_51469(), player, garrison);
    }

    private static void sendTaxEditSnapshot(class_3222 player, UUID castleId) {
        GarrisonData garrison = GarrisonManager.get(player.method_51469()).get(castleId);
        if (garrison == null) {
            return;
        }
        class_2540 out = PacketByteBufs.create();
        LinkedHashSet<class_2338> marked = new LinkedHashSet<class_2338>();
        for (class_2338 pos : garrison.getTaxChestPositions()) {
            List<class_2338> physical = WatchpostNetworking.linkedChestPositions(player.method_51469(), pos);
            if (physical.isEmpty()) {
                marked.add(pos);
                continue;
            }
            marked.addAll(physical);
        }
        out.method_10797(castleId);
        out.method_10804(marked.size());
        for (class_2338 pos : marked) {
            out.method_10807(pos);
        }
        ServerPlayNetworking.send((class_3222)player, (class_2960)TAX_EDIT_SNAPSHOT, (class_2540)out);
    }

    public static void sendTaxChestSync(class_3222 player) {
        GarrisonManager manager = GarrisonManager.get(player.method_51469());
        LinkedHashSet<class_2338> marked = new LinkedHashSet<class_2338>();
        String dimensionId = player.method_51469().method_27983().method_29177().toString();
        for (GarrisonData garrison : manager.getAll()) {
            if (garrison.getCenterDimension() != null && !dimensionId.equals(garrison.getCenterDimension())) continue;
            marked.addAll(garrison.getTaxChestPositions());
        }
        class_2540 out = PacketByteBufs.create();
        out.method_10804(marked.size());
        for (class_2338 pos : marked) {
            out.method_10807(pos);
        }
        ServerPlayNetworking.send((class_3222)player, (class_2960)TAX_CHEST_SYNC, (class_2540)out);
    }

    public static void clearEditorState(UUID playerId) {
        if (playerId == null) {
            return;
        }
        ACTIVE_SUPPLY_EDIT.remove(playerId);
        ACTIVE_TAX_EDIT.remove(playerId);
        ACTIVE_RALLY_EDIT.remove(playerId);
        ACTIVE_CASTLE_AREA_EDIT.remove(playerId);
        PENDING_WATCHPOST_BREAKS.remove(playerId);
    }

    public static void clearAllTransientState() {
        ACTIVE_SUPPLY_EDIT.clear();
        ACTIVE_TAX_EDIT.clear();
        ACTIVE_RALLY_EDIT.clear();
        ACTIVE_CASTLE_AREA_EDIT.clear();
        PENDING_WATCHPOST_BREAKS.clear();
    }

    private static List<class_2338> linkedChestPositions(class_3218 world, class_2338 clicked) {
        if (world == null || clicked == null || !(world.method_8321(clicked) instanceof class_2595)) {
            return List.of();
        }
        class_2680 state = world.method_8320(clicked);
        if (!(state.method_26204() instanceof class_2281) || !state.method_28498((class_2769)class_2741.field_12506) || state.method_11654((class_2769)class_2741.field_12506) == class_2745.field_12569) {
            return List.of(clicked.method_10062());
        }
        ArrayList<class_2338> result = new ArrayList<class_2338>(2);
        result.add(clicked.method_10062());
        for (class_2350 direction : class_2350.class_2353.field_11062) {
            class_2338 other = clicked.method_10093(direction);
            class_2680 otherState = world.method_8320(other);
            if (otherState.method_26204() != state.method_26204() || !otherState.method_28498((class_2769)class_2741.field_12506) || otherState.method_11654((class_2769)class_2741.field_12506) == class_2745.field_12569 || !(world.method_8321(other) instanceof class_2595)) continue;
            result.add(other.method_10062());
            break;
        }
        return List.copyOf(result);
    }

    private record PendingWatchpostBreak(UUID playerId, class_5321<class_1937> worldKey, class_2338 dropPos, UUID castleId, String castleName, class_1799 relocationStack, class_1799 activeSigil, class_2680 mainState) {
    }
}

