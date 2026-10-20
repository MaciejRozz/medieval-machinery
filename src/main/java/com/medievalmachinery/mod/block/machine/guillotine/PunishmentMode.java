/*
 * Decompiled with CFR 0.152.
 */
package com.medievalmachinery.mod.block.machine.guillotine;

public enum PunishmentMode {
    NONE("gui.medievalmachinery.none"),
    ADVENTURE_10_MINUTES("gui.medievalmachinery.adventure"),
    BAN_24_HOURS("gui.medievalmachinery.ban24"),
    PERMANENT_BAN("gui.medievalmachinery.permanent"),
    CUSTOM_COMMAND("gui.medievalmachinery.custom");

    private final String translationKey;

    private PunishmentMode(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public PunishmentMode next() {
        PunishmentMode[] values = PunishmentMode.values();
        return values[(this.ordinal() + 1) % values.length];
    }

    public static PunishmentMode fromString(String value) {
        if (value == null) {
            return NONE;
        }
        try {
            return PunishmentMode.valueOf(value);
        }
        catch (IllegalArgumentException ignored) {
            return NONE;
        }
    }
}

