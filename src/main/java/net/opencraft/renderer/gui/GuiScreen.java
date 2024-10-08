
package net.opencraft.renderer.gui;

import java.util.ArrayList;
import java.util.List;

import net.opencraft.OpenCraft;
import net.opencraft.client.input.MouseInput;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.font.FontRenderer;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL11;

public abstract class GuiScreen extends GuiElement {

    protected OpenCraft id;
    public int width;
    public int height;
    protected List<GuiElement> controlList;
    public boolean allowUserInput;
    protected FontRenderer fontRenderer;

    public GuiScreen() {
        this.controlList = new ArrayList<>();
        this.allowUserInput = false;
    }

    public void drawScreen(final int integer1, final int integer2, final float float3) {
        for (int i = 0; i < this.controlList.size(); ++i) {
            ((GuiButton) this.controlList.get(i)).drawButton(this.id, integer1, integer2);
        }
    }

    protected void keyTyped(final char character, final int integer) {
        if (integer == 1) {
            this.id.displayGuiScreen(null);
            this.id.setIngameFocus();
        }
    }

    protected void onMouseButtonPressed(final int x, final int y, final int mouseButtonNumber) {
        if (mouseButtonNumber == MouseInput.ButtonEvent.BUTTON_1_PRESS.buttonNumber()) {
			for(GuiElement guiElement : this.controlList) {
				final GuiButton iq = (GuiButton) guiElement;
				if(iq.mousePressed(x, y)) {
					this.id.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
					this.actionPerformed(iq);
				}
			}
        }
    }

    protected void onMouseButtonReleased(final int x, final int y, final int button) {
        if(button == MouseInput.ButtonEvent.BUTTON_1_RELEASE.buttonNumber()) {
			for(GuiElement guiElement : this.controlList) {
                if(guiElement instanceof GuiButton guiButton) {
                    guiButton.mouseReleased(x, y);
                }
			}
        }
    }

    protected void actionPerformed(final GuiButton iq) {
    }

    public void setWorldAndResolution(final OpenCraft aw, final int integer2, final int integer3) {
        this.id = aw;
        this.fontRenderer = aw.font;
        this.width = integer2;
        this.height = integer3;
        this.initGui();
    }

    public void initGui() {
    }

    public void handleInputEvents() {
        for(MouseInput.ButtonEvent event : this.id.mouse.buttons.events) {
            this.handleMouseEvent(event);
        }
        for(int key : this.id.keyboard.pressedKeys) {
            if(glfwGetKeyName(key, glfwGetKeyScancode(key)) != null)
                this.handleKeyboardInput(glfwGetKeyName(key, glfwGetKeyScancode(key)).charAt(0), key);
        }
    }

    public void handleMouseEvent(MouseInput.ButtonEvent event) {
        final int x = ((int) id.mouse.position.x) * this.width / this.id.width;
        final int y = this.height - ((int) id.mouse.position.y) * this.height / this.id.height - 1;
        if (event.isPress()) {
            this.onMouseButtonPressed(x, y, event.buttonNumber());
        } else {
            this.onMouseButtonReleased(x, y, event.buttonNumber());
        }
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
        if (this.id.world != null) {
            this.drawGradientRect(0, 0, this.width, this.height, 1610941696, -1607454656);
        } else {
            GL11.glDisable(2896);
            GL11.glDisable(2912);
            final Tessellator instance = Tessellator.instance;
            GL11.glBindTexture(3553, this.id.renderer.loadTexture("/assets/dirt.png"));
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final float n = 32.0f;
            instance.beginQuads();
            instance.setColorOpaque_I(4210752);
            instance.vertexUV(0.0, this.height, 0.0, 0.0, this.height / n + integer);
            instance.vertexUV(this.width, this.height, 0.0, this.width / n, this.height / n + integer);
            instance.vertexUV(this.width, 0.0, 0.0, this.width / n, 0 + integer);
            instance.vertexUV(0.0, 0.0, 0.0, 0.0, 0 + integer);
            instance.draw();
        }
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void deleteWorld(final boolean boolean1, final int integer) {
    }
}
