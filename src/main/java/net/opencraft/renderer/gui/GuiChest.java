
package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;

import org.lwjgl.opengl.GL11;

import net.opencraft.inventory.IInventory;
import net.opencraft.inventory.Slot;

public class GuiChest extends GuiContainer {

	private IInventory upperChestInventory;
	private IInventory lowerChestInventory;
	private int inventoryRows;

	public GuiChest(final IInventory kd1, final IInventory kd2) {
		this.inventoryRows = 0;
		this.upperChestInventory = kd1;
		this.lowerChestInventory = kd2;
		this.allowUserInput = false;
		final int n = 222 - 108;
		this.inventoryRows = kd2.getSizeInventory() / 9;
		this.ySize = n + this.inventoryRows * 18;
		final int n2 = (this.inventoryRows - 4) * 18;
		for ( int i = 0; i < this.inventoryRows; ++i ) {
			for ( int j = 0; j < 9; ++j ) {
				this.inventorySlots.add(new Slot(this, kd2, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}
		for ( int i = 0; i < 3; ++i ) {
			for ( int j = 0; j < 9; ++j ) {
				this.inventorySlots.add(new Slot(this, kd1, j + (i + 1) * 9, 8 + j * 18, 103 + i * 18 + n2));
			}
		}
		for ( int i = 0; i < 9; ++i ) {
			this.inventorySlots.add(new Slot(this, kd1, i, 8 + i * 18, 161 + n2));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.draw(this.lowerChestInventory.getInvName(), 8, 6, 0x404040);
		this.fontRenderer.draw(this.upperChestInventory.getInvName(), 8, this.ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float float1) {
		final int texture = oc.renderer.loadTexture("/assets/gui/container.png");
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		oc.renderer.bindTexture(texture);
		final int n = (this.width - this.xSize) / 2;
		final int integer2 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(n, integer2, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
		this.drawTexturedModalRect(n, integer2 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}

}
