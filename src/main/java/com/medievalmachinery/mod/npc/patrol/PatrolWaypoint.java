/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 */
package com.medievalmachinery.mod.npc.patrol;

import net.minecraft.class_2338;
import net.minecraft.class_2487;

public final class PatrolWaypoint {
    private final class_2338 position;

    public PatrolWaypoint(class_2338 position) {
        this.position = position == null ? class_2338.field_10980 : position.method_10062();
    }

    public class_2338 position() {
        return this.position;
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        nbt.method_10569("X", this.position.method_10263());
        nbt.method_10569("Y", this.position.method_10264());
        nbt.method_10569("Z", this.position.method_10260());
        return nbt;
    }

    public static PatrolWaypoint fromNbt(class_2487 nbt) {
        if (!(nbt != null && nbt.method_10573("X", 3) && nbt.method_10573("Y", 3) && nbt.method_10573("Z", 3))) {
            return new PatrolWaypoint(class_2338.field_10980);
        }
        return new PatrolWaypoint(new class_2338(nbt.method_10550("X"), nbt.method_10550("Y"), nbt.method_10550("Z")));
    }
}

