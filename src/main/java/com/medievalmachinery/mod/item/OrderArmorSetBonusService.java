/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1282
 *  net.minecraft.class_1291
 *  net.minecraft.class_1293
 *  net.minecraft.class_1294
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1320
 *  net.minecraft.class_1322
 *  net.minecraft.class_1322$class_1323
 *  net.minecraft.class_1324
 *  net.minecraft.class_1657
 *  net.minecraft.class_1676
 *  net.minecraft.class_1799
 *  net.minecraft.class_5134
 *  net.minecraft.class_8103
 */
package com.medievalmachinery.mod.item;

import com.medievalmachinery.mod.entity.cannon.CannonballEntity;
import com.medievalmachinery.mod.entity.catapult.CatapultStoneEntity;
import com.medievalmachinery.mod.item.MedievalArmorItem;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.platform.RegisteredContent;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.class_1282;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1320;
import net.minecraft.class_1322;
import net.minecraft.class_1324;
import net.minecraft.class_1657;
import net.minecraft.class_1676;
import net.minecraft.class_1799;
import net.minecraft.class_5134;
import net.minecraft.class_8103;

public final class OrderArmorSetBonusService {
    public static final double GOLD_MELEE_DAMAGE = 0.1;
    public static final double COPPER_SPEED = 0.1;
    public static final double IRON_KNOCKBACK = 0.2;
    public static final double DIAMOND_ARMOR = 2.0;
    public static final double DIAMOND_TOUGHNESS = 1.0;
    public static final double LAPIS_RANGED_DAMAGE = 0.1;
    public static final double QUARTZ_PROJECTILE_REDUCTION = 0.15;
    public static final double COAL_TARGET_DISTANCE_FACTOR = 0.9;
    public static final double NETHERITE_KNOCKBACK = 0.35;
    private static final int EMERALD_OUT_OF_COMBAT_TICKS = 100;
    private static final int EMERALD_HEAL_INTERVAL = 100;
    private static final float EMERALD_HEAL_AMOUNT = 1.0f;
    private static final Map<class_1309, Integer> LAST_DAMAGE_AGE = Collections.synchronizedMap(new WeakHashMap());
    private static final ModifierDef[] MODIFIERS = new ModifierDef[]{new ModifierDef("gold_melee", class_5134.field_23721, 0.1, class_1322.class_1323.field_6331, OrderStyle.GOLD), new ModifierDef("copper_speed", class_5134.field_23719, 0.1, class_1322.class_1323.field_6331, OrderStyle.COPPER), new ModifierDef("iron_knockback", class_5134.field_23718, 0.2, class_1322.class_1323.field_6328, OrderStyle.IRON), new ModifierDef("diamond_armor", class_5134.field_23724, 2.0, class_1322.class_1323.field_6328, OrderStyle.DIAMOND), new ModifierDef("diamond_toughness", class_5134.field_23725, 1.0, class_1322.class_1323.field_6328, OrderStyle.DIAMOND), new ModifierDef("netherite_knockback", class_5134.field_23718, 0.35, class_1322.class_1323.field_6328, OrderStyle.NETHERITE)};

    private OrderArmorSetBonusService() {
    }

