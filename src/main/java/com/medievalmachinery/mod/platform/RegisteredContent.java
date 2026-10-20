/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1291
 *  net.minecraft.class_1299
 *  net.minecraft.class_2338
 *  net.minecraft.class_2586
 *  net.minecraft.class_2591
 *  net.minecraft.class_2680
 *  net.minecraft.class_3852
 *  net.minecraft.class_3917
 */
package com.medievalmachinery.mod.platform;

import com.medievalmachinery.mod.block.WheelBlockEntity;
import com.medievalmachinery.mod.block.ammunition.PotionPayloadBlockEntity;
import com.medievalmachinery.mod.block.ammunition.SpecialPayloadBlockEntity;
import com.medievalmachinery.mod.blockentity.machine.cannon.DecorativeFieldCannonBlockEntity;
import com.medievalmachinery.mod.blockentity.machine.cannon.DecorativeFortressCannonBlockEntity;
import com.medievalmachinery.mod.entity.cannon.CannonballEntity;
import com.medievalmachinery.mod.entity.cannon.FieldCannonEntity;
import com.medievalmachinery.mod.entity.cannon.StationaryCannonEntity;
import com.medievalmachinery.mod.entity.catapult.CatapultEntity;
import com.medievalmachinery.mod.entity.catapult.CatapultStoneEntity;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.screen.garrison.WatchpostScreenHandler;
import com.medievalmachinery.mod.screen.machine.cannon.FieldCannonScreenHandler;
import com.medievalmachinery.mod.screen.machine.cannon.StationaryCannonScreenHandler;
import com.medievalmachinery.mod.screen.npc.SoldierInventoryScreenHandler;
import com.medievalmachinery.mod.screen.storage.WickerBasketScreenHandler;
import com.medievalmachinery.mod.screen.workshop.MachineWorkshopScreenHandler;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import net.minecraft.class_1291;
import net.minecraft.class_1299;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import net.minecraft.class_2680;
import net.minecraft.class_3852;
import net.minecraft.class_3917;

public final class RegisteredContent {
    private static class_1299<CannonballEntity> cannonball;
    private static class_1299<FieldCannonEntity> fieldCannon;
    private static class_1299<StationaryCannonEntity> stationaryCannon;
    private static class_1299<CatapultStoneEntity> catapultStone;
    private static class_1299<CatapultEntity> catapult;
    private static class_1299<MedievalSoldierEntity> soldier;
    private static class_2591<WheelBlockEntity> wheel;
    private static class_2591<SpecialPayloadBlockEntity> specialPayload;
    private static class_2591<PotionPayloadBlockEntity> potionPayload;
    private static class_2591<DecorativeFortressCannonBlockEntity> decorativeFortressCannon;
    private static class_2591<DecorativeFieldCannonBlockEntity> decorativeFieldCannon;
    private static class_2591<? extends class_2586> guillotine;
    private static class_2591<? extends class_2586> stationaryCannonBlock;
    private static class_2591<? extends class_2586> wickerBasket;
    private static BiFunction<class_2338, class_2680, class_2586> wickerBasketFactory;
    private static BiFunction<class_2338, class_2680, class_2586> stationaryCannonBlockFactory;
    private static class_3917<SoldierInventoryScreenHandler> soldierInventory;
    private static class_3917<WatchpostScreenHandler> watchpost;
    private static class_3917<MachineWorkshopScreenHandler> machineWorkshop;
    private static class_3917<StationaryCannonScreenHandler> stationaryCannonScreen;
    private static class_3917<FieldCannonScreenHandler> fieldCannonScreen;
    private static class_3917<WickerBasketScreenHandler> wickerBasketScreen;
    private static class_3852 executionerProfession;
    private static final Map<OrderStyle, class_1291> orderSetEffects;

    private RegisteredContent() {
    }

