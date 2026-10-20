/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.event.player.UseBlockCallback
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_1799
 *  net.minecraft.class_1937
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2595
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_2960
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3532
 *  net.minecraft.server.MinecraftServer
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.block.machine.cannon.StationaryCannonBlock;
import com.medievalmachinery.mod.blockentity.machine.cannon.StationaryCannonBlockEntity;
import com.medievalmachinery.mod.entity.cannon.StationaryCannonEntity;
import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineAssignmentAudit;
import com.medievalmachinery.mod.npc.machine.NpcMachineAssignmentService;
import com.medievalmachinery.mod.npc.machine.NpcMachineRolePolicy;
import com.medievalmachinery.mod.npc.machine.NpcMachineSupplyPolicy;
import com.medievalmachinery.mod.npc.organization.CaptainGroupTransferService;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.NpcSupplyCommandService;
import com.medievalmachinery.mod.npc.organization.SoldierControlPolicy;
import com.medievalmachinery.mod.npc.organization.SoldierTransferService;
import com.medievalmachinery.mod.npc.patrol.PatrolRoute;
import com.medievalmachinery.mod.npc.patrol.PatrolWaypoint;
import com.medievalmachinery.mod.registry.ModEntities;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2595;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3532;
import net.minecraft.server.MinecraftServer;

public final class SoldierNetworking {
    public static final class_2960 RENAME = MedievalMachinery.id("soldier/rename");
    public static final class_2960 OPEN_INVENTORY = MedievalMachinery.id("soldier/open_inventory");
    public static final class_2960 TRANSFER_REQUEST = MedievalMachinery.id("soldier/transfer_request");
    public static final class_2960 OPEN_TRANSFER_CONFIRM = MedievalMachinery.id("soldier/open_transfer_confirm");
    public static final class_2960 TRANSFER_RESPONSE = MedievalMachinery.id("soldier/transfer_response");
    public static final class_2960 CASTLE_TRANSFER_REQUEST = MedievalMachinery.id("soldier/castle_transfer_request");
    public static final class_2960 PREFERRED_SUPPLY_EDIT_START = MedievalMachinery.id("soldier/preferred_supply_start");
    public static final class_2960 PREFERRED_SUPPLY_CLEAR = MedievalMachinery.id("soldier/preferred_supply_clear");
    public static final class_2960 GUI_MOUNT = MedievalMachinery.id("soldier/gui_mount");
    public static final class_2960 GUI_DISMOUNT = MedievalMachinery.id("soldier/gui_dismount");
    public static final class_2960 MACHINE_ASSIGN_START = MedievalMachinery.id("soldier/machine_assign_start");
    public static final class_2960 MACHINE_ASSIGN_SELECT = MedievalMachinery.id("soldier/machine_assign_select");
    public static final class_2960 MACHINE_ASSIGN_CLEAR = MedievalMachinery.id("soldier/machine_assign_clear");
    public static final class_2960 MACHINE_ASSIGN_STOP = MedievalMachinery.id("soldier/machine_assign_stop");
    public static final class_2960 MACHINE_ASSIGN_RESULT = MedievalMachinery.id("soldier/machine_assign_result");
    public static final class_2960 GUI_SUPPLY = MedievalMachinery.id("soldier/gui_supply");
    public static final class_2960 PATROL_MENU_REQUEST = MedievalMachinery.id("soldier/patrol_menu_request");
    public static final class_2960 OPEN_PATROL_MENU = MedievalMachinery.id("soldier/open_patrol_menu");
    public static final class_2960 PATROL_EDIT_START = MedievalMachinery.id("soldier/patrol_edit_start");
    public static final class_2960 PATROL_EDIT_STOP = MedievalMachinery.id("soldier/patrol_edit_stop");
    public static final class_2960 PATROL_ADD_POINT = MedievalMachinery.id("soldier/patrol_add_point");
    public static final class_2960 PATROL_REMOVE_LAST = MedievalMachinery.id("soldier/patrol_remove_last");
    public static final class_2960 PATROL_CLEAR_ROUTE = MedievalMachinery.id("soldier/patrol_clear_route");
    public static final class_2960 PATROL_ROUTE_SNAPSHOT = MedievalMachinery.id("soldier/patrol_route_snapshot");
    public static final class_2960 OBSERVATION_EDIT_START = MedievalMachinery.id("soldier/observation_edit_start");
    public static final class_2960 OBSERVATION_EDIT_STOP = MedievalMachinery.id("soldier/observation_edit_stop");
    public static final class_2960 OBSERVATION_EDIT_ACTION = MedievalMachinery.id("soldier/observation_edit_action");
    public static final class_2960 QUARTERMASTER_COMPOSTER_EDIT_START = MedievalMachinery.id("soldier/quartermaster_composter_start");
    public static final class_2960 QUARTERMASTER_FIELD_EDIT_START = MedievalMachinery.id("soldier/quartermaster_field_start");
    public static final class_2960 QUARTERMASTER_FARM_EDIT_STOP = MedievalMachinery.id("soldier/quartermaster_farm_stop");
    public static final class_2960 QUARTERMASTER_FARM_EDIT_ACTION = MedievalMachinery.id("soldier/quartermaster_farm_action");
    public static final class_2960 QUARTERMASTER_FARM_EDIT_COMPLETE = MedievalMachinery.id("soldier/quartermaster_farm_complete");
    public static final class_2960 QUARTERMASTER_FIELDS_CLEAR = MedievalMachinery.id("soldier/quartermaster_fields_clear");
    public static final class_2960 QUARTERMASTER_FIELD_REMOVE = MedievalMachinery.id("soldier/quartermaster_field_remove");
    public static final class_2960 QUARTERMASTER_COMPOSTER_CLEAR = MedievalMachinery.id("soldier/quartermaster_composter_clear");
    private static final Map<UUID, UUID> PATROL_EDIT_SESSIONS = new ConcurrentHashMap<UUID, UUID>();
    private static final Map<UUID, UUID> OBSERVATION_EDIT_SESSIONS = new ConcurrentHashMap<UUID, UUID>();
    private static final Map<UUID, FarmEditSession> QUARTERMASTER_FARM_EDIT = new ConcurrentHashMap<UUID, FarmEditSession>();
    private static final Map<UUID, PendingSoldierTransfer> PENDING_TRANSFERS = new ConcurrentHashMap<UUID, PendingSoldierTransfer>();
    private static final Map<UUID, UUID> PREFERRED_SUPPLY_EDIT = new ConcurrentHashMap<UUID, UUID>();
    private static final Map<UUID, UUID> MACHINE_ASSIGN_EDIT = new ConcurrentHashMap<UUID, UUID>();
    private static final long TRANSFER_REQUEST_LIFETIME_MS = 60000L;

