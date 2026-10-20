/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1743
 *  net.minecraft.class_1753
 *  net.minecraft.class_1764
 *  net.minecraft.class_1792
 *  net.minecraft.class_1794
 *  net.minecraft.class_1799
 *  net.minecraft.class_1810
 *  net.minecraft.class_1829
 *  net.minecraft.class_1835
 *  net.minecraft.class_2960
 *  net.minecraft.class_5321
 *  net.minecraft.class_6862
 *  net.minecraft.class_7923
 *  net.minecraft.class_7924
 */
package com.medievalmachinery.mod.npc.core;

import com.medievalmachinery.mod.item.spear.SpearItem;
import com.medievalmachinery.mod.npc.core.NpcWeaponRole;
import net.minecraft.class_1743;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1792;
import net.minecraft.class_1794;
import net.minecraft.class_1799;
import net.minecraft.class_1810;
import net.minecraft.class_1829;
import net.minecraft.class_1835;
import net.minecraft.class_2960;
import net.minecraft.class_5321;
import net.minecraft.class_6862;
import net.minecraft.class_7923;
import net.minecraft.class_7924;

public final class NpcWeaponProfileResolver {
    private static final class_6862<class_1792> VANILLA_SWORDS = NpcWeaponProfileResolver.tag("minecraft", "swords");
    private static final class_6862<class_1792> VANILLA_BOWS = NpcWeaponProfileResolver.tag("minecraft", "bows");
    private static final class_6862<class_1792> VANILLA_CROSSBOWS = NpcWeaponProfileResolver.tag("minecraft", "crossbows");
    private static final class_6862<class_1792> NPC_SWORDS = NpcWeaponProfileResolver.mmTag("npc_weapons/swords");
    private static final class_6862<class_1792> NPC_AXES = NpcWeaponProfileResolver.mmTag("npc_weapons/axes");
    private static final class_6862<class_1792> NPC_SPEARS = NpcWeaponProfileResolver.mmTag("npc_weapons/spears");
    private static final class_6862<class_1792> NPC_POLEARMS = NpcWeaponProfileResolver.mmTag("npc_weapons/polearms");
    private static final class_6862<class_1792> NPC_HEAVY_MELEE = NpcWeaponProfileResolver.mmTag("npc_weapons/heavy_melee");
    private static final class_6862<class_1792> NPC_BOWS = NpcWeaponProfileResolver.mmTag("npc_weapons/bows");
    private static final class_6862<class_1792> NPC_CROSSBOWS = NpcWeaponProfileResolver.mmTag("npc_weapons/crossbows");

    private NpcWeaponProfileResolver() {
    }

    public static NpcWeaponRole resolve(class_1799 stack) {
        NpcWeaponRole compatRole;
        if (stack == null || stack.method_7960()) {
            return NpcWeaponRole.NONE;
        }
        class_1792 item = stack.method_7909();
        if (item instanceof SpearItem || item instanceof class_1835) {
            return NpcWeaponRole.SPEAR;
        }
        if (item instanceof class_1829) {
            return NpcWeaponRole.SWORD;
        }
        if (item instanceof class_1743) {
            return NpcWeaponRole.AXE;
        }
        if (item instanceof class_1753) {
            return NpcWeaponRole.BOW;
        }
        if (item instanceof class_1764) {
            return NpcWeaponRole.CROSSBOW;
        }
        if (item instanceof class_1810) {
            return NpcWeaponRole.PICKAXE;
        }
        if (item instanceof class_1794) {
            return NpcWeaponRole.HOE;
        }
        if (stack.method_31573(NPC_SPEARS)) {
            return NpcWeaponRole.SPEAR;
        }
        if (stack.method_31573(NPC_POLEARMS)) {
            return NpcWeaponRole.POLEARM;
        }
        if (stack.method_31573(NPC_HEAVY_MELEE)) {
            return NpcWeaponRole.HEAVY_MELEE;
        }
        if (stack.method_31573(NPC_AXES)) {
            return NpcWeaponRole.AXE;
        }
        if (stack.method_31573(NPC_SWORDS)) {
            return NpcWeaponRole.SWORD;
        }
        if (stack.method_31573(NPC_BOWS)) {
            return NpcWeaponRole.BOW;
        }
        if (stack.method_31573(NPC_CROSSBOWS)) {
            return NpcWeaponRole.CROSSBOW;
        }
        class_2960 id = class_7923.field_41178.method_10221((Object)item);
        if (id != null && "magistuarmory".equals(id.method_12836()) && (compatRole = NpcWeaponProfileResolver.resolveMagistuarmory(id.method_12832())) != NpcWeaponRole.NONE) {
            return compatRole;
        }
        if (stack.method_31573(VANILLA_SWORDS)) {
            return NpcWeaponRole.SWORD;
        }
        if (stack.method_31573(VANILLA_BOWS)) {
            return NpcWeaponRole.BOW;
        }
        if (stack.method_31573(VANILLA_CROSSBOWS)) {
            return NpcWeaponRole.CROSSBOW;
        }
        return NpcWeaponRole.NONE;
    }

