/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1646
 *  net.minecraft.class_3852
 */
package com.medievalmachinery.mod.npc.recruitment;

import net.minecraft.class_1646;
import net.minecraft.class_3852;

public final class WatchpostRecruitmentRules {
    public static final int RECRUIT_RADIUS = 50;
    public static final int MIN_VILLAGERS = 10;

    private WatchpostRecruitmentRules() {
    }

    public static boolean countsTowardPopulation(class_1646 villager) {
        return villager.method_5805() && !villager.method_6109();
    }

    public static boolean isEmployed(class_1646 villager) {
        if (!WatchpostRecruitmentRules.countsTowardPopulation(villager)) {
            return false;
        }
        class_3852 profession = villager.method_7231().method_16924();
        return profession != class_3852.field_17051 && profession != class_3852.field_17062;
    }

    public static boolean isRecruitable(class_1646 villager) {
        return WatchpostRecruitmentRules.countsTowardPopulation(villager) && villager.method_7231().method_16924() == class_3852.field_17051;
    }

    public static boolean settlementRequirementMet(int totalAdults) {
        return totalAdults >= 10;
    }

    public static int availableRecruitSlots(int totalAdults, int recruitableVillagers) {
        if (!WatchpostRecruitmentRules.settlementRequirementMet(totalAdults)) {
            return 0;
        }
        int populationSurplus = Math.max(0, totalAdults - 10);
        return Math.min(recruitableVillagers, populationSurplus);
    }

    public static boolean recruitmentPossible(int totalAdults, int recruitableVillagers) {
        return WatchpostRecruitmentRules.availableRecruitSlots(totalAdults, recruitableVillagers) > 0;
    }
}

