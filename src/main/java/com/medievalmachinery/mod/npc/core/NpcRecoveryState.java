/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1314
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_3218
 *  net.minecraft.class_3481
 */
package com.medievalmachinery.mod.npc.core;

import com.medievalmachinery.mod.registry.ModBlocks;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.class_1314;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_3218;
import net.minecraft.class_3481;

public final class NpcRecoveryState {
    private static final int ACCIDENT_WINDOW_TICKS = 600;
    private static final double MIN_ACCIDENT_DROP = 1.35;
    private static final double MAX_ACCIDENT_HORIZONTAL_DISTANCE_SQ = 20.25;
    private static final int MAX_TEMP_BLOCKS = 4;
    private class_2338 lastSafePosition;
    private class_2338 accidentOrigin;
    private int accidentUntilAge;
    private int placedBlockCount;
    private final Set<class_2338> placedBlocks = new LinkedHashSet<class_2338>();

    public void observe(class_1314 npc) {
        boolean onClimbable;
        if (npc == null) {
            return;
        }
        class_2338 current = npc.method_24515();
        boolean bl = onClimbable = npc.method_37908().method_8320(current).method_26164(class_3481.field_22414) || npc.method_37908().method_8320(current.method_10084()).method_26164(class_3481.field_22414);
        if (this.lastSafePosition == null && npc.method_24828() && !onClimbable) {
            this.lastSafePosition = current.method_10062();
        }
        if (!this.isAccidentActive(npc)) {
            boolean localDrop;
            boolean bl2 = localDrop = this.lastSafePosition != null && npc.method_23318() <= (double)this.lastSafePosition.method_10264() - 1.35 && NpcRecoveryState.horizontalDistanceSq(current, this.lastSafePosition) <= 20.25;
            if (!onClimbable && localDrop) {
                this.accidentOrigin = this.lastSafePosition.method_10062();
                this.accidentUntilAge = npc.field_6012 + 600;
                this.placedBlockCount = 0;
            } else if (npc.method_24828() && !onClimbable) {
                this.lastSafePosition = current.method_10062();
            }
            return;
        }
        if (npc.method_23318() >= (double)this.accidentOrigin.method_10264() - 0.2 || NpcRecoveryState.horizontalDistanceSq(current, this.accidentOrigin) > 20.25 || npc.field_6012 > this.accidentUntilAge) {
            this.accidentUntilAge = 0;
        }
    }

    public boolean canUseEmergencyBlock(class_1314 npc) {
        if (!this.isAccidentActive(npc) || !npc.method_24828()) {
            return false;
        }
        if (this.placedBlockCount >= 4) {
            return false;
        }
        if (npc.method_23318() > (double)this.accidentOrigin.method_10264() - 0.75) {
            return false;
        }
        return NpcRecoveryState.horizontalDistanceSq(npc.method_24515(), this.accidentOrigin) <= 20.25;
    }

    public class_2338 getAccidentOrigin() {
        return this.accidentOrigin;
    }

    public void recordPlacedBlock(class_2338 pos) {
        if (pos == null) {
            return;
        }
        this.placedBlocks.add(pos.method_10062());
        ++this.placedBlockCount;
    }

    public Set<class_2338> getPlacedBlocks() {
        return Collections.unmodifiableSet(this.placedBlocks);
    }

    public boolean shouldCleanup(class_1314 npc) {
        return !this.placedBlocks.isEmpty() && !this.isAccidentActive(npc);
    }

    public void cleanupPlacedBlocksIfFinished(class_1314 npc) {
        class_3218 world;
        block4: {
            block3: {
                class_1937 class_19372 = npc.method_37908();
                if (!(class_19372 instanceof class_3218)) break block3;
                world = (class_3218)class_19372;
                if (this.shouldCleanup(npc)) break block4;
            }
            return;
        }
        for (class_2338 pos : this.placedBlocks) {
            if (!world.method_8320(pos).method_27852(ModBlocks.NPC_RESCUE_BLOCK)) continue;
            world.method_8650(pos, false);
        }
        this.clearPlacedBlocks();
    }

    public void clearPlacedBlocks() {
        this.placedBlocks.clear();
        this.placedBlockCount = 0;
        this.accidentOrigin = null;
        this.accidentUntilAge = 0;
    }

    private boolean isAccidentActive(class_1314 npc) {
        return this.accidentOrigin != null && this.accidentUntilAge > 0 && npc.field_6012 <= this.accidentUntilAge;
    }

    private static double horizontalDistanceSq(class_2338 a, class_2338 b) {
        double dx = a.method_10263() - b.method_10263();
        double dz = a.method_10260() - b.method_10260();
        return dx * dx + dz * dz;
    }
}

