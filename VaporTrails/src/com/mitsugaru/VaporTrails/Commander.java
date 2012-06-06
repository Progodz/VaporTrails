package com.mitsugaru.VaporTrails;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commander implements CommandExecutor {
	private VaporTrails plugin;
	private PermCheck perm;
	private final static String bar = "======================";
	private Map<String, String> playerEffects = new HashMap<String, String>();

	public Commander(VaporTrails karmiclotto) {
		plugin = karmiclotto;
		perm = plugin.getPerm();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.BLUE + bar);
			sender.sendMessage(ChatColor.GREEN + "VaporTrails v"
					+ plugin.getDescription().getVersion());
			sender.sendMessage(ChatColor.GREEN + "Coded by Mitsugaru");
			sender.sendMessage(ChatColor.BLUE + bar);
			if (playerEffects.containsKey(sender.getName()))
			{
				sender.sendMessage(ChatColor.AQUA + "Current effect: "
						+ ChatColor.GRAY + playerEffects.get(sender.getName()));
			}
		}
		else
		{
			String com = args[0].toLowerCase();
			if (com.equals("reload"))
			{
				if (perm.has(sender, "VaporTrails.admin"))
				{
					plugin.getPluginConfig().reload();
					sender.sendMessage(ChatColor.GREEN + VaporTrails.prefix
							+ " Config reloaded");
				}
				else
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ " Lack permission: VaporTrails.admin");
				}
				return true;
			}
			else if (com.equals("help") || com.equals("?"))
			{
				sender.sendMessage(ChatColor.GRAY + "Possible Effects: "
						+ ChatColor.GRAY + Effect.SMOKE + ChatColor.BLUE + "/"
						+ ChatColor.GRAY + "FIRE" + ChatColor.BLUE + "/"
						+ ChatColor.GRAY + "ENDER" + ChatColor.BLUE + "/"
						+ ChatColor.GRAY + "THUNDER" + ChatColor.BLUE + "/"
						+ ChatColor.GRAY + "TNT" + ChatColor.BLUE + "/"
						+ ChatColor.GRAY + "SNOW" + ChatColor.BLUE + "/"
						+ ChatColor.GRAY + "SHINE" + ChatColor.BLUE + "/"
						+ ChatColor.GRAY + "<block>:<data>");
			}
			else if (com.equals("stop") || com.equals("off"))
			{
				if (playerEffects.containsKey(sender.getName()))
				{
					playerEffects.remove(sender.getName());
					sender.sendMessage(ChatColor.YELLOW + VaporTrails.prefix
							+ "Stopping effects.");
				}
			}
			else if (com.equals("smoke"))
			{
				if (!perm.has(sender, "VaporTrails.effect.smoke"))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ "Lack permission: VaporTrails.effect.smoke");
					return true;
				}
				if (sender instanceof Player)
				{
					playerEffects.put(sender.getName(), ("" + Effect.SMOKE));
					sender.sendMessage(ChatColor.GREEN + VaporTrails.prefix
							+ "Effect: " + ChatColor.GRAY + Effect.SMOKE);
				}
			}
			else if (com.equals("ender"))
			{
				if (!perm.has(sender, "VaporTrails.effect.ender"))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ "Lack permission: VaporTrails.effect.ender");
					return true;
				}
				if (sender instanceof Player)
				{
					playerEffects.put(sender.getName(),
							("" + Effect.ENDER_SIGNAL));
					sender.sendMessage(ChatColor.GREEN + VaporTrails.prefix
							+ "Effect: " + ChatColor.GRAY + Effect.ENDER_SIGNAL);
				}
			}
			else if (com.equals("lightning") || com.equals("thunder"))
			{
				if (!perm.has(sender, "VaporTrails.effect.thunder"))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ "Lack permission: VaporTrails.effect.thunder");
					return true;
				}
				playerEffects.put(sender.getName(), "THUNDER");
				sender.sendMessage(ChatColor.GREEN + VaporTrails.prefix
						+ "Effect: " + ChatColor.GRAY + "THUNDER");
			}
			else if (com.equals("explosion") || com.equals("explode")
					|| com.equals("tnt"))
			{
				if (!perm.has(sender, "VaporTrails.effect.tnt"))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ "Lack permission: VaporTrails.effect.tnt");
					return true;
				}
				playerEffects.put(sender.getName(), "TNT");
				sender.sendMessage(ChatColor.GREEN + VaporTrails.prefix
						+ "Effect: " + ChatColor.GRAY + "EXPLOSION");
			}
			else if (com.equals("snow"))
			{
				if (!perm.has(sender, "VaporTrails.effect.snow"))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ "Lack permission: VaporTrails.effect.snow");
					return true;
				}
				playerEffects.put(sender.getName(), "SNOW");
				sender.sendMessage(ChatColor.GREEN + VaporTrails.prefix
						+ "Effect: " + ChatColor.GRAY + "SNOW");
			}
			else if (com.equals("fire") || com.equals("blaze"))
			{
				if (!perm.has(sender, "VaporTrails.effect.fire"))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ "Lack permission: VaporTrails.effect.fire");
					return true;
				}
				playerEffects.put(sender.getName(), "FIRE");
				sender.sendMessage(ChatColor.GREEN + VaporTrails.prefix
						+ "Effect: " + ChatColor.GRAY + "FIRE");
			}
			else if(com.equals("shine"))
			{
				if (!perm.has(sender, "VaporTrails.effect.shine"))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ "Lack permission: VaporTrails.effect.shine");
					return true;
				}
				playerEffects.put(sender.getName(), "SHINE");
				sender.sendMessage(ChatColor.GREEN + VaporTrails.prefix
						+ "Effect: " + ChatColor.GRAY + "SHINE");
			}
			else
			{
				if (!perm.has(sender, "VaporTrails.effect.block"))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ "Lack permission: VaporTrails.effect.block");
					return true;
				}
				try
				{
					int id = 1;
					byte data = 0;
					boolean hasData = false;
					if (com.contains(":"))
					{
						final String[] split = com.split(":");
						id = Integer.parseInt(split[0]);
						data = Byte.parseByte(split[1]);
						hasData = true;
					}
					else
					{
						id = Integer.parseInt(com);
					}
					if (id == 0)
					{
						// Handle air as effects off
						if (playerEffects.containsKey(sender.getName()))
						{
							playerEffects.remove(sender.getName());
							sender.sendMessage(ChatColor.YELLOW
									+ VaporTrails.prefix + "Stopping effects.");
						}
						return true;
					}
					ItemStack block = new ItemStack(id, data);
					if (block.getData().getItemType().isBlock())
					{
						if (hasData)
						{
							playerEffects
									.put(sender.getName(), id + ":" + data);
						}
						else
						{
							playerEffects.put(sender.getName(), "" + id);
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + VaporTrails.prefix
								+ "Must use a placeable block.");
					}
				}
				catch (NumberFormatException e)
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.prefix
							+ "Invalid item id / data value given");
				}
			}
		}
		return true;
	}

	public Map<String, String> getPlayers() {
		return playerEffects;
	}

}
