/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1263
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1542
 *  net.minecraft.class_1792
 *  net.minecraft.class_1794
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1922
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2302
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2350$class_2353
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_3417
 *  net.minecraft.class_3419
 *  net.minecraft.class_3962
 *  net.minecraft.class_5250
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.entity.QuartermasterComposterPoiService;
import com.medievalmachinery.mod.npc.entity.QuartermasterCropPolicy;
import com.medievalmachinery.mod.npc.entity.QuartermasterFarmState;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import java.util.Comparator;
import java.util.UUID;
import net.minecraft.class_1263;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1542;
import net.minecraft.class_1792;
import net.minecraft.class_1794;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1922;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2302;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3962;
import net.minecraft.class_5250;
import org.jetbrains.annotations.Nullable;

final class QuartermasterFarmingController {
    private static final int PERSONAL_BREAD_RESERVE = 10;
    private static final int BREAD_DELIVERY_BATCH = 10;
    private static final int PERSONAL_WHEAT_RESERVE = 3;
    private static final int COMPOST_BATCH_MINIMUM = 64;
    private Work work;
    private int nextPlanTick;
    private int hoeWarningCooldown;
    private int nextInspectTick;
    private boolean compostingBatch;
    @Nullable
    private class_2338 harvestPickupAnchor;
    private int harvestPickupEarliestReleaseTick;
    private int harvestPickupClearTicks;
    @Nullable
    private class_2338 pendingHarvestReplant;
    @Nullable
    private class_1792 pendingHarvestReplantItem;

    QuartermasterFarmingController() {
    }

    @Nullable
    Work currentWork() {
        return this.work;
    }

    void clearWork() {
        this.work = null;
    }

    void tickPassive(MedievalSoldierEntity soldier) {
        class_1937 class_19372;
        if (this.hoeWarningCooldown > 0) {
            --this.hoeWarningCooldown;
        }
        if (soldier.getNpcRole() != NpcRole.QUARTERMASTER || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return;
        }
        class_3218 world = (class_3218)class_19372;
        if (soldier.getOrderType() != NpcOrderType.WORK) {
            return;
        }
        QuartermasterFarmingController.craftBread(soldier);
        QuartermasterFarmingController.trimSeedsToComposter(soldier, world);
        if (soldier.isStaggeredAiTick(40) && soldier.quartermasterFarmState().composter != null) {
            QuartermasterComposterPoiService.suppress(world, soldier.quartermasterFarmState().composter);
        }
    }

