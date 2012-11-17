package com.mitsugaru.VaporTrails.permissions;

public enum PermissionNode {
    TIME(".time")
    ADMIN(".admin"),
    EFFECT_SMOKE(".effect.smoke"),
    EFFECT_ENDER(".effect.ender"),
    EFFECT_THUNDER(".effect.thunder"),
    EFFECT_TNT(".effect.tnt"),
    EFFECT_SNOW(".effect.snow"),
    EFFECT_FIRE(".effect.fire"),
    EFFECT_SHINE(".effect.shine"),
    EFFECT_SWIRL(".effect.swirl"),
    EFFECT_POTION_PINK(".effect.potion.pink"),
    EFFECT_POTION_AQUA(".effect.potion.aqua"),
    EFFECT_POTION_GOLD(".effect.potion.gold"),
    EFFECT_POTION_GREEN(".effect.potion.green"),
    EFFECT_POTION_RED(".effect.potion.red"),
    EFFECT_POTION_DARKRED(".effect.potion.darkred"),
    EFFECT_POTION_GRAY(".effect.potion.gray"),
    EFFECT_POTION_CRIMSON(".effect.potion.crimson"),
    EFFECT_POTION_CYAN(".effect.potion.cyan"),
    EFFECT_POTION_BLUE(".effect.potion.blue"),
    EFFECT_BLOCK(".effect.block");

    private static final String prefix = "VaporTrails";
    private String node;

    private PermissionNode(String node) {
        this.node = prefix + node;
    }

    public String getNode() {
        return node;
    }
}
