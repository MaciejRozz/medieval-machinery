/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1799
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2680
 *  net.minecraft.class_3908
 */
package com.medievalmachinery.mod.block.storage;

import net.minecraft.class_1263;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_3908;

public interface WickerBasketBlockAccess
extends class_1263,
class_3908 {
    public void suppressNextDrop();

    public boolean shouldSuppressDrop();

    public class_1799 createBasketStack();

    public void method_5431();

    public void tickServer(class_1937 var1, class_2338 var2, class_2680 var3);
}

