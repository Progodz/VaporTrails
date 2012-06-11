package com.mitsugaru.VaporTrails.permissions;

public enum PermissionNode
{
	ADMIN(".admin"), EFFECT_SMOKE(".effect.smoke"), EFFECT_ENDER(
			".effect.ender"), EFFECT_THUNDER(".effect.thunder"), EFFECT_TNT(
			".effect.tnt"), EFFECT_SNOW(".effect.snow"), EFFECT_FIRE(
			".effect.fire"), EFFECT_SHINE(".effect.shine"), EFFECT_BLOCK(
			".effect.block");

	private static final String prefix = "VaporTrails";
	private String node;

	private PermissionNode(String node)
	{
		this.node = prefix + node;
	}

	public String getNode()
	{
		return node;
	}
}
