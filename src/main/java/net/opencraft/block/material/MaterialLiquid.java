
package net.opencraft.block.material;

public class MaterialLiquid extends Material {

    @Override
    public boolean getIsLiquid() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
