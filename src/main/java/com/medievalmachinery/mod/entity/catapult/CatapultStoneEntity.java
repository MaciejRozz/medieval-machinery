/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1282
 *  net.minecraft.class_1293
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1309
 *  net.minecraft.class_1675
 *  net.minecraft.class_1676
 *  net.minecraft.class_1747
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1844
 *  net.minecraft.class_1922
 *  net.minecraft.class_1928
 *  net.minecraft.class_1935
 *  net.minecraft.class_1937
 *  net.minecraft.class_1937$class_7867
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_2388
 *  net.minecraft.class_239
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_2392
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_2520
 *  net.minecraft.class_2596
 *  net.minecraft.class_2602
 *  net.minecraft.class_2604
 *  net.minecraft.class_2680
 *  net.minecraft.class_2940
 *  net.minecraft.class_2941
 *  net.minecraft.class_2943
 *  net.minecraft.class_2945
 *  net.minecraft.class_3218
 *  net.minecraft.class_3856
 *  net.minecraft.class_3965
 *  net.minecraft.class_3966
 *  net.minecraft.class_4538
 */
package com.medievalmachinery.mod.entity.catapult;

import com.medievalmachinery.mod.catapult.CatapultAmmunition;
import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.medievalmachinery.mod.entity.machine.AbstractMedievalMachineEntity;
import com.medievalmachinery.mod.item.PotionPayloadItem;
import com.medievalmachinery.mod.platform.RegisteredContent;
import com.medievalmachinery.mod.registry.ModItems;
import java.util.List;
import net.minecraft.class_1282;
import net.minecraft.class_1293;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1675;
import net.minecraft.class_1676;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1844;
import net.minecraft.class_1922;
import net.minecraft.class_1928;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_2388;
import net.minecraft.class_239;
import net.minecraft.class_2392;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_2596;
import net.minecraft.class_2602;
import net.minecraft.class_2604;
import net.minecraft.class_2680;
import net.minecraft.class_2940;
import net.minecraft.class_2941;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
import net.minecraft.class_3856;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_4538;

