/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
 *  net.minecraft.class_1262
 *  net.minecraft.class_1263
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2371
 *  net.minecraft.class_2382
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2596
 *  net.minecraft.class_2602
 *  net.minecraft.class_2622
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3417
 *  net.minecraft.class_3419
 *  net.minecraft.class_3532
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoBlockEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.medievalmachinery.mod.blockentity.machine.cannon;

import com.medievalmachinery.mod.block.machine.cannon.StationaryCannonBlock;
import com.medievalmachinery.mod.blockentity.machine.cannon.LegacyStationaryCannonAccess;
import com.medievalmachinery.mod.entity.cannon.CannonballEntity;
import com.medievalmachinery.mod.machine.cannon.CannonBallistics;
import com.medievalmachinery.mod.machine.cannon.CannonLoadState;
import com.medievalmachinery.mod.machine.control.MedievalMachineryOperator;
import com.medievalmachinery.mod.registry.ModBlockEntities;
import com.medievalmachinery.mod.registry.ModItems;
import com.medievalmachinery.mod.screen.machine.cannon.StationaryCannonScreenHandler;
import java.util.UUID;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.class_1262;
import net.minecraft.class_1263;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2371;
import net.minecraft.class_2382;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2596;
import net.minecraft.class_2602;
import net.minecraft.class_2622;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3532;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

