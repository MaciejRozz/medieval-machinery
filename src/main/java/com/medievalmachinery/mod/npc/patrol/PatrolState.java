/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 */
package com.medievalmachinery.mod.npc.patrol;

import com.medievalmachinery.mod.npc.patrol.PatrolRoute;
import com.medievalmachinery.mod.npc.patrol.PatrolWaypoint;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2520;

public final class PatrolState {
    private final PatrolRoute route = new PatrolRoute();
    private int nextWaypointIndex;
    private boolean patrolOrderActive;
    private int repathCooldown;

    public PatrolRoute route() {
        return this.route;
    }

    public int nextWaypointIndex() {
        this.normalizeIndex();
        return this.nextWaypointIndex;
    }

    public void setNextWaypointIndex(int index) {
        this.nextWaypointIndex = index;
        this.normalizeIndex();
    }

    public class_2338 nextWaypointPosition() {
        PatrolWaypoint waypoint = this.route.get(this.nextWaypointIndex());
        return waypoint == null ? null : waypoint.position();
    }

    public void markWaypointReached() {
        if (this.route.isEmpty()) {
            this.nextWaypointIndex = 0;
            return;
        }
        this.nextWaypointIndex = (this.nextWaypointIndex() + 1) % this.route.size();
    }

    public void onRouteChanged() {
        this.normalizeIndex();
    }

    public boolean isPatrolOrderActive() {
        return this.patrolOrderActive;
    }

    public void setPatrolOrderActive(boolean active) {
        this.patrolOrderActive = active;
    }

    public void resetRepathCooldown() {
        this.repathCooldown = 0;
    }

    public boolean consumeRepathReady() {
        return this.repathCooldown-- <= 0;
    }

    public void scheduleRepath(int ticks) {
        this.repathCooldown = Math.max(0, ticks);
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        nbt.method_10566("Route", (class_2520)this.route.toNbt());
        nbt.method_10569("NextWaypoint", this.nextWaypointIndex());
        nbt.method_10556("Active", this.patrolOrderActive);
        return nbt;
    }

    public void readNbt(class_2487 nbt) {
        this.route.clear();
        this.nextWaypointIndex = 0;
        this.patrolOrderActive = false;
        if (nbt == null) {
            return;
        }
        if (nbt.method_10573("Route", 10)) {
            this.route.readNbt(nbt.method_10562("Route"));
        }
        if (nbt.method_10573("NextWaypoint", 3)) {
            this.nextWaypointIndex = nbt.method_10550("NextWaypoint");
        }
        this.patrolOrderActive = nbt.method_10577("Active");
        this.normalizeIndex();
    }

    private void normalizeIndex() {
        this.nextWaypointIndex = this.route.isEmpty() ? 0 : Math.floorMod(this.nextWaypointIndex, this.route.size());
    }
}

