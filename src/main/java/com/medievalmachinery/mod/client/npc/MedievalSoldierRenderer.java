/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1268
 *  net.minecraft.class_1304
 *  net.minecraft.class_1308
 *  net.minecraft.class_1753
 *  net.minecraft.class_1764
 *  net.minecraft.class_1799
 *  net.minecraft.class_1819
 *  net.minecraft.class_2960
 *  net.minecraft.class_3883
 *  net.minecraft.class_3887
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_5617$class_5618
 *  net.minecraft.class_572
 *  net.minecraft.class_572$class_573
 *  net.minecraft.class_583
 *  net.minecraft.class_927
 *  net.minecraft.class_970
 *  net.minecraft.class_989
 */
package com.medievalmachinery.mod.client.npc;

import com.medievalmachinery.mod.MedievalMachinery;
import com.medievalmachinery.mod.client.npc.MedievalSoldierModel;
import com.medievalmachinery.mod.client.npc.MedievalSoldierOrderLayer;
import com.medievalmachinery.mod.client.npc.MedievalSoldierVanillaArmorModel;
import com.medievalmachinery.mod.client.npc.QuartermasterFarmerHatFeatureRenderer;
import com.medievalmachinery.mod.npc.core.NpcRangedVisualState;
import com.medievalmachinery.mod.npc.core.NpcRole;
import com.medievalmachinery.mod.npc.entity.MedievalSoldierEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1304;
import net.minecraft.class_1308;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1799;
import net.minecraft.class_1819;
import net.minecraft.class_2960;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_5617;
import net.minecraft.class_572;
import net.minecraft.class_583;
import net.minecraft.class_927;
import net.minecraft.class_970;
import net.minecraft.class_989;

@Environment(value=EnvType.CLIENT)
public final class MedievalSoldierRenderer
extends class_927<MedievalSoldierEntity, MedievalSoldierModel> {
    private static final class_2960 DEFAULT_TEXTURE = MedievalMachinery.id("textures/entity/npc/recruit_villager.png");

    public MedievalSoldierRenderer(class_5617.class_5618 context) {
        super(context, (class_583)new MedievalSoldierModel(context.method_32167(MedievalSoldierModel.LAYER)), 0.5f);
        this.method_4046(new MedievalSoldierOrderLayer((class_3883<MedievalSoldierEntity, MedievalSoldierModel>)this));
        this.method_4046(new QuartermasterFarmerHatFeatureRenderer((class_3883<MedievalSoldierEntity, MedievalSoldierModel>)this, context));
        this.method_4046((class_3887)new class_970((class_3883)this, (class_572)new MedievalSoldierVanillaArmorModel(context.method_32167(MedievalSoldierVanillaArmorModel.INNER_LAYER)), (class_572)new MedievalSoldierVanillaArmorModel(context.method_32167(MedievalSoldierVanillaArmorModel.OUTER_LAYER)), context.method_48481()));
        this.method_4046((class_3887)new class_989((class_3883)this, context.method_43338()));
    }

    public void render(MedievalSoldierEntity entity, float yaw, float tickDelta, class_4587 matrices, class_4597 vertices, int light) {
        boolean machineHeldOverride;
        class_1799 farmingVisualOriginal = class_1799.field_8037;
        boolean farmingHandOverride = entity.isQuartermasterCompostingVisual() || entity.isFarmPlantVisualActive((float)entity.field_6012 + tickDelta);
        class_1799 machineHeldVisual = !farmingHandOverride ? entity.getMachineHeldVisual() : class_1799.field_8037;
        boolean bl = machineHeldOverride = !machineHeldVisual.method_7960();
        if (farmingHandOverride || machineHeldOverride) {
            farmingVisualOriginal = entity.method_6047().method_7972();
            entity.method_5673(class_1304.field_6173, farmingHandOverride ? entity.getQuartermasterWorkVisualStack() : machineHeldVisual.method_7972());
        }
        this.updateArmPoses(entity);
        class_1799 hiddenQuartermasterHelmet = class_1799.field_8037;
        if (entity.getNpcRole() == NpcRole.QUARTERMASTER && !entity.method_6118(class_1304.field_6169).method_7960()) {
            hiddenQuartermasterHelmet = entity.method_6118(class_1304.field_6169).method_7972();
            entity.method_5673(class_1304.field_6169, class_1799.field_8037);
        }
        matrices.method_22903();
        if (entity.method_5765()) {
            matrices.method_22904(0.0, -0.31, 0.0);
        }
        super.method_4072((class_1308)entity, yaw, tickDelta, matrices, vertices, light);
        matrices.method_22909();
        if (!hiddenQuartermasterHelmet.method_7960()) {
            entity.method_5673(class_1304.field_6169, hiddenQuartermasterHelmet);
        }
        if (farmingHandOverride || machineHeldOverride) {
            entity.method_5673(class_1304.field_6173, farmingVisualOriginal);
        }
    }

    private void updateArmPoses(MedievalSoldierEntity entity) {
        class_1799 main = entity.method_6047();
        class_1799 off = entity.method_6079();
        if (entity.method_6115() && entity.method_6030().method_7909() instanceof class_1819) {
            boolean shieldInMainHand = entity.method_6058() == class_1268.field_5808;
            ((MedievalSoldierModel)this.method_4038()).field_3395 = shieldInMainHand ? class_572.class_573.field_3406 : (main.method_7960() ? class_572.class_573.field_3409 : class_572.class_573.field_3410);
            ((MedievalSoldierModel)this.method_4038()).field_3399 = shieldInMainHand ? (off.method_7960() ? class_572.class_573.field_3409 : class_572.class_573.field_3410) : class_572.class_573.field_3406;
            return;
        }
        if (entity.isRangedAttackAnimating() && main.method_7909() instanceof class_1753) {
            ((MedievalSoldierModel)this.method_4038()).field_3395 = class_572.class_573.field_3403;
            ((MedievalSoldierModel)this.method_4038()).field_3399 = class_572.class_573.field_3409;
            return;
        }
        if (entity.isRangedAttackAnimating() && main.method_7909() instanceof class_1764) {
            class_572.class_573 pose;
            NpcRangedVisualState visual = entity.getRangedVisualState();
            ((MedievalSoldierModel)this.method_4038()).field_3395 = pose = visual == NpcRangedVisualState.LOAD ? class_572.class_573.field_3410 : (visual == NpcRangedVisualState.PREPARE ? class_572.class_573.field_3405 : class_572.class_573.field_3408);
            ((MedievalSoldierModel)this.method_4038()).field_3399 = class_572.class_573.field_3410;
            return;
        }
        ((MedievalSoldierModel)this.method_4038()).field_3395 = main.method_7960() ? class_572.class_573.field_3409 : class_572.class_573.field_3410;
        ((MedievalSoldierModel)this.method_4038()).field_3399 = off.method_7960() ? class_572.class_573.field_3409 : class_572.class_573.field_3410;
    }

    public class_2960 getTexture(MedievalSoldierEntity entity) {
        String appearance = entity.getAppearanceId();
        if (appearance == null || appearance.equals("villager")) {
            return DEFAULT_TEXTURE;
        }
        return MedievalMachinery.id("textures/entity/npc/skins/" + appearance + ".png");
    }

    protected void scale(MedievalSoldierEntity entity, class_4587 matrices, float amount) {
        matrices.method_22905(0.9375f, 0.9375f, 0.9375f);
    }
}