public final class CatapultStoneEntity
extends class_1676
implements class_3856 {
    private static final class_2940<class_1799> PAYLOAD = class_2945.method_12791(CatapultStoneEntity.class, (class_2941)class_2943.field_13322);
    private static final int BUNDLE_SPLIT_TICKS = 12;
    private static final int BUNDLE_FRAGMENT_COUNT = 8;
    private static final float BUNDLE_FRAGMENT_DAMAGE = 60.0f;
    private static final float BUNDLE_FRAGMENT_EXPLOSION_POWER = 0.5f;
    private static final double BUNDLE_FRAGMENT_SPREAD = 0.115;
    private int ageTicks;
    private boolean cobblestoneBundleFragment;

    public CatapultStoneEntity(class_1299<? extends CatapultStoneEntity> type, class_1937 world) {
        super(type, world);
    }

    public CatapultStoneEntity(class_1937 world, double x, double y, double z, class_1799 payload) {
        this(RegisteredContent.catapultStone(), world);
        this.method_5814(x, y, z);
        this.setPayload(payload);
    }

    protected void method_5693() {
        this.field_6011.method_12784(PAYLOAD, (Object)new class_1799((class_1935)class_1802.field_20412));
    }

    public void setPayload(class_1799 stack) {
        this.field_6011.method_12778(PAYLOAD, (Object)stack.method_46651(1));
    }

    public class_1799 getPayload() {
        return (class_1799)this.field_6011.method_12789(PAYLOAD);
    }

    public void method_5773() {
        super.method_5773();
        ++this.ageTicks;
        if (!this.method_37908().field_9236 && !this.cobblestoneBundleFragment && this.getPayload().method_31574(ModItems.COBBLESTONE_BUNDLE) && this.ageTicks >= 12) {
            this.splitCobblestoneBundle((class_3218)this.method_37908());
            return;
        }
        CatapultStoneEntity catapultStoneEntity = this;
        class_239 hit = class_1675.method_49997((class_1297)this, x$0 -> catapultStoneEntity.method_26958((class_1297)x$0));
        if (hit.method_17783() != class_239.class_240.field_1333) {
            this.method_7488(hit);
        }
        if (this.method_31481()) {
            return;
        }
        class_243 velocity = this.method_18798();
        this.method_5814(this.method_23317() + velocity.field_1352, this.method_23318() + velocity.field_1351, this.method_23321() + velocity.field_1350);
        CatapultAmmunition.Profile profile = CatapultAmmunition.resolve(this.getPayload(), this.method_37908(), this.method_24515());
        this.method_18799(velocity.method_1021(1.0 - (double)profile.drag()).method_1031(0.0, -0.05 * (double)profile.gravityMultiplier(), 0.0));
        if (this.ageTicks > 600 || this.method_23318() < (double)this.method_37908().method_31607()) {
            this.method_31472();
        }
    }

    protected void method_7454(class_3966 hit) {
        super.method_7454(hit);
        CatapultAmmunition.Profile profile = CatapultAmmunition.resolve(this.getPayload(), this.method_37908(), hit.method_17782().method_24515());
        class_1297 target = hit.method_17782();
        if (profile.damage() > 0.0f) {
            class_1282 source = this.method_48923().method_48811((class_1297)this, this.method_24921());
            target.method_5643(source, this.cobblestoneBundleFragment ? 60.0f : profile.damage());
        }
        this.impact(hit.method_17784(), target.method_24515(), target);
    }

    protected void method_24920(class_3965 hit) {
        super.method_24920(hit);
        this.impact(hit.method_17784(), hit.method_17777(), null);
    }

    private void impact(class_243 pos, class_2338 blockPos, class_1297 directTarget) {
        class_1937 class_19372 = this.method_37908();
        if (class_19372 instanceof class_3218) {
            class_3218 serverWorld = (class_3218)class_19372;
            CatapultAmmunition.Profile profile = CatapultAmmunition.resolve(this.getPayload(), (class_1937)serverWorld, blockPos);
            if (profile.potion()) {
                this.applyPotionPayload(serverWorld, pos);
            } else if (profile.explosive()) {
                this.applyExplosivePayload(serverWorld, pos, directTarget);
            } else if (profile.fire()) {
                this.applyFirePayload(serverWorld, pos, blockPos);
            } else {
                this.spawnImpactParticles(serverWorld, pos);
                if (this.cobblestoneBundleFragment) {
                    serverWorld.method_8437((class_1297)this, pos.field_1352, pos.field_1351, pos.field_1350, 0.5f, class_1937.class_7867.field_40891);
                } else {
                    if (profile.cobblestoneBundle()) {
                        this.splitCobblestoneBundle(serverWorld);
                        return;
                    }
                    this.tryDamageBlocks(serverWorld, blockPos, profile.blockDamage());
                }
            }
        }
        this.method_31472();
    }

    private void splitCobblestoneBundle(class_3218 world) {
        class_243 base = this.method_18798();
        class_243 forward = base.method_1027() > 1.0E-6 ? base.method_1029() : new class_243(0.0, 0.0, 1.0);
        class_243 side = new class_243(-forward.field_1350, 0.0, forward.field_1352);
        side = side.method_1027() < 1.0E-6 ? new class_243(1.0, 0.0, 0.0) : side.method_1029();
        class_243 up = side.method_1036(forward).method_1029();
        for (int i = 0; i < 8; ++i) {
            int column = i % 4;
            int row = i / 4;
            double horizontal = ((double)column - 1.5) * 0.115;
            double vertical = ((double)row - 0.5) * 0.115;
            class_243 fragmentVelocity = base.method_1019(side.method_1021(horizontal)).method_1019(up.method_1021(vertical));
            CatapultStoneEntity fragment = new CatapultStoneEntity((class_1937)world, this.method_23317(), this.method_23318(), this.method_23321(), new class_1799((class_1935)class_1802.field_20412));
            fragment.cobblestoneBundleFragment = true;
            fragment.method_7432(this.method_24921());
            fragment.method_18799(fragmentVelocity);
            world.method_8649((class_1297)fragment);
        }
        this.method_31472();
    }

    private void applyPotionPayload(class_3218 world, class_243 pos) {
        MedievalMachineryConfig.Catapult config = MedievalMachineryConfig.get().catapult;
        class_1799 potion = PotionPayloadItem.getStoredPotion(this.getPayload());
        if (potion.method_7960()) {
            return;
        }
        float radius = config.potionRadius;
        class_238 area = new class_238(pos, pos).method_1014((double)radius);
        List targets = world.method_8390(class_1309.class, area, entity -> entity.method_5805() && entity.method_5707(pos) <= (double)(radius * radius));
        for (class_1293 effect : class_1844.method_8067((class_1799)potion)) {
            boolean beneficial = effect.method_5579().method_5573();
            if (beneficial && !config.allowPositivePotionEffects || !beneficial && !config.allowNegativePotionEffects) continue;
            for (class_1309 target : targets) {
                double distance = Math.sqrt(target.method_5707(pos));
                double factor = 1.0 - distance / (double)radius;
                factor = (double)config.potionEdgeDurationMultiplier + factor * (1.0 - (double)config.potionEdgeDurationMultiplier);
                if (effect.method_5579().method_5561()) {
                    effect.method_5579().method_5564((class_1297)this, this.method_24921(), target, effect.method_5578(), factor);
                    continue;
                }
                int duration = Math.max(1, (int)((double)effect.method_5584() * factor));
                target.method_37222(new class_1293(effect.method_5579(), duration, effect.method_5578(), effect.method_5591(), effect.method_5581(), effect.method_5592()), this.method_24921());
            }
        }
        world.method_14199((class_2394)class_2398.field_11226, pos.field_1352, pos.field_1351, pos.field_1350, 80, (double)radius * 0.45, 0.35, (double)radius * 0.45, 0.0);
        world.method_14199((class_2394)new class_2392(class_2398.field_11218, this.getPayload()), pos.field_1352, pos.field_1351, pos.field_1350, 20, 0.45, 0.25, 0.45, 0.08);
    }

    private void applyExplosivePayload(class_3218 world, class_243 pos, class_1297 directTarget) {
        MedievalMachineryConfig config = MedievalMachineryConfig.get();
        float splashDamage = (float)config.tnt.damage * 0.6666667f;
        double radius = 4.5;
        class_1282 source = this.method_48923().method_48811((class_1297)this, this.method_24921());
        for (class_1297 entity : world.method_8333((class_1297)this, new class_238(pos, pos).method_1014(radius), candidate -> (candidate instanceof class_1309 || candidate instanceof AbstractMedievalMachineEntity) && candidate != directTarget)) {
            double distance = entity.method_19538().method_1022(pos);
            if (distance > radius) continue;
            float falloff = (float)(1.0 - distance / radius);
            entity.method_5643(source, Math.max(2.0f, splashDamage * falloff));
        }
        world.method_8437((class_1297)this, pos.field_1352, pos.field_1351, pos.field_1350, config.catapult.explosivePayloadExplosionPower, class_1937.class_7867.field_40891);
    }

    private void applyFirePayload(class_3218 world, class_243 pos, class_2338 impactPos) {
        MedievalMachineryConfig.Catapult config = MedievalMachineryConfig.get().catapult;
        this.spawnImpactParticles(world, pos);
        if (world.method_8450().method_8355(class_1928.field_19388)) {
            this.breakFirePayloadBlocks(world, impactPos, config.firePayloadMaxBrokenBlocks);
            this.igniteFireArea(world, impactPos, config.firePayloadRadius);
        }
        world.method_14199((class_2394)class_2398.field_11240, pos.field_1352, pos.field_1351, pos.field_1350, 55, 0.85, 0.45, 0.85, 0.045);
        world.method_14199((class_2394)class_2398.field_11237, pos.field_1352, pos.field_1351 + 0.15, pos.field_1350, 18, 0.65, 0.35, 0.65, 0.03);
    }

    private void breakFirePayloadBlocks(class_3218 world, class_2338 center, int maximumBlocks) {
        if (maximumBlocks <= 0) {
            return;
        }
        class_2338[] offsets = new class_2338[]{class_2338.field_10980, new class_2338(1, 0, 0), new class_2338(-1, 0, 0), new class_2338(0, 0, 1), new class_2338(0, 0, -1), new class_2338(0, 1, 0), new class_2338(0, -1, 0)};
        int broken = 0;
        for (class_2338 offset : offsets) {
            if (broken >= maximumBlocks) break;
            class_2338 target = center.method_10081((class_2382)offset);
            class_2680 state = world.method_8320(target);
            if (state.method_26215() || state.method_26214((class_1922)world, target) < 0.0f || world.method_8321(target) != null) continue;
            float hardness = state.method_26214((class_1922)world, target);
            float resistance = state.method_26204().method_9520();
            if (hardness > 3.0f || resistance > 8.0f || !world.method_8651(target, true, this.method_24921())) continue;
            ++broken;
        }
    }

    private void igniteFireArea(class_3218 world, class_2338 center, float radius) {
        int r = Math.max(1, Math.round(radius));
        double radiusSq = radius * radius;
        for (int x = -r; x <= r; ++x) {
            block1: for (int z = -r; z <= r; ++z) {
                double horizontalSq = x * x + z * z;
                if (horizontalSq > radiusSq) continue;
                double distance = Math.sqrt(horizontalSq);
                double normalized = 1.0 - distance / (double)radius;
                double chance = 0.04 + 0.48 * normalized * normalized;
                if (world.field_9229.method_43058() > chance) continue;
                for (int y = 3; y >= -2; --y) {
                    class_2680 fire;
                    class_2338 firePos = center.method_10069(x, y, z);
                    if (!world.method_8320(firePos).method_26215() || !(fire = class_2246.field_10036.method_9564()).method_26184((class_4538)world, firePos)) continue;
                    world.method_8652(firePos, fire, 3);
                    continue block1;
                }
            }
        }
    }

    private void spawnImpactParticles(class_3218 world, class_243 pos) {
        class_2680 class_26802;
        class_1792 class_17922 = this.getPayload().method_7909();
        if (class_17922 instanceof class_1747) {
            class_1747 blockItem = (class_1747)class_17922;
            class_26802 = blockItem.method_7711().method_9564();
        } else {
            class_26802 = class_2246.field_10445.method_9564();
        }
        class_2680 state = class_26802;
        world.method_14199((class_2394)new class_2388(class_2398.field_11217, state), pos.field_1352, pos.field_1351, pos.field_1350, 36, 0.65, 0.35, 0.65, 0.14);
        world.method_14199((class_2394)class_2398.field_17430, pos.field_1352, pos.field_1351 + 0.15, pos.field_1350, 5, 0.35, 0.15, 0.35, 0.03);
    }

    private void tryDamageBlocks(class_3218 world, class_2338 center, float blockDamage) {
        if (!MedievalMachineryConfig.get().catapult.blockDamageEnabled || blockDamage <= 0.0f) {
            return;
        }
        if (!world.method_8450().method_8355(class_1928.field_19388)) {
            return;
        }
        int radius = blockDamage >= 10.0f ? 2 : (blockDamage >= 4.0f ? 1 : 0);
        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    float resistance;
                    float attenuation;
                    float localDamage;
                    class_2338 target;
                    class_2680 state;
                    double distance = Math.sqrt(x * x + y * y + z * z);
                    if (distance > (double)radius + 0.25 || (state = world.method_8320(target = center.method_10069(x, y, z))).method_26215() || state.method_26214((class_1922)world, target) < 0.0f || !((localDamage = blockDamage * (attenuation = radius == 0 ? 1.0f : Math.max(0.35f, 1.0f - (float)(distance / ((double)radius + 0.75)) * 0.65f))) >= (resistance = state.method_26214((class_1922)world, target) + state.method_26204().method_9520() * 0.08f))) continue;
                    world.method_8651(target, true, this.method_24921());
                }
            }
        }
    }

    protected void method_5749(class_2487 nbt) {
        this.ageTicks = nbt.method_10550("Age");
        this.cobblestoneBundleFragment = nbt.method_10577("CobblestoneBundleFragment");
        if (nbt.method_10573("Payload", 10)) {
            this.setPayload(class_1799.method_7915((class_2487)nbt.method_10562("Payload")));
        }
    }

    protected void method_5652(class_2487 nbt) {
        nbt.method_10569("Age", this.ageTicks);
        nbt.method_10556("CobblestoneBundleFragment", this.cobblestoneBundleFragment);
        nbt.method_10566("Payload", (class_2520)this.getPayload().method_7953(new class_2487()));
    }

    public class_1799 method_7495() {
        return this.getPayload();
    }

    public class_2596<class_2602> method_18002() {
        return new class_2604((class_1297)this);
    }
}

