/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_3222
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 */
package com.medievalmachinery.mod.network;

import com.medievalmachinery.mod.network.SoldierCommandScopeResolver;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.SoldierControlPolicy;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_3222;
import net.minecraft.class_3959;
import net.minecraft.class_3965;

final class SoldierAttackCommandHandler {
    private SoldierAttackCommandHandler() {
    }

    static void apply(class_3222 player, int scope, int entityId, List<MedievalSoldierEntity> targets) {
        class_1309 attackTarget;
        class_1297 rawTarget = player.method_37908().method_8469(entityId);
        if (!(rawTarget instanceof class_1309) || !(attackTarget = (class_1309)rawTarget).method_5805()) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.attack_target_invalid"), false);
            return;
        }
        if (player.method_5858((class_1297)attackTarget) > 10000.0) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.attack_target_too_far"), false);
            return;
        }
        class_243 eye = player.method_33571();
        class_243 targetPoint = new class_243(attackTarget.method_23317(), attackTarget.method_23323(0.55), attackTarget.method_23321());
        class_3965 obstruction = player.method_37908().method_17742(new class_3959(eye, targetPoint, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)player));
        if (obstruction.method_17783() == class_239.class_240.field_1332 && eye.method_1025(obstruction.method_17784()) + 0.25 < eye.method_1025(targetPoint)) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.attack_target_not_visible"), false);
            return;
        }
        int assigned = 0;
        for (MedievalSoldierEntity soldier : targets) {
            MedievalSoldierEntity other;
            if (attackTarget == soldier || attackTarget instanceof MedievalSoldierEntity && SoldierControlPolicy.canPersonallyControl((class_1657)player, other = (MedievalSoldierEntity)attackTarget)) continue;
            soldier.commandAttackTarget(attackTarget);
            ++assigned;
        }
        if (assigned <= 0) {
            player.method_7353((class_2561)class_2561.method_43471((String)"message.medieval-machinery.command.attack_target_invalid"), false);
        } else {
            SoldierCommandScopeResolver.sendCommandFeedback(player, scope, "message.medieval-machinery.command.chat.attack", attackTarget.method_5476());
        }
    }
}

