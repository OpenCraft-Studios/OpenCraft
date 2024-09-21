
package net.opencraft.client.renderer.gui;

import net.opencraft.tileentity.TileEntityRenderer;
import net.opencraft.tileentity.TileEntitySign;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiEditSign extends GuiScreen {

    protected String screenTitle;
    private TileEntitySign entitySign;
    private int updateCounter;
    private int editLine;

    public GuiEditSign(final TileEntitySign jn) {
        this.screenTitle = "Edit sign message:";
        this.editLine = 0;
        this.entitySign = jn;
    }

    @Override
    public void initGui() {
        this.controlList.clear();
        Keyboard.enableRepeatEvents(true);
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done", 200, 20));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }

    @Override
    protected void actionPerformed(final GuiButton iq) {
        if (!iq.enabled) {
            return;
        }
        if (iq.buttonId == 0) {
            this.entitySign.onInventoryChanged();
            this.id.displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(final char character, final int integer) {
        if (integer == 200) {
            this.editLine = (this.editLine - 1 & 0x3);
        }
        if (integer == 208 || integer == 28) {
            this.editLine = (this.editLine + 1 & 0x3);
        }
        if (integer == 14 && this.entitySign.signText[this.editLine].length() > 0) {
            this.entitySign.signText[this.editLine] = this.entitySign.signText[this.editLine].substring(0, this.entitySign.signText[this.editLine].length() - 1);
        }
        if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_'*!\"#%/()=+?[]{}<>".indexOf((int) character) >= 0 && this.entitySign.signText[this.editLine].length() < 15) {
            final StringBuilder sb = new StringBuilder();
            final String[] signText = this.entitySign.signText;
            final int editLine = this.editLine;
            signText[editLine] = sb.append(signText[editLine]).append(character).toString();
        }
    }

    @Override
    public void drawScreen(final int integer1, final int integer2, final float float3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 40, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) (this.width / 2), (float) (this.height / 2), 50.0f);
        final float n = 60.0f;
        GL11.glScalef(-n, -n, -n);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        if (this.updateCounter / 6 % 2 == 0) {
            this.entitySign.lineBeingEdited = this.editLine;
        }
        GL11.glRotatef(this.entitySign.getBlockMetadata() * 360 / 16.0f, 0.0f, 1.0f, 0.0f);
        TileEntityRenderer.instance.renderTileEntityAt(this.entitySign, -0.5, -0.75, -0.5, 0.0f);
        this.entitySign.lineBeingEdited = -1;
        GL11.glPopMatrix();
        super.drawScreen(integer1, integer2, float3);
    }
}
