package net.opencraft.renderer.gui;

import net.opencraft.OpenCraft;
import net.opencraft.renderer.font.FontRenderer;

import org.lwjgl.opengl.GL11;

public class GuiWorldButton extends GuiElement {

    private int width;
    private int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean enabled2;

    public GuiWorldButton(final int id, final int xPosition, final int yPosition, final String displayString) {
        this(id, xPosition, yPosition, 200, 20, displayString);
    }

    protected GuiWorldButton(final int id, final int xPosition, final int yPosition, final int width, final int height, final String displayString) {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.enabled2 = true;
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.displayString = displayString;
    }

    public void drawButton(final OpenCraft aw, final int integer2, final int integer3) {
        if (!this.enabled2) {
            return;
        }
        final FontRenderer fontRenderer = aw.fontRenderer;
        GL11.glBindTexture(3553, aw.renderEngine.getTexture("/assets/gui/gui.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int n = 1;
        final boolean b = integer2 >= this.xPosition && integer3 >= this.yPosition && integer2 < this.xPosition + this.width && integer3 < this.yPosition + this.height;
        if (!this.enabled) {
            n = 0;
        } else if (b) {
            n = 2;
        }
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + n * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + n * 20, this.width / 2, this.height);
        if (!this.enabled) {
            this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, -6250336);
        } else if (b) {
            this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
        } else {
            this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
        }
    }

    public boolean mousePressed(final int integer1, final int integer2) {
        return this.enabled && integer1 >= this.xPosition && integer2 >= this.yPosition && integer1 < this.xPosition + this.width && integer2 < this.yPosition + this.height;
    }
}
