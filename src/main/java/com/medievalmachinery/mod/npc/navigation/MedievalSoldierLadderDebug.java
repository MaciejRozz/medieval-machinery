/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.navigation;

import com.medievalmachinery.mod.MedievalMachineryCore;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.Locale;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierLadderDebug {
    private final MedievalSoldierEntity soldier;
    @Nullable
    private String lastState;
    private long lastSnapshotTick = Long.MIN_VALUE;

    MedievalSoldierLadderDebug(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
    }

    boolean enabled() {
        return MedievalMachineryConfig.get().ladderDebugEnabled;
    }

    void state(String state, @Nullable class_2338 column, @Nullable class_2338 point, String detail) {
        if (!this.enabled()) {
            return;
        }
        String key = state + "|" + String.valueOf(column) + "|" + String.valueOf(point) + "|" + detail;
        if (key.equals(this.lastState)) {
            return;
        }
        this.lastState = key;
        this.log(state, column, point, detail);
        this.mark(column, point);
    }

    void event(String event, @Nullable class_2338 column, @Nullable class_2338 point, String detail) {
        if (!this.enabled()) {
            return;
        }
        this.log(event, column, point, detail);
        this.mark(column, point);
    }

    void snapshot(@Nullable class_2338 column, @Nullable class_2338 point, String detail) {
        if (!this.enabled()) {
            return;
        }
        long now = this.soldier.method_37908().method_8510();
        if (now - this.lastSnapshotTick < 20L) {
            return;
        }
        this.lastSnapshotTick = now;
        this.log("SNAPSHOT", column, point, detail);
        this.mark(column, point);
    }

    void clearState() {
        this.lastState = null;
    }

    private void log(String event, @Nullable class_2338 column, @Nullable class_2338 point, String detail) {
        MedievalMachineryCore.LOGGER.info("[LADDER] npc={} id={} tick={} event={} pos=({},{},{}) column={} point={} {}", new Object[]{this.soldier.method_5845(), this.soldier.method_5628(), this.soldier.method_37908().method_8510(), event, MedievalSoldierLadderDebug.fmt(this.soldier.method_23317()), MedievalSoldierLadderDebug.fmt(this.soldier.method_23318()), MedievalSoldierLadderDebug.fmt(this.soldier.method_23321()), column, point, detail});
    }

    private void mark(@Nullable class_2338 column, @Nullable class_2338 point) {
        class_1937 class_19372 = this.soldier.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 world = (class_3218)class_19372;
        if (column != null) {
            world.method_14199((class_2394)class_2398.field_11207, (double)column.method_10263() + 0.5, (double)column.method_10264() + 0.55, (double)column.method_10260() + 0.5, 1, 0.03, 0.03, 0.03, 0.0);
        }
        if (point != null) {
            world.method_14199((class_2394)class_2398.field_11211, (double)point.method_10263() + 0.5, (double)point.method_10264() + 0.25, (double)point.method_10260() + 0.5, 1, 0.03, 0.03, 0.03, 0.0);
        }
    }

    private static String fmt(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }
}

