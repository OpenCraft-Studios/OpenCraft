
package net.opencraft.inventory;

import net.opencraft.item.ItemStack;

public class InventoryLargeChest implements IInventory {

    private String name;
    private IInventory upperChest;
    private IInventory lowerChest;

    public InventoryLargeChest(final String string, final IInventory kd2, final IInventory kd3) {
        this.name = string;
        this.upperChest = kd2;
        this.lowerChest = kd3;
    }

    public int getSizeInventory() {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }

    public String getInvName() {
        return this.name;
    }

    public ItemStack getStackInSlot(final int integer) {
        if (integer >= this.upperChest.getSizeInventory()) {
            return this.lowerChest.getStackInSlot(integer - this.upperChest.getSizeInventory());
        }
        return this.upperChest.getStackInSlot(integer);
    }

    public ItemStack decrStackSize(final int integer1, final int integer2) {
        if (integer1 >= this.upperChest.getSizeInventory()) {
            return this.lowerChest.decrStackSize(integer1 - this.upperChest.getSizeInventory(), integer2);
        }
        return this.upperChest.decrStackSize(integer1, integer2);
    }

    public void setInventorySlotContents(final int integer, final ItemStack hw) {
        if (integer >= this.upperChest.getSizeInventory()) {
            this.lowerChest.setInventorySlotContents(integer - this.upperChest.getSizeInventory(), hw);
        } else {
            this.upperChest.setInventorySlotContents(integer, hw);
        }
    }

    public int getInventoryStackLimit() {
        return this.upperChest.getInventoryStackLimit();
    }

    public void onInventoryChanged() {
        this.upperChest.onInventoryChanged();
        this.lowerChest.onInventoryChanged();
    }
}
