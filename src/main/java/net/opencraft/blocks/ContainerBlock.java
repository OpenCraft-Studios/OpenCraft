
package net.opencraft.blocks;

import net.opencraft.blocks.material.EnumMaterial;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.world.World;

public abstract class ContainerBlock extends Block {

	protected ContainerBlock(final int blockid, final EnumMaterial material) {
		super(blockid, material);
	}

	protected ContainerBlock(final int blockid, final int textureIndexSlot, final EnumMaterial material) {
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
