/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_2338
 *  net.minecraft.class_2540
 *  net.minecraft.class_2960
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.network;

import com.medievalmachinery.mod.client.command.ClientCommandGroupStatus;
import com.medievalmachinery.mod.client.command.CommandGroupTransferConfirmScreen;
import com.medievalmachinery.mod.network.SoldierCommandNetworking;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class ClientSoldierCommandNetworking {
    private ClientSoldierCommandNetworking() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)SoldierCommandNetworking.GROUP_TRANSFER_CONFIRM, (client, handler, buf, responseSender) -> {
            UUID requestId = buf.method_10790();
            String sourceName = buf.method_10800(32);
            String groupName = buf.method_10800(64);
            int memberCount = buf.method_10816();
            client.execute(() -> client.method_1507((class_437)new CommandGroupTransferConfirmScreen(requestId, sourceName, groupName, memberCount)));
        });
    }

    public static void setOrder(int scope, int orderOrdinal) {
        ClientSoldierCommandNetworking.send(0, scope, orderOrdinal, 0);
    }

    public static void setStance(int scope, int stanceOrdinal) {
        ClientSoldierCommandNetworking.send(1, scope, stanceOrdinal, 0);
    }

    public static void setFormation(int scope, int formationOrdinal) {
        ClientSoldierCommandNetworking.send(9, scope, formationOrdinal, 0);
    }

    public static void setShieldMode(int scope, int shieldModeOrdinal) {
        ClientSoldierCommandNetworking.send(10, scope, shieldModeOrdinal, 0);
    }

    public static void setTargetPriority(int scope, int priorityOrdinal) {
        ClientSoldierCommandNetworking.send(23, scope, priorityOrdinal, 0);
    }

    public static void setHoldFire(int scope, boolean hold) {
        ClientSoldierCommandNetworking.send(14, scope, hold ? 1 : 0, 0);
    }

    public static void adjustFormationSpacing(int scope, int direction) {
        ClientSoldierCommandNetworking.send(15, scope, direction, 0);
    }

    public static void alignFormation(int scope) {
        ClientSoldierCommandNetworking.send(16, scope, 0, 0);
    }

    public static void areaVolley(int scope, class_2338 target) {
        if (target == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(17);
        buf.method_10804(scope);
        buf.method_10804(0);
        buf.method_10804(0);
        ClientSoldierCommandNetworking.writeDynamicGroupContext(buf, scope);
        buf.method_10807(target);
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.COMMAND, (class_2540)buf);
    }

    public static void forgetTargets(int scope) {
        ClientSoldierCommandNetworking.send(7, scope, 0, 0);
    }

    public static void attackTarget(int scope, int entityId) {
        if (entityId < 0) {
            return;
        }
        ClientSoldierCommandNetworking.send(12, scope, entityId, 0);
    }

    public static void requestSupply(int scope, int supplyRequestOrdinal) {
        ClientSoldierCommandNetworking.send(13, scope, supplyRequestOrdinal, 0);
    }

    public static void mount(int scope) {
        ClientSoldierCommandNetworking.send(19, scope, 0, 0);
    }

    public static void dismount(int scope) {
        ClientSoldierCommandNetworking.send(20, scope, 0, 0);
    }

    public static void mountEntity(int entityId) {
        ClientSoldierCommandNetworking.send(21, 0, entityId, 0);
    }

    public static void dismountEntity(int entityId) {
        ClientSoldierCommandNetworking.send(22, 0, entityId, 0);
    }

    public static void assignGeneralBodyguards(int entityId) {
        ClientSoldierCommandNetworking.send(25, 0, entityId, 0);
    }

    public static void startBodyguardEdit(int generalEntityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(generalEntityId);
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.BODYGUARD_EDIT_START, (class_2540)buf);
    }

    public static void selectBodyguard(int targetEntityId, boolean remove) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(targetEntityId);
        buf.writeBoolean(remove);
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.BODYGUARD_EDIT_SELECT, (class_2540)buf);
    }

    public static void stopBodyguardEdit() {
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.BODYGUARD_EDIT_STOP, (class_2540)PacketByteBufs.create());
    }

    public static void clearGeneralBodyguards(int entityId) {
        ClientSoldierCommandNetworking.send(26, 0, entityId, 0);
    }

    public static void forgetEntityTarget(int entityId) {
        ClientSoldierCommandNetworking.send(8, 0, entityId, 0);
    }

    public static void assignGroup(int entityId, int group) {
        ClientSoldierCommandNetworking.send(2, 0, entityId, group);
    }

    public static void setEntityOrder(int entityId, int orderOrdinal) {
        ClientSoldierCommandNetworking.send(4, 0, entityId, orderOrdinal);
    }

    public static void setEntityStance(int entityId, int stanceOrdinal) {
        ClientSoldierCommandNetworking.send(5, 0, entityId, stanceOrdinal);
    }

    public static void setEntityHoldFire(int entityId, boolean hold) {
        ClientSoldierCommandNetworking.send(18, 0, entityId, hold ? 1 : 0);
    }

    public static void setEntityShieldMode(int entityId, int shieldModeOrdinal) {
        ClientSoldierCommandNetworking.send(11, 0, entityId, shieldModeOrdinal);
    }

    public static void setEntityTargetPriority(int entityId, int priorityOrdinal) {
        ClientSoldierCommandNetworking.send(24, 0, entityId, priorityOrdinal);
    }

    public static void setEntityPreferredMeleeWeapon(int entityId, int weaponOrdinal) {
        ClientSoldierCommandNetworking.send(27, 0, entityId, weaponOrdinal);
    }

    public static void moveEntityToPlayer(int entityId) {
        ClientSoldierCommandNetworking.send(6, 0, entityId, 0);
    }

    public static void createGroup(int slot, String name) {
        ClientSoldierCommandNetworking.sendGroupMutation(0, slot, name);
    }

    public static void renameGroup(int slot, String name) {
        ClientSoldierCommandNetworking.sendGroupMutation(1, slot, name);
    }

    public static void deleteGroup(int slot) {
        ClientSoldierCommandNetworking.sendGroupMutation(2, slot, "");
    }

    public static void transferGroup(int slot, String playerName) {
        ClientSoldierCommandNetworking.sendGroupMutation(3, slot, playerName);
    }

    public static void respondGroupTransfer(UUID requestId, boolean accepted) {
        if (requestId == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10797(requestId);
        buf.writeBoolean(accepted);
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.GROUP_TRANSFER_RESPONSE, (class_2540)buf);
    }

    public static void transferGroupToCastle(int slot, UUID castleId) {
        ClientSoldierCommandNetworking.sendGroupMutation(5, slot, castleId == null ? "none" : castleId.toString());
    }

    public static void assignGroupPersistent(UUID soldierId, int slot) {
        if (soldierId == null) {
            return;
        }
        ClientSoldierCommandNetworking.sendGroupMutation(4, slot, soldierId.toString());
    }

    public static void moveTo(int scope, class_2338 target) {
        if (target == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(3);
        buf.method_10804(scope);
        buf.method_10804(0);
        buf.method_10804(0);
        ClientSoldierCommandNetworking.writeDynamicGroupContext(buf, scope);
        buf.method_10807(target);
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.COMMAND, (class_2540)buf);
    }

    private static void sendGroupMutation(int mutation, int slot, String value) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(mutation);
        buf.method_10804(slot);
        buf.method_10788(value == null ? "" : value, 48);
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.GROUP_MUTATION, (class_2540)buf);
    }

    private static void send(int action, int scope, int value, int extra) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(action);
        buf.method_10804(scope);
        buf.method_10804(value);
        buf.method_10804(extra);
        ClientSoldierCommandNetworking.writeDynamicGroupContext(buf, scope);
        ClientPlayNetworking.send((class_2960)SoldierCommandNetworking.COMMAND, (class_2540)buf);
    }

    private static void writeDynamicGroupContext(class_2540 buf, int scope) {
        int slot = scope >= 1000 ? scope - 1000 : 0;
        ClientCommandGroupStatus.Group group = slot > 0 ? ClientCommandGroupStatus.group(slot) : null;
        buf.writeBoolean(group != null);
        if (group != null) {
            buf.method_10797(group.id());
        }
    }
}

