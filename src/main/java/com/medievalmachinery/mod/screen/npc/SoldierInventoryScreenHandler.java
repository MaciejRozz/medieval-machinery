/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1277
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1735
 *  net.minecraft.class_1738
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1819
 *  net.minecraft.class_1937
 *  net.minecraft.class_3218
 *  net.minecraft.class_3913
 *  net.minecraft.class_3919
 */
package com.medievalmachinery.mod.screen.npc;

import com.medievalmachinery.mod.catapult.CatapultAmmunition;
import com.medievalmachinery.mod.entity.cannon.AbstractCannonEntity;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.npc.core.NpcCombatStance;
import com.medievalmachinery.mod.npc.core.NpcEquipmentProfile;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.QuartermasterCropPolicy;
import com.medievalmachinery.mod.npc.organization.SoldierControlPolicy;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.registry.ModItems;
import java.util.UUID;
import net.minecraft.class_1263;
import net.minecraft.class_1277;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1735;
import net.minecraft.class_1738;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1819;
import net.minecraft.class_1937;
import net.minecraft.class_3218;
import net.minecraft.class_3913;
import net.minecraft.class_3919;

public final class SoldierInventoryScreenHandler
extends class_1703 {
    private static final class_1304[] EQUIPMENT = new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166, class_1304.field_6173, class_1304.field_6171};
    private final class_1263 soldierStorage;
    private final class_1263 equipmentInventory;
    private final class_3913 properties;
    private final MedievalSoldierEntity soldier;

    public SoldierInventoryScreenHandler(int syncId, class_1661 playerInventory) {
        this(syncId, playerInventory, null, (class_1263)new class_1277(10), (class_1263)new class_1277(6), (class_3913)new class_3919(7));
    }

    public SoldierInventoryScreenHandler(int syncId, class_1661 playerInventory, MedievalSoldierEntity soldier) {
        this(syncId, playerInventory, soldier, soldier.getPersonalInventory(), new SoldierEquipmentInventory(soldier), SoldierInventoryScreenHandler.createProperties(soldier));
        soldier.setInventoryGuiOpen(true);
    }

    private SoldierInventoryScreenHandler(int syncId, class_1661 playerInventory, final MedievalSoldierEntity soldier, class_1263 soldierStorage, class_1263 equipmentInventory, class_3913 properties) {
        super(RegisteredContent.soldierInventoryScreen(), syncId);
        int row;
        this.soldier = soldier;
        this.soldierStorage = soldierStorage;
        this.equipmentInventory = equipmentInventory;
        this.properties = properties;
        SoldierInventoryScreenHandler.method_17359((class_1263)soldierStorage, (int)10);
        SoldierInventoryScreenHandler.method_17359((class_1263)equipmentInventory, (int)6);
        this.method_17360(properties);
        for (int i = 0; i < 4; ++i) {
            final class_1304 equipmentSlot = EQUIPMENT[i];
            this.method_7621(new class_1735(this, equipmentInventory, i, 18, 48 + i * 18){
                final /* synthetic */ SoldierInventoryScreenHandler this$0;
                {
                    this.this$0 = this$0;
                    super(arg0, arg1, arg2, arg3);
                }

                public boolean method_7680(class_1799 stack) {
                    class_1738 armor;
                    class_1792 class_17922 = stack.method_7909();
                    return class_17922 instanceof class_1738 && (armor = (class_1738)class_17922).method_7685() == equipmentSlot && (soldier == null || MedievalArmorItem.canNpcEquip(soldier, stack));
                }

                public int method_7675() {
                    return 1;
                }
            });
        }
        this.method_7621(new class_1735(equipmentInventory, 4, 54, 120){

            public boolean method_7680(class_1799 stack) {
                return SoldierInventoryScreenHandler.isAllowedMainHandItem(SoldierInventoryScreenHandler.this.getNpcRole(), stack);
            }

            public int method_7675() {
                return 1;
            }
        });
        this.method_7621(new class_1735(equipmentInventory, 5, 36, 120){

            public int method_7675() {
                return 1;
            }
        });
        for (row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.method_7621(new class_1735(soldierStorage, col + row * 3, 240 + col * 18, 72 + row * 18));
            }
        }
        this.method_7621(new class_1735(soldierStorage, 9, 72, 120){

            public boolean method_7680(class_1799 stack) {
                return SoldierInventoryScreenHandler.this.getNpcRole() == NpcRole.CANNONEER && SoldierInventoryScreenHandler.this.isAllowedCannoneerSelector(stack) || SoldierInventoryScreenHandler.this.getNpcRole() == NpcRole.QUARTERMASTER && QuartermasterCropPolicy.isSupportedPlantingItem(stack);
            }

            public boolean method_7682() {
                return SoldierInventoryScreenHandler.this.getNpcRole() == NpcRole.CANNONEER || SoldierInventoryScreenHandler.this.getNpcRole() == NpcRole.QUARTERMASTER;
            }

            public int method_7675() {
                return 1;
            }
        });
        for (row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.method_7621(new class_1735((class_1263)playerInventory, col + row * 9 + 9, 69 + col * 18, 176 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.method_7621(new class_1735((class_1263)playerInventory, col, 69 + col * 18, 234));
        }
    }

    private static class_3913 createProperties(final MedievalSoldierEntity soldier) {
        return new class_3913(){

            public int method_17390(int index) {
                return switch (index) {
                    case 0 -> soldier.method_5628();
                    case 1 -> soldier.getNpcRole().ordinal();
                    case 2 -> soldier.getOrderType().ordinal();
                    case 3 -> soldier.getCombatStance().ordinal();
                    case 4 -> {
                        if (soldier.medievalMachinery$hasMachineAssignment()) {
                            yield 1;
                        }
                        yield 0;
                    }
                    case 5 -> {
                        if (soldier.isHoldFire()) {
                            yield 1;
                        }
                        yield 0;
                    }
                    case 6 -> SoldierInventoryScreenHandler.assignedMachineAmmoKind(soldier);
                    default -> 0;
                };
            }

            public void method_17391(int index, int value) {
            }

            public int method_17389() {
                return 7;
            }
        };
    }

    private static int assignedMachineAmmoKind(MedievalSoldierEntity soldier) {
        class_1297 machine;
        class_1937 class_19372;
        if (soldier == null || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return 0;
        }
        class_3218 world = (class_3218)class_19372;
        UUID machineId = soldier.medievalMachinery$getAssignedMachineId();
        class_1297 class_12972 = machine = machineId == null ? null : world.method_14190(machineId);
        if (machine instanceof CatapultEntity) {
            return 2;
        }
        if (machine instanceof AbstractCannonEntity) {
            return 1;
        }
        return 0;
    }

    private boolean isAllowedCannoneerSelector(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return false;
        }
        int machineKind = this.properties.method_17390(6);
        if (machineKind == 2) {
            return CatapultAmmunition.isValid(stack);
        }
        return ModItems.isCannonball(stack.method_7909());
    }

    public int getSoldierEntityId() {
        return this.properties.method_17390(0);
    }

    public NpcRole getNpcRole() {
        int ordinal = this.properties.method_17390(1);
        NpcRole[] values = NpcRole.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcRole.SWORDSMAN;
    }

    public NpcOrderType getNpcOrderType() {
        int ordinal = this.properties.method_17390(2);
        NpcOrderType[] values = NpcOrderType.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcOrderType.IDLE;
    }

    public boolean hasMachineAssignment() {
        return this.properties.method_17390(4) != 0;
    }

    public boolean isHoldFire() {
        return this.properties.method_17390(5) != 0;
    }

    public NpcCombatStance getNpcCombatStance() {
        int ordinal = this.properties.method_17390(3);
        NpcCombatStance[] values = NpcCombatStance.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : NpcCombatStance.NEUTRAL;
    }

    public MedievalSoldierEntity getServerSoldier() {
        return this.soldier;
    }

    public boolean method_7597(class_1657 player) {
        if (this.soldier == null) {
            return true;
        }
        return SoldierControlPolicy.canPersonallyControl(player, this.soldier);
    }

    public void method_7595(class_1657 player) {
        super.method_7595(player);
        if (this.soldier != null) {
            this.soldier.setInventoryGuiOpen(false);
        }
    }

    public class_1799 method_7601(class_1657 player, int index) {
        class_1799 result = class_1799.field_8037;
        class_1735 slot = (class_1735)this.field_7761.get(index);
        if (slot == null || !slot.method_7681()) {
            return result;
        }
        class_1799 stack = slot.method_7677();
        result = stack.method_7972();
        boolean npcStart = false;
        int npcEnd = 16;
        int playerStart = 16;
        int playerEnd = 52;
        if (index < 16) {
            if (!this.method_7616(stack, 16, 52, true)) {
                return class_1799.field_8037;
            }
        } else {
            boolean moved = false;
            class_1792 class_17922 = stack.method_7909();
            if (class_17922 instanceof class_1738) {
                int armorIndex;
                class_1738 armor = (class_1738)class_17922;
                switch (armor.method_7685()) {
                    case field_6169: {
                        int n = 0;
                        break;
                    }
                    case field_6174: {
                        int n = 1;
                        break;
                    }
                    case field_6172: {
                        int n = 2;
                        break;
                    }
                    case field_6166: {
                        int n = 3;
                        break;
                    }
                    default: {
                        int n = armorIndex = -1;
                    }
                }
                if (armorIndex >= 0 && !((class_1735)this.field_7761.get(armorIndex)).method_7681()) {
                    moved = this.method_7616(stack, armorIndex, armorIndex + 1, false);
                }
            }
            if (!moved && !((class_1735)this.field_7761.get(15)).method_7681() && (this.getNpcRole() == NpcRole.CANNONEER && this.isAllowedCannoneerSelector(stack) || this.getNpcRole() == NpcRole.QUARTERMASTER && QuartermasterCropPolicy.isSupportedPlantingItem(stack))) {
                moved = this.method_7616(stack, 15, 16, false);
            }
            if (!moved && !((class_1735)this.field_7761.get(5)).method_7681() && stack.method_7909() instanceof class_1819) {
                moved = this.method_7616(stack, 5, 6, false);
            }
            if (!moved && !((class_1735)this.field_7761.get(4)).method_7681() && SoldierInventoryScreenHandler.isPreferredMainHandItem(this.getNpcRole(), stack)) {
                moved = this.method_7616(stack, 4, 5, false);
            }
            if (!moved) {
                moved = this.method_7616(stack, 6, 15, false);
            }
            if (!moved) {
                return class_1799.field_8037;
            }
        }
        if (stack.method_7960()) {
            slot.method_48931(class_1799.field_8037);
        } else {
            slot.method_7668();
        }
        if (stack.method_7947() == result.method_7947()) {
            return class_1799.field_8037;
        }
        slot.method_7667(player, stack);
        return result;
    }

    private static boolean isAllowedMainHandItem(NpcRole role, class_1799 stack) {
        return NpcEquipmentProfile.acceptsMainHand(role, stack);
    }

    private static boolean isPreferredMainHandItem(NpcRole role, class_1799 stack) {
        return NpcEquipmentProfile.weaponPreference(role, stack) >= 100;
    }

    private static final class SoldierEquipmentInventory
    implements class_1263 {
        private final MedievalSoldierEntity soldier;

        private SoldierEquipmentInventory(MedievalSoldierEntity soldier) {
            this.soldier = soldier;
        }

        public int method_5439() {
            return EQUIPMENT.length;
        }

        public boolean method_5442() {
            for (class_1304 slot : EQUIPMENT) {
                if (this.soldier.method_6118(slot).method_7960()) continue;
                return false;
            }
            return true;
        }

        public class_1799 method_5438(int slot) {
            return this.valid(slot) ? this.soldier.method_6118(EQUIPMENT[slot]) : class_1799.field_8037;
        }

        public class_1799 method_5434(int slot, int amount) {
            if (!this.valid(slot)) {
                return class_1799.field_8037;
            }
            class_1304 equipmentSlot = EQUIPMENT[slot];
            class_1799 current = this.soldier.method_6118(equipmentSlot);
            if (current.method_7960()) {
                return class_1799.field_8037;
            }
            class_1799 removed = current.method_7971(amount);
            if (current.method_7960()) {
                this.soldier.method_5673(equipmentSlot, class_1799.field_8037);
            }
            this.method_5431();
            return removed;
        }

        public class_1799 method_5441(int slot) {
            if (!this.valid(slot)) {
                return class_1799.field_8037;
            }
            class_1304 equipmentSlot = EQUIPMENT[slot];
            class_1799 current = this.soldier.method_6118(equipmentSlot);
            if (current.method_7960()) {
                return class_1799.field_8037;
            }
            class_1799 removed = current.method_7972();
            this.soldier.method_5673(equipmentSlot, class_1799.field_8037);
            this.method_5431();
            return removed;
        }

        public void method_5447(int slot, class_1799 stack) {
            if (!this.valid(slot)) {
                return;
            }
            this.soldier.method_5673(EQUIPMENT[slot], stack);
            this.method_5431();
        }

        public void method_5431() {
            this.soldier.method_5971();
            this.soldier.onEquipmentInventoryChanged();
        }

        public boolean method_5443(class_1657 player) {
            return SoldierControlPolicy.canPersonallyControl(player, this.soldier) && player.method_5858((class_1297)this.soldier) <= 64.0;
        }

        public void method_5448() {
            for (class_1304 slot : EQUIPMENT) {
                this.soldier.method_5673(slot, class_1799.field_8037);
            }
            this.method_5431();
        }

        private boolean valid(int slot) {
            return slot >= 0 && slot < EQUIPMENT.length;
        }
    }
}

