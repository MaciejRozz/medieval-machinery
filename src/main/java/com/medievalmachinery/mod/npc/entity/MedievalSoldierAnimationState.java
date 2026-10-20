/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3532
 */
package com.medievalmachinery.mod.npc.entity;

import net.minecraft.class_3532;

final class MedievalSoldierAnimationState {
    private int spearStartAge = Integer.MIN_VALUE;
    private int meleeStartAge = Integer.MIN_VALUE;
    private int farmTillStartAge = Integer.MIN_VALUE;
    private int farmPlantStartAge = Integer.MIN_VALUE;
    private int farmHarvestStartAge = Integer.MIN_VALUE;

    MedievalSoldierAnimationState() {
    }

    void startSpear(int age) {
        this.spearStartAge = age;
    }

    void startMelee(int age) {
        this.meleeStartAge = age;
    }

    void startFarmTill(int age) {
        this.farmTillStartAge = age;
    }

    void startFarmPlant(int age) {
        this.farmPlantStartAge = age;
    }

    void startFarmHarvest(int age) {
        this.farmHarvestStartAge = age;
    }

    float spearProgress(float age, float duration) {
        return MedievalSoldierAnimationState.progress(age, this.spearStartAge, duration);
    }

    float meleeProgress(float age, float duration) {
        return MedievalSoldierAnimationState.progress(age, this.meleeStartAge, duration);
    }

    float farmTillProgress(float age, float duration) {
        return MedievalSoldierAnimationState.progress(age, this.farmTillStartAge, duration);
    }

    float farmPlantProgress(float age, float duration) {
        return MedievalSoldierAnimationState.progress(age, this.farmPlantStartAge, duration);
    }

    float farmHarvestProgress(float age, float duration) {
        return MedievalSoldierAnimationState.progress(age, this.farmHarvestStartAge, duration);
    }

    private static float progress(float age, int start, float duration) {
        if (start == Integer.MIN_VALUE) {
            return -1.0f;
        }
        float elapsed = age - (float)start;
        if (elapsed < 0.0f || elapsed > duration) {
            return -1.0f;
        }
        return class_3532.method_15363((float)(elapsed / duration), (float)0.0f, (float)1.0f);
    }
}

