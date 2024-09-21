
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.util.AxisAlignedBB;
import net.opencraft.world.World;

public class BlockLadder extends Block {

    protected BlockLadder(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.circuits);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        final float n = 0.125f;
        if (blockMetadata == 2) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
        }
        if (blockMetadata == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
        }
        if (blockMetadata == 4) {
            this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (blockMetadata == 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
        }
        return super.getCollisionBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        final float n = 0.125f;
        if (blockMetadata == 2) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
        }
        if (blockMetadata == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
        }
        if (blockMetadata == 4) {
            this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (blockMetadata == 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
        }
        return super.getSelectedBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
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
        return 8;
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return world.isBlockNormalCube(xCoord - 1, yCoord, zCoord) || world.isBlockNormalCube(xCoord + 1, yCoord, zCoord) || world.isBlockNormalCube(xCoord, yCoord, zCoord - 1) || world.isBlockNormalCube(xCoord, yCoord, zCoord + 1);
    }

    @Override
    public void onBlockPlaced(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        if ((blockMetadata == 0 || nya4 == 2) && world.isBlockNormalCube(xCoord, yCoord, zCoord + 1)) {
            blockMetadata = 2;
        }
        if ((blockMetadata == 0 || nya4 == 3) && world.isBlockNormalCube(xCoord, yCoord, zCoord - 1)) {
            blockMetadata = 3;
        }
        if ((blockMetadata == 0 || nya4 == 4) && world.isBlockNormalCube(xCoord + 1, yCoord, zCoord)) {
            blockMetadata = 4;
        }
        if ((blockMetadata == 0 || nya4 == 5) && world.isBlockNormalCube(xCoord - 1, yCoord, zCoord)) {
            blockMetadata = 5;
        }
        world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata);
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        boolean b = false;
        if (blockMetadata == 2 && world.isBlockNormalCube(xCoord, yCoord, zCoord + 1)) {
            b = true;
        }
        if (blockMetadata == 3 && world.isBlockNormalCube(xCoord, yCoord, zCoord - 1)) {
            b = true;
        }
        if (blockMetadata == 4 && world.isBlockNormalCube(xCoord + 1, yCoord, zCoord)) {
            b = true;
        }
        if (blockMetadata == 5 && world.isBlockNormalCube(xCoord - 1, yCoord, zCoord)) {
            b = true;
        }
        if (!b) {
            this.dropBlockAsItem(world, xCoord, yCoord, zCoord, blockMetadata);
            world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
        }
        super.onNeighborBlockChange(world, xCoord, yCoord, zCoord, nya4);
    }

    @Override
    public int quantityDropped(final Random random) {
        return 1;
    }
}
