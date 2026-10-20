/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.network.SoldierAreaFireCommandService;
import com.medievalmachinery.mod.network.SoldierAttackCommandHandler;
import com.medievalmachinery.mod.network.SoldierCommandGroupPersistence;
import com.medievalmachinery.mod.network.SoldierCommandScopeResolver;
import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcPreferredMeleeWeapon;
import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.core.NpcSupplyRequest;
import com.medievalmachinery.mod.npc.core.NpcTargetPriority;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFormationPlanner;
import com.medievalmachinery.mod.npc.organization.CaptainCommandRelayService;
import com.medievalmachinery.mod.npc.organization.CastleAccessPolicy;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.CommandGroupType;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.GeneralBodyguardService;
import com.medievalmachinery.mod.npc.organization.GeneralCommandRelayService;
import com.medievalmachinery.mod.npc.organization.GroupCastleTransferService;
import com.medievalmachinery.mod.npc.organization.NpcSupplyCommandService;
import com.medievalmachinery.mod.npc.organization.SoldierControlPolicy;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryData;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class SoldierCommandNetworking {
    public static final class_2960 COMMAND = MedievalMachinery.id("soldier/command_overlay");
    public static final class_2960 GROUP_STATUS_REQUEST = MedievalMachinery.id("soldier/group_status_request");
    public static final class_2960 GROUP_STATUS_SNAPSHOT = MedievalMachinery.id("soldier/group_status_snapshot");
    public static final class_2960 GROUP_MUTATION = MedievalMachinery.id("soldier/group_mutation");
    public static final class_2960 GROUP_TRANSFER_CONFIRM = MedievalMachinery.id("soldier/group_transfer_confirm");
    public static final class_2960 GROUP_TRANSFER_RESPONSE = MedievalMachinery.id("soldier/group_transfer_response");
    public static final class_2960 ENTITY_GROUP_CONTEXT_REQUEST = MedievalMachinery.id("soldier/entity_group_context_request");
    public static final class_2960 ENTITY_GROUP_CONTEXT_SNAPSHOT = MedievalMachinery.id("soldier/entity_group_context_snapshot");
    public static final class_2960 BODYGUARD_EDIT_START = MedievalMachinery.id("soldier/bodyguard_edit_start");
    public static final class_2960 BODYGUARD_EDIT_SELECT = MedievalMachinery.id("soldier/bodyguard_edit_select");
    public static final class_2960 BODYGUARD_EDIT_STOP = MedievalMachinery.id("soldier/bodyguard_edit_stop");
    public static final int ACTION_SET_ORDER = 0;
    public static final int ACTION_SET_STANCE = 1;
    public static final int ACTION_ASSIGN_GROUP = 2;
    public static final int ACTION_MOVE_TO = 3;
    public static final int ACTION_SET_ENTITY_ORDER = 4;
    public static final int ACTION_SET_ENTITY_STANCE = 5;
    public static final int ACTION_MOVE_ENTITY_TO_PLAYER = 6;
    public static final int ACTION_FORGET_TARGETS = 7;
    public static final int ACTION_FORGET_ENTITY_TARGET = 8;
    public static final int ACTION_SET_FORMATION = 9;
    public static final int ACTION_SET_SHIELD_MODE = 10;
    public static final int ACTION_SET_ENTITY_SHIELD_MODE = 11;
    public static final int ACTION_ATTACK_TARGET = 12;
    public static final int ACTION_SUPPLY = 13;
    public static final int ACTION_HOLD_FIRE = 14;
    public static final int ACTION_FORMATION_SPACING = 15;
    public static final int ACTION_ALIGN_FORMATION = 16;
    public static final int ACTION_AREA_VOLLEY = 17;
    public static final int ACTION_SET_ENTITY_HOLD_FIRE = 18;
    public static final int ACTION_MOUNT = 19;
    public static final int ACTION_DISMOUNT = 20;
    public static final int ACTION_MOUNT_ENTITY = 21;
    public static final int ACTION_DISMOUNT_ENTITY = 22;
    public static final int ACTION_SET_TARGET_PRIORITY = 23;
    public static final int ACTION_SET_ENTITY_TARGET_PRIORITY = 24;
    public static final int ACTION_ASSIGN_GENERAL_BODYGUARDS = 25;
    public static final int ACTION_CLEAR_GENERAL_BODYGUARDS = 26;
    public static final int ACTION_SET_ENTITY_PREFERRED_MELEE_WEAPON = 27;
    public static final int MUTATION_CREATE = 0;
    public static final int MUTATION_RENAME = 1;
    public static final int MUTATION_DELETE = 2;
    public static final int MUTATION_TRANSFER = 3;
    public static final int MUTATION_ASSIGN_MEMBER = 4;
    public static final int MUTATION_TRANSFER_CASTLE = 5;
    public static final int SCOPE_NEARBY = 0;
    public static final int SCOPE_CASTLE = 1;
    public static final int SCOPE_ALL_LOADED = 6;
    public static final int SCOPE_ALL_GROUPS = 7;
    public static final int SCOPE_DYNAMIC_GROUP_BASE = 1000;
    private static final Map<UUID, PendingGroupTransfer> PENDING_GROUP_TRANSFERS = new ConcurrentHashMap<UUID, PendingGroupTransfer>();
    private static final Map<UUID, UUID> BODYGUARD_EDIT_SESSIONS = new ConcurrentHashMap<UUID, UUID>();
    private static final long GROUP_TRANSFER_REQUEST_LIFETIME_MS = 60000L;

    private SoldierCommandNetworking() {
    }

    public static int scopeForGroupSlot(int slot) {
        if (slot <= 0) {
            return 0;
        }
        return 1000 + slot;
    }

    private static CommandPacketData readCommandPacket(class_2540 buf) {
        int action = buf.method_10816();
        int scope = buf.method_10816();
        int value = buf.method_10816();
        int extra = buf.method_10816();
        UUID requestedGroupId = buf.readBoolean() ? buf.method_10790() : null;
        class_2338 moveTarget = action == 3 || action == 17 ? buf.method_10811() : null;
        return new CommandPacketData(action, scope, value, extra, requestedGroupId, moveTarget);
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver((class_2960)BODYGUARD_EDIT_START, (server, player, handler, buf, sender) -> {
            int generalEntityId = buf.method_10816();
            server.execute(() -> {
                MedievalSoldierEntity general;
                class_1297 entity = player.method_51469().method_8469(generalEntityId);
                if (!(entity instanceof MedievalSoldierEntity) || (general = (MedievalSoldierEntity)entity).getNpcRank() != NpcRank.GENERAL || !SoldierControlPolicy.canPersonallyControl((class_1657)player, general) || player.method_5858((class_1297)general) > 64.0) {
                    return;
                }
                BODYGUARD_EDIT_SESSIONS.put(player.method_5667(), general.method_5667());
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.general_bodyguard.editor_started"), false);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)BODYGUARD_EDIT_SELECT, (server, player, handler, buf, sender) -> {
            int targetEntityId = buf.method_10816();
            boolean remove = buf.readBoolean();
            server.execute(() -> {
                boolean changed;
                MedievalSoldierEntity guard;
                MedievalSoldierEntity general;
                block5: {
                    block4: {
                        UUID generalUuid = BODYGUARD_EDIT_SESSIONS.get(player.method_5667());
                        if (generalUuid == null) {
                            return;
                        }
                        class_1297 generalEntity = player.method_51469().method_14190(generalUuid);
                        class_1297 targetEntity = player.method_51469().method_8469(targetEntityId);
                        if (!(generalEntity instanceof MedievalSoldierEntity)) break block4;
                        general = (MedievalSoldierEntity)generalEntity;
                        if (!(targetEntity instanceof MedievalSoldierEntity)) break block4;
                        guard = (MedievalSoldierEntity)targetEntity;
                        if (general.getNpcRank() == NpcRank.GENERAL && SoldierControlPolicy.canPersonallyControl((class_1657)player, general) && !(player.method_5858((class_1297)guard) > 64.0)) break block5;
                    }
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.general_bodyguard.invalid"), true);
                    return;
                }
                boolean bl = changed = remove ? GeneralBodyguardService.removeSelected(player.method_51469(), general, guard) : GeneralBodyguardService.assignSelected(player.method_51469(), general, guard);
                player.method_7353((class_2561)class_2561.method_43471((String)(changed ? (remove ? "message.medieval-machinery.general_bodyguard.removed_selected" : "message.medieval-machinery.general_bodyguard.assigned_selected") : "message.medieval-machinery.general_bodyguard.invalid")), true);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)BODYGUARD_EDIT_STOP, (server, player, handler, buf, sender) -> server.execute(() -> BODYGUARD_EDIT_SESSIONS.remove(player.method_5667())));
        ServerPlayNetworking.registerGlobalReceiver((class_2960)COMMAND, (server, player, handler, buf, sender) -> {
            CommandPacketData packet = SoldierCommandNetworking.readCommandPacket(buf);
            int action = packet.action();
            int scope = packet.scope();
            int value = packet.value();
            int extra = packet.extra();
            UUID requestedGroupId = packet.requestedGroupId();
            class_2338 moveTarget = packet.moveTarget();
            server.execute(() -> {
                if (action == 2) {
                    SoldierCommandNetworking.assignGroup(player, value, extra);
                    return;
                }
                if (action == 4) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, soldier -> {
                        NpcOrderType[] values = NpcOrderType.values();
                        if (extra < 0 || extra >= values.length) {
                            return;
                        }
                        NpcOrderType requested = values[extra];
                        if (requested == NpcOrderType.PATROL && !soldier.hasPatrolRoute()) {
                            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.patrol.no_route_single"), false);
                            return;
                        }
                        soldier.setOrderType(requested);
                    });
                    return;
                }
                if (action == 5) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, soldier -> {
                        NpcCombatStance[] values = NpcCombatStance.values();
                        if (extra >= 0 && extra < values.length) {
                            soldier.setCombatStance(values[extra]);
                        }
                    });
                    return;
                }
                if (action == 6) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, soldier -> soldier.setCommandDestination(player.method_24515()));
                    return;
                }
                if (action == 8) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, MedievalSoldierEntity::forgetCurrentTarget);
                    return;
                }
                if (action == 11) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, soldier -> {
                        NpcShieldMode[] values = NpcShieldMode.values();
                        if (extra >= 0 && extra < values.length) {
                            soldier.setShieldMode(values[extra]);
                        }
                    });
                    return;
                }
                if (action == 18) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, soldier -> soldier.setHoldFire(extra != 0));
                    return;
                }
                if (action == 24) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, soldier -> {
                        NpcTargetPriority[] values = NpcTargetPriority.values();
                        if (extra >= 0 && extra < values.length) {
                            soldier.setTargetPriority(values[extra]);
                        }
                    });
                    return;
                }
                if (action == 27) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, soldier -> {
                        if (!soldier.supportsPreferredMeleeWeaponSelection()) {
                            return;
                        }
                        NpcPreferredMeleeWeapon[] values = NpcPreferredMeleeWeapon.values();
                        if (extra >= 0 && extra < values.length) {
                            soldier.setPreferredMeleeWeapon(values[extra]);
                        }
                    });
                    return;
                }
                if (action == 21) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, MedievalSoldierEntity::commandMountNearby);
                    return;
                }
                if (action == 22) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, MedievalSoldierEntity::commandDismount);
                    return;
                }
                if (action == 25) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, general -> {
                        if (general.getNpcRank() != NpcRank.GENERAL) {
                            return;
                        }
                        int assigned = GeneralBodyguardService.assignNearest(player.method_51469(), general);
                        player.method_7353((class_2561)class_2561.method_43469((String)(assigned > 0 ? "message.medieval-machinery.general_bodyguard.assigned" : "message.medieval-machinery.general_bodyguard.none"), (Object[])new Object[]{assigned}), false);
                    });
                    return;
                }
                if (action == 26) {
                    SoldierCommandNetworking.withOwnedSoldier(player, value, general -> {
                        if (general.getNpcRank() != NpcRank.GENERAL) {
                            return;
                        }
                        int removed = GeneralBodyguardService.clear(player.method_51469(), general);
                        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.general_bodyguard.cleared", (Object[])new Object[]{removed}), false);
                    });
                    return;
                }
                List<MedievalSoldierEntity> targets = SoldierCommandNetworking.resolveTargets(player, scope, requestedGroupId);
                if (action == 19 || action == 20) {
                    int changed = 0;
                    for (MedievalSoldierEntity soldier2 : targets) {
                        boolean ok = action == 19 ? soldier2.commandMountNearby() : soldier2.commandDismount();
                        if (!ok) continue;
                        ++changed;
                    }
                    if (changed > 0) {
                        SoldierCommandScopeResolver.sendCommandFeedback(player, scope, action == 19 ? "message.medieval-machinery.command.chat.mount" : "message.medieval-machinery.command.chat.dismount", new Object[0]);
                    } else {
                        player.method_7353((class_2561)class_2561.method_43471((String)(action == 19 ? "message.medieval-machinery.command.mount.none" : "message.medieval-machinery.command.dismount.none")), false);
                    }
                    return;
                }
                if (action == 13) {
                    if (value != NpcSupplyRequest.SUPPLY.ordinal()) {
                        return;
                    }
                    int assigned = 0;
                    for (MedievalSoldierEntity soldier3 : targets) {
                        if (!NpcSupplyCommandService.requestSupply(soldier3)) continue;
                        ++assigned;
                    }
                    if (assigned <= 0) {
                        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.supply.no_chest"), false);
                    } else {
                        SoldierCommandScopeResolver.sendCommandFeedback(player, scope, "message.medieval-machinery.command.chat.supply", new Object[0]);
                    }
                    return;
                }
                if (action == 12) {
                    SoldierAttackCommandHandler.apply(player, scope, value, targets);
                    return;
                }
                if (action == 7) {
                    for (MedievalSoldierEntity soldier4 : targets) {
                        soldier4.forgetCurrentTarget();
                    }
                    SoldierCommandScopeResolver.sendCommandFeedback(player, scope, "message.medieval-machinery.command.chat.forget_targets", new Object[0]);
                    return;
                }
                if (action == 14) {
                    boolean hold = value != 0;
                    for (MedievalSoldierEntity soldier5 : targets) {
                        if (!hold) {
                            soldier5.stopStrategicAreaFire();
                        }
                        soldier5.setHoldFire(hold);
                    }
                    SoldierCommandScopeResolver.sendCommandFeedback(player, scope, hold ? "message.medieval-machinery.command.chat.hold_fire" : "message.medieval-machinery.command.chat.fire_at_will", new Object[0]);
                    SoldierCommandNetworking.sendGroupStatus(player);
                    return;
                }
                if (action == 15) {
                    NpcFormation formation;
                    for (MedievalSoldierEntity soldier6 : targets) {
                        soldier6.adjustFormationSpacing(value);
                    }
                    NpcFormation npcFormation = formation = targets.isEmpty() ? NpcFormation.FREE : targets.get(0).getFormation();
                    if (formation != NpcFormation.FREE) {
                        SoldierCommandNetworking.assembleFormationNow(player, targets, formation);
                    }
                    SoldierCommandScopeResolver.sendCommandFeedback(player, scope, value < 0 ? "message.medieval-machinery.command.chat.formation_tighter" : "message.medieval-machinery.command.chat.formation_wider", new Object[0]);
                    return;
                }
                if (action == 16) {
                    NpcFormation formation;
                    for (MedievalSoldierEntity soldier7 : targets) {
                        soldier7.setFormationFacingYaw(player.method_36454());
                    }
                    NpcFormation npcFormation = formation = targets.isEmpty() ? NpcFormation.FREE : targets.get(0).getFormation();
                    if (formation != NpcFormation.FREE) {
                        SoldierCommandNetworking.assembleFormationNow(player, targets, formation);
                    }
                    SoldierCommandScopeResolver.sendCommandFeedback(player, scope, "message.medieval-machinery.command.chat.align_formation", new Object[0]);
                    return;
                }
                if (action == 17) {
                    if (moveTarget == null) {
                        return;
                    }
                    class_243 center = class_243.method_24953((class_2382)moveTarget);
                    int fired = SoldierAreaFireCommandService.start(targets, center, 3.5);
                    if (fired > 0) {
                        SoldierCommandScopeResolver.sendCommandFeedback(player, scope, "message.medieval-machinery.command.chat.area_volley", new Object[0]);
                        SoldierCommandNetworking.sendGroupStatus(player);
                    } else {
                        player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.area_volley.none"), false);
                    }
                    return;
                }
                if (action == 9) {
                    NpcFormation[] values = NpcFormation.values();
                    if (value >= 0 && value < values.length) {
                        NpcFormation formation = values[value];
                        for (MedievalSoldierEntity soldier8 : targets) {
                            soldier8.setFormation(formation);
                        }
                        SoldierCommandGroupPersistence.persistFormation(player, scope, formation);
                        SoldierCommandGroupPersistence.markFormationRevisionApplied(player.method_51469(), targets);
                        if (formation != NpcFormation.FREE) {
                            SoldierCommandNetworking.assembleFormationNow(player, targets, formation);
                        }
                    }
                    SoldierCommandNetworking.sendGroupStatus(player);
                    return;
                }
                if (action == 10) {
                    NpcShieldMode[] values = NpcShieldMode.values();
                    if (value >= 0 && value < values.length) {
                        NpcShieldMode mode = values[value];
                        for (MedievalSoldierEntity soldier9 : targets) {
                            soldier9.setShieldMode(mode);
                        }
                        SoldierCommandGroupPersistence.persistShieldMode(player, scope, mode);
                        SoldierCommandGroupPersistence.markShieldRevisionApplied(player.method_51469(), targets);
                    }
                    SoldierCommandScopeResolver.sendCommandFeedback(player, scope, value == NpcShieldMode.RAISED.ordinal() ? "message.medieval-machinery.command.chat.shields_up" : "message.medieval-machinery.command.chat.shields_down", new Object[0]);
                    SoldierCommandNetworking.sendGroupStatus(player);
                    return;
                }
                if (targets.isEmpty()) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.no_soldiers"), false);
                    return;
                }
                if (action == 0) {
                    values = NpcOrderType.values();
                    if (value >= 0 && value < values.length) {
                        Enum requested = values[value];
                        if (requested == NpcOrderType.PATROL) {
                            int started = 0;
                            int skipped = 0;
                            for (MedievalSoldierEntity soldier10 : targets) {
                                if (soldier10.hasPatrolRoute()) {
                                    soldier10.cancelTemporarySupplyForNewCommand();
                                    soldier10.setOrderType(NpcOrderType.PATROL);
                                    ++started;
                                    continue;
                                }
                                ++skipped;
                            }
                            if (started > 0) {
                                SoldierCommandGroupPersistence.persistOrder(player, scope, NpcOrderType.PATROL);
                                SoldierCommandGroupPersistence.markOrderRevisionApplied(player.method_51469(), targets);
                            }
                            if (started == 0) {
                                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.patrol.no_route_group"), false);
                            } else if (skipped > 0) {
                                player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.patrol.group_partial", (Object[])new Object[]{started, skipped}), false);
                            } else {
                                SoldierCommandScopeResolver.sendCommandFeedback(player, scope, "message.medieval-machinery.command.chat.patrol", new Object[0]);
                            }
                            SoldierCommandNetworking.sendGroupStatus(player);
                            return;
                        }
                        for (MedievalSoldierEntity soldier11 : targets) {
                            soldier11.cancelTemporarySupplyForNewCommand();
                            soldier11.setOrderType((NpcOrderType)requested);
                        }
                        SoldierCommandGroupPersistence.persistOrder(player, scope, (NpcOrderType)requested);
                        SoldierCommandGroupPersistence.markOrderRevisionApplied(player.method_51469(), targets);
                    }
                } else if (action == 1) {
                    values = NpcCombatStance.values();
                    if (value >= 0 && value < values.length) {
                        Enum stance = values[value];
                        for (MedievalSoldierEntity soldier12 : targets) {
                            soldier12.setCombatStance((NpcCombatStance)stance);
                        }
                        SoldierCommandGroupPersistence.persistCombatStance(player, scope, (NpcCombatStance)stance);
                        SoldierCommandGroupPersistence.markCombatStanceRevisionApplied(player.method_51469(), targets);
                    }
                } else if (action == 23) {
                    values = NpcTargetPriority.values();
                    if (value >= 0 && value < values.length) {
                        Enum priority = values[value];
                        for (MedievalSoldierEntity soldier13 : targets) {
                            soldier13.setTargetPriority((NpcTargetPriority)priority);
                        }
                    }
                } else if (action == 3 && moveTarget != null) {
                    SoldierCommandNetworking.applyMoveTargetsWithFormation(player, targets, moveTarget);
                    SoldierCommandGroupPersistence.persistOrder(player, scope, NpcOrderType.HOLD_POSITION);
                    SoldierCommandGroupPersistence.markOrderRevisionApplied(player.method_51469(), targets);
                    SoldierCommandScopeResolver.sendCommandFeedback(player, scope, "message.medieval-machinery.command.chat.move", new Object[0]);
                    SoldierCommandNetworking.sendGroupStatus(player);
                    return;
                }
                if (action == 0 && value >= 0 && value < NpcOrderType.values().length) {
                    NpcOrderType order = NpcOrderType.values()[value];
                    key = switch (order) {
                        default -> throw new IncompatibleClassChangeError();
                        case NpcOrderType.IDLE -> "message.medieval-machinery.command.chat.wander";
                        case NpcOrderType.FOLLOW -> "message.medieval-machinery.command.chat.follow";
                        case NpcOrderType.HOLD_POSITION -> "message.medieval-machinery.command.chat.hold";
                        case NpcOrderType.PATROL -> "message.medieval-machinery.command.chat.patrol";
                        case NpcOrderType.RETURN_TO_GARRISON -> "message.medieval-machinery.command.chat.return";
                        case NpcOrderType.MOVE_TO_POSITION -> "message.medieval-machinery.command.chat.move";
                        case NpcOrderType.WORK -> "message.medieval-machinery.command.chat.work";
                        case NpcOrderType.OBSERVATION_POINT -> "message.medieval-machinery.command.chat.observe";
                    };
                    SoldierCommandScopeResolver.sendCommandFeedback(player, scope, key, new Object[0]);
                } else if (action == 1 && value >= 0 && value < NpcCombatStance.values().length) {
                    NpcCombatStance stance = NpcCombatStance.values()[value];
                    key = switch (stance) {
                        default -> throw new IncompatibleClassChangeError();
                        case NpcCombatStance.PASSIVE -> "message.medieval-machinery.command.chat.passive";
                        case NpcCombatStance.NEUTRAL -> "message.medieval-machinery.command.chat.neutral";
                        case NpcCombatStance.AGGRESSIVE -> "message.medieval-machinery.command.chat.aggressive";
                        case NpcCombatStance.RAID -> "message.medieval-machinery.command.chat.raid";
                    };
                    SoldierCommandScopeResolver.sendCommandFeedback(player, scope, key, new Object[0]);
                }
                SoldierCommandNetworking.sendGroupStatus(player);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)ENTITY_GROUP_CONTEXT_REQUEST, (server, player, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            server.execute(() -> SoldierCommandNetworking.sendEntityGroupContext(player, entityId));
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)GROUP_STATUS_REQUEST, (server, player, handler, buf, sender) -> {
            boolean autoSelectCurrent = buf.readBoolean();
            UUID requestedCastle = buf.readBoolean() ? buf.method_10790() : null;
            server.execute(() -> {
                SoldierCommandScopeResolver.selectCastle(player, requestedCastle, autoSelectCurrent);
                SoldierCommandNetworking.sendGroupStatus(player);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)GROUP_TRANSFER_RESPONSE, (server, player, handler, buf, sender) -> {
            UUID requestId = buf.method_10790();
            boolean accepted = buf.readBoolean();
            server.execute(() -> {
                GarrisonData transferCastle;
                PendingGroupTransfer pending = PENDING_GROUP_TRANSFERS.remove(requestId);
                if (pending == null || !pending.targetPlayerId().equals(player.method_5667())) {
                    return;
                }
                class_3222 source = server.method_3760().method_14602(pending.sourcePlayerId());
                if (source == null) {
                    return;
                }
                if (System.currentTimeMillis() - pending.createdAtMs() > 60000L) {
                    source.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.group_transfer_expired"), false);
                    return;
                }
                if (!accepted) {
                    source.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.command.group_transfer_declined", (Object[])new Object[]{player.method_7334().getName()}), false);
                    return;
                }
                class_3218 sourceWorld = source.method_51469();
                CommandGroupManager manager = CommandGroupManager.get(sourceWorld);
                CommandGroupData group = manager.get(pending.groupId());
                if (group == null || !group.getOwnerId().equals(source.method_5667())) {
                    source.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.group_transfer_invalid"), false);
                    return;
                }
                GarrisonData garrisonData = transferCastle = group.getCastleId() == null ? null : GarrisonManager.get(sourceWorld).get(group.getCastleId());
                if (!(transferCastle == null || CastleAccessPolicy.isKingdomMember(sourceWorld, source, transferCastle) && CastleAccessPolicy.isKingdomMember(sourceWorld, player, transferCastle))) {
                    source.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.group_transfer_invalid"), false);
                    return;
                }
                LinkedHashSet<UUID> transferredMembers = new LinkedHashSet<UUID>(group.getSoldierIds());
                UUID castleId = group.getCastleId();
                UUID targetGroup = manager.transferGroup(group.getId(), source.method_5667(), player.method_5667());
                if (targetGroup == null) {
                    source.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.group_transfer_invalid"), false);
                    return;
                }
                SoldierRegistryManager registry = SoldierRegistryManager.get(sourceWorld);
                for (UUID soldierId : transferredMembers) {
                    registry.updateMembership(soldierId, player.method_5667(), castleId, targetGroup);
                }
                SoldierCommandNetworking.applyPendingTransfersToLoaded(source);
                source.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.command.group_transferred", (Object[])new Object[]{group.getName(), player.method_7334().getName()}), false);
                player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.command.group_transfer_received", (Object[])new Object[]{group.getName(), source.method_7334().getName()}), false);
                SoldierCommandNetworking.sendGroupStatus(source);
                SoldierCommandNetworking.sendGroupStatus(player);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)GROUP_MUTATION, (server, player, handler, buf, sender) -> {
            int mutation = buf.method_10816();
            int slot = buf.method_10816();
            String value = buf.method_10800(48);
            server.execute(() -> {
                SoldierCommandNetworking.mutateGroup(player, mutation, slot, value);
                SoldierCommandNetworking.sendGroupStatus(player);
            });
        });
    }

    private static void mutateGroup(class_3222 player, int mutation, int slot, String value) {
        class_3218 world = player.method_51469();
        UUID castleId = SoldierCommandScopeResolver.selectedCastle(player);
        if (castleId == null) {
            return;
        }
        CommandGroupManager manager = CommandGroupManager.get(world);
        CommandGroupData group = SoldierCommandScopeResolver.groupForSlot(world, player, castleId, slot);
        if (mutation == 0) {
            String clean;
            String string = clean = value == null ? "" : value.trim();
            if (clean.isEmpty()) {
                return;
            }
            manager.createCustomGroup(SoldierCommandScopeResolver.UNASSIGNED_SCOPE_ID.equals(castleId) ? null : castleId, player.method_5667(), clean);
            return;
        }
        if (mutation == 4) {
            UUID soldierId;
            try {
                soldierId = UUID.fromString(value);
            }
            catch (Exception ignored) {
                return;
            }
            SoldierRegistryManager registry = SoldierRegistryManager.get(world);
            SoldierRegistryData record = SoldierCommandScopeResolver.ownedSoldierInCastle(world, player, castleId, soldierId);
            if (record == null) {
                return;
            }
            UUID targetGroupId = null;
            if (slot > 0) {
                CommandGroupData targetGroup = SoldierCommandScopeResolver.groupForSlot(world, player, castleId, slot);
                if (targetGroup == null) {
                    return;
                }
                targetGroupId = targetGroup.getId();
            }
            manager.queueAssignment(soldierId, player.method_5667(), castleId, targetGroupId);
            registry.updateMembership(soldierId, player.method_5667(), castleId, targetGroupId);
            SoldierCommandNetworking.applyPendingTransfersToLoaded(player);
            return;
        }
        if (group == null) {
            return;
        }
        if (mutation == 1) {
            if (group.getType() == CommandGroupType.CUSTOM) {
                manager.rename(group.getId(), player.method_5667(), value);
            }
            return;
        }
        if (mutation == 2) {
            if (group.getType() == CommandGroupType.CUSTOM) {
                Set<UUID> members = group.getSoldierIds();
                manager.deleteGroup(group.getId(), player.method_5667());
                SoldierRegistryManager registry = SoldierRegistryManager.get(world);
                for (UUID soldierId : members) {
                    registry.updateMembership(soldierId, player.method_5667(), castleId, null);
                }
                SoldierCommandNetworking.applyPendingTransfersToLoaded(player);
            }
            return;
        }
        if (mutation == 5) {
            GarrisonData sourceCastle;
            GarrisonData garrisonData = sourceCastle = group.getCastleId() == null ? null : GarrisonManager.get(world).get(group.getCastleId());
            if (sourceCastle != null && !CastleAccessPolicy.canAdminister(world, player, sourceCastle)) {
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.castle_admin_required"), false);
                return;
            }
            UUID destinationCastle = null;
            if (!"none".equalsIgnoreCase(value)) {
                try {
                    destinationCastle = UUID.fromString(value);
                }
                catch (IllegalArgumentException ignored) {
                    return;
                }
                GarrisonData destination = GarrisonManager.get(world).get(destinationCastle);
                if (destination == null || !CastleAccessPolicy.canAdminister(world, player, destination)) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.castle_admin_required"), false);
                    return;
                }
            }
            UUID oldCastle = group.getCastleId();
            GarrisonManager garrisons = GarrisonManager.get(world);
            if (destinationCastle != null && oldCastle != null) {
                GroupCastleTransferService.StartResult result = GroupCastleTransferService.request(world, group, player.method_5667(), destinationCastle);
                String key = switch (result) {
                    case GroupCastleTransferService.StartResult.STARTED -> "message.medieval-machinery.group_castle_transfer.started";
                    case GroupCastleTransferService.StartResult.MEMBERS_NOT_LOADED -> "message.medieval-machinery.group_castle_transfer.members_not_loaded";
                    case GroupCastleTransferService.StartResult.ALREADY_ACTIVE -> "message.medieval-machinery.group_castle_transfer.already_active";
                    default -> "message.medieval-machinery.group_castle_transfer.invalid";
                };
                player.method_7353((class_2561)class_2561.method_43471((String)key), false);
                SoldierCommandNetworking.sendGroupStatus(player);
                return;
            }
            if (!manager.assignGroupToCastle(group.getId(), player.method_5667(), destinationCastle)) {
                return;
            }
            SoldierRegistryManager registry = SoldierRegistryManager.get(world);
            for (UUID soldierId : group.getSoldierIds()) {
                if (oldCastle != null) {
                    garrisons.unregisterSoldier(oldCastle, soldierId);
                }
                registry.updateMembership(soldierId, player.method_5667(), null, group.getId());
            }
            class_238 transferArea = player.method_5829().method_1014(300.0);
            for (MedievalSoldierEntity soldier : world.method_8390(MedievalSoldierEntity.class, transferArea, npc -> group.getSoldierIds().contains(npc.method_5667()))) {
                soldier.getNpcIdentity().setCastleId(null);
                soldier.getNpcIdentity().setSquadId(group.getId());
                soldier.setHomePosition(null);
                soldier.applyCastleDisplayName("-");
                manager.reconcileMembership(soldier);
            }
            SoldierCommandNetworking.sendGroupStatus(player);
            return;
        }
        if (mutation == 3) {
            GarrisonData transferCastle;
            GarrisonData groupCastle;
            GarrisonData garrisonData = groupCastle = group.getCastleId() == null ? null : GarrisonManager.get(world).get(group.getCastleId());
            if (groupCastle != null && !CastleAccessPolicy.isKingdomMember(world, player, groupCastle)) {
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.group_transfer_no_source_access"), false);
                return;
            }
            class_3222 target = null;
            for (class_3222 candidate : player.method_5682().method_3760().method_14571()) {
                if (!candidate.method_7334().getName().equalsIgnoreCase(value)) continue;
                target = candidate;
                break;
            }
            if (target == null) {
                player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.command.group_transfer_player_missing", (Object[])new Object[]{value}), false);
                return;
            }
            GarrisonData garrisonData2 = transferCastle = group.getCastleId() == null ? null : GarrisonManager.get(world).get(group.getCastleId());
            if (transferCastle != null && !CastleAccessPolicy.isKingdomMember(world, target, transferCastle)) {
                player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.command.group_transfer_no_access", (Object[])new Object[]{target.method_7334().getName()}), false);
                return;
            }
            UUID targetPlayerId = target.method_5667();
            PENDING_GROUP_TRANSFERS.values().removeIf(pending -> pending.groupId().equals(group.getId()) || pending.targetPlayerId().equals(targetPlayerId));
            UUID requestId = UUID.randomUUID();
            PENDING_GROUP_TRANSFERS.put(requestId, new PendingGroupTransfer(requestId, group.getId(), player.method_5667(), targetPlayerId, System.currentTimeMillis()));
            class_2540 out = PacketByteBufs.create();
            out.method_10797(requestId);
            out.method_10788(player.method_7334().getName(), 32);
            out.method_10788(group.getName(), 64);
            out.method_10804(group.getSoldierIds().size());
            ServerPlayNetworking.send((class_3222)target, (class_2960)GROUP_TRANSFER_CONFIRM, (class_2540)out);
            player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.command.group_transfer_sent", (Object[])new Object[]{group.getName(), target.method_7334().getName()}), false);
        }
    }

    private static void applyPendingTransfersToLoaded(class_3222 player) {
        class_3218 world = player.method_51469();
        CommandGroupManager manager = CommandGroupManager.get(world);
        class_238 area = player.method_5829().method_1014(256.0);
        for (MedievalSoldierEntity soldier2 : world.method_8390(MedievalSoldierEntity.class, area, soldier -> soldier.method_5805())) {
            manager.reconcileMembership(soldier2);
        }
    }

    private static void assignGroup(class_3222 player, int entityId, int slot) {
        MedievalSoldierEntity soldier;
        if (slot < 0 || slot > 512) {
            return;
        }
        class_1297 entity = player.method_51469().method_8469(entityId);
        if (!(entity instanceof MedievalSoldierEntity) || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier = (MedievalSoldierEntity)entity) || player.method_5858((class_1297)soldier) > 65536.0) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.invalid_target"), false);
            return;
        }
        UUID castleId = soldier.getNpcIdentity().getCastleId();
        if (castleId == null) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.wrong_castle"), false);
            return;
        }
        class_3218 world = player.method_51469();
        CommandGroupManager manager = CommandGroupManager.get(world);
        if (slot == 0) {
            manager.assignSoldier(soldier, null);
        } else {
            CommandGroupData group = SoldierCommandScopeResolver.groupForSlot(world, player, castleId, slot);
            if (group == null) {
                return;
            }
            manager.assignSoldier(soldier, group.getId());
        }
        SoldierRegistryManager.get(world).update(soldier);
        SoldierCommandNetworking.sendGroupStatus(player);
    }

    private static void withOwnedSoldier(class_3222 player, int entityId, Consumer<MedievalSoldierEntity> action) {
        MedievalSoldierEntity soldier;
        class_1297 entity = player.method_51469().method_8469(entityId);
        if (entity instanceof MedievalSoldierEntity && (soldier = (MedievalSoldierEntity)entity).method_5805() && SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) && player.method_5858((class_1297)soldier) <= 64.0) {
            action.accept(soldier);
        }
    }

    private static void sendEntityGroupContext(class_3222 player, int entityId) {
        CommandGroupData existingGroup;
        MedievalSoldierEntity soldier;
        class_1297 entity = player.method_51469().method_8469(entityId);
        if (!(entity instanceof MedievalSoldierEntity) || !(soldier = (MedievalSoldierEntity)entity).method_5805() || !SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) || player.method_5858((class_1297)soldier) > 64.0) {
            return;
        }
        class_3218 world = player.method_51469();
        CommandGroupManager manager = CommandGroupManager.get(world);
        manager.reconcileMembership(soldier);
        UUID castleId = soldier.getNpcIdentity().getCastleId();
        if (castleId == null && soldier.getNpcIdentity().getSquadId() != null && (existingGroup = manager.get(soldier.getNpcIdentity().getSquadId())) != null && player.method_5667().equals(existingGroup.getOwnerId())) {
            castleId = existingGroup.getCastleId();
            soldier.getNpcIdentity().setCastleId(castleId);
            soldier.markOrganizationDirty();
        }
        List<CommandGroupData> groups = castleId == null ? manager.getUnassignedGroups(player.method_5667()) : manager.getUiGroups(castleId, player.method_5667());
        UUID soldierGroupId = manager.resolveAlias(soldier.getNpcIdentity().getSquadId());
        int currentSlot = 0;
        for (int i = 0; i < groups.size(); ++i) {
            if (!groups.get(i).getId().equals(soldierGroupId)) continue;
            currentSlot = i + 1;
            break;
        }
        class_2540 out = PacketByteBufs.create();
        out.method_10804(soldier.method_5628());
        out.method_10804(currentSlot);
        out.method_10804(groups.size());
        for (int i = 0; i < groups.size(); ++i) {
            CommandGroupData group = groups.get(i);
            out.method_10804(i + 1);
            out.method_10797(group.getId());
            out.method_10788(group.getName(), 24);
            out.method_10788(group.getType().serializedName(), 16);
        }
        ServerPlayNetworking.send((class_3222)player, (class_2960)ENTITY_GROUP_CONTEXT_SNAPSHOT, (class_2540)out);
    }

    private static void assembleFormationNow(class_3222 player, List<MedievalSoldierEntity> targets, NpcFormation formation) {
        if (targets.isEmpty() || formation == NpcFormation.FREE) {
            return;
        }
        List<MedievalSoldierFormationPlanner.Assignment> assignments = MedievalSoldierFormationPlanner.planAroundCurrentOrTacticalAnchor(player.method_51469(), targets, formation, player.method_36454());
        for (MedievalSoldierFormationPlanner.Assignment assignment : assignments) {
            MedievalSoldierEntity soldier = assignment.soldier();
            soldier.cancelTemporarySupplyForNewCommand();
            soldier.setFormationSlot(assignment.slot());
            soldier.assembleFormationAt(assignment.target());
        }
    }

    private static void applyMoveTargetsWithFormation(class_3222 player, List<MedievalSoldierEntity> targets, class_2338 base) {
        if (targets.isEmpty()) {
            return;
        }
        List<MedievalSoldierFormationPlanner.Assignment> assignments = MedievalSoldierFormationPlanner.planAtMoveTarget(player.method_51469(), targets, base, player.method_36454());
        for (MedievalSoldierFormationPlanner.Assignment assignment : assignments) {
            MedievalSoldierEntity soldier = assignment.soldier();
            soldier.cancelTemporarySupplyForNewCommand();
            soldier.setFormationSlot(assignment.slot());
            class_2338 feet = class_2338.method_49638((class_2374)assignment.target());
            soldier.setCommandDestination(feet.method_10074());
        }
    }

    private static List<MedievalSoldierEntity> collectDirectlyControlledSoldiers(class_3222 player, int scope) {
        double radius = scope == 0 ? 96.0 : 300.0;
        class_238 area = player.method_5829().method_1014(radius);
        return player.method_51469().method_8390(MedievalSoldierEntity.class, area, soldier -> soldier.method_5805() && SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier));
    }

    private static List<MedievalSoldierEntity> resolveTargets(class_3222 player, int scope, UUID requestedGroupId) {
        class_3218 world = player.method_51469();
        UUID castleId = SoldierCommandScopeResolver.selectedCastle(player);
        List<MedievalSoldierEntity> owned = SoldierCommandNetworking.collectDirectlyControlledSoldiers(player, scope);
        CommandGroupManager groupManager = CommandGroupManager.get(world);
        for (MedievalSoldierEntity medievalSoldierEntity : owned) {
            groupManager.reconcileMembership(medievalSoldierEntity);
        }
        if (scope != 0 && scope != 6) {
            Collection<CommandGroupData> relayGroups = SoldierCommandNetworking.groupsForRelayScope(world, player, scope, requestedGroupId, castleId);
            for (MedievalSoldierEntity relayed : CaptainCommandRelayService.collectLoadedRelayedMembers(world, player.method_5667(), player.method_19538(), 300.0, relayGroups)) {
                groupManager.reconcileMembership(relayed);
                if (owned.contains(relayed)) continue;
                owned.add(relayed);
            }
            if (!(scope != 1 && scope != 7 || castleId == null || SoldierCommandScopeResolver.UNASSIGNED_SCOPE_ID.equals(castleId))) {
                for (MedievalSoldierEntity relayed : GeneralCommandRelayService.collectLoadedRelayedMembers(world, player.method_5667(), player.method_19538(), 300.0, castleId, relayGroups)) {
                    groupManager.reconcileMembership(relayed);
                    if (owned.contains(relayed)) continue;
                    owned.add(relayed);
                }
            }
        }
        if (scope == 0) {
            class_238 nearby = player.method_5829().method_1014(64.0);
            owned.removeIf(soldier -> !soldier.method_5829().method_994(nearby));
            return owned;
        }
        if (scope == 1) {
            if (castleId == null) {
                return List.of();
            }
            owned.removeIf(soldier -> !SoldierCommandScopeResolver.matchesCastle(soldier, castleId));
            return owned;
        }
        if (scope == 7) {
            if (castleId == null) {
                return List.of();
            }
            owned.removeIf(soldier -> !SoldierCommandScopeResolver.matchesCastle(soldier, castleId) || soldier.getNpcIdentity().getSquadId() == null);
            return owned;
        }
        int slot = SoldierCommandScopeResolver.groupSlotForScope(scope);
        if (slot > 0) {
            CommandGroupData commandGroupData;
            if (castleId == null) {
                return List.of();
            }
            CommandGroupData commandGroupData2 = commandGroupData = requestedGroupId == null ? SoldierCommandScopeResolver.groupForSlot(world, player, castleId, slot) : CommandGroupManager.get(world).get(CommandGroupManager.get(world).resolveAlias(requestedGroupId));
            if (commandGroupData == null || !SoldierCommandScopeResolver.matchesCastle(commandGroupData, castleId) || !player.method_5667().equals(commandGroupData.getOwnerId())) {
                return List.of();
            }
            UUID groupId = commandGroupData.getId();
            owned.removeIf(soldier -> !SoldierCommandScopeResolver.matchesCastle(soldier, castleId) || !groupId.equals(CommandGroupManager.get(world).resolveAlias(soldier.getNpcIdentity().getSquadId())));
            return owned;
        }
        return owned;
    }

    private static Collection<CommandGroupData> groupsForRelayScope(class_3218 world, class_3222 player, int scope, UUID requestedGroupId, UUID castleId) {
        CommandGroupData group;
        if (castleId == null) {
            return List.of();
        }
        CommandGroupManager groups = CommandGroupManager.get(world);
        if (scope == 1 || scope == 7) {
            return SoldierCommandScopeResolver.UNASSIGNED_SCOPE_ID.equals(castleId) ? groups.getUnassignedGroups(player.method_5667()) : groups.getUiGroups(castleId, player.method_5667());
        }
        int slot = SoldierCommandScopeResolver.groupSlotForScope(scope);
        if (slot <= 0) {
            return List.of();
        }
        CommandGroupData commandGroupData = group = requestedGroupId == null ? SoldierCommandScopeResolver.groupForSlot(world, player, castleId, slot) : groups.get(groups.resolveAlias(requestedGroupId));
        if (group == null || !player.method_5667().equals(group.getOwnerId())) {
            return List.of();
        }
        if (SoldierCommandScopeResolver.UNASSIGNED_SCOPE_ID.equals(castleId)) {
            return group.getCastleId() == null ? List.of(group) : List.of();
        }
        return castleId.equals(group.getCastleId()) ? List.of(group) : List.of();
    }

    private static void sendGroupStatus(class_3222 player) {
        class_3218 world = player.method_51469();
        List<GarrisonData> castles = SoldierCommandScopeResolver.accessibleCastles(player);
        UUID castleId = SoldierCommandScopeResolver.selectedCastle(player);
        if (!SoldierCommandScopeResolver.isAccessibleCastle(castleId, castles)) {
            SoldierCommandScopeResolver.selectCastle(player, null, true);
            castleId = SoldierCommandScopeResolver.selectedCastle(player);
        }
        GarrisonData selectedCastleData = SoldierCommandScopeResolver.selectedCastleData(player, castleId);
        class_2540 out = PacketByteBufs.create();
        out.writeBoolean(selectedCastleData != null);
        if (selectedCastleData != null) {
            out.method_10797(selectedCastleData.getId());
            out.method_10788(selectedCastleData.getName(), 48);
        }
        out.method_10804(castles.size());
        for (GarrisonData castle : castles) {
            out.method_10797(castle.getId());
            out.method_10788(castle.getName(), 48);
        }
        boolean unassignedScope = SoldierCommandScopeResolver.UNASSIGNED_SCOPE_ID.equals(castleId);
        List uiGroups = selectedCastleData == null ? List.of() : (unassignedScope ? CommandGroupManager.get(world).getUnassignedGroups(player.method_5667()) : CommandGroupManager.get(world).getUiGroups(selectedCastleData.getId(), player.method_5667()));
        class_238 commandArea = player.method_5829().method_1014(64.0);
        class_238 loadedArea = player.method_5829().method_1014(256.0);
        List loadedOwned = world.method_8390(MedievalSoldierEntity.class, loadedArea, soldier -> soldier.method_5805() && SoldierControlPolicy.canPersonallyControl((class_1657)player, soldier) && selectedCastleData != null && (unassignedScope ? soldier.getNpcIdentity().getCastleId() == null : selectedCastleData.getId().equals(soldier.getNpcIdentity().getCastleId())));
        HashMap<UUID, MedievalSoldierEntity> loadedByUuid = new HashMap<UUID, MedievalSoldierEntity>();
        SoldierRegistryManager registry = SoldierRegistryManager.get(world);
        for (MedievalSoldierEntity soldier2 : loadedOwned) {
            loadedByUuid.put(soldier2.method_5667(), soldier2);
            registry.update(soldier2);
        }
        List<SoldierRegistryData> roster = selectedCastleData == null ? List.of() : (unassignedScope ? registry.getUnassignedFor(player.method_5667()) : registry.getFor(selectedCastleData.getId(), player.method_5667()));
        HashMap<UUID, Integer> registryTotalsByGroup = new HashMap<UUID, Integer>();
        for (SoldierRegistryData record : roster) {
            UUID resolved = CommandGroupManager.get(world).resolveAlias(record.getGroupId());
            if (resolved == null) continue;
            registryTotalsByGroup.merge(resolved, 1, Integer::sum);
        }
        out.method_10804(uiGroups.size());
        for (int slot = 1; slot <= uiGroups.size(); ++slot) {
            CommandGroupData group = (CommandGroupData)uiGroups.get(slot - 1);
            out.writeBoolean(true);
            out.method_10797(group.getId());
            out.method_10788(group.getName(), 24);
            out.method_10788(group.getType().serializedName(), 16);
            int inRange = 0;
            int commonOrder = -2;
            int commonStance = -2;
            int commonHoldFire = -2;
            int commonAreaFire = -2;
            for (MedievalSoldierEntity soldier3 : loadedOwned) {
                int areaFire;
                UUID soldierGroup = CommandGroupManager.get(world).resolveAlias(soldier3.getNpcIdentity().getSquadId());
                if (!group.getId().equals(soldierGroup)) continue;
                if (soldier3.method_5829().method_994(commandArea)) {
                    ++inRange;
                }
                int order = soldier3.getOrderType().ordinal();
                int stance = soldier3.getCombatStance().ordinal();
                int holdFire = soldier3.isHoldFire() ? 1 : 0;
                int n = areaFire = soldier3.isStrategicAreaFireActive() ? 1 : 0;
                if (commonOrder == -2) {
                    commonOrder = order;
                } else if (commonOrder != order) {
                    commonOrder = -1;
                }
                if (commonStance == -2) {
                    commonStance = stance;
                } else if (commonStance != stance) {
                    commonStance = -1;
                }
                if (commonHoldFire == -2) {
                    commonHoldFire = holdFire;
                } else if (commonHoldFire != holdFire) {
                    commonHoldFire = -1;
                }
                if (commonAreaFire == -2) {
                    commonAreaFire = areaFire;
                    continue;
                }
                if (commonAreaFire == areaFire) continue;
                commonAreaFire = -1;
            }
            HashSet<UUID> actualMemberIds = new HashSet<UUID>(group.getSoldierIds());
            for (SoldierRegistryData record : roster) {
                UUID resolved = CommandGroupManager.get(world).resolveAlias(record.getGroupId());
                if (!group.getId().equals(resolved)) continue;
                actualMemberIds.add(record.getSoldierId());
            }
            for (MedievalSoldierEntity soldier4 : loadedOwned) {
                UUID resolved = CommandGroupManager.get(world).resolveAlias(soldier4.getNpcIdentity().getSquadId());
                if (!group.getId().equals(resolved)) continue;
                actualMemberIds.add(soldier4.method_5667());
            }
            out.method_10804(inRange);
            out.method_10804(actualMemberIds.size());
            out.writeInt(commonOrder);
            out.writeInt(commonStance);
            out.writeInt(commonHoldFire);
            out.writeInt(commonAreaFire);
            out.writeInt(group.getFormation().ordinal());
            out.writeInt(group.getShieldMode().ordinal());
        }
        out.method_10804(roster.size());
        for (SoldierRegistryData record : roster) {
            MedievalSoldierEntity loaded;
            UUID resolvedGroupId = CommandGroupManager.get(world).resolveAlias(record.getGroupId());
            int slot = 0;
            for (int i = 0; i < uiGroups.size(); ++i) {
                if (!((CommandGroupData)uiGroups.get(i)).getId().equals(resolvedGroupId)) continue;
                slot = i + 1;
                break;
            }
            boolean isLoaded = (loaded = (MedievalSoldierEntity)loadedByUuid.get(record.getSoldierId())) != null;
            out.method_10797(record.getSoldierId());
            out.method_10804(isLoaded ? loaded.method_5628() : -1);
            out.writeBoolean(isLoaded);
            out.method_10804(slot);
            out.method_10788(record.getName(), 48);
            out.method_10788(record.getRole(), 24);
            out.writeFloat(record.getHealth());
            out.writeFloat(record.getMaxHealth());
            out.writeBoolean(record.hasFood());
            out.writeBoolean(record.hasWeapon());
        }
        ServerPlayNetworking.send((class_3222)player, (class_2960)GROUP_STATUS_SNAPSHOT, (class_2540)out);
    }

    private record CommandPacketData(int action, int scope, int value, int extra, UUID requestedGroupId, class_2338 moveTarget) {
    }

    private record PendingGroupTransfer(UUID requestId, UUID groupId, UUID sourcePlayerId, UUID targetPlayerId, long createdAtMs) {
    }
}

