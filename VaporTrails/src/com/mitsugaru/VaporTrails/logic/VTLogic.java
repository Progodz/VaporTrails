package com.mitsugaru.VaporTrails.logic;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.Packet61WorldEvent;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;

import com.mitsugaru.VaporTrails.VaporTrails;

public class VTLogic
{
	private static VaporTrails plugin;
	public static Map<String, Trail> playerEffects = new HashMap<String, Trail>();

	public static void init(VaporTrails vt)
	{
		plugin = vt;
	}

	public static void playEffect(Player player, String effect,
			boolean useBlocks)
	{
		if (effect.equalsIgnoreCase("" + Effect.SMOKE))
		{
			effectPlayer(player, Effect.SMOKE);
		}
		else if (effect.equalsIgnoreCase("" + Effect.ENDER_SIGNAL))
		{
			effectPlayer(player, Effect.ENDER_SIGNAL);
		}
		else if (effect.equalsIgnoreCase("THUNDER"))
		{
			thunderPlayer(player);
		}
		else if (effect.equalsIgnoreCase("TNT"))
		{
			if (plugin.getPluginConfig().worldGuard)
			{
				if (plugin.getWorldGuard().canBuild(player,
						player.getLocation()))
				{
					explodePlayer(player);
				}
			}
			else
			{
				explodePlayer(player);
			}
		}
		else if (effect.equalsIgnoreCase("SNOW"))
		{
			if (plugin.getPluginConfig().worldGuard)
			{
				if (plugin.getWorldGuard().canBuild(player,
						player.getLocation()))
				{
					snowPlayer(player);
				}
			}
			else
			{
				snowPlayer(player);
			}
		}
		else if (effect.equalsIgnoreCase("FIRE"))
		{
			if (plugin.getPluginConfig().worldGuard)
			{
				if (plugin.getWorldGuard().canBuild(player,
						player.getLocation()))
				{
					firePlayer(player);
				}
			}
			else
			{
				firePlayer(player);
			}
		}
		else if (effect.equalsIgnoreCase("SHINE"))
		{
			shinePlayer(player);
		}
		else if (useBlocks)
		{
			if (plugin.getPluginConfig().worldGuard)
			{
				if (plugin.getWorldGuard().canBuild(player,
						player.getLocation()))
				{
					blockPlayer(player, effect);
				}
			}
			else
			{
				blockPlayer(player, effect);
			}
		}
	}

	/*
	 * Following method from BlazeOfGlory plugin
	 * http://dev.bukkit.org/server-mods/blazeofglory/ Copyright (c) 2011-2012
	 * craftycreeper, minebot.net
	 */
	private static void shinePlayer(final Player player)
	{
		final Location loc = player.getLocation();
		final int x = (int) Math.round(loc.getX());
		final int y = (int) Math.round(loc.getY());
		final int z = (int) Math.round(loc.getZ());
		((CraftServer) plugin.getServer()).getServer().serverConfigurationManager
				.sendPacketNearby(loc.getX(), loc.getY(), loc.getZ(), 64,
						((CraftWorld) loc.getWorld()).getHandle().dimension,
						new Packet61WorldEvent(2004, x, y, z, 0));
	}

	private static void thunderPlayer(final Player player)
	{
		player.getLocation().getWorld()
				.strikeLightningEffect(player.getLocation());
	}

	private static void explodePlayer(final Player player)
	{
		player.getLocation().getWorld()
				.createExplosion(player.getLocation(), 1F);
	}

	private static void snowPlayer(final Player player)
	{
		final Block block = player.getLocation().getBlock();
		if (block.getType().equals(Material.AIR))
		{
			if (!block.getRelative(BlockFace.DOWN).getType()
					.equals(Material.AIR))
			{
				block.setType(Material.SNOW);
			}
		}
	}

	private static void blockPlayer(final Player player, final String effect)
	{
		int id = 1;
		byte data = 0;
		if (effect.contains(":"))
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
		if (block.getType().equals(Material.AIR))
		{
			block.setTypeIdAndData(id, data, true);
		}
	}

	private static void firePlayer(final Player player)
	{
		final Block block = player.getLocation().getBlock();
		if (block.getType().equals(Material.AIR))
		{
			if (!block.getRelative(BlockFace.DOWN).getType()
					.equals(Material.AIR))
			{
				block.setType(Material.FIRE);
			}
		}
	}

	private static void effectPlayer(final Player player, final Effect effect)
	{
		player.getLocation().getWorld()
				.playEffect(player.getLocation(), effect, 1);
	}
}
