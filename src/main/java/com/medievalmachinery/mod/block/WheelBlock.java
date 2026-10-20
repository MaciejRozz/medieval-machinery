/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1750
 *  net.minecraft.class_1922
 *  net.minecraft.class_1936
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2343
 *  net.minecraft.class_2350
 *  net.minecraft.class_2383
 *  net.minecraft.class_2415
 *  net.minecraft.class_2464
 *  net.minecraft.class_2470
 *  net.minecraft.class_2586
 *  net.minecraft.class_259
 *  net.minecraft.class_265
 *  net.minecraft.class_2680
 *  net.minecraft.class_2689$class_2690
 *  net.minecraft.class_2753
 *  net.minecraft.class_2754
 *  net.minecraft.class_2769
 *  net.minecraft.class_3726
 *  net.minecraft.class_4538
 *  net.minecraft.class_4970$class_2251
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block;

import com.medievalmachinery.mod.block.WheelBlockEntity;
import com.medievalmachinery.mod.block.WheelPlacementMode;
import net.minecraft.class_1750;
import net.minecraft.class_1922;
import net.minecraft.class_1936;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2343;
import net.minecraft.class_2350;
import net.minecraft.class_2383;
import net.minecraft.class_2415;
import net.minecraft.class_2464;
import net.minecraft.class_2470;
import net.minecraft.class_2586;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2689;
import net.minecraft.class_2753;
import net.minecraft.class_2754;
import net.minecraft.class_2769;
import net.minecraft.class_3726;
import net.minecraft.class_4538;
import net.minecraft.class_4970;
import org.jetbrains.annotations.Nullable;

public final class WheelBlock
extends class_2383
implements class_2343 {
    public static final class_2753 FACING = class_2383.field_11177;
    public static final class_2754<WheelPlacementMode> PLACEMENT_MODE = class_2754.method_11850((String)"placement_mode", WheelPlacementMode.class);
    private static final class_265 FLOOR_SHAPE = class_259.method_1084((class_265)class_2248.method_9541((double)2.0, (double)0.0, (double)0.75, (double)14.0, (double)2.75, (double)15.25), (class_265)class_2248.method_9541((double)0.75, (double)0.0, (double)2.0, (double)15.25, (double)2.75, (double)14.0));
    private static final class_265 NORTH_SHAPE = class_259.method_1084((class_265)class_2248.method_9541((double)2.0, (double)0.0, (double)0.0, (double)14.0, (double)15.25, (double)5.5), (class_265)class_2248.method_9541((double)0.75, (double)2.0, (double)0.0, (double)15.25, (double)14.0, (double)5.5));
    private static final class_265 SOUTH_SHAPE = class_259.method_1084((class_265)class_2248.method_9541((double)2.0, (double)0.0, (double)10.5, (double)14.0, (double)15.25, (double)16.0), (class_265)class_2248.method_9541((double)0.75, (double)2.0, (double)10.5, (double)15.25, (double)14.0, (double)16.0));
    private static final class_265 WEST_SHAPE = class_259.method_1084((class_265)class_2248.method_9541((double)0.0, (double)0.0, (double)2.0, (double)5.5, (double)15.25, (double)14.0), (class_265)class_2248.method_9541((double)0.0, (double)2.0, (double)0.75, (double)5.5, (double)14.0, (double)15.25));
    private static final class_265 EAST_SHAPE = class_259.method_1084((class_265)class_2248.method_9541((double)10.5, (double)0.0, (double)2.0, (double)16.0, (double)15.25, (double)14.0), (class_265)class_2248.method_9541((double)10.5, (double)2.0, (double)0.75, (double)16.0, (double)14.0, (double)15.25));

    public WheelBlock(class_4970.class_2251 settings) {
        super(settings);
        this.method_9590((class_2680)((class_2680)((class_2680)this.method_9595().method_11664()).method_11657((class_2769)FACING, (Comparable)class_2350.field_11043)).method_11657(PLACEMENT_MODE, (Comparable)((Object)WheelPlacementMode.FLOOR)));
    }

    @Nullable
    public class_2680 method_9605(class_1750 context) {
        class_2350 side = context.method_8038();
        if (side == class_2350.field_11033) {
            return null;
        }
        if (side.method_10166().method_10179()) {
            class_2680 state = (class_2680)((class_2680)this.method_9564().method_11657(PLACEMENT_MODE, (Comparable)((Object)WheelPlacementMode.WALL))).method_11657((class_2769)FACING, (Comparable)side.method_10153());
            return state.method_26184((class_4538)context.method_8045(), context.method_8037()) ? state : null;
        }
        class_2680 state = (class_2680)((class_2680)this.method_9564().method_11657(PLACEMENT_MODE, (Comparable)((Object)WheelPlacementMode.FLOOR))).method_11657((class_2769)FACING, (Comparable)class_2350.field_11043);
        return state.method_26184((class_4538)context.method_8045(), context.method_8037()) ? state : null;
    }

    protected void method_9515(class_2689.class_2690<class_2248, class_2680> builder) {
        builder.method_11667(new class_2769[]{FACING, PLACEMENT_MODE});
    }

    public class_2464 method_9604(class_2680 state) {
        return class_2464.field_11455;
    }

    public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return WheelBlock.getShapeForState(state);
    }

    public class_265 method_9549(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
        return WheelBlock.getShapeForState(state);
    }

    public class_265 method_9571(class_2680 state, class_1922 world, class_2338 pos) {
        return class_259.method_1073();
    }

    private static class_265 getShapeForState(class_2680 state) {
        if (state.method_11654(PLACEMENT_MODE) == WheelPlacementMode.FLOOR) {
            return FLOOR_SHAPE;
        }
        return switch ((class_2350)state.method_11654((class_2769)FACING)) {
            case class_2350.field_11043 -> NORTH_SHAPE;
            case class_2350.field_11035 -> SOUTH_SHAPE;
            case class_2350.field_11039 -> WEST_SHAPE;
            case class_2350.field_11034 -> EAST_SHAPE;
            default -> FLOOR_SHAPE;
        };
    }

    public boolean method_9558(class_2680 state, class_4538 world, class_2338 pos) {
        class_2338 floorPos = pos.method_10074();
        if (!world.method_8320(floorPos).method_26206((class_1922)world, floorPos, class_2350.field_11036)) {
            return false;
        }
        if (state.method_11654(PLACEMENT_MODE) == WheelPlacementMode.FLOOR) {
            return true;
        }
        class_2350 wallDirection = (class_2350)state.method_11654((class_2769)FACING);
        class_2338 wallPos = pos.method_10093(wallDirection);
        return world.method_8320(wallPos).method_26206((class_1922)world, wallPos, wallDirection.method_10153());
    }

    public class_2680 method_9598(class_2680 state, class_2470 rotation) {
        return (class_2680)state.method_11657((class_2769)FACING, (Comparable)rotation.method_10503((class_2350)state.method_11654((class_2769)FACING)));
    }

    public class_2680 method_9569(class_2680 state, class_2415 mirror) {
        return state.method_26186(mirror.method_10345((class_2350)state.method_11654((class_2769)FACING)));
    }

    @Nullable
    public class_2586 method_10123(class_2338 pos, class_2680 state) {
        return new WheelBlockEntity(pos, state);
    }

    public class_2680 method_9559(class_2680 state, class_2350 direction, class_2680 neighborState, class_1936 world, class_2338 pos, class_2338 neighborPos) {
        if (!state.method_26184((class_4538)world, pos)) {
            return class_2246.field_10124.method_9564();
        }
        return super.method_9559(state, direction, neighborState, world, pos, neighborPos);
    }
}

