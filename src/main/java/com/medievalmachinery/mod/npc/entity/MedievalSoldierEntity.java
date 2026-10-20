/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1262
 *  net.minecraft.class_1263
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1277
 *  net.minecraft.class_1282
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1304
 *  net.minecraft.class_1306
 *  net.minecraft.class_1308
 *  net.minecraft.class_1309
 *  net.minecraft.class_1314
 *  net.minecraft.class_1322
 *  net.minecraft.class_1322$class_1323
 *  net.minecraft.class_1324
 *  net.minecraft.class_1347
 *  net.minecraft.class_1352
 *  net.minecraft.class_1361
 *  net.minecraft.class_1376
 *  net.minecraft.class_1496
 *  net.minecraft.class_1542
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1676
 *  net.minecraft.class_1688
 *  net.minecraft.class_1703
 *  net.minecraft.class_1738
 *  net.minecraft.class_1743
 *  net.minecraft.class_1764
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1819
 *  net.minecraft.class_1922
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2371
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2940
 *  net.minecraft.class_2941
 *  net.minecraft.class_2943
 *  net.minecraft.class_2945
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3414
 *  net.minecraft.class_3417
 *  net.minecraft.class_3481
 *  net.minecraft.class_3532
 *  net.minecraft.class_3908
 *  net.minecraft.class_5132$class_5133
 *  net.minecraft.class_5134
 *  net.minecraft.class_7923
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.item.OrderArmorSetBonusService;
import com.medievalmachinery.mod.item.spear.SpearItem;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.npc.armor.ArmorStyle;
import com.medievalmachinery.mod.npc.armor.ArmorTier;
import com.medievalmachinery.mod.npc.armor.NpcArmorProfiles;
import com.medievalmachinery.mod.npc.core.NpcCombatMovementState;
import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcCombatTargetState;
import com.medievalmachinery.mod.npc.core.NpcEquipmentProfile;
import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcFormationState;
import com.medievalmachinery.mod.npc.core.NpcHungerData;
import com.medievalmachinery.mod.npc.core.NpcHungerState;
import com.medievalmachinery.mod.npc.core.NpcIdentity;
import com.medievalmachinery.mod.npc.core.NpcLeadershipPolicy;
import com.medievalmachinery.mod.npc.core.NpcMoraleBehaviorPolicy;
import com.medievalmachinery.mod.npc.core.NpcMoraleData;
import com.medievalmachinery.mod.npc.core.NpcNavigationInteractionState;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcPreferredMeleeWeapon;
import com.medievalmachinery.mod.npc.core.NpcRangedVisualState;
import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.core.NpcRecoveryState;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.core.NpcSupplyRequest;
import com.medievalmachinery.mod.npc.core.NpcTargetPolicy;
import com.medievalmachinery.mod.npc.core.NpcTargetPriority;
import com.medievalmachinery.mod.npc.core.NpcWeaponProfileResolver;
import com.medievalmachinery.mod.npc.entity.AggressiveHostileTargetGoal;
import com.medievalmachinery.mod.npc.entity.BodyguardEscortGoal;
import com.medievalmachinery.mod.npc.entity.DiplomaticHostileTargetGoal;
import com.medievalmachinery.mod.npc.entity.FollowOwnerGoal;
import com.medievalmachinery.mod.npc.entity.FormationAssemblyGoal;
import com.medievalmachinery.mod.npc.entity.HazardAvoidanceGoal;
import com.medievalmachinery.mod.npc.entity.HoldPositionGoal;
import com.medievalmachinery.mod.npc.entity.IdleRestGoal;
import com.medievalmachinery.mod.npc.entity.IdleReturnGoal;
import com.medievalmachinery.mod.npc.entity.MachineCrewAssignmentGoal;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierAnimationState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierAutonomousSupplyPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierCombatController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierCombatState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierCommandState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierDebug;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEatingController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEatingState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEquipmentContext;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEquipmentController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFireControl;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFormationCombatPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierFormationController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierGroupCombatCoordinator;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierLocalDefensePolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMachineResourceController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMaintenanceState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMeleeTimingPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierModifierState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMountCommandState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMountedController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMountedPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierMountedState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierOrderController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierPatrolCombatPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierRangedFirePolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierRangedLanePolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierRangedProjectileController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierRideablePolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierScoutController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierShieldControl;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierShieldPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierStrategicFirePolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierSupplyController;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierSupplyState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierTargetPriorityPolicy;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierWarningController;
import com.medievalmachinery.mod.npc.entity.MoraleFallbackGoal;
import com.medievalmachinery.mod.npc.entity.MountCommandGoal;
import com.medievalmachinery.mod.npc.entity.MoveToCommandGoal;
import com.medievalmachinery.mod.npc.entity.OrderAwareWanderGoal;
import com.medievalmachinery.mod.npc.entity.OwnerHurtByTargetGoal;
import com.medievalmachinery.mod.npc.entity.OwnerHurtTargetGoal;
import com.medievalmachinery.mod.npc.entity.PatrolHomeGoal;
import com.medievalmachinery.mod.npc.entity.QuartermasterComposterPoiService;
import com.medievalmachinery.mod.npc.entity.QuartermasterCropPolicy;
import com.medievalmachinery.mod.npc.entity.QuartermasterFarmState;
import com.medievalmachinery.mod.npc.entity.QuartermasterFarmingController;
import com.medievalmachinery.mod.npc.entity.QuartermasterFarmingGoal;
import com.medievalmachinery.mod.npc.entity.QuartermasterWorkVisualState;
import com.medievalmachinery.mod.npc.entity.RaidLivingTargetGoal;
import com.medievalmachinery.mod.npc.entity.ReturnToGarrisonGoal;
import com.medievalmachinery.mod.npc.entity.RoleAwareMeleeGoal;
import com.medievalmachinery.mod.npc.entity.RoleAwareRangedGoal;
import com.medievalmachinery.mod.npc.entity.StanceAwareRevengeGoal;
import com.medievalmachinery.mod.npc.entity.StrategicAreaFireGoal;
import com.medievalmachinery.mod.npc.entity.UnarmedRangedFallbackGoal;
import com.medievalmachinery.mod.npc.entity.WantedItemPickupGoal;
import com.medievalmachinery.mod.npc.equipment.NpcMendingService;
import com.medievalmachinery.mod.npc.machine.NpcMachineAssignmentData;
import com.medievalmachinery.mod.npc.machine.NpcMachineAssignmentService;
import com.medievalmachinery.mod.npc.machine.NpcMachineResourceAccess;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierAdaptiveNavigation;
import com.medievalmachinery.mod.npc.navigation.MedievalSoldierCrowdSafety;
import com.medievalmachinery.mod.npc.organization.CaptainAssignmentService;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.CommandGroupType;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.GarrisonRallyPolicy;
import com.medievalmachinery.mod.npc.organization.GeneralBodyguardService;
import com.medievalmachinery.mod.npc.organization.GeneralCooldownManager;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomaticIncidentService;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.KingdomRelationPolicy;
import com.medievalmachinery.mod.npc.organization.LocalAggressionManager;
import com.medievalmachinery.mod.npc.organization.NpcOrganizationService;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import com.medievalmachinery.mod.npc.patrol.PatrolRoute;
import com.medievalmachinery.mod.npc.patrol.PatrolState;
import com.medievalmachinery.mod.npc.transfer.CastleTransferController;
import com.medievalmachinery.mod.npc.transfer.CastleTransferData;
import com.medievalmachinery.mod.registry.ModItems;
import com.medievalmachinery.mod.screen.npc.SoldierInventoryScreenHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.class_1262;
import net.minecraft.class_1263;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1277;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1304;
import net.minecraft.class_1306;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1314;
import net.minecraft.class_1322;
import net.minecraft.class_1324;
import net.minecraft.class_1347;
import net.minecraft.class_1352;
import net.minecraft.class_1361;
import net.minecraft.class_1376;
import net.minecraft.class_1496;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1676;
import net.minecraft.class_1688;
import net.minecraft.class_1703;
import net.minecraft.class_1738;
import net.minecraft.class_1743;
import net.minecraft.class_1764;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1819;
import net.minecraft.class_1922;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2371;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2940;
import net.minecraft.class_2941;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_3481;
import net.minecraft.class_3532;
import net.minecraft.class_3908;
import net.minecraft.class_5132;
import net.minecraft.class_5134;
import net.minecraft.class_7923;
import org.jetbrains.annotations.Nullable;

