/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1309
 *  net.minecraft.class_1646
 *  net.minecraft.class_1657
 *  net.minecraft.class_1750
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2237
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_2383
 *  net.minecraft.class_2464
 *  net.minecraft.class_247
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_259
 *  net.minecraft.class_2591
 *  net.minecraft.class_265
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2753
 *  net.minecraft.class_2769
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3414
 *  net.minecraft.class_3417
 *  net.minecraft.class_3419
 *  net.minecraft.class_3726
 *  net.minecraft.class_3852
 *  net.minecraft.class_3965
 *  net.minecraft.class_4050
 *  net.minecraft.class_4140
 *  net.minecraft.class_4208
 *  net.minecraft.class_4970$class_2251
 *  net.minecraft.class_5558
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.machine.guillotine;

import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockAccess;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotinePartBlock;
import com.medievalmachinery.mod.platform.LoaderHooks;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.registry.ModBlocks;
import java.util.Optional;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1309;
import net.minecraft.class_1646;
import net.minecraft.class_1657;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2237;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_2383;
import net.minecraft.class_2464;
import net.minecraft.class_247;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_259;
import net.minecraft.class_2591;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2753;
import net.minecraft.class_2769;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3726;
import net.minecraft.class_3852;
import net.minecraft.class_3965;
import net.minecraft.class_4050;
import net.minecraft.class_4140;
import net.minecraft.class_4208;
import net.minecraft.class_4970;
import net.minecraft.class_5558;
import org.jetbrains.annotations.Nullable;

