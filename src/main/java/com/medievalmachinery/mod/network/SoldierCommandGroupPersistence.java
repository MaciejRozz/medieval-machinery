/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.network.SoldierCommandScopeResolver;
import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

final class SoldierCommandGroupPersistence {
    private SoldierCommandGroupPersistence() {
    }

    static void markOrderRevisionApplied(class_3218 world, List<MedievalSoldierEntity> soldiers) {
        SoldierCommandGroupPersistence.markRevisionApplied(world, soldiers, RevisionKind.ORDER);
    }

    static void markFormationRevisionApplied(class_3218 world, List<MedievalSoldierEntity> soldiers) {
        SoldierCommandGroupPersistence.markRevisionApplied(world, soldiers, RevisionKind.FORMATION);
    }

    static void markShieldRevisionApplied(class_3218 world, List<MedievalSoldierEntity> soldiers) {
        SoldierCommandGroupPersistence.markRevisionApplied(world, soldiers, RevisionKind.SHIELD);
    }

    static void markCombatStanceRevisionApplied(class_3218 world, List<MedievalSoldierEntity> soldiers) {
        SoldierCommandGroupPersistence.markRevisionApplied(world, soldiers, RevisionKind.STANCE);
    }

    static void persistCombatStance(class_3222 player, int scope, NpcCombatStance stance) {
        SoldierCommandGroupPersistence.mutate(player, scope, (manager, group) -> manager.setCombatStance(group.getId(), player.method_5667(), stance));
    }

    static void persistOrder(class_3222 player, int scope, NpcOrderType order) {
        SoldierCommandGroupPersistence.mutate(player, scope, (manager, group) -> manager.setOrderType(group.getId(), player.method_5667(), order));
    }

    static void persistFormation(class_3222 player, int scope, NpcFormation formation) {
        SoldierCommandGroupPersistence.mutate(player, scope, (manager, group) -> manager.setFormation(group.getId(), player.method_5667(), formation));
    }

    static void persistShieldMode(class_3222 player, int scope, NpcShieldMode mode) {
        SoldierCommandGroupPersistence.mutate(player, scope, (manager, group) -> manager.setShieldMode(group.getId(), player.method_5667(), mode));
    }

    private static void mutate(class_3222 player, int scope, BiConsumer<CommandGroupManager, CommandGroupData> mutation) {
        class_3218 world = player.method_51469();
        UUID castleId = SoldierCommandScopeResolver.selectedCastle(player);
        if (castleId == null) {
            return;
        }
        CommandGroupManager manager = CommandGroupManager.get(world);
        if (scope == 7 || scope == 1) {
            List<CommandGroupData> groups = SoldierCommandScopeResolver.UNASSIGNED_SCOPE_ID.equals(castleId) ? manager.getUnassignedGroups(player.method_5667()) : manager.getUiGroups(castleId, player.method_5667());
            for (CommandGroupData group : groups) {
                mutation.accept(manager, group);
            }
            return;
        }
        int slot = SoldierCommandScopeResolver.groupSlotForScope(scope);
        if (slot <= 0) {
            return;
        }
        CommandGroupData group = SoldierCommandScopeResolver.groupForSlot(world, player, castleId, slot);
        if (group != null) {
            mutation.accept(manager, group);
        }
    }

    private static void markRevisionApplied(class_3218 world, List<MedievalSoldierEntity> soldiers, RevisionKind kind) {
        if (world == null || soldiers == null || soldiers.isEmpty()) {
            return;
        }
        CommandGroupManager manager = CommandGroupManager.get(world);
        for (MedievalSoldierEntity soldier : soldiers) {
            CommandGroupData group;
            UUID groupId = soldier.getNpcIdentity().getSquadId();
            if (groupId == null || (group = manager.get(groupId)) == null) continue;
            switch (kind.ordinal()) {
                case 0: {
                    soldier.markGroupOrderRevisionApplied(group.getOrderRevision());
                    break;
                }
                case 1: {
                    soldier.markGroupFormationRevisionApplied(group.getFormationRevision());
                    break;
                }
                case 2: {
                    soldier.markGroupShieldRevisionApplied(group.getShieldRevision());
                    break;
                }
                case 3: {
                    soldier.markGroupCombatStanceRevisionApplied(group.getCombatStanceRevision());
                }
            }
        }
    }

    private static enum RevisionKind {
        ORDER,
        FORMATION,
        SHIELD,
        STANCE;

    }
}

