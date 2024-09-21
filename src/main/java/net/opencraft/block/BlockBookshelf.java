
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;

public class BlockBookshelf extends Block {

    public BlockBookshelf(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.wood);
    }

    @Override
    public int getBlockTextureFromSide(final int textureIndexSlot) {
        if (textureIndexSlot <= 1) {
            return 4;
        }
        return this.blockIndexInTexture;
    }

    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
}
