
package net.opencraft.blocks;

import net.opencraft.blocks.material.EnumMaterial;
import net.opencraft.world.IBlockAccess;

public class BreakableBlock extends Block {

	private final boolean localFlag;

	protected BreakableBlock(final int blockid, final int blockIndexInTexture, final EnumMaterial material, final boolean boolean4) {
		super(blockid, blockIndexInTexture, material);
		this.localFlag = boolean4;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int xCoord, final int yCoord, final int zCoord, final int nya4) {
		final int blockId = blockAccess.getBlockId(xCoord, yCoord, zCoord);
		return (this.localFlag || blockId != this.id) && super.shouldSideBeRendered(blockAccess, xCoord, yCoord, zCoord, nya4);
	}

}
