/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1282
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1324
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 *  net.minecraft.class_3222
 *  net.minecraft.class_5134
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcOrderType;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.core.NpcSupplyRequest;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierCommandState;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.machine.NpcMachineAssignmentService;
import com.medievalmachinery.mod.npc.organization.GarrisonRallyPolicy;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1324;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_5134;

final class MedievalSoldierOrderController {
    MedievalSoldierOrderController() {
    }

    boolean canCommandShieldBlock(MedievalSoldierEntity soldier, class_1282 source) {
        return soldier.orderShieldControl().canCommandShieldBlock(soldier, source);
    }

    void clearSuspendedOrderSnapshot(MedievalSoldierEntity soldier) {
        soldier.orderCommandState().clearSuspension();
    }

    void updateRoleFollowRange(MedievalSoldierEntity soldier) {
        class_1324 followRange = soldier.method_5996(class_5134.field_23717);
        if (followRange == null) {
            return;
        }
        followRange.method_6192(soldier.getNpcRole() == NpcRole.ARCHER || soldier.getNpcRole() == NpcRole.CROSSBOWMAN || soldier.getNpcRole() == NpcRole.MOUNTED_ARCHER ? 64.0 : 32.0);
    }

    NpcOrderType normalizeRequestedOrder(MedievalSoldierEntity soldier, NpcOrderType requested) {
        NpcOrderType resolved;
        NpcOrderType npcOrderType = resolved = requested == null ? NpcOrderType.IDLE : requested;
        if (soldier.getNpcRole() != NpcRole.CANNONEER) {
            return resolved;
        }
        if (resolved == NpcOrderType.PATROL) {
            return NpcOrderType.HOLD_POSITION;
        }
        if (resolved == NpcOrderType.IDLE && soldier.medievalMachinery$hasMachineAssignment()) {
            return NpcOrderType.HOLD_POSITION;
        }
        if ((resolved == NpcOrderType.FOLLOW || resolved == NpcOrderType.MOVE_TO_POSITION) && NpcMachineAssignmentService.isAssignedToLoadedStationaryMachine(soldier)) {
            return NpcOrderType.HOLD_POSITION;
        }
        return resolved;
    }

    void cycleFieldOrder(MedievalSoldierEntity soldier, class_3222 player) {
        NpcOrderType next = switch (soldier.orderOrderType()) {
            default -> throw new IncompatibleClassChangeError();
            case NpcOrderType.IDLE -> NpcOrderType.FOLLOW;
            case NpcOrderType.FOLLOW -> NpcOrderType.HOLD_POSITION;
            case NpcOrderType.HOLD_POSITION -> {
                if (soldier.getNpcRole() == NpcRole.CANNONEER && soldier.medievalMachinery$hasMachineAssignment()) {
                    if (NpcMachineAssignmentService.isAssignedToLoadedStationaryMachine(soldier)) {
                        yield NpcOrderType.HOLD_POSITION;
                    }
                    yield NpcOrderType.FOLLOW;
                }
                if (soldier.getNpcRole() == NpcRole.CANNONEER) {
                    yield NpcOrderType.IDLE;
                }
                if (soldier.getNpcRole() == NpcRole.QUARTERMASTER) {
                    yield NpcOrderType.WORK;
                }
                if (soldier.getHomePosition() != null) {
                    yield NpcOrderType.PATROL;
                }
                yield NpcOrderType.IDLE;
            }
            case NpcOrderType.WORK -> {
                if (soldier.getHomePosition() != null) {
                    yield NpcOrderType.PATROL;
                }
                yield NpcOrderType.IDLE;
            }
            case NpcOrderType.PATROL, NpcOrderType.RETURN_TO_GARRISON, NpcOrderType.MOVE_TO_POSITION, NpcOrderType.OBSERVATION_POINT -> NpcOrderType.IDLE;
        };
        soldier.setOrderType(next);
        player.method_7353((class_2561)class_2561.method_43469((String)"message.medieval-machinery.npc.order_changed", (Object[])new Object[]{class_2561.method_43471((String)("npc.order.medieval-machinery." + next.serializedName()))}), false);
    }

    void suspendCurrentOrderIfNeeded(MedievalSoldierEntity soldier, boolean combat) {
        if (soldier.orderCommandState().suspendedOrder != null) {
            if (combat) {
                soldier.orderCommandState().markCombatSuspension();
            }
            return;
        }
        soldier.orderCommandState().suspend(soldier.getOrderType(), soldier.orderCommandDestination(), soldier.orderHoldPosition(), combat);
    }

    void prepareForExplicitMovementCommand(MedievalSoldierEntity soldier, NpcOrderType nextOrder) {
        this.clearSuspendedOrderSnapshot(soldier);
        soldier.stopAdaptiveNavigation();
        soldier.orderFormationState().clearAssembly();
        soldier.cancelTemporarySupplyForNewCommand();
        soldier.orderCombatMovementState().clearRepositioning();
        class_1309 current = soldier.method_5968();
        if (current != null && !soldier.isCommandedCombatTarget(current) && soldier.method_5858((class_1297)current) > 36.0) {
            soldier.method_5980(null);
            soldier.orderCombatTargetState().resetSight();
            soldier.orderCombatTargetState().suppressTargetAcquisition(60);
        }
        soldier.orderPatrolState().resetRepathCooldown();
        soldier.orderCommandState().beginGrace(12);
    }

