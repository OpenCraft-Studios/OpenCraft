
package net.ncraft.inventory.recipe;

import net.ncraft.block.Block;
import net.ncraft.item.ItemStack;

public class RecipesUtilities {

    public void addRecipes(final CraftingManager gy) {
        gy.addRecipe(new ItemStack(Block.chest), "###", "# #", "###", '#', Block.planks);
        gy.addRecipe(new ItemStack(Block.stoneOvenIdle), "###", "# #", "###", '#', Block.cobblestone);
        gy.addRecipe(new ItemStack(Block.workbench), "##", "##", '#', Block.planks);
    }
}