    @Nullable
    Work plan(MedievalSoldierEntity soldier) {
        class_2338 chest;
        class_2338 chest2;
        class_3218 world;
        block33: {
            block32: {
                class_1937 class_19372 = soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block32;
                world = (class_3218)class_19372;
                if (soldier.getNpcRole() == NpcRole.QUARTERMASTER) break block33;
            }
            return null;
        }
        int now = soldier.field_6012;
        if (this.work != null) {
            return this.work;
        }
        if (now < this.nextPlanTick) {
            return null;
        }
        this.nextPlanTick = now + 10 + Math.floorMod(soldier.method_5628(), 5);
        if (!QuartermasterFarmingController.hasUsableHoe(soldier)) {
            class_2338 chest3 = QuartermasterFarmingController.bestSupplyChestWithHoe(soldier, world);
            if (chest3 != null) {
                this.work = new Work(Action.FETCH_HOE, chest3);
                return this.work;
            }
            this.warnMissingHoe(soldier, world);
            return null;
        }
        QuartermasterFarmState state = soldier.quartermasterFarmState();
        QuartermasterCropPolicy.Crop cropProfile = QuartermasterCropPolicy.resolveSelection(soldier.getQuartermasterPlantingSelection());
        if (this.harvestPickupAnchor != null) {
            if (QuartermasterFarmingController.hasCollectibleHarvestDropNear(soldier, world, this.harvestPickupAnchor)) {
                this.harvestPickupClearTicks = 0;
                return null;
            }
            if (now < this.harvestPickupEarliestReleaseTick) {
                return null;
            }
            if (++this.harvestPickupClearTicks < 2) {
                return null;
            }
            this.harvestPickupAnchor = null;
            this.harvestPickupClearTicks = 0;
        }
        if (this.pendingHarvestReplant != null) {
            class_2338 replant = this.pendingHarvestReplant;
            class_1792 plantingItem = this.pendingHarvestReplantItem;
            if (world.method_22340(replant) && plantingItem != null && world.method_8320(replant).method_26215() && world.method_8320(replant.method_10074()).method_27852(class_2246.field_10362) && QuartermasterFarmingController.itemCount(soldier, plantingItem) > 0) {
                this.pendingHarvestReplant = null;
                this.pendingHarvestReplantItem = null;
                this.work = new Work(Action.PLANT, replant, plantingItem);
                return this.work;
            }
            if (!(world.method_22340(replant) && world.method_8320(replant).method_26215() && world.method_8320(replant.method_10074()).method_27852(class_2246.field_10362))) {
                this.pendingHarvestReplant = null;
                this.pendingHarvestReplantItem = null;
            }
        }
        if (this.compostingBatch) {
            if (state.composter == null || !world.method_22340(state.composter) || !world.method_8320(state.composter).method_27852(class_2246.field_17563)) {
                this.compostingBatch = false;
            } else {
                if (cropProfile != null && cropProfile.compostPlantingExcess() && QuartermasterFarmingController.compostableExcessCount(soldier, cropProfile) > 0) {
                    this.work = new Work(Action.COMPOST, state.composter, cropProfile.plantingItem());
                    return this.work;
                }
                this.compostingBatch = false;
            }
        }
        for (class_2338 center : state.fieldCenters) {
            if (!world.method_22340(center)) continue;
            class_2680 middle = world.method_8320(center);
            if (middle.method_27852(class_2246.field_10566) || middle.method_27852(class_2246.field_10219) || middle.method_27852(class_2246.field_10362)) {
                this.work = new Work(Action.WATER, center);
                return this.work;
            }
            Work harvest = QuartermasterFarmingController.firstMatureCropWork(world, center);
            if (harvest != null) {
                this.work = harvest;
                return this.work;
            }
            Work till = QuartermasterFarmingController.firstTillWork(world, center);
            if (till != null) {
                this.work = till;
                return this.work;
            }
            for (int dx = -4; dx <= 4; ++dx) {
                for (int dz = -4; dz <= 4; ++dz) {
                    class_2338 pos;
                    if (dx == 0 && dz == 0 || !world.method_22340(pos = center.method_10069(dx, 0, dz))) continue;
                    class_2680 ground = world.method_8320(pos);
                    class_2680 above = world.method_8320(pos.method_10084());
                    if (cropProfile != null && ground.method_27852(class_2246.field_10362) && above.method_26215() && QuartermasterFarmingController.itemCount(soldier, cropProfile.plantingItem()) > 0) {
                        this.work = new Work(Action.PLANT, pos.method_10084(), cropProfile.plantingItem());
                        return this.work;
                    }
                    if (cropProfile == null || !above.method_27852(cropProfile.cropBlock()) || QuartermasterCropPolicy.isMature(cropProfile.cropBlock(), above) || QuartermasterFarmingController.boneMealCount(soldier) <= 0) continue;
                    this.work = new Work(Action.FERTILIZE, pos.method_10084(), cropProfile.plantingItem());
                    return this.work;
                }
            }
        }
        if (QuartermasterFarmingController.breadCount(soldier) - 10 >= 10 && (chest2 = QuartermasterFarmingController.nearestSupplyChest(soldier, world)) != null) {
            this.work = new Work(Action.DEPOSIT_BREAD, chest2);
            return this.work;
        }
        class_1792 depositItem = QuartermasterFarmingController.firstProduceToDeposit(soldier, cropProfile);
        if (depositItem != null && (chest = QuartermasterFarmingController.nearestSupplyChest(soldier, world)) != null) {
            this.work = new Work(Action.DEPOSIT_PRODUCE, chest, depositItem);
            return this.work;
        }
        if (state.composter != null && world.method_22340(state.composter) && world.method_8320(state.composter).method_27852(class_2246.field_17563)) {
            int level = (Integer)world.method_8320(state.composter).method_11654((class_2769)class_3962.field_17565);
            if (level == 8) {
                this.work = new Work(Action.COLLECT_BONEMEAL, state.composter);
                return this.work;
            }
            if (cropProfile != null && cropProfile.compostPlantingExcess() && QuartermasterFarmingController.compostableExcessCount(soldier, cropProfile) >= 64) {
                this.compostingBatch = true;
                this.work = new Work(Action.COMPOST, state.composter, cropProfile.plantingItem());
                return this.work;
            }
        }
        if (now >= this.nextInspectTick) {
            class_2338 inspect = QuartermasterFarmingController.chooseInspectionPoint(soldier, world, state);
            this.nextInspectTick = now + 80 + soldier.method_6051().method_43048(81);
            if (inspect != null) {
                this.work = new Work(Action.INSPECT, inspect);
                return this.work;
            }
        }
        return null;
    }

