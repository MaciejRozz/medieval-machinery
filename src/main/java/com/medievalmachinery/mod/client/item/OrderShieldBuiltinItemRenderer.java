/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1921
 *  net.minecraft.class_1935
 *  net.minecraft.class_2960
 *  net.minecraft.class_4587
 *  net.minecraft.class_4587$class_4665
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_811
 *  org.joml.Matrix3f
 *  org.joml.Matrix4f
 */
package com.medievalmachinery.mod.client.item;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.item.OrderShieldItem;
import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.registry.ModItems;
import java.util.EnumMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_1935;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_811;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(value=EnvType.CLIENT)
public final class OrderShieldBuiltinItemRenderer {
    private static final Map<OrderStyle, class_2960> BASE_TEXTURES = new EnumMap<OrderStyle, class_2960>(OrderStyle.class);
    private static final Map<OrderStyle, class_2960> SYMBOL_TEXTURES = new EnumMap<OrderStyle, class_2960>(OrderStyle.class);

    private OrderShieldBuiltinItemRenderer() {
    }

    public static void register() {
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_GOLD);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_COPPER);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_IRON);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_DIAMOND);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_REDSTONE);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_AMETHYST);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_LAPIS);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_EMERALD);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_QUARTZ);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_COAL);
        OrderShieldBuiltinItemRenderer.register(ModItems.ORDER_SHIELD_NETHERITE);
    }

    private static void register(class_1792 item) {
        BuiltinItemRendererRegistry.INSTANCE.register((class_1935)item, OrderShieldBuiltinItemRenderer::render);
    }

    private static void render(class_1799 stack, class_811 mode, class_4587 matrices, class_4597 consumers, int light, int overlay) {
        class_1792 class_17922 = stack.method_7909();
        if (!(class_17922 instanceof OrderShieldItem)) {
            return;
        }
        OrderShieldItem shield = (OrderShieldItem)class_17922;
        class_2960 base = BASE_TEXTURES.get((Object)shield.getOrderStyle());
        class_2960 symbol = SYMBOL_TEXTURES.get((Object)shield.getOrderStyle());
        if (base == null || symbol == null) {
            return;
        }
        matrices.method_22903();
        if (mode == class_811.field_4317) {
            matrices.method_46416(0.0f, 0.02f, 0.0f);
            matrices.method_22905(0.78f, 0.78f, 0.78f);
        } else if (mode == class_811.field_4323 || mode == class_811.field_4320) {
            matrices.method_46416(0.0f, 0.0f, 0.1f);
        }
        class_4588 vc = consumers.getBuffer(class_1921.method_23578((class_2960)base));
        OrderShieldBuiltinItemRenderer.cuboid(vc, matrices, light, 0.0f, 0.0f, 0.0f, 14.0f, 20.0f, 1.0f, 2.0f, 2.0f, 16.0f, 22.0f, 2.0f, 2.0f, 16.0f, 22.0f, 18.0f, 2.0f, 17.0f, 22.0f, 17.0f, 2.0f, 18.0f, 22.0f, 2.0f, 2.0f, 16.0f, 3.0f, 2.0f, 1.0f, 16.0f, 2.0f);
        OrderShieldBuiltinItemRenderer.cuboid(vc, matrices, light, 1.0f, -1.0f, 0.0f, 13.0f, 0.0f, 1.0f, 3.0f, 22.0f, 15.0f, 23.0f, 3.0f, 22.0f, 15.0f, 23.0f, 3.0f, 22.0f, 4.0f, 23.0f, 14.0f, 22.0f, 15.0f, 23.0f, 3.0f, 22.0f, 15.0f, 23.0f, 3.0f, 22.0f, 15.0f, 23.0f);
        OrderShieldBuiltinItemRenderer.cuboid(vc, matrices, light, 2.0f, -2.0f, 0.0f, 12.0f, -1.0f, 1.0f, 4.0f, 23.0f, 14.0f, 24.0f, 4.0f, 23.0f, 14.0f, 24.0f, 4.0f, 23.0f, 5.0f, 24.0f, 13.0f, 23.0f, 14.0f, 24.0f, 5.0f, 23.0f, 14.0f, 24.0f, 4.0f, 23.0f, 14.0f, 24.0f);
        OrderShieldBuiltinItemRenderer.cuboid(vc, matrices, light, 3.0f, -3.0f, 0.0f, 11.0f, -2.0f, 1.0f, 5.0f, 24.0f, 13.0f, 25.0f, 5.0f, 24.0f, 13.0f, 25.0f, 5.0f, 24.0f, 6.0f, 25.0f, 12.0f, 24.0f, 13.0f, 25.0f, 5.0f, 24.0f, 13.0f, 25.0f, 5.0f, 24.0f, 13.0f, 25.0f);
        OrderShieldBuiltinItemRenderer.cuboid(vc, matrices, light, 4.0f, -4.0f, 0.0f, 10.0f, -3.0f, 1.0f, 6.0f, 25.0f, 12.0f, 26.0f, 6.0f, 25.0f, 12.0f, 26.0f, 6.0f, 25.0f, 7.0f, 26.0f, 11.0f, 25.0f, 12.0f, 26.0f, 6.0f, 25.0f, 12.0f, 26.0f, 6.0f, 25.0f, 12.0f, 26.0f);
        OrderShieldBuiltinItemRenderer.cuboid(vc, matrices, light, 5.0f, -5.0f, 0.0f, 9.0f, -4.0f, 1.0f, 7.0f, 26.0f, 11.0f, 27.0f, 7.0f, 26.0f, 11.0f, 27.0f, 7.0f, 26.0f, 8.0f, 27.0f, 10.0f, 26.0f, 11.0f, 27.0f, 7.0f, 26.0f, 11.0f, 27.0f, 7.0f, 26.0f, 11.0f, 27.0f);
        OrderShieldBuiltinItemRenderer.cuboid(vc, matrices, light, 6.0f, -6.0f, 0.0f, 8.0f, -5.0f, 1.0f, 8.0f, 27.0f, 10.0f, 28.0f, 8.0f, 27.0f, 10.0f, 28.0f, 8.0f, 27.0f, 9.0f, 28.0f, 9.0f, 27.0f, 10.0f, 28.0f, 8.0f, 27.0f, 10.0f, 28.0f, 8.0f, 27.0f, 10.0f, 28.0f);
        OrderShieldBuiltinItemRenderer.cuboid(vc, matrices, light, 6.0f, 6.0f, 1.0f, 8.0f, 12.0f, 7.0f, 29.0f, 8.0f, 31.0f, 13.0f, 23.0f, 8.0f, 25.0f, 13.0f, 25.0f, 2.0f, 20.0f, 7.0f, 20.0f, 2.0f, 25.0f, 7.0f, 20.0f, 8.0f, 22.0f, 13.0f, 26.0f, 8.0f, 28.0f, 13.0f);
        class_4588 emblem = consumers.getBuffer(class_1921.method_23578((class_2960)symbol));
        OrderShieldBuiltinItemRenderer.face(emblem, matrices, light, OrderShieldBuiltinItemRenderer.px(3.0f), OrderShieldBuiltinItemRenderer.py(4.0f), 0.00625f, OrderShieldBuiltinItemRenderer.px(11.0f), OrderShieldBuiltinItemRenderer.py(15.0f), 0.00625f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f);
        matrices.method_22909();
    }

    private static float px(float x) {
        return (x - 7.0f) / 16.0f;
    }

    private static float py(float y) {
        return (y - 7.0f) / 16.0f;
    }

    private static void cuboid(class_4588 vc, class_4587 ms, int light, float x0, float y0, float z0, float x1, float y1, float z1, float nu0, float nv0, float nu1, float nv1, float su0, float sv0, float su1, float sv1, float eu0, float ev0, float eu1, float ev1, float wu0, float wv0, float wu1, float wv1, float uu0, float uv0, float uu1, float uv1, float du0, float dv0, float du1, float dv1) {
        float ax = OrderShieldBuiltinItemRenderer.px(x0);
        float bx = OrderShieldBuiltinItemRenderer.px(x1);
        float ay = OrderShieldBuiltinItemRenderer.py(y0);
        float by = OrderShieldBuiltinItemRenderer.py(y1);
        float az = -z1 / 16.0f;
        float bz = -z0 / 16.0f;
        OrderShieldBuiltinItemRenderer.face(vc, ms, light, ax, ay, az, bx, by, az, nu0 / 32.0f, nv0 / 48.0f, nu1 / 32.0f, nv1 / 48.0f, 0.0f, 0.0f, -1.0f);
        OrderShieldBuiltinItemRenderer.face(vc, ms, light, bx, ay, bz, ax, by, bz, su0 / 32.0f, sv0 / 48.0f, su1 / 32.0f, sv1 / 48.0f, 0.0f, 0.0f, 1.0f);
        OrderShieldBuiltinItemRenderer.sideFace(vc, ms, light, bx, ay, az, bx, by, bz, eu0 / 32.0f, ev0 / 48.0f, eu1 / 32.0f, ev1 / 48.0f, 1.0f, 0.0f, 0.0f);
        OrderShieldBuiltinItemRenderer.sideFace(vc, ms, light, ax, ay, bz, ax, by, az, wu0 / 32.0f, wv0 / 48.0f, wu1 / 32.0f, wv1 / 48.0f, -1.0f, 0.0f, 0.0f);
        OrderShieldBuiltinItemRenderer.horizontalFace(vc, ms, light, ax, ay, bz, bx, ay, az, du0 / 32.0f, dv0 / 48.0f, du1 / 32.0f, dv1 / 48.0f, 0.0f, -1.0f, 0.0f);
        OrderShieldBuiltinItemRenderer.horizontalFace(vc, ms, light, ax, by, az, bx, by, bz, uu0 / 32.0f, uv0 / 48.0f, uu1 / 32.0f, uv1 / 48.0f, 0.0f, 1.0f, 0.0f);
    }

    private static void face(class_4588 vc, class_4587 ms, int light, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, float nx, float ny, float nz) {
        class_4587.class_4665 e = ms.method_23760();
        Matrix4f p = e.method_23761();
        Matrix3f n = e.method_23762();
        OrderShieldBuiltinItemRenderer.vertex(vc, p, n, x0, y0, z0, u0, v1, light, nx, ny, nz);
        OrderShieldBuiltinItemRenderer.vertex(vc, p, n, x0, y1, z0, u0, v0, light, nx, ny, nz);
        OrderShieldBuiltinItemRenderer.vertex(vc, p, n, x1, y1, z1, u1, v0, light, nx, ny, nz);
        OrderShieldBuiltinItemRenderer.vertex(vc, p, n, x1, y0, z1, u1, v1, light, nx, ny, nz);
    }

    private static void sideFace(class_4588 vc, class_4587 ms, int light, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, float nx, float ny, float nz) {
        OrderShieldBuiltinItemRenderer.face(vc, ms, light, x0, y0, z0, x1, y1, z1, u0, v0, u1, v1, nx, ny, nz);
    }

    private static void horizontalFace(class_4588 vc, class_4587 ms, int light, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, float nx, float ny, float nz) {
        class_4587.class_4665 e = ms.method_23760();
        Matrix4f p = e.method_23761();
        Matrix3f n = e.method_23762();
        OrderShieldBuiltinItemRenderer.vertex(vc, p, n, x0, y0, z0, u0, v0, light, nx, ny, nz);
        OrderShieldBuiltinItemRenderer.vertex(vc, p, n, x1, y0, z0, u1, v0, light, nx, ny, nz);
        OrderShieldBuiltinItemRenderer.vertex(vc, p, n, x1, y1, z1, u1, v1, light, nx, ny, nz);
        OrderShieldBuiltinItemRenderer.vertex(vc, p, n, x0, y1, z1, u0, v1, light, nx, ny, nz);
    }

    private static void vertex(class_4588 vc, Matrix4f p, Matrix3f n, float x, float y, float z, float u, float v, int light, float nx, float ny, float nz) {
        vc.method_22918(p, x, y, z).method_1336(255, 255, 255, 255).method_22913(u, v).method_22922(class_4608.field_21444).method_22916(light).method_23763(n, nx, ny, nz).method_1344();
    }

    static {
        for (OrderStyle style : OrderStyle.values()) {
            if (style == OrderStyle.NONE) continue;
            String id = style.method_15434();
            BASE_TEXTURES.put(style, MedievalMachinery.id("textures/entity/shield/base/order_shield_" + id + ".png"));
            SYMBOL_TEXTURES.put(style, MedievalMachinery.id("textures/entity/shield/symbol/order_shield_symbol_" + id + ".png"));
        }
    }
}

