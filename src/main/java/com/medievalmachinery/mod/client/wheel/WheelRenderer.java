/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2350
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_5614$class_5615
 *  net.minecraft.class_7833
 *  net.minecraft.class_827
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoBlockRenderer
 */
package com.medievalmachinery.mod.client.wheel;

import com.medievalmachinery.mod.block.WheelBlock;
import com.medievalmachinery.mod.block.WheelBlockEntity;
import com.medievalmachinery.mod.block.WheelPlacementMode;
import com.medievalmachinery.mod.client.render.RenderTransforms;
import com.medievalmachinery.mod.client.wheel.WheelModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2350;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_5614;
import net.minecraft.class_7833;
import net.minecraft.class_827;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@Environment(value=EnvType.CLIENT)
public final class WheelRenderer
implements class_827<WheelBlockEntity> {
    private final GeoBlockRenderer<WheelBlockEntity> delegate = new GeoBlockRenderer((GeoModel)new WheelModel());
    private final boolean applyPlacementTransform;

    public WheelRenderer(class_5614.class_5615 context) {
        this(true);
    }

    public WheelRenderer() {
        this(false);
    }

    private WheelRenderer(boolean applyPlacementTransform) {
        this.applyPlacementTransform = applyPlacementTransform;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void render(WheelBlockEntity wheel, float tickDelta, class_4587 matrices, class_4597 consumers, int light, int overlay) {
        matrices.method_22903();
        try {
            if (this.applyPlacementTransform) {
                class_2680 state = wheel.method_11010();
                WheelPlacementMode placement = (WheelPlacementMode)((Object)state.method_11654(WheelBlock.PLACEMENT_MODE));
                class_2350 facing = (class_2350)state.method_11654((class_2769)WheelBlock.FACING);
                RenderTransforms.Transform transform = placement == WheelPlacementMode.FLOOR ? RenderTransforms.wheelGround() : WheelRenderer.wallTransform(facing);
                matrices.method_22904(transform.translationX, transform.translationY, transform.translationZ);
                WheelRenderer.applyTransformRotation(matrices, transform, placement == WheelPlacementMode.WALL && (facing == class_2350.field_11034 || facing == class_2350.field_11039));
                matrices.method_22905(transform.scale, transform.scale, transform.scale);
            }
            this.delegate.method_3569((class_2586)wheel, tickDelta, matrices, consumers, light, overlay);
        }
        finally {
            matrices.method_22909();
        }
    }

    private static RenderTransforms.Transform wallTransform(class_2350 facing) {
        return switch (facing) {
            case class_2350.field_11039 -> RenderTransforms.wheelWallWest();
            case class_2350.field_11043 -> RenderTransforms.wheelWallNorth();
            case class_2350.field_11034 -> RenderTransforms.wheelWallEast();
            case class_2350.field_11035 -> RenderTransforms.wheelWallSouth();
            default -> RenderTransforms.wheelWallWest();
        };
    }

    private static void applyTransformRotation(class_4587 matrices, RenderTransforms.Transform transform, boolean yRotationHandledByWheelBone) {
        matrices.method_22907(class_7833.field_40714.rotationDegrees(transform.rotationX));
        if (!yRotationHandledByWheelBone) {
            matrices.method_22907(class_7833.field_40716.rotationDegrees(transform.rotationY));
        }
        matrices.method_22907(class_7833.field_40718.rotationDegrees(transform.rotationZ));
    }

    public boolean rendersOutsideBoundingBox(WheelBlockEntity blockEntity) {
        return true;
    }
}