    @Nullable
    class_2338 pendingHarvestPickupAnchor() {
        return this.harvestPickupAnchor;
    }

    private static boolean hasCollectibleHarvestDropNear(MedievalSoldierEntity soldier, class_3218 world, class_2338 anchor) {
        class_238 area = new class_238(anchor).method_1009(4.5, 2.5, 4.5);
        return !world.method_8390(class_1542.class, area, item -> item.method_5805() && !item.method_6983().method_7960() && QuartermasterCropPolicy.isFarmPickup(item.method_6983()) && soldier.method_5939(item.method_6983())).isEmpty();
    }

    private static int compostableExcessCount(MedievalSoldierEntity soldier, QuartermasterCropPolicy.Crop crop) {
        return Math.max(0, QuartermasterFarmingController.itemCount(soldier, crop.plantingItem()) - crop.plantingReserve());
    }

    @Nullable
    private static Work firstMatureCropWork(class_3218 world, class_2338 center) {
        for (int dx = -4; dx <= 4; ++dx) {
            for (int dz = -4; dz <= 4; ++dz) {
                class_2680 cropState;
                QuartermasterCropPolicy.Crop crop;
                class_2338 cropPos;
                if (dx == 0 && dz == 0 || !world.method_22340(cropPos = center.method_10069(dx, 1, dz)) || (crop = QuartermasterCropPolicy.resolveCropBlock((cropState = world.method_8320(cropPos)).method_26204())) == null || !QuartermasterCropPolicy.isMature(crop.cropBlock(), cropState)) continue;
                return new Work(Action.HARVEST, cropPos, crop.plantingItem());
            }
        }
        return null;
    }

    @Nullable
    private static Work firstTillWork(class_3218 world, class_2338 center) {
        for (int dx = -4; dx <= 4; ++dx) {
            for (int dz = -4; dz <= 4; ++dz) {
                class_2680 ground;
                class_2338 pos;
                if (dx == 0 && dz == 0 || !world.method_22340(pos = center.method_10069(dx, 0, dz)) || !(ground = world.method_8320(pos)).method_27852(class_2246.field_10566) && !ground.method_27852(class_2246.field_10219) || !world.method_8320(pos.method_10084()).method_26215()) continue;
                return new Work(Action.TILL, pos);
            }
        }
        return null;
    }

