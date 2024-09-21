
package net.opencraft.inventory;

import net.opencraft.item.ItemStack;
import net.opencraft.renderer.gui.GuiContainer;

public class InventoryCrafting implements IInventory {

    private ItemStack[] stackList;
    private int inventoryWidth;
    private GuiContainer eventHandler;

    public InventoryCrafting(final GuiContainer hj, final int integer2, final int integer3) {
        this.inventoryWidth = integer2 * integer3;
        this.stackList = new ItemStack[this.inventoryWidth];
        this.eventHandler = hj;
    }

    public int getSizeInventory() {
        return this.inventoryWidth;
    }

    public ItemStack getStackInSlot(final int integer) {
        return this.stackList[integer];
    }

    public String getInvName() {
        return "Crafting";
    }

    public ItemStack decrStackSize(final int integer1, final int integer2) {
        if (this.stackList[integer1] == null) {
            return null;
        }
        if (this.stackList[integer1].stackSize <= integer2) {
            final ItemStack itemStack = this.stackList[integer1];
            this.stackList[integer1] = null;
            this.eventHandler.onCraftMatrixChanged(this);
            return itemStack;
        }
        final ItemStack splitStack = this.stackList[integer1].splitStack(integer2);
        if (this.stackList[integer1].stackSize == 0) {
            this.stackList[integer1] = null;
        }
        this.eventHandler.onCraftMatrixChanged(this);
        return splitStack;
    }

    public void setInventorySlotContents(final int integer, final ItemStack hw) {
        this.stackList[integer] = hw;
        this.eventHandler.onCraftMatrixChanged(this);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void onInventoryChanged() {
    }
}
