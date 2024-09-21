
package net.opencraft.inventory.recipe;

import net.opencraft.blocks.Block;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;

public class RecipesSwords {

    private String[][] recipePatterns;
    private Object[][] recipeItems;

    public RecipesSwords() {
        this.recipePatterns = new String[][]{{"X", "X", "#"}};
        this.recipeItems = new Object[][]{{Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold}, {Item.swordWood, Item.swordStone, Item.swordSteel, Item.swordDiamond, Item.swordGold}};
    }

    public void addRecipe(final CraftingManager gy) {
        for (int i = 0; i < this.recipeItems[0].length; ++i) {
            final Object o = this.recipeItems[0][i];
            for (int j = 0; j < this.recipeItems.length - 1; ++j) {
                gy.addRecipe(new ItemStack((Item) this.recipeItems[j + 1][i]), this.recipePatterns[j], '#', Item.stick, 'X', o);
            }
        }
        gy.addRecipe(new ItemStack(Item.bow, 1), " #X", "# X", " #X", 'X', Item.silk, '#', Item.stick);
        gy.addRecipe(new ItemStack(Item.arrow, 4), "X", "#", "Y", 'Y', Item.feather, 'X', Item.ingotIron, '#', Item.stick);
    }
}
