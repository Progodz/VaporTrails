package com.mitsugaru.VaporTrails.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mitsugaru.VaporTrails.VaporTrails;
import com.mitsugaru.VaporTrails.logic.Trail;
import com.mitsugaru.VaporTrails.logic.Trail.Type;
import com.mitsugaru.VaporTrails.logic.VTLogic;
import com.mitsugaru.VaporTrails.permissions.PermissionHandler;

public class VTPlayerListener implements Listener {
    private VaporTrails plugin;

    public VTPlayerListener(VaporTrails vt) {
        plugin = vt;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (VTLogic.playerEffects.containsKey(event.getPlayer().getName())) {
            VTLogic.cancelExisting(event.getPlayer().getName());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerModeChange(PlayerGameModeChangeEvent event) {
        if (!event.isCancelled() || !plugin.getPluginConfig().gamemode) {
            return;
        }
        switch (event.getNewGameMode()) {
            case SURVIVAL: {
                VTLogic.playerEffects.remove(event.getPlayer());
                break;
            }
            case CREATIVE: {
                Type type = Type.BLOCK;
                try {
                    type = Type
                            .valueOf(plugin.getPluginConfig().gameModeEffect);
                } catch (IllegalArgumentException ia) {
                    return;
                }
                if (type == Type.BLOCK) {
                    return;
                }
                else if (!PermissionHandler.has(event.getPlayer(),
                        type.getPermissionNode())) {
                    return;
                }
                VTLogic.playerEffects.put(event.getPlayer().getName(),
                        new Trail(plugin, event.getPlayer().getName(), type));
            }
            default: {
                break;
            }
        }
    }
}
