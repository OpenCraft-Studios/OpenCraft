
package net.opencraft.block;

import net.opencraft.block.material.Material;

public class BlockOreStorage extends Block {

    public BlockOreStorage(final int blockid, final int blockIndexInTexture) {
        super(blockid, Material.iron);
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
