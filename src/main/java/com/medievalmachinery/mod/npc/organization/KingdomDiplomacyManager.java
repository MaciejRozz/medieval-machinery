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

import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyStance;
import com.medievalmachinery.mod.npc.organization.KingdomRelation;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_18;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class KingdomDiplomacyManager
extends class_18 {
    private static final String SAVE_ID = "medieval-machinery_kingdom_diplomacy";
    private final Map<UUID, Map<UUID, KingdomDiplomacyStance>> stances = new LinkedHashMap<UUID, Map<UUID, KingdomDiplomacyStance>>();

    public static KingdomDiplomacyManager get(class_3218 world) {
        class_3218 overworld = world.method_8503().method_30002();
        return (KingdomDiplomacyManager)overworld.method_17983().method_17924(KingdomDiplomacyManager::fromNbt, KingdomDiplomacyManager::new, SAVE_ID);
    }

    public KingdomDiplomacyStance getStance(@Nullable UUID source, @Nullable UUID target) {
        if (source == null || target == null || source.equals(target)) {
            return KingdomDiplomacyStance.NEUTRAL;
        }
        Map<UUID, KingdomDiplomacyStance> row = this.stances.get(source);
        return row == null ? KingdomDiplomacyStance.NEUTRAL : row.getOrDefault(target, KingdomDiplomacyStance.NEUTRAL);
    }

    public boolean setStance(UUID source, UUID target, KingdomDiplomacyStance stance) {
        if (source == null || target == null || source.equals(target) || stance == null) {
            return false;
        }
        KingdomDiplomacyStance previous = this.getStance(source, target);
        if (previous == stance) {
            return false;
        }
        Map row = this.stances.computeIfAbsent(source, ignored -> new LinkedHashMap());
        if (stance == KingdomDiplomacyStance.NEUTRAL) {
            row.remove(target);
        } else {
            row.put(target, stance);
        }
        if (row.isEmpty()) {
            this.stances.remove(source);
        }
        this.method_80();
        return true;
    }

    public KingdomRelation getEffectiveRelation(@Nullable UUID a, @Nullable UUID b) {
        if (a == null || b == null) {
            return KingdomRelation.NEUTRAL;
        }
        if (a.equals(b)) {
            return KingdomRelation.SAME_KINGDOM;
        }
        KingdomDiplomacyStance ab = this.getStance(a, b);
        KingdomDiplomacyStance ba = this.getStance(b, a);
        if (ab == KingdomDiplomacyStance.WAR || ba == KingdomDiplomacyStance.WAR) {
            return KingdomRelation.WAR;
        }
        if (ab == KingdomDiplomacyStance.ALLY && ba == KingdomDiplomacyStance.ALLY) {
            return KingdomRelation.ALLIED;
        }
        return KingdomRelation.NEUTRAL;
    }

    public void removeKingdom(UUID kingdomId) {
        if (kingdomId == null) {
            return;
        }
        boolean changed = this.stances.remove(kingdomId) != null;
        for (Map<UUID, KingdomDiplomacyStance> row : this.stances.values()) {
            changed |= row.remove(kingdomId) != null;
        }
        this.stances.entrySet().removeIf(e -> ((Map)e.getValue()).isEmpty());
        if (changed) {
            this.method_80();
        }
    }

    public class_2487 method_75(class_2487 nbt) {
        class_2499 list = new class_2499();
        for (Map.Entry<UUID, Map<UUID, KingdomDiplomacyStance>> source : this.stances.entrySet()) {
            for (Map.Entry<UUID, KingdomDiplomacyStance> target : source.getValue().entrySet()) {
                class_2487 entry = new class_2487();
                entry.method_25927("Source", source.getKey());
                entry.method_25927("Target", target.getKey());
                entry.method_10582("Stance", target.getValue().name());
                list.add((Object)entry);
            }
        }
        nbt.method_10566("Relations", (class_2520)list);
        return nbt;
    }

    private static KingdomDiplomacyManager fromNbt(class_2487 nbt) {
        KingdomDiplomacyManager manager = new KingdomDiplomacyManager();
        class_2499 list = nbt.method_10554("Relations", 10);
        for (int i = 0; i < list.size(); ++i) {
            KingdomDiplomacyStance stance;
            UUID target;
            UUID source;
            class_2487 entry = list.method_10602(i);
            if (!entry.method_25928("Source") || !entry.method_25928("Target") || (source = entry.method_25926("Source")).equals(target = entry.method_25926("Target")) || (stance = KingdomDiplomacyStance.fromName(entry.method_10558("Stance"))) == KingdomDiplomacyStance.NEUTRAL) continue;
            manager.stances.computeIfAbsent(source, ignored -> new LinkedHashMap()).put(target, stance);
        }
        return manager;
    }
}

