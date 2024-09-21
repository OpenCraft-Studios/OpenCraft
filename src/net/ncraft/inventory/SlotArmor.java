
package net.ncraft.inventory;

import net.ncraft.client.gui.GuiContainer;
import net.ncraft.client.gui.GuiInventory;
import net.ncraft.item.ItemArmor;
import net.ncraft.item.ItemStack;

public class SlotArmor extends Slot {

    public final /* synthetic */ int armorType;
    public final /* synthetic */ GuiInventory guiInventory;

    public SlotArmor(GuiInventory ga2, GuiContainer hj2, IInventory kd2, int n, int n2, int n3, int n4) {
        super(hj2, kd2, n, n2, n3);
        this.guiInventory = ga2;
        this.armorType = n4;
    }

    public boolean isItemValid(ItemStack hw2) {
        if (hw2.getItem() instanceof ItemArmor) {
            return ((ItemArmor) hw2.getItem()).armorType == this.armorType;
        }
        return false;
    }

    public int getBackgroundIconIndex() {
        return 15 + this.armorType * 16;
    }
}
