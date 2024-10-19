package net.opencraft.blocks.material;

public enum EnumMaterial {

	/*
	 * static {
		AIR = new MaterialTransparent();
		GROUND = new EnumMaterial();
		WOOD = new EnumMaterial();
		ROCK = new EnumMaterial();
		METAL = new EnumMaterial();
		WATER = new MaterialLiquid();
		LAVA = new MaterialLiquid();
		LEAVES = new EnumMaterial();
		PLANTS = new MaterialLogic();
		SPONGE = new EnumMaterial();
		CLOTH = new EnumMaterial();
		FIRE = new MaterialTransparent();
		SAND = new EnumMaterial();
		REDSTONE = new MaterialLogic();
		GLASS = new EnumMaterial();
		TNT = new EnumMaterial();
		UNUSED = new EnumMaterial();
	}
	 * */
	
	AIR(EnumMaterial.TRANSPARENT),
	GROUND,
	WOOD,
	ROCK,
	METAL,
	WATER(EnumMaterial.LIQUID),
	LAVA(EnumMaterial.LIQUID),
	LEAVES,
	PLANTS(EnumMaterial.LOGIC),
	SPONGE,
	CLOTH,
	FIRE(EnumMaterial.TRANSPARENT),
	SAND,
	REDSTONE(EnumMaterial.LOGIC),
	GLASS,
	TNT,
	UNUSED;
	
	private static final byte TRANSPARENT = 0b001,
							  LIQUID      = 0b100,
							  LOGIC       = 0b000;
	
	private boolean liquid, solid, grass;
	
	EnumMaterial(boolean liquid, boolean solid, boolean grass) {
		this.liquid = liquid;
		this.solid = solid;
		this.grass = grass;
	}
	
	EnumMaterial(byte type) {
		this(((type >> 2) & 0xFF) == 1, ((type >> 2) & 0xFF) == 1, (type & 0xFF) == 1);
	}
	
	EnumMaterial() {
		this(false, true, true);
	}
	
	public boolean isLiquid() {
		return liquid;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isBlockGrass() {
		return grass;
	}

}
