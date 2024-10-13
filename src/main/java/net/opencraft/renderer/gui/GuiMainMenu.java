
package net.opencraft.renderer.gui;

import static org.joml.Math.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import net.opencraft.client.Main;
import net.opencraft.renderer.*;
import net.opencraft.util.Splashes;

public class GuiMainMenu extends GuiScreen {

	private float updateCounter;
	
	private Splashes splashes = new Splashes();
	
	private String currentSplash;
	private final RenderSkybox panorama = new RenderSkybox(new RenderSkyboxCube("textures/gui/title/background/panorama"));

	public GuiMainMenu() {
		this.updateCounter = 0.0f;

		splashes.load(getClass().getResourceAsStream("/assets/texts/splashes.txt"));
		currentSplash = splashes.pickRandom();
	}

	@Override
	public void updateScreen() {
		this.updateCounter += 0.01f;
	}

	@Override
	protected void keyTyped(final char character, final int integer) {
	}

	@Override
	public void initGui() {
		this.controlList.clear();
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, "Singleplayer", 200, 20));
		this.controlList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72, "Multiplayer", 200, 20));
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 114, "Options...", 95, 20));
		this.controlList.add(new GuiButton(3, this.width / 2 + 5, this.height / 4 + 114, "Quit Game", 95, 20));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.buttonId) {
			case 0:
				this.id.displayGuiScreen(new GuiOptions(this, this.id.options));
				break;

			case 1:
				this.id.displayGuiScreen(new GuiCreateWorld(this));
				break;

			case 2:
				this.id.displayGuiScreen(new GuiMultiplayer(this));
				break;

			case 3:
				this.id.shutdown();
				break;
		}
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		this.panorama.render(partialTicks);
		//this.drawDefaultBackground();
		final Tessellator instance = Tessellator.instance;
		GL11.glBindTexture(3553, this.id.renderer.loadTexture("/assets/gui/logo.png"));
		final int integer3 = 256;
		final int integer4 = 49;
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		instance.setColorOpaque_I(16777215);
		this.drawTexturedModalRect((this.width - integer3) / 2, 30, 0, 0, integer3, integer4);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) (this.width / 2 + 90), 70.0f, 0.0f);
		GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
		float n = 1.8f - abs(sin(System.currentTimeMillis() % 1000L / 1000.0f * PI_TIMES_2_f) * 0.1f);
		n = n * 100.0f / (this.fontRenderer.getStringWidth(this.currentSplash) + 32);
		GL11.glScalef(n, n, n);
		this.drawCenteredString(this.fontRenderer, this.currentSplash, 0, -8, 16776960);
		GL11.glPopMatrix();
		final String gameVersion = Main.TITLE;
		this.drawString(this.fontRenderer, gameVersion, 4, this.height - 12, 0xFFFFFF);
		final long maxMemory = Runtime.getRuntime().maxMemory();
		final long totalMemory = Runtime.getRuntime().totalMemory();
		final String string = new StringBuilder().append("Free memory: ").append((maxMemory - Runtime.getRuntime().freeMemory()) * 100L / maxMemory).append("% of ").append(maxMemory / 1024L / 1024L).append("MB").toString();
		this.drawString(this.fontRenderer, string, this.width - this.fontRenderer.getStringWidth(string) - 2, 2, 16777215);
		final String string2 = new StringBuilder().append("Allocated memory: ").append(totalMemory * 100L / maxMemory).append("% (").append(totalMemory / 1024L / 1024L).append("MB)").toString();
		this.drawString(this.fontRenderer, string2, this.width - this.fontRenderer.getStringWidth(string2) - 2, 12, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);

		id.sndManager.currentMusicTheme = "menu";
		id.sndManager.playRandomMusicIfReady();
	}

}
