/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.machine.core;

import com.medievalmachinery.mod.machine.core.MachineTargetCategory;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import org.jetbrains.annotations.Nullable;

public record MachineCombatTarget(@Nullable UUID entityId, @Nullable class_2338 blockPos, MachineTargetCategory category, class_243 aimPoint, long stableKey) {
    public static MachineCombatTarget entity(class_1297 entity, MachineTargetCategory category, class_243 aimPoint) {
        UUID id = entity.method_5667();
        return new MachineCombatTarget(id, null, category, aimPoint, id.getMostSignificantBits() ^ id.getLeastSignificantBits());
    }

    public static MachineCombatTarget block(class_2338 pos, MachineTargetCategory category, class_243 aimPoint) {
        long key = pos.method_10063();
        return new MachineCombatTarget(null, pos.method_10062(), category, aimPoint, key);
    }

    public static MachineCombatTarget point(class_243 aimPoint) {
        long x = Double.doubleToLongBits(aimPoint.field_1352);
        long y = Double.doubleToLongBits(aimPoint.field_1351);
        long z = Double.doubleToLongBits(aimPoint.field_1350);
        return new MachineCombatTarget(null, null, MachineTargetCategory.FORTIFICATIONS, aimPoint, x ^ Long.rotateLeft(y, 21) ^ Long.rotateLeft(z, 42));
    }

    public boolean isEntity() {
        return this.entityId != null;
    }

    public boolean isBlock() {
        return this.blockPos != null;
    }

    public boolean isPoint() {
        return this.entityId == null && this.blockPos == null && this.aimPoint != null;
    }
}

