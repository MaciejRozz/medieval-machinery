/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_5321
 */
package com.medievalmachinery.mod.npc.navigation;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_5321;

public final class MedievalSoldierCrowdCoordinator {
    private static final Map<Key, Reservation> RESERVATIONS = new HashMap<Key, Reservation>();
    private static final int CLIMB_MIN_VERTICAL_SPACING = 2;

    private MedievalSoldierCrowdCoordinator() {
    }

    public static synchronized boolean tryReserve(class_5321<class_1937> world, class_2338 pos, Lane lane, UUID soldier, long now, int ttlTicks) {
        MedievalSoldierCrowdCoordinator.cleanup(world, now);
        if (lane == Lane.CLIMB) {
            RESERVATIONS.entrySet().removeIf(entry -> ((Key)entry.getKey()).lane == Lane.CLIMB && ((Key)entry.getKey()).world.equals((Object)world) && ((Reservation)entry.getValue()).owner.equals(soldier));
            for (Map.Entry<Key, Reservation> entry2 : RESERVATIONS.entrySet()) {
                Key key = entry2.getKey();
                if (key.lane != Lane.CLIMB || !key.world.equals(world) || key.pos.method_10263() != pos.method_10263() || key.pos.method_10260() != pos.method_10260() || Math.abs(key.pos.method_10264() - pos.method_10264()) >= 2) continue;
                return false;
            }
            RESERVATIONS.put(new Key(world, pos.method_10062(), lane), new Reservation(soldier, now + (long)Math.max(4, ttlTicks)));
            return true;
        }
        Key key = new Key(world, pos.method_10062(), lane);
        Reservation current = RESERVATIONS.get(key);
        if (current == null || current.owner.equals(soldier)) {
            RESERVATIONS.put(key, new Reservation(soldier, now + (long)Math.max(4, ttlTicks)));
            return true;
        }
        return false;
    }

    public static synchronized boolean isReservedByOther(class_5321<class_1937> world, class_2338 pos, Lane lane, UUID soldier, long now) {
        MedievalSoldierCrowdCoordinator.cleanup(world, now);
        if (lane == Lane.CLIMB) {
            for (Map.Entry<Key, Reservation> entry : RESERVATIONS.entrySet()) {
                Key key = entry.getKey();
                Reservation reservation = entry.getValue();
                if (key.lane != Lane.CLIMB || !key.world.equals(world) || reservation.owner.equals(soldier) || key.pos.method_10263() != pos.method_10263() || key.pos.method_10260() != pos.method_10260() || Math.abs(key.pos.method_10264() - pos.method_10264()) >= 2) continue;
                return true;
            }
            return false;
        }
        Reservation current = RESERVATIONS.get(new Key(world, pos.method_10062(), lane));
        return current != null && !current.owner.equals(soldier);
    }

    public static synchronized boolean tryReserve(class_5321<class_1937> world, class_2338 pos, UUID soldier, long now, int ttlTicks) {
        return MedievalSoldierCrowdCoordinator.tryReserve(world, pos, Lane.CHOKEPOINT, soldier, now, ttlTicks);
    }

    public static synchronized void releaseOwned(UUID soldier, Lane lane) {
        RESERVATIONS.entrySet().removeIf(entry -> ((Key)entry.getKey()).lane == lane && ((Reservation)entry.getValue()).owner.equals(soldier));
    }

    public static synchronized void releaseOwned(UUID soldier) {
        RESERVATIONS.entrySet().removeIf(entry -> ((Reservation)entry.getValue()).owner.equals(soldier));
    }

    private static void cleanup(class_5321<class_1937> world, long now) {
        Iterator<Map.Entry<Key, Reservation>> it = RESERVATIONS.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Key, Reservation> entry = it.next();
            if (!entry.getKey().world.equals(world) || entry.getValue().expiresAt > now) continue;
            it.remove();
        }
    }

    public static synchronized void clearAll() {
        RESERVATIONS.clear();
    }

    public static int groupRepathDelay(MedievalSoldierEntity soldier, int baseTicks) {
        if (soldier == null) {
            return Math.max(1, baseTicks);
        }
        return Math.max(1, baseTicks + Math.floorMod(soldier.method_5667().hashCode(), 4));
    }

    public static enum Lane {
        PASSAGE,
        CHOKEPOINT,
        CLIMB,
        CLIMB_EXIT;

    }

    private record Key(class_5321<class_1937> world, class_2338 pos, Lane lane) {
    }

    private record Reservation(UUID owner, long expiresAt) {
    }
}

