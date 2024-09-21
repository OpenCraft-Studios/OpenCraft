
package net.opencraft.blocks;

import net.opencraft.world.World;

public class MushroomBlock extends FlowerBlock {

    protected MushroomBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture);
        final float n = 0.2f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 2.0f, 0.5f + n);
    }

    @Override
    protected boolean canThisPlantGrowOnThisBlockID(final int blockid) {
        return Block.opaqueCubeLookup[blockid];
    }

    @Override
    public boolean canBlockStay(final World world, final int xCoord, final int yCoord, final int zCoord) {
        return world.getBlockLightValue(xCoord, yCoord, zCoord) <= 13 && this.canThisPlantGrowOnThisBlockID(world.getBlockId(xCoord, yCoord - 1, zCoord));
    }
}
