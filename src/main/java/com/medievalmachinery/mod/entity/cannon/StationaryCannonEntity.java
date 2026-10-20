/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1299
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1792
 *  net.minecraft.class_1937
 *  net.minecraft.class_243
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_3222
 *  net.minecraft.class_3532
 */
package com.medievalmachinery.mod.entity.cannon;

import com.medievalmachinery.mod.entity.cannon.AbstractCannonEntity;
import com.medievalmachinery.mod.machine.core.MachineAimSolution;
import com.medievalmachinery.mod.registry.ModItems;
import com.medievalmachinery.mod.screen.machine.cannon.StationaryCannonScreenHandler;
import java.util.UUID;
import net.minecraft.class_1263;
import net.minecraft.class_1299;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1792;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_3222;
import net.minecraft.class_3532;

public final class StationaryCannonEntity
extends AbstractCannonEntity {
    public StationaryCannonEntity(class_1299<? extends AbstractCannonEntity> type, class_1937 world) {
        super(type, world);
        this.method_49477(0.0f);
    }

    @Override
    public void applyOperatorMovement(UUID operator, float forwardInput, float sidewaysInput) {
    }

    @Override
    public MachineAimSolution medievalMachinery$solveNpcAimAt(class_243 aimPoint, long stableTargetKey, float spreadMultiplier) {
        if (aimPoint == null) {
            return MachineAimSolution.INVALID;
        }
        class_243 delta = aimPoint.method_1020(this.getMuzzlePosition());
        float targetYaw = class_3532.method_15393((float)((float)Math.toDegrees(Math.atan2(-delta.field_1352, delta.field_1350))));
        float yawFromBase = Math.abs(class_3532.method_15393((float)(targetYaw - this.getBaseYaw())));
        if (yawFromBase > 90.0f) {
            return MachineAimSolution.INVALID;
        }
        return super.medievalMachinery$solveNpcAimAt(aimPoint, stableTargetKey, spreadMultiplier);
    }

    @Override
    protected void applyAimTarget(float absoluteYaw, float pitch) {
        float base = this.getBaseYaw();
        float offset = class_3532.method_15363((float)class_3532.method_15393((float)(absoluteYaw - base)), (float)-90.0f, (float)90.0f);
        this.field_5982 = this.method_36454();
        this.method_36456(class_3532.method_15393((float)(base + offset)));
        this.setBarrelPitch(pitch);
    }

    @Override
    public class_243 getMuzzlePosition() {
        return this.method_19538().method_1019(this.getBarrelUpDirection().method_1021(0.75)).method_1019(this.getBarrelDirection().method_1021(1.125)).method_1019(this.getBarrelUpDirection().method_1021(0.0625));
    }

    @Override
    protected class_243 getFusePosition() {
        return this.method_19538().method_1019(this.getBarrelUpDirection().method_1021(1.04)).method_1019(this.getBarrelDirection().method_1021(-0.06));
    }

    @Override
    protected class_1792 getCannonItem() {
        return ModItems.STATIONARY_CANNON;
    }

    @Override
    public class_2561 method_5476() {
        return class_2561.method_43471((String)"entity.medieval-machinery.stationary_cannon");
    }

    @Override
    public void writeScreenOpeningData(class_3222 player, class_2540 buf) {
        buf.method_10804(this.method_5628());
    }

    @Override
    public class_1703 createMenu(int syncId, class_1661 playerInventory, class_1657 player) {
        return new StationaryCannonScreenHandler(syncId, playerInventory, (class_1263)this, this.method_5628());
    }

    @Override
    public boolean medievalMachinery$isMobile() {
        return false;
    }
}

