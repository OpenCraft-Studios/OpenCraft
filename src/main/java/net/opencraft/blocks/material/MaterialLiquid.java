
package net.opencraft.blocks.material;

public class MaterialLiquid extends Material {

    @Override
    public boolean isLiquid() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
