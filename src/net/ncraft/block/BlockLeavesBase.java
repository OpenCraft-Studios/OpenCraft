
package net.ncraft.block;

import net.ncraft.block.material.Material;
import net.ncraft.world.IBlockAccess;

public class BlockLeavesBase extends Block {

    protected boolean graphicsLevel;

    protected BlockLeavesBase(final int blockid, final int blockIndexInTexture, final Material material, final boolean boolean4) {
        super(blockid, blockIndexInTexture, material);
        this.graphicsLevel = boolean4;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
        final int blockId = blockAccess.getBlockId(xCoord, yCoord, zCoord);
        return (this.graphicsLevel || blockId != this.blockID) && super.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord, nya4);
    }
}
