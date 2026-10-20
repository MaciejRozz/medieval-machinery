/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.machine.core;

import com.medievalmachinery.mod.machine.core.MachineTargetCategory;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public final class MachineTargetProfile {
    public static final MachineTargetProfile NONE = new MachineTargetProfile(EnumSet.noneOf(MachineTargetCategory.class));
    private final Set<MachineTargetCategory> categories;

    private MachineTargetProfile(EnumSet<MachineTargetCategory> categories) {
        this.categories = Collections.unmodifiableSet(EnumSet.copyOf(categories));
    }

    public static MachineTargetProfile of(MachineTargetCategory first, MachineTargetCategory ... rest) {
        EnumSet<MachineTargetCategory[]> set = EnumSet.of(first, rest);
        return new MachineTargetProfile(set);
    }

    public boolean accepts(MachineTargetCategory category) {
        return category != null && this.categories.contains((Object)category);
    }

    public Set<MachineTargetCategory> categories() {
        return this.categories;
    }
}

