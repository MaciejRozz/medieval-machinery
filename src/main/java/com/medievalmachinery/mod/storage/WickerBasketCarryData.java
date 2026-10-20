/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_2487
 *  net.minecraft.class_2512
 *  net.minecraft.class_2520
 *  net.minecraft.class_2680
 *  net.minecraft.class_7225$class_7226
 *  net.minecraft.class_7871
 *  net.minecraft.class_7924
 */
package com.medievalmachinery.mod.storage;

import net.minecraft.class_1937;
import net.minecraft.class_2487;
import net.minecraft.class_2512;
import net.minecraft.class_2520;
import net.minecraft.class_2680;
import net.minecraft.class_7225;
import net.minecraft.class_7871;
import net.minecraft.class_7924;

public record WickerBasketCarryData(class_2487 blockStateNbt, class_2487 blockEntityNbt) {
    public WickerBasketCarryData {
        blockStateNbt = blockStateNbt.method_10553();
        blockEntityNbt = blockEntityNbt.method_10553();
    }

    public static WickerBasketCarryData of(class_2680 state, class_2487 blockEntityNbt) {
        return new WickerBasketCarryData(class_2512.method_10686((class_2680)state), blockEntityNbt);
    }

    public class_2680 blockState(class_1937 world) {
        class_7225.class_7226 blocks = world.method_30349().method_46762(class_7924.field_41254);
        return class_2512.method_10681((class_7871)blocks, (class_2487)this.blockStateNbt);
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        nbt.method_10566("BlockState", (class_2520)this.blockStateNbt.method_10553());
        nbt.method_10566("BlockEntity", (class_2520)this.blockEntityNbt.method_10553());
        return nbt;
    }

    public static WickerBasketCarryData fromNbt(class_2487 nbt) {
        if (!nbt.method_10573("BlockState", 10) || !nbt.method_10573("BlockEntity", 10)) {
            return null;
        }
        return new WickerBasketCarryData(nbt.method_10562("BlockState"), nbt.method_10562("BlockEntity"));
    }
}

