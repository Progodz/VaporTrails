package com.mitsugaru.VaporTrails;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mitsugaru.VaporTrails.listeners.VTPlayerListener;
import com.mitsugaru.VaporTrails.listeners.VTPlayerMoveListener;
import com.mitsugaru.VaporTrails.logic.VTLogic;
import com.mitsugaru.VaporTrails.permissions.PermCheck;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class VaporTrails extends JavaPlugin
{
	private Config config;
	public static final String TAG = "[VaporTrails]";

	@Override
	public void onDisable()
	{
		// Save config
		this.saveConfig();
	}

	@Override
	public void onEnable()
	{
		// Config
		config = new Config(this);
		//Check update
		config.checkUpdate();
		// Create permissions
		PermCheck.init(this);
		//Create logic
		VTLogic.init(this);
		// Create commander
		getCommand("trail").setExecutor(new Commander(this));
		// Create listener
		getServer().getPluginManager().registerEvents(
				new VTPlayerListener(this), this);
		if (config.useListener)
		{
			getServer().getPluginManager().registerEvents(
					new VTPlayerMoveListener(), this);
		}
		// Check for worldguard?
		if (config.worldGuard)
		{
			try
			{
				WorldGuardPlugin test = getWorldGuard();
				if (test == null)
				{
					getLogger()
							.warning(
									"Could not attach to WorldGuard. Ignoring WorldGuard regions.");
					config.worldGuard = false;
				}
			}
			catch (Exception e)
			{
				getLogger()
						.warning(
								"Could not attach to WorldGuard. Ignoring WorldGuard regions.");
				config.worldGuard = false;
			}
		}
	}

	public Config getPluginConfig()
	{
		return config;
	}

	public WorldGuardPlugin getWorldGuard()
	{
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin))
		{
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}
}
