/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.terraformersmc.modmenu.api.ConfigScreenFactory
 *  com.terraformersmc.modmenu.api.ModMenuApi
 *  me.shedaniel.autoconfig.AutoConfig
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_437
 */
package com.medievalmachinery.mod.client.config;

import com.medievalmachinery.mod.config.MedievalMachineryConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_437;

@Environment(value=EnvType.CLIENT)
public final class MedievalMachineryModMenu
implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> (class_437)AutoConfig.getConfigScreen(MedievalMachineryConfig.class, (class_437)parent).get();
    }
}

