
package net.ncraft.block;

import java.util.Random;
import net.ncraft.block.material.Material;
import net.ncraft.util.AxisAlignedBB;
import net.ncraft.world.World;

public class BlockGears extends Block {

    protected BlockGears(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.circuits);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 5;
    }

    @Override
    public int quantityDropped(final Random random) {
        return 1;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }
}
