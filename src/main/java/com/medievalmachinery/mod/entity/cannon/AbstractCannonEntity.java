/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2940
 *  net.minecraft.class_2941
 *  net.minecraft.class_2943
 *  net.minecraft.class_2945
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3417
 *  net.minecraft.class_3419
 *  net.minecraft.class_3532
 */
package com.medievalmachinery.mod.entity.cannon;

import com.medievalmachinery.mod.entity.cannon.CannonMenuAccess;
import com.medievalmachinery.mod.entity.cannon.CannonballEntity;
import com.medievalmachinery.mod.entity.machine.AbstractMedievalMachineEntity;
import com.medievalmachinery.mod.item.MachineSpannerItem;
import com.medievalmachinery.mod.machine.cannon.CannonBallistics;
import com.medievalmachinery.mod.machine.core.MachineAimSolution;
import com.medievalmachinery.mod.machine.core.MachineCombatTarget;
import com.medievalmachinery.mod.machine.core.MachineCrewAction;
import com.medievalmachinery.mod.machine.core.MachineCrewResourceAccess;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.machine.core.MachineCrewSupplyNeed;
import com.medievalmachinery.mod.machine.core.MachineCrewWorkContext;
import com.medievalmachinery.mod.machine.core.MachineTargetCategory;
import com.medievalmachinery.mod.machine.core.MachineTargetProfile;
import com.medievalmachinery.mod.platform.LoaderHooks;
import com.medievalmachinery.mod.registry.ModItems;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1263;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2940;
import net.minecraft.class_2941;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3532;

