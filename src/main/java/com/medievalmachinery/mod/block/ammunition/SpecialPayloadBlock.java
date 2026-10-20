/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1922
 *  net.minecraft.class_2237
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_2464
 *  net.minecraft.class_2586
 *  net.minecraft.class_265
 *  net.minecraft.class_2680
 *  net.minecraft.class_3726
 *  net.minecraft.class_4970$class_2251
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.ammunition;

import com.medievalmachinery.mod.block.ammunition.SpecialPayloadBlockEntity;
import net.minecraft.class_1922;
import net.minecraft.class_2237;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2464;
import net.minecraft.class_2586;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_3726;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public final class SpecialPayloadBlock
extends class_2237 {
    public SpecialPayloadBlock(class_4970.class_2251 settings) {
        super(settings);
    }

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11455;
    }

    public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return class_2246.field_42752.method_9564().method_26172(world, pos, context);
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        return new SpecialPayloadBlockEntity(pos, state);
    }
}

