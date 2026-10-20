/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1738$class_8051
 *  net.minecraft.class_1741
 *  net.minecraft.class_1802
 *  net.minecraft.class_1856
 *  net.minecraft.class_1935
 *  net.minecraft.class_3414
 *  net.minecraft.class_3417
 */
package com.medievalmachinery.mod.item;

import net.minecraft.class_1738;
import net.minecraft.class_1741;
import net.minecraft.class_1802;
import net.minecraft.class_1856;
import net.minecraft.class_1935;
import net.minecraft.class_3414;
import net.minecraft.class_3417;

public enum MedievalArmorMaterials implements class_1741
{
    ORDER(35, new int[]{3, 6, 8, 3}, 12, class_3417.field_14862, 2.5f, 0.05f),
    GENERAL(42, new int[]{4, 7, 9, 4}, 12, class_3417.field_15103, 4.0f, 0.1f);

    private static final int[] BASE_DURABILITY;
    private final int durabilityMultiplier;
    private final int[] protection;
    private final int enchantability;
    private final class_3414 equipSound;
    private final float toughness;
    private final float knockbackResistance;

    private MedievalArmorMaterials(int durabilityMultiplier, int[] protection, int enchantability, class_3414 equipSound, float toughness, float knockbackResistance) {
        this.durabilityMultiplier = durabilityMultiplier;
        this.protection = protection;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    private static int index(class_1738.class_8051 type) {
        return switch (type) {
            default -> throw new IncompatibleClassChangeError();
            case class_1738.class_8051.field_41937 -> 0;
            case class_1738.class_8051.field_41936 -> 1;
            case class_1738.class_8051.field_41935 -> 2;
            case class_1738.class_8051.field_41934 -> 3;
        };
    }

    public int method_48402(class_1738.class_8051 type) {
        return BASE_DURABILITY[MedievalArmorMaterials.index(type)] * this.durabilityMultiplier;
    }

    public int method_48403(class_1738.class_8051 type) {
        return this.protection[MedievalArmorMaterials.index(type)];
    }

    public int method_7699() {
        return this.enchantability;
    }

    public class_3414 method_7698() {
        return this.equipSound;
    }

    public class_1856 method_7695() {
        return this == ORDER ? class_1856.method_8091((class_1935[])new class_1935[]{class_1802.field_8620}) : class_1856.method_8091((class_1935[])new class_1935[]{class_1802.field_8477});
    }

    public String method_7694() {
        return this == ORDER ? "minecraft:iron" : "minecraft:diamond";
    }

    public float method_7700() {
        return this.toughness;
    }

    public float method_24355() {
        return this.knockbackResistance;
    }

    static {
        BASE_DURABILITY = new int[]{13, 15, 16, 11};
    }
}

