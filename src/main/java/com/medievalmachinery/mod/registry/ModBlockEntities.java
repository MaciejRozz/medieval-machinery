/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder
 *  net.minecraft.class_2248
 *  net.minecraft.class_2378
 *  net.minecraft.class_2591
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.registry;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.block.WheelBlockEntity;
import com.medievalmachinery.mod.block.ammunition.PotionPayloadBlockEntity;
import com.medievalmachinery.mod.block.ammunition.SpecialPayloadBlockEntity;
import com.medievalmachinery.mod.block.garrison.WatchpostBlockEntity;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import com.medievalmachinery.mod.block.storage.WickerBasketBlockEntity;
import com.medievalmachinery.mod.block.workshop.MachineWorkshopBlockEntity;
import com.medievalmachinery.mod.blockentity.machine.cannon.DecorativeFieldCannonBlockEntity;
import com.medievalmachinery.mod.blockentity.machine.cannon.DecorativeFortressCannonBlockEntity;
import com.medievalmachinery.mod.blockentity.machine.cannon.StationaryCannonBlockEntity;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.registry.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.class_2248;
import net.minecraft.class_2378;
import net.minecraft.class_2591;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public final class ModBlockEntities {
    public static final class_2591<WatchpostBlockEntity> WATCHPOST = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("watchpost"), (Object)FabricBlockEntityTypeBuilder.create(WatchpostBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.WATCHPOST}).build());
    public static final class_2591<MachineWorkshopBlockEntity> MACHINE_WORKSHOP = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("machine_workshop"), (Object)FabricBlockEntityTypeBuilder.create(MachineWorkshopBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.MACHINE_WORKSHOP}).build());
    public static final class_2591<SpecialPayloadBlockEntity> SPECIAL_PAYLOAD = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("special_payload"), (Object)FabricBlockEntityTypeBuilder.create(SpecialPayloadBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.EXPLOSIVE_PAYLOAD, ModBlocks.FIRE_PAYLOAD}).build());
    public static final class_2591<PotionPayloadBlockEntity> POTION_PAYLOAD = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("potion_payload"), (Object)FabricBlockEntityTypeBuilder.create(PotionPayloadBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.POTION_PAYLOAD}).build());
    public static final class_2591<GuillotineBlockEntity> GUILLOTINE = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("guillotine"), (Object)FabricBlockEntityTypeBuilder.create(GuillotineBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.GUILLOTINE, ModBlocks.ROYAL_GUILLOTINE}).build());
    public static final class_2591<StationaryCannonBlockEntity> STATIONARY_CANNON = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("stationary_cannon"), (Object)FabricBlockEntityTypeBuilder.create(StationaryCannonBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.STATIONARY_CANNON}).build());
    public static final class_2591<DecorativeFortressCannonBlockEntity> DECORATIVE_FORTRESS_CANNON = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("decorative_fortress_cannon"), (Object)FabricBlockEntityTypeBuilder.create(DecorativeFortressCannonBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.DECORATIVE_FORTRESS_CANNON}).build());
    public static final class_2591<DecorativeFieldCannonBlockEntity> DECORATIVE_FIELD_CANNON = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("decorative_field_cannon"), (Object)FabricBlockEntityTypeBuilder.create(DecorativeFieldCannonBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.DECORATIVE_FIELD_CANNON}).build());
    public static final class_2591<WickerBasketBlockEntity> WICKER_BASKET = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("wicker_basket"), (Object)FabricBlockEntityTypeBuilder.create(WickerBasketBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.WICKER_BASKET}).build());
    public static final class_2591<WheelBlockEntity> WHEEL = (class_2591)class_2378.method_10230((class_2378)class_7923.field_41181, (class_2960)MedievalMachinery.id("wheel"), (Object)FabricBlockEntityTypeBuilder.create(WheelBlockEntity::new, (class_2248[])new class_2248[]{ModBlocks.WHEEL}).build());

    private ModBlockEntities() {
    }

    public static void register() {
        RegisteredContent.bindCommonBlockEntities(WHEEL, SPECIAL_PAYLOAD, POTION_PAYLOAD, DECORATIVE_FORTRESS_CANNON, DECORATIVE_FIELD_CANNON, GUILLOTINE, STATIONARY_CANNON, WICKER_BASKET, WickerBasketBlockEntity::new, StationaryCannonBlockEntity::new);
        MedievalMachinery.LOGGER.info("Registering block entities for Medieval Machinery");
    }
}

