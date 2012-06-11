package com.mitsugaru.VaporTrails.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import com.mitsugaru.VaporTrails.VaporTrails;
import com.mitsugaru.VaporTrails.logic.Trail;
import com.mitsugaru.VaporTrails.logic.VTLogic;
import com.mitsugaru.VaporTrails.permissions.PermCheck;

public class VTPlayerListener implements Listener
{
	private VaporTrails plugin;

	public VTPlayerListener(VaporTrails vt)
	{
		plugin = vt;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerModeChange(PlayerGameModeChangeEvent event)
	{
		if (!event.isCancelled() || !plugin.getPluginConfig().gamemode)
		{
			return;
		}
		if (event.getNewGameMode().getValue() == 0)
		{
			// Going away from creative
			VTLogic.playerEffects.remove(event.getPlayer());
		}
		else if (event.getNewGameMode().getValue() == 1)
		{
			// Going to creative
			if (PermCheck.has(event.getPlayer(),
					"VaporTrails.effect."
							+ plugin.getPluginConfig().gameModeEffect))
			{
				VTLogic.playerEffects.put(
						event.getPlayer().getName(),
						new Trail(plugin, event.getPlayer().getName(), plugin
								.getPluginConfig().gameModeEffect));
			}
		}
	}
}
