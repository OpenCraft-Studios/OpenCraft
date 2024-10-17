
package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;

import org.lwjgl.opengl.GL11;

public class GuiGameOver extends GuiScreen {

	@Override
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 72, "Respawn", 200, 20));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96, "Title menu", 200, 20));
		if (oc.sessionData == null) {
			((GuiButton) this.buttonList.get(1)).enabled = false;
		}
	}

	@Override
	protected void keyTyped(final char character, final int integer) {
	}

	@Override
	protected void actionPerformed(final GuiButton iq) {
		if (iq.id == 0) {
		}
		if (iq.id == 1) {
			oc.respawn();
			oc.displayGuiScreen(null);
		}
		if (iq.id == 2) {
			oc.changeWorld1(null);
			oc.displayGuiScreen(new GuiMainMenu());
		}
	}

	@Override
	public void drawScreen(final int integer1, final int integer2, final float float3) {
		this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
		GL11.glPushMatrix();
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		this.drawCenteredString(this.fontRenderer, "Game over!", this.width / 2 / 2, 30, 16777215);
		GL11.glPopMatrix();
		this.drawCenteredString(this.fontRenderer, new StringBuilder().append("Score: &e").append(oc.player.getScore()).toString(), this.width / 2, 100, 16777215);
		super.drawScreen(integer1, integer2, float3);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
