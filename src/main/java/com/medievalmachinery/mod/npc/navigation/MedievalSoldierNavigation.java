/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 */
package com.medievalmachinery.mod.npc.navigation;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1297;

public final class MedievalSoldierNavigation {
    private final MedievalSoldierEntity soldier;

    public MedievalSoldierNavigation(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
    }

    public boolean isIdle() {
        return this.soldier.method_5942().method_6357();
    }

    public void stop() {
        this.soldier.method_5942().method_6340();
    }

    public void moveTo(class_1297 target, double speed) {
        if (target != null) {
            this.soldier.method_5942().method_6335(target, speed);
        }
    }

    public void moveTo(double x, double y, double z, double speed) {
        this.soldier.method_5942().method_6337(x, y, z, speed);
    }
}

