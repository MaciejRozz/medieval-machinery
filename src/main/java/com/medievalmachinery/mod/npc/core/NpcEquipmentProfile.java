/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1304
 *  net.minecraft.class_1322
 *  net.minecraft.class_1799
 *  net.minecraft.class_5134
 */
package com.medievalmachinery.mod.npc.core;

import com.medievalmachinery.mod.npc.core.NpcPreferredMeleeWeapon;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.core.NpcWeaponProfileResolver;
import com.medievalmachinery.mod.npc.core.NpcWeaponRole;
import net.minecraft.class_1304;
import net.minecraft.class_1322;
import net.minecraft.class_1799;
import net.minecraft.class_5134;

public final class NpcEquipmentProfile {
    private static final int PRIMARY = 100;
    private static final int SECONDARY = 85;
    private static final int RANGED_FALLBACK = 60;
    private static final int OTHER_FALLBACK = 70;

    private NpcEquipmentProfile() {
    }

    public static boolean acceptsMainHand(NpcRole role, class_1799 stack) {
        return NpcEquipmentProfile.weaponPreference(role, stack) > 0;
    }

    public static boolean isCombatReady(NpcRole role, class_1799 mainHand) {
        return NpcEquipmentProfile.weaponPreference(role, mainHand) > 0;
    }

    public static boolean isSupportedWeapon(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return false;
        }
        return NpcWeaponProfileResolver.resolve(stack) != NpcWeaponRole.NONE;
    }

    public static int weaponPreference(NpcRole role, class_1799 stack) {
        if (role == null || stack == null || stack.method_7960()) {
            return 0;
        }
        NpcWeaponRole weaponRole = NpcWeaponProfileResolver.resolve(stack);
        boolean sword = weaponRole == NpcWeaponRole.SWORD;
        boolean axe = weaponRole == NpcWeaponRole.AXE || weaponRole == NpcWeaponRole.HEAVY_MELEE;
        boolean bow = weaponRole == NpcWeaponRole.BOW;
        boolean crossbow = weaponRole == NpcWeaponRole.CROSSBOW;
        boolean spear = weaponRole.isSpearLike();
        boolean pickaxe = weaponRole == NpcWeaponRole.PICKAXE;
        boolean hoe = weaponRole == NpcWeaponRole.HOE;
        return switch (role) {
            default -> throw new IncompatibleClassChangeError();
            case NpcRole.SWORDSMAN, NpcRole.CANNONEER, NpcRole.RECRUIT -> {
                if (sword) {
                    yield 100;
                }
                if (axe) {
                    yield 85;
                }
                if (bow || crossbow) {
                    yield 60;
                }
                if (spear) {
                    yield 70;
                }
                yield 0;
            }
            case NpcRole.QUARTERMASTER -> {
                if (hoe) {
                    yield 100;
                }
                if (axe || sword) {
                    yield 85;
                }
                if (bow || crossbow) {
                    yield 60;
                }
                if (spear) {
                    yield 70;
                }
                yield 0;
            }
            case NpcRole.KNIGHT, NpcRole.MOUNTED_KNIGHT -> {
                if (sword || axe || spear) {
                    yield 100;
                }
                if (bow || crossbow) {
                    yield 60;
                }
                yield 0;
            }
            case NpcRole.AXEMAN -> {
                if (axe) {
                    yield 100;
                }
                if (sword) {
                    yield 85;
                }
                if (bow || crossbow) {
                    yield 60;
                }
                if (spear) {
                    yield 70;
                }
                yield 0;
            }
            case NpcRole.ARCHER, NpcRole.MOUNTED_ARCHER -> {
                if (bow) {
                    yield 100;
                }
                if (crossbow) {
                    yield 85;
                }
                if (sword || axe) {
                    yield 60;
                }
                if (spear) {
                    yield 70;
                }
                yield 0;
            }
            case NpcRole.CROSSBOWMAN -> {
                if (crossbow) {
                    yield 100;
                }
                if (bow) {
                    yield 85;
                }
                if (sword || axe) {
                    yield 60;
                }
                if (spear) {
                    yield 70;
                }
                yield 0;
            }
            case NpcRole.SPEARMAN -> {
                if (spear) {
                    yield 100;
                }
                if (sword) {
                    yield 85;
                }
                if (axe) {
                    yield 70;
                }
                if (bow || crossbow) {
                    yield 60;
                }
                yield 0;
            }
            case NpcRole.ENGINEER -> {
                if (pickaxe || axe) {
                    yield 100;
                }
                if (sword) {
                    yield 85;
                }
                if (bow || crossbow) {
                    yield 60;
                }
                if (spear) {
                    yield 70;
                }
                yield 0;
            }
            case NpcRole.SCOUT -> bow ? 100 : (sword ? 85 : (crossbow || axe ? 70 : (spear ? 70 : 0)));
        };
    }

    public static double damageMultiplier(NpcRole role, class_1799 stack) {
        int preference = NpcEquipmentProfile.weaponPreference(role, stack);
        if (preference <= 0) {
            return 0.5;
        }
        return (double)preference / 100.0;
    }

    public static long weaponScore(NpcRole role, class_1799 stack) {
        int preference = NpcEquipmentProfile.weaponPreference(role, stack);
        if (preference <= 0) {
            return Long.MIN_VALUE;
        }
        long quality = NpcEquipmentProfile.weaponAttributeQuality(stack);
        if (stack.method_7963()) {
            quality += (long)Math.min(20000, stack.method_7936());
            quality += (long)Math.max(0, stack.method_7936() - stack.method_7919()) / 4L;
        }
        if (stack.method_7942()) {
            quality += 5000L;
        }
        return (long)preference * 1000000L + quality;
    }

    private static long weaponAttributeQuality(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return 0L;
        }
        double damage = 0.0;
        double speed = 0.0;
        for (class_1322 modifier : stack.method_7926(class_1304.field_6173).get((Object)class_5134.field_23721)) {
            damage += modifier.method_6186();
        }
        for (class_1322 modifier : stack.method_7926(class_1304.field_6173).get((Object)class_5134.field_23723)) {
            speed += modifier.method_6186();
        }
        long damageScore = Math.round(Math.max(0.0, damage) * 10000.0);
        long speedScore = Math.round(Math.max(-4.0, speed) * 1000.0);
        return damageScore + speedScore;
    }

    public static long weaponScore(NpcRole role, NpcPreferredMeleeWeapon preferred, class_1799 stack) {
        long base = NpcEquipmentProfile.weaponScore(role, stack);
        if (base == Long.MIN_VALUE) {
            return base;
        }
        if ((role == NpcRole.KNIGHT || role == NpcRole.MOUNTED_KNIGHT) && preferred != null && preferred.matches(stack)) {
            return base + 100000L;
        }
        return base;
    }

    public static boolean isBetterWeapon(NpcRole role, NpcPreferredMeleeWeapon preferred, class_1799 candidate, class_1799 current) {
        if (!NpcEquipmentProfile.acceptsMainHand(role, candidate)) {
            return false;
        }
        if (current == null || current.method_7960()) {
            return true;
        }
        return NpcEquipmentProfile.weaponScore(role, preferred, candidate) > NpcEquipmentProfile.weaponScore(role, preferred, current);
    }

    public static boolean isBetterWeapon(NpcRole role, class_1799 candidate, class_1799 current) {
        if (!NpcEquipmentProfile.acceptsMainHand(role, candidate)) {
            return false;
        }
        if (current == null || current.method_7960()) {
            return true;
        }
        return NpcEquipmentProfile.weaponScore(role, candidate) > NpcEquipmentProfile.weaponScore(role, current);
    }
}

