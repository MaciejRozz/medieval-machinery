/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.KingdomData;
import com.medievalmachinery.mod.npc.organization.KingdomManager;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.minecraft.class_3218;

public final class SystemKingdoms {
    public static final UUID DRAGON_KNIGHTS_ID = UUID.nameUUIDFromBytes("medieval-machinery:system:dragon_knights".getBytes(StandardCharsets.UTF_8));
    public static final UUID DRAGON_KNIGHTS_KING_ID = UUID.nameUUIDFromBytes("medieval-machinery:system:dragon_knights:leader".getBytes(StandardCharsets.UTF_8));

    private SystemKingdoms() {
    }

    public static KingdomData ensureDragonKnights(class_3218 world) {
        KingdomManager manager = KingdomManager.get(world);
        KingdomData existing = manager.get(DRAGON_KNIGHTS_ID);
        if (existing != null) {
            return existing;
        }
        return manager.createSystemKingdom(DRAGON_KNIGHTS_ID, DRAGON_KNIGHTS_KING_ID, "Drachenritter", OrderStyle.NETHERITE);
    }
}

