
package net.ncraft.block;

import java.util.Random;
import net.ncraft.block.material.Material;
import net.ncraft.item.Item;

public class BlockOre extends Block {

    public BlockOre(final int blockid, final int blockIndexInTexture) {
        super(blockid, blockIndexInTexture, Material.rock);
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