    boolean perform(MedievalSoldierEntity soldier, Work current) {
        class_3218 world;
        block18: {
            block17: {
                class_1937 class_19372 = soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block17;
                world = (class_3218)class_19372;
                if (current != null) break block18;
            }
            return true;
        }
        class_2338 pos = current.pos();
        switch (current.action().ordinal()) {
            case 1: {
                class_2680 state = world.method_8320(pos);
                if (!state.method_27852(class_2246.field_10566) && !state.method_27852(class_2246.field_10219) && !state.method_27852(class_2246.field_10362)) break;
                world.method_8652(pos, class_2246.field_10382.method_9564(), 3);
                soldier.method_6104(class_1268.field_5808);
                world.method_8396(null, pos, class_3417.field_14834, class_3419.field_15245, 0.7f, 1.0f);
                break;
            }
            case 2: {
                if (!QuartermasterFarmingController.hasUsableHoe(soldier)) {
                    return true;
                }
                class_2680 state = world.method_8320(pos);
                if (!state.method_27852(class_2246.field_10566) && !state.method_27852(class_2246.field_10219) || !world.method_8320(pos.method_10084()).method_26215()) break;
                world.method_8652(pos, class_2246.field_10362.method_9564(), 3);
                soldier.method_6104(class_1268.field_5808);
                world.method_8396(null, pos, class_3417.field_14846, class_3419.field_15245, 0.75f, 0.95f + soldier.method_6051().method_43057() * 0.1f);
                QuartermasterFarmingController.damageHoe(soldier);
                break;
            }
            case 3: {
                QuartermasterCropPolicy.Crop crop;
                QuartermasterCropPolicy.Crop crop2 = crop = current.item() == null ? null : QuartermasterCropPolicy.resolve(current.item());
                if (crop == null || !world.method_8320(pos).method_26215() || !world.method_8320(pos.method_10074()).method_27852(class_2246.field_10362) || !QuartermasterFarmingController.removeItem(soldier, crop.plantingItem(), 1)) break;
                world.method_8652(pos, crop.cropBlock().method_9564(), 3);
                soldier.method_6104(class_1268.field_5808);
                world.method_8396(null, pos, class_3417.field_17611, class_3419.field_15245, 0.65f, 0.95f + soldier.method_6051().method_43057() * 0.1f);
                break;
            }
            case 4: {
                QuartermasterCropPolicy.Crop crop;
                class_2680 cropState = world.method_8320(pos);
                if (current.item() == null || (crop = QuartermasterCropPolicy.resolve(current.item())) == null || !cropState.method_27852(crop.cropBlock()) || !QuartermasterCropPolicy.isMature(crop.cropBlock(), cropState)) break;
                class_2248.method_9511((class_2680)cropState, (class_1937)world, (class_2338)pos, (class_2586)world.method_8321(pos), (class_1297)soldier, (class_1799)soldier.method_6047());
                world.method_8652(pos, class_2246.field_10124.method_9564(), 3);
                this.harvestPickupAnchor = pos.method_10062();
                this.harvestPickupEarliestReleaseTick = soldier.field_6012 + 4;
                this.harvestPickupClearTicks = 0;
                this.pendingHarvestReplant = pos.method_10062();
                this.pendingHarvestReplantItem = crop.plantingItem();
                world.method_8396(null, pos, class_3417.field_17610, class_3419.field_15245, 0.75f, 0.95f + soldier.method_6051().method_43057() * 0.1f);
                break;
            }
            case 7: {
                class_2302 cropBlock;
                class_2248 class_22482;
                QuartermasterCropPolicy.Crop crop;
                class_2680 cropState = world.method_8320(pos);
                if (current.item() == null || (crop = QuartermasterCropPolicy.resolve(current.item())) == null || !cropState.method_27852(crop.cropBlock()) || !((class_22482 = crop.cropBlock()) instanceof class_2302) || (cropBlock = (class_2302)class_22482).method_9825(cropState) || !QuartermasterFarmingController.removeItem(soldier, class_1802.field_8324, 1)) break;
                int age = Math.min(cropBlock.method_9827(), cropBlock.method_9829(cropState) + 2 + soldier.method_6051().method_43048(3));
                world.method_8652(pos, cropBlock.method_9828(age), 3);
                break;
            }
            case 5: {
                if (current.item() == null) break;
                QuartermasterFarmingController.compostOneSeed(soldier, world, pos, current.item());
                break;
            }
            case 6: {
                class_2680 composter = world.method_8320(pos);
                if (!composter.method_27852(class_2246.field_17563) || (Integer)composter.method_11654((class_2769)class_3962.field_17565) != 8) break;
                world.method_8652(pos, (class_2680)composter.method_11657((class_2769)class_3962.field_17565, (Comparable)Integer.valueOf(0)), 3);
                class_1542 drop = new class_1542((class_1937)world, (double)pos.method_10263() + 0.5, (double)pos.method_10264() + 1.05, (double)pos.method_10260() + 0.5, new class_1799((class_1935)class_1802.field_8324));
                drop.method_6988();
                world.method_8649((class_1297)drop);
                world.method_8396(null, pos, class_3417.field_17606, class_3419.field_15245, 1.0f, 1.0f);
                break;
            }
            case 8: {
                QuartermasterFarmingController.depositBread(soldier, world, pos);
                break;
            }
            case 9: {
                if (current.item() == null) break;
                QuartermasterFarmingController.depositProduce(soldier, world, pos, current.item());
                break;
            }
            case 10: {
                QuartermasterFarmingController.fetchHoe(soldier, world, pos);
                break;
            }
            case 11: {
                break;
            }
        }
        this.work = null;
        this.nextPlanTick = soldier.field_6012 + 6;
        return true;
    }