    public static boolean isSupportedWeapon(class_1799 stack) {
        NpcWeaponRole role = NpcWeaponProfileResolver.resolve(stack);
        return role != NpcWeaponRole.NONE && role != NpcWeaponRole.PICKAXE && role != NpcWeaponRole.HOE;
    }

    public static boolean sameFamily(class_1799 a, class_1799 b) {
        NpcWeaponRole ar = NpcWeaponProfileResolver.resolve(a);
        NpcWeaponRole br = NpcWeaponProfileResolver.resolve(b);
        if (ar == NpcWeaponRole.NONE || br == NpcWeaponRole.NONE) {
            return false;
        }
        if (ar.isSpearLike() && br.isSpearLike()) {
            return true;
        }
        return ar == br;
    }

    private static NpcWeaponRole resolveMagistuarmory(String path) {
        if (path == null) {
            return NpcWeaponRole.NONE;
        }
        if (path.equals("longbow") || path.endsWith("_longbow")) {
            return NpcWeaponRole.BOW;
        }
        if (path.equals("crossbow") || path.equals("heavy_crossbow") || path.endsWith("_crossbow")) {
            return NpcWeaponRole.CROSSBOW;
        }
        if (NpcWeaponProfileResolver.hasWeaponSuffix(path, "pike", "chivalrylance", "ahlspiess")) {
            return NpcWeaponRole.SPEAR;
        }
        if (NpcWeaponProfileResolver.hasWeaponSuffix(path, "concavehalberd", "ranseur", "guisarme", "pitchfork", "lochaberaxe")) {
            return NpcWeaponRole.POLEARM;
        }
        if (NpcWeaponProfileResolver.hasWeaponSuffix(path, "heavymace", "heavywarhammer", "lucernhammer", "morgenstern", "chainmorgenstern", "barbedclub", "blacksmith_hammer")) {
            return NpcWeaponRole.HEAVY_MELEE;
        }
        if (NpcWeaponProfileResolver.hasWeaponSuffix(path, "bastardsword", "shortsword", "flamebladedsword", "messer_sword", "noble_sword", "katzbalger", "claymore", "estoc", "stylet", "zweihander")) {
            return NpcWeaponRole.SWORD;
        }
        return NpcWeaponRole.NONE;
    }

    private static boolean hasWeaponSuffix(String path, String ... names) {
        for (String name : names) {
            if (!path.equals(name) && !path.endsWith("_" + name)) continue;
            return true;
        }
        return false;
    }

    private static class_6862<class_1792> mmTag(String path) {
        return NpcWeaponProfileResolver.tag("medieval-machinery", path);
    }

    private static class_6862<class_1792> tag(String namespace, String path) {
        return class_6862.method_40092((class_5321)class_7924.field_41197, (class_2960)new class_2960(namespace, path));
    }
}

