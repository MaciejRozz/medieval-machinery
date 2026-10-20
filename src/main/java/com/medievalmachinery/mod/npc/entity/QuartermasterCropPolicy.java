/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2302
 *  net.minecraft.class_2680
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import java.util.ArrayList;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2302;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import org.jetbrains.annotations.Nullable;

public final class QuartermasterCropPolicy {
    private QuartermasterCropPolicy() {
    }

    public static Crop resolveSelection(class_1799 selector) {
        if (selector == null || selector.method_7960()) {
            return QuartermasterCropPolicy.resolve(class_1802.field_8317);
        }
        Crop crop = QuartermasterCropPolicy.resolve(selector.method_7909());
        return crop != null ? crop : QuartermasterCropPolicy.resolve(class_1802.field_8317);
    }

    public static boolean isSupportedPlantingItem(class_1799 stack) {
        return stack != null && !stack.method_7960() && QuartermasterCropPolicy.resolve(stack.method_7909()) != null;
    }

    public static boolean isFarmPickup(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return false;
        }
        class_1792 item = stack.method_7909();
        if (item == class_1802.field_8861 || item == class_1802.field_8324) {
            return true;
        }
        if (QuartermasterCropPolicy.resolve(item) != null) {
            return true;
        }
        for (Crop crop : QuartermasterCropPolicy.supportedCrops()) {
            if (crop.produceItem() != item) continue;
            return true;
        }
        return false;
    }

    @Nullable
    public static Crop resolve(class_1792 item) {
        if (item == class_1802.field_8317) {
            return new Crop(item, class_2246.field_10293, class_1802.field_8861, 64, true);
        }
        if (item == class_1802.field_8179) {
            return new Crop(item, class_2246.field_10609, class_1802.field_8179, 16, false);
        }
        if (item == class_1802.field_8567) {
            return new Crop(item, class_2246.field_10247, class_1802.field_8567, 16, false);
        }
        if (item == class_1802.field_8309) {
            return new Crop(item, class_2246.field_10341, class_1802.field_8186, 16, true);
        }
        class_2960 id = class_7923.field_41178.method_10221((Object)item);
        if (id == null || !"farmersdelight".equals(id.method_12836())) {
            return null;
        }
        return switch (id.method_12832()) {
            case "cabbage_seeds" -> QuartermasterCropPolicy.optionalCrop(item, "farmersdelight:cabbages", "farmersdelight:cabbage", 16, true);
            case "tomato_seeds" -> QuartermasterCropPolicy.optionalCrop(item, "farmersdelight:tomatoes", "farmersdelight:tomato", 16, true);
            case "onion" -> QuartermasterCropPolicy.optionalCrop(item, "farmersdelight:onions", "farmersdelight:onion", 16, false);
            default -> null;
        };
    }

    @Nullable
    public static Crop resolveCropBlock(class_2248 block) {
        if (block == null) {
            return null;
        }
        for (Crop crop : QuartermasterCropPolicy.supportedCrops()) {
            if (crop.cropBlock() != block) continue;
            return crop;
        }
        return null;
    }

    public static boolean isMature(class_2248 block, class_2680 state) {
        class_2302 crop;
        return block instanceof class_2302 && (crop = (class_2302)block).method_9825(state);
    }

    @Nullable
    private static Crop optionalCrop(class_1792 plantingItem, String cropId, String produceId, int reserve, boolean compostExcess) {
        class_2960 cropIdentifier = new class_2960(cropId);
        class_2960 produceIdentifier = new class_2960(produceId);
        if (!class_7923.field_41175.method_10250(cropIdentifier) || !class_7923.field_41178.method_10250(produceIdentifier)) {
            return null;
        }
        class_2248 crop = (class_2248)class_7923.field_41175.method_10223(cropIdentifier);
        class_1792 produce = (class_1792)class_7923.field_41178.method_10223(produceIdentifier);
        if (!(crop instanceof class_2302)) {
            return null;
        }
        return new Crop(plantingItem, crop, produce, reserve, compostExcess);
    }

    private static Crop[] supportedCrops() {
        ArrayList<Crop> crops = new ArrayList<Crop>();
        for (class_1792 class_17922 : new class_1792[]{class_1802.field_8317, class_1802.field_8179, class_1802.field_8567, class_1802.field_8309}) {
            Crop crop = QuartermasterCropPolicy.resolve(class_17922);
            if (crop == null) continue;
            crops.add(crop);
        }
        for (String string : new String[]{"farmersdelight:cabbage_seeds", "farmersdelight:tomato_seeds", "farmersdelight:onion"}) {
            Crop crop;
            class_2960 key = new class_2960(string);
            if (!class_7923.field_41178.method_10250(key) || (crop = QuartermasterCropPolicy.resolve((class_1792)class_7923.field_41178.method_10223(key))) == null) continue;
            crops.add(crop);
        }
        return (Crop[])crops.toArray(Crop[]::new);
    }

    public record Crop(class_1792 plantingItem, class_2248 cropBlock, @Nullable class_1792 produceItem, int plantingReserve, boolean compostPlantingExcess) {
    }
}

