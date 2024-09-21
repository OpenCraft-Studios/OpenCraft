
package net.opencraft.item;

import net.opencraft.block.Block;

public class ItemAxe extends ItemTool {

    private static Block[] blocksEffectiveAgainst;

    public ItemAxe(final int itemid, final int toolTier) {
        super(itemid, 3, toolTier, ItemAxe.blocksEffectiveAgainst);
    }

    static {
        ItemAxe.blocksEffectiveAgainst = new Block[]{Block.planks, Block.bookshelf, Block.wood, Block.chest};
    }
}
