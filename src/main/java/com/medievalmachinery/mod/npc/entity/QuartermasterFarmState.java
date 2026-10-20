/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import org.jetbrains.annotations.Nullable;

final class QuartermasterFarmState {
    static final int MAX_FIELDS = 3;
    @Nullable
    class_2338 composter;
    final List<class_2338> fieldCenters = new ArrayList<class_2338>(3);

    QuartermasterFarmState() {
    }

    boolean addField(class_2338 center) {
        if (center == null || this.fieldCenters.size() >= 3) {
            return false;
        }
        class_2338 immutable = center.method_10062();
        if (this.fieldCenters.contains(immutable)) {
            return false;
        }
        this.fieldCenters.add(immutable);
        return true;
    }

    void clearFields() {
        this.fieldCenters.clear();
    }

    boolean removeField(int index) {
        if (index < 0 || index >= this.fieldCenters.size()) {
            return false;
        }
        this.fieldCenters.remove(index);
        return true;
    }

    class_2487 toNbt() {
        class_2487 out = new class_2487();
        if (this.composter != null) {
            out.method_10544("Composter", this.composter.method_10063());
        }
        class_2499 fields = new class_2499();
        for (class_2338 pos : this.fieldCenters) {
            class_2487 f = new class_2487();
            f.method_10544("Pos", pos.method_10063());
            fields.add((Object)f);
        }
        out.method_10566("Fields", (class_2520)fields);
        return out;
    }

    void readNbt(class_2487 in) {
        this.composter = in.method_10573("Composter", 4) ? class_2338.method_10092((long)in.method_10537("Composter")) : null;
        this.fieldCenters.clear();
        if (in.method_10573("Fields", 9)) {
            class_2499 fields = in.method_10554("Fields", 10);
            for (int i = 0; i < fields.size() && this.fieldCenters.size() < 3; ++i) {
                class_2487 f = fields.method_10602(i);
                if (!f.method_10573("Pos", 4)) continue;
                this.addField(class_2338.method_10092((long)f.method_10537("Pos")));
            }
        }
    }
}

