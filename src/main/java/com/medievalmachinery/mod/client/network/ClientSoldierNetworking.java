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

import com.medievalmachinery.mod.client.npc.MachineAssignmentEditorClient;
import com.medievalmachinery.mod.client.npc.QuartermasterFarmEditorClient;
import com.medievalmachinery.mod.client.npc.SoldierPatrolMenuScreen;
import com.medievalmachinery.mod.client.npc.SoldierTransferConfirmScreen;
import com.medievalmachinery.mod.client.npc.patrol.PatrolRouteEditorClient;
import com.medievalmachinery.mod.network.SoldierNetworking;
import java.util.ArrayList;
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
public final class ClientSoldierNetworking {
    private ClientSoldierNetworking() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)SoldierNetworking.QUARTERMASTER_FARM_EDIT_COMPLETE, (client, handler, buf, sender) -> client.execute(QuartermasterFarmEditorClient::editingCompleted));
        ClientPlayNetworking.registerGlobalReceiver((class_2960)SoldierNetworking.MACHINE_ASSIGN_RESULT, (client, handler, buf, sender) -> {
            boolean assigned = buf.readBoolean();
            if (assigned) {
                client.execute(MachineAssignmentEditorClient::assignmentSucceeded);
            }
        });
        ClientPlayNetworking.registerGlobalReceiver((class_2960)SoldierNetworking.OPEN_PATROL_MENU, (client, handler, buf, sender) -> {
            int entityId = buf.method_10816();
            int orderOrdinal = buf.method_10816();
            int waypointCount = buf.method_10816();
            client.execute(() -> client.method_1507((class_437)new SoldierPatrolMenuScreen(entityId, orderOrdinal, waypointCount)));
        });
        ClientPlayNetworking.registerGlobalReceiver((class_2960)SoldierNetworking.OPEN_TRANSFER_CONFIRM, (client, handler, buf, sender) -> {
            UUID requestId = buf.method_10790();
            String sourceName = buf.method_10800(32);
            String soldierName = buf.method_10800(64);
            boolean withTroop = buf.readBoolean();
            String groupName = withTroop ? buf.method_10800(64) : "";
            int memberCount = withTroop ? buf.method_10816() : 0;
            client.execute(() -> client.method_1507((class_437)new SoldierTransferConfirmScreen(requestId, sourceName, soldierName, withTroop, groupName, memberCount)));
        });
        ClientPlayNetworking.registerGlobalReceiver((class_2960)SoldierNetworking.PATROL_ROUTE_SNAPSHOT, (client, handler, buf, sender) -> {
            boolean editing = buf.readBoolean();
            int entityId = buf.method_10816();
            UUID soldierUuid = buf.method_10790();
            int nextIndex = buf.method_10816();
            int count = buf.method_10816();
            ArrayList<class_2338> points = new ArrayList<class_2338>(count);
            for (int i = 0; i < count; ++i) {
                points.add(buf.method_10811());
            }
            client.execute(() -> PatrolRouteEditorClient.acceptSnapshot(editing, entityId, soldierUuid, nextIndex, points));
        });
    }

    public static void openSoldierInventory(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.OPEN_INVENTORY, (class_2540)buf);
    }

    public static void requestPatrolMenu(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.PATROL_MENU_REQUEST, (class_2540)buf);
    }

    public static void startPatrolEditing(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.PATROL_EDIT_START, (class_2540)buf);
    }

    public static void stopPatrolEditing() {
        ClientPlayNetworking.send((class_2960)SoldierNetworking.PATROL_EDIT_STOP, (class_2540)PacketByteBufs.create());
    }

    public static void addPatrolPoint(class_2338 point) {
        if (point == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(point);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.PATROL_ADD_POINT, (class_2540)buf);
    }

    public static void removeLastPatrolPoint() {
        ClientPlayNetworking.send((class_2960)SoldierNetworking.PATROL_REMOVE_LAST, (class_2540)PacketByteBufs.create());
    }

    public static void clearPatrolRoute(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.PATROL_CLEAR_ROUTE, (class_2540)buf);
    }

    public static void startObservationPointEditing(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.OBSERVATION_EDIT_START, (class_2540)buf);
    }

    public static void setObservationPoint(class_2338 point) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(point);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.OBSERVATION_EDIT_ACTION, (class_2540)buf);
    }

    public static void stopObservationPointEditing() {
        ClientPlayNetworking.send((class_2960)SoldierNetworking.OBSERVATION_EDIT_STOP, (class_2540)PacketByteBufs.create());
    }

    public static void startQuartermasterComposterEditing(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.QUARTERMASTER_COMPOSTER_EDIT_START, (class_2540)buf);
    }

    public static void startQuartermasterFieldEditing(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.QUARTERMASTER_FIELD_EDIT_START, (class_2540)buf);
    }

    public static void setQuartermasterFarmTarget(class_2338 point) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(point);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.QUARTERMASTER_FARM_EDIT_ACTION, (class_2540)buf);
    }

    public static void stopQuartermasterFarmEditing() {
        ClientPlayNetworking.send((class_2960)SoldierNetworking.QUARTERMASTER_FARM_EDIT_STOP, (class_2540)PacketByteBufs.create());
    }

    public static void clearQuartermasterFields(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.QUARTERMASTER_FIELDS_CLEAR, (class_2540)buf);
    }

    public static void removeQuartermasterField(int entityId, int index) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        buf.method_10804(index);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.QUARTERMASTER_FIELD_REMOVE, (class_2540)buf);
    }

    public static void clearQuartermasterComposter(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.QUARTERMASTER_COMPOSTER_CLEAR, (class_2540)buf);
    }

    public static void requestSoldierTransfer(int entityId, UUID targetPlayerId) {
        ClientSoldierNetworking.requestSoldierTransfer(entityId, targetPlayerId, false);
    }

    public static void requestSoldierTransfer(int entityId, UUID targetPlayerId, boolean withTroop) {
        if (targetPlayerId == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        buf.method_10797(targetPlayerId);
        buf.writeBoolean(withTroop);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.TRANSFER_REQUEST, (class_2540)buf);
    }

    public static void requestCastleTransfer(int entityId, UUID targetCastleId) {
        if (entityId <= 0 || targetCastleId == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        buf.method_10797(targetCastleId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.CASTLE_TRANSFER_REQUEST, (class_2540)buf);
    }

    public static void respondSoldierTransfer(UUID requestId, boolean accepted) {
        if (requestId == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10797(requestId);
        buf.writeBoolean(accepted);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.TRANSFER_RESPONSE, (class_2540)buf);
    }

    public static void startPreferredSupplySelection(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.PREFERRED_SUPPLY_EDIT_START, (class_2540)buf);
    }

    public static void clearPreferredSupplySelection(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.PREFERRED_SUPPLY_CLEAR, (class_2540)buf);
    }

    public static void mountFromGui(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.GUI_MOUNT, (class_2540)buf);
    }

    public static void dismountFromGui(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.GUI_DISMOUNT, (class_2540)buf);
    }

    public static void startMachineAssignment(int entityId) {
        MachineAssignmentEditorClient.start(entityId);
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.MACHINE_ASSIGN_START, (class_2540)buf);
    }

    public static void selectMachineAssignment(int soldierEntityId, int machineEntityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(soldierEntityId);
        buf.method_10804(machineEntityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.MACHINE_ASSIGN_SELECT, (class_2540)buf);
    }

    public static void stopMachineAssignment() {
        ClientPlayNetworking.send((class_2960)SoldierNetworking.MACHINE_ASSIGN_STOP, (class_2540)PacketByteBufs.create());
    }

    public static void requestSupplyFromGui(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.GUI_SUPPLY, (class_2540)buf);
    }

    public static void clearMachineAssignment(int entityId) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(entityId);
        ClientPlayNetworking.send((class_2960)SoldierNetworking.MACHINE_ASSIGN_CLEAR, (class_2540)buf);
    }
}

