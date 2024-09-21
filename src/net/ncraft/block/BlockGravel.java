
package net.ncraft.block;

import java.util.Random;
import net.ncraft.item.Item;

public class BlockGravel extends BlockSand {

    public BlockGravel(final int blockid, final int blockIndexInTexture) {
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
