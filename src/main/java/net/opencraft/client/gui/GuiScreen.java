
package net.opencraft.client.gui;

import java.util.ArrayList;
import java.util.List;
import net.opencraft.OpenCraft;
import net.opencraft.client.font.FontRenderer;
import net.opencraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiScreen extends Gui {

    protected OpenCraft id;
    public int width;
    public int height;
    protected List controlList;
    public boolean allowUserInput;
    protected FontRenderer fontRenderer;

    public GuiScreen() {
        this.controlList = (List) new ArrayList();
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
            for (int i = 0; i < this.controlList.size(); ++i) {
                final GuiButton iq = (GuiButton) this.controlList.get(i);
                if (iq.mousePressed(integer1, integer2)) {
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
        this.fontRenderer = aw.fontRenderer;
        this.width = integer2;
        this.height = integer3;
        this.initGui();
    }

    public void initGui() {
    }

    public void e() {
        while (Mouse.next()) {
            this.f();
        }
        while (Keyboard.next()) {
            this.handleKeyboardInput();
        }
    }

    public void f() {
        if (Mouse.getEventButtonState()) {
            final int n = Mouse.getEventX() * this.width / this.id.width;
            final int n2 = this.height - Mouse.getEventY() * this.height / this.id.height - 1;
            this.drawSlotInventory(n, n2, Mouse.getEventButton());
        } else {
            final int n = Mouse.getEventX() * this.width / this.id.width;
            final int n2 = this.height - Mouse.getEventY() * this.height / this.id.height - 1;
            this.b(n, n2, Mouse.getEventButton());
        }
    }

    public void handleKeyboardInput() {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 87) {
                this.id.toggleFullscreen();
                return;
            }
            this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
    }

    public void updateScreen() {
    }

    public void onGuiClosed() {
    }

    public void drawDefaultBackground() {
        this.b(0);
    }

    public void b(final int integer) {
        if (this.id.theWorld != null) {
            this.drawGradientRect(0, 0, this.width, this.height, 1610941696, -1607454656);
        } else {
            GL11.glDisable(2896);
            GL11.glDisable(2912);
            final Tessellator instance = Tessellator.instance;
            GL11.glBindTexture(3553, this.id.renderEngine.getTexture("/assets/dirt.png"));
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final float n = 32.0f;
            instance.startDrawingQuads();
            instance.setColorOpaque_I(4210752);
            instance.addVertexWithUV(0.0, this.height, 0.0, 0.0, this.height / n + integer);
            instance.addVertexWithUV(this.width, this.height, 0.0, this.width / n, this.height / n + integer);
            instance.addVertexWithUV(this.width, 0.0, 0.0, this.width / n, 0 + integer);
            instance.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0 + integer);
            instance.draw();
        }
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void deleteWorld(final boolean boolean1, final int integer) {
    }
}
