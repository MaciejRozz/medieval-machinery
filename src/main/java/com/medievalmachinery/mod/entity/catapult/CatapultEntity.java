/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1657
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2246
 *  net.minecraft.class_2388
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2561
 *  net.minecraft.class_2940
 *  net.minecraft.class_2941
 *  net.minecraft.class_2943
 *  net.minecraft.class_2945
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3417
 *  net.minecraft.class_3532
 */
package com.medievalmachinery.mod.entity.catapult;

import com.medievalmachinery.mod.catapult.CatapultAmmunition;
import com.medievalmachinery.mod.catapult.CatapultAnchors;
import com.medievalmachinery.mod.entity.catapult.CatapultStoneEntity;
import com.medievalmachinery.mod.entity.machine.AbstractMedievalMachineEntity;
import com.medievalmachinery.mod.item.MachineSpannerItem;
import com.medievalmachinery.mod.machine.control.MachineControlSync;
import com.medievalmachinery.mod.machine.core.MachineAimSolution;
import com.medievalmachinery.mod.machine.core.MachineCombatTarget;
import com.medievalmachinery.mod.machine.core.MachineCrewAction;
import com.medievalmachinery.mod.machine.core.MachineCrewResourceAccess;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.machine.core.MachineCrewSupplyNeed;
import com.medievalmachinery.mod.machine.core.MachineCrewWorkContext;
import com.medievalmachinery.mod.machine.core.MachineTargetCategory;
import com.medievalmachinery.mod.machine.core.MachineTargetProfile;
import com.medievalmachinery.mod.registry.ModItems;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2388;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2561;
import net.minecraft.class_2940;
import net.minecraft.class_2941;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3417;
import net.minecraft.class_3532;

