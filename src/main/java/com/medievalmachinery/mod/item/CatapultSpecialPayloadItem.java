/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_124
 *  net.minecraft.class_1747
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_1836
 *  net.minecraft.class_1937
 *  net.minecraft.class_2248
 *  net.minecraft.class_2561
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.item;

import java.util.List;
import net.minecraft.class_124;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1836;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2561;
import org.jetbrains.annotations.Nullable;

public final class CatapultSpecialPayloadItem
extends class_1747 {
    private final String tooltipKey;

    public CatapultSpecialPayloadItem(class_2248 block, class_1792.class_1793 settings, String tooltipKey) {
        super(block, settings);
        this.tooltipKey = tooltipKey;
    }

    public void method_7851(class_1799 stack, @Nullable class_1937 world, List<class_2561> tooltip, class_1836 context) {
        tooltip.add((class_2561)class_2561.method_43471((String)(this.tooltipKey + ".line1")).method_27692(class_124.field_1080));
        tooltip.add((class_2561)class_2561.method_43471((String)(this.tooltipKey + ".line2")).method_27692(class_124.field_1080));
        super.method_7851(stack, world, tooltip, context);
    }
}

