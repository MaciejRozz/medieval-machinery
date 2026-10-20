/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 */
package com.medievalmachinery.mod.npc.patrol;

import com.medievalmachinery.mod.npc.patrol.PatrolWaypoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;

public final class PatrolRoute {
    public static final int MAX_WAYPOINTS = 32;
    public static final double MAX_DISTANCE_FROM_SOLDIER = 128.0;
    public static final double MAX_SEGMENT_DISTANCE = 64.0;
    private final List<PatrolWaypoint> waypoints = new ArrayList<PatrolWaypoint>();

    public int size() {
        return this.waypoints.size();
    }

    public boolean isEmpty() {
        return this.waypoints.isEmpty();
    }

    public PatrolWaypoint get(int index) {
        if (this.waypoints.isEmpty()) {
            return null;
        }
        return this.waypoints.get(Math.floorMod(index, this.waypoints.size()));
    }

    public List<PatrolWaypoint> waypoints() {
        return Collections.unmodifiableList(this.waypoints);
    }

    public boolean contains(class_2338 pos) {
        if (pos == null) {
            return false;
        }
        for (PatrolWaypoint waypoint : this.waypoints) {
            if (!waypoint.position().equals((Object)pos)) continue;
            return true;
        }
        return false;
    }

    public boolean add(class_2338 pos) {
        if (pos == null || this.waypoints.size() >= 32 || this.contains(pos)) {
            return false;
        }
        this.waypoints.add(new PatrolWaypoint(pos));
        return true;
    }

    public boolean removeLast() {
        if (this.waypoints.isEmpty()) {
            return false;
        }
        this.waypoints.remove(this.waypoints.size() - 1);
        return true;
    }

    public void clear() {
        this.waypoints.clear();
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        class_2499 list = new class_2499();
        for (PatrolWaypoint waypoint : this.waypoints) {
            list.add((Object)waypoint.toNbt());
        }
        nbt.method_10566("Waypoints", (class_2520)list);
        return nbt;
    }

    public void readNbt(class_2487 nbt) {
        this.waypoints.clear();
        if (nbt == null || !nbt.method_10573("Waypoints", 9)) {
            return;
        }
        class_2499 list = nbt.method_10554("Waypoints", 10);
        int count = Math.min(32, list.size());
        for (int i = 0; i < count; ++i) {
            PatrolWaypoint waypoint = PatrolWaypoint.fromNbt(list.method_10602(i));
            if (this.contains(waypoint.position())) continue;
            this.waypoints.add(waypoint);
        }
    }
}

