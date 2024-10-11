package net.opencraft;

public enum EnumSkyBlock {

	Sky(15),
	Block(0);

	public final int defaultLightValue;

	private EnumSkyBlock(int lightValue) {
		this.defaultLightValue = lightValue;
	}

}
