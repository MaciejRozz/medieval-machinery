/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1703
 *  net.minecraft.class_2338
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2591
 *  net.minecraft.class_2680
 *  net.minecraft.class_3222
 *  net.minecraft.class_3908
 *  net.minecraft.class_5558
 */
package com.medievalmachinery.mod.platform;

import com.medievalmachinery.mod.block.garrison.WatchpostBlockEntity;
import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import com.medievalmachinery.mod.block.workshop.MachineWorkshopBlockEntity;
import com.medievalmachinery.mod.entity.cannon.AbstractCannonEntity;
import com.medievalmachinery.mod.network.KingdomNetworking;
import com.medievalmachinery.mod.network.MachineControlNetworking;
import com.medievalmachinery.mod.platform.LoaderHooks;
import com.medievalmachinery.mod.registry.ModBlockEntities;
import com.medievalmachinery.mod.screen.garrison.WatchpostScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import net.minecraft.class_2680;
import net.minecraft.class_3222;
import net.minecraft.class_3908;
import net.minecraft.class_5558;

public final class FabricLoaderHooks
implements LoaderHooks.Adapter {
    public static void install() {
        LoaderHooks.install(new FabricLoaderHooks());
    }

    private FabricLoaderHooks() {
    }

    @Override
    public class_2586 createWatchpost(class_2338 pos, class_2680 state) {
        return new WatchpostBlockEntity(pos, state);
    }

    @Override
    public class_2586 createMachineWorkshop(class_2338 pos, class_2680 state) {
        return new MachineWorkshopBlockEntity(pos, state);
    }

    @Override
    public class_2586 createGuillotine(class_2338 pos, class_2680 state) {
        return new GuillotineBlockEntity(pos, state);
    }

    @Override
    public <T extends class_2586> class_5558<T> guillotineTicker(class_2591<T> type) {
        if (type != ModBlockEntities.GUILLOTINE) {
            return null;
        }
        return (world, pos, state, blockEntity) -> GuillotineBlockEntity.tick(world, pos, state, (GuillotineBlockEntity)blockEntity);
    }

    @Override
    public void openWatchpost(class_3222 player, class_2586 blockEntity, final boolean captureMode) {
        if (!(blockEntity instanceof WatchpostBlockEntity)) {
            return;
        }
        final WatchpostBlockEntity watchpost = (WatchpostBlockEntity)blockEntity;
        player.method_17355((class_3908)new ExtendedScreenHandlerFactory(){
            final /* synthetic */ FabricLoaderHooks this$0;
            {
                this.this$0 = this$0;
            }

            public class_2561 method_5476() {
                return captureMode ? class_2561.method_43471((String)"gui.medieval-machinery.watchpost.capture_title") : watchpost.method_5476();
            }

            public class_1703 createMenu(int syncId, class_1661 inventory, class_1657 playerEntity) {
                return new WatchpostScreenHandler(syncId, inventory, watchpost, captureMode);
            }

            public void writeScreenOpeningData(class_3222 serverPlayer, class_2540 buf) {
                if (captureMode) {
                    watchpost.captureScreenFactory().writeScreenOpeningData(serverPlayer, buf);
                } else {
                    watchpost.writeScreenOpeningData(serverPlayer, buf);
                }
            }
        });
    }

    @Override
    public void openKingdomOnboarding(class_3222 player, boolean fromWatchpost) {
        KingdomNetworking.sendOnboardingSnapshot(player, fromWatchpost);
    }

    @Override
    public void openMachineWorkshop(class_3222 player, class_2586 blockEntity) {
        if (blockEntity instanceof MachineWorkshopBlockEntity) {
            MachineWorkshopBlockEntity workshop = (MachineWorkshopBlockEntity)blockEntity;
            player.method_17355((class_3908)workshop);
        }
    }

    @Override
    public void openCannon(class_3222 player, AbstractCannonEntity cannon) {
        final AbstractCannonEntity menu = cannon;
        player.method_17355((class_3908)new ExtendedScreenHandlerFactory(){
            final /* synthetic */ FabricLoaderHooks this$0;
            {
                this.this$0 = this$0;
            }

            public class_2561 method_5476() {
                return menu.method_5476();
            }

            public class_1703 createMenu(int syncId, class_1661 inventory, class_1657 playerEntity) {
                return menu.createMenu(syncId, inventory, playerEntity);
            }

            public void writeScreenOpeningData(class_3222 serverPlayer, class_2540 buf) {
                menu.writeScreenOpeningData(serverPlayer, buf);
            }
        });
    }

    @Override
    public void machineControlStarted(class_3222 player, AbstractCannonEntity cannon) {
        MachineControlNetworking.notifyStarted(player, cannon);
    }

    @Override
    public void machineControlStopped(class_3222 player, AbstractCannonEntity cannon) {
        MachineControlNetworking.notifyStopped(player, cannon);
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}

