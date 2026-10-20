/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1304
 *  net.minecraft.class_1799
 */
package com.medievalmachinery.mod.npc.entity;

import net.minecraft.class_1304;
import net.minecraft.class_1799;

interface MedievalSoldierEquipmentContext {
    public class_1799 equipmentStack(class_1304 var1);

    public void setEquipmentStack(class_1304 var1, class_1799 var2);

    public boolean equipmentEvaluationInProgress();

    public void setEquipmentEvaluationInProgress(boolean var1);
}

