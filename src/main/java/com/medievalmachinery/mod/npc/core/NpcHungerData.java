/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_2487
 *  net.minecraft.class_4174
 */
package com.medievalmachinery.mod.npc.core;

import com.medievalmachinery.mod.npc.core.NpcHungerState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_2487;
import net.minecraft.class_4174;

public final class NpcHungerData {
    public static final int MAX_FOOD = 20;
    public static final int SATIATED_THRESHOLD = 18;
    public static final int HUNGRY_THRESHOLD = 10;
    public static final int VERY_HUNGRY_THRESHOLD = 5;
    public static final int SLOW_FOOD_THRESHOLD = 6;
    public static final int VERY_SLOW_FOOD_THRESHOLD = 2;
    private int foodLevel = 20;
    private float saturationLevel = 5.0f;
    private float exhaustion;
    private double lastX;
    private double lastZ;
    private boolean positionInitialized;
    private int regenerationTicks;
    private static final int REGEN_INTERVAL_TICKS = 100;
    private static final float REGEN_HEALTH = 0.5f;
    private static final float REGEN_EXHAUSTION = 1.5f;

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public float getSaturationLevel() {
        return this.saturationLevel;
    }

    public NpcHungerState getState() {
        if (this.foodLevel >= 18) {
            return NpcHungerState.SATIATED;
        }
        if (this.foodLevel > 10) {
            return NpcHungerState.NORMAL;
        }
        if (this.foodLevel > 5) {
            return NpcHungerState.HUNGRY;
        }
        return NpcHungerState.VERY_HUNGRY;
    }

    public boolean needsFood() {
        return this.foodLevel <= 10;
    }

    public boolean shouldEat(MedievalSoldierEntity soldier) {
        if (this.needsFood()) {
            return true;
        }
        if (soldier == null) {
            return false;
        }
        if (soldier.method_6032() >= soldier.method_6063()) {
            return false;
        }
        return this.foodLevel <= 18;
    }

    public void eat(class_4174 food) {
        if (food == null) {
            return;
        }
        this.eat(food.method_19230(), food.method_19231());
    }

    public void eat(int hunger, float saturationModifier) {
        int restored = Math.max(0, hunger);
        if (restored <= 0) {
            return;
        }
        this.foodLevel = Math.min(20, this.foodLevel + restored);
        float saturationGain = (float)restored * Math.max(0.0f, saturationModifier) * 2.0f;
        this.saturationLevel = Math.min((float)this.foodLevel, this.saturationLevel + saturationGain);
    }

    public void tick(MedievalSoldierEntity soldier) {
        if (soldier == null || soldier.method_37908().field_9236) {
            return;
        }
        this.addExhaustion(0.0015f);
        this.tickMovementExhaustion(soldier);
        this.processExhaustion();
        this.tickNaturalRegeneration(soldier);
    }

    private void tickNaturalRegeneration(MedievalSoldierEntity soldier) {
        boolean injured = soldier.method_6032() < soldier.method_6063();
        boolean wellFed = this.foodLevel >= 18;
        boolean activeCombat = soldier.method_5968() != null;
        class_1309 attacker = soldier.method_6065();
        if (!activeCombat && attacker != null && attacker.method_5805() && soldier.method_5858((class_1297)attacker) < 144.0) {
            activeCombat = true;
        }
        if (!injured || !wellFed || activeCombat) {
            this.regenerationTicks = 0;
            return;
        }
        if (++this.regenerationTicks < 100) {
            return;
        }
        this.regenerationTicks = 0;
        soldier.method_6025(0.5f);
        this.addExhaustion(1.5f);
    }

    private void tickMovementExhaustion(MedievalSoldierEntity soldier) {
        if (!this.positionInitialized) {
            this.lastX = soldier.method_23317();
            this.lastZ = soldier.method_23321();
            this.positionInitialized = true;
            return;
        }
        double dx = soldier.method_23317() - this.lastX;
        double dz = soldier.method_23321() - this.lastZ;
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        this.lastX = soldier.method_23317();
        this.lastZ = soldier.method_23321();
        if (horizontalDistance <= 0.001) {
            return;
        }
        float factor = soldier.method_5968() == null ? 0.01f : 0.02f;
        this.addExhaustion((float)horizontalDistance * factor);
    }

    public void addExhaustion(float amount) {
        if (amount <= 0.0f) {
            return;
        }
        this.exhaustion = Math.min(40.0f, this.exhaustion + amount);
    }

    private void processExhaustion() {
        while (this.exhaustion >= 4.0f) {
            this.exhaustion -= 4.0f;
            if (this.saturationLevel > 0.0f) {
                this.saturationLevel = Math.max(0.0f, this.saturationLevel - 1.0f);
                continue;
            }
            if (this.foodLevel <= 0) continue;
            --this.foodLevel;
        }
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        nbt.method_10569("Food", this.foodLevel);
        nbt.method_10548("Saturation", this.saturationLevel);
        nbt.method_10548("Exhaustion", this.exhaustion);
        return nbt;
    }

    public void readNbt(class_2487 nbt) {
        if (nbt == null) {
            return;
        }
        if (nbt.method_10573("Food", 3)) {
            this.foodLevel = Math.max(0, Math.min(20, nbt.method_10550("Food")));
        }
        if (nbt.method_10573("Saturation", 5)) {
            this.saturationLevel = Math.max(0.0f, Math.min((float)this.foodLevel, nbt.method_10583("Saturation")));
        }
        if (nbt.method_10573("Exhaustion", 5)) {
            this.exhaustion = Math.max(0.0f, Math.min(40.0f, nbt.method_10583("Exhaustion")));
        }
        this.positionInitialized = false;
        this.regenerationTicks = 0;
    }
}

