/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1657
 *  net.minecraft.class_1750
 *  net.minecraft.class_1799
 *  net.minecraft.class_1922
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_259
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
package com.medievalmachinery.mod.block.machine.cannon;

import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1922;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2758;
import net.minecraft.class_2769;
import net.minecraft.class_3726;
import net.minecraft.class_3965;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public final class CannonballPileBlock
extends class_2248 {
    public static final class_2758 COUNT = class_2758.method_11867((String)"count", (int)1, (int)4);
    private static final class_265 ONE = CannonballPileBlock.sphere(5, 0, 5);
    private static final class_265 TWO = class_259.method_1084((class_265)CannonballPileBlock.sphere(2, 0, 5), (class_265)CannonballPileBlock.sphere(8, 0, 5));
    private static final class_265 THREE = class_259.method_17786((class_265)CannonballPileBlock.sphere(2, 0, 2), (class_265[])new class_265[]{CannonballPileBlock.sphere(8, 0, 2), CannonballPileBlock.sphere(5, 0, 8)});
    private static final class_265 FOUR = class_259.method_1084((class_265)THREE, (class_265)CannonballPileBlock.sphere(5, 6, 4));

    public CannonballPileBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)this.method_9595().method_11664()).method_11657((class_2769)COUNT, (Comparable)Integer.valueOf(1)));
    }

    private static class_265 sphere(int x, int y, int z) {
        return class_2248.method_9541((double)x, (double)y, (double)z, (double)(x + 6), (double)(y + 6), (double)(z + 6));
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        builder.method_11667(new class_2769[]{COUNT});
    }

    @Nullable
    public class_2680 method_9605(class_1750 context) {
        class_2680 existing = context.method_8045().method_8320(context.method_8037());
        if (existing.method_27852((class_2248)this)) {
            int count = (Integer)existing.method_11654((class_2769)COUNT);
            return count < 4 ? (class_2680)existing.method_11657((class_2769)COUNT, (Comparable)Integer.valueOf(count + 1)) : existing;
        }
        return this.method_9564();
    }

    public boolean method_9616(class_2680 state, class_1750 context) {
        return !context.method_8046() && context.method_8041().method_31574(this.method_8389()) && (Integer)state.method_11654((class_2769)COUNT) < 4;
    }

    public class_1269 method_9534(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_1268 hand, class_3965 hit) {
        class_1799 held = player.method_5998(hand);
        if (held.method_7960() && !player.method_5715()) {
            if (!world.field_9236) {
                int count = (Integer)state.method_11654((class_2769)COUNT);
                class_1799 collected = new class_1799((class_1935)this.method_8389());
                if (!player.method_31548().method_7394(collected) && !collected.method_7960()) {
                    player.method_7328(collected, false);
                }
                if (count <= 1) {
                    world.method_8650(pos, false);
                } else {
                    world.method_8652(pos, (class_2680)state.method_11657((class_2769)COUNT, (Comparable)Integer.valueOf(count - 1)), 3);
                }
            }
            return class_1269.method_29236((boolean)world.field_9236);
        }
        return class_1269.field_5811;
    }

    public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return switch ((Integer)state.method_11654((class_2769)COUNT)) {
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            default -> ONE;
        };
    }

    public class_1799 method_9574(class_1922 world, class_2338 pos, class_2680 state) {
        return new class_1799((class_1935)this.method_8389());
    }
}

