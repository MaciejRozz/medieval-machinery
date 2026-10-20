/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 */
package com.medievalmachinery.mod.npc.machine;

import com.medievalmachinery.mod.machine.core.MachineCrewRole;
import java.util.UUID;
import net.minecraft.class_2338;
import net.minecraft.class_2487;

public final class NpcMachineAssignmentData {
    private UUID machineId;
    private MachineCrewRole crewRole = MachineCrewRole.OPERATOR;
    private class_2338 preferredSupplyChest;

    public boolean isAssigned() {
        return this.machineId != null;
    }

    public UUID getMachineId() {
        return this.machineId;
    }

    public MachineCrewRole getCrewRole() {
        return this.crewRole;
    }

    public class_2338 getPreferredSupplyChest() {
        return this.preferredSupplyChest;
    }

    public void setPreferredSupplyChest(class_2338 pos) {
        this.preferredSupplyChest = pos == null ? null : pos.method_10062();
    }

    public void assign(UUID machineId, MachineCrewRole crewRole) {
        this.machineId = machineId;
        this.crewRole = crewRole == null ? MachineCrewRole.OPERATOR : crewRole;
    }

    public void clear() {
        this.machineId = null;
        this.crewRole = MachineCrewRole.OPERATOR;
        this.preferredSupplyChest = null;
    }

    public class_2487 toNbt() {
        class_2487 nbt = new class_2487();
        if (this.machineId != null) {
            nbt.method_25927("MachineId", this.machineId);
        }
        nbt.method_10582("CrewRole", this.crewRole.serializedName());
        if (this.preferredSupplyChest != null) {
            nbt.method_10569("PreferredSupplyX", this.preferredSupplyChest.method_10263());
            nbt.method_10569("PreferredSupplyY", this.preferredSupplyChest.method_10264());
            nbt.method_10569("PreferredSupplyZ", this.preferredSupplyChest.method_10260());
        }
        return nbt;
    }

    public void readNbt(class_2487 nbt) {
        this.machineId = nbt.method_25928("MachineId") ? nbt.method_25926("MachineId") : null;
        this.crewRole = nbt.method_10573("CrewRole", 8) ? MachineCrewRole.fromSerializedName(nbt.method_10558("CrewRole")) : MachineCrewRole.OPERATOR;
        this.preferredSupplyChest = nbt.method_10573("PreferredSupplyX", 3) && nbt.method_10573("PreferredSupplyY", 3) && nbt.method_10573("PreferredSupplyZ", 3) ? new class_2338(nbt.method_10550("PreferredSupplyX"), nbt.method_10550("PreferredSupplyY"), nbt.method_10550("PreferredSupplyZ")) : null;
    }
}

