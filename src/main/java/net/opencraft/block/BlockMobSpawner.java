
package net.opencraft.block;

import net.opencraft.block.material.Material;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.tileentity.TileEntityMobSpawner;

public class BlockMobSpawner extends BlockContainer {

    protected BlockMobSpawner(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.rock);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityMobSpawner();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
