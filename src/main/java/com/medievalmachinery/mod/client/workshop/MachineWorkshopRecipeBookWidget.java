/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1860
 *  net.minecraft.class_507
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.client.workshop;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1860;
import net.minecraft.class_507;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public final class MachineWorkshopRecipeBookWidget
extends class_507 {
    @Nullable
    public class_1860<?> getGhostRecipe() {
        return this.field_3092.method_2566();
    }
}

