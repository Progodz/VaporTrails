package com.mitsugaru.VaporTrails;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

public class Config
{
	private VaporTrails plugin;
	public boolean debug, gamemode, worldGuard, useListener;
	public String gameModeEffect;
	public int interval;

	public Config(VaporTrails karmiclottery)
	{
		plugin = karmiclottery;
		ConfigurationSection config = plugin.getConfig();
		// Hashmap of defaults
		final Map<String, Object> defaults = new HashMap<String, Object>();
		defaults.put("effects.useListener", false);
		defaults.put("effects.interval", 20);
		defaults.put("gamemode.use", false);
		defaults.put("gamemode.effect", "smoke");
		defaults.put("checkWorldGuardRegions", false);
		defaults.put("version", plugin.getDescription().getVersion());
		for (final Entry<String, Object> e : defaults.entrySet())
		{
			if (!config.contains(e.getKey()))
			{
				config.set(e.getKey(), e.getValue());
			}
		}
		plugin.saveConfig();
		worldGuard = config.getBoolean("checkWorldGuardRegions", true);
		loadVariables();
	}

	public void reload()
	{
		// Reload
		plugin.reloadConfig();
		loadVariables();
	}

	private void loadVariables()
	{
		final ConfigurationSection config = plugin.getConfig();
		debug = config.getBoolean("debug", false);
		gamemode = config.getBoolean("gamemode.use", false);
		gameModeEffect = config.getString("gamemode.effect", "smoke");
		useListener = config.getBoolean("useListener", false);
		interval = config.getInt("effects.interval", 20);
		boundsCheck();
	}

	private void boundsCheck()
	{
		if (interval <= 0)
		{
			plugin.getLogger().warning(
					"Invalid negative or 0 interval, defaulting to 20.");
			plugin.getConfig().set("effects.interval", 20);
			plugin.saveConfig();
			interval = 20;
		}
	}

	public void save()
	{
		plugin.saveConfig();
	}
}
