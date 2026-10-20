/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_18
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.class_18;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class GeneralBodyguardManager
extends class_18 {
    private static final String SAVE_ID = "medieval_machinery_general_bodyguards";
    private final Map<UUID, LinkedHashSet<UUID>> guardsByGeneral = new HashMap<UUID, LinkedHashSet<UUID>>();

    public static GeneralBodyguardManager get(class_3218 world) {
        class_3218 overworld = world.method_8503().method_30002();
        return (GeneralBodyguardManager)overworld.method_17983().method_17924(GeneralBodyguardManager::fromNbt, GeneralBodyguardManager::new, SAVE_ID);
    }

    public Set<UUID> getGuards(UUID generalId) {
        if (generalId == null) {
            return Set.of();
        }
        LinkedHashSet<UUID> guards = this.guardsByGeneral.get(generalId);
        return guards == null ? Set.of() : Set.copyOf(guards);
    }

    @Nullable
    public UUID getGeneralForGuard(UUID guardId) {
        if (guardId == null) {
            return null;
        }
        for (Map.Entry<UUID, LinkedHashSet<UUID>> entry : this.guardsByGeneral.entrySet()) {
            if (!entry.getValue().contains(guardId)) continue;
            return entry.getKey();
        }
        return null;
    }

    public int getGuardSlot(UUID generalId, UUID guardId) {
        if (generalId == null || guardId == null) {
            return -1;
        }
        LinkedHashSet<UUID> guards = this.guardsByGeneral.get(generalId);
        if (guards == null) {
            return -1;
        }
        int slot = 0;
        for (UUID id : guards) {
            if (guardId.equals(id)) {
                return slot;
            }
            ++slot;
        }
        return -1;
    }

    public boolean assign(UUID generalId, UUID guardId) {
        if (generalId == null || guardId == null || generalId.equals(guardId)) {
            return false;
        }
        this.removeGuard(guardId);
        LinkedHashSet guards = this.guardsByGeneral.computeIfAbsent(generalId, ignored -> new LinkedHashSet());
        if (guards.size() >= 2) {
            return false;
        }
        boolean changed = guards.add(guardId);
        if (changed) {
            this.method_80();
        }
        return changed;
    }

    public boolean removeGuard(UUID guardId) {
        if (guardId == null) {
            return false;
        }
        boolean changed = false;
        Iterator<Map.Entry<UUID, LinkedHashSet<UUID>>> iterator = this.guardsByGeneral.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, LinkedHashSet<UUID>> entry = iterator.next();
            if (entry.getValue().remove(guardId)) {
                changed = true;
            }
            if (!entry.getValue().isEmpty()) continue;
            iterator.remove();
        }
        if (changed) {
            this.method_80();
        }
        return changed;
    }

    public boolean clearGeneral(UUID generalId) {
        boolean changed;
        if (generalId == null) {
            return false;
        }
        boolean bl = changed = this.guardsByGeneral.remove(generalId) != null;
        if (changed) {
            this.method_80();
        }
        return changed;
    }

    public class_2487 method_75(class_2487 nbt) {
        class_2499 list = new class_2499();
        for (Map.Entry<UUID, LinkedHashSet<UUID>> entry : this.guardsByGeneral.entrySet()) {
            class_2487 row = new class_2487();
            row.method_25927("General", entry.getKey());
            class_2499 guards = new class_2499();
            for (UUID guardId : entry.getValue()) {
                class_2487 guard = new class_2487();
                guard.method_25927("Id", guardId);
                guards.add((Object)guard);
            }
            row.method_10566("Guards", (class_2520)guards);
            list.add((Object)row);
        }
        nbt.method_10566("Assignments", (class_2520)list);
        return nbt;
    }

    private static GeneralBodyguardManager fromNbt(class_2487 nbt) {
        GeneralBodyguardManager manager = new GeneralBodyguardManager();
        class_2499 list = nbt.method_10554("Assignments", 10);
        for (int i = 0; i < list.size(); ++i) {
            class_2487 row = list.method_10602(i);
            if (!row.method_25928("General")) continue;
            UUID generalId = row.method_25926("General");
            LinkedHashSet<UUID> guards = new LinkedHashSet<UUID>();
            class_2499 guardList = row.method_10554("Guards", 10);
            for (int j = 0; j < guardList.size() && guards.size() < 2; ++j) {
                class_2487 guard = guardList.method_10602(j);
                if (!guard.method_25928("Id")) continue;
                guards.add(guard.method_25926("Id"));
            }
            if (guards.isEmpty()) continue;
            manager.guardsByGeneral.put(generalId, guards);
        }
        return manager;
    }
}

