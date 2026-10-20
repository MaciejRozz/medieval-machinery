/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1282
 *  net.minecraft.class_1297
 *  net.minecraft.class_1297$class_4738
 *  net.minecraft.class_1299
 *  net.minecraft.class_1309
 *  net.minecraft.class_1313
 *  net.minecraft.class_1657
 *  net.minecraft.class_1665
 *  net.minecraft.class_1676
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_2338$class_2339
 *  net.minecraft.class_2388
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2596
 *  net.minecraft.class_2602
 *  net.minecraft.class_2604
 *  net.minecraft.class_2940
 *  net.minecraft.class_2941
 *  net.minecraft.class_2943
 *  net.minecraft.class_2945
 *  net.minecraft.class_3218
 *  net.minecraft.class_3417
 *  net.minecraft.class_3532
 *  net.minecraft.class_4050
 *  net.minecraft.class_8103
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.medievalmachinery.mod.entity.machine;

import com.medievalmachinery.mod.entity.cannon.CannonballEntity;
import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.machine.control.MedievalMachineryOperator;
import com.medievalmachinery.mod.machine.core.MachineCrewAction;
import com.medievalmachinery.mod.machine.core.MachineCrewResourceAccess;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.machine.core.MachineCrewSupplyNeed;
import com.medievalmachinery.mod.machine.core.MachineCrewWorkContext;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineSupplyBatchPolicy;
import com.medievalmachinery.mod.registry.ModItems;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1313;
import net.minecraft.class_1657;
import net.minecraft.class_1665;
import net.minecraft.class_1676;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2388;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2596;
import net.minecraft.class_2602;
import net.minecraft.class_2604;
import net.minecraft.class_2940;
import net.minecraft.class_2941;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
import net.minecraft.class_3417;
import net.minecraft.class_3532;
import net.minecraft.class_4050;
import net.minecraft.class_8103;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AbstractMedievalMachineEntity
extends class_1297
implements GeoEntity,
MedievalMachineryOperable {
    public static final int DEFAULT_MAX_HEALTH = 200;
    public static final int REPAIR_PER_IRON_INGOT = 20;
    private static final class_2940<Integer> HEALTH = class_2945.method_12791(AbstractMedievalMachineEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Float> BASE_YAW = class_2945.method_12791(AbstractMedievalMachineEntity.class, (class_2941)class_2943.field_13320);
    private static final class_2940<Float> WHEEL_ROTATION = class_2945.method_12791(AbstractMedievalMachineEntity.class, (class_2941)class_2943.field_13320);
    private final MedievalMachineryOperator operator = new MedievalMachineryOperator();
    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private boolean controlInputQueued;
    private float queuedForward;
    private float queuedSideways;
    private float controlForward;
    private float controlSideways;
    private int controlTimeoutTicks;
    private float driveSpeed;
    private boolean aimInputQueued;
    private float queuedAimYaw;
    private float queuedAimPitch;
    private int lastOrdinaryProjectileDamageTick = Integer.MIN_VALUE;
    private int lastOrdinaryProjectileEntityId = Integer.MIN_VALUE;
    private float clientVisualYaw;
    private float previousClientVisualYaw;
    private boolean clientVisualYawInitialized;
    private int clientSteeringPredictionTicks;

    protected AbstractMedievalMachineEntity(class_1299<? extends AbstractMedievalMachineEntity> type, class_1937 world) {
        super(type, world);
        this.method_49477(1.05f);
    }

    protected void method_5693() {
        this.field_6011.method_12784(HEALTH, (Object)this.getMaximumHealth());
        this.field_6011.method_12784(BASE_YAW, (Object)Float.valueOf(0.0f));
        this.field_6011.method_12784(WHEEL_ROTATION, (Object)Float.valueOf(0.0f));
        this.initMachineDataTracker();
    }

    protected void initMachineDataTracker() {
    }

    public void method_5773() {
        super.method_5773();
        if (this.method_37908().field_9236) {
            this.tickClientInterpolation();
            this.tickMachineClient();
            return;
        }
        this.ensureOperatorPassenger();
        this.applyQueuedControlInput();
        if (!this.method_5740()) {
            this.method_18799(this.method_18798().method_1031(0.0, -0.08, 0.0));
        }
        this.method_5784(class_1313.field_6308, this.method_18798());
        this.method_18799(this.method_18798().method_18805(0.78, 0.98, 0.78));
        if (this.method_24828()) {
            this.method_18799(this.method_18798().method_18805(0.55, -0.35, 0.55));
        }
        this.tickMachineServer((class_3218)this.method_37908());
    }

    protected void tickMachineClient() {
    }

    protected abstract class_1799 createDroppedStack();

    public final class_1799 createDismantledStack(float healthFraction) {
        int wear = Math.max(1, Math.round((float)this.getMaximumHealth() * Math.max(0.0f, healthFraction)));
        this.setHealth(Math.max(1, this.getHealth() - wear));
        return this.createDroppedStack();
    }

    protected void tickMachineServer(class_3218 world) {
    }

    @Override
    public MachineCrewAction medievalMachinery$getNpcCrewAction(class_1297 npc, MachineCrewRole role, MachineCrewWorkContext context) {
        if (role == MachineCrewRole.MAINTENANCE && this.medievalMachinery$needsNpcMaintenance()) {
            return MachineCrewAction.MAINTAIN;
        }
        return MedievalMachineryOperable.super.medievalMachinery$getNpcCrewAction(npc, role, context);
    }

    @Override
    public List<MachineCrewSupplyNeed> medievalMachinery$getNpcCrewSupplyNeeds(class_1297 npc, MachineCrewRole role, MachineCrewWorkContext context) {
        if (role != MachineCrewRole.MAINTENANCE || !this.medievalMachinery$needsNpcMaintenance()) {
            return List.of();
        }
        if (!(npc instanceof MachineCrewResourceAccess)) {
            return List.of();
        }
        MachineCrewResourceAccess resources = (MachineCrewResourceAccess)npc;
        int carried = resources.medievalMachinery$countMachineResource(class_1802.field_8620);
        if (carried >= 1) {
            return List.of();
        }
        int missing = Math.max(0, this.getMaximumHealth() - this.getHealth());
        int required = Math.max(1, (missing + 20 - 1) / 20);
        int desired = Math.min(NpcMachineSupplyBatchPolicy.maintenanceIngots(), required);
        return List.of(new MachineCrewSupplyNeed(class_1802.field_8620, 1, desired));
    }

    @Override
    public boolean medievalMachinery$performNpcCrewAction(class_1297 npc, MachineCrewRole role, MachineCrewAction action, MachineCrewWorkContext context) {
        if (role == MachineCrewRole.MAINTENANCE && action == MachineCrewAction.MAINTAIN) {
            MachineCrewResourceAccess resources;
            if (!this.medievalMachinery$needsNpcMaintenance()) {
                return false;
            }
            if (!(npc instanceof MachineCrewResourceAccess) || !(resources = (MachineCrewResourceAccess)npc).medievalMachinery$consumeMachineResource(class_1802.field_8620, 1)) {
                return false;
            }
            int repaired = this.repair(20);
            if (repaired <= 0) {
                return false;
            }
            this.method_5783(class_3417.field_21077, 0.85f, 1.05f);
            class_1937 class_19372 = this.method_37908();
            if (class_19372 instanceof class_3218) {
                class_3218 serverWorld = (class_3218)class_19372;
                serverWorld.method_14199((class_2394)class_2398.field_29642, this.method_23317(), this.method_23323(0.55), this.method_23321(), 8, 0.35, 0.25, 0.35, 0.04);
            }
            return true;
        }
        return MedievalMachineryOperable.super.medievalMachinery$performNpcCrewAction(npc, role, action, context);
    }

    protected boolean medievalMachinery$needsNpcMaintenance() {
        return this.getHealth() < Math.max(1, (int)Math.floor((double)this.getMaximumHealth() * 0.9));
    }

    private void tickClientInterpolation() {
        if (!this.clientVisualYawInitialized) {
            this.previousClientVisualYaw = this.clientVisualYaw = this.method_36454();
            this.clientVisualYawInitialized = true;
        }
        this.previousClientVisualYaw = this.clientVisualYaw;
        if (this.clientSteeringPredictionTicks > 0) {
            --this.clientSteeringPredictionTicks;
        } else {
            this.clientVisualYaw = class_3532.method_17821((float)0.12f, (float)this.clientVisualYaw, (float)this.method_36454());
        }
    }

    public final void setAimTarget(UUID operatorUuid, float absoluteYaw, float pitch) {
        if (!this.isOperatedBy(operatorUuid)) {
            return;
        }
        this.queuedAimYaw = class_3532.method_15393((float)absoluteYaw);
        this.queuedAimPitch = this.clampAimPitch(pitch);
        this.aimInputQueued = true;
    }

    protected float clampAimPitch(float pitch) {
        return pitch;
    }

    protected abstract void applyAimTarget(float var1, float var2);

    private void queueOperatorMovement(UUID operatorUuid, float forward, float sideways) {
        if (!this.isOperatedBy(operatorUuid)) {
            return;
        }
        this.queuedForward = class_3532.method_15363((float)forward, (float)-1.0f, (float)1.0f);
        this.queuedSideways = class_3532.method_15363((float)sideways, (float)-1.0f, (float)1.0f);
        this.controlInputQueued = true;
    }

    private void applyQueuedControlInput() {
        if (this.aimInputQueued) {
            this.applyAimTarget(this.queuedAimYaw, this.queuedAimPitch);
            this.aimInputQueued = false;
        }
        if (this.controlInputQueued) {
            this.controlForward = this.queuedForward;
            this.controlSideways = this.queuedSideways;
            this.controlTimeoutTicks = 20;
            this.controlInputQueued = false;
        } else if (this.controlTimeoutTicks > 0) {
            --this.controlTimeoutTicks;
        } else {
            this.controlForward = 0.0f;
            this.controlSideways = 0.0f;
        }
        UUID operatorUuid = this.operator.getUuid();
        if (operatorUuid != null) {
            this.applyOperatorMovement(operatorUuid, this.controlForward, this.controlSideways);
        }
    }

    public void applyOperatorMovement(UUID operatorUuid, float forwardInput, float sidewaysInput) {
        if (!this.isOperatedBy(operatorUuid)) {
            return;
        }
        float forward = class_3532.method_15363((float)forwardInput, (float)-1.0f, (float)1.0f) * this.getDriveDirectionSign();
        float turn = class_3532.method_15363((float)sidewaysInput, (float)-1.0f, (float)1.0f);
        if (Math.abs(turn) >= 0.01f) {
            this.field_5982 = this.method_36454();
            float newYaw = class_3532.method_15393((float)(this.method_36454() + turn * this.getSteeringSpeed()));
            this.method_36456(newYaw);
            this.setBaseYaw(newYaw);
        }
        if (Math.abs(forward) <= 0.01f) {
            this.driveSpeed = 0.0f;
        } else {
            this.driveSpeed = AbstractMedievalMachineEntity.approachZero(this.driveSpeed, 0.005f);
            this.driveSpeed = forward > 0.01f ? Math.min(this.getMaximumDriveSpeed(), this.driveSpeed + 0.01f) : Math.max(-this.getMaximumDriveSpeed(), this.driveSpeed - 0.01f);
        }
        double yaw = Math.toRadians(this.method_36454());
        double motionX = -Math.sin(yaw) * (double)this.driveSpeed;
        double motionZ = Math.cos(yaw) * (double)this.driveSpeed;
        class_243 horizontalMotion = new class_243(motionX, 0.0, motionZ);
        this.addWheelTravel(horizontalMotion);
        this.method_18800(motionX, this.method_18798().field_1351, motionZ);
        this.field_6007 = true;
    }

    protected float getDriveDirectionSign() {
        return 1.0f;
    }

    protected float getMaximumDriveSpeed() {
        return 0.09f;
    }

    protected float getSteeringSpeed() {
        return 0.5f;
    }

    private static float approachZero(float value, float amount) {
        if (value > 0.0f) {
            return Math.max(0.0f, value - amount);
        }
        if (value < 0.0f) {
            return Math.min(0.0f, value + amount);
        }
        return 0.0f;
    }

    private void addWheelTravel(class_243 movement) {
        double distance = Math.sqrt(movement.field_1352 * movement.field_1352 + movement.field_1350 * movement.field_1350);
        if (distance < 1.0E-5) {
            return;
        }
        class_243 forward = new class_243(-Math.sin(Math.toRadians(this.method_36454())), 0.0, Math.cos(Math.toRadians(this.method_36454())));
        float sign = movement.method_1026(forward) < 0.0 ? -1.0f : 1.0f;
        this.setWheelRotation(this.getWheelRotation() + (float)Math.toDegrees(distance / 0.72) * sign * this.getWheelRotationMultiplier());
    }

    protected float getWheelRotationMultiplier() {
        return 0.45f;
    }

    public boolean tryClaimOperator(UUID playerUuid) {
        class_1297 class_12972;
        class_1937 class_19372 = this.method_37908();
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            class_12972 = serverWorld.method_14190(playerUuid);
        } else {
            class_12972 = null;
        }
        class_1297 entity = class_12972;
        return entity != null && this.tryClaimOperator(entity);
    }

    public boolean tryClaimOperator(class_1297 entity) {
        if (entity == null || !this.operator.tryClaim(entity)) {
            return false;
        }
        float machineYaw = class_3532.method_15393((float)(this.method_36454() + this.getOperatorBodyYawOffset()));
        entity.method_36456(machineYaw);
        entity.method_36457(0.0f);
        if (entity instanceof class_1309) {
            class_1309 living = (class_1309)entity;
            living.method_5847(machineYaw);
            living.method_5636(machineYaw);
        }
        if (entity instanceof MedievalSoldierEntity) {
            if (entity.method_5765()) {
                entity.method_5848();
            }
            return true;
        }
        if (entity.method_5854() != this && !entity.method_5873((class_1297)this, true)) {
            this.operator.release(entity.method_5667());
            return false;
        }
        return entity.method_5854() == this;
    }

    private void ensureOperatorPassenger() {
        class_1937 class_19372;
        UUID operatorUuid = this.operator.getUuid();
        if (operatorUuid == null || !((class_19372 = this.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_1297 operatorEntity = serverWorld.method_14190(operatorUuid);
        if (operatorEntity == null || operatorEntity.method_31481()) {
            this.operator.forceRelease();
            this.clearVehicleControls();
            return;
        }
        if (operatorEntity instanceof MedievalSoldierEntity) {
            MedievalSoldierEntity soldier = (MedievalSoldierEntity)operatorEntity;
            if (!soldier.medievalMachinery$isAssignedTo(this.method_5667(), MachineCrewRole.OPERATOR)) {
                this.operator.forceRelease();
                this.clearVehicleControls();
                return;
            }
            if (operatorEntity.method_5765()) {
                operatorEntity.method_5848();
            }
            return;
        }
        if (operatorEntity.method_5854() != this && !operatorEntity.method_5873((class_1297)this, true)) {
            this.operator.forceRelease();
            this.clearVehicleControls();
        }
    }

    public boolean releaseOperator(UUID playerUuid) {
        if (!this.operator.isOperatedBy(playerUuid)) {
            return false;
        }
        for (class_1297 passenger : this.method_5685()) {
            if (!passenger.method_5667().equals(playerUuid)) continue;
            passenger.method_5848();
        }
        this.clearVehicleControls();
        return this.operator.release(playerUuid);
    }

    public boolean isOperatedBy(UUID playerUuid) {
        return this.operator.isOperatedBy(playerUuid);
    }

    protected final void clearVehicleControls() {
        this.controlInputQueued = false;
        this.aimInputQueued = false;
        this.controlForward = 0.0f;
        this.controlSideways = 0.0f;
        this.controlTimeoutTicks = 0;
        this.driveSpeed = 0.0f;
        this.method_18800(0.0, this.method_18798().field_1351, 0.0);
    }

    @Override
    public class_1297 asEntity() {
        return this;
    }

    @Override
    public boolean medievalMachinery$claim(class_1297 candidate) {
        return this.tryClaimOperator(candidate);
    }

    @Override
    public boolean medievalMachinery$release(UUID operatorUuid) {
        return this.releaseOperator(operatorUuid);
    }

    @Override
    public MedievalMachineryOperator medievalMachinery$getOperator() {
        return this.operator;
    }

    @Override
    public void medievalMachinery$setAim(UUID operatorUuid, float yaw, float pitch) {
        this.setAimTarget(operatorUuid, yaw, pitch);
    }

    @Override
    public void medievalMachinery$move(UUID operatorUuid, float forward, float sideways) {
        this.queueOperatorMovement(operatorUuid, forward, sideways);
    }

    @Override
    public float medievalMachinery$getNpcForwardInputSign() {
        return this.getDriveDirectionSign();
    }

    public void loadFromItem(class_2487 nbt) {
        if (nbt.method_10545("Health")) {
            this.setHealth(nbt.method_10550("Health"));
        }
    }

    public int getMaximumHealth() {
        return 200;
    }

    public int getHealth() {
        return (Integer)this.field_6011.method_12789(HEALTH);
    }

    public void setHealth(int health) {
        this.field_6011.method_12778(HEALTH, (Object)class_3532.method_15340((int)health, (int)0, (int)this.getMaximumHealth()));
    }

    public int repair(int amount) {
        int old = this.getHealth();
        this.setHealth(old + Math.max(0, amount));
        return this.getHealth() - old;
    }

    public float getBaseYaw() {
        return ((Float)this.field_6011.method_12789(BASE_YAW)).floatValue();
    }

    public void setBaseYaw(float yaw) {
        this.field_6011.method_12778(BASE_YAW, (Object)Float.valueOf(yaw));
    }

    public float getWheelRotation() {
        return ((Float)this.field_6011.method_12789(WHEEL_ROTATION)).floatValue();
    }

    protected void setWheelRotation(float degrees) {
        this.field_6011.method_12778(WHEEL_ROTATION, (Object)Float.valueOf(class_3532.method_15393((float)degrees)));
    }

    public boolean method_5643(class_1282 source, float amount) {
        if (this.method_5679(source) || amount <= 0.0f || this.method_31481()) {
            return false;
        }
        if (this.method_37908().field_9236) {
            return true;
        }
        int damage = this.calculateStructuralDamage(source, amount);
        if (damage <= 0) {
            return false;
        }
        class_1297 class_12972 = source.method_5529();
        if (class_12972 instanceof class_1657) {
            class_1657 player = (class_1657)class_12972;
            if (player.method_31549().field_7477) {
                damage = this.getMaximumHealth();
            }
        }
        this.setHealth(this.getHealth() - damage);
        this.method_5785();
        if (this.getHealth() <= 0) {
            this.onMachineDestroyed();
            this.method_31472();
        } else {
            this.method_5783(class_3417.field_21076, 0.75f, 0.95f);
        }
        return true;
    }

    protected int calculateStructuralDamage(class_1282 source, float amount) {
        class_1297 projectile = source.method_5526();
        if (projectile instanceof CannonballEntity) {
            CannonballEntity cannonball = (CannonballEntity)projectile;
            class_1792 shot = cannonball.getAmmunitionItem();
            if (shot == ModItems.HEAVY_IRON_CANNONBALL) {
                return 28;
            }
            if (shot == ModItems.IRON_CANNONBALL) {
                return 12;
            }
            if (shot == ModItems.TNT_CANNONBALL) {
                return 18;
            }
            return 8;
        }
        if (source.method_48789(class_8103.field_42249)) {
            return class_3532.method_15340((int)class_3532.method_15386((float)(amount * 0.85f)), (int)4, (int)24);
        }
        if (projectile instanceof class_1665) {
            if (projectile.method_5628() == this.lastOrdinaryProjectileEntityId && this.field_6012 - this.lastOrdinaryProjectileDamageTick < 10) {
                return 0;
            }
            this.lastOrdinaryProjectileEntityId = projectile.method_5628();
            this.lastOrdinaryProjectileDamageTick = this.field_6012;
            return 1;
        }
        if (projectile instanceof class_1676) {
            return class_3532.method_15340((int)class_3532.method_15386((float)(amount * 0.15f)), (int)1, (int)6);
        }
        return class_3532.method_15340((int)class_3532.method_15386((float)(amount * 0.65f)), (int)1, (int)20);
    }

    protected void onMachineDestroyed() {
        this.method_5783(class_3417.field_15215, 1.35f, 0.72f);
        this.method_5783(class_3417.field_24065, 1.1f, 0.62f);
        class_1937 class_19372 = this.method_37908();
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            serverWorld.method_14199((class_2394)class_2398.field_11203, this.method_23317(), this.method_23323(0.5), this.method_23321(), 28, 0.9, 0.7, 0.9, 0.08);
            serverWorld.method_14199((class_2394)new class_2388(class_2398.field_11217, class_2246.field_10161.method_9564()), this.method_23317(), this.method_23323(0.45), this.method_23321(), 42, 1.0, 0.65, 1.0, 0.12);
        }
    }

    protected boolean method_5818(class_1297 passenger) {
        return this.method_5685().isEmpty();
    }

    protected void method_5865(class_1297 passenger, class_1297.class_4738 positionUpdater) {
        if (!this.method_5626(passenger)) {
            return;
        }
        double yaw = Math.toRadians(this.method_36454());
        double rear = this.getOperatorRearOffset();
        double side = this.getOperatorSideOffset();
        double x = this.method_23317() + Math.sin(yaw) * rear - Math.cos(yaw) * side;
        double z = this.method_23321() - Math.cos(yaw) * rear - Math.sin(yaw) * side;
        positionUpdater.accept(passenger, x, this.method_23318() + this.getOperatorHeightOffset(), z);
        passenger.method_18380(class_4050.field_18076);
        if (passenger instanceof class_1309) {
            class_1309 living = (class_1309)passenger;
            living.method_5636(class_3532.method_15393((float)(this.method_36454() + this.getOperatorBodyYawOffset())));
        }
    }

    public class_243 method_24829(class_1309 passenger) {
        class_243[] candidates;
        class_243 seat = passenger.method_19538();
        class_243 outward = seat.method_1020(this.method_19538()).method_18805(1.0, 0.0, 1.0);
        if (outward.method_1027() < 1.0E-6) {
            double yaw = Math.toRadians(this.method_36454());
            outward = new class_243(-Math.cos(yaw), 0.0, -Math.sin(yaw));
        } else {
            outward = outward.method_1029();
        }
        for (class_243 candidate : candidates = new class_243[]{new class_243(seat.field_1352, this.method_23318(), seat.field_1350), new class_243(seat.field_1352, this.method_23318(), seat.field_1350).method_1019(outward.method_1021(0.55)), new class_243(seat.field_1352, this.method_23318(), seat.field_1350).method_1019(outward.method_1021(1.1))}) {
            class_2338.class_2339 pos = new class_2338.class_2339(class_3532.method_15357((double)candidate.field_1352), class_3532.method_15357((double)(this.method_23318() + 2.0)), class_3532.method_15357((double)candidate.field_1350));
            int minY = Math.max(this.method_37908().method_31607() + 1, class_3532.method_15357((double)this.method_23318()) - 6);
            while (pos.method_10264() > minY) {
                class_2338 below = pos.method_10074();
                if (this.method_37908().method_8320(below).method_26212((class_1922)this.method_37908(), below) && this.method_37908().method_8320((class_2338)pos).method_26220((class_1922)this.method_37908(), (class_2338)pos).method_1110() && this.method_37908().method_8320(pos.method_10084()).method_26220((class_1922)this.method_37908(), pos.method_10084()).method_1110()) {
                    return new class_243(candidate.field_1352, (double)pos.method_10264(), candidate.field_1350);
                }
                pos.method_10100(0, -1, 0);
            }
        }
        return new class_243(seat.field_1352, Math.max(seat.field_1351, this.method_23318()), seat.field_1350);
    }

    protected double getOperatorRearOffset() {
        return 1.45;
    }

    protected double getOperatorSideOffset() {
        return 0.72;
    }

    protected double getOperatorHeightOffset() {
        return 0.05;
    }

    protected float getOperatorBodyYawOffset() {
        return 0.0f;
    }

    public final float getClientOperatorBodyYawOffset() {
        return this.getOperatorBodyYawOffset();
    }

    @Override
    public double medievalMachinery$operatorRearDistance() {
        return this.getOperatorRearOffset();
    }

    @Override
    public double medievalMachinery$operatorSideOffset() {
        return this.getOperatorSideOffset();
    }

    @Override
    public float medievalMachinery$getCrewStationYaw() {
        return this.medievalMachinery$isMobile() ? this.method_36454() : this.getBaseYaw();
    }

    @Override
    public float medievalMachinery$operatorLookYaw() {
        return class_3532.method_15393((float)(this.method_36454() + this.getOperatorBodyYawOffset()));
    }

    public void predictClientSteering(float sidewaysInput) {
        float turn;
        if (!this.method_37908().field_9236) {
            return;
        }
        if (!this.clientVisualYawInitialized) {
            this.previousClientVisualYaw = this.clientVisualYaw = this.method_36454();
            this.clientVisualYawInitialized = true;
        }
        if (Math.abs(turn = class_3532.method_15363((float)sidewaysInput, (float)-1.0f, (float)1.0f)) < 0.01f) {
            return;
        }
        this.previousClientVisualYaw = this.clientVisualYaw;
        this.clientVisualYaw = class_3532.method_15393((float)(this.clientVisualYaw + turn * this.getSteeringSpeed()));
        this.clientSteeringPredictionTicks = 4;
    }

    public void predictClientAimYaw(float targetYaw) {
        if (!this.method_37908().field_9236) {
            return;
        }
        if (!this.clientVisualYawInitialized) {
            this.previousClientVisualYaw = this.clientVisualYaw = this.method_36454();
            this.clientVisualYawInitialized = true;
        }
        this.previousClientVisualYaw = this.clientVisualYaw;
        this.clientVisualYaw = class_3532.method_17821((float)0.45f, (float)this.clientVisualYaw, (float)targetYaw);
        this.clientSteeringPredictionTicks = 3;
    }

    public float getVisualYaw(float tickDelta) {
        if (!this.method_37908().field_9236 || !this.clientVisualYawInitialized) {
            return this.method_5705(tickDelta);
        }
        return class_3532.method_17821((float)tickDelta, (float)this.previousClientVisualYaw, (float)this.clientVisualYaw);
    }

    public boolean method_5863() {
        return !this.method_31481();
    }

    public boolean method_30948() {
        return true;
    }

    public boolean method_5810() {
        return false;
    }

    protected void method_5749(class_2487 nbt) {
        this.operator.readNbt(nbt);
        this.method_36456(nbt.method_10583("MachineYaw"));
        this.setBaseYaw(nbt.method_10545("BaseYaw") ? nbt.method_10583("BaseYaw") : this.method_36454());
        if (nbt.method_10545("WheelRotation")) {
            this.setWheelRotation(nbt.method_10583("WheelRotation"));
        }
        if (nbt.method_10545("Health")) {
            this.setHealth(nbt.method_10550("Health"));
        }
        this.readMachineNbt(nbt);
    }

    protected void method_5652(class_2487 nbt) {
        this.operator.writeNbt(nbt);
        nbt.method_10548("MachineYaw", this.method_36454());
        nbt.method_10548("BaseYaw", this.getBaseYaw());
        nbt.method_10548("WheelRotation", this.getWheelRotation());
        nbt.method_10569("Health", this.getHealth());
        this.writeMachineNbt(nbt);
    }

    protected void readMachineNbt(class_2487 nbt) {
    }

    protected void writeMachineNbt(class_2487 nbt) {
    }

    public class_2596<class_2602> method_18002() {
        return new class_2604((class_1297)this);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animationCache;
    }
}

