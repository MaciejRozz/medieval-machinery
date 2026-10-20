/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1799
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.block.garrison;

import com.medievalmachinery.mod.block.garrison.WatchpostMenuAccess;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_3222;

public interface WatchpostBlockAccess
extends WatchpostMenuAccess {
    public void ensureAssigned(class_3222 var1);

    public boolean hasGeneralProtection();

    public boolean restoreRelocation(class_3222 var1, class_1799 var2);

    public void removeCastleAssignment();

    @Override
    public boolean canPlayerAdminister(class_1657 var1);

    @Override
    public boolean canOpenCapture(class_3222 var1);
}

