/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1750
 *  net.minecraft.class_2237
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2383
 *  net.minecraft.class_2415
 *  net.minecraft.class_2464
 *  net.minecraft.class_2470
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2753
 *  net.minecraft.class_2769
 *  net.minecraft.class_4970$class_2251
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.machine.cannon;

import com.medievalmachinery.mod.blockentity.machine.cannon.DecorativeFieldCannonBlockEntity;
import net.minecraft.class_1750;
import net.minecraft.class_2237;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2383;
import net.minecraft.class_2415;
import net.minecraft.class_2464;
import net.minecraft.class_2470;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2753;
import net.minecraft.class_2769;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public final class DecorativeFieldCannonBlock
extends class_2237 {
    public static final class_2753 FACING = class_2383.field_11177;

    public DecorativeFieldCannonBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)this.method_9595().method_11664()).method_11657((class_2769)FACING, (Comparable)class_2350.field_11043));
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> b) {
        b.method_11667(new class_2769[]{FACING});
    }

    @Nullable
    public class_2680 method_9605(class_1750 context) {
        return (class_2680)this.method_9564().method_11657((class_2769)FACING, (Comparable)context.method_8042());
    }

    public class_2680 method_9598(class_2680 state, class_2470 rotation) {
        return (class_2680)state.method_11657((class_2769)FACING, (Comparable)rotation.method_10503((class_2350)state.method_11654((class_2769)FACING)));
    }

    public class_2680 method_9569(class_2680 state, class_2415 mirror) {
        return state.method_26186(mirror.method_10345((class_2350)state.method_11654((class_2769)FACING)));
    }

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11455;
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        return new DecorativeFieldCannonBlockEntity(pos, state);
    }
}

