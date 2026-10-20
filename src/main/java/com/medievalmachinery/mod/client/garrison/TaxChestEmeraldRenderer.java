/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_243
 *  net.minecraft.class_2595
 *  net.minecraft.class_2741
 *  net.minecraft.class_2769
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_7833
 *  net.minecraft.class_811
 */
package com.medievalmachinery.mod.client.garrison;

import com.medievalmachinery.mod.client.garrison.TaxChestEditorClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2595;
import net.minecraft.class_2741;
import net.minecraft.class_2769;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_7833;
import net.minecraft.class_811;

@Environment(value=EnvType.CLIENT)
public final class TaxChestEmeraldRenderer {
    private static final class_1799 EMERALD = new class_1799((class_1935)class_1802.field_8687);

    private TaxChestEmeraldRenderer() {
    }

    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            class_310 client = class_310.method_1551();
            if (client.field_1687 == null || client.field_1724 == null || context.matrixStack() == null || context.consumers() == null) {
                return;
            }
            class_243 camera = context.camera().method_19326();
            for (class_2338 pos : TaxChestEditorClient.getTaxChestPositions()) {
                if (client.field_1724.method_5649((double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5) > 16384.0 || !(client.field_1687.method_8321(pos) instanceof class_2595)) continue;
                TaxChestEmeraldRenderer.renderChestMarks(client, context.matrixStack(), context.consumers(), camera, pos, (class_2595)client.field_1687.method_8321(pos));
            }
        });
    }

    private static void renderChestMarks(class_310 client, class_4587 matrices, class_4597 consumers, class_243 camera, class_2338 pos, class_2595 chest) {
        matrices.method_22903();
        matrices.method_22904((double)pos.method_10263() - camera.field_1352, (double)pos.method_10264() - camera.field_1351, (double)pos.method_10260() - camera.field_1350);
        TaxChestEmeraldRenderer.renderMark(client, matrices, consumers, 0.5, 0.31, 0.035, 0.0f, 0.0f);
        TaxChestEmeraldRenderer.renderMark(client, matrices, consumers, 0.5, 0.31, 0.965, 180.0f, 0.0f);
        TaxChestEmeraldRenderer.renderMark(client, matrices, consumers, 0.035, 0.31, 0.5, 90.0f, 0.0f);
        TaxChestEmeraldRenderer.renderMark(client, matrices, consumers, 0.965, 0.31, 0.5, -90.0f, 0.0f);
        class_2350 facing = chest.method_11010().method_28498((class_2769)class_2741.field_12481) ? (class_2350)chest.method_11010().method_11654((class_2769)class_2741.field_12481) : class_2350.field_11043;
        float progress = chest.method_11274(client.method_1488());
        progress = 1.0f - progress;
        progress = 1.0f - progress * progress * progress;
        double angle = (double)progress * 1.5707963267948966;
        double fx = facing.method_10148();
        double fz = facing.method_10165();
        double radius = 0.42;
        double hingeX = 0.5 - fx * radius;
        double hingeZ = 0.5 - fz * radius;
        double along = radius * Math.cos(angle);
        double topX = hingeX + fx * along;
        double topZ = hingeZ + fz * along;
        double topY = 0.895 + radius * Math.sin(angle);
        float topYaw = switch (facing) {
            case class_2350.field_11043 -> 180.0f;
            case class_2350.field_11035 -> 0.0f;
            case class_2350.field_11039 -> 90.0f;
            case class_2350.field_11034 -> -90.0f;
            default -> 0.0f;
        };
        TaxChestEmeraldRenderer.renderMark(client, matrices, consumers, topX, topY, topZ, topYaw, 90.0f + (float)Math.toDegrees(angle));
        matrices.method_22909();
    }

    private static void renderMark(class_310 client, class_4587 matrices, class_4597 consumers, double x, double y, double z, float yaw, float pitch) {
        matrices.method_22903();
        matrices.method_22904(x, y, z);
        matrices.method_22907(class_7833.field_40716.rotationDegrees(yaw));
        matrices.method_22907(class_7833.field_40714.rotationDegrees(pitch));
        matrices.method_22905(0.34f, 0.34f, 0.34f);
        client.method_1480().method_23178(EMERALD, class_811.field_4319, 0xF000F0, class_4608.field_21444, matrices, consumers, (class_1937)client.field_1687, 0);
        matrices.method_22909();
    }
}

