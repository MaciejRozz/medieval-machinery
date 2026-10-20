/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_10
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1750
 *  net.minecraft.class_1799
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2237
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2464
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_259
 *  net.minecraft.class_265
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2741
 *  net.minecraft.class_2753
 *  net.minecraft.class_2754
 *  net.minecraft.class_2769
 *  net.minecraft.class_3222
 *  net.minecraft.class_3726
 *  net.minecraft.class_3965
 *  net.minecraft.class_4970$class_2251
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.garrison;

import com.medievalmachinery.mod.block.garrison.WatchpostBlockAccess;
import com.medievalmachinery.mod.block.garrison.WatchpostPart;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.platform.LoaderHooks;
import net.minecraft.class_10;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2237;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2464;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2741;
import net.minecraft.class_2753;
import net.minecraft.class_2754;
import net.minecraft.class_2769;
import net.minecraft.class_3222;
import net.minecraft.class_3726;
import net.minecraft.class_3965;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public final class WatchpostBlock
extends class_2237 {
    public static final class_2753 FACING = class_2741.field_12481;
    public static final class_2754<WatchpostPart> PART = class_2754.method_11850((String)"part", WatchpostPart.class);
    public static final class_2754<OrderStyle> ORDER = class_2754.method_11850((String)"order", OrderStyle.class);
    private static final class_265 MAIN_NORTH = class_259.method_17786((class_265)class_2248.method_9541((double)0.5, (double)10.0, (double)0.5, (double)16.0, (double)13.9, (double)15.5), (class_265[])new class_265[]{class_2248.method_9541((double)1.0, (double)0.0, (double)1.0, (double)4.0, (double)10.0, (double)4.0), class_2248.method_9541((double)1.0, (double)0.0, (double)12.0, (double)4.0, (double)10.0, (double)15.0)});
    private static final class_265 EXT_NORTH = class_259.method_17786((class_265)class_2248.method_9541((double)0.0, (double)10.0, (double)0.5, (double)15.5, (double)13.9, (double)15.5), (class_265[])new class_265[]{class_2248.method_9541((double)12.0, (double)0.0, (double)1.0, (double)15.0, (double)10.0, (double)4.0), class_2248.method_9541((double)12.0, (double)0.0, (double)12.0, (double)15.0, (double)10.0, (double)15.0)});
    private static final class_265 MAIN_EAST = class_259.method_17786((class_265)class_2248.method_9541((double)0.5, (double)10.0, (double)0.5, (double)15.5, (double)13.9, (double)16.0), (class_265[])new class_265[]{class_2248.method_9541((double)1.0, (double)0.0, (double)1.0, (double)4.0, (double)10.0, (double)4.0), class_2248.method_9541((double)12.0, (double)0.0, (double)1.0, (double)15.0, (double)10.0, (double)4.0)});
    private static final class_265 EXT_EAST = class_259.method_17786((class_265)class_2248.method_9541((double)0.5, (double)10.0, (double)0.0, (double)15.5, (double)13.9, (double)15.5), (class_265[])new class_265[]{class_2248.method_9541((double)1.0, (double)0.0, (double)12.0, (double)4.0, (double)10.0, (double)15.0), class_2248.method_9541((double)12.0, (double)0.0, (double)12.0, (double)15.0, (double)10.0, (double)15.0)});
    private static final class_265 MAIN_SOUTH = class_259.method_17786((class_265)class_2248.method_9541((double)0.0, (double)10.0, (double)0.5, (double)15.5, (double)13.9, (double)15.5), (class_265[])new class_265[]{class_2248.method_9541((double)12.0, (double)0.0, (double)1.0, (double)15.0, (double)10.0, (double)4.0), class_2248.method_9541((double)12.0, (double)0.0, (double)12.0, (double)15.0, (double)10.0, (double)15.0)});
    private static final class_265 EXT_SOUTH = class_259.method_17786((class_265)class_2248.method_9541((double)0.5, (double)10.0, (double)0.5, (double)16.0, (double)13.9, (double)15.5), (class_265[])new class_265[]{class_2248.method_9541((double)1.0, (double)0.0, (double)1.0, (double)4.0, (double)10.0, (double)4.0), class_2248.method_9541((double)1.0, (double)0.0, (double)12.0, (double)4.0, (double)10.0, (double)15.0)});
    private static final class_265 MAIN_WEST = class_259.method_17786((class_265)class_2248.method_9541((double)0.5, (double)10.0, (double)0.0, (double)15.5, (double)13.9, (double)15.5), (class_265[])new class_265[]{class_2248.method_9541((double)1.0, (double)0.0, (double)12.0, (double)4.0, (double)10.0, (double)15.0), class_2248.method_9541((double)12.0, (double)0.0, (double)12.0, (double)15.0, (double)10.0, (double)15.0)});
    private static final class_265 EXT_WEST = class_259.method_17786((class_265)class_2248.method_9541((double)0.5, (double)10.0, (double)0.5, (double)15.5, (double)13.9, (double)16.0), (class_265[])new class_265[]{class_2248.method_9541((double)1.0, (double)0.0, (double)1.0, (double)4.0, (double)10.0, (double)4.0), class_2248.method_9541((double)12.0, (double)0.0, (double)1.0, (double)15.0, (double)10.0, (double)4.0)});

    private static class_265 shapeFor(class_2680 state) {
        boolean main = state.method_11654(PART) == WatchpostPart.MAIN;
        return switch ((class_2350)state.method_11654((class_2769)FACING)) {
            case class_2350.field_11034 -> {
                if (main) {
                    yield MAIN_EAST;
                }
                yield EXT_EAST;
            }
            case class_2350.field_11035 -> {
                if (main) {
                    yield MAIN_SOUTH;
                }
                yield EXT_SOUTH;
            }
            case class_2350.field_11039 -> {
                if (main) {
                    yield MAIN_WEST;
                }
                yield EXT_WEST;
            }
            default -> main ? MAIN_NORTH : EXT_NORTH;
        };
    }

    public WatchpostBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)((class_2680)((class_2680)this.method_9595().method_11664()).method_11657((class_2769)FACING, (Comparable)class_2350.field_11043)).method_11657(PART, (Comparable)((Object)WatchpostPart.MAIN))).method_11657(ORDER, (Comparable)((Object)OrderStyle.NONE)));
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        builder.method_11667(new class_2769[]{FACING, PART, ORDER});
    }

    @Nullable
    public class_2680 method_9605(class_1750 ctx) {
        class_2350 facing = ctx.method_8042().method_10153();
        class_2680 state = (class_2680)((class_2680)((class_2680)this.method_9564().method_11657((class_2769)FACING, (Comparable)facing)).method_11657(PART, (Comparable)((Object)WatchpostPart.MAIN))).method_11657(ORDER, (Comparable)((Object)OrderStyle.NONE));
        class_2338 extensionPos = ctx.method_8037().method_10093(WatchpostBlock.getExtensionDirection(facing));
        if (!ctx.method_8045().method_8320(extensionPos).method_26166(ctx)) {
            return null;
        }
        return state;
    }

    public void method_9567(class_1937 world, class_2338 pos, class_2680 state, @Nullable class_1309 placer, class_1799 itemStack) {
        super.method_9567(world, pos, state, placer, itemStack);
        if (state.method_11654(PART) != WatchpostPart.MAIN) {
            return;
        }
        class_2338 extensionPos = pos.method_10093(WatchpostBlock.getExtensionDirection((class_2350)state.method_11654((class_2769)FACING)));
        world.method_8652(extensionPos, (class_2680)state.method_11657(PART, (Comparable)((Object)WatchpostPart.EXTENSION)), 3);
        if (placer instanceof class_3222) {
            class_3222 serverPlayer = (class_3222)placer;
            class_2586 blockEntity = world.method_8321(pos);
            if (blockEntity instanceof WatchpostBlockAccess) {
                WatchpostBlockAccess watchpost = (WatchpostBlockAccess)blockEntity;
                watchpost.restoreRelocation(serverPlayer, itemStack);
            }
        }
    }

    public static class_2350 getExtensionDirection(class_2350 facing) {
        return facing.method_10170();
    }

    public static class_2338 getMainPos(class_2680 state, class_2338 pos) {
        if (state.method_11654(PART) == WatchpostPart.MAIN) {
            return pos;
        }
        return pos.method_10093(WatchpostBlock.getExtensionDirection((class_2350)state.method_11654((class_2769)FACING)).method_10153());
    }

    public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return WatchpostBlock.shapeFor(state);
    }

    public class_265 method_9549(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return WatchpostBlock.shapeFor(state);
    }

    public boolean method_9516(class_2680 state, class_1922 world, class_2338 pos, class_10 type) {
        return type != class_10.field_50 && super.method_9516(state, world, pos, type);
    }

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11458;
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        if (state.method_11654(PART) != WatchpostPart.MAIN) {
            return null;
        }
        return LoaderHooks.createWatchpost(pos, state);
    }

    public class_1269 method_9534(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_1268 hand, class_3965 hit) {
        WatchpostBlockAccess watchpost;
        class_2586 be;
        block9: {
            block8: {
                if (world.field_9236) {
                    return class_1269.field_5812;
                }
                class_2338 mainPos = WatchpostBlock.getMainPos(state, pos);
                be = world.method_8321(mainPos);
                if (!(be instanceof WatchpostBlockAccess)) break block8;
                watchpost = (WatchpostBlockAccess)be;
                if (player instanceof class_3222) break block9;
            }
            return class_1269.field_5811;
        }
        class_3222 serverPlayer = (class_3222)player;
        if (KingdomManager.get(serverPlayer.method_51469()).findByMember(serverPlayer.method_5667()) == null) {
            LoaderHooks.openKingdomOnboarding(serverPlayer, true);
            return class_1269.field_21466;
        }
        watchpost.ensureAssigned(serverPlayer);
        if (watchpost.canPlayerAdminister((class_1657)serverPlayer)) {
            LoaderHooks.openWatchpost(serverPlayer, be, false);
            return class_1269.field_21466;
        }
        if (watchpost.hasGeneralProtection()) {
            serverPlayer.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.watchpost.general_protected"), true);
            return class_1269.field_21466;
        }
        if (watchpost.canOpenCapture(serverPlayer)) {
            LoaderHooks.openWatchpost(serverPlayer, be, true);
            return class_1269.field_21466;
        }
        serverPlayer.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.watchpost.no_admin_access"), true);
        return class_1269.field_21466;
    }

    public void method_9576(class_1937 world, class_2338 pos, class_2680 state, class_1657 player) {
        class_2338 otherPos;
        class_2680 other;
        class_2586 mainEntity;
        class_2338 mainPos = WatchpostBlock.getMainPos(state, pos);
        if (!world.field_9236 && (mainEntity = world.method_8321(mainPos)) instanceof WatchpostBlockAccess) {
            WatchpostBlockAccess watchpost = (WatchpostBlockAccess)mainEntity;
            watchpost.removeCastleAssignment();
        }
        if ((other = world.method_8320(otherPos = state.method_11654(PART) == WatchpostPart.MAIN ? pos.method_10093(WatchpostBlock.getExtensionDirection((class_2350)state.method_11654((class_2769)FACING))) : WatchpostBlock.getMainPos(state, pos))).method_27852((class_2248)this)) {
            world.method_8651(otherPos, false, (class_1297)player);
        }
        super.method_9576(world, pos, state, player);
    }
}

