/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1922
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2350$class_2353
 *  net.minecraft.class_2374
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_3218
 */
package com.medievalmachinery.mod.npc.entity;

import com.medievalmachinery.mod.npc.core.NpcFormation;
import com.medievalmachinery.mod.npc.core.NpcFormationLayout;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2374;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_3218;

public final class MedievalSoldierFormationPlanner {
    private MedievalSoldierFormationPlanner() {
    }

    public static List<Assignment> planAroundCurrentOrTacticalAnchor(class_3218 world, List<MedievalSoldierEntity> soldiers, NpcFormation formation, float fallbackYaw) {
        if (world == null || soldiers == null || soldiers.isEmpty() || formation == NpcFormation.FREE) {
            return List.of();
        }
        ArrayList<MedievalSoldierEntity> ordered = new ArrayList<MedievalSoldierEntity>(soldiers);
        ordered.sort(Comparator.comparing(s -> s.method_5667().toString()));
        class_243 anchor = MedievalSoldierFormationPlanner.resolveGroupAnchor(ordered);
        float yaw = ((MedievalSoldierEntity)ordered.get(0)).getFormationFacingYaw(fallbackYaw);
        return MedievalSoldierFormationPlanner.plan(world, ordered, formation, anchor, yaw);
    }

    public static List<Assignment> planAtMoveTarget(class_3218 world, List<MedievalSoldierEntity> soldiers, class_2338 base, float fallbackYaw) {
        if (world == null || soldiers == null || soldiers.isEmpty() || base == null) {
            return List.of();
        }
        ArrayList<MedievalSoldierEntity> ordered = new ArrayList<MedievalSoldierEntity>(soldiers);
        ordered.sort(Comparator.comparing(s -> s.method_5667().toString()));
        NpcFormation formation = MedievalSoldierFormationPlanner.commonFormation(ordered);
        if (formation == NpcFormation.FREE) {
            ArrayList<Assignment> free = new ArrayList<Assignment>(ordered.size());
            HashSet<Long> reservedFeet = new HashSet<Long>();
            class_243 anchor = class_243.method_24955((class_2382)base.method_10084());
            for (int i = 0; i < ordered.size(); ++i) {
                int[] off = MedievalSoldierFormationPlanner.freeGatherOffset(i);
                class_243 raw = new class_243(anchor.field_1352 + (double)off[0] * 1.75, anchor.field_1351, anchor.field_1350 + (double)off[1] * 1.75);
                class_243 resolved = MedievalSoldierFormationPlanner.resolveStandingPoint(world, (MedievalSoldierEntity)ordered.get(i), raw, reservedFeet);
                MedievalSoldierFormationPlanner.reserve(reservedFeet, resolved);
                free.add(new Assignment((MedievalSoldierEntity)ordered.get(i), i, resolved));
            }
            return free;
        }
        float yaw = ((MedievalSoldierEntity)ordered.get(0)).getFormationFacingYaw(fallbackYaw);
        return MedievalSoldierFormationPlanner.plan(world, ordered, formation, class_243.method_24955((class_2382)base.method_10084()), yaw);
    }

    private static int[] freeGatherOffset(int slot) {
        int wanted = Math.max(0, slot);
        int seen = 0;
        for (int ring = 1; ring < 64; ++ring) {
            int z;
            int x;
            for (x = -ring; x <= ring; ++x) {
                if (seen++ != wanted) continue;
                return new int[]{x, -ring};
            }
            for (z = -ring + 1; z <= ring; ++z) {
                if (seen++ != wanted) continue;
                return new int[]{ring, z};
            }
            for (x = ring - 1; x >= -ring; --x) {
                if (seen++ != wanted) continue;
                return new int[]{x, ring};
            }
            for (z = ring - 1; z >= -ring + 1; --z) {
                if (seen++ != wanted) continue;
                return new int[]{-ring, z};
            }
        }
        return new int[]{0, 1};
    }

    public static NpcFormation commonFormation(List<MedievalSoldierEntity> soldiers) {
        if (soldiers == null || soldiers.isEmpty()) {
            return NpcFormation.FREE;
        }
        NpcFormation first = soldiers.get(0).getFormation();
        for (MedievalSoldierEntity soldier : soldiers) {
            if (soldier.getFormation() == first) continue;
            return first;
        }
        return first;
    }

