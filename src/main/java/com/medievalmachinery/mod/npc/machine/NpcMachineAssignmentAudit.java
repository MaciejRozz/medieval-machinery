/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.machine.control.MedievalMachineryOperable;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineRolePolicy;

public final class NpcMachineAssignmentAudit {
    private NpcMachineAssignmentAudit() {
    }

    public static Result validate(MedievalSoldierEntity npc, MedievalMachineryOperable machine, MachineCrewRole role) {
        if (npc == null || machine == null || role == null) {
            return Result.INVALID_REFERENCE;
        }
        if (!machine.medievalMachinery$supportsCrewRole(role)) {
            return Result.UNSUPPORTED_ROLE;
        }
        if (!NpcMachineRolePolicy.canPerform(npc.getNpcRole(), role)) {
            return Result.WRONG_NPC_CLASS;
        }
        return Result.OK;
    }

    public static enum Result {
        OK,
        INVALID_REFERENCE,
        UNSUPPORTED_ROLE,
        WRONG_NPC_CLASS;

    }
}