@Deprecated
public final class StationaryCannonBlockEntity
extends class_2586
implements class_1263,
ExtendedScreenHandlerFactory,
GeoBlockEntity,
LegacyStationaryCannonAccess {
    public static final int GUNPOWDER_SLOT = 0;
    public static final int CANNONBALL_SLOT = 1;
    public static final float MIN_YAW_OFFSET = -90.0f;
    public static final float MAX_YAW_OFFSET = 90.0f;
    public static final float MIN_PITCH = -5.0f;
    public static final float MAX_PITCH = 45.0f;
    private static final float YAW_SPEED_PER_TICK = 2.0f;
    private static final float PITCH_SPEED_PER_TICK = 1.0f;
    private static final double OPERATOR_DISTANCE = 1.35;
    private static final double MAX_OPERATOR_DISTANCE_SQUARED = 64.0;
    private static final int OPERATOR_CHECK_INTERVAL_TICKS = 20;
    public static final int IGNITION_DURATION_TICKS = 30;
    private static final int FUSE_SOUND_INTERVAL_TICKS = 6;
    private static final int RECOIL_DURATION_TICKS = 12;
    private final MedievalMachineryOperator operator = new MedievalMachineryOperator();
    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private final class_2371<class_1799> inventory = class_2371.method_10213((int)2, (Object)class_1799.field_8037);
    private int operatorCheckTicks;
    private float yawOffset;
    private float previousYawOffset;
    private float targetYawOffset;
    private float pitch;
    private float previousPitch;
    private float targetPitch;
    private int ignitionTicksRemaining;
    private int recoilTicksRemaining;
    private boolean clientAimPreviewActive;
    private float clientPreviousYawOffset;
    private float clientYawOffset;
    private float clientPreviousPitch;
    private float clientPitch;

    public StationaryCannonBlockEntity(class_2338 pos, class_2680 state) {
        super(ModBlockEntities.STATIONARY_CANNON, pos, state);
    }

    public boolean tryClaimOperator(UUID playerUuid) {
        boolean claimed = this.operator.tryClaim(playerUuid, true);
        if (claimed) {
            this.markDirtyAndSync();
        }
        return claimed;
    }

    public boolean releaseOperator(UUID playerUuid) {
        boolean released = this.operator.release(playerUuid);
        if (released) {
            this.markDirtyAndSync();
        }
        return released;
    }

    public boolean isOperatedBy(UUID playerUuid) {
        return this.operator.isOperatedBy(playerUuid);
    }

    @Nullable
    public UUID getOperatorUuid() {
        return this.operator.getOperatorUuid();
    }

    public void setAimTarget(UUID playerUuid, float requestedYawOffset, float requestedPitch) {
        if (!this.operator.isOperatedBy(playerUuid)) {
            return;
        }
        this.targetYawOffset = class_3532.method_15363((float)requestedYawOffset, (float)-90.0f, (float)90.0f);
        this.targetPitch = class_3532.method_15363((float)requestedPitch, (float)-5.0f, (float)45.0f);
        this.method_5431();
    }

    public void setClientAimPreview(float requestedYawOffset, float requestedPitch) {
        if (this.field_11863 == null || !this.field_11863.field_9236) {
            return;
        }
        float previewYaw = class_3532.method_15363((float)requestedYawOffset, (float)-90.0f, (float)90.0f);
        float previewPitch = class_3532.method_15363((float)requestedPitch, (float)-5.0f, (float)45.0f);
        if (!this.clientAimPreviewActive) {
            this.clientAimPreviewActive = true;
            this.clientPreviousYawOffset = previewYaw;
            this.clientYawOffset = previewYaw;
            this.clientPreviousPitch = previewPitch;
            this.clientPitch = previewPitch;
            return;
        }
        this.clientPreviousYawOffset = this.clientYawOffset;
        this.clientYawOffset = previewYaw;
        this.clientPreviousPitch = this.clientPitch;
        this.clientPitch = previewPitch;
    }

    public void clearClientAimPreview() {
        this.clientAimPreviewActive = false;
    }

    public float getYawOffset(float tickDelta) {
        if (this.clientAimPreviewActive) {
            return class_3532.method_16439((float)tickDelta, (float)this.clientPreviousYawOffset, (float)this.clientYawOffset);
        }
        return class_3532.method_16439((float)tickDelta, (float)this.previousYawOffset, (float)this.yawOffset);
    }

    public float getPitch(float tickDelta) {
        if (this.clientAimPreviewActive) {
            return class_3532.method_16439((float)tickDelta, (float)this.clientPreviousPitch, (float)this.clientPitch);
        }
        return class_3532.method_16439((float)tickDelta, (float)this.previousPitch, (float)this.pitch);
    }

    public float getTargetYawOffset() {
        return this.targetYawOffset;
    }

    @Override
    public float getShapeYawOffset() {
        if (this.field_11863 != null && this.field_11863.field_9236 && this.clientAimPreviewActive) {
            return this.clientYawOffset;
        }
        return this.yawOffset;
    }

    public float getTargetPitch() {
        return this.targetPitch;
    }

    @Override
    public boolean isIgnited() {
        return this.ignitionTicksRemaining > 0;
    }

    public int getIgnitionTicksRemaining() {
        return this.ignitionTicksRemaining;
    }

    public float getRecoilOffset(float tickDelta) {
        if (this.recoilTicksRemaining <= 0) {
            return 0.0f;
        }
        float progress = ((float)(12 - this.recoilTicksRemaining) + tickDelta) / 12.0f;
        float kick = (float)Math.sin(Math.PI * (double)class_3532.method_15363((float)progress, (float)0.0f, (float)1.0f));
        return kick * 2.25f;
    }

    @Override
    public boolean tryIgnite() {
        if (this.field_11863 == null || this.field_11863.field_9236 || this.isIgnited() || !this.isReadyToFire()) {
            return false;
        }
        this.ignitionTicksRemaining = 30;
        this.markDirtyAndSync();
        return true;
    }

    public CannonLoadState getLoadState() {
        boolean hasPowder = this.method_5438(0).method_31574(class_1802.field_8054);
        boolean hasCannonball = ModItems.isCannonball(this.method_5438(1).method_7909());
        if (hasPowder && hasCannonball) {
            return CannonLoadState.READY;
        }
        if (hasPowder) {
            return CannonLoadState.POWDER_LOADED;
        }
        if (hasCannonball) {
            return CannonLoadState.CANNONBALL_LOADED;
        }
        return CannonLoadState.EMPTY;
    }

    @Override
    public boolean isReadyToFire() {
        return this.getLoadState() == CannonLoadState.READY;
    }

    public boolean method_5437(int slot, class_1799 stack) {
        return switch (slot) {
            case 0 -> stack.method_31574(class_1802.field_8054);
            case 1 -> ModItems.isCannonball(stack.method_7909());
            default -> false;
        };
    }

    public class_2561 method_5476() {
        return class_2561.method_43471((String)"screen.medieval-machinery.stationary_cannon");
    }

    public void writeScreenOpeningData(class_3222 player, class_2540 buf) {
        buf.method_10807(this.field_11867);
    }

    @Nullable
    public class_1703 createMenu(int syncId, class_1661 playerInventory, class_1657 player) {
        return new StationaryCannonScreenHandler(syncId, playerInventory, (class_1263)this, this.field_11867);
    }

    public int method_5439() {
        return this.inventory.size();
    }

    public boolean method_5442() {
        for (class_1799 stack : this.inventory) {
            if (stack.method_7960()) continue;
            return false;
        }
        return true;
    }

    public class_1799 method_5438(int slot) {
        return (class_1799)this.inventory.get(slot);
    }

    public class_1799 method_5434(int slot, int amount) {
        class_1799 result = class_1262.method_5430(this.inventory, (int)slot, (int)amount);
        if (!result.method_7960()) {
            this.markDirtyAndSync();
        }
        return result;
    }

    public class_1799 method_5441(int slot) {
        class_1799 result = class_1262.method_5428(this.inventory, (int)slot);
        if (!result.method_7960()) {
            this.markDirtyAndSync();
        }
        return result;
    }

    public void method_5447(int slot, class_1799 stack) {
        if (!stack.method_7960() && !this.method_5437(slot, stack)) {
            return;
        }
        boolean wasReady = this.isReadyToFire();
        class_1799 stored = stack;
        if (!stack.method_7960() && stack.method_7947() > this.method_5444()) {
            stored = stack.method_7972();
            stored.method_7939(this.method_5444());
        }
        this.inventory.set(slot, (Object)stored);
        if (!wasReady && this.isReadyToFire() && this.field_11863 != null && !this.field_11863.field_9236) {
            this.field_11863.method_8396(null, this.field_11867, class_3417.field_15131, class_3419.field_15245, 0.65f, 1.35f);
            for (class_1657 player : this.field_11863.method_18456()) {
                if (!(player.method_5707(class_243.method_24953((class_2382)this.field_11867)) <= 36.0)) continue;
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.stationary_cannon.loaded"), true);
            }
        }
        this.markDirtyAndSync();
    }

    public int method_5444() {
        return 1;
    }

    public boolean method_5443(class_1657 player) {
        if (this.field_11863 == null || this.field_11863.method_8321(this.field_11867) != this) {
            return false;
        }
        return player.method_5707(class_243.method_24953((class_2382)this.field_11867)) <= 64.0;
    }

    public void method_5448() {
        this.inventory.clear();
        this.markDirtyAndSync();
    }

    public static void serverTick(class_1937 world, class_2338 pos, class_2680 state, StationaryCannonBlockEntity cannon) {
        if (!(world instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)world;
        cannon.updateAimAngles();
        cannon.tickRecoil();
        cannon.tickIgnition(serverWorld, state);
        UUID operatorUuid = cannon.operator.getOperatorUuid();
        if (operatorUuid == null) {
            return;
        }
        class_3222 player = serverWorld.method_8503().method_3760().method_14602(operatorUuid);
        ++cannon.operatorCheckTicks;
        if (cannon.operatorCheckTicks >= 20) {
            boolean invalid;
            cannon.operatorCheckTicks = 0;
            class_243 center = class_243.method_24953((class_2382)pos);
            boolean bl = invalid = player == null || !player.method_5805() || player.method_37908() != world || player.method_5707(center) > 64.0;
            if (invalid) {
                cannon.operator.forceRelease();
                cannon.markDirtyAndSync();
                return;
            }
        }
        if (player != null) {
            cannon.keepOperatorAtControls(player, state, pos);
        }
    }

    private void tickIgnition(class_3218 world, class_2680 state) {
        if (this.ignitionTicksRemaining <= 0) {
            return;
        }
        --this.ignitionTicksRemaining;
        class_243 fusePos = this.getFuseWorldPosition(state);
        world.method_14199((class_2394)class_2398.field_11251, fusePos.field_1352, fusePos.field_1351, fusePos.field_1350, 1, 0.025, 0.025, 0.025, 0.005);
        if (this.ignitionTicksRemaining % 3 == 0) {
            world.method_14199((class_2394)class_2398.field_11240, fusePos.field_1352, fusePos.field_1351, fusePos.field_1350, 1, 0.012, 0.012, 0.012, 0.002);
        }
        if (this.ignitionTicksRemaining % 6 == 0) {
            world.method_43128(null, fusePos.field_1352, fusePos.field_1351, fusePos.field_1350, class_3417.field_14993, class_3419.field_15245, 0.35f, 1.65f + world.field_9229.method_43057() * 0.15f);
        }
        if (this.ignitionTicksRemaining == 0) {
            this.fire(world, state);
        }
        this.markDirtyAndSync();
    }

    private class_243 getFuseWorldPosition(class_2680 state) {
        class_243 horizontalForward = this.getHorizontalForward(state);
        double pitchRadians = Math.toRadians(this.pitch);
        class_243 pivot = class_243.method_24953((class_2382)this.field_11867).method_1020(horizontalForward.method_1021(0.3125)).method_1031(0.0, 0.25, 0.0);
        double attachmentHeight = 0.34375;
        class_243 rotatedAttachment = new class_243(-horizontalForward.field_1352 * Math.sin(pitchRadians) * attachmentHeight, Math.cos(pitchRadians) * attachmentHeight, -horizontalForward.field_1350 * Math.sin(pitchRadians) * attachmentHeight);
        return pivot.method_1019(rotatedAttachment).method_1031(0.0, 0.025, 0.0);
    }

    private class_243 getMuzzleWorldPosition(class_2680 state) {
        class_243 direction = this.getBarrelDirection(state);
        class_243 horizontalForward = this.getHorizontalForward(state);
        class_243 pivot = class_243.method_24953((class_2382)this.field_11867).method_1020(horizontalForward.method_1021(0.3125)).method_1031(0.0, 0.25, 0.0);
        return pivot.method_1019(direction.method_1021(1.40625));
    }

    public class_243 getPreviewMuzzlePosition(class_2680 state) {
        return this.getMuzzleWorldPosition(state);
    }

    public class_243 getPreviewDirection(class_2680 state) {
        return this.getBarrelDirection(state);
    }

    private class_243 getHorizontalForward(class_2680 state) {
        float baseYaw = ((class_2350)state.method_11654((class_2769)StationaryCannonBlock.FACING)).method_10144();
        double yawRadians = Math.toRadians(baseYaw + this.yawOffset);
        return new class_243(-Math.sin(yawRadians), 0.0, Math.cos(yawRadians));
    }

    private class_243 getBarrelDirection(class_2680 state) {
        float baseYaw = ((class_2350)state.method_11654((class_2769)StationaryCannonBlock.FACING)).method_10144();
        double yawRadians = Math.toRadians(baseYaw + this.yawOffset);
        double pitchRadians = Math.toRadians(this.pitch);
        double horizontal = Math.cos(pitchRadians);
        return new class_243(-Math.sin(yawRadians) * horizontal, Math.sin(pitchRadians), Math.cos(yawRadians) * horizontal).method_1029();
    }

    private void fire(class_3218 world, class_2680 state) {
        class_1799 powder = this.method_5438(0);
        class_1799 ammunitionStack = this.method_5438(1);
        if (!powder.method_31574(class_1802.field_8054) || !ModItems.isCannonball(ammunitionStack.method_7909())) {
            this.markDirtyAndSync();
            return;
        }
        class_243 direction = this.getBarrelDirection(state);
        class_243 muzzle = this.getMuzzleWorldPosition(state);
        float maximumRange = CannonBallistics.maximumRange(ammunitionStack.method_7909());
        float launchSpeed = CannonBallistics.launchSpeed(ammunitionStack.method_7909());
        CannonballEntity projectile = new CannonballEntity((class_1937)world, muzzle.field_1352, muzzle.field_1351, muzzle.field_1350, ammunitionStack.method_7909(), maximumRange);
        projectile.method_7485(direction.field_1352, direction.field_1351, direction.field_1350, launchSpeed, 0.1f);
        world.method_8649((class_1297)projectile);
        world.method_43128(null, muzzle.field_1352, muzzle.field_1351, muzzle.field_1350, class_3417.field_15152, class_3419.field_15245, 3.5f, 0.72f + world.field_9229.method_43057() * 0.08f);
        world.method_14199((class_2394)class_2398.field_11240, muzzle.field_1352, muzzle.field_1351, muzzle.field_1350, 18, 0.12, 0.12, 0.12, 0.08);
        world.method_14199((class_2394)class_2398.field_17430, muzzle.field_1352, muzzle.field_1351, muzzle.field_1350, 34, 0.22, 0.22, 0.22, 0.1);
        world.method_14199((class_2394)class_2398.field_11203, muzzle.field_1352, muzzle.field_1351, muzzle.field_1350, 24, Math.abs(direction.field_1352) * 0.32 + 0.08, Math.abs(direction.field_1351) * 0.32 + 0.08, Math.abs(direction.field_1350) * 0.32 + 0.08, 0.16);
        powder.method_7934(1);
        ammunitionStack.method_7934(1);
        this.recoilTicksRemaining = 12;
        this.markDirtyAndSync();
    }

    public float getPreviewLaunchSpeed() {
        return CannonBallistics.launchSpeed(this.method_5438(1).method_7909());
    }

    private void tickRecoil() {
        if (this.recoilTicksRemaining > 0) {
            --this.recoilTicksRemaining;
            this.markDirtyAndSync();
        }
    }

    private void updateAimAngles() {
        this.previousYawOffset = this.yawOffset;
        this.previousPitch = this.pitch;
        float newYaw = StationaryCannonBlockEntity.approach(this.yawOffset, this.targetYawOffset, 2.0f);
        float newPitch = StationaryCannonBlockEntity.approach(this.pitch, this.targetPitch, 1.0f);
        if (newYaw != this.yawOffset || newPitch != this.pitch) {
            this.yawOffset = newYaw;
            this.pitch = newPitch;
            this.markDirtyAndSync();
        }
    }

    private static float approach(float current, float target, float maximumChange) {
        float difference = target - current;
        if (Math.abs(difference) <= maximumChange) {
            return target;
        }
        return current + Math.copySign(maximumChange, difference);
    }

    private void keepOperatorAtControls(class_3222 player, class_2680 state, class_2338 cannonPos) {
        double dz;
        double dy;
        float baseYaw = ((class_2350)state.method_11654((class_2769)StationaryCannonBlock.FACING)).method_10144();
        float cannonYaw = baseYaw + this.yawOffset;
        double radians = Math.toRadians(cannonYaw);
        double behindX = Math.sin(radians) * 1.35;
        double behindZ = -Math.cos(radians) * 1.35;
        double rightOffset = 0.45;
        double rightX = -Math.cos(radians) * rightOffset;
        double rightZ = -Math.sin(radians) * rightOffset;
        double x = (double)cannonPos.method_10263() + 0.5 + behindX + rightX;
        double y = cannonPos.method_10264();
        double z = (double)cannonPos.method_10260() + 0.5 + behindZ + rightZ;
        double dx = player.method_23317() - x;
        double distanceSquared = dx * dx + (dy = player.method_23318() - y) * dy + (dz = player.method_23321() - z) * dz;
        if (distanceSquared > 1.0) {
            player.method_14251((class_3218)player.method_37908(), x, y, z, player.method_36454(), player.method_36455());
        }
        player.method_18799(class_243.field_1353);
        player.field_6017 = 0.0f;
    }

    private void markDirtyAndSync() {
        this.method_5431();
        if (this.field_11863 != null && !this.field_11863.field_9236) {
            this.field_11863.method_8413(this.field_11867, this.method_11010(), this.method_11010(), 3);
        }
    }

    protected void method_11007(class_2487 nbt) {
        super.method_11007(nbt);
        this.operator.writeNbt(nbt);
        class_1262.method_5426((class_2487)nbt, this.inventory);
        nbt.method_10548("YawOffset", this.yawOffset);
        nbt.method_10548("TargetYawOffset", this.targetYawOffset);
        nbt.method_10548("Pitch", this.pitch);
        nbt.method_10548("TargetPitch", this.targetPitch);
        nbt.method_10569("IgnitionTicks", this.ignitionTicksRemaining);
        nbt.method_10569("RecoilTicks", this.recoilTicksRemaining);
    }

    public void method_11014(class_2487 nbt) {
        super.method_11014(nbt);
        this.operator.readNbt(nbt);
        this.inventory.clear();
        class_1262.method_5429((class_2487)nbt, this.inventory);
        this.previousYawOffset = this.yawOffset = class_3532.method_15363((float)nbt.method_10583("YawOffset"), (float)-90.0f, (float)90.0f);
        this.targetYawOffset = nbt.method_10545("TargetYawOffset") ? class_3532.method_15363((float)nbt.method_10583("TargetYawOffset"), (float)-90.0f, (float)90.0f) : this.yawOffset;
        this.previousPitch = this.pitch = class_3532.method_15363((float)nbt.method_10583("Pitch"), (float)-5.0f, (float)45.0f);
        this.ignitionTicksRemaining = class_3532.method_15340((int)nbt.method_10550("IgnitionTicks"), (int)0, (int)30);
        this.recoilTicksRemaining = class_3532.method_15340((int)nbt.method_10550("RecoilTicks"), (int)0, (int)12);
        this.targetPitch = nbt.method_10545("TargetPitch") ? class_3532.method_15363((float)nbt.method_10583("TargetPitch"), (float)-5.0f, (float)45.0f) : this.pitch;
    }

    public class_2487 method_16887() {
        return this.method_38244();
    }

    @Nullable
    public class_2596<class_2602> method_38235() {
        return class_2622.method_38585((class_2586)this);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animationCache;
    }

    @Override
    public void tickServer(class_1937 world, class_2338 pos, class_2680 state) {
        StationaryCannonBlockEntity.serverTick(world, pos, state, this);
    }
}

