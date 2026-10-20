/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1935
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.QuartermasterFarmingController;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_7923;

final class QuartermasterWorkVisualState {
    private int workItemRawId;

    QuartermasterWorkVisualState() {
    }

    void usePlanting(class_1799 stack) {
        this.workItemRawId = stack == null || stack.method_7960() ? 0 : class_7923.field_41178.method_10206((Object)stack.method_7909());
    }

    void useBoneMeal() {
        this.workItemRawId = class_7923.field_41178.method_10206((Object)class_1802.field_8324);
    }

    class_1799 workStack(class_1799 fallback) {
        class_1792 item;
        if (this.workItemRawId > 0 && (item = (class_1792)class_7923.field_41178.method_10200(this.workItemRawId)) != null) {
            return new class_1799((class_1935)item);
        }
        return fallback;
    }

    static byte statusFor(QuartermasterFarmingController.Action action, byte till, byte plant, byte fertilize, byte harvest) {
        if (action == null) {
            return 0;
        }
        return switch (action) {
            case QuartermasterFarmingController.Action.TILL -> till;
            case QuartermasterFarmingController.Action.PLANT, QuartermasterFarmingController.Action.COMPOST -> plant;
            case QuartermasterFarmingController.Action.FERTILIZE -> fertilize;
            case QuartermasterFarmingController.Action.HARVEST -> harvest;
            default -> 0;
        };
    }

    static class_1799 plantingStack(int rawId) {
        if (rawId <= 0) {
            return new class_1799((class_1935)class_1802.field_8317);
        }
        class_1792 item = (class_1792)class_7923.field_41178.method_10200(rawId);
        return item == null ? new class_1799((class_1935)class_1802.field_8317) : new class_1799((class_1935)item);
    }
}

