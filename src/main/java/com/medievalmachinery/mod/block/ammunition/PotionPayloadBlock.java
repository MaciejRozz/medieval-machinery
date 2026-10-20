/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1542
 *  net.minecraft.class_1657
 *  net.minecraft.class_1799
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2237
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2464
 *  net.minecraft.class_2586
 *  net.minecraft.class_265
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2758
 *  net.minecraft.class_2769
 *  net.minecraft.class_3726
 *  net.minecraft.class_3965
 *  net.minecraft.class_4970$class_2251
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.ammunition;

import com.medievalmachinery.mod.block.ammunition.PotionPayloadBlockEntity;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2237;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2464;
import net.minecraft.class_2586;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2758;
import net.minecraft.class_2769;
import net.minecraft.class_3726;
import net.minecraft.class_3965;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public final class PotionPayloadBlock
extends class_2237 {
    public static final class_2758 COUNT = class_2758.method_11867((String)"count", (int)1, (int)3);
    private static final class_265 SHAPE = class_2248.method_9541((double)2.0, (double)0.0, (double)2.0, (double)14.0, (double)13.0, (double)14.0);

    public PotionPayloadBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)this.method_9595().method_11664()).method_11657((class_2769)COUNT, (Comparable)Integer.valueOf(1)));
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        builder.method_11667(new class_2769[]{COUNT});
    }

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11455;
    }

    public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return SHAPE;
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        return new PotionPayloadBlockEntity(pos, state);
    }

    public class_1269 method_9534(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_1268 hand, class_3965 hit) {
        if (!player.method_5998(hand).method_7960()) {
            return class_1269.field_5811;
        }
        class_2586 class_25862 = world.method_8321(pos);
        if (class_25862 instanceof PotionPayloadBlockEntity) {
            PotionPayloadBlockEntity blockEntity = (PotionPayloadBlockEntity)class_25862;
            if (!world.field_9236) {
                int count;
                class_1799 removed = blockEntity.removeLast();
                if (!removed.method_7960() && !player.method_31548().method_7394(removed)) {
                    player.method_7328(removed, false);
                }
                if ((count = blockEntity.size()) <= 0) {
                    world.method_8650(pos, false);
                } else {
                    world.method_8652(pos, (class_2680)state.method_11657((class_2769)COUNT, (Comparable)Integer.valueOf(count)), 3);
                }
            }
            return class_1269.method_29236((boolean)world.field_9236);
        }
        return class_1269.field_5811;
    }

    public void method_9536(class_2680 state, class_1937 world, class_2338 pos, class_2680 newState, boolean moved) {
        Object object;
        if (!state.method_27852(newState.method_26204()) && (object = world.method_8321(pos)) instanceof PotionPayloadBlockEntity) {
            PotionPayloadBlockEntity blockEntity = (PotionPayloadBlockEntity)((Object)object);
            for (class_1799 stack : blockEntity.copyStacks()) {
                if (stack.method_7960()) continue;
                world.method_8649((class_1297)new class_1542(world, (double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.25, (double)pos.method_10260() + 0.5, stack));
            }
        }
        super.method_9536(state, world, pos, newState, moved);
    }
}

