/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1713
 *  net.minecraft.class_1729
 *  net.minecraft.class_1735
 *  net.minecraft.class_1860
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_332
 *  net.minecraft.class_344
 *  net.minecraft.class_364
 *  net.minecraft.class_465
 *  net.minecraft.class_507
 *  net.minecraft.class_518
 */
package com.medievalmachinery.mod.client.workshop;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.client.workshop.MachineWorkshopRecipeBookWidget;
import com.medievalmachinery.mod.recipe.workshop.CountedIngredient;
import com.medievalmachinery.mod.recipe.workshop.MachineWorkshopRecipe;
import com.medievalmachinery.mod.screen.workshop.MachineWorkshopScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1729;
import net.minecraft.class_1735;
import net.minecraft.class_1860;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_344;
import net.minecraft.class_364;
import net.minecraft.class_465;
import net.minecraft.class_507;
import net.minecraft.class_518;

@Environment(value=EnvType.CLIENT)
public final class MachineWorkshopScreen
extends class_465<MachineWorkshopScreenHandler>
implements class_518 {
    private static final class_2960 TEXTURE = MedievalMachinery.id("textures/gui/machine_workshop.png");
    private static final class_2960 RECIPE_BUTTON_TEXTURE = new class_2960("minecraft", "textures/gui/recipe_button.png");
    private final MachineWorkshopRecipeBookWidget recipeBook = new MachineWorkshopRecipeBookWidget();
    private boolean narrow;
    private class_344 recipeBookButton;

    public MachineWorkshopScreen(MachineWorkshopScreenHandler handler, class_1661 inventory, class_2561 title) {
        super((class_1703)handler, inventory, title);
        this.field_2792 = 176;
        this.field_2779 = 184;
        this.field_25269 = 8;
        this.field_25270 = 90;
        this.field_25267 = 8;
        this.field_25268 = 6;
    }

    protected void method_25426() {
        super.method_25426();
        this.narrow = this.field_22789 < 410;
        this.recipeBook.method_2597(this.field_22789, this.field_22790, this.field_22787, this.narrow, (class_1729)this.field_2797);
        this.field_2776 = this.recipeBook.method_2595(this.field_22789, this.field_2792);
        this.recipeBookButton = new class_344(this.field_2776 + 5, this.field_22790 / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, 256, 256, button -> {
            this.recipeBook.method_2591();
            this.field_2776 = this.recipeBook.method_2595(this.field_22789, this.field_2792);
            button.method_48229(this.field_2776 + 5, this.field_22790 / 2 - 49);
        });
        this.method_37063((class_364)this.recipeBookButton);
        this.method_25429((class_364)this.recipeBook);
    }

    public void method_37432() {
        super.method_37432();
        this.recipeBook.method_2590();
    }

    protected void method_2389(class_332 context, float delta, int mouseX, int mouseY) {
        context.method_25290(TEXTURE, this.field_2776, this.field_2800, 0.0f, 0.0f, this.field_2792, this.field_2779, this.field_2792, this.field_2779);
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        if (this.recipeBook.method_2605() && this.narrow) {
            this.method_2389(context, delta, mouseX, mouseY);
            this.recipeBook.method_25394(context, mouseX, mouseY, delta);
        } else {
            this.recipeBook.method_25394(context, mouseX, mouseY, delta);
            super.method_25394(context, mouseX, mouseY, delta);
            this.recipeBook.method_2581(context, this.field_2776, this.field_2800, true, delta);
            this.drawGhostIngredientCounts(context);
        }
        this.recipeBook.method_2601(context, this.field_2776, this.field_2800, mouseX, mouseY);
        this.method_2380(context, mouseX, mouseY);
    }

    private void drawGhostIngredientCounts(class_332 context) {
        class_1860<?> class_18602 = this.recipeBook.getGhostRecipe();
        if (!(class_18602 instanceof MachineWorkshopRecipe)) {
            return;
        }
        MachineWorkshopRecipe recipe = (MachineWorkshopRecipe)class_18602;
        int offsetX = Math.max(0, (4 - recipe.getRecipeWidth()) / 2);
        int offsetY = Math.max(0, (4 - recipe.getRecipeHeight()) / 2);
        for (int recipeY = 0; recipeY < recipe.getRecipeHeight(); ++recipeY) {
            for (int recipeX = 0; recipeX < recipe.getRecipeWidth(); ++recipeX) {
                int count = ((CountedIngredient)recipe.getCountedIngredients().get(recipeX + recipeY * recipe.getRecipeWidth())).count();
                if (count <= 1) continue;
                int gridX = recipeX + offsetX;
                int gridY = recipeY + offsetY;
                int slotX = this.field_2776 + 30 + gridX * 18;
                int slotY = this.field_2800 + 17 + gridY * 18;
                String amount = Integer.toString(count);
                context.method_51433(this.field_22793, amount, slotX + 17 - this.field_22793.method_1727(amount), slotY + 9, 0xFFFFFF, true);
            }
        }
    }

    protected void method_2383(class_1735 slot, int slotId, int button, class_1713 actionType) {
        super.method_2383(slot, slotId, button, actionType);
        this.recipeBook.method_2600(slot);
    }

    public boolean method_25402(double mouseX, double mouseY, int button) {
        if (this.recipeBook.method_25402(mouseX, mouseY, button)) {
            this.method_25395((class_364)this.recipeBook);
            return true;
        }
        return this.narrow && this.recipeBook.method_2605() || super.method_25402(mouseX, mouseY, button);
    }

    public boolean method_25404(int keyCode, int scanCode, int modifiers) {
        return this.recipeBook.method_25404(keyCode, scanCode, modifiers) || super.method_25404(keyCode, scanCode, modifiers);
    }

    public boolean method_25400(char chr, int modifiers) {
        return this.recipeBook.method_25400(chr, modifiers) || super.method_25400(chr, modifiers);
    }

    protected boolean method_2381(double mouseX, double mouseY, int left, int top, int button) {
        boolean outsideMain = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.field_2792) || mouseY >= (double)(top + this.field_2779);
        return this.recipeBook.method_2598(mouseX, mouseY, this.field_2776, this.field_2800, this.field_2792, this.field_2779, button) && outsideMain;
    }

    public void method_16891() {
        this.recipeBook.method_2579();
    }

    public class_507 method_2659() {
        return this.recipeBook;
    }
}

