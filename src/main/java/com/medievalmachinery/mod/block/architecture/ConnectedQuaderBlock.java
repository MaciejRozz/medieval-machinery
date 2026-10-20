/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_1750
 *  net.minecraft.class_1799
 *  net.minecraft.class_1936
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350$class_2351
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2754
 *  net.minecraft.class_2769
 *  net.minecraft.class_4970$class_2251
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.architecture;

import com.medievalmachinery.mod.block.architecture.QuaderBondResolver;
import com.medievalmachinery.mod.block.architecture.QuaderPattern;
import net.minecraft.class_1309;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1936;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2754;
import net.minecraft.class_2769;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public class ConnectedQuaderBlock
extends class_2248 {
    public static final class_2754<class_2350.class_2351> WALL_AXIS = class_2754.method_11848((String)"axis", class_2350.class_2351.class, axis -> axis == class_2350.class_2351.field_11048 || axis == class_2350.class_2351.field_11051);
    public static final class_2754<QuaderPattern> PATTERN = class_2754.method_11850((String)"pattern", QuaderPattern.class);
    private static final int REFRESH_HORIZONTAL_RADIUS = 2;
    private static final int REFRESH_VERTICAL_RADIUS = 2;

    public ConnectedQuaderBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)((class_2680)this.method_9595().method_11664()).method_11657(WALL_AXIS, (Comparable)class_2350.class_2351.field_11048)).method_11657(PATTERN, (Comparable)((Object)QuaderPattern.SINGLE)));
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        builder.method_11667(new class_2769[]{WALL_AXIS, PATTERN});
    }

    public class_2680 method_9605(class_1750 context) {
        class_2350.class_2351 lookAxis = context.method_8042().method_10166();
        class_2350.class_2351 wallAxis = lookAxis == class_2350.class_2351.field_11048 ? class_2350.class_2351.field_11051 : class_2350.class_2351.field_11048;
        return (class_2680)((class_2680)this.method_9564().method_11657(WALL_AXIS, (Comparable)wallAxis)).method_11657(PATTERN, (Comparable)((Object)QuaderPattern.SINGLE));
    }

    public void method_9567(class_1937 world, class_2338 pos, class_2680 state, @Nullable class_1309 placer, class_1799 itemStack) {
        super.method_9567(world, pos, state, placer, itemStack);
        if (!world.field_9236) {
            ConnectedQuaderBlock.refreshAround(world, pos);
        }
    }

    public void method_9536(class_2680 state, class_1937 world, class_2338 pos, class_2680 newState, boolean moved) {
        boolean removed = !state.method_27852(newState.method_26204());
        super.method_9536(state, world, pos, newState, moved);
        if (!world.field_9236 && removed) {
            ConnectedQuaderBlock.refreshAround(world, pos);
        }
    }

    private static void refreshAround(class_1937 world, class_2338 center) {
        class_2680 current;
        class_2338 pos;
        int dz;
        int dx;
        int dy;
        for (dy = -2; dy <= 2; ++dy) {
            for (dx = -2; dx <= 2; ++dx) {
                for (dz = -2; dz <= 2; ++dz) {
                    pos = center.method_10069(dx, dy, dz);
                    current = world.method_8320(pos);
                    if (!(current.method_26204() instanceof ConnectedQuaderBlock)) continue;
                    class_2350.class_2351 axis = QuaderBondResolver.resolveWallAxis((class_1936)world, pos, (class_2350.class_2351)current.method_11654(WALL_AXIS));
                    if (current.method_11654(WALL_AXIS) == axis) continue;
                    world.method_8652(pos, (class_2680)current.method_11657(WALL_AXIS, (Comparable)axis), 2);
                }
            }
        }
        for (dy = -2; dy <= 2; ++dy) {
            for (dx = -2; dx <= 2; ++dx) {
                for (dz = -2; dz <= 2; ++dz) {
                    pos = center.method_10069(dx, dy, dz);
                    current = world.method_8320(pos);
                    if (!(current.method_26204() instanceof ConnectedQuaderBlock)) continue;
                    QuaderPattern pattern = QuaderBondResolver.resolvePattern((class_1936)world, pos, (class_2350.class_2351)current.method_11654(WALL_AXIS));
                    if (current.method_11654(PATTERN) == pattern) continue;
                    world.method_8652(pos, (class_2680)current.method_11657(PATTERN, (Comparable)((Object)pattern)), 2);
                }
            }
        }
    }
}

