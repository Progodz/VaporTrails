package com.mitsugaru.VaporTrails;

import net.minecraft.server.Packet61WorldEvent;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;

public class VTPlayerListener implements Listener {
	private VaporTrails plugin;

	public VTPlayerListener(VaporTrails karmicLotto) {
		plugin = karmicLotto;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!event.isCancelled())
		{
			final String name = event.getPlayer().getName();
			if(plugin.getCommander().getPlayers().containsKey(name))
			{
				final String effect = plugin.getCommander().getPlayers().get(name);
				if(effect.equals(""+Effect.SMOKE))
				{
					effectPlayer(event.getPlayer(), Effect.SMOKE);
				}
				else if(effect.equals(""+Effect.ENDER_SIGNAL))
				{
					effectPlayer(event.getPlayer(), Effect.ENDER_SIGNAL);
				}
				else if(effect.equals("THUNDER"))
				{
					thunderPlayer(event.getPlayer());
				}
				else if(effect.equals("EXPLOSION"))
				{
					explodePlayer(event.getPlayer());
				}
				else if(effect.equals("SNOW"))
				{
					snowPlayer(event.getPlayer());
				}
				else if(effect.equals("FIRE"))
				{
					firePlayer(event.getPlayer());
				}
				else if(effect.equals("SHINE"))
				{
					shinePlayer(event.getPlayer());
				}
				else
				{
					blockPlayer(event.getPlayer(), effect);
				}
			}
		}
	}

	/*
	 * Following method from BlazeOfGlory plugin
	 * http://dev.bukkit.org/server-mods/blazeofglory/
	 *
	 * Copyright (c) 2011-2012 craftycreeper, minebot.net
	 */
	private void shinePlayer(Player player)
	{
		//TODO might need a thread and do this on intervals
		final Location loc = player.getLocation();
		final int x = (int)Math.round(loc.getX());
		final int y = (int)Math.round(loc.getY());
		final int z = (int)Math.round(loc.getZ());
		((CraftServer) plugin.getServer()).getServer().serverConfigurationManager.sendPacketNearby(
				loc.getX(), loc.getY(), loc.getZ(), 64,
				((CraftWorld)loc.getWorld()).getHandle().dimension,
				new Packet61WorldEvent(2004, x, y, z, 0));
	}

	private void thunderPlayer(Player player)
	{
		player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
	}

	private void explodePlayer(Player player)
	{
		player.getLocation().getWorld().createExplosion(player.getLocation(), 1F);
	}

	private void snowPlayer(Player player)
	{
		final Block block = player.getLocation().getBlock();
		if(block.getType().equals(Material.AIR))
		{
			if(!block.getRelative(BlockFace.DOWN).getType().equals(Material.AIR))
			{
				block.setType(Material.SNOW);
			}
		}
	}

	private void blockPlayer(Player player, String effect)
	{
		int id = 1;
		byte data = 0;
		if(effect.contains(":"))
		{
			final String[] split = effect.split(":");
			id = Integer.parseInt(split[0]);
			data = Byte.parseByte(split[1]);
		}
		else
		{
			id = Integer.parseInt(effect);
		}
		final Block block = player.getLocation().getBlock();
		if(block.getType().equals(Material.AIR))
		{
				block.setTypeIdAndData(id, data, true);
		}
	}

	private void firePlayer(Player player)
	{
		final Block block = player.getLocation().getBlock();
		if(block.getType().equals(Material.AIR))
		{
			if(!block.getRelative(BlockFace.DOWN).getType().equals(Material.AIR))
			{
				block.setType(Material.FIRE);
			}
		}
	}

	/**
	 * Provides a smoke effect for the player.
	 *
	 * http://forums.bukkit.org/threads/smoke-effect-yes-i-know-others-have-
	 * asked.29492/
	 *
	 * @param Player
	 *            that should get the effect
	 * @author Adamki11s
	 */
	private void effectPlayer(Player player, Effect effect) {
		player.getLocation().getWorld().playEffect(player.getLocation(), effect, 1);
	}
}
