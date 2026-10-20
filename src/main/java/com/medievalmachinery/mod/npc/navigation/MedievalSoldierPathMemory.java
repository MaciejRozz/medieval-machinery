/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 */
package com.medievalmachinery.mod.npc.navigation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.class_2338;

public final class MedievalSoldierPathMemory {
    private final Map<class_2338, Long> blockedUntil = new HashMap<class_2338, Long>();

    public void markFailed(class_2338 pos, long now, int ttlTicks) {
        if (pos != null) {
            this.blockedUntil.put(pos.method_10062(), now + (long)Math.max(10, ttlTicks));
        }
    }

    public boolean isTemporarilyBlocked(class_2338 pos, long now) {
        this.cleanup(now);
        Long until = this.blockedUntil.get(pos);
        return until != null && until > now;
    }

    public void clear() {
        this.blockedUntil.clear();
    }

    private void cleanup(long now) {
        Iterator<Map.Entry<class_2338, Long>> it = this.blockedUntil.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue() > now) continue;
            it.remove();
        }
    }
}

