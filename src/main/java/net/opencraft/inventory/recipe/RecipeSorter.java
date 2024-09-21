
package net.opencraft.inventory.recipe;

import java.util.Comparator;

public class RecipeSorter implements Comparator<IRecipe> {

    public final /* synthetic */ CraftingManager craftingManager;

    public RecipeSorter(final CraftingManager gy) {
        this.craftingManager = gy;
    }

    public int compare(final IRecipe dw1, final IRecipe dw2) {
        if (dw2.getRecipeSize() < dw1.getRecipeSize()) {
            return -1;
        }
        if (dw2.getRecipeSize() > dw1.getRecipeSize()) {
            return 1;
        }
        return 0;
    }
}
