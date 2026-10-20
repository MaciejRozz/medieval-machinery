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
 *  net.minecraft.class_2561
 *  net.minecraft.class_3222
 *  net.minecraft.class_3913
 *  net.minecraft.class_3919
 */
package com.medievalmachinery.mod.screen.garrison;

import com.medievalmachinery.mod.block.garrison.WatchpostMenuAccess;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.recruitment.RecruitmentCostPolicy;
import com.medievalmachinery.mod.platform.RegisteredContent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1263;
import net.minecraft.class_1277;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_3222;
import net.minecraft.class_3913;
import net.minecraft.class_3919;

public final class WatchpostScreenHandler
extends class_1703 {
    private final class_1657 player;
    public static final int BUTTON_SWORDSMAN = 0;
    public static final int BUTTON_AXEMAN = 1;
    public static final int BUTTON_ARCHER = 2;
    public static final int BUTTON_CROSSBOWMAN = 3;
    public static final int BUTTON_SPEARMAN = 4;
    public static final int BUTTON_CANNONEER = 5;
    public static final int BUTTON_KNIGHT = 6;
    public static final int BUTTON_QUARTERMASTER = 7;
    public static final int BUTTON_SCOUT = 8;
    public static final int BUTTON_ENGINEER = 9;
    public static final int BUTTON_MOUNTED_ARCHER = 10;
    public static final int BUTTON_MOUNTED_KNIGHT = 11;
    public static final int BUTTON_START_CAPTURE = 100;
    private static final int PROP_TOTAL = 0;
    private static final int PROP_EMPLOYED = 1;
    private static final int PROP_AVAILABLE = 2;
    private static final int PROP_SOLDIERS = 3;
    private static final int PROP_TAX_BALANCE = 4;
    private static final int PROP_TAX_VILLAGERS = 5;
    private final WatchpostMenuAccess watchpost;
    private final class_2338 pos;
    private final class_3913 properties;
    private final String castleName;
    private final List<RecruitmentGroupOption> recruitmentGroups;
    private final List<PromotionOption> promotionOptions;
    private final boolean captureMode;
    private final boolean recruitmentCostsEnabled;
    private final int[] recruitmentEmeraldCosts;

    public WatchpostScreenHandler(int syncId, class_1661 inventory, class_2540 buf) {
        this(syncId, inventory, WatchpostScreenHandler.readOpeningData(buf));
    }

    private WatchpostScreenHandler(int syncId, class_1661 inventory, OpeningData data) {
        this(syncId, inventory, data.pos(), null, (class_3913)new class_3919(6), data.castleName(), data.groups(), data.promotions(), data.captureMode(), data.recruitmentCostsEnabled(), data.recruitmentEmeraldCosts());
    }

    public WatchpostScreenHandler(int syncId, class_1661 inventory, WatchpostMenuAccess watchpost) {
        this(syncId, inventory, watchpost, false);
    }

    public WatchpostScreenHandler(int syncId, class_1661 inventory, WatchpostMenuAccess watchpost, boolean captureMode) {
        this(syncId, inventory, watchpost == null ? class_2338.field_10980 : watchpost.getWatchpostPos(), watchpost, WatchpostScreenHandler.createServerProperties(watchpost), watchpost == null ? "Wachposten" : watchpost.getCastleName(), List.of(), List.of(), captureMode, MedievalMachineryConfig.get().recruitmentCostsEnabled, WatchpostScreenHandler.serverRecruitmentCosts());
    }

    private static OpeningData readOpeningData(class_2540 buf) {
        class_2338 pos = buf.method_10811();
        String castleName = buf.method_10800(48);
        boolean captureMode = buf.readBoolean();
        int count = Math.max(0, Math.min(128, buf.method_10816()));
        ArrayList<RecruitmentGroupOption> groups = new ArrayList<RecruitmentGroupOption>(count);
        for (int i = 0; i < count; ++i) {
            groups.add(new RecruitmentGroupOption(buf.method_10790(), buf.method_10800(48)));
        }
        int promotionCount = Math.max(0, Math.min(128, buf.method_10816()));
        ArrayList<PromotionOption> promotions = new ArrayList<PromotionOption>(promotionCount);
        for (int i = 0; i < promotionCount; ++i) {
            promotions.add(new PromotionOption(buf.method_10790(), buf.method_10800(48), buf.method_10800(16), buf.method_10816(), buf.method_10800(16), buf.method_10816(), buf.method_10816(), buf.method_10816(), buf.method_10816(), buf.method_10816(), buf.method_10816(), buf.method_10816()));
        }
        boolean recruitmentCostsEnabled = buf.readBoolean();
        int[] recruitmentEmeraldCosts = new int[NpcRole.values().length];
        for (int i = 0; i < recruitmentEmeraldCosts.length; ++i) {
            recruitmentEmeraldCosts[i] = Math.max(0, buf.method_10816());
        }
        return new OpeningData(pos, castleName, captureMode, List.copyOf(groups), List.copyOf(promotions), recruitmentCostsEnabled, recruitmentEmeraldCosts);
    }

    private WatchpostScreenHandler(int syncId, class_1661 inventory, class_2338 pos, final WatchpostMenuAccess watchpost, class_3913 properties, String castleName, List<RecruitmentGroupOption> recruitmentGroups, List<PromotionOption> promotionOptions, final boolean captureMode, boolean recruitmentCostsEnabled, int[] recruitmentEmeraldCosts) {
        super(RegisteredContent.watchpostScreen(), syncId);
        this.player = inventory.field_7546;
        this.watchpost = watchpost;
        this.pos = pos;
        this.properties = properties;
        this.castleName = castleName;
        this.recruitmentGroups = recruitmentGroups == null ? List.of() : List.copyOf(recruitmentGroups);
        this.promotionOptions = promotionOptions == null ? List.of() : List.copyOf(promotionOptions);
        this.captureMode = captureMode;
        this.recruitmentCostsEnabled = recruitmentCostsEnabled;
        this.recruitmentEmeraldCosts = recruitmentEmeraldCosts == null ? new int[NpcRole.values().length] : (int[])recruitmentEmeraldCosts.clone();
        this.method_17360(properties);
        class_1277 sigilInventory = watchpost != null ? (captureMode ? watchpost.getCaptureInventory() : watchpost.getOrderInventory()) : new class_1277(1);
        this.method_7621(new class_1735(this, (class_1263)sigilInventory, 0, 367, 49){
            final /* synthetic */ WatchpostScreenHandler this$0;
            {
                this.this$0 = this$0;
                super(arg0, arg1, arg2, arg3);
            }

            public boolean method_7680(class_1799 stack) {
                if (captureMode && watchpost != null && watchpost.isCaptureActive()) {
                    return false;
                }
                if (!(stack.method_7909() instanceof OrderSigilItem) || !OrderSigilItem.isStamped(stack)) {
                    return false;
                }
                return watchpost == null || this.this$0.player.method_37908().field_9236 || watchpost.canPlayerUseSigil(this.this$0.player, stack);
            }

            public int method_7675() {
                return 1;
            }

            public boolean method_7674(class_1657 playerEntity) {
                return !captureMode || watchpost == null || !watchpost.isCaptureActive();
            }
        });
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.method_7621(new class_1735((class_1263)inventory, col + row * 9 + 9, 130 + col * 18, 285 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.method_7621(new class_1735((class_1263)inventory, col, 130 + col * 18, 343));
        }
    }

    private static int[] serverRecruitmentCosts() {
        int[] costs = new int[NpcRole.values().length];
        MedievalMachineryConfig cfg = MedievalMachineryConfig.get();
        for (NpcRole role : NpcRole.values()) {
            costs[role.ordinal()] = RecruitmentCostPolicy.emeraldCost(cfg, role);
        }
        return costs;
    }

    private static class_3913 createServerProperties(final WatchpostMenuAccess watchpost) {
        return new class_3913(){

            public int method_17390(int index) {
                if (watchpost == null) {
                    return 0;
                }
                return switch (index) {
                    case 0 -> watchpost.getTotalVillagerCount();
                    case 1 -> watchpost.getEmployedVillagerCount();
                    case 2 -> watchpost.getAvailableVillagerCount();
                    case 3 -> watchpost.getSoldierCount();
                    case 4 -> watchpost.getStoredTaxEmeralds();
                    case 5 -> watchpost.getLastKnownTaxVillagerCount();
                    default -> 0;
                };
            }

            public void method_17391(int index, int value) {
            }

            public int method_17389() {
                return 6;
            }
        };
    }

    public String getCastleName() {
        return this.castleName;
    }

    public class_2338 getWatchpostPos() {
        return this.pos;
    }

    public List<RecruitmentGroupOption> getRecruitmentGroups() {
        return this.recruitmentGroups;
    }

    public List<PromotionOption> getPromotionOptions() {
        return this.promotionOptions;
    }

    public boolean isCaptureMode() {
        return this.captureMode;
    }

    public boolean areRecruitmentCostsEnabled() {
        return this.recruitmentCostsEnabled;
    }

    public int getRecruitmentEmeraldCost(NpcRole role) {
        if (!this.recruitmentCostsEnabled || role == null) {
            return 0;
        }
        int index = role.ordinal();
        return index >= 0 && index < this.recruitmentEmeraldCosts.length ? Math.max(0, this.recruitmentEmeraldCosts[index]) : 0;
    }

    public int getTotalVillagerCount() {
        return this.properties.method_17390(0);
    }

    public int getEmployedVillagerCount() {
        return this.properties.method_17390(1);
    }

    public int getAvailableVillagerCount() {
        return this.properties.method_17390(2);
    }

    public int getSoldierCount() {
        return this.properties.method_17390(3);
    }

    public int getTaxBalance() {
        return this.properties.method_17390(4);
    }

    public int getTaxVillagerCount() {
        return this.properties.method_17390(5);
    }

    public int getMinimumVillagers() {
        return 10;
    }

    public boolean method_7604(class_1657 player, int id) {
        class_3222 serverPlayer;
        block22: {
            block21: {
                if (!(player instanceof class_3222)) break block21;
                serverPlayer = (class_3222)player;
                if (this.watchpost != null) break block22;
            }
            return false;
        }
        if (id == 100) {
            boolean success = this.captureMode && this.watchpost.startCapture(serverPlayer);
            serverPlayer.method_7353((class_2561)class_2561.method_43471((String)(success ? "message.medieval-machinery.watchpost.capture_started" : "message.medieval-machinery.watchpost.capture_denied")), false);
            return true;
        }
        if (this.captureMode) {
            return false;
        }
        NpcRole role = switch (id) {
            case 1 -> NpcRole.AXEMAN;
            case 2 -> NpcRole.ARCHER;
            case 3 -> NpcRole.CROSSBOWMAN;
            case 4 -> NpcRole.SPEARMAN;
            case 5 -> NpcRole.CANNONEER;
            case 6 -> NpcRole.KNIGHT;
            case 7 -> NpcRole.QUARTERMASTER;
            case 8 -> NpcRole.SCOUT;
            case 9 -> null;
            case 10 -> NpcRole.MOUNTED_ARCHER;
            case 11 -> NpcRole.MOUNTED_KNIGHT;
            default -> NpcRole.SWORDSMAN;
        };
        boolean success = this.watchpost.recruit(serverPlayer, role);
        if (!success) {
            if (!this.watchpost.hasValidStampedSigil()) {
                serverPlayer.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.watchpost.requires_stamped_sigil"), false);
            } else {
                serverPlayer.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.watchpost.not_enough_villagers", (Object[])new Object[]{this.watchpost == null ? 0 : this.watchpost.getMinimumVillagers(), this.watchpost.getTotalVillagerCount(), this.watchpost.getAvailableVillagerCount()}), false);
            }
        }
        return true;
    }

    public class_1799 method_7601(class_1657 player, int slotIndex) {
        if (this.captureMode && this.watchpost != null && this.watchpost.isCaptureActive()) {
            return class_1799.field_8037;
        }
        if (slotIndex < 0 || slotIndex >= this.field_7761.size()) {
            return class_1799.field_8037;
        }
        class_1735 slot = (class_1735)this.field_7761.get(slotIndex);
        if (!slot.method_7681()) {
            return class_1799.field_8037;
        }
        class_1799 source = slot.method_7677();
        class_1799 copy = source.method_7972();
        if (slotIndex == 0 ? !this.method_7616(source, 1, 37, true) : !(source.method_7909() instanceof OrderSigilItem) || !OrderSigilItem.isStamped(source) || !this.method_7616(source, 0, 1, false)) {
            return class_1799.field_8037;
        }
        if (source.method_7960()) {
            slot.method_48931(class_1799.field_8037);
        } else {
            slot.method_7668();
        }
        return copy;
    }

    public void method_7595(class_1657 player) {
        super.method_7595(player);
        if (this.captureMode && this.watchpost != null && player instanceof class_3222) {
            class_3222 serverPlayer = (class_3222)player;
            if (!this.watchpost.isCaptureActive()) {
                this.watchpost.returnCaptureSigil(serverPlayer);
            }
        }
    }

    public boolean method_7597(class_1657 player) {
        if (player.method_37908().field_9236) {
            return player.method_5649((double)this.pos.method_10263() + 0.5, (double)this.pos.method_10264() + 0.5, (double)this.pos.method_10260() + 0.5) <= 64.0;
        }
        return this.watchpost != null && (this.captureMode ? this.watchpost.canOpenCapture((class_3222)player) : this.watchpost.canPlayerAdminister(player)) && player.method_5649((double)this.pos.method_10263() + 0.5, (double)this.pos.method_10264() + 0.5, (double)this.pos.method_10260() + 0.5) <= 64.0;
    }

    private record OpeningData(class_2338 pos, String castleName, boolean captureMode, List<RecruitmentGroupOption> groups, List<PromotionOption> promotions, boolean recruitmentCostsEnabled, int[] recruitmentEmeraldCosts) {
    }

    public record RecruitmentGroupOption(UUID id, String name) {
    }

    public record PromotionOption(UUID soldierId, String name, String currentRank, int level, String targetRank, int emeraldCost, int diamondCost, int knightCount, int soldierCount, int requiredLevel, int requiredKnights, int requiredSoldiers) {
    }
}

