/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_3414
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.sound;

import com.medievalmachinery.mod.MedievalMachineryCore;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_7923;

public final class ModSounds {
    public static final class_2960 CROW_EXECUTION_ID = MedievalMachineryCore.id("crow_execution");
    public static final class_3414 CROW_EXECUTION = class_3414.method_47908((class_2960)CROW_EXECUTION_ID);

    private ModSounds() {
    }

    public static void register() {
        class_2378.method_10230((class_2378)class_7923.field_41172, (class_2960)CROW_EXECUTION_ID, (Object)CROW_EXECUTION);
    }
}