    private static void craftBread(MedievalSoldierEntity soldier) {
        while (QuartermasterFarmingController.itemCount(soldier, class_1802.field_8861) >= 6 && QuartermasterFarmingController.breadCount(soldier) < 64 && QuartermasterFarmingController.removeItem(soldier, class_1802.field_8861, 3)) {
            QuartermasterFarmingController.addItem(soldier, new class_1799((class_1935)class_1802.field_8229));
        }
    }

    private static void trimSeedsToComposter(MedievalSoldierEntity soldier, class_3218 world) {
    }

    boolean isCompostingBatch() {
        return this.compostingBatch;
    }

    boolean continueCompostBatch(MedievalSoldierEntity soldier, Work current) {
        class_3218 world;
        block7: {
            block6: {
                class_1937 class_19372 = soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block6;
                world = (class_3218)class_19372;
                if (current != null && current.action() == Action.COMPOST && current.item() != null) break block7;
            }
            return false;
        }
        QuartermasterCropPolicy.Crop crop = QuartermasterCropPolicy.resolve(current.item());
        if (crop == null || !crop.compostPlantingExcess()) {
            return false;
        }
        if (!world.method_22340(current.pos()) || !world.method_8320(current.pos()).method_27852(class_2246.field_17563)) {
            this.compostingBatch = false;
            return false;
        }
        if (QuartermasterFarmingController.compostableExcessCount(soldier, crop) <= 0) {
            this.compostingBatch = false;
            return false;
        }
        return true;
    }

    void performCompostBatchStep(MedievalSoldierEntity soldier, Work current) {
        class_3218 world;
        block6: {
            block5: {
                class_1937 class_19372 = soldier.method_37908();
                if (!(class_19372 instanceof class_3218)) break block5;
                world = (class_3218)class_19372;
                if (current != null && current.item() != null) break block6;
            }
            return;
        }
        class_2338 pos = current.pos();
        class_2680 state = world.method_8320(pos);
        if (!state.method_27852(class_2246.field_17563)) {
            this.compostingBatch = false;
            return;
        }
        int level = (Integer)state.method_11654((class_2769)class_3962.field_17565);
        if (level == 8) {
            world.method_8652(pos, (class_2680)state.method_11657((class_2769)class_3962.field_17565, (Comparable)Integer.valueOf(0)), 3);
            class_1542 drop = new class_1542((class_1937)world, (double)pos.method_10263() + 0.5, (double)pos.method_10264() + 1.05, (double)pos.method_10260() + 0.5, new class_1799((class_1935)class_1802.field_8324));
            drop.method_6988();
            world.method_8649((class_1297)drop);
            world.method_8396(null, pos, class_3417.field_17606, class_3419.field_15245, 1.0f, 1.0f);
            return;
        }
        QuartermasterFarmingController.compostOneSeed(soldier, world, pos, current.item());
    }

    private static void compostOneSeed(MedievalSoldierEntity soldier, class_3218 world, class_2338 pos, class_1792 plantingItem) {
        QuartermasterCropPolicy.Crop crop = QuartermasterCropPolicy.resolve(plantingItem);
        if (crop == null || !crop.compostPlantingExcess() || QuartermasterFarmingController.compostableExcessCount(soldier, crop) <= 0) {
            return;
        }
        class_2680 state = world.method_8320(pos);
        if (!state.method_27852(class_2246.field_17563)) {
            return;
        }
        if (!QuartermasterFarmingController.removeItem(soldier, plantingItem, 1)) {
            return;
        }
        int level = (Integer)state.method_11654((class_2769)class_3962.field_17565);
        if (level < 7 && soldier.method_6051().method_43057() < 0.3f) {
            world.method_8652(pos, (class_2680)state.method_11657((class_2769)class_3962.field_17565, (Comparable)Integer.valueOf(++level)), 3);
            world.method_8396(null, pos, class_3417.field_17608, class_3419.field_15245, 1.0f, 1.0f);
        } else if (level == 7) {
            world.method_8652(pos, (class_2680)state.method_11657((class_2769)class_3962.field_17565, (Comparable)Integer.valueOf(8)), 3);
            world.method_8396(null, pos, class_3417.field_17608, class_3419.field_15245, 1.0f, 1.0f);
        }
    }

