/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.npc.core;

import com.medievalmachinery.mod.npc.core.NpcFormation;

public final class NpcFormationLayout {
    private NpcFormationLayout() {
    }

    public static Offset offset(NpcFormation formation, int index, int count, double spacing) {
        if (formation == null || formation == NpcFormation.FREE || count <= 1) {
            return new Offset(0.0, 0.0);
        }
        index = Math.max(0, Math.min(index, count - 1));
        return switch (formation) {
            default -> throw new IncompatibleClassChangeError();
            case NpcFormation.LINE -> new Offset(((double)index - (double)(count - 1) / 2.0) * spacing, 0.0);
            case NpcFormation.SQUARE -> NpcFormationLayout.square(index, count, spacing, false);
            case NpcFormation.HOLLOW_SQUARE -> NpcFormationLayout.square(index, count, spacing, true);
            case NpcFormation.TRIANGLE -> NpcFormationLayout.triangle(index, spacing);
            case NpcFormation.V_FORM -> NpcFormationLayout.vForm(index, spacing);
            case NpcFormation.HALF_CIRCLE -> NpcFormationLayout.arc(index, count, spacing, false);
            case NpcFormation.CIRCLE -> NpcFormationLayout.arc(index, count, spacing, true);
            case NpcFormation.MOVEMENT -> NpcFormationLayout.movement(index, count, spacing);
            case NpcFormation.FREE -> new Offset(0.0, 0.0);
        };
    }

    private static Offset square(int index, int count, double spacing, boolean hollow) {
        int z;
        int x;
        int side = Math.max(2, (int)Math.ceil(Math.sqrt(count)));
        if (!hollow || count <= 8) {
            int row = index / side;
            int col = index % side;
            int rowCount = Math.min(side, count - row * side);
            return new Offset(((double)col - (double)(rowCount - 1) / 2.0) * spacing, (double)row * spacing);
        }
        int perimeter = Math.max(1, side * 4 - 4);
        int p = index % perimeter;
        if (p < side) {
            x = p;
            z = 0;
        } else if (p < side * 2 - 1) {
            x = side - 1;
            z = p - side + 1;
        } else if (p < side * 3 - 2) {
            x = side - 2 - (p - (side * 2 - 1));
            z = side - 1;
        } else {
            x = 0;
            z = side - 2 - (p - (side * 3 - 2));
        }
        return new Offset(((double)x - (double)(side - 1) / 2.0) * spacing, (double)z * spacing);
    }

    private static Offset triangle(int index, double spacing) {
        int row = 0;
        int start = 0;
        while (start + row + 1 <= index) {
            start += ++row;
        }
        int inRow = index - start;
        return new Offset(((double)inRow - (double)row / 2.0) * spacing, (double)row * spacing * 0.9);
    }

    private static Offset vForm(int index, double spacing) {
        if (index == 0) {
            return new Offset(0.0, 0.0);
        }
        int n = index - 1;
        int row = n / 2 + 1;
        int side = (n & 1) == 0 ? -1 : 1;
        return new Offset((double)(side * row) * spacing * 0.85, (double)row * spacing * 0.85);
    }

    private static Offset arc(int index, int count, double spacing, boolean fullCircle) {
        double radius = Math.max(spacing * 1.4, spacing * (double)count / (fullCircle ? 6.0 : 3.5));
        if (fullCircle) {
            double angle = Math.PI * 2 * (double)index / (double)count - 1.5707963267948966;
            return new Offset(Math.cos(angle) * radius, radius + Math.sin(angle) * radius);
        }
        double angle = count == 1 ? 0.0 : -1.5707963267948966 + Math.PI * (double)index / ((double)count - 1.0);
        return new Offset(Math.sin(angle) * radius, (1.0 - Math.cos(angle)) * radius * 0.65);
    }

    private static Offset movement(int index, int count, double spacing) {
        int columns = count <= 3 ? 1 : 2;
        int row = index / columns;
        int col = index % columns;
        return new Offset(((double)col - (double)(columns - 1) / 2.0) * spacing * 0.8, (double)row * spacing * 0.95);
    }

    public record Offset(double lateral, double back) {
    }
}