    public static void bindEntities(class_1299<CannonballEntity> cannonballType, class_1299<FieldCannonEntity> fieldCannonType, class_1299<StationaryCannonEntity> stationaryCannonType, class_1299<CatapultStoneEntity> catapultStoneType, class_1299<CatapultEntity> catapultType, class_1299<MedievalSoldierEntity> soldierType) {
        if (cannonball != null) {
            throw new IllegalStateException("Entity content already bound");
        }
        cannonball = cannonballType;
        fieldCannon = fieldCannonType;
        stationaryCannon = stationaryCannonType;
        catapultStone = catapultStoneType;
        catapult = catapultType;
        soldier = soldierType;
    }

    public static void bindCommonBlockEntities(class_2591<WheelBlockEntity> wheelType, class_2591<SpecialPayloadBlockEntity> specialPayloadType, class_2591<PotionPayloadBlockEntity> potionPayloadType, class_2591<DecorativeFortressCannonBlockEntity> decorativeFortressCannonType, class_2591<DecorativeFieldCannonBlockEntity> decorativeFieldCannonType, class_2591<? extends class_2586> guillotineType, class_2591<? extends class_2586> stationaryCannonBlockType, class_2591<? extends class_2586> wickerBasketType, BiFunction<class_2338, class_2680, class_2586> wickerBasketFactoryValue, BiFunction<class_2338, class_2680, class_2586> stationaryCannonBlockFactoryValue) {
        if (wheel != null) {
            throw new IllegalStateException("Common block-entity content already bound");
        }
        wheel = wheelType;
        specialPayload = specialPayloadType;
        potionPayload = potionPayloadType;
        decorativeFortressCannon = decorativeFortressCannonType;
        decorativeFieldCannon = decorativeFieldCannonType;
        guillotine = guillotineType;
        stationaryCannonBlock = stationaryCannonBlockType;
        wickerBasket = wickerBasketType;
        wickerBasketFactory = wickerBasketFactoryValue;
        stationaryCannonBlockFactory = stationaryCannonBlockFactoryValue;
    }

    public static void bindScreens(class_3917<SoldierInventoryScreenHandler> soldierInventoryType, class_3917<WatchpostScreenHandler> watchpostType, class_3917<MachineWorkshopScreenHandler> machineWorkshopType, class_3917<StationaryCannonScreenHandler> stationaryCannonType, class_3917<FieldCannonScreenHandler> fieldCannonType, class_3917<WickerBasketScreenHandler> wickerBasketType) {
        if (soldierInventory != null) {
            throw new IllegalStateException("Screen content already bound");
        }
        soldierInventory = soldierInventoryType;
        watchpost = watchpostType;
        machineWorkshop = machineWorkshopType;
        stationaryCannonScreen = stationaryCannonType;
        fieldCannonScreen = fieldCannonType;
        wickerBasketScreen = wickerBasketType;
    }

    public static void bindOrderSetEffects(Map<OrderStyle, class_1291> effects) {
        if (!orderSetEffects.isEmpty()) {
            throw new IllegalStateException("Order set effects already bound");
        }
        if (effects == null) {
            throw new IllegalArgumentException("effects");
        }
        for (OrderStyle style : OrderStyle.values()) {
            if (style == OrderStyle.NONE) continue;
            class_1291 effect = effects.get((Object)style);
            if (effect == null) {
                throw new IllegalStateException("Missing order set display effect for " + String.valueOf((Object)style));
            }
            orderSetEffects.put(style, effect);
        }
    }

    public static void bindExecutionerProfession(class_3852 profession) {
        if (executionerProfession != null) {
            throw new IllegalStateException("Executioner profession already bound");
        }
        executionerProfession = Objects.requireNonNull(profession, "profession");
    }

    private static <T> T require(T value, String name) {
        if (value == null) {
            throw new IllegalStateException(name + " requested before loader registration completed");
        }
        return value;
    }

    public static class_1299<CannonballEntity> cannonball() {
        return RegisteredContent.require(cannonball, "cannonball entity type");
    }

    public static class_1299<FieldCannonEntity> fieldCannon() {
        return RegisteredContent.require(fieldCannon, "field cannon entity type");
    }

