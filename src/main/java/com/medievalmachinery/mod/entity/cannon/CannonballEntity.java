/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1282
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1309
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1922
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_1937$class_7867
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2586
 *  net.minecraft.class_2680
 *  net.minecraft.class_2960
 *  net.minecraft.class_3218
 *  net.minecraft.class_3417
 *  net.minecraft.class_3419
 *  net.minecraft.class_3857
 *  net.minecraft.class_3965
 *  net.minecraft.class_3966
 *  net.minecraft.class_7923
 */
package com.medievalmachinery.mod.entity.cannon;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.entity.machine.AbstractMedievalMachineEntity;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.registry.ModItems;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1922;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3857;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_7923;

public final class CannonballEntity
extends class_3857 {
    private class_1792 ammunition = ModItems.STONE_CANNONBALL;
    private double launchX;
    private double launchZ;
    private float maximumRange = 18.0f;

    public CannonballEntity(class_1299<? extends CannonballEntity> type, class_1937 world) {
        super(type, world);
    }

    public CannonballEntity(class_1937 world, double x, double y, double z, class_1792 ammunition, float maximumRange) {
        super(RegisteredContent.cannonball(), x, y, z, world);
        this.ammunition = ModItems.isCannonball(ammunition) ? ammunition : ModItems.STONE_CANNONBALL;
        this.method_16940(new class_1799((class_1935)this.ammunition));
        this.launchX = x;
        this.launchZ = z;
        this.maximumRange = maximumRange;
    }

    protected class_1792 method_16942() {
        return this.ammunition;
    }

    public void method_5773() {
        double safetyRange;
        double dz;
        double dx;
        super.method_5773();
        if (!this.method_37908().field_9236 && ((dx = this.method_23317() - this.launchX) * dx + (dz = this.method_23321() - this.launchZ) * dz >= (safetyRange = (double)this.maximumRange * 4.0 + 32.0) * safetyRange || this.field_6012 > 600)) {
            this.method_31472();
        }
    }

    protected float method_7490() {
        return 0.05f;
    }

    protected void method_7454(class_3966 hit) {
        super.method_7454(hit);
        if (this.method_37908().field_9236) {
            return;
        }
        this.applyImpactDamage(hit.method_17782());
        this.createImpactEffect();
        this.finishImpact();
    }

    protected void method_24920(class_3965 hit) {
        super.method_24920(hit);
        if (this.method_37908().field_9236) {
            return;
        }
        this.applyImpactDamage(null);
        if (this.ammunition == ModItems.HEAVY_IRON_CANNONBALL) {
            this.damageBlocksInFlightDirection(hit);
        }
        this.createImpactEffect();
        this.finishImpact();
    }

    private void applyImpactDamage(class_1297 directTarget) {
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        MedievalMachineryConfig config = MedievalMachineryConfig.get();
        float impactDamage = this.ammunition == ModItems.HEAVY_IRON_CANNONBALL ? (float)config.heavyIron.damage : (this.ammunition == ModItems.IRON_CANNONBALL ? (float)config.iron.damage : (this.ammunition == ModItems.TNT_CANNONBALL ? (float)config.tnt.damage : (float)config.stone.damage));
        class_1282 source = this.method_48923().method_48811((class_1297)this, this.method_24921());
        class_243 impact = this.method_19538();
        class_238 impactArea = new class_238(impact.field_1352 - 1.5, impact.field_1351 - 1.5, impact.field_1350 - 1.5, impact.field_1352 + 1.5, impact.field_1351 + 1.5, impact.field_1350 + 1.5);
        for (class_1297 entity : serverWorld.method_8333((class_1297)this, impactArea, candidate -> candidate instanceof class_1309 || candidate instanceof AbstractMedievalMachineEntity)) {
            entity.method_5643(source, impactDamage);
        }
        if (directTarget != null && !directTarget.method_5829().method_994(impactArea)) {
            directTarget.method_5643(source, impactDamage);
        }
    }

    private void damageBlocksInFlightDirection(class_3965 hit) {
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        class_243 velocity = this.method_18798();
        if (velocity.method_1027() < 1.0E-5) {
            velocity = class_243.method_24954((class_2382)hit.method_17780().method_10153().method_10163());
        }
        class_243 direction = velocity.method_1029();
        class_243 start = hit.method_17784().method_1020(direction.method_1021(0.15));
        int broken = 0;
        class_2338 previous = null;
        for (double distance = 0.0; distance <= 2.4 && broken < 4; distance += 0.35) {
            class_2338 pos = class_2338.method_49638((class_2374)start.method_1019(direction.method_1021(distance)));
            if (pos.equals(previous)) continue;
            previous = pos;
            class_2680 state = serverWorld.method_8320(pos);
            if (state.method_26215() || state.method_26214((class_1922)serverWorld, pos) < 0.0f) continue;
            class_2586 blockEntity = serverWorld.method_8321(pos);
            float hardness = state.method_26214((class_1922)serverWorld, pos);
            float blastResistance = state.method_26204().method_9520();
            if (blockEntity != null || hardness > 4.0f || blastResistance > 12.0f) break;
            if (!serverWorld.method_8651(pos, true, this.method_24921())) continue;
            ++broken;
        }
    }

    private void finishImpact() {
        if (this.ammunition == ModItems.TNT_CANNONBALL) {
            this.method_37908().method_8437((class_1297)this, this.method_23317(), this.method_23318(), this.method_23321(), 4.0f, class_1937.class_7867.field_40891);
        }
        this.method_31472();
    }

    private void createImpactEffect() {
        class_1937 class_19372 = this.method_37908();
        if (!(class_19372 instanceof class_3218)) {
            return;
        }
        class_3218 serverWorld = (class_3218)class_19372;
        int particleCount = this.ammunition == ModItems.TNT_CANNONBALL ? 24 : 14;
        serverWorld.method_14199((class_2394)class_2398.field_11203, this.method_23317(), this.method_23318(), this.method_23321(), particleCount, 0.28, 0.22, 0.28, 0.08);
        serverWorld.method_14199((class_2394)class_2398.field_17430, this.method_23317(), this.method_23318(), this.method_23321(), this.ammunition == ModItems.TNT_CANNONBALL ? 10 : 5, 0.2, 0.2, 0.2, 0.035);
        serverWorld.method_43128(null, this.method_23317(), this.method_23318(), this.method_23321(), this.ammunition == ModItems.IRON_CANNONBALL || this.ammunition == ModItems.HEAVY_IRON_CANNONBALL ? class_3417.field_14833 : class_3417.field_28973, class_3419.field_15245, this.ammunition == ModItems.TNT_CANNONBALL ? 1.6f : 1.35f, this.ammunition == ModItems.IRON_CANNONBALL || this.ammunition == ModItems.HEAVY_IRON_CANNONBALL ? 0.55f : 0.72f);
    }

    public class_1792 getAmmunitionItem() {
        return this.ammunition;
    }

    public void method_5652(class_2487 nbt) {
        super.method_5652(nbt);
        nbt.method_10582("Ammunition", class_7923.field_41178.method_10221((Object)this.ammunition).toString());
        nbt.method_10549("LaunchX", this.launchX);
        nbt.method_10549("LaunchZ", this.launchZ);
        nbt.method_10548("MaximumRange", this.maximumRange);
    }

    public void method_5749(class_2487 nbt) {
        class_1792 loaded;
        super.method_5749(nbt);
        this.launchX = nbt.method_10545("LaunchX") ? nbt.method_10574("LaunchX") : this.method_23317();
        double d = this.launchZ = nbt.method_10545("LaunchZ") ? nbt.method_10574("LaunchZ") : this.method_23321();
        if (nbt.method_10545("MaximumRange")) {
            this.maximumRange = nbt.method_10583("MaximumRange");
        }
        if (nbt.method_10545("Ammunition") && ModItems.isCannonball(loaded = (class_1792)class_7923.field_41178.method_10223(new class_2960(nbt.method_10558("Ammunition"))))) {
            this.ammunition = loaded;
            this.method_16940(new class_1799((class_1935)this.ammunition));
        }
    }
}

