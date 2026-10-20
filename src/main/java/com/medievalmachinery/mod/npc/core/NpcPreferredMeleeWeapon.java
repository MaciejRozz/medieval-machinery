/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 */
package com.medievalmachinery.mod.npc.core;

import com.medievalmachinery.mod.npc.core.NpcWeaponProfileResolver;
import com.medievalmachinery.mod.npc.core.NpcWeaponRole;
import net.minecraft.class_1799;

public enum NpcPreferredMeleeWeapon {
    SWORD("sword"),
    AXE("axe"),
    SPEAR("spear");

    private final String serializedName;

    private NpcPreferredMeleeWeapon(String serializedName) {
        this.serializedName = serializedName;
    }

    public String serializedName() {
        return this.serializedName;
    }

    public boolean matches(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return false;
        }
        NpcWeaponRole role = NpcWeaponProfileResolver.resolve(stack);
        return switch (this.ordinal()) {
            default -> throw new IncompatibleClassChangeError();
            case 0 -> {
                if (role == NpcWeaponRole.SWORD) {
                    yield true;
                }
                yield false;
            }
            case 1 -> {
                if (role == NpcWeaponRole.AXE || role == NpcWeaponRole.HEAVY_MELEE) {
                    yield true;
                }
                yield false;
            }
            case 2 -> role.isSpearLike();
        };
    }

    public static NpcPreferredMeleeWeapon fromSerializedName(String name) {
        if (name != null) {
            for (NpcPreferredMeleeWeapon value : NpcPreferredMeleeWeapon.values()) {
                if (!value.serializedName.equalsIgnoreCase(name)) continue;
                return value;
            }
        }
        return SWORD;
    }
}

