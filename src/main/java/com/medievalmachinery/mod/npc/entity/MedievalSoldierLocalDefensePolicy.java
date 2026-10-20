/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3218
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import java.util.UUID;
import net.minecraft.class_3218;
import org.jetbrains.annotations.Nullable;

final class MedievalSoldierLocalDefensePolicy {
    private MedievalSoldierLocalDefensePolicy() {
    }

    static boolean sameCommand(MedievalSoldierEntity source, MedievalSoldierEntity other, @Nullable UUID resolvedSourceGroup, class_3218 world) {
        UUID resolvedOther;
        if (source == null || other == null) {
            return false;
        }
        UUID otherGroup = other.getNpcIdentity().getSquadId();
        if (resolvedSourceGroup != null && otherGroup != null && resolvedSourceGroup.equals(resolvedOther = CommandGroupManager.get(world).resolveAlias(otherGroup))) {
            return true;
        }
        UUID owner = source.getNpcIdentity().getOwnerId();
        UUID otherOwner = other.getNpcIdentity().getOwnerId();
        if (owner != null && owner.equals(otherOwner)) {
            return true;
        }
        UUID castle = source.getNpcIdentity().getCastleId();
        UUID otherCastle = other.getNpcIdentity().getCastleId();
        return castle != null && castle.equals(otherCastle);
    }
}

