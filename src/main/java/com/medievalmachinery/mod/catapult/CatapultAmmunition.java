/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1747
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1922
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2680
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.catapult;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.item.PotionPayloadItem;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public final class CatapultAmmunition {
    private CatapultAmmunition() {
    }

    public static boolean isValid(class_1799 stack) {
        if (stack.method_7960()) {
            return false;
        }
        if (PotionPayloadItem.isFilled(stack)) {
            return true;
        }
        if (stack.method_31574(ModItems.EXPLOSIVE_PAYLOAD) || stack.method_31574(ModItems.FIRE_PAYLOAD)) {
            return true;
        }
        if (stack.method_7909() instanceof class_1747) {
            return MedievalMachineryConfig.get().catapult.allowUnlistedBlocks;
        }
        class_2960 id = class_7923.field_41178.method_10221((Object)stack.method_7909());
        return MedievalMachineryConfig.get().catapult.ammunitionProfiles.containsKey(id.toString());
    }

    public static Profile resolve(class_1799 stack, class_1937 world, class_2338 pos) {
        MedievalMachineryConfig.Catapult config = MedievalMachineryConfig.get().catapult;
        if (PotionPayloadItem.isFilled(stack)) {
            return new Profile(config.potionDirectHitDamage, 0.0f, 0.92f, 1.0f, 0.01f, Effect.POTION);
        }
        if (stack.method_31574(ModItems.EXPLOSIVE_PAYLOAD)) {
            return new Profile(MedievalMachineryConfig.get().tnt.damage, 0.0f, 0.86f, 1.08f, 0.014f, Effect.EXPLOSIVE);
        }
        if (stack.method_31574(ModItems.FIRE_PAYLOAD)) {
            return new Profile(config.firePayloadDirectDamage, 0.0f, 0.94f, 1.02f, 0.012f, Effect.FIRE);
        }
        if (stack.method_31574(ModItems.COBBLESTONE_BUNDLE)) {
            return new Profile(0.0f, 0.0f, 0.98f, 1.0f, 0.01f, Effect.COBBLESTONE_BUNDLE);
        }
        class_2960 itemId = class_7923.field_41178.method_10221((Object)stack.method_7909());
        MedievalMachineryConfig.CatapultAmmo configured = config.ammunitionProfiles.get(itemId.toString());
        if (configured != null) {
            return CatapultAmmunition.fromConfig(configured, config);
        }
        class_1792 class_17922 = stack.method_7909();
        if (class_17922 instanceof class_1747) {
            class_1747 blockItem = (class_1747)class_17922;
            if (config.allowUnlistedBlocks) {
                class_2248 block = blockItem.method_7711();
                class_2680 state = block.method_9564();
                float hardness = Math.max(0.0f, state.method_26214((class_1922)world, pos));
                float resistance = Math.max(0.0f, block.method_9520());
                float mass = Math.min(12.0f, 0.75f + hardness * 0.65f + resistance * 0.08f);
                float damage = CatapultAmmunition.clamp(2.0f + mass * 2.2f, 1.0f, config.maximumAutomaticDamage);
                float blockDamage = CatapultAmmunition.clamp(0.5f + hardness * 0.65f + resistance * 0.04f, 0.0f, 16.0f);
                float velocity = CatapultAmmunition.clamp(1.15f - mass * 0.035f, 0.62f, 1.18f);
                float gravity = CatapultAmmunition.clamp(0.88f + mass * 0.025f, 0.85f, 1.25f);
                return CatapultAmmunition.scaled(new Profile(damage, blockDamage, velocity, gravity, 0.01f, Effect.NORMAL), config);
            }
        }
        return CatapultAmmunition.scaled(new Profile(6.0f, 1.0f, 1.0f, 1.0f, 0.01f, Effect.NORMAL), config);
    }

    private static Profile fromConfig(MedievalMachineryConfig.CatapultAmmo ammo, MedievalMachineryConfig.Catapult global) {
        return CatapultAmmunition.scaled(new Profile(ammo.damage, ammo.blockDamage, ammo.velocityMultiplier, ammo.gravityMultiplier, ammo.drag, Effect.NORMAL), global);
    }

    private static Profile scaled(Profile profile, MedievalMachineryConfig.Catapult global) {
        return new Profile(Math.max(0.0f, profile.damage * global.damageMultiplier), Math.max(0.0f, profile.blockDamage * global.blockDamageMultiplier), CatapultAmmunition.clamp(profile.velocityMultiplier, 0.1f, 3.0f), CatapultAmmunition.clamp(profile.gravityMultiplier, 0.1f, 3.0f), CatapultAmmunition.clamp(profile.drag, 0.0f, 0.25f), profile.effect());
    }

    private static float clamp(float value, float minimum, float maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    public record Profile(float damage, float blockDamage, float velocityMultiplier, float gravityMultiplier, float drag, Effect effect) {
        public boolean potion() {
            return this.effect == Effect.POTION;
        }

        public boolean explosive() {
            return this.effect == Effect.EXPLOSIVE;
        }

        public boolean fire() {
            return this.effect == Effect.FIRE;
        }

        public boolean cobblestoneBundle() {
            return this.effect == Effect.COBBLESTONE_BUNDLE;
        }
    }

    public static enum Effect {
        NORMAL,
        POTION,
        EXPLOSIVE,
        FIRE,
        COBBLESTONE_BUNDLE;

    }
}

