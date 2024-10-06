
package net.opencraft.renderer.gui;

import java.util.ArrayList;
import java.util.List;

import net.opencraft.OpenCraft;
import net.opencraft.client.input.MouseInput;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.font.FontRenderer;

import org.lwjgl.glfw.GLFW;
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

    protected void drawSlotInventory(final int integer1, final int integer2, final int integer3) {
        if (integer3 == 0) {
			for(GuiElement guiElement : this.controlList) {
				final GuiButton iq = (GuiButton) guiElement;
				if(iq.mousePressed(integer1, integer2)) {
					this.id.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
					this.actionPerformed(iq);
				}
			}
        }
    }

    protected void b(final int integer1, final int integer2, final int integer3) {
        if (integer3 == 0) {
            for (int i = 0; i < this.controlList.size(); ++i) {
                final GuiButton iq = (GuiButton) this.controlList.get(i);
                iq.mouseReleased(integer1, integer2);
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
        // TODO plz fix
        for(Integer key : this.id.keyboard.pressedKeys) {
            if(GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key)) != null)
                this.handleKeyboardInput(GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key)).charAt(0), key);
        }
    }

    public void handleMouseEvent(MouseInput.ButtonEvent event) {
        final int n = ((int) id.mouse.position.x) * this.width / this.id.width;
        final int n2 = this.height - ((int) id.mouse.position.y) * this.height / this.id.height - 1;
        if (event.isPress()) {
            this.drawSlotInventory(n, n2, event.buttonNumber());
        } else {
            this.b(n, n2, event.buttonNumber());
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
