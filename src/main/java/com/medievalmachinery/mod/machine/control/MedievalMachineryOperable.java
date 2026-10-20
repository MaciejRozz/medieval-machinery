/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1799
 *  net.minecraft.class_243
 *  net.minecraft.class_3532
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.machine.control;

import com.medievalmachinery.mod.machine.control.MedievalMachineryOperator;
import com.medievalmachinery.mod.machine.core.MachineAimSolution;
import com.medievalmachinery.mod.machine.core.MachineCombatTarget;
import com.medievalmachinery.mod.machine.core.MachineCrewAction;
import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import com.medievalmachinery.mod.machine.core.MachineCrewSupplyNeed;
import com.medievalmachinery.mod.machine.core.MachineCrewWorkContext;
import com.medievalmachinery.mod.machine.core.MachineTargetCategory;
import com.medievalmachinery.mod.machine.core.MachineTargetProfile;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import org.jetbrains.annotations.Nullable;

public interface MedievalMachineryOperable {
    public class_1297 asEntity();

    public MedievalMachineryOperator medievalMachinery$getOperator();

    public void medievalMachinery$setAim(UUID var1, float var2, float var3);

    public void medievalMachinery$move(UUID var1, float var2, float var3);

    default public boolean medievalMachinery$isMobile() {
        return true;
    }

    default public float medievalMachinery$getNpcForwardInputSign() {
        return 1.0f;
    }

    default public class_1799 medievalMachinery$getNpcHeldVisual(class_1297 npc) {
        return class_1799.field_8037;
    }

    default public boolean medievalMachinery$canPlayerTakeMachineSlot(int slot) {
        return true;
    }

    default public void medievalMachinery$onNpcAssignmentCleared(class_1297 npc) {
    }

    @Nullable
    default public UUID medievalMachinery$getKingdomId() {
        return null;
    }

    @Nullable
    default public UUID medievalMachinery$getCastleId() {
        return null;
    }

    default public void medievalMachinery$bindPoliticalIdentity(@Nullable UUID kingdomId, @Nullable UUID castleId) {
    }

    default public MachineTargetProfile medievalMachinery$getTargetProfile() {
        return MachineTargetProfile.NONE;
    }

    default public double medievalMachinery$getNpcTargetingRange() {
        return 48.0;
    }

    default public double medievalMachinery$getNpcMinimumTargetingRange() {
        return 0.0;
    }

    default public double medievalMachinery$getNpcImpactSafetyRadius(MachineCombatTarget target) {
        return 0.0;
    }

    default public boolean medievalMachinery$requiresDirectNpcLineOfSight() {
        return true;
    }

    default public double medievalMachinery$getNpcTargetCategoryBias(MachineTargetCategory category) {
        return 0.0;
    }

    default public class_243 medievalMachinery$getNpcAimOrigin() {
        class_1297 machine = this.asEntity();
        return machine.method_19538().method_1031(0.0, (double)machine.method_17682() * 0.55, 0.0);
    }

    default public MachineAimSolution medievalMachinery$solveNpcAim(class_1309 target, float spreadMultiplier) {
        if (target == null || !target.method_5805()) {
            return MachineAimSolution.INVALID;
        }
        UUID id = target.method_5667();
        return this.medievalMachinery$solveNpcAimAt(target.method_19538().method_1031(0.0, (double)target.method_17682() * 0.55, 0.0), id.getMostSignificantBits() ^ id.getLeastSignificantBits(), spreadMultiplier);
    }

    default public MachineAimSolution medievalMachinery$solveNpcAim(MachineCombatTarget target, float spreadMultiplier) {
        if (target == null || target.aimPoint() == null) {
            return MachineAimSolution.INVALID;
        }
        return this.medievalMachinery$solveNpcAimAt(target.aimPoint(), target.stableKey(), spreadMultiplier);
    }

    default public MachineAimSolution medievalMachinery$solveNpcAimAt(class_243 aimPoint, long stableTargetKey, float spreadMultiplier) {
        if (aimPoint == null) {
            return MachineAimSolution.INVALID;
        }
        class_243 origin = this.medievalMachinery$getNpcAimOrigin();
        class_243 delta = aimPoint.method_1020(origin);
        double horizontal = Math.sqrt(delta.field_1352 * delta.field_1352 + delta.field_1350 * delta.field_1350);
        if (horizontal < 1.0E-4) {
            return MachineAimSolution.INVALID;
        }
        float yaw = (float)Math.toDegrees(Math.atan2(-delta.field_1352, delta.field_1350));
        float pitch = (float)Math.toDegrees(Math.atan2(delta.field_1351, horizontal));
        return new MachineAimSolution(class_3532.method_15393((float)yaw), pitch, true);
    }

