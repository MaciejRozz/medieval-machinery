/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.shedaniel.autoconfig.AutoConfig
 *  me.shedaniel.autoconfig.ConfigData
 *  me.shedaniel.autoconfig.annotation.Config
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$BoundedDiscrete
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$Category
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$Gui$CollapsibleObject
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$Gui$Excluded
 *  me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
 *  me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment
 */
package com.medievalmachinery.mod.config;

import java.util.LinkedHashMap;
import java.util.Map;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="medieval-machinery")
public final class MedievalMachineryConfig
implements ConfigData {
    @Comment(value="MASCHINEN / MACHINES - Kanonenmunition / cannon ammunition")
    @ConfigEntry.Category(value="machines_cannon")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded=true)
    public Cannonball stone = new Cannonball(18, 26);
    @ConfigEntry.Category(value="machines_cannon")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded=true)
    public Cannonball iron = new Cannonball(32, 29);
    @ConfigEntry.Category(value="machines_cannon")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded=true)
    public Cannonball heavyIron = new Cannonball(32, 29);
    @ConfigEntry.Category(value="machines_cannon")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded=true)
    public Cannonball tnt = new Cannonball(24, 32);
    @Comment(value="MASCHINEN / MACHINES - Katapult: Munition, Schaden und Blockwirkung / ammunition, damage and block effects")
    @ConfigEntry.Category(value="machines_catapult")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded=true)
    public Catapult catapult = new Catapult();
    @Comment(value="NPC - Automatische Waffen-/Ausruestungswahl / automatic equipment selection")
    @ConfigEntry.Category(value="npc_general")
    public boolean automaticEquipmentSelectionEnabled = true;
    @ConfigEntry.Category(value="debug")
    public boolean npcDebugEnabled = false;
    @ConfigEntry.Category(value="debug")
    public boolean verboseNavigationDebugEnabled = false;
    @ConfigEntry.Category(value="debug")
    public boolean ladderDebugEnabled = false;
    @ConfigEntry.Category(value="debug")
    public boolean rangedCombatDebugEnabled = false;
    @Comment(value="KOENIGREICH / KINGDOM - Rekrutierung / recruitment")
    @ConfigEntry.Category(value="kingdom_recruitment")
    public boolean recruitmentCostsEnabled = true;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitSwordsmanEmeraldCost = 4;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitAxemanEmeraldCost = 4;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitArcherEmeraldCost = 6;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitCrossbowmanEmeraldCost = 8;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitSpearmanEmeraldCost = 6;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitCannoneerEmeraldCost = 10;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitKnightEmeraldCost = 12;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitQuartermasterEmeraldCost = 6;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitScoutEmeraldCost = 6;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitEngineerEmeraldCost = 8;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitMountedArcherEmeraldCost = 20;
    @ConfigEntry.Category(value="kingdom_recruitment")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int recruitMountedKnightEmeraldCost = 20;
    @Comment(value="KOENIGREICH / KINGDOM - Steuern / taxes")
    @ConfigEntry.Category(value="kingdom_tax")
    public boolean taxesEnabled = true;
    @ConfigEntry.Category(value="kingdom_tax")
    @ConfigEntry.BoundedDiscrete(min=0L, max=64L)
    public int taxEmeraldsPerAdultVillager = 1;
    @ConfigEntry.Category(value="kingdom_tax")
    @ConfigEntry.BoundedDiscrete(min=8L, max=128L)
    public int taxVillagerRadius = 50;
    @ConfigEntry.Category(value="kingdom_tax")
    @ConfigEntry.BoundedDiscrete(min=1L, max=60L)
    public int taxAfkTimeoutMinutes = 5;
    @Comment(value="KOENIGREICH / KINGDOM - Befoerderungen / promotions")
    @ConfigEntry.Category(value="kingdom_promotion")
    public boolean promotionCostsEnabled = true;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=1L, max=100L)
    public int captainMinimumLevel = 10;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=0L, max=100L)
    public int captainRequiredKnights = 5;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int captainEmeraldCost = 16;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=0L, max=64L)
    public int captainDiamondCost = 4;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=1L, max=100L)
    public int generalMinimumLevel = 20;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=0L, max=100L)
    public int generalRequiredKnights = 10;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=0L, max=500L)
    public int generalRequiredSoldiers = 25;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=0L, max=256L)
    public int generalEmeraldCost = 32;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=0L, max=64L)
    public int generalDiamondCost = 8;
    @ConfigEntry.Category(value="kingdom_promotion")
    @ConfigEntry.BoundedDiscrete(min=0L, max=30L)
    public int generalCooldownDays = 3;
    @Comment(value="KOENIGREICH / KINGDOM - Burguebernahme / castle capture")
    @ConfigEntry.Category(value="kingdom_capture")
    @ConfigEntry.BoundedDiscrete(min=5L, max=300L)
    public int castleCaptureSeconds = 30;
    @ConfigEntry.Category(value="kingdom_capture")
    @ConfigEntry.BoundedDiscrete(min=2L, max=32L)
    public int castleCaptureMaxDistance = 5;
    @ConfigEntry.Category(value="kingdom_capture")
    @ConfigEntry.BoundedDiscrete(min=16L, max=256L)
    public int castleCaptureBossbarRadius = 100;
    @Comment(value="MASCHINEN / MACHINES - Guillotine: Servernachrichten / server messages")
    @ConfigEntry.Category(value="machines_guillotine")
    public boolean broadcastExecutionMessage = true;
    @ConfigEntry.Category(value="machines_guillotine")
    public String serverLanguage = "de_de";
    @ConfigEntry.Category(value="machines_guillotine")
    public String banReason = "Im Namen des K\u00f6nigs hingerichtet";
    @ConfigEntry.Gui.Excluded
    public Map<String, String> executionMessages = MedievalMachineryConfig.defaultMessages();

    public static void register() {
        AutoConfig.register(MedievalMachineryConfig.class, JanksonConfigSerializer::new);
    }

    public static MedievalMachineryConfig get() {
        MedievalMachineryConfig config = (MedievalMachineryConfig)AutoConfig.getConfigHolder(MedievalMachineryConfig.class).getConfig();
        config.ensureDefaults();
        return config;
    }

    public static void save() {
        AutoConfig.getConfigHolder(MedievalMachineryConfig.class).save();
    }

    public String getExecutionMessage(String playerName) {
        this.ensureDefaults();
        String template = this.executionMessages.get(this.serverLanguage);
        if (template == null || template.isBlank()) {
            template = this.executionMessages.get("en_us");
        }
        if (template == null || template.isBlank()) {
            template = "%player% was executed by guillotine.";
        }
        return template.replace("%player%", playerName);
    }

    private void ensureDefaults() {
        if (this.catapult == null) {
            this.catapult = new Catapult();
        }
        if (this.catapult.ammunitionProfiles == null) {
            this.catapult.ammunitionProfiles = MedievalMachineryConfig.defaultCatapultAmmunition();
        } else {
            MedievalMachineryConfig.defaultCatapultAmmunition().forEach(this.catapult.ammunitionProfiles::putIfAbsent);
        }
        this.catapult.damageMultiplier = MedievalMachineryConfig.clampFloat(this.catapult.damageMultiplier, 0.0f, 20.0f);
        this.catapult.blockDamageMultiplier = MedievalMachineryConfig.clampFloat(this.catapult.blockDamageMultiplier, 0.0f, 20.0f);
        this.catapult.maximumAutomaticDamage = MedievalMachineryConfig.clampFloat(this.catapult.maximumAutomaticDamage, 1.0f, 200.0f);
        this.catapult.potionRadius = MedievalMachineryConfig.clampFloat(this.catapult.potionRadius, 1.0f, 16.0f);
        this.catapult.potionDirectHitDamage = MedievalMachineryConfig.clampFloat(this.catapult.potionDirectHitDamage, 0.0f, 50.0f);
        this.catapult.potionEdgeDurationMultiplier = MedievalMachineryConfig.clampFloat(this.catapult.potionEdgeDurationMultiplier, 0.0f, 1.0f);
        this.catapult.explosivePayloadExplosionPower = MedievalMachineryConfig.clampFloat(this.catapult.explosivePayloadExplosionPower, 0.0f, 8.0f);
        this.catapult.firePayloadDirectDamage = MedievalMachineryConfig.clampFloat(this.catapult.firePayloadDirectDamage, 0.0f, 50.0f);
        this.catapult.firePayloadRadius = MedievalMachineryConfig.clampFloat(this.catapult.firePayloadRadius, 1.0f, 16.0f);
        this.catapult.firePayloadMaxBrokenBlocks = MedievalMachineryConfig.clamp(this.catapult.firePayloadMaxBrokenBlocks, 0, 8);
        this.recruitSwordsmanEmeraldCost = MedievalMachineryConfig.clamp(this.recruitSwordsmanEmeraldCost, 0, 256);
        this.recruitAxemanEmeraldCost = MedievalMachineryConfig.clamp(this.recruitAxemanEmeraldCost, 0, 256);
        this.recruitArcherEmeraldCost = MedievalMachineryConfig.clamp(this.recruitArcherEmeraldCost, 0, 256);
        this.recruitCrossbowmanEmeraldCost = MedievalMachineryConfig.clamp(this.recruitCrossbowmanEmeraldCost, 0, 256);
        this.recruitSpearmanEmeraldCost = MedievalMachineryConfig.clamp(this.recruitSpearmanEmeraldCost, 0, 256);
        this.recruitCannoneerEmeraldCost = MedievalMachineryConfig.clamp(this.recruitCannoneerEmeraldCost, 0, 256);
        this.recruitKnightEmeraldCost = MedievalMachineryConfig.clamp(this.recruitKnightEmeraldCost, 0, 256);
        this.recruitQuartermasterEmeraldCost = MedievalMachineryConfig.clamp(this.recruitQuartermasterEmeraldCost, 0, 256);
        this.recruitScoutEmeraldCost = MedievalMachineryConfig.clamp(this.recruitScoutEmeraldCost, 0, 256);
        this.recruitEngineerEmeraldCost = MedievalMachineryConfig.clamp(this.recruitEngineerEmeraldCost, 0, 256);
        this.recruitMountedArcherEmeraldCost = MedievalMachineryConfig.clamp(this.recruitMountedArcherEmeraldCost, 0, 256);
        this.recruitMountedKnightEmeraldCost = MedievalMachineryConfig.clamp(this.recruitMountedKnightEmeraldCost, 0, 256);
        this.taxEmeraldsPerAdultVillager = MedievalMachineryConfig.clamp(this.taxEmeraldsPerAdultVillager, 0, 64);
        this.taxVillagerRadius = MedievalMachineryConfig.clamp(this.taxVillagerRadius, 8, 128);
        this.taxAfkTimeoutMinutes = MedievalMachineryConfig.clamp(this.taxAfkTimeoutMinutes, 1, 60);
        this.captainMinimumLevel = MedievalMachineryConfig.clamp(this.captainMinimumLevel, 1, 100);
        this.captainRequiredKnights = MedievalMachineryConfig.clamp(this.captainRequiredKnights, 0, 100);
        this.captainEmeraldCost = MedievalMachineryConfig.clamp(this.captainEmeraldCost, 0, 256);
        this.captainDiamondCost = MedievalMachineryConfig.clamp(this.captainDiamondCost, 0, 64);
        this.generalMinimumLevel = MedievalMachineryConfig.clamp(this.generalMinimumLevel, 1, 100);
        this.generalRequiredKnights = MedievalMachineryConfig.clamp(this.generalRequiredKnights, 0, 100);
        this.generalRequiredSoldiers = MedievalMachineryConfig.clamp(this.generalRequiredSoldiers, 0, 500);
        this.generalEmeraldCost = MedievalMachineryConfig.clamp(this.generalEmeraldCost, 0, 256);
        this.generalDiamondCost = MedievalMachineryConfig.clamp(this.generalDiamondCost, 0, 64);
        this.generalCooldownDays = MedievalMachineryConfig.clamp(this.generalCooldownDays, 0, 30);
        this.castleCaptureSeconds = MedievalMachineryConfig.clamp(this.castleCaptureSeconds, 5, 300);
        this.castleCaptureMaxDistance = MedievalMachineryConfig.clamp(this.castleCaptureMaxDistance, 2, 32);
        this.castleCaptureBossbarRadius = MedievalMachineryConfig.clamp(this.castleCaptureBossbarRadius, 16, 256);
        if (this.stone == null) {
            this.stone = new Cannonball(18, 26);
        }
        if (this.iron == null) {
            this.iron = new Cannonball(32, 29);
        }
        if (this.heavyIron == null) {
            this.heavyIron = new Cannonball(32, 29);
        }
        if (this.tnt == null) {
            this.tnt = new Cannonball(24, 32);
        }
        this.stone.damage = MedievalMachineryConfig.clamp(this.stone.damage, 0, 200);
        this.stone.range = MedievalMachineryConfig.clamp(this.stone.range, 4, 256);
        this.iron.damage = MedievalMachineryConfig.clamp(this.iron.damage, 0, 200);
        this.iron.range = MedievalMachineryConfig.clamp(this.iron.range, 4, 256);
        this.heavyIron.damage = MedievalMachineryConfig.clamp(this.heavyIron.damage, 0, 200);
        this.heavyIron.range = MedievalMachineryConfig.clamp(this.heavyIron.range, 4, 256);
        this.tnt.damage = MedievalMachineryConfig.clamp(this.tnt.damage, 0, 200);
        this.tnt.range = MedievalMachineryConfig.clamp(this.tnt.range, 4, 256);
        if (this.serverLanguage == null || this.serverLanguage.isBlank()) {
            this.serverLanguage = "de_de";
        }
        if (this.banReason == null) {
            this.banReason = "Im Namen des K\u00f6nigs hingerichtet";
        }
        if (this.executionMessages == null) {
            this.executionMessages = MedievalMachineryConfig.defaultMessages();
        } else {
            MedievalMachineryConfig.defaultMessages().forEach(this.executionMessages::putIfAbsent);
        }
    }

    private static float clampFloat(float value, float minimum, float maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private static Map<String, CatapultAmmo> defaultCatapultAmmunition() {
        LinkedHashMap<String, CatapultAmmo> map = new LinkedHashMap<String, CatapultAmmo>();
        map.put("minecraft:oak_planks", new CatapultAmmo(3.0f, 0.3f, 1.15f, 0.9f, 0.01f));
        map.put("minecraft:oak_log", new CatapultAmmo(6.0f, 0.8f, 1.05f, 0.95f, 0.01f));
        map.put("minecraft:sandstone", new CatapultAmmo(8.0f, 1.2f, 1.05f, 0.95f, 0.01f));
        map.put("minecraft:cobblestone", new CatapultAmmo(14.0f, 2.5f, 1.0f, 1.0f, 0.01f));
        map.put("minecraft:stone", new CatapultAmmo(16.0f, 3.0f, 0.98f, 1.02f, 0.01f));
        map.put("minecraft:deepslate", new CatapultAmmo(20.0f, 4.2f, 0.9f, 1.1f, 0.012f));
        map.put("minecraft:iron_block", new CatapultAmmo(30.0f, 6.0f, 0.72f, 1.22f, 0.016f));
        map.put("minecraft:obsidian", new CatapultAmmo(34.0f, 8.0f, 0.66f, 1.28f, 0.018f));
        map.put("minecraft:anvil", new CatapultAmmo(36.0f, 7.0f, 0.62f, 1.32f, 0.02f));
        return map;
    }

    private static int clamp(int value, int minimum, int maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private static Map<String, String> defaultMessages() {
        LinkedHashMap<String, String> messages = new LinkedHashMap<String, String>();
        messages.put("de_de", "%player% wurde im Namen des K\u00f6nigs zum Tode verurteilt und durch die Guillotine hingerichtet.");
        messages.put("en_us", "%player% was sentenced to death in the name of the King and executed by guillotine.");
        messages.put("fr_fr", "%player% a \u00e9t\u00e9 condamn\u00e9 \u00e0 mort au nom du Roi et ex\u00e9cut\u00e9 par la guillotine.");
        messages.put("es_es", "%player% fue condenado a muerte en nombre del Rey y ejecutado en la guillotina.");
        return messages;
    }

    public static final class Cannonball {
        @ConfigEntry.BoundedDiscrete(min=0L, max=200L)
        public int damage;
        @ConfigEntry.BoundedDiscrete(min=4L, max=256L)
        public int range;

        public Cannonball() {
        }

        public Cannonball(int damage, int range) {
            this.damage = damage;
            this.range = range;
        }
    }

    public static final class Catapult {
        public boolean allowUnlistedBlocks = true;
        public boolean blockDamageEnabled = true;
        public boolean allowPositivePotionEffects = true;
        public boolean allowNegativePotionEffects = true;
        public float damageMultiplier = 1.0f;
        public float blockDamageMultiplier = 1.0f;
        public float maximumAutomaticDamage = 30.0f;
        public float potionRadius = 5.0f;
        public float potionDirectHitDamage = 2.0f;
        public float potionEdgeDurationMultiplier = 0.35f;
        public float explosivePayloadExplosionPower = 4.0f;
        public float firePayloadDirectDamage = 8.0f;
        public float firePayloadRadius = 10.0f;
        public int firePayloadMaxBrokenBlocks = 3;
        @ConfigEntry.Gui.Excluded
        public Map<String, CatapultAmmo> ammunitionProfiles = MedievalMachineryConfig.defaultCatapultAmmunition();
    }

    public static final class CatapultAmmo {
        public float damage;
        public float blockDamage;
        public float velocityMultiplier;
        public float gravityMultiplier;
        public float drag;

        public CatapultAmmo() {
        }

        public CatapultAmmo(float damage, float blockDamage, float velocityMultiplier, float gravityMultiplier, float drag) {
            this.damage = damage;
            this.blockDamage = blockDamage;
            this.velocityMultiplier = velocityMultiplier;
            this.gravityMultiplier = gravityMultiplier;
            this.drag = drag;
        }
    }
}

