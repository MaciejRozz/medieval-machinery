/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.npc.organization.OrderStyle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class MedievalGarmentGeoContext {
    private static final ThreadLocal<State> CURRENT = ThreadLocal.withInitial(() -> new State(OrderStyle.IRON, true, true, true));

    private MedievalGarmentGeoContext() {
    }

    public static void prepare(OrderStyle order, boolean torso, boolean legs, boolean feet) {
        CURRENT.set(new State(order == null || order == OrderStyle.NONE ? OrderStyle.IRON : order, torso, legs, feet));
    }

    public static State current() {
        return CURRENT.get();
    }

    @Environment(value=EnvType.CLIENT)
    public record State(OrderStyle order, boolean torso, boolean legs, boolean feet) {
    }
}

