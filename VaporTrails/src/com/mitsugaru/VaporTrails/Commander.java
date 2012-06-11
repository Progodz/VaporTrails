package com.mitsugaru.VaporTrails;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mitsugaru.VaporTrails.logic.Trail;
import com.mitsugaru.VaporTrails.logic.VTLogic;
import com.mitsugaru.VaporTrails.permissions.PermCheck;
import com.mitsugaru.VaporTrails.permissions.PermissionNode;

public class Commander implements CommandExecutor
{
	private VaporTrails plugin;
	private final static String bar = "======================";

	public Commander(VaporTrails karmiclotto)
	{
		plugin = karmiclotto;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args)
	{
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.BLUE + bar);
			sender.sendMessage(ChatColor.GREEN + "VaporTrails v"
					+ plugin.getDescription().getVersion());
			sender.sendMessage(ChatColor.GREEN + "Coded by Mitsugaru");
			sender.sendMessage(ChatColor.BLUE + bar);
			if (VTLogic.playerEffects.containsKey(sender.getName()))
			{
				sender.sendMessage(ChatColor.AQUA + "Current effect: "
						+ ChatColor.GRAY
						+ VTLogic.playerEffects.get(sender.getName()));
			}
		}
		else
		{
			String com = args[0].toLowerCase();
			if (com.equals("reload"))
			{
				if (PermCheck.has(sender, PermissionNode.ADMIN))
				{
					plugin.getPluginConfig().reload();
					sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
							+ " Config reloaded");
				}
				else
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ " Lack permission: "
							+ PermissionNode.ADMIN.getNode());
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
				if (VTLogic.playerEffects.containsKey(sender.getName()))
				{
					final Trail trail = VTLogic.playerEffects.remove(sender
							.getName());
					trail.cancelEffect();
					sender.sendMessage(ChatColor.YELLOW + VaporTrails.TAG
							+ "Stopping effects.");
				}
			}
			else if (com.equals("smoke"))
			{
				if (!PermCheck.has(sender, PermissionNode.EFFECT_SMOKE))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ "Lack permission: "
							+ PermissionNode.EFFECT_SMOKE.getNode());
					return true;
				}
				if (sender instanceof Player)
				{
					VTLogic.playerEffects.put(sender.getName(), new Trail(
							plugin, sender.getName(), "" + Effect.SMOKE));
					sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
							+ "Effect: " + ChatColor.GRAY + Effect.SMOKE);
				}
			}
			else if (com.equals("ender"))
			{
				if (!PermCheck.has(sender, PermissionNode.EFFECT_ENDER))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ "Lack permission: "
							+ PermissionNode.EFFECT_ENDER.getNode());
					return true;
				}
				if (sender instanceof Player)
				{
					VTLogic.playerEffects
							.put(sender.getName(),
									new Trail(plugin, sender.getName(), ""
											+ Effect.ENDER_SIGNAL));
					sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
							+ "Effect: " + ChatColor.GRAY + Effect.ENDER_SIGNAL);
				}
			}
			else if (com.equals("lightning") || com.equals("thunder"))
			{
				if (!PermCheck.has(sender,
						PermissionNode.EFFECT_THUNDER.getNode()))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ "Lack permission: "
							+ PermissionNode.EFFECT_THUNDER.getNode());
					return true;
				}
				VTLogic.playerEffects.put(sender.getName(), new Trail(plugin,
						sender.getName(), "THUNDER"));
				sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
						+ "Effect: " + ChatColor.GRAY + "THUNDER");
			}
			else if (com.equals("explosion") || com.equals("explode")
					|| com.equals("tnt"))
			{
				if (!PermCheck.has(sender, PermissionNode.EFFECT_TNT.getNode()))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ "Lack permission: "
							+ PermissionNode.EFFECT_TNT.getNode());
					return true;
				}
				VTLogic.playerEffects.put(sender.getName(), new Trail(plugin,
						sender.getName(), "TNT"));
				sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
						+ "Effect: " + ChatColor.GRAY + "EXPLOSION");
			}
			else if (com.equals("snow"))
			{
				if (!PermCheck
						.has(sender, PermissionNode.EFFECT_SNOW.getNode()))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ "Lack permission: "
							+ PermissionNode.EFFECT_SNOW.getNode());
					return true;
				}
				VTLogic.playerEffects.put(sender.getName(), new Trail(plugin,
						sender.getName(), "SNOW"));
				sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
						+ "Effect: " + ChatColor.GRAY + "SNOW");
			}
			else if (com.equals("fire") || com.equals("flame"))
			{
				if (!PermCheck
						.has(sender, PermissionNode.EFFECT_FIRE.getNode()))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ "Lack permission: "
							+ PermissionNode.EFFECT_FIRE.getNode());
					return true;
				}
				VTLogic.playerEffects.put(sender.getName(), new Trail(plugin,
						sender.getName(), "FIRE"));
				sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
						+ "Effect: " + ChatColor.GRAY + "FIRE");
			}
			else if (com.equals("shine") || com.equals("blaze"))
			{
				if (!PermCheck.has(sender,
						PermissionNode.EFFECT_SHINE.getNode()))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ "Lack permission: "
							+ PermissionNode.EFFECT_SHINE.getNode());
					return true;
				}
				VTLogic.playerEffects.put(sender.getName(), new Trail(plugin,
						sender.getName(), "SHINE"));
				sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
						+ "Effect: " + ChatColor.GRAY + "SHINE");
			}
			else
			{
				if (!PermCheck.has(sender,
						PermissionNode.EFFECT_BLOCK.getNode()))
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ "Lack permission: "
							+ PermissionNode.EFFECT_BLOCK.getNode());
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
						if (VTLogic.playerEffects.containsKey(sender.getName()))
						{
							VTLogic.playerEffects.remove(sender.getName());
							sender.sendMessage(ChatColor.YELLOW
									+ VaporTrails.TAG + "Stopping effects.");
						}
						return true;
					}
					ItemStack block = new ItemStack(id, data);
					if (block.getData().getItemType().isBlock())
					{
						if (hasData)
						{
							VTLogic.playerEffects.put(sender.getName(),
									new Trail(plugin, sender.getName(), id
											+ ":" + data));
						}
						else
						{
							VTLogic.playerEffects
									.put(sender.getName(), new Trail(plugin,
											sender.getName(), "" + id));
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + VaporTrails.TAG
								+ "Must use a placeable block.");
					}
				}
				catch (NumberFormatException e)
				{
					sender.sendMessage(ChatColor.RED + VaporTrails.TAG
							+ "Invalid item id / data value given");
				}
			}
		}
		return true;
	}
}
