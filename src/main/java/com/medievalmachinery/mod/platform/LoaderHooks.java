/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2586
 *  net.minecraft.class_2591
 *  net.minecraft.class_2680
 *  net.minecraft.class_3222
 *  net.minecraft.class_5558
 */
package com.medievalmachinery.mod.platform;

import com.medievalmachinery.mod.entity.cannon.AbstractCannonEntity;
import java.util.Objects;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import net.minecraft.class_2680;
import net.minecraft.class_3222;
import net.minecraft.class_5558;

public final class LoaderHooks {
    private static Adapter adapter;

    private LoaderHooks() {
    }

    public static void install(Adapter value) {
        if (adapter != null) {
            throw new IllegalStateException("Loader adapter already installed");
        }
        adapter = Objects.requireNonNull(value, "value");
    }

    private static Adapter require() {
        if (adapter == null) {
            throw new IllegalStateException("Loader adapter requested before initialization");
        }
        return adapter;
    }

    public static class_2586 createWatchpost(class_2338 pos, class_2680 state) {
        return LoaderHooks.require().createWatchpost(pos, state);
    }

    public static class_2586 createMachineWorkshop(class_2338 pos, class_2680 state) {
        return LoaderHooks.require().createMachineWorkshop(pos, state);
    }

    public static class_2586 createGuillotine(class_2338 pos, class_2680 state) {
        return LoaderHooks.require().createGuillotine(pos, state);
    }

    public static <T extends class_2586> class_5558<T> guillotineTicker(class_2591<T> type) {
        return LoaderHooks.require().guillotineTicker(type);
    }

    public static void openWatchpost(class_3222 player, class_2586 blockEntity, boolean captureMode) {
        LoaderHooks.require().openWatchpost(player, blockEntity, captureMode);
    }

    public static void openKingdomOnboarding(class_3222 player, boolean fromWatchpost) {
        LoaderHooks.require().openKingdomOnboarding(player, fromWatchpost);
    }

    public static void openMachineWorkshop(class_3222 player, class_2586 blockEntity) {
        LoaderHooks.require().openMachineWorkshop(player, blockEntity);
    }

    public static void openCannon(class_3222 player, AbstractCannonEntity cannon) {
        LoaderHooks.require().openCannon(player, cannon);
    }

    public static void machineControlStarted(class_3222 player, AbstractCannonEntity cannon) {
        LoaderHooks.require().machineControlStarted(player, cannon);
    }

    public static void machineControlStopped(class_3222 player, AbstractCannonEntity cannon) {
        LoaderHooks.require().machineControlStopped(player, cannon);
    }

    public static boolean isModLoaded(String modId) {
        return LoaderHooks.require().isModLoaded(modId);
    }

    public static interface Adapter {
        public class_2586 createWatchpost(class_2338 var1, class_2680 var2);

        public class_2586 createMachineWorkshop(class_2338 var1, class_2680 var2);

        public class_2586 createGuillotine(class_2338 var1, class_2680 var2);

        public <T extends class_2586> class_5558<T> guillotineTicker(class_2591<T> var1);

        public void openWatchpost(class_3222 var1, class_2586 var2, boolean var3);

        public void openKingdomOnboarding(class_3222 var1, boolean var2);

        public void openMachineWorkshop(class_3222 var1, class_2586 var2);

        public void openCannon(class_3222 var1, AbstractCannonEntity var2);

        public void machineControlStarted(class_3222 var1, AbstractCannonEntity var2);

        public void machineControlStopped(class_3222 var1, AbstractCannonEntity var2);

        public boolean isModLoaded(String var1);
    }
}

