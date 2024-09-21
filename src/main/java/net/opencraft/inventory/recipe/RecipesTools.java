
package net.opencraft.inventory.recipe;

import net.opencraft.blocks.Block;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;

public class RecipesTools {

	private String[][] recipePatterns;
	private Object[][] recipeItems;

	public RecipesTools() {
		this.recipePatterns = new String[][] { { "XXX", " # ", " # " }, { "X", "#", "#" }, { "XX", "X#", " #" },
				{ "XX", " #", " #" } };
		this.recipeItems = new Object[][] {
				{ Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold },
				{ Item.pickaxeWood, Item.pickaxeStone, Item.pickaxeSteel, Item.pickaxeDiamond, Item.pickaxeGold },
				{ Item.shovelWood, Item.shovelStone, Item.shovelSteel, Item.shovelDiamond, Item.shovelGold },
				{ Item.axeWood, Item.axeStone, Item.axeSteel, Item.axeDiamond, Item.axeGold },
				{ Item.hoeWood, Item.hoeStone, Item.hoeSteel, Item.hoeDiamond, Item.hoeGold } };
	}

	public void addRecipes(final CraftingManager gy) {
		for (int i = 0; i < this.recipeItems[0].length; ++i) {
			final Object o = this.recipeItems[0][i];
			for (int j = 0; j < this.recipeItems.length - 1; ++j) {
				gy.addRecipe(new ItemStack((Item) this.recipeItems[j + 1][i]), this.recipePatterns[j], '#', Item.stick,
						'X', o);
			}
		}
	}
}
