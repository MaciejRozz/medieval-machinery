/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1352
 *  net.minecraft.class_1352$class_4134
 *  net.minecraft.class_1764
 *  net.minecraft.class_243
 *  net.minecraft.class_3532
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcMoraleBehaviorPolicy;
import com.medievalmachinery.mod.npc.core.NpcRangedVisualState;
import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierRangedFirePolicy;
import java.util.EnumSet;
import net.minecraft.class_1268;
import net.minecraft.class_1352;
import net.minecraft.class_1764;
import net.minecraft.class_243;
import net.minecraft.class_3532;

final class StrategicAreaFireGoal
extends class_1352 {
    private final MedievalSoldierEntity soldier;
    private int attackCooldown;
    private int preparationTicks;
    private int aimTicks;
    private boolean preparingShot;
    private boolean crossbowShot;
    private class_243 aimPoint;

    StrategicAreaFireGoal(MedievalSoldierEntity soldier) {
        this.soldier = soldier;
        this.method_6265(EnumSet.of(class_1352.class_4134.field_18405, class_1352.class_4134.field_18406));
    }

    public boolean method_6264() {
        return this.soldier.isStrategicAreaFireActive() && !this.soldier.isHoldFire() && this.soldier.method_5968() == null && !this.soldier.isActivelyMountedRole() && this.soldier.getShieldMode() != NpcShieldMode.RAISED && !this.soldier.isUsingShield() && this.soldier.isHoldingRangedWeapon();
    }

    public boolean method_6266() {
        return this.method_6264();
    }

    public void method_6269() {
        this.attackCooldown = Math.floorMod(this.soldier.method_5628(), 4);
        this.preparationTicks = 0;
        this.aimTicks = 0;
        this.preparingShot = false;
        this.crossbowShot = false;
        this.aimPoint = null;
        this.soldier.stopAdaptiveNavigation();
    }

    public void method_6270() {
        this.soldier.stopRangedWeaponUse();
        if (this.preparingShot) {
            this.soldier.clearRangedVisual();
        }
        this.preparingShot = false;
        this.preparationTicks = 0;
        this.aimTicks = 0;
        this.aimPoint = null;
    }

    public void method_6268() {
        if (this.soldier.method_5968() != null) {
            return;
        }
        this.soldier.method_5728(false);
        this.soldier.stopAdaptiveNavigation();
        if (this.preparingShot) {
            this.tickPreparation();
            return;
        }
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
            return;
        }
        this.aimPoint = this.soldier.chooseStrategicAreaAimPoint();
        this.soldier.method_5988().method_6230(this.aimPoint.field_1352, this.aimPoint.field_1351, this.aimPoint.field_1350, 30.0f, 30.0f);
        if (this.soldier.hasFriendlyInLineOfFire(this.aimPoint)) {
            this.attackCooldown = MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(this.soldier);
            this.aimPoint = null;
            return;
        }
        this.beginPreparation();
    }

    private void beginPreparation() {
        this.preparingShot = true;
        this.preparationTicks = NpcMoraleBehaviorPolicy.rangedPreparationDelayTicks(this.soldier.getMorale(), this.soldier.hasNearbyMoraleLeader());
        this.aimTicks = 0;
        this.crossbowShot = this.soldier.method_6047().method_7909() instanceof class_1764;
        if (this.crossbowShot) {
            this.soldier.beginRangedVisual(NpcRangedVisualState.LOAD, MedievalSoldierRangedFirePolicy.crossbowLoadTicks(false) + MedievalSoldierRangedFirePolicy.crossbowChargeTicks(false) + MedievalSoldierRangedFirePolicy.crossbowAimTicks(false) + MedievalSoldierRangedFirePolicy.releaseHoldTicks(true, false));
        } else {
            this.soldier.beginRangedVisual(NpcRangedVisualState.PREPARE, MedievalSoldierRangedFirePolicy.bowDrawTicks(false) + MedievalSoldierRangedFirePolicy.releaseHoldTicks(false, false));
        }
        if (!this.crossbowShot) {
            this.soldier.method_6019(class_1268.field_5808);
        }
    }

    private void tickPreparation() {
        if (this.aimPoint == null) {
            this.cancelPreparation();
            this.attackCooldown = 4;
            return;
        }
        this.soldier.method_5988().method_6230(this.aimPoint.field_1352, this.aimPoint.field_1351, this.aimPoint.field_1350, 30.0f, 30.0f);
        ++this.preparationTicks;
        if (this.crossbowShot) {
            int loadTicks = MedievalSoldierRangedFirePolicy.crossbowLoadTicks(false);
            int chargeDone = loadTicks + MedievalSoldierRangedFirePolicy.crossbowChargeTicks(false);
            if (this.preparationTicks == loadTicks) {
                this.soldier.markCrossbowBoltInsertedForVisual();
                this.soldier.setRangedVisualState(NpcRangedVisualState.PREPARE);
                this.soldier.method_6019(class_1268.field_5808);
            }
            if (this.preparationTicks < chargeDone) {
                return;
            }
            if (this.soldier.method_6115()) {
                this.soldier.method_6075();
            }
            if (this.aimTicks == 0) {
                this.soldier.setRangedVisualState(NpcRangedVisualState.AIM);
            }
            ++this.aimTicks;
            if (this.aimTicks < MedievalSoldierRangedFirePolicy.crossbowAimTicks(false)) {
                return;
            }
            if (this.soldier.hasFriendlyInLineOfFire(this.aimPoint)) {
                this.cancelPreparation();
                this.attackCooldown = MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(this.soldier);
                return;
            }
            this.soldier.holdRangedReleaseVisual(true);
            this.soldier.fireStandardArrowAtPoint(this.aimPoint);
            this.soldier.markCrossbowUnloadedAfterShot();
            this.finishShot(this.soldier.applyLevelRangedCooldown(MedievalSoldierRangedFirePolicy.postShotCooldown(true, false)));
            return;
        }
        if (this.preparationTicks < MedievalSoldierRangedFirePolicy.bowDrawTicks(false)) {
            return;
        }
        this.soldier.stopRangedWeaponUse();
        if (this.soldier.hasFriendlyInLineOfFire(this.aimPoint)) {
            this.cancelPreparation();
            this.attackCooldown = MedievalSoldierRangedFirePolicy.blockedLaneRetryTicks(this.soldier);
            return;
        }
        this.soldier.holdRangedReleaseVisual(false);
        this.soldier.fireStandardArrowAtPoint(this.aimPoint);
        class_243 center = this.soldier.getStrategicAreaFireCenter();
        double distance = center == null ? 32.0 : Math.sqrt(this.soldier.method_5707(center));
        int rangedCooldown = class_3532.method_15386((float)(class_3532.method_15363((float)((float)(distance / 40.0)), (float)0.0f, (float)1.0f) * 10.0f + 10.0f));
        this.finishShot(this.soldier.applyLevelRangedCooldown(rangedCooldown));
    }

    private void finishShot(int cooldown) {
        this.soldier.stopRangedWeaponUse();
        this.preparingShot = false;
        this.preparationTicks = 0;
        this.aimTicks = 0;
        this.aimPoint = null;
        this.attackCooldown = Math.max(1, cooldown);
    }

    private void cancelPreparation() {
        this.soldier.stopRangedWeaponUse();
        this.soldier.clearRangedVisual();
        this.preparingShot = false;
        this.preparationTicks = 0;
        this.aimTicks = 0;
        this.aimPoint = null;
    }
}

