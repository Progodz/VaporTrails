package com.mitsugaru.VaporTrails;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VaporTrails extends JavaPlugin {
	public Logger syslog;
	private Commander commander;
	private Config config;
	private PermCheck perm;
	public static final String prefix = "[VaporTrails]";

	@Override
	public void onDisable() {
		// Save config
		this.saveConfig();
		syslog.info(prefix + " Plugin disabled");
	}

	@Override
	public void onEnable() {
		// Logger
		syslog = this.getServer().getLogger();
		// Config
		config = new Config(this);
		//Create permissions
		perm = new PermCheck(this);
		//Create commander
		commander = new Commander(this);
		getCommand("trail").setExecutor(commander);

		PluginManager pm = this.getServer().getPluginManager();
		//Create listener
		VTPlayerListener playerListener = new VTPlayerListener(this);
		pm.registerEvents(playerListener, this);
		syslog.info(prefix + " v" + this.getDescription().getVersion() + " enabled");
	}

	public Config getPluginConfig()
	{
		return config;
	}

	public PermCheck getPerm() {
		return perm;
	}

	public Commander getCommander()
	{
		return commander;
	}
}