    default public List<class_243> medievalMachinery$getNpcProjectilePath(MachineCombatTarget target, MachineAimSolution solution) {
        if (target == null || target.aimPoint() == null || solution == null || !solution.valid()) {
            return List.of();
        }
        return List.of(this.medievalMachinery$getNpcAimOrigin(), target.aimPoint());
    }

    default public boolean medievalMachinery$isNpcAimAligned(MachineAimSolution solution) {
        return solution != null && solution.valid() && Math.abs(class_3532.method_15393((float)(this.asEntity().method_36454() - solution.yaw()))) <= 4.0f;
    }

    default public boolean medievalMachinery$supportsCrewRole(MachineCrewRole role) {
        return role == MachineCrewRole.OPERATOR;
    }

    default public double medievalMachinery$operatorRearDistance() {
        return 1.45;
    }

    default public double medievalMachinery$operatorSideOffset() {
        return -0.55;
    }

    default public float medievalMachinery$getCrewStationYaw() {
        return this.asEntity().method_36454();
    }

    default public float medievalMachinery$operatorLookYaw() {
        return this.asEntity().method_36454();
    }

    default public float medievalMachinery$getNpcFiringLookYaw(MachineAimSolution solution) {
        return solution != null && solution.valid() ? solution.yaw() : this.medievalMachinery$operatorLookYaw();
    }

    default public class_243 medievalMachinery$getOperatorPosition() {
        return this.medievalMachinery$getCrewPosition(MachineCrewRole.OPERATOR, null);
    }

    default public class_243 medievalMachinery$getCrewPosition(MachineCrewRole role, UUID crewId) {
        class_1297 machine = this.asEntity();
        double yaw = Math.toRadians(this.medievalMachinery$getCrewStationYaw());
        double rear = this.medievalMachinery$operatorRearDistance();
        double side = this.medievalMachinery$operatorSideOffset();
        if (role == MachineCrewRole.LOADER) {
            rear = Math.max(0.55, rear * 0.55);
            side = 0.85;
        } else if (role == MachineCrewRole.MAINTENANCE) {
            rear = Math.max(0.85, rear * 0.8);
            side = -1.05;
        }
        if (crewId != null && role != MachineCrewRole.OPERATOR) {
            side += (double)(Math.floorMod(crewId.hashCode(), 3) - 1) * 0.22;
        }
        return new class_243(machine.method_23317() + Math.sin(yaw) * rear - Math.cos(yaw) * side, machine.method_23318(), machine.method_23321() - Math.cos(yaw) * rear - Math.sin(yaw) * side);
    }

    default public boolean medievalMachinery$claim(class_1297 operator) {
        return this.medievalMachinery$getOperator().tryClaim(operator);
    }

    default public boolean medievalMachinery$release(UUID operator) {
        return this.medievalMachinery$getOperator().release(operator);
    }

    default public boolean medievalMachinery$isOperatedBy(UUID operator) {
        return this.medievalMachinery$getOperator().isOperatedBy(operator);
    }

    default public MachineCrewAction medievalMachinery$getNpcCrewAction(class_1297 npc, MachineCrewRole role, MachineCrewWorkContext context) {
        return MachineCrewAction.IDLE;
    }

    default public List<MachineCrewSupplyNeed> medievalMachinery$getNpcCrewSupplyNeeds(class_1297 npc, MachineCrewRole role, MachineCrewWorkContext context) {
        return List.of();
    }

    default public List<MachineCrewSupplyNeed> medievalMachinery$getNpcCrewSupplyStockNeeds(class_1297 npc, MachineCrewRole role, MachineCrewWorkContext context) {
        return this.medievalMachinery$getNpcCrewSupplyNeeds(npc, role, context);
    }

    default public int medievalMachinery$getNpcCrewActionTicks(MachineCrewAction action, MachineCrewRole role) {
        return switch (action) {
            case MachineCrewAction.LOAD -> 30;
            case MachineCrewAction.AIM -> 8;
            case MachineCrewAction.IGNITE -> 12;
            case MachineCrewAction.FIRE -> 6;
            case MachineCrewAction.MAINTAIN -> 40;
            default -> 0;
        };
    }

    default public boolean medievalMachinery$performNpcCrewAction(class_1297 npc, MachineCrewRole role, MachineCrewAction action, MachineCrewWorkContext context) {
        return action == MachineCrewAction.IDLE;
    }

    default public void medievalMachinery$npcTick(class_1297 npc, float targetYaw, float targetPitch, float forward, float sideways) {
        if (!this.medievalMachinery$isOperatedBy(npc.method_5667()) && !this.medievalMachinery$claim(npc)) {
            return;
        }
        this.medievalMachinery$setAim(npc.method_5667(), targetYaw, targetPitch);
        if (this.medievalMachinery$isMobile()) {
            this.medievalMachinery$move(npc.method_5667(), forward, sideways);
        }
    }
}

