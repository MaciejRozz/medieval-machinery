/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 *  net.minecraft.class_2338
 *  net.minecraft.class_2487
 *  net.minecraft.class_2499
 *  net.minecraft.class_2520
 *  net.minecraft.class_2586
 *  net.minecraft.class_2596
 *  net.minecraft.class_2602
 *  net.minecraft.class_2622
 *  net.minecraft.class_2680
 *  org.jetbrains.annotations.Nullable
 */
package com.medievalmachinery.mod.block.ammunition;

import com.medievalmachinery.mod.platform.RegisteredContent;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2520;
import net.minecraft.class_2586;
import net.minecraft.class_2596;
import net.minecraft.class_2602;
import net.minecraft.class_2622;
import net.minecraft.class_2680;
import org.jetbrains.annotations.Nullable;

public final class PotionPayloadBlockEntity
extends class_2586 {
    private final List<class_1799> stacks = new ArrayList<class_1799>(3);

    public PotionPayloadBlockEntity(class_2338 pos, class_2680 state) {
        super(RegisteredContent.potionPayload(), pos, state);
    }

    public boolean add(class_1799 stack) {
        if (stack.method_7960() || this.stacks.size() >= 3) {
            return false;
        }
        this.stacks.add(stack.method_46651(1));
        this.method_5431();
        if (this.field_11863 != null) {
            this.field_11863.method_8413(this.field_11867, this.method_11010(), this.method_11010(), 3);
        }
        return true;
    }

    public class_1799 removeLast() {
        if (this.stacks.isEmpty()) {
            return class_1799.field_8037;
        }
        class_1799 result = this.stacks.remove(this.stacks.size() - 1);
        this.method_5431();
        return result;
    }

    public int size() {
        return this.stacks.size();
    }

    public List<class_1799> copyStacks() {
        ArrayList<class_1799> result = new ArrayList<class_1799>(this.stacks.size());
        for (class_1799 stack : this.stacks) {
            result.add(stack.method_7972());
        }
        return result;
    }

    public class_2487 method_16887() {
        return this.method_38244();
    }

    @Nullable
    public class_2596<class_2602> method_38235() {
        return class_2622.method_38585((class_2586)this);
    }

    protected void method_11007(class_2487 nbt) {
        super.method_11007(nbt);
        class_2499 list = new class_2499();
        for (class_1799 stack : this.stacks) {
            list.add((Object)stack.method_7953(new class_2487()));
        }
        nbt.method_10566("Payloads", (class_2520)list);
    }

    public void method_11014(class_2487 nbt) {
        super.method_11014(nbt);
        this.stacks.clear();
        class_2499 list = nbt.method_10554("Payloads", 10);
        for (int i = 0; i < list.size() && i < 3; ++i) {
            this.stacks.add(class_1799.method_7915((class_2487)list.method_10602(i)));
        }
    }
}

