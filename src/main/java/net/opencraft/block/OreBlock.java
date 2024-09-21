
package net.opencraft.block;

import java.util.Random;
import net.opencraft.block.material.Material;
import net.opencraft.item.Item;

public class OreBlock extends Block {

    public OreBlock(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.ROCK);
    }

    @Override
    public int idDropped(final int blockid, final Random random) {
        if (this.blockID == Block.oreCoal.blockID) {
            return Item.coal.shiftedIndex;
        }
        if (this.blockID == Block.oreDiamond.blockID) {
            return Item.diamond.shiftedIndex;
        }
        return this.blockID;
    }

    @Override
    public int quantityDropped(final Random random) {
        return 1;
    }
}
