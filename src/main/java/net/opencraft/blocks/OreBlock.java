
package net.opencraft.blocks;

import java.util.Random;

import net.opencraft.blocks.material.EnumMaterial;
import net.opencraft.item.Item;

public class OreBlock extends Block {

	public OreBlock(final int blockid, final int blockIndexInTexture) {
		super(blockid, blockIndexInTexture, EnumMaterial.ROCK);
	}

	@Override
	public int idDropped(final int blockid, final Random random) {
		if (this.id == Block.oreCoal.id) {
			return Item.coal.shiftedIndex;
		}
		if (this.id == Block.oreDiamond.id) {
			return Item.diamond.shiftedIndex;
		}
		return this.id;
	}

	@Override
	public int quantityDropped(final Random random) {
		return 1;
	}

}
