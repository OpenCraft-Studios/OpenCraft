
package net.opencraft.inventory.recipe;

import net.opencraft.block.Block;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;

public class RecipesArmor {

    private String[][] recipePatterns;
    private Object[][] recipeItems;

    public RecipesArmor() {
        this.recipePatterns = new String[][]{{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
        this.recipeItems = new Object[][]{{Block.woolGray, Block.fire, Item.ingotIron, Item.diamond, Item.ingotGold}, {Item.helmetLeather, Item.helmetChain, Item.helmetSteel, Item.helmetDiamond, Item.helmetGold}, {Item.plateLeather, Item.plateChain, Item.plateSteel, Item.plateDiamonhd, Item.plateGold}, {Item.legsLeather, Item.legsChain, Item.legsSteel, Item.legsDiamond, Item.legsGold}, {Item.bootsLeather, Item.bootsChain, Item.bootsSteel, Item.bootsDiamond, Item.bootsGold}};
    }

    public void addRecipes(final CraftingManager gy) {
        for (int i = 0; i < this.recipeItems[0].length; ++i) {
            final Object o = this.recipeItems[0][i];
            for (int j = 0; j < this.recipeItems.length - 1; ++j) {
                gy.addRecipe(new ItemStack((Item) this.recipeItems[j + 1][i]), this.recipePatterns[j], 'X', o);
            }
        }
    }
}
