/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1542
 *  net.minecraft.class_1657
 *  net.minecraft.class_1750
 *  net.minecraft.class_1799
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2237
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2343
 *  net.minecraft.class_2350
 *  net.minecraft.class_2464
 *  net.minecraft.class_2586
 *  net.minecraft.class_259
 *  net.minecraft.class_2591
 *  net.minecraft.class_265
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2741
 *  net.minecraft.class_2753
 *  net.minecraft.class_2769
 *  net.minecraft.class_3222
 *  net.minecraft.class_3726
 *  net.minecraft.class_3908
 *  net.minecraft.class_3965
 *  net.minecraft.class_4970$class_2251
 *  net.minecraft.class_5558
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.storage;

import com.medievalmachinery.mod.block.storage.WickerBasketBlockAccess;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.storage.WickerBasketCarryBridge;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2237;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2343;
import net.minecraft.class_2350;
import net.minecraft.class_2464;
import net.minecraft.class_2586;
import net.minecraft.class_259;
import net.minecraft.class_2591;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2741;
import net.minecraft.class_2753;
import net.minecraft.class_2769;
import net.minecraft.class_3222;
import net.minecraft.class_3726;
import net.minecraft.class_3908;
import net.minecraft.class_3965;
import net.minecraft.class_4970;
import net.minecraft.class_5558;
import org.jetbrains.annotations.Nullable;

public final class WickerBasketBlock
extends class_2237
implements class_2343 {
    public static final class_2753 FACING = class_2741.field_12481;
    private static final class_265 SHAPE_NORTH_SOUTH = class_259.method_17786((class_265)WickerBasketBlock.method_9541((double)2.0, (double)0.0, (double)2.0, (double)14.0, (double)1.0, (double)14.0), (class_265[])new class_265[]{WickerBasketBlock.method_9541((double)2.0, (double)1.0, (double)2.0, (double)3.0, (double)8.0, (double)14.0), WickerBasketBlock.method_9541((double)13.0, (double)1.0, (double)2.0, (double)14.0, (double)8.0, (double)14.0), WickerBasketBlock.method_9541((double)3.0, (double)1.0, (double)2.0, (double)13.0, (double)8.0, (double)3.0), WickerBasketBlock.method_9541((double)3.0, (double)1.0, (double)13.0, (double)13.0, (double)8.0, (double)14.0), WickerBasketBlock.method_9541((double)4.0, (double)8.0, (double)2.25, (double)12.0, (double)11.0, (double)3.25), WickerBasketBlock.method_9541((double)4.0, (double)8.0, (double)12.75, (double)12.0, (double)11.0, (double)13.75)});
    private static final class_265 SHAPE_EAST_WEST = class_259.method_17786((class_265)WickerBasketBlock.method_9541((double)2.0, (double)0.0, (double)2.0, (double)14.0, (double)1.0, (double)14.0), (class_265[])new class_265[]{WickerBasketBlock.method_9541((double)2.0, (double)1.0, (double)2.0, (double)3.0, (double)8.0, (double)14.0), WickerBasketBlock.method_9541((double)13.0, (double)1.0, (double)2.0, (double)14.0, (double)8.0, (double)14.0), WickerBasketBlock.method_9541((double)3.0, (double)1.0, (double)2.0, (double)13.0, (double)8.0, (double)3.0), WickerBasketBlock.method_9541((double)3.0, (double)1.0, (double)13.0, (double)13.0, (double)8.0, (double)14.0), WickerBasketBlock.method_9541((double)2.25, (double)8.0, (double)4.0, (double)3.25, (double)11.0, (double)12.0), WickerBasketBlock.method_9541((double)12.75, (double)8.0, (double)4.0, (double)13.75, (double)11.0, (double)12.0)});

    public WickerBasketBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)this.method_9595().method_11664()).method_11657((class_2769)FACING, (Comparable)class_2350.field_11043));
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        builder.method_11667(new class_2769[]{FACING});
    }

    @Nullable
    public class_2680 method_9605(class_1750 ctx) {
        return (class_2680)this.method_9564().method_11657((class_2769)FACING, (Comparable)ctx.method_8042().method_10153());
    }

    public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        class_2350 facing = (class_2350)state.method_11654((class_2769)FACING);
        return facing == class_2350.field_11034 || facing == class_2350.field_11039 ? SHAPE_EAST_WEST : SHAPE_NORTH_SOUTH;
    }

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11458;
    }

    public class_1269 method_9534(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_1268 hand, class_3965 hit) {
        class_2586 class_25862;
        if (hand != class_1268.field_5808) {
            return class_1269.field_5811;
        }
        if (player.method_5715()) {
            if (world.field_9236) {
                return class_1269.field_5812;
            }
            class_2586 class_25863 = world.method_8321(pos);
            if (class_25863 instanceof WickerBasketBlockAccess) {
                WickerBasketBlockAccess basket = (WickerBasketBlockAccess)class_25863;
                return WickerBasketCarryBridge.pickUp((class_3222)player, pos, state, (class_2586)basket);
            }
            return class_1269.field_21466;
        }
        if (!world.field_9236 && (class_25862 = world.method_8321(pos)) instanceof WickerBasketBlockAccess) {
            WickerBasketBlockAccess basket = (WickerBasketBlockAccess)class_25862;
            player.method_17355((class_3908)basket);
        }
        return class_1269.method_29236((boolean)world.field_9236);
    }

    public void method_9567(class_1937 world, class_2338 pos, class_2680 state, @Nullable class_1309 placer, class_1799 itemStack) {
        class_2586 class_25862;
        super.method_9567(world, pos, state, placer, itemStack);
        if (itemStack.method_7938() && (class_25862 = world.method_8321(pos)) instanceof WickerBasketBlockAccess) {
            WickerBasketBlockAccess basket = (WickerBasketBlockAccess)class_25862;
            basket.method_5431();
        }
    }

    public void method_9536(class_2680 state, class_1937 world, class_2338 pos, class_2680 newState, boolean moved) {
        WickerBasketBlockAccess basket;
        class_2586 blockEntity;
        if (!state.method_27852(newState.method_26204()) && !world.field_9236 && (blockEntity = world.method_8321(pos)) instanceof WickerBasketBlockAccess && !(basket = (WickerBasketBlockAccess)blockEntity).shouldSuppressDrop()) {
            class_1799 dropped = basket.createBasketStack();
            class_1542 entity = new class_1542(world, (double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.35, (double)pos.method_10260() + 0.5, dropped);
            entity.method_6988();
            world.method_8649((class_1297)entity);
        }
        super.method_9536(state, world, pos, newState, moved);
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        return RegisteredContent.createWickerBasket(pos, state);
    }

    @Nullable
    public <T extends class_2586> class_5558<T> method_31645(class_1937 world, class_2680 state, class_2591<T> type) {
        return world.field_9236 ? null : WickerBasketBlock.method_31618(type, RegisteredContent.wickerBasket(), (tickWorld, tickPos, tickState, blockEntity) -> ((WickerBasketBlockAccess)blockEntity).tickServer(tickWorld, tickPos, tickState));
    }
}

