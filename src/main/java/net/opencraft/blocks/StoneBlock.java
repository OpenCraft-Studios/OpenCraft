
package net.opencraft.blocks;

import java.util.Random;

import net.opencraft.blocks.material.Material;

public class StoneBlock extends Block {

	public StoneBlock(final int blockid, final int blockIndexInTexture) {
		super(blockid, blockIndexInTexture, Material.ROCK);
	}

	@Override
	public int idDropped(final int blockid, final Random random) {
		return Block.cobblestone.id;
	}

}
