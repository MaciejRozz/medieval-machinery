/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1277
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1735
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_2540
 */
package com.medievalmachinery.mod.screen.machine.cannon;

import com.medievalmachinery.mod.entity.cannon.FieldCannonEntity;
import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1263;
import net.minecraft.class_1277;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2540;

public final class FieldCannonScreenHandler
extends class_1703 {
    public static final int GUNPOWDER_SLOT = 0;
    public static final int CANNONBALL_SLOT = 1;
    private static final int MACHINE_SLOT_COUNT = 2;
    private final class_1263 inventory;
    private final int entityId;

    public FieldCannonScreenHandler(int syncId, class_1661 playerInventory, class_2540 buffer) {
        this(syncId, playerInventory, FieldCannonScreenHandler.resolveInventory(playerInventory, buffer.method_10816()));
    }

    private FieldCannonScreenHandler(int syncId, class_1661 playerInventory, ResolvedInventory resolved) {
        this(syncId, playerInventory, resolved.inventory(), resolved.entityId());
    }

    public FieldCannonScreenHandler(int syncId, class_1661 playerInventory, class_1263 inventory, int entityId) {
        super(RegisteredContent.fieldCannonScreen(), syncId);
        FieldCannonScreenHandler.method_17359((class_1263)inventory, (int)2);
        this.inventory = inventory;
        this.entityId = entityId;
        inventory.method_5435(playerInventory.field_7546);
        this.method_7621(new class_1735(inventory, 0, 145, 54){

            public boolean method_7680(class_1799 stack) {
                return stack.method_31574(class_1802.field_8054);
            }

            public boolean method_7674(class_1657 player) {
                MedievalMachineryOperable operable;
                class_1263 class_12632 = this.field_7871;
                return !(class_12632 instanceof MedievalMachineryOperable) || (operable = (MedievalMachineryOperable)class_12632).medievalMachinery$canPlayerTakeMachineSlot(this.method_34266());
            }

            public int method_7675() {
                return 1;
            }
        });
        this.method_7621(new class_1735(inventory, 1, 34, 25){

            public boolean method_7680(class_1799 stack) {
                return ModItems.isCannonball(stack.method_7909());
            }

            public boolean method_7674(class_1657 player) {
                MedievalMachineryOperable operable;
                class_1263 class_12632 = this.field_7871;
                return !(class_12632 instanceof MedievalMachineryOperable) || (operable = (MedievalMachineryOperable)class_12632).medievalMachinery$canPlayerTakeMachineSlot(this.method_34266());
            }

            public int method_7675() {
                return 1;
            }
        });
        this.addPlayerInventory(playerInventory);
        this.addPlayerHotbar(playerInventory);
    }

    private static ResolvedInventory resolveInventory(class_1661 playerInventory, int entityId) {
        class_1297 entity = playerInventory.field_7546.method_37908().method_8469(entityId);
        if (entity instanceof FieldCannonEntity) {
            FieldCannonEntity cannon = (FieldCannonEntity)entity;
            return new ResolvedInventory(cannon, entityId);
        }
        return new ResolvedInventory((class_1263)new class_1277(2), entityId);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public class_1263 getMachineInventory() {
        return this.inventory;
    }

    public boolean method_7597(class_1657 player) {
        return this.inventory.method_5443(player);
    }

    public class_1799 method_7601(class_1657 player, int slotIndex) {
        class_1735 sourceSlot = (class_1735)this.field_7761.get(slotIndex);
        if (!sourceSlot.method_7681()) {
            return class_1799.field_8037;
        }
        class_1799 source = sourceSlot.method_7677();
        class_1799 original = source.method_7972();
        if (slotIndex < 2) {
            if (!sourceSlot.method_7674(player)) {
                return class_1799.field_8037;
            }
            if (!this.method_7616(source, 2, this.field_7761.size(), true)) {
                return class_1799.field_8037;
            }
        } else {
            int targetSlot;
            if (source.method_31574(class_1802.field_8054)) {
                targetSlot = 0;
            } else if (ModItems.isCannonball(source.method_7909())) {
                targetSlot = 1;
            } else {
                return class_1799.field_8037;
            }
            class_1735 machineSlot = (class_1735)this.field_7761.get(targetSlot);
            if (machineSlot.method_7681() || !machineSlot.method_7680(source)) {
                return class_1799.field_8037;
            }
            class_1799 loaded = source.method_7971(1);
            machineSlot.method_48931(loaded);
            machineSlot.method_7668();
        }
        if (source.method_7960()) {
            sourceSlot.method_48931(class_1799.field_8037);
        } else {
            sourceSlot.method_7668();
        }
        if (source.method_7947() == original.method_7947()) {
            return class_1799.field_8037;
        }
        sourceSlot.method_7667(player, source);
        return original;
    }

    public void method_7595(class_1657 player) {
        super.method_7595(player);
        this.inventory.method_5432(player);
    }

    private void addPlayerInventory(class_1661 playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.method_7621(new class_1735((class_1263)playerInventory, column + row * 9 + 9, 8 + column * 18, 107 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(class_1661 playerInventory) {
        for (int column = 0; column < 9; ++column) {
            this.method_7621(new class_1735((class_1263)playerInventory, column, 8 + column * 18, 165));
        }
    }

    private record ResolvedInventory(class_1263 inventory, int entityId) {
    }
}

