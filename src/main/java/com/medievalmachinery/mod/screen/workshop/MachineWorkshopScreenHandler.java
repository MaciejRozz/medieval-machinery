/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1662
 *  net.minecraft.class_1703
 *  net.minecraft.class_1715
 *  net.minecraft.class_1729
 *  net.minecraft.class_1731
 *  net.minecraft.class_1734
 *  net.minecraft.class_1735
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1860
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2540
 *  net.minecraft.class_2596
 *  net.minecraft.class_2695
 *  net.minecraft.class_3222
 *  net.minecraft.class_3914
 *  net.minecraft.class_3955
 *  net.minecraft.class_3956
 *  net.minecraft.class_5421
 *  net.minecraft.class_8566
 */
package com.medievalmachinery.mod.screen.workshop;

import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.recipe.workshop.CountedIngredient;
import com.medievalmachinery.mod.recipe.workshop.MachineWorkshopRecipe;
import com.medievalmachinery.mod.registry.ModBlocks;
import java.util.Optional;
import net.minecraft.class_1263;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1662;
import net.minecraft.class_1703;
import net.minecraft.class_1715;
import net.minecraft.class_1729;
import net.minecraft.class_1731;
import net.minecraft.class_1734;
import net.minecraft.class_1735;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1860;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2596;
import net.minecraft.class_2695;
import net.minecraft.class_3222;
import net.minecraft.class_3914;
import net.minecraft.class_3955;
import net.minecraft.class_3956;
import net.minecraft.class_5421;
import net.minecraft.class_8566;