    private static boolean hasUsableHoe(MedievalSoldierEntity soldier) {
        class_1799 main = soldier.method_6047();
        return main.method_7909() instanceof class_1794 && (!main.method_7963() || main.method_7919() < main.method_7936());
    }

    private static void damageHoe(MedievalSoldierEntity soldier) {
        class_1799 hoe = soldier.method_6047();
        if (!(hoe.method_7909() instanceof class_1794)) {
            return;
        }
        hoe.method_7956(1, (class_1309)soldier, e -> e.method_20235(class_1304.field_6173));
        if (hoe.method_7960() || hoe.method_7963() && hoe.method_7919() >= hoe.method_7936()) {
            soldier.method_5673(class_1304.field_6173, class_1799.field_8037);
        }
    }

    private void warnMissingHoe(MedievalSoldierEntity soldier, class_3218 world) {
        if (this.hoeWarningCooldown > 0) {
            return;
        }
        this.hoeWarningCooldown = 1200;
        UUID kingdomId = soldier.getNpcIdentity().getKingdomId();
        if (kingdomId == null) {
            return;
        }
        KingdomData kingdom = KingdomManager.get(world).get(kingdomId);
        if (kingdom == null) {
            return;
        }
        class_5250 message = class_2561.method_43469((String)"message.medieval-machinery.quartermaster.hoe_missing", (Object[])new Object[]{soldier.method_5476()});
        for (UUID memberId : kingdom.getMembers().keySet()) {
            class_3222 player = world.method_8503().method_3760().method_14602(memberId);
            if (player == null) continue;
            player.method_7353((class_2561)message, false);
        }
    }

    @Nullable
    private static class_2338 bestSupplyChestWithHoe(MedievalSoldierEntity soldier, class_3218 world) {
        GarrisonData garrison = QuartermasterFarmingController.garrison(soldier, world);
        if (garrison == null) {
            return null;
        }
        return garrison.getSupplyChestPositions().stream().filter(arg_0 -> ((class_3218)world).method_22340(arg_0)).filter(p -> QuartermasterFarmingController.inventoryAt(world, p) != null && QuartermasterFarmingController.containsHoe(QuartermasterFarmingController.inventoryAt(world, p))).min(Comparator.comparingDouble(p -> p.method_10262((class_2382)soldier.method_24515()))).orElse(null);
    }

    @Nullable
    private static class_2338 nearestSupplyChest(MedievalSoldierEntity soldier, class_3218 world) {
        GarrisonData garrison = QuartermasterFarmingController.garrison(soldier, world);
        if (garrison == null) {
            return null;
        }
        return garrison.getSupplyChestPositions().stream().filter(arg_0 -> ((class_3218)world).method_22340(arg_0)).filter(p -> QuartermasterFarmingController.inventoryAt(world, p) != null).min(Comparator.comparingDouble(p -> p.method_10262((class_2382)soldier.method_24515()))).orElse(null);
    }

    @Nullable
    private static class_2338 chooseInspectionPoint(MedievalSoldierEntity soldier, class_3218 world, QuartermasterFarmState state) {
        if (state == null) {
            return null;
        }
        if (!state.fieldCenters.isEmpty()) {
            class_2338 center = state.fieldCenters.get(soldier.method_6051().method_43048(state.fieldCenters.size()));
            for (int attempt = 0; attempt < 12; ++attempt) {
                class_2680 floor;
                class_2338 ground;
                int dx = soldier.method_6051().method_43048(9) - 4;
                int dz = soldier.method_6051().method_43048(9) - 4;
                if (dx == 0 && dz == 0 || !world.method_22340(ground = center.method_10069(dx, 0, dz)) || !(floor = world.method_8320(ground)).method_27852(class_2246.field_10362) && !floor.method_27852(class_2246.field_10566) && !floor.method_27852(class_2246.field_10219) || !world.method_8320(ground.method_10084()).method_26220((class_1922)world, ground.method_10084()).method_1110()) continue;
                return ground;
            }
        }
        if (state.composter != null && world.method_22340(state.composter)) {
            for (class_2350 dir : class_2350.class_2353.field_11062) {
                class_2338 stand = state.composter.method_10093(dir);
                if (world.method_8320(stand.method_10074()).method_26220((class_1922)world, stand.method_10074()).method_1110() || !world.method_8320(stand).method_26220((class_1922)world, stand).method_1110() || !world.method_8320(stand.method_10084()).method_26220((class_1922)world, stand.method_10084()).method_1110()) continue;
                return stand.method_10074();
            }
        }
        return null;
    }

