/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.kingdom;

import com.medievalmachinery.mod.client.command.CommandOverlayScreen;
import com.medievalmachinery.mod.client.kingdom.DiplomacyConfirmScreen;
import com.medievalmachinery.mod.client.network.ClientKingdomNetworking;
import com.medievalmachinery.mod.npc.organization.KingdomDiplomacyStance;
import com.medievalmachinery.mod.npc.organization.KingdomRelation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class DiplomacyScreen
extends class_437 {
    private static final int PANEL_WIDTH = 470;
    private static final int PANEL_HEIGHT = 310;
    private static final int LIST_WIDTH = 190;
    private static final int ROW_HEIGHT = 20;
    private static final int VISIBLE_ROWS = 7;
    private final ClientKingdomNetworking.DiplomacySnapshot snapshot;
    private final UUID preferredTarget;
    private Filter filter = Filter.ALL;
    private UUID selectedKingdomId;
    private int listOffset;
    private final List<class_4185> dynamicButtons = new ArrayList<class_4185>();
    private class_4185 allyButton;
    private class_4185 neutralButton;
    private class_4185 warButton;

    public DiplomacyScreen(ClientKingdomNetworking.DiplomacySnapshot snapshot) {
        this(snapshot, null);
    }

    public DiplomacyScreen(ClientKingdomNetworking.DiplomacySnapshot snapshot, UUID preferredTarget) {
        super((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.title"));
        this.snapshot = snapshot;
        this.preferredTarget = preferredTarget;
        this.selectedKingdomId = preferredTarget;
    }

    protected void method_25426() {
        int left = (this.field_22789 - 470) / 2;
        int top = Math.max(10, (this.field_22790 - 310) / 2);
        this.addFilterButton(Filter.ALL, left + 12, top + 42, 82);
        this.addFilterButton(Filter.ALLIED, left + 98, top + 42, 82);
        this.addFilterButton(Filter.NEUTRAL, left + 184, top + 42, 82);
        this.addFilterButton(Filter.WAR, left + 270, top + 42, 82);
        this.addFilterButton(Filter.OFFERS, left + 356, top + 42, 102);
        this.allyButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.ally"), button -> this.requestSet(KingdomDiplomacyStance.ALLY)).method_46434(left + 190 + 30, top + 211, 112, 20).method_46431());
        this.neutralButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.neutral"), button -> this.requestSet(KingdomDiplomacyStance.NEUTRAL)).method_46434(left + 190 + 148, top + 211, 112, 20).method_46431());
        this.warButton = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.war"), button -> this.requestSet(KingdomDiplomacyStance.WAR)).method_46434(left + 190 + 30, top + 237, 230, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.refresh"), button -> ClientKingdomNetworking.requestDiplomacy(this.selectedKingdomId)).method_46434(left + 12, top + 271, 106, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.back"), button -> {
            if (this.field_22787 != null) {
                this.field_22787.method_1507((class_437)new CommandOverlayScreen());
            }
        }).method_46434(left + 470 - 118, top + 271, 106, 20).method_46431());
        this.ensureSelection();
        this.rebuildKingdomRows();
        this.refreshActionButtons();
    }

    private void addFilterButton(Filter value, int x, int y, int w) {
        class_4185 button = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)value.key), b -> {
            this.filter = value;
            this.listOffset = 0;
            this.selectedKingdomId = null;
            this.ensureSelection();
            this.rebuildKingdomRows();
            this.refreshActionButtons();
        }).method_46434(x, y, w, 20).method_46431());
        this.dynamicButtons.add(button);
    }

    private List<ClientKingdomNetworking.DiplomacyEntry> filteredEntries() {
        if (!this.snapshot.hasKingdom()) {
            return List.of();
        }
        return this.snapshot.kingdoms().stream().filter(this::matchesFilter).sorted(Comparator.comparingInt(this::sortPriority).thenComparing(ClientKingdomNetworking.DiplomacyEntry::name, String.CASE_INSENSITIVE_ORDER)).toList();
    }

    private boolean matchesFilter(ClientKingdomNetworking.DiplomacyEntry entry) {
        return switch (this.filter.ordinal()) {
            default -> throw new IncompatibleClassChangeError();
            case 0 -> true;
            case 1 -> {
                if (entry.relation() == KingdomRelation.ALLIED) {
                    yield true;
                }
                yield false;
            }
            case 2 -> {
                if (entry.relation() == KingdomRelation.NEUTRAL) {
                    yield true;
                }
                yield false;
            }
            case 3 -> {
                if (entry.relation() == KingdomRelation.WAR) {
                    yield true;
                }
                yield false;
            }
            case 4 -> this.isIncomingAlliance(entry) || this.isOutgoingAlliance(entry);
        };
    }

    private int sortPriority(ClientKingdomNetworking.DiplomacyEntry entry) {
        if (this.isIncomingAlliance(entry)) {
            return 0;
        }
        if (entry.relation() == KingdomRelation.WAR) {
            return 1;
        }
        if (entry.relation() == KingdomRelation.ALLIED) {
            return 2;
        }
        if (this.isOutgoingAlliance(entry)) {
            return 3;
        }
        return 4;
    }

    private void ensureSelection() {
        List<ClientKingdomNetworking.DiplomacyEntry> entries = this.filteredEntries();
        if (entries.isEmpty()) {
            this.selectedKingdomId = null;
            return;
        }
        if (this.selectedKingdomId != null && entries.stream().anyMatch(e -> e.kingdomId().equals(this.selectedKingdomId))) {
            return;
        }
        if (this.preferredTarget != null && entries.stream().anyMatch(e -> e.kingdomId().equals(this.preferredTarget))) {
            this.selectedKingdomId = this.preferredTarget;
            return;
        }
        this.selectedKingdomId = entries.get(0).kingdomId();
    }

    private void rebuildKingdomRows() {
        for (class_4185 widget : List.copyOf(this.dynamicButtons)) {
            if (widget.method_46427() <= Math.max(10, (this.field_22790 - 310) / 2) + 62) continue;
            this.method_37066((class_364)widget);
            this.dynamicButtons.remove(widget);
        }
        int left = (this.field_22789 - 470) / 2;
        int top = Math.max(10, (this.field_22790 - 310) / 2);
        List<ClientKingdomNetworking.DiplomacyEntry> entries = this.filteredEntries();
        if (entries.isEmpty()) {
            return;
        }
        int maxOffset = Math.max(0, entries.size() - 7);
        this.listOffset = Math.max(0, Math.min(this.listOffset, maxOffset));
        int end = Math.min(entries.size(), this.listOffset + 7);
        for (int i = this.listOffset; i < end; ++i) {
            ClientKingdomNetworking.DiplomacyEntry entry = entries.get(i);
            String marker = this.relationMarker(entry);
            class_4185 row = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)(marker + entry.name())), b -> {
                this.selectedKingdomId = entry.kingdomId();
                ClientKingdomNetworking.rememberDiplomacyTarget(this.selectedKingdomId);
                this.rebuildKingdomRows();
                this.refreshActionButtons();
            }).method_46434(left + 12, top + 72 + (i - this.listOffset) * 20, 166, 18).method_46431());
            row.field_22763 = !entry.kingdomId().equals(this.selectedKingdomId);
            this.dynamicButtons.add(row);
        }
        if (entries.size() > 7) {
            class_4185 up = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u25b2"), b -> {
                if (this.listOffset > 0) {
                    --this.listOffset;
                    this.rebuildKingdomRows();
                }
            }).method_46434(left + 12, top + 216, 78, 18).method_46431());
            up.field_22763 = this.listOffset > 0;
            this.dynamicButtons.add(up);
            class_4185 down = (class_4185)this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43470((String)"\u25bc"), b -> {
                if (this.listOffset < maxOffset) {
                    ++this.listOffset;
                    this.rebuildKingdomRows();
                }
            }).method_46434(left + 96, top + 216, 78, 18).method_46431());
            down.field_22763 = this.listOffset < maxOffset;
            this.dynamicButtons.add(down);
        }
    }

    private String relationMarker(ClientKingdomNetworking.DiplomacyEntry entry) {
        if (this.isIncomingAlliance(entry)) {
            return "+ ";
        }
        return switch (entry.relation()) {
            case KingdomRelation.WAR -> "! ";
            case KingdomRelation.ALLIED -> "= ";
            default -> "  ";
        };
    }

    private ClientKingdomNetworking.DiplomacyEntry selected() {
        if (this.selectedKingdomId == null) {
            return null;
        }
        return this.snapshot.kingdoms().stream().filter(e -> e.kingdomId().equals(this.selectedKingdomId)).findFirst().orElse(null);
    }

    private void refreshActionButtons() {
        ClientKingdomNetworking.DiplomacyEntry entry = this.selected();
        boolean editable = entry != null && this.snapshot.canManage();
        boolean incomingAlly = this.isIncomingAlliance(entry);
        if (this.allyButton != null) {
            this.allyButton.method_25355((class_2561)(incomingAlly ? class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.accept_ally") : class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.ally")));
            boolean bl = this.allyButton.field_22763 = editable && (incomingAlly || entry.ownStance() != KingdomDiplomacyStance.ALLY);
        }
        if (this.neutralButton != null) {
            boolean bl = this.neutralButton.field_22763 = editable && entry.ownStance() != KingdomDiplomacyStance.NEUTRAL;
        }
        if (this.warButton != null) {
            this.warButton.field_22763 = editable && entry.ownStance() != KingdomDiplomacyStance.WAR;
        }
    }

    private boolean isIncomingAlliance(ClientKingdomNetworking.DiplomacyEntry entry) {
        return entry != null && entry.otherStance() == KingdomDiplomacyStance.ALLY && entry.ownStance() != KingdomDiplomacyStance.ALLY;
    }

    private boolean isOutgoingAlliance(ClientKingdomNetworking.DiplomacyEntry entry) {
        return entry != null && entry.ownStance() == KingdomDiplomacyStance.ALLY && entry.otherStance() != KingdomDiplomacyStance.ALLY;
    }

    private void requestSet(KingdomDiplomacyStance stance) {
        boolean confirmAllianceBreak;
        ClientKingdomNetworking.DiplomacyEntry entry = this.selected();
        if (entry == null || !this.snapshot.canManage()) {
            return;
        }
        boolean confirmWar = stance == KingdomDiplomacyStance.WAR;
        boolean bl = confirmAllianceBreak = stance == KingdomDiplomacyStance.NEUTRAL && entry.relation() == KingdomRelation.ALLIED;
        if (confirmWar || confirmAllianceBreak) {
            if (this.field_22787 != null) {
                this.field_22787.method_1507((class_437)new DiplomacyConfirmScreen(this, entry, stance, (class_2561)(confirmWar ? class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.confirm_war.title") : class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.confirm_break_alliance.title")), (class_2561)(confirmWar ? class_2561.method_43469((String)"gui.medieval-machinery.diplomacy.confirm_war.text", (Object[])new Object[]{entry.name()}) : class_2561.method_43469((String)"gui.medieval-machinery.diplomacy.confirm_break_alliance.text", (Object[])new Object[]{entry.name()}))));
            }
            return;
        }
        this.apply(entry, stance);
    }

    void apply(ClientKingdomNetworking.DiplomacyEntry entry, KingdomDiplomacyStance stance) {
        this.selectedKingdomId = entry.kingdomId();
        ClientKingdomNetworking.setDiplomacy(entry.kingdomId(), stance);
    }

    private class_2561 stance(KingdomDiplomacyStance stance) {
        return class_2561.method_43471((String)("diplomacy.stance.medieval-machinery." + stance.name().toLowerCase(Locale.ROOT)));
    }

    private class_2561 relation(KingdomRelation relation) {
        return class_2561.method_43471((String)("diplomacy.relation.medieval-machinery." + relation.name().toLowerCase(Locale.ROOT)));
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        context.method_25294(0, 0, this.field_22789, this.field_22790, -2013265920);
        int left = (this.field_22789 - 470) / 2;
        int top = Math.max(10, (this.field_22790 - 310) / 2);
        context.method_25294(left, top, left + 470, top + 310, -871033579);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, top + 12, 0xFFFFFF);
        if (!this.snapshot.hasKingdom()) {
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.no_kingdom"), this.field_22789 / 2, top + 120, 0xE0E0E0);
            super.method_25394(context, mouseX, mouseY, delta);
            return;
        }
        long allies = this.snapshot.kingdoms().stream().filter(e -> e.relation() == KingdomRelation.ALLIED).count();
        long neutral = this.snapshot.kingdoms().stream().filter(e -> e.relation() == KingdomRelation.NEUTRAL).count();
        long wars = this.snapshot.kingdoms().stream().filter(e -> e.relation() == KingdomRelation.WAR).count();
        long offers = this.snapshot.kingdoms().stream().filter(this::isIncomingAlliance).count();
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.diplomacy.summary", (Object[])new Object[]{allies, neutral, wars, offers}), this.field_22789 / 2, top + 28, 0xD8D8D8);
        List<ClientKingdomNetworking.DiplomacyEntry> entries = this.filteredEntries();
        if (this.snapshot.kingdoms().isEmpty()) {
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.no_others"), this.field_22789 / 2, top + 120, 0xE0E0E0);
        } else if (entries.isEmpty()) {
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.filter.empty"), left + 18, top + 92, 0xB0B0B0, false);
        }
        context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.kingdoms"), left + 12, top + 62, 0xFFFFFF, false);
        ClientKingdomNetworking.DiplomacyEntry entry = this.selected();
        int detailLeft = left + 190 + 18;
        if (entry != null) {
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43470((String)entry.name()), detailLeft + 128, top + 80, 0xFFFFFF);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.diplomacy.order", (Object[])new Object[]{class_2561.method_43471((String)("order.medieval-machinery." + entry.mainOrder().method_15434()))}), detailLeft, top + 104, 0xD0D0D0, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.diplomacy.own_stance", (Object[])new Object[]{this.stance(entry.ownStance())}), detailLeft, top + 124, 0xE0E0E0, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.diplomacy.other_stance", (Object[])new Object[]{this.stance(entry.otherStance())}), detailLeft, top + 144, 0xE0E0E0, false);
            context.method_51439(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.diplomacy.effective", (Object[])new Object[]{this.relation(entry.relation())}), detailLeft, top + 164, 0xFFFFFF, false);
            if (this.isIncomingAlliance(entry)) {
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.incoming_alliance"), detailLeft, top + 184, 0x80FF80, false);
            } else if (this.isOutgoingAlliance(entry)) {
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.outgoing_alliance"), detailLeft, top + 184, 0xD8D880, false);
            } else if (entry.relation() == KingdomRelation.WAR && entry.ownStance() != KingdomDiplomacyStance.WAR) {
                context.method_51439(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.incoming_war"), detailLeft, top + 184, 0xFF8080, false);
            }
        }
        if (!this.snapshot.canManage()) {
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.diplomacy.read_only"), left + 235, top + 260, 0xB0B0B0);
        }
        super.method_25394(context, mouseX, mouseY, delta);
    }

    public boolean method_25421() {
        return false;
    }

    @Environment(value=EnvType.CLIENT)
    private static enum Filter {
        ALL("gui.medieval-machinery.diplomacy.filter.all"),
        ALLIED("gui.medieval-machinery.diplomacy.filter.allied"),
        NEUTRAL("gui.medieval-machinery.diplomacy.filter.neutral"),
        WAR("gui.medieval-machinery.diplomacy.filter.war"),
        OFFERS("gui.medieval-machinery.diplomacy.filter.offers");

        private final String key;

        private Filter(String key) {
            this.key = key;
        }
    }
}

