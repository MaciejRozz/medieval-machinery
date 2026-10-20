/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 */
package com.medievalmachinery.mod.npc.entity;

import net.minecraft.class_1792;

final class MedievalSoldierMaintenanceState {
    boolean equipmentDirty = true;
    boolean applyingEquipmentEvaluation;
    class_1792 lastObservedMainHandItem;
    boolean registryDirty = true;
    boolean organizationRemovalCleaned;
    boolean organizationDirty = true;
    boolean cachedHasFood;
    boolean automaticWeaponReplacementNeeded;
    boolean inventoryCacheDirty = true;

    MedievalSoldierMaintenanceState() {
    }
}

