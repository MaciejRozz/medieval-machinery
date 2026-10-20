/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2323
 *  net.minecraft.class_2338
 *  net.minecraft.class_2349
 *  net.minecraft.class_2350
 *  net.minecraft.class_2350$class_2353
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_3218
 *  net.minecraft.class_3481
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcFormationLayout;
import com.medievalmachinery.mod.npc.core.NpcFormationState;
import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.CommandGroupData;
import com.medievalmachinery.mod.npc.organization.CommandGroupManager;
import java.util.UUID;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2323;
import net.minecraft.class_2338;
import net.minecraft.class_2349;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3481;

final class MedievalSoldierFormationController {
    MedievalSoldierFormationController() {
    }

    boolean isInFormationChokepoint(MedievalSoldierEntity soldier) {
        class_2338 feet = soldier.method_24515();
        if (soldier.isOnClimbableSurface()) {
            return true;
        }
        for (class_2350 direction : class_2350.class_2353.field_11062) {
            class_2338 pos = feet.method_10093(direction);
            class_2680 state = soldier.method_37908().method_8320(pos);
            if ((!(state.method_26204() instanceof class_2323) || !state.method_26164(class_3481.field_15494)) && !(state.method_26204() instanceof class_2349) || !(soldier.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5) <= 9.0)) continue;
            return true;
        }
        class_2350 facing = soldier.method_5735();
        class_2350 left = facing.method_10160();
        class_2350 right = facing.method_10170();
        class_2338 leftPos = feet.method_10093(left);
        class_2338 rightPos = feet.method_10093(right);
        boolean leftBlocked = !soldier.method_37908().method_8320(leftPos).method_26220((class_1922)soldier.method_37908(), leftPos).method_1110();
        boolean rightBlocked = !soldier.method_37908().method_8320(rightPos).method_26220((class_1922)soldier.method_37908(), rightPos).method_1110();
        return leftBlocked && rightBlocked;
    }

    boolean isFormationCorridor(MedievalSoldierEntity soldier, class_1657 owner) {
        return this.isNarrowAt(soldier, soldier.method_24515()) || owner != null && this.isNarrowAt(soldier, owner.method_24515());
    }

    private boolean isNarrowAt(MedievalSoldierEntity soldier, class_2338 center) {
        class_1937 world = soldier.method_37908();
        for (class_2338 pos : class_2338.method_10097((class_2338)center.method_10069(-1, 0, -1), (class_2338)center.method_10069(1, 1, 1))) {
            class_2680 state = world.method_8320(pos);
            if (!(state.method_26204() instanceof class_2323) && !(state.method_26204() instanceof class_2349)) continue;
            return true;
        }
        boolean west = this.bodyColumnBlocked(soldier, center.method_10067());
        boolean east = this.bodyColumnBlocked(soldier, center.method_10078());
        boolean north = this.bodyColumnBlocked(soldier, center.method_10095());
        boolean south = this.bodyColumnBlocked(soldier, center.method_10072());
        return west && east || north && south;
    }

    private boolean bodyColumnBlocked(MedievalSoldierEntity soldier, class_2338 feet) {
        class_1937 world = soldier.method_37908();
        return !world.method_8320(feet).method_26220((class_1922)world, feet).method_1110() || !world.method_8320(feet.method_10084()).method_26220((class_1922)world, feet.method_10084()).method_1110();
    }

    void beginExplicitAssembly(MedievalSoldierEntity soldier, class_243 target) {
        if (soldier == null || target == null) {
            return;
        }
        soldier.formationStateForController().clearTemporaryRelease();
        soldier.formationStateForController().assembleAt(target, 240);
        class_1309 current = soldier.method_5968();
        if (current != null && !soldier.isCommandedCombatTarget(current)) {
            soldier.method_5980(null);
            soldier.combatTargetStateForController().resetSight();
            soldier.combatTargetStateForController().suppressTargetAcquisition(30);
        }
        if (soldier.getOrderType() == NpcOrderType.MOVE_TO_POSITION) {
            soldier.formationTakeOverMove(target);
        } else if (soldier.getOrderType() == NpcOrderType.HOLD_POSITION) {
            soldier.formationSetHoldTarget(target);
        }
        soldier.stopAdaptiveNavigation();
    }

    void finishExplicitAssembly(MedievalSoldierEntity soldier, class_243 target) {
        if (soldier == null || target == null) {
            return;
        }
        if (soldier.getOrderType() == NpcOrderType.HOLD_POSITION) {
            soldier.formationSetHoldTarget(target);
        }
        soldier.formationStateForController().clearAssembly();
    }

    void onCombatFinished(MedievalSoldierEntity soldier) {
        class_243 hold;
        if (soldier == null) {
            return;
        }
        soldier.formationStateForController().clearTemporaryRelease();
        soldier.stopAdaptiveNavigation();
        if (soldier.getOrderType() == NpcOrderType.HOLD_POSITION && (hold = soldier.goalHoldPosition()) != null) {
            soldier.formationSetHoldTarget(hold);
        }
    }

    void tickPassageState(MedievalSoldierEntity soldier) {
        if (soldier.getFormation() == NpcFormation.FREE) {
            soldier.formationStateForController().clearTemporaryRelease();
            return;
        }
        if (this.isInFormationChokepoint(soldier)) {
            soldier.formationStateForController().releaseTemporarily(25);
            soldier.formationStateForController().clearAssembly();
            return;
        }
        if (soldier.formationStateForController().isTemporarilyReleased() && soldier.formationStateForController().getReassembleDelay() <= 0) {
            soldier.formationStateForController().clearTemporaryRelease();
            soldier.stopAdaptiveNavigation();
        }
    }

    boolean shouldUseLooseMovement(MedievalSoldierEntity soldier) {
        return soldier.getFormation() == NpcFormation.FREE || soldier.formationStateForController().isTemporarilyReleased();
    }

    class_243 formationPointBehind(MedievalSoldierEntity soldier, class_1657 owner) {
        double forwardZ;
        class_1937 class_19372;
        if (owner == null || soldier.getFormation() == NpcFormation.FREE || !((class_19372 = soldier.method_37908()) instanceof class_3218)) {
            return owner == null ? soldier.method_19538() : owner.method_19538();
        }
        class_3218 world = (class_3218)class_19372;
        UUID squadId = soldier.identityForController().getSquadId();
        CommandGroupData group = CommandGroupManager.get(world).get(squadId);
        if (group == null) {
            return owner.method_19538();
        }
        NpcFormationState state = soldier.formationStateForController();
        int index = state.getSlot() >= 0 ? state.getSlot() : group.memberIndex(soldier.method_5667());
        int count = Math.max(1, group.getSoldierIds().size());
        double spacing = 2.2 * soldier.getFormationSpacingMultiplier() * soldier.getFormationFootprintMultiplier();
        NpcFormationLayout.Offset offset = NpcFormationLayout.offset(soldier.getFormation(), index, count, spacing);
        double lateral = offset.lateral();
        double back = 3.0 + offset.back();
        if (this.isFormationCorridor(soldier, owner)) {
            lateral = 0.0;
            back = 2.25 + (double)Math.max(0, index) * 1.35;
        }
        state.updateHeading(owner);
        double forwardX = state.forwardX();
        double rightX = forwardZ = state.forwardZ();
        double rightZ = -forwardX;
        return new class_243(owner.method_23317() - forwardX * back + rightX * lateral, owner.method_23318(), owner.method_23321() - forwardZ * back + rightZ * lateral);
    }
}

