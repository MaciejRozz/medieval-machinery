/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1269
 *  net.minecraft.class_1297
 *  net.minecraft.class_1792
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1838
 *  net.minecraft.class_1937
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_3218
 *  net.minecraft.class_3417
 *  net.minecraft.class_3419
 *  net.minecraft.class_3532
 */
package com.medievalmachinery.mod.item;

import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import com.medievalmachinery.mod.platform.RegisteredContent;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1792;
import net.minecraft.class_1838;
import net.minecraft.class_1937;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_3218;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3532;

public final class CatapultItem
extends class_1792 {
    public CatapultItem(class_1792.class_1793 settings) {
        super(settings);
    }

    public class_1269 method_7884(class_1838 context) {
        class_1937 class_19372 = context.method_8045();
        if (!(class_19372 instanceof class_3218)) {
            return class_1269.field_5812;
        }
        class_3218 world = (class_3218)class_19372;
        CatapultEntity entity = (CatapultEntity)RegisteredContent.catapult().method_5883((class_1937)world);
        if (entity == null) {
            return class_1269.field_5814;
        }
        class_243 pos = class_243.method_24955((class_2382)context.method_8037().method_10093(context.method_8038())).method_1031(0.0, 0.12, 0.0);
        float yaw = class_3532.method_15393((float)(context.method_8042().method_10144() + 180.0f));
        entity.method_5808(pos.field_1352, pos.field_1351, pos.field_1350, yaw, 0.0f);
        class_2487 nbt = context.method_8041().method_7969();
        if (nbt != null) {
            entity.loadFromItem(nbt);
        }
        entity.method_5808(pos.field_1352, pos.field_1351, pos.field_1350, yaw, 0.0f);
        entity.setBaseYaw(yaw);
        if (!world.method_17892((class_1297)entity) || !world.method_8649((class_1297)entity)) {
            return class_1269.field_5814;
        }
        world.method_8396(null, entity.method_24515(), class_3417.field_14718, class_3419.field_15245, 1.0f, 0.75f);
        if (context.method_8036() == null || !context.method_8036().method_31549().field_7477) {
            context.method_8041().method_7934(1);
        }
        return class_1269.field_21466;
    }
}

