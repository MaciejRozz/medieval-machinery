/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
 *  net.minecraft.class_1277
 *  net.minecraft.class_1297
 *  net.minecraft.class_1542
 *  net.minecraft.class_1646
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_238
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.garrison;

import com.medievalmachinery.mod.block.garrison.WatchpostBlock;
import com.medievalmachinery.mod.block.garrison.WatchpostBlockAccess;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CastleAccessPolicy;
import com.medievalmachinery.mod.npc.organization.CastleCaptureStateManager;
import com.medievalmachinery.mod.npc.organization.CastleDiplomacyPolicy;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import com.medievalmachinery.mod.npc.organization.CommandGroupType;
import com.medievalmachinery.mod.npc.organization.GarrisonData;
import com.medievalmachinery.mod.npc.organization.GarrisonManager;
import com.medievalmachinery.mod.npc.organization.GarrisonRallyPolicy;
import com.medievalmachinery.mod.npc.organization.GeneralCooldownManager;
import com.medievalmachinery.mod.npc.organization.GroupCastleTransferService;
import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.KingdomPermission;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.npc.organization.PromotionRules;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryData;
import com.medievalmachinery.mod.npc.organization.SoldierRegistryManager;
import com.medievalmachinery.mod.npc.recruitment.RecruitmentCostPolicy;
import com.medievalmachinery.mod.npc.recruitment.WatchpostRecruitmentRules;
import com.medievalmachinery.mod.registry.ModBlockEntities;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.registry.ModEntities;
import com.medievalmachinery.mod.screen.garrison.WatchpostScreenHandler;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.class_1277;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1646;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import org.jetbrains.annotations.Nullable;

