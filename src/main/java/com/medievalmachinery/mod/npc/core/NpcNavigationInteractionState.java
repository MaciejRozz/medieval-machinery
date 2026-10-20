/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1314
 *  net.minecraft.class_2248
 *  net.minecraft.class_2323
 *  net.minecraft.class_2338
 *  net.minecraft.class_2349
 *  net.minecraft.class_238
 *  net.minecraft.class_2680
 *  net.minecraft.class_2741
 *  net.minecraft.class_2769
 *  net.minecraft.class_3481
 */
package com.medievalmachinery.mod.npc.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1314;
import net.minecraft.class_2248;
import net.minecraft.class_2323;
import net.minecraft.class_2338;
import net.minecraft.class_2349;
import net.minecraft.class_238;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2769;
import net.minecraft.class_3481;

public final class NpcNavigationInteractionState {
    private static final int MIN_OPEN_TICKS = 8;
    private static final int FORGET_AFTER_TICKS = 200;
    private static final double CLOSE_DISTANCE_SQ = 2.25;
    private final Map<class_2338, Integer> openedPassages = new HashMap<class_2338, Integer>();

    public void recordOpenedPassage(class_2338 pos) {
        if (pos == null) {
            return;
        }
        this.openedPassages.putIfAbsent(pos.method_10062(), 0);
    }

    public void tick(class_1314 npc) {
        if (npc == null || this.openedPassages.isEmpty() || npc.method_37908().field_9236) {
            return;
        }
        Iterator<Map.Entry<class_2338, Integer>> iterator = this.openedPassages.entrySet().iterator();
        while (iterator.hasNext()) {
            boolean occupied;
            Map.Entry<class_2338, Integer> entry = iterator.next();
            class_2338 pos = entry.getKey();
            int openTicks = entry.getValue() + 1;
            entry.setValue(openTicks);
            class_2680 state = npc.method_37908().method_8320(pos);
            if (!NpcNavigationInteractionState.isManagedOpenPassage(state)) {
                iterator.remove();
                continue;
            }
            double distanceSq = npc.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5);
            if (openTicks < 8 || distanceSq < 2.25) continue;
            class_238 doorway = new class_238(pos).method_1009(0.55, 1.0, 0.55);
            boolean bl = occupied = !npc.method_37908().method_8333((class_1297)npc, doorway, entity -> entity.method_5805() && entity instanceof class_1309).isEmpty();
            if (occupied) {
                if (openTicks <= 200) continue;
                iterator.remove();
                continue;
            }
            NpcNavigationInteractionState.closePassage(npc, pos, state);
            iterator.remove();
        }
    }

    private static boolean isManagedOpenPassage(class_2680 state) {
        if (!state.method_28498((class_2769)class_2741.field_12537) || !((Boolean)state.method_11654((class_2769)class_2741.field_12537)).booleanValue()) {
            return false;
        }
        if (state.method_26204() instanceof class_2323) {
            return state.method_26164(class_3481.field_15494);
        }
        return state.method_26204() instanceof class_2349;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void closePassage(class_1314 npc, class_2338 pos, class_2680 state) {
        class_2248 class_22482 = state.method_26204();
        if (class_22482 instanceof class_2323) {
            class_2323 door = (class_2323)class_22482;
            if (state.method_26164(class_3481.field_15494)) {
                door.method_10033((class_1297)npc, npc.method_37908(), state, pos, false);
                return;
            }
        }
        if (!(state.method_26204() instanceof class_2349)) return;
        npc.method_37908().method_8501(pos, (class_2680)state.method_11657((class_2769)class_2741.field_12537, (Comparable)Boolean.valueOf(false)));
    }
}