public final class CatapultEntity
extends AbstractMedievalMachineEntity {
    public static final int MAX_HEALTH = 200;
    public static final float MIN_TENSION = 0.0f;
    public static final float MAX_TENSION = 100.0f;
    private static final float TENSION_PER_SCROLL = 2.0f;
    private static final int RELEASE_TICKS = 18;
    private static final int CLIENT_WINDING_HOLD_TICKS = 8;
    private static final int NPC_CATAPULT_AMMO_WORKING_STOCK = 16;
    private static final class_2940<Float> TENSION = class_2945.method_12791(CatapultEntity.class, (class_2941)class_2943.field_13320);
    private static final class_2940<class_1799> PAYLOAD = class_2945.method_12791(CatapultEntity.class, (class_2941)class_2943.field_13322);
    private static final class_2940<Integer> RELEASE = class_2945.method_12791(CatapultEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Float> RELEASE_START_TENSION = class_2945.method_12791(CatapultEntity.class, (class_2941)class_2943.field_13320);
    private boolean npcLoadedPayload;
    private boolean npcVirtualPayload;
    private int clientWindingTicks;
    private class_1799 pendingLaunchPayload = class_1799.field_8037;
    private float pendingLaunchPower;

    public CatapultEntity(class_1299<? extends CatapultEntity> type, class_1937 world) {
        super(type, world);
    }

    @Override
    public boolean medievalMachinery$supportsCrewRole(MachineCrewRole role) {
        return role == MachineCrewRole.OPERATOR || role == MachineCrewRole.LOADER || role == MachineCrewRole.MAINTENANCE;
    }

    @Override
    public MachineTargetProfile medievalMachinery$getTargetProfile() {
        return MachineTargetProfile.of(MachineTargetCategory.FORTIFICATIONS, MachineTargetCategory.SIEGE_WEAPONS, MachineTargetCategory.LARGE_TARGETS, MachineTargetCategory.INFANTRY);
    }

    @Override
    public double medievalMachinery$getNpcTargetingRange() {
        class_1799 ammunition = this.getLoadedAmmunition().method_7960() ? new class_1799((class_1935)class_1802.field_20412) : this.getLoadedAmmunition();
        return CatapultEntity.calculateMaximumNpcRange(CatapultAmmunition.resolve(ammunition, this.method_37908(), this.method_24515()));
    }

    private static double calculateMaximumNpcRange(CatapultAmmunition.Profile profile) {
        double speed = 2.4 * (double)profile.velocityMultiplier();
        double angle = Math.toRadians(43.0);
        double vx = Math.cos(angle) * speed;
        double vy = Math.sin(angle) * speed;
        double x = 0.0;
        double y = 0.0;
        double drag = Math.max(0.0, 1.0 - (double)profile.drag());
        for (int tick = 0; tick < 220; ++tick) {
            x += vx;
            vx *= drag;
            vy = vy * drag - 0.05 * (double)profile.gravityMultiplier();
            if ((y += vy) <= 0.0 && tick > 1) break;
        }
        return Math.max(16.0, x + 2.0);
    }

    @Override
    public double medievalMachinery$getNpcMinimumTargetingRange() {
        return 8.0;
    }

    @Override
    public boolean medievalMachinery$requiresDirectNpcLineOfSight() {
        return false;
    }

    @Override
    public double medievalMachinery$getNpcImpactSafetyRadius(MachineCombatTarget target) {
        return 4.0;
    }

    @Override
    public class_243 medievalMachinery$getNpcAimOrigin() {
        return this.getLaunchPosition();
    }

    @Override
    public MachineAimSolution medievalMachinery$solveNpcAimAt(class_243 aimPoint, long stableTargetKey, float spreadMultiplier) {
        if (aimPoint == null) {
            return MachineAimSolution.INVALID;
        }
        class_243 origin = this.getLaunchPosition();
        class_243 delta = aimPoint.method_1020(origin);
        double horizontal = Math.hypot(delta.field_1352, delta.field_1350);
        if (horizontal < 1.0) {
            return MachineAimSolution.INVALID;
        }
        float yaw = class_3532.method_15393((float)((float)Math.toDegrees(Math.atan2(delta.field_1352, -delta.field_1350))));
        float bestTension = Float.NaN;
        double bestError = Double.POSITIVE_INFINITY;
        CatapultAmmunition.Profile profile = CatapultAmmunition.resolve(new class_1799((class_1935)class_1802.field_20412), this.method_37908(), this.method_24515());
        block0: for (float tension = 5.0f; tension <= 100.0f; tension += 1.0f) {
            double speed = (0.65 + (double)(tension / 100.0f) * 1.75) * (double)profile.velocityMultiplier();
            double angle = Math.toRadians(43.0);
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            double x = 0.0;
            double y = 0.0;
            double previousX = 0.0;
            double previousY = 0.0;
            for (int tick = 0; tick < 220 && x <= horizontal + 3.0; ++tick) {
                previousX = x;
                previousY = y;
                x += vx;
                y += vy;
                if (x >= horizontal) {
                    double span = Math.max(1.0E-6, x - previousX);
                    double lerp = class_3532.method_15350((double)((horizontal - previousX) / span), (double)0.0, (double)1.0);
                    double atTarget = previousY + (y - previousY) * lerp;
                    double error = Math.abs(atTarget - delta.field_1351);
                    if (!(error < bestError)) continue block0;
                    bestError = error;
                    bestTension = tension;
                    continue block0;
                }
                double drag = Math.max(0.0, 1.0 - (double)profile.drag());
                vx *= drag;
                vy = vy * drag - 0.05 * (double)profile.gravityMultiplier();
            }
        }
        return bestError <= 3.5 && Float.isFinite(bestTension) ? new MachineAimSolution(yaw, bestTension, true) : MachineAimSolution.INVALID;
    }

    @Override
    public List<class_243> medievalMachinery$getNpcProjectilePath(MachineCombatTarget target, MachineAimSolution solution) {
        if (target == null || solution == null || !solution.valid()) {
            return List.of();
        }
        class_243 origin = this.getLaunchPosition();
        double yaw = Math.toRadians(solution.yaw());
        double angle = Math.toRadians(43.0);
        CatapultAmmunition.Profile profile = CatapultAmmunition.resolve(new class_1799((class_1935)class_1802.field_20412), this.method_37908(), this.method_24515());
        double speed = (0.65 + (double)(solution.pitch() / 100.0f) * 1.75) * (double)profile.velocityMultiplier();
        class_243 velocity = new class_243(Math.sin(yaw) * Math.cos(angle) * speed, Math.sin(angle) * speed, -Math.cos(yaw) * Math.cos(angle) * speed);
        double targetHorizontal = Math.hypot(target.aimPoint().field_1352 - origin.field_1352, target.aimPoint().field_1350 - origin.field_1350);
        ArrayList<class_243> path = new ArrayList<class_243>();
        class_243 pos = origin;
        path.add(pos);
        for (int tick = 0; tick < 160; ++tick) {
            pos = pos.method_1019(velocity);
            if ((tick & 1) == 0) {
                path.add(pos);
            }
            double drag = Math.max(0.0, 1.0 - (double)profile.drag());
            velocity = velocity.method_1021(drag).method_1031(0.0, -0.05 * (double)profile.gravityMultiplier(), 0.0);
            double travelledHorizontal = Math.hypot(pos.field_1352 - origin.field_1352, pos.field_1350 - origin.field_1350);
            if (travelledHorizontal >= targetHorizontal + 1.5 || pos.field_1351 < origin.field_1351 - 16.0) break;
        }
        return List.copyOf(path);
    }

    @Override
    public class_1799 medievalMachinery$getNpcHeldVisual(class_1297 npc) {
        MachineCrewResourceAccess resources;
        class_1799 preferred;
        if (npc instanceof MachineCrewResourceAccess && !(preferred = (resources = (MachineCrewResourceAccess)npc).medievalMachinery$getPreferredMachineAmmunition()).method_7960() && CatapultAmmunition.isValid(preferred)) {
            return preferred.method_46651(1);
        }
        return new class_1799((class_1935)class_1802.field_20412);
    }

    @Override
    public boolean medievalMachinery$isNpcAimAligned(MachineAimSolution solution) {
        return solution != null && solution.valid() && Math.abs(class_3532.method_15393((float)(this.method_36454() - solution.yaw()))) <= 7.0f && Math.abs(this.getTension() - solution.pitch()) <= 5.0f;
    }

    @Override
    public void medievalMachinery$npcTick(class_1297 npc, float targetYaw, float targetPitch, float forward, float sideways) {
        if (!this.medievalMachinery$isOperatedBy(npc.method_5667()) && !this.medievalMachinery$claim(npc)) {
            return;
        }
        if (this.medievalMachinery$isMobile()) {
            this.medievalMachinery$move(npc.method_5667(), forward, sideways);
        }
        float yawDelta = class_3532.method_15393((float)(targetYaw - this.method_36454()));
        this.method_36456(this.method_36454() + class_3532.method_15363((float)yawDelta, (float)-8.0f, (float)8.0f));
        float tensionDelta = targetPitch - this.getTension();
        this.setTension(this.getTension() + class_3532.method_15363((float)tensionDelta, (float)-6.0f, (float)6.0f));
    }

    @Override
    public MachineCrewAction medievalMachinery$getNpcCrewAction(class_1297 npc, MachineCrewRole role, MachineCrewWorkContext context) {
        if (role == MachineCrewRole.LOADER || role == MachineCrewRole.OPERATOR) {
            if (this.getReleaseTicks() > 0) {
                return MachineCrewAction.IDLE;
            }
            if (this.getLoadedAmmunition().method_7960()) {
                MachineCrewResourceAccess resources;
                class_1799 preferred;
                if (npc instanceof MachineCrewResourceAccess && !(preferred = (resources = (MachineCrewResourceAccess)npc).medievalMachinery$getPreferredMachineAmmunition()).method_7960() && CatapultAmmunition.isValid(preferred) && !preferred.method_31574(class_1802.field_20412) && resources.medievalMachinery$hasMachineResource(preferred.method_7909(), 1)) {
                    return MachineCrewAction.LOAD;
                }
                if (context.mayUseVirtualStandardResources()) {
                    return MachineCrewAction.LOAD;
                }
            }
            if (role == MachineCrewRole.OPERATOR && !this.getLoadedAmmunition().method_7960() && this.getTension() >= 5.0f) {
                return MachineCrewAction.IGNITE;
            }
        }
        return super.medievalMachinery$getNpcCrewAction(npc, role, context);
    }

    @Override
    public List<MachineCrewSupplyNeed> medievalMachinery$getNpcCrewSupplyNeeds(class_1297 npc, MachineCrewRole role, MachineCrewWorkContext context) {
        if (role != MachineCrewRole.LOADER && role != MachineCrewRole.OPERATOR) {
            return List.of();
        }
        if (!(npc instanceof MachineCrewResourceAccess)) {
            return List.of();
        }
        MachineCrewResourceAccess resources = (MachineCrewResourceAccess)npc;
        class_1799 preferred = resources.medievalMachinery$getPreferredMachineAmmunition();
        if (preferred.method_7960() || preferred.method_31574(class_1802.field_20412) || !CatapultAmmunition.isValid(preferred)) {
            return List.of();
        }
        if (!this.getLoadedAmmunition().method_7960() || resources.medievalMachinery$countMachineResource(preferred.method_7909()) >= 1) {
            return List.of();
        }
        return List.of(new MachineCrewSupplyNeed(preferred.method_7909(), 1, 16));
    }

    @Override
    public List<MachineCrewSupplyNeed> medievalMachinery$getNpcCrewSupplyStockNeeds(class_1297 npc, MachineCrewRole role, MachineCrewWorkContext context) {
        if (role != MachineCrewRole.LOADER && role != MachineCrewRole.OPERATOR) {
            return List.of();
        }
        if (!(npc instanceof MachineCrewResourceAccess)) {
            return List.of();
        }
        MachineCrewResourceAccess resources = (MachineCrewResourceAccess)npc;
        class_1799 preferred = resources.medievalMachinery$getPreferredMachineAmmunition();
        if (preferred.method_7960() || preferred.method_31574(class_1802.field_20412) || !CatapultAmmunition.isValid(preferred)) {
            return List.of();
        }
        return List.of(new MachineCrewSupplyNeed(preferred.method_7909(), 1, 16));
    }

    @Override
    public boolean medievalMachinery$performNpcCrewAction(class_1297 npc, MachineCrewRole role, MachineCrewAction action, MachineCrewWorkContext context) {
        if (action == MachineCrewAction.LOAD && (role == MachineCrewRole.LOADER || role == MachineCrewRole.OPERATOR)) {
            MachineCrewResourceAccess resources;
            class_1799 preferred;
            if (!this.getLoadedAmmunition().method_7960()) {
                return false;
            }
            if (npc instanceof MachineCrewResourceAccess && !(preferred = (resources = (MachineCrewResourceAccess)npc).medievalMachinery$getPreferredMachineAmmunition()).method_7960() && !preferred.method_31574(class_1802.field_20412) && CatapultAmmunition.isValid(preferred) && resources.medievalMachinery$consumeMachineResource(preferred.method_7909(), 1)) {
                this.setLoadedAmmunition(preferred.method_46651(1));
                this.npcLoadedPayload = true;
                this.npcVirtualPayload = false;
                this.method_5783(class_3417.field_15197, 0.55f, 0.8f);
                return true;
            }
            if (!context.mayUseVirtualStandardResources()) {
                return false;
            }
            this.setLoadedAmmunition(new class_1799((class_1935)class_1802.field_20412));
            this.npcLoadedPayload = true;
            this.npcVirtualPayload = true;
            this.method_5783(class_3417.field_15197, 0.55f, 0.8f);
            return true;
        }
        if (action == MachineCrewAction.IGNITE && role == MachineCrewRole.OPERATOR) {
            if (this.getLoadedAmmunition().method_7960() || this.getReleaseTicks() > 0 || this.getTension() < 5.0f) {
                return false;
            }
            this.fire(npc.method_5667());
            return this.getReleaseTicks() > 0;
        }
        return super.medievalMachinery$performNpcCrewAction(npc, role, action, context);
    }

    @Override
    protected void initMachineDataTracker() {
        this.field_6011.method_12784(TENSION, (Object)Float.valueOf(0.0f));
        this.field_6011.method_12784(PAYLOAD, (Object)class_1799.field_8037);
        this.field_6011.method_12784(RELEASE, (Object)0);
        this.field_6011.method_12784(RELEASE_START_TENSION, (Object)Float.valueOf(0.0f));
    }

    @Override
    protected void tickMachineServer(class_3218 serverWorld) {
        if (this.getReleaseTicks() > 0) {
            int nextTicks = this.getReleaseTicks() - 1;
            this.setReleaseTicks(nextTicks);
            if (nextTicks == 10 && !this.pendingLaunchPayload.method_7960()) {
                this.launchPendingPayload(serverWorld);
            }
        }
    }

    @Override
    protected void tickMachineClient() {
        if (this.clientWindingTicks > 0) {
            --this.clientWindingTicks;
        }
    }

    public class_1269 method_5688(class_1657 player, class_1268 hand) {
        class_1799 held = player.method_5998(hand);
        if (MachineSpannerItem.tryDismantle(this, player, hand)) {
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (player.method_5715()) {
            if (!this.method_37908().field_9236 && player instanceof class_3222) {
                class_3222 serverPlayer = (class_3222)player;
                if (this.isOperatedBy(player.method_5667())) {
                    this.releaseOperator(player.method_5667());
                    MachineControlSync.notifyStopped(serverPlayer, this);
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.catapult.aiming_stopped"), true);
                } else if (this.tryClaimOperator(player.method_5667())) {
                    MachineControlSync.notifyStarted(serverPlayer, this);
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.catapult.aiming_mode"), true);
                } else {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.catapult.in_use"), true);
                }
            }
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (this.getReleaseTicks() > 0) {
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (CatapultAmmunition.isValid(held)) {
            if (!this.method_37908().field_9236) {
                if (this.getLoadedAmmunition().method_7960()) {
                    this.setLoadedAmmunition(held.method_46651(1));
                    this.npcLoadedPayload = false;
                    this.npcVirtualPayload = false;
                    if (!player.method_31549().field_7477) {
                        held.method_7934(1);
                    }
                    this.method_5783(class_3417.field_15197, 0.5f, 0.8f);
                    player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.catapult.loaded_item", (Object[])new Object[]{this.getLoadedAmmunition().method_7964()}), true);
                } else {
                    player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.catapult.already_loaded", (Object[])new Object[]{this.getLoadedAmmunition().method_7964()}), true);
                }
            }
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (held.method_31574(class_1802.field_8620)) {
            if (this.getHealth() >= 200) {
                return class_1269.method_29236((boolean)this.method_37908().field_9236);
            }
            if (!this.method_37908().field_9236) {
                this.repair(20);
                if (!player.method_31549().field_7477) {
                    held.method_7934(1);
                }
                this.method_5783(class_3417.field_21077, 1.0f, 1.0f);
            }
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (held.method_7960() && !this.method_37908().field_9236) {
            if (!this.getLoadedAmmunition().method_7960() && this.npcLoadedPayload) {
                return class_1269.field_21466;
            }
            if (!this.getLoadedAmmunition().method_7960()) {
                class_1799 removed = this.getLoadedAmmunition().method_7972();
                this.setLoadedAmmunition(class_1799.field_8037);
                if (!player.method_31548().method_7394(removed)) {
                    player.method_7328(removed, false);
                }
                this.method_5783(class_3417.field_15197, 0.5f, 1.15f);
                player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.catapult.unloaded", (Object[])new Object[]{removed.method_7964()}), true);
            } else {
                player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.catapult.status_empty", (Object[])new Object[]{Math.round(this.getTension())}), true);
            }
            return class_1269.field_21466;
        }
        return class_1269.field_5811;
    }

    public void adjustTension(UUID operator, float scrollDirection) {
        if (!this.isOperatedBy(operator) || this.getReleaseTicks() > 0) {
            return;
        }
        float old = this.getTension();
        this.setTension(old + Math.signum(scrollDirection) * 2.0f);
        if ((int)(old / 10.0f) != (int)(this.getTension() / 10.0f)) {
            this.method_5783(class_3417.field_14674, 0.45f, 0.75f + this.getTension() / 250.0f);
        }
    }

    public void fire(UUID operator) {
        class_3222 serverPlayer;
        class_3222 player;
        if (!this.isOperatedBy(operator) || this.getReleaseTicks() > 0) {
            return;
        }
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_1657 foundPlayer = serverWorld.method_18470(operator);
        class_3222 class_32222 = player = foundPlayer instanceof class_3222 ? (serverPlayer = (class_3222)foundPlayer) : null;
        if (this.getLoadedAmmunition().method_7960()) {
            if (player != null) {
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.catapult.fire_no_ammunition"), true);
            }
            return;
        }
        if (this.getTension() < 5.0f) {
            if (player != null) {
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.catapult.fire_not_tensioned"), true);
            }
            return;
        }
        this.pendingLaunchPower = this.getTension() / 100.0f;
        this.pendingLaunchPayload = this.getLoadedAmmunition().method_46651(1);
        this.field_6011.method_12778(RELEASE_START_TENSION, (Object)Float.valueOf(this.getTension()));
        this.setReleaseTicks(18);
        this.setTension(0.0f);
        this.method_5783(class_3417.field_14649, 1.2f, 0.65f);
        serverWorld.method_14199((class_2394)new class_2388(class_2398.field_11217, class_2246.field_10566.method_9564()), this.method_23317(), this.method_23318() + 0.2, this.method_23321(), 24, 0.9, 0.15, 0.9, 0.08);
    }

    private void launchPendingPayload(class_3218 serverWorld) {
        class_1799 payload = this.pendingLaunchPayload.method_46651(1);
        this.pendingLaunchPayload = class_1799.field_8037;
        this.setLoadedAmmunition(class_1799.field_8037);
        this.npcLoadedPayload = false;
        this.npcVirtualPayload = false;
        class_243 direction = this.getLaunchDirection();
        class_243 origin = this.getLaunchPosition();
        CatapultAmmunition.Profile profile = CatapultAmmunition.resolve(payload, (class_1937)serverWorld, this.method_24515());
        CatapultStoneEntity stone = new CatapultStoneEntity((class_1937)serverWorld, origin.field_1352, origin.field_1351, origin.field_1350, payload);
        stone.method_7432(this);
        stone.method_18799(direction.method_1021((0.65 + (double)this.pendingLaunchPower * 1.75) * (double)profile.velocityMultiplier()));
        serverWorld.method_8649((class_1297)stone);
        this.pendingLaunchPower = 0.0f;
    }

    public class_243 getLaunchDirection() {
        double yaw = Math.toRadians(this.method_36454());
        double pitch = Math.toRadians(43.0);
        double horizontal = Math.cos(pitch);
        return new class_243(Math.sin(yaw) * horizontal, Math.sin(pitch), -Math.cos(yaw) * horizontal).method_1029();
    }

    public class_243 getLaunchPosition() {
        return CatapultAnchors.launchWorld(this);
    }

    public float getTension() {
        return ((Float)this.field_6011.method_12789(TENSION)).floatValue();
    }

    public void setTension(float value) {
        float clamped = class_3532.method_15363((float)value, (float)0.0f, (float)100.0f);
        if (this.method_37908().field_9236 && this.getReleaseTicks() <= 0 && Math.abs(clamped - this.getTension()) > 0.001f) {
            this.clientWindingTicks = 8;
        }
        this.field_6011.method_12778(TENSION, (Object)Float.valueOf(clamped));
    }

    public boolean isWinchActive() {
        return this.getReleaseTicks() > 0 || this.clientWindingTicks > 0;
    }

    public float getReleaseStartTension() {
        return ((Float)this.field_6011.method_12789(RELEASE_START_TENSION)).floatValue();
    }

    @Override
    public boolean medievalMachinery$canPlayerTakeMachineSlot(int slot) {
        return !this.npcLoadedPayload;
    }

    @Override
    public void medievalMachinery$onNpcAssignmentCleared(class_1297 npc) {
        if (this.npcLoadedPayload && !this.getLoadedAmmunition().method_7960()) {
            class_1799 payload;
            MachineCrewResourceAccess resources;
            int stored;
            if (!this.npcVirtualPayload && npc instanceof MachineCrewResourceAccess && (stored = (resources = (MachineCrewResourceAccess)npc).medievalMachinery$storeMachineResource((payload = this.getLoadedAmmunition()).method_7909(), 1)) < 1) {
                npc.method_5775(payload.method_46651(1));
            }
            this.setLoadedAmmunition(class_1799.field_8037);
        }
        this.npcLoadedPayload = false;
        this.npcVirtualPayload = false;
    }

    public class_1799 getLoadedAmmunition() {
        return (class_1799)this.field_6011.method_12789(PAYLOAD);
    }

    public void setLoadedAmmunition(class_1799 stack) {
        this.field_6011.method_12778(PAYLOAD, (Object)stack.method_46651(stack.method_7960() ? 0 : 1));
    }

    public boolean hasLoadedAmmunition() {
        return !this.getLoadedAmmunition().method_7960();
    }

    public int getReleaseTicks() {
        return (Integer)this.field_6011.method_12789(RELEASE);
    }

    private void setReleaseTicks(int value) {
        this.field_6011.method_12778(RELEASE, (Object)Math.max(0, value));
    }

    public float getArmAngleDegrees(float tickDelta) {
        if (this.getReleaseTicks() <= 0) {
            return class_3532.method_16439((float)(this.getTension() / 100.0f), (float)-68.0f, (float)0.0f);
        }
        float elapsed = (float)(18 - this.getReleaseTicks()) + tickDelta;
        float startAngle = class_3532.method_16439((float)(this.getReleaseStartTension() / 100.0f), (float)-68.0f, (float)0.0f);
        if (elapsed < 8.0f) {
            return class_3532.method_16439((float)(elapsed / 8.0f), (float)startAngle, (float)-68.0f);
        }
        if (elapsed < 12.0f) {
            return class_3532.method_16439((float)((elapsed - 8.0f) / 4.0f), (float)-68.0f, (float)-61.0f);
        }
        return class_3532.method_16439((float)class_3532.method_15363((float)((elapsed - 12.0f) / 6.0f), (float)0.0f, (float)1.0f), (float)-61.0f, (float)-68.0f);
    }

    public float getPhysicalArmAngleDegrees(float tickDelta) {
        return this.getArmAngleDegrees(tickDelta);
    }

    public float getRuntimeArmRotationDegrees(float tickDelta) {
        return -this.getArmAngleDegrees(tickDelta);
    }

    public float getWinchRotationDegrees(float tickDelta) {
        if (this.getReleaseTicks() <= 0) {
            return this.getTension() * 3.6f;
        }
        float elapsed = (float)(18 - this.getReleaseTicks()) + tickDelta;
        float release = class_3532.method_15363((float)(elapsed / 8.0f), (float)0.0f, (float)1.0f);
        return this.getReleaseStartTension() * 3.6f * (1.0f - release);
    }

    public float getCrankAngleDegrees(float tickDelta) {
        if (this.getReleaseTicks() > 0) {
            return 30.0f;
        }
        if (this.clientWindingTicks <= 0) {
            return 0.0f;
        }
        return 30.0f * class_3532.method_15363((float)((float)this.clientWindingTicks / 8.0f), (float)0.0f, (float)1.0f);
    }

    public float getBrakeAngleDegrees(float tickDelta) {
        return this.isWinchActive() ? 24.0f : 0.0f;
    }

    @Override
    public void loadFromItem(class_2487 nbt) {
        super.loadFromItem(nbt);
        if (nbt.method_10573("CatapultPayload", 10)) {
            this.setLoadedAmmunition(class_1799.method_7915((class_2487)nbt.method_10562("CatapultPayload")));
        }
        if (nbt.method_10545("CatapultTension")) {
            this.setTension(nbt.method_10583("CatapultTension"));
        }
    }

    @Override
    protected class_1799 createDroppedStack() {
        class_1799 stack = new class_1799((class_1935)ModItems.CATAPULT);
        if (this.getHealth() != 200 || !this.getLoadedAmmunition().method_7960() || this.getTension() > 0.001f) {
            class_2487 nbt = stack.method_7948();
            if (this.getHealth() != 200) {
                nbt.method_10569("Health", this.getHealth());
            }
            if (!this.getLoadedAmmunition().method_7960()) {
                nbt.method_10566("CatapultPayload", (class_2520)this.getLoadedAmmunition().method_7953(new class_2487()));
            }
            if (this.getTension() > 0.001f) {
                nbt.method_10548("CatapultTension", this.getTension());
            }
        }
        return stack;
    }

    @Override
    protected void applyAimTarget(float absoluteYaw, float pitch) {
    }

    @Override
    protected float getDriveDirectionSign() {
        return -1.0f;
    }

    @Override
    protected float getSteeringSpeed() {
        return 0.45f;
    }

    @Override
    protected void readMachineNbt(class_2487 nbt) {
        this.setTension(nbt.method_10583("CatapultTension"));
        if (nbt.method_10573("CatapultPayload", 10)) {
            this.setLoadedAmmunition(class_1799.method_7915((class_2487)nbt.method_10562("CatapultPayload")));
        } else if (nbt.method_10550("Cobblestone") > 0) {
            this.setLoadedAmmunition(new class_1799((class_1935)class_1802.field_20412));
        }
        this.npcLoadedPayload = nbt.method_10577("NpcLoadedPayload");
        this.npcVirtualPayload = nbt.method_10577("NpcVirtualPayload");
        this.setReleaseTicks(nbt.method_10550("ReleaseTicks"));
        this.field_6011.method_12778(RELEASE_START_TENSION, (Object)Float.valueOf(nbt.method_10583("ReleaseStartTension")));
    }

    @Override
    protected void writeMachineNbt(class_2487 nbt) {
        nbt.method_10548("CatapultTension", this.getTension());
        if (!this.getLoadedAmmunition().method_7960()) {
            nbt.method_10566("CatapultPayload", (class_2520)this.getLoadedAmmunition().method_7953(new class_2487()));
        }
        if (this.npcLoadedPayload) {
            nbt.method_10556("NpcLoadedPayload", true);
        }
        if (this.npcVirtualPayload) {
            nbt.method_10556("NpcVirtualPayload", true);
        }
        nbt.method_10569("ReleaseTicks", this.getReleaseTicks());
        nbt.method_10548("ReleaseStartTension", this.getReleaseStartTension());
    }

    @Override
    public double medievalMachinery$operatorRearDistance() {
        return -2.85;
    }

    @Override
    public double medievalMachinery$operatorSideOffset() {
        return -0.75;
    }

    @Override
    protected double getOperatorRearOffset() {
        return -2.85;
    }

    @Override
    protected double getOperatorSideOffset() {
        return -0.75;
    }

    @Override
    protected double getOperatorHeightOffset() {
        return 0.1;
    }

    @Override
    protected float getOperatorBodyYawOffset() {
        return 180.0f;
    }

    @Override
    public float medievalMachinery$getNpcFiringLookYaw(MachineAimSolution solution) {
        return solution != null && solution.valid() ? class_3532.method_15393((float)(solution.yaw() + 180.0f)) : this.medievalMachinery$operatorLookYaw();
    }
}

