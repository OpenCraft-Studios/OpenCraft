
package net.opencraft.inventory;

import net.opencraft.item.ItemStack;
import net.opencraft.client.renderer.gui.GuiContainer;

public class Slot {

    public final int slotIndex;
    public final int xPos;
    public final int yPos;
    public final IInventory inventory;
    private final GuiContainer guiHandler;

    public Slot(final GuiContainer hj, final IInventory kd, final int integer3, final int integer4, final int integer5) {
        this.guiHandler = hj;
        this.inventory = kd;
        this.slotIndex = integer3;
        this.xPos = integer4;
        this.yPos = integer5;
    }

    public boolean isAtCursorPos(int integer1, int integer2) {
        final int n = (this.guiHandler.width - this.guiHandler.xSize) / 2;
        final int n2 = (this.guiHandler.height - this.guiHandler.ySize) / 2;
        integer1 -= n;
        integer2 -= n2;
        return integer1 >= this.xPos - 1 && integer1 < this.xPos + 16 + 1 && integer2 >= this.yPos - 1 && integer2 < this.yPos + 16 + 1;
    }

    public void onPickupFromSlot() {
        this.onSlotChanged();
    }

    public boolean isItemValid(final ItemStack hw) {
        return true;
    }

    public ItemStack slotIndex() {
        return this.inventory.getStackInSlot(this.slotIndex);
    }

    public void putStack(final ItemStack hw) {
        this.inventory.setInventorySlotContents(this.slotIndex, hw);
        this.onSlotChanged();
    }

    public int getBackgroundIconIndex() {
        return -1;
    }

    public void onSlotChanged() {
        this.inventory.onInventoryChanged();
    }
}
