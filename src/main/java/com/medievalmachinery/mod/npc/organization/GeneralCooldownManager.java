/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_18
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_18;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_3218;

public final class GeneralCooldownManager
extends class_18 {
    private static final String SAVE_ID = "medieval-machinery_general_cooldowns";
    private final Map<UUID, Long> cooldownUntil = new HashMap<UUID, Long>();

    public static GeneralCooldownManager get(class_3218 world) {
        class_3218 overworld = world.method_8503().method_30002();
        return (GeneralCooldownManager)overworld.method_17983().method_17924(GeneralCooldownManager::fromNbt, GeneralCooldownManager::new, SAVE_ID);
    }

    public void start(class_3218 world, UUID castleId) {
        if (castleId == null) {
            return;
        }
        long days = Math.max(0, MedievalMachineryConfig.get().generalCooldownDays);
        this.cooldownUntil.put(castleId, world.method_8503().method_30002().method_8510() + days * 24000L);
        this.method_80();
    }

    public boolean isActive(class_3218 world, UUID castleId) {
        if (castleId == null) {
            return false;
        }
        Long until = this.cooldownUntil.get(castleId);
        if (until == null) {
            return false;
        }
        long now = world.method_8503().method_30002().method_8510();
        if (now >= until) {
            this.cooldownUntil.remove(castleId);
            this.method_80();
            return false;
        }
        return true;
    }

    public long remainingTicks(class_3218 world, UUID castleId) {
        Long until;
        Long l = until = castleId == null ? null : this.cooldownUntil.get(castleId);
        if (until == null) {
            return 0L;
        }
        return Math.max(0L, until - world.method_8503().method_30002().method_8510());
    }

    public void remove(UUID castleId) {
        if (castleId != null && this.cooldownUntil.remove(castleId) != null) {
            this.method_80();
        }
    }

    public class_2487 method_75(class_2487 nbt) {
        class_2499 list = new class_2499();
        for (Map.Entry<UUID, Long> entry : this.cooldownUntil.entrySet()) {
            class_2487 row = new class_2487();
            row.method_25927("CastleId", entry.getKey());
            row.method_10544("Until", entry.getValue().longValue());
            list.add((Object)row);
        }
        nbt.method_10566("Cooldowns", (class_2520)list);
        return nbt;
    }

    private static GeneralCooldownManager fromNbt(class_2487 nbt) {
        GeneralCooldownManager manager = new GeneralCooldownManager();
        class_2499 list = nbt.method_10554("Cooldowns", 10);
        for (int i = 0; i < list.size(); ++i) {
            class_2487 row = list.method_10602(i);
            if (!row.method_25928("CastleId")) continue;
            manager.cooldownUntil.put(row.method_25926("CastleId"), row.method_10537("Until"));
        }
        return manager;
    }
}

