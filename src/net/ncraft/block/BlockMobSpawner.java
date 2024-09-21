
package net.ncraft.block;

import net.ncraft.block.material.Material;
import net.ncraft.tileentity.TileEntity;
import net.ncraft.tileentity.TileEntityMobSpawner;

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
