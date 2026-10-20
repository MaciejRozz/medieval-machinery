/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 *  net.fabricmc.fabric.api.networking.v1.PacketByteBufs
 *  net.minecraft.class_2338
 *  net.minecraft.class_2540
 *  net.minecraft.class_2561
 *  net.minecraft.class_2586
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_332
 *  net.minecraft.class_342
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5250
 */
package com.medievalmachinery.mod.client.screen;

import com.medievalmachinery.mod.block.machine.guillotine.GuillotineBlockEntity;
import com.medievalmachinery.mod.network.AdminGuiNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5250;

@Environment(value=EnvType.CLIENT)
public final class GuillotineAdminScreen
extends class_437 {
    private static final int PANEL_WIDTH = 360;
    private static final int PANEL_HEIGHT = 348;
    private final class_2338 guillotinePos;
    private class_342 nameField;
    private class_342 commandField;

    public GuillotineAdminScreen(class_2338 guillotinePos) {
        super((class_2561)class_2561.method_43471((String)"gui.medievalmachinery.title"));
        this.guillotinePos = guillotinePos;
    }

    protected void method_25426() {
        int left = (this.field_22789 - 360) / 2;
        int top = (this.field_22790 - 348) / 2;
        GuillotineBlockEntity guillotine = this.getGuillotine();
        this.nameField = new class_342(this.field_22793, left + 15, top + 43, 330, 20, (class_2561)class_2561.method_43471((String)"gui.medievalmachinery.name"));
        this.nameField.method_1880(64);
        this.nameField.method_1852(guillotine == null ? "" : guillotine.getConfiguredName());
        this.method_37063((class_364)this.nameField);
        this.method_37063((class_364)this.createModeButton(left + 15, top + 76, 155, 20, "gui.medievalmachinery.none", 0));
        this.method_37063((class_364)this.createModeButton(left + 190, top + 76, 155, 20, "gui.medievalmachinery.adventure", 1));
        this.method_37063((class_364)this.createModeButton(left + 15, top + 102, 155, 20, "gui.medievalmachinery.ban24", 2));
        this.method_37063((class_364)this.createModeButton(left + 190, top + 102, 155, 20, "gui.medievalmachinery.permanent", 3));
        this.method_37063((class_364)this.createModeButton(left + 15, top + 128, 330, 20, "gui.medievalmachinery.custom", 4));
        this.commandField = new class_342(this.field_22793, left + 15, top + 174, 330, 20, (class_2561)class_2561.method_43471((String)"gui.medievalmachinery.command"));
        this.commandField.method_1880(512);
        this.commandField.method_1852(guillotine == null ? "" : guillotine.getCustomCommand());
        this.updateCommandSuggestion(this.commandField.method_1882());
        this.commandField.method_1863(this::updateCommandSuggestion);
        this.method_37063((class_364)this.commandField);
        this.method_37063((class_364)this.toggleButton(left + 15, top + 206, 155, 20, "gui.medievalmachinery.broadcast", 10));
        this.method_37063((class_364)this.toggleButton(left + 190, top + 206, 155, 20, "gui.medievalmachinery.head_drop", 22));
        this.method_37063((class_364)this.toggleButton(left + 15, top + 232, 155, 20, "gui.medievalmachinery.executioner", 24));
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medievalmachinery.save"), button -> this.saveTextFields()).method_46434(left + 190, top + 232, 75, 20).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.medievalmachinery.close"), button -> this.method_25419()).method_46434(left + 270, top + 232, 75, 20).method_46431());
    }

    private void updateCommandSuggestion(String value) {
        this.commandField.method_1887(value == null || value.isEmpty() ? class_2561.method_43471((String)"gui.medievalmachinery.command_hint").getString() : "");
    }

    private class_4185 createModeButton(int x, int y, int width, int height, String translationKey, int action) {
        return class_4185.method_46430((class_2561)class_2561.method_43471((String)translationKey), button -> this.sendAction(action, "")).method_46434(x, y, width, height).method_46431();
    }

    private class_4185 toggleButton(int x, int y, int width, int height, String translationKey, int action) {
        return class_4185.method_46430((class_2561)class_2561.method_43471((String)translationKey), button -> this.sendAction(action, "")).method_46434(x, y, width, height).method_46431();
    }

    private void saveTextFields() {
        this.sendAction(20, this.nameField.method_1882());
        this.sendAction(21, this.commandField.method_1882());
    }

    private void sendAction(int action, String textValue) {
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(this.guillotinePos);
        buf.method_10804(action);
        buf.method_10788(textValue == null ? "" : textValue, 512);
        ClientPlayNetworking.send((class_2960)AdminGuiNetworking.ADMIN_GUI_ACTION, (class_2540)buf);
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        int left = (this.field_22789 - 360) / 2;
        int top = (this.field_22790 - 348) / 2;
        context.method_25294(left, top, left + 360, top + 348, -585098730);
        context.method_49601(left, top, 360, 348, -4678818);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, top + 12, -1781088);
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medievalmachinery.name"), left + 15, top + 31, -4210753);
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medievalmachinery.command"), left + 15, top + 158, -4210753);
        GuillotineBlockEntity guillotine = this.getGuillotine();
        class_5250 mode = guillotine == null ? class_2561.method_43470((String)"?") : class_2561.method_43471((String)guillotine.getPunishmentMode().getTranslationKey());
        boolean broadcast = guillotine != null && guillotine.isBroadcastExecution();
        boolean headDrop = guillotine == null || guillotine.isHeadDropEnabled();
        boolean executioner = guillotine == null || guillotine.isExecutionerEnabled();
        String lastExecution = guillotine == null ? "?" : guillotine.getLastExecutionSummary();
        int total = guillotine == null ? 0 : guillotine.getExecutionCount();
        int today = guillotine == null ? 0 : guillotine.getExecutionsToday();
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medievalmachinery.active", (Object[])new Object[]{mode}), left + 15, top + 272, -1781088);
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medievalmachinery.broadcast_state", (Object[])new Object[]{this.onOff(broadcast)}), left + 245, top + 272, -1781088);
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medievalmachinery.head_state", (Object[])new Object[]{this.onOff(headDrop)}), left + 15, top + 288, -4210753);
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medievalmachinery.executioner_state", (Object[])new Object[]{this.onOff(executioner)}), left + 190, top + 288, -4210753);
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medievalmachinery.executions", (Object[])new Object[]{today, total}), left + 15, top + 318, -4210753);
        context.method_27535(this.field_22793, (class_2561)class_2561.method_43469((String)"gui.medievalmachinery.last", (Object[])new Object[]{lastExecution}), left + 15, top + 332, -4210753);
        super.method_25394(context, mouseX, mouseY, delta);
    }

    private String onOff(boolean value) {
        return class_2561.method_43471((String)(value ? "gui.medievalmachinery.on" : "gui.medievalmachinery.off")).getString();
    }

    private GuillotineBlockEntity getGuillotine() {
        class_310 client = class_310.method_1551();
        if (client.field_1687 == null) {
            return null;
        }
        class_2586 class_25862 = client.field_1687.method_8321(this.guillotinePos);
        if (class_25862 instanceof GuillotineBlockEntity) {
            GuillotineBlockEntity guillotine = (GuillotineBlockEntity)class_25862;
            return guillotine;
        }
        return null;
    }

    public boolean method_25421() {
        return false;
    }
}

