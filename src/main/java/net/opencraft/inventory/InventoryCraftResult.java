
package net.opencraft.inventory;

import net.opencraft.item.ItemStack;

public class InventoryCraftResult implements IInventory {

    private ItemStack[] a;

    public InventoryCraftResult() {
        this.a = new ItemStack[1];
    }

    public int getSizeInventory() {
        return 1;
    }

    public ItemStack getStackInSlot(final int integer) {
        return this.a[integer];
    }

    public String getInvName() {
        return "Result";
    }

    public ItemStack decrStackSize(final int integer1, final int integer2) {
        if (this.a[integer1] != null) {
            final ItemStack itemStack = this.a[integer1];
            this.a[integer1] = null;
            return itemStack;
        }
        return null;
    }

    public void setInventorySlotContents(final int integer, final ItemStack hw) {
        this.a[integer] = hw;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void onInventoryChanged() {
    }
}
