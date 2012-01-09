package com.mitsugaru.VaporTrails;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VTPlayerListener extends PlayerListener {
	private VaporTrails plugin;

	public VTPlayerListener(VaporTrails karmicLotto) {
		plugin = karmicLotto;
	}

	@Override
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
				else
				{
					blockPlayer(event.getPlayer(), effect);
				}
			}
		}
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
