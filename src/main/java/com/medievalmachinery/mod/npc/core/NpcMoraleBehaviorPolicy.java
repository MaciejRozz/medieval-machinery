/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

public final class NpcMoraleBehaviorPolicy {
    public static final float BROKEN_THRESHOLD = 15.0f;
    public static final float SHAKEN_THRESHOLD = 30.0f;
    public static final float RALLY_THRESHOLD = 30.0f;

    private NpcMoraleBehaviorPolicy() {
    }

    public static boolean mayAcquireProactiveTarget(float morale) {
        return morale >= 15.0f;
    }

    public static boolean mayJoinGroupDefense(float morale, boolean leadershipNearby) {
        return morale >= 30.0f || leadershipNearby;
    }

    public static boolean shouldFallback(float morale, boolean commandedTarget, boolean leadershipNearby) {
        return !commandedTarget && !leadershipNearby && morale < 15.0f;
    }

    public static boolean hasRallied(float morale) {
        return morale >= 30.0f;
    }

    public static AttributeModifiers attributeModifiers(float morale) {
        if (morale < 20.0f) {
            return new AttributeModifiers(-0.2, -0.25);
        }
        if (morale < 40.0f) {
            return new AttributeModifiers(-0.1, -0.1);
        }
        if (morale >= 90.0f) {
            return new AttributeModifiers(0.05, 0.05);
        }
        return new AttributeModifiers(0.0, 0.0);
    }

    public static double pursuitSpeedMultiplier(float morale, boolean leadershipNearby) {
        if (leadershipNearby) {
            return 1.0;
        }
        if (morale < 15.0f) {
            return 0.8;
        }
        if (morale < 30.0f) {
            return 0.9;
        }
        return 1.0;
    }

    public static int rangedPreparationDelayTicks(float morale, boolean leadershipNearby) {
        if (leadershipNearby || morale >= 30.0f) {
            return 0;
        }
        if (morale < 15.0f) {
            return 6;
        }
        return 3;
    }

    public static float leadershipRallyFloor(float morale, boolean captainNearby, boolean generalNearby) {
        if (generalNearby && morale < 30.0f) {
            return Math.min(30.0f, morale + 4.0f);
        }
        if (captainNearby && morale < 30.0f) {
            return Math.min(30.0f, morale + 2.0f);
        }
        return morale;
    }

    public record AttributeModifiers(double speedMultiplier, double damageMultiplier) {
    }
}

