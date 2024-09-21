
package net.opencraft.renderer.gui;

public class GuiEmptyScreen extends GuiScreen {

    private String message1;
    private String message2;

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(final int integer1, final int integer2, final float float3) {
        this.drawGradientRect(0, 0, this.width, this.height, -12574688, -11530224);
        this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 90, 16777215);
        this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 110, 16777215);
        super.drawScreen(integer1, integer2, float3);
    }

    @Override
    protected void keyTyped(final char character, final int integer) {
    }
}
