/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2382
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.machine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class MachineFortificationTargetRegistry {
    private static final Map<class_3218, Map<Long, Entry>> BY_WORLD = new WeakHashMap<class_3218, Map<Long, Entry>>();

    private MachineFortificationTargetRegistry() {
    }

    public static synchronized void register(class_3218 world, class_2338 pos, @Nullable UUID kingdomId, @Nullable UUID castleId) {
        if (world == null || pos == null) {
            return;
        }
        BY_WORLD.computeIfAbsent(world, ignored -> new HashMap()).put(pos.method_10063(), new Entry(pos.method_10062(), kingdomId, castleId));
    }

    public static synchronized void unregister(class_3218 world, class_2338 pos) {
        Map<Long, Entry> entries = BY_WORLD.get(world);
        if (entries == null || pos == null) {
            return;
        }
        entries.remove(pos.method_10063());
        if (entries.isEmpty()) {
            BY_WORLD.remove(world);
        }
    }

    public static synchronized List<Entry> loadedWithin(class_3218 world, class_2338 center, double range) {
        Map<Long, Entry> entries = BY_WORLD.get(world);
        if (entries == null || entries.isEmpty()) {
            return List.of();
        }
        double rangeSq = range * range;
        ArrayList<Entry> result = new ArrayList<Entry>();
        for (Entry entry : entries.values()) {
            if (!world.method_22340(entry.pos()) || !(entry.pos().method_10262((class_2382)center) <= rangeSq)) continue;
            result.add(entry);
        }
        return List.copyOf(result);
    }

    public record Entry(class_2338 pos, @Nullable UUID kingdomId, @Nullable UUID castleId) {
    }
}

