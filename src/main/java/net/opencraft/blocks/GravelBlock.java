
package net.opencraft.blocks;

import java.util.Random;
import net.opencraft.item.Item;

public class GravelBlock extends SandBlock {

    public GravelBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture);
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        if (random.nextInt(10) == 0) {
            return Item.flint.shiftedIndex;
        }
        return this.blockID;
    }
}
