
package net.ncraft.block;

import net.ncraft.block.material.Material;
import net.ncraft.tileentity.TileEntity;
import net.ncraft.world.World;

public abstract class BlockContainer extends Block {

    protected BlockContainer(final int blockid, final Material material) {
        super(blockid, material);
    }

    protected BlockContainer(final int blockid, final int textureIndexSlot, final Material material) {
        super(blockid, textureIndexSlot, material);
    }

    @Override
    public void onBlockAdded(final World world, final int xCoord, final int yCoord, final int zCoord) {
        super.onBlockAdded(world, xCoord, yCoord, zCoord);
        world.setBlockTileEntity(xCoord, yCoord, zCoord, this.getBlockEntity());
    }

    @Override
    public void onBlockRemoval(final World world, final int xCoord, final int yCoord, final int zCoord) {
        super.onBlockRemoval(world, xCoord, yCoord, zCoord);
        world.removeBlockTileEntity(xCoord, yCoord, zCoord);
    }

    protected abstract TileEntity getBlockEntity();
}
