/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_18
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.organization;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_18;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_3218;

public final class CastleCaptureStateManager
extends class_18 {
    private static final String SAVE_ID = "medieval-machinery_castle_capture";
    private final Map<UUID, CaptureState> byCastle = new LinkedHashMap<UUID, CaptureState>();

    public static CastleCaptureStateManager get(class_3218 world) {
        class_3218 overworld = world.method_8503().method_30002();
        return (CastleCaptureStateManager)overworld.method_17983().method_17924(CastleCaptureStateManager::fromNbt, CastleCaptureStateManager::new, SAVE_ID);
    }

    public boolean start(UUID castleId, UUID playerId, UUID attackerKingdomId, UUID defenderKingdomId, String dimension, class_2338 pos, long startTick, long durationTicks) {
        if (castleId == null || playerId == null || attackerKingdomId == null || defenderKingdomId == null || dimension == null || pos == null || this.byCastle.containsKey(castleId)) {
            return false;
        }
        this.byCastle.put(castleId, new CaptureState(castleId, playerId, attackerKingdomId, defenderKingdomId, dimension, pos.method_10062(), startTick, Math.max(1L, durationTicks)));
        this.method_80();
        return true;
    }

    public CaptureState get(UUID castleId) {
        return castleId == null ? null : this.byCastle.get(castleId);
    }

    public Collection<CaptureState> all() {
        return List.copyOf(this.byCastle.values());
    }

    public void finish(UUID castleId) {
        if (castleId != null && this.byCastle.remove(castleId) != null) {
            this.method_80();
        }
    }

    public void cancel(UUID castleId) {
        this.finish(castleId);
    }

    public class_2487 method_75(class_2487 nbt) {
        class_2499 list = new class_2499();
        for (CaptureState state : this.byCastle.values()) {
            class_2487 row = new class_2487();
            row.method_25927("CastleId", state.castleId());
            row.method_25927("PlayerId", state.playerId());
            row.method_25927("AttackerKingdomId", state.attackerKingdomId());
            row.method_25927("DefenderKingdomId", state.defenderKingdomId());
            row.method_10582("Dimension", state.dimension());
            row.method_10544("Pos", state.pos().method_10063());
            row.method_10544("Start", state.startTick());
            row.method_10544("Duration", state.durationTicks());
            list.add((Object)row);
        }
        nbt.method_10566("Captures", (class_2520)list);
        return nbt;
    }

    private static CastleCaptureStateManager fromNbt(class_2487 nbt) {
        CastleCaptureStateManager manager = new CastleCaptureStateManager();
        class_2499 list = nbt.method_10554("Captures", 10);
        for (int i = 0; i < list.size(); ++i) {
            class_2487 row = list.method_10602(i);
            if (!row.method_25928("CastleId") || !row.method_25928("PlayerId") || !row.method_25928("AttackerKingdomId") || !row.method_25928("DefenderKingdomId")) continue;
            CaptureState state = new CaptureState(row.method_25926("CastleId"), row.method_25926("PlayerId"), row.method_25926("AttackerKingdomId"), row.method_25926("DefenderKingdomId"), row.method_10558("Dimension"), class_2338.method_10092((long)row.method_10537("Pos")), row.method_10537("Start"), Math.max(1L, row.method_10537("Duration")));
            manager.byCastle.put(state.castleId(), state);
        }
        return manager;
    }

    public record CaptureState(UUID castleId, UUID playerId, UUID attackerKingdomId, UUID defenderKingdomId, String dimension, class_2338 pos, long startTick, long durationTicks) {
        public float progress(long now) {
            return Math.max(0.0f, Math.min(1.0f, (float)(now - this.startTick) / (float)this.durationTicks));
        }

        public boolean complete(long now) {
            return now - this.startTick >= this.durationTicks;
        }
    }
}

