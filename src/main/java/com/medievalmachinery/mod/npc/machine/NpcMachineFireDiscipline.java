/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.machine.core.MachineCombatTarget;
import com.medievalmachinery.mod.machine.core.MachineTargetCategory;

public final class NpcMachineFireDiscipline {
    private NpcMachineFireDiscipline() {
    }

    public static double saturationPenalty(MachineCombatTarget target, int otherMachines) {
        if (target == null || otherMachines <= 0) {
            return 0.0;
        }
        double perMachine = switch (target.category()) {
            case MachineTargetCategory.INFANTRY -> 12.0;
            case MachineTargetCategory.CAVALRY -> 9.0;
            case MachineTargetCategory.SHIPS -> 4.0;
            case MachineTargetCategory.LARGE_TARGETS -> 3.0;
            case MachineTargetCategory.SIEGE_WEAPONS, MachineTargetCategory.FORTIFICATIONS -> 1.5;
            default -> 5.0;
        };
        return Math.min(30.0, perMachine * (double)otherMachines);
    }

    public static boolean mayFocusFire(MachineCombatTarget target) {
        if (target == null) {
            return false;
        }
        return target.category() == MachineTargetCategory.SIEGE_WEAPONS || target.category() == MachineTargetCategory.FORTIFICATIONS || target.category() == MachineTargetCategory.LARGE_TARGETS || target.category() == MachineTargetCategory.SHIPS;
    }
}

