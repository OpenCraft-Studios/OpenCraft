
package net.opencraft.blocks;

import net.opencraft.blocks.material.EnumMaterial;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.tileentity.TileEntityMobSpawner;

public class SpawnerBlock extends ContainerBlock {

	protected SpawnerBlock(final int blockid, final int blockIndexInTexture) {
		super(blockid, blockIndexInTexture, EnumMaterial.ROCK);
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
