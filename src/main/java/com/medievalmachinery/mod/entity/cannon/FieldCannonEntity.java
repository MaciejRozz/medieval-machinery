/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1299
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1792
 *  net.minecraft.class_1937
 *  net.minecraft.class_2561
 */
package com.medievalmachinery.mod.entity.cannon;

import com.medievalmachinery.mod.entity.cannon.AbstractCannonEntity;
import com.medievalmachinery.mod.registry.ModItems;
import com.medievalmachinery.mod.screen.machine.cannon.FieldCannonScreenHandler;
import net.minecraft.class_1299;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1792;
import net.minecraft.class_1937;
import net.minecraft.class_2561;

public final class FieldCannonEntity
extends AbstractCannonEntity {
    public FieldCannonEntity(class_1299<? extends FieldCannonEntity> type, class_1937 world) {
        super((class_1299<? extends AbstractCannonEntity>)type, world);
    }

    @Override
    protected class_1792 getCannonItem() {
        return ModItems.FIELD_CANNON;
    }

    @Override
    public class_2561 method_5476() {
        return class_2561.method_43471((String)"container.medieval-machinery.field_cannon");
    }

    @Override
    public class_1703 createMenu(int syncId, class_1661 playerInventory, class_1657 player) {
        return new FieldCannonScreenHandler(syncId, playerInventory, this, this.method_5628());
    }
}

