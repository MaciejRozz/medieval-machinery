/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.compat;

import com.medievalmachinery.mod.client.command.CommandOverlayScreen;
import com.medievalmachinery.mod.client.garrison.WatchpostScreen;
import com.medievalmachinery.mod.client.npc.SoldierInventoryScreen;
import java.lang.reflect.Field;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class RecipeOverlayVisibilityController {
    private static Boolean savedEmiEnabled;

    private RecipeOverlayVisibilityController() {
    }

    public static void onScreenChanging(class_437 nextScreen) {
        RecipeOverlayVisibilityController.setEmiSuppressed(RecipeOverlayVisibilityController.usesFullCommandLayout(nextScreen));
    }

    private static boolean usesFullCommandLayout(class_437 screen) {
        return screen instanceof WatchpostScreen || screen instanceof CommandOverlayScreen || screen instanceof SoldierInventoryScreen;
    }

    private static void setEmiSuppressed(boolean suppress) {
        try {
            Class<?> configClass = Class.forName("dev.emi.emi.config.EmiConfig", false, RecipeOverlayVisibilityController.class.getClassLoader());
            Field enabled = configClass.getField("enabled");
            if (suppress) {
                if (savedEmiEnabled == null) {
                    savedEmiEnabled = enabled.getBoolean(null);
                }
                enabled.setBoolean(null, false);
            } else if (savedEmiEnabled != null) {
                enabled.setBoolean(null, savedEmiEnabled);
                savedEmiEnabled = null;
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        catch (LinkageError | ReflectiveOperationException throwable) {
            // empty catch block
        }
    }
}