public class MedievalSoldierEntity
extends class_1314
implements MedievalSoldierEquipmentContext,
NpcMachineResourceAccess {
    private static final class_2940<Integer> TRACKED_ORDER_STYLE = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_ARMOR_TIER = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_ROLE = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_TARGET_PRIORITY = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_PREFERRED_MELEE_WEAPON = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_RANK = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_ARMOR_STYLE_OVERRIDE = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_LEVEL = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_EXPERIENCE = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Float> TRACKED_MORALE = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13320);
    private static final class_2940<String> TRACKED_CASTLE_NAME = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13326);
    private static final class_2940<Integer> TRACKED_ORDER_TYPE = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_COMBAT_STANCE = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_HUNGER = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_RANGED_ATTACK_TICKS = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_RANGED_VISUAL_STATE = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_FORMATION = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Integer> TRACKED_SHIELD_MODE = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Boolean> TRACKED_BODYGUARD_DUTY = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13323);
    private static final class_2940<Integer> TRACKED_QUARTERMASTER_FIELD_COUNT = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<Boolean> TRACKED_QUARTERMASTER_COMPOSTER = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13323);
    private static final class_2940<Boolean> TRACKED_QUARTERMASTER_COMPOSTING = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13323);
    private static final class_2940<Integer> TRACKED_QUARTERMASTER_PLANT_ITEM = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13327);
    private static final class_2940<class_1799> TRACKED_MACHINE_HELD_VISUAL = class_2945.method_12791(MedievalSoldierEntity.class, (class_2941)class_2943.field_13322);
    private static final float RANGED_ATTACK_RANGE = 44.0f;
    private static final int BOW_DRAW_TICKS = 20;
    private static final int CROSSBOW_CHARGE_TICKS = 40;
    private static final int CROSSBOW_AIM_TICKS = 40;
    private static final double RANGED_RETREAT_LEASH = 10.0;
    private final NpcIdentity identity = new NpcIdentity();
    private NpcRole role = NpcRole.SWORDSMAN;
    private NpcTargetPriority targetPriority = NpcTargetPriority.CLOSEST;
    private NpcPreferredMeleeWeapon preferredMeleeWeapon = NpcPreferredMeleeWeapon.SWORD;
    private NpcOrderType orderType = NpcOrderType.IDLE;
    private NpcCombatStance combatStance = NpcCombatStance.NEUTRAL;
    private String appearanceId = "villager";
    private ArmorStyle armorStyleOverride;
    private class_2338 homePosition;
    private class_2338 commandDestination;
    private final MedievalSoldierCommandState commandState = new MedievalSoldierCommandState();
    @Nullable
    private class_243 idleWanderAnchor;
    private boolean idleRallyAnchorActive;
    private final MedievalSoldierEatingState eatingState = new MedievalSoldierEatingState();
    private int inventoryGuiViewers;
    private boolean inventoryGuiMovementLocked = true;
    private final MedievalSoldierCombatState combatState = new MedievalSoldierCombatState();
    private final MedievalSoldierAdaptiveNavigation adaptiveNavigation = new MedievalSoldierAdaptiveNavigation(this);
    private final MedievalSoldierDebug npcDebug = new MedievalSoldierDebug(this);
    private boolean strategicAreaFireActive;
    @Nullable
    private class_243 strategicAreaFireCenter;
    private double strategicAreaFireRadius = 3.5;
    private static final byte SPEAR_THRUST_STATUS = 61;
    private static final byte MELEE_SWING_STATUS = 62;
    private static final byte FARM_TILL_STATUS = 63;
    private static final byte FARM_PLANT_STATUS = 64;
    private static final byte FARM_HARVEST_STATUS = 65;
    private static final byte FARM_FERTILIZE_STATUS = 66;
    private static final float SPEAR_THRUST_DURATION_TICKS = 11.0f;
    private static final float MELEE_SWING_DURATION_TICKS = 7.0f;
    private static final float FARM_TILL_DURATION_TICKS = 12.0f;
    private static final float FARM_PLANT_DURATION_TICKS = 9.0f;
    private static final float FARM_HARVEST_DURATION_TICKS = 9.0f;
    private final MedievalSoldierAnimationState animationState = new MedievalSoldierAnimationState();
    private class_243 holdPosition;
    private int holdDisplacedTicks;
    private final MedievalSoldierSupplyState supplyState = new MedievalSoldierSupplyState(this);
    private final MedievalSoldierAutonomousSupplyPolicy autonomousSupplyPolicy = new MedievalSoldierAutonomousSupplyPolicy();
    private final NpcFormationState formationState = new NpcFormationState();
    private final NpcCombatTargetState combatTargetState = new NpcCombatTargetState();
    private final NpcCombatMovementState combatMovementState = new NpcCombatMovementState();
    private final NpcRecoveryState recoveryState = new NpcRecoveryState();
    private final NpcNavigationInteractionState navigationInteractionState = new NpcNavigationInteractionState();
    private final PatrolState patrolState = new PatrolState();
    private static final UUID LEVEL_HEALTH_MODIFIER_ID = UUID.fromString("bcb4fb17-6ce0-4e93-bffc-7cb763f01b8f");
    private static final UUID LEVEL_DAMAGE_MODIFIER_ID = UUID.fromString("b2afbcf0-9d36-466b-8d24-9ebd849e1f76");
    private final NpcHungerData hungerData = new NpcHungerData();
    private final NpcMachineAssignmentData machineAssignment = new NpcMachineAssignmentData();
    private final NpcMoraleData moraleData = new NpcMoraleData();
    private final CastleTransferData castleTransferData = new CastleTransferData();
    private NpcLeadershipPolicy.Strength cachedLeadershipStrength = NpcLeadershipPolicy.Strength.NONE;
    private int leadershipCacheExpiresAt = Integer.MIN_VALUE;
    private static final UUID HUNGER_SPEED_MODIFIER_ID = UUID.fromString("5e6cc1c3-6a18-4d35-8d4f-1112175f6574");
    private static final UUID MORALE_SPEED_MODIFIER_ID = UUID.fromString("bc899fce-50a0-4751-b24d-6849fe5182af");
    private static final UUID MORALE_DAMAGE_MODIFIER_ID = UUID.fromString("ddf6fded-c52d-4c84-9b3d-18ac254d655a");
    private static final UUID LEADERSHIP_DAMAGE_MODIFIER_ID = UUID.fromString("cf56cc95-6b51-42f8-b7cb-64f2bc61bb49");
    private static final UUID LEADERSHIP_KNOCKBACK_MODIFIER_ID = UUID.fromString("5ef8d970-cd45-40b5-9db2-ec0ef0bb77c6");
    private static final UUID SHIELD_SPEED_MODIFIER_ID = UUID.fromString("9e0c042a-37dd-40cf-bd30-27726c91c322");
    private static final UUID WEAPON_CLASS_DAMAGE_MODIFIER_ID = UUID.fromString("41df6f1f-8ed0-44e7-87fa-0f806b77e35d");
    private final MedievalSoldierModifierState modifierState = new MedievalSoldierModifierState();
    private final MedievalSoldierMaintenanceState maintenanceState = new MedievalSoldierMaintenanceState();
    private final MedievalSoldierEquipmentController equipmentController = new MedievalSoldierEquipmentController();
    private final MedievalSoldierCombatController combatController = new MedievalSoldierCombatController();
    private final MedievalSoldierRangedProjectileController rangedProjectileController = new MedievalSoldierRangedProjectileController();
    private final MedievalSoldierFireControl fireControl = new MedievalSoldierFireControl();
    private final MedievalSoldierShieldControl shieldControl = new MedievalSoldierShieldControl();
    private final MedievalSoldierOrderController orderController = new MedievalSoldierOrderController();
    private final MedievalSoldierSupplyController supplyController = new MedievalSoldierSupplyController();
    private final MedievalSoldierEatingController eatingController = new MedievalSoldierEatingController();
    private final MedievalSoldierScoutController scoutController = new MedievalSoldierScoutController();
    private final QuartermasterFarmState quartermasterFarmState = new QuartermasterFarmState();
    private final QuartermasterFarmingController quartermasterFarmingController = new QuartermasterFarmingController();
    private final QuartermasterWorkVisualState quartermasterWorkVisualState = new QuartermasterWorkVisualState();
    private final MedievalSoldierMachineResourceController machineResourceController = new MedievalSoldierMachineResourceController();
    private final MedievalSoldierFormationController formationController = new MedievalSoldierFormationController();
    private final MedievalSoldierMountedController mountedController = new MedievalSoldierMountedController();
    private final MedievalSoldierMountedState mountedRuntimeState = new MedievalSoldierMountedState();
    private final MedievalSoldierMountCommandState mountCommandState = new MedievalSoldierMountCommandState();
    private final MedievalSoldierWarningController warningController = new MedievalSoldierWarningController();
    private final class_1277 personalInventory = new class_1277(10){

        public void method_5431() {
            super.method_5431();
            MedievalSoldierEntity.this.onPersonalInventoryChanged();
        }
    };
    private long lastAppliedGroupOrderRevision = -1L;
    private long lastAppliedGroupFormationRevision = -1L;
    private long lastAppliedGroupShieldRevision = -1L;
    private long lastAppliedGroupCombatStanceRevision = -1L;
    private static final int SUPPLY_RATION_NUTRITION_TARGET = 48;

    public MedievalSoldierEntity(class_1299<? extends class_1314> type, class_1937 world) {
        super(type, world);
        this.method_5971();
        this.method_5952(true);
        this.method_5946(class_1304.field_6173, 2.0f);
        this.method_5946(class_1304.field_6171, 2.0f);
        this.method_5946(class_1304.field_6169, 2.0f);
        this.method_5946(class_1304.field_6174, 2.0f);
        this.method_5946(class_1304.field_6172, 2.0f);
        this.method_5946(class_1304.field_6166, 2.0f);
        this.maintenanceState.lastObservedMainHandItem = this.method_6047().method_7909();
    }

    public void method_5697(class_1297 entity) {
        MedievalSoldierEntity other;
        if (entity instanceof MedievalSoldierEntity && this.isFriendlySoldier(other = (MedievalSoldierEntity)entity) && (MedievalSoldierCrowdSafety.isDangerousCrowdEdge(this.method_37908(), this.method_24515()) || MedievalSoldierCrowdSafety.isDangerousCrowdEdge(this.method_37908(), other.method_24515()))) {
            return;
        }
        super.method_5697(entity);
    }

    public boolean method_5643(class_1282 source, float amount) {
        MedievalSoldierEntity friendly;
        class_1657 playerAttacker;
        class_1309 livingAttacker;
        MedievalSoldierEntity shooter;
        class_1676 projectile;
        class_1297 class_12972;
        class_1297 directSource;
        class_3218 serverWorld;
        class_1937 class_19372;
        MedievalSoldierEntity other;
        class_1297 attacker = source.method_5529();
        if (attacker instanceof MedievalSoldierEntity && this.isFriendlySoldier(other = (MedievalSoldierEntity)attacker)) {
            return false;
        }
        if (!this.method_37908().field_9236 && (class_19372 = this.method_37908()) instanceof class_3218) {
            serverWorld = (class_3218)class_19372;
            if (attacker instanceof class_3222) {
                class_3222 playerAttacker2 = (class_3222)attacker;
                KingdomDiplomaticIncidentService.onPlayerAttacksSoldier(serverWorld, playerAttacker2, this);
            }
        }
        if (!this.method_37908().field_9236 && (class_19372 = this.method_37908()) instanceof class_3218) {
            MedievalSoldierEntity soldierAttacker;
            serverWorld = (class_3218)class_19372;
            if (attacker instanceof MedievalSoldierEntity && !this.isFriendlySoldier(soldierAttacker = (MedievalSoldierEntity)attacker)) {
                KingdomDiplomaticIncidentService.onSoldierAttacksSoldier(serverWorld, soldierAttacker, this);
            }
        }
        if ((directSource = source.method_5526()) instanceof class_1676 && (class_12972 = (projectile = (class_1676)directSource).method_24921()) instanceof MedievalSoldierEntity && this.isFriendlySoldier(shooter = (MedievalSoldierEntity)class_12972)) {
            return false;
        }
        if (!(this.method_37908().field_9236 || this.getCombatStance() == NpcCombatStance.PASSIVE || !(attacker instanceof class_1309) || !(livingAttacker = (class_1309)attacker).method_5805() || livingAttacker instanceof class_1657 && this.isOwnedBy(playerAttacker = (class_1657)livingAttacker) || livingAttacker instanceof MedievalSoldierEntity && this.isFriendlySoldier(friendly = (MedievalSoldierEntity)livingAttacker))) {
            if (!OrderArmorSetBonusService.isUnshakable((class_1309)this)) {
                this.suspendCurrentOrderIfNeeded(true);
            }
            this.method_5980(livingAttacker);
            this.alertNearbyFriendlySoldiers(livingAttacker);
        }
        if (amount > 0.0f && this.canCommandShieldBlock(source)) {
            this.damageRaisedShield(amount);
            this.method_5783(class_3417.field_15150, 1.0f, 0.9f + this.method_6051().method_43057() * 0.2f);
            if (attacker instanceof class_1309 && (livingAttacker = (class_1309)attacker).method_6047().method_7909() instanceof class_1743) {
                int disabledTicks;
                this.method_6075();
                this.combatState.shieldBlockCooldownTicks = disabledTicks = MedievalSoldierShieldPolicy.disabledCooldownTicks();
                this.combatState.shieldReRaiseTicks = Math.max(this.combatState.shieldReRaiseTicks, disabledTicks);
            }
            return false;
        }
        boolean damaged = super.method_5643(source, amount);
        if (damaged) {
            this.addMorale(-0.25f);
            this.maintenanceState.registryDirty = true;
        }
        return damaged;
    }

    void alertNearbyFriendlySoldiers(class_1309 attacker) {
        class_1937 class_19372;
        if (attacker == null || !attacker.method_5805() || this.getCombatStance() == NpcCombatStance.PASSIVE || !((class_19372 = this.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        UUID groupId = this.getNpcIdentity().getSquadId();
        UUID resolvedGroupId = groupId != null ? CommandGroupManager.get(serverWorld).resolveAlias(groupId) : null;
        double horizontal = Math.min(16.0, Math.max(12.0, this.method_26825(class_5134.field_23717)));
        class_238 alertBox = this.method_5829().method_1009(horizontal, 12.0, horizontal);
        for (MedievalSoldierEntity ally : serverWorld.method_8390(MedievalSoldierEntity.class, alertBox, other -> other != this && other.method_5805() && other.getCombatStance() != NpcCombatStance.PASSIVE && this.isFriendlySoldier((MedievalSoldierEntity)other) && MedievalSoldierLocalDefensePolicy.sameCommand(this, other, resolvedGroupId, serverWorld))) {
            class_1309 current = ally.method_5968();
            if (current != null && current.method_5805() || !MedievalSoldierFormationCombatPolicy.mayJoinGroupDefense(ally, attacker) || !NpcMoraleBehaviorPolicy.mayJoinGroupDefense(ally.getMorale(), ally.hasNearbyMoraleLeader())) continue;
            ally.suspendCurrentOrderIfNeeded(true);
            if (!ally.isHoldingRangedWeapon()) {
                ally.formationState.releaseTemporarily(80);
            }
            class_1309 coordinated = MedievalSoldierGroupCombatCoordinator.coordinatedTarget(ally, attacker, !ally.isCommandedCombatTarget(attacker));
            ally.combatTargetState.resetSight();
            ally.method_5980(coordinated != null ? coordinated : attacker);
            ally.stopAdaptiveNavigation();
        }
    }

    private boolean canCommandShieldBlock(class_1282 source) {
        return this.orderController.canCommandShieldBlock(this, source);
    }

    private void damageRaisedShield(float amount) {
        this.shieldControl.damageRaisedShield(this, amount);
    }

    public void method_6105(class_1282 source, float amount) {
        if (amount <= 0.0f) {
            return;
        }
        int durabilityDamage = Math.max(1, (int)(amount / 4.0f));
        for (class_1304 slot : new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166}) {
            class_1799 stack = this.method_6118(slot);
            if (stack.method_7960() || !(stack.method_7909() instanceof class_1738) || !stack.method_7963()) continue;
            stack.method_7956(durabilityDamage, (class_1309)this, entity -> entity.method_20235(slot));
            if (!this.method_6118(slot).method_7960()) continue;
            this.equipStoredArmorAfterBreak(slot);
        }
        this.maintainFieldArmorReserve();
        this.onEquipmentInventoryChanged();
    }

    public boolean method_6121(class_1297 target) {
        class_1309 livingTarget;
        if (this.combatState.meleeAttackCooldownTicks > 0 || this.combatState.hasPendingMeleeAttack()) {
            return false;
        }
        if (!(target instanceof class_1309) || !(livingTarget = (class_1309)target).method_5805()) {
            return false;
        }
        this.method_5988().method_6226((class_1297)livingTarget, 30.0f, 30.0f);
        if (!this.isFacingCombatTarget(livingTarget)) {
            return false;
        }
        if (this.method_5858((class_1297)livingTarget) > MedievalSoldierMeleeTimingPolicy.attackReachSq(this, livingTarget)) {
            return false;
        }
        if (this.isUsingShield()) {
            this.method_6075();
            this.combatState.shieldReRaiseTicks = Math.max(this.combatState.shieldReRaiseTicks, MedievalSoldierShieldPolicy.reRaiseDelayAfterAttack(this));
        }
        this.method_6104(class_1268.field_5808);
        if (!this.method_37908().field_9236) {
            if (NpcWeaponProfileResolver.resolve(this.method_6047()).isSpearLike()) {
                this.method_37908().method_8421((class_1297)this, (byte)61);
            } else {
                this.method_37908().method_8421((class_1297)this, (byte)62);
            }
        }
        this.combatState.beginMeleeWindup(livingTarget.method_5667(), MedievalSoldierMeleeTimingPolicy.windupTicks(this));
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void tickPendingMeleeAttack() {
        boolean hit;
        class_1309 target;
        class_1937 class_19372;
        if (this.method_37908().field_9236 || !this.combatState.meleeWindupReady()) {
            return;
        }
        UUID targetId = this.combatState.consumePendingMeleeTarget();
        if (targetId == null || !((class_19372 = this.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_1297 rawTarget = serverWorld.method_14190(targetId);
        if (!(rawTarget instanceof class_1309) || !(target = (class_1309)rawTarget).method_5805()) {
            this.combatState.meleeAttackCooldownTicks = 4;
            return;
        }
        this.method_5988().method_6226((class_1297)target, 30.0f, 30.0f);
        if (this.method_5858((class_1297)target) > MedievalSoldierMeleeTimingPolicy.attackReachSq(this, target) || !this.isFacingCombatTarget(target)) {
            this.combatState.meleeAttackCooldownTicks = 4;
            return;
        }
        class_1324 attack = this.method_5996(class_5134.field_23721);
        boolean closeSpear = this.method_6047().method_7909() instanceof SpearItem && this.method_5858((class_1297)target) < 2.25;
        UUID closePenaltyId = UUID.fromString("9ec846f3-6f35-45b8-982c-9a2f640b9b73");
        if (closeSpear && attack != null && attack.method_6199(closePenaltyId) == null) {
            attack.method_26835(new class_1322(closePenaltyId, "Medieval Machinery spear close-range penalty", -0.25, class_1322.class_1323.field_6331));
        }
        try {
            hit = super.method_6121((class_1297)target);
        }
        finally {
            if (attack != null) {
                attack.method_6200(closePenaltyId);
            }
        }
        if (hit) {
            if (target instanceof class_3222) {
                class_3222 playerTarget = (class_3222)target;
                class_1937 class_19373 = this.method_37908();
                if (class_19373 instanceof class_3218) {
                    class_3218 world = (class_3218)class_19373;
                    KingdomDiplomaticIncidentService.onSoldierAttacksPlayer(world, this, playerTarget);
                }
            }
            this.damageMainHandWeapon(1);
            this.hungerData.addExhaustion(0.1f);
            this.combatState.meleeAttackCooldownTicks = this.getMeleeAttackCooldown();
        } else {
            this.combatState.meleeAttackCooldownTicks = 4;
        }
    }

    int getMeleeAttackCooldown() {
        return this.combatController.getMeleeAttackCooldown(this);
    }

    private boolean isFacingCombatTarget(class_1309 target) {
        return this.combatController.isFacingCombatTarget(this, target);
    }

    public void method_5711(byte status) {
        if (status == 61) {
            this.animationState.startSpear(this.field_6012);
            return;
        }
        if (status == 62) {
            this.animationState.startMelee(this.field_6012);
            return;
        }
        if (status == 63) {
            this.animationState.startFarmTill(this.field_6012);
            return;
        }
        if (status == 64) {
            this.animationState.startFarmPlant(this.field_6012);
            this.quartermasterWorkVisualState.usePlanting(this.getQuartermasterPlantingVisualStack());
            return;
        }
        if (status == 66) {
            this.animationState.startFarmPlant(this.field_6012);
            this.quartermasterWorkVisualState.useBoneMeal();
            return;
        }
        if (status == 65) {
            this.animationState.startFarmHarvest(this.field_6012);
            return;
        }
        super.method_5711(status);
    }

    void beginMountedSpearWindupVisual() {
        if (!this.method_37908().field_9236 && NpcWeaponProfileResolver.resolve(this.method_6047()).isSpearLike()) {
            this.method_37908().method_8421((class_1297)this, (byte)61);
        }
    }

    public float getSpearThrustAnimationProgress(float animationProgress) {
        return this.animationState.spearProgress(animationProgress, 11.0f);
    }

    public float getMeleeSwingAnimationProgress(float animationProgress) {
        return this.animationState.meleeProgress(animationProgress, 7.0f);
    }

    void beginQuartermasterWorkVisual(QuartermasterFarmingController.Action action) {
        if (this.method_37908().field_9236) {
            return;
        }
        byte status = QuartermasterWorkVisualState.statusFor(action, (byte)63, (byte)64, (byte)66, (byte)65);
        if (status != 0) {
            this.method_37908().method_8421((class_1297)this, status);
        }
    }

    public float getFarmTillAnimationProgress(float animationProgress) {
        return this.animationState.farmTillProgress(animationProgress, 12.0f);
    }

    public float getFarmPlantAnimationProgress(float animationProgress) {
        return this.animationState.farmPlantProgress(animationProgress, 9.0f);
    }

    public float getFarmHarvestAnimationProgress(float animationProgress) {
        return this.animationState.farmHarvestProgress(animationProgress, 9.0f);
    }

    public boolean isFarmPlantVisualActive(float animationProgress) {
        return this.getNpcRole() == NpcRole.QUARTERMASTER && this.getFarmPlantAnimationProgress(animationProgress) >= 0.0f;
    }

    void damageMainHandWeapon(int amount) {
        this.equipmentController.damageMainHandWeapon(this, amount);
    }

    private boolean isFriendlySoldier(MedievalSoldierEntity other) {
        if (other == null || other == this) {
            return true;
        }
        UUID owner = this.getNpcIdentity().getOwnerId();
        UUID otherOwner = other.getNpcIdentity().getOwnerId();
        if (owner != null && owner.equals(otherOwner)) {
            return true;
        }
        UUID kingdom = this.getNpcIdentity().getKingdomId();
        UUID otherKingdom = other.getNpcIdentity().getKingdomId();
        class_1937 class_19372 = this.method_37908();
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            if (kingdom != null && otherKingdom != null) {
                return KingdomRelationPolicy.areFriendly(serverWorld, kingdom, otherKingdom);
            }
        }
        UUID castle = this.getNpcIdentity().getCastleId();
        UUID otherCastle = other.getNpcIdentity().getCastleId();
        return castle != null && castle.equals(otherCastle);
    }

    double formationCombatAttackReach(class_1309 target) {
        return MedievalSoldierMeleeTimingPolicy.attackReach(this, target);
    }

    boolean isSameCommandGroup(MedievalSoldierEntity other) {
        if (other == null || other == this) {
            return other == this;
        }
        UUID ownGroup = this.getNpcIdentity().getSquadId();
        UUID otherGroup = other.getNpcIdentity().getSquadId();
        if (ownGroup == null || otherGroup == null) {
            return false;
        }
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return ownGroup.equals(otherGroup);
        }
        class_3218 serverWorld = (class_3218)class_19372;
        CommandGroupManager groups = CommandGroupManager.get(serverWorld);
        UUID ownResolved = groups.resolveAlias(ownGroup);
        UUID otherResolved = groups.resolveAlias(otherGroup);
        return ownResolved != null && ownResolved.equals(otherResolved);
    }

    boolean isFriendlyCombatEntity(class_1309 target) {
        if (target == null || target == this) {
            return true;
        }
        if (target instanceof MedievalSoldierEntity) {
            MedievalSoldierEntity other = (MedievalSoldierEntity)target;
            return this.isFriendlySoldier(other);
        }
        if (target instanceof class_1657) {
            class_1657 player = (class_1657)target;
            if (this.isOwnedBy(player)) {
                return true;
            }
            class_1937 class_19372 = this.method_37908();
            if (class_19372 instanceof class_3218) {
                class_3218 serverWorld = (class_3218)class_19372;
                UUID ownKingdom = this.getNpcIdentity().getKingdomId();
                KingdomData playerKingdom = KingdomManager.get(serverWorld).findByMember(player.method_5667());
                if (ownKingdom != null && playerKingdom != null && KingdomRelationPolicy.areFriendly(serverWorld, ownKingdom, playerKingdom.getId())) {
                    return true;
                }
            }
        }
        return target == this.method_5854() || target.method_5752().contains("medieval_machinery_mount");
    }

    public boolean canBodyguardTarget(@Nullable class_1309 target) {
        return target != null && target.method_5805() && !this.isFriendlyCombatEntity(target);
    }

    public boolean hasCommandedCombatTarget() {
        return this.isCommandedCombatTarget(this.method_5968());
    }

    public void respondToLocalAggression(class_3222 player) {
        if (player == null || !player.method_5805() || this.getCombatStance() == NpcCombatStance.PASSIVE) {
            return;
        }
        if (this.isFriendlyCombatEntity((class_1309)player)) {
            return;
        }
        if (this.hasCommandedCombatTarget()) {
            return;
        }
        this.suspendCurrentOrderIfNeeded(true);
        this.method_5980((class_1309)player);
        this.stopAdaptiveNavigation();
    }

    void reconcileDiplomacyAndLocalAggressionTarget() {
        class_1309 target = this.method_5968();
        if (target == null || !target.method_5805() || this.isCommandedCombatTarget(target)) {
            return;
        }
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        UUID ownKingdom = this.getNpcIdentity().getKingdomId();
        UUID targetKingdom = null;
        boolean activeLocalAlarm = false;
        if (target instanceof MedievalSoldierEntity) {
            MedievalSoldierEntity soldierTarget = (MedievalSoldierEntity)target;
            targetKingdom = soldierTarget.getNpcIdentity().getKingdomId();
        } else if (target instanceof class_3222) {
            class_3222 playerTarget = (class_3222)target;
            KingdomData playerKingdom = KingdomManager.get(serverWorld).findByMember(playerTarget.method_5667());
            targetKingdom = playerKingdom == null ? null : playerKingdom.getId();
            activeLocalAlarm = LocalAggressionManager.isActive(serverWorld, playerTarget.method_5667(), this.getNpcIdentity().getCastleId(), ownKingdom);
        } else {
            return;
        }
        if (activeLocalAlarm) {
            return;
        }
        if (target instanceof class_3222 && targetKingdom == null) {
            this.method_5980(null);
            this.combatTargetState.resetSight();
            this.formationCombatFinished();
            this.resumeSuspendedOrderIfPossible();
            this.stopAdaptiveNavigation();
            return;
        }
        if (ownKingdom == null || targetKingdom == null) {
            return;
        }
        if (KingdomRelationPolicy.areHostile(serverWorld, ownKingdom, targetKingdom)) {
            return;
        }
        this.method_5980(null);
        this.combatTargetState.resetSight();
        this.formationCombatFinished();
        this.resumeSuspendedOrderIfPossible();
        this.stopAdaptiveNavigation();
    }

    boolean isDiplomaticWarTarget(class_1309 target) {
        class_3218 serverWorld;
        block8: {
            block7: {
                class_1937 class_19372 = this.method_37908();
                if (!(class_19372 instanceof class_3218)) break block7;
                serverWorld = (class_3218)class_19372;
                if (target != null) break block8;
            }
            return false;
        }
        UUID ownKingdom = this.getNpcIdentity().getKingdomId();
        if (ownKingdom == null) {
            return false;
        }
        UUID targetKingdom = null;
        if (target instanceof MedievalSoldierEntity) {
            MedievalSoldierEntity soldierTarget = (MedievalSoldierEntity)target;
            targetKingdom = soldierTarget.getNpcIdentity().getKingdomId();
        } else if (target instanceof class_1657) {
            class_1657 playerTarget = (class_1657)target;
            KingdomData data = KingdomManager.get(serverWorld).findByMember(playerTarget.method_5667());
            targetKingdom = data == null ? null : data.getId();
        }
        return targetKingdom != null && KingdomRelationPolicy.areHostile(serverWorld, ownKingdom, targetKingdom);
    }

    boolean isLegalAutomaticCombatTarget(class_1309 target) {
        if (target == null || !target.method_5805() || this.isFriendlyCombatEntity(target)) {
            return false;
        }
        return switch (this.getCombatStance()) {
            default -> throw new IncompatibleClassChangeError();
            case NpcCombatStance.PASSIVE -> false;
            case NpcCombatStance.NEUTRAL -> {
                if (target == this.method_6065() || target == this.method_5968()) {
                    yield true;
                }
                yield false;
            }
            case NpcCombatStance.AGGRESSIVE -> {
                if (this.isDiplomaticWarTarget(target) || NpcTargetPolicy.isProactiveAggressiveTarget(this, target)) {
                    yield true;
                }
                yield false;
            }
            case NpcCombatStance.RAID -> !this.isRaidFriendly(target);
        };
    }

    public void method_6025(float amount) {
        float before = this.method_6032();
        super.method_6025(amount);
        if (this.method_6032() != before) {
            this.maintenanceState.registryDirty = true;
        }
    }

    protected void method_5693() {
        super.method_5693();
        this.field_6011.method_12784(TRACKED_ORDER_STYLE, OrderStyle.NONE.ordinal());
        this.field_6011.method_12784(TRACKED_ARMOR_TIER, ArmorTier.ORDER.ordinal());
        this.field_6011.method_12784(TRACKED_ROLE, NpcRole.SWORDSMAN.ordinal());
        this.field_6011.method_12784(TRACKED_TARGET_PRIORITY, NpcTargetPriority.CLOSEST.ordinal());
        this.field_6011.method_12784(TRACKED_PREFERRED_MELEE_WEAPON, NpcPreferredMeleeWeapon.SWORD.ordinal());
        this.field_6011.method_12784(TRACKED_RANK, NpcRank.NONE.ordinal());
        this.field_6011.method_12784(TRACKED_ARMOR_STYLE_OVERRIDE, -1);
        this.field_6011.method_12784(TRACKED_LEVEL, 1);
        this.field_6011.method_12784(TRACKED_EXPERIENCE, 0);
        this.field_6011.method_12784(TRACKED_MORALE, Float.valueOf(50.0f));
        this.field_6011.method_12784(TRACKED_CASTLE_NAME, "-");
        this.field_6011.method_12784(TRACKED_ORDER_TYPE, NpcOrderType.IDLE.ordinal());
        this.field_6011.method_12784(TRACKED_COMBAT_STANCE, NpcCombatStance.NEUTRAL.ordinal());
        this.field_6011.method_12784(TRACKED_HUNGER, 20);
        this.field_6011.method_12784(TRACKED_RANGED_ATTACK_TICKS, 0);
        this.field_6011.method_12784(TRACKED_RANGED_VISUAL_STATE, NpcRangedVisualState.IDLE.ordinal());
        this.field_6011.method_12784(TRACKED_FORMATION, NpcFormation.FREE.ordinal());
        this.field_6011.method_12784(TRACKED_SHIELD_MODE, NpcShieldMode.AUTO.ordinal());
        this.field_6011.method_12784(TRACKED_BODYGUARD_DUTY, false);
        this.field_6011.method_12784(TRACKED_QUARTERMASTER_FIELD_COUNT, 0);
        this.field_6011.method_12784(TRACKED_QUARTERMASTER_COMPOSTER, false);
        this.field_6011.method_12784(TRACKED_QUARTERMASTER_COMPOSTING, false);
        this.field_6011.method_12784(TRACKED_QUARTERMASTER_PLANT_ITEM, 0);
        this.field_6011.method_12784(TRACKED_MACHINE_HELD_VISUAL, class_1799.field_8037);
    }

    public void equipRecruitStarterWeapon() {
        class_1799 weapon;
        NpcRole role = this.getNpcRole();
        switch (role) {
            default: {
                throw new IncompatibleClassChangeError();
            }
            case SWORDSMAN: 
            case KNIGHT: 
            case CANNONEER: 
            case SCOUT: {
                weapon = new class_1799((class_1935)class_1802.field_8091);
                break;
            }
            case AXEMAN: 
            case ENGINEER: {
                weapon = new class_1799((class_1935)class_1802.field_8406);
                break;
            }
            case QUARTERMASTER: {
                weapon = new class_1799((class_1935)class_1802.field_8167);
                break;
            }
            case ARCHER: 
            case MOUNTED_ARCHER: {
                weapon = new class_1799((class_1935)class_1802.field_8102);
                break;
            }
            case CROSSBOWMAN: {
                weapon = new class_1799((class_1935)class_1802.field_8399);
                break;
            }
            case RECRUIT: {
                if (this.method_6051().method_43056()) {
                    weapon = new class_1799((class_1935)class_1802.field_8091);
                    break;
                }
                weapon = new class_1799((class_1935)class_1802.field_8406);
                break;
            }
            case SPEARMAN: 
            case MOUNTED_KNIGHT: {
                weapon = new class_1799((class_1935)ModItems.WOOD_WAR_SPEAR);
            }
        }
        if (!weapon.method_7960()) {
            this.method_5673(class_1304.field_6173, weapon);
        }
        this.onEquipmentInventoryChanged();
        this.maintenanceState.equipmentDirty = true;
        this.maintenanceState.registryDirty = true;
    }

    public class_1799 method_18808(class_1799 weaponStack) {
        if (weaponStack != null && NpcWeaponProfileResolver.resolve(weaponStack).isRanged()) {
            return new class_1799((class_1935)class_1802.field_8107);
        }
        return super.method_18808(weaponStack);
    }

    public void giveRecruitStarterFood() {
        this.insertIntoPersonalStorage(new class_1799((class_1935)class_1802.field_8229, 6));
        if (this.getNpcRole() == NpcRole.QUARTERMASTER) {
            this.insertIntoPersonalStorage(new class_1799((class_1935)class_1802.field_8317, 64));
            if (this.personalInventory.method_5438(9).method_7960()) {
                this.personalInventory.method_5447(9, new class_1799((class_1935)class_1802.field_8317));
                this.personalInventory.method_5431();
            }
        }
    }

    public void rollRecruitMaxHealth() {
        class_1324 maxHealth = this.method_5996(class_5134.field_23716);
        if (maxHealth == null) {
            return;
        }
        double rolled = 20.0 + (double)this.method_6051().method_43048(11);
        maxHealth.method_6192(rolled);
        this.applyLevelProgressionModifiers();
        this.method_6033(this.method_6063());
        this.maintenanceState.registryDirty = true;
    }

    public static class_5132.class_5133 createAttributes() {
        return class_1308.method_26828().method_26868(class_5134.field_23716, 20.0).method_26868(class_5134.field_23719, 0.3).method_26868(class_5134.field_23717, 32.0).method_26868(class_5134.field_23721, 2.0).method_26868(class_5134.field_23723, 4.0);
    }

    public void method_5773() {
        class_3218 serverWorld;
        class_1309 currentTarget;
        super.method_5773();
        if (this.method_37908().field_9236) {
            return;
        }
        if (!this.method_5805() || this.method_6032() <= 0.0f) {
            this.cleanupOrganizationAfterRemoval();
            return;
        }
        OrderArmorSetBonusService.tickServer((class_1309)this);
        this.tickRangedCloseCombatWeaponState();
        this.warningController.tickWeaponShortage(this);
        this.warningController.tickFoodShortage(this);
        if (this.isStaggeredAiTick(200)) {
            this.updateMoraleState();
        }
        this.recoveryState.observe(this);
        this.recoveryState.cleanupPlacedBlocksIfFinished(this);
        this.navigationInteractionState.tick(this);
        this.combatTargetState.tickManualForget();
        if (!(!this.combatState.hasCommandedTarget() || (currentTarget = this.method_5968()) != null && currentTarget.method_5805() && this.combatState.isCommandedTarget(currentTarget.method_5667()))) {
            this.combatState.clearCommandedTarget();
        }
        this.combatState.tickCooldowns();
        this.tickPendingMeleeAttack();
        this.commandState.tick();
        class_1937 class_19372 = this.method_37908();
        if (class_19372 instanceof class_3218) {
            class_3218 transferWorld = (class_3218)class_19372;
            CastleTransferController.tick(transferWorld, this);
        }
        if ((class_19372 = this.method_37908()) instanceof class_3218) {
            class_3218 bodyguardWorld = (class_3218)class_19372;
            GeneralBodyguardService.tickGuard(bodyguardWorld, this);
        }
        this.formationState.tick();
        this.mountCommandState.tick();
        this.tickFormationPassageState();
        this.mountedController.tick(this);
        this.combatMovementState.tick();
        MedievalSoldierPatrolCombatPolicy.tick(this);
        this.combatController.tickServerAwareness(this);
        this.tickShieldBehaviour();
        if (this.inventoryGuiViewers > 0 && this.inventoryGuiMovementLocked) {
            this.stopAdaptiveNavigation();
            this.method_18800(0.0, this.method_18798().field_1351, 0.0);
        }
        this.hungerData.tick(this);
        this.field_6011.method_12778(TRACKED_HUNGER, this.hungerData.getFoodLevel());
        this.updateHungerMovementModifier();
        this.eatingController.tick(this);
        this.scoutController.tick(this);
        this.quartermasterFarmingController.tickPassive(this);
        this.autonomousSupplyPolicy.tick(this);
        if (this.supplyState.request != NpcSupplyRequest.NONE) {
            this.tickExplicitSupplyJob();
        } else if (this.supplyState.isReturningToOrigin()) {
            this.tickSupplyReturnToOrigin();
        }
        if (this.supplyState.request == NpcSupplyRequest.NONE && !this.supplyState.isReturningToOrigin()) {
            this.tickHoldPositionDisplacement();
        }
        this.equipmentController.tickOpportunisticNearbyPickup(this);
        this.equipmentController.tickEventDrivenMaintenance(this);
        if (this.maintenanceState.organizationDirty && (class_19372 = this.method_37908()) instanceof class_3218) {
            serverWorld = (class_3218)class_19372;
            this.maintenanceState.organizationDirty = false;
            this.validateCastleAssignment();
            CommandGroupManager.get(serverWorld).reconcileMembership(this);
            this.maintenanceState.registryDirty = true;
        }
        if (this.maintenanceState.registryDirty && (class_19372 = this.method_37908()) instanceof class_3218) {
            serverWorld = (class_3218)class_19372;
            this.maintenanceState.registryDirty = false;
            SoldierRegistryManager.get(serverWorld).update(this);
        }
        if (!this.method_37908().field_9236 && this.getRangedAttackTicks() > 0) {
            int nextRangedTicks = this.getRangedAttackTicks() - 1;
            this.field_6011.method_12778(TRACKED_RANGED_ATTACK_TICKS, nextRangedTicks);
            if (nextRangedTicks <= 0) {
                this.field_6011.method_12778(TRACKED_RANGED_VISUAL_STATE, NpcRangedVisualState.IDLE.ordinal());
            }
        }
        this.combatController.tickTargetLifecycle(this);
        this.npcDebug.tick();
    }

    public boolean isStaggeredAiTick(int interval) {
        if (interval <= 1) {
            return true;
        }
        return Math.floorMod(this.field_6012 + Math.floorMod(this.method_5667().hashCode(), interval), interval) == 0;
    }

    void onPersonalInventoryChanged() {
        this.equipmentController.onPersonalInventoryChanged(this);
        if (!this.method_37908().field_9236 && this.getNpcRole() == NpcRole.QUARTERMASTER) {
            class_1799 selected = this.personalInventory.method_5438(9);
            this.field_6011.method_12778(TRACKED_QUARTERMASTER_PLANT_ITEM, (selected.method_7960() ? 0 : class_7923.field_41178.method_10206(selected.method_7909())));
        }
    }

    public void onEquipmentInventoryChanged() {
        this.maintenanceState.equipmentDirty = true;
        this.maintenanceState.registryDirty = true;
    }

    public void markOrganizationDirty() {
        this.maintenanceState.organizationDirty = true;
        this.maintenanceState.registryDirty = true;
    }

    private void refreshInventoryCachesIfNeeded() {
        if (!this.maintenanceState.inventoryCacheDirty) {
            return;
        }
        this.maintenanceState.inventoryCacheDirty = false;
        boolean hasFood = false;
        for (int slot = 0; slot < Math.min(9, this.personalInventory.method_5439()); ++slot) {
            class_1799 stack = this.personalInventory.method_5438(slot);
            if (stack.method_7960() || !stack.method_19267()) continue;
            hasFood = true;
            break;
        }
        this.maintenanceState.cachedHasFood = hasFood;
    }

    boolean hasUsableShield() {
        return this.combatController.hasUsableShield(this);
    }

    boolean isUsingShield() {
        return this.method_6115() && this.method_6058() == class_1268.field_5810 && this.method_6030().method_7909() instanceof class_1819;
    }

    private void tickShieldBehaviour() {
        this.shieldControl.tickShieldBehaviour(this);
    }

    void updateShieldMovementModifier(boolean shieldActive) {
        this.shieldControl.updateShieldMovementModifier(this, shieldActive);
    }

    boolean shouldAutoRaiseShield(@Nullable class_1309 target) {
        return this.shieldControl.shouldAutoRaiseShield(this, target);
    }

    public boolean method_6039() {
        if (this.getShieldMode() == NpcShieldMode.RAISED && this.combatState.shieldReRaiseTicks <= 0 && this.combatState.shieldBlockCooldownTicks <= 0 && this.hasUsableShield() && this.isUsingShield()) {
            return true;
        }
        return super.method_6039();
    }

    public void assembleFormationAt(class_243 target) {
        this.formationController.beginExplicitAssembly(this, target);
    }

    public void requestSupplyRun(List<class_2338> chestPositions, NpcSupplyRequest request) {
        if (this.method_37908().field_9236 || chestPositions == null || request == null || !request.isRun()) {
            return;
        }
        if (this.method_5968() != null && this.method_5968().method_5805()) {
            if (request != NpcSupplyRequest.SUPPLY) {
                return;
            }
            this.forgetCurrentTarget();
        }
        ArrayList<class_2338> usable = new ArrayList<class_2338>();
        for (class_2338 chest : chestPositions) {
            if (chest == null || usable.contains(chest)) continue;
            usable.add(chest.method_10062());
        }
        if (usable.isEmpty()) {
            return;
        }
        usable.sort((a, b) -> Double.compare(this.method_5649((double)a.method_10263() + 0.5, (double)a.method_10264() + 0.5, (double)a.method_10260() + 0.5), this.method_5649((double)b.method_10263() + 0.5, (double)b.method_10264() + 0.5, (double)b.method_10260() + 0.5)));
        int rotate = Math.floorMod(this.method_5667().hashCode(), usable.size());
        if (rotate != 0) {
            ArrayList rotated = new ArrayList(usable.size());
            rotated.addAll(usable.subList(rotate, usable.size()));
            rotated.addAll(usable.subList(0, rotate));
            usable = rotated;
        }
        if (request == NpcSupplyRequest.SUPPLY) {
            this.orderController.clearSuspendedOrderSnapshot(this);
        }
        this.supplyState.begin(usable, request, this.getOrderType(), this.commandDestination, this.holdPosition, this.method_19538(), 4 + Math.floorMod(this.method_5667().hashCode(), 32));
        NpcMachineAssignmentService.releaseOperatorClaim(this);
        this.stopAdaptiveNavigation();
    }

    public void requestSupplyRun(class_2338 chestPos, NpcSupplyRequest request) {
        this.requestSupplyRun(chestPos == null ? List.of() : List.of(chestPos), request);
    }

    public void cancelTemporarySupplyForNewCommand() {
        if (this.supplyState.request == NpcSupplyRequest.NONE && !this.supplyState.isReturningToOrigin()) {
            return;
        }
        this.closeSupplyChestVisual();
        this.supplyState.cancelAll();
        this.stopAdaptiveNavigation();
    }

    public boolean hasActiveSupplyRun() {
        return this.supplyState.request.isRun() && this.supplyState.hasChest();
    }

    MedievalSoldierAdaptiveNavigation adaptiveNavigation() {
        return this.adaptiveNavigation;
    }

    MedievalSoldierDebug npcDebug() {
        return this.npcDebug;
    }

    public void stopAdaptiveNavigation() {
        this.adaptiveNavigation.stop();
    }

    boolean hasSupplyMovementOverride() {
        return this.supplyState.request.isRun() || this.supplyState.isReturningToOrigin();
    }

    void openSupplyChestVisual() {
        this.supplyController.openSupplyChestVisual(this);
    }

    void closeSupplyChestVisual() {
        this.supplyController.closeSupplyChestVisual(this);
    }

    private void advanceSupplyChestOrFinish() {
        this.supplyController.advanceSupplyChestOrFinish(this);
    }

    void finishSupplyRun() {
        this.supplyController.finishSupplyRun(this);
    }

    void restoreOrderAfterSupply() {
        this.supplyState.returningToOrigin = false;
        NpcOrderType restore = this.supplyState.orderBefore;
        class_243 restoreHold = restore == NpcOrderType.HOLD_POSITION || restore == NpcOrderType.OBSERVATION_POINT ? (this.supplyState.holdPositionBefore != null ? this.supplyState.holdPositionBefore : this.supplyState.originPosition) : null;
        this.orderController.restoreTemporaryOrderState(this, restore, this.supplyState.commandDestinationBefore, restoreHold);
        this.supplyState.commandDestinationBefore = null;
        this.supplyState.holdPositionBefore = null;
        this.supplyState.originPosition = null;
    }

    private void tickSupplyReturnToOrigin() {
        double dz;
        if (this.supplyState.originPosition == null) {
            this.restoreOrderAfterSupply();
            return;
        }
        if (this.method_5968() != null && this.method_5968().method_5805()) {
            return;
        }
        double dx = this.supplyState.originPosition.field_1352 - this.method_23317();
        if (dx * dx + (dz = this.supplyState.originPosition.field_1350 - this.method_23321()) * dz <= 0.36 && Math.abs(this.supplyState.originPosition.field_1351 - this.method_23318()) <= 0.75) {
            this.supplyState.movement.stop();
            this.restoreOrderAfterSupply();
            return;
        }
        this.supplyState.movement.tickToPoint(this.supplyState.originPosition, 1.0);
    }

    class_243 findSupplyInteractionPoint(class_2338 chest) {
        return this.supplyController.findSupplyInteractionPoint(this, chest);
    }

    private boolean canPhysicallyInteractWithSupplyChest(class_2338 chest) {
        return this.supplyController.canPhysicallyInteractWithSupplyChest(this, chest);
    }

    private void tickExplicitSupplyJob() {
        boolean besideReachableChest;
        class_3218 serverWorld;
        block25: {
            block24: {
                class_1937 class_19372 = this.method_37908();
                if (!(class_19372 instanceof class_3218)) break block24;
                serverWorld = (class_3218)class_19372;
                if (this.supplyState.chest != null) break block25;
            }
            this.finishSupplyRun();
            return;
        }
        class_2586 blockEntity = serverWorld.method_8321(this.supplyState.chest);
        class_1263 inventory = this.resolveSupplyInventory(serverWorld, this.supplyState.chest);
        if (blockEntity == null || inventory == null) {
            this.advanceSupplyChestOrFinish();
            return;
        }
        if (this.method_5968() != null && this.method_5968().method_5805()) {
            if (this.supplyState.request == NpcSupplyRequest.SUPPLY) {
                this.forgetCurrentTarget();
            } else {
                this.closeSupplyChestVisual();
                this.supplyState.interactionTicks = 0;
                return;
            }
        }
        if (this.supplyState.startDelay > 0) {
            --this.supplyState.startDelay;
            return;
        }
        class_243 interactionPoint = this.findSupplyInteractionPoint(this.supplyState.chest);
        double pdx = interactionPoint.field_1352 - this.method_23317();
        double pdy = interactionPoint.field_1351 - this.method_23318();
        double pdz = interactionPoint.field_1350 - this.method_23321();
        double pointDistanceSq = pdx * pdx + pdz * pdz;
        double chestDx = (double)this.supplyState.chest.method_10263() + 0.5 - this.method_23317();
        double chestDy = (double)this.supplyState.chest.method_10264() + 0.5 - this.method_23318();
        double chestDz = (double)this.supplyState.chest.method_10260() + 0.5 - this.method_23321();
        double chestDistanceSq = chestDx * chestDx + chestDy * chestDy + chestDz * chestDz;
        if (this.supplyState.interactionTicks > 0) {
            if (chestDistanceSq > 25.0 || Math.abs(chestDy) > 2.5) {
                this.closeSupplyChestVisual();
                this.supplyState.interactionTicks = 0;
                return;
            }
            this.supplyState.movement.stop();
            this.method_5988().method_20248((double)this.supplyState.chest.method_10263() + 0.5, (double)this.supplyState.chest.method_10264() + 0.5, (double)this.supplyState.chest.method_10260() + 0.5);
            if (--this.supplyState.interactionTicks > 0) {
                return;
            }
            this.closeSupplyChestVisual();
            if (this.supplyController.serviceCurrentSupplyRequest(this, blockEntity, inventory)) {
                this.finishSupplyRun();
            } else {
                this.advanceSupplyChestOrFinish();
            }
            return;
        }
        boolean bl = besideReachableChest = chestDistanceSq <= (this.isActivelyMountedRole() ? 10.24 : 7.84) && Math.abs(chestDy) <= 2.0 && this.canPhysicallyInteractWithSupplyChest(this.supplyState.chest);
        if (besideReachableChest) {
            this.supplyState.approachTicks = 0;
        } else if (chestDistanceSq <= 25.0) {
            ++this.supplyState.approachTicks;
            if (this.supplyState.approachTicks > 160) {
                this.advanceSupplyChestOrFinish();
                return;
            }
        } else {
            this.supplyState.approachTicks = 0;
        }
        if (!besideReachableChest) {
            double d = this.isActivelyMountedRole() ? 4.0 : 1.8225000000000002;
            if (pointDistanceSq > d || Math.abs(pdy) > 1.5 || chestDistanceSq > 16.0) {
                this.closeSupplyChestVisual();
                if (!this.isActivelyMountedRole()) {
                    this.supplyState.movement.tickToPoint(interactionPoint, 1.0);
                }
                return;
            }
        }
        if (!this.canPhysicallyInteractWithSupplyChest(this.supplyState.chest)) {
            this.closeSupplyChestVisual();
            if (!this.isActivelyMountedRole()) {
                this.supplyState.movement.tickToPoint(interactionPoint, 1.0);
            }
            return;
        }
        this.supplyState.movement.stop();
        this.method_5988().method_20248((double)this.supplyState.chest.method_10263() + 0.5, (double)this.supplyState.chest.method_10264() + 0.5, (double)this.supplyState.chest.method_10260() + 0.5);
        this.supplyState.interactionTicks = 18;
        this.supplyState.approachTicks = 0;
        this.openSupplyChestVisual();
        this.method_6104(class_1268.field_5808);
    }

    @Nullable
    private class_1263 resolveSupplyInventory(class_3218 world, class_2338 pos) {
        return this.supplyController.resolveSupplyInventory(this, world, pos);
    }

    private boolean takeFoodFromSupply(class_1263 inventory) {
        return this.supplyController.takeFoodFromSupply(this, inventory);
    }

    int carriedFoodNutrition() {
        return this.supplyController.carriedFoodNutrition(this);
    }

    private boolean takeEquipmentFromSupply(class_1263 inventory) {
        return this.supplyController.takeEquipmentFromSupply(this, inventory);
    }

    boolean sameWeaponFamily(class_1799 a, class_1799 b) {
        return this.equipmentController.sameWeaponFamily(this, a, b);
    }

    private int countStoredWeaponFamily(class_1799 reference) {
        return this.equipmentController.countStoredWeaponFamily(this, reference);
    }

    boolean ensureReplacementWeaponFromSupply(class_1263 inventory) {
        return this.supplyController.ensureReplacementWeaponFromSupply(this, inventory);
    }

    boolean isProtectedReserveWeapon(class_1799 stack, int slot, int protectedFamilySlot, int protectedSidearmSlot) {
        return this.equipmentController.isProtectedReserveWeapon(this, stack, slot, protectedFamilySlot, protectedSidearmSlot);
    }

    boolean returnExcessEquipmentToSupply(class_1263 inventory) {
        return this.supplyController.returnExcessEquipmentToSupply(this, inventory);
    }

    boolean insertIntoSupplyInventory(class_1263 inventory, class_1799 stack) {
        return this.supplyController.insertIntoSupplyInventory(this, inventory, stack);
    }

    int findBestSupplyArmorSlot(class_1263 inventory, class_1304 equipSlot) {
        return this.supplyController.findBestSupplyArmorSlot(this, inventory, equipSlot);
    }

    boolean isBetterArmorPiece(class_1799 candidate, class_1799 current) {
        return this.equipmentController.isBetterArmorPiece(this, candidate, current);
    }

    boolean hasCarriedCloseCombatSidearm() {
        return this.equipmentController.hasCarriedCloseCombatSidearm(this);
    }

    int chooseReserveSidearmSlot(class_1263 inventory) {
        return this.equipmentController.chooseReserveSidearmSlot(this, inventory);
    }

    public int getHungerLevel() {
        return (Integer)this.field_6011.method_12789(TRACKED_HUNGER);
    }

    public float getHungerSaturation() {
        return this.hungerData.getSaturationLevel();
    }

    public NpcHungerState getHungerState() {
        return this.hungerData.getState();
    }

    private void updateHungerMovementModifier() {
        double penalty;
        class_1324 speed = this.method_5996(class_5134.field_23719);
        if (speed == null) {
            return;
        }
        int food = this.hungerData.getFoodLevel();
        penalty = food <= 2 ? -0.3 : (food <= 6 ? -0.15 : 0.0);
        if (Double.compare(penalty, this.modifierState.hungerSpeedPenalty) == 0) {
            return;
        }
        class_1322 old = speed.method_6199(HUNGER_SPEED_MODIFIER_ID);
        if (old != null) {
            speed.method_6200(HUNGER_SPEED_MODIFIER_ID);
        }
        if (penalty != 0.0) {
            speed.method_26835(new class_1322(HUNGER_SPEED_MODIFIER_ID, "Medieval Machinery hunger movement penalty", penalty, class_1322.class_1323.field_6331));
        }
        this.modifierState.hungerSpeedPenalty = penalty;
    }

    boolean beginEatingBestFood() {
        return this.supplyController.beginEatingBestFood(this);
    }

    void tickEatingUse() {
        this.eatingController.tickEatingUse(this);
    }

    void finishEating(boolean completed) {
        this.eatingController.finishEating(this, completed);
    }

    void syncTrackedHunger() {
        this.field_6011.method_12778(TRACKED_HUNGER, this.hungerData.getFoodLevel());
    }

    boolean isBusyWithCombatForEating() {
        if (this.method_5968() != null) {
            return true;
        }
        class_1309 attacker = this.method_6065();
        return attacker != null && attacker.method_5805() && this.method_5858((class_1297)attacker) < 144.0;
    }

    boolean hasFoodInStorage() {
        this.refreshInventoryCachesIfNeeded();
        return this.maintenanceState.cachedHasFood;
    }

    public boolean hasFoodCached() {
        this.refreshInventoryCachesIfNeeded();
        return this.maintenanceState.cachedHasFood;
    }

    public boolean method_5939(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return false;
        }
        if (NpcEquipmentProfile.isSupportedWeapon(stack) && NpcEquipmentProfile.acceptsMainHand(this.getNpcRole(), stack)) {
            return this.canAcceptWeapon(stack);
        }
        class_1792 class_17922 = stack.method_7909();
        if (class_17922 instanceof class_1738) {
            class_1738 armor = (class_1738)class_17922;
            if (this.method_6118(armor.method_7685()).method_7960() && MedievalArmorItem.canNpcEquip(this, stack)) {
                return true;
            }
            return this.equipmentController.hasStorageCapacityFor(this, stack);
        }
        if (stack.method_19267()) {
            return this.equipmentController.hasStorageCapacityFor(this, stack);
        }
        if (this.getNpcRole() == NpcRole.QUARTERMASTER && this.getOrderType() == NpcOrderType.WORK && QuartermasterCropPolicy.isFarmPickup(stack)) {
            return this.equipmentController.hasStorageCapacityFor(this, stack);
        }
        return false;
    }

    protected void method_5949(class_1542 item) {
        class_1738 armor;
        class_1792 class_17922;
        if (item == null || item.method_31481()) {
            return;
        }
        class_1799 ground = item.method_6983();
        if (ground.method_7960() || !this.method_5939(ground)) {
            return;
        }
        if (NpcEquipmentProfile.isSupportedWeapon(ground) && NpcEquipmentProfile.acceptsMainHand(this.getNpcRole(), ground) && this.pickupWeapon(item)) {
            return;
        }
        if (MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled && (class_17922 = ground.method_7909()) instanceof class_1738 && this.method_6118((armor = (class_1738)class_17922).method_7685()).method_7960() && MedievalArmorItem.canNpcEquip(this, ground)) {
            class_1799 equipped = ground.method_7972();
            equipped.method_7939(1);
            this.method_5673(armor.method_7685(), equipped);
            this.onEquipmentInventoryChanged();
            ground.method_7934(1);
            this.method_6103((class_1297)item, 1);
            if (ground.method_7960()) {
                item.method_31472();
            } else {
                item.method_6979(ground);
            }
            return;
        }
        this.storeGroundStack(item);
    }

    private boolean canAcceptWeapon(class_1799 candidate) {
        if (candidate == null || candidate.method_7960()) {
            return false;
        }
        if (!MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled) {
            return this.equipmentController.hasStorageCapacityFor(this, candidate);
        }
        class_1799 mainHand = this.method_6047();
        if (this.isBetterWeapon(candidate, mainHand)) {
            return true;
        }
        if (this.equipmentController.hasStorageCapacityFor(this, candidate)) {
            return true;
        }
        int worst = this.findWorstStoredWeaponSlot();
        return worst >= 0 && this.getWeaponScore(candidate) > this.getWeaponScore(this.personalInventory.method_5438(worst));
    }

    private boolean pickupWeapon(class_1542 item) {
        return this.equipmentController.pickupWeapon(this, item);
    }

    private void maintainFieldWeaponReserve() {
        this.equipmentController.maintainFieldWeaponReserve(this);
    }

    private void equipBestFieldReplacement(class_1799 brokenWeapon) {
        this.equipmentController.equipBestFieldReplacement(this, brokenWeapon);
    }

    private int findBestStoredReplacementSlot(class_1799 reference, boolean requireSameFamily) {
        return this.equipmentController.findBestStoredReplacementSlot(this, reference, requireSameFamily);
    }

    boolean isCriticallyWornArmor(class_1799 stack) {
        return this.equipmentController.isCriticallyWornArmor(this, stack);
    }

    long storedArmorScore(class_1799 stack) {
        return this.equipmentController.storedArmorScore(this, stack);
    }

    int findBestStoredArmorReplacementSlot(class_1304 equipmentSlot) {
        return this.equipmentController.findBestStoredArmorReplacementSlot(this, equipmentSlot);
    }

    private void maintainFieldArmorReserve() {
        this.equipmentController.maintainFieldArmorReserve(this);
    }

    private void equipStoredArmorAfterBreak(class_1304 equipmentSlot) {
        this.equipmentController.equipStoredArmorAfterBreak(this, equipmentSlot);
    }

    void equipBestWeaponFromInventory() {
        int bestSlot = -1;
        class_1799 best = this.method_6047();
        for (int slot = 0; slot < Math.min(9, this.personalInventory.method_5439()); ++slot) {
            class_1799 stack = this.personalInventory.method_5438(slot);
            if (!NpcEquipmentProfile.acceptsMainHand(this.getNpcRole(), stack) || !(bestSlot < 0 ? this.isBetterWeapon(stack, best) : this.isBetterWeapon(stack, this.personalInventory.method_5438(bestSlot)))) continue;
            bestSlot = slot;
        }
        if (bestSlot < 0) {
            return;
        }
        class_1799 replacement = this.personalInventory.method_5441(bestSlot);
        if (replacement.method_7960()) {
            return;
        }
        class_1799 previous = this.method_6047().method_7972();
        this.method_5673(class_1304.field_6173, replacement);
        this.maintenanceState.registryDirty = true;
        if (!previous.method_7960()) {
            this.personalInventory.method_5447(bestSlot, previous);
        }
        this.personalInventory.method_5431();
        this.updateWeaponClassDamageModifier();
    }

    boolean isPersonalStorageFull() {
        return this.equipmentController.isPersonalStorageFull(this);
    }

    int insertIntoPersonalStorageAndCount(class_1799 stack) {
        return this.equipmentController.insertIntoPersonalStorageAndCount(this, stack);
    }

    boolean insertIntoPersonalStorage(class_1799 stack) {
        return this.equipmentController.insertIntoPersonalStorage(this, stack);
    }

    int findWorstStoredWeaponSlot() {
        return this.equipmentController.findWorstStoredWeaponSlot(this);
    }

    private void storeGroundStack(class_1542 item) {
        class_1799 ground = item.method_6983();
        int collected = this.insertIntoPersonalStorageAndCount(ground);
        if (collected <= 0) {
            return;
        }
        this.method_6103((class_1297)item, collected);
        ground.method_7934(collected);
        if (ground.method_7960()) {
            item.method_31472();
        } else {
            item.method_6979(ground);
        }
    }

    void updateWeaponClassDamageModifier() {
        double multiplier = NpcEquipmentProfile.damageMultiplier(this.getNpcRole(), this.method_6047());
        if (Double.compare(multiplier, this.modifierState.weaponClassMultiplier) == 0) {
            return;
        }
        this.modifierState.weaponClassMultiplier = multiplier;
        class_1324 attribute = this.method_5996(class_5134.field_23721);
        if (attribute == null) {
            return;
        }
        attribute.method_6200(WEAPON_CLASS_DAMAGE_MODIFIER_ID);
        double amount = multiplier - 1.0;
        if (Math.abs(amount) > 1.0E-4) {
            attribute.method_26835(new class_1322(WEAPON_CLASS_DAMAGE_MODIFIER_ID, "Medieval Machinery class weapon efficiency", amount, class_1322.class_1323.field_6331));
        }
    }

    private void validateCastleAssignment() {
        boolean membershipRemoved;
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        UUID castleId = this.identity.getCastleId();
        GarrisonData assignedGarrison = castleId == null ? null : GarrisonManager.get(serverWorld).get(castleId);
        boolean castleMissing = castleId != null && assignedGarrison == null;
        boolean bl = membershipRemoved = castleId != null && assignedGarrison != null && !assignedGarrison.getSoldierIds().contains(this.method_5667());
        if (castleMissing || membershipRemoved) {
            this.clearInvalidCastleAssignment(serverWorld);
        }
    }

    private void clearInvalidCastleAssignment(class_3218 serverWorld) {
        this.identity.setCastleId(null);
        UUID groupId = this.identity.getSquadId();
        if (groupId == null || CommandGroupManager.get(serverWorld).get(groupId) == null) {
            this.identity.setSquadId(null);
            this.setFormationSlot(-1);
        }
        this.setHomePosition(null);
        this.maintenanceState.registryDirty = true;
    }

    MedievalSoldierEatingState goalEatingState() {
        return this.eatingState;
    }

    class_243 goalHoldPosition() {
        return this.holdPosition;
    }

    NpcHungerData goalHungerData() {
        return this.hungerData;
    }

    @Nullable
    class_243 goalIdleWanderAnchor() {
        return this.idleWanderAnchor;
    }

    class_2338 goalCommandDestination() {
        return this.commandDestination;
    }

    MedievalSoldierMountedState mountedState() {
        return this.mountedRuntimeState;
    }

    class_243 mountedHoldPosition() {
        return this.holdPosition;
    }

    boolean isActivelyMountedRole() {
        return this.mountedController.isActivelyMounted(this);
    }

    boolean mountedCanSee(class_1309 target) {
        return target != null && this.method_5985().method_6369((class_1297)target);
    }

    public void createRecruitMount() {
        this.mountedController.createRecruitMount(this);
    }

    NpcFormationState formationStateForController() {
        return this.formationState;
    }

    NpcIdentity identityForController() {
        return this.identity;
    }

    NpcCombatTargetState combatTargetStateForController() {
        return this.combatTargetState;
    }

    MedievalSoldierCombatState combatStateForController() {
        return this.combatState;
    }

    void formationSetHoldTarget(class_243 target) {
        if (target == null) {
            return;
        }
        this.holdPosition = target;
        this.holdDisplacedTicks = 0;
    }

    void formationTakeOverMove(class_243 target) {
        if (target == null) {
            return;
        }
        if (this.getOrderType() == NpcOrderType.MOVE_TO_POSITION) {
            this.commandDestination = null;
            this.setOrderType(NpcOrderType.HOLD_POSITION);
        }
        this.holdPosition = target;
        this.holdDisplacedTicks = 0;
    }

    void formationFinishAssembly(class_243 target) {
        this.formationController.finishExplicitAssembly(this, target);
    }

    void formationCombatFinished() {
        this.formationController.onCombatFinished(this);
    }

    NpcFormationState goalFormationState() {
        return this.formationState;
    }

    int goalHoldDisplacedTicks() {
        return this.holdDisplacedTicks;
    }

    class_2338 goalHomePosition() {
        return this.homePosition;
    }

    PatrolState goalPatrolState() {
        return this.patrolState;
    }

    MedievalSoldierSupplyState goalSupplyState() {
        return this.supplyState;
    }

    NpcCombatMovementState goalCombatMovementState() {
        return this.combatMovementState;
    }

    MedievalSoldierCombatState goalCombatState() {
        return this.combatState;
    }

    MedievalSoldierCommandState goalCommandState() {
        return this.commandState;
    }

    void goalSetHoldDisplacedTicks(int value) {
        this.holdDisplacedTicks = value;
    }

    void goalSetCommandDestination(@Nullable class_2338 value) {
        this.commandDestination = value;
    }

    void goalCompleteMoveAt(class_2338 feet) {
        this.commandDestination = null;
        this.formationState.clearAssembly();
        this.setOrderType(NpcOrderType.HOLD_POSITION);
        if (feet != null) {
            this.holdPosition = new class_243((double)feet.method_10263() + 0.5, (double)feet.method_10264(), (double)feet.method_10260() + 0.5);
            this.holdDisplacedTicks = 0;
        }
    }

    void goalSetIdleWanderAnchor(@Nullable class_243 value) {
        this.idleWanderAnchor = value;
        this.idleRallyAnchorActive = false;
    }

    public void setIdleRallyAnchor(@Nullable class_243 value) {
        this.idleWanderAnchor = value;
        boolean bl = this.idleRallyAnchorActive = value != null;
        if (this.getOrderType() == NpcOrderType.IDLE) {
            this.stopAdaptiveNavigation();
        }
    }

    public void setIdleFreeAnchor(@Nullable class_243 value) {
        this.idleWanderAnchor = value;
        this.idleRallyAnchorActive = false;
        if (this.getOrderType() == NpcOrderType.IDLE) {
            this.stopAdaptiveNavigation();
        }
    }

    @Nullable
    public class_243 getIdleRallyAnchor() {
        return this.idleWanderAnchor;
    }

    boolean goalUsesIdleRallyAnchor() {
        return this.idleRallyAnchorActive;
    }

    void goalMarkRegistryDirty() {
        this.maintenanceState.registryDirty = true;
    }

    int goalNextRandomInt(int bound) {
        return this.field_5974.method_43048(bound);
    }

    @Override
    public class_1799 equipmentStack(class_1304 slot) {
        return this.method_6118(slot);
    }

    @Override
    public void setEquipmentStack(class_1304 slot, class_1799 stack) {
        this.method_5673(slot, stack);
    }

    @Override
    public boolean equipmentEvaluationInProgress() {
        return this.maintenanceState.applyingEquipmentEvaluation;
    }

    @Override
    public void setEquipmentEvaluationInProgress(boolean value) {
        this.maintenanceState.applyingEquipmentEvaluation = value;
    }

    class_1277 equipmentPersonalInventory() {
        return this.personalInventory;
    }

    MedievalSoldierMaintenanceState equipmentMaintenanceState() {
        return this.maintenanceState;
    }

    static final double fireControlRANGED_RETREAT_LEASH() {
        return 10.0;
    }

    NpcCombatMovementState fireControlCombatMovementState() {
        return this.combatMovementState;
    }

    class_2338 fireControlCommandDestination() {
        return this.commandDestination;
    }

    MedievalSoldierCombatState fireControlCombatState() {
        return this.combatState;
    }

    boolean fireControlRestoreRangedWeaponAfterCloseCombat() {
        return this.restoreRangedWeaponAfterCloseCombat();
    }

    MedievalSoldierMaintenanceState fireControlMaintenanceState() {
        return this.maintenanceState;
    }

    class_1277 fireControlPersonalInventory() {
        return this.personalInventory;
    }

    void fireControlUpdateWeaponClassDamageModifier() {
        this.updateWeaponClassDamageModifier();
    }

    MedievalSoldierCombatState shieldCombatState() {
        return this.combatState;
    }

    MedievalSoldierEatingState shieldEatingState() {
        return this.eatingState;
    }

    int shieldGetMeleeAttackCooldown() {
        return this.getMeleeAttackCooldown();
    }

    boolean shieldHasUsableShield() {
        return this.hasUsableShield();
    }

    boolean shieldShouldAutoRaiseShield() {
        return this.shouldAutoRaiseShield(this.method_5968());
    }

    void shieldUpdateShieldMovementModifier() {
        this.updateShieldMovementModifier(this.isUsingShield());
    }

    static final UUID shieldSHIELD_SPEED_MODIFIER_ID() {
        return SHIELD_SPEED_MODIFIER_ID;
    }

    MedievalSoldierModifierState shieldModifierState() {
        return this.modifierState;
    }

    MedievalSoldierShieldControl orderShieldControl() {
        return this.shieldControl;
    }

    MedievalSoldierCommandState orderCommandState() {
        return this.commandState;
    }

    NpcOrderType orderOrderType() {
        return this.orderType;
    }

    class_2338 orderCommandDestination() {
        return this.commandDestination;
    }

    class_243 orderHoldPosition() {
        return this.holdPosition;
    }

    PatrolState orderPatrolState() {
        return this.patrolState;
    }

    NpcFormationState orderFormationState() {
        return this.formationState;
    }

    NpcCombatMovementState orderCombatMovementState() {
        return this.combatMovementState;
    }

    NpcCombatTargetState orderCombatTargetState() {
        return this.combatTargetState;
    }

    MedievalSoldierSupplyState orderSupplyState() {
        return this.supplyState;
    }

    void orderSetTypeRaw(NpcOrderType value) {
        this.orderType = value;
    }

    void orderSetCommandDestination(@Nullable class_2338 value) {
        this.commandDestination = value;
    }

    void orderSetHoldPosition(@Nullable class_243 value) {
        this.holdPosition = value;
    }

    void orderSetHoldDisplacedTicks(int value) {
        this.holdDisplacedTicks = value;
    }

    void orderSetIdleAnchor(@Nullable class_243 value, boolean rallyBound) {
        this.idleWanderAnchor = value;
        this.idleRallyAnchorActive = rallyBound && value != null;
    }

    void orderTrackType(NpcOrderType value) {
        this.field_6011.method_12778(TRACKED_ORDER_TYPE, value.ordinal());
    }

    MedievalSoldierEquipmentController supplyEquipmentController() {
        return this.equipmentController;
    }

    class_1277 supplyPersonalInventory() {
        return this.personalInventory;
    }

    MedievalSoldierSupplyState supplySupplyState() {
        return this.supplyState;
    }

    QuartermasterFarmState quartermasterFarmState() {
        return this.quartermasterFarmState;
    }

    QuartermasterFarmingController quartermasterFarmingController() {
        return this.quartermasterFarmingController;
    }

    public int getQuartermasterFieldCount() {
        return (Integer)this.field_6011.method_12789(TRACKED_QUARTERMASTER_FIELD_COUNT);
    }

    public boolean hasQuartermasterComposter() {
        return (Boolean)this.field_6011.method_12789(TRACKED_QUARTERMASTER_COMPOSTER);
    }

    public boolean isQuartermasterCompostingVisual() {
        return (Boolean)this.field_6011.method_12789(TRACKED_QUARTERMASTER_COMPOSTING);
    }

    void setQuartermasterCompostingVisual(boolean composting) {
        this.field_6011.method_12778(TRACKED_QUARTERMASTER_COMPOSTING, composting);
    }

    public class_1799 getQuartermasterPlantingSelection() {
        return this.getNpcRole() == NpcRole.QUARTERMASTER ? this.personalInventory.method_5438(9) : class_1799.field_8037;
    }

    public class_1799 getQuartermasterPlantingVisualStack() {
        return QuartermasterWorkVisualState.plantingStack((Integer)this.field_6011.method_12789(TRACKED_QUARTERMASTER_PLANT_ITEM));
    }

    public class_1799 getQuartermasterWorkVisualStack() {
        return this.quartermasterWorkVisualState.workStack(this.getQuartermasterPlantingVisualStack());
    }

    public boolean setQuartermasterComposter(class_2338 pos) {
        class_3218 serverWorld;
        class_1937 class_19372;
        if (this.getNpcRole() != NpcRole.QUARTERMASTER || pos == null) {
            return false;
        }
        class_2338 previous = this.quartermasterFarmState.composter;
        if (previous != null && !previous.equals((Object)pos) && (class_19372 = this.method_37908()) instanceof class_3218) {
            serverWorld = (class_3218)class_19372;
            QuartermasterComposterPoiService.restore(serverWorld, previous);
        }
        this.quartermasterFarmState.composter = pos.method_10062();
        this.field_6011.method_12778(TRACKED_QUARTERMASTER_COMPOSTER, true);
        class_19372 = this.method_37908();
        if (class_19372 instanceof class_3218) {
            serverWorld = (class_3218)class_19372;
            QuartermasterComposterPoiService.suppress(serverWorld, pos);
        }
        this.quartermasterFarmingController.clearWork();
        this.maintenanceState.registryDirty = true;
        return true;
    }

    public boolean addQuartermasterField(class_2338 pos) {
        if (this.getNpcRole() != NpcRole.QUARTERMASTER || pos == null) {
            return false;
        }
        boolean added = this.quartermasterFarmState.addField(pos);
        if (added) {
            this.field_6011.method_12778(TRACKED_QUARTERMASTER_FIELD_COUNT, this.quartermasterFarmState.fieldCenters.size());
            this.quartermasterFarmingController.clearWork();
            this.maintenanceState.registryDirty = true;
        }
        return added;
    }

    public void clearQuartermasterFields() {
        this.quartermasterFarmState.clearFields();
        this.field_6011.method_12778(TRACKED_QUARTERMASTER_FIELD_COUNT, 0);
        this.quartermasterFarmingController.clearWork();
        this.maintenanceState.registryDirty = true;
    }

    public boolean removeQuartermasterField(int index) {
        boolean removed = this.quartermasterFarmState.removeField(index);
        if (removed) {
            this.field_6011.method_12778(TRACKED_QUARTERMASTER_FIELD_COUNT, this.quartermasterFarmState.fieldCenters.size());
            this.quartermasterFarmingController.clearWork();
            this.maintenanceState.registryDirty = true;
        }
        return removed;
    }

    public void clearQuartermasterComposter() {
        class_1937 class_19372;
        class_2338 oldComposter = this.quartermasterFarmState.composter;
        this.quartermasterFarmState.composter = null;
        this.field_6011.method_12778(TRACKED_QUARTERMASTER_COMPOSTER, false);
        if (oldComposter != null && (class_19372 = this.method_37908()) instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            QuartermasterComposterPoiService.restore(serverWorld, oldComposter);
        }
        this.quartermasterFarmingController.clearWork();
        this.maintenanceState.registryDirty = true;
    }

    static final int supplySUPPLY_RATION_NUTRITION_TARGET() {
        return 48;
    }

    class_1277 formationPersonalInventory() {
        return this.personalInventory;
    }

    MedievalSoldierEatingState supplyEatingState() {
        return this.eatingState;
    }

    void supplyCloseSupplyChestVisual() {
        this.closeSupplyChestVisual();
    }

    void openWorkChestVisual(class_2338 pos) {
        this.supplyController.openWorkChestVisual(this, pos);
    }

    void closeWorkChestVisual(class_2338 pos) {
        this.supplyController.closeWorkChestVisual(this, pos);
    }

    void supplyRestoreOrderAfterSupply() {
        this.restoreOrderAfterSupply();
    }

    void supplyResumeSuspendedOrderIfPossible() {
        this.resumeSuspendedOrderIfPossible();
    }

    void supplyFinishSupplyRun() {
        this.finishSupplyRun();
    }

    protected void method_5959() {
        this.registerSafetyAndCombatGoals();
        this.registerCommandAndUtilityGoals();
        this.registerIdleGoals();
        this.registerTargetGoals();
    }

    private void registerSafetyAndCombatGoals() {
        this.field_6201.method_6277(0, (class_1352)new class_1347((class_1308)this));
        this.field_6201.method_6277(1, (class_1352)new HazardAvoidanceGoal(this));
        this.field_6201.method_6277(2, (class_1352)new MoraleFallbackGoal(this));
        this.field_6201.method_6277(2, (class_1352)new UnarmedRangedFallbackGoal(this, 1.2));
        this.field_6201.method_6277(2, (class_1352)new StrategicAreaFireGoal(this));
        this.field_6201.method_6277(2, (class_1352)new RoleAwareRangedGoal(this, 1.15, 44.0f));
        this.field_6201.method_6277(2, (class_1352)new RoleAwareMeleeGoal(this, 1.05));
    }

    private void registerCommandAndUtilityGoals() {
        this.field_6201.method_6277(3, (class_1352)new MachineCrewAssignmentGoal(this, 1.05));
        this.field_6201.method_6277(3, (class_1352)new MountCommandGoal(this, 1.1));
        this.field_6201.method_6277(3, (class_1352)new MoveToCommandGoal(this, 1.1));
        this.field_6201.method_6277(3, (class_1352)new FormationAssemblyGoal(this, 1.05));
        this.field_6201.method_6277(4, (class_1352)new ReturnToGarrisonGoal(this, 1.05));
        this.field_6201.method_6277(3, (class_1352)new BodyguardEscortGoal(this));
        this.field_6201.method_6277(5, (class_1352)new FollowOwnerGoal(this, 1.05, 7.0f, 9.0f));
        this.field_6201.method_6277(5, (class_1352)new WantedItemPickupGoal(this, 0.95));
        this.field_6201.method_6277(6, (class_1352)new QuartermasterFarmingGoal(this));
        this.field_6201.method_6277(7, (class_1352)new HoldPositionGoal(this, 1.0));
        this.field_6201.method_6277(7, (class_1352)new PatrolHomeGoal(this));
    }

    private void registerIdleGoals() {
        this.field_6201.method_6277(8, (class_1352)new IdleRestGoal(this));
        this.field_6201.method_6277(9, (class_1352)new IdleReturnGoal(this, 0.75));
        this.field_6201.method_6277(10, (class_1352)new OrderAwareWanderGoal(this, 0.7));
        this.field_6201.method_6277(11, (class_1352)new class_1361((class_1308)this, class_1657.class, 8.0f));
        this.field_6201.method_6277(12, (class_1352)new class_1376((class_1308)this));
    }

    private void registerTargetGoals() {
        this.field_6185.method_6277(1, (class_1352)new StanceAwareRevengeGoal(this));
        this.field_6185.method_6277(2, (class_1352)new OwnerHurtByTargetGoal(this));
        this.field_6185.method_6277(3, (class_1352)new OwnerHurtTargetGoal(this));
        this.field_6185.method_6277(4, (class_1352)new DiplomaticHostileTargetGoal(this));
        this.field_6185.method_6277(5, (class_1352)new AggressiveHostileTargetGoal(this));
        this.field_6185.method_6277(6, (class_1352)new RaidLivingTargetGoal(this));
    }

    public NpcIdentity getNpcIdentity() {
        return this.identity;
    }

    public class_1263 getPersonalInventory() {
        return this.personalInventory;
    }

    public void setInventoryGuiOpen(boolean open) {
        this.inventoryGuiViewers = Math.max(0, this.inventoryGuiViewers + (open ? 1 : -1));
        if (open) {
            this.inventoryGuiMovementLocked = true;
            this.stopAdaptiveNavigation();
        } else if (this.inventoryGuiViewers == 0) {
            this.inventoryGuiMovementLocked = true;
        }
    }

    public void releaseInventoryGuiMovementLock() {
        if (this.inventoryGuiViewers > 0) {
            this.inventoryGuiMovementLocked = false;
        }
    }

    public boolean isInventoryGuiOpen() {
        return this.inventoryGuiViewers > 0;
    }

    public PatrolState getPatrolState() {
        return this.patrolState;
    }

    public PatrolRoute getPatrolRoute() {
        return this.patrolState.route();
    }

    public int getPatrolWaypointCount() {
        return this.patrolState.route().size();
    }

    public boolean hasPatrolRoute() {
        return !this.patrolState.route().isEmpty();
    }

    public boolean addPatrolWaypoint(class_2338 point) {
        boolean changed = this.patrolState.route().add(point);
        if (changed) {
            this.patrolState.onRouteChanged();
            this.maintenanceState.registryDirty = true;
        }
        return changed;
    }

    public boolean removeLastPatrolWaypoint() {
        boolean changed = this.patrolState.route().removeLast();
        if (changed) {
            this.patrolState.onRouteChanged();
            this.maintenanceState.registryDirty = true;
        }
        return changed;
    }

    public void clearPatrolRoute() {
        this.patrolState.route().clear();
        this.patrolState.setNextWaypointIndex(0);
        this.maintenanceState.registryDirty = true;
    }

    public void setHomePosition(class_2338 position) {
        this.homePosition = position == null ? null : position.method_10062();
    }

    public class_2338 getHomePosition() {
        return this.homePosition;
    }

    public void setCommandDestination(class_2338 position) {
        class_2338 class_23382 = this.commandDestination = position == null ? null : position.method_10062();
        if (this.commandDestination != null) {
            this.formationState.clearAssembly();
            this.prepareForExplicitMovementCommand(NpcOrderType.MOVE_TO_POSITION);
            this.stopAdaptiveNavigation();
            this.setOrderType(NpcOrderType.MOVE_TO_POSITION);
        }
    }

    public class_2338 getCommandDestination() {
        return this.commandDestination;
    }

    public void clearCommandDestination() {
        this.commandDestination = null;
    }

    public class_1799 getSelectedCannonball() {
        return this.machineResourceController.selectedAmmunition(this);
    }

    public boolean hasSelectedCannonball() {
        return !this.getSelectedCannonball().method_7960();
    }

    @Override
    public class_1799 medievalMachinery$getPreferredMachineAmmunition() {
        return this.machineResourceController.preferredAmmunition(this);
    }

    @Override
    public int medievalMachinery$countMachineResource(class_1792 item) {
        return this.machineResourceController.count(this, item);
    }

    @Override
    public int medievalMachinery$storeMachineResource(class_1792 item, int count) {
        return this.machineResourceController.store(this, item, count);
    }

    @Override
    public boolean medievalMachinery$consumeMachineResource(class_1792 item, int count) {
        return this.machineResourceController.consume(this, item, count);
    }

    public boolean matchesSelectedCannonball(class_1799 ammunition) {
        return this.machineResourceController.matchesSelected(this, ammunition);
    }

    void setMachineHeldVisual(class_1799 stack) {
        if (!this.method_37908().field_9236) {
            this.field_6011.method_12778(TRACKED_MACHINE_HELD_VISUAL, (stack == null ? class_1799.field_8037 : stack.method_46651(1)));
        }
    }

    public class_1799 getMachineHeldVisual() {
        return ((class_1799)this.field_6011.method_12789(TRACKED_MACHINE_HELD_VISUAL)).method_7972();
    }

    public boolean isRoleEquipmentReady() {
        return NpcEquipmentProfile.isCombatReady(this.getNpcRole(), this.method_6047());
    }

    public void openPersonalInventory(class_3222 player) {
        this.personalInventory.method_5435((class_1657)player);
        player.method_17355(new class_3908(){

            public class_2561 method_5476() {
                return class_2561.method_43469((String)"screen.medieval-machinery.soldier_inventory", (Object[])new Object[]{class_2561.method_43471((String)("npc.medieval-machinery.role." + MedievalSoldierEntity.this.getNpcRole().serializedName()))});
            }

            public class_1703 createMenu(int syncId, class_1661 playerInventory, class_1657 playerEntity) {
                return new SoldierInventoryScreenHandler(syncId, playerInventory, MedievalSoldierEntity.this);
            }
        });
    }

    public int getNpcLevel() {
        return Math.max(1, (Integer)this.field_6011.method_12789(TRACKED_LEVEL));
    }

    public int getNpcExperience() {
        return Math.max(0, (Integer)this.field_6011.method_12789(TRACKED_EXPERIENCE));
    }

    public int getExperienceForNextLevel() {
        return 100;
    }

    public void addNpcExperience(int amount) {
        int level;
        if (amount <= 0 || this.method_37908().field_9236) {
            return;
        }
        int progressionXp = NpcMendingService.repairEquippedItems((class_1309)this, amount);
        if (progressionXp <= 0) {
            return;
        }
        int experience = this.getNpcExperience() + progressionXp;
        int oldLevel = level = this.getNpcLevel();
        int needed = this.getExperienceForNextLevel();
        while (experience >= needed) {
            experience -= needed;
            ++level;
        }
        this.field_6011.method_12778(TRACKED_LEVEL, level);
        this.field_6011.method_12778(TRACKED_EXPERIENCE, experience);
        if (level != oldLevel) {
            this.applyLevelProgressionModifiers();
            this.addMorale(5.0f * (float)(level - oldLevel));
            this.method_6025(Math.min(10.0f, 2.0f * (float)(level - oldLevel)));
            this.maintenanceState.registryDirty = true;
        }
    }

    public void awardCombatExperience(class_1309 victim, boolean killed) {
        if (this.method_37908().field_9236 || victim == null || victim == this) {
            return;
        }
        int xp = 1;
        this.addMorale(0.25f);
        if (killed) {
            xp += 5;
            if (victim instanceof class_1657) {
                xp += 45;
            } else if (victim instanceof MedievalSoldierEntity) {
                xp += 10;
            }
            this.addMorale(victim instanceof class_1657 ? 10.0f : 1.0f);
            this.inspireNearbyAlliesAfterKill(victim);
        }
        this.addNpcExperience(xp);
        this.hungerData.addExhaustion(0.06f);
    }

    private void applyLevelProgressionModifiers() {
        class_1324 attack;
        class_1324 health = this.method_5996(class_5134.field_23716);
        if (health != null) {
            double bonusHealth;
            class_1322 old = health.method_6199(LEVEL_HEALTH_MODIFIER_ID);
            if (old != null) {
                health.method_6200(LEVEL_HEALTH_MODIFIER_ID);
            }
            if ((bonusHealth = Math.min(18.0, (double)Math.max(0, Math.min(10, this.getNpcLevel()) - 1) * 2.0)) > 0.0) {
                health.method_26837(new class_1322(LEVEL_HEALTH_MODIFIER_ID, "Medieval Machinery NPC level health", bonusHealth, class_1322.class_1323.field_6328));
            }
            if (this.method_6032() > this.method_6063()) {
                this.method_6033(this.method_6063());
            }
        }
        if ((attack = this.method_5996(class_5134.field_23721)) != null) {
            double bonus;
            class_1322 old = attack.method_6199(LEVEL_DAMAGE_MODIFIER_ID);
            if (old != null) {
                attack.method_6200(LEVEL_DAMAGE_MODIFIER_ID);
            }
            if ((bonus = Math.min(0.27, (double)Math.max(0, Math.min(10, this.getNpcLevel()) - 1) * 0.03)) > 0.0) {
                attack.method_26837(new class_1322(LEVEL_DAMAGE_MODIFIER_ID, "Medieval Machinery NPC level damage", bonus, class_1322.class_1323.field_6331));
            }
        }
    }

    float getLevelRangedInaccuracy() {
        float levelInaccuracy = this.combatController.getLevelRangedInaccuracy(this);
        return Math.max(1.0f, levelInaccuracy * NpcLeadershipPolicy.rangedInaccuracyMultiplier(this.getNearbyLeadershipStrength()));
    }

    int applyLevelRangedCooldown(int baseCooldown) {
        double reduction = Math.min(0.2, (double)Math.max(0, Math.min(10, this.getNpcLevel()) - 1) * 0.02);
        int levelAdjusted = Math.max(6, (int)Math.round((double)baseCooldown * (1.0 - reduction)));
        return this.applyLeadershipRangedCooldown(levelAdjusted);
    }

    int applyLeadershipRangedCooldown(int baseCooldown) {
        double multiplier = NpcLeadershipPolicy.rangedCooldownMultiplier(this.getNearbyLeadershipStrength());
        return Math.max(6, (int)Math.round((double)baseCooldown * multiplier));
    }

    public float getMorale() {
        return ((Float)this.field_6011.method_12789(TRACKED_MORALE)).floatValue();
    }

    public void setMorale(float value) {
        this.moraleData.set(value);
        this.field_6011.method_12778(TRACKED_MORALE, Float.valueOf(this.moraleData.get()));
        this.applyMoraleModifiers();
    }

    public void addMorale(float amount) {
        this.setMorale(this.getMorale() + amount);
    }

    private void updateMoraleState() {
        NpcLeadershipPolicy.Strength leadership;
        float leadershipRecovery;
        this.moraleData.set(this.getMorale());
        float healthFraction = this.method_6063() <= 0.0f ? 1.0f : this.method_6032() / this.method_6063();
        float beforePassiveUpdate = this.moraleData.get();
        this.moraleData.update(this.hungerData.getState(), healthFraction);
        if (this.method_5968() != null && this.method_5968().method_5805() && this.moraleData.get() > beforePassiveUpdate) {
            this.moraleData.set(beforePassiveUpdate);
        }
        if ((leadershipRecovery = NpcLeadershipPolicy.moraleRecovery(leadership = this.getNearbyLeadershipStrength())) > 0.0f && this.moraleData.get() < 80.0f) {
            this.moraleData.add(Math.min(leadershipRecovery, 80.0f - this.moraleData.get()));
        }
        this.field_6011.method_12778(TRACKED_MORALE, Float.valueOf(this.moraleData.get()));
        this.applyMoraleModifiers();
        this.applyLeadershipCombatModifier(leadership);
    }

    private void inspireNearbyAlliesAfterKill(class_1309 victim) {
        class_3218 serverWorld;
        block4: {
            block3: {
                class_1937 class_19372 = this.method_37908();
                if (!(class_19372 instanceof class_3218)) break block3;
                serverWorld = (class_3218)class_19372;
                if (victim != null) break block4;
            }
            return;
        }
        float shared = victim instanceof class_1657 ? 2.0f : (victim instanceof MedievalSoldierEntity ? 1.0f : 0.5f);
        class_238 area = this.method_5829().method_1009(12.0, 6.0, 12.0);
        for (MedievalSoldierEntity ally : serverWorld.method_8390(MedievalSoldierEntity.class, area, other -> other != this && other.method_5805() && this.isFriendlySoldier((MedievalSoldierEntity)other))) {
            ally.addMorale(shared);
        }
    }

    private void shockNearbyAlliesOnDeath() {
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        float loss = switch (this.getNpcRank()) {
            case NpcRank.GENERAL -> 10.0f;
            case NpcRank.CAPTAIN -> 7.0f;
            default -> 4.0f;
        };
        class_238 area = this.method_5829().method_1009(16.0, 8.0, 16.0);
        for (MedievalSoldierEntity ally : serverWorld.method_8390(MedievalSoldierEntity.class, area, other -> other != this && other.method_5805() && this.isFriendlySoldier((MedievalSoldierEntity)other))) {
            ally.addMorale(-loss);
        }
    }

    boolean hasNearbyMoraleLeader() {
        return NpcLeadershipPolicy.steadiesShakenTroops(this.getNearbyLeadershipStrength());
    }

    NpcLeadershipPolicy.Strength getNearbyLeadershipStrength() {
        if (this.field_6012 < this.leadershipCacheExpiresAt) {
            return this.cachedLeadershipStrength;
        }
        this.cachedLeadershipStrength = this.resolveNearbyLeadershipStrength();
        this.leadershipCacheExpiresAt = this.field_6012 + 20;
        return this.cachedLeadershipStrength;
    }

    private NpcLeadershipPolicy.Strength resolveNearbyLeadershipStrength() {
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return NpcLeadershipPolicy.Strength.NONE;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        UUID ownKingdom = this.getNpcIdentity().getKingdomId();
        UUID ownCastle = this.getNpcIdentity().getCastleId();
        UUID assignedCaptain = this.getNpcIdentity().getCaptainId();
        class_238 area = this.method_5829().method_1009(24.0, 12.0, 24.0);
        boolean captainPresent = false;
        for (MedievalSoldierEntity ally : serverWorld.method_8390(MedievalSoldierEntity.class, area, other -> other != this && other.method_5805())) {
            double distanceSq = this.method_5858((class_1297)ally);
            if (ally.getNpcRank() == NpcRank.GENERAL && ownKingdom != null && ownKingdom.equals(ally.getNpcIdentity().getKingdomId()) && ownCastle != null && ownCastle.equals(ally.getNpcIdentity().getCastleId()) && distanceSq <= 576.0) {
                return NpcLeadershipPolicy.Strength.GENERAL;
            }
            if (ally.getNpcRank() != NpcRank.CAPTAIN || assignedCaptain == null || !assignedCaptain.equals(ally.method_5667()) || !(distanceSq <= 256.0)) continue;
            captainPresent = true;
        }
        return captainPresent ? NpcLeadershipPolicy.Strength.CAPTAIN : NpcLeadershipPolicy.Strength.NONE;
    }

    void applyLeadershipRallyAfterFallback() {
        NpcLeadershipPolicy.Strength leadership = this.getNearbyLeadershipStrength();
        float amount = NpcLeadershipPolicy.rallyAmount(leadership);
        if (amount <= 0.0f || this.getMorale() >= 30.0f) {
            return;
        }
        this.setMorale(Math.min(30.0f, this.getMorale() + amount));
    }

    private void applyLeadershipCombatModifier(NpcLeadershipPolicy.Strength leadership) {
        class_1324 knockback;
        class_1324 attack = this.method_5996(class_5134.field_23721);
        if (attack != null) {
            double bonus;
            class_1322 old = attack.method_6199(LEADERSHIP_DAMAGE_MODIFIER_ID);
            if (old != null) {
                attack.method_6200(LEADERSHIP_DAMAGE_MODIFIER_ID);
            }
            if ((bonus = NpcLeadershipPolicy.attackDamageBonus(leadership)) != 0.0) {
                attack.method_26835(new class_1322(LEADERSHIP_DAMAGE_MODIFIER_ID, "Medieval Machinery NPC leadership damage", bonus, class_1322.class_1323.field_6331));
            }
        }
        if ((knockback = this.method_5996(class_5134.field_23718)) != null) {
            double stability;
            class_1322 old = knockback.method_6199(LEADERSHIP_KNOCKBACK_MODIFIER_ID);
            if (old != null) {
                knockback.method_6200(LEADERSHIP_KNOCKBACK_MODIFIER_ID);
            }
            if ((stability = NpcLeadershipPolicy.formationKnockbackResistanceBonus(leadership)) > 0.0) {
                knockback.method_26835(new class_1322(LEADERSHIP_KNOCKBACK_MODIFIER_ID, "Medieval Machinery NPC leadership formation stability", stability, class_1322.class_1323.field_6328));
            }
        }
    }

    private void applyMoraleModifiers() {
        class_1324 attack;
        NpcMoraleBehaviorPolicy.AttributeModifiers moraleModifiers = NpcMoraleBehaviorPolicy.attributeModifiers(this.getMorale());
        double speedBonus = moraleModifiers.speedMultiplier();
        double damageBonus = moraleModifiers.damageMultiplier();
        class_1324 speed = this.method_5996(class_5134.field_23719);
        if (speed != null) {
            class_1322 old = speed.method_6199(MORALE_SPEED_MODIFIER_ID);
            if (old != null) {
                speed.method_6200(MORALE_SPEED_MODIFIER_ID);
            }
            if (speedBonus != 0.0) {
                speed.method_26835(new class_1322(MORALE_SPEED_MODIFIER_ID, "Medieval Machinery NPC morale speed", speedBonus, class_1322.class_1323.field_6331));
            }
        }
        if ((attack = this.method_5996(class_5134.field_23721)) != null) {
            class_1322 old = attack.method_6199(MORALE_DAMAGE_MODIFIER_ID);
            if (old != null) {
                attack.method_6200(MORALE_DAMAGE_MODIFIER_ID);
            }
            if (damageBonus != 0.0) {
                attack.method_26835(new class_1322(MORALE_DAMAGE_MODIFIER_ID, "Medieval Machinery NPC morale damage", damageBonus, class_1322.class_1323.field_6331));
            }
        }
    }

    public boolean medievalMachinery$hasMachineAssignment() {
        return this.machineAssignment.isAssigned();
    }

    public UUID medievalMachinery$getAssignedMachineId() {
        return this.machineAssignment.getMachineId();
    }

    public MachineCrewRole medievalMachinery$getAssignedMachineCrewRole() {
        return this.machineAssignment.getCrewRole();
    }

    public CastleTransferData medievalMachinery$getCastleTransferData() {
        return this.castleTransferData;
    }

    public boolean medievalMachinery$startCastleTransfer(UUID targetCastleId) {
        class_3218 serverWorld;
        class_1937 class_19372 = this.method_37908();
        return class_19372 instanceof class_3218 && CastleTransferController.request(serverWorld = (class_3218)class_19372, this, targetCastleId);
    }

    public void medievalMachinery$cancelCastleTransfer() {
        CastleTransferController.cancel(this);
    }

    public void medievalMachinery$assignMachine(UUID machineId, MachineCrewRole crewRole) {
        this.machineAssignment.assign(machineId, crewRole);
        if (this.machineAssignmentOwnsPersonalCombat()) {
            this.method_5980(null);
            this.combatState.clearCommandedTarget();
            this.combatTargetState.resetSight();
            this.stopAdaptiveNavigation();
        }
    }

    public boolean machineAssignmentOwnsPersonalCombat() {
        return this.machineAssignment.isAssigned() && this.machineAssignment.getCrewRole() == MachineCrewRole.OPERATOR && !this.medievalMachinery$isTemporarilyDefendingFromMachine();
    }

    public boolean medievalMachinery$isTemporarilyDefendingFromMachine() {
        if (!this.machineAssignment.isAssigned() || this.machineAssignment.getCrewRole() != MachineCrewRole.OPERATOR) {
            return false;
        }
        class_1309 target = this.method_5968();
        class_1309 attacker = this.method_6065();
        return target != null && target.method_5805() && !target.method_31481() && attacker == target && !this.isFriendlyCombatEntity(target);
    }

    public void medievalMachinery$clearMachineAssignment() {
        this.machineAssignment.clear();
        this.setMachineHeldVisual(class_1799.field_8037);
    }

    public class_2338 medievalMachinery$getPreferredMachineSupplyChest() {
        return this.machineAssignment.getPreferredSupplyChest();
    }

    public void medievalMachinery$setPreferredMachineSupplyChest(class_2338 pos) {
        this.machineAssignment.setPreferredSupplyChest(pos);
    }

    public boolean medievalMachinery$isAssignedTo(UUID machineId, MachineCrewRole crewRole) {
        return machineId != null && this.machineAssignment.isAssigned() && machineId.equals(this.machineAssignment.getMachineId()) && crewRole == this.machineAssignment.getCrewRole();
    }

    public NpcRole getNpcRole() {
        return NpcRole.fromOrdinal((Integer)this.field_6011.method_12789(TRACKED_ROLE));
    }

    public void setNpcRole(NpcRole role) {
        this.role = role == null ? NpcRole.SWORDSMAN : role;
        this.field_6011.method_12778(TRACKED_ROLE, this.role.ordinal());
        if (this.role == NpcRole.MOUNTED_KNIGHT && this.preferredMeleeWeapon == NpcPreferredMeleeWeapon.SWORD) {
            this.preferredMeleeWeapon = NpcPreferredMeleeWeapon.SPEAR;
            this.field_6011.method_12778(TRACKED_PREFERRED_MELEE_WEAPON, this.preferredMeleeWeapon.ordinal());
        }
        this.updateRoleFollowRange();
        this.maintenanceState.equipmentDirty = true;
        this.maintenanceState.registryDirty = true;
    }

    public NpcTargetPriority getTargetPriority() {
        if (this.method_37908().field_9236) {
            int ordinal = (Integer)this.field_6011.method_12789(TRACKED_TARGET_PRIORITY);
            NpcTargetPriority[] values = NpcTargetPriority.values();
            return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcTargetPriority.CLOSEST;
        }
        return this.targetPriority == null ? NpcTargetPriority.CLOSEST : this.targetPriority;
    }

    public void setTargetPriority(NpcTargetPriority priority) {
        this.targetPriority = priority == null ? NpcTargetPriority.CLOSEST : priority;
        this.field_6011.method_12778(TRACKED_TARGET_PRIORITY, this.targetPriority.ordinal());
    }

    public boolean supportsPreferredMeleeWeaponSelection() {
        return this.getNpcRole() == NpcRole.KNIGHT || this.getNpcRole() == NpcRole.MOUNTED_KNIGHT;
    }

    public NpcPreferredMeleeWeapon getPreferredMeleeWeapon() {
        if (this.method_37908().field_9236) {
            int ordinal = (Integer)this.field_6011.method_12789(TRACKED_PREFERRED_MELEE_WEAPON);
            NpcPreferredMeleeWeapon[] values = NpcPreferredMeleeWeapon.values();
            return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcPreferredMeleeWeapon.SWORD;
        }
        return this.preferredMeleeWeapon == null ? NpcPreferredMeleeWeapon.SWORD : this.preferredMeleeWeapon;
    }

    public void setPreferredMeleeWeapon(NpcPreferredMeleeWeapon preferred) {
        if (!this.supportsPreferredMeleeWeaponSelection()) {
            return;
        }
        this.preferredMeleeWeapon = preferred == null ? NpcPreferredMeleeWeapon.SWORD : preferred;
        this.field_6011.method_12778(TRACKED_PREFERRED_MELEE_WEAPON, this.preferredMeleeWeapon.ordinal());
        this.maintenanceState.equipmentDirty = true;
        if (!this.method_37908().field_9236) {
            this.equipBestPreferredMeleeWeapon();
        }
    }

    public long getWeaponScore(class_1799 stack) {
        return NpcEquipmentProfile.weaponScore(this.getNpcRole(), this.getPreferredMeleeWeapon(), stack);
    }

    public boolean isBetterWeapon(class_1799 candidate, class_1799 current) {
        return NpcEquipmentProfile.isBetterWeapon(this.getNpcRole(), this.getPreferredMeleeWeapon(), candidate, current);
    }

    private void equipBestPreferredMeleeWeapon() {
        if (!this.supportsPreferredMeleeWeaponSelection()) {
            return;
        }
        class_1799 current = this.method_6047();
        long bestScore = this.getWeaponScore(current);
        int bestSlot = -1;
        for (int i = 0; i < this.personalInventory.method_5439(); ++i) {
            class_1799 candidate = this.personalInventory.method_5438(i);
            long score = this.getWeaponScore(candidate);
            if (score <= bestScore) continue;
            bestScore = score;
            bestSlot = i;
        }
        if (bestSlot >= 0) {
            class_1799 replacement = this.personalInventory.method_5438(bestSlot);
            this.personalInventory.method_5447(bestSlot, current.method_7972());
            this.method_5673(class_1304.field_6173, replacement.method_7972());
            this.maintenanceState.registryDirty = true;
        }
    }

    private void ensureRoleTargetPriorityDefault() {
        if (this.targetPriority == null || this.targetPriority == NpcTargetPriority.CLOSEST) {
            this.setTargetPriority(MedievalSoldierTargetPriorityPolicy.defaultPriorityForRole(this.getNpcRole()));
        }
    }

    public void applyRoleTargetPriorityDefault() {
        this.setTargetPriority(MedievalSoldierTargetPriorityPolicy.defaultPriorityForRole(this.getNpcRole()));
    }

    private void updateRoleFollowRange() {
        this.orderController.updateRoleFollowRange(this);
    }

    public NpcRank getNpcRank() {
        int ordinal = (Integer)this.field_6011.method_12789(TRACKED_RANK);
        NpcRank[] values = NpcRank.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcRank.NONE;
    }

    public void setNpcRank(NpcRank rank) {
        this.field_6011.method_12778(TRACKED_RANK, (rank == null ? NpcRank.NONE : rank).ordinal());
    }

    public long getLastAppliedGroupOrderRevision() {
        return this.lastAppliedGroupOrderRevision;
    }

    public void markGroupOrderRevisionApplied(long revision) {
        if (revision > this.lastAppliedGroupOrderRevision) {
            this.lastAppliedGroupOrderRevision = revision;
        }
    }

    public long getLastAppliedGroupFormationRevision() {
        return this.lastAppliedGroupFormationRevision;
    }

    public void markGroupFormationRevisionApplied(long revision) {
        if (revision > this.lastAppliedGroupFormationRevision) {
            this.lastAppliedGroupFormationRevision = revision;
        }
    }

    public long getLastAppliedGroupShieldRevision() {
        return this.lastAppliedGroupShieldRevision;
    }

    public void markGroupShieldRevisionApplied(long revision) {
        if (revision > this.lastAppliedGroupShieldRevision) {
            this.lastAppliedGroupShieldRevision = revision;
        }
    }

    public long getLastAppliedGroupCombatStanceRevision() {
        return this.lastAppliedGroupCombatStanceRevision;
    }

    public void markGroupCombatStanceRevisionApplied(long revision) {
        if (revision > this.lastAppliedGroupCombatStanceRevision) {
            this.lastAppliedGroupCombatStanceRevision = revision;
        }
    }

    public boolean isGeneralBodyguard() {
        return (Boolean)this.field_6011.method_12789(TRACKED_BODYGUARD_DUTY);
    }

    public void setGeneralBodyguardDuty(boolean active) {
        if (!this.method_37908().field_9236) {
            this.field_6011.method_12778(TRACKED_BODYGUARD_DUTY, active);
        }
    }

    public NpcOrderType getOrderType() {
        int ordinal = (Integer)this.field_6011.method_12789(TRACKED_ORDER_TYPE);
        NpcOrderType[] values = NpcOrderType.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcOrderType.IDLE;
    }

    private void prepareForExplicitMovementCommand(NpcOrderType nextOrder) {
        this.orderController.prepareForExplicitMovementCommand(this, nextOrder);
    }

    private void suspendCurrentOrderIfNeeded(boolean combat) {
        this.orderController.suspendCurrentOrderIfNeeded(this, combat);
    }

    void resumeSuspendedOrderIfPossible() {
        this.orderController.resumeSuspendedOrderIfPossible(this);
    }

    private void clearSuspendedOrderSnapshot() {
        this.orderController.clearSuspendedOrderSnapshot(this);
    }

    private void refreshIdleAnchorFromCastleRally() {
        this.orderController.refreshIdleAnchorFromCastleRally(this);
    }

    @Nullable
    public class_243 resolveReturnToGarrisonDestination() {
        class_243 class_2432;
        class_1937 class_19372 = this.method_37908();
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            class_2432 = GarrisonRallyPolicy.resolveReturnDestination(serverWorld, this.getNpcIdentity().getCastleId(), this.method_5667(), this.homePosition);
        } else {
            class_2432 = this.homePosition == null ? null : class_243.method_24955((class_2382)this.homePosition.method_10084());
        }
        return class_2432;
    }

    public void setOrderType(NpcOrderType orderType) {
        this.orderController.setOrderType(this, orderType);
    }

    public boolean setObservationPoint(class_2338 groundBlock) {
        return this.orderController.setObservationPoint(this, groundBlock);
    }

    public NpcCombatStance getCombatStance() {
        int ordinal = (Integer)this.field_6011.method_12789(TRACKED_COMBAT_STANCE);
        NpcCombatStance[] values = NpcCombatStance.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcCombatStance.NEUTRAL;
    }

    public void setCombatStance(NpcCombatStance stance) {
        this.combatStance = stance == null ? NpcCombatStance.NEUTRAL : stance;
        this.field_6011.method_12778(TRACKED_COMBAT_STANCE, this.combatStance.ordinal());
        if (this.combatStance == NpcCombatStance.PASSIVE) {
            this.method_5980(null);
        }
    }

    public boolean isHoldFire() {
        return this.combatState.isHoldFire();
    }

    public void setHoldFire(boolean holdFire) {
        this.combatState.setHoldFire(holdFire);
        if (holdFire) {
            this.stopStrategicAreaFire();
            this.stopRangedWeaponUse();
        }
    }

    public boolean startStrategicAreaFire(class_243 center, double radius) {
        if (center == null || !MedievalSoldierStrategicFirePolicy.canAccept(this)) {
            return false;
        }
        this.strategicAreaFireCenter = center;
        this.strategicAreaFireRadius = class_3532.method_15350((double)radius, (double)0.5, (double)12.0);
        this.strategicAreaFireActive = true;
        this.combatState.setHoldFire(false);
        this.method_5980(null);
        this.combatState.clearCommandedTarget();
        this.stopRangedWeaponUse();
        return true;
    }

    public void stopStrategicAreaFire() {
        this.strategicAreaFireActive = false;
        this.strategicAreaFireCenter = null;
        this.stopRangedWeaponUse();
        this.clearRangedVisual();
    }

    public boolean isStrategicAreaFireActive() {
        return this.strategicAreaFireActive && this.strategicAreaFireCenter != null;
    }

    @Nullable
    public class_243 getStrategicAreaFireCenter() {
        return this.strategicAreaFireCenter;
    }

    public double getStrategicAreaFireRadius() {
        return this.strategicAreaFireRadius;
    }

    class_243 chooseStrategicAreaAimPoint() {
        return MedievalSoldierStrategicFirePolicy.chooseAimPoint(this.method_6051(), this.strategicAreaFireCenter, this.strategicAreaFireRadius, this.method_19538());
    }

    public double getFormationSpacingMultiplier() {
        return this.formationState.getSpacingMultiplier();
    }

    public double getFormationFootprintMultiplier() {
        return MedievalSoldierMountedPolicy.formationFootprint(this);
    }

    @Nullable
    public class_243 getFormationCommandAnchor() {
        if (this.getOrderType() == NpcOrderType.MOVE_TO_POSITION && this.commandDestination != null) {
            return class_243.method_24955((class_2382)this.commandDestination);
        }
        if ((this.getOrderType() == NpcOrderType.HOLD_POSITION || this.getOrderType() == NpcOrderType.OBSERVATION_POINT) && this.holdPosition != null) {
            return this.holdPosition;
        }
        if (this.getOrderType() == NpcOrderType.MOVE_TO_POSITION || this.getOrderType() == NpcOrderType.HOLD_POSITION) {
            class_243 assembly = this.formationState.getAssemblyTarget();
            return assembly != null ? assembly : null;
        }
        return null;
    }

    public void adjustFormationSpacing(int direction) {
        this.formationState.setSpacingMultiplier(this.getFormationSpacingMultiplier() + (double)Math.signum(direction) * 0.15);
    }

    public float getFormationFacingYaw(float fallbackYaw) {
        return this.formationState.resolveFacingYaw(fallbackYaw);
    }

    public void setFormationFacingYaw(float yaw) {
        this.formationState.setFacingYaw(yaw);
    }

    public boolean fireStrategicAreaVolley(class_243 center, double radius) {
        return this.startStrategicAreaFire(center, radius);
    }

    public NpcFormation getFormation() {
        int ordinal = (Integer)this.field_6011.method_12789(TRACKED_FORMATION);
        NpcFormation[] values = NpcFormation.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcFormation.FREE;
    }

    public void setFormation(NpcFormation formation) {
        this.field_6011.method_12778(TRACKED_FORMATION, (formation == null ? NpcFormation.FREE : formation).ordinal());
        this.formationState.resetHeading();
    }

    public int getFormationSlot() {
        return this.formationState.getSlot();
    }

    public void setFormationSlot(int slot) {
        this.formationState.setSlot(slot);
    }

    public NpcShieldMode getShieldMode() {
        int ordinal = (Integer)this.field_6011.method_12789(TRACKED_SHIELD_MODE);
        NpcShieldMode[] values = NpcShieldMode.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcShieldMode.AUTO;
    }

    public void setShieldMode(NpcShieldMode mode) {
        this.field_6011.method_12778(TRACKED_SHIELD_MODE, (mode == null ? NpcShieldMode.AUTO : mode).ordinal());
        if (this.getShieldMode() == NpcShieldMode.RAISED && this.hasUsableShield() && this.combatState.shieldReRaiseTicks <= 0 && this.combatState.shieldBlockCooldownTicks <= 0) {
            if (this.method_6115()) {
                this.method_6075();
            }
            this.method_6019(class_1268.field_5810);
        }
        if (this.getShieldMode() == NpcShieldMode.RAISED && this.isHoldingRangedWeapon()) {
            this.stopRangedWeaponUse();
            this.field_6011.method_12778(TRACKED_RANGED_ATTACK_TICKS, 0);
        }
    }

    public static CommandGroupType defaultCommandGroupTypeForRole(NpcRole role) {
        if (role == null) {
            return CommandGroupType.INFANTRY;
        }
        return switch (role) {
            default -> throw new IncompatibleClassChangeError();
            case NpcRole.ARCHER, NpcRole.CROSSBOWMAN -> CommandGroupType.RANGED;
            case NpcRole.MOUNTED_ARCHER, NpcRole.MOUNTED_KNIGHT -> CommandGroupType.MOUNTED;
            case NpcRole.KNIGHT -> CommandGroupType.KNIGHTS;
            case NpcRole.CANNONEER, NpcRole.ENGINEER -> CommandGroupType.MACHINISTS;
            case NpcRole.QUARTERMASTER -> CommandGroupType.QUARTERMASTERS;
            case NpcRole.SCOUT -> CommandGroupType.SCOUTS;
            case NpcRole.SWORDSMAN, NpcRole.AXEMAN, NpcRole.RECRUIT, NpcRole.SPEARMAN -> CommandGroupType.INFANTRY;
        };
    }

    private void tickHoldPositionDisplacement() {
        double dz;
        if (this.getOrderType() != NpcOrderType.HOLD_POSITION || this.holdPosition == null || this.supplyState.request != NpcSupplyRequest.NONE || this.supplyState.isReturningToOrigin() || this.method_5968() != null && this.method_5968().method_5805()) {
            this.holdDisplacedTicks = 0;
            return;
        }
        double dx = this.method_23317() - this.holdPosition.field_1352;
        this.holdDisplacedTicks = dx * dx + (dz = this.method_23321() - this.holdPosition.field_1350) * dz > 2.25 || Math.abs(this.method_23318() - this.holdPosition.field_1351) > 1.25 ? Math.min(200, this.holdDisplacedTicks + 1) : 0;
    }

    private void cycleFieldOrder(class_3222 player) {
        this.orderController.cycleFieldOrder(this, player);
    }

    public boolean isOwnedBy(class_1657 player) {
        UUID owner = this.identity.getOwnerId();
        return owner != null && owner.equals(player.method_5667());
    }

    class_1309 nearbyOwnerThreat(double radius) {
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return null;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        UUID ownerId = this.identity.getOwnerId();
        if (ownerId == null) {
            return null;
        }
        class_3222 owner = serverWorld.method_8503().method_3760().method_14602(ownerId);
        if (owner == null || !owner.method_5805() || this.method_5858((class_1297)owner) > radius * radius) {
            return null;
        }
        class_1309 attacker = owner.method_6065();
        if (attacker == null || !attacker.method_5805() || this.isFriendlyCombatEntity(attacker)) {
            return null;
        }
        return this.method_5858((class_1297)attacker) <= radius * radius ? attacker : null;
    }

    class_1309 nearbyLeaderThreat(double radius) {
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return null;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_238 area = this.method_5829().method_1009(radius, 8.0, radius);
        MedievalSoldierEntity bestLeader = null;
        class_1309 bestThreat = null;
        double bestScore = Double.MAX_VALUE;
        for (MedievalSoldierEntity ally : serverWorld.method_8390(MedievalSoldierEntity.class, area, other -> other != this && other.method_5805() && this.isFriendlySoldier((MedievalSoldierEntity)other) && (other.getNpcRank() == NpcRank.CAPTAIN || other.getNpcRank() == NpcRank.GENERAL))) {
            double rankBias;
            class_1309 attacker = ally.method_6065();
            if (this.field_6012 - ally.method_6117() > 60 || attacker == null || !attacker.method_5805() || this.isFriendlyCombatEntity(attacker) || this.method_5858((class_1297)attacker) > radius * radius) continue;
            double d = rankBias = ally.getNpcRank() == NpcRank.GENERAL ? -20.0 : -10.0;
            double score = this.method_5858((class_1297)ally) + rankBias;
            if (!(score < bestScore)) continue;
            bestScore = score;
            bestLeader = ally;
            bestThreat = attacker;
        }
        return bestLeader == null ? null : bestThreat;
    }

    class_1309 nearbyEndangeredAllyThreat(double radius) {
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return null;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_238 area = this.method_5829().method_1009(radius, 6.0, radius);
        class_1309 bestThreat = null;
        double bestScore = Double.MAX_VALUE;
        for (MedievalSoldierEntity ally : serverWorld.method_8390(MedievalSoldierEntity.class, area, other -> other != this && other.method_5805() && this.isFriendlySoldier((MedievalSoldierEntity)other))) {
            double score;
            float healthFraction;
            class_1309 attacker = ally.method_6065();
            if (this.field_6012 - ally.method_6117() > 60 || attacker == null || !attacker.method_5805() || this.isFriendlyCombatEntity(attacker) || (healthFraction = ally.method_6063() <= 0.0f ? 1.0f : ally.method_6032() / ally.method_6063()) > 0.55f || !((score = this.method_5858((class_1297)ally) + (double)healthFraction * 20.0) < bestScore)) continue;
            bestScore = score;
            bestThreat = attacker;
        }
        return bestThreat;
    }

    class_1309 immediateDefensiveThreat() {
        class_1309 ownerThreat = this.nearbyOwnerThreat(24.0);
        if (ownerThreat != null) {
            return ownerThreat;
        }
        class_1309 leaderThreat = this.nearbyLeaderThreat(20.0);
        if (leaderThreat != null) {
            return leaderThreat;
        }
        return this.nearbyEndangeredAllyThreat(14.0);
    }

    public boolean commandMountNearby() {
        return this.mountedController.commandMountNearby(this);
    }

    MedievalSoldierMountCommandState mountCommandState() {
        return this.mountCommandState;
    }

    boolean hasPendingMountCommand() {
        return this.mountCommandState.active();
    }

    @Nullable
    class_1297 pendingMountTarget() {
        class_1937 class_19372;
        if (!this.mountCommandState.active() || !((class_19372 = this.method_37908()) instanceof class_3218)) {
            return null;
        }
        class_3218 world = (class_3218)class_19372;
        return world.method_14190(this.mountCommandState.targetVehicleId());
    }

    boolean tryBoardPendingMount(class_1297 target) {
        if (target == null || !MedievalSoldierRideablePolicy.isSupportedCandidate(this, target)) {
            return false;
        }
        boolean mounted = this.method_5873(target, false);
        if (mounted) {
            this.mountCommandState.clear();
            this.stopAdaptiveNavigation();
        }
        return mounted;
    }

    void retryOrCancelMountCommand() {
        this.mountedController.retryOrCancelMountCommand(this);
    }

    double mountReservationPenalty(UUID candidateId) {
        class_1937 class_19372;
        if (candidateId == null || !((class_19372 = this.method_37908()) instanceof class_3218)) {
            return 0.0;
        }
        class_3218 world = (class_3218)class_19372;
        class_238 area = this.method_5829().method_1009(20.0, 8.0, 20.0);
        int reservations = 0;
        for (MedievalSoldierEntity other : world.method_8390(MedievalSoldierEntity.class, area, npc -> npc != this && npc.method_5805() && npc.hasPendingMountCommand())) {
            if (!candidateId.equals(other.mountCommandState.targetVehicleId())) continue;
            ++reservations;
        }
        class_1297 candidate = world.method_14190(candidateId);
        if (candidate instanceof class_1496 || candidate instanceof class_1688) {
            return (double)reservations * 1000.0;
        }
        return (double)reservations * 4.0;
    }

    public boolean commandDismount() {
        return this.mountedController.commandDismount(this);
    }

    public OrderStyle getOrderStyle() {
        int ordinal = (Integer)this.field_6011.method_12789(TRACKED_ORDER_STYLE);
        OrderStyle[] values = OrderStyle.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : OrderStyle.NONE;
    }

    public void setOrderStyle(OrderStyle style) {
        this.field_6011.method_12778(TRACKED_ORDER_STYLE, (style == null ? OrderStyle.NONE : style).ordinal());
    }

    public ArmorStyle getArmorStyle() {
        int ordinal = (Integer)this.field_6011.method_12789(TRACKED_ARMOR_STYLE_OVERRIDE);
        ArmorStyle[] values = ArmorStyle.values();
        if (ordinal >= 0 && ordinal < values.length) {
            return values[ordinal];
        }
        return NpcArmorProfiles.defaultStyle(this.getNpcRole(), this.getNpcRank());
    }

    public void setArmorStyleOverride(ArmorStyle style) {
        this.armorStyleOverride = style;
        this.field_6011.method_12778(TRACKED_ARMOR_STYLE_OVERRIDE, (style == null ? -1 : style.ordinal()));
    }

    public ArmorTier getArmorTier() {
        int ordinal = (Integer)this.field_6011.method_12789(TRACKED_ARMOR_TIER);
        ArmorTier[] values = ArmorTier.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : ArmorTier.ORDER;
    }

    public void setArmorTier(ArmorTier tier) {
        this.field_6011.method_12778(TRACKED_ARMOR_TIER, (tier == null ? ArmorTier.ORDER : tier).ordinal());
    }

    public String getAppearanceId() {
        return this.appearanceId;
    }

    public void setAppearanceId(String value) {
        if (value == null || value.isBlank()) {
            this.appearanceId = "villager";
            return;
        }
        String normalized = value.toLowerCase(Locale.ROOT);
        if (!normalized.matches("[a-z0-9_./-]+") || normalized.contains("..")) {
            this.appearanceId = "villager";
            return;
        }
        this.appearanceId = normalized;
    }

    protected class_3414 method_5994() {
        return null;
    }

    protected class_3414 method_6011(class_1282 source) {
        return class_3417.field_15139;
    }

    protected class_3414 method_6002() {
        return class_3417.field_15225;
    }

    public void applyCastleDisplayName(String castleName) {
        String clean = castleName == null ? "" : castleName.trim();
        this.field_6011.method_12778(TRACKED_CASTLE_NAME, (clean.isEmpty() ? "-" : clean));
        this.method_5880(false);
    }

    public String getCastleDisplayName() {
        return (String)this.field_6011.method_12789(TRACKED_CASTLE_NAME);
    }

    public void renameByOwner(class_3222 player, String requestedName) {
        String clean;
        if (!this.isOwnedBy((class_1657)player)) {
            return;
        }
        String string = clean = requestedName == null ? "" : requestedName.trim();
        if (clean.length() > 32) {
            clean = clean.substring(0, 32);
        }
        this.method_5665((class_2561)(clean.isEmpty() ? null : class_2561.method_43470((String)clean)));
        this.method_5880(false);
        this.maintenanceState.registryDirty = true;
    }

    protected class_1269 method_5992(class_1657 player, class_1268 hand) {
        if (this.method_37908().field_9236) {
            return class_1269.field_5812;
        }
        if (!(player instanceof class_3222)) {
            return class_1269.field_5811;
        }
        class_3222 serverPlayer = (class_3222)player;
        UUID owner = this.identity.getOwnerId();
        if (owner == null) {
            NpcOrganizationService.assignToPlayerDefaultContext(this, serverPlayer);
            serverPlayer.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.npc.assigned", (Object[])new Object[]{this.method_5476()}), false);
            return class_1269.field_21466;
        }
        if (owner.equals(serverPlayer.method_5667())) {
            if (serverPlayer.method_5715()) {
                this.openPersonalInventory(serverPlayer);
                return class_1269.field_21466;
            }
            this.cycleFieldOrder(serverPlayer);
            return class_1269.field_21466;
        }
        serverPlayer.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.npc.other_owner"), false);
        return class_1269.field_21466;
    }

    public NpcRecoveryState getRecoveryStateForNavigation() {
        return this.recoveryState;
    }

    public NpcNavigationInteractionState getNavigationInteractionStateForNavigation() {
        return this.navigationInteractionState;
    }

    public void cleanupRecoveryBlocksForNavigation() {
        this.recoveryState.cleanupPlacedBlocksIfFinished(this);
    }

    public boolean isSafeNavigationPosition(class_243 candidate) {
        return this.isSafeCombatPosition(candidate);
    }

    private void tickFormationPassageState() {
        this.formationController.tickPassageState(this);
    }

    boolean shouldUseLooseFormationMovement() {
        return this.formationController.shouldUseLooseMovement(this);
    }

    class_243 formationPointBehind(class_1657 owner) {
        return this.formationController.formationPointBehind(this, owner);
    }

    public void method_5652(class_2487 nbt) {
        class_243 persistedHold;
        class_2338 persistedCommandTarget;
        super.method_5652(nbt);
        class_2487 npc = new class_2487();
        this.identity.writeNbt(npc);
        npc.method_10582("Role", this.role.serializedName());
        npc.method_10582("TargetPriority", this.getTargetPriority().serializedName());
        if (this.supportsPreferredMeleeWeaponSelection()) {
            npc.method_10582("PreferredMeleeWeapon", this.getPreferredMeleeWeapon().serializedName());
        }
        npc.method_10582("Rank", this.getNpcRank().serializedName());
        NpcOrderType persistedOrder = this.supplyState.request != NpcSupplyRequest.NONE || this.supplyState.isReturningToOrigin() ? this.supplyState.orderBefore : this.orderType;
        npc.method_10582("Order", persistedOrder.serializedName());
        npc.method_10544("LastAppliedGroupOrderRevision", this.lastAppliedGroupOrderRevision);
        npc.method_10544("LastAppliedGroupFormationRevision", this.lastAppliedGroupFormationRevision);
        npc.method_10544("LastAppliedGroupShieldRevision", this.lastAppliedGroupShieldRevision);
        npc.method_10544("LastAppliedGroupCombatStanceRevision", this.lastAppliedGroupCombatStanceRevision);
        npc.method_10582("CombatStance", this.getCombatStance().serializedName());
        npc.method_10582("Formation", this.getFormation().serializedName());
        npc.method_10569("FormationSlot", this.getFormationSlot());
        npc.method_10556("HoldFire", this.combatState.isHoldFire());
        if (this.isStrategicAreaFireActive()) {
            npc.method_10556("StrategicAreaFire", true);
            npc.method_10549("StrategicAreaFireX", this.strategicAreaFireCenter.field_1352);
            npc.method_10549("StrategicAreaFireY", this.strategicAreaFireCenter.field_1351);
            npc.method_10549("StrategicAreaFireZ", this.strategicAreaFireCenter.field_1350);
            npc.method_10549("StrategicAreaFireRadius", this.strategicAreaFireRadius);
        }
        npc.method_10549("FormationSpacing", this.getFormationSpacingMultiplier());
        if (this.formationState.hasFacingYaw()) {
            npc.method_10548("FormationFacingYaw", this.formationState.getFacingYaw());
        }
        npc.method_10582("ShieldMode", this.getShieldMode().serializedName());
        npc.method_10582("Appearance", this.appearanceId);
        npc.method_10582("OrderStyle", this.getOrderStyle().method_15434());
        npc.method_10582("ArmorTier", this.getArmorTier().serializedName());
        npc.method_10569("Level", this.getNpcLevel());
        npc.method_10569("Experience", this.getNpcExperience());
        npc.method_10548("Morale", this.getMorale());
        npc.method_10582("CastleDisplayName", this.getCastleDisplayName());
        if (this.armorStyleOverride != null) {
            npc.method_10582("ArmorStyleOverride", this.armorStyleOverride.serializedName());
        }
        class_2487 inventoryNbt = new class_2487();
        class_2371 inventoryStacks = class_2371.method_10213((int)this.personalInventory.method_5439(), (Object)class_1799.field_8037);
        for (int i = 0; i < this.personalInventory.method_5439(); ++i) {
            inventoryStacks.set(i, (Object)this.personalInventory.method_5438(i));
        }
        class_1262.method_5426((class_2487)inventoryNbt, (class_2371)inventoryStacks);
        npc.method_10566("Inventory", (class_2520)inventoryNbt);
        if (this.homePosition != null) {
            npc.method_10569("HomeX", this.homePosition.method_10263());
            npc.method_10569("HomeY", this.homePosition.method_10264());
            npc.method_10569("HomeZ", this.homePosition.method_10260());
        }
        class_2338 class_23382 = persistedCommandTarget = this.supplyState.request != NpcSupplyRequest.NONE || this.supplyState.isReturningToOrigin() ? this.supplyState.commandDestinationBefore : this.commandDestination;
        if (persistedCommandTarget != null && persistedOrder == NpcOrderType.MOVE_TO_POSITION) {
            npc.method_10569("CommandTargetX", persistedCommandTarget.method_10263());
            npc.method_10569("CommandTargetY", persistedCommandTarget.method_10264());
            npc.method_10569("CommandTargetZ", persistedCommandTarget.method_10260());
        }
        class_243 class_2432 = persistedHold = this.supplyState.request != NpcSupplyRequest.NONE || this.supplyState.isReturningToOrigin() ? this.supplyState.holdPositionBefore : this.holdPosition;
        if ((persistedOrder == NpcOrderType.HOLD_POSITION || persistedOrder == NpcOrderType.OBSERVATION_POINT) && persistedHold != null) {
            npc.method_10549("HoldX", persistedHold.field_1352);
            npc.method_10549("HoldY", persistedHold.field_1351);
            npc.method_10549("HoldZ", persistedHold.field_1350);
        }
        this.patrolState.setPatrolOrderActive(this.getOrderType() == NpcOrderType.PATROL);
        npc.method_10566("PatrolState", (class_2520)this.patrolState.toNbt());
        npc.method_10566("Hunger", (class_2520)this.hungerData.toNbt());
        if (!(this.getNpcRole() != NpcRole.QUARTERMASTER || this.quartermasterFarmState.composter == null && this.quartermasterFarmState.fieldCenters.isEmpty())) {
            npc.method_10566("QuartermasterFarm", (class_2520)this.quartermasterFarmState.toNbt());
        }
        if (this.machineAssignment.isAssigned()) {
            npc.method_10566("MachineAssignment", (class_2520)this.machineAssignment.toNbt());
        }
        if (this.castleTransferData.isActive()) {
            npc.method_10566("CastleTransfer", (class_2520)this.castleTransferData.toNbt());
        }
        if (this.eatingState.sourceSlot >= 0) {
            npc.method_10569("EatingSourceSlot", this.eatingState.sourceSlot);
            npc.method_10569("EatingTicksRemaining", this.eatingState.ticksRemaining);
            npc.method_10548("PendingEatHeal", this.eatingState.pendingHeal);
            npc.method_10569("PendingFoodHunger", this.eatingState.pendingHunger);
            npc.method_10548("PendingFoodSaturation", this.eatingState.pendingSaturationModifier);
            if (!this.eatingState.previousOffhand.method_7960()) {
                npc.method_10566("EatingPreviousOffhand", (class_2520)this.eatingState.previousOffhand.method_7953(new class_2487()));
            }
        }
        nbt.method_10566("MedievalMachineryNpc", (class_2520)npc);
    }

    public void method_5749(class_2487 nbt) {
        class_1937 class_19372;
        super.method_5749(nbt);
        if (!nbt.method_10573("MedievalMachineryNpc", 10)) {
            return;
        }
        class_2487 npc = nbt.method_10562("MedievalMachineryNpc");
        this.lastAppliedGroupOrderRevision = npc.method_10545("LastAppliedGroupOrderRevision") ? npc.method_10537("LastAppliedGroupOrderRevision") : -1L;
        this.lastAppliedGroupFormationRevision = npc.method_10545("LastAppliedGroupFormationRevision") ? npc.method_10537("LastAppliedGroupFormationRevision") : -1L;
        this.lastAppliedGroupShieldRevision = npc.method_10545("LastAppliedGroupShieldRevision") ? npc.method_10537("LastAppliedGroupShieldRevision") : -1L;
        this.lastAppliedGroupCombatStanceRevision = npc.method_10545("LastAppliedGroupCombatStanceRevision") ? npc.method_10537("LastAppliedGroupCombatStanceRevision") : -1L;
        this.homePosition = npc.method_10573("HomeX", 3) && npc.method_10573("HomeY", 3) && npc.method_10573("HomeZ", 3) ? new class_2338(npc.method_10550("HomeX"), npc.method_10550("HomeY"), npc.method_10550("HomeZ")) : null;
        this.identity.readNbt(npc);
        this.setNpcRole(NpcRole.fromSerializedName(npc.method_10558("Role")));
        if (npc.method_10573("QuartermasterFarm", 10)) {
            this.quartermasterFarmState.readNbt(npc.method_10562("QuartermasterFarm"));
        } else {
            this.quartermasterFarmState.composter = null;
            this.quartermasterFarmState.clearFields();
        }
        this.field_6011.method_12778(TRACKED_QUARTERMASTER_FIELD_COUNT, this.quartermasterFarmState.fieldCenters.size());
        this.field_6011.method_12778(TRACKED_QUARTERMASTER_COMPOSTER, (this.quartermasterFarmState.composter != null));
        class_1799 selectedPlant = this.personalInventory.method_5438(9);
        this.field_6011.method_12778(TRACKED_QUARTERMASTER_PLANT_ITEM, (selectedPlant.method_7960() ? 0 : class_7923.field_41178.method_10206(selectedPlant.method_7909())));
        if (this.quartermasterFarmState.composter != null && (class_19372 = this.method_37908()) instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            QuartermasterComposterPoiService.suppress(serverWorld, this.quartermasterFarmState.composter);
        }
        if (npc.method_10573("TargetPriority", 8)) {
            this.setTargetPriority(NpcTargetPriority.fromSerializedName(npc.method_10558("TargetPriority")));
        } else {
            this.ensureRoleTargetPriorityDefault();
        }
        if (this.supportsPreferredMeleeWeaponSelection()) {
            NpcPreferredMeleeWeapon fallback = this.getNpcRole() == NpcRole.MOUNTED_KNIGHT ? NpcPreferredMeleeWeapon.SPEAR : NpcPreferredMeleeWeapon.SWORD;
            this.preferredMeleeWeapon = npc.method_10573("PreferredMeleeWeapon", 8) ? NpcPreferredMeleeWeapon.fromSerializedName(npc.method_10558("PreferredMeleeWeapon")) : fallback;
            this.field_6011.method_12778(TRACKED_PREFERRED_MELEE_WEAPON, this.preferredMeleeWeapon.ordinal());
        }
        this.setNpcRank(npc.method_10573("Rank", 8) ? NpcRank.fromSerializedName(npc.method_10558("Rank")) : NpcRank.NONE);
        this.setOrderType(NpcOrderType.fromSerializedName(npc.method_10558("Order")));
        if (this.getOrderType() == NpcOrderType.MOVE_TO_POSITION && npc.method_10573("CommandTargetX", 3) && npc.method_10573("CommandTargetY", 3) && npc.method_10573("CommandTargetZ", 3)) {
            this.commandDestination = new class_2338(npc.method_10550("CommandTargetX"), npc.method_10550("CommandTargetY"), npc.method_10550("CommandTargetZ"));
        }
        if ((this.getOrderType() == NpcOrderType.HOLD_POSITION || this.getOrderType() == NpcOrderType.OBSERVATION_POINT) && npc.method_10573("HoldX", 6) && npc.method_10573("HoldY", 6) && npc.method_10573("HoldZ", 6)) {
            this.holdPosition = new class_243(npc.method_10574("HoldX"), npc.method_10574("HoldY"), npc.method_10574("HoldZ"));
            this.holdDisplacedTicks = 0;
        }
        if (npc.method_10573("PatrolState", 10)) {
            this.patrolState.readNbt(npc.method_10562("PatrolState"));
        } else {
            this.clearPatrolRoute();
        }
        this.patrolState.setPatrolOrderActive(this.getOrderType() == NpcOrderType.PATROL);
        if (npc.method_10573("Hunger", 10)) {
            this.hungerData.readNbt(npc.method_10562("Hunger"));
        }
        if (npc.method_10573("MachineAssignment", 10)) {
            this.machineAssignment.readNbt(npc.method_10562("MachineAssignment"));
        } else {
            this.machineAssignment.clear();
        }
        if (npc.method_10573("CastleTransfer", 10)) {
            this.castleTransferData.readNbt(npc.method_10562("CastleTransfer"));
        } else {
            this.castleTransferData.clear();
        }
        this.field_6011.method_12778(TRACKED_HUNGER, this.hungerData.getFoodLevel());
        if (npc.method_10573("EatingSourceSlot", 3)) {
            this.eatingState.sourceSlot = npc.method_10550("EatingSourceSlot");
            this.eatingState.ticksRemaining = npc.method_10550("EatingTicksRemaining");
            this.eatingState.pendingHeal = npc.method_10583("PendingEatHeal");
            this.eatingState.pendingHunger = npc.method_10573("PendingFoodHunger", 3) ? Math.max(0, npc.method_10550("PendingFoodHunger")) : 0;
            float f = this.eatingState.pendingSaturationModifier = npc.method_10573("PendingFoodSaturation", 5) ? Math.max(0.0f, npc.method_10583("PendingFoodSaturation")) : 0.0f;
            if (npc.method_10573("EatingPreviousOffhand", 10)) {
                this.eatingState.previousOffhand = class_1799.method_7915((class_2487)npc.method_10562("EatingPreviousOffhand"));
            }
        }
        this.setCombatStance(npc.method_10573("CombatStance", 8) ? NpcCombatStance.fromSerializedName(npc.method_10558("CombatStance")) : NpcCombatStance.NEUTRAL);
        this.setFormation(npc.method_10573("Formation", 8) ? NpcFormation.fromSerializedName(npc.method_10558("Formation")) : NpcFormation.FREE);
        this.setFormationSlot(npc.method_10573("FormationSlot", 3) ? npc.method_10550("FormationSlot") : -1);
        this.combatState.setHoldFire(npc.method_10577("HoldFire"));
        if (!this.combatState.isHoldFire() && npc.method_10577("StrategicAreaFire") && npc.method_10573("StrategicAreaFireX", 6) && npc.method_10573("StrategicAreaFireY", 6) && npc.method_10573("StrategicAreaFireZ", 6)) {
            this.strategicAreaFireActive = true;
            this.strategicAreaFireCenter = new class_243(npc.method_10574("StrategicAreaFireX"), npc.method_10574("StrategicAreaFireY"), npc.method_10574("StrategicAreaFireZ"));
            this.strategicAreaFireRadius = class_3532.method_15350((double)(npc.method_10573("StrategicAreaFireRadius", 6) ? npc.method_10574("StrategicAreaFireRadius") : 3.5), (double)0.5, (double)12.0);
        } else {
            this.strategicAreaFireActive = false;
            this.strategicAreaFireCenter = null;
        }
        this.formationState.setSpacingMultiplier(npc.method_10573("FormationSpacing", 6) ? npc.method_10574("FormationSpacing") : 1.0);
        if (npc.method_10573("FormationFacingYaw", 5)) {
            this.formationState.setFacingYaw(npc.method_10583("FormationFacingYaw"));
        }
        this.setShieldMode(npc.method_10573("ShieldMode", 8) ? NpcShieldMode.fromSerializedName(npc.method_10558("ShieldMode")) : NpcShieldMode.AUTO);
        this.setAppearanceId(npc.method_10558("Appearance"));
        this.setOrderStyle(OrderStyle.fromSerializedName(npc.method_10558("OrderStyle")));
        this.setArmorTier(ArmorTier.fromSerializedName(npc.method_10558("ArmorTier")));
        this.field_6011.method_12778(TRACKED_LEVEL, Math.max(1, npc.method_10573("Level", 3) ? npc.method_10550("Level") : 1));
        this.field_6011.method_12778(TRACKED_EXPERIENCE, Math.max(0, npc.method_10573("Experience", 3) ? npc.method_10550("Experience") : 0));
        this.moraleData.set(npc.method_10573("Morale", 5) ? npc.method_10583("Morale") : 50.0f);
        this.field_6011.method_12778(TRACKED_MORALE, Float.valueOf(this.moraleData.get()));
        this.applyLevelProgressionModifiers();
        this.applyMoraleModifiers();
        this.field_6011.method_12778(TRACKED_CASTLE_NAME, (npc.method_10573("CastleDisplayName", 8) ? npc.method_10558("CastleDisplayName") : "-"));
        this.setArmorStyleOverride(npc.method_10573("ArmorStyleOverride", 8) ? ArmorStyle.fromSerializedName(npc.method_10558("ArmorStyleOverride")) : null);
        if (npc.method_10573("Inventory", 10)) {
            this.personalInventory.method_5448();
            class_2371 inventoryStacks = class_2371.method_10213((int)this.personalInventory.method_5439(), (Object)class_1799.field_8037);
            class_1262.method_5429((class_2487)npc.method_10562("Inventory"), (class_2371)inventoryStacks);
            this.personalInventory.method_5448();
            for (int i = 0; i < inventoryStacks.size(); ++i) {
                this.personalInventory.method_5447(i, (class_1799)inventoryStacks.get(i));
            }
        }
        if (this.eatingState.sourceSlot >= 0) {
            this.finishEating(false);
        }
        this.maintenanceState.equipmentDirty = true;
        this.maintenanceState.inventoryCacheDirty = true;
        this.maintenanceState.organizationDirty = true;
        this.maintenanceState.registryDirty = true;
    }

    private void cleanupOrganizationAfterRemoval() {
        if (this.maintenanceState.organizationRemovalCleaned || this.method_37908().field_9236) {
            return;
        }
        this.maintenanceState.organizationRemovalCleaned = true;
        class_1937 class_19372 = this.method_37908();
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            CommandGroupManager.get(serverWorld).removeSoldierEverywhere(this.method_5667());
            SoldierRegistryManager.get(serverWorld).remove(this.method_5667());
            GarrisonManager.get(serverWorld).removeSoldierEverywhere(this.method_5667());
        }
    }

    public void method_6078(class_1282 damageSource) {
        class_1937 class_19372;
        if (!this.method_37908().field_9236 && (class_19372 = this.method_37908()) instanceof class_3218) {
            class_3218 bodyguardWorld = (class_3218)class_19372;
            GeneralBodyguardService.onSoldierDeath(bodyguardWorld, this);
        }
        if (!this.method_37908().field_9236 && this.getNpcRank() == NpcRank.CAPTAIN && (class_19372 = this.method_37908()) instanceof class_3218) {
            class_3218 captainWorld = (class_3218)class_19372;
            CaptainAssignmentService.clearOnCaptainDeath(captainWorld, this);
        }
        if (!this.method_37908().field_9236 && this.getNpcRank() == NpcRank.GENERAL && this.getNpcIdentity().getCastleId() != null && (class_19372 = this.method_37908()) instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            GeneralCooldownManager.get(serverWorld).start(serverWorld, this.getNpcIdentity().getCastleId());
        }
        this.shockNearbyAlliesOnDeath();
        this.cleanupOrganizationAfterRemoval();
        super.method_6078(damageSource);
    }

    protected void method_16078() {
        if (!this.method_37908().field_9236 && this.eatingState.sourceSlot >= 0) {
            this.finishEating(false);
        }
        super.method_16078();
        if (!this.method_37908().field_9236) {
            for (int slot = 0; slot < this.personalInventory.method_5439(); ++slot) {
                class_1799 stack = this.personalInventory.method_5441(slot);
                if (stack.method_7960()) continue;
                this.method_5775(stack);
            }
            this.cleanupOrganizationAfterRemoval();
        }
    }

    public void forgetCurrentTarget() {
        this.combatState.clearCommandedTarget();
        this.method_5980(null);
        this.combatTargetState.resetSight();
        this.combatTargetState.suppressTargetAcquisition(200);
        this.stopRangedWeaponUse();
        this.clearRangedVisual();
        this.stopAdaptiveNavigation();
    }

    public void commandAttackTarget(class_1309 target) {
        if (target == null || !target.method_5805() || target == this) {
            return;
        }
        this.combatTargetState.acceptCommandedTarget();
        this.combatState.setCommandedTarget(target.method_5667());
        this.method_5980(target);
        this.stopAdaptiveNavigation();
    }

    boolean isCommandedCombatTarget(@Nullable class_1309 target) {
        return target != null && this.combatState.isCommandedTarget(target.method_5667());
    }

    void acceptCommandedTargetForGoal() {
        this.combatTargetState.acceptCommandedTarget();
    }

    boolean mayAcquireCombatTarget() {
        return !this.machineAssignmentOwnsPersonalCombat() && this.combatTargetState.mayAcquireTarget();
    }

    void abandonAutomaticCombatTarget(int reacquireDelayTicks) {
        class_1309 target = this.method_5968();
        if (target != null && this.isCommandedCombatTarget(target)) {
            return;
        }
        this.method_5980(null);
        this.combatTargetState.resetSight();
        if (reacquireDelayTicks > 0) {
            this.combatTargetState.suppressTargetAcquisition(reacquireDelayTicks);
        }
        this.stopAdaptiveNavigation();
        this.formationCombatFinished();
        this.resumeSuspendedOrderIfPossible();
    }

    boolean isWithinPatrolCombatLeash(@Nullable class_1309 target) {
        return MedievalSoldierPatrolCombatPolicy.allowsTarget(this, target);
    }

    boolean hasCarriedRangedWeapon() {
        if (NpcWeaponProfileResolver.resolve(this.method_6047()).isRanged()) {
            return true;
        }
        for (int slot = 0; slot < Math.min(9, this.personalInventory.method_5439()); ++slot) {
            if (!NpcWeaponProfileResolver.resolve(this.personalInventory.method_5438(slot)).isRanged()) continue;
            return true;
        }
        return false;
    }

    public boolean isOnClimbableSurface() {
        class_2338 base = this.method_24515();
        class_238 body = this.method_5829().method_1009(0.12, 0.08, 0.12);
        for (int dy = -1; dy <= 2; ++dy) {
            for (int dx = -1; dx <= 1; ++dx) {
                for (int dz = -1; dz <= 1; ++dz) {
                    class_2338 pos = base.method_10069(dx, dy, dz);
                    if (!this.method_37908().method_8320(pos).method_26164(class_3481.field_22414) || !body.method_994(new class_238(pos))) continue;
                    return true;
                }
            }
        }
        return false;
    }

    boolean isRangedRole() {
        return this.getNpcRole() == NpcRole.ARCHER || this.getNpcRole() == NpcRole.CROSSBOWMAN || this.getNpcRole() == NpcRole.MOUNTED_ARCHER;
    }

    boolean isCloseCombatWeapon(class_1799 stack) {
        return stack != null && !stack.method_7960() && NpcWeaponProfileResolver.resolve(stack).isCloseCombat();
    }

    long closeCombatWeaponScore(class_1799 stack) {
        return this.equipmentController.closeCombatWeaponScore(this, stack);
    }

    boolean switchRangedUnitToCloseCombatWeapon() {
        if (!MedievalMachineryConfig.get().automaticEquipmentSelectionEnabled) {
            return false;
        }
        if (!this.isRangedRole() || this.combatState.rangedCloseCombatWeaponActive || !this.isHoldingRangedWeapon()) {
            return false;
        }
        int bestSlot = -1;
        long bestScore = Long.MIN_VALUE;
        for (int slot = 0; slot < Math.min(9, this.personalInventory.method_5439()); ++slot) {
            class_1799 candidate = this.personalInventory.method_5438(slot);
            long score = this.closeCombatWeaponScore(candidate);
            if (score <= bestScore) continue;
            bestScore = score;
            bestSlot = slot;
        }
        if (bestSlot < 0) {
            return false;
        }
        class_1799 sidearm = this.personalInventory.method_5441(bestSlot);
        if (sidearm.method_7960()) {
            return false;
        }
        class_1799 rangedWeapon = this.method_6047().method_7972();
        this.stopRangedWeaponUse();
        this.method_5673(class_1304.field_6173, sidearm);
        this.personalInventory.method_5447(bestSlot, rangedWeapon);
        this.personalInventory.method_5431();
        this.combatState.rangedCloseCombatWeaponActive = true;
        this.combatState.rangedCloseCombatCalmTicks = 0;
        this.maintenanceState.equipmentDirty = false;
        this.maintenanceState.lastObservedMainHandItem = this.method_6047().method_7909();
        this.maintenanceState.registryDirty = true;
        this.updateWeaponClassDamageModifier();
        return true;
    }

    boolean restoreRangedWeaponAfterCloseCombat() {
        return this.fireControl.restoreRangedWeaponAfterCloseCombat(this);
    }

    private void tickRangedCloseCombatWeaponState() {
        this.fireControl.tickRangedCloseCombatWeaponState(this);
    }

    boolean hasCarriedFoodForWarning() {
        return this.supplyController.hasCarriedFood(this);
    }

    boolean usesMeleeCombat() {
        return switch (this.getNpcRole()) {
            case NpcRole.ARCHER, NpcRole.MOUNTED_ARCHER, NpcRole.CROSSBOWMAN -> this.combatState.rangedCloseCombatWeaponActive;
            case NpcRole.QUARTERMASTER -> false;
            default -> true;
        };
    }

    boolean isHoldingRangedWeapon() {
        return NpcWeaponProfileResolver.resolve(this.method_6047()).isRanged();
    }

    public int getRangedAttackTicks() {
        return Math.max(0, (Integer)this.field_6011.method_12789(TRACKED_RANGED_ATTACK_TICKS));
    }

    public boolean isRangedAttackAnimating() {
        return this.getRangedVisualState() != NpcRangedVisualState.IDLE || this.getRangedAttackTicks() > 0 || this.method_6115();
    }

    public NpcRangedVisualState getRangedVisualState() {
        return NpcRangedVisualState.fromOrdinal((Integer)this.field_6011.method_12789(TRACKED_RANGED_VISUAL_STATE));
    }

    void beginRangedAttackAnimation(int ticks) {
        this.beginRangedVisual(NpcRangedVisualState.PREPARE, ticks);
    }

    void beginRangedVisual(NpcRangedVisualState state, int ticks) {
        this.field_6011.method_12778(TRACKED_RANGED_VISUAL_STATE, (state == null ? NpcRangedVisualState.IDLE : state).ordinal());
        this.field_6011.method_12778(TRACKED_RANGED_ATTACK_TICKS, Math.max(1, ticks));
    }

    void setRangedVisualState(NpcRangedVisualState state) {
        this.field_6011.method_12778(TRACKED_RANGED_VISUAL_STATE, (state == null ? NpcRangedVisualState.IDLE : state).ordinal());
    }

    void markCrossbowBoltInsertedForVisual() {
        class_1799 stack = this.method_6047();
        if (!(stack.method_7909() instanceof class_1764)) {
            return;
        }
        stack.method_7948().method_10556("Charged", true);
    }

    void markCrossbowUnloadedAfterShot() {
        class_1799 stack = this.method_6047();
        if (!(stack.method_7909() instanceof class_1764)) {
            return;
        }
        if (stack.method_7985()) {
            stack.method_7969().method_10556("Charged", false);
            stack.method_7969().method_10551("ChargedProjectiles");
        }
    }

    void holdRangedReleaseVisual(boolean crossbow) {
        this.holdRangedReleaseVisual(crossbow, this.method_5765());
    }

    void holdRangedReleaseVisual(boolean crossbow, boolean mounted) {
        this.beginRangedVisual(NpcRangedVisualState.RELEASE, MedievalSoldierRangedFirePolicy.releaseHoldTicks(crossbow, mounted));
    }

    void clearRangedVisual() {
        this.field_6011.method_12778(TRACKED_RANGED_VISUAL_STATE, NpcRangedVisualState.IDLE.ordinal());
        this.field_6011.method_12778(TRACKED_RANGED_ATTACK_TICKS, 0);
    }

    void stopRangedWeaponUse() {
        if (this.method_6115()) {
            this.method_6075();
        }
    }

    void fireStandardArrowAt(class_1309 target) {
        this.rangedProjectileController.fireAt(this, target);
    }

    void fireStandardArrowAtPoint(class_243 aimPoint) {
        this.rangedProjectileController.fireAtPoint(this, aimPoint);
    }

    class_243 rangedShotOrigin() {
        class_243 forward = this.method_5828(1.0f).method_1029();
        class_243 right = new class_243(-forward.field_1350, 0.0, forward.field_1352);
        double handSide = this.method_6068() == class_1306.field_6183 ? 0.26 : -0.26;
        double mountedY = this.method_5765() ? -0.3 : 0.0;
        return this.method_33571().method_1031(0.0, -0.24 + mountedY, 0.0).method_1019(forward.method_1021(0.42)).method_1019(right.method_1021(handSide));
    }

    boolean hasFriendlyInLineOfFire(class_1309 target) {
        if (target == null) {
            return false;
        }
        class_243 start = this.rangedShotOrigin();
        class_243 end = new class_243(target.method_23317(), target.method_23323(0.55), target.method_23321());
        class_238 corridor = new class_238(start, end).method_1014(0.75);
        for (class_1309 other2 : this.method_37908().method_8390(class_1309.class, corridor, other -> other != this && other.method_5805() && this.isFriendlyCombatEntity((class_1309)other))) {
            if (!other2.method_5829().method_1014(0.2).method_992(start, end).isPresent()) continue;
            return true;
        }
        return false;
    }

    boolean hasFriendlyInLineOfFire(class_243 end) {
        if (end == null) {
            return false;
        }
        class_243 start = this.rangedShotOrigin();
        class_238 corridor = new class_238(start, end).method_1014(0.75);
        for (class_1309 other2 : this.method_37908().method_8390(class_1309.class, corridor, other -> other != this && other.method_5805() && this.isFriendlyCombatEntity((class_1309)other))) {
            if (!other2.method_5829().method_1014(0.2).method_992(start, end).isPresent()) continue;
            return true;
        }
        return false;
    }

    private boolean hasClearBlockLineOfFire(class_243 shooterPos, class_1309 target) {
        return this.fireControl.hasClearBlockLineOfFire(this, shooterPos, target);
    }

    boolean isTrueCloseRangedThreat(class_1309 target, double minimumRange) {
        if (target == null) {
            return false;
        }
        double dx = target.method_23317() - this.method_23317();
        double dz = target.method_23321() - this.method_23321();
        double horizontalSq = dx * dx + dz * dz;
        double vertical = Math.abs(target.method_23323(0.5) - this.method_23323(0.5));
        return horizontalSq < minimumRange * minimumRange && vertical < 1.45;
    }

    boolean canShootCloseTargetFromHeight(class_1309 target) {
        if (target == null) {
            return false;
        }
        double vertical = this.method_23323(0.5) - target.method_23323(0.5);
        return vertical > 1.2 && this.method_5985().method_6369((class_1297)target);
    }

    void sidestepForClearShot(class_1309 target, double speed) {
        if (target == null || !this.combatMovementState.mayReposition()) {
            return;
        }
        class_243 candidate = MedievalSoldierRangedLanePolicy.chooseLane(this, target);
        if (candidate == null) {
            this.combatMovementState.markRepositioned(8);
            return;
        }
        this.adaptiveNavigation.tickToPoint(candidate, speed);
        this.combatMovementState.markRepositioned(MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(this));
    }

    boolean tryMeleeCombatReposition(class_1309 target, double speed) {
        double dz;
        if (target == null || !this.combatMovementState.mayReposition()) {
            return false;
        }
        if (this.method_5858((class_1297)target) >= 10.0) {
            return false;
        }
        double dx = this.method_23317() - target.method_23317();
        double len = Math.sqrt(dx * dx + (dz = this.method_23321() - target.method_23321()) * dz);
        if (len < 0.001) {
            return false;
        }
        double awayX = dx / len;
        double awayZ = dz / len;
        double sideX = -awayZ * (double)this.combatMovementState.preferredSide(this.method_5667().hashCode());
        double sideZ = awayX * (double)this.combatMovementState.preferredSide(this.method_5667().hashCode());
        int choice = this.method_6051().method_43048(3);
        double moveX = choice == 2 ? awayX : (choice == 1 ? -sideX : sideX);
        double moveZ = choice == 2 ? awayZ : (choice == 1 ? -sideZ : sideZ);
        class_243 candidate = new class_243(this.method_23317() + moveX * 0.85, this.method_23318(), this.method_23321() + moveZ * 0.85);
        if (!this.isSafeCombatPosition(candidate) || !this.isWithinPatrolCombatLeash(target)) {
            this.combatMovementState.markRepositioned(10);
            return false;
        }
        this.adaptiveNavigation.tickToPoint(candidate, Math.max(1.0, speed));
        this.combatMovementState.markRepositioned(10);
        return true;
    }

    boolean isSafeCombatPosition(class_243 candidate) {
        class_2338 feet = class_2338.method_49637((double)candidate.field_1352, (double)candidate.field_1351, (double)candidate.field_1350);
        class_2338 head = feet.method_10084();
        class_2338 floor = feet.method_10074();
        if (!this.method_37908().method_8320(feet).method_26220((class_1922)this.method_37908(), feet).method_1110()) {
            return false;
        }
        if (!this.method_37908().method_8320(head).method_26220((class_1922)this.method_37908(), head).method_1110()) {
            return false;
        }
        return !this.method_37908().method_8320(floor).method_26220((class_1922)this.method_37908(), floor).method_1110();
    }

    private boolean withinRangedRetreatLeash(class_243 candidate) {
        return this.fireControl.withinRangedRetreatLeash(this, candidate);
    }

    boolean retreatForRangedCombat(class_1309 target, double speed, double desiredDistance) {
        double[] lateral;
        double dz;
        if (target == null || !this.combatMovementState.mayReposition()) {
            return false;
        }
        double dx = this.method_23317() - target.method_23317();
        double len = Math.sqrt(dx * dx + (dz = this.method_23321() - target.method_23321()) * dz);
        if (len < 0.001) {
            return false;
        }
        double awayX = dx / len;
        double awayZ = dz / len;
        double rightX = -awayZ;
        double rightZ = awayX;
        int side = this.combatMovementState.preferredSide(this.method_5667().hashCode());
        double retreat = class_3532.method_15350((double)(desiredDistance - len), (double)2.0, (double)4.5);
        for (double sideOffset : lateral = new double[]{0.75 * (double)side, 1.75 * (double)side, -1.25 * (double)side, 0.0}) {
            class_243 candidate = new class_243(this.method_23317() + awayX * retreat + rightX * sideOffset, this.method_23318(), this.method_23321() + awayZ * retreat + rightZ * sideOffset);
            if (!this.isSafeCombatPosition(candidate) || !this.withinRangedRetreatLeash(candidate)) continue;
            this.method_5728(true);
            this.adaptiveNavigation.tickToPoint(candidate, speed);
            this.combatMovementState.markRepositioned(10);
            return true;
        }
        return false;
    }

    boolean combatIsSafePosition(class_243 candidate) {
        return this.isSafeCombatPosition(candidate);
    }

    boolean rangedHasClearLineFrom(class_243 candidate, class_1309 target) {
        return this.hasClearBlockLineOfFire(candidate, target);
    }

    boolean canAssistOwnerAgainst(@Nullable class_1309 target) {
        MedievalSoldierEntity other;
        if (target == null || !target.method_5805() || target == this) {
            return false;
        }
        if (target == this.method_5854() || target.method_5752().contains("medieval_machinery_mount")) {
            return false;
        }
        UUID ownerId = this.getNpcIdentity().getOwnerId();
        if (ownerId != null && target.method_5667().equals(ownerId)) {
            return false;
        }
        if (target instanceof MedievalSoldierEntity && this.isFriendlySoldier(other = (MedievalSoldierEntity)target)) {
            return false;
        }
        if (target instanceof class_1657) {
            class_1657 playerTarget = (class_1657)target;
            class_1937 class_19372 = this.method_37908();
            if (!(class_19372 instanceof class_3218)) {
                return false;
            }
            class_3218 serverWorld = (class_3218)class_19372;
            UUID ownKingdom = this.getNpcIdentity().getKingdomId();
            KingdomData playerKingdom = KingdomManager.get(serverWorld).findByMember(playerTarget.method_5667());
            return ownKingdom != null && playerKingdom != null && KingdomRelationPolicy.areHostile(serverWorld, ownKingdom, playerKingdom.getId()) && this.isWithinPatrolCombatLeash(target);
        }
        return this.isWithinPatrolCombatLeash(target);
    }

    boolean isRaidFriendly(class_1309 target) {
        MedievalSoldierEntity other;
        if (target == null || target == this) {
            return true;
        }
        if (target == this.method_5854() || target.method_5752().contains("medieval_machinery_mount")) {
            return true;
        }
        UUID ownerId = this.identity.getOwnerId();
        if (ownerId != null && ownerId.equals(target.method_5667())) {
            return true;
        }
        return target instanceof MedievalSoldierEntity && this.isFriendlySoldier(other = (MedievalSoldierEntity)target);
    }
}

