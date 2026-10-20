/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1734
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package com.medievalmachinery.mod.mixin;

import net.minecraft.class_1657;
import net.minecraft.class_1734;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_1734.class})
public interface CraftingResultSlotAccessor {
    @Accessor(value="field_7868")
    public class_1657 medievalMachinery$getCraftingPlayer();
}

