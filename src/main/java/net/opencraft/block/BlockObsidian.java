
package net.opencraft.block;

import java.util.Random;

public class BlockObsidian extends BlockStone {

    public BlockObsidian(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture);
    }

    @Override
    public int quantityDropped(final Random random) {
        return 1;
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        return Block.obsidian.blockID;
    }
}