    private SoldierNetworking() {
    }

    public static void registerServer() {
        UseBlockCallback.EVENT.register((playerEntity, world, hand, hit) -> {
            MedievalSoldierEntity soldier;
            class_2586 patt0$temp;
            class_3218 serverWorld;
            block12: {
                block11: {
                    if (!(world instanceof class_3218)) break block11;
                    serverWorld = (class_3218)world;
                    if (playerEntity instanceof class_3222) break block12;
                }
                return class_1269.field_5811;
            }
            class_3222 player = (class_3222)playerEntity;
            class_2338 pos = hit.method_17777();
            UUID machineSoldierId = MACHINE_ASSIGN_EDIT.get(player.method_5667());
            if (machineSoldierId != null && (patt0$temp = serverWorld.method_8321(pos)) instanceof StationaryCannonBlockEntity) {
                NpcMachineAssignmentAudit.Result audit;
                StationaryCannonBlockEntity legacy = (StationaryCannonBlockEntity)patt0$temp;
                soldier = SoldierNetworking.findLoadedSoldier(player.method_5682(), machineSoldierId);
                if (soldier == null || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier)) {
                    SoldierNetworking.sendMachineAssignmentResult(player, false);
                    return class_1269.field_5812;
                }
                StationaryCannonEntity migrated = SoldierNetworking.migrateLegacyFortressCannon(serverWorld, pos, legacy);
                if (migrated == null) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.machine_assignment.invalid"), true);
                    SoldierNetworking.sendMachineAssignmentResult(player, false);
                    return class_1269.field_5812;
                }
                MachineCrewRole role = SoldierNetworking.preferredCrewRole(soldier, migrated);
                NpcMachineAssignmentAudit.Result result = audit = role == null ? NpcMachineAssignmentAudit.Result.UNSUPPORTED_ROLE : NpcMachineAssignmentService.validate(soldier, migrated, role);
                if (audit != NpcMachineAssignmentAudit.Result.OK) {
                    SoldierNetworking.sendMachineAssignmentFailure(player, audit, true);
                    SoldierNetworking.sendMachineAssignmentResult(player, false);
                    return class_1269.field_5812;
                }
                NpcMachineAssignmentService.clear(soldier);
                boolean assigned = NpcMachineAssignmentService.assign(soldier, migrated, role);
                if (assigned) {
                    MACHINE_ASSIGN_EDIT.remove(player.method_5667());
                }
                player.method_7353((class_2561)class_2561.method_43471((String)(assigned ? "message.medieval-machinery.machine_assignment.assigned" : "message.medieval-machinery.machine_assignment.invalid")), true);
                SoldierNetworking.sendMachineAssignmentResult(player, assigned);
                return class_1269.field_5812;
            }
            UUID soldierId = PREFERRED_SUPPLY_EDIT.get(player.method_5667());
            if (soldierId == null) {
                return class_1269.field_5811;
            }
            if (!(serverWorld.method_8321(pos) instanceof class_2595)) {
                return class_1269.field_5811;
            }
            soldier = SoldierNetworking.findLoadedSoldier(player.method_5682(), soldierId);
            PREFERRED_SUPPLY_EDIT.remove(player.method_5667());
            if (soldier == null || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier)) {
                return class_1269.field_5812;
            }
            boolean ok = NpcMachineSupplyPolicy.setPreferredSupplyChest(soldier, pos);
            player.method_7353((class_2561)class_2561.method_43471((String)(ok ? "message.medieval-machinery.machine_supply.preferred_set" : "message.medieval-machinery.machine_supply.not_castle_supply")), true);
            return class_1269.field_5812;
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)GUI_MOUNT, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 1024.0);
                if (soldier == null) {
                    return;
                }
                boolean ok = soldier.commandMountNearby();
                player.method_7353((class_2561)class_2561.method_43471((String)(ok ? "message.medieval-machinery.command.mount.started" : "message.medieval-machinery.command.mount.none")), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)GUI_DISMOUNT, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 1024.0);
                if (soldier == null) {
                    return;
                }
                boolean ok = soldier.commandDismount();
                player.method_7353((class_2561)class_2561.method_43471((String)(ok ? "message.medieval-machinery.command.dismount.done" : "message.medieval-machinery.command.dismount.none")), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)MACHINE_ASSIGN_START, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null) {
                    return;
                }
                MACHINE_ASSIGN_EDIT.put(player.method_5667(), soldier.method_5667());
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.machine_assignment.select"), false);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)MACHINE_ASSIGN_SELECT, (server, player, handler, buf, sender) -> {
            int soldierEntityId = buf.method_10816();
            int machineEntityId = buf.method_10816();
            server.execute(() -> {
                NpcMachineAssignmentAudit.Result audit;
                MedievalMachineryOperable machine;
                MedievalSoldierEntity soldier;
                block6: {
                    block5: {
                        UUID editingSoldier = MACHINE_ASSIGN_EDIT.get(player.method_5667());
                        soldier = SoldierNetworking.findLoadedSoldier(player.method_5682(), editingSoldier);
                        class_1297 rawMachine = player.method_51469().method_8469(machineEntityId);
                        if (editingSoldier == null || soldier == null || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) || !(rawMachine instanceof MedievalMachineryOperable)) break block5;
                        machine = (MedievalMachineryOperable)rawMachine;
                        if (!(player.method_5858(rawMachine) > 64.0)) break block6;
                    }
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.machine_assignment.invalid"), true);
                    return;
                }
                MachineCrewRole role = SoldierNetworking.preferredCrewRole(soldier, machine);
                NpcMachineAssignmentAudit.Result result = audit = role == null ? NpcMachineAssignmentAudit.Result.UNSUPPORTED_ROLE : NpcMachineAssignmentService.validate(soldier, machine, role);
                if (audit != NpcMachineAssignmentAudit.Result.OK) {
                    SoldierNetworking.sendMachineAssignmentFailure(player, audit, true);
                    return;
                }
                NpcMachineAssignmentService.clear(soldier);
                boolean assigned = NpcMachineAssignmentService.assign(soldier, machine, role);
                if (assigned) {
                    MACHINE_ASSIGN_EDIT.remove(player.method_5667());
                }
                player.method_7353((class_2561)class_2561.method_43471((String)(assigned ? "message.medieval-machinery.machine_assignment.assigned" : "message.medieval-machinery.machine_assignment.invalid")), true);
                SoldierNetworking.sendMachineAssignmentResult(player, assigned);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)MACHINE_ASSIGN_STOP, (server, player, handler, buf, sender) -> server.execute(() -> {
            if (MACHINE_ASSIGN_EDIT.remove(player.method_5667()) != null) {
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.machine_assignment.cancelled"), true);
            }
        }));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)MACHINE_ASSIGN_CLEAR, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null) {
                    return;
                }
                NpcMachineAssignmentService.clear(soldier);
                MACHINE_ASSIGN_EDIT.remove(player.method_5667());
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.machine_assignment.cleared"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)PREFERRED_SUPPLY_EDIT_START, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null || !soldier.medievalMachinery$hasMachineAssignment()) {
                    return;
                }
                PREFERRED_SUPPLY_EDIT.put(player.method_5667(), soldier.method_5667());
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.machine_supply.select_preferred"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)PREFERRED_SUPPLY_CLEAR, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null) {
                    return;
                }
                NpcMachineSupplyPolicy.clearPreferredSupplyChest(soldier);
                PREFERRED_SUPPLY_EDIT.remove(player.method_5667());
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.machine_supply.preferred_cleared"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)GUI_SUPPLY, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null) {
                    return;
                }
                soldier.releaseInventoryGuiMovementLock();
                if (!NpcSupplyCommandService.requestSupply(soldier)) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.supply.no_chest"), false);
                    return;
                }
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.supply.started"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)RENAME, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            String name = buf.method_10800(32);
            server.execute(() -> {
                MedievalSoldierEntity soldier;
                class_1297 entity = player.method_51469().method_8469(entityId);
                if (entity instanceof MedievalSoldierEntity && (soldier = (MedievalSoldierEntity)entity).method_5805() && player.method_5858((class_1297)soldier) <= 64.0) {
                    soldier.renameByOwner(player, name);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)OPEN_INVENTORY, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier;
                class_1297 entity = player.method_51469().method_8469(entityId);
                if (entity instanceof MedievalSoldierEntity && (soldier = (MedievalSoldierEntity)entity).method_5805() && SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) && player.method_5858((class_1297)soldier) <= 64.0) {
                    soldier.openPersonalInventory(player);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)TRANSFER_REQUEST, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            UUID targetPlayerId = buf.method_10790();
            boolean withTroop = buf.readBoolean();
            server.execute(() -> {
                MedievalSoldierEntity soldier;
                class_1297 entity = player.method_51469().method_8469(entityId);
                if (!(entity instanceof MedievalSoldierEntity) || !(soldier = (MedievalSoldierEntity)entity).method_5805() || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) || player.method_5858((class_1297)soldier) > 64.0) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.soldier_transfer.invalid"), false);
                    return;
                }
                class_3222 target = server.method_3760().method_14602(targetPlayerId);
                if (target == null || target.method_5667().equals(player.method_5667())) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.soldier_transfer.player_missing"), false);
                    return;
                }
                CaptainGroupTransferService.Preview troopPreview = null;
                if (withTroop && (troopPreview = CaptainGroupTransferService.preview(player.method_51469(), soldier, player)) == null) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.soldier_transfer.troop_invalid"), false);
                    return;
                }
                PENDING_TRANSFERS.values().removeIf(pending -> pending.soldierId().equals(soldier.method_5667()) || pending.targetPlayerId().equals(target.method_5667()));
                UUID requestId = UUID.randomUUID();
                PendingSoldierTransfer pending2 = new PendingSoldierTransfer(requestId, soldier.method_5667(), player.method_5667(), target.method_5667(), withTroop, System.currentTimeMillis());
                PENDING_TRANSFERS.put(requestId, pending2);
                class_2540 out = PacketByteBufs.create();
                out.method_10797(requestId);
                out.method_10788(player.method_7334().getName(), 32);
                out.method_10788(soldier.method_5476().getString(), 64);
                out.writeBoolean(withTroop);
                if (withTroop && troopPreview != null) {
                    out.method_10788(troopPreview.groupName(), 64);
                    out.method_10804(troopPreview.memberCount());
                }
                ServerPlayNetworking.send((class_3222)target, (class_2960)OPEN_TRANSFER_CONFIRM, (class_2540)out);
                player.method_7353((class_2561)class_2561.method_43469((String)(withTroop ? "message.medieval-machinery.soldier_transfer.troop_sent" : "message.medieval-machinery.soldier_transfer.sent"), (Object[])new Object[]{soldier.method_5476(), target.method_7334().getName()}), false);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)TRANSFER_RESPONSE, (server, player, handler, buf, sender) -> {
            UUID requestId = buf.method_10790();
            boolean accepted = buf.readBoolean();
            server.execute(() -> {
                boolean transferred;
                PendingSoldierTransfer pending = PENDING_TRANSFERS.remove(requestId);
                if (pending == null || !pending.targetPlayerId().equals(player.method_5667())) {
                    return;
                }
                class_3222 source = server.method_3760().method_14602(pending.sourcePlayerId());
                if (source == null) {
                    return;
                }
                if (System.currentTimeMillis() - pending.createdAtMs() > 60000L) {
                    source.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.soldier_transfer.expired"), false);
                    return;
                }
                if (!accepted) {
                    source.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.soldier_transfer.declined", (Object[])new Object[]{player.method_7334().getName()}), false);
                    return;
                }
                MedievalSoldierEntity soldier = SoldierNetworking.findLoadedSoldier(server, pending.soldierId());
                if (soldier == null || !SoldierControlPolicy.canPersonallyControl((class_1657)source, soldier)) {
                    source.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.soldier_transfer.invalid"), false);
                    return;
                }
                class_3218 soldierWorld = (class_3218)soldier.method_37908();
                boolean bl = transferred = pending.withTroop() ? CaptainGroupTransferService.transfer(soldierWorld, soldier, source, player) : SoldierTransferService.transfer(soldierWorld, soldier, source, player);
                if (!transferred) {
                    source.method_7353((class_2561)class_2561.method_43471((String)(pending.withTroop() ? "message.medieval-machinery.soldier_transfer.troop_invalid" : "message.medieval-machinery.soldier_transfer.invalid")), false);
                    return;
                }
                source.method_7353((class_2561)class_2561.method_43469((String)(pending.withTroop() ? "message.medieval-machinery.soldier_transfer.troop_completed_sender" : "message.medieval-machinery.soldier_transfer.completed_sender"), (Object[])new Object[]{soldier.method_5476(), player.method_7334().getName()}), false);
                player.method_7353((class_2561)class_2561.method_43469((String)(pending.withTroop() ? "message.medieval-machinery.soldier_transfer.troop_completed_target" : "message.medieval-machinery.soldier_transfer.completed_target"), (Object[])new Object[]{soldier.method_5476(), source.method_7334().getName()}), false);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)CASTLE_TRANSFER_REQUEST, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            UUID targetCastleId = buf.method_10790();
            server.execute(() -> {
                boolean validKingdom;
                MedievalSoldierEntity soldier;
                class_1297 entity = player.method_51469().method_8469(entityId);
                if (!(entity instanceof MedievalSoldierEntity) || !(soldier = (MedievalSoldierEntity)entity).method_5805() || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) || player.method_5858((class_1297)soldier) > 64.0) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.castle_transfer.invalid"), false);
                    return;
                }
                GarrisonManager garrisons = GarrisonManager.get(player.method_51469());
                GarrisonData target = garrisons.get(targetCastleId);
                UUID sourceCastleId = soldier.getNpcIdentity().getCastleId();
                GarrisonData source = sourceCastleId == null ? null : garrisons.get(sourceCastleId);
                UUID soldierKingdom = soldier.getNpcIdentity().getKingdomId();
                boolean bl = target != null && (soldierKingdom != null ? soldierKingdom.equals(target.getKingdomId()) : target.getKingdomId() == null && target.getOwnerId().equals(player.method_5667())) ? true : (validKingdom = false);
                if (!validKingdom || sourceCastleId != null && sourceCastleId.equals(targetCastleId)) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.castle_transfer.invalid"), false);
                    return;
                }
                if (!soldier.medievalMachinery$startCastleTransfer(targetCastleId)) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.castle_transfer.invalid"), false);
                    return;
                }
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.castle_transfer.started"), false);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)PATROL_MENU_REQUEST, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null || soldier.getNpcRole() == NpcRole.CANNONEER) {
                    return;
                }
                SoldierNetworking.sendPatrolMenu(player, soldier);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)PATROL_EDIT_START, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null || soldier.getNpcRole() == NpcRole.CANNONEER) {
                    return;
                }
                PATROL_EDIT_SESSIONS.put(player.method_5667(), soldier.method_5667());
                SoldierNetworking.sendPatrolSnapshot(player, soldier, true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)PATROL_EDIT_STOP, (server, player, handler, buf, sender) -> server.execute(() -> PATROL_EDIT_SESSIONS.remove(player.method_5667())));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)PATROL_ADD_POINT, (server, player, handler, buf, sender) -> {
            class_2338 point = buf.method_10811().method_10062();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.editingSoldier(player);
                if (soldier == null) {
                    return;
                }
                String validation = SoldierNetworking.validateWaypoint(player, soldier, point);
                if (validation != null) {
                    player.method_7353((class_2561)class_2561.method_43471((String)validation), true);
                    SoldierNetworking.sendPatrolSnapshot(player, soldier, true);
                    return;
                }
                if (soldier.addPatrolWaypoint(point)) {
                    player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.patrol.point_added", (Object[])new Object[]{soldier.getPatrolWaypointCount(), 32}), true);
                }
                SoldierNetworking.sendPatrolSnapshot(player, soldier, true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)PATROL_REMOVE_LAST, (server, player, handler, buf, sender) -> server.execute(() -> {
            MedievalSoldierEntity soldier = SoldierNetworking.editingSoldier(player);
            if (soldier == null) {
                return;
            }
            soldier.removeLastPatrolWaypoint();
            SoldierNetworking.sendPatrolSnapshot(player, soldier, true);
        }));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)PATROL_CLEAR_ROUTE, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null) {
                    return;
                }
                soldier.clearPatrolRoute();
                if (soldier.getOrderType() == NpcOrderType.PATROL) {
                    soldier.setOrderType(NpcOrderType.IDLE);
                }
                UUID editing = PATROL_EDIT_SESSIONS.get(player.method_5667());
                if (soldier.method_5667().equals(editing)) {
                    SoldierNetworking.sendPatrolSnapshot(player, soldier, true);
                }
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.patrol.route_deleted"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)OBSERVATION_EDIT_START, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null || soldier.getNpcRole() != NpcRole.SCOUT) {
                    return;
                }
                OBSERVATION_EDIT_SESSIONS.put(player.method_5667(), soldier.method_5667());
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.scout.observation_select"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)OBSERVATION_EDIT_STOP, (server, player, handler, buf, sender) -> server.execute(() -> OBSERVATION_EDIT_SESSIONS.remove(player.method_5667())));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)OBSERVATION_EDIT_ACTION, (server, player, handler, buf, sender) -> {
            class_2338 clicked = buf.method_10811().method_10062();
            server.execute(() -> {
                UUID soldierId = OBSERVATION_EDIT_SESSIONS.get(player.method_5667());
                MedievalSoldierEntity soldier = SoldierNetworking.findLoadedSoldier(server, soldierId);
                if (soldier == null || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) || soldier.getNpcRole() != NpcRole.SCOUT) {
                    OBSERVATION_EDIT_SESSIONS.remove(player.method_5667());
                    return;
                }
                if (player.method_5649((double)clicked.method_10263() + 0.5, (double)clicked.method_10264() + 0.5, (double)clicked.method_10260() + 0.5) > 4096.0) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.scout.observation_too_far"), true);
                    return;
                }
                if (!soldier.setObservationPoint(clicked)) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.scout.observation_invalid"), true);
                    return;
                }
                OBSERVATION_EDIT_SESSIONS.remove(player.method_5667());
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.scout.observation_set"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)QUARTERMASTER_COMPOSTER_EDIT_START, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null || soldier.getNpcRole() != NpcRole.QUARTERMASTER) {
                    return;
                }
                QUARTERMASTER_FARM_EDIT.put(player.method_5667(), new FarmEditSession(soldier.method_5667(), true));
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.composter_select"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)QUARTERMASTER_FIELD_EDIT_START, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null || soldier.getNpcRole() != NpcRole.QUARTERMASTER) {
                    return;
                }
                if (soldier.getQuartermasterFieldCount() >= 3) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.fields_full"), true);
                    return;
                }
                QUARTERMASTER_FARM_EDIT.put(player.method_5667(), new FarmEditSession(soldier.method_5667(), false));
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.field_select"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)QUARTERMASTER_FARM_EDIT_STOP, (server, player, handler, buf, sender) -> server.execute(() -> QUARTERMASTER_FARM_EDIT.remove(player.method_5667())));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)QUARTERMASTER_FARM_EDIT_ACTION, (server, player, handler, buf, sender) -> {
            class_2338 clicked = buf.method_10811().method_10062();
            server.execute(() -> {
                MedievalSoldierEntity soldier;
                FarmEditSession session = QUARTERMASTER_FARM_EDIT.get(player.method_5667());
                MedievalSoldierEntity medievalSoldierEntity = soldier = session == null ? null : SoldierNetworking.findLoadedSoldier(server, session.soldierId());
                if (soldier == null || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) || soldier.getNpcRole() != NpcRole.QUARTERMASTER) {
                    QUARTERMASTER_FARM_EDIT.remove(player.method_5667());
                    return;
                }
                if (player.method_5649((double)clicked.method_10263() + 0.5, (double)clicked.method_10264() + 0.5, (double)clicked.method_10260() + 0.5) > 4096.0) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.too_far"), true);
                    return;
                }
                if (session.composter()) {
                    if (!player.method_37908().method_8320(clicked).method_27852(class_2246.field_17563)) {
                        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.composter_invalid"), true);
                        return;
                    }
                    soldier.setQuartermasterComposter(clicked);
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.composter_set"), true);
                } else {
                    class_2680 state = player.method_37908().method_8320(clicked);
                    if (!(state.method_27852(class_2246.field_10566) || state.method_27852(class_2246.field_10219) || state.method_27852(class_2246.field_10362))) {
                        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.field_invalid"), true);
                        return;
                    }
                    if (!soldier.addQuartermasterField(clicked)) {
                        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.fields_full"), true);
                        return;
                    }
                    player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.quartermaster.field_set", (Object[])new Object[]{soldier.getQuartermasterFieldCount(), 3}), true);
                }
                if (session.composter() || soldier.getQuartermasterFieldCount() >= 3) {
                    QUARTERMASTER_FARM_EDIT.remove(player.method_5667());
                    ServerPlayNetworking.send((class_3222)player, (class_2960)QUARTERMASTER_FARM_EDIT_COMPLETE, (class_2540)PacketByteBufs.create());
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)QUARTERMASTER_FIELDS_CLEAR, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null || soldier.getNpcRole() != NpcRole.QUARTERMASTER) {
                    return;
                }
                soldier.clearQuartermasterFields();
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.fields_cleared"), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)QUARTERMASTER_FIELD_REMOVE, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            int index = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null || soldier.getNpcRole() != NpcRole.QUARTERMASTER) {
                    return;
                }
                if (soldier.removeQuartermasterField(index)) {
                    player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.quartermaster.field_removed", (Object[])new Object[]{index + 1}), true);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)QUARTERMASTER_COMPOSTER_CLEAR, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity soldier = SoldierNetworking.ownedSoldier(player, entityId, 64.0);
                if (soldier == null || soldier.getNpcRole() != NpcRole.QUARTERMASTER) {
                    return;
                }
                soldier.clearQuartermasterComposter();
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.quartermaster.composter_cleared"), true);
            });
        });
    }

    private static MedievalSoldierEntity findLoadedSoldier(MinecraftServer server, UUID soldierId) {
        if (soldierId == null) {
            return null;
        }
        for (class_3218 world : server.method_3738()) {
            MedievalSoldierEntity soldier;
            class_1297 entity = world.method_14190(soldierId);
            if (!(entity instanceof MedievalSoldierEntity) || !(soldier = (MedievalSoldierEntity)entity).method_5805()) continue;
            return soldier;
        }
        return null;
    }

    private static void sendPatrolMenu(class_3222 player, MedievalSoldierEntity soldier) {
        if (!soldier.method_5805() || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) || player.method_5858((class_1297)soldier) > 64.0) {
            return;
        }
        class_2540 out = PacketByteBufs.create();
        out.method_10804(soldier.method_5628());
        out.method_10804(soldier.getOrderType().ordinal());
        out.method_10804(soldier.getPatrolWaypointCount());
        ServerPlayNetworking.send((class_3222)player, (class_2960)OPEN_PATROL_MENU, (class_2540)out);
    }

    private static void sendMachineAssignmentFailure(class_3222 player, NpcMachineAssignmentAudit.Result result, boolean overlay) {
        String key = switch (result) {
            case NpcMachineAssignmentAudit.Result.UNSUPPORTED_ROLE -> "message.medieval-machinery.machine_assignment.unsupported_role";
            case NpcMachineAssignmentAudit.Result.WRONG_NPC_CLASS -> "message.medieval-machinery.machine_assignment.wrong_class";
            default -> "message.medieval-machinery.machine_assignment.invalid";
        };
        player.method_7353((class_2561)class_2561.method_43471((String)key), overlay);
    }

    private static void sendMachineAssignmentResult(class_3222 player, boolean assigned) {
        class_2540 out = PacketByteBufs.create();
        out.writeBoolean(assigned);
        ServerPlayNetworking.send((class_3222)player, (class_2960)MACHINE_ASSIGN_RESULT, (class_2540)out);
    }

    private static MachineCrewRole preferredCrewRole(MedievalSoldierEntity soldier, MedievalMachineryOperable machine) {
        MachineCrewRole[] preference;
        MachineCrewRole[] machineCrewRoleArray;
        if (soldier == null || machine == null) {
            return null;
        }
        switch (soldier.getNpcRole()) {
            case CANNONEER: {
                MachineCrewRole[] machineCrewRoleArray2 = new MachineCrewRole[2];
                machineCrewRoleArray2[0] = MachineCrewRole.OPERATOR;
                machineCrewRoleArray = machineCrewRoleArray2;
                machineCrewRoleArray2[1] = MachineCrewRole.LOADER;
                break;
            }
            case ENGINEER: {
                MachineCrewRole[] machineCrewRoleArray3 = new MachineCrewRole[2];
                machineCrewRoleArray3[0] = MachineCrewRole.MAINTENANCE;
                machineCrewRoleArray = machineCrewRoleArray3;
                machineCrewRoleArray3[1] = MachineCrewRole.OPERATOR;
                break;
            }
            case QUARTERMASTER: {
                MachineCrewRole[] machineCrewRoleArray4 = new MachineCrewRole[1];
                machineCrewRoleArray = machineCrewRoleArray4;
                machineCrewRoleArray4[0] = MachineCrewRole.LOADER;
                break;
            }
            default: {
                machineCrewRoleArray = new MachineCrewRole[]{};
            }
        }
        for (MachineCrewRole role : preference = machineCrewRoleArray) {
            if (!machine.medievalMachinery$supportsCrewRole(role) || !NpcMachineRolePolicy.canPerform(soldier.getNpcRole(), role)) continue;
            return role;
        }
        return null;
    }

    private static StationaryCannonEntity migrateLegacyFortressCannon(class_3218 world, class_2338 pos, StationaryCannonBlockEntity legacy) {
        class_2680 legacyState = world.method_8320(pos);
        class_2487 restoreNbt = legacy.method_38244();
        class_1799 powder = legacy.method_5438(0).method_7972();
        class_1799 ammunition = legacy.method_5438(1).method_7972();
        float baseYaw = legacyState.method_28498((class_2769)StationaryCannonBlock.FACING) ? ((class_2350)legacyState.method_11654((class_2769)StationaryCannonBlock.FACING)).method_10144() : 0.0f;
        float actualYaw = class_3532.method_15393((float)(baseYaw + legacy.getShapeYawOffset()));
        float pitch = legacy.getPitch(1.0f);
        StationaryCannonEntity cannon = (StationaryCannonEntity)ModEntities.STATIONARY_CANNON.method_5883((class_1937)world);
        if (cannon == null) {
            return null;
        }
        class_2487 itemState = new class_2487();
        if (!powder.method_7960()) {
            itemState.method_10566("Powder", (class_2520)powder.method_7953(new class_2487()));
        }
        if (!ammunition.method_7960()) {
            itemState.method_10566("Ammunition", (class_2520)ammunition.method_7953(new class_2487()));
        }
        cannon.loadFromItem(itemState);
        cannon.setBaseYaw(baseYaw);
        cannon.setBarrelPitch(pitch);
        class_243 spawn = class_243.method_24955((class_2382)pos);
        cannon.method_5808(spawn.field_1352, spawn.field_1351, spawn.field_1350, actualYaw, 0.0f);
        world.method_8650(pos, false);
        if (world.method_8649((class_1297)cannon)) {
            return cannon;
        }
        world.method_8501(pos, legacyState);
        class_2586 class_25862 = world.method_8321(pos);
        if (class_25862 instanceof StationaryCannonBlockEntity) {
            StationaryCannonBlockEntity restored = (StationaryCannonBlockEntity)class_25862;
            restored.method_11014(restoreNbt);
            restored.method_5431();
        }
        return null;
    }

    private static MedievalSoldierEntity ownedSoldier(class_3222 player, int entityId, double maxDistanceSq) {
        MedievalSoldierEntity soldier;
        class_1297 entity = player.method_51469().method_8469(entityId);
        if (!(entity instanceof MedievalSoldierEntity) || !(soldier = (MedievalSoldierEntity)entity).method_5805() || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) || player.method_5858((class_1297)soldier) > maxDistanceSq) {
            return null;
        }
        return soldier;
    }

    private static MedievalSoldierEntity editingSoldier(class_3222 player) {
        MedievalSoldierEntity soldier;
        UUID soldierId = PATROL_EDIT_SESSIONS.get(player.method_5667());
        if (soldierId == null) {
            return null;
        }
        class_3218 world = player.method_51469();
        class_1297 entity = world.method_14190(soldierId);
        if (!(entity instanceof MedievalSoldierEntity && (soldier = (MedievalSoldierEntity)entity).method_5805() && SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier))) {
            PATROL_EDIT_SESSIONS.remove(player.method_5667());
            return null;
        }
        return soldier;
    }

    private static String validateWaypoint(class_3222 player, MedievalSoldierEntity soldier, class_2338 point) {
        class_3218 world = player.method_51469();
        if (point.method_10264() < world.method_31607() || point.method_10264() >= world.method_31600()) {
            return "message.medieval-machinery.patrol.invalid_point";
        }
        if (!world.method_8621().method_11952(point)) {
            return "message.medieval-machinery.patrol.invalid_point";
        }
        class_243 pointCenter = class_243.method_24953((class_2382)point);
        if (player.method_5707(pointCenter) > 64.0) {
            return "message.medieval-machinery.patrol.point_too_far_from_player";
        }
        double maxSoldier = 128.0;
        if (soldier.method_5707(pointCenter) > maxSoldier * maxSoldier) {
            return "message.medieval-machinery.patrol.point_too_far";
        }
        PatrolRoute route = soldier.getPatrolRoute();
        if (route.size() >= 32) {
            return "message.medieval-machinery.patrol.too_many_points";
        }
        if (!route.isEmpty()) {
            PatrolWaypoint previous = route.get(route.size() - 1);
            double maxSegment = 64.0;
            if (previous != null && previous.position().method_10262((class_2382)point) > maxSegment * maxSegment) {
                return "message.medieval-machinery.patrol.segment_too_long";
            }
        }
        return null;
    }

    private static void sendPatrolSnapshot(class_3222 player, MedievalSoldierEntity soldier, boolean editing) {
        class_2540 out = PacketByteBufs.create();
        out.writeBoolean(editing);
        out.method_10804(soldier.method_5628());
        out.method_10797(soldier.method_5667());
        out.method_10804(soldier.getPatrolState().nextWaypointIndex());
        PatrolRoute route = soldier.getPatrolRoute();
        out.method_10804(route.size());
        for (PatrolWaypoint waypoint : route.waypoints()) {
            out.method_10807(waypoint.position());
        }
        ServerPlayNetworking.send((class_3222)player, (class_2960)PATROL_ROUTE_SNAPSHOT, (class_2540)out);
    }

    private record FarmEditSession(UUID soldierId, boolean composter) {
    }

    private record PendingSoldierTransfer(UUID requestId, UUID soldierId, UUID sourcePlayerId, UUID targetPlayerId, boolean withTroop, long createdAtMs) {
    }
}

