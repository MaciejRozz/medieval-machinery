/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1799
 *  net.minecraft.class_2350
 *  net.minecraft.class_2769
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_5614$class_5615
 *  net.minecraft.class_7833
 *  net.minecraft.class_7923
 *  net.minecraft.class_811
 *  net.minecraft.class_827
 */
package com.medievalmachinery.mod.client.storage;

import com.medievalmachinery.mod.block.storage.WickerBasketBlock;
import com.medievalmachinery.mod.block.storage.WickerBasketBlockEntity;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_2350;
import net.minecraft.class_2769;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_5614;
import net.minecraft.class_7833;
import net.minecraft.class_7923;
import net.minecraft.class_811;
import net.minecraft.class_827;

@Environment(value=EnvType.CLIENT)
public final class WickerBasketRenderer
implements class_827<WickerBasketBlockEntity> {
    private static final double[][] SLOT_POSITIONS = new double[][]{{0.37, 0.36}, {0.5, 0.35}, {0.63, 0.37}, {0.4, 0.49}, {0.6, 0.5}, {0.37, 0.63}, {0.5, 0.64}, {0.63, 0.62}};
    private static final double[][][] HEAD_LAYER_LAYOUTS = new double[][][]{new double[0][], new double[][]{{0.5, 0.5}}, new double[][]{{0.39, 0.5}, {0.61, 0.5}}, new double[][]{{0.5, 0.38}, {0.39, 0.6}, {0.61, 0.6}}, new double[][]{{0.39, 0.39}, {0.61, 0.4}, {0.4, 0.61}, {0.61, 0.6}}};

    public WickerBasketRenderer(class_5614.class_5615 context) {
    }

    public WickerBasketRenderer() {
    }

    public void render(WickerBasketBlockEntity basket, float tickDelta, class_4587 matrices, class_4597 consumers, int light, int overlay) {
        class_2350 facing = (class_2350)basket.method_11010().method_11654((class_2769)WickerBasketBlock.FACING);
        matrices.method_22903();
        matrices.method_22904(0.5, 0.0, 0.5);
        matrices.method_22907(class_7833.field_40716.rotationDegrees(-facing.method_10144()));
        matrices.method_22904(-0.5, 0.0, -0.5);
        this.renderContents(basket, matrices, consumers, light);
        matrices.method_22909();
    }

    public void renderContents(WickerBasketBlockEntity basket, class_4587 matrices, class_4597 consumers, int light) {
        class_310 client = class_310.method_1551();
        int packedLight = light == 0 ? 0xF000F0 : light;
        ArrayList<class_1799> visibleHeads = new ArrayList<class_1799>(8);
        for (int slot = 0; slot < 8; ++slot) {
            class_1799 stack = basket.method_5438(slot);
            if (stack.method_7960()) continue;
            if (WickerBasketRenderer.isHeadItem(stack)) {
                int visible = Math.min(stack.method_7947(), 8 - visibleHeads.size());
                for (int i = 0; i < visible; ++i) {
                    visibleHeads.add(stack);
                }
                continue;
            }
            WickerBasketRenderer.renderNormalStack(client, basket, stack, slot, matrices, consumers, packedLight);
        }
        WickerBasketRenderer.renderHeads(client, basket, visibleHeads, matrices, consumers, packedLight);
    }

    private static void renderNormalStack(class_310 client, WickerBasketBlockEntity basket, class_1799 stack, int slot, class_4587 matrices, class_4597 consumers, int packedLight) {
        int layers = 1 + Math.min(5, (stack.method_7947() - 1) / 11);
        double baseX = SLOT_POSITIONS[slot][0];
        double baseZ = SLOT_POSITIONS[slot][1];
        for (int layer = 0; layer < layers; ++layer) {
            long seed = 9187L + (long)slot * 131L + (long)layer * 47L;
            double jitterX = (double)(seed * 17L % 13L - 6L) * 0.0025;
            double jitterZ = (double)(seed * 29L % 13L - 6L) * 0.0025;
            float yaw = seed * 37L % 360L;
            float tiltX = -82.0f + (float)(seed * 7L % 15L);
            float tiltZ = -7.0f + (float)(seed * 11L % 15L);
            matrices.method_22903();
            matrices.method_22904(baseX + jitterX, 0.18 + (double)layer * 0.073, baseZ + jitterZ);
            matrices.method_22907(class_7833.field_40716.rotationDegrees(yaw));
            matrices.method_22907(class_7833.field_40714.rotationDegrees(tiltX));
            matrices.method_22907(class_7833.field_40718.rotationDegrees(tiltZ));
            matrices.method_22905(0.82f, 0.82f, 0.82f);
            client.method_1480().method_23178(stack, class_811.field_4318, packedLight, class_4608.field_21444, matrices, consumers, basket.method_10997(), slot * 8 + layer);
            matrices.method_22909();
        }
    }

    private static void renderHeads(class_310 client, WickerBasketBlockEntity basket, List<class_1799> heads, class_4587 matrices, class_4597 consumers, int packedLight) {
        int bottomCount = Math.min(4, heads.size());
        int topCount = Math.max(0, heads.size() - bottomCount);
        for (int index = 0; index < heads.size(); ++index) {
            float yaw;
            boolean looksOut;
            boolean level = index >= bottomCount;
            int layerIndex = !level ? index : index - bottomCount;
            int layerCount = !level ? bottomCount : topCount;
            double[] base = HEAD_LAYER_LAYOUTS[layerCount][layerIndex];
            long seed = 31337L + (long)index * 9973L + (long)class_7923.field_41178.method_10206((Object)heads.get(index).method_7909()) * 131L;
            double jitterX = ((double)(seed * 17L & 0xFL) - 7.5) * 0.0032;
            double jitterZ = ((double)(seed * 29L & 0xFL) - 7.5) * 0.0032;
            boolean bl = looksOut = index % 3 != 1;
            if (looksOut) {
                double dx = base[0] - 0.5;
                double dz = base[1] - 0.5;
                yaw = (float)Math.toDegrees(Math.atan2(-dx, dz));
                yaw += (float)(seed * 37L % 17L) - 8.0f;
            } else {
                yaw = (float)(seed * 37L % 81L) - 40.0f;
            }
            float tiltX = looksOut ? (float)(seed * 11L % 9L) - 4.0f : (float)(seed * 11L % 19L) - 9.0f;
            float tiltZ = (float)(seed * 19L % 17L) - 8.0f;
            matrices.method_22903();
            matrices.method_22904(base[0] + jitterX, !level ? 0.175 : 0.35, base[1] + jitterZ);
            matrices.method_22907(class_7833.field_40716.rotationDegrees(yaw));
            matrices.method_22907(class_7833.field_40714.rotationDegrees(tiltX));
            matrices.method_22907(class_7833.field_40718.rotationDegrees(tiltZ));
            matrices.method_22905(0.7f, 0.7f, 0.7f);
            client.method_1480().method_23178(heads.get(index), class_811.field_4318, packedLight, class_4608.field_21444, matrices, consumers, basket.method_10997(), 700 + index);
            matrices.method_22909();
        }
    }

    private static boolean isHeadItem(class_1799 stack) {
        String path = class_7923.field_41178.method_10221((Object)stack.method_7909()).method_12832();
        return path.contains("head") || path.contains("skull");
    }
}

