
package net.ncraft.inventory.recipe;

import net.ncraft.block.Block;
import net.ncraft.item.Item;
import net.ncraft.item.ItemStack;

public class RecipesFood {

    public void addRecipes(final CraftingManager gy) {
        gy.addRecipe(new ItemStack(Item.bowlSoup), "Y", "X", "#", 'X', Block.mushroomBrown, 'Y', Block.mushroomRed, '#', Item.bowlEmpty);
        gy.addRecipe(new ItemStack(Item.bowlSoup), "Y", "X", "#", 'X', Block.mushroomRed, 'Y', Block.mushroomBrown, '#', Item.bowlEmpty);
    }
}
