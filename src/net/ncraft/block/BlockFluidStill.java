
package net.ncraft.block;

import net.ncraft.block.material.Material;
import net.ncraft.world.World;

public class BlockFluidStill extends BlockFluid {

    protected BlockFluidStill(final int blockid, final Material material) {
        super(blockid, material);
        this.setTickOnLoad(false);
    }

    @Override
    public void onNeighborBlockChange(final World world, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        super.onNeighborBlockChange(world, xCoord, yCoord, zCoord, nya4);
        if (world.getBlockId(xCoord, yCoord, zCoord) == this.blockID) {
            this.updateTick(world, xCoord, yCoord, zCoord);
        }
    }

    private void updateTick(final World world, final int xCoord, final int yCoord, final int zCoord) {
        final int blockMetadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
        world.editingBlocks = true;
        world.setBlockAndMetadata(xCoord, yCoord, zCoord, this.blockID - 1, blockMetadata);
        world.markBlocksDirty(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
        world.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID - 1);
        world.editingBlocks = false;
    }
}
