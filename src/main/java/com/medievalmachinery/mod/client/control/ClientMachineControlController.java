/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_1297
 *  net.minecraft.class_1937
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_243
 *  net.minecraft.class_2540
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 *  net.minecraft.class_5498
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.client.control;

import com.medievalmachinery.mod.catapult.CatapultAmmunition;
import com.medievalmachinery.mod.entity.cannon.AbstractCannonEntity;
import com.medievalmachinery.mod.entity.cannon.FieldCannonEntity;
import com.medievalmachinery.mod.entity.cannon.StationaryCannonEntity;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import com.medievalmachinery.mod.entity.machine.AbstractMedievalMachineEntity;
import com.medievalmachinery.mod.network.MachineControlNetworking;
import java.util.ArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_239;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_5498;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public final class ClientMachineControlController {
    private static final int AIM_SEND_INTERVAL_TICKS = 1;
    private static final float MIN_SEND_CHANGE = 0.05f;
    private static int activeMachineEntityId = -1;
    private static boolean waitingForInitialShiftRelease;
    private static boolean shiftWasDown;
    private static int sendCooldown;
    private static float lastSentYawOffset;
    private static float lastSentPitch;
    private static int lockedHotbarSlot;
    private static float desiredPitch;
    private static int trajectoryParticleTick;
    private static float lastSentForward;
    private static float lastSentSideways;
    private static int movementKeepAlive;
    private static boolean thirdPersonForced;
    private static boolean catapultFireButtonWasDown;
    private static boolean catapultUseButtonWasDown;
    private static int pendingCameraAlignmentTicks;

    private ClientMachineControlController() {
    }

    public static boolean isAiming() {
        return activeMachineEntityId >= 0;
    }

    public static float getDesiredPitch() {
        return desiredPitch;
    }

    @Nullable
    public static AbstractMedievalMachineEntity getActiveMachine(class_310 client) {
        AbstractMedievalMachineEntity machine;
        if (activeMachineEntityId < 0 || client.field_1687 == null) {
            return null;
        }
        class_1297 entity = client.field_1687.method_8469(activeMachineEntityId);
        return entity instanceof AbstractMedievalMachineEntity && !(machine = (AbstractMedievalMachineEntity)entity).method_31481() ? machine : null;
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver((class_2960)MachineControlNetworking.START_AIMING, (client, handler, buffer, responseSender) -> {
            int entityId = buffer.method_10816();
            client.execute(() -> ClientMachineControlController.beginAiming(client, entityId));
        });
        ClientPlayNetworking.registerGlobalReceiver((class_2960)MachineControlNetworking.STOP_AIMING, (client, handler, buffer, responseSender) -> {
            buffer.method_10816();
            client.execute(ClientMachineControlController::clearAiming);
        });
        ClientTickEvents.END_CLIENT_TICK.register(ClientMachineControlController::tick);
    }

    private static void beginAiming(class_310 client, int entityId) {
        AbstractCannonEntity cannon;
        activeMachineEntityId = entityId;
        waitingForInitialShiftRelease = true;
        shiftWasDown = client.field_1690.field_1832.method_1434();
        sendCooldown = 0;
        lastSentYawOffset = Float.NaN;
        lastSentPitch = Float.NaN;
        lastSentForward = Float.NaN;
        lastSentSideways = Float.NaN;
        movementKeepAlive = 0;
        catapultFireButtonWasDown = client.field_1690.field_1886.method_1434();
        catapultUseButtonWasDown = client.field_1690.field_1904.method_1434();
        pendingCameraAlignmentTicks = 8;
        lockedHotbarSlot = client.field_1724 != null ? client.field_1724.method_31548().field_7545 : 0;
        desiredPitch = 0.0f;
        CatapultEntity catapult = ClientMachineControlController.getActiveCatapult(client);
        if (catapult == null && (cannon = ClientMachineControlController.getActiveCannon(client)) != null) {
            desiredPitch = cannon.getBarrelPitch();
        }
        client.field_1690.method_31043(class_5498.field_26665);
        thirdPersonForced = true;
        ClientMachineControlController.alignCameraToOperator(client);
    }

    private static boolean alignCameraToOperator(class_310 client) {
        AbstractMedievalMachineEntity machine = ClientMachineControlController.getActiveMachine(client);
        if (client.field_1724 == null || machine == null) {
            return false;
        }
        float yaw = class_3532.method_15393((float)(machine.method_5705(1.0f) + machine.getClientOperatorBodyYawOffset()));
        client.field_1724.method_36456(yaw);
        client.field_1724.method_5847(yaw);
        client.field_1724.field_6283 = yaw;
        client.field_1724.field_5982 = yaw;
        client.field_1724.field_6259 = yaw;
        client.field_1724.field_6220 = yaw;
        client.field_1724.method_36457(8.0f);
        client.field_1724.field_6004 = 8.0f;
        return true;
    }

    private static void clearAiming() {
        class_310 client = class_310.method_1551();
        if (thirdPersonForced) {
            client.field_1690.method_31043(class_5498.field_26664);
        }
        thirdPersonForced = false;
        activeMachineEntityId = -1;
        waitingForInitialShiftRelease = false;
        shiftWasDown = false;
        sendCooldown = 0;
        lastSentYawOffset = Float.NaN;
        lastSentPitch = Float.NaN;
        lockedHotbarSlot = 0;
        desiredPitch = 0.0f;
        lastSentForward = Float.NaN;
        lastSentSideways = Float.NaN;
        movementKeepAlive = 0;
        catapultFireButtonWasDown = false;
        catapultUseButtonWasDown = false;
        pendingCameraAlignmentTicks = 0;
    }

    private static void tick(class_310 client) {
        AbstractMedievalMachineEntity mobileMachine;
        if (activeMachineEntityId < 0 || client.field_1724 == null || client.field_1687 == null) {
            return;
        }
        CatapultEntity activeCatapult = ClientMachineControlController.getActiveCatapult(client);
        FieldCannonEntity activeFieldCannon = ClientMachineControlController.getActiveFieldCannon(client);
        AbstractMedievalMachineEntity abstractMedievalMachineEntity = mobileMachine = activeCatapult != null ? activeCatapult : activeFieldCannon;
        if (pendingCameraAlignmentTicks > 0) {
            ClientMachineControlController.alignCameraToOperator(client);
            --pendingCameraAlignmentTicks;
        }
        float cannonForward = 0.0f;
        float cannonSideways = 0.0f;
        if (mobileMachine != null) {
            cannonForward = (client.field_1690.field_1894.method_1434() ? 1.0f : 0.0f) - (client.field_1690.field_1881.method_1434() ? 1.0f : 0.0f);
            cannonSideways = (client.field_1690.field_1849.method_1434() ? 1.0f : 0.0f) - (client.field_1690.field_1913.method_1434() ? 1.0f : 0.0f);
            ClientMachineControlController.sendMachineMovement(client, cannonForward, cannonSideways);
            mobileMachine.predictClientSteering(cannonSideways);
        }
        client.field_1724.field_3913.field_3905 = 0.0f;
        client.field_1724.field_3913.field_3907 = 0.0f;
        client.field_1724.field_3913.field_3904 = false;
        client.field_1724.field_3913.field_3903 = false;
        boolean attackDown = client.field_1690.field_1886.method_1434();
        boolean useDown = client.field_1690.field_1904.method_1434();
        if (activeCatapult != null && (attackDown && !catapultFireButtonWasDown || useDown && !catapultUseButtonWasDown)) {
            class_2540 fire = PacketByteBufs.create();
            fire.method_10804(activeMachineEntityId);
            ClientPlayNetworking.send((class_2960)MachineControlNetworking.FIRE_CATAPULT, (class_2540)fire);
        }
        catapultFireButtonWasDown = attackDown;
        catapultUseButtonWasDown = useDown;
        client.field_1690.field_1886.method_23481(false);
        if (activeCatapult != null) {
            client.field_1690.field_1904.method_23481(false);
        }
        client.field_1690.field_1822.method_23481(false);
        client.field_1724.method_31548().field_7545 = lockedHotbarSlot;
        while (client.field_1690.field_1822.method_1436()) {
        }
        ClientMachineControlController.updateLocalAimAndOperatorPosition(client);
        ClientMachineControlController.sendAimTarget(client);
        ClientMachineControlController.renderTrajectoryPreview(client);
        boolean shiftDown = client.field_1690.field_1832.method_1434();
        if (waitingForInitialShiftRelease) {
            if (!shiftDown) {
                waitingForInitialShiftRelease = false;
            }
            shiftWasDown = shiftDown;
            return;
        }
        if (shiftDown && !shiftWasDown) {
            class_2540 buffer = PacketByteBufs.create();
            buffer.method_10804(activeMachineEntityId);
            ClientPlayNetworking.send((class_2960)MachineControlNetworking.REQUEST_STOP_AIMING, (class_2540)buffer);
        }
        shiftWasDown = shiftDown;
    }

    private static void renderTrajectoryPreview(class_310 client) {
        class_243 velocity;
        class_243 position;
        if (++trajectoryParticleTick % 2 != 0 || activeMachineEntityId < 0) {
            return;
        }
        CatapultEntity catapult = ClientMachineControlController.getActiveCatapult(client);
        AbstractCannonEntity fieldCannon = ClientMachineControlController.getActiveCannon(client);
        if (fieldCannon == null && catapult == null) {
            return;
        }
        class_243 class_2432 = position = catapult != null ? catapult.getLaunchPosition() : fieldCannon.getMuzzlePosition();
        if (catapult != null) {
            CatapultAmmunition.Profile profile = CatapultAmmunition.resolve(catapult.getLoadedAmmunition(), (class_1937)client.field_1687, catapult.method_24515());
            velocity = catapult.getLaunchDirection().method_1021((0.65 + (double)catapult.getTension() / 100.0 * 1.75) * (double)profile.velocityMultiplier());
        } else {
            velocity = fieldCannon.getBarrelDirection().method_1021((double)fieldCannon.getPreviewLaunchSpeed());
        }
        class_243 impact = position;
        boolean foundImpact = false;
        ArrayList<class_243> path = new ArrayList<class_243>();
        CatapultAmmunition.Profile catapultProfile = catapult != null ? CatapultAmmunition.resolve(catapult.getLoadedAmmunition(), (class_1937)client.field_1687, catapult.method_24515()) : null;
        for (int tick = 0; tick < (catapult != null ? 600 : 800); ++tick) {
            path.add(position);
            class_243 next = position.method_1019(velocity);
            class_3965 hit = client.field_1687.method_17742(new class_3959(position, next, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)client.field_1724));
            class_243 class_2433 = impact = hit.method_17783() == class_239.class_240.field_1333 ? next : hit.method_17784();
            if (hit.method_17783() != class_239.class_240.field_1333) {
                foundImpact = true;
                break;
            }
            if (next.field_1351 < (double)client.field_1687.method_31607()) break;
            position = next;
            velocity = catapultProfile != null ? velocity.method_1021(1.0 - (double)catapultProfile.drag()).method_1031(0.0, -0.05 * (double)catapultProfile.gravityMultiplier(), 0.0) : velocity.method_1021(0.99).method_1031(0.0, -0.05, 0.0);
        }
        int stride = Math.max(1, (int)Math.ceil((double)path.size() / 360.0));
        for (int i = 0; i < path.size(); i += stride) {
            class_243 point = (class_243)path.get(i);
            client.field_1687.method_17452((class_2394)class_2398.field_11205, true, point.field_1352, point.field_1351, point.field_1350, 0.0, 0.0, 0.0);
        }
        if (!path.isEmpty()) {
            class_243 last = (class_243)path.get(path.size() - 1);
            client.field_1687.method_17452((class_2394)class_2398.field_11205, true, last.field_1352, last.field_1351, last.field_1350, 0.0, 0.0, 0.0);
        }
        if (foundImpact) {
            for (int i = 0; i < 12; ++i) {
                double angle = Math.PI * 2 * (double)i / 12.0;
                client.field_1687.method_17452((class_2394)class_2398.field_11205, true, impact.field_1352 + Math.cos(angle) * 0.32, impact.field_1351 + 0.03, impact.field_1350 + Math.sin(angle) * 0.32, 0.0, 0.0, 0.0);
            }
        }
    }

    public static boolean handleMouseScroll(double verticalAmount) {
        if (activeMachineEntityId < 0 || verticalAmount == 0.0) {
            return false;
        }
        float direction = verticalAmount > 0.0 ? -1.0f : 1.0f;
        class_310 client = class_310.method_1551();
        CatapultEntity catapult = ClientMachineControlController.getActiveCatapult(client);
        if (catapult != null) {
            catapult.setTension(catapult.getTension() + direction * 2.0f);
            class_2540 buffer = PacketByteBufs.create();
            buffer.method_10804(activeMachineEntityId);
            buffer.writeFloat(direction);
            ClientPlayNetworking.send((class_2960)MachineControlNetworking.ADJUST_CATAPULT_TENSION, (class_2540)buffer);
            return true;
        }
        AbstractCannonEntity cannon = ClientMachineControlController.getActiveCannon(client);
        if (cannon == null) {
            return false;
        }
        desiredPitch = class_3532.method_15363((float)(desiredPitch + direction), (float)0.0f, (float)40.0f);
        sendCooldown = 0;
        return true;
    }

    private static void updateLocalAimAndOperatorPosition(class_310 client) {
        AbstractMedievalMachineEntity machine = ClientMachineControlController.getActiveMachine(client);
        if (machine == null || client.field_1724 == null) {
            return;
        }
        AbstractCannonEntity cannon = ClientMachineControlController.getActiveCannon(client);
        if (cannon != null) {
            cannon.setBarrelPitch(desiredPitch);
        }
        client.field_1724.field_6017 = 0.0f;
    }

    private static void sendMachineMovement(class_310 client, float forward, float sideways) {
        boolean changed;
        boolean bl = changed = Float.isNaN(lastSentForward) || Math.abs(forward - lastSentForward) > 0.001f || Math.abs(sideways - lastSentSideways) > 0.001f;
        if (!changed && ++movementKeepAlive < 10) {
            return;
        }
        class_2540 buffer = PacketByteBufs.create();
        buffer.method_10804(activeMachineEntityId);
        buffer.writeFloat(forward);
        buffer.writeFloat(sideways);
        ClientPlayNetworking.send((class_2960)MachineControlNetworking.MOVE_MACHINE, (class_2540)buffer);
        lastSentForward = forward;
        lastSentSideways = sideways;
        movementKeepAlive = 0;
    }

    private static void sendAimTarget(class_310 client) {
        boolean changed;
        float absoluteYaw;
        if (sendCooldown > 0) {
            --sendCooldown;
            return;
        }
        CatapultEntity catapult = ClientMachineControlController.getActiveCatapult(client);
        if (catapult != null) {
            return;
        }
        AbstractCannonEntity machine = ClientMachineControlController.getActiveCannon(client);
        if (machine == null || client.field_1724 == null) {
            return;
        }
        if (machine instanceof StationaryCannonEntity) {
            float baseYaw = machine.getBaseYaw();
            float offset = class_3532.method_15363((float)class_3532.method_15393((float)(client.field_1724.method_36454() - baseYaw)), (float)-90.0f, (float)90.0f);
            absoluteYaw = class_3532.method_15393((float)(baseYaw + offset));
            machine.predictClientAimYaw(absoluteYaw);
        } else {
            absoluteYaw = machine.method_36454();
        }
        float pitch = desiredPitch;
        boolean bl = changed = Float.isNaN(lastSentYawOffset) || Math.abs(class_3532.method_15393((float)(absoluteYaw - lastSentYawOffset))) >= 0.05f || Math.abs(pitch - lastSentPitch) >= 0.05f;
        if (!changed) {
            return;
        }
        class_2540 buffer = PacketByteBufs.create();
        buffer.method_10804(activeMachineEntityId);
        buffer.writeFloat(absoluteYaw);
        buffer.writeFloat(pitch);
        ClientPlayNetworking.send((class_2960)MachineControlNetworking.UPDATE_AIM, (class_2540)buffer);
        lastSentYawOffset = absoluteYaw;
        lastSentPitch = pitch;
        sendCooldown = 1;
    }

    @Nullable
    private static CatapultEntity getActiveCatapult(class_310 client) {
        CatapultEntity catapult;
        if (activeMachineEntityId < 0 || client.field_1687 == null) {
            return null;
        }
        class_1297 entity = client.field_1687.method_8469(activeMachineEntityId);
        return entity instanceof CatapultEntity && !(catapult = (CatapultEntity)entity).method_31481() ? catapult : null;
    }

    @Nullable
    private static AbstractCannonEntity getActiveCannon(class_310 client) {
        AbstractCannonEntity cannon;
        if (activeMachineEntityId < 0 || client.field_1687 == null) {
            return null;
        }
        class_1297 entity = client.field_1687.method_8469(activeMachineEntityId);
        return entity instanceof AbstractCannonEntity && !(cannon = (AbstractCannonEntity)entity).method_31481() ? cannon : null;
    }

    @Nullable
    private static FieldCannonEntity getActiveFieldCannon(class_310 client) {
        FieldCannonEntity cannon;
        if (activeMachineEntityId < 0 || client.field_1687 == null) {
            return null;
        }
        class_1297 entity = client.field_1687.method_8469(activeMachineEntityId);
        return entity instanceof FieldCannonEntity && !(cannon = (FieldCannonEntity)entity).method_31481() ? cannon : null;
    }

    static {
        lastSentYawOffset = Float.NaN;
        lastSentPitch = Float.NaN;
        lastSentForward = Float.NaN;
        lastSentSideways = Float.NaN;
    }
}

