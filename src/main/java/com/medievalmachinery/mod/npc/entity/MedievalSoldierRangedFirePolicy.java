/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;

final class MedievalSoldierRangedFirePolicy {
    private static final int STABLE_SIGHT_TICKS = 2;
    private static final int AUTOMATIC_TARGET_LOST_SIGHT_TICKS = 8;
    private static final int AUTO_PREPARE_LOST_SIGHT_GRACE = 2;
    private static final int AUTO_AIM_LOST_SIGHT_GRACE = 1;
    private static final int COMMAND_PREPARE_LOST_SIGHT_GRACE = 6;
    private static final int COMMAND_AIM_LOST_SIGHT_GRACE = 4;

    private MedievalSoldierRangedFirePolicy() {
    }

    static int bowDrawTicks(boolean mounted) {
        return 20;
    }

    static int crossbowLoadTicks(boolean mounted) {
        return mounted ? 7 : 8;
    }

    static int crossbowChargeTicks(boolean mounted) {
        return mounted ? 32 : 40;
    }

    static int crossbowAimTicks(boolean mounted) {
        return 3;
    }

    static int releaseHoldTicks(boolean crossbow, boolean mounted) {
        if (crossbow) {
            return mounted ? 9 : 8;
        }
        return mounted ? 6 : 5;
    }

    static int postShotCooldown(boolean crossbow, boolean mounted) {
        if (crossbow) {
            return mounted ? 26 : 28;
        }
        return mounted ? 18 : 20;
    }

    static boolean mayBeginShot(MedievalSoldierEntity soldier, int visibleTicks) {
        int phase = soldier == null ? 0 : Math.floorMod(soldier.method_5667().hashCode(), 2);
        return visibleTicks >= 2 + phase;
    }

    static int automaticLostSightTicks() {
        return 8;
    }

    static boolean shouldCancelPreparation(boolean canSee, int unseenTicks, boolean aiming, boolean commandedTarget) {
        if (canSee) {
            return false;
        }
        int grace = commandedTarget ? (aiming ? 4 : 6) : (aiming ? 1 : 2);
        return unseenTicks > grace;
    }

    static int blockedLaneRetryTicks(MedievalSoldierEntity soldier) {
        return 8 + Math.floorMod(soldier.method_5667().hashCode(), 5);
    }
}

