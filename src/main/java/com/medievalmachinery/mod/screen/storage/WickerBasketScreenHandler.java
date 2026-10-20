/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1277
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1735
 *  net.minecraft.class_1799
 *  net.minecraft.class_2338
 *  net.minecraft.class_2540
 *  net.minecraft.class_2586
 */
package com.medievalmachinery.mod.screen.storage;

import com.medievalmachinery.mod.platform.RegisteredContent;
import net.minecraft.class_1263;
import net.minecraft.class_1277;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2586;

public final class WickerBasketScreenHandler
extends class_1703 {
    private static final int BASKET_SIZE = 8;
    private final class_1263 inventory;

    public WickerBasketScreenHandler(int syncId, class_1661 playerInventory, class_2540 buf) {
        this(syncId, playerInventory, WickerBasketScreenHandler.findInventory(playerInventory, buf.method_10811()));
    }

    public WickerBasketScreenHandler(int syncId, class_1661 playerInventory, class_1263 inventory) {
        super(RegisteredContent.wickerBasketScreen(), syncId);
        int column;
        int row;
        WickerBasketScreenHandler.method_17359((class_1263)inventory, (int)8);
        this.inventory = inventory;
        inventory.method_5435(playerInventory.field_7546);
        for (row = 0; row < 2; ++row) {
            for (column = 0; column < 4; ++column) {
                int slot = column + row * 4;
                this.method_7621(new class_1735(inventory, slot, 53 + column * 18, 18 + row * 18));
            }
        }
        for (row = 0; row < 3; ++row) {
            for (column = 0; column < 9; ++column) {
                this.method_7621(new class_1735((class_1263)playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
        for (int column2 = 0; column2 < 9; ++column2) {
            this.method_7621(new class_1735((class_1263)playerInventory, column2, 8 + column2 * 18, 142));
        }
    }

    private static class_1263 findInventory(class_1661 playerInventory, class_2338 pos) {
        class_1263 inventory;
        class_2586 class_25862 = playerInventory.field_7546.method_37908().method_8321(pos);
        if (class_25862 instanceof class_1263 && (inventory = (class_1263)class_25862).method_5439() == 8) {
            return inventory;
        }
        return new class_1277(8);
    }

    public boolean method_7597(class_1657 player) {
        return this.inventory.method_5443(player);
    }

    public class_1799 method_7601(class_1657 player, int slotIndex) {
        class_1799 result = class_1799.field_8037;
        class_1735 slot = (class_1735)this.field_7761.get(slotIndex);
        if (slot != null && slot.method_7681()) {
            class_1799 source = slot.method_7677();
            result = source.method_7972();
            if (slotIndex < 8 ? !this.method_7616(source, 8, this.field_7761.size(), true) : !this.method_7616(source, 0, 8, false)) {
                return class_1799.field_8037;
            }
            if (source.method_7960()) {
                slot.method_48931(class_1799.field_8037);
            } else {
                slot.method_7668();
            }
        }
        return result;
    }

    public void method_7595(class_1657 player) {
        super.method_7595(player);
        this.inventory.method_5432(player);
    }
}

