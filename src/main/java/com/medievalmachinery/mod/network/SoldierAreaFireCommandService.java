/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.List;
import net.minecraft.class_243;

final class SoldierAreaFireCommandService {
    private SoldierAreaFireCommandService() {
    }

    static int start(List<MedievalSoldierEntity> soldiers, class_243 center, double radius) {
        if (soldiers == null || center == null) {
            return 0;
        }
        int accepted = 0;
        for (MedievalSoldierEntity soldier : soldiers) {
            if (soldier == null || !soldier.fireStrategicAreaVolley(center, radius)) continue;
            ++accepted;
        }
        return accepted;
    }
}

