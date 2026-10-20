/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1309
 *  net.minecraft.class_1799
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.npc.armor.ArmorStyle;
import com.medievalmachinery.mod.npc.armor.ArmorTier;
import com.medievalmachinery.mod.npc.armor.NpcArmorProfiles;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_1799;

@Environment(value=EnvType.CLIENT)
public final class MedievalArmorGeoContext {
    private static final ThreadLocal<State> CURRENT = ThreadLocal.withInitial(() -> new State(ArmorStyle.HEAVY_BRIGANDINE, OrderStyle.NONE, ArmorTier.ORDER));

    private MedievalArmorGeoContext() {
    }

    public static void prepare(class_1309 wearer, class_1799 stack) {
        ArmorStyle style;
        ArmorTier tier = MedievalArmorItem.getTier(stack);
        if (wearer instanceof MedievalSoldierEntity) {
            MedievalSoldierEntity soldier = (MedievalSoldierEntity)wearer;
            style = NpcArmorProfiles.styleForEquippedArmor(soldier.getNpcRole(), soldier.getNpcRank(), tier);
        } else {
            style = MedievalArmorItem.getArmorStyle(stack);
        }
        OrderStyle order = MedievalArmorItem.getOrderStyle(stack);
        if (order == OrderStyle.NONE && wearer instanceof MedievalSoldierEntity) {
            MedievalSoldierEntity soldier = (MedievalSoldierEntity)wearer;
            order = soldier.getOrderStyle();
        }
        CURRENT.set(new State(style, order, tier));
    }

    public static State current() {
        return CURRENT.get();
    }

    @Environment(value=EnvType.CLIENT)
    public record State(ArmorStyle style, OrderStyle order, ArmorTier tier) {
    }
}

