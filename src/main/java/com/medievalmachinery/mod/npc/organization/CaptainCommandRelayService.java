/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_243;
import net.minecraft.class_3218;

public final class CaptainCommandRelayService {
    public static final double DEFAULT_DIRECT_COMMAND_RANGE = 300.0;

    private CaptainCommandRelayService() {
    }

    public static Set<MedievalSoldierEntity> collectLoadedRelayedMembers(class_3218 world, UUID ownerId, class_243 commanderPos, double directRange, Collection<CommandGroupData> groups) {
        LinkedHashSet<MedievalSoldierEntity> result = new LinkedHashSet<MedievalSoldierEntity>();
        if (world == null || ownerId == null || commanderPos == null || groups == null) {
            return result;
        }
        double maxSq = Math.max(1.0, directRange) * Math.max(1.0, directRange);
        for (CommandGroupData group : groups) {
            MedievalSoldierEntity captain;
            class_1297 captainEntity;
            UUID captainId;
            if (group == null || !ownerId.equals(group.getOwnerId()) || (captainId = group.getCaptainId()) == null || !((captainEntity = world.method_14190(captainId)) instanceof MedievalSoldierEntity) || !(captain = (MedievalSoldierEntity)captainEntity).method_5805() || !ownerId.equals(captain.getNpcIdentity().getOwnerId()) || !Objects.equals(group.getId(), captain.getNpcIdentity().getSquadId()) || captain.method_5707(commanderPos) > maxSq) continue;
            for (UUID memberId : group.getSoldierIds()) {
                MedievalSoldierEntity member;
                class_1297 entity = world.method_14190(memberId);
                if (!(entity instanceof MedievalSoldierEntity) || !(member = (MedievalSoldierEntity)entity).method_5805() || !ownerId.equals(member.getNpcIdentity().getOwnerId()) || !Objects.equals(group.getId(), member.getNpcIdentity().getSquadId())) continue;
                result.add(member);
            }
        }
        return result;
    }
}

