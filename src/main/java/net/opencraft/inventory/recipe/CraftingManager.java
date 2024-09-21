
package net.opencraft.inventory.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.opencraft.blocks.Block;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;

public class CraftingManager {

    private static final CraftingManager instance;
    private List<IRecipe> recipes = new ArrayList<>();

    public static final CraftingManager getInstance() {
        return CraftingManager.instance;
    }

    private CraftingManager() {
        new RecipesTools().addRecipes(this);
        new RecipesSwords().addRecipe(this);
        new RecipesOreBlocks().addRecipes(this);
        new RecipesFood().addRecipes(this);
        new RecipesUtilities().addRecipes(this);
        new RecipesArmor().addRecipes(this);
        
        this.addRecipe(new ItemStack(Block.woolGray, 1), "###", "###", "###", '#', Item.silk);
        this.addRecipe(new ItemStack(Block.tnt, 1), "X#X", "#X#", "X#X", 'X', Item.gunpowder, '#', Block.sand);
        this.addRecipe(new ItemStack(Block.slabSingle, 3), "###", '#', Block.cobblestone);
        this.addRecipe(new ItemStack(Block.ladder, 1), "# #", "###", "# #", '#', Item.stick);
        this.addRecipe(new ItemStack(Item.door, 1), "##", "##", "##", '#', Block.planks);
        this.addRecipe(new ItemStack(Item.sign, 1), "###", "###", " X ", '#', Block.planks, 'X', Item.stick);
        this.addRecipe(new ItemStack(Block.planks, 4), "#", '#', Block.wood);
        this.addRecipe(new ItemStack(Item.stick, 4), "#", "#", '#', Block.planks);
        this.addRecipe(new ItemStack(Block.torch, 4), "X", "#", 'X', Item.coal, '#', Item.stick);
        this.addRecipe(new ItemStack(Item.bowlEmpty, 4), "# #", " # ", '#', Block.planks);
        this.addRecipe(new ItemStack(Block.rail, 16), "X X", "X#X", "X X", 'X', Item.ingotIron, '#', Item.stick);
        this.addRecipe(new ItemStack(Item.minecart, 1), "# #", "###", '#', Item.ingotIron);
        this.addRecipe(new ItemStack(Item.bucketEmpty, 1), "# #", " # ", '#', Item.ingotIron);
        this.addRecipe(new ItemStack(Item.flintAndSteel, 1), "A ", " B", 'A', Item.ingotIron, 'B', Item.flint);
        this.addRecipe(new ItemStack(Item.bread, 1), "###", '#', Item.wheat);
        this.addRecipe(new ItemStack(Block.stairPlanks, 4), "#  ", "## ", "###", '#', Block.planks);
        this.addRecipe(new ItemStack(Block.stairCobblestone, 4), "#  ", "## ", "###", '#', Block.cobblestone);
        this.addRecipe(new ItemStack(Item.painting, 1), "###", "#X#", "###", '#', Item.stick, 'X', Block.woolGray);
        this.addRecipe(new ItemStack(Item.appleGold, 1), "###", "#X#", "###", '#', Block.blockGold, 'X', Item.appleRed);
        Collections.sort(this.recipes, (Comparator) new RecipeSorter(this));
        System.out.println(new StringBuilder().append(this.recipes.size()).append(" recipes").toString());
    }

    void addRecipe(final ItemStack hw, final Object... arr) {
        String s = "";
        int i = 0;
        int integer1 = 0;
        int integer2 = 0;
        if (arr[i] instanceof String[]) {
            final String[] array = (String[]) arr[i++];
            for (int j = 0; j < array.length; ++j) {
                final String s2 = array[j];
                ++integer2;
                integer1 = s2.length();
                s += s2;
            }
        } else {
            while (arr[i] instanceof String) {
                final String s3 = (String) arr[i++];
                ++integer2;
                integer1 = s3.length();
                s += s3;
            }
        }
        final HashMap hashMap = new HashMap();
        while (i < arr.length) {
            final Character c = (Character) arr[i];
            int k = 0;
            if (arr[i + 1] instanceof Item) {
                k = ((Item) arr[i + 1]).shiftedIndex;
            } else if (arr[i + 1] instanceof Block) {
                k = ((Block) arr[i + 1]).blockID;
            }
            ((Map) hashMap).put(c, k);
            i += 2;
        }
        final int[] arr2 = new int[integer1 * integer2];
        for (int k = 0; k < integer1 * integer2; ++k) {
            final char char1 = s.charAt(k);
            if (((Map) hashMap).containsKey(char1)) {
                arr2[k] = (int) ((Map) hashMap).get(char1);
            } else {
                arr2[k] = -1;
            }
        }
        this.recipes.add(new IRecipe(integer1, integer2, arr2, hw));
    }

    public ItemStack findMatchingRecipe(final int[] arr) {
        for (int i = 0; i < this.recipes.size(); ++i) {
            final IRecipe recipe = (IRecipe) this.recipes.get(i);
            if (recipe.matchRecipe(arr)) {
                return recipe.createResult(arr);
            }
        }
        return null;
    }

    static {
        instance = new CraftingManager();
    }
}
