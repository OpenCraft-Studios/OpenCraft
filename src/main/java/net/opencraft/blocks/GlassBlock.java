
package net.opencraft.blocks;

import java.util.Random;

import net.opencraft.blocks.material.Material;

public class GlassBlock extends BreakableBlock {

	public GlassBlock(final int blockid, final int blockIndexInTexture, final Material material, final boolean boolean4) {
		super(blockid, blockIndexInTexture, material, boolean4);
	}

	@Override
	public int quantityDropped(final Random random) {
		return 0;
	}

}
