/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1258
 *  net.minecraft.class_1263
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1738
 *  net.minecraft.class_1799
 *  net.minecraft.class_1819
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2350$class_2353
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_2741
 *  net.minecraft.class_2745
 *  net.minecraft.class_2769
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3417
 *  net.minecraft.class_3419
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 *  net.minecraft.class_4174
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.machine.core.MachineCrewSupplyNeed;
import com.medievalmachinery.mod.machine.core.MachineCrewWorkContext;
import com.medievalmachinery.mod.npc.core.NpcEquipmentProfile;
import com.medievalmachinery.mod.npc.core.NpcLeadershipPolicy;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcSupplyRequest;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineRolePolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.class_1258;
import net.minecraft.class_1263;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1738;
import net.minecraft.class_1799;
import net.minecraft.class_1819;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2745;
import net.minecraft.class_2769;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_4174;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierSupplyController {
    private static final Map<class_3218, Map<class_2338, Integer>> OPEN_SUPPLY_CHESTS = new WeakHashMap<class_3218, Map<class_2338, Integer>>();

    MedievalSoldierSupplyController() {
    }

    void openSupplyChestVisual(MedievalSoldierEntity soldier) {
        class_1937 class_19372;
        if (soldier.supplySupplyState().chestVisualOpen || soldier.supplySupplyState().chest == null || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_2338 chest = soldier.supplySupplyState().chest.method_10062();
        class_2586 blockEntity = serverWorld.method_8321(chest);
        if (blockEntity == null) {
            return;
        }
        Map counts = OPEN_SUPPLY_CHESTS.computeIfAbsent(serverWorld, ignored -> new HashMap());
        int before = counts.getOrDefault(chest, 0);
        counts.put(chest, before + 1);
        if (before == 0) {
            serverWorld.method_8427(chest, blockEntity.method_11010().method_26204(), 1, 1);
            serverWorld.method_8396(null, chest, class_3417.field_14982, class_3419.field_15245, 0.65f, 0.95f + soldier.method_6051().method_43057() * 0.1f);
        }
        soldier.supplySupplyState().chestVisualOpen = true;
    }

    void openWorkChestVisual(MedievalSoldierEntity soldier, class_2338 chest) {
        class_1937 class_19372;
        if (chest == null || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_2586 blockEntity = serverWorld.method_8321(chest);
        if (blockEntity == null) {
            return;
        }
        Map counts = OPEN_SUPPLY_CHESTS.computeIfAbsent(serverWorld, ignored -> new HashMap());
        int before = counts.getOrDefault(chest, 0);
        counts.put(chest, before + 1);
        if (before == 0) {
            serverWorld.method_8427(chest, blockEntity.method_11010().method_26204(), 1, 1);
            serverWorld.method_8396(null, chest, class_3417.field_14982, class_3419.field_15245, 0.65f, 0.95f + soldier.method_6051().method_43057() * 0.1f);
        }
    }

    void closeWorkChestVisual(MedievalSoldierEntity soldier, class_2338 chest) {
        class_2586 blockEntity;
        class_1937 class_19372;
        if (chest == null || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        Map<class_2338, Integer> counts = OPEN_SUPPLY_CHESTS.get(serverWorld);
        int remaining = 0;
        if (counts != null) {
            int before = counts.getOrDefault(chest, 0);
            remaining = Math.max(0, before - 1);
            if (remaining == 0) {
                counts.remove(chest);
            } else {
                counts.put(chest, remaining);
            }
            if (counts.isEmpty()) {
                OPEN_SUPPLY_CHESTS.remove(serverWorld);
            }
        }
        if (remaining == 0 && (blockEntity = serverWorld.method_8321(chest)) != null) {
            serverWorld.method_8427(chest, blockEntity.method_11010().method_26204(), 1, 0);
            serverWorld.method_8396(null, chest, class_3417.field_14823, class_3419.field_15245, 0.55f, 0.95f + soldier.method_6051().method_43057() * 0.1f);
        }
    }

    class_243 findSupplyInteractionPoint(MedievalSoldierEntity soldier, class_2338 chest) {
        if (chest == null) {
            return soldier.method_19538();
        }
        boolean mounted = soldier.isActivelyMountedRole();
        int[][] footOffsets = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        int[][] horseOffsets = new int[][]{{2, 0}, {0, 2}, {-2, 0}, {0, -2}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
        int[][] offsets = mounted ? horseOffsets : footOffsets;
        class_1937 world = soldier.method_37908();
        class_243 best = null;
        double bestScore = Double.MAX_VALUE;
        int tieSeed = Math.floorMod(soldier.method_5667().hashCode() ^ chest.hashCode(), offsets.length);
        for (int i = 0; i < offsets.length; ++i) {
            int[] offset = offsets[i];
            class_2338 feet = new class_2338(chest.method_10263() + offset[0], chest.method_10264(), chest.method_10260() + offset[1]);
            if (!world.method_8320(feet).method_26220((class_1922)world, feet).method_1110() || !world.method_8320(feet.method_10084()).method_26220((class_1922)world, feet.method_10084()).method_1110() || mounted && !world.method_8320(feet.method_10086(2)).method_26220((class_1922)world, feet.method_10086(2)).method_1110() || world.method_8320(feet.method_10074()).method_26220((class_1922)world, feet.method_10074()).method_1110()) continue;
            class_243 point = new class_243((double)feet.method_10263() + 0.5, (double)feet.method_10264(), (double)feet.method_10260() + 0.5);
            double score = soldier.method_5707(point);
            if (!((score += (double)Math.floorMod(i - tieSeed, offsets.length) * 0.015) < bestScore)) continue;
            bestScore = score;
            best = point;
        }
        return best != null ? best : new class_243((double)chest.method_10263() + 0.5, (double)chest.method_10264(), (double)chest.method_10260() + 0.5);
    }

    boolean canPhysicallyInteractWithSupplyChest(MedievalSoldierEntity soldier, class_2338 chest) {
        if (chest == null) {
            return false;
        }
        class_243 from = new class_243(soldier.method_23317(), soldier.method_23323(0.55), soldier.method_23321());
        class_243 to = new class_243((double)chest.method_10263() + 0.5, (double)chest.method_10264() + 0.55, (double)chest.method_10260() + 0.5);
        class_3965 hit = soldier.method_37908().method_17742(new class_3959(from, to, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)soldier));
        return hit.method_17783() == class_239.class_240.field_1333 || hit.method_17784().method_1025(to) <= 1.1;
    }

    @Nullable
    class_1263 resolveSupplyInventory(MedievalSoldierEntity soldier, class_3218 world, class_2338 pos) {
        class_2586 firstBe = world.method_8321(pos);
        if (!(firstBe instanceof class_1263)) {
            return null;
        }
        class_1263 first = (class_1263)firstBe;
        class_2680 state = world.method_8320(pos);
        if (!state.method_28498((class_2769)class_2741.field_12506) || state.method_11654((class_2769)class_2741.field_12506) == class_2745.field_12569) {
            return first;
        }
        for (class_2350 direction : class_2350.class_2353.field_11062) {
            class_2586 otherBe;
            class_2338 otherPos = pos.method_10093(direction);
            class_2680 otherState = world.method_8320(otherPos);
            if (otherState.method_26204() != state.method_26204() || !otherState.method_28498((class_2769)class_2741.field_12506) || otherState.method_11654((class_2769)class_2741.field_12506) == class_2745.field_12569 || !((otherBe = world.method_8321(otherPos)) instanceof class_1263)) continue;
            class_1263 second = (class_1263)otherBe;
            return new class_1258(first, second);
        }
        return first;
    }

    boolean ensureReplacementWeaponFromSupply(MedievalSoldierEntity soldier, class_1263 inventory) {
        return soldier.supplyEquipmentController().ensureReplacementWeaponFromSupply(soldier, inventory);
    }

    boolean insertIntoSupplyInventory(MedievalSoldierEntity soldier, class_1263 inventory, class_1799 stack) {
        return soldier.supplyEquipmentController().insertIntoSupplyInventory(soldier, inventory, stack);
    }

    int findBestSupplyArmorSlot(MedievalSoldierEntity soldier, class_1263 inventory, class_1304 equipSlot) {
        return soldier.supplyEquipmentController().findBestSupplyArmorSlot(soldier, inventory, equipSlot);
    }

    boolean hasCarriedFood(MedievalSoldierEntity soldier) {
        for (int slot = 0; slot < Math.min(9, soldier.supplyPersonalInventory().method_5439()); ++slot) {
            class_1799 stack = soldier.supplyPersonalInventory().method_5438(slot);
            if (stack.method_7960() || !stack.method_19267()) continue;
            return true;
        }
        return false;
    }

    int carriedFoodNutrition(MedievalSoldierEntity soldier) {
        int nutrition = 0;
        for (int i = 0; i < Math.min(9, soldier.supplyPersonalInventory().method_5439()); ++i) {
            class_4174 food;
            class_1799 carried = soldier.supplyPersonalInventory().method_5438(i);
            if (carried.method_7960() || !carried.method_19267() || (food = carried.method_7909().method_19264()) == null) continue;
            nutrition += Math.max(0, food.method_19230()) * carried.method_7947();
        }
        return nutrition;
    }

    void closeSupplyChestVisual(MedievalSoldierEntity soldier) {
        class_2586 blockEntity;
        class_1937 class_19372;
        if (!soldier.supplySupplyState().chestVisualOpen || soldier.supplySupplyState().chest == null || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            soldier.supplySupplyState().chestVisualOpen = false;
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_2338 chest = soldier.supplySupplyState().chest.method_10062();
        Map<class_2338, Integer> counts = OPEN_SUPPLY_CHESTS.get(serverWorld);
        int remaining = 0;
        if (counts != null) {
            int before = counts.getOrDefault(chest, 0);
            remaining = Math.max(0, before - 1);
            if (remaining == 0) {
                counts.remove(chest);
            } else {
                counts.put(chest, remaining);
            }
            if (counts.isEmpty()) {
                OPEN_SUPPLY_CHESTS.remove(serverWorld);
            }
        }
        if (remaining == 0 && (blockEntity = serverWorld.method_8321(chest)) != null) {
            serverWorld.method_8427(chest, blockEntity.method_11010().method_26204(), 1, 0);
            serverWorld.method_8396(null, chest, class_3417.field_14823, class_3419.field_15245, 0.55f, 0.95f + soldier.method_6051().method_43057() * 0.1f);
        }
        soldier.supplySupplyState().chestVisualOpen = false;
    }

    boolean isSupplyFieldKitSatisfied(MedievalSoldierEntity soldier) {
        if (soldier.carriedFoodNutrition() < soldier.supplySUPPLY_RATION_NUTRITION_TARGET()) {
            return false;
        }
        if (!soldier.isRoleEquipmentReady()) {
            return false;
        }
        if (!soldier.hasUsableShield()) {
            return false;
        }
        for (class_1304 slot : new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166}) {
            if (!soldier.method_6118(slot).method_7960()) continue;
            return false;
        }
        if (soldier.isRangedRole() && !soldier.hasCarriedCloseCombatSidearm()) {
            return false;
        }
        return this.assignedMachineSupplyNeeds(soldier).stream().allMatch(need -> soldier.medievalMachinery$countMachineResource(need.item()) >= need.minimumCount());
    }

    boolean isSupplyRequestSatisfied(MedievalSoldierEntity soldier, NpcSupplyRequest request) {
        return switch (request) {
            default -> throw new IncompatibleClassChangeError();
            case NpcSupplyRequest.SUPPLY -> this.isSupplyFieldKitSatisfied(soldier);
            case NpcSupplyRequest.AUTO_FOOD -> {
                if (soldier.carriedFoodNutrition() >= soldier.supplySUPPLY_RATION_NUTRITION_TARGET()) {
                    yield true;
                }
                yield false;
            }
            case NpcSupplyRequest.AUTO_WEAPON -> {
                if (!soldier.method_6047().method_7960()) {
                    yield true;
                }
                yield false;
            }
            case NpcSupplyRequest.NONE -> true;
        };
    }

    boolean takeAutomaticBrokenWeaponReplacementFromSupply(MedievalSoldierEntity soldier, class_1263 inventory) {
        if (!soldier.method_6047().method_7960()) {
            soldier.equipmentMaintenanceState().automaticWeaponReplacementNeeded = false;
            return false;
        }
        int bestSlot = -1;
        long bestScore = Long.MIN_VALUE;
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            long score;
            class_1799 candidate = inventory.method_5438(slot);
            if (candidate.method_7960() || !NpcEquipmentProfile.acceptsMainHand(soldier.getNpcRole(), candidate) || (score = soldier.getWeaponScore(candidate)) <= bestScore) continue;
            bestScore = score;
            bestSlot = slot;
        }
        if (bestSlot < 0) {
            return false;
        }
        class_1799 source = inventory.method_5438(bestSlot);
        class_1799 replacement = source.method_7972();
        replacement.method_7939(1);
        soldier.method_5673(class_1304.field_6173, replacement);
        source.method_7934(1);
        soldier.equipmentMaintenanceState().automaticWeaponReplacementNeeded = false;
        soldier.onEquipmentInventoryChanged();
        soldier.updateWeaponClassDamageModifier();
        return true;
    }

    private List<MachineCrewSupplyNeed> assignedMachineSupplyNeeds(MedievalSoldierEntity soldier) {
        class_1297 entity;
        class_3218 world;
        block6: {
            block5: {
                class_1937 class_19372 = soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block5;
                world = (class_3218)class_19372;
                if (soldier.medievalMachinery$hasMachineAssignment()) break block6;
            }
            return List.of();
        }
        UUID machineId = soldier.medievalMachinery$getAssignedMachineId();
        class_1297 class_12972 = entity = machineId == null ? null : world.method_14190(machineId);
        if (!(entity instanceof MedievalMachineryOperable)) {
            return List.of();
        }
        MedievalMachineryOperable machine = (MedievalMachineryOperable)entity;
        MachineCrewRole role = soldier.medievalMachinery$getAssignedMachineCrewRole();
        if (role == null) {
            return List.of();
        }
        NpcLeadershipPolicy.Strength leadership = soldier.getNearbyLeadershipStrength();
        MachineCrewWorkContext context = new MachineCrewWorkContext(NpcLeadershipPolicy.machineCycleTimeMultiplier(leadership), NpcLeadershipPolicy.machineAimSpreadMultiplier(leadership), NpcMachineRolePolicy.mayUseVirtualStandardMachineResources(soldier.getNpcRole(), role));
        List<MachineCrewSupplyNeed> needs = machine.medievalMachinery$getNpcCrewSupplyStockNeeds((class_1297)soldier, role, context);
        return needs == null ? List.of() : needs;
    }

    boolean takeAssignedMachineResourcesFromSupply(MedievalSoldierEntity soldier, class_1263 inventory) {
        boolean changed = false;
        block0: for (MachineCrewSupplyNeed need : this.assignedMachineSupplyNeeds(soldier)) {
            int carried = soldier.medievalMachinery$countMachineResource(need.item());
            int wanted = Math.max(0, need.desiredCount() - carried);
            if (wanted <= 0) continue;
            for (int slot = 0; slot < inventory.method_5439() && wanted > 0; ++slot) {
                class_1799 source = inventory.method_5438(slot);
                if (source.method_7960() || !source.method_31574(need.item())) continue;
                int requested = Math.min(wanted, source.method_7947());
                int stored = soldier.medievalMachinery$storeMachineResource(need.item(), requested);
                if (stored <= 0) continue block0;
                source.method_7934(stored);
                wanted -= stored;
                changed = true;
            }
        }
        return changed;
    }

    boolean takeFoodFromSupply(MedievalSoldierEntity soldier, class_1263 inventory) {
        int bestNutrition;
        int inserted;
        int carriedNutrition = soldier.carriedFoodNutrition();
        if (carriedNutrition >= soldier.supplySUPPLY_RATION_NUTRITION_TARGET()) {
            return false;
        }
        boolean changed = false;
        for (int wantedNutrition = soldier.supplySUPPLY_RATION_NUTRITION_TARGET() - carriedNutrition; wantedNutrition > 0; wantedNutrition -= inserted * bestNutrition) {
            int bestSlot = -1;
            bestNutrition = 0;
            float bestSaturation = -1.0f;
            for (int slot = 0; slot < inventory.method_5439(); ++slot) {
                class_4174 food;
                class_1799 source = inventory.method_5438(slot);
                if (source.method_7960() || !source.method_19267() || (food = source.method_7909().method_19264()) == null || food.method_19230() <= 0 || food.method_19230() <= bestNutrition && (food.method_19230() != bestNutrition || !(food.method_19231() > bestSaturation))) continue;
                bestSlot = slot;
                bestNutrition = food.method_19230();
                bestSaturation = food.method_19231();
            }
            if (bestSlot < 0 || bestNutrition <= 0) break;
            class_1799 source = inventory.method_5438(bestSlot);
            int neededItems = Math.max(1, (int)Math.ceil((double)wantedNutrition / (double)bestNutrition));
            int move = Math.min(neededItems, source.method_7947());
            class_1799 part = source.method_7972();
            part.method_7939(move);
            inserted = soldier.insertIntoPersonalStorageAndCount(part);
            if (inserted <= 0) break;
            source.method_7934(inserted);
            changed = true;
        }
        return changed;
    }

    boolean returnExcessEquipmentToSupply(MedievalSoldierEntity soldier, class_1263 inventory) {
        long score;
        class_1799 stack;
        int slot;
        class_1799 main = soldier.method_6047();
        int protectedFamilySlot = -1;
        long familyScore = Long.MIN_VALUE;
        int protectedSidearmSlot = -1;
        long sidearmScore = Long.MIN_VALUE;
        for (slot = 0; slot < Math.min(9, soldier.supplyPersonalInventory().method_5439()); ++slot) {
            stack = soldier.supplyPersonalInventory().method_5438(slot);
            if (!soldier.sameWeaponFamily(main, stack) || (score = soldier.getWeaponScore(stack)) <= familyScore) continue;
            familyScore = score;
            protectedFamilySlot = slot;
        }
        if (soldier.isRangedRole()) {
            for (slot = 0; slot < Math.min(9, soldier.supplyPersonalInventory().method_5439()); ++slot) {
                stack = soldier.supplyPersonalInventory().method_5438(slot);
                if (!soldier.isCloseCombatWeapon(stack) || (score = soldier.closeCombatWeaponScore(stack)) <= sidearmScore) continue;
                sidearmScore = score;
                protectedSidearmSlot = slot;
            }
        }
        boolean changed = false;
        for (int slot2 = 0; slot2 < Math.min(9, soldier.supplyPersonalInventory().method_5439()); ++slot2) {
            class_1799 moving;
            class_1799 stack2 = soldier.supplyPersonalInventory().method_5438(slot2);
            if (stack2.method_7960() || stack2.method_19267() || !NpcEquipmentProfile.isSupportedWeapon(stack2) && !(stack2.method_7909() instanceof class_1738) || soldier.isProtectedReserveWeapon(stack2, slot2, protectedFamilySlot, protectedSidearmSlot) || !soldier.insertIntoSupplyInventory(inventory, moving = stack2.method_7972())) continue;
            soldier.supplyPersonalInventory().method_5447(slot2, class_1799.field_8037);
            changed = true;
        }
        if (changed) {
            soldier.supplyPersonalInventory().method_5431();
        }
        return changed;
    }

    boolean takeEquipmentFromSupply(MedievalSoldierEntity soldier, class_1263 inventory) {
        int sidearmSlot;
        boolean changed = false;
        int bestWeaponSlot = -1;
        long bestWeaponScore = soldier.getWeaponScore(soldier.method_6047());
        for (int slot = 0; slot < inventory.method_5439(); ++slot) {
            class_1799 candidate = inventory.method_5438(slot);
            long score = soldier.getWeaponScore(candidate);
            if (score <= bestWeaponScore) continue;
            bestWeaponScore = score;
            bestWeaponSlot = slot;
        }
        if (bestWeaponSlot >= 0) {
            class_1799 source = inventory.method_5438(bestWeaponSlot);
            class_1799 replacement = source.method_7972();
            replacement.method_7939(1);
            class_1799 previous = soldier.method_6047().method_7972();
            soldier.method_5673(class_1304.field_6173, replacement);
            source.method_7934(1);
            if (!previous.method_7960()) {
                soldier.insertIntoPersonalStorage(previous);
            }
            changed = true;
        }
        if (soldier.isRangedRole() && !soldier.hasCarriedCloseCombatSidearm() && (sidearmSlot = soldier.chooseReserveSidearmSlot(inventory)) >= 0) {
            class_1799 source = inventory.method_5438(sidearmSlot);
            class_1799 one = source.method_7972();
            one.method_7939(1);
            int inserted = soldier.insertIntoPersonalStorageAndCount(one);
            if (inserted > 0) {
                source.method_7934(inserted);
                changed = true;
            }
        }
        if (!soldier.hasUsableShield()) {
            int bestShieldSlot = -1;
            long bestShieldScore = Long.MIN_VALUE;
            for (int slot = 0; slot < inventory.method_5439(); ++slot) {
                long remaining;
                long score;
                class_1799 candidate = inventory.method_5438(slot);
                if (!(candidate.method_7909() instanceof class_1819) || (score = (remaining = candidate.method_7963() ? (long)(candidate.method_7936() - candidate.method_7919()) : 100000L) + (candidate.method_7942() ? 250000L : 0L)) <= bestShieldScore) continue;
                bestShieldScore = score;
                bestShieldSlot = slot;
            }
            if (bestShieldSlot >= 0) {
                class_1799 source = inventory.method_5438(bestShieldSlot);
                class_1799 one = source.method_7972();
                one.method_7939(1);
                class_1799 previous = soldier.method_6079().method_7972();
                soldier.method_5673(class_1304.field_6171, one);
                source.method_7934(1);
                if (!previous.method_7960()) {
                    soldier.insertIntoPersonalStorage(previous);
                }
                changed = true;
            }
        }
        for (class_1304 equipSlot : new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166}) {
            class_1799 current;
            class_1799 candidate;
            int bestArmorSlot = soldier.findBestSupplyArmorSlot(inventory, equipSlot);
            if (bestArmorSlot < 0 || !soldier.isBetterArmorPiece(candidate = inventory.method_5438(bestArmorSlot), current = soldier.method_6118(equipSlot).method_7972())) continue;
            class_1799 one = candidate.method_7972();
            one.method_7939(1);
            candidate.method_7934(1);
            soldier.method_5673(equipSlot, one);
            if (!current.method_7960() && !soldier.insertIntoSupplyInventory(inventory, current)) {
                soldier.insertIntoPersonalStorage(current);
            }
            changed = true;
        }
        changed |= soldier.ensureReplacementWeaponFromSupply(inventory);
        return changed |= soldier.returnExcessEquipmentToSupply(inventory);
    }

    boolean beginEatingBestFood(MedievalSoldierEntity soldier) {
        if (soldier.method_6115()) {
            return false;
        }
        int bestSlot = -1;
        double bestScore = Double.NEGATIVE_INFINITY;
        int foodDeficit = Math.max(0, 20 - soldier.goalHungerData().getFoodLevel());
        boolean criticalRecovery = soldier.method_6032() <= soldier.method_6063() * 0.3f;
        boolean wounded = soldier.method_6032() < soldier.method_6063();
        for (int slot = 0; slot < Math.min(9, soldier.supplyPersonalInventory().method_5439()); ++slot) {
            class_4174 food;
            class_1799 stack = soldier.supplyPersonalInventory().method_5438(slot);
            if (stack.method_7960() || !stack.method_19267() || (food = stack.method_7909().method_19264()) == null || food.method_19230() <= 0) continue;
            int overfill = Math.max(0, food.method_19230() - Math.max(1, foodDeficit));
            double score = (double)food.method_19230() + (double)food.method_19231() * (criticalRecovery ? 14.0 : (wounded ? 11.0 : 8.0)) - (double)overfill * 1.25;
            if (!(score > bestScore)) continue;
            bestScore = score;
            bestSlot = slot;
        }
        if (bestSlot < 0) {
            return false;
        }
        class_1799 source = soldier.supplyPersonalInventory().method_5438(bestSlot);
        if (source.method_7960()) {
            return false;
        }
        soldier.supplyEatingState().previousOffhand = soldier.method_6079().method_7972();
        soldier.supplyEatingState().sourceSlot = bestSlot;
        class_1799 visibleFood = source.method_7972();
        visibleFood.method_7939(1);
        class_4174 visibleFoodComponent = visibleFood.method_7909().method_19264();
        float saturationModifier = visibleFoodComponent == null ? 0.0f : Math.max(0.0f, visibleFoodComponent.method_19231());
        soldier.supplyEatingState().pendingHeal = soldier.method_6032() < soldier.method_6063() ? Math.min(2.0f, 0.75f + saturationModifier * 1.25f) : 0.0f;
        soldier.supplyEatingState().pendingHunger = visibleFoodComponent == null ? 0 : Math.max(0, visibleFoodComponent.method_19230());
        soldier.supplyEatingState().pendingSaturationModifier = saturationModifier;
        source.method_7934(1);
        soldier.supplyPersonalInventory().method_5431();
        soldier.method_5673(class_1304.field_6171, visibleFood);
        soldier.supplyEatingState().ticksRemaining = Math.max(1, visibleFood.method_7935());
        soldier.method_6019(class_1268.field_5810);
        return true;
    }

    boolean serviceCurrentSupplyRequest(MedievalSoldierEntity soldier, class_2586 blockEntity, class_1263 inventory) {
        boolean changed;
        NpcSupplyRequest request = soldier.supplySupplyState().request;
        switch (request) {
            default: {
                throw new IncompatibleClassChangeError();
            }
            case SUPPLY: {
                boolean fullChanged = this.takeFoodFromSupply(soldier, inventory);
                fullChanged |= this.takeEquipmentFromSupply(soldier, inventory);
                boolean bl = fullChanged |= this.takeAssignedMachineResourcesFromSupply(soldier, inventory);
                break;
            }
            case AUTO_FOOD: {
                boolean bl = this.takeFoodFromSupply(soldier, inventory);
                break;
            }
            case AUTO_WEAPON: {
                boolean bl = this.takeAutomaticBrokenWeaponReplacementFromSupply(soldier, inventory);
                break;
            }
            case NONE: {
                boolean bl = changed = false;
            }
        }
        if (changed) {
            inventory.method_5431();
            blockEntity.method_5431();
            soldier.equipmentMaintenanceState().equipmentDirty = true;
            soldier.equipmentMaintenanceState().inventoryCacheDirty = true;
            soldier.equipmentMaintenanceState().registryDirty = true;
        }
        return this.isSupplyRequestSatisfied(soldier, request);
    }

    void finishSupplyRun(MedievalSoldierEntity soldier) {
        NpcOrderType orderBefore = soldier.supplySupplyState().orderBefore;
        class_243 originBefore = soldier.supplySupplyState().originPosition;
        soldier.closeSupplyChestVisual();
        soldier.supplySupplyState().clearRun();
        if (!(soldier.isGeneralBodyguard() || orderBefore != NpcOrderType.IDLE && orderBefore != NpcOrderType.HOLD_POSITION && orderBefore != NpcOrderType.OBSERVATION_POINT || originBefore == null)) {
            soldier.supplySupplyState().beginReturn();
            return;
        }
        soldier.restoreOrderAfterSupply();
        if (orderBefore == NpcOrderType.PATROL) {
            soldier.orderPatrolState().resetRepathCooldown();
        }
        soldier.resumeSuspendedOrderIfPossible();
    }

    void advanceSupplyChestOrFinish(MedievalSoldierEntity soldier) {
        soldier.closeSupplyChestVisual();
        if (!soldier.supplySupplyState().advanceChest()) {
            this.notifyMissingCoreSupply(soldier);
            soldier.finishSupplyRun();
        }
    }

    private void notifyMissingCoreSupply(MedievalSoldierEntity soldier) {
        boolean weaponStillMissing;
        class_3222 owner;
        NpcSupplyRequest request;
        block8: {
            block7: {
                class_1937 class_19372;
                request = soldier.supplySupplyState().request;
                if (request == NpcSupplyRequest.NONE || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
                    return;
                }
                class_3218 world = (class_3218)class_19372;
                UUID ownerId = soldier.getNpcIdentity().getOwnerId();
                class_3222 class_32222 = owner = ownerId == null ? null : world.method_8503().method_3760().method_14602(ownerId);
                if (owner == null) {
                    return;
                }
                if (request == NpcSupplyRequest.SUPPLY) break block7;
                if (request != NpcSupplyRequest.AUTO_FOOD) break block8;
            }
            if (soldier.carriedFoodNutrition() < soldier.supplySUPPLY_RATION_NUTRITION_TARGET()) {
                owner.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.npc.supply_no_food"), false);
            }
        }
        boolean bl = request == NpcSupplyRequest.AUTO_WEAPON ? soldier.method_6047().method_7960() : (weaponStillMissing = request == NpcSupplyRequest.SUPPLY && !soldier.isRoleEquipmentReady());
        if (weaponStillMissing) {
            owner.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.npc.supply_no_weapon"), false);
        }
    }
}

