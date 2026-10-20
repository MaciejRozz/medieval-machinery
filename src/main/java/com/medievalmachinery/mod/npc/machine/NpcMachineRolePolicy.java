/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.npc.core.NpcRole;

public final class NpcMachineRolePolicy {
    private NpcMachineRolePolicy() {
    }

    public static boolean canPerform(NpcRole npcRole, MachineCrewRole crewRole) {
        if (npcRole == null || crewRole == null) {
            return false;
        }
        return switch (crewRole) {
            default -> throw new IncompatibleClassChangeError();
            case MachineCrewRole.OPERATOR -> {
                if (npcRole == NpcRole.CANNONEER || npcRole == NpcRole.ENGINEER) {
                    yield true;
                }
                yield false;
            }
            case MachineCrewRole.LOADER -> {
                if (npcRole == NpcRole.CANNONEER) {
                    yield true;
                }
                yield false;
            }
            case MachineCrewRole.MAINTENANCE -> npcRole == NpcRole.ENGINEER;
        };
    }

    public static boolean mayUseVirtualStandardMachineResources(NpcRole npcRole, MachineCrewRole role) {
        return npcRole == NpcRole.CANNONEER && (role == MachineCrewRole.OPERATOR || role == MachineCrewRole.LOADER);
    }
}

