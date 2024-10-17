
package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;

import java.awt.Point;
import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import net.opencraft.renderer.font.FontRenderer;

public class GuiButton extends GuiElement {

	public int x, y, width, height;

	public String text;
	public int id;
	public boolean enabled;
	public boolean enabled2;

	public GuiButton(int buttonId, int x, int y, String text, int width, int height) {
		this.enabled = true;
		this.enabled2 = true;
		this.id = buttonId;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}

	public void drawButton(final int integer2, final int integer3) {
		if (!this.enabled2)
			return;
		final FontRenderer fontRenderer = oc.font;
		GL11.glBindTexture(3553, oc.renderer.loadTexture("/assets/gui/gui.png"));
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int n = 1;
		final boolean b = integer2 >= this.x && integer3 >= this.y && integer2 < this.x + this.width
				&& integer3 < this.y + this.height;
		if (!this.enabled)
			n = 0;
		else if (b)
			n = 2;
		this.drawTexturedModalRect(this.x, this.y, 0, 46 + n * 20, this.width / 2, this.height);
		this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + n * 20, this.width / 2,
				this.height);
		if (!this.enabled)
			this.drawCenteredString(fontRenderer, this.text, this.x + this.width / 2, this.y + (this.height - 8) / 2,
					-6250336);
		else if (b)
			this.drawCenteredString(fontRenderer, this.text, this.x + this.width / 2, this.y + (this.height - 8) / 2,
					16777120);
		else
			this.drawCenteredString(fontRenderer, this.text, this.x + this.width / 2, this.y + (this.height - 8) / 2,
					14737632);
	}

	public boolean mousePressed(int mouseX, int mouseY) {
		if (!enabled)
			return false;

		return new Rectangle(x, y, width, height).contains(new Point(mouseX, mouseY));
	}

	public void mouseReleased(final int integer1, final int integer2) {

	}

}
