/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1277
 *  net.minecraft.class_1657
 *  net.minecraft.class_1799
 *  net.minecraft.class_2338
 *  net.minecraft.class_3222
 */
package com.medievalmachinery.mod.block.garrison;

import com.medievalmachinery.mod.npc.core.NpcRole;
import net.minecraft.class_1277;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_3222;

public interface WatchpostMenuAccess {
    public class_2338 getWatchpostPos();

    public String getCastleName();

    public class_1277 getCaptureInventory();

    public class_1277 getOrderInventory();

    public boolean isCaptureActive();

    public boolean canPlayerUseSigil(class_1657 var1, class_1799 var2);

    public boolean startCapture(class_3222 var1);

    public boolean recruit(class_3222 var1, NpcRole var2);

    public boolean hasValidStampedSigil();

    public int getTotalVillagerCount();

    public int getEmployedVillagerCount();

    public int getAvailableVillagerCount();

    public int getSoldierCount();

    public int getStoredTaxEmeralds();

    public int getLastKnownTaxVillagerCount();

    public void returnCaptureSigil(class_3222 var1);

    public boolean canOpenCapture(class_3222 var1);

    public boolean canPlayerAdminister(class_1657 var1);

    public int getMinimumVillagers();
}

