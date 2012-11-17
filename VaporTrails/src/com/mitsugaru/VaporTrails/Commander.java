package com.mitsugaru.VaporTrails;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mitsugaru.VaporTrails.logic.Trail;
import com.mitsugaru.VaporTrails.logic.Trail.Type;
import com.mitsugaru.VaporTrails.logic.VTLogic;
import com.mitsugaru.VaporTrails.permissions.PermissionHandler;
import com.mitsugaru.VaporTrails.permissions.PermissionNode;

public class Commander implements CommandExecutor {
    private VaporTrails plugin;
    private final static String bar = "======================";

    public Commander(VaporTrails karmiclotto) {
        plugin = karmiclotto;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.BLUE + bar);
            sender.sendMessage(ChatColor.GREEN + "VaporTrails v"
                    + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.GREEN + "Coded by Mitsugaru");
            sender.sendMessage(ChatColor.BLUE + bar);
            if (VTLogic.playerEffects.containsKey(sender.getName())) {
                sender.sendMessage(ChatColor.AQUA
                        + "Current effect: "
                        + ChatColor.GRAY
                        + VTLogic.playerEffects.get(sender.getName())
                                .getTitle());
            }
        } else {
            String com = args[0].toLowerCase();
            if (com.equals("reload")) {
                if (PermissionHandler.has(sender, PermissionNode.ADMIN)) {
                    plugin.getPluginConfig().reload();
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + " Config reloaded");
                } else {
                    sender.sendMessage(ChatColor.DARK_RED"You do not have access to that command!"());
                }
                return true;
            } else if (com.equals("help") || com.equals("?")) {
                sender.sendMessage(ChatColor.GRAY
                        + "Possible Effects: "
                        + ChatColor.GRAY
                        + "SMOKE"
                        + ChatColor.BLUE
                        + "/"
                        + ChatColor.GRAY
                        + "FIRE"
                        + ChatColor.BLUE
                        + "/"
                        + ChatColor.GRAY
                        + "ENDER"
                        + ChatColor.BLUE
                        + "/"
                        + ChatColor.GRAY
                        + "THUNDER"
                        + ChatColor.BLUE
                        + "/"
                        + ChatColor.GRAY
                        + "TNT"
                        + ChatColor.BLUE
                        + "/"
                        + ChatColor.GRAY
                        + "SNOW"
                        + ChatColor.BLUE
                        + "/"
                        + ChatColor.GRAY
                        + "SHINE"
                        + ChatColor.BLUE
                        + "/"
                        + ChatColor.GRAY
                        + "SWIRL"
                        + ChatColor.BLUE
                        + "/"
                        + ChatColor.GRAY
                        + "POTION "
                        + "[pink|aqua|gold|green|red|darkred|gray|crimson|cyan|blue]"
                        + ChatColor.BLUE + "/" + ChatColor.GRAY
                        + "<block>:<data>");
                sender.sendMessage(ChatColor.WHITE + "/trail stop"
                        + ChatColor.GRAY + " : " + "Stops the active trail.");
                sender.sendMessage(ChatColor.WHITE + "/trail time <ticks>"
                        + ChatColor.GRAY
                        + " : Resets the interval for an active trail.");
            } else if (com.equals("stop") || com.equals("off")) {
                if (VTLogic.playerEffects.containsKey(sender.getName())) {
                    VTLogic.cancelExisting(sender.getName());
                    sender.sendMessage(ChatColor.YELLOW + VaporTrails.TAG
                            + " Stopping effects.");
                }
            } else if (com.equals("interval") || com.equals("time")) {
                final Trail trail = VTLogic.playerEffects.get(sender.getName());
                if (trail == null) {
                    sender.sendMessage(ChatColor.YELLOW + VaporTrails.TAG
                            + " No effect is enabled.");
                    return true;
                }
                try {
                    final int interval = Integer.parseInt(args[1]);
                    if (interval <= 0) {
                        sender.sendMessage(ChatColor.YELLOW
                                + VaporTrails.TAG
                                + " Cannot use 0 or a negative number for the interval.");
                        return true;
                    }
                    trail.setInterval(interval);
                    return true;
                } catch (ArrayIndexOutOfBoundsException aioob) {
                    sender.sendMessage(ChatColor.DARK_RED + VaporTrails.TAG
                            + " No interval given.");
                    return true;
                } catch (NumberFormatException nf) {
                    sender.sendMessage(ChatColor.DARK_RED + VaporTrails.TAG
                            + " Non-number given for interval.");
                    return true;
                }
            } else if (com.equals("smoke")) {
                if (!PermissionHandler.has(sender, PermissionNode.EFFECT_SMOKE)) {
                    sender.sendMessage(ChatColor.DARK_RED"You do not have access to that trail!"());
                    return true;
                }
                if (sender instanceof Player) {
                    VTLogic.cancelExisting(sender.getName());
                    VTLogic.playerEffects.put(sender.getName(), new Trail(
                            plugin, sender.getName(), Type.SMOKE));
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + "Effect: " + ChatColor.GRAY + Effect.SMOKE);
                }
            } else if (com.equals("ender")) {
                if (!PermissionHandler.has(sender, PermissionNode.EFFECT_ENDER)) {
                    sender.sendMessage(ChatColor.RED"You do not have access to that trail!"());
                    return true;
                }
                if (sender instanceof Player) {
                    VTLogic.cancelExisting(sender.getName());
                    VTLogic.playerEffects.put(sender.getName(), new Trail(
                            plugin, sender.getName(), Type.ENDERSIGNAL));
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + "Effect: " + ChatColor.GRAY + Effect.ENDER_SIGNAL);
                }
            } else if (com.equals("lightning") || com.equals("thunder")) {
                if (!PermissionHandler.has(sender,
                        PermissionNode.EFFECT_THUNDER)) {
                    sender.sendMessage(ChatColor.DARK_RED"You do not have access to that trail!"());
                    return true;
                }
                if (sender instanceof Player) {
                    VTLogic.cancelExisting(sender.getName());
                    VTLogic.playerEffects.put(sender.getName(), new Trail(
                            plugin, sender.getName(), Type.LIGHTNING));
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + " Effect: " + ChatColor.GRAY + "THUNDER");
                }
            } else if (com.equals("explosion") || com.equals("explode")
                    || com.equals("tnt")) {
                if (!PermissionHandler.has(sender, PermissionNode.EFFECT_TNT)) {
                    sender.sendMessage(ChatColor.RED"You do not have access to that trail!"());
                    return true;
                }
                if (sender instanceof Player) {
                    VTLogic.cancelExisting(sender.getName());
                    VTLogic.playerEffects.put(sender.getName(), new Trail(
                            plugin, sender.getName(), Type.TNT));
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + " Effect: " + ChatColor.GRAY + "EXPLOSION");
                }
            } else if (com.equals("snow")) {
                if (!PermissionHandler.has(sender, PermissionNode.EFFECT_SNOW)) {
                    sender.sendMessage(ChatColor.DARK_RED"You do not have access to that trail!"());
                    return true;
                }
                if (sender instanceof Player) {
                    VTLogic.cancelExisting(sender.getName());
                    VTLogic.playerEffects.put(sender.getName(), new Trail(
                            plugin, sender.getName(), Type.SNOW));
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + " Effect: " + ChatColor.GRAY + "SNOW");
                }
            } else if (com.equals("fire") || com.equals("flame")) {
                if (!PermissionHandler.has(sender, PermissionNode.EFFECT_FIRE)) {
                    sender.sendMessage(ChatColor.DARK_RED"You do not have access to that trail!"());
                    return true;
                }
                if (sender instanceof Player) {
                    VTLogic.cancelExisting(sender.getName());
                    VTLogic.playerEffects.put(sender.getName(), new Trail(
                            plugin, sender.getName(), Type.FIRE));
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + " Effect: " + ChatColor.GRAY + "FIRE");
                }
            } else if (com.equals("shine") || com.equals("blaze")) {
                if (!PermissionHandler.has(sender, PermissionNode.EFFECT_SHINE)) {
                    sender.sendMessage(ChatColor.DARK_RED"You do not have access to that trail!"());
                    return true;
                }
                if (sender instanceof Player) {
                    VTLogic.cancelExisting(sender.getName());
                    VTLogic.playerEffects.put(sender.getName(), new Trail(
                            plugin, sender.getName(), Type.SHINE));
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + " Effect: " + ChatColor.GRAY + "SHINE");
                }
            } else if (com.equalsIgnoreCase("swirl")) {
                if (!PermissionHandler.has(sender, PermissionNode.EFFECT_SWIRL)) {
                    sender.sendMessage(ChatColor.DARK_RED"You do not have access to that trail!"());
                    return true;
                }
                if (sender instanceof Player) {
                    VTLogic.cancelExisting(sender.getName());
                    final Trail trail = new Trail(plugin, sender.getName(),
                            Type.SWIRL);
                    VTLogic.playerEffects.put(sender.getName(), trail);
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + " Effect: " + ChatColor.GRAY + trail.getTitle());
                }
            } else if (com.equalsIgnoreCase("potion")) {
                Trail trail = null;
                if (args.length <= 1) {
                    // use 0
                } else {
                    final String type = args[1].toLowerCase();
                    if (type.equalsIgnoreCase("pink")
                            || type.equalsIgnoreCase("magenta")
                            || type.equalsIgnoreCase("purple")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_PINK)) {
                            sender.sendMessage(ChatColor.DARK_RED
                                    "You do not have access to that trail!"());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_PINK);
                    } else if (type.equalsIgnoreCase("aqua")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_AQUA)) {
                            sender.sendMessage(ChatColor.DARK_RED
                                    "You do not have access to that trail!"());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_AQUA);
                    } else if (type.equalsIgnoreCase("gold")
                            || type.equalsIgnoreCase("yellow")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_GOLD)) {
                            sender.sendMessage(ChatColor.DARK_RED
                                    "You do not have access to that trail!"());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_GOLD);
                    } else if (type.equalsIgnoreCase("green")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_GREEN)) {
                            sender.sendMessage(ChatColor.RED
                                    "You do not have access to that trail!"());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_GREEN);
                    } else if (type.equalsIgnoreCase("red")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_RED)) {
                            sender.sendMessage(ChatColor.RED
                                   "You do not have access to that trail!"());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_RED);
                    } else if (type.equalsIgnoreCase("darkred")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_DARKRED)) {
                            sender.sendMessage(ChatColor.RED
                                    + VaporTrails.TAG
                                    + " Lack permission: "
                                    + PermissionNode.EFFECT_POTION_DARKRED
                                            .getNode());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_DARKRED);
                    } else if (type.equalsIgnoreCase("gray")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_GRAY)) {
                            sender.sendMessage(ChatColor.RED
                                    + VaporTrails.TAG
                                    + " Lack permission: "
                                    + PermissionNode.EFFECT_POTION_GRAY
                                            .getNode());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_GRAY);
                    } else if (type.equalsIgnoreCase("blood")
                            || type.equalsIgnoreCase("crimson")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_CRIMSON)) {
                            sender.sendMessage(ChatColor.RED
                                    + VaporTrails.TAG
                                    + " Lack permission: "
                                    + PermissionNode.EFFECT_POTION_CRIMSON
                                            .getNode());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_CRIMSON);
                    } else if (type.equalsIgnoreCase("cyan")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_CYAN)) {
                            sender.sendMessage(ChatColor.RED
                                    + VaporTrails.TAG
                                    + " Lack permission: "
                                    + PermissionNode.EFFECT_POTION_CYAN
                                            .getNode());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_CYAN);
                    } else if (type.equalsIgnoreCase("blue")) {
                        if (!PermissionHandler.has(sender,
                                PermissionNode.EFFECT_POTION_BLUE)) {
                            sender.sendMessage(ChatColor.RED
                                    + VaporTrails.TAG
                                    + " Lack permission: "
                                    + PermissionNode.EFFECT_POTION_BLUE
                                            .getNode());
                            return true;
                        }
                        trail = new Trail(plugin, sender.getName(),
                                Type.POTION_BLUE);
                    }
                }
                if (trail != null && sender instanceof Player) {
                    VTLogic.cancelExisting(sender.getName());
                    VTLogic.playerEffects.put(sender.getName(), trail);
                    sender.sendMessage(ChatColor.GREEN + VaporTrails.TAG
                            + " Effect: " + ChatColor.GRAY + trail.getTitle());
                }
            } else {
                if (!PermissionHandler.has(sender, PermissionNode.EFFECT_BLOCK)) {
                    sender.sendMessage(ChatColor.RED + VaporTrails.TAG
                            + " Lack permission: "
                            + PermissionNode.EFFECT_BLOCK.getNode());
                    return true;
                }
                if (!(sender instanceof Player)) {
                    return true;
                }
                try {
                    int id = 1;
                    byte data = 0;
                    boolean hasData = false;
                    if (com.contains(":")) {
                        final String[] split = com.split(":");
                        id = Integer.parseInt(split[0]);
                        data = Byte.parseByte(split[1]);
                        hasData = true;
                    } else {
                        id = Integer.parseInt(com);
                    }
                    if (id == 0) {
                        // Handle air as effects off
                        if (VTLogic.playerEffects.containsKey(sender.getName())) {
                            VTLogic.cancelExisting(sender.getName());
                            sender.sendMessage(ChatColor.YELLOW
                                    + VaporTrails.TAG + " Stopping effects.");
                        }
                        return true;
                    }
                    final ItemStack block = new ItemStack(id, data);
                    if (block.getData().getItemType().isBlock()) {
                        VTLogic.cancelExisting(sender.getName());
                        if (hasData) {
                            final Trail trail = new Trail(plugin,
                                    sender.getName(), Type.BLOCK, id, data);
                            trail.setTitle(" Block: "
                                    + block.getType().toString() + " : " + data);
                            VTLogic.playerEffects.put(sender.getName(), trail);
                            sender.sendMessage(ChatColor.GREEN
                                    + VaporTrails.TAG + " Effect: "
                                    + ChatColor.GRAY + trail.getTitle());
                        } else {
                            final Trail trail = new Trail(plugin,
                                    sender.getName(), Type.BLOCK, id, 0);
                            trail.setTitle(" Block: "
                                    + block.getType().toString());
                            VTLogic.playerEffects.put(sender.getName(), trail);
                            sender.sendMessage(ChatColor.GREEN
                                    + VaporTrails.TAG + " Effect: "
                                    + ChatColor.GRAY + trail.getTitle());
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + VaporTrails.TAG
                                + " Must use a placeable block.");
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + VaporTrails.TAG
                            + " Invalid item id / data value given");
                }
            }
        }
        return true;
    }
}
