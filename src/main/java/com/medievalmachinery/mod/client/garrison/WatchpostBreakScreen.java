/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2338
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.garrison;

import com.medievalmachinery.mod.client.garrison.WatchpostBreakClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class WatchpostBreakScreen
extends class_437 {
    private final class_2338 watchpostPos;
    private final String castleName;

    public WatchpostBreakScreen(class_2338 watchpostPos, String castleName) {
        super((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.break.title"));
        this.watchpostPos = watchpostPos.method_10062();
        this.castleName = castleName == null ? "" : castleName;
    }

    protected void method_25426() {
        int center = this.field_22789 / 2;
        int top = this.field_22790 / 2 - 70;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.break.move"), b -> this.choose(0)).method_46434(center - 120, top + 50, 240, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.break.dissolve"), b -> this.choose(1)).method_46434(center - 120, top + 78, 240, 20).method_46431());
    }

    private void choose(int action) {
        WatchpostBreakClient.send(this.watchpostPos, action);
        this.method_25419();
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        context.method_25294(0, 0, this.field_22789, this.field_22790, -1442840576);
        int center = this.field_22789 / 2;
        int top = this.field_22790 / 2 - 70;
        context.method_27534(this.field_22793, this.field_22785, center, top, 0xFFFFFF);
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medieval-machinery.watchpost.break.castle", (Object[])new Object[]{this.castleName}), center, top + 22, 0xD8D8D8);
        context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.break.hint"), center, top + 140, 0xB0B0B0);
        super.method_25394(context, mouseX, mouseY, delta);
    }

    public boolean method_25422() {
        return false;
    }

    public boolean method_25421() {
        return false;
    }
}

