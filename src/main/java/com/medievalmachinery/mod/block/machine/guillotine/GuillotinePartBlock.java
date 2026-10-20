/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1269
 *  net.minecraft.class_1657
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2382
 *  net.minecraft.class_2464
 *  net.minecraft.class_2586
 *  net.minecraft.class_265
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2753
 *  net.minecraft.class_2758
 *  net.minecraft.class_2769
 *  net.minecraft.class_3726
 *  net.minecraft.class_3965
 *  net.minecraft.class_4970$class_2251
 */
package com.medievalmachinery.mod.block.machine.guillotine;

import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlock;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockAccess;
import com.medievalmachinery.mod.registry.ModBlocks;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_2464;
import net.minecraft.class_2586;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2753;
import net.minecraft.class_2758;
import net.minecraft.class_2769;
import net.minecraft.class_3726;
import net.minecraft.class_3965;
import net.minecraft.class_4970;

public final class GuillotinePartBlock
extends class_2248 {
    public static final class_2753 FACING = GuillotineBlock.FACING;
    public static final class_2758 OFFSET_X = class_2758.method_11867((String)"offset_x", (int)0, (int)1);
    public static final class_2758 OFFSET_Y = class_2758.method_11867((String)"offset_y", (int)0, (int)3);
    public static final class_2758 OFFSET_Z = class_2758.method_11867((String)"offset_z", (int)0, (int)2);

    public GuillotinePartBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)((class_2680)((class_2680)((class_2680)this.method_9595().method_11664()).method_11657((class_2769)FACING, (Comparable)class_2350.field_11043)).method_11657((class_2769)OFFSET_X, (Comparable)Integer.valueOf(0))).method_11657((class_2769)OFFSET_Y, (Comparable)Integer.valueOf(0))).method_11657((class_2769)OFFSET_Z, (Comparable)Integer.valueOf(0)));
    }

    public static int encodeOffsetZ(int canonicalOffsetZ) {
        if (canonicalOffsetZ < -1 || canonicalOffsetZ > 1) {
            throw new IllegalArgumentException("Royal guillotine offset Z must be between -1 and 1");
        }
        return canonicalOffsetZ + 1;
    }

    public static int decodeOffsetZ(int storedOffsetZ) {
        return storedOffsetZ - 1;
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        builder.method_11667(new class_2769[]{FACING, OFFSET_X, OFFSET_Y, OFFSET_Z});
    }

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11455;
    }

    public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return GuillotinePartBlock.getPartShape(state);
    }

    public class_265 method_9549(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return GuillotinePartBlock.getPartShape(state);
    }

    private static class_265 getPartShape(class_2680 state) {
        return GuillotineBlock.getRoyalPartShape((class_2350)state.method_11654((class_2769)FACING), (Integer)state.method_11654((class_2769)OFFSET_X), (Integer)state.method_11654((class_2769)OFFSET_Y), GuillotinePartBlock.decodeOffsetZ((Integer)state.method_11654((class_2769)OFFSET_Z)));
    }

    public static class_2338 getControllerPos(class_2680 state, class_2338 partPos) {
        class_2338 offset = GuillotineBlock.rotateRoyalOffset((class_2350)state.method_11654((class_2769)FACING), (Integer)state.method_11654((class_2769)OFFSET_X), (Integer)state.method_11654((class_2769)OFFSET_Y), GuillotinePartBlock.decodeOffsetZ((Integer)state.method_11654((class_2769)OFFSET_Z)));
        return partPos.method_10059((class_2382)offset);
    }

    public class_1269 method_9534(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_1268 hand, class_3965 hit) {
        class_2338 controllerPos = GuillotinePartBlock.getControllerPos(state, pos);
        class_2680 controllerState = world.method_8320(controllerPos);
        class_2586 blockEntity = world.method_8321(controllerPos);
        if (!controllerState.method_27852(ModBlocks.ROYAL_GUILLOTINE) || !(blockEntity instanceof GuillotineBlockAccess)) {
            return class_1269.field_5811;
        }
        GuillotineBlockAccess guillotine = (GuillotineBlockAccess)blockEntity;
        return GuillotineBlock.interactWithGuillotine(world, controllerPos, player, guillotine);
    }

    public void method_9612(class_2680 state, class_1937 world, class_2338 pos, class_2248 sourceBlock, class_2338 sourcePos, boolean notify) {
        super.method_9612(state, world, pos, sourceBlock, sourcePos, notify);
        if (world.field_9236) {
            return;
        }
        class_2338 controllerPos = GuillotinePartBlock.getControllerPos(state, pos);
        GuillotineBlock.updateRoyalRedstoneState(world, controllerPos);
    }

    public void method_9536(class_2680 state, class_1937 world, class_2338 pos, class_2680 newState, boolean moved) {
        class_2338 controllerPos;
        if (!state.method_27852(newState.method_26204()) && !world.field_9236 && world.method_8320(controllerPos = GuillotinePartBlock.getControllerPos(state, pos)).method_27852(ModBlocks.ROYAL_GUILLOTINE)) {
            world.method_22352(controllerPos, true);
        }
        super.method_9536(state, world, pos, newState, moved);
    }
}

