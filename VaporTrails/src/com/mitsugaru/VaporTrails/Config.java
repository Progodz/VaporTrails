package com.mitsugaru.VaporTrails;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

public class Config {
	private VaporTrails plugin;
	public boolean debug, effects;
	public int amount;
	public int percent;

	public Config(VaporTrails karmiclottery) {
		plugin = karmiclottery;
		ConfigurationSection config = plugin.getConfig();
		// Hashmap of defaults
		final Map<String, Object> defaults = new HashMap<String, Object>();
		defaults.put("effects", true);
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
			plugin.syslog
					.info(VaporTrails.prefix
							+ " No KarmicLotto config file found. Creating config file.");
		}
		debug = config.getBoolean("debug", false);
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
