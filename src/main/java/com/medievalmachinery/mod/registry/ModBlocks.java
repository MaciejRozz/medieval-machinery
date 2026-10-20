/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1747
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_4970
 *  net.minecraft.class_4970$class_2251
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.registry;

import com.medievalmachinery.mod.MedievalMachineryCore;
import com.medievalmachinery.mod.block.TemporaryRescueBlock;
import com.medievalmachinery.mod.block.WheelBlock;
import com.medievalmachinery.mod.block.ammunition.PotionPayloadBlock;
import com.medievalmachinery.mod.block.ammunition.SpecialPayloadBlock;
import com.medievalmachinery.mod.block.architecture.ConnectedQuaderBlock;
import com.medievalmachinery.mod.block.garrison.WatchpostBlock;
import com.medievalmachinery.mod.block.machine.cannon.CannonballPileBlock;
import com.medievalmachinery.mod.block.machine.cannon.DecorativeFieldCannonBlock;
import com.medievalmachinery.mod.block.machine.cannon.DecorativeFortressCannonBlock;
import com.medievalmachinery.mod.block.machine.cannon.StationaryCannonBlock;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlock;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotinePartBlock;
import com.medievalmachinery.mod.block.storage.WickerBasketBlock;
import com.medievalmachinery.mod.block.workshop.MachineWorkshopBlock;
import com.medievalmachinery.mod.block.workshop.RoyalSmelterBlock;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_4970;
import net.minecraft.class_7923;

public final class ModBlocks {
    public static final class_2248 NPC_RESCUE_BLOCK = ModBlocks.registerBlockWithoutItem("npc_rescue_block", new TemporaryRescueBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10566).method_9632(0.35f).method_42327()));
    public static final class_2248 STONE_BRICK_QUADER = ModBlocks.registerBlock("stone_brick_quader", new ConnectedQuaderBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10056).method_9629(1.5f, 6.0f).method_29292()));
    public static final class_2248 WATCHPOST = ModBlocks.registerBlock("watchpost", (class_2248)new WatchpostBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_9980).method_9629(2.5f, 3.0f).method_22488()));
    public static final class_2248 MACHINE_WORKSHOP = ModBlocks.registerBlock("machine_workshop", (class_2248)new MachineWorkshopBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_9980).method_9629(2.5f, 3.0f)));
    public static final class_2248 ROYAL_SMELTER = ModBlocks.registerBlockWithoutItem("royal_smelter", (class_2248)new RoyalSmelterBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_16333).method_9629(3.5f, 6.0f).method_29292()));
    public static final class_2248 COBBLESTONE_BUNDLE = ModBlocks.registerBlockWithoutItem("cobblestone_bundle", new class_2248(class_4970.class_2251.method_9630((class_4970)class_2246.field_10445).method_9629(2.0f, 6.0f).method_22488()));
    public static final class_2248 EXPLOSIVE_PAYLOAD = ModBlocks.registerBlockWithoutItem("explosive_payload", (class_2248)new SpecialPayloadBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_42752).method_9632(0.6f).method_22488()));
    public static final class_2248 FIRE_PAYLOAD = ModBlocks.registerBlockWithoutItem("fire_payload", (class_2248)new SpecialPayloadBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_42752).method_9632(0.6f).method_22488()));
    public static final class_2248 POTION_PAYLOAD = ModBlocks.registerBlockWithoutItem("potion_payload_placed", (class_2248)new PotionPayloadBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10033).method_9632(0.35f).method_22488()));
    public static final class_2248 GUILLOTINE = ModBlocks.registerBlock("guillotine", (class_2248)new GuillotineBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10161).method_9632(2.5f)));
    public static final class_2248 ROYAL_GUILLOTINE = ModBlocks.registerBlock("royal_guillotine", (class_2248)new GuillotineBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10161).method_9632(3.5f)));
    public static final class_2248 STATIONARY_CANNON = ModBlocks.registerBlockWithoutItem("stationary_cannon", (class_2248)new StationaryCannonBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10161).method_9629(3.2f, 6.0f).method_22488()));
    public static final class_2248 DECORATIVE_FORTRESS_CANNON = ModBlocks.registerBlock("decorative_fortress_cannon", (class_2248)new DecorativeFortressCannonBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10161).method_9629(3.2f, 6.0f).method_22488()));
    public static final class_2248 DECORATIVE_FIELD_CANNON = ModBlocks.registerBlock("decorative_field_cannon", (class_2248)new DecorativeFieldCannonBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10161).method_9629(3.0f, 5.0f).method_22488()));
    public static final class_2248 STONE_CANNONBALL_PILE = ModBlocks.registerBlockWithoutItem("stone_cannonball_pile", new CannonballPileBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10161).method_9629(2.0f, 4.0f).method_22488()));
    public static final class_2248 IRON_CANNONBALL_PILE = ModBlocks.registerBlockWithoutItem("iron_cannonball_pile", new CannonballPileBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10161).method_9629(2.4f, 5.0f).method_22488()));
    public static final class_2248 TNT_CANNONBALL_PILE = ModBlocks.registerBlockWithoutItem("tnt_cannonball_pile", new CannonballPileBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10161).method_9629(2.0f, 4.0f).method_22488()));
    public static final class_2248 HEAVY_IRON_CANNONBALL_PILE = ModBlocks.registerBlockWithoutItem("heavy_iron_cannonball_pile", new CannonballPileBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10085).method_9629(3.0f, 6.0f).method_22488()));
    public static final class_2248 WHEEL = ModBlocks.registerBlock("wheel", (class_2248)new WheelBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10161).method_9629(2.0f, 3.0f).method_22488()));
    public static final class_2248 WICKER_BASKET = ModBlocks.registerBlock("wicker_basket", (class_2248)new WickerBasketBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_40294).method_9629(2.2f, 3.0f).method_22488()));
    public static final class_2248 GUILLOTINE_PART = (class_2248)class_2378.method_10230((class_2378)class_7923.field_41175, (class_2960)MedievalMachineryCore.id("guillotine_part"), (Object)((Object)new GuillotinePartBlock(class_4970.class_2251.method_9630((class_4970)class_2246.field_10499).method_9632(3.5f).method_42327())));

    private ModBlocks() {
    }

    private static class_2248 registerBlock(String name, class_2248 block) {
        ModBlocks.registerBlockItem(name, block);
        return (class_2248)class_2378.method_10230((class_2378)class_7923.field_41175, (class_2960)MedievalMachineryCore.id(name), (Object)block);
    }

    private static class_2248 registerBlockWithoutItem(String name, class_2248 block) {
        return (class_2248)class_2378.method_10230((class_2378)class_7923.field_41175, (class_2960)MedievalMachineryCore.id(name), (Object)block);
    }

    private static void registerBlockItem(String name, class_2248 block) {
        class_2378.method_10230((class_2378)class_7923.field_41178, (class_2960)MedievalMachineryCore.id(name), (Object)new class_1747(block, new class_1792.class_1793()));
    }

    public static void register() {
        MedievalMachineryCore.LOGGER.info("Registering blocks for Medieval Machinery");
    }
}