    public static OrderStyle getCompleteSetOrder(class_1309 entity) {
        if (entity == null) {
            return OrderStyle.NONE;
        }
        OrderStyle order = OrderStyle.NONE;
        for (class_1304 slot : new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166}) {
            class_1799 stack = entity.method_6118(slot);
            if (!(stack.method_7909() instanceof MedievalArmorItem)) {
                return OrderStyle.NONE;
            }
            OrderStyle pieceOrder = MedievalArmorItem.getOrderStyle(stack);
            if (pieceOrder == OrderStyle.NONE) {
                return OrderStyle.NONE;
            }
            if (order == OrderStyle.NONE) {
                order = pieceOrder;
                continue;
            }
            if (order == pieceOrder) continue;
            return OrderStyle.NONE;
        }
        return order;
    }

    public static boolean hasCompleteSet(class_1309 entity, OrderStyle order) {
        return order != null && order != OrderStyle.NONE && OrderArmorSetBonusService.getCompleteSetOrder(entity) == order;
    }

    public static void tickServer(class_1309 entity) {
        MedievalSoldierEntity soldier;
        if (entity == null || entity.method_37908().field_9236) {
            return;
        }
        OrderStyle active = OrderArmorSetBonusService.getCompleteSetOrder(entity);
        OrderArmorSetBonusService.syncAttributeModifiers(entity, active);
        OrderArmorSetBonusService.syncDisplayEffect(entity, active);
        if (active == OrderStyle.REDSTONE && entity.method_5809()) {
            entity.method_5646();
        }
        if (active == OrderStyle.AMETHYST && entity instanceof class_1657) {
            entity.method_6092(new class_1293(class_1294.field_5925, 260, 0, true, false, false));
        }
        if (!(active != OrderStyle.EMERALD || entity.field_6012 % 100 != 0 || !(entity.method_6032() < entity.method_6063()) || OrderArmorSetBonusService.ticksSinceLastDamage(entity) < 100 || entity instanceof MedievalSoldierEntity && (soldier = (MedievalSoldierEntity)entity).method_5968() != null && soldier.method_5968().method_5805())) {
            entity.method_6025(1.0f);
        }
    }

    private static void syncDisplayEffect(class_1309 entity, OrderStyle active) {
        for (OrderStyle style : OrderStyle.values()) {
            if (style == OrderStyle.NONE) continue;
            class_1291 effect = RegisteredContent.orderSetEffect(style);
            if (style == active) {
                class_1293 current = entity.method_6112(effect);
                if (current != null && current.method_5584() >= 120) continue;
                entity.method_6092(new class_1293(effect, 220, 0, false, false, true));
                continue;
            }
            if (!entity.method_6059(effect)) continue;
            entity.method_6016(effect);
        }
    }

    public static void markDamaged(class_1309 entity) {
        if (entity != null && !entity.method_37908().field_9236) {
            LAST_DAMAGE_AGE.put(entity, entity.field_6012);
        }
    }

    private static int ticksSinceLastDamage(class_1309 entity) {
        Integer last = LAST_DAMAGE_AGE.get(entity);
        return last == null ? Integer.MAX_VALUE : Math.max(0, entity.field_6012 - last);
    }

    public static float modifyIncomingDamage(class_1309 victim, class_1282 source, float amount) {
        if (amount <= 0.0f || victim == null || source == null) {
            return amount;
        }
        if (OrderArmorSetBonusService.hasCompleteSet(victim, OrderStyle.REDSTONE) && source.method_48789(class_8103.field_42246)) {
            return 0.0f;
        }
        if (!OrderArmorSetBonusService.hasCompleteSet(victim, OrderStyle.QUARTZ)) {
            return amount;
        }
        class_1297 direct = source.method_5526();
        if (!(direct instanceof class_1676)) {
            return amount;
        }
        if (direct instanceof CannonballEntity || direct instanceof CatapultStoneEntity) {
            return amount;
        }
        return amount * 0.85f;
    }

    public static boolean isUnshakable(class_1309 entity) {
        return OrderArmorSetBonusService.hasCompleteSet(entity, OrderStyle.NETHERITE);
    }

    public static double aiTargetDistanceFactor(class_1309 target) {
        return OrderArmorSetBonusService.hasCompleteSet(target, OrderStyle.COAL) ? 0.9 : 1.0;
    }

    public static double rangedDamageMultiplier(class_1309 shooter) {
        return OrderArmorSetBonusService.hasCompleteSet(shooter, OrderStyle.LAPIS) ? 1.1 : 1.0;
    }

    private static void syncAttributeModifiers(class_1309 entity, OrderStyle active) {
        for (ModifierDef def : MODIFIERS) {
            class_1324 instance = entity.method_5996(def.attribute());
            if (instance == null) continue;
            class_1322 existing = instance.method_6199(def.id());
            if (active == def.order()) {
                if (existing != null) continue;
                instance.method_26835(new class_1322(def.id(), "Medieval Machinery order set " + def.name(), def.amount(), def.operation()));
                continue;
            }
            if (existing == null) continue;
            instance.method_6200(def.id());
        }
    }

    private record ModifierDef(String name, class_1320 attribute, double amount, class_1322.class_1323 operation, OrderStyle order) {
        UUID id() {
            return UUID.nameUUIDFromBytes(("medieval-machinery:order-set:" + this.name).getBytes(StandardCharsets.UTF_8));
        }
    }
}

