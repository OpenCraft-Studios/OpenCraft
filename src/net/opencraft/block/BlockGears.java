
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.util.AxisAlignedBB;
import net.opencraft.world.World;

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
