/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;

final class MedievalSoldierCommandMovementPolicy {
    private MedievalSoldierCommandMovementPolicy() {
    }

    static double moveArrivalRadiusSq(MedievalSoldierEntity soldier) {
        return soldier != null && soldier.getFormation() == NpcFormation.FREE ? 10.0 : 1.0;
    }

    static double moveArrivalVerticalTolerance(MedievalSoldierEntity soldier) {
        return soldier != null && soldier.getFormation() == NpcFormation.FREE ? 0.65 : 0.55;
    }

    static double holdStartRadiusSq() {
        return 0.3;
    }

    static double holdStopRadiusSq() {
        return 0.2;
    }
}

