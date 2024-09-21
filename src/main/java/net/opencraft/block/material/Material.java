
package net.opencraft.block.material;

public class Material {

    public static final Material AIR, GROUND, WOOD, ROCK, METAL, WATER, LAVA,
    							 LEAVES, PLANTS, SPONGE, CLOTH, FIRE, SAND,
    							 REDSTONE, GLASS, TNT, UNUSED;

    public boolean isLiquid() {
        return false;
    }

    public boolean isSolid() {
        return true;
    }

    public boolean isBlockGrass() {
        return true;
    }

    public boolean getIsSolid() {
        return true;
    }

    static {
        AIR = new MaterialTransparent();
        GROUND = new Material();
        WOOD = new Material();
        ROCK = new Material();
        METAL = new Material();
        WATER = new MaterialLiquid();
        LAVA = new MaterialLiquid();
        LEAVES = new Material();
        PLANTS = new MaterialLogic();
        SPONGE = new Material();
        CLOTH = new Material();
        FIRE = new MaterialTransparent();
        SAND = new Material();
        REDSTONE = new MaterialLogic();
        GLASS = new Material();
        TNT = new Material();
        UNUSED = new Material();
    }
}