    @Nullable
    private static GarrisonData garrison(MedievalSoldierEntity soldier, class_3218 world) {
        UUID castleId = soldier.getNpcIdentity().getCastleId();
        return castleId == null ? null : GarrisonManager.get(world).get(castleId);
    }

    @Nullable
    private static class_1263 inventoryAt(class_3218 world, class_2338 pos) {
        class_1263 inv;
        class_2586 be = world.method_8321(pos);
        return be instanceof class_1263 ? (inv = (class_1263)be) : null;
    }

    private static boolean containsHoe(@Nullable class_1263 inv) {
        if (inv == null) {
            return false;
        }
        for (int i = 0; i < inv.method_5439(); ++i) {
            if (!(inv.method_5438(i).method_7909() instanceof class_1794)) continue;
            return true;
        }
        return false;
    }

    private static void fetchHoe(MedievalSoldierEntity soldier, class_3218 world, class_2338 pos) {
        class_1263 inv = QuartermasterFarmingController.inventoryAt(world, pos);
        if (inv == null) {
            return;
        }
        int best = -1;
        int score = -1;
        for (int i = 0; i < inv.method_5439(); ++i) {
            int s;
            class_1799 stack = inv.method_5438(i);
            if (!(stack.method_7909() instanceof class_1794) || (s = stack.method_7936() * 10000 + (stack.method_7936() - stack.method_7919())) <= score) continue;
            score = s;
            best = i;
        }
        if (best < 0) {
            return;
        }
        class_1799 replacement = inv.method_5434(best, 1);
        class_1799 old = soldier.method_6047();
        soldier.method_5673(class_1304.field_6173, replacement);
        if (!old.method_7960()) {
            QuartermasterFarmingController.insert(inv, old.method_7972());
        }
        inv.method_5431();
    }

    @Nullable
    private static class_1792 firstProduceToDeposit(MedievalSoldierEntity soldier, @Nullable QuartermasterCropPolicy.Crop crop) {
        if (crop == null || crop.produceItem() == null || crop.produceItem() == class_1802.field_8861) {
            return null;
        }
        class_1792 produce = crop.produceItem();
        int keep = produce == crop.plantingItem() ? crop.plantingReserve() : 0;
        return QuartermasterFarmingController.itemCount(soldier, produce) > keep ? produce : null;
    }

    private static void depositProduce(MedievalSoldierEntity soldier, class_3218 world, class_2338 pos, class_1792 item) {
        class_1263 inv = QuartermasterFarmingController.inventoryAt(world, pos);
        if (inv == null || item == null) {
            return;
        }
        QuartermasterCropPolicy.Crop selected = QuartermasterCropPolicy.resolve(soldier.getQuartermasterPlantingSelection().method_7909());
        int keep = selected != null && selected.produceItem() == item && selected.plantingItem() == item ? selected.plantingReserve() : 0;
        int excess = Math.max(0, QuartermasterFarmingController.itemCount(soldier, item) - keep);
        if (excess <= 0) {
            return;
        }
        int removed = QuartermasterFarmingController.removeUpTo(soldier, item, excess);
        class_1799 left = QuartermasterFarmingController.insert(inv, new class_1799((class_1935)item, removed));
        if (!left.method_7960()) {
            QuartermasterFarmingController.addItem(soldier, left);
        }
        inv.method_5431();
    }

    private static void depositBread(MedievalSoldierEntity soldier, class_3218 world, class_2338 pos) {
        class_1263 inv = QuartermasterFarmingController.inventoryAt(world, pos);
        if (inv == null) {
            return;
        }
        int excess = Math.max(0, QuartermasterFarmingController.breadCount(soldier) - 10);
        if (excess <= 0) {
            return;
        }
        int removed = QuartermasterFarmingController.removeUpTo(soldier, class_1802.field_8229, excess);
        if (removed <= 0) {
            return;
        }
        class_1799 left = QuartermasterFarmingController.insert(inv, new class_1799((class_1935)class_1802.field_8229, removed));
        if (!left.method_7960()) {
            QuartermasterFarmingController.addItem(soldier, left);
        }
        inv.method_5431();
    }

