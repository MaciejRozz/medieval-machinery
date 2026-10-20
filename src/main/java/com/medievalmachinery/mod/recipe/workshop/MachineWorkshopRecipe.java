/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  net.minecraft.class_1799
 *  net.minecraft.class_1856
 *  net.minecraft.class_1865
 *  net.minecraft.class_1869
 *  net.minecraft.class_1937
 *  net.minecraft.class_2371
 *  net.minecraft.class_2540
 *  net.minecraft.class_2960
 *  net.minecraft.class_3518
 *  net.minecraft.class_3955
 *  net.minecraft.class_5455
 *  net.minecraft.class_7710
 *  net.minecraft.class_8566
 */
package com.medievalmachinery.mod.recipe.workshop;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.medievalmachinery.mod.recipe.ModRecipes;
import com.medievalmachinery.mod.recipe.workshop.CountedIngredient;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.class_1799;
import net.minecraft.class_1856;
import net.minecraft.class_1865;
import net.minecraft.class_1869;
import net.minecraft.class_1937;
import net.minecraft.class_2371;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_3518;
import net.minecraft.class_3955;
import net.minecraft.class_5455;
import net.minecraft.class_7710;
import net.minecraft.class_8566;

public final class MachineWorkshopRecipe
implements class_3955 {
    public static final int MAX_WIDTH = 4;
    public static final int MAX_HEIGHT = 4;
    private final class_2960 id;
    private final String group;
    private final int width;
    private final int height;
    private final class_2371<CountedIngredient> ingredients;
    private final class_1799 output;

    public MachineWorkshopRecipe(class_2960 id, String group, int width, int height, class_2371<CountedIngredient> ingredients, class_1799 output) {
        this.id = id;
        this.group = group;
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.output = output;
    }

    public boolean matches(class_8566 inventory, class_1937 world) {
        return this.findMatch(inventory).isPresent();
    }

    public Optional<Match> findMatch(class_8566 inventory) {
        if (inventory.method_17398() < this.width || inventory.method_17397() < this.height) {
            return Optional.empty();
        }
        for (int offsetY = 0; offsetY <= inventory.method_17397() - this.height; ++offsetY) {
            for (int offsetX = 0; offsetX <= inventory.method_17398() - this.width; ++offsetX) {
                if (this.matchesAt(inventory, offsetX, offsetY, false)) {
                    return Optional.of(new Match(offsetX, offsetY, false));
                }
                if (!this.matchesAt(inventory, offsetX, offsetY, true)) continue;
                return Optional.of(new Match(offsetX, offsetY, true));
            }
        }
        return Optional.empty();
    }

    private boolean matchesAt(class_8566 inventory, int offsetX, int offsetY, boolean mirrored) {
        for (int y = 0; y < inventory.method_17397(); ++y) {
            for (int x = 0; x < inventory.method_17398(); ++x) {
                int recipeX = x - offsetX;
                int recipeY = y - offsetY;
                CountedIngredient ingredient = CountedIngredient.EMPTY;
                if (recipeX >= 0 && recipeY >= 0 && recipeX < this.width && recipeY < this.height) {
                    int actualX = mirrored ? this.width - 1 - recipeX : recipeX;
                    ingredient = (CountedIngredient)this.ingredients.get(actualX + recipeY * this.width);
                }
                if (ingredient.test(inventory.method_5438(x + y * inventory.method_17398()))) continue;
                return false;
            }
        }
        return true;
    }

    public int requiredCountForSlot(class_8566 inventory, Match match, int slot) {
        int x = slot % inventory.method_17398();
        int y = slot / inventory.method_17398();
        int recipeX = x - match.offsetX();
        int recipeY = y - match.offsetY();
        if (recipeX < 0 || recipeY < 0 || recipeX >= this.width || recipeY >= this.height) {
            return 0;
        }
        int actualX = match.mirrored() ? this.width - 1 - recipeX : recipeX;
        return ((CountedIngredient)this.ingredients.get(actualX + recipeY * this.width)).count();
    }

    public class_1799 craft(class_8566 inventory, class_5455 registryManager) {
        return this.output.method_7972();
    }

    public boolean method_8113(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    public class_1799 method_8110(class_5455 registryManager) {
        return this.output;
    }

    public class_2371<class_1856> method_8117() {
        class_2371 vanillaIngredients = class_2371.method_10213((int)this.ingredients.size(), (Object)class_1856.field_9017);
        for (int i = 0; i < this.ingredients.size(); ++i) {
            CountedIngredient counted = (CountedIngredient)this.ingredients.get(i);
            if (counted.isEmpty()) continue;
            vanillaIngredients.set(i, (Object)counted.ingredient());
        }
        return vanillaIngredients;
    }

    public class_2960 method_8114() {
        return this.id;
    }

    public String method_8112() {
        return this.group;
    }

    public class_7710 method_45441() {
        return class_7710.field_40251;
    }

    public boolean method_31584() {
        for (CountedIngredient ingredient : this.ingredients) {
            if (ingredient.isEmpty()) continue;
            return false;
        }
        return true;
    }

    public boolean method_8118() {
        return false;
    }

    public class_1865<?> method_8119() {
        return ModRecipes.MACHINE_WORKSHOP_SHAPED;
    }

    public int getRecipeWidth() {
        return this.width;
    }

    public int getRecipeHeight() {
        return this.height;
    }

    public class_2371<CountedIngredient> getCountedIngredients() {
        return this.ingredients;
    }

    public record Match(int offsetX, int offsetY, boolean mirrored) {
    }

    public static final class Serializer
    implements class_1865<MachineWorkshopRecipe> {
        public MachineWorkshopRecipe read(class_2960 id, JsonObject json) {
            String group = class_3518.method_15253((JsonObject)json, (String)"group", (String)"");
            JsonArray patternArray = class_3518.method_15261((JsonObject)json, (String)"pattern");
            if (patternArray.size() == 0 || patternArray.size() > 4) {
                throw new JsonParseException("Machine workshop pattern must contain 1 to 4 rows");
            }
            String[] pattern = new String[patternArray.size()];
            int width = -1;
            for (int row = 0; row < pattern.length; ++row) {
                String line = class_3518.method_15287((JsonElement)patternArray.get(row), (String)("pattern[" + row + "]"));
                if (line.isEmpty() || line.length() > 4) {
                    throw new JsonParseException("Machine workshop pattern rows must contain 1 to 4 columns");
                }
                if (width < 0) {
                    width = line.length();
                }
                if (line.length() != width) {
                    throw new JsonParseException("All machine workshop pattern rows must have the same width");
                }
                pattern[row] = line;
            }
            JsonObject keyJson = class_3518.method_15296((JsonObject)json, (String)"key");
            HashMap<Character, CountedIngredient> key = new HashMap<Character, CountedIngredient>();
            for (Map.Entry entry : keyJson.entrySet()) {
                String symbol = (String)entry.getKey();
                if (symbol.length() != 1 || " ".equals(symbol)) {
                    throw new JsonParseException("Machine workshop key symbols must be one non-space character");
                }
                key.put(Character.valueOf(symbol.charAt(0)), CountedIngredient.fromJson((JsonElement)entry.getValue()));
            }
            class_2371 ingredients = class_2371.method_10213((int)(width * pattern.length), (Object)CountedIngredient.EMPTY);
            for (int y = 0; y < pattern.length; ++y) {
                for (int x = 0; x < width; ++x) {
                    char symbol = pattern[y].charAt(x);
                    if (symbol == ' ') continue;
                    CountedIngredient ingredient = (CountedIngredient)key.get(Character.valueOf(symbol));
                    if (ingredient == null) {
                        throw new JsonParseException("Pattern references undefined symbol '" + symbol + "'");
                    }
                    ingredients.set(x + y * width, (Object)ingredient);
                }
            }
            class_1799 output = class_1869.method_35228((JsonObject)class_3518.method_15296((JsonObject)json, (String)"result"));
            return new MachineWorkshopRecipe(id, group, width, pattern.length, (class_2371<CountedIngredient>)ingredients, output);
        }

        public MachineWorkshopRecipe read(class_2960 id, class_2540 buf) {
            int width = buf.method_10816();
            int height = buf.method_10816();
            String group = buf.method_10800(Short.MAX_VALUE);
            class_2371 ingredients = class_2371.method_10213((int)(width * height), (Object)CountedIngredient.EMPTY);
            for (int i = 0; i < ingredients.size(); ++i) {
                ingredients.set(i, (Object)CountedIngredient.fromPacket(buf));
            }
            class_1799 output = buf.method_10819();
            return new MachineWorkshopRecipe(id, group, width, height, (class_2371<CountedIngredient>)ingredients, output);
        }

        public void write(class_2540 buf, MachineWorkshopRecipe recipe) {
            buf.method_10804(recipe.width);
            buf.method_10804(recipe.height);
            buf.method_10814(recipe.group);
            for (CountedIngredient ingredient : recipe.ingredients) {
                ingredient.write(buf);
            }
            buf.method_10793(recipe.output);
        }
    }
}

