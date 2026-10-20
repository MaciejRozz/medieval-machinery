/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1657
 *  net.minecraft.class_1750
 *  net.minecraft.class_1937
 *  net.minecraft.class_2237
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2464
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2741
 *  net.minecraft.class_2753
 *  net.minecraft.class_2769
 *  net.minecraft.class_3222
 *  net.minecraft.class_3965
 *  net.minecraft.class_4970$class_2251
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.workshop;

import com.medievalmachinery.mod.platform.LoaderHooks;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1750;
import net.minecraft.class_1937;
import net.minecraft.class_2237;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2464;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2741;
import net.minecraft.class_2753;
import net.minecraft.class_2769;
import net.minecraft.class_3222;
import net.minecraft.class_3965;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public final class MachineWorkshopBlock
extends class_2237 {
    public static final class_2753 FACING = class_2741.field_12481;

    public MachineWorkshopBlock(class_4970.class_2251 settings) {
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

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11458;
    }

    public class_1269 method_9534(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_1268 hand, class_3965 hit) {
        if (hand != class_1268.field_5808) {
            return class_1269.field_5811;
        }
        if (!world.field_9236 && player instanceof class_3222) {
            class_3222 serverPlayer = (class_3222)player;
            class_2586 workshop = world.method_8321(pos);
            if (workshop != null) {
                LoaderHooks.openMachineWorkshop(serverPlayer, workshop);
            }
        }
        return class_1269.method_29236((boolean)world.field_9236);
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        return LoaderHooks.createMachineWorkshop(pos, state);
    }
}