    private static class_1799 insert(class_1263 inv, class_1799 stack) {
        int i;
        class_1799 remaining = stack.method_7972();
        for (i = 0; i < inv.method_5439() && !remaining.method_7960(); ++i) {
            class_1799 slot = inv.method_5438(i);
            if (slot.method_7960() || !class_1799.method_31577((class_1799)slot, (class_1799)remaining) || slot.method_7947() >= slot.method_7914()) continue;
            int move = Math.min(remaining.method_7947(), slot.method_7914() - slot.method_7947());
            slot.method_7933(move);
            remaining.method_7934(move);
        }
        for (i = 0; i < inv.method_5439() && !remaining.method_7960(); ++i) {
            if (!inv.method_5438(i).method_7960()) continue;
            int move = Math.min(remaining.method_7947(), remaining.method_7914());
            class_1799 placed = remaining.method_7972();
            placed.method_7939(move);
            inv.method_5447(i, placed);
            remaining.method_7934(move);
        }
        return remaining;
    }

    private static int breadCount(MedievalSoldierEntity s) {
        return QuartermasterFarmingController.itemCount(s, class_1802.field_8229);
    }

    private static int boneMealCount(MedievalSoldierEntity s) {
        return QuartermasterFarmingController.itemCount(s, class_1802.field_8324);
    }

    private static int itemCount(MedievalSoldierEntity s, class_1792 item) {
        int total = 0;
        class_1263 inv = s.getPersonalInventory();
        for (int i = 0; i < Math.min(9, inv.method_5439()); ++i) {
            if (!inv.method_5438(i).method_31574(item)) continue;
            total += inv.method_5438(i).method_7947();
        }
        return total;
    }

    private static boolean removeItem(MedievalSoldierEntity s, class_1792 item, int count) {
        return QuartermasterFarmingController.removeUpTo(s, item, count) == count;
    }

    private static int removeUpTo(MedievalSoldierEntity s, class_1792 item, int count) {
        class_1263 inv = s.getPersonalInventory();
        int remaining = count;
        for (int i = 0; i < Math.min(9, inv.method_5439()) && remaining > 0; ++i) {
            class_1799 stack = inv.method_5438(i);
            if (!stack.method_31574(item)) continue;
            int take = Math.min(remaining, stack.method_7947());
            stack.method_7934(take);
            remaining -= take;
            if (!stack.method_7960()) continue;
            inv.method_5447(i, class_1799.field_8037);
        }
        inv.method_5431();
        return count - remaining;
    }

    private static void addItem(MedievalSoldierEntity s, class_1799 stack) {
        int i;
        class_1263 inv = s.getPersonalInventory();
        class_1799 remaining = stack.method_7972();
        for (i = 0; i < Math.min(9, inv.method_5439()) && !remaining.method_7960(); ++i) {
            class_1799 slot = inv.method_5438(i);
            if (slot.method_7960() || !class_1799.method_31577((class_1799)slot, (class_1799)remaining) || slot.method_7947() >= slot.method_7914()) continue;
            int move = Math.min(remaining.method_7947(), slot.method_7914() - slot.method_7947());
            slot.method_7933(move);
            remaining.method_7934(move);
        }
        for (i = 0; i < Math.min(9, inv.method_5439()) && !remaining.method_7960(); ++i) {
            if (!inv.method_5438(i).method_7960()) continue;
            int move = Math.min(remaining.method_7947(), remaining.method_7914());
            class_1799 placed = remaining.method_7972();
            placed.method_7939(move);
            inv.method_5447(i, placed);
            remaining.method_7934(move);
        }
        inv.method_5431();
    }

    record Work(Action action, class_2338 pos, @Nullable class_1792 item) {
        Work(Action action, class_2338 pos) {
            this(action, pos, null);
        }
    }

    static enum Action {
        NONE,
        WATER,
        TILL,
        PLANT,
        HARVEST,
        COMPOST,
        COLLECT_BONEMEAL,
        FERTILIZE,
        DEPOSIT_BREAD,
        DEPOSIT_PRODUCE,
        FETCH_HOE,
        INSPECT;

    }
}

