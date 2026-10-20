/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2561
 */
package com.medievalmachinery.mod.client.command;

import com.medievalmachinery.mod.client.command.ClientCommandGroupStatus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;

@Environment(value=EnvType.CLIENT)
public final class CommandGroupPreferences {
    private CommandGroupPreferences() {
    }

    public static class_2561 displayName(int group) {
        ClientCommandGroupStatus.Group server = ClientCommandGroupStatus.group(group);
        if (server != null) {
            class_2561 fixedName = CommandGroupPreferences.fixedGroupName(server.type());
            if (fixedName != null) {
                return fixedName;
            }
            return class_2561.method_43470((String)server.name());
        }
        return switch (group) {
            case 0 -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.none");
            case 1 -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.infantry");
            case 2 -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.shooters");
            case 3 -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.knights");
            case 4 -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.mounted");
            case 5 -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.machinists");
            default -> class_2561.method_43470((String)"-");
        };
    }

    private static class_2561 fixedGroupName(String type) {
        if (type == null) {
            return null;
        }
        return switch (type) {
            case "infantry" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.infantry");
            case "ranged" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.shooters");
            case "knights" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.knights");
            case "mounted" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.mounted");
            case "machinists" -> class_2561.method_43471((String)"screen.medieval-machinery.command.group.machinists");
            default -> null;
        };
    }

    public static boolean isCustomEnabled(int group) {
        ClientCommandGroupStatus.Group server = ClientCommandGroupStatus.group(group);
        return server != null && "custom".equals(server.type());
    }

    public static int firstFreeCustomGroup() {
        int next = ClientCommandGroupStatus.groups().size() + 1;
        return next <= 512 ? next : -1;
    }

    public static String customName(int group) {
        ClientCommandGroupStatus.Group server = ClientCommandGroupStatus.group(group);
        return server == null ? "" : server.name();
    }

    public static void removeCustomGroup(int group) {
    }
}

