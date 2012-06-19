package com.mitsugaru.VaporTrails.logic;

import org.bukkit.entity.Player;

import com.mitsugaru.VaporTrails.VaporTrails;
import com.mitsugaru.VaporTrails.config.RootConfig;
import com.mitsugaru.VaporTrails.permissions.PermissionNode;

public class Trail {
    private Trail trail;
    private VaporTrails plugin;
    private String name = "", title;
    private Type type;
    private int id, blockid = 0, data = 0, interval = RootConfig.getDefaultInterval();

    public Trail(VaporTrails plugin, String name, Type type) {
        this.trail = this;
        this.plugin = plugin;
        this.name = name;
        this.type = type;
        this.data = type.getData();
        schedule();
    }

    public Trail(VaporTrails plugin, String name, Type type, int blockid,
            int data) {
        this.trail = this;
        this.plugin = plugin;
        this.name = name;
        this.type = type;
        this.blockid = blockid;
        this.data = data;
        schedule();
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        return type.getTitle();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public int getBlockId() {
        return blockid;
    }

    public int getData() {
        return data;
    }
    
    public void setInterval(int interval)
    {
        if(interval <= 0)
        {
            return;
        }
        cancelEffect();
        this.interval = interval;
        schedule();
    }

    public void cancelEffect() {
        if (id != -1) {
            plugin.getServer().getScheduler().cancelTask(id);
        }
    }

    private void schedule() {
        if (!plugin.getPluginConfig().useListener) {
            // Schedule effect
            id = plugin
                    .getServer()
                    .getScheduler()
                    .scheduleSyncRepeatingTask(plugin, new TrailTask(), 0,
                            interval);
        }
    }

    private class TrailTask implements Runnable {
        @Override
        public void run() {
            final Player player = plugin.getServer().getPlayer(name);
            if (player != null && player.isOnline()) {
                VTLogic.playEffect(player, trail, data);
            }
        }
    }

    public enum Type {
        SMOKE("Smoke", 0, PermissionNode.EFFECT_SMOKE),
        ENDERSIGNAL("Ender Signal", 0, PermissionNode.EFFECT_ENDER),
        LIGHTNING("Lightning", 0, PermissionNode.EFFECT_THUNDER),
        TNT("TNT", 0, PermissionNode.EFFECT_TNT),
        SNOW("Snow", 0, PermissionNode.EFFECT_SNOW),
        FIRE("Fire", 0, PermissionNode.EFFECT_FIRE),
        SHINE("Shine", 0, PermissionNode.EFFECT_SHINE),
        SWIRL("Swirl", 1, PermissionNode.EFFECT_SWIRL),
        POTION_PINK("Pink potion swirl", 1, PermissionNode.EFFECT_POTION_PINK),
        POTION_AQUA("Aqua potion swirl", 2, PermissionNode.EFFECT_POTION_AQUA),
        POTION_GOLD("Gold potion swirl", 3, PermissionNode.EFFECT_POTION_GOLD),
        POTION_GREEN("Green potion swirl", 4,
                PermissionNode.EFFECT_POTION_GREEN),
        POTION_RED("Red sparks", 5, PermissionNode.EFFECT_POTION_RED),
        POTION_DARKRED("Dark red sparks", 12,
                PermissionNode.EFFECT_POTION_DARKRED),
        POTION_GRAY("Gray potion swirl", 8, PermissionNode.EFFECT_POTION_GRAY),
        POTION_CRIMSON("Crimson potion swirl", 9,
                PermissionNode.EFFECT_POTION_CRIMSON),
        POTION_CYAN("Cyan potion swirl", 10, PermissionNode.EFFECT_POTION_CYAN),
        POTION_BLUE("Blue potion swirl", 7, PermissionNode.EFFECT_POTION_BLUE),
        BLOCK("Block", 0, PermissionNode.EFFECT_BLOCK);

        private String title;
        private int data;
        private PermissionNode node;

        private Type(String title, int data, PermissionNode node) {
            this.title = title;
            this.data = data;
            this.node = node;
        }

        public String getTitle() {
            return title;
        }

        public int getData() {
            return data;
        }

        public PermissionNode getPermissionNode() {
            return node;
        }
    }
}
