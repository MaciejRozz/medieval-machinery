/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_124
 *  net.minecraft.class_1269
 *  net.minecraft.class_1747
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1836
 *  net.minecraft.class_1838
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.item;

import com.medievalmachinery.mod.block.ammunition.PotionPayloadBlock;
import com.medievalmachinery.mod.block.ammunition.PotionPayloadBlockEntity;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.registry.ModItems;
import java.util.List;
import net.minecraft.class_124;
import net.minecraft.class_1269;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1836;
import net.minecraft.class_1838;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import org.jetbrains.annotations.Nullable;

public final class PotionPayloadItem
extends class_1747 {
    private static final String POTION_STACK_KEY = "StoredPotion";

    public PotionPayloadItem(class_1792.class_1793 settings) {
        super(ModBlocks.POTION_PAYLOAD, settings);
    }

    public class_1269 method_7884(class_1838 context) {
        PotionPayloadBlockEntity blockEntity;
        class_2338 placed;
        class_2586 class_25862;
        class_2586 class_25863;
        class_2338 target;
        class_1937 world = context.method_8045();
        if (world.method_8320(target = context.method_8037()).method_27852(ModBlocks.POTION_PAYLOAD) && (Integer)world.method_8320(target).method_11654((class_2769)PotionPayloadBlock.COUNT) < 3 && (class_25863 = world.method_8321(target)) instanceof PotionPayloadBlockEntity) {
            PotionPayloadBlockEntity blockEntity2 = (PotionPayloadBlockEntity)class_25863;
            if (!world.field_9236 && blockEntity2.add(context.method_8041())) {
                int count = blockEntity2.size();
                world.method_8652(target, (class_2680)world.method_8320(target).method_11657((class_2769)PotionPayloadBlock.COUNT, (Comparable)Integer.valueOf(count)), 3);
                if (context.method_8036() == null || !context.method_8036().method_31549().field_7477) {
                    context.method_8041().method_7934(1);
                }
            }
            return class_1269.method_29236((boolean)world.field_9236);
        }
        class_1799 placementCopy = context.method_8041().method_46651(1);
        class_1269 result = super.method_7884(context);
        if (result.method_23665() && !world.field_9236 && (class_25862 = world.method_8321(placed = world.method_8320(target).method_27852(ModBlocks.POTION_PAYLOAD) ? target : target.method_10093(context.method_8038()))) instanceof PotionPayloadBlockEntity && (blockEntity = (PotionPayloadBlockEntity)class_25862).size() == 0) {
            blockEntity.add(placementCopy);
        }
        return result;
    }

    public static boolean isFilled(class_1799 stack) {
        return stack.method_7909() instanceof PotionPayloadItem && stack.method_7985() && stack.method_7969().method_10573(POTION_STACK_KEY, 10);
    }

    public static class_1799 createFilled(class_1799 potion) {
        class_1799 result = new class_1799((class_1935)ModItems.POTION_PAYLOAD);
        class_2487 potionNbt = new class_2487();
        potion.method_46651(1).method_7953(potionNbt);
        result.method_7948().method_10566(POTION_STACK_KEY, (class_2520)potionNbt);
        result.method_7948().method_10569("CustomModelData", 1);
        return result;
    }

    public static class_1799 getStoredPotion(class_1799 payload) {
        if (!PotionPayloadItem.isFilled(payload)) {
            return class_1799.field_8037;
        }
        class_1799 stack = class_1799.method_7915((class_2487)payload.method_7969().method_10562(POTION_STACK_KEY));
        return stack.method_31574(class_1802.field_8574) ? stack : class_1799.field_8037;
    }

    public class_2561 method_7864(class_1799 stack) {
        return class_2561.method_43471((String)"item.medieval-machinery.potion_payload");
    }

    public void method_7851(class_1799 stack, @Nullable class_1937 world, List<class_2561> tooltip, class_1836 context) {
        class_1799 potion = PotionPayloadItem.getStoredPotion(stack);
        if (!potion.method_7960()) {
            tooltip.add((class_2561)class_2561.method_43469((String)"tooltip.medieval-machinery.potion_payload", (Object[])new Object[]{potion.method_7964()}).method_27692(class_124.field_1080));
        }
        tooltip.add((class_2561)class_2561.method_43471((String)"tooltip.medieval-machinery.potion_payload.fillable").method_27692(class_124.field_1080));
        tooltip.add((class_2561)class_2561.method_43471((String)"tooltip.medieval-machinery.potion_payload.ammunition").method_27692(class_124.field_1080));
        super.method_7851(stack, world, tooltip, context);
    }
}

