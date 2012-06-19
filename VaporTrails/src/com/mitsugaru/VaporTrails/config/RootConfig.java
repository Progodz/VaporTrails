package com.mitsugaru.VaporTrails.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

import com.mitsugaru.VaporTrails.VaporTrails;

public class RootConfig {
    private VaporTrails plugin;
    public boolean gamemode, worldGuard, useListener, explodeBlockDamage;
    public String gameModeEffect;
    private static int defaultInterval;

    public RootConfig(VaporTrails karmiclottery) {
        plugin = karmiclottery;
        ConfigurationSection config = plugin.getConfig();
        // Hashmap of defaults
        final Map<String, Object> defaults = new HashMap<String, Object>();
        defaults.put("effects.useListener", false);
        defaults.put("effects.checkWorldGuardRegions", false);
        defaults.put("effects.defaultInterval", 20);
        defaults.put("gamemode.use", false);
        defaults.put("gamemode.effect", "smoke");

        defaults.put("version", plugin.getDescription().getVersion());
        for (final Entry<String, Object> e : defaults.entrySet()) {
            if (!config.contains(e.getKey())) {
                config.set(e.getKey(), e.getValue());
            }
        }
        plugin.saveConfig();
        worldGuard = config.getBoolean("effects.checkWorldGuardRegions", false);
        loadVariables();
    }

    public void reload() {
        // Reload
        plugin.reloadConfig();
        loadVariables();
    }

    private void loadVariables() {
        final ConfigurationSection config = plugin.getConfig();
        gamemode = config.getBoolean("gamemode.use", false);
        gameModeEffect = config.getString("gamemode.effect", "smoke");
        useListener = config.getBoolean("effects.useListener", false);
        defaultInterval = config.getInt("effects.defaultInterval", 20);
        boundsCheck();
    }

    private void boundsCheck() {
        if (defaultInterval <= 0) {
            plugin.getLogger().warning(
                    "Invalid negative or 0 interval, defaulting to 20.");
            plugin.getConfig().set("effects.interval", 20);
            plugin.saveConfig();
            defaultInterval = 20;
        }
    }

    /**
     * Check if updates are necessary
     */
    public void checkUpdate() {
        // Check if need to update
        ConfigurationSection config = plugin.getConfig();
        if (Double.parseDouble(plugin.getDescription().getVersion()) > Double
                .parseDouble(config.getString("version"))) {
            // Update to latest version
            plugin.getLogger().info(
                    "Updating to v" + plugin.getDescription().getVersion());
            update();
        }
    }

    /**
     * This method is called to make the appropriate changes, most likely only
     * necessary for database schema modification, for a proper update.
     */
    public void update() {
        // Grab current version
        final double ver = Double.parseDouble(plugin.getConfig().getString(
                "version"));
        if (ver < 0.07) {
            defaultInterval = plugin.getConfig().getInt("effects.interval", 20);
            plugin.getConfig().set("effects.interval", null);
            plugin.getConfig().set("effects.defaultInterval", defaultInterval);
            worldGuard = plugin.getConfig().getBoolean(
                    "checkWorldGuardRegions", false);
            plugin.getConfig().set("checkWorldGuardRegions", null);
            plugin.getConfig()
                    .set("effects.checkWorldGuardRegions", worldGuard);
        }
        // Update version number in config.yml
        plugin.getConfig().set("version", plugin.getDescription().getVersion());
        plugin.saveConfig();
        plugin.getLogger().info("Upgrade complete");
    }

    public void save() {
        plugin.saveConfig();
    }
    
    public static int getDefaultInterval()
    {
        return defaultInterval;
    }
}
