/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1282
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1322
 *  net.minecraft.class_1322$class_1323
 *  net.minecraft.class_1324
 *  net.minecraft.class_1799
 *  net.minecraft.class_243
 *  net.minecraft.class_3532
 *  net.minecraft.class_5134
 *  net.minecraft.class_8103
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcShieldMode;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierShieldPolicy;
import net.minecraft.class_1268;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1322;
import net.minecraft.class_1324;
import net.minecraft.class_1799;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_5134;
import net.minecraft.class_8103;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierShieldControl {
    MedievalSoldierShieldControl() {
    }

    void tickShieldBehaviour(MedievalSoldierEntity soldier) {
        boolean wantsRaised;
        if (!soldier.hasUsableShield()) {
            if (soldier.isUsingShield()) {
                soldier.method_6075();
            }
            soldier.updateShieldMovementModifier(false);
            return;
        }
        class_1309 target = soldier.method_5968();
        switch (soldier.getShieldMode()) {
            default: {
                throw new IncompatibleClassChangeError();
            }
            case RAISED: {
                boolean bl = true;
                break;
            }
            case AUTO: {
                boolean bl = wantsRaised = soldier.shouldAutoRaiseShield(target);
            }
        }
        if (soldier.shieldCombatState().shieldReRaiseTicks > 0 || soldier.shieldEatingState().sourceSlot >= 0) {
            wantsRaised = false;
        }
        if (soldier.shieldCombatState().shieldBlockCooldownTicks > 0) {
            wantsRaised = false;
        }
        if (soldier.shieldCombatState().meleeAttackCooldownTicks > Math.max(3, soldier.getMeleeAttackCooldown() - 7)) {
            wantsRaised = false;
        }
        if (wantsRaised) {
            if (!soldier.isUsingShield()) {
                if (soldier.method_6115()) {
                    soldier.method_6075();
                }
                soldier.method_6019(class_1268.field_5810);
            }
            if (target != null && target.method_5805()) {
                soldier.method_5988().method_6226((class_1297)target, 35.0f, 35.0f);
            }
        } else if (soldier.isUsingShield()) {
            soldier.method_6075();
        }
        soldier.updateShieldMovementModifier(soldier.isUsingShield());
    }

    void updateShieldMovementModifier(MedievalSoldierEntity soldier, boolean shieldActive) {
        if (shieldActive == soldier.shieldModifierState().shieldSpeedPenaltyApplied) {
            return;
        }
        class_1324 speed = soldier.method_5996(class_5134.field_23719);
        if (speed == null) {
            return;
        }
        class_1322 old = speed.method_6199(soldier.shieldSHIELD_SPEED_MODIFIER_ID());
        if (old != null) {
            speed.method_6200(soldier.shieldSHIELD_SPEED_MODIFIER_ID());
        }
        if (shieldActive) {
            speed.method_26835(new class_1322(soldier.shieldSHIELD_SPEED_MODIFIER_ID(), "Medieval Machinery raised shield movement penalty", -0.3, class_1322.class_1323.field_6331));
        }
        soldier.shieldModifierState().shieldSpeedPenaltyApplied = shieldActive;
    }

    boolean shouldAutoRaiseShield(MedievalSoldierEntity soldier, @Nullable class_1309 target) {
        if (target == null || soldier.shieldCombatState().shieldBlockCooldownTicks > 0 || soldier.shieldCombatState().meleeWindupTicks > 0) {
            return false;
        }
        return MedievalSoldierShieldPolicy.shouldRaise(soldier, target);
    }

    boolean canCommandShieldBlock(MedievalSoldierEntity soldier, class_1282 source) {
        class_243 facing;
        if (soldier.getShieldMode() != NpcShieldMode.RAISED || soldier.shieldCombatState().shieldBlockCooldownTicks > 0 || !soldier.hasUsableShield() || source.method_48789(class_8103.field_43116)) {
            return false;
        }
        class_243 sourcePos = source.method_5510();
        if (sourcePos == null) {
            return false;
        }
        class_243 toDefender = soldier.method_19538().method_1020(sourcePos).method_1029();
        return toDefender.method_1026(facing = soldier.method_5828(1.0f)) < 0.0;
    }

    void damageRaisedShield(MedievalSoldierEntity soldier, float amount) {
        class_1799 shield = soldier.method_6079();
        if (shield.method_7960()) {
            return;
        }
        int durability = Math.max(1, class_3532.method_15386((float)amount));
        shield.method_7956(durability, (class_1309)soldier, broken -> soldier.method_20235(class_1304.field_6171));
        if (shield.method_7960()) {
            soldier.method_6075();
        }
    }
}

