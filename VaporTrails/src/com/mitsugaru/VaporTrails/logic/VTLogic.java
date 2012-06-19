package com.mitsugaru.VaporTrails.logic;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.DataWatcher;
import net.minecraft.server.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mitsugaru.VaporTrails.VaporTrails;

public class VTLogic {
    private static VaporTrails plugin;
    public static Map<String, Trail> playerEffects = new HashMap<String, Trail>();

    public static void init(VaporTrails vt) {
        plugin = vt;
    }

    public static void playEffect(Player player, Trail trail, int data) {
        switch (trail.getType()) {
            case SMOKE: {
                effectPlayer(player, Effect.SMOKE);
                break;
            }
            case ENDERSIGNAL: {
                effectPlayer(player, Effect.ENDER_SIGNAL);
                break;
            }
            case LIGHTNING: {
                thunderPlayer(player);
                break;
            }
            case TNT: {
                if (plugin.getPluginConfig().worldGuard) {
                    if (plugin.getWorldGuard().canBuild(player,
                            player.getLocation())) {
                        explodePlayer(player);
                    }
                } else {
                    explodePlayer(player);
                }
                break;
            }
            case SNOW: {
                if (plugin.getPluginConfig().worldGuard) {
                    if (plugin.getWorldGuard().canBuild(player,
                            player.getLocation())) {
                        snowPlayer(player);
                    }
                } else {
                    snowPlayer(player);
                }
                break;
            }
            case FIRE: {
                if (plugin.getPluginConfig().worldGuard) {
                    if (plugin.getWorldGuard().canBuild(player,
                            player.getLocation())) {
                        firePlayer(player);
                    }
                } else {
                    firePlayer(player);
                }
                break;
            }
            case SHINE: {
                effectPlayer(player, Effect.MOBSPAWNER_FLAMES);
                break;
            }
            case SWIRL: {
                addPotionGraphicalEffect(player, data, 8);
                break;
            }
            case POTION_AQUA:
            case POTION_BLUE:
            case POTION_CRIMSON:
            case POTION_CYAN:
            case POTION_DARKRED:
            case POTION_GOLD:
            case POTION_GRAY:
            case POTION_GREEN:
            case POTION_PINK:
            case POTION_RED: {
                potionEffect(player, data);
                break;
            }
            case BLOCK: {
                if (plugin.getPluginConfig().worldGuard) {
                    if (plugin.getWorldGuard().canBuild(player,
                            player.getLocation())) {
                        blockPlayer(player, trail.getBlockId(), data);
                    }
                } else {
                    blockPlayer(player, trail.getBlockId(), data);
                }
                break;
            }
        }
    }

    private static void thunderPlayer(final Player player) {
        player.getLocation().getWorld()
                .strikeLightningEffect(player.getLocation());
    }

    private static void explodePlayer(final Player player) {
        player.getLocation().getWorld()
                .createExplosion(player.getLocation(), 0F);
    }

    private static void snowPlayer(final Player player) {
        final Block block = player.getLocation().getBlock();
        if (block.getType().equals(Material.AIR)) {
            if (!block.getRelative(BlockFace.DOWN).getType()
                    .equals(Material.AIR)) {
                block.setType(Material.SNOW);
            }
        }
    }

    private static void blockPlayer(final Player player, int blockid,
            int data) {
        final Block block = player.getLocation().getBlock();
        if (block.getType().equals(Material.AIR)) {
            block.setTypeIdAndData(blockid, (byte) data, true);
        }
    }

    private static void firePlayer(final Player player) {
        final Block block = player.getLocation().getBlock();
        if (block.getType().equals(Material.AIR)) {
            if (!block.getRelative(BlockFace.DOWN).getType()
                    .equals(Material.AIR)) {
                block.setType(Material.FIRE);
            }
        }
    }

    private static void effectPlayer(final Player player, final Effect effect) {
        switch (effect) {
            case SMOKE: {
                player.getLocation().getWorld()
                        .playEffect(player.getLocation(), effect, 1);
                player.getLocation().getWorld()
                        .playEffect(player.getLocation(), effect, 3);
                player.getLocation().getWorld()
                        .playEffect(player.getLocation(), effect, 5);
                player.getLocation().getWorld()
                        .playEffect(player.getLocation(), effect, 7);
                break;
            }
            case ENDER_SIGNAL: {
                player.getLocation().getWorld()
                        .playEffect(player.getLocation(), effect, 1);
                break;
            }
            default: {
                player.getLocation().getWorld()
                        .playEffect(player.getLocation(), effect, 0);
                break;
            }
        }

    }

    public static void potionEffect(Player player, int potionid) {
        player.getWorld().playEffect(player.getLocation(), Effect.POTION_BREAK,
                potionid);
    }

    /**
     * 
     * Creates swirly particle potion effect.
     * 
     * http://forums.bukkit.org/threads/how-to-create-the-swirly-particle-potion
     * -effect.55988/
     * 
     * @author nisovin
     * @param entity
     * @param color
     * @param duration
     */
    public static void addPotionGraphicalEffect(Player entity, int color,
            int duration) {
        final EntityPlayer player = ((CraftPlayer) entity).getHandle();
        final DataWatcher dw = player.getDataWatcher();
        dw.watch(8, Integer.valueOf(color));

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                int c = 0;
                if (!player.effects.isEmpty()) {
                    c = net.minecraft.server.PotionBrewer.a(player.effects
                            .values());
                }
                dw.watch(8, Integer.valueOf(c));
            }
        }, duration);
    }

    public static void cancelExisting(String name) {
        if (playerEffects.containsKey(name)) {
            final Trail trail = playerEffects.remove(name);
            trail.cancelEffect();
        }
    }
}
