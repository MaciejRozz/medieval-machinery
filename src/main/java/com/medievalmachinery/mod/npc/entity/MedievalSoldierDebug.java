/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_2338
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.MedievalMachineryCore;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.Locale;
import net.minecraft.class_1309;
import net.minecraft.class_2338;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierDebug {
    private final MedievalSoldierEntity soldier;
    private String lastSnapshot = "";
    private long nextVerboseTick;

    MedievalSoldierDebug(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
    }

    private boolean enabled() {
        return MedievalMachineryConfig.get().npcDebugEnabled;
    }

    void tick() {
        if (!this.enabled()) {
            return;
        }
        class_1309 target = this.soldier.method_5968();
        String owner = this.movementOwner(target);
        String snapshot = "order=" + String.valueOf((Object)this.soldier.getOrderType()) + " move=" + owner + " target=" + String.valueOf(target == null ? "none" : Integer.valueOf(target.method_5628())) + " stance=" + String.valueOf((Object)this.soldier.getCombatStance()) + " holdFire=" + this.soldier.isHoldFire() + " shield=" + String.valueOf((Object)this.soldier.getShieldMode()) + " nav=" + (this.soldier.method_5942().method_6357() ? "IDLE" : "PATHING");
        if (!snapshot.equals(this.lastSnapshot)) {
            this.lastSnapshot = snapshot;
            this.log("STATE", null, snapshot);
        }
        if (MedievalMachineryConfig.get().verboseNavigationDebugEnabled && this.soldier.method_37908().method_8510() >= this.nextVerboseTick) {
            this.nextVerboseTick = this.soldier.method_37908().method_8510() + 20L;
            this.log("NAV_SNAPSHOT", null, snapshot);
        }
    }

    void event(String event, @Nullable class_2338 point, String detail) {
        if (!this.enabled()) {
            return;
        }
        this.log(event, point, detail);
    }

    private String movementOwner(@Nullable class_1309 target) {
        if (this.soldier.machineAssignmentOwnsPersonalCombat()) {
            return "MACHINE";
        }
        if (this.soldier.isActivelyMountedRole()) {
            return "MOUNT";
        }
        if (this.soldier.hasSupplyMovementOverride()) {
            return "SUPPLY";
        }
        if (this.soldier.isGeneralBodyguard()) {
            return "BODYGUARD";
        }
        if (target != null && target.method_5805()) {
            return "COMBAT";
        }
        return switch (this.soldier.getOrderType()) {
            case NpcOrderType.PATROL -> "PATROL";
            case NpcOrderType.FOLLOW -> "FOLLOW";
            case NpcOrderType.HOLD_POSITION, NpcOrderType.OBSERVATION_POINT -> "HOLD";
            case NpcOrderType.MOVE_TO_POSITION -> "MOVE_TO";
            case NpcOrderType.RETURN_TO_GARRISON -> "GARRISON";
            default -> this.soldier.getOrderType().name();
        };
    }

    private void log(String event, @Nullable class_2338 point, String detail) {
        MedievalMachineryCore.LOGGER.info("[MM-NPC] npc={} uuid={} tick={} event={} pos=({},{},{}) point={} {}", new Object[]{this.soldier.method_5628(), this.soldier.method_5667(), this.soldier.method_37908().method_8510(), event, String.format(Locale.ROOT, "%.2f", this.soldier.method_23317()), String.format(Locale.ROOT, "%.2f", this.soldier.method_23318()), String.format(Locale.ROOT, "%.2f", this.soldier.method_23321()), point == null ? "-" : point.method_23854(), detail == null ? "" : detail});
    }
}

