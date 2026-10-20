/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;

public final class NpcMachineCrewReadinessPolicy {
    private NpcMachineCrewReadinessPolicy() {
    }

    public static boolean canWork(MedievalSoldierEntity npc, MachineCrewRole role) {
        if (npc == null || role == null || !npc.method_5805() || npc.method_31481()) {
            return false;
        }
        if (npc.method_6032() <= Math.max(2.0f, npc.method_6063() * 0.15f)) {
            return false;
        }
        return !npc.method_6113();
    }
}

