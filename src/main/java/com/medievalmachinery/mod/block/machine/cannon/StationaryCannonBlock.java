/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1264
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1309
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
 *  net.minecraft.class_2383
 *  net.minecraft.class_2464
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_259
 *  net.minecraft.class_2591
 *  net.minecraft.class_265
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2753
 *  net.minecraft.class_2769
 *  net.minecraft.class_3222
 *  net.minecraft.class_3417
 *  net.minecraft.class_3419
 *  net.minecraft.class_3726
 *  net.minecraft.class_3908
 *  net.minecraft.class_3965
 *  net.minecraft.class_4970$class_2251
 *  net.minecraft.class_5558
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.machine.cannon;

import com.medievalmachinery.mod.blockentity.machine.cannon.LegacyStationaryCannonAccess;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1263;
import net.minecraft.class_1264;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1309;
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
import net.minecraft.class_2383;
import net.minecraft.class_2464;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_259;
import net.minecraft.class_2591;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2753;
import net.minecraft.class_2769;
import net.minecraft.class_3222;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3726;
import net.minecraft.class_3908;
import net.minecraft.class_3965;
import net.minecraft.class_4970;
import net.minecraft.class_5558;
import org.jetbrains.annotations.Nullable;

public final class StationaryCannonBlock
extends class_2237 {
    public static final class_2753 FACING = class_2383.field_11177;
    private static final class_265 BASE_COLLISION_SHAPE = class_259.method_1084((class_265)StationaryCannonBlock.method_9541((double)-2.0, (double)0.0, (double)-2.0, (double)18.0, (double)9.5, (double)18.0), (class_265)StationaryCannonBlock.method_9541((double)1.0, (double)8.0, (double)1.0, (double)15.0, (double)14.0, (double)15.0)).method_1097();

    private static class_265 createAimedOutline(float yawDegrees) {
        return BASE_COLLISION_SHAPE;
    }

    public StationaryCannonBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)this.method_9595().method_11664()).method_11657((class_2769)FACING, (Comparable)class_2350.field_11043));
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        builder.method_11667(new class_2769[]{FACING});
    }

    @Nullable
    public class_2680 method_9605(class_1750 context) {
        return (class_2680)this.method_9564().method_11657((class_2769)FACING, (Comparable)context.method_8042());
    }

    public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        float yaw = ((class_2350)state.method_11654((class_2769)FACING)).method_10144();
        class_2586 blockEntity = world.method_8321(pos);
        if (blockEntity instanceof LegacyStationaryCannonAccess) {
            LegacyStationaryCannonAccess cannon = (LegacyStationaryCannonAccess)blockEntity;
            yaw += cannon.getShapeYawOffset();
        }
        return StationaryCannonBlock.createAimedOutline(yaw);
    }

    public class_265 method_9549(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return BASE_COLLISION_SHAPE;
    }

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11458;
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        return RegisteredContent.createStationaryCannonBlock(pos, state);
    }

    @Nullable
    public <T extends class_2586> class_5558<T> method_31645(class_1937 world, class_2680 state, class_2591<T> type) {
        if (world.field_9236) {
            return null;
        }
        return type == RegisteredContent.stationaryCannonBlock() ? (tickWorld, tickPos, tickState, blockEntity) -> ((LegacyStationaryCannonAccess)blockEntity).tickServer(tickWorld, tickPos, tickState) : null;
    }

    public class_1269 method_9534(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_1268 hand, class_3965 hit) {
        if (hand != class_1268.field_5808) {
            return class_1269.field_5811;
        }
        class_2586 blockEntity = world.method_8321(pos);
        if (!(blockEntity instanceof LegacyStationaryCannonAccess)) {
            return class_1269.field_5811;
        }
        LegacyStationaryCannonAccess cannon = (LegacyStationaryCannonAccess)blockEntity;
        class_1799 heldStack = player.method_5998(hand);
        if (heldStack.method_31574(class_1802.field_8054) || ModItems.isCannonball(heldStack.method_7909())) {
            int targetSlot;
            if (world.field_9236) {
                return class_1269.field_5812;
            }
            int n = targetSlot = heldStack.method_31574(class_1802.field_8054) ? 0 : 1;
            if (!cannon.method_5438(targetSlot).method_7960()) {
                return class_1269.field_21466;
            }
            class_1799 loaded = heldStack.method_7972();
            loaded.method_7939(1);
            cannon.method_5447(targetSlot, loaded);
            if (!player.method_31549().field_7477) {
                heldStack.method_7934(1);
            }
            world.method_8396(null, pos, class_3417.field_15197, class_3419.field_15245, 0.45f, 0.85f);
            return class_1269.field_21466;
        }
        if (heldStack.method_31574(class_1802.field_8884)) {
            if (world.field_9236) {
                return class_1269.field_5812;
            }
            if (cannon.isIgnited()) {
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.stationary_cannon.already_ignited"), true);
                return class_1269.field_21466;
            }
            if (!cannon.isReadyToFire()) {
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.stationary_cannon.not_ready"), true);
                return class_1269.field_21466;
            }
            if (cannon.tryIgnite()) {
                world.method_8396(null, pos, class_3417.field_15145, class_3419.field_15245, 1.0f, 1.0f);
                if (!player.method_31549().field_7477) {
                    heldStack.method_7956(1, (class_1309)player, living -> living.method_20236(hand));
                }
                player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.stationary_cannon.ignited"), true);
            }
            return class_1269.field_21466;
        }
        if (world.field_9236) {
            return class_1269.field_5812;
        }
        if (cannon.isIgnited()) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.stationary_cannon.fuse_burning"), true);
            return class_1269.field_21466;
        }
        if (player instanceof class_3222) {
            class_3222 serverPlayer = (class_3222)player;
            serverPlayer.method_17355((class_3908)cannon);
        }
        return class_1269.field_21466;
    }

    public void method_9536(class_2680 state, class_1937 world, class_2338 pos, class_2680 newState, boolean moved) {
        if (!state.method_27852(newState.method_26204())) {
            class_2586 blockEntity = world.method_8321(pos);
            if (blockEntity instanceof LegacyStationaryCannonAccess) {
                LegacyStationaryCannonAccess cannon = (LegacyStationaryCannonAccess)blockEntity;
                class_1264.method_5451((class_1937)world, (class_2338)pos, (class_1263)cannon);
                world.method_8455(pos, (class_2248)this);
            }
            super.method_9536(state, world, pos, newState, moved);
        }
    }
}

