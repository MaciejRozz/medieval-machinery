/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1646
 *  net.minecraft.class_1937
 *  net.minecraft.class_2940
 *  net.minecraft.class_2941
 *  net.minecraft.class_2943
 *  net.minecraft.class_2945
 *  net.minecraft.class_3218
 *  net.minecraft.class_4140
 *  net.minecraft.class_4208
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.medievalmachinery.mod.mixin;

import com.medievalmachinery.mod.npc.organization.OrderStyle;
import com.medievalmachinery.mod.npc.organization.RoyalArmorerOrderCarrier;
import com.medievalmachinery.mod.npc.organization.RoyalSmelterBindingState;
import com.medievalmachinery.mod.registry.ModBlocks;
import com.medievalmachinery.mod.registry.ModVillagers;
import net.minecraft.class_1646;
import net.minecraft.class_1937;
import net.minecraft.class_2940;
import net.minecraft.class_2941;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
import net.minecraft.class_4140;
import net.minecraft.class_4208;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1646.class})
public abstract class RoyalArmorerVillagerOrderMixin
implements RoyalArmorerOrderCarrier {
    @Unique
    private static final class_2940<Integer> MEDIEVAL_MACHINERY_ROYAL_ORDER = class_2945.method_12791(class_1646.class, (class_2941)class_2943.field_13327);

    @Inject(method={"method_5693"}, at={@At(value="TAIL")})
    private void medievalMachinery$trackRoyalOrder(CallbackInfo ci) {
        ((class_1646)this).method_5841().method_12784(MEDIEVAL_MACHINERY_ROYAL_ORDER, (Object)0);
    }

    @Inject(method={"method_5773"}, at={@At(value="TAIL")})
    private void medievalMachinery$syncRoyalOrder(CallbackInfo ci) {
        class_1937 class_19372;
        class_1646 self = (class_1646)this;
        if (self.method_37908().field_9236 || self.field_6012 % 20 != 0) {
            return;
        }
        int ordinal = 0;
        if (self.method_7231().method_16924() == ModVillagers.ROYAL_ARMORER && (class_19372 = self.method_37908()) instanceof class_3218) {
            RoyalSmelterBindingState.Binding binding;
            class_3218 world = (class_3218)class_19372;
            class_4208 job = self.method_18868().method_46873(class_4140.field_18439).orElse(null);
            if (job != null && job.method_19442().equals(world.method_27983()) && world.method_8320(job.method_19446()).method_27852(ModBlocks.ROYAL_SMELTER) && (binding = RoyalSmelterBindingState.get(world).get(job.method_19446())) != null && binding.orderStyle() != null) {
                ordinal = binding.orderStyle().ordinal();
            }
        }
        ordinal = Math.max(0, Math.min(OrderStyle.values().length - 1, ordinal));
        if ((Integer)self.method_5841().method_12789(MEDIEVAL_MACHINERY_ROYAL_ORDER) != ordinal) {
            self.method_5841().method_12778(MEDIEVAL_MACHINERY_ROYAL_ORDER, (Object)ordinal);
        }
    }

    @Override
    public int medievalMachinery$getRoyalArmorerOrderOrdinal() {
        return (Integer)((class_1646)this).method_5841().method_12789(MEDIEVAL_MACHINERY_ROYAL_ORDER);
    }
}

