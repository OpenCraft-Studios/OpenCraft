
package net.opencraft.client.gui;

import net.opencraft.inventory.IInventory;
import net.opencraft.inventory.InventoryCraftResult;
import net.opencraft.inventory.InventoryCrafting;
import net.opencraft.inventory.InventoryPlayer;
import net.opencraft.inventory.Slot;
import net.opencraft.inventory.SlotCrafting;
import net.opencraft.inventory.recipe.CraftingManager;
import net.opencraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiCrafting extends GuiContainer {

    private InventoryCrafting inventoryCrafting;
    private IInventory iInventory;

    public GuiCrafting(final InventoryPlayer ht) {
        this.inventoryCrafting = new InventoryCrafting(this, 3, 3);
        this.iInventory = new InventoryCraftResult();
        this.inventorySlots.add(new SlotCrafting(this, this.inventoryCrafting, this.iInventory, 0, 124, 35));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.inventorySlots.add(new Slot(this, this.inventoryCrafting, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.inventorySlots.add(new Slot(this, ht, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.inventorySlots.add(new Slot(this, ht, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        for (int i = 0; i < 9; ++i) {
            final ItemStack stackInSlot = this.inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null) {
                this.id.player.dropPlayerItem(stackInSlot);
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(final IInventory kd) {
        final int[] arr = new int[9];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                final int integer = i + j * 3;
                final ItemStack stackInSlot = this.inventoryCrafting.getStackInSlot(integer);
                if (stackInSlot == null) {
                    arr[integer] = -1;
                } else {
                    arr[integer] = stackInSlot.itemID;
                }
            }
        }
        this.iInventory.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(arr));
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString2("Crafting", 28, 6, 4210752);
        this.fontRenderer.drawString2("Inventory", 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float float1) {
        final int texture = this.id.renderEngine.getTexture("/assets/gui/crafting.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.id.renderEngine.bindTexture(texture);
        this.drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
    }
}