    public static class_1299<StationaryCannonEntity> stationaryCannon() {
        return RegisteredContent.require(stationaryCannon, "stationary cannon entity type");
    }

    public static class_1299<CatapultStoneEntity> catapultStone() {
        return RegisteredContent.require(catapultStone, "catapult stone entity type");
    }

    public static class_1299<CatapultEntity> catapult() {
        return RegisteredContent.require(catapult, "catapult entity type");
    }

    public static class_1299<MedievalSoldierEntity> soldier() {
        return RegisteredContent.require(soldier, "soldier entity type");
    }

    public static class_2591<WheelBlockEntity> wheel() {
        return RegisteredContent.require(wheel, "wheel block entity type");
    }

    public static class_2591<SpecialPayloadBlockEntity> specialPayload() {
        return RegisteredContent.require(specialPayload, "special payload block entity type");
    }

    public static class_2591<PotionPayloadBlockEntity> potionPayload() {
        return RegisteredContent.require(potionPayload, "potion payload block entity type");
    }

    public static class_2591<DecorativeFortressCannonBlockEntity> decorativeFortressCannon() {
        return RegisteredContent.require(decorativeFortressCannon, "decorative fortress cannon block entity type");
    }

    public static class_2591<DecorativeFieldCannonBlockEntity> decorativeFieldCannon() {
        return RegisteredContent.require(decorativeFieldCannon, "decorative field cannon block entity type");
    }

    public static <T extends class_2586> class_2591<T> guillotine() {
        return RegisteredContent.require(guillotine, "guillotine block entity type");
    }

    public static <T extends class_2586> class_2591<T> stationaryCannonBlock() {
        return RegisteredContent.require(stationaryCannonBlock, "stationary cannon block entity type");
    }

    public static <T extends class_2586> class_2591<T> wickerBasket() {
        return RegisteredContent.require(wickerBasket, "wicker basket block entity type");
    }

    public static class_2586 createWickerBasket(class_2338 pos, class_2680 state) {
        return RegisteredContent.require(wickerBasketFactory, "wicker basket factory").apply(pos, state);
    }

    public static class_2586 createStationaryCannonBlock(class_2338 pos, class_2680 state) {
        return RegisteredContent.require(stationaryCannonBlockFactory, "stationary cannon block factory").apply(pos, state);
    }

    public static class_3917<SoldierInventoryScreenHandler> soldierInventoryScreen() {
        return RegisteredContent.require(soldierInventory, "soldier inventory screen type");
    }

    public static class_3917<WatchpostScreenHandler> watchpostScreen() {
        return RegisteredContent.require(watchpost, "watchpost screen type");
    }

    public static class_3917<MachineWorkshopScreenHandler> machineWorkshopScreen() {
        return RegisteredContent.require(machineWorkshop, "machine workshop screen type");
    }

    public static class_3917<StationaryCannonScreenHandler> stationaryCannonScreen() {
        return RegisteredContent.require(stationaryCannonScreen, "stationary cannon screen type");
    }

    public static class_3917<FieldCannonScreenHandler> fieldCannonScreen() {
        return RegisteredContent.require(fieldCannonScreen, "field cannon screen type");
    }

    public static class_3917<WickerBasketScreenHandler> wickerBasketScreen() {
        return RegisteredContent.require(wickerBasketScreen, "wicker basket screen type");
    }

    public static class_3852 executionerProfession() {
        return RegisteredContent.require(executionerProfession, "executioner profession");
    }

    public static class_1291 orderSetEffect(OrderStyle style) {
        if (style == null || style == OrderStyle.NONE) {
            throw new IllegalArgumentException("No display effect for NONE");
        }
        return RegisteredContent.require(orderSetEffects.get((Object)style), "order set effect " + style.method_15434());
    }

    static {
        orderSetEffects = new EnumMap<OrderStyle, class_1291>(OrderStyle.class);
    }
}

