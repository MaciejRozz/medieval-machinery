/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_124
 *  net.minecraft.class_1304
 *  net.minecraft.class_1320
 *  net.minecraft.class_1322
 *  net.minecraft.class_1322$class_1323
 *  net.minecraft.class_1738
 *  net.minecraft.class_1738$class_8051
 *  net.minecraft.class_1741
 *  net.minecraft.class_1792
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_1836
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2487
 *  net.minecraft.class_2561
 *  net.minecraft.class_5134
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoItem
 *  software.bernie.geckolib.core.animatable.GeoAnimatable
 *  software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.core.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.medievalmachinery.mod.item;

import com.medievalmachinery.mod.item.OrderSigilItem;
import com.medievalmachinery.mod.npc.armor.ArmorStyle;
import com.medievalmachinery.mod.npc.armor.ArmorTier;
import com.medievalmachinery.mod.npc.core.NpcRank;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.class_124;
import net.minecraft.class_1304;
import net.minecraft.class_1320;
import net.minecraft.class_1322;
import net.minecraft.class_1738;
import net.minecraft.class_1741;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1836;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2487;
import net.minecraft.class_2561;
import net.minecraft.class_5134;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class MedievalArmorItem
extends class_1738
implements GeoItem {
    private static final String ROOT = "MedievalMachineryArmor";
    private static final String KEY_KINGDOM = "KingdomId";
    private static final String KEY_ORDER = "OrderVariant";
    private static final String KEY_KINGDOM_NAME = "KingdomName";
    private static final String KEY_TIER = "ArmorTier";
    private static final String KEY_STYLE = "ArmorStyle";
    private static final String KEY_ATTRIBUTE_TIER = "AppliedAttributeTier";
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer((GeoItem)this);
    private final ArmorTier armorTier;

    public MedievalArmorItem(class_1741 material, class_1738.class_8051 type, ArmorTier armorTier, class_1792.class_1793 settings) {
        super(material, type, settings);
        this.armorTier = armorTier;
    }

    public ArmorTier getArmorTier() {
        return this.armorTier;
    }

    public boolean isGeneralFamily() {
        return this.armorTier.isGeneralFamily();
    }

    public static class_1799 createFromSigil(MedievalArmorItem item, class_1799 sigil) {
        class_1792 class_17922 = sigil.method_7909();
        if (!(class_17922 instanceof OrderSigilItem)) {
            return class_1799.field_8037;
        }
        OrderSigilItem sigilItem = (OrderSigilItem)class_17922;
        class_1799 result = new class_1799((class_1935)item);
        class_2487 data = result.method_7911(ROOT);
        if (OrderSigilItem.isStamped(sigil)) {
            UUID kingdomId = OrderSigilItem.getKingdomId(sigil);
            OrderStyle order = OrderSigilItem.getStampedOrder(sigil);
            if (kingdomId == null || order == OrderStyle.NONE) {
                return class_1799.field_8037;
            }
            data.method_25927(KEY_KINGDOM, kingdomId);
            data.method_10582(KEY_ORDER, order.method_15434());
            data.method_10582(KEY_KINGDOM_NAME, OrderSigilItem.getKingdomNameSnapshot(sigil));
        } else {
            data.method_10582(KEY_ORDER, sigilItem.getOrderStyle().method_15434());
            data.method_10582(KEY_KINGDOM_NAME, "");
        }
        data.method_10582(KEY_TIER, item.armorTier.serializedName());
        data.method_10582(KEY_STYLE, item.armorTier.isGeneralFamily() ? ArmorStyle.GENERAL.serializedName() : ArmorStyle.PLATE.serializedName());
        MedievalArmorItem.refreshInventoryModel(result);
        return result;
    }

    public static boolean isMedievalArmor(class_1799 stack) {
        return stack.method_7909() instanceof MedievalArmorItem;
    }

    public static OrderStyle getOrderStyle(class_1799 stack) {
        if (!(stack.method_7909() instanceof MedievalArmorItem)) {
            return OrderStyle.NONE;
        }
        class_2487 data = stack.method_7941(ROOT);
        if (data == null) {
            return OrderStyle.NONE;
        }
        return OrderStyle.fromSerializedName(data.method_10558(KEY_ORDER));
    }

    @Nullable
    public static UUID getKingdomId(class_1799 stack) {
        class_2487 data = stack.method_7941(ROOT);
        return data != null && data.method_25928(KEY_KINGDOM) ? data.method_25926(KEY_KINGDOM) : null;
    }

    public static String getKingdomName(class_1799 stack) {
        class_2487 data = stack.method_7941(ROOT);
        return data == null ? "" : data.method_10558(KEY_KINGDOM_NAME);
    }

    public static ArmorTier getTier(class_1799 stack) {
        class_1792 class_17922 = stack.method_7909();
        if (!(class_17922 instanceof MedievalArmorItem)) {
            return ArmorTier.ORDER;
        }
        MedievalArmorItem armor = (MedievalArmorItem)class_17922;
        class_2487 data = stack.method_7941(ROOT);
        if (data != null && data.method_10545(KEY_TIER)) {
            return ArmorTier.fromSerializedName(data.method_10558(KEY_TIER));
        }
        return armor.getArmorTier();
    }

    public static class_1799 upgradeToNetherite(class_1799 stack) {
        if (!(stack.method_7909() instanceof MedievalArmorItem)) {
            return class_1799.field_8037;
        }
        ArmorTier current = MedievalArmorItem.getTier(stack);
        if (current.isNetheriteUpgraded()) {
            return class_1799.field_8037;
        }
        ArmorTier upgraded = current.isGeneralFamily() ? ArmorTier.NETHERITE_GENERAL : ArmorTier.NETHERITE_ORDER;
        class_1799 result = stack.method_7972();
        result.method_7939(1);
        result.method_7911(ROOT).method_10582(KEY_TIER, upgraded.serializedName());
        MedievalArmorItem.applyTierAttributes(result);
        MedievalArmorItem.refreshInventoryModel(result);
        return result;
    }

    private static void applyTierAttributes(class_1799 stack) {
        class_1792 class_17922 = stack.method_7909();
        if (!(class_17922 instanceof MedievalArmorItem)) {
            return;
        }
        MedievalArmorItem armor = (MedievalArmorItem)class_17922;
        ArmorTier tier = MedievalArmorItem.getTier(stack);
        if (!tier.isNetheriteUpgraded()) {
            return;
        }
        class_2487 root = stack.method_7911(ROOT);
        if (tier.serializedName().equals(root.method_10558(KEY_ATTRIBUTE_TIER)) && stack.method_7985() && stack.method_7969().method_10545("AttributeModifiers")) {
            return;
        }
        stack.method_7948().method_10551("AttributeModifiers");
        boolean general = tier == ArmorTier.NETHERITE_GENERAL;
        double protection = switch (armor.method_48398()) {
            default -> throw new IncompatibleClassChangeError();
            case class_1738.class_8051.field_41934, class_1738.class_8051.field_41937 -> {
                if (general) {
                    yield 4.5;
                }
                yield 3.5;
            }
            case class_1738.class_8051.field_41936 -> {
                if (general) {
                    yield 7.5;
                }
                yield 6.5;
            }
            case class_1738.class_8051.field_41935 -> general ? 9.5 : 8.5;
        };
        double toughness = general ? 4.0 : 3.5;
        double knockback = general ? 0.14 : 0.12;
        class_1304 slot = switch (armor.method_48398()) {
            default -> throw new IncompatibleClassChangeError();
            case class_1738.class_8051.field_41934 -> class_1304.field_6169;
            case class_1738.class_8051.field_41935 -> class_1304.field_6174;
            case class_1738.class_8051.field_41936 -> class_1304.field_6172;
            case class_1738.class_8051.field_41937 -> class_1304.field_6166;
        };
        MedievalArmorItem.addTierModifier(stack, class_5134.field_23724, "armor", protection, slot);
        MedievalArmorItem.addTierModifier(stack, class_5134.field_23725, "toughness", toughness, slot);
        MedievalArmorItem.addTierModifier(stack, class_5134.field_23718, "knockback", knockback, slot);
        root.method_10582(KEY_ATTRIBUTE_TIER, tier.serializedName());
    }

    private static void addTierModifier(class_1799 stack, class_1320 attribute, String kind, double value, class_1304 slot) {
        String seed = "medieval-machinery:" + MedievalArmorItem.getTier(stack).serializedName() + ":" + ((MedievalArmorItem)stack.method_7909()).method_48398().method_48400() + ":" + kind;
        UUID id = UUID.nameUUIDFromBytes(seed.getBytes(StandardCharsets.UTF_8));
        stack.method_7916(attribute, new class_1322(id, "Medieval Machinery armor " + kind, value, class_1322.class_1323.field_6328), slot);
    }

    private static int inventoryModelId(class_1799 stack) {
        OrderStyle order = MedievalArmorItem.getOrderStyle(stack);
        if (order == null || order == OrderStyle.NONE) {
            order = OrderStyle.IRON;
        }
        int orderIndex = switch (order) {
            default -> throw new IncompatibleClassChangeError();
            case OrderStyle.GOLD -> 0;
            case OrderStyle.COPPER -> 1;
            case OrderStyle.IRON -> 2;
            case OrderStyle.DIAMOND -> 3;
            case OrderStyle.REDSTONE -> 4;
            case OrderStyle.AMETHYST -> 5;
            case OrderStyle.LAPIS -> 6;
            case OrderStyle.EMERALD -> 7;
            case OrderStyle.QUARTZ -> 8;
            case OrderStyle.COAL -> 9;
            case OrderStyle.NETHERITE -> 10;
            case OrderStyle.NONE -> 2;
        };
        return 100 + orderIndex * 2 + (MedievalArmorItem.getTier(stack).isNetheriteUpgraded() ? 1 : 0);
    }

    private static void refreshInventoryModel(class_1799 stack) {
        if (!(stack.method_7909() instanceof MedievalArmorItem)) {
            return;
        }
        stack.method_7948().method_10569("CustomModelData", MedievalArmorItem.inventoryModelId(stack));
    }

    public static ArmorStyle getArmorStyle(class_1799 stack) {
        class_2487 data = stack.method_7941(ROOT);
        if (data == null || !data.method_10545(KEY_STYLE)) {
            return MedievalArmorItem.getTier(stack).isGeneralFamily() ? ArmorStyle.GENERAL : ArmorStyle.PLATE;
        }
        return ArmorStyle.fromSerializedName(data.method_10558(KEY_STYLE));
    }

    public static void setArmorStyle(class_1799 stack, ArmorStyle style) {
        if (!(stack.method_7909() instanceof MedievalArmorItem)) {
            return;
        }
        stack.method_7911(ROOT).method_10582(KEY_STYLE, (style == null ? ArmorStyle.HEAVY_BRIGANDINE : style).serializedName());
        MedievalArmorItem.refreshInventoryModel(stack);
    }

    public static boolean canNpcEquip(MedievalSoldierEntity soldier, class_1799 stack) {
        class_1792 class_17922 = stack.method_7909();
        if (!(class_17922 instanceof MedievalArmorItem)) {
            return true;
        }
        MedievalArmorItem armor = (MedievalArmorItem)class_17922;
        return !armor.isGeneralFamily() || soldier.getNpcRank() == NpcRank.GENERAL;
    }

    public class_2561 method_7864(class_1799 stack) {
        class_2561 baseName;
        ArmorTier tier = MedievalArmorItem.getTier(stack);
        if (tier.isNetheriteUpgraded()) {
            String family = tier.isGeneralFamily() ? "netherite_general_" : "netherite_order_";
            String piece = switch (this.method_48398()) {
                default -> throw new IncompatibleClassChangeError();
                case class_1738.class_8051.field_41934 -> "helmet";
                case class_1738.class_8051.field_41935 -> "chestplate";
                case class_1738.class_8051.field_41936 -> "leggings";
                case class_1738.class_8051.field_41937 -> "boots";
            };
            baseName = class_2561.method_43471((String)("item.medieval-machinery." + family + piece));
        } else {
            baseName = super.method_7864(stack);
        }
        if (tier.isGeneralFamily()) {
            return baseName;
        }
        ArmorStyle style = MedievalArmorItem.getArmorStyle(stack);
        return class_2561.method_43469((String)"item.medieval-machinery.armor.versioned_name", (Object[])new Object[]{baseName, class_2561.method_43471((String)("armor_style.medieval-machinery." + style.serializedName()))});
    }

    public void createRenderer(Consumer<Object> consumer) {
        try {
            Class<?> providerClass = Class.forName("com.medievalmachinery.mod.client.npc.MedievalArmorRenderProvider");
            consumer.accept(providerClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        }
        catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to create Medieval Machinery armor renderer", exception);
        }
    }

    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public void method_7851(class_1799 stack, @Nullable class_1937 world, List<class_2561> tooltip, class_1836 context) {
        String kingdom;
        super.method_7851(stack, world, tooltip, context);
        OrderStyle order = MedievalArmorItem.getOrderStyle(stack);
        if (order != OrderStyle.NONE) {
            tooltip.add((class_2561)class_2561.method_43469((String)"tooltip.medieval-machinery.armor.order", (Object[])new Object[]{class_2561.method_43471((String)("order.medieval-machinery." + order.method_15434()))}).method_27692(class_124.field_1080));
        }
        if (!(kingdom = MedievalArmorItem.getKingdomName(stack)).isBlank()) {
            tooltip.add((class_2561)class_2561.method_43469((String)"tooltip.medieval-machinery.armor.kingdom", (Object[])new Object[]{kingdom}).method_27692(class_124.field_1065));
        }
        if (this.isGeneralFamily()) {
            tooltip.add((class_2561)class_2561.method_43471((String)"tooltip.medieval-machinery.armor.general_npc_rule").method_27692(class_124.field_1062));
        }
    }
}

