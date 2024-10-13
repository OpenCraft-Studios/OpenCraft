
package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;

import org.lwjgl.opengl.GL11;

import net.opencraft.inventory.InventoryPlayer;
import net.opencraft.inventory.Slot;
import net.opencraft.tileentity.TileEntityFurnace;

public class GuiFurnace extends GuiContainer {

	private TileEntityFurnace furnaceInventory;

	public GuiFurnace(final InventoryPlayer ht, final TileEntityFurnace el) {
		this.furnaceInventory = el;
		this.inventorySlots.add(new Slot(this, el, 0, 56, 17));
		this.inventorySlots.add(new Slot(this, el, 1, 56, 53));
		this.inventorySlots.add(new Slot(this, el, 2, 116, 35));
		for ( int i = 0; i < 3; ++i ) {
			for ( int j = 0; j < 9; ++j ) {
				this.inventorySlots.add(new Slot(this, ht, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for ( int i = 0; i < 9; ++i ) {
			this.inventorySlots.add(new Slot(this, ht, i, 8 + i * 18, 142));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.draw("Furnace", 60, 6, 4210752);
		this.fontRenderer.draw("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float float1) {
		final int texture = oc.renderer.loadTexture("/assets/gui/furnace.png");
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		oc.renderer.bindTexture(texture);
		final int integer1 = (this.width - this.xSize) / 2;
		final int integer2 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(integer1, integer2, 0, 0, this.xSize, this.ySize);
		if (this.furnaceInventory.isBurning()) {
			final int n = this.furnaceInventory.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(integer1 + 56, integer2 + 36 + 12 - n, 176, 12 - n, 14, n + 2);
		}
		final int n = this.furnaceInventory.getCookProgressScaled(24);
		this.drawTexturedModalRect(integer1 + 79, integer2 + 34, 176, 14, n + 1, 16);
	}

}
