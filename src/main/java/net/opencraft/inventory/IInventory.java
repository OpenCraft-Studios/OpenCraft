
package net.opencraft.inventory;

import net.opencraft.item.ItemStack;

public interface IInventory {

    int getSizeInventory();

    ItemStack getStackInSlot(final int integer);

    ItemStack decrStackSize(final int integer1, final int integer2);

    void setInventorySlotContents(final int integer, final ItemStack hw);

    String getInvName();

    int getInventoryStackLimit();

    void onInventoryChanged();
}
