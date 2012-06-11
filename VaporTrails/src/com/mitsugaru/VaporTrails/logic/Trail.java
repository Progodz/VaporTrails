package com.mitsugaru.VaporTrails.logic;

import org.bukkit.entity.Player;

import com.mitsugaru.VaporTrails.VaporTrails;

public class Trail
{
	private VaporTrails plugin;
	private String name, effect;
	private int id;

	public Trail(VaporTrails plugin, String name, String effect)
	{
		this.plugin = plugin;
		this.name = name;
		this.effect = effect;
		if (!plugin.getPluginConfig().useListener)
		{
			// Schedule effect
			id = plugin
					.getServer()
					.getScheduler()
					.scheduleSyncRepeatingTask(plugin, new TrailTask(), 0,
							plugin.getPluginConfig().interval);
		}
	}

	public String getEffect()
	{
		return effect;
	}

	public void cancelEffect()
	{
		if (id != -1)
		{
			plugin.getServer().getScheduler().cancelTask(id);
		}
	}

	private class TrailTask implements Runnable
	{

		@Override
		public void run()
		{
			final Player player = plugin.getServer().getPlayer(name);
			if (player != null && player.isOnline())
			{
				VTLogic.playEffect(player, effect, true);
			}
		}

	}
}
