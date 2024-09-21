
package net.opencraft.item;

import net.opencraft.block.Block;
import net.opencraft.block.material.Material;

public class ItemPickaxe extends ItemTool {

    private static Block[] blocksEffectiveAgainst;
    private int getHarvestLevel;

    public ItemPickaxe(final int itemid, final int toolTier) {
        super(itemid, 2, toolTier, ItemPickaxe.blocksEffectiveAgainst);
        this.getHarvestLevel = toolTier;
    }

    @Override
    public boolean canHarvestBlock(final Block gs) {
        if (gs == Block.obsidian) {
            return this.getHarvestLevel == 3;
        }
        if (gs == Block.blockDiamond || gs == Block.oreDiamond) {
            return this.getHarvestLevel >= 2;
        }
        if (gs == Block.blockGold || gs == Block.oreGold) {
            return this.getHarvestLevel >= 2;
        }
        if (gs == Block.blockSteel || gs == Block.oreIron) {
            return this.getHarvestLevel >= 1;
        }
        return gs.blockMaterial == Material.rock || gs.blockMaterial == Material.iron;
    }

    static {
        ItemPickaxe.blocksEffectiveAgainst = new Block[]{Block.cobblestone, Block.slabDouble, Block.slabSingle, Block.stone, Block.mossyCobblestone, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond};
    }
}