public abstract class AbstractCannonEntity
extends AbstractMedievalMachineEntity
implements class_1263,
CannonMenuAccess {
    private boolean npcLoadedPowder;
    private boolean npcLoadedAmmunition;
    private boolean npcVirtualPowder;
    private boolean npcVirtualAmmunition;
    public static final int MAX_HEALTH = 200;
    private static final int IGNITION_DURATION_TICKS = 30;
    private static final int RECOIL_DURATION_TICKS = 12;
    private static final int NPC_CANNONBALL_WORKING_STOCK = 16;
    private static final int NPC_GUNPOWDER_WORKING_STOCK = 16;
    public static final float MIN_PITCH = 0.0f;
    public static final float MAX_PITCH = 40.0f;
    public static final float MAX_YAW_OFFSET = 45.0f;
    private static final float DEFAULT_BARREL_PITCH = 12.0f;
    private static final class_2940<class_1799> POWDER = class_2945.method_12791(AbstractCannonEntity.class, (class_2941)class_2943.field_13322);
    private static final class_2940<class_1799> AMMUNITION = class_2945.method_12791(AbstractCannonEntity.class, (class_2941)class_2943.field_13322);
    private static final class_2940<Integer> IGNITION_TICKS = class_2945.method_12791(AbstractCannonEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> RECOIL_TICKS = class_2945.method_12791(AbstractCannonEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Float> BARREL_PITCH = class_2945.method_12791(AbstractCannonEntity.class, (class_2941)class_2943.field_13320);

    protected AbstractCannonEntity(class_1299<? extends AbstractCannonEntity> type, class_1937 world) {
        super(type, world);
    }

    @Override
    public MachineTargetProfile medievalMachinery$getTargetProfile() {
        return MachineTargetProfile.of(MachineTargetCategory.SIEGE_WEAPONS, MachineTargetCategory.FORTIFICATIONS, MachineTargetCategory.LARGE_TARGETS, MachineTargetCategory.INFANTRY, MachineTargetCategory.CAVALRY);
    }

    @Override
    public double medievalMachinery$getNpcTargetingRange() {
        class_1792 ammo = this.getAmmunition().method_7960() ? ModItems.STONE_CANNONBALL : this.getAmmunition().method_7909();
        return Math.max(16.0, (double)CannonBallistics.maximumRange(ammo));
    }

    @Override
    public double medievalMachinery$getNpcMinimumTargetingRange() {
        class_1792 ammo;
        class_1792 class_17922 = ammo = this.getAmmunition().method_7960() ? ModItems.STONE_CANNONBALL : this.getAmmunition().method_7909();
        if (ammo == ModItems.TNT_CANNONBALL) {
            return 10.0;
        }
        if (ammo == ModItems.IRON_CANNONBALL || ammo == ModItems.HEAVY_IRON_CANNONBALL) {
            return 7.0;
        }
        return 6.0;
    }

    @Override
    public double medievalMachinery$getNpcImpactSafetyRadius(MachineCombatTarget target) {
        class_1792 ammo;
        class_1792 class_17922 = ammo = this.getAmmunition().method_7960() ? ModItems.STONE_CANNONBALL : this.getAmmunition().method_7909();
        if (ammo == ModItems.TNT_CANNONBALL) {
            return 5.5;
        }
        if (ammo == ModItems.IRON_CANNONBALL || ammo == ModItems.HEAVY_IRON_CANNONBALL) {
            return 4.5;
        }
        return 3.5;
    }

    @Override
    public boolean medievalMachinery$requiresDirectNpcLineOfSight() {
        return false;
    }

    @Override
    public double medievalMachinery$getNpcTargetCategoryBias(MachineTargetCategory category) {
        class_1792 ammo;
        class_1792 class_17922 = ammo = this.getAmmunition().method_7960() ? ModItems.STONE_CANNONBALL : this.getAmmunition().method_7909();
        if (ammo == ModItems.TNT_CANNONBALL || ammo == ModItems.HEAVY_IRON_CANNONBALL) {
            return switch (category) {
                case MachineTargetCategory.FORTIFICATIONS -> -12.0;
                case MachineTargetCategory.SIEGE_WEAPONS -> -7.0;
                case MachineTargetCategory.LARGE_TARGETS -> -3.0;
                default -> 0.0;
            };
        }
        if (ammo == ModItems.IRON_CANNONBALL) {
            return switch (category) {
                case MachineTargetCategory.SIEGE_WEAPONS -> -5.0;
                case MachineTargetCategory.LARGE_TARGETS -> -4.0;
                case MachineTargetCategory.FORTIFICATIONS -> -3.0;
                default -> 0.0;
            };
        }
        return category == MachineTargetCategory.INFANTRY ? -3.0 : 0.0;
    }

    @Override
    public class_243 medievalMachinery$getNpcAimOrigin() {
        return this.getMuzzlePosition();
    }

    @Override
    public MachineAimSolution medievalMachinery$solveNpcAim(class_1309 target, float spreadMultiplier) {
        if (target == null || !target.method_5805()) {
            return MachineAimSolution.INVALID;
        }
        UUID id = target.method_5667();
        return this.medievalMachinery$solveNpcAimAt(target.method_19538().method_1031(0.0, (double)target.method_17682() * 0.52, 0.0), id.getMostSignificantBits() ^ id.getLeastSignificantBits(), spreadMultiplier);
    }

    @Override
    public MachineAimSolution medievalMachinery$solveNpcAimAt(class_243 aimPoint, long stableTargetKey, float spreadMultiplier) {
        if (aimPoint == null) {
            return MachineAimSolution.INVALID;
        }
        class_243 origin = this.getMuzzlePosition();
        class_243 delta = aimPoint.method_1020(origin);
        double horizontal = Math.sqrt(delta.field_1352 * delta.field_1352 + delta.field_1350 * delta.field_1350);
        if (horizontal < 0.25) {
            return MachineAimSolution.INVALID;
        }
        float yaw = class_3532.method_15393((float)((float)Math.toDegrees(Math.atan2(-delta.field_1352, delta.field_1350))));
        long aimSeed = this.method_5667().getLeastSignificantBits() ^ stableTargetKey;
        float normalizedYawError = (float)(aimSeed >>> 8 & 0x3FFL) / 511.5f - 1.0f;
        float normalizedPitchError = (float)(aimSeed >>> 20 & 0x3FFL) / 511.5f - 1.0f;
        float spread = class_3532.method_15363((float)spreadMultiplier, (float)0.25f, (float)2.0f);
        yaw = class_3532.method_15393((float)(yaw + normalizedYawError * 0.75f * spread));
        class_1792 ammo = this.getAmmunition().method_7960() ? ModItems.STONE_CANNONBALL : this.getAmmunition().method_7909();
        double v = CannonBallistics.launchSpeed(ammo);
        float pitch = AbstractCannonEntity.solveDragConsistentPitch(horizontal, delta.field_1351, v);
        if (!Float.isFinite(pitch)) {
            return MachineAimSolution.INVALID;
        }
        return new MachineAimSolution(yaw, class_3532.method_15363((float)(pitch += normalizedPitchError * 0.45f * spread), (float)0.0f, (float)40.0f), true);
    }

    private static float solveDragConsistentPitch(double targetHorizontal, double targetY, double speed) {
        double bestError = Double.POSITIVE_INFINITY;
        float bestPitch = Float.NaN;
        block0: for (float candidate = 0.0f; candidate <= 40.001f; candidate += 0.2f) {
            double rad = Math.toRadians(candidate);
            double vx = Math.cos(rad) * speed;
            double vy = Math.sin(rad) * speed;
            double x = 0.0;
            double y = 0.0;
            double prevX = 0.0;
            double prevY = 0.0;
            for (int tick = 0; tick < 180 && x <= targetHorizontal + 2.0; ++tick) {
                prevX = x;
                prevY = y;
                x += vx;
                y += vy;
                if (x >= targetHorizontal) {
                    double span = Math.max(1.0E-6, x - prevX);
                    double t = class_3532.method_15350((double)((targetHorizontal - prevX) / span), (double)0.0, (double)1.0);
                    double atTarget = prevY + (y - prevY) * t;
                    double error = Math.abs(atTarget - targetY);
                    if (!(error < bestError)) continue block0;
                    bestError = error;
                    bestPitch = candidate;
                    continue block0;
                }
                vx *= 0.99;
                vy = vy * 0.99 - 0.05;
            }
        }
        return bestError <= 2.25 ? bestPitch : Float.NaN;
    }

    @Override
    public List<class_243> medievalMachinery$getNpcProjectilePath(MachineCombatTarget target, MachineAimSolution solution) {
        if (target == null || target.aimPoint() == null || solution == null || !solution.valid()) {
            return List.of();
        }
        class_243 origin = this.getMuzzlePosition();
        class_1792 ammo = this.getAmmunition().method_7960() ? ModItems.STONE_CANNONBALL : this.getAmmunition().method_7909();
        double speed = CannonBallistics.launchSpeed(ammo);
        double yaw = Math.toRadians(solution.yaw());
        double pitch = Math.toRadians(solution.pitch());
        double horizontal = Math.cos(pitch);
        class_243 velocity = new class_243(-Math.sin(yaw) * horizontal, Math.sin(pitch), Math.cos(yaw) * horizontal).method_1021(speed);
        double targetHorizontal = Math.hypot(target.aimPoint().field_1352 - origin.field_1352, target.aimPoint().field_1350 - origin.field_1350);
        ArrayList<class_243> points = new ArrayList<class_243>();
        class_243 pos = origin;
        points.add(pos);
        for (int tick = 0; tick < 160; ++tick) {
            pos = pos.method_1019(velocity);
            points.add(pos);
            velocity = velocity.method_18805(0.99, 0.99, 0.99).method_1031(0.0, -0.05, 0.0);
            double travelledHorizontal = Math.hypot(pos.field_1352 - origin.field_1352, pos.field_1350 - origin.field_1350);
            if (travelledHorizontal >= targetHorizontal + 1.5 || pos.field_1351 < origin.field_1351 - 16.0) break;
        }
        return List.copyOf(points);
    }

    @Override
    public class_1799 medievalMachinery$getNpcHeldVisual(class_1297 npc) {
        return new class_1799((class_1935)class_1802.field_8884);
    }

    @Override
    public boolean medievalMachinery$isNpcAimAligned(MachineAimSolution solution) {
        if (solution == null || !solution.valid()) {
            return false;
        }
        float yawError = Math.abs(class_3532.method_15393((float)(this.method_36454() - solution.yaw())));
        float pitchError = Math.abs(this.getBarrelPitch() - solution.pitch());
        return yawError <= 8.0f && pitchError <= 5.0f;
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
        this.method_36456(this.method_36454() + class_3532.method_15363((float)yawDelta, (float)-12.0f, (float)12.0f));
        float pitchDelta = targetPitch - this.getBarrelPitch();
        this.setBarrelPitch(this.getBarrelPitch() + class_3532.method_15363((float)pitchDelta, (float)-6.0f, (float)6.0f));
    }

    @Override
    public boolean medievalMachinery$supportsCrewRole(MachineCrewRole role) {
        return role == MachineCrewRole.OPERATOR || role == MachineCrewRole.LOADER || role == MachineCrewRole.MAINTENANCE;
    }

    @Override
    public MachineCrewAction medievalMachinery$getNpcCrewAction(class_1297 npc, MachineCrewRole role, MachineCrewWorkContext context) {
        if (role == MachineCrewRole.LOADER || role == MachineCrewRole.OPERATOR) {
            if (!this.isReadyToFire() && this.canNpcCompleteLoadStep(npc, context)) {
                return MachineCrewAction.LOAD;
            }
            if (role == MachineCrewRole.OPERATOR && this.isReadyToFire() && !this.isIgnited()) {
                return MachineCrewAction.IGNITE;
            }
        }
        return MachineCrewAction.IDLE;
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
        if (preferred.method_7960() || !ModItems.isCannonball(preferred.method_7909()) || preferred.method_7909() == ModItems.STONE_CANNONBALL) {
            return List.of();
        }
        ArrayList<MachineCrewSupplyNeed> needs = new ArrayList<MachineCrewSupplyNeed>(2);
        class_1792 preferredItem = preferred.method_7909();
        if (this.getAmmunition().method_7960() && resources.medievalMachinery$countMachineResource(preferredItem) < 1) {
            needs.add(new MachineCrewSupplyNeed(preferredItem, 1, 16));
        }
        if (this.getPowder().method_7960() && resources.medievalMachinery$countMachineResource(class_1802.field_8054) < 1) {
            needs.add(new MachineCrewSupplyNeed(class_1802.field_8054, 1, 16));
        }
        return List.copyOf(needs);
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
        if (preferred.method_7960() || !ModItems.isCannonball(preferred.method_7909()) || preferred.method_7909() == ModItems.STONE_CANNONBALL) {
            return List.of();
        }
        return List.of(new MachineCrewSupplyNeed(preferred.method_7909(), 1, 16), new MachineCrewSupplyNeed(class_1802.field_8054, 1, 16));
    }

    private boolean canNpcCompleteLoadStep(class_1297 npc, MachineCrewWorkContext context) {
        class_1792 preferredItem;
        if (this.isReadyToFire()) {
            return false;
        }
        if (!(npc instanceof MachineCrewResourceAccess)) {
            return context.mayUseVirtualStandardResources() && this.getAmmunition().method_7960();
        }
        MachineCrewResourceAccess resources = (MachineCrewResourceAccess)npc;
        class_1799 preferred = resources.medievalMachinery$getPreferredMachineAmmunition();
        class_1792 class_17922 = preferredItem = preferred.method_7960() ? ModItems.STONE_CANNONBALL : preferred.method_7909();
        if (!this.getAmmunition().method_7960()) {
            if (!this.getPowder().method_7960()) {
                return false;
            }
            if (this.getAmmunition().method_31574(ModItems.STONE_CANNONBALL)) {
                return context.mayUseVirtualStandardResources();
            }
            return resources.medievalMachinery$hasMachineResource(class_1802.field_8054, 1) || context.mayUseVirtualStandardResources();
        }
        if (preferredItem != ModItems.STONE_CANNONBALL && ModItems.isCannonball(preferredItem)) {
            return resources.medievalMachinery$hasMachineResource(preferredItem, 1) && resources.medievalMachinery$hasMachineResource(class_1802.field_8054, 1) || context.mayUseVirtualStandardResources();
        }
        return context.mayUseVirtualStandardResources();
    }

    @Override
    public int medievalMachinery$getNpcCrewActionTicks(MachineCrewAction action, MachineCrewRole role) {
        return switch (action) {
            case MachineCrewAction.LOAD -> {
                if (role == MachineCrewRole.LOADER) {
                    yield 24;
                }
                yield 32;
            }
            case MachineCrewAction.IGNITE -> 6;
            default -> super.medievalMachinery$getNpcCrewActionTicks(action, role);
        };
    }

    @Override
    public boolean medievalMachinery$performNpcCrewAction(class_1297 npc, MachineCrewRole role, MachineCrewAction action, MachineCrewWorkContext context) {
        if (!(this.method_37908() instanceof class_3218)) {
            return false;
        }
        if (action == MachineCrewAction.LOAD) {
            boolean specialSelected;
            class_1792 preferredItem;
            if (!(npc instanceof MachineCrewResourceAccess)) {
                return false;
            }
            MachineCrewResourceAccess resources = (MachineCrewResourceAccess)npc;
            class_1799 preferred = resources.medievalMachinery$getPreferredMachineAmmunition();
            class_1792 class_17922 = preferredItem = preferred.method_7960() ? ModItems.STONE_CANNONBALL : preferred.method_7909();
            if (!this.getAmmunition().method_7960()) {
                class_1792 loadedItem;
                if (!this.getPowder().method_7960()) {
                    return this.isReadyToFire();
                }
                if (this.getAmmunition().method_31574(ModItems.STONE_CANNONBALL) && context.mayUseVirtualStandardResources()) {
                    this.setPowder(new class_1799((class_1935)class_1802.field_8054));
                    this.markNpcPowderLoaded(true);
                } else if (resources.medievalMachinery$consumeMachineResource(class_1802.field_8054, 1)) {
                    this.setPowder(new class_1799((class_1935)class_1802.field_8054));
                    this.markNpcPowderLoaded(false);
                } else if (context.mayUseVirtualStandardResources() && resources.medievalMachinery$storeMachineResource(loadedItem = this.getAmmunition().method_7909(), 1) == 1) {
                    this.setAmmunition(class_1799.field_8037);
                    this.setAmmunition(new class_1799((class_1935)ModItems.STONE_CANNONBALL));
                    this.markNpcAmmunitionLoaded(true);
                    this.setPowder(new class_1799((class_1935)class_1802.field_8054));
                    this.markNpcPowderLoaded(true);
                }
                if (this.isReadyToFire()) {
                    this.method_5783(class_3417.field_15131, 0.65f, 1.35f);
                }
                return this.isReadyToFire();
            }
            boolean bl = specialSelected = preferredItem != ModItems.STONE_CANNONBALL && ModItems.isCannonball(preferredItem);
            if (specialSelected && resources.medievalMachinery$hasMachineResource(preferredItem, 1) && resources.medievalMachinery$hasMachineResource(class_1802.field_8054, 1)) {
                if (!resources.medievalMachinery$consumeMachineResource(preferredItem, 1)) {
                    return false;
                }
                if (!resources.medievalMachinery$consumeMachineResource(class_1802.field_8054, 1)) {
                    resources.medievalMachinery$storeMachineResource(preferredItem, 1);
                    return false;
                }
                this.setAmmunition(new class_1799((class_1935)preferredItem));
                this.markNpcAmmunitionLoaded(false);
                this.setPowder(new class_1799((class_1935)class_1802.field_8054));
                this.markNpcPowderLoaded(false);
            } else if (context.mayUseVirtualStandardResources()) {
                this.setAmmunition(new class_1799((class_1935)ModItems.STONE_CANNONBALL));
                this.markNpcAmmunitionLoaded(true);
                this.setPowder(new class_1799((class_1935)class_1802.field_8054));
                this.markNpcPowderLoaded(true);
            } else {
                return false;
            }
            if (this.isReadyToFire()) {
                this.method_5783(class_3417.field_15131, 0.65f, 1.35f);
            }
            return this.isReadyToFire();
        }
        if (action == MachineCrewAction.IGNITE) {
            if (role != MachineCrewRole.OPERATOR || !this.isReadyToFire() || this.isIgnited()) {
                return false;
            }
            this.setIgnitionTicks(30);
            this.method_5783(class_3417.field_15145, 1.0f, 1.0f);
            return true;
        }
        return super.medievalMachinery$performNpcCrewAction(npc, role, action, context);
    }

    @Override
    protected void initMachineDataTracker() {
        this.field_6011.method_12784(POWDER, (Object)class_1799.field_8037);
        this.field_6011.method_12784(AMMUNITION, (Object)class_1799.field_8037);
        this.field_6011.method_12784(IGNITION_TICKS, (Object)0);
        this.field_6011.method_12784(RECOIL_TICKS, (Object)0);
        this.field_6011.method_12784(BARREL_PITCH, (Object)Float.valueOf(12.0f));
    }

    @Override
    protected void tickMachineServer(class_3218 world) {
        if (this.getRecoilTicks() > 0) {
            this.setRecoilTicks(this.getRecoilTicks() - 1);
        }
        this.tickIgnition(world);
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
                    LoaderHooks.machineControlStopped(serverPlayer, this);
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.field_cannon.aiming_stopped"), true);
                } else if (this.tryClaimOperator(player.method_5667())) {
                    LoaderHooks.machineControlStarted(serverPlayer, this);
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.field_cannon.aiming_mode"), true);
                } else {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.field_cannon.in_use"), true);
                }
            }
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (held.method_31574(class_1802.field_8620)) {
            return this.repairWithIron(player, held);
        }
        if (held.method_31574(class_1802.field_8054)) {
            if (!this.getPowder().method_7960()) {
                return class_1269.method_29236((boolean)this.method_37908().field_9236);
            }
            if (!this.method_37908().field_9236) {
                this.setPowder(new class_1799((class_1935)class_1802.field_8054));
                AbstractCannonEntity.consumeOne(player, held);
                this.method_5783(class_3417.field_15197, 0.45f, 0.85f);
                this.announceReadyIfNeeded(player);
            }
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (ModItems.isCannonball(held.method_7909())) {
            if (!this.getAmmunition().method_7960()) {
                return class_1269.method_29236((boolean)this.method_37908().field_9236);
            }
            if (!this.method_37908().field_9236) {
                class_1799 loaded = held.method_7972();
                loaded.method_7939(1);
                this.setAmmunition(loaded);
                AbstractCannonEntity.consumeOne(player, held);
                this.method_5783(class_3417.field_15197, 0.45f, 0.85f);
                this.announceReadyIfNeeded(player);
            }
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (held.method_31574(class_1802.field_8884)) {
            if (!this.method_37908().field_9236) {
                if (this.isIgnited()) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.field_cannon.already_ignited"), true);
                } else if (!this.isReadyToFire()) {
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.field_cannon.not_ready"), true);
                } else {
                    this.setIgnitionTicks(30);
                    this.method_5783(class_3417.field_15145, 1.0f, 1.0f);
                    if (!player.method_31549().field_7477) {
                        held.method_7956(1, (class_1309)player, living -> living.method_20236(hand));
                    }
                    player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.field_cannon.ignited"), true);
                }
            }
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (held.method_7960()) {
            if (!this.method_37908().field_9236 && player instanceof class_3222) {
                class_3222 serverPlayer = (class_3222)player;
                LoaderHooks.openCannon(serverPlayer, this);
            }
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        return class_1269.field_5811;
    }

    private class_1269 repairWithIron(class_1657 player, class_1799 held) {
        if (this.getHealth() >= 200) {
            if (!this.method_37908().field_9236) {
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.field_cannon.already_repaired"), true);
            }
            return class_1269.method_29236((boolean)this.method_37908().field_9236);
        }
        if (!this.method_37908().field_9236) {
            int repaired = this.repair(20);
            AbstractCannonEntity.consumeOne(player, held);
            this.method_5783(class_3417.field_21077, 1.0f, 1.0f);
            class_1937 class_19372 = this.method_37908();
            if (class_19372 instanceof class_3218) {
                class_3218 serverWorld = (class_3218)class_19372;
                serverWorld.method_14199((class_2394)class_2398.field_29642, this.method_23317(), this.method_23323(0.55), this.method_23321(), 12, 0.45, 0.35, 0.45, 0.06);
            }
            player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.field_cannon.repaired", (Object[])new Object[]{repaired, this.getHealth(), 200}), true);
        }
        return class_1269.method_29236((boolean)this.method_37908().field_9236);
    }

    private void announceReadyIfNeeded(class_1657 player) {
        if (this.isReadyToFire()) {
            this.method_5783(class_3417.field_15131, 0.65f, 1.35f);
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.field_cannon.loaded"), true);
        }
    }

    private void tickIgnition(class_3218 world) {
        int ticks = this.getIgnitionTicks();
        if (ticks <= 0) {
            return;
        }
        this.setIgnitionTicks(--ticks);
        class_243 fuse = this.getFusePosition();
        world.method_14199((class_2394)class_2398.field_11251, fuse.field_1352, fuse.field_1351, fuse.field_1350, 1, 0.025, 0.025, 0.025, 0.005);
        if (ticks % 3 == 0) {
            world.method_14199((class_2394)class_2398.field_11240, fuse.field_1352, fuse.field_1351, fuse.field_1350, 1, 0.012, 0.012, 0.012, 0.002);
        }
        if (ticks % 6 == 0) {
            world.method_43128(null, fuse.field_1352, fuse.field_1351, fuse.field_1350, class_3417.field_14993, class_3419.field_15245, 0.35f, 1.65f);
        }
        if (ticks == 0) {
            this.fire(world);
        }
    }

    private void fire(class_3218 world) {
        if (!this.isReadyToFire()) {
            return;
        }
        class_1792 ammunitionItem = this.getAmmunition().method_7909();
        class_243 direction = this.getBarrelDirection();
        class_243 muzzle = this.getMuzzlePosition();
        CannonballEntity projectile = new CannonballEntity((class_1937)world, muzzle.field_1352, muzzle.field_1351, muzzle.field_1350, ammunitionItem, CannonBallistics.maximumRange(ammunitionItem));
        float speed = CannonBallistics.launchSpeed(ammunitionItem);
        projectile.method_7485(direction.field_1352, direction.field_1351, direction.field_1350, speed, 0.1f);
        world.method_8649((class_1297)projectile);
        world.method_43128(null, muzzle.field_1352, muzzle.field_1351, muzzle.field_1350, class_3417.field_15152, class_3419.field_15245, 3.2f, 0.78f + world.field_9229.method_43057() * 0.08f);
        world.method_14199((class_2394)class_2398.field_11240, muzzle.field_1352, muzzle.field_1351, muzzle.field_1350, 14, 0.12, 0.12, 0.12, 0.08);
        world.method_14199((class_2394)class_2398.field_17430, muzzle.field_1352, muzzle.field_1351, muzzle.field_1350, 28, 0.22, 0.22, 0.22, 0.1);
        this.setPowder(class_1799.field_8037);
        this.setAmmunition(class_1799.field_8037);
        this.clearNpcLoadProvenance();
        this.setRecoilTicks(12);
    }

    public class_243 getBarrelDirection() {
        double yaw = Math.toRadians(this.method_36454());
        double pitch = Math.toRadians(this.getBarrelPitch());
        double horizontal = Math.cos(pitch);
        return new class_243(-Math.sin(yaw) * horizontal, Math.sin(pitch), Math.cos(yaw) * horizontal).method_1029();
    }

    public class_243 getMuzzlePosition() {
        return this.method_19538().method_1019(this.getBarrelUpDirection().method_1021(1.16)).method_1019(this.getBarrelDirection().method_1021(1.48));
    }

    public float getPreviewLaunchSpeed() {
        return CannonBallistics.launchSpeed(this.getAmmunition().method_7909());
    }

    protected class_243 getBarrelUpDirection() {
        double yaw = Math.toRadians(this.method_36454());
        double pitch = Math.toRadians(this.getBarrelPitch());
        return new class_243(Math.sin(yaw) * Math.sin(pitch), Math.cos(pitch), -Math.cos(yaw) * Math.sin(pitch)).method_1029();
    }

    protected class_243 getFusePosition() {
        return this.method_19538().method_1019(this.getBarrelUpDirection().method_1021(1.48)).method_1019(this.getBarrelDirection().method_1021(-0.22));
    }

    public float getRecoilOffset(float tickDelta) {
        int ticks = this.getRecoilTicks();
        if (ticks <= 0) {
            return 0.0f;
        }
        float progress = ((float)(12 - ticks) + tickDelta) / 12.0f;
        return (float)Math.sin(Math.PI * (double)class_3532.method_15363((float)progress, (float)0.0f, (float)1.0f)) * 1.45f;
    }

    @Override
    public void loadFromItem(class_2487 nbt) {
        if (nbt.method_10545("Health")) {
            this.setHealth(nbt.method_10550("Health"));
        }
        if (nbt.method_10545("Powder")) {
            this.setPowder(class_1799.method_7915((class_2487)nbt.method_10562("Powder")));
        }
        if (nbt.method_10545("Ammunition")) {
            this.setAmmunition(class_1799.method_7915((class_2487)nbt.method_10562("Ammunition")));
        }
    }

    protected abstract class_1792 getCannonItem();

    @Override
    protected class_1799 createDroppedStack() {
        class_1799 stack = new class_1799((class_1935)this.getCannonItem());
        if (this.getHealth() != 200 || !this.getPowder().method_7960() || !this.getAmmunition().method_7960()) {
            class_2487 nbt = stack.method_7948();
            if (this.getHealth() != 200) {
                nbt.method_10569("Health", this.getHealth());
            }
            if (!this.getPowder().method_7960()) {
                nbt.method_10566("Powder", (class_2520)this.getPowder().method_7953(new class_2487()));
            }
            if (!this.getAmmunition().method_7960()) {
                nbt.method_10566("Ammunition", (class_2520)this.getAmmunition().method_7953(new class_2487()));
            }
        }
        return stack;
    }

    private static void consumeOne(class_1657 player, class_1799 stack) {
        if (!player.method_31549().field_7477) {
            stack.method_7934(1);
        }
    }

    public float getBarrelPitch() {
        return ((Float)this.field_6011.method_12789(BARREL_PITCH)).floatValue();
    }

    public void setBarrelPitch(float pitch) {
        this.field_6011.method_12778(BARREL_PITCH, (Object)Float.valueOf(class_3532.method_15363((float)pitch, (float)0.0f, (float)40.0f)));
    }

    @Override
    protected float clampAimPitch(float pitch) {
        return class_3532.method_15363((float)pitch, (float)0.0f, (float)40.0f);
    }

    @Override
    protected void applyAimTarget(float absoluteYaw, float pitch) {
        this.setBarrelPitch(pitch);
    }

    private void markNpcPowderLoaded(boolean virtual) {
        this.npcLoadedPowder = true;
        this.npcVirtualPowder = virtual;
    }

    private void markNpcAmmunitionLoaded(boolean virtual) {
        this.npcLoadedAmmunition = true;
        this.npcVirtualAmmunition = virtual;
    }

    private void clearNpcLoadProvenance() {
        this.npcLoadedPowder = false;
        this.npcLoadedAmmunition = false;
        this.npcVirtualPowder = false;
        this.npcVirtualAmmunition = false;
    }

    @Override
    public boolean medievalMachinery$canPlayerTakeMachineSlot(int slot) {
        return switch (slot) {
            case 0 -> {
                if (!this.npcLoadedPowder) {
                    yield true;
                }
                yield false;
            }
            case 1 -> {
                if (!this.npcLoadedAmmunition) {
                    yield true;
                }
                yield false;
            }
            default -> true;
        };
    }

    @Override
    public void medievalMachinery$onNpcAssignmentCleared(class_1297 npc) {
        if (npc instanceof MachineCrewResourceAccess) {
            int stored;
            MachineCrewResourceAccess resources = (MachineCrewResourceAccess)npc;
            if (this.npcLoadedAmmunition && !this.npcVirtualAmmunition && !this.getAmmunition().method_7960() && (stored = resources.medievalMachinery$storeMachineResource(this.getAmmunition().method_7909(), 1)) < 1) {
                npc.method_5775(this.getAmmunition().method_46651(1));
            }
            if (this.npcLoadedPowder && !this.npcVirtualPowder && !this.getPowder().method_7960() && (stored = resources.medievalMachinery$storeMachineResource(class_1802.field_8054, 1)) < 1) {
                npc.method_5775(new class_1799((class_1935)class_1802.field_8054));
            }
        }
        if (this.npcLoadedAmmunition) {
            this.setAmmunition(class_1799.field_8037);
        }
        if (this.npcLoadedPowder) {
            this.setPowder(class_1799.field_8037);
        }
        this.clearNpcLoadProvenance();
    }

    public class_1799 getPowder() {
        return (class_1799)this.field_6011.method_12789(POWDER);
    }

    private void setPowder(class_1799 stack) {
        this.field_6011.method_12778(POWDER, (Object)stack.method_7972());
    }

    public class_1799 getAmmunition() {
        return (class_1799)this.field_6011.method_12789(AMMUNITION);
    }

    private void setAmmunition(class_1799 stack) {
        this.field_6011.method_12778(AMMUNITION, (Object)stack.method_7972());
    }

    public boolean isReadyToFire() {
        return this.getPowder().method_31574(class_1802.field_8054) && ModItems.isCannonball(this.getAmmunition().method_7909());
    }

    public boolean isIgnited() {
        return this.getIgnitionTicks() > 0;
    }

    public int getIgnitionTicks() {
        return (Integer)this.field_6011.method_12789(IGNITION_TICKS);
    }

    private void setIgnitionTicks(int ticks) {
        this.field_6011.method_12778(IGNITION_TICKS, (Object)Math.max(0, ticks));
    }

    public int getRecoilTicks() {
        return (Integer)this.field_6011.method_12789(RECOIL_TICKS);
    }

    private void setRecoilTicks(int ticks) {
        this.field_6011.method_12778(RECOIL_TICKS, (Object)Math.max(0, ticks));
    }

    public int method_5439() {
        return 2;
    }

    public boolean method_5442() {
        return this.getPowder().method_7960() && this.getAmmunition().method_7960();
    }

    public class_1799 method_5438(int slot) {
        return slot == 0 ? this.getPowder() : (slot == 1 ? this.getAmmunition() : class_1799.field_8037);
    }

    public class_1799 method_5434(int slot, int amount) {
        class_1799 current = this.method_5438(slot);
        if (current.method_7960()) {
            return class_1799.field_8037;
        }
        class_1799 removed = current.method_7971(amount);
        this.method_5447(slot, current);
        return removed;
    }

    public class_1799 method_5441(int slot) {
        class_1799 current = this.method_5438(slot).method_7972();
        this.method_5447(slot, class_1799.field_8037);
        return current;
    }

    public void method_5447(int slot, class_1799 stack) {
        class_1799 one = stack.method_7972();
        if (!one.method_7960()) {
            one.method_7939(1);
        }
        if (slot == 0) {
            this.setPowder(one);
            this.npcLoadedPowder = false;
            this.npcVirtualPowder = false;
        } else if (slot == 1) {
            this.setAmmunition(one);
            this.npcLoadedAmmunition = false;
            this.npcVirtualAmmunition = false;
        }
    }

    public void method_5431() {
    }

    public boolean method_5443(class_1657 player) {
        return !this.method_31481() && player.method_5858((class_1297)this) <= 64.0;
    }

    public void method_5448() {
        this.setPowder(class_1799.field_8037);
        this.setAmmunition(class_1799.field_8037);
    }

    public boolean method_5437(int slot, class_1799 stack) {
        return slot == 0 ? stack.method_31574(class_1802.field_8054) : slot == 1 && ModItems.isCannonball(stack.method_7909());
    }

    @Override
    public void writeScreenOpeningData(class_3222 player, class_2540 buf) {
        buf.method_10804(this.method_5628());
    }

    @Override
    protected void readMachineNbt(class_2487 nbt) {
        if (nbt.method_10545("CannonYaw")) {
            this.method_36456(nbt.method_10583("CannonYaw"));
        }
        if (nbt.method_10545("BarrelPitch")) {
            this.setBarrelPitch(nbt.method_10583("BarrelPitch"));
        }
        if (nbt.method_10545("Powder")) {
            this.setPowder(class_1799.method_7915((class_2487)nbt.method_10562("Powder")));
        }
        if (nbt.method_10545("Ammunition")) {
            this.setAmmunition(class_1799.method_7915((class_2487)nbt.method_10562("Ammunition")));
        }
        this.npcLoadedPowder = nbt.method_10577("NpcLoadedPowder");
        this.npcLoadedAmmunition = nbt.method_10577("NpcLoadedAmmunition");
        this.npcVirtualPowder = nbt.method_10577("NpcVirtualPowder");
        this.npcVirtualAmmunition = nbt.method_10577("NpcVirtualAmmunition");
        this.setIgnitionTicks(nbt.method_10550("IgnitionTicks"));
        this.setRecoilTicks(nbt.method_10550("RecoilTicks"));
    }

    @Override
    protected void writeMachineNbt(class_2487 nbt) {
        nbt.method_10548("BarrelPitch", this.getBarrelPitch());
        if (!this.getPowder().method_7960()) {
            nbt.method_10566("Powder", (class_2520)this.getPowder().method_7953(new class_2487()));
        }
        if (!this.getAmmunition().method_7960()) {
            nbt.method_10566("Ammunition", (class_2520)this.getAmmunition().method_7953(new class_2487()));
        }
        if (this.npcLoadedPowder) {
            nbt.method_10556("NpcLoadedPowder", true);
        }
        if (this.npcLoadedAmmunition) {
            nbt.method_10556("NpcLoadedAmmunition", true);
        }
        if (this.npcVirtualPowder) {
            nbt.method_10556("NpcVirtualPowder", true);
        }
        if (this.npcVirtualAmmunition) {
            nbt.method_10556("NpcVirtualAmmunition", true);
        }
        nbt.method_10569("IgnitionTicks", this.getIgnitionTicks());
        nbt.method_10569("RecoilTicks", this.getRecoilTicks());
    }
}

