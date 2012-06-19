package com.mitsugaru.VaporTrails.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.mitsugaru.VaporTrails.logic.Trail;
import com.mitsugaru.VaporTrails.logic.VTLogic;

public class VTPlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final String name = event.getPlayer().getName();

        if (!VTLogic.playerEffects.containsKey(name)) {
            return;
        }
        try {
            final Trail trail = VTLogic.playerEffects.get(name);
            VTLogic.playEffect(event.getPlayer(), trail,
                    trail.getData());
        } catch (IllegalArgumentException ia) {
            // IGNORE
        }
    }
}