public final class MachineWorkshopScreenHandler
extends class_1729<class_8566> {
    public static final int GRID_SIZE = 16;
    private static final int RESULT_SLOT = 0;
    private static final int FIRST_GRID_SLOT = 1;
    private static final int FIRST_PLAYER_SLOT = 17;
    private final class_1715 input;
    private final class_1731 result;
    private final class_3914 context;
    private final class_1657 player;

    public MachineWorkshopScreenHandler(int syncId, class_1661 playerInventory, class_2540 buf) {
        this(syncId, playerInventory, class_3914.method_17392((class_1937)playerInventory.field_7546.method_37908(), (class_2338)buf.method_10811()));
    }

    public MachineWorkshopScreenHandler(int syncId, class_1661 playerInventory, class_3914 context) {
        super(RegisteredContent.machineWorkshopScreen(), syncId);
        int column;
        int row;
        this.context = context;
        this.player = playerInventory.field_7546;
        this.input = new class_1715((class_1703)this, 4, 4);
        this.result = new class_1731();
        this.method_7621((class_1735)new WorkshopResultSlot(playerInventory.field_7546, (class_8566)this.input, (class_1263)this.result, 0, 124, 44));
        for (row = 0; row < 4; ++row) {
            for (column = 0; column < 4; ++column) {
                this.method_7621(new class_1735((class_1263)this.input, column + row * 4, 30 + column * 18, 17 + row * 18));
            }
        }
        for (row = 0; row < 3; ++row) {
            for (column = 0; column < 9; ++column) {
                this.method_7621(new class_1735((class_1263)playerInventory, column + row * 9 + 9, 8 + column * 18, 102 + row * 18));
            }
        }
        for (int column2 = 0; column2 < 9; ++column2) {
            this.method_7621(new class_1735((class_1263)playerInventory, column2, 8 + column2 * 18, 160));
        }
    }

    public void method_7609(class_1263 inventory) {
        super.method_7609(inventory);
        this.updateResult();
    }

    private void updateResult() {
        this.context.method_17393((world, pos) -> {
            if (world.field_9236) {
                return;
            }
            Optional match = world.method_8433().method_8132(class_3956.field_17545, (class_1263)this.input, world);
            class_1799 output = class_1799.field_8037;
            if (match.isPresent()) {
                class_3955 recipe = (class_3955)match.get();
                this.result.method_7662((class_1860)recipe);
                output = recipe.method_8116((class_1263)this.input, world.method_30349());
            } else {
                this.result.method_7662(null);
            }
            this.result.method_5447(0, output);
            this.method_7623();
        });
    }

    private void consumeCountedRecipe(MachineWorkshopRecipe recipe, class_1657 player) {
        Optional<MachineWorkshopRecipe.Match> matchOptional = recipe.findMatch((class_8566)this.input);
        if (matchOptional.isEmpty()) {
            return;
        }
        MachineWorkshopRecipe.Match match = matchOptional.get();
        this.result.method_7664(player, this.input.method_51305());
        for (int slot = 0; slot < this.input.method_5439(); ++slot) {
            int room;
            int required = recipe.requiredCountForSlot((class_8566)this.input, match, slot);
            if (required <= 0) continue;
            class_1799 before = this.input.method_5438(slot);
            class_1792 remainderItem = before.method_7909().method_7858();
            this.input.method_5434(slot, required);
            if (remainderItem == null) continue;
            class_1799 remainder = new class_1799((class_1935)remainderItem, required);
            class_1799 current = this.input.method_5438(slot);
            if (current.method_7960()) {
                int place = Math.min(remainder.method_7947(), remainder.method_7914());
                this.input.method_5447(slot, remainder.method_46651(place));
                remainder.method_7934(place);
            } else if (class_1799.method_31577((class_1799)current, (class_1799)remainder) && (room = Math.min(current.method_7914(), this.input.method_5444()) - current.method_7947()) > 0) {
                int place = Math.min(room, remainder.method_7947());
                current.method_7933(place);
                remainder.method_7934(place);
            }
            if (remainder.method_7960() || player.method_31548().method_7394(remainder)) continue;
            player.method_7328(remainder, false);
        }
        this.input.method_5431();
    }

    public void method_7654(class_1662 finder) {
        this.input.method_7683(finder);
    }

    public void method_7657() {
        this.input.method_5448();
        this.result.method_5448();
    }

    public boolean method_7652(class_1860<? super class_8566> recipe) {
        return recipe.method_8115((class_1263)this.input, this.player.method_37908());
    }

    public int method_7655() {
        return 0;
    }

    public int method_7653() {
        return 4;
    }

    public int method_7656() {
        return 4;
    }

    public int method_7658() {
        return 17;
    }

    public class_5421 method_30264() {
        return class_5421.field_25763;
    }

    public boolean method_32339(int index) {
        return index != 0;
    }

    public void method_17697(boolean craftAll, class_1860<?> recipe, class_3222 player) {
        if (recipe instanceof MachineWorkshopRecipe) {
            MachineWorkshopRecipe workshopRecipe = (MachineWorkshopRecipe)recipe;
            this.fillCountedWorkshopRecipe(workshopRecipe, player, craftAll);
            return;
        }
        super.method_17697(craftAll, recipe, player);
    }

    private void fillCountedWorkshopRecipe(MachineWorkshopRecipe recipe, class_3222 player, boolean craftAll) {
        this.returnCraftingGridToPlayer(player);
        int inventorySize = player.method_31548().method_5439();
        int[] remaining = new int[inventorySize];
        for (int slot = 0; slot < inventorySize; ++slot) {
            remaining[slot] = player.method_31548().method_5438(slot).method_7947();
        }
        class_1799[] plannedGrid = new class_1799[16];
        for (int i = 0; i < plannedGrid.length; ++i) {
            plannedGrid[i] = class_1799.field_8037;
        }
        int offsetX = Math.max(0, (4 - recipe.getRecipeWidth()) / 2);
        int offsetY = Math.max(0, (4 - recipe.getRecipeHeight()) / 2);
        for (int recipeY = 0; recipeY < recipe.getRecipeHeight(); ++recipeY) {
            for (int recipeX = 0; recipeX < recipe.getRecipeWidth(); ++recipeX) {
                int recipeIndex = recipeX + recipeY * recipe.getRecipeWidth();
                CountedIngredient counted = (CountedIngredient)recipe.getCountedIngredients().get(recipeIndex);
                if (counted.isEmpty()) continue;
                int required = counted.count();
                int gridIndex = recipeX + offsetX + (recipeY + offsetY) * 4;
                class_1799 planned = class_1799.field_8037;
                for (int invSlot = 0; invSlot < inventorySize && required > 0; ++invSlot) {
                    int take;
                    int room;
                    class_1799 candidate;
                    if (remaining[invSlot] <= 0 || (candidate = player.method_31548().method_5438(invSlot)).method_7960() || !counted.ingredient().method_8093(candidate) || !planned.method_7960() && !class_1799.method_31577((class_1799)planned, (class_1799)candidate)) continue;
                    int maxStack = Math.min(candidate.method_7914(), this.input.method_5444());
                    int n = room = planned.method_7960() ? maxStack : maxStack - planned.method_7947();
                    if (room <= 0 || (take = Math.min(required, Math.min(remaining[invSlot], room))) <= 0) continue;
                    if (planned.method_7960()) {
                        planned = candidate.method_46651(take);
                    } else {
                        planned.method_7933(take);
                    }
                    int n2 = invSlot;
                    remaining[n2] = remaining[n2] - take;
                    required -= take;
                }
                if (required > 0) {
                    player.field_13987.method_14364((class_2596)new class_2695(this.field_7763, (class_1860)recipe));
                    this.method_7609((class_1263)this.input);
                    return;
                }
                plannedGrid[gridIndex] = planned;
            }
        }
        for (int invSlot = 0; invSlot < inventorySize; ++invSlot) {
            class_1799 stack = player.method_31548().method_5438(invSlot);
            int used = stack.method_7947() - remaining[invSlot];
            if (used <= 0) continue;
            stack.method_7934(used);
        }
        for (int gridIndex = 0; gridIndex < 16; ++gridIndex) {
            if (plannedGrid[gridIndex].method_7960()) continue;
            this.input.method_5447(gridIndex, plannedGrid[gridIndex]);
        }
        this.input.method_5431();
        this.method_7609((class_1263)this.input);
    }

    private void returnCraftingGridToPlayer(class_3222 player) {
        for (int slot = 0; slot < this.input.method_5439(); ++slot) {
            class_1799 stack = this.input.method_5441(slot);
            if (stack.method_7960() || player.method_31548().method_7394(stack)) continue;
            player.method_7328(stack, false);
        }
        this.input.method_5448();
        this.result.method_5448();
    }

    public boolean method_7597(class_1657 player) {
        return MachineWorkshopScreenHandler.method_17695((class_3914)this.context, (class_1657)player, (class_2248)ModBlocks.MACHINE_WORKSHOP);
    }

    public class_1799 method_7601(class_1657 player, int slotIndex) {
        class_1799 resultStack = class_1799.field_8037;
        class_1735 slot = (class_1735)this.field_7761.get(slotIndex);
        if (slot == null || !slot.method_7681()) {
            return class_1799.field_8037;
        }
        class_1799 source = slot.method_7677();
        resultStack = source.method_7972();
        if (slotIndex == 0) {
            if (!this.method_7616(source, 17, this.field_7761.size(), true)) {
                return class_1799.field_8037;
            }
            slot.method_7670(source, resultStack);
        } else if (slotIndex >= 17 ? !this.method_7616(source, 1, 17, false) : !this.method_7616(source, 17, this.field_7761.size(), false)) {
            return class_1799.field_8037;
        }
        if (source.method_7960()) {
            slot.method_48931(class_1799.field_8037);
        } else {
            slot.method_7668();
        }
        if (source.method_7947() == resultStack.method_7947()) {
            return class_1799.field_8037;
        }
        slot.method_7667(player, source);
        return resultStack;
    }

    public void method_7595(class_1657 player) {
        super.method_7595(player);
        this.context.method_17393((world, pos) -> {
            if (!world.field_9236) {
                this.method_7607(player, (class_1263)this.input);
            }
        });
    }

    private final class WorkshopResultSlot
    extends class_1734 {
        WorkshopResultSlot(class_1657 player, class_8566 input, class_1263 inventory, int index, int x, int y) {
            super(player, input, inventory, index, x, y);
        }

        public void method_7667(class_1657 player, class_1799 stack) {
            class_1860 class_18602 = MachineWorkshopScreenHandler.this.result.method_7663();
            if (class_18602 instanceof MachineWorkshopRecipe) {
                MachineWorkshopRecipe workshopRecipe = (MachineWorkshopRecipe)class_18602;
                this.method_7669(stack);
                MachineWorkshopScreenHandler.this.consumeCountedRecipe(workshopRecipe, player);
                MachineWorkshopScreenHandler.this.method_7609((class_1263)MachineWorkshopScreenHandler.this.input);
                return;
            }
            super.method_7667(player, stack);
        }
    }
}

