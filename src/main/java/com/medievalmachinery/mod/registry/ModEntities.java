/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry
 *  net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder
 *  net.minecraft.class_1299
 *  net.minecraft.class_1311
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_4048
 *  net.minecraft.class_5132$class_5133
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.registry;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.entity.cannon.CannonballEntity;
import com.medievalmachinery.mod.entity.cannon.FieldCannonEntity;
import com.medievalmachinery.mod.entity.cannon.StationaryCannonEntity;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import com.medievalmachinery.mod.entity.catapult.CatapultStoneEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.platform.RegisteredContent;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.class_1299;
import net.minecraft.class_1311;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_4048;
import net.minecraft.class_5132;
import net.minecraft.class_7923;

public final class ModEntities {
    public static final class_1299<CannonballEntity> CANNONBALL = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)MedievalMachinery.id("cannonball"), (Object)FabricEntityTypeBuilder.create((class_1311)class_1311.field_17715, CannonballEntity::new).dimensions(class_4048.method_18385((float)0.38f, (float)0.38f)).trackRangeBlocks(96).trackedUpdateRate(1).build());
    public static final class_1299<FieldCannonEntity> FIELD_CANNON = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)MedievalMachinery.id("field_cannon"), (Object)FabricEntityTypeBuilder.create((class_1311)class_1311.field_17715, FieldCannonEntity::new).dimensions(class_4048.method_18385((float)2.1f, (float)1.85f)).trackRangeBlocks(96).trackedUpdateRate(1).build());
    public static final class_1299<StationaryCannonEntity> STATIONARY_CANNON = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)MedievalMachinery.id("stationary_cannon"), (Object)FabricEntityTypeBuilder.create((class_1311)class_1311.field_17715, StationaryCannonEntity::new).dimensions(class_4048.method_18385((float)2.05f, (float)1.65f)).trackRangeBlocks(96).trackedUpdateRate(1).build());
    public static final class_1299<CatapultStoneEntity> CATAPULT_STONE = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)MedievalMachinery.id("catapult_stone"), (Object)FabricEntityTypeBuilder.create((class_1311)class_1311.field_17715, CatapultStoneEntity::new).dimensions(class_4048.method_18385((float)0.55f, (float)0.55f)).trackRangeBlocks(128).trackedUpdateRate(1).build());
    public static final class_1299<CatapultEntity> CATAPULT = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)MedievalMachinery.id("catapult"), (Object)FabricEntityTypeBuilder.create((class_1311)class_1311.field_17715, CatapultEntity::new).dimensions(class_4048.method_18385((float)3.75f, (float)2.95f)).trackRangeBlocks(96).trackedUpdateRate(1).build());
    public static final class_1299<MedievalSoldierEntity> SOLDIER = (class_1299)class_2378.method_10230((class_2378)class_7923.field_41177, (class_2960)MedievalMachinery.id("soldier"), (Object)FabricEntityTypeBuilder.create((class_1311)class_1311.field_6294, MedievalSoldierEntity::new).dimensions(class_4048.method_18385((float)0.6f, (float)1.95f)).trackRangeBlocks(96).trackedUpdateRate(2).build());

    private ModEntities() {
    }

    public static void register() {
        RegisteredContent.bindEntities(CANNONBALL, FIELD_CANNON, STATIONARY_CANNON, CATAPULT_STONE, CATAPULT, SOLDIER);
        FabricDefaultAttributeRegistry.register(SOLDIER, (class_5132.class_5133)MedievalSoldierEntity.createAttributes());
        MedievalMachinery.LOGGER.info("Registering entities for Medieval Machinery");
    }
}

