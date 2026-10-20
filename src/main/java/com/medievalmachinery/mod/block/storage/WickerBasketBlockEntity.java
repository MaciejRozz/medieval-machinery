/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
 *  net.minecraft.class_1262
 *  net.minecraft.class_1263
 *  net.minecraft.class_1542
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2371
 *  net.minecraft.class_238
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2596
 *  net.minecraft.class_2602
 *  net.minecraft.class_2622
 *  net.minecraft.class_2680
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_7923
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.storage;

import com.medievalmachinery.mod.block.storage.WickerBasketBlockAccess;
import com.medievalmachinery.mod.registry.ModBlockEntities;
import com.medievalmachinery.mod.screen.storage.WickerBasketScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.class_1262;
import net.minecraft.class_1263;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2371;
import net.minecraft.class_238;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2596;
import net.minecraft.class_2602;
import net.minecraft.class_2622;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_7923;
import org.jetbrains.annotations.Nullable;

public final class WickerBasketBlockEntity
extends class_2586
implements WickerBasketBlockAccess,
class_1263,
ExtendedScreenHandlerFactory {
    public static final int SIZE = 8;
    private final class_2371<class_1799> inventory = class_2371.method_10213((int)8, (Object)class_1799.field_8037);
    private boolean suppressDrop;

    public WickerBasketBlockEntity(class_2338 pos, class_2680 state) {
        super(ModBlockEntities.WICKER_BASKET, pos, state);
    }

    public class_2371<class_1799> getItems() {
        return this.inventory;
    }

    public static void serverTick(class_1937 world, class_2338 pos, class_2680 state, WickerBasketBlockEntity basket) {
        if (!(world instanceof class_3218) || world.method_8510() % 5L != 0L) {
            return;
        }
        class_238 catchArea = new class_238(pos).method_1009(0.22, 0.65, 0.22);
        for (class_1542 entity : world.method_8390(class_1542.class, catchArea, item -> WickerBasketBlockEntity.isHeadItem(item.method_6983()))) {
            class_1799 remaining = entity.method_6983().method_7972();
            basket.insert(remaining);
            if (remaining.method_7960()) {
                entity.method_31472();
                continue;
            }
            entity.method_6979(remaining);
        }
    }

    private static boolean isHeadItem(class_1799 stack) {
        String path = class_7923.field_41178.method_10221((Object)stack.method_7909()).method_12832();
        return path.contains("head") || path.contains("skull");
    }

    public boolean insert(class_1799 incoming) {
        int i;
        if (incoming.method_7960()) {
            return true;
        }
        for (i = 0; i < this.inventory.size(); ++i) {
            class_1799 stored = (class_1799)this.inventory.get(i);
            if (stored.method_7960() || !class_1799.method_31577((class_1799)stored, (class_1799)incoming) || stored.method_7947() >= stored.method_7914()) continue;
            int moved = Math.min(incoming.method_7947(), stored.method_7914() - stored.method_7947());
            stored.method_7933(moved);
            incoming.method_7934(moved);
            if (!incoming.method_7960()) continue;
            this.markDirtyAndSync();
            return true;
        }
        for (i = 0; i < this.inventory.size(); ++i) {
            if (!((class_1799)this.inventory.get(i)).method_7960()) continue;
            this.inventory.set(i, (Object)incoming.method_7972());
            incoming.method_7939(0);
            this.markDirtyAndSync();
            return true;
        }
        this.markDirtyAndSync();
        return incoming.method_7960();
    }

    @Override
    public void suppressNextDrop() {
        this.suppressDrop = true;
    }

    @Override
    public boolean shouldSuppressDrop() {
        return this.suppressDrop;
    }

    @Override
    public class_1799 createBasketStack() {
        class_1799 stack = new class_1799((class_1935)this.method_11010().method_26204());
        class_2487 blockEntityTag = new class_2487();
        this.method_11007(blockEntityTag);
        stack.method_7948().method_10566("BlockEntityTag", (class_2520)blockEntityTag);
        return stack;
    }

    public int method_5439() {
        return this.inventory.size();
    }

    public boolean method_5442() {
        for (class_1799 stack : this.inventory) {
            if (stack.method_7960()) continue;
            return false;
        }
        return true;
    }

    public class_1799 method_5438(int slot) {
        return (class_1799)this.inventory.get(slot);
    }

    public class_1799 method_5434(int slot, int amount) {
        class_1799 result = class_1262.method_5430(this.inventory, (int)slot, (int)amount);
        if (!result.method_7960()) {
            this.markDirtyAndSync();
        }
        return result;
    }

    public class_1799 method_5441(int slot) {
        class_1799 result = class_1262.method_5428(this.inventory, (int)slot);
        if (!result.method_7960()) {
            this.markDirtyAndSync();
        }
        return result;
    }

    public void method_5447(int slot, class_1799 stack) {
        this.inventory.set(slot, (Object)stack);
        if (stack.method_7947() > this.method_5444()) {
            stack.method_7939(this.method_5444());
        }
        this.markDirtyAndSync();
    }

    public boolean method_5443(class_1657 player) {
        return this.field_11863 != null && this.field_11863.method_8321(this.field_11867) == this && player.method_5649((double)this.field_11867.method_10263() + 0.5, (double)this.field_11867.method_10264() + 0.5, (double)this.field_11867.method_10260() + 0.5) <= 64.0;
    }

    public void method_5448() {
        this.inventory.clear();
        this.markDirtyAndSync();
    }

    protected void method_11007(class_2487 nbt) {
        super.method_11007(nbt);
        class_1262.method_5426((class_2487)nbt, this.inventory);
    }

    public void method_11014(class_2487 nbt) {
        super.method_11014(nbt);
        this.inventory.clear();
        class_1262.method_5429((class_2487)nbt, this.inventory);
    }

    public class_2561 method_5476() {
        return class_2561.method_43471((String)"container.medieval-machinery.wicker_basket");
    }

    @Nullable
    public class_1703 createMenu(int syncId, class_1661 playerInventory, class_1657 player) {
        return new WickerBasketScreenHandler(syncId, playerInventory, this);
    }

    public void writeScreenOpeningData(class_3222 player, class_2540 buf) {
        buf.method_10807(this.field_11867);
    }

    public class_2487 method_16887() {
        return this.method_38244();
    }

    @Nullable
    public class_2596<class_2602> method_38235() {
        return class_2622.method_38585((class_2586)this);
    }

    private void markDirtyAndSync() {
        this.method_5431();
        if (this.field_11863 != null && !this.field_11863.field_9236) {
            this.field_11863.method_8413(this.field_11867, this.method_11010(), this.method_11010(), 3);
        }
    }

    @Override
    public void tickServer(class_1937 world, class_2338 pos, class_2680 state) {
        WickerBasketBlockEntity.serverTick(world, pos, state, this);
    }
}

