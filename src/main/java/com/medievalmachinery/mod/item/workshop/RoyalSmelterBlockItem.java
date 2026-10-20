/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1747
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2680
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.item.workshop;

import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.npc.organization.RoyalSmelterBindingState;
import java.util.UUID;
import net.minecraft.class_1657;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class RoyalSmelterBlockItem
extends class_1747 {
    private static final String ROOT = "MedievalMachineryRoyalSmelter";
    private static final String KEY_KINGDOM = "KingdomId";
    private static final String KEY_ORDER = "OrderVariant";
    private static final String KEY_NAME = "KingdomName";

    public RoyalSmelterBlockItem(class_2248 block, class_1792.class_1793 settings) {
        super(block, settings);
    }

    public static class_1799 createFromSigil(class_2248 block, class_1799 sigil) {
        if (!OrderSigilItem.isStamped(sigil)) {
            return class_1799.field_8037;
        }
        UUID kingdomId = OrderSigilItem.getKingdomId(sigil);
        OrderStyle order = OrderSigilItem.getStampedOrder(sigil);
        if (kingdomId == null || order == OrderStyle.NONE) {
            return class_1799.field_8037;
        }
        class_1799 result = new class_1799((class_1935)block);
        class_2487 data = result.method_7911(ROOT);
        data.method_25927(KEY_KINGDOM, kingdomId);
        data.method_10582(KEY_ORDER, order.method_15434());
        data.method_10582(KEY_NAME, OrderSigilItem.getKingdomNameSnapshot(sigil));
        return result;
    }

    public static OrderStyle getBoundOrder(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return OrderStyle.NONE;
        }
        class_2487 data = stack.method_7941(ROOT);
        return data == null ? OrderStyle.NONE : OrderStyle.fromSerializedName(data.method_10558(KEY_ORDER));
    }

    public static boolean isBound(class_1799 stack) {
        class_2487 data = stack.method_7941(ROOT);
        return data != null && data.method_25928(KEY_KINGDOM) && OrderStyle.fromSerializedName(data.method_10558(KEY_ORDER)) != OrderStyle.NONE;
    }

    protected boolean method_7710(class_2338 pos, class_1937 world, @Nullable class_1657 player, class_1799 stack, class_2680 state) {
        boolean result = super.method_7710(pos, world, player, stack, state);
        if (world instanceof class_3218) {
            OrderStyle order;
            class_3218 serverWorld = (class_3218)world;
            class_2487 data = stack.method_7941(ROOT);
            if (data != null && data.method_25928(KEY_KINGDOM) && (order = OrderStyle.fromSerializedName(data.method_10558(KEY_ORDER))) != OrderStyle.NONE) {
                RoyalSmelterBindingState.get(serverWorld).bind(pos, data.method_25926(KEY_KINGDOM), order, data.method_10558(KEY_NAME));
            }
        }
        return result;
    }
}