public final class WatchpostBlockEntity
extends class_2586
implements WatchpostBlockAccess,
ExtendedScreenHandlerFactory {
    public static final int RECRUIT_RADIUS = 50;
    public static final int MIN_VILLAGERS = 10;
    @Nullable
    private UUID ownerId;
    @Nullable
    private UUID kingdomId;
    @Nullable
    private UUID castleId;
    private OrderStyle orderStyle = OrderStyle.NONE;
    private final class_1277 orderInventory = new class_1277(1){

        public void method_5431() {
            super.method_5431();
            WatchpostBlockEntity.this.updateOrderFromSigil();
        }
    };
    private final class_1277 captureInventory = new class_1277(1){

        public void method_5431() {
            super.method_5431();
            WatchpostBlockEntity.this.method_5431();
        }
    };

    public WatchpostBlockEntity(class_2338 pos, class_2680 state) {
        super(ModBlockEntities.WATCHPOST, pos, state);
    }

    @Override
    public class_2338 getWatchpostPos() {
        return this.field_11867;
    }

    @Override
    public void ensureAssigned(class_3222 player) {
        class_1937 class_19372 = this.field_11863;
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        if (this.ownerId == null) {
            this.ownerId = player.method_5667();
            GarrisonData garrison = GarrisonManager.get(serverWorld).create(player.method_5667(), null, "Wachposten " + player.method_7334().getName(), this.field_11867);
            this.castleId = garrison.getCastleId();
            this.kingdomId = null;
            this.method_5431();
        }
        if (this.castleId != null) {
            GarrisonManager.get(serverWorld).setCenter(this.castleId, this.field_11867, serverWorld.method_27983().method_29177().toString());
        }
    }

    public boolean isOwner(class_1657 player) {
        return this.ownerId != null && this.ownerId.equals(player.method_5667());
    }

    @Override
    public boolean canPlayerAdminister(class_1657 player) {
        class_3218 serverWorld;
        block5: {
            block4: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block4;
                serverWorld = (class_3218)class_19372;
                if (player instanceof class_3222) break block5;
            }
            return this.isOwner(player);
        }
        class_3222 serverPlayer = (class_3222)player;
        if (this.castleId == null) {
            return this.isOwner(player);
        }
        GarrisonData garrison = GarrisonManager.get(serverWorld).get(this.castleId);
        return garrison != null ? CastleAccessPolicy.canAdminister(serverWorld, serverPlayer, garrison) : this.isOwner(player);
    }

    @Override
    public boolean canPlayerUseSigil(class_1657 player, class_1799 stack) {
        class_3218 serverWorld;
        block5: {
            block4: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block4;
                serverWorld = (class_3218)class_19372;
                if (OrderSigilItem.isStamped(stack)) break block5;
            }
            return false;
        }
        UUID sealKingdom = OrderSigilItem.getKingdomId(stack);
        if (sealKingdom == null) {
            return false;
        }
        KingdomData playerKingdom = KingdomManager.get(serverWorld).findByMember(player.method_5667());
        return playerKingdom != null && playerKingdom.getId().equals(sealKingdom);
    }

    @Override
    public boolean hasGeneralProtection() {
        class_3218 serverWorld;
        block4: {
            block3: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block3;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null) break block4;
            }
            return false;
        }
        for (MedievalSoldierEntity soldier : serverWorld.method_8390(MedievalSoldierEntity.class, new class_238(this.field_11867).method_1014(512.0), npc -> this.castleId.equals(npc.getNpcIdentity().getCastleId()))) {
            SoldierRegistryManager.get(serverWorld).update(soldier);
        }
        return SoldierRegistryManager.get(serverWorld).hasLivingGeneral(this.castleId);
    }

    public boolean blocksForeignBreak(class_1657 player) {
        class_3218 serverWorld;
        block3: {
            block2: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block2;
                serverWorld = (class_3218)class_19372;
                if (this.kingdomId != null) break block3;
            }
            return false;
        }
        KingdomData playerKingdom = KingdomManager.get(serverWorld).findByMember(player.method_5667());
        return playerKingdom == null || !this.kingdomId.equals(playerKingdom.getId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isCaptureActive() {
        class_1937 class_19372 = this.field_11863;
        if (!(class_19372 instanceof class_3218)) return false;
        class_3218 serverWorld = (class_3218)class_19372;
        if (this.castleId == null) return false;
        if (CastleCaptureStateManager.get(serverWorld).get(this.castleId) == null) return false;
        return true;
    }

    @Override
    public class_1277 getCaptureInventory() {
        return this.captureInventory;
    }

    @Override
    public boolean canOpenCapture(class_3222 player) {
        class_3218 serverWorld;
        block6: {
            block5: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block5;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null && this.kingdomId != null) break block6;
            }
            return false;
        }
        if (this.canPlayerAdminister((class_1657)player) || this.hasGeneralProtection()) {
            return false;
        }
        KingdomData playerKingdom = KingdomManager.get(serverWorld).findByMember(player.method_5667());
        if (playerKingdom == null || playerKingdom.getId().equals(this.kingdomId)) {
            return false;
        }
        return CastleDiplomacyPolicy.mayAttemptConquest(serverWorld, this.kingdomId, playerKingdom.getId());
    }

    @Override
    public boolean startCapture(class_3222 player) {
        class_3218 serverWorld;
        block7: {
            block6: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block6;
                serverWorld = (class_3218)class_19372;
                if (this.canOpenCapture(player) && this.castleId != null) break block7;
            }
            return false;
        }
        class_1799 offered = this.captureInventory.method_5438(0);
        if (!this.canPlayerUseSigil((class_1657)player, offered)) {
            return false;
        }
        UUID sealKingdom = OrderSigilItem.getKingdomId(offered);
        if (sealKingdom == null) {
            return false;
        }
        KingdomData playerKingdom = KingdomManager.get(serverWorld).findByMember(player.method_5667());
        if (playerKingdom == null || !sealKingdom.equals(playerKingdom.getId())) {
            return false;
        }
        long duration = (long)Math.max(1, MedievalMachineryConfig.get().castleCaptureSeconds) * 20L;
        return CastleCaptureStateManager.get(serverWorld).start(this.castleId, player.method_5667(), playerKingdom.getId(), this.kingdomId, serverWorld.method_27983().method_29177().toString(), this.field_11867, serverWorld.method_8503().method_30002().method_8510(), duration);
    }

    public boolean canContinueCapture(class_3222 player, CastleCaptureStateManager.CaptureState capture) {
        class_3218 serverWorld;
        block10: {
            block9: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block9;
                serverWorld = (class_3218)class_19372;
                if (player != null && capture != null && this.castleId != null) break block10;
            }
            return false;
        }
        if (!this.castleId.equals(capture.castleId()) || !player.method_5667().equals(capture.playerId())) {
            return false;
        }
        if (this.kingdomId == null || !this.kingdomId.equals(capture.defenderKingdomId())) {
            return false;
        }
        KingdomData playerKingdom = KingdomManager.get(serverWorld).findByMember(player.method_5667());
        if (playerKingdom == null || !playerKingdom.getId().equals(capture.attackerKingdomId())) {
            return false;
        }
        if (this.hasGeneralProtection()) {
            return false;
        }
        if (!CastleDiplomacyPolicy.mayAttemptConquest(serverWorld, this.kingdomId, playerKingdom.getId())) {
            return false;
        }
        class_1799 staged = this.captureInventory.method_5438(0);
        if (!this.canPlayerUseSigil((class_1657)player, staged)) {
            return false;
        }
        UUID sealKingdom = OrderSigilItem.getKingdomId(staged);
        return sealKingdom != null && sealKingdom.equals(capture.attackerKingdomId());
    }

    public boolean completeActiveCapture(class_3222 player) {
        class_3218 serverWorld;
        block11: {
            block10: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block10;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null) break block11;
            }
            return false;
        }
        CastleCaptureStateManager.CaptureState capture = CastleCaptureStateManager.get(serverWorld).get(this.castleId);
        if (capture == null || !capture.playerId().equals(player.method_5667())) {
            return false;
        }
        if (!this.canContinueCapture(player, capture)) {
            return false;
        }
        class_1799 offeredSigil = this.captureInventory.method_5438(0);
        UUID newKingdomId = OrderSigilItem.getKingdomId(offeredSigil);
        if (newKingdomId == null || newKingdomId.equals(this.kingdomId)) {
            return false;
        }
        if (!CastleDiplomacyPolicy.mayAttemptConquest(serverWorld, this.kingdomId, newKingdomId)) {
            return false;
        }
        UUID capturedCastleId = this.castleId;
        CommandGroupManager.get(serverWorld).detachCastle(capturedCastleId);
        SoldierRegistryManager registry = SoldierRegistryManager.get(serverWorld);
        registry.clearCastle(capturedCastleId);
        GarrisonData garrison = GarrisonManager.get(serverWorld).get(capturedCastleId);
        if (garrison != null) {
            for (UUID soldierId : List.copyOf(garrison.getSoldierIds())) {
                GarrisonManager.get(serverWorld).unregisterSoldier(capturedCastleId, soldierId);
            }
        }
        for (MedievalSoldierEntity soldier : serverWorld.method_8390(MedievalSoldierEntity.class, new class_238(this.field_11867).method_1014(512.0), npc -> capturedCastleId.equals(npc.getNpcIdentity().getCastleId()))) {
            soldier.getNpcIdentity().setCastleId(null);
            soldier.setHomePosition(null);
            registry.update(soldier);
        }
        this.ownerId = player.method_5667();
        OrderStyle newOrder = OrderSigilItem.getStampedOrder(offeredSigil);
        GarrisonManager.get(serverWorld).transferOwnership(capturedCastleId, this.ownerId, newKingdomId, newOrder);
        class_1799 inserted = offeredSigil.method_7972();
        inserted.method_7939(1);
        this.orderInventory.method_5447(0, inserted);
        this.captureInventory.method_5447(0, class_1799.field_8037);
        this.kingdomId = newKingdomId;
        this.orderStyle = newOrder;
        this.syncOrderBlockState();
        this.method_5431();
        return true;
    }

    @Override
    public void returnCaptureSigil(class_3222 player) {
        class_1937 class_19372;
        class_1799 stack = this.captureInventory.method_5441(0);
        if (stack.method_7960()) {
            return;
        }
        if (!player.method_31548().method_7394(stack) && (class_19372 = this.field_11863) instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            serverWorld.method_8649((class_1297)new class_1542((class_1937)serverWorld, player.method_23317(), player.method_23318(), player.method_23321(), stack));
        }
        this.method_5431();
    }

    private List<class_1646> getAdultVillagersInRange() {
        class_1937 class_19372 = this.field_11863;
        if (!(class_19372 instanceof class_3218)) {
            return List.of();
        }
        class_3218 serverWorld = (class_3218)class_19372;
        return serverWorld.method_8390(class_1646.class, new class_238(this.field_11867).method_1014(50.0), WatchpostRecruitmentRules::countsTowardPopulation);
    }

    @Override
    public int getTotalVillagerCount() {
        return this.getAdultVillagersInRange().size();
    }

    @Override
    public int getEmployedVillagerCount() {
        int count = 0;
        for (class_1646 villager : this.getAdultVillagersInRange()) {
            if (!WatchpostRecruitmentRules.isEmployed(villager)) continue;
            ++count;
        }
        return count;
    }

    @Override
    public int getAvailableVillagerCount() {
        List<class_1646> villagers = this.getAdultVillagersInRange();
        int recruitable = 0;
        for (class_1646 villager : villagers) {
            if (!WatchpostRecruitmentRules.isRecruitable(villager)) continue;
            ++recruitable;
        }
        return WatchpostRecruitmentRules.availableRecruitSlots(villagers.size(), recruitable);
    }

    @Override
    public int getStoredTaxEmeralds() {
        class_3218 serverWorld;
        block5: {
            block4: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block4;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null) break block5;
            }
            return 0;
        }
        GarrisonData garrison = GarrisonManager.get(serverWorld).get(this.castleId);
        if (garrison == null) {
            return 0;
        }
        return (int)Math.min(Integer.MAX_VALUE, garrison.getStoredTaxEmeralds());
    }

    @Override
    public int getLastKnownTaxVillagerCount() {
        class_3218 serverWorld;
        block3: {
            block2: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block2;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null) break block3;
            }
            return 0;
        }
        GarrisonData garrison = GarrisonManager.get(serverWorld).get(this.castleId);
        return garrison == null ? 0 : garrison.getLastKnownAdultVillagerCount();
    }

    @Override
    public boolean recruit(class_3222 player, NpcRole role) {
        return this.recruit(player, role, null);
    }

    public boolean recruit(class_3222 player, NpcRole role, @Nullable UUID requestedGroupId) {
        class_3218 serverWorld;
        block16: {
            block15: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block15;
                serverWorld = (class_3218)class_19372;
                if (this.canPlayerAdminister((class_1657)player)) break block16;
            }
            return false;
        }
        if (!this.hasValidStampedSigil() || this.kingdomId == null || this.castleId == null) {
            return false;
        }
        KingdomData kingdom = KingdomManager.get(serverWorld).get(this.kingdomId);
        if (kingdom == null || !kingdom.hasPermission(player.method_5667(), KingdomPermission.RECRUIT)) {
            return false;
        }
        int emeraldCost = this.getRecruitmentEmeraldCost(role);
        if (!WatchpostBlockEntity.canAffordRecruitment(player, emeraldCost)) {
            return false;
        }
        List<class_1646> allVillagers = this.getAdultVillagersInRange();
        List<class_1646> availableVillagers = allVillagers.stream().filter(WatchpostRecruitmentRules::isRecruitable).toList();
        if (!WatchpostRecruitmentRules.recruitmentPossible(allVillagers.size(), availableVillagers.size())) {
            return false;
        }
        class_1646 selected = availableVillagers.stream().min(Comparator.comparingDouble(v -> v.method_5649((double)this.field_11867.method_10263() + 0.5, (double)this.field_11867.method_10264() + 0.5, (double)this.field_11867.method_10260() + 0.5))).orElse(null);
        if (selected == null) {
            return false;
        }
        MedievalSoldierEntity soldier = (MedievalSoldierEntity)ModEntities.SOLDIER.method_5883((class_1937)serverWorld);
        if (soldier == null) {
            return false;
        }
        soldier.rollRecruitMaxHealth();
        soldier.method_5808(selected.method_23317(), selected.method_23318(), selected.method_23321(), selected.method_36454(), selected.method_36455());
        if (selected.method_16914()) {
            soldier.method_5665(selected.method_5797());
            soldier.method_5880(selected.method_5807());
        }
        soldier.setNpcRole(role);
        soldier.applyRoleTargetPriorityDefault();
        soldier.equipRecruitStarterWeapon();
        soldier.giveRecruitStarterFood();
        soldier.setOrderStyle(this.orderStyle);
        soldier.applyCastleDisplayName(this.getCastleName());
        soldier.getNpcIdentity().setOwnerId(player.method_5667());
        soldier.getNpcIdentity().setKingdomId(this.kingdomId);
        soldier.getNpcIdentity().setCastleId(this.castleId);
        soldier.setHomePosition(this.field_11867);
        GarrisonData recruitGarrison = GarrisonManager.get(serverWorld).get(this.castleId);
        if (recruitGarrison != null && recruitGarrison.getRallyPoint() != null && GarrisonRallyPolicy.isWithinInfluence(soldier.method_19538(), recruitGarrison.getRallyPoint())) {
            soldier.setIdleRallyAnchor(GarrisonRallyPolicy.nearbyAnchor(serverWorld, recruitGarrison.getRallyPoint(), soldier.method_5667()));
        }
        if (!serverWorld.method_8649((class_1297)soldier)) {
            return false;
        }
        soldier.createRecruitMount();
        if (this.castleId != null) {
            CommandGroupData group;
            GarrisonManager.get(serverWorld).registerSoldier(this.castleId, soldier.method_5667());
            CommandGroupManager groupManager = CommandGroupManager.get(serverWorld);
            CommandGroupData commandGroupData = group = requestedGroupId == null ? null : groupManager.get(requestedGroupId);
            if (group == null || !this.castleId.equals(group.getCastleId()) || !player.method_5667().equals(group.getOwnerId())) {
                CommandGroupType groupType = MedievalSoldierEntity.defaultCommandGroupTypeForRole(soldier.getNpcRole());
                group = groupManager.ensureFixedGroup(this.castleId, player.method_5667(), groupType);
            }
            groupManager.assignRecruit(soldier, group.getId());
        }
        if (!player.method_31549().field_7477 && emeraldCost > 0) {
            WatchpostBlockEntity.removeEmeralds(player, emeraldCost);
        }
        selected.method_31472();
        this.method_5431();
        return true;
    }

    public int getRecruitmentEmeraldCost(NpcRole role) {
        return RecruitmentCostPolicy.emeraldCost(role);
    }

    public boolean canAffordRecruitment(class_3222 player, NpcRole role) {
        return WatchpostBlockEntity.canAffordRecruitment(player, this.getRecruitmentEmeraldCost(role));
    }

    private static boolean canAffordRecruitment(class_3222 player, int emeraldCost) {
        if (player == null || player.method_31549().field_7477 || emeraldCost <= 0) {
            return true;
        }
        int found = 0;
        for (int slot = 0; slot < player.method_31548().method_5439(); ++slot) {
            class_1799 stack = player.method_31548().method_5438(slot);
            if (!stack.method_31574(class_1802.field_8687) || (found += stack.method_7947()) < emeraldCost) continue;
            return true;
        }
        return false;
    }

    private static void removeEmeralds(class_3222 player, int emeraldCost) {
        int remaining = Math.max(0, emeraldCost);
        for (int slot = 0; slot < player.method_31548().method_5439() && remaining > 0; ++slot) {
            class_1799 stack = player.method_31548().method_5438(slot);
            if (!stack.method_31574(class_1802.field_8687)) continue;
            int take = Math.min(remaining, stack.method_7947());
            stack.method_7934(take);
            remaining -= take;
        }
    }

    @Override
    public class_1277 getOrderInventory() {
        return this.orderInventory;
    }

    @Override
    public boolean hasValidStampedSigil() {
        return OrderSigilItem.isStamped(this.orderInventory.method_5438(0));
    }

    private void updateOrderFromSigil() {
        class_1799 stack = this.orderInventory.method_5438(0);
        UUID newKingdom = OrderSigilItem.isStamped(stack) ? OrderSigilItem.getKingdomId(stack) : null;
        OrderStyle newOrder = OrderSigilItem.isStamped(stack) ? OrderSigilItem.getStampedOrder(stack) : OrderStyle.NONE;
        this.kingdomId = newKingdom;
        this.orderStyle = newOrder;
        class_1937 class_19372 = this.field_11863;
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            if (this.castleId != null) {
                GarrisonManager.get(serverWorld).setCastleContext(this.castleId, this.kingdomId, this.orderStyle);
            }
        }
        this.syncOrderBlockState();
        this.method_5431();
    }

    public OrderStyle getOrderStyle() {
        return this.orderStyle;
    }

    public void setOrderStyle(OrderStyle style) {
        this.orderStyle = style == null ? OrderStyle.NONE : style;
        class_1937 class_19372 = this.field_11863;
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            if (this.castleId != null) {
                GarrisonManager.get(serverWorld).setCastleContext(this.castleId, this.kingdomId, this.orderStyle);
            }
        }
        this.syncOrderBlockState();
        this.method_5431();
    }

    private void syncOrderBlockState() {
        class_2338 extensionPos;
        class_2680 extension;
        if (this.field_11863 == null) {
            return;
        }
        class_2680 mainState = this.field_11863.method_8320(this.field_11867);
        if (!(mainState.method_26204() instanceof WatchpostBlock)) {
            return;
        }
        if (mainState.method_11654(WatchpostBlock.ORDER) != this.orderStyle) {
            this.field_11863.method_8652(this.field_11867, (class_2680)mainState.method_11657(WatchpostBlock.ORDER, (Comparable)((Object)this.orderStyle)), 3);
        }
        if ((extension = this.field_11863.method_8320(extensionPos = this.field_11867.method_10093(WatchpostBlock.getExtensionDirection((class_2350)mainState.method_11654((class_2769)WatchpostBlock.FACING))))).method_27852(mainState.method_26204()) && extension.method_11654(WatchpostBlock.ORDER) != this.orderStyle) {
            this.field_11863.method_8652(extensionPos, (class_2680)extension.method_11657(WatchpostBlock.ORDER, (Comparable)((Object)this.orderStyle)), 3);
        }
    }

    @Override
    public void removeCastleAssignment() {
        class_3218 serverWorld;
        block3: {
            block2: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block2;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null) break block3;
            }
            return;
        }
        UUID removedCastleId = this.castleId;
        WatchpostBlockEntity.removeCastleAssignment(serverWorld, removedCastleId, this.field_11867);
        this.castleId = null;
        this.method_5431();
    }

    public static void removeCastleAssignment(class_3218 serverWorld, UUID removedCastleId, class_2338 formerWatchpostPos) {
        if (serverWorld == null || removedCastleId == null || formerWatchpostPos == null) {
            return;
        }
        CommandGroupManager.get(serverWorld).detachCastle(removedCastleId);
        SoldierRegistryManager.get(serverWorld).clearCastle(removedCastleId);
        GeneralCooldownManager.get(serverWorld).remove(removedCastleId);
        CastleCaptureStateManager.get(serverWorld).cancel(removedCastleId);
        GarrisonManager.get(serverWorld).remove(removedCastleId);
        for (MedievalSoldierEntity soldier : serverWorld.method_8390(MedievalSoldierEntity.class, new class_238(formerWatchpostPos).method_1014(512.0), npc -> removedCastleId.equals(npc.getNpcIdentity().getCastleId()))) {
            soldier.getNpcIdentity().setCastleId(null);
            soldier.setHomePosition(null);
            SoldierRegistryManager.get(serverWorld).update(soldier);
        }
    }

    public boolean assignGroupToThisCastle(class_3222 player, UUID groupId) {
        class_3218 serverWorld;
        block10: {
            block9: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block9;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null && groupId != null && this.canPlayerAdminister((class_1657)player)) break block10;
            }
            return false;
        }
        CommandGroupManager groups = CommandGroupManager.get(serverWorld);
        CommandGroupData group = groups.get(groupId);
        if (group == null || !player.method_5667().equals(group.getOwnerId())) {
            return false;
        }
        UUID oldCastle = group.getCastleId();
        if (oldCastle != null && !oldCastle.equals(this.castleId)) {
            GarrisonData source = GarrisonManager.get(serverWorld).get(oldCastle);
            if (source == null || !CastleAccessPolicy.canAdminister(serverWorld, player, source)) {
                return false;
            }
            return GroupCastleTransferService.request(serverWorld, group, player.method_5667(), this.castleId) == GroupCastleTransferService.StartResult.STARTED;
        }
        if (!groups.assignGroupToCastle(groupId, player.method_5667(), this.castleId)) {
            return false;
        }
        GarrisonManager garrisons = GarrisonManager.get(serverWorld);
        SoldierRegistryManager registry = SoldierRegistryManager.get(serverWorld);
        for (UUID soldierId : group.getSoldierIds()) {
            garrisons.registerSoldier(this.castleId, soldierId);
            registry.updateMembership(soldierId, player.method_5667(), this.castleId, group.getId());
        }
        for (MedievalSoldierEntity soldier : serverWorld.method_8390(MedievalSoldierEntity.class, new class_238(this.field_11867).method_1014(512.0), npc -> group.getSoldierIds().contains(npc.method_5667()))) {
            soldier.getNpcIdentity().setCastleId(this.castleId);
            soldier.getNpcIdentity().setSquadId(group.getId());
            soldier.setHomePosition(this.field_11867);
            groups.reconcileMembership(soldier);
            registry.update(soldier);
        }
        return true;
    }

    public boolean detachGroupFromCastle(class_3222 player, UUID groupId) {
        class_3218 serverWorld;
        block8: {
            block7: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block7;
                serverWorld = (class_3218)class_19372;
                if (groupId != null && this.canPlayerAdminister((class_1657)player)) break block8;
            }
            return false;
        }
        CommandGroupManager groups = CommandGroupManager.get(serverWorld);
        CommandGroupData group = groups.get(groupId);
        if (group == null || !player.method_5667().equals(group.getOwnerId())) {
            return false;
        }
        UUID oldCastle = group.getCastleId();
        if (oldCastle == null || !groups.assignGroupToCastle(groupId, player.method_5667(), null)) {
            return false;
        }
        GarrisonManager garrisons = GarrisonManager.get(serverWorld);
        SoldierRegistryManager registry = SoldierRegistryManager.get(serverWorld);
        for (UUID soldierId : group.getSoldierIds()) {
            garrisons.unregisterSoldier(oldCastle, soldierId);
            registry.updateMembership(soldierId, player.method_5667(), null, group.getId());
        }
        for (MedievalSoldierEntity soldier : serverWorld.method_8390(MedievalSoldierEntity.class, new class_238(this.field_11867).method_1014(512.0), npc -> group.getSoldierIds().contains(npc.method_5667()))) {
            soldier.getNpcIdentity().setCastleId(null);
            soldier.getNpcIdentity().setSquadId(group.getId());
            soldier.setHomePosition(null);
            groups.reconcileMembership(soldier);
            registry.update(soldier);
        }
        return true;
    }

    @Override
    public String getCastleName() {
        class_1937 class_19372 = this.field_11863;
        if (class_19372 instanceof class_3218) {
            GarrisonData data;
            class_3218 serverWorld = (class_3218)class_19372;
            if (this.castleId != null && (data = GarrisonManager.get(serverWorld).get(this.castleId)) != null) {
                return data.getName();
            }
        }
        return "Wachposten";
    }

    public boolean renameCastle(class_3222 player, String name) {
        class_3218 serverWorld;
        block6: {
            block5: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block5;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null && this.canPlayerAdminister((class_1657)player)) break block6;
            }
            return false;
        }
        if (!GarrisonManager.get(serverWorld).rename(this.castleId, name)) {
            return false;
        }
        String castleName = this.getCastleName();
        for (MedievalSoldierEntity soldier : serverWorld.method_8390(MedievalSoldierEntity.class, new class_238(this.field_11867).method_1014(256.0), e -> this.castleId.equals(e.getNpcIdentity().getCastleId()))) {
            soldier.applyCastleDisplayName(castleName);
        }
        this.method_5431();
        return true;
    }

    @Override
    public int getSoldierCount() {
        class_3218 serverWorld;
        block3: {
            block2: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block2;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null) break block3;
            }
            return 0;
        }
        return GarrisonManager.get(serverWorld).getSoldierCount(this.castleId);
    }

    @Nullable
    public UUID getCastleId() {
        return this.castleId;
    }

    public boolean hasStagedCaptureSigil() {
        return !this.captureInventory.method_5438(0).method_7960();
    }

    public class_1799 createRelocationStack() {
        class_1799 stack = new class_1799((class_1935)ModBlocks.WATCHPOST);
        if (this.castleId == null) {
            return stack;
        }
        class_2487 root = stack.method_7948();
        class_2487 relocation = new class_2487();
        relocation.method_25927("CastleId", this.castleId);
        class_1799 sigil = this.orderInventory.method_5438(0);
        if (!sigil.method_7960()) {
            class_2487 sigilNbt = new class_2487();
            sigil.method_7953(sigilNbt);
            relocation.method_10566("OrderSigil", (class_2520)sigilNbt);
        }
        root.method_10566("MedievalMachineryWatchpostRelocation", (class_2520)relocation);
        return stack;
    }

    public void prepareRelocation() {
        class_3218 serverWorld;
        block3: {
            block2: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block2;
                serverWorld = (class_3218)class_19372;
                if (this.castleId != null) break block3;
            }
            return;
        }
        GarrisonManager.get(serverWorld).setCenter(this.castleId, null, null);
    }

    @Override
    public boolean restoreRelocation(class_3222 placer, class_1799 stack) {
        class_3218 serverWorld;
        block10: {
            block9: {
                class_1937 class_19372 = this.field_11863;
                if (!(class_19372 instanceof class_3218)) break block9;
                serverWorld = (class_3218)class_19372;
                if (placer != null && stack != null && stack.method_7985()) break block10;
            }
            return false;
        }
        class_2487 root = stack.method_7969();
        if (root == null || !root.method_10573("MedievalMachineryWatchpostRelocation", 10)) {
            return false;
        }
        class_2487 relocation = root.method_10562("MedievalMachineryWatchpostRelocation");
        if (!relocation.method_25928("CastleId")) {
            return false;
        }
        UUID restoredCastleId = relocation.method_25926("CastleId");
        GarrisonData garrison = GarrisonManager.get(serverWorld).get(restoredCastleId);
        if (garrison == null || !CastleAccessPolicy.canAdminister(serverWorld, placer, garrison)) {
            return false;
        }
        this.ownerId = garrison.getOwnerId();
        this.castleId = restoredCastleId;
        this.kingdomId = garrison.getKingdomId();
        this.orderStyle = garrison.getOrderStyle();
        if (relocation.method_10573("OrderSigil", 10)) {
            this.orderInventory.method_5447(0, class_1799.method_7915((class_2487)relocation.method_10562("OrderSigil")));
        } else {
            this.orderInventory.method_5447(0, class_1799.field_8037);
        }
        GarrisonManager.get(serverWorld).setCenter(restoredCastleId, this.field_11867, serverWorld.method_27983().method_29177().toString());
        for (UUID soldierId : garrison.getSoldierIds()) {
            MedievalSoldierEntity soldier;
            class_1297 class_12972 = serverWorld.method_14190(soldierId);
            if (!(class_12972 instanceof MedievalSoldierEntity) || !restoredCastleId.equals((soldier = (MedievalSoldierEntity)class_12972).getNpcIdentity().getCastleId())) continue;
            soldier.setHomePosition(this.field_11867);
            soldier.applyCastleDisplayName(garrison.getName());
            SoldierRegistryManager.get(serverWorld).update(soldier);
        }
        this.syncOrderBlockState();
        this.method_5431();
        return true;
    }

    public class_1799 peekActiveSigilForDissolve() {
        class_1799 stack = this.orderInventory.method_5438(0);
        return stack.method_7960() ? class_1799.field_8037 : stack.method_7972();
    }

    public class_1799 takeActiveSigilForDissolve() {
        class_1799 stack = this.orderInventory.method_5438(0);
        if (stack.method_7960()) {
            return class_1799.field_8037;
        }
        class_1799 copy = stack.method_7972();
        this.orderInventory.method_5447(0, class_1799.field_8037);
        return copy;
    }

    @Nullable
    public UUID getKingdomId() {
        return this.kingdomId;
    }

    protected void method_11007(class_2487 nbt) {
        super.method_11007(nbt);
        if (this.ownerId != null) {
            nbt.method_25927("Owner", this.ownerId);
        }
        if (this.kingdomId != null) {
            nbt.method_25927("KingdomId", this.kingdomId);
        }
        if (this.castleId != null) {
            nbt.method_25927("CastleId", this.castleId);
        }
        nbt.method_10582("OrderStyle", this.orderStyle.method_15434());
        if (!this.orderInventory.method_5438(0).method_7960()) {
            class_2487 sigilNbt = new class_2487();
            this.orderInventory.method_5438(0).method_7953(sigilNbt);
            nbt.method_10566("OrderSigil", (class_2520)sigilNbt);
        }
        if (!this.captureInventory.method_5438(0).method_7960()) {
            class_2487 captureNbt = new class_2487();
            this.captureInventory.method_5438(0).method_7953(captureNbt);
            nbt.method_10566("CaptureSigil", (class_2520)captureNbt);
        }
    }

    public void method_11014(class_2487 nbt) {
        super.method_11014(nbt);
        this.ownerId = nbt.method_25928("Owner") ? nbt.method_25926("Owner") : null;
        this.kingdomId = nbt.method_25928("KingdomId") ? nbt.method_25926("KingdomId") : null;
        this.castleId = nbt.method_25928("CastleId") ? nbt.method_25926("CastleId") : null;
        this.orderStyle = OrderStyle.fromSerializedName(nbt.method_10558("OrderStyle"));
        if (nbt.method_10573("OrderSigil", 10)) {
            this.orderInventory.method_5447(0, class_1799.method_7915((class_2487)nbt.method_10562("OrderSigil")));
        } else {
            this.orderInventory.method_5447(0, class_1799.field_8037);
        }
        if (nbt.method_10573("CaptureSigil", 10)) {
            this.captureInventory.method_5447(0, class_1799.method_7915((class_2487)nbt.method_10562("CaptureSigil")));
        } else {
            this.captureInventory.method_5447(0, class_1799.field_8037);
        }
        if (OrderSigilItem.isStamped(this.orderInventory.method_5438(0))) {
            this.kingdomId = OrderSigilItem.getKingdomId(this.orderInventory.method_5438(0));
            this.orderStyle = OrderSigilItem.getStampedOrder(this.orderInventory.method_5438(0));
        }
    }

    public class_2561 method_5476() {
        return class_2561.method_43471((String)"block.medieval-machinery.watchpost");
    }

    public class_1703 createMenu(int syncId, class_1661 inventory, class_1657 player) {
        return new WatchpostScreenHandler(syncId, inventory, this);
    }

    public void writeScreenOpeningData(class_3222 player, class_2540 buf) {
        this.writeOpeningData(player, buf, false);
    }

    /*
     * Iterators could be improved
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private void writeOpeningData(class_3222 player, class_2540 buf, boolean captureMode) {
        buf.method_10807(this.field_11867);
        buf.method_10788(this.getCastleName(), 48);
        buf.writeBoolean(captureMode);
        if (captureMode || !((var6_4 = this.field_11863) instanceof class_3218)) ** GOTO lbl-1000
        serverWorld = (class_3218)var6_4;
        if (this.castleId != null) {
            v0 /* !! */  = CommandGroupManager.get(serverWorld).getUiGroups(this.castleId, player.method_5667());
        } else lbl-1000:
        // 2 sources

        {
            v0 /* !! */  = List.of();
        }
        groups = v0 /* !! */ ;
        buf.method_10804(groups.size());
        for (CommandGroupData group : groups) {
            buf.method_10797(group.getId());
            buf.method_10788(group.getName(), 48);
        }
        if (captureMode || !((var7_7 = this.field_11863) instanceof class_3218)) ** GOTO lbl-1000
        serverWorld = (class_3218)var7_7;
        if (this.castleId != null) {
            v1 /* !! */  = PromotionRules.eligibleCandidates(serverWorld, this.castleId);
        } else lbl-1000:
        // 2 sources

        {
            v1 /* !! */  = List.of();
        }
        promotions = v1 /* !! */ ;
        buf.method_10804(promotions.size());
        for (SoldierRegistryData data : promotions) {
            buf.method_10797(data.getSoldierId());
            buf.method_10788(data.getName(), 48);
            buf.method_10788(data.getRank(), 16);
            buf.method_10804(data.getLevel());
            targetRank = PromotionRules.targetRank((class_3218)this.field_11863, this.castleId, data).serializedName();
            buf.method_10788(targetRank, 16);
            cfg = MedievalMachineryConfig.get();
            buf.method_10804("general".equals(targetRank) != false ? cfg.generalEmeraldCost : cfg.captainEmeraldCost);
            buf.method_10804("general".equals(targetRank) != false ? cfg.generalDiamondCost : cfg.captainDiamondCost);
            knights = PromotionRules.knightCount((class_3218)this.field_11863, this.castleId);
            soldiers = PromotionRules.soldierCount((class_3218)this.field_11863, this.castleId);
            buf.method_10804(knights);
            buf.method_10804(soldiers);
            buf.method_10804("general".equals(targetRank) != false ? cfg.generalMinimumLevel : cfg.captainMinimumLevel);
            buf.method_10804("general".equals(targetRank) != false ? cfg.generalRequiredKnights : cfg.captainRequiredKnights);
            buf.method_10804("general".equals(targetRank) != false ? cfg.generalRequiredSoldiers : 0);
        }
        recruitmentConfig = MedievalMachineryConfig.get();
        buf.writeBoolean(recruitmentConfig.recruitmentCostsEnabled);
        for (NpcRole role : NpcRole.values()) {
            buf.method_10804(RecruitmentCostPolicy.emeraldCost(recruitmentConfig, role));
        }
    }

    public ExtendedScreenHandlerFactory captureScreenFactory() {
        return new ExtendedScreenHandlerFactory(){

            public class_2561 method_5476() {
                return class_2561.method_43471((String)"gui.medieval-machinery.watchpost.capture_title");
            }

            public class_1703 createMenu(int syncId, class_1661 inventory, class_1657 player) {
                return new WatchpostScreenHandler(syncId, inventory, WatchpostBlockEntity.this, true);
            }

            public void writeScreenOpeningData(class_3222 player, class_2540 buf) {
                WatchpostBlockEntity.this.writeOpeningData(player, buf, true);
            }
        };
    }

    @Override
    public int getMinimumVillagers() {
        return 10;
    }
}

