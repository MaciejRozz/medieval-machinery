/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1087
 *  net.minecraft.class_1799
 *  net.minecraft.class_1937
 *  net.minecraft.class_2350
 *  net.minecraft.class_310
 *  net.minecraft.class_5819
 *  net.minecraft.class_777
 */
package com.medievalmachinery.mod.client.catapult;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1087;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2350;
import net.minecraft.class_310;
import net.minecraft.class_5819;
import net.minecraft.class_777;

@Environment(value=EnvType.CLIENT)
final class PayloadModelMetrics {
    private PayloadModelMetrics() {
    }

    static Bounds bounds(class_1799 stack) {
        class_310 client = class_310.method_1551();
        class_1087 model = client.method_1480().method_4019(stack, (class_1937)client.field_1687, null, 0);
        if (model == null || model.method_4713()) {
            return new Bounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f).inItemRendererSpace();
        }
        MutableBounds bounds = new MutableBounds();
        class_5819 random = class_5819.method_43049((long)42L);
        PayloadModelMetrics.scan(model.method_4707(null, null, random), bounds);
        for (class_2350 direction : class_2350.values()) {
            random.method_43052(42L);
            PayloadModelMetrics.scan(model.method_4707(null, direction, random), bounds);
        }
        Bounds raw = bounds.isValid() ? new Bounds(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ) : new Bounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return raw.inItemRendererSpace();
    }

    private static void scan(List<class_777> quads, MutableBounds bounds) {
        for (class_777 quad : quads) {
            int stride;
            int[] data = quad.method_3357();
            if (data.length < 12 || (stride = data.length / 4) < 3) continue;
            for (int vertex = 0; vertex < 4; ++vertex) {
                int offset = vertex * stride;
                float x = Float.intBitsToFloat(data[offset]);
                float y = Float.intBitsToFloat(data[offset + 1]);
                float z = Float.intBitsToFloat(data[offset + 2]);
                bounds.include(x, y, z);
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    record Bounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        float bottomCenterX() {
            return (this.minX + this.maxX) * 0.5f;
        }

        float bottomCenterZ() {
            return (this.minZ + this.maxZ) * 0.5f;
        }

        Bounds inItemRendererSpace() {
            return new Bounds(this.minX - 0.5f, this.minY - 0.5f, this.minZ - 0.5f, this.maxX - 0.5f, this.maxY - 0.5f, this.maxZ - 0.5f);
        }
    }

    @Environment(value=EnvType.CLIENT)
    private static final class MutableBounds {
        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float minZ = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        float maxZ = Float.NEGATIVE_INFINITY;

        private MutableBounds() {
        }

        void include(float x, float y, float z) {
            this.minX = Math.min(this.minX, x);
            this.minY = Math.min(this.minY, y);
            this.minZ = Math.min(this.minZ, z);
            this.maxX = Math.max(this.maxX, x);
            this.maxY = Math.max(this.maxY, y);
            this.maxZ = Math.max(this.maxZ, z);
        }

        boolean isValid() {
            return Float.isFinite(this.minX) && Float.isFinite(this.minY) && Float.isFinite(this.minZ) && Float.isFinite(this.maxX) && Float.isFinite(this.maxY) && Float.isFinite(this.maxZ);
        }
    }
}

