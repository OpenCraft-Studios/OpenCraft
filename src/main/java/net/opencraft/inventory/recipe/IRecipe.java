
package net.opencraft.inventory.recipe;

import net.opencraft.item.ItemStack;

public class IRecipe {

    private int b;
    private int c;
    private int[] d;
    private ItemStack e;
    public final int a;

    public IRecipe(final int integer1, final int integer2, final int[] arr, final ItemStack hw) {
        this.a = hw.itemID;
        this.b = integer1;
        this.c = integer2;
        this.d = arr;
        this.e = hw;
    }

    public boolean matchRecipe(final int[] arr) {
        for (int i = 0; i <= 3 - this.b; ++i) {
            for (int j = 0; j <= 3 - this.c; ++j) {
                if (this.a(arr, i, j, true)) {
                    return true;
                }
                if (this.a(arr, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(final int[] arr, final int integer2, final int integer3, final boolean boolean4) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                final int n = i - integer2;
                final int n2 = j - integer3;
                int n3 = -1;
                if (n >= 0 && n2 >= 0 && n < this.b && n2 < this.c) {
                    if (boolean4) {
                        n3 = this.d[this.b - n - 1 + n2 * this.b];
                    } else {
                        n3 = this.d[n + n2 * this.b];
                    }
                }
                if (arr[i + j * 3] != n3) {
                    return false;
                }
            }
        }
        return true;
    }

    public ItemStack createResult(final int[] arr) {
        return new ItemStack(this.e.itemID, this.e.stackSize);
    }

    public int getRecipeSize() {
        return this.b * this.c;
    }
}
