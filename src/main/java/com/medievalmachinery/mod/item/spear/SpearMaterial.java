/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1802
 *  net.minecraft.class_1832
 *  net.minecraft.class_1856
 *  net.minecraft.class_1935
 *  net.minecraft.class_3489
 *  net.minecraft.class_6862
 */
package com.medievalmachinery.mod.item.spear;

import com.medievalmachinery.mod.item.spear.SpearType;
import net.minecraft.class_1802;
import net.minecraft.class_1832;
import net.minecraft.class_1856;
import net.minecraft.class_1935;
import net.minecraft.class_3489;
import net.minecraft.class_6862;

public enum SpearMaterial implements class_1832
{
    WOOD("wood", 4, 90, 15, class_1856.method_8106((class_6862)class_3489.field_15537)),
    STONE("stone", 5, 180, 5, class_1856.method_8091((class_1935[])new class_1935[]{class_1802.field_20412})),
    COPPER("copper", 5, 280, 12, class_1856.method_8091((class_1935[])new class_1935[]{class_1802.field_27022})),
    IRON("iron", 7, 650, 14, class_1856.method_8091((class_1935[])new class_1935[]{class_1802.field_8620})),
    DIAMOND("diamond", 8, 1350, 10, class_1856.method_8091((class_1935[])new class_1935[]{class_1802.field_8477})),
    NETHERITE("netherite", 9, 1900, 15, class_1856.method_8091((class_1935[])new class_1935[]{class_1802.field_22020}));

    private final String id;
    private final int damage;
    private final int durability;
    private final int enchantability;
    private final class_1856 repair;

    private SpearMaterial(String id, int damage, int durability, int enchantability, class_1856 repair) {
        this.id = id;
        this.damage = damage;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repair = repair;
    }

    public String id() {
        return this.id;
    }

    public int displayedDamage() {
        return this.damage;
    }

    public int durabilityFor(SpearType type) {
        return Math.max(1, Math.round((float)this.durability * type.durabilityMultiplier()));
    }

    public int method_8025() {
        return this.durability;
    }

    public float method_8027() {
        return 1.0f;
    }

    public float method_8028() {
        return 0.0f;
    }

    public int method_8024() {
        return this == WOOD ? 0 : (this == STONE ? 1 : (this == COPPER || this == IRON ? 2 : (this == DIAMOND ? 3 : 4)));
    }

    public int method_8026() {
        return this.enchantability;
    }

    public class_1856 method_8023() {
        return this.repair;
    }
}

