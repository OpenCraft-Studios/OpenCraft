
package net.ncraft.block;

import java.util.Random;
import net.ncraft.block.material.Material;
import net.ncraft.util.AxisAlignedBB;
import net.ncraft.world.World;

public class BlockFlower extends Block {

    protected BlockFlower(final int blockid, final int blockIndexInTexture) {
        super(blockid, Material.plants);
        this.blockIndexInTexture = blockIndexInTexture;
        this.setTickOnLoad(true);
        final float n = 0.2f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 3.0f, 0.5f + n);
    }

    @Override
    public boolean canPlaceBlockAt(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return this.canThisPlantGrowOnThisBlockID(world.getBlockId(xCoord, yCoord - 1, zCoord));
    }

    protected boolean canThisPlantGrowOnThisBlockID(final int blockid) {
        return blockid == Block.grass.blockID || blockid == Block.dirt.blockID || blockid == Block.tilledField.blockID;
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        super.onNeighborBlockChange(world, xCoord, yCoord, zCoord, nya4);
        this.checkFlowerChange(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord, final Random random) {
        this.checkFlowerChange(world, xCoord, yCoord, zCoord);
    }

    protected final void checkFlowerChange(final World world, final int xCoord, final int yCoord, final int zCoord) {
        if (!this.canBlockStay(world, xCoord, yCoord, zCoord)) {
            this.dropBlockAsItem(world, xCoord, yCoord, zCoord, world.getBlockMetadata(xCoord, yCoord, zCoord));
            world.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
        }
    }

    public boolean canBlockStay(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return (world.getBlockLightValue(xCoord, yCoord, zCoord) >= 8 || world.canBlockSeeTheSky(xCoord, yCoord, zCoord)) && this.canThisPlantGrowOnThisBlockID(world.getBlockId(xCoord, yCoord - 1, zCoord));
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
        return 1;
    }
}
