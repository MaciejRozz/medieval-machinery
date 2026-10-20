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
 *  net.minecraft.class_2960
 *  net.minecraft.class_332
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5250
 */
package com.medievalmachinery.mod.client.garrison;

import com.medievalmachinery.mod.network.WatchpostNetworking;
import com.medievalmachinery.mod.screen.garrison.WatchpostScreenHandler;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5250;

@Environment(value=EnvType.CLIENT)
public final class WatchpostPromotionScreen
extends class_437 {
    private final class_2338 watchpostPos;
    private final List<WatchpostScreenHandler.PromotionOption> options;

    public WatchpostPromotionScreen(class_2338 watchpostPos, List<WatchpostScreenHandler.PromotionOption> options) {
        super((class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.promotion_title"));
        this.watchpostPos = watchpostPos;
        this.options = options == null ? List.of() : List.copyOf(options);
    }

    protected void method_25426() {
        int y = 52;
        for (int i = 0; i < Math.min(6, this.options.size()); ++i) {
            WatchpostScreenHandler.PromotionOption option = this.options.get(i);
            String target = "general".equals(option.targetRank()) ? "General" : "Hauptmann";
            class_5250 label = class_2561.method_43470((String)(option.name() + " Lv." + option.level() + " -> " + target + " | " + option.emeraldCost() + " Sm + " + option.diamondCost() + " Dia"));
            this.method_37063((class_364)class_4185.method_46430((class_2561)label, b -> this.promote(option)).method_46434(this.field_22789 / 2 - 150, y, 300, 20).method_46431());
            y += 36;
        }
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"gui.cancel"), b -> this.method_25419()).method_46434(this.field_22789 / 2 - 75, this.field_22790 - 36, 150, 20).method_46431());
    }

    private void promote(WatchpostScreenHandler.PromotionOption option) {
        if (this.field_22787 == null || this.field_22787.field_1724 == null) {
            return;
        }
        class_2540 buf = PacketByteBufs.create();
        buf.method_10807(this.watchpostPos);
        buf.method_10797(option.soldierId());
        ClientPlayNetworking.send((class_2960)WatchpostNetworking.PROMOTE, (class_2540)buf);
        this.method_25419();
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context);
        context.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, 20, 0xFFFFFF);
        if (this.options.isEmpty()) {
            context.method_27534(this.field_22793, (class_2561)class_2561.method_43471((String)"gui.medieval-machinery.watchpost.no_promotions"), this.field_22789 / 2, 62, 0xAAAAAA);
        } else {
            int y = 74;
            for (int i = 0; i < Math.min(6, this.options.size()); ++i) {
                WatchpostScreenHandler.PromotionOption option = this.options.get(i);
                String req = "Voraussetzungen: Lv " + option.level() + "/" + option.requiredLevel() + ", Ritter " + option.knightCount() + "/" + option.requiredKnights();
                if (option.requiredSoldiers() > 0) {
                    req = req + ", Soldaten " + option.soldierCount() + "/" + option.requiredSoldiers();
                }
                context.method_27534(this.field_22793, (class_2561)class_2561.method_43470((String)req), this.field_22789 / 2, y, 0xBBBBBB);
                y += 36;
            }
        }
        super.method_25394(context, mouseX, mouseY, delta);
    }

    public boolean method_25421() {
        return false;
    }
}

