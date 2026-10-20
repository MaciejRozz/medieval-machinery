/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_124
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_1829
 *  net.minecraft.class_1832
 *  net.minecraft.class_1836
 *  net.minecraft.class_1856
 *  net.minecraft.class_1937
 *  net.minecraft.class_2561
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.item.spear;

import com.medievalmachinery.mod.item.spear.SpearMaterial;
import com.medievalmachinery.mod.item.spear.SpearType;
import java.util.List;
import java.util.Locale;
import net.minecraft.class_124;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1829;
import net.minecraft.class_1832;
import net.minecraft.class_1836;
import net.minecraft.class_1856;
import net.minecraft.class_1937;
import net.minecraft.class_2561;
import org.jetbrains.annotations.Nullable;

public final class SpearItem
extends class_1829 {
    public static final double CLOSE_RANGE = 1.5;
    public static final double CLOSE_RANGE_DAMAGE_MULTIPLIER = 0.75;
    private final SpearType type;
    private final SpearMaterial material;

    public SpearItem(SpearType type, SpearMaterial material, class_1792.class_1793 settings) {
        super((class_1832)new ProfileToolMaterial(material, type), Math.max(0, material.displayedDamage() + type.damageOffset() - 1), type.attackSpeed() - 4.0f, SpearItem.prepareSettings(material, settings));
        this.type = type;
        this.material = material;
    }

    private static class_1792.class_1793 prepareSettings(SpearMaterial material, class_1792.class_1793 settings) {
        return material == SpearMaterial.NETHERITE ? settings.method_24359() : settings;
    }

    public SpearType getSpearType() {
        return this.type;
    }

    public SpearMaterial getSpearMaterial() {
        return this.material;
    }

    public double getReach() {
        double d = this.type.reach();
        return d + (switch (this.material) {
            default -> throw new IncompatibleClassChangeError();
            case SpearMaterial.WOOD -> -0.25;
            case SpearMaterial.STONE -> -0.15;
            case SpearMaterial.COPPER -> -0.05;
            case SpearMaterial.IRON -> 0.0;
            case SpearMaterial.DIAMOND -> 0.1;
            case SpearMaterial.NETHERITE -> 0.15;
        });
    }

    public double getOptimalMinRange() {
        return 1.5;
    }

    public void method_7851(class_1799 stack, @Nullable class_1937 world, List<class_2561> tooltip, class_1836 context) {
        tooltip.add((class_2561)class_2561.method_43469((String)"tooltip.medieval-machinery.spear.reach", (Object[])new Object[]{String.format(Locale.ROOT, "%.2f", this.getReach())}).method_27692(class_124.field_1080));
        tooltip.add((class_2561)class_2561.method_43471((String)"tooltip.medieval-machinery.spear.close_range").method_27692(class_124.field_1063));
    }

    private record ProfileToolMaterial(SpearMaterial material, SpearType type) implements class_1832
    {
        public int method_8025() {
            return this.material.durabilityFor(this.type);
        }

        public float method_8027() {
            return this.material.method_8027();
        }

        public float method_8028() {
            return 0.0f;
        }

        public int method_8024() {
            return this.material.method_8024();
        }

        public int method_8026() {
            return this.material.method_8026();
        }

        public class_1856 method_8023() {
            return this.material.method_8023();
        }
    }
}

