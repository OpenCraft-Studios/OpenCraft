
package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.opencraft.OpenCraft;
import net.opencraft.client.input.MouseHandler;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.font.FontRenderer;

public abstract class GuiScreen extends GuiElement {

	public int width;
	public int height;
	protected List<GuiButton> buttonList;
	public boolean allowUserInput;
	protected FontRenderer fontRenderer;

	public GuiScreen() {
		buttonList = new ArrayList<>();
		allowUserInput = false;
	}

	public void drawScreen(int integer1, int integer2, float float3) {
		for (int i = 0; i < buttonList.size(); ++i)
			buttonList.get(i).drawButton(integer1, integer2);
	}

	protected void keyTyped(final char character, final int integer) {
		if (integer == 1) {
			oc.displayGuiScreen(null);
			oc.setIngameFocus();
		}
	}

	protected void drawSlotInventory(final int integer1, final int integer2, final int integer3) {
		if (integer3 != 0)
			return;

		for (GuiButton button : buttonList) {
			if (!button.mousePressed(integer1, integer2))
				continue;

			oc.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
			this.actionPerformed(button);
		}
	}

	protected void b(final int integer1, final int integer2, final int integer3) {
		if (integer3 != 0)
			return;

		for (int i = 0; i < buttonList.size(); ++i) {
			final GuiButton iq = buttonList.get(i);
			iq.mouseReleased(integer1, integer2);
		}
	}

	protected void actionPerformed(final GuiButton iq) {
	}

	public void setWorldAndResolution(final int integer2, final int integer3) {
		fontRenderer = oc.font;
		width = integer2;
		height = integer3;
		this.initGui();
	}

	public void initGui() {
	}

	public void handleInputEvents() {
		for (MouseHandler.ButtonEvent event : oc.mouse.buttons.events)
			this.handleMouseEvent(event);

		for (int key : oc.keyboard.pressedKeys) {
			if (key == -1)
				continue;

			if (glfwGetKeyName(key, glfwGetKeyScancode(key)) != null)
				this.handleKeyboardInput(glfwGetKeyName(key, glfwGetKeyScancode(key)).charAt(0), key);
		}
	}

	public void handleMouseEvent(MouseHandler.ButtonEvent event) {
		final int n = (int) oc.mouse.position.x * width / oc.width;
		final int n2 = height - (int) oc.mouse.position.y * height / oc.height - 1;
		if (event.isPressed())
			this.drawSlotInventory(n, n2, event.buttonNumber());
		else
			this.b(n, n2, event.buttonNumber());
	}

	public void handleKeyboardInput(char c, int key) {
		this.keyTyped(c, key);
	}

	public void updateScreen() {
	}

	public void onGuiClosed() {
	}

	public void drawDefaultBackground() {
		this.b(0);
	}

	public void b(final int integer) {
		if (oc.world != null)
			this.drawGradientRect(0, 0, width, height, 1610941696, -1607454656);
		else {
			GL11.glDisable(2896);
			GL11.glDisable(2912);
			final Tessellator t = Tessellator.instance;
			GL11.glBindTexture(3553, oc.renderer.loadTexture("/assets/dirt.png"));
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			final float n = 32.0f;
			t.beginQuads();
			{
				t.color(0x404040);
				
				t.vertexUV(0.0, height, 0.0, 0.0, height / n + integer);
				t.vertexUV(width, height, 0.0, width / n, height / n + integer);
				t.vertexUV(width, 0.0, 0.0, width / n, 0 + integer);
				t.vertexUV(0.0, 0.0, 0.0, 0.0, 0 + integer);
			}
			t.render();
		}
	}

	public boolean doesGuiPauseGame() {
		return true;
	}

	public void deleteWorld(final boolean boolean1, final int integer) {
	}

}
