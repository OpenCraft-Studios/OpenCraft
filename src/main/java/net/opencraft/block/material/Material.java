
package net.opencraft.block.material;

public class Material {

    public static final Material air;
    public static final Material ground;
    public static final Material wood;
    public static final Material rock;
    public static final Material iron;
    public static final Material water;
    public static final Material lava;
    public static final Material leaves;
    public static final Material plants;
    public static final Material sponge;
    public static final Material cloth;
    public static final Material fire;
    public static final Material sand;
    public static final Material circuits;
    public static final Material glass;
    public static final Material tnt;
    public static final Material unused;

    public boolean getIsLiquid() {
        return false;
    }

    public boolean isSolid() {
        return true;
    }

    public boolean getCanBlockGrass() {
        return true;
    }

    public boolean getIsSolid() {
        return true;
    }

    static {
        air = new MaterialTransparent();
        ground = new Material();
        wood = new Material();
        rock = new Material();
        iron = new Material();
        water = new MaterialLiquid();
        lava = new MaterialLiquid();
        leaves = new Material();
        plants = new MaterialLogic();
        sponge = new Material();
        cloth = new Material();
        fire = new MaterialTransparent();
        sand = new Material();
        circuits = new MaterialLogic();
        glass = new Material();
        tnt = new Material();
        unused = new Material();
    }
}
