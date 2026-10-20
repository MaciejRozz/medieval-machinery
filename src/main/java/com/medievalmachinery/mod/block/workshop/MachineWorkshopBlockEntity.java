/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_3222
 *  net.minecraft.class_3914
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.workshop;

import com.medievalmachinery.mod.registry.ModBlockEntities;
import com.medievalmachinery.mod.screen.workshop.MachineWorkshopScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_3222;
import net.minecraft.class_3914;
import org.jetbrains.annotations.Nullable;

public final class MachineWorkshopBlockEntity
extends class_2586
implements ExtendedScreenHandlerFactory {
    public MachineWorkshopBlockEntity(class_2338 pos, class_2680 state) {
        super(ModBlockEntities.MACHINE_WORKSHOP, pos, state);
    }

    public class_2561 method_5476() {
        return class_2561.method_43471((String)"container.medieval-machinery.machine_workshop");
    }

    @Nullable
    public class_1703 createMenu(int syncId, class_1661 playerInventory, class_1657 player) {
        if (this.field_11863 == null) {
            return null;
        }
        return new MachineWorkshopScreenHandler(syncId, playerInventory, class_3914.method_17392((class_1937)this.field_11863, (class_2338)this.field_11867));
    }

    public void writeScreenOpeningData(class_3222 player, class_2540 buf) {
        buf.method_10807(this.field_11867);
    }
}

