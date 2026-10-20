/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_18
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.class_18;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

public final class RoyalSmelterBindingState
extends class_18 {
    private static final String STORAGE_KEY = "medieval_machinery_royal_smelters";
    private final Map<class_2338, Binding> bindings = new HashMap<class_2338, Binding>();

    public static RoyalSmelterBindingState get(class_3218 world) {
        return (RoyalSmelterBindingState)world.method_17983().method_17924(RoyalSmelterBindingState::fromNbt, RoyalSmelterBindingState::new, STORAGE_KEY);
    }

    public void bind(class_2338 pos, UUID kingdomId, OrderStyle style, String kingdomName) {
        if (pos == null || kingdomId == null || style == null || style == OrderStyle.NONE) {
            return;
        }
        this.bindings.put(pos.method_10062(), new Binding(kingdomId, style, kingdomName == null ? "" : kingdomName));
        this.method_80();
    }

    public void remove(class_2338 pos) {
        if (pos != null && this.bindings.remove(pos) != null) {
            this.method_80();
        }
    }

    @Nullable
    public Binding get(class_2338 pos) {
        return pos == null ? null : this.bindings.get(pos);
    }

    private static RoyalSmelterBindingState fromNbt(class_2487 nbt) {
        RoyalSmelterBindingState state = new RoyalSmelterBindingState();
        class_2499 rows = nbt.method_10554("Bindings", 10);
        for (int i = 0; i < rows.size(); ++i) {
            OrderStyle style;
            class_2487 row = rows.method_10602(i);
            if (!row.method_25928("KingdomId") || (style = OrderStyle.fromSerializedName(row.method_10558("OrderStyle"))) == OrderStyle.NONE) continue;
            class_2338 pos = new class_2338(row.method_10550("X"), row.method_10550("Y"), row.method_10550("Z"));
            state.bindings.put(pos, new Binding(row.method_25926("KingdomId"), style, row.method_10558("KingdomName")));
        }
        return state;
    }

    public class_2487 method_75(class_2487 nbt) {
        class_2499 rows = new class_2499();
        for (Map.Entry<class_2338, Binding> entry : this.bindings.entrySet()) {
            class_2487 row = new class_2487();
            class_2338 pos = entry.getKey();
            Binding binding = entry.getValue();
            row.method_10569("X", pos.method_10263());
            row.method_10569("Y", pos.method_10264());
            row.method_10569("Z", pos.method_10260());
            row.method_25927("KingdomId", binding.kingdomId());
            row.method_10582("OrderStyle", binding.orderStyle().method_15434());
            row.method_10582("KingdomName", binding.kingdomName());
            rows.add((Object)row);
        }
        nbt.method_10566("Bindings", (class_2520)rows);
        return nbt;
    }

    public record Binding(UUID kingdomId, OrderStyle orderStyle, String kingdomName) {
    }
}

