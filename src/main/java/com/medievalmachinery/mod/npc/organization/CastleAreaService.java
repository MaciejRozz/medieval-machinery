/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1923
 *  net.minecraft.class_2338
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.npc.organization;

import com.medievalmachinery.mod.npc.organization.GarrisonData;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import org.jetbrains.annotations.Nullable;

public final class CastleAreaService {
    public static final int MAX_BOUNDARY_POINTS = 24;
    public static final int MAX_BOUNDARY_DISTANCE = 256;

    private CastleAreaService() {
    }

    public static boolean hasCustomBoundary(@Nullable GarrisonData garrison) {
        return garrison != null && garrison.getBoundaryPoints().size() >= 3;
    }

    public static boolean contains(@Nullable GarrisonData garrison, class_2338 pos, int fallbackRadius) {
        double radius;
        double dz;
        if (garrison == null || pos == null) {
            return false;
        }
        List<class_2338> points = garrison.getBoundaryPoints();
        if (points.size() >= 3) {
            return CastleAreaService.containsPolygon(points, (double)pos.method_10263() + 0.5, (double)pos.method_10260() + 0.5);
        }
        class_2338 center = garrison.getCenter();
        if (center == null) {
            return false;
        }
        double dx = pos.method_10263() - center.method_10263();
        return dx * dx + (dz = (double)(pos.method_10260() - center.method_10260())) * dz <= (radius = (double)Math.max(1, fallbackRadius)) * radius;
    }

    public static boolean canAddBoundaryPoint(@Nullable GarrisonData garrison, class_2338 point) {
        long dz;
        if (garrison == null || point == null || garrison.getCenter() == null) {
            return false;
        }
        if (garrison.getBoundaryPoints().size() >= 24) {
            return false;
        }
        class_2338 center = garrison.getCenter();
        long dx = (long)point.method_10263() - (long)center.method_10263();
        return dx * dx + (dz = (long)point.method_10260() - (long)center.method_10260()) * dz <= 65536L;
    }

    public static Set<Long> candidateChunkKeys(@Nullable GarrisonData garrison, int fallbackRadius) {
        LinkedHashSet<Long> result = new LinkedHashSet<Long>();
        if (garrison == null || garrison.getCenter() == null) {
            return result;
        }
        Bounds bounds = CastleAreaService.bounds(garrison, fallbackRadius);
        for (int chunkX = bounds.minX >> 4; chunkX <= bounds.maxX >> 4; ++chunkX) {
            for (int chunkZ = bounds.minZ >> 4; chunkZ <= bounds.maxZ >> 4; ++chunkZ) {
                result.add(class_1923.method_8331((int)chunkX, (int)chunkZ));
            }
        }
        return result;
    }

    public static List<class_2338> sanitizeBoundary(@Nullable GarrisonData garrison, List<class_2338> points) {
        if (garrison == null || garrison.getCenter() == null || points == null || points.isEmpty()) {
            return List.of();
        }
        ArrayList<class_2338> result = new ArrayList<class_2338>(Math.min(points.size(), 24));
        for (class_2338 point : points) {
            long dz;
            if (point == null || result.size() >= 24) break;
            long dx = (long)point.method_10263() - (long)garrison.getCenter().method_10263();
            if (dx * dx + (dz = (long)point.method_10260() - (long)garrison.getCenter().method_10260()) * dz > 65536L) continue;
            class_2338 immutable = point.method_10062();
            boolean duplicate = false;
            for (class_2338 existing : result) {
                if (existing.method_10263() != immutable.method_10263() || existing.method_10260() != immutable.method_10260()) continue;
                duplicate = true;
                break;
            }
            if (duplicate) continue;
            result.add(immutable);
        }
        return List.copyOf(result);
    }

    private static Bounds bounds(GarrisonData garrison, int fallbackRadius) {
        List<class_2338> points = garrison.getBoundaryPoints();
        if (points.size() >= 3) {
            int minX = Integer.MAX_VALUE;
            int minZ = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxZ = Integer.MIN_VALUE;
            for (class_2338 point : points) {
                minX = Math.min(minX, point.method_10263());
                minZ = Math.min(minZ, point.method_10260());
                maxX = Math.max(maxX, point.method_10263());
                maxZ = Math.max(maxZ, point.method_10260());
            }
            return new Bounds(minX, minZ, maxX, maxZ);
        }
        class_2338 center = garrison.getCenter();
        int radius = Math.max(1, fallbackRadius);
        return new Bounds(center.method_10263() - radius, center.method_10260() - radius, center.method_10263() + radius, center.method_10260() + radius);
    }

    /*
     * Unable to fully structure code
     */
    private static boolean containsPolygon(List<class_2338> points, double x, double z) {
        inside = false;
        i = 0;
        j = points.size() - 1;
        while (i < points.size()) {
            xi = (double)points.get(i).method_10263() + 0.5;
            zi = (double)points.get(i).method_10260() + 0.5;
            xj = (double)points.get(j).method_10263() + 0.5;
            if (zi > z == (zj = (double)points.get(j).method_10260() + 0.5) > z) ** GOTO lbl-1000
            v0 = zj - zi == 0.0 ? 1.0E-9 : zj - zi;
            if (x < (xj - xi) * (z - zi) / v0 + xi) {
                v1 = true;
            } else lbl-1000:
            // 2 sources

            {
                v1 = crosses = false;
            }
            if (crosses) {
                inside = inside == false;
            }
            j = i++;
        }
        return inside;
    }

    private record Bounds(int minX, int minZ, int maxX, int maxZ) {
    }
}

