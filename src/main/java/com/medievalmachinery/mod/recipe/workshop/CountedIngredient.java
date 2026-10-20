/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  net.minecraft.class_1799
 *  net.minecraft.class_1856
 *  net.minecraft.class_2540
 *  net.minecraft.class_3518
 */
package com.medievalmachinery.mod.recipe.workshop;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.class_1799;
import net.minecraft.class_1856;
import net.minecraft.class_2540;
import net.minecraft.class_3518;

public final class CountedIngredient {
    public static final CountedIngredient EMPTY = new CountedIngredient(class_1856.method_35226(), 0);
    private final class_1856 ingredient;
    private final int count;

    public CountedIngredient(class_1856 ingredient, int count) {
        this.ingredient = ingredient;
        this.count = Math.max(0, count);
    }

    public class_1856 ingredient() {
        return this.ingredient;
    }

    public int count() {
        return this.count;
    }

    public boolean isEmpty() {
        return this.count <= 0;
    }

    public boolean test(class_1799 stack) {
        if (this.isEmpty()) {
            return stack.method_7960();
        }
        return !stack.method_7960() && this.ingredient.method_8093(stack) && stack.method_7947() >= this.count;
    }

    public static CountedIngredient fromJson(JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return EMPTY;
        }
        if (element.isJsonObject()) {
            JsonObject original = element.getAsJsonObject();
            int count = class_3518.method_15282((JsonObject)original, (String)"count", (int)1);
            if (count < 1 || count > 64) {
                throw new JsonParseException("Machine workshop ingredient count must be between 1 and 64");
            }
            JsonObject ingredientJson = original.deepCopy();
            ingredientJson.remove("count");
            class_1856 ingredient = class_1856.method_52177((JsonElement)ingredientJson);
            return new CountedIngredient(ingredient, count);
        }
        return new CountedIngredient(class_1856.method_52177((JsonElement)element), 1);
    }

    public static CountedIngredient fromPacket(class_2540 buf) {
        class_1856 ingredient = class_1856.method_8086((class_2540)buf);
        int count = buf.method_10816();
        return count <= 0 ? EMPTY : new CountedIngredient(ingredient, count);
    }

    public void write(class_2540 buf) {
        this.ingredient.method_8088(buf);
        buf.method_10804(this.count);
    }
}

