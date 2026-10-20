/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.transfer;

public final class CastleTransferPolicy {
    public static final double GATHER_RADIUS = 5.0;
    public static final int DEPARTURE_RING_MIN = 20;
    public static final int DEPARTURE_RING_MAX = 28;
    public static final double DEPARTURE_REACHED_RADIUS = 3.0;
    public static final int EXIT_RETRY_TICKS = 40;
    public static final int ARRIVAL_RING_MIN = 20;
    public static final int ARRIVAL_RING_MAX = 28;
    public static final double ARRIVAL_REACHED_RADIUS = 4.0;
    public static final double GROUP_STRAGGLER_DISTANCE = 10.0;
    public static final double TRAVEL_TICKS_PER_BLOCK = 2.0;
    public static final int MIN_TRAVEL_TICKS = 200;
    public static final int MAX_TRAVEL_TICKS = 12000;

    private CastleTransferPolicy() {
    }

    public static long travelTicks(double distanceBlocks) {
        long calculated = Math.round(Math.max(0.0, distanceBlocks) * 2.0);
        return Math.max(200L, Math.min(12000L, calculated));
    }
}

