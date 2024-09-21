
package net.opencraft.inventory;

import net.opencraft.item.ItemStack;
import net.opencraft.renderer.gui.GuiContainer;

public class SlotCrafting extends Slot {

    private final IInventory craftMatrix;

    public SlotCrafting(final GuiContainer hj, final IInventory kd2, final IInventory kd3, final int integer4, final int integer5, final int integer6) {
        super(hj, kd3, integer4, integer5, integer6);
        this.craftMatrix = kd2;
    }

    @Override
    public boolean isItemValid(final ItemStack hw) {
        return false;
    }

    @Override
    public void onPickupFromSlot() {
        for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i) {
            if (this.craftMatrix.getStackInSlot(i) != null) {
                this.craftMatrix.decrStackSize(i, 1);
            }
        }
    }
}