public final class GuillotineBlock
extends class_2237 {
    public static final class_2753 FACING = class_2383.field_11177;
    private static final class_265 NORTH_SHAPE = class_259.method_17786((class_265)GuillotineBlock.method_9541((double)1.0, (double)0.0, (double)1.0, (double)15.0, (double)2.0, (double)15.0), (class_265[])new class_265[]{GuillotineBlock.method_9541((double)2.0, (double)2.0, (double)5.0, (double)4.0, (double)32.0, (double)9.0), GuillotineBlock.method_9541((double)12.0, (double)2.0, (double)5.0, (double)14.0, (double)32.0, (double)9.0), GuillotineBlock.method_9541((double)0.75, (double)30.0, (double)4.0, (double)15.25, (double)32.0, (double)10.0), GuillotineBlock.method_9541((double)2.0, (double)9.5, (double)9.0, (double)14.0, (double)12.0, (double)32.0), GuillotineBlock.method_9541((double)2.0, (double)0.0, (double)9.0, (double)14.0, (double)9.5, (double)32.0), GuillotineBlock.method_9541((double)3.0, (double)0.0, (double)28.0, (double)4.0, (double)10.0, (double)29.0), GuillotineBlock.method_9541((double)12.0, (double)0.0, (double)28.0, (double)13.0, (double)10.0, (double)29.0), GuillotineBlock.method_9541((double)3.0, (double)4.0, (double)8.0, (double)4.0, (double)5.0, (double)29.0), GuillotineBlock.method_9541((double)12.0, (double)4.0, (double)8.0, (double)13.0, (double)5.0, (double)29.0)});
    private static final class_265 EAST_SHAPE = GuillotineBlock.rotateClockwise(NORTH_SHAPE);
    private static final class_265 SOUTH_SHAPE = GuillotineBlock.rotateClockwise(EAST_SHAPE);
    private static final class_265 WEST_SHAPE = GuillotineBlock.rotateClockwise(SOUTH_SHAPE);
    private static final class_265 ROYAL_NORTH_SHAPE = class_259.method_17786((class_265)GuillotineBlock.method_9541((double)3.0, (double)0.0, (double)-14.0, (double)29.0, (double)3.0, (double)8.0), (class_265[])new class_265[]{GuillotineBlock.method_9541((double)8.0, (double)10.0, (double)0.0, (double)24.0, (double)13.0, (double)27.0), GuillotineBlock.method_9541((double)8.0, (double)0.0, (double)0.0, (double)24.0, (double)10.0, (double)27.0), GuillotineBlock.method_9541((double)5.0, (double)3.0, (double)-6.0, (double)9.0, (double)45.0, (double)0.0), GuillotineBlock.method_9541((double)23.0, (double)3.0, (double)-6.0, (double)27.0, (double)45.0, (double)0.0), GuillotineBlock.method_9541((double)3.0, (double)42.0, (double)-8.0, (double)29.0, (double)49.0, (double)2.0), GuillotineBlock.method_9541((double)8.0, (double)13.0, (double)25.0, (double)24.0, (double)15.0, (double)27.0), GuillotineBlock.method_9541((double)10.0, (double)0.0, (double)24.0, (double)12.0, (double)10.0, (double)26.0), GuillotineBlock.method_9541((double)20.0, (double)0.0, (double)24.0, (double)22.0, (double)10.0, (double)26.0)});
    private static final class_265 ROYAL_EAST_SHAPE = GuillotineBlock.rotateClockwise(ROYAL_NORTH_SHAPE);
    private static final class_265 ROYAL_SOUTH_SHAPE = GuillotineBlock.rotateClockwise(ROYAL_EAST_SHAPE);
    private static final class_265 ROYAL_WEST_SHAPE = GuillotineBlock.rotateClockwise(ROYAL_SOUTH_SHAPE);

    public GuillotineBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)this.method_9595().method_11664()).method_11657((class_2769)FACING, (Comparable)class_2350.field_11043));
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        builder.method_11667(new class_2769[]{FACING});
    }

    @Nullable
    public class_2680 method_9605(class_1750 context) {
        class_2680 placementState = (class_2680)this.method_9564().method_11657((class_2769)FACING, (Comparable)context.method_8042().method_10153());
        if (!placementState.method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
            return placementState;
        }
        class_2350 facing = (class_2350)placementState.method_11654((class_2769)FACING);
        class_2338 controllerPos = context.method_8037();
        boolean[] blocked = new boolean[]{false};
        GuillotineBlock.forEachRoyalPart(facing, (x, y, z) -> {
            class_2338 partPos = controllerPos.method_10081((class_2382)GuillotineBlock.rotateRoyalOffset(facing, x, y, z));
            if (!context.method_8045().method_8320(partPos).method_45474()) {
                blocked[0] = true;
            }
        });
        return blocked[0] ? null : placementState;
    }

    public void method_9567(class_1937 world, class_2338 pos, class_2680 state, @Nullable class_1309 placer, class_1799 itemStack) {
        super.method_9567(world, pos, state, placer, itemStack);
        if (world.field_9236 || !state.method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
            return;
        }
        class_2350 facing = (class_2350)state.method_11654((class_2769)FACING);
        GuillotineBlock.forEachRoyalPart(facing, (x, y, z) -> {
            class_2338 partPos = pos.method_10081((class_2382)GuillotineBlock.rotateRoyalOffset(facing, x, y, z));
            class_2680 partState = (class_2680)((class_2680)((class_2680)((class_2680)ModBlocks.GUILLOTINE_PART.method_9564().method_11657((class_2769)GuillotinePartBlock.FACING, (Comparable)facing)).method_11657((class_2769)GuillotinePartBlock.OFFSET_X, (Comparable)Integer.valueOf(x))).method_11657((class_2769)GuillotinePartBlock.OFFSET_Y, (Comparable)Integer.valueOf(y))).method_11657((class_2769)GuillotinePartBlock.OFFSET_Z, (Comparable)Integer.valueOf(GuillotinePartBlock.encodeOffsetZ(z)));
            world.method_8652(partPos, partState, 3);
        });
    }

    public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        if (state.method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
            return GuillotineBlock.getRoyalPartShape((class_2350)state.method_11654((class_2769)FACING), 0, 0, 0);
        }
        return GuillotineBlock.getShapeForFacing(state, (class_2350)state.method_11654((class_2769)FACING));
    }

    public class_265 method_9549(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        if (state.method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
            return GuillotineBlock.getRoyalPartShape((class_2350)state.method_11654((class_2769)FACING), 0, 0, 0);
        }
        return GuillotineBlock.getShapeForFacing(state, (class_2350)state.method_11654((class_2769)FACING));
    }

    private static class_265 getShapeForFacing(class_2680 state, class_2350 direction) {
        if (state.method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
            return switch (direction) {
                case class_2350.field_11034 -> ROYAL_EAST_SHAPE;
                case class_2350.field_11035 -> ROYAL_SOUTH_SHAPE;
                case class_2350.field_11039 -> ROYAL_WEST_SHAPE;
                default -> ROYAL_NORTH_SHAPE;
            };
        }
        return switch (direction) {
            case class_2350.field_11034 -> EAST_SHAPE;
            case class_2350.field_11035 -> SOUTH_SHAPE;
            case class_2350.field_11039 -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    private static class_265 rotateClockwise(class_265 source) {
        class_265[] result = new class_265[]{class_259.method_1073()};
        source.method_1089((minX, minY, minZ, maxX, maxY, maxZ) -> {
            result[0] = class_259.method_1084((class_265)result[0], (class_265)class_259.method_1081((double)(1.0 - maxZ), (double)minY, (double)minX, (double)(1.0 - minZ), (double)maxY, (double)maxX));
        });
        return result[0];
    }

    public static class_2338 rotateRoyalOffset(class_2350 facing, int canonicalX, int canonicalY, int canonicalZ) {
        return switch (facing) {
            case class_2350.field_11034 -> new class_2338(-canonicalZ, canonicalY, canonicalX);
            case class_2350.field_11035 -> new class_2338(-canonicalX, canonicalY, -canonicalZ);
            case class_2350.field_11039 -> new class_2338(canonicalZ, canonicalY, -canonicalX);
            default -> new class_2338(canonicalX, canonicalY, canonicalZ);
        };
    }

    public static class_265 getRoyalPartShape(class_2350 facing, int canonicalX, int canonicalY, int canonicalZ) {
        class_265 fullShape = switch (facing) {
            case class_2350.field_11034 -> ROYAL_EAST_SHAPE;
            case class_2350.field_11035 -> ROYAL_SOUTH_SHAPE;
            case class_2350.field_11039 -> ROYAL_WEST_SHAPE;
            default -> ROYAL_NORTH_SHAPE;
        };
        class_2338 worldOffset = GuillotineBlock.rotateRoyalOffset(facing, canonicalX, canonicalY, canonicalZ);
        class_265 cell = class_259.method_1081((double)worldOffset.method_10263(), (double)worldOffset.method_10264(), (double)worldOffset.method_10260(), (double)((double)worldOffset.method_10263() + 1.0), (double)((double)worldOffset.method_10264() + 1.0), (double)((double)worldOffset.method_10260() + 1.0));
        return class_259.method_1072((class_265)fullShape, (class_265)cell, (class_247)class_247.field_16896).method_1096((double)(-worldOffset.method_10263()), (double)(-worldOffset.method_10264()), (double)(-worldOffset.method_10260()));
    }

    private static boolean isRoyalPartCellUsed(class_2350 facing, int canonicalX, int canonicalY, int canonicalZ) {
        return !GuillotineBlock.getRoyalPartShape(facing, canonicalX, canonicalY, canonicalZ).method_1110();
    }

    private static void forEachRoyalPart(class_2350 facing, RoyalPartConsumer consumer) {
        for (int x = 0; x <= 1; ++x) {
            for (int y = 0; y <= 3; ++y) {
                for (int z = -1; z <= 1; ++z) {
                    if (x == 0 && y == 0 && z == 0 || !GuillotineBlock.isRoyalPartCellUsed(facing, x, y, z)) continue;
                    consumer.accept(x, y, z);
                }
            }
        }
    }

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11455;
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        return LoaderHooks.createGuillotine(pos, state);
    }

    @Nullable
    public <T extends class_2586> class_5558<T> method_31645(class_1937 world, class_2680 state, class_2591<T> type) {
        if (type != RegisteredContent.guillotine()) {
            return null;
        }
        return LoaderHooks.guillotineTicker(type);
    }

    public class_1269 method_9534(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_1268 hand, class_3965 hit) {
        class_2586 blockEntity = world.method_8321(pos);
        if (!(blockEntity instanceof GuillotineBlockAccess)) {
            return class_1269.field_5811;
        }
        GuillotineBlockAccess guillotine = (GuillotineBlockAccess)blockEntity;
        return GuillotineBlock.interactWithGuillotine(world, pos, player, guillotine);
    }

    public static class_1269 interactWithGuillotine(class_1937 world, class_2338 pos, class_1657 player, GuillotineBlockAccess guillotine) {
        if (world.field_9236) {
            return class_1269.field_5812;
        }
        boolean crouching = player.method_5715() || player.method_18376() == class_4050.field_18081;
        boolean holdingWoodenSword = player.method_6047().method_31574(class_1802.field_8091);
        if (crouching) {
            if (holdingWoodenSword) {
                class_3222 serverPlayer;
                if (!(player instanceof class_3222) || !(serverPlayer = (class_3222)player).method_5687(2)) {
                    player.method_7353((class_2561)class_2561.method_43470((String)"Nur Operatoren d\u00fcrfen die Guillotine konfigurieren."), true);
                    return class_1269.field_21466;
                }
                boolean banEnabled = guillotine.toggleBanOnExecution();
                player.method_7353((class_2561)class_2561.method_43470((String)("Guillotine-Bann: " + (banEnabled ? "AN" : "AUS"))), false);
                world.method_8396(null, pos, (class_3414)class_3417.field_15015.comp_349(), class_3419.field_15245, 0.7f, banEnabled ? 1.2f : 0.8f);
                return class_1269.field_21466;
            }
            if (guillotine.isSequenceRunning()) {
                player.method_7353((class_2561)class_2561.method_43470((String)"Die Guillotine l\u00e4uft bereits."), true);
                return class_1269.field_21466;
            }
            guillotine.startSequence();
            world.method_8396(null, pos, class_3417.field_14962, class_3419.field_15245, 0.9f, 0.6f);
            return class_1269.field_21466;
        }
        if (guillotine.isOccupiedBy(player.method_5667())) {
            if (guillotine.isLocked()) {
                player.method_7353((class_2561)class_2561.method_43470((String)"The guillotine is locked."), true);
                return class_1269.field_21466;
            }
            guillotine.releaseOccupant();
            player.method_7353((class_2561)class_2561.method_43470((String)"You leave the guillotine."), true);
            return class_1269.field_21466;
        }
        if (!guillotine.isEmpty()) {
            player.method_7353((class_2561)class_2561.method_43470((String)"The guillotine is already occupied."), true);
            return class_1269.field_21466;
        }
        if (guillotine.occupy(player.method_5667())) {
            player.method_7353((class_2561)class_2561.method_43470((String)"You enter the guillotine. Shift-right-click to trigger it."), true);
            world.method_8396(null, pos, class_3417.field_15080, class_3419.field_15245, 0.8f, 0.9f);
        }
        return class_1269.field_21466;
    }

    public void method_9612(class_2680 state, class_1937 world, class_2338 pos, class_2248 sourceBlock, class_2338 sourcePos, boolean notify) {
        super.method_9612(state, world, pos, sourceBlock, sourcePos, notify);
        if (world.field_9236) {
            return;
        }
        if (state.method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
            GuillotineBlock.updateRoyalRedstoneState(world, pos);
            return;
        }
        GuillotineBlock.updateControllerRedstoneState(world, pos, world.method_49803(pos));
    }

    public static void updateRoyalRedstoneState(class_1937 world, class_2338 controllerPos) {
        if (world.field_9236) {
            return;
        }
        class_2680 controllerState = world.method_8320(controllerPos);
        if (!controllerState.method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
            return;
        }
        boolean[] powered = new boolean[]{world.method_49803(controllerPos)};
        class_2350 facing = (class_2350)controllerState.method_11654((class_2769)FACING);
        GuillotineBlock.forEachRoyalPart(facing, (x, y, z) -> {
            if (powered[0]) {
                return;
            }
            class_2338 partPos = controllerPos.method_10081((class_2382)GuillotineBlock.rotateRoyalOffset(facing, x, y, z));
            class_2680 partState = world.method_8320(partPos);
            if (partState.method_27852(ModBlocks.GUILLOTINE_PART) && world.method_49803(partPos)) {
                powered[0] = true;
            }
        });
        GuillotineBlock.updateControllerRedstoneState(world, controllerPos, powered[0]);
    }

    private static void updateControllerRedstoneState(class_1937 world, class_2338 controllerPos, boolean powered) {
        class_2586 blockEntity = world.method_8321(controllerPos);
        if (!(blockEntity instanceof GuillotineBlockAccess)) {
            return;
        }
        GuillotineBlockAccess guillotine = (GuillotineBlockAccess)blockEntity;
        boolean newlyPowered = guillotine.updateRedstonePower(powered);
        if (!newlyPowered) {
            return;
        }
        guillotine.startSequence();
        world.method_8396(null, controllerPos, class_3417.field_15131, class_3419.field_15245, 1.0f, 0.8f);
    }

    private static void clearExecutionerJobSite(class_3218 world, class_2338 pos) {
        world.method_19494().method_19112(pos);
        class_238 searchBox = new class_238(pos).method_1014(48.0);
        for (class_1646 villager2 : world.method_8390(class_1646.class, searchBox, villager -> villager.method_5805())) {
            boolean matched = GuillotineBlock.forgetIfMatches(villager2, (class_4140<class_4208>)class_4140.field_18439, world, pos);
            if (!(matched |= GuillotineBlock.forgetIfMatches(villager2, (class_4140<class_4208>)class_4140.field_25160, world, pos)) || villager2.method_7231().method_16924() != RegisteredContent.executionerProfession() || villager2.method_19269() != 0) continue;
            villager2.method_7195(villager2.method_7231().method_16921(class_3852.field_17051));
        }
    }

    private static boolean forgetIfMatches(class_1646 villager, class_4140<class_4208> memoryType, class_3218 world, class_2338 pos) {
        Optional memory = villager.method_18868().method_46873(memoryType);
        if (memory.isEmpty()) {
            return false;
        }
        class_4208 globalPos = (class_4208)memory.get();
        if (!globalPos.method_19442().equals(world.method_27983()) || !globalPos.method_19446().equals((Object)pos)) {
            return false;
        }
        villager.method_18868().method_18875(memoryType);
        return true;
    }

    public void method_9536(class_2680 state, class_1937 world, class_2338 pos, class_2680 newState, boolean moved) {
        if (!state.method_27852(newState.method_26204())) {
            class_2586 blockEntity;
            if (!world.field_9236 && (state.method_27852(ModBlocks.GUILLOTINE) || state.method_27852(ModBlocks.ROYAL_GUILLOTINE)) && world instanceof class_3218) {
                class_3218 serverWorld = (class_3218)world;
                GuillotineBlock.clearExecutionerJobSite(serverWorld, pos);
            }
            if (!world.field_9236 && state.method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
                class_2350 facing = (class_2350)state.method_11654((class_2769)FACING);
                GuillotineBlock.forEachRoyalPart(facing, (x, y, z) -> {
                    class_2338 partPos = pos.method_10081((class_2382)GuillotineBlock.rotateRoyalOffset(facing, x, y, z));
                    if (world.method_8320(partPos).method_27852(ModBlocks.GUILLOTINE_PART)) {
                        world.method_8650(partPos, false);
                    }
                });
            }
            if ((blockEntity = world.method_8321(pos)) instanceof GuillotineBlockAccess) {
                GuillotineBlockAccess guillotine = (GuillotineBlockAccess)blockEntity;
                guillotine.releaseOccupant();
            }
        }
        super.method_9536(state, world, pos, newState, moved);
    }

    @FunctionalInterface
    private static interface RoyalPartConsumer {
        public void accept(int var1, int var2, int var3);
    }
}

