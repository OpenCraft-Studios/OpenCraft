
package net.opencraft.inventory.recipe;

import net.opencraft.block.Block;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;

public class RecipesFood {

    public void addRecipes(final CraftingManager gy) {
        gy.addRecipe(new ItemStack(Item.bowlSoup), "Y", "X", "#", 'X', Block.mushroomBrown, 'Y', Block.mushroomRed, '#', Item.bowlEmpty);
        gy.addRecipe(new ItemStack(Item.bowlSoup), "Y", "X", "#", 'X', Block.mushroomRed, 'Y', Block.mushroomBrown, '#', Item.bowlEmpty);
    }
}
