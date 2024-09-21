
package net.opencraft.blocks;

import java.util.Random;

import net.opencraft.blocks.material.Material;
import net.opencraft.physics.AABB;
import net.opencraft.world.World;

public class GearsBlock extends Block {

    protected GearsBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.REDSTONE);
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
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