    private static List<Assignment> plan(class_3218 world, List<MedievalSoldierEntity> ordered, NpcFormation formation, class_243 anchor, float yawDegrees) {
        int count = ordered.size();
        double yaw = Math.toRadians(yawDegrees);
        double forwardX = -Math.sin(yaw);
        double forwardZ = Math.cos(yaw);
        double rightX = Math.cos(yaw);
        double rightZ = Math.sin(yaw);
        double footprint = ordered.stream().mapToDouble(MedievalSoldierEntity::getFormationFootprintMultiplier).max().orElse(1.0);
        double spacingMultiplier = ordered.stream().mapToDouble(MedievalSoldierEntity::getFormationSpacingMultiplier).average().orElse(1.0);
        double spacing = 2.2 * footprint * spacingMultiplier;
        boolean[] occupied = new boolean[count];
        int[] assignedSlots = new int[count];
        Arrays.fill(assignedSlots, -1);
        for (int i = 0; i < count; ++i) {
            int current;
            int currentSlot = ordered.get(i).getFormationSlot();
            int n = current = currentSlot >= 0 && currentSlot < count ? currentSlot : -1;
            if (current < 0 || occupied[current]) continue;
            assignedSlots[i] = current;
            occupied[current] = true;
        }
        int nextFree = 0;
        for (int i = 0; i < count; ++i) {
            if (assignedSlots[i] >= 0) continue;
            while (nextFree < count && occupied[nextFree]) {
                ++nextFree;
            }
            if (nextFree >= count) break;
            assignedSlots[i] = nextFree;
            occupied[nextFree] = true;
        }
        ArrayList<Assignment> result = new ArrayList<Assignment>(count);
        HashSet<Long> reservedFeet = new HashSet<Long>();
        for (int i = 0; i < count; ++i) {
            MedievalSoldierEntity soldier = ordered.get(i);
            int slot = assignedSlots[i] >= 0 ? assignedSlots[i] : i;
            NpcFormationLayout.Offset offset = NpcFormationLayout.offset(formation, slot, count, spacing);
            class_243 raw = new class_243(anchor.field_1352 - forwardX * offset.back() + rightX * offset.lateral(), anchor.field_1351, anchor.field_1350 - forwardZ * offset.back() + rightZ * offset.lateral());
            class_243 resolved = MedievalSoldierFormationPlanner.resolveStandingPoint(world, soldier, raw, reservedFeet);
            MedievalSoldierFormationPlanner.reserve(reservedFeet, resolved);
            result.add(new Assignment(soldier, slot, resolved));
        }
        return result;
    }

    private static class_243 resolveGroupAnchor(List<MedievalSoldierEntity> ordered) {
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        int tactical = 0;
        for (MedievalSoldierEntity soldier : ordered) {
            class_243 intended = soldier.getFormationCommandAnchor();
            if (intended == null) continue;
            x += intended.field_1352;
            y += intended.field_1351;
            z += intended.field_1350;
            ++tactical;
        }
        if (tactical > 0) {
            return new class_243(x / (double)tactical, y / (double)tactical, z / (double)tactical);
        }
        for (MedievalSoldierEntity soldier : ordered) {
            x += soldier.method_23317();
            y += soldier.method_23318();
            z += soldier.method_23321();
        }
        return new class_243(x / (double)ordered.size(), y / (double)ordered.size(), z / (double)ordered.size());
    }

    private static class_243 resolveStandingPoint(class_3218 world, MedievalSoldierEntity soldier, class_243 raw, Set<Long> reservedFeet) {
        int[] vertical;
        class_2338 base = class_2338.method_49638((class_2374)raw);
        int[][] horizontal = new int[][]{{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int dy : vertical = new int[]{0, 1, -1, 2, -2}) {
            for (int[] off : horizontal) {
                class_2338 feet = base.method_10069(off[0], dy, off[1]);
                if (!MedievalSoldierFormationPlanner.isStandable(world, soldier, feet) || reservedFeet.contains(feet.method_10063())) continue;
                return new class_243((double)feet.method_10263() + 0.5, (double)feet.method_10264(), (double)feet.method_10260() + 0.5);
            }
        }
        for (int radius = 2; radius <= 6; ++radius) {
            for (int x = -radius; x <= radius; ++x) {
                for (int z : new int[]{-radius, radius}) {
                    class_2338 feet = base.method_10069(x, 0, z);
                    if (!MedievalSoldierFormationPlanner.isStandable(world, soldier, feet) || reservedFeet.contains(feet.method_10063())) continue;
                    return class_243.method_24955((class_2382)feet);
                }
            }
            for (int z = -radius + 1; z < radius; ++z) {
                for (int x : new int[]{-radius, radius}) {
                    class_2338 feet = base.method_10069(x, 0, z);
                    if (!MedievalSoldierFormationPlanner.isStandable(world, soldier, feet) || reservedFeet.contains(feet.method_10063())) continue;
                    return class_243.method_24955((class_2382)feet);
                }
            }
        }
        return raw;
    }

    private static void reserve(Set<Long> reservedFeet, class_243 target) {
        if (reservedFeet != null && target != null) {
            reservedFeet.add(class_2338.method_49638((class_2374)target).method_10063());
        }
    }

    private static boolean isStandable(class_3218 world, MedievalSoldierEntity soldier, class_2338 feet) {
        if (!world.method_8320(feet).method_26220((class_1922)world, feet).method_1110()) {
            return false;
        }
        if (!world.method_8320(feet.method_10084()).method_26220((class_1922)world, feet.method_10084()).method_1110()) {
            return false;
        }
        if (world.method_8320(feet.method_10074()).method_26220((class_1922)world, feet.method_10074()).method_1110()) {
            return false;
        }
        if (soldier != null && soldier.method_5765()) {
            class_2338 floor = feet.method_10074();
            int solidNeighbors = 0;
            for (class_2350 d : class_2350.class_2353.field_11062) {
                class_2338 side = floor.method_10093(d);
                if (world.method_8320(side).method_26220((class_1922)world, side).method_1110()) continue;
                ++solidNeighbors;
            }
            return solidNeighbors >= 2;
        }
        return true;
    }

    public record Assignment(MedievalSoldierEntity soldier, int slot, class_243 target) {
    }
}

