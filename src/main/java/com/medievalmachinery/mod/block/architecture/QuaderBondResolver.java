/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1936
 *  net.minecraft.class_2338
 *  net.minecraft.class_2338$class_2339
 *  net.minecraft.class_2350
 *  net.minecraft.class_2350$class_2351
 *  net.minecraft.class_2680
 */
package com.medievalmachinery.mod.block.architecture;

import com.medievalmachinery.mod.block.architecture.ConnectedQuaderBlock;
import com.medievalmachinery.mod.block.architecture.QuaderPattern;
import net.minecraft.class_1936;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2680;

public final class QuaderBondResolver {
    private static final int FOUNDATION_SCAN_LIMIT = 32;

    private QuaderBondResolver() {
    }

    public static class_2350.class_2351 resolveWallAxis(class_1936 world, class_2338 pos, class_2350.class_2351 fallback) {
        int zConnections;
        int xConnections = QuaderBondResolver.same(world, pos.method_10078()) + QuaderBondResolver.same(world, pos.method_10067());
        if (xConnections > (zConnections = QuaderBondResolver.same(world, pos.method_10095()) + QuaderBondResolver.same(world, pos.method_10072()))) {
            return class_2350.class_2351.field_11048;
        }
        if (zConnections > xConnections) {
            return class_2350.class_2351.field_11051;
        }
        return fallback == class_2350.class_2351.field_11051 ? class_2350.class_2351.field_11051 : class_2350.class_2351.field_11048;
    }

    public static QuaderPattern resolvePattern(class_1936 world, class_2338 pos, class_2350.class_2351 axis) {
        int fineChance;
        int horizontalChance;
        int u = axis == class_2350.class_2351.field_11048 ? pos.method_10263() : pos.method_10260();
        int hAnchorU = QuaderBondResolver.floorEven(u);
        class_2338 hAnchor = QuaderBondResolver.withUY(pos, axis, hAnchorU, pos.method_10264());
        int height = QuaderBondResolver.heightAboveLocalFoot(world, hAnchor);
        int n = height <= 3 ? 34 : (horizontalChance = height <= 7 ? 30 : 26);
        if (QuaderBondResolver.pairHorizontal(world, hAnchor, axis) && QuaderBondResolver.choose(hAnchor, axis, 101) < horizontalChance) {
            return u == hAnchorU ? QuaderPattern.H_LEFT : QuaderPattern.H_RIGHT;
        }
        int vAnchorY = QuaderBondResolver.floorEven(pos.method_10264());
        class_2338 vAnchor = QuaderBondResolver.withUY(pos, axis, u, vAnchorY);
        if (QuaderBondResolver.pairVertical(world, vAnchor) && QuaderBondResolver.choose(vAnchor, axis, 102) < 6) {
            return pos.method_10264() == vAnchorY ? QuaderPattern.V_BOTTOM : QuaderPattern.V_TOP;
        }
        int n2 = height <= 3 ? 42 : (fineChance = height <= 7 ? 48 : 54);
        if (QuaderBondResolver.choose(pos, axis, 103) < fineChance) {
            return QuaderBondResolver.choose(pos, axis, 104) < 50 ? QuaderPattern.SMALL_A : QuaderPattern.SMALL_B;
        }
        return QuaderPattern.SINGLE;
    }

    private static int heightAboveLocalFoot(class_1936 world, class_2338 pos) {
        int depth;
        class_2338.class_2339 cursor = pos.method_25503();
        for (depth = 0; depth < 32; ++depth) {
            cursor.method_10098(class_2350.field_11033);
            if (!QuaderBondResolver.isQuader(world.method_8320((class_2338)cursor))) break;
        }
        return depth;
    }

    private static boolean pairHorizontal(class_1936 world, class_2338 anchor, class_2350.class_2351 axis) {
        return QuaderBondResolver.isQuader(world.method_8320(anchor)) && QuaderBondResolver.isQuader(world.method_8320(QuaderBondResolver.offsetU(anchor, axis, 1)));
    }

    private static boolean pairVertical(class_1936 world, class_2338 anchor) {
        return QuaderBondResolver.isQuader(world.method_8320(anchor)) && QuaderBondResolver.isQuader(world.method_8320(anchor.method_10084()));
    }

    private static int same(class_1936 world, class_2338 pos) {
        return QuaderBondResolver.isQuader(world.method_8320(pos)) ? 1 : 0;
    }

    private static boolean isQuader(class_2680 state) {
        return state.method_26204() instanceof ConnectedQuaderBlock;
    }

    private static int floorEven(int value) {
        return Math.floorDiv(value, 2) * 2;
    }

    private static class_2338 offsetU(class_2338 pos, class_2350.class_2351 axis, int amount) {
        return axis == class_2350.class_2351.field_11048 ? pos.method_10069(amount, 0, 0) : pos.method_10069(0, 0, amount);
    }

    private static class_2338 withUY(class_2338 source, class_2350.class_2351 axis, int u, int y) {
        return axis == class_2350.class_2351.field_11048 ? new class_2338(u, y, source.method_10260()) : new class_2338(source.method_10263(), y, u);
    }

    private static int choose(class_2338 anchor, class_2350.class_2351 axis, int salt) {
        long h = anchor.method_10063();
        h ^= axis == class_2350.class_2351.field_11048 ? 1327217884L : 1940257233L;
        h ^= (long)salt * -7046029254386353131L;
        h ^= h >>> 33;
        h *= -49064778989728563L;
        h ^= h >>> 33;
        return (int)Math.floorMod(h, 100L);
    }
}

