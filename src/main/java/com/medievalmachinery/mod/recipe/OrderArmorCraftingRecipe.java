/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1738$class_8051
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1852
 *  net.minecraft.class_1865
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2960
 *  net.minecraft.class_5455
 *  net.minecraft.class_7710
 *  net.minecraft.class_8566
 */
package com.medievalmachinery.mod.recipe;

import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.recipe.ModRecipes;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1738;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1852;
import net.minecraft.class_1865;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_5455;
import net.minecraft.class_7710;
import net.minecraft.class_8566;

public final class OrderArmorCraftingRecipe
extends class_1852 {
    private final boolean general;
    private final class_1738.class_8051 armorType;

    public OrderArmorCraftingRecipe(class_2960 id, class_7710 category) {
        super(id, category);
        String path = id.method_12832();
        this.general = path.startsWith("general_");
        if (path.endsWith("_helmet")) {
            this.armorType = class_1738.class_8051.field_41934;
        } else if (path.endsWith("_chestplate")) {
            this.armorType = class_1738.class_8051.field_41935;
        } else if (path.endsWith("_leggings")) {
            this.armorType = class_1738.class_8051.field_41936;
        } else if (path.endsWith("_boots")) {
            this.armorType = class_1738.class_8051.field_41937;
        } else {
            throw new IllegalArgumentException("Unsupported Medieval Machinery armor recipe id: " + String.valueOf(id));
        }
    }

    public boolean matches(class_8566 inventory, class_1937 world) {
        if (inventory.method_17398() < 3 || inventory.method_17397() < 2) {
            return false;
        }
        class_1792 material = this.general ? class_1802.field_8477 : class_1802.field_8620;
        for (int offsetY = 0; offsetY <= inventory.method_17397() - this.shapeHeight(); ++offsetY) {
            for (int offsetX = 0; offsetX <= inventory.method_17398() - 3; ++offsetX) {
                MatchResult result = this.matchesAt(inventory, material, offsetX, offsetY);
                if (!result.matches || !result.sigilFound) continue;
                return true;
            }
        }
        return false;
    }

    private MatchResult matchesAt(class_8566 inv, class_1792 material, int offsetX, int offsetY) {
        boolean sigil = false;
        for (int y = 0; y < inv.method_17397(); ++y) {
            for (int x = 0; x < inv.method_17398(); ++x) {
                boolean occupied;
                class_1799 stack = inv.method_5438(x + y * inv.method_17398());
                int recipeX = x - offsetX;
                int recipeY = y - offsetY;
                boolean bl = occupied = recipeX >= 0 && recipeX < 3 && recipeY >= 0 && recipeY < this.shapeHeight() && this.occupied(recipeX, recipeY);
                if (!occupied) {
                    if (stack.method_7960()) continue;
                    return new MatchResult(false, false);
                }
                if (stack.method_31574(material)) continue;
                if (!sigil && stack.method_7909() instanceof OrderSigilItem) {
                    sigil = true;
                    continue;
                }
                return new MatchResult(false, false);
            }
        }
        return new MatchResult(sigil, sigil);
    }

    private boolean occupied(int x, int y) {
        return switch (this.armorType) {
            default -> throw new IncompatibleClassChangeError();
            case class_1738.class_8051.field_41934 -> {
                if (y == 0 || y == 1 && (x == 0 || x == 2)) {
                    yield true;
                }
                yield false;
            }
            case class_1738.class_8051.field_41935 -> {
                if (y == 0 && (x == 0 || x == 2) || y == 1 || y == 2) {
                    yield true;
                }
                yield false;
            }
            case class_1738.class_8051.field_41936 -> {
                if (y == 0 || (y == 1 || y == 2) && (x == 0 || x == 2)) {
                    yield true;
                }
                yield false;
            }
            case class_1738.class_8051.field_41937 -> !(y != 0 && y != 1 || x != 0 && x != 2);
        };
    }

    private int shapeHeight() {
        return this.armorType == class_1738.class_8051.field_41934 || this.armorType == class_1738.class_8051.field_41937 ? 2 : 3;
    }

    public class_1799 craft(class_8566 inventory, class_5455 registryManager) {
        class_1799 sigil = class_1799.field_8037;
        for (int i = 0; i < inventory.method_5439(); ++i) {
            class_1799 stack = inventory.method_5438(i);
            if (!(stack.method_7909() instanceof OrderSigilItem)) continue;
            sigil = stack;
            break;
        }
        MedievalArmorItem output = (MedievalArmorItem)this.outputItem();
        return MedievalArmorItem.createFromSigil(output, sigil);
    }

    public boolean method_8118() {
        return false;
    }

    public boolean method_8113(int width, int height) {
        return width >= 3 && height >= this.shapeHeight();
    }

    public class_1799 method_8110(class_5455 registryManager) {
        return new class_1799((class_1935)this.outputItem());
    }

    private class_1792 outputItem() {
        if (this.general) {
            return switch (this.armorType) {
                default -> throw new IncompatibleClassChangeError();
                case class_1738.class_8051.field_41934 -> ModItems.GENERAL_HELMET;
                case class_1738.class_8051.field_41935 -> ModItems.GENERAL_CHESTPLATE;
                case class_1738.class_8051.field_41936 -> ModItems.GENERAL_LEGGINGS;
                case class_1738.class_8051.field_41937 -> ModItems.GENERAL_BOOTS;
            };
        }
        return switch (this.armorType) {
            default -> throw new IncompatibleClassChangeError();
            case class_1738.class_8051.field_41934 -> ModItems.ORDER_HELMET;
            case class_1738.class_8051.field_41935 -> ModItems.ORDER_CHESTPLATE;
            case class_1738.class_8051.field_41936 -> ModItems.ORDER_LEGGINGS;
            case class_1738.class_8051.field_41937 -> ModItems.ORDER_BOOTS;
        };
    }

    public class_1865<?> method_8119() {
        return ModRecipes.ORDER_ARMOR_CRAFTING;
    }

    private record MatchResult(boolean matches, boolean sigilFound) {
    }
}

