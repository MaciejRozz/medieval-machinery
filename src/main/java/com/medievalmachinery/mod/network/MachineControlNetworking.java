/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents
 *  net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
 *  net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
 *  net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
 *  net.minecraft.class_1297
 *  net.minecraft.class_2540
 *  net.minecraft.class_2960
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import com.medievalmachinery.mod.entity.machine.AbstractMedievalMachineEntity;
import com.medievalmachinery.mod.machine.control.MachineControlSync;
import com.medievalmachinery.mod.machine.control.MedievalMachineryControlRegistry;
import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1297;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class MachineControlNetworking {
    public static final class_2960 START_AIMING = MedievalMachinery.id("machine/start_control");
    public static final class_2960 STOP_AIMING = MedievalMachinery.id("machine/stop_control");
    public static final class_2960 REQUEST_STOP_AIMING = MedievalMachinery.id("machine/request_stop_control");
    public static final class_2960 UPDATE_AIM = MedievalMachinery.id("machine/update_aim");
    public static final class_2960 MOVE_MACHINE = MedievalMachinery.id("machine/move");
    public static final class_2960 ADJUST_CATAPULT_TENSION = MedievalMachinery.id("machine/catapult_tension");
    public static final class_2960 FIRE_CATAPULT = MedievalMachinery.id("machine/catapult_fire");

    private MachineControlNetworking() {
    }

    public static void registerServer() {
        MachineControlSync.install(new MachineControlSync.Handler(){

            @Override
            public void notifyStarted(class_3222 player, AbstractMedievalMachineEntity machine) {
                MachineControlNetworking.notifyStarted(player, machine);
            }

            @Override
            public void notifyStopped(class_3222 player, AbstractMedievalMachineEntity machine) {
                MachineControlNetworking.notifyStopped(player, machine);
            }
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)REQUEST_STOP_AIMING, (server, player, handler, buf, sender) -> {
            int id = buf.method_10816();
            server.execute(() -> MachineControlNetworking.stop(player, id));
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)UPDATE_AIM, (server, player, handler, buf, sender) -> {
            int id = buf.method_10816();
            float yaw = buf.readFloat();
            float pitch = buf.readFloat();
            server.execute(() -> MachineControlNetworking.updateAim(player, id, yaw, pitch));
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)MOVE_MACHINE, (server, player, handler, buf, sender) -> {
            int id = buf.method_10816();
            float forward = buf.readFloat();
            float sideways = buf.readFloat();
            server.execute(() -> {
                MedievalMachineryOperable machine = MachineControlNetworking.exactMachine(player, id);
                if (machine != null && machine.medievalMachinery$isMobile()) {
                    machine.medievalMachinery$move(player.method_5667(), forward, sideways);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)ADJUST_CATAPULT_TENSION, (server, player, handler, buf, sender) -> {
            int id = buf.method_10816();
            float direction = buf.readFloat();
            server.execute(() -> {
                MedievalMachineryOperable machine = MachineControlNetworking.exactMachine(player, id);
                if (machine instanceof CatapultEntity) {
                    CatapultEntity catapult = (CatapultEntity)machine;
                    catapult.adjustTension(player.method_5667(), direction);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver((class_2960)FIRE_CATAPULT, (server, player, handler, buf, sender) -> {
            int id = buf.method_10816();
            server.execute(() -> {
                MedievalMachineryOperable machine = MachineControlNetworking.exactMachine(player, id);
                if (machine instanceof CatapultEntity) {
                    CatapultEntity catapult = (CatapultEntity)machine;
                    catapult.fire(player.method_5667());
                }
            });
        });
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, be) -> !MedievalMachineryControlRegistry.isControlling(player.method_5667()));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> MachineControlNetworking.releaseDisconnected(handler.field_14140));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> MachineControlNetworking.releaseWorldChanged(player, origin));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> MedievalMachineryControlRegistry.clearAll());
    }

    public static void notifyStarted(class_3222 player, AbstractMedievalMachineEntity machine) {
        MedievalMachineryControlRegistry.start(player, machine.method_5628());
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(machine.method_5628());
        ServerPlayNetworking.send((class_3222)player, (class_2960)START_AIMING, (class_2540)buf);
    }

    public static void notifyStopped(class_3222 player, AbstractMedievalMachineEntity machine) {
        MedievalMachineryControlRegistry.stop(player.method_5667());
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(machine.method_5628());
        ServerPlayNetworking.send((class_3222)player, (class_2960)STOP_AIMING, (class_2540)buf);
    }

    private static void updateAim(class_3222 player, int id, float yaw, float pitch) {
        MedievalMachineryOperable machine = MachineControlNetworking.exactMachine(player, id);
        if (machine != null) {
            machine.medievalMachinery$setAim(player.method_5667(), yaw, pitch);
        }
    }

    private static void stop(class_3222 player, int id) {
        MedievalMachineryOperable machine = MachineControlNetworking.exactMachine(player, id);
        if (machine != null) {
            machine.medievalMachinery$release(player.method_5667());
        }
        MedievalMachineryControlRegistry.stop(player.method_5667());
        class_2540 buf = PacketByteBufs.create();
        buf.method_10804(id);
        ServerPlayNetworking.send((class_3222)player, (class_2960)STOP_AIMING, (class_2540)buf);
    }

    private static MedievalMachineryOperable exactMachine(class_3222 player, int id) {
        MedievalMachineryOperable machine;
        block5: {
            block4: {
                MedievalMachineryControlRegistry.Session session = MedievalMachineryControlRegistry.get(player.method_5667());
                if (session == null || session.entityId() != id || session.world() != player.method_37908().method_27983()) {
                    return null;
                }
                class_1297 entity = player.method_37908().method_8469(id);
                if (!(entity instanceof MedievalMachineryOperable)) break block4;
                machine = (MedievalMachineryOperable)entity;
                if (!entity.method_31481()) break block5;
            }
            return null;
        }
        return machine.medievalMachinery$isOperatedBy(player.method_5667()) ? machine : null;
    }

    private static void releaseWorldChanged(class_3222 player, class_3218 origin) {
        class_1297 entity;
        MedievalMachineryControlRegistry.Session session = MedievalMachineryControlRegistry.get(player.method_5667());
        if (session != null && session.world() == origin.method_27983() && (entity = origin.method_8469(session.entityId())) instanceof MedievalMachineryOperable) {
            MedievalMachineryOperable machine = (MedievalMachineryOperable)entity;
            machine.medievalMachinery$release(player.method_5667());
        }
        MedievalMachineryControlRegistry.stop(player.method_5667());
    }

    private static void releaseDisconnected(class_3222 player) {
        class_1297 entity;
        MedievalMachineryControlRegistry.Session session = MedievalMachineryControlRegistry.get(player.method_5667());
        if (session != null && session.world() == player.method_37908().method_27983() && (entity = player.method_37908().method_8469(session.entityId())) instanceof MedievalMachineryOperable) {
            MedievalMachineryOperable machine = (MedievalMachineryOperable)entity;
            machine.medievalMachinery$release(player.method_5667());
        }
        MedievalMachineryControlRegistry.stop(player.method_5667());
    }
}

