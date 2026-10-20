/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_243
 *  net.minecraft.class_3532
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_3532;

final class MedievalSoldierFollowPolicy {
    private MedievalSoldierFollowPolicy() {
    }

    static class_243 desiredPoint(MedievalSoldierEntity soldier, class_1657 owner) {
        double fz;
        double fx;
        if (owner == null) {
            return soldier == null ? class_243.field_1353 : soldier.method_19538();
        }
        if (soldier != null && soldier.getFormation() != NpcFormation.FREE) {
            return soldier.formationPointBehind(owner);
        }
        class_243 velocity = owner.method_18798();
        double horizontalSq = velocity.field_1352 * velocity.field_1352 + velocity.field_1350 * velocity.field_1350;
        if (horizontalSq > 0.0025) {
            double len = Math.sqrt(horizontalSq);
            fx = velocity.field_1352 / len;
            fz = velocity.field_1350 / len;
        } else {
            float yaw = owner.method_36454() * ((float)Math.PI / 180);
            fx = -class_3532.method_15374((float)yaw);
            fz = class_3532.method_15362((float)yaw);
        }
        return new class_243(owner.method_23317() - fx * 5.5, owner.method_23318(), owner.method_23321() - fz * 5.5);
    }

    static double hysteresisDistance(MedievalSoldierEntity soldier, class_1657 owner, class_243 desiredPoint) {
        if (soldier == null || owner == null) {
            return 0.0;
        }
        if (soldier.getFormation() == NpcFormation.FREE) {
            return soldier.method_19538().method_1022(owner.method_19538());
        }
        class_243 point = desiredPoint == null ? owner.method_19538() : desiredPoint;
        return soldier.method_19538().method_1022(point);
    }

    static double startDistance(MedievalSoldierEntity soldier) {
        return soldier != null && soldier.getFormation() == NpcFormation.FREE ? 9.0 : 5.0;
    }

    static double stopDistance(MedievalSoldierEntity soldier) {
        return soldier != null && soldier.getFormation() == NpcFormation.FREE ? 7.0 : 2.75;
    }

    static double speed(MedievalSoldierEntity soldier, double distanceToDesiredPoint) {
        if (soldier != null && soldier.method_5765()) {
            if (distanceToDesiredPoint > 18.0) {
                return 1.28;
            }
            if (distanceToDesiredPoint > 10.0) {
                return 1.18;
            }
            return 1.08;
        }
        if (distanceToDesiredPoint > 16.0) {
            return 1.18;
        }
        if (distanceToDesiredPoint > 9.0) {
            return 1.08;
        }
        return 1.0;
    }

    static int repathTicks(MedievalSoldierEntity soldier) {
        return soldier != null && soldier.method_5765() ? 5 : 10;
    }

    static double catchUpSpeed(MedievalSoldierEntity soldier, double distance) {
        if (distance >= 18.0) {
            return 1.28;
        }
        if (distance >= 12.0) {
            return 1.18;
        }
        if (distance >= 8.0) {
            return 1.08;
        }
        return 0.98;
    }

    static boolean shouldHardStop(MedievalSoldierEntity soldier, double distance) {
        return distance <= MedievalSoldierFollowPolicy.stopDistance(soldier);
    }

    static void updateLook(MedievalSoldierEntity soldier, class_1657 owner) {
        if (soldier == null || owner == null) {
            return;
        }
        if (soldier.method_5968() == null || !soldier.method_5968().method_5805()) {
            soldier.method_5988().method_6226((class_1297)owner, 10.0f, 40.0f);
        }
    }
}

