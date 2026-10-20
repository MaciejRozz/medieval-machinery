/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2960
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.medievalmachinery.mod;

import net.minecraft.class_2960;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MedievalMachineryCore {
    public static final String MOD_ID = "medieval-machinery";
    public static final Logger LOGGER = LoggerFactory.getLogger((String)"medieval-machinery");

    private MedievalMachineryCore() {
    }

    public static class_2960 id(String path) {
        return new class_2960(MOD_ID, path);
    }
}

