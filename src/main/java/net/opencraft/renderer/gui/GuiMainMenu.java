package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;
import static net.opencraft.SharedConstants.*;
import static org.joml.Math.*;
import static org.lwjgl.opengl.GL11.*;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;

import net.opencraft.animations.GoDownAnimation;
import net.opencraft.renderer.*;
import net.opencraft.util.Splashes;

public class GuiMainMenu extends GuiScreen {

	private float updateCounter;
	private AtomicReference<Float> logoY = new AtomicReference<Float>(0F);
	
	private Splashes splashes = new Splashes();
	
	private String currentSplash;
	private final RenderSkybox panorama = new RenderSkybox(new RenderSkyboxCube("textures/gui/title/background/panorama"));

	public GuiMainMenu() {
		this.updateCounter = 0.0f;

		splashes.load(URI.create(SPLASHES_URL));
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
				oc.displayGuiScreen(new GuiOptions(this, oc.options));
				break;

			case 1:
				oc.displayGuiScreen(new GuiCreateWorld(this));
				break;

			case 2:
				oc.displayGuiScreen(new GuiMultiplayer(this));
				break;

			case 3:
				oc.destroy();
				break;
		}
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		this.panorama.render(partialTicks);
		//this.drawDefaultBackground();
		final Tessellator instance = Tessellator.instance;
		drawLogo(instance);
		glPushMatrix();
		glTranslatef((float) (this.width / 2 + 90), 70.0f, 0.0f);
		glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
		float n = 1.8f - abs(sin(System.currentTimeMillis() % 1000L / 1000.0f * PI_TIMES_2_f) * 0.1f);
		n = n * 100.0f / (this.fontRenderer.width(this.currentSplash) + 32);
		glScalef(n, n, n);
		this.drawCenteredString(this.fontRenderer, this.currentSplash, 0, -8, 16776960);
		glPopMatrix();
		final String gameVersion = TITLE;
		this.drawString(this.fontRenderer, gameVersion, 4, this.height - 12, 0xFFFFFF);
		final long maxMemory = Runtime.getRuntime().maxMemory();
		final long totalMemory = Runtime.getRuntime().totalMemory();
		final String string = new StringBuilder().append("Free memory: ").append((maxMemory - Runtime.getRuntime().freeMemory()) * 100L / maxMemory).append("% of ").append(maxMemory / 1024L / 1024L).append("MB").toString();
		this.drawString(this.fontRenderer, string, this.width - this.fontRenderer.width(string) - 2, 2, 16777215);
		final String string2 = new StringBuilder().append("Allocated memory: ").append(totalMemory * 100L / maxMemory).append("% (").append(totalMemory / 1024L / 1024L).append("MB)").toString();
		this.drawString(this.fontRenderer, string2, this.width - this.fontRenderer.width(string2) - 2, 12, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);

		oc.sndManager.currentMusicTheme = "menu";
		oc.sndManager.playRandomMusicIfReady();
	}

	private void drawLogo(Tessellator t) {
		glBindTexture(GL_TEXTURE_2D, oc.renderer.loadTexture("/assets/gui/logo.png"));
		final int integer3 = 256;
		final int integer4 = 49;
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		t.color(0xFFFFFF);
		
		// Animation
		GoDownAnimation.animate(logoY, 30, 0.2f);
		this.drawTexturedModalRect((this.width - integer3) / 2, logoY.get().intValue(), 0, 0, integer3, integer4);
	}

}
