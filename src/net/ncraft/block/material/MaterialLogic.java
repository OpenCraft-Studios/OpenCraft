
package net.ncraft.block.material;

public class MaterialLogic extends Material {

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean getCanBlockGrass() {
        return false;
    }

    @Override
    public boolean getIsSolid() {
        return false;
    }
}