    void resumeSuspendedOrderIfPossible(MedievalSoldierEntity soldier) {
        MedievalSoldierCommandState state = soldier.orderCommandState();
        if (state.suspendedOrder == null || soldier.method_5968() != null || soldier.hasSupplyMovementOverride()) {
            return;
        }
        NpcOrderType restore = state.suspendedOrder;
        class_2338 destination = state.suspendedDestination;
        class_243 hold = state.suspendedHold;
        state.clearSuspension();
        this.restoreTemporaryOrderState(soldier, restore, destination, hold);
    }

    void restoreTemporaryOrderState(MedievalSoldierEntity soldier, NpcOrderType restore, class_2338 destination, class_243 hold) {
        NpcOrderType resolved = restore == null ? NpcOrderType.IDLE : restore;
        soldier.orderSetTypeRaw(resolved);
        soldier.orderSetCommandDestination(resolved == NpcOrderType.MOVE_TO_POSITION && destination != null ? destination.method_10062() : null);
        if ((resolved == NpcOrderType.HOLD_POSITION || resolved == NpcOrderType.OBSERVATION_POINT) && hold != null) {
            soldier.orderSetHoldPosition(hold);
            soldier.orderSetHoldDisplacedTicks(0);
        }
        soldier.orderPatrolState().setPatrolOrderActive(resolved == NpcOrderType.PATROL);
        if (resolved == NpcOrderType.PATROL) {
            soldier.orderPatrolState().resetRepathCooldown();
        }
        if (resolved != NpcOrderType.FOLLOW) {
            soldier.orderFormationState().resetHeading();
        }
        if (resolved == NpcOrderType.IDLE) {
            this.refreshIdleAnchorFromCastleRally(soldier);
        }
        soldier.orderTrackType(resolved);
        soldier.stopAdaptiveNavigation();
    }

    void refreshIdleAnchorFromCastleRally(MedievalSoldierEntity soldier) {
        GarrisonRallyPolicy.IdleAnchor idleAnchor;
        class_1937 class_19372 = soldier.method_37908();
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            idleAnchor = GarrisonRallyPolicy.resolveIdleAnchor(serverWorld, soldier.getNpcIdentity().getCastleId(), soldier.method_5667(), soldier.method_19538());
        } else {
            idleAnchor = new GarrisonRallyPolicy.IdleAnchor(soldier.method_19538(), false);
        }
        GarrisonRallyPolicy.IdleAnchor resolved = idleAnchor;
        soldier.orderSetIdleAnchor(resolved.position(), resolved.rallyBound());
    }

    void setOrderType(MedievalSoldierEntity soldier, NpcOrderType requested) {
        NpcOrderType resolved = this.normalizeRequestedOrder(soldier, requested);
        if (soldier.isGeneralBodyguard() && resolved != NpcOrderType.IDLE) {
            return;
        }
        if (soldier.orderOrderType() != resolved) {
            this.prepareForExplicitMovementCommand(soldier, resolved);
        }
        soldier.orderSetTypeRaw(resolved);
        if (resolved != NpcOrderType.MOVE_TO_POSITION) {
            soldier.orderSetCommandDestination(null);
        }
        soldier.orderPatrolState().setPatrolOrderActive(resolved == NpcOrderType.PATROL);
        if (resolved == NpcOrderType.IDLE) {
            this.refreshIdleAnchorFromCastleRally(soldier);
        } else {
            soldier.orderSetIdleAnchor(null, false);
        }
        if (resolved == NpcOrderType.PATROL) {
            soldier.orderPatrolState().resetRepathCooldown();
            soldier.stopAdaptiveNavigation();
        }
        soldier.orderTrackType(resolved);
        if (resolved != NpcOrderType.FOLLOW) {
            soldier.orderFormationState().resetHeading();
        }
        if (resolved == NpcOrderType.HOLD_POSITION || resolved == NpcOrderType.OBSERVATION_POINT) {
            soldier.stopAdaptiveNavigation();
            if (resolved == NpcOrderType.HOLD_POSITION && soldier.orderSupplyState().request == NpcSupplyRequest.NONE && !soldier.orderSupplyState().isReturningToOrigin()) {
                soldier.orderSetHoldPosition(soldier.method_19538());
                soldier.orderSetHoldDisplacedTicks(0);
            }
        }
    }

    boolean setObservationPoint(MedievalSoldierEntity soldier, class_2338 groundBlock) {
        if (soldier.getNpcRole() != NpcRole.SCOUT || groundBlock == null) {
            return false;
        }
        class_2338 feet = groundBlock.method_10084();
        if (!soldier.method_37908().method_8320(feet).method_26220((class_1922)soldier.method_37908(), feet).method_1110() || !soldier.method_37908().method_8320(feet.method_10084()).method_26220((class_1922)soldier.method_37908(), feet.method_10084()).method_1110() || soldier.method_37908().method_8320(groundBlock).method_26220((class_1922)soldier.method_37908(), groundBlock).method_1110()) {
            return false;
        }
        soldier.orderSetHoldPosition(new class_243((double)feet.method_10263() + 0.5, (double)feet.method_10264(), (double)feet.method_10260() + 0.5));
        soldier.orderSetHoldDisplacedTicks(0);
        soldier.setOrderType(NpcOrderType.OBSERVATION_POINT);
        return true;
    }
}

