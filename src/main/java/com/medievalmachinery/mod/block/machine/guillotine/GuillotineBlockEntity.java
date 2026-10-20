/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1542
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1928
 *  net.minecraft.class_1928$class_4310
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2378
 *  net.minecraft.class_2382
 *  net.minecraft.class_2388
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
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
 *  net.minecraft.class_4153
 *  net.minecraft.class_6880
 *  net.minecraft.class_7924
 *  net.minecraft.server.MinecraftServer
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoBlockEntity
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.medievalmachinery.mod.block.machine.guillotine;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlock;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockAccess;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineState;
import com.medievalmachinery.mod.block.machine.guillotine.PunishmentMode;
import com.medievalmachinery.mod.block.storage.WickerBasketBlockEntity;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.execution.DeferredExecutionCommands;
import com.medievalmachinery.mod.execution.GuillotinePunishmentManager;
import com.medievalmachinery.mod.machine.core.MachineSeat;
import com.medievalmachinery.mod.platform.LoaderHooks;
import com.medievalmachinery.mod.registry.ModBlockEntities;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.registry.ModVillagers;
import com.medievalmachinery.mod.sound.ModSounds;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1928;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2378;
import net.minecraft.class_2382;
import net.minecraft.class_2388;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
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
import net.minecraft.class_4153;
import net.minecraft.class_6880;
import net.minecraft.class_7924;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class GuillotineBlockEntity
extends class_2586
implements GeoBlockEntity,
GuillotineBlockAccess {
    private static final String[] DARKHEADS_MOD_IDS = new String[]{"darkheads", "dark_heads", "rpgplayerheads"};
    public static final int BLADE_DROP_TICKS = 12;
    public static final int IMPACT_HOLD_TICKS = 18;
    public static final int RESET_TICKS = 20;
    public static final int TOTAL_SEQUENCE_TICKS = 50;
    private static final int WOOD_CREAK_TICK = 4;
    private static final int ROPE_TENSION_TICK = 8;
    private static final String STATE_NBT_KEY = "MachineState";
    private static final String POWERED_NBT_KEY = "RedstonePowered";
    private static final String OCCUPANT_NBT_KEY = "OccupantUuid";
    private static final String LOCKED_NBT_KEY = "OccupantLocked";
    private static final String SEQUENCE_TICKS_NBT_KEY = "SequenceTicks";
    private static final String BAN_ON_EXECUTION_NBT_KEY = "BanOnExecution";
    private static final String PUNISHMENT_MODE_NBT_KEY = "PunishmentMode";
    private static final String BROADCAST_NBT_KEY = "BroadcastExecution";
    private static final String DISPLAY_NAME_NBT_KEY = "DisplayName";
    private static final String LAST_EXECUTION_NBT_KEY = "LastExecution";
    private static final String CUSTOM_COMMAND_NBT_KEY = "CustomCommand";
    private static final String EXECUTION_COUNT_NBT_KEY = "ExecutionCount";
    private static final String EXECUTIONS_TODAY_NBT_KEY = "ExecutionsToday";
    private static final String STATS_DATE_NBT_KEY = "StatsDate";
    private static final String HEAD_DROP_NBT_KEY = "HeadDropEnabled";
    private static final String EXECUTIONER_ENABLED_NBT_KEY = "ExecutionerEnabled";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.ROOT);
    private static final double OCCUPANT_HEIGHT_OFFSET = 0.7675;
    private static final double OCCUPANT_BACKWARD_OFFSET = 0.7625;
    private static final double ROYAL_OCCUPANT_HEIGHT_OFFSET = 0.86;
    private static final double ROYAL_OCCUPANT_BACKWARD_OFFSET = 0.12;
    private static final double ROYAL_OCCUPANT_SIDE_OFFSET = 0.5125;
    private GuillotineState machineState = GuillotineState.EMPTY;
    private boolean redstonePowered;
    private int sequenceTicks;
    private int previousSequenceTicks;
    private boolean banOnExecution;
    private PunishmentMode punishmentMode = PunishmentMode.NONE;
    private boolean broadcastExecution = true;
    private String displayName = "";
    private String lastExecutionSummary = "Noch keine";
    private String customCommand = "";
    private int executionCount;
    private int executionsToday;
    private String statsDate = "";
    private boolean headDropEnabled = true;
    private boolean executionerEnabled = true;
    private int executionerPoiRefreshTicks;
    private final MachineSeat machineSeat = new MachineSeat();
    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    public static final float BLADE_MAX_DROP_PIXELS = 15.0f;
    public static final float ROYAL_BLADE_MAX_DROP_PIXELS = 27.0f;

    public GuillotineBlockEntity(class_2338 pos, class_2680 state) {
        super(ModBlockEntities.GUILLOTINE, pos, state);
    }

    public boolean isRoyalGuillotine() {
        return this.method_11010().method_27852(ModBlocks.ROYAL_GUILLOTINE);
    }

    public GuillotineState getMachineState() {
        return this.machineState;
    }

    @Override
    public boolean isEmpty() {
        return !this.machineSeat.isOccupied();
    }

    public boolean isOccupied() {
        return this.machineSeat.isOccupied();
    }

    public boolean isBanOnExecution() {
        return this.banOnExecution;
    }

    @Override
    public boolean toggleBanOnExecution() {
        this.banOnExecution = !this.banOnExecution;
        this.markDirtyAndSync();
        return this.banOnExecution;
    }

    public PunishmentMode getPunishmentMode() {
        return this.punishmentMode;
    }

    public void setPunishmentMode(PunishmentMode punishmentMode) {
        if (punishmentMode == null) {
            punishmentMode = PunishmentMode.NONE;
        }
        this.punishmentMode = punishmentMode;
        this.banOnExecution = punishmentMode == PunishmentMode.PERMANENT_BAN;
        this.markDirtyAndSync();
    }

    public boolean isBroadcastExecution() {
        return this.broadcastExecution;
    }

    public boolean toggleBroadcastExecution() {
        this.broadcastExecution = !this.broadcastExecution;
        this.markDirtyAndSync();
        return this.broadcastExecution;
    }

    public String getDisplayName() {
        if (this.displayName == null || this.displayName.isBlank()) {
            return "Guillotine " + this.field_11867.method_10263() + ", " + this.field_11867.method_10264() + ", " + this.field_11867.method_10260();
        }
        return this.displayName;
    }

    public String getConfiguredName() {
        return this.displayName == null ? "" : this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = GuillotineBlockEntity.sanitizeText(displayName, 64);
        this.markDirtyAndSync();
    }

    public String getCustomCommand() {
        return this.customCommand == null ? "" : this.customCommand;
    }

    public void setCustomCommand(String customCommand) {
        this.customCommand = GuillotineBlockEntity.sanitizeText(customCommand, 512);
        this.markDirtyAndSync();
    }

    public int getExecutionCount() {
        return this.executionCount;
    }

    public int getExecutionsToday() {
        this.refreshDailyCounter();
        return this.executionsToday;
    }

    public boolean isHeadDropEnabled() {
        return this.headDropEnabled;
    }

    public boolean toggleHeadDrop() {
        this.headDropEnabled = !this.headDropEnabled;
        this.markDirtyAndSync();
        return this.headDropEnabled;
    }

    public boolean isExecutionerEnabled() {
        return this.executionerEnabled;
    }

    public boolean toggleExecutionerEnabled() {
        return this.setExecutionerEnabled(!this.executionerEnabled);
    }

    public boolean setExecutionerEnabled(boolean enabled) {
        this.executionerEnabled = enabled;
        this.updateExecutionerPoi();
        this.markDirtyAndSync();
        return this.executionerEnabled;
    }

    public String getLastExecutionSummary() {
        return this.lastExecutionSummary == null || this.lastExecutionSummary.isBlank() ? "Noch keine" : this.lastExecutionSummary;
    }

    @Override
    public boolean isLocked() {
        return this.machineSeat.isLocked();
    }

    @Override
    public boolean isSequenceRunning() {
        return this.machineState == GuillotineState.TRIGGERED || this.machineState == GuillotineState.RESETTING;
    }

    public float getSequenceTicks(float tickDelta) {
        return (float)this.previousSequenceTicks + (float)(this.sequenceTicks - this.previousSequenceTicks) * tickDelta;
    }

    public float getBladeDropPixels(float tickDelta) {
        float ticks = this.getSequenceTicks(tickDelta);
        if (ticks <= 0.0f) {
            return 0.0f;
        }
        if (ticks < 12.0f) {
            float progress = ticks / 12.0f;
            return this.getBladeMaxDropPixels() * progress * progress;
        }
        if (ticks < 30.0f) {
            return this.getBladeMaxDropPixels();
        }
        float resetProgress = (ticks - 12.0f - 18.0f) / 20.0f;
        return this.getBladeMaxDropPixels() * (1.0f - Math.min(resetProgress, 1.0f));
    }

    private float getBladeMaxDropPixels() {
        return this.isRoyalGuillotine() ? 27.0f : 15.0f;
    }

    public void setMachineState(GuillotineState machineState) {
        if (machineState == null || this.machineState == machineState) {
            return;
        }
        this.machineState = machineState;
        this.markDirtyAndSync();
    }

    public MachineSeat getMachineSeat() {
        return this.machineSeat;
    }

    @Override
    public boolean occupy(UUID entityUuid) {
        if (!this.machineSeat.occupy(entityUuid)) {
            return false;
        }
        this.sequenceTicks = 0;
        this.previousSequenceTicks = 0;
        this.machineState = GuillotineState.OCCUPIED;
        this.markDirtyAndSync();
        return true;
    }

    public void lockOccupant() {
        if (!this.machineSeat.isOccupied()) {
            return;
        }
        this.machineSeat.lock();
        this.machineState = GuillotineState.LOCKED;
        this.markDirtyAndSync();
    }

    @Override
    public void startSequence() {
        if (this.isSequenceRunning()) {
            return;
        }
        if (this.machineSeat.isOccupied()) {
            this.machineSeat.lock();
        }
        this.machineState = GuillotineState.TRIGGERED;
        this.sequenceTicks = 0;
        this.previousSequenceTicks = 0;
        this.markDirtyAndSync();
    }

    @Override
    public boolean releaseOccupant() {
        if (!this.machineSeat.release()) {
            return false;
        }
        this.machineState = GuillotineState.EMPTY;
        this.sequenceTicks = 0;
        this.previousSequenceTicks = 0;
        this.markDirtyAndSync();
        return true;
    }

    @Override
    public boolean isOccupiedBy(UUID entityUuid) {
        return this.machineSeat.isOccupiedBy(entityUuid);
    }

    @Nullable
    public UUID getOccupantUuid() {
        return this.machineSeat.getOccupantUuid();
    }

    @Override
    public boolean updateRedstonePower(boolean powered) {
        boolean risingEdge;
        boolean bl = risingEdge = powered && !this.redstonePowered;
        if (this.redstonePowered != powered) {
            this.redstonePowered = powered;
            this.markDirtyAndSync();
        }
        return risingEdge;
    }

    public static void tick(class_1937 world, class_2338 pos, class_2680 state, GuillotineBlockEntity guillotine) {
        class_3222 player;
        guillotine.tickSequence(world);
        if (!world.field_9236 && world instanceof class_3218) {
            ++guillotine.executionerPoiRefreshTicks;
            if (guillotine.executionerPoiRefreshTicks >= 20) {
                guillotine.executionerPoiRefreshTicks = 0;
                guillotine.updateExecutionerPoi();
            }
        }
        if (world.field_9236 || !(world instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)world;
        guillotine.checkMachineRedstone(serverWorld, state);
        UUID occupantUuid = guillotine.getOccupantUuid();
        if (occupantUuid == null) {
            return;
        }
        class_1297 entity = serverWorld.method_14190(occupantUuid);
        if (!(entity instanceof class_3222) || !(player = (class_3222)entity).method_5805()) {
            guillotine.releaseOccupant();
            return;
        }
        if (!guillotine.isLocked() && player.method_5715()) {
            guillotine.releaseOccupant();
            return;
        }
        class_2350 facing = (class_2350)state.method_11654((class_2769)GuillotineBlock.FACING);
        double backwardOffset = guillotine.isRoyalGuillotine() ? 0.12 : 0.7625;
        double heightOffset = guillotine.isRoyalGuillotine() ? 0.86 : 0.7675;
        double sideOffset = guillotine.isRoyalGuillotine() ? 0.5125 : 0.0;
        class_2350 right = facing.method_10170();
        class_243 targetPosition = class_243.method_24955((class_2382)pos).method_1031((double)(-facing.method_10148()) * backwardOffset + (double)right.method_10148() * sideOffset, heightOffset, (double)(-facing.method_10165()) * backwardOffset + (double)right.method_10165() * sideOffset);
        float targetYaw = facing.method_10144();
        player.method_5859(targetPosition.field_1352, targetPosition.field_1351, targetPosition.field_1350);
        player.method_5636(targetYaw);
        player.method_18799(class_243.field_1353);
        player.field_6017 = 0.0f;
    }

    private void updateExecutionerPoi() {
        class_1937 class_19372 = this.field_11863;
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_4153 poiStorage = serverWorld.method_19494();
        boolean registered = poiStorage.method_26339(ModVillagers.GUILLOTINE_POI_KEY, this.field_11867);
        if (!this.executionerEnabled) {
            if (registered) {
                poiStorage.method_19112(this.field_11867);
            }
            return;
        }
        if (registered) {
            return;
        }
        class_2378 poiRegistry = serverWorld.method_30349().method_30530(class_7924.field_41212);
        poiStorage.method_19115(this.field_11867, (class_6880)poiRegistry.method_40290(ModVillagers.GUILLOTINE_POI_KEY));
    }

    private void checkMachineRedstone(class_3218 world, class_2680 state) {
        class_2350 facing = (class_2350)state.method_11654((class_2769)GuillotineBlock.FACING);
        class_2338 tablePos = this.field_11867.method_10093(facing.method_10153());
        boolean powered = world.method_49803(this.field_11867) || world.method_49803(this.field_11867.method_10084()) || world.method_49803(tablePos) || world.method_49803(tablePos.method_10084());
        boolean risingEdge = this.updateRedstonePower(powered);
        if (!risingEdge || this.isSequenceRunning()) {
            return;
        }
        this.startSequence();
        world.method_8396(null, this.field_11867, class_3417.field_15131, class_3419.field_15245, 1.0f, 0.8f);
    }

    private void tickSequence(class_1937 world) {
        this.previousSequenceTicks = this.sequenceTicks++;
        if (!this.isSequenceRunning()) {
            return;
        }
        if (!world.field_9236 && this.sequenceTicks == 4) {
            world.method_8396(null, this.field_11867, class_3417.field_14932, class_3419.field_15245, 0.65f, 0.55f);
        }
        if (!world.field_9236 && this.sequenceTicks == 8) {
            world.method_8396(null, this.field_11867, class_3417.field_24063, class_3419.field_15245, 0.55f, 1.35f);
        }
        if (!world.field_9236 && this.sequenceTicks == 12) {
            this.handleImpact(world);
        }
        if (!world.field_9236 && this.machineState == GuillotineState.TRIGGERED && this.sequenceTicks >= 30) {
            this.machineState = GuillotineState.RESETTING;
            this.markDirtyAndSync();
        }
        if (!world.field_9236 && this.sequenceTicks >= 50) {
            if (this.machineSeat.isOccupied()) {
                this.releaseOccupant();
            } else {
                this.machineState = GuillotineState.EMPTY;
                this.sequenceTicks = 0;
                this.previousSequenceTicks = 0;
                this.markDirtyAndSync();
            }
        }
    }

    private void handleImpact(class_1937 world) {
        class_3222 player;
        UUID occupantUuid;
        world.method_8396(null, this.field_11867, class_3417.field_14833, class_3419.field_15245, 0.8f, 0.75f);
        world.method_8396(null, this.field_11867, ModSounds.CROW_EXECUTION, class_3419.field_15251, 4.0f, 0.96f + world.method_8409().method_43057() * 0.08f);
        if (world instanceof class_3218) {
            class_3218 serverWorld = (class_3218)world;
            this.spawnImpactParticles(serverWorld);
        }
        if ((occupantUuid = this.machineSeat.getOccupantUuid()) == null || !(world instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)world;
        class_1297 entity = serverWorld.method_14190(occupantUuid);
        if (!(entity instanceof class_3222) || !(player = (class_3222)entity).method_5805()) {
            this.clearOccupantAfterImpact();
            return;
        }
        if (this.headDropEnabled && !GuillotineBlockEntity.isExternalHeadDropModLoaded()) {
            this.dropPlayerHead(serverWorld, player);
        }
        this.clearOccupantAfterImpact();
        this.executePlayer(serverWorld, player);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executePlayer(class_3218 world, class_3222 player) {
        String banReason;
        MinecraftServer server = world.method_8503();
        boolean showDeathMessages = world.method_8450().method_8355(class_1928.field_19398);
        if (this.punishmentMode == PunishmentMode.CUSTOM_COMMAND) {
            this.scheduleCustomCommands(world, player);
        } else if (this.punishmentMode == PunishmentMode.ADVENTURE_10_MINUTES) {
            GuillotinePunishmentManager.scheduleAdventure(player);
        }
        if (showDeathMessages) {
            ((class_1928.class_4310)world.method_8450().method_20746(class_1928.field_19398)).method_20758(false, server);
        }
        try {
            player.method_5643(world.method_48963().method_51847(), Float.MAX_VALUE);
        }
        finally {
            if (showDeathMessages) {
                ((class_1928.class_4310)world.method_8450().method_20746(class_1928.field_19398)).method_20758(true, server);
            }
        }
        MedievalMachineryConfig config = MedievalMachineryConfig.get();
        this.recordExecution(player.method_7334().getName());
        if (config.broadcastExecutionMessage && this.broadcastExecution) {
            server.method_3760().method_43514((class_2561)class_2561.method_43470((String)config.getExecutionMessage(player.method_7334().getName())), false);
        }
        String playerName = player.method_7334().getName();
        String string = banReason = config.banReason == null || config.banReason.isBlank() ? "Im Namen des K\u00f6nigs hingerichtet" : config.banReason;
        if (this.punishmentMode == PunishmentMode.BAN_24_HOURS) {
            GuillotinePunishmentManager.scheduleTemporaryBan(server, playerName, banReason);
        } else if (this.punishmentMode == PunishmentMode.PERMANENT_BAN || this.banOnExecution) {
            server.method_3734().method_44252(server.method_3739(), "ban " + playerName + " " + banReason);
        }
    }

    private void scheduleCustomCommands(class_3218 world, class_3222 player) {
        if (this.customCommand == null || this.customCommand.isBlank()) {
            MedievalMachinery.LOGGER.warn("Guillotine at {} is set to CUSTOM_COMMAND, but no command is configured.", (Object)this.field_11867);
            return;
        }
        String[] configuredCommands = this.customCommand.split("\\s*;;\\s*");
        ArrayList<String> resolvedCommands = new ArrayList<String>();
        for (String configuredCommand : configuredCommands) {
            String command = configuredCommand.trim();
            while (command.startsWith("/")) {
                command = command.substring(1).trim();
            }
            if (command.isBlank()) continue;
            String playerName = player.method_7334().getName();
            command = command.replace("%player%", playerName).replace("%Player%", playerName).replace("%PLAYER%", playerName).replace("%player_uuid%", player.method_5845()).replace("%world%", world.method_27983().method_29177().toString()).replace("%x%", Integer.toString(this.field_11867.method_10263())).replace("%y%", Integer.toString(this.field_11867.method_10264())).replace("%z%", Integer.toString(this.field_11867.method_10260()));
            resolvedCommands.add(command);
        }
        DeferredExecutionCommands.schedule(player, resolvedCommands);
        MedievalMachinery.LOGGER.info("Scheduled {} guillotine command(s) for {} after respawn.", (Object)resolvedCommands.size(), (Object)player.method_7334().getName());
    }

    private void recordExecution(String playerName) {
        LocalDate today = LocalDate.now();
        if (!today.toString().equals(this.statsDate)) {
            this.statsDate = today.toString();
            this.executionsToday = 0;
        }
        ++this.executionCount;
        ++this.executionsToday;
        this.lastExecutionSummary = playerName + " | " + LocalDateTime.now().format(TIME_FORMAT);
        this.markDirtyAndSync();
    }

    private void refreshDailyCounter() {
        String today = LocalDate.now().toString();
        if (!today.equals(this.statsDate)) {
            this.statsDate = today;
            this.executionsToday = 0;
        }
    }

    private static String sanitizeText(String value, int maxLength) {
        if (value == null) {
            return "";
        }
        String cleaned = value.replace("\n", " ").replace("\r", " ").trim();
        return cleaned.length() <= maxLength ? cleaned : cleaned.substring(0, maxLength);
    }

    private void spawnImpactParticles(class_3218 world) {
        class_2350 facing = (class_2350)this.method_11010().method_11654((class_2769)GuillotineBlock.FACING);
        double impactDistance = this.isRoyalGuillotine() ? 0.68 : 0.15;
        double impactHeight = this.isRoyalGuillotine() ? 1.08 : 0.85;
        double impactSideOffset = this.isRoyalGuillotine() ? 0.56 : 0.0;
        class_2350 right = facing.method_10170();
        class_243 impactPosition = class_243.method_24955((class_2382)this.field_11867).method_1031((double)facing.method_10148() * impactDistance + (double)right.method_10148() * impactSideOffset, impactHeight, (double)facing.method_10165() * impactDistance + (double)right.method_10165() * impactSideOffset);
        world.method_14199((class_2394)new class_2388(class_2398.field_11217, class_2246.field_10161.method_9564()), impactPosition.field_1352, impactPosition.field_1351, impactPosition.field_1350, 12, 0.25, 0.12, 0.25, 0.05);
        world.method_14199((class_2394)class_2398.field_11204, impactPosition.field_1352, impactPosition.field_1351, impactPosition.field_1350, 4, 0.16, 0.08, 0.16, 0.015);
    }

    private void clearOccupantAfterImpact() {
        if (this.machineSeat.release()) {
            this.markDirtyAndSync();
        }
    }

    private static boolean isExternalHeadDropModLoaded() {
        for (String modId : DARKHEADS_MOD_IDS) {
            if (!LoaderHooks.isModLoaded(modId)) continue;
            return true;
        }
        return false;
    }

    private void dropPlayerHead(class_3218 world, class_3222 player) {
        class_1799 headStack = new class_1799((class_1935)class_1802.field_8575);
        headStack.method_7948().method_10582("SkullOwner", player.method_7334().getName());
        headStack.method_7977((class_2561)class_2561.method_43470((String)("Head of " + player.method_7334().getName())));
        class_2350 facing = (class_2350)this.method_11010().method_11654((class_2769)GuillotineBlock.FACING);
        if (this.tryInsertHeadIntoBasket(world, headStack, facing)) {
            return;
        }
        double headDistance = this.isRoyalGuillotine() ? 0.28 : 0.35;
        double headHeight = this.isRoyalGuillotine() ? 1.05 : 0.85;
        double headSideOffset = this.isRoyalGuillotine() ? 0.3125 : 0.0;
        class_2350 right = facing.method_10170();
        class_243 spawnPosition = class_243.method_24955((class_2382)this.field_11867).method_1031((double)facing.method_10148() * headDistance + (double)right.method_10148() * headSideOffset, headHeight, (double)facing.method_10165() * headDistance + (double)right.method_10165() * headSideOffset);
        class_1542 headEntity = new class_1542((class_1937)world, spawnPosition.field_1352, spawnPosition.field_1351, spawnPosition.field_1350, headStack);
        headEntity.method_18800((double)facing.method_10148() * 0.12 + (double)right.method_10148() * 0.06, 0.12, (double)facing.method_10165() * 0.12 + (double)right.method_10165() * 0.06);
        world.method_8649((class_1297)headEntity);
    }

    private boolean tryInsertHeadIntoBasket(class_3218 world, class_1799 headStack, class_2350 facing) {
        class_2350 right = facing.method_10170();
        ArrayList<class_2338> candidates = new ArrayList<class_2338>();
        class_2338 front = this.field_11867.method_10093(facing);
        candidates.add(front);
        candidates.add(front.method_10074());
        if (this.isRoyalGuillotine()) {
            candidates.add(front.method_10093(right));
            candidates.add(front.method_10093(right).method_10074());
            candidates.add(front.method_10093(right.method_10153()));
            candidates.add(front.method_10093(right.method_10153()).method_10074());
        }
        for (class_2338 candidate : candidates) {
            class_2586 class_25862 = world.method_8321(candidate);
            if (!(class_25862 instanceof WickerBasketBlockEntity)) continue;
            WickerBasketBlockEntity basket = (WickerBasketBlockEntity)class_25862;
            class_1799 remaining = headStack.method_7972();
            basket.insert(remaining);
            if (!remaining.method_7960()) continue;
            world.method_8396(null, candidate, class_3417.field_15197, class_3419.field_15245, 0.35f, 0.9f);
            return true;
        }
        return false;
    }

    private void markDirtyAndSync() {
        this.method_5431();
        if (this.field_11863 != null && !this.field_11863.field_9236) {
            this.field_11863.method_8413(this.field_11867, this.method_11010(), this.method_11010(), 3);
        }
    }

    protected void method_11007(class_2487 nbt) {
        super.method_11007(nbt);
        nbt.method_10582(STATE_NBT_KEY, this.machineState.asString());
        nbt.method_10556(POWERED_NBT_KEY, this.redstonePowered);
        nbt.method_10556(LOCKED_NBT_KEY, this.machineSeat.isLocked());
        nbt.method_10569(SEQUENCE_TICKS_NBT_KEY, this.sequenceTicks);
        nbt.method_10556(BAN_ON_EXECUTION_NBT_KEY, this.banOnExecution);
        nbt.method_10582(PUNISHMENT_MODE_NBT_KEY, this.punishmentMode.name());
        nbt.method_10556(BROADCAST_NBT_KEY, this.broadcastExecution);
        nbt.method_10582(DISPLAY_NAME_NBT_KEY, this.getDisplayName());
        nbt.method_10582(LAST_EXECUTION_NBT_KEY, this.getLastExecutionSummary());
        nbt.method_10582(CUSTOM_COMMAND_NBT_KEY, this.getCustomCommand());
        nbt.method_10569(EXECUTION_COUNT_NBT_KEY, this.executionCount);
        nbt.method_10569(EXECUTIONS_TODAY_NBT_KEY, this.executionsToday);
        nbt.method_10582(STATS_DATE_NBT_KEY, this.statsDate == null ? "" : this.statsDate);
        nbt.method_10556(HEAD_DROP_NBT_KEY, this.headDropEnabled);
        nbt.method_10556(EXECUTIONER_ENABLED_NBT_KEY, this.executionerEnabled);
        UUID occupantUuid = this.machineSeat.getOccupantUuid();
        if (occupantUuid != null) {
            nbt.method_25927(OCCUPANT_NBT_KEY, occupantUuid);
        }
    }

    public void method_11014(class_2487 nbt) {
        super.method_11014(nbt);
        this.machineState = nbt.method_10573(STATE_NBT_KEY, 8) ? GuillotineState.fromString(nbt.method_10558(STATE_NBT_KEY)) : GuillotineState.EMPTY;
        this.redstonePowered = nbt.method_10577(POWERED_NBT_KEY);
        this.previousSequenceTicks = this.sequenceTicks = Math.max(0, nbt.method_10550(SEQUENCE_TICKS_NBT_KEY));
        this.banOnExecution = nbt.method_10577(BAN_ON_EXECUTION_NBT_KEY);
        this.punishmentMode = nbt.method_10573(PUNISHMENT_MODE_NBT_KEY, 8) ? PunishmentMode.fromString(nbt.method_10558(PUNISHMENT_MODE_NBT_KEY)) : (this.banOnExecution ? PunishmentMode.PERMANENT_BAN : PunishmentMode.NONE);
        this.broadcastExecution = !nbt.method_10545(BROADCAST_NBT_KEY) || nbt.method_10577(BROADCAST_NBT_KEY);
        this.displayName = nbt.method_10558(DISPLAY_NAME_NBT_KEY);
        this.lastExecutionSummary = nbt.method_10558(LAST_EXECUTION_NBT_KEY);
        this.customCommand = nbt.method_10558(CUSTOM_COMMAND_NBT_KEY);
        this.executionCount = Math.max(0, nbt.method_10550(EXECUTION_COUNT_NBT_KEY));
        this.executionsToday = Math.max(0, nbt.method_10550(EXECUTIONS_TODAY_NBT_KEY));
        this.statsDate = nbt.method_10558(STATS_DATE_NBT_KEY);
        this.headDropEnabled = !nbt.method_10545(HEAD_DROP_NBT_KEY) || nbt.method_10577(HEAD_DROP_NBT_KEY);
        this.executionerEnabled = !nbt.method_10545(EXECUTIONER_ENABLED_NBT_KEY) || nbt.method_10577(EXECUTIONER_ENABLED_NBT_KEY);
        this.refreshDailyCounter();
        UUID occupantUuid = nbt.method_25928(OCCUPANT_NBT_KEY) ? nbt.method_25926(OCCUPANT_NBT_KEY) : null;
        this.machineSeat.restore(occupantUuid, nbt.method_10577(LOCKED_NBT_KEY));
        if (occupantUuid == null && (this.machineState == GuillotineState.OCCUPIED || this.machineState == GuillotineState.LOCKED)) {
            this.machineState = GuillotineState.EMPTY;
            this.sequenceTicks = 0;
            this.previousSequenceTicks = 0;
        }
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
}

