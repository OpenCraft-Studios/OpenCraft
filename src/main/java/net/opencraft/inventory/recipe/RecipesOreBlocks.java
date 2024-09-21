
package net.opencraft.inventory.recipe;

import net.opencraft.blocks.Block;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;

public class RecipesOreBlocks {

    private Object[][] recipeItems;

    public RecipesOreBlocks() {
        this.recipeItems = new Object[][]{{Block.blockGold, Item.ingotGold}, {Block.blockSteel, Item.ingotIron}, {Block.blockDiamond, Item.diamond}};
    }

    public void addRecipes(final CraftingManager gy) {
        for (int i = 0; i < this.recipeItems.length; ++i) {
            final Block gs = (Block) this.recipeItems[i][0];
            final Item ge = (Item) this.recipeItems[i][1];
            gy.addRecipe(new ItemStack(gs), "###", "###", "###", '#', ge);
            gy.addRecipe(new ItemStack(ge, 9), "#", '#', gs);
        }
    }
}
