/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_124
 *  net.minecraft.class_1268
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1792
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_1836
 *  net.minecraft.class_1937
 *  net.minecraft.class_2561
 *  net.minecraft.class_3218
 *  net.minecraft.class_3417
 */
package com.medievalmachinery.mod.item;

import com.medievalmachinery.mod.entity.machine.AbstractMedievalMachineEntity;
import java.util.List;
import net.minecraft.class_124;
import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1836;
import net.minecraft.class_1937;
import net.minecraft.class_2561;
import net.minecraft.class_3218;
import net.minecraft.class_3417;

public final class MachineSpannerItem
extends class_1792 {
    public static final float DISMANTLE_HEALTH_FRACTION = 0.1f;

    public MachineSpannerItem(class_1792.class_1793 settings) {
        super(settings);
    }

    public void method_7851(class_1799 stack, class_1937 world, List<class_2561> tooltip, class_1836 context) {
        tooltip.add((class_2561)class_2561.method_43471((String)"tooltip.medieval-machinery.machine_spanner.line1").method_27692(class_124.field_1080));
        tooltip.add((class_2561)class_2561.method_43471((String)"tooltip.medieval-machinery.machine_spanner.line2").method_27692(class_124.field_1080));
    }

    public static boolean tryDismantle(AbstractMedievalMachineEntity machine, class_1657 player, class_1268 hand) {
        class_1799 held = player.method_5998(hand);
        if (!(held.method_7909() instanceof MachineSpannerItem)) {
            return false;
        }
        if (machine.method_37908().field_9236) {
            return true;
        }
        if (!(machine.method_37908() instanceof class_3218)) {
            return true;
        }
        class_1799 packed = machine.createDismantledStack(0.1f);
        machine.method_5775(packed);
        machine.method_5783(class_3417.field_14559, 0.65f, 1.25f);
        machine.method_31472();
        if (!player.method_31549().field_7477) {
            held.method_7956(1, (class_1309)player, p -> p.method_20236(hand));
        }
        return true;
    }
}

