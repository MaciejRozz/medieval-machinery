/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

public final class NpcLeadershipPolicy {
    public static final double CAPTAIN_RADIUS = 16.0;
    public static final double GENERAL_RADIUS = 24.0;

    private NpcLeadershipPolicy() {
    }

    public static double radius(Strength strength) {
        return switch (strength.ordinal()) {
            case 1 -> 16.0;
            case 2 -> 24.0;
            default -> 0.0;
        };
    }

    public static float moraleRecovery(Strength strength) {
        return switch (strength.ordinal()) {
            case 1 -> 0.5f;
            case 2 -> 1.0f;
            default -> 0.0f;
        };
    }

    public static float rallyAmount(Strength strength) {
        return switch (strength.ordinal()) {
            case 1 -> 2.0f;
            case 2 -> 4.0f;
            default -> 0.0f;
        };
    }

    public static double attackDamageBonus(Strength strength) {
        return switch (strength.ordinal()) {
            case 1 -> 0.025;
            case 2 -> 0.05;
            default -> 0.0;
        };
    }

    public static float rangedInaccuracyMultiplier(Strength strength) {
        return switch (strength.ordinal()) {
            case 1 -> 0.95f;
            case 2 -> 0.9f;
            default -> 1.0f;
        };
    }

    public static double rangedCooldownMultiplier(Strength strength) {
        return switch (strength.ordinal()) {
            case 1 -> 0.975;
            case 2 -> 0.95;
            default -> 1.0;
        };
    }

    public static double formationKnockbackResistanceBonus(Strength strength) {
        return switch (strength.ordinal()) {
            case 1 -> 0.05;
            case 2 -> 0.1;
            default -> 0.0;
        };
    }

    public static double machineCycleTimeMultiplier(Strength strength) {
        return switch (strength.ordinal()) {
            case 1 -> 0.975;
            case 2 -> 0.95;
            default -> 1.0;
        };
    }

    public static float machineAimSpreadMultiplier(Strength strength) {
        return switch (strength.ordinal()) {
            case 1 -> 0.95f;
            case 2 -> 0.9f;
            default -> 1.0f;
        };
    }

    public static boolean steadiesShakenTroops(Strength strength) {
        return strength != Strength.NONE;
    }

    public static enum Strength {
        NONE,
        CAPTAIN,
        GENERAL;

    }
}

