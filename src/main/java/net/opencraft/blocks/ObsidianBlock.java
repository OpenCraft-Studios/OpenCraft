
package net.opencraft.blocks;

import java.util.Random;

public class ObsidianBlock extends StoneBlock {

    public ObsidianBlock(final int blockid, final int blockIndexInTexture) {
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
