/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_3917
 *  net.minecraft.class_7701
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.registry;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.screen.garrison.WatchpostScreenHandler;
import com.medievalmachinery.mod.screen.machine.cannon.FieldCannonScreenHandler;
import com.medievalmachinery.mod.screen.machine.cannon.StationaryCannonScreenHandler;
import com.medievalmachinery.mod.screen.npc.SoldierInventoryScreenHandler;
import com.medievalmachinery.mod.screen.storage.WickerBasketScreenHandler;
import com.medievalmachinery.mod.screen.workshop.MachineWorkshopScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3917;
import net.minecraft.class_7701;
import net.minecraft.class_7923;

public final class ModScreenHandlers {
    public static final class_3917<SoldierInventoryScreenHandler> SOLDIER_INVENTORY = (class_3917)class_2378.method_10230((class_2378)class_7923.field_41187, (class_2960)MedievalMachinery.id("soldier_inventory"), (Object)new class_3917(SoldierInventoryScreenHandler::new, class_7701.field_40182));
    public static final class_3917<WatchpostScreenHandler> WATCHPOST = (class_3917)class_2378.method_10230((class_2378)class_7923.field_41187, (class_2960)MedievalMachinery.id("watchpost"), (Object)new ExtendedScreenHandlerType(WatchpostScreenHandler::new));
    public static final class_3917<MachineWorkshopScreenHandler> MACHINE_WORKSHOP = (class_3917)class_2378.method_10230((class_2378)class_7923.field_41187, (class_2960)MedievalMachinery.id("machine_workshop"), (Object)new ExtendedScreenHandlerType(MachineWorkshopScreenHandler::new));
    public static final class_3917<StationaryCannonScreenHandler> STATIONARY_CANNON = (class_3917)class_2378.method_10230((class_2378)class_7923.field_41187, (class_2960)MedievalMachinery.id("stationary_cannon"), (Object)new ExtendedScreenHandlerType(StationaryCannonScreenHandler::new));
    public static final class_3917<FieldCannonScreenHandler> FIELD_CANNON = (class_3917)class_2378.method_10230((class_2378)class_7923.field_41187, (class_2960)MedievalMachinery.id("field_cannon"), (Object)new ExtendedScreenHandlerType(FieldCannonScreenHandler::new));
    public static final class_3917<WickerBasketScreenHandler> WICKER_BASKET = (class_3917)class_2378.method_10230((class_2378)class_7923.field_41187, (class_2960)MedievalMachinery.id("wicker_basket"), (Object)new ExtendedScreenHandlerType(WickerBasketScreenHandler::new));

    private ModScreenHandlers() {
    }

    public static void register() {
        RegisteredContent.bindScreens(SOLDIER_INVENTORY, WATCHPOST, MACHINE_WORKSHOP, STATIONARY_CANNON, FIELD_CANNON, WICKER_BASKET);
        MedievalMachinery.LOGGER.info("Registering screen handlers for Medieval Machinery");
    }
}

