package com.mitsugaru.VaporTrails;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

public class Config {
	private VaporTrails plugin;
	public boolean debug, effects, gamemode, worldGuard;
	public String gameModeEffect;
	public int amount;
	public int percent;

	public Config(VaporTrails karmiclottery) {
		plugin = karmiclottery;
		ConfigurationSection config = plugin.getConfig();
		// Hashmap of defaults
		final Map<String, Object> defaults = new HashMap<String, Object>();
		defaults.put("effects", true);
		defaults.put("gamemode.use", false);
		defaults.put("gamemode.effect", "smoke");
		defaults.put("checkWorldGuardRegions", false);
		defaults.put("version", plugin.getDescription().getVersion());
		boolean gen = false;
		for (final Entry<String, Object> e : defaults.entrySet())
		{
			if (!config.contains(e.getKey()))
			{
				config.set(e.getKey(), e.getValue());
				gen = true;
			}
		}
		if (gen)
		{
			plugin.getLogger()
					.info(VaporTrails.prefix
							+ " No VaporTrails config file found. Creating config file.");
			plugin.saveConfig();
		}
		debug = config.getBoolean("debug", false);
		gamemode = config.getBoolean("gamemode.use", false);
		gameModeEffect = config.getString("gamemode.effect", "smoke");
		worldGuard = config.getBoolean("checkWorldGuardRegions", true);
		effects = config.getBoolean("effects", true);
		// Check for update
		checkUpdate();
	}

	private void checkUpdate() {
		// TODO Auto-generated method stub

	}

	public void reload() {
		// Reload
		plugin.reloadConfig();
		// Grab config
		ConfigurationSection config = plugin.getConfig();
		// Set variables
		debug = config.getBoolean("debug", false);
		effects = config.getBoolean("effects", true);
	}

	public void save() {
		plugin.saveConfig();
	}
}
