package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;
import static net.opencraft.SharedConstants.*;
import static org.joml.Math.*;
import static org.lwjgl.opengl.GL11.*;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;

import net.opencraft.animations.GoDownAnimation;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.panorama.RenderSkybox;
import net.opencraft.renderer.panorama.RenderSkyboxCube;
import net.opencraft.util.Splashes;

public class GuiMainMenu extends GuiScreen {

	private float updateCounter;
	private final AtomicReference<Float> logoY = new AtomicReference<>(0F);

	private final Splashes splashes = new Splashes();

	private final String currentSplash;
	private final RenderSkybox panorama = new RenderSkybox(
			new RenderSkyboxCube("textures/gui/title/background/panorama"));

	public GuiMainMenu() {
		updateCounter = 0.0f;

		splashes.load(URI.create(SPLASHES_URL));
		currentSplash = splashes.pickRandom();
	}

	@Override
	public void updateScreen() {
		updateCounter += 0.01f;
	}

	@Override
	protected void keyTyped(final char character, final int integer) {
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 48, "Singleplayer", 200, 20));
		buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 72, "Multiplayer", 200, 20));
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 114, "Options...", 95, 20));
		buttonList.add(new GuiButton(3, width / 2 + 5, height / 4 + 114, "Quit Game", 95, 20));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
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
		panorama.render(partialTicks);
		// this.drawDefaultBackground();
		final Tessellator instance = Tessellator.instance;
		drawLogo(instance);
		glPushMatrix();
		glTranslatef(width / 2 + 90, 70.0f, 0.0f);
		glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
		float n = 1.8f - abs(sin(System.currentTimeMillis() % 1000L / 1000.0f * PI_TIMES_2_f) * 0.1f);
		n = n * 100.0f / (fontRenderer.width(currentSplash) + 32);
		glScalef(n, n, n);
		this.drawCenteredString(fontRenderer, currentSplash, 0, -8, 16776960);
		glPopMatrix();
		final String gameVersion = TITLE;
		this.drawString(fontRenderer, gameVersion, 4, height - 12, 0xFFFFFF);
		final long maxMemory = Runtime.getRuntime().maxMemory();
		final long totalMemory = Runtime.getRuntime().totalMemory();
		final String string = new StringBuilder().append("Free memory: ")
				.append((maxMemory - Runtime.getRuntime().freeMemory()) * 100L / maxMemory).append("% of ")
				.append(maxMemory / 1024L / 1024L).append("MB").toString();
		this.drawString(fontRenderer, string, width - fontRenderer.width(string) - 2, 2, 16777215);
		final String string2 = new StringBuilder().append("Allocated memory: ").append(totalMemory * 100L / maxMemory)
				.append("% (").append(totalMemory / 1024L / 1024L).append("MB)").toString();
		this.drawString(fontRenderer, string2, width - fontRenderer.width(string2) - 2, 12, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);

		oc.sndManager.currentMusicTheme = "menu";
		oc.sndManager.playRandomMusicIfReady();
	}

	private void drawLogo(Tessellator t) {
		// Bind the texture
		glBindTexture(GL_TEXTURE_2D, oc.renderer.loadTexture("/assets/gui/logo.png"));

		int logoW = 256;
		int logoH = 49;

		glColor4f(1, 1, 1, 1);
		t.color(0xffffff);

		// Animation
		GoDownAnimation.animate(logoY, 30, 0.2f);
		this.drawTexturedModalRect((width - logoW) / 2, logoY.get().intValue(), 0, 0, logoW, logoH);
	}

}
