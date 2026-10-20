/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_124
 *  net.minecraft.class_1792
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_1836
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2487
 *  net.minecraft.class_2561
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.item;

import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_124;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1836;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2487;
import net.minecraft.class_2561;
import org.jetbrains.annotations.Nullable;

public final class OrderSigilItem
extends class_1792 {
    private static final String ROOT = "MedievalMachinerySigil";
    private static final String KEY_KINGDOM = "KingdomId";
    private static final String KEY_ORDER = "OrderVariant";
    private static final String KEY_KINGDOM_NAME = "KingdomName";
    private final OrderStyle orderStyle;

    public OrderSigilItem(OrderStyle orderStyle, class_1792.class_1793 settings) {
        super(settings);
        this.orderStyle = orderStyle;
    }

    public OrderStyle getOrderStyle() {
        return this.orderStyle;
    }

    public class_1799 stamp(UUID kingdomId, String kingdomName) {
        class_1799 stack = new class_1799((class_1935)this);
        OrderSigilItem.stamp(stack, kingdomId, this.orderStyle, kingdomName);
        return stack;
    }

    public static void stamp(class_1799 stack, UUID kingdomId, OrderStyle style, String kingdomName) {
        if (!(stack.method_7909() instanceof OrderSigilItem) || OrderSigilItem.isStamped(stack)) {
            return;
        }
        class_2487 data = stack.method_7911(ROOT);
        data.method_25927(KEY_KINGDOM, kingdomId);
        data.method_10582(KEY_ORDER, style.method_15434());
        data.method_10582(KEY_KINGDOM_NAME, kingdomName == null ? "" : kingdomName);
    }

    public static boolean isStamped(class_1799 stack) {
        class_2487 data = stack.method_7941(ROOT);
        return stack.method_7909() instanceof OrderSigilItem && data != null && data.method_25928(KEY_KINGDOM) && OrderStyle.fromSerializedName(data.method_10558(KEY_ORDER)) != OrderStyle.NONE;
    }

    @Nullable
    public static UUID getKingdomId(class_1799 stack) {
        class_2487 data = stack.method_7941(ROOT);
        return data != null && data.method_25928(KEY_KINGDOM) ? data.method_25926(KEY_KINGDOM) : null;
    }

    public static OrderStyle getStampedOrder(class_1799 stack) {
        class_2487 data = stack.method_7941(ROOT);
        if (data == null) {
            return OrderStyle.NONE;
        }
        return OrderStyle.fromSerializedName(data.method_10558(KEY_ORDER));
    }

    public static String getKingdomNameSnapshot(class_1799 stack) {
        class_2487 data = stack.method_7941(ROOT);
        return data == null ? "" : data.method_10558(KEY_KINGDOM_NAME);
    }

    public static boolean canStackStampedTogether(class_1799 first, class_1799 second) {
        if (first.method_7960() || second.method_7960()) {
            return false;
        }
        if (first.method_7909() != second.method_7909()) {
            return false;
        }
        if (!(first.method_7909() instanceof OrderSigilItem)) {
            return false;
        }
        if (!OrderSigilItem.isStamped(first) || !OrderSigilItem.isStamped(second)) {
            return false;
        }
        UUID firstKingdom = OrderSigilItem.getKingdomId(first);
        UUID secondKingdom = OrderSigilItem.getKingdomId(second);
        if (firstKingdom == null || !firstKingdom.equals(secondKingdom)) {
            return false;
        }
        return OrderSigilItem.getStampedOrder(first) == OrderSigilItem.getStampedOrder(second);
    }

    public void method_7851(class_1799 stack, @Nullable class_1937 world, List<class_2561> tooltip, class_1836 context) {
        super.method_7851(stack, world, tooltip, context);
        if (!OrderSigilItem.isStamped(stack)) {
            tooltip.add((class_2561)class_2561.method_43471((String)"tooltip.medieval-machinery.order_sigil.unstamped").method_27692(class_124.field_1063));
            return;
        }
        String kingdom = OrderSigilItem.getKingdomNameSnapshot(stack);
        if (!kingdom.isBlank()) {
            tooltip.add((class_2561)class_2561.method_43469((String)"tooltip.medieval-machinery.order_sigil.kingdom", (Object[])new Object[]{kingdom}).method_27692(class_124.field_1065));
        }
        tooltip.add((class_2561)class_2561.method_43469((String)"tooltip.medieval-machinery.order_sigil.kingdom_id", (Object[])new Object[]{OrderSigilItem.getKingdomId(stack).toString().substring(0, 8)}).method_27692(class_124.field_1063));
    }
}

