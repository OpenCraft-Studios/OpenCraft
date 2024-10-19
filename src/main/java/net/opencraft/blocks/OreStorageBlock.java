
package net.opencraft.blocks;

import net.opencraft.blocks.material.EnumMaterial;

public class OreStorageBlock extends Block {

	public OreStorageBlock(final int blockid, final int blockIndexInTexture) {
		super(blockid, EnumMaterial.METAL);
		this.blockIndexInTexture = blockIndexInTexture;
	}

	@Override
	public int getBlockTextureFromSide(final int textureIndexSlot) {
		if (textureIndexSlot == 1) {
			return this.blockIndexInTexture - 16;
		}
		if (textureIndexSlot == 0) {
			return this.blockIndexInTexture + 16;
		}
		return this.blockIndexInTexture;
	}

}
