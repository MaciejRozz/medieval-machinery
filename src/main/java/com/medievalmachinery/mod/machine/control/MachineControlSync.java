/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.machine.control;

import com.medievalmachinery.mod.entity.machine.AbstractMedievalMachineEntity;
import java.util.Objects;
import net.minecraft.class_3222;

public final class MachineControlSync {
    private static Handler handler;

    private MachineControlSync() {
    }

    public static void install(Handler value) {
        if (handler != null) {
            throw new IllegalStateException("Machine control sync already installed");
        }
        handler = Objects.requireNonNull(value, "handler");
    }

    public static void notifyStarted(class_3222 player, AbstractMedievalMachineEntity machine) {
        if (handler == null) {
            throw new IllegalStateException("Machine control sync not installed");
        }
        handler.notifyStarted(player, machine);
    }

    public static void notifyStopped(class_3222 player, AbstractMedievalMachineEntity machine) {
        if (handler == null) {
            throw new IllegalStateException("Machine control sync not installed");
        }
        handler.notifyStopped(player, machine);
    }

    public static interface Handler {
        public void notifyStarted(class_3222 var1, AbstractMedievalMachineEntity var2);

        public void notifyStopped(class_3222 var1, AbstractMedievalMachineEntity var2);
    }
}

